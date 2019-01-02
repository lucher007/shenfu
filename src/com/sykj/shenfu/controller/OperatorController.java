package com.sykj.shenfu.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.interfaces.RSAPrivateKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sykj.shenfu.common.AesSecret;
import com.sykj.shenfu.common.ConfigUtil;
import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.common.loginencryption.RSAUtils;
import com.sykj.shenfu.dao.IEmployeeDao;
import com.sykj.shenfu.dao.IMenuDao;
import com.sykj.shenfu.dao.IOperatorDao;
import com.sykj.shenfu.dao.IOperatorrolerefDao;
import com.sykj.shenfu.dao.IRoleDao;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Menu;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Operatorroleref;
import com.sykj.shenfu.po.Role;
import com.sykj.shenfu.service.IOperatorlogService;


/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/operator")
@Transactional
public class OperatorController extends BaseController {

	private static final String LOGIN_COOKIE_STARTNAME = "tfc_login_cookie_";// 用户首页cookie前缀名

	@Autowired
	private ServletContext servletContext;
	@Autowired
	private IOperatorDao operatorDao;
	@Autowired
	private IMenuDao menuDao;
	@Autowired
	private IRoleDao roleDao;
	@Autowired
	private IOperatorrolerefDao operatorrolerefDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private IEmployeeDao employeeDao;
	
	/**
	 * 初始化登录验证
	 */
	@RequestMapping(value = "/initLogin")
	public String initLogin(HttpSession httpSession, HttpServletRequest request, Model model) throws UnsupportedEncodingException {
		
		// 判断是否需要验证码
		String captcha_check = ConfigUtil.getConfigFilePath("captcha_check", "system.properties");
		model.addAttribute("captcha_check", captcha_check);
		return "login";
		
	}

	/**
	 * 初始化登录验证
	 */
	@RequestMapping(value = "/changeLanguage")
	public String changeLanguage(HttpSession httpSession, HttpServletRequest request, Locale locale, Model model) {
		// 判断是否需要验证码
		String captcha_check = ConfigUtil.getConfigFilePath("captcha_check", "system.properties");
		model.addAttribute("captcha_check", captcha_check);
		return "login";
	}

	/**
	 * 用户登录
	 * @throws Exception 
	 */
	@ResponseBody
	// 此标志就是返回json数据给页面的标志
	@RequestMapping(value = "/login")
	public Map<String, Object> login(Operator form, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 封装返回给页面的json对象
		HashMap<String, Object> responseJson = new HashMap<String, Object>();
		
		System.out.println(form.getLoginname());
		
		RSAPrivateKey privateKey = (RSAPrivateKey)getSession().getAttribute("privateKey");  
		String descrypedPwd = RSAUtils.decryptByPrivateKey(form.getPassword(), privateKey); //解密后的密码,password是提交过来的密码  
		
		System.out.println(descrypedPwd);
		
		//将页面输入的密码加密
		String passwordEncrypt = AesSecret.aesEncrypt(descrypedPwd, AesSecret.key);
		
		// 判断是否需要验证码
		String captcha_check = ConfigUtil.getConfigFilePath("captcha_check", "system.properties");
		if ("1".equals(captcha_check)) {// 系统要求输入验证码
			if (!form.getLogincode().equalsIgnoreCase(session.getAttribute("captcha").toString())) {
				responseJson.put("flag", "logincode_error");
				responseJson.put("error", "验证码不正确");
				return responseJson;
			}
		}

		Operator operator = operatorDao.findByLoginname(form);
		if (operator != null) {
			if (!"1".equals(operator.getStatus())) {
				responseJson.put("flag", "loginname_error");
				responseJson.put("error", "无效的操作员");
				return responseJson;
			} else if (!operator.getPassword().equals(passwordEncrypt)) {
				responseJson.put("flag", "password_error");
				responseJson.put("error", "登录密码不正确");
				return responseJson;
			}
			// 保存操作员到session中
			session.setAttribute("Operator", operator);

			// 判断是否单点登录（不允许同一个操作员登录同时在俩个客户端在线）
			String single_login = ConfigUtil.getConfigFilePath("single_login", "system.properties");
			if (single_login.equals("1")) {
				SessionListener.isLogined(session, form.getLoginname());
			}
			responseJson.put("flag", "0");
			return responseJson;
		} else {
			responseJson.put("flag", "loginname_error");
			responseJson.put("error", "登录账号不正确");
			return responseJson;
		}
	}

	/**
	 * 用户登录后判断权限
	 * 
	 * @Title: logined
	 */
	@RequestMapping(value = "/logined")
	public String logined(Operator form, Model model, HttpSession session) {
		
		Operator operator = (Operator) session.getAttribute("Operator");
		
		//菜单对象
		List<Menu> menulist = new ArrayList<Menu>();
			
		if("admin".equals(operator.getLoginname())){//超级管理员admin能查询到所有的菜单
			  // 查询所有一级菜单
			  Menu menuform = new Menu(); 
			  menuform.setMenutype("1");
			  menuform.setState("1");
			  // 查询有效的菜单
			  menulist = menuDao.queryByList(menuform);
			  // 封装各种1级菜单下的二级菜单
			  if (menulist != null && menulist.size() > 0) { 
				  for (Menu menu : menulist) { 
					  List<Menu> secondmenulist = menuDao.querySecondMenuByPid(menu);
					  menu.setSecondmenulist(secondmenulist); 
			      } 
			  } else {
				  System.out.println("该用户没有权限!");
			  }
		}else{
			Integer roleid = operatorrolerefDao.findByOperatorid(operator.getId()).getRoleid();
			List<Menu> list = menuDao.queryFirstMenuByRoleid(roleid);
			List<Integer> ids = new ArrayList<Integer>();
			for (Menu pid : list) {
				ids.add(pid.getPid());
			}
			menulist = menuDao.queryMenuByIds(ids);
			if (menulist != null && menulist.size() > 0) {
				for (Menu firstmenu : menulist) {
					Menu findsecondmenu = new Menu();
					findsecondmenu.setRoleid(roleid);
					findsecondmenu.setPid(firstmenu.getId());
					List<Menu> secondmenulist = menuDao.queryRelatedSecondMenu(findsecondmenu);
					firstmenu.setSecondmenulist(secondmenulist);
				}
			} else {
				// menu.setReturninfo("该用户没有权限!");
				System.out.println("该用户没有权限!");
			}
		}
		
		model.addAttribute("menulist", menulist);
		model.addAttribute("operator", operator);
		// saveOpLog(user,
		// Constant.LOGS_OPERATETYPE_LOGIN_ON,"用户登录",this.getRequest());
		return "main/main";
	}

	/**
	 * 重新回到登录界面（SESSION断掉）
	 */
	@RequestMapping(value = "/noPermission")
	public String noPermission(HttpSession httpSession, HttpServletRequest request, Model model) throws UnsupportedEncodingException {
		return "noPermission";
	}
	
	/**
	 * 退出系统
	 */
	@RequestMapping(value = "/logout")
	public String logout(HttpSession httpSession, HttpServletRequest request, Model model) throws UnsupportedEncodingException {
		
		//清空操作员
		httpSession.setAttribute("Operator", null);
		
		
		// 判断是否需要验证码
		String captcha_check = ConfigUtil.getConfigFilePath("captcha_check", "system.properties");
		model.addAttribute("captcha_check", captcha_check);
		return "login";
	}
	
	/**
	 * 查询用户信息
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Operator form) {
		return "operator/findOperatorList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Operator form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Integer total = operatorDao.findByCount(form);
		List<Operator> list = operatorDao.findByList(form);
		
		for (Operator operator : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			Employee employee = employeeDao.findByEmployeecodeStr(operator.getEmployeecode());
			if(employee == null){
				employee = new Employee();
			}
			operator.setEmployee(employee);
			objectMap.put("id", operator.getId());
			objectMap.put("loginname", operator.getLoginname());
			objectMap.put("operatortype", operator.getOperatortype());
			objectMap.put("operatortypename", operator.getOperatortypename());
			objectMap.put("addtime", StringUtils.isEmpty(operator.getAddtime())?"":Tools.getStr(operator.getAddtime()).substring(0, 19));
			objectMap.put("status",  operator.getStatus());
			objectMap.put("statusname",  operator.getStatusname());
			objectMap.put("employeecode", operator.getEmployeecode());
			objectMap.put("employeename", employee.getEmployeename());

			//员工信息
			objectMap.put("employeename", employee.getEmployeename());
			objectMap.put("employeesex", employee.getEmployeesex());
			objectMap.put("employeesexname", employee.getEmployeesexname());
			objectMap.put("birthday", StringUtils.isEmpty(employee.getBirthday())?"":Tools.getStr(employee.getBirthday()).substring(0, 10));
			objectMap.put("identification", employee.getIdentification());
			objectMap.put("email", employee.getEmail());
			objectMap.put("address", employee.getAddress());
			objectMap.put("phone", employee.getPhone());
			objectMap.put("level", employee.getLevel());
			objectMap.put("status", employee.getStatus());
			objectMap.put("statusname", employee.getStatusname());
			objectMap.put("department", employee.getDepartment());
			objectMap.put("departmentname", employee.getDepartmentname());
			objectMap.put("employeetype", employee.getEmployeetype());
			objectMap.put("employeetypename", employee.getEmployeetypename());
			objectMap.put("bankcardname", employee.getBankcardname());
			objectMap.put("bankcardno", employee.getBankcardno());
			
			objectlist.add(objectMap);
		}
		
		result.put("total", total);
		result.put("rows", objectlist);
		return result;
	}

	/**
	 * 添加用户信息初始化
	 * 
	 * @return
	 */
	@RequestMapping(value = "/addInit")
	public String addInit(Operator form) {

		// 构建Role对象
		List<Role> roleList = roleDao.queryByList(new Role());
		Map<Integer, String> roleMap = new HashMap<Integer, String>();
		for (Role role : roleList) {
			roleMap.put(role.getId(), getMessage(role.getRolename()));
		}
		form.setRolemap(roleMap);

		return "operator/addOperator";
	}

	/**
	 * 新增
	 */
	@RequestMapping(value = "/save")
	public String save(Operator form) {
		form.setStatus("1");// 有效
		form.setOperatortype("1");//操作员
		form.setAddtime(Tools.getCurrentTime());// 取当前时间
		if ("".equals(form.getLoginname())) {
			form.setReturninfo("登录名称不能为空");
			return addInit(form);
		} else {
			Operator oldOperator = operatorDao.findByLoginname(form);
			if (oldOperator != null) {
				form.setReturninfo("登录名称已经存在");
				return addInit(form);
			}
		}
		
		//通过AES加密
		String passwordEncrypt = AesSecret.aesEncrypt(form.getPassword(), AesSecret.key);
		form.setPassword(passwordEncrypt);
		operatorDao.save(form);
		
		Operatorroleref orref = new Operatorroleref();
		orref.setOperatorid(form.getId());
		orref.setRoleid(form.getRoleid());
		operatorrolerefDao.save(orref);
        
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "增加操作员，操作员登录名称为:"+form.getLoginname();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("添加成功");
		return addInit(form);
	}

	/**
	 * 更新初始化
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Operator form) {
        
		Operator operator = operatorDao.findById(form.getId());
		Employee employee = null;
		if(StringUtils.isNotEmpty(operator.getEmployeecode())){
			employee = employeeDao.findByEmployeecodeStr(operator.getEmployeecode());
		}
		if(employee == null){
			employee = new Employee();
		}
		operator.setEmployee(employee);
		form.setOperator(operator);
		
		Operatorroleref operatorroleref = operatorrolerefDao.findByOperatorid(form.getId());
		if(operatorroleref == null){
			operatorroleref = new Operatorroleref();
		}

		form.setRoleid(operatorroleref.getRoleid());

		// 构建Role对象
		List<Role> roleList = roleDao.queryByList(new Role());
		Map<Integer, String> roleMap = new HashMap<Integer, String>();
		for (Role role : roleList) {
			roleMap.put(role.getId(), getMessage(role.getRolename()));
		}
		form.setRolemap(roleMap);

		return "operator/updateOperator";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update")
	public String update(Operator form) {

		if ("".equals(form.getLoginname())) {
			form.setReturninfo("登录名称不能为空");
			return updateInit(form);
		} else {
			Operator oldOperator = operatorDao.findByLoginname(form);
			if (oldOperator != null && !oldOperator.getId().equals(form.getId())) {
				form.setReturninfo(getMessage("登录名称已经存在"));
				return updateInit(form);
			}
		}

		Operatorroleref orref = operatorrolerefDao.findByOperatorid(form.getId());
		orref.setOperatorid(form.getId());
		orref.setRoleid(form.getRoleid());
		operatorrolerefDao.update(orref);
		// 修改信息
		operatorDao.update(form);
        
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "修改操作员信息，操作员登录名称为:"+form.getLoginname();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}

	/**
	 * 密码修改初始化
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updatePasswordInit")
	public String updatePasswordInit(Operator form) {
		form.setOperator(operatorDao.findById(form.getId()));
		return "operator/updatePassword";
	}

	/**
	 * 密码修改
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updatePassword")
	public String updatePassword(Operator form) {

		if ("dialog".equals(form.getRemark())) {
			Operator oldOperator = operatorDao.findById(form.getId());
			//数据库的原密码
			String oldpassword = Tools.getStr(oldOperator.getPassword());
			//页面输入的原密码
			String oldpassword_input = Tools.getStr(getRequest().getParameter("oldPassword"));
			//将页面输入的密码加密
			String oldpasswordEncrypt = AesSecret.aesEncrypt(oldpassword_input, AesSecret.key);
			
			if (!oldpasswordEncrypt.equals(oldpassword)) {
				form.setReturninfo("原密码输入不正确");
				return updatePasswordInit(form);
			}
		}

		//页面输入的新密码
		String password_input = Tools.getStr(getRequest().getParameter("password"));
		//将页面输入的密码加密
		String passwordEncrypt = AesSecret.aesEncrypt(password_input, AesSecret.key);
		form.setPassword(passwordEncrypt);
		Integer updateFlag = operatorDao.updatePassword(form);
		form.setReturninfo("修改成功");
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "修改登录密码，操作员登录名称为:"+form.getLoginname();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		return updatePasswordInit(form);
	}
    
	/**
	 * 修改购买中的设备主副机属性
	 * @return
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value="/delete")
	public Map<String,Object> delete(Operator form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		// 删除网络，实际上就是修改state为0-无效
		Operator operatordmp = operatorDao.findById(form.getId());
		operatordmp.setStatus("0");
		operatorDao.update(operatordmp);

		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "删除登录员，操作员登录名称为:"+operatordmp.getLoginname();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		return responseJson;
	}
	
	
	
	
	/**
	 * 主界面系统帮助
	 * 
	 * @return
	 */
	@RequestMapping(value = "/helpInfoInit")
	public String helpInfoInit() {
		return "operator/helpInfo";
	}
	
	
	/**
	 * 系统帮助文档下载
	 */
	@RequestMapping(value = "/downloadHelpDocument")
	public void downloadHelpDocument(Operator form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String import_template_path = servletContext.getInitParameter("import_template_path");
			String filename = "";
			if("stbfiletemplate".equals(request.getParameter("filetype"))){ //机顶盒入库文件模板
				filename = "stbinfo_template.xls";
			}else if("cardfiletemplate".equals(request.getParameter("filetype"))){
				filename = "cardinfo_template.xls";
			}else if("printPlugin".equals(request.getParameter("filetype"))){
				filename = "printPlugin.exe";
			}
			
			String folderpath = servletContext.getRealPath(File.separator) + import_template_path + File.separatorChar + filename;
			File excelTemplate = new File(folderpath);
			response.reset();
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Length", "" + excelTemplate.length()); // 文件大小
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
			FileInputStream fis = new FileInputStream(excelTemplate);
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			byte[] buffer = new byte[1024 * 1024];
			while (fis.read(buffer) > 0) {
				toClient.write(buffer);
			}
			fis.close();
			toClient.flush();
			toClient.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	/**
	 * 查询操作员信息
	 */
	@RequestMapping(value = "/findOperatorListForDialog")
	public String findOperatorListForDialog(Operator form) {
		if (StringUtils.isEmpty(form.getStatus())) {
			form.setStatus("1");
		}
		form.setPager_openset(5);
		form.setPager_count(operatorDao.findByCount(form));
		form.setOperatorlist(operatorDao.findByList(form));

		return "operator/findOperatorListForDialog";
	}
}
