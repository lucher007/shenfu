package com.sykj.shenfu.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.JDOMException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

import com.google.gson.Gson;
import com.sykj.shenfu.common.ExportExcel;
import com.sykj.shenfu.common.PrimaryGenerater;
import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.common.weixin.HttpRequest;
import com.sykj.shenfu.common.weixin.WeiXinConfig;
import com.sykj.shenfu.common.weixin.WeiXinPayCommonUtil;
import com.sykj.shenfu.common.weixin.WeixinGetOpenid;
import com.sykj.shenfu.common.weixin.XMLUtil;
import com.sykj.shenfu.dao.IEmployeeDao;
import com.sykj.shenfu.dao.IProductmodelDao;
import com.sykj.shenfu.dao.ISaleDao;
import com.sykj.shenfu.dao.IUserDao;
import com.sykj.shenfu.dao.IUserbusinessDao;
import com.sykj.shenfu.dao.IUserorderDao;
import com.sykj.shenfu.dao.IUsertaskDao;
import com.sykj.shenfu.dao.IWechatpayDao;
import com.sykj.shenfu.po.Coderule;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.JsonObjectForPara;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Productmodel;
import com.sykj.shenfu.po.Sale;
import com.sykj.shenfu.po.User;
import com.sykj.shenfu.po.Userbusiness;
import com.sykj.shenfu.po.Userorder;
import com.sykj.shenfu.po.Userpaylog;
import com.sykj.shenfu.po.Usertask;
import com.sykj.shenfu.po.Wechatpay;
import com.sykj.shenfu.service.ICoderuleService;
import com.sykj.shenfu.service.IHttpForMpsService;
import com.sykj.shenfu.service.IOperatorService;
import com.sykj.shenfu.service.IOperatorlogService;
import com.sykj.shenfu.service.ISaleService;

import fr.opensagres.xdocreport.utils.StringUtils;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/sale")
@Transactional
public class SaleController extends BaseController {

	@Autowired
	private ISaleDao saleDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private IOperatorService operatorService;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private IUsertaskDao taskDao;
	@Autowired
	private IUserorderDao userorderDao;
	@Autowired
	private IUserbusinessDao userbusinessDao;
	@Autowired
	private ICoderuleService coderuleService;
	@Autowired
	private IProductmodelDao productmodelDao;
	@Autowired
	private ISaleService saleService;
	@Autowired
	private IWechatpayDao wechatpayDao;
	@Autowired
	private IHttpForMpsService httpForMpsService;
	@Autowired
	private ServletContext servletContext;
	/*
     * 特殊的instance变量
     * 注：零长度的byte数组对象创建起来将比任何对象都经济――查看编译后的字节码：
     * 生成零长度的byte[]对象只需3条操作码，而Object lock = new Object()则需要7行操作码。
     */
    private static byte[] lock = new byte[0];   
	
	/**
	 * 查询销售单
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Sale form) {
		return "sale/findSaleList";
	}
	
	/**
	 * 查询任务单信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Sale form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Integer total = saleDao.findByCount(form);
		List<Sale> list = saleDao.findByList(form);
		for (Sale sale : list) {

			Employee saler = employeeDao.findByEmployeecodeStr(sale.getSalercode());
			if(saler == null){
				saler = new Employee();
			}
			
			Operator operator = operatorService.findByEmployeecode(sale.getOperatorcode());
			if(operator == null){
				operator = new Operator();
			}
			
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			
			objectMap.put("id", sale.getId());
			objectMap.put("salecode", sale.getSalecode());
			objectMap.put("intention", sale.getIntention());
			objectMap.put("status", sale.getStatus());
			objectMap.put("content", sale.getContent());
			objectMap.put("addtime", sale.getAddtime());
			objectMap.put("remark", sale.getRemark());
			//潜在订户信息
			objectMap.put("usercode", sale.getUsercode());
			objectMap.put("username", sale.getUsername());
			objectMap.put("phone", sale.getPhone());
			objectMap.put("usersex", sale.getUsersex());
			objectMap.put("source", sale.getSource());
			objectMap.put("address", sale.getAddress());
			
			//销售人员信息
			objectMap.put("salercode",sale.getSalercode());
			objectMap.put("salername",saler.getEmployeename());
			objectMap.put("salerphone", saler.getPhone());
			
			//操作员信息
			objectMap.put("operatorcode", sale.getOperatorcode());
			objectMap.put("operatorname", operator.getEmployee().getEmployeename());
			
			objectlist.add(objectMap);
			
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}

	/**
	 * 添加销售单页面初始化
	 */
	@RequestMapping(value = "/addInit")
	public String addInit(Sale form) {
		return "sale/addSale";
	}

	/**
	 * 保存添加销售单
	 */
	@RequestMapping(value = "/save")
	public String save(Sale form) {
		
		//加锁
		synchronized(lock) {
			Operator operator = (Operator)getSession().getAttribute("Operator");
			//最大任务单号
			String maxSaleno = saleDao.findMaxSaleno();
			form.setSalecode(PrimaryGenerater.generaterNextNumber(maxSaleno));
			form.setAddtime(Tools.getCurrentTime());
			form.setOperatorcode(operator.getEmployeecode());
			//默认状态为未处理0
			form.setStatus("0");
			saleDao.save(form);
			
			//修改潜在订户的状态
			User user = userDao.findByUsercodeStr(form.getUsercode());
			user.setStatus("1");//已生成销售单
			userDao.update(user);
			
			//增加操作日记
			String content = "添加销售单， 销售单号:"+form.getSalecode();
			operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		} 
		
		form.setReturninfo("保存成功");
		
		return addInit(form);
	}

	/**
	 * 编辑销售单权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Sale form) {
		Sale sale = saleDao.findById(form.getId());
		sale.setUser(userDao.findByUsercodeStr(sale.getUsercode()));
		sale.setSaler(employeeDao.findByEmployeecodeStr(sale.getSalercode()));
		form.setSale(sale);
		return "sale/updateSale";
	}

	/**
	 * 保存编辑后销售单权限
	 */
	@RequestMapping(value = "/update")
	public String update(Sale form) {
		
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		if(!"3".equals(form.getStatus())){
			form.setContent(null);
		}
		
		saleDao.update(form);
		//增加操作日记
		String content = "修改销售单信息，销售单编号:"+form.getSalecode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}

	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Sale form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Sale sale = saleDao.findById(form.getId());
		
		//删除销售单
		saleDao.delete(form.getId());
		
		//修改用户状态
		User user = userDao.findByUsercodeStr(sale.getUsercode());
		user.setStatus("0");//未处理
		userDao.update(user);
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "删除销售单，销售单编号:"+sale.getSalecode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
	/**
	 * 生成任务单
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/saveComplete")
	public Map<String,Object> saveComplete(Sale form) {
		
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		//加锁
		synchronized(lock) {
			Operator operator = (Operator)getSession().getAttribute("Operator");
			
			Sale sale = saleDao.findById(form.getId());
			sale.setStatus("2");//生成任务单
			sale.setAddtasktime(Tools.getCurrentTime());//生成任务单时间
			saleDao.update(sale);
			
			//修改客户状态
			User user = userDao.findByUsercodeStr(sale.getUsercode());
			user.setStatus("2");//修改成已派任务单状态
			userDao.update(user);
			
			//增加任务单
			Usertask task = new Usertask();
			//任务单号
			String tastcode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_taskcode);
			task.setTaskcode(tastcode);
			task.setAddtime(Tools.getCurrentTime());
			task.setOperatorcode(operator.getEmployeecode());
			task.setStatus("0");//默认状态为未处理0
			//封装订户信息
			task.setUsercode(sale.getUsercode());
			task.setUsername(sale.getUsername());
			task.setUsersex(sale.getUsersex());
			task.setPhone(sale.getPhone());
			task.setAddress(sale.getAddress());
			task.setSource(sale.getSource());
			//封装销售人ID
			task.setSalercode(sale.getSalecode());
			taskDao.save(task);
			
			//增加业务记录
			Userbusiness userbusiness = new Userbusiness();
			userbusiness.setOperatorcode(operator.getEmployeecode());
			userbusiness.setBusinesstypekey("taskadd");
			userbusiness.setBusinesstypename("任务单生成");
			userbusiness.setUsercode(sale.getUsercode());
			userbusiness.setOrdercode(null);
			userbusiness.setDispatchcode(null);
			userbusiness.setAddtime(Tools.getCurrentTime());
			userbusiness.setContent("生成任务单；任务单号为： "+task.getTaskcode());
			userbusiness.setSource("0");//PC平台操作
			userbusinessDao.save(userbusiness);
			
			//增加操作日记
			//Operator operator = (Operator)getSession().getAttribute("Operator");
			String content = "销售单生成任务单，销售单号:"+sale.getSalecode()+";生成的任务单号:"+task.getTaskcode();
			operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
			
			responseJson.put("flag", "1");//保存成功
			responseJson.put("msg", "保存成功");
		}
		
		
		return responseJson;
	}
	
	/**
	 * 查询任务单信息
	 */
	@RequestMapping(value = "/findSaleListDialog")
	public String findSaleListDialog(Sale form) {
		return "sale/findSaleListDialog";
	}
	
	
	/**
	 * 查询销售总量龙虎榜
	 */
	@RequestMapping(value = "/findListByTotalstat")
	public String findListByTotalstat(Sale form) {
		return "sale/findListByTotalstat";
	}
	
	/**
	 * 查询销售龙虎榜信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListByTotalstatJson")
	public Map<String, Object> findListByTotalstatJson(Sale form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Integer total = saleDao.findCountByTotalstat(form);
		List<Sale> list = saleDao.findListByTotalstat(form);
		for (Sale sale : list) {

			Employee saler = employeeDao.findByEmployeecodeStr(sale.getSalercode());
			if(saler == null){
				saler = new Employee();
			}
			
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			
			objectMap.put("salercode", sale.getSalercode());
			objectMap.put("salername", saler.getEmployeename());
			objectMap.put("saletotal", sale.getSaletotal());
			objectMap.put("saletotalmoney", sale.getSaletotalmoney());
			
			objectlist.add(objectMap);
			
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}

	/**
	 * 导出EXL-销售龙虎榜统计
	 */
	@RequestMapping(value = "/saveExportSalestatForExcel")
	public String saveExportSalestatForExcel(Sale form, HttpServletResponse response) throws Exception {
		
		List<Sale> list = saleDao.queryListByTotalstat(form);
		List<HashMap<String,Object>> objecList = new ArrayList<HashMap<String,Object>>();
		if (list != null) {
			for (Sale sale : list) {
				
				Employee saler = employeeDao.findByEmployeecodeStr(sale.getSalercode());
				if(saler == null){
					saler = new Employee();
				}
				
				// 构建填充EXCEL文件所需资源包括数据库数据和国际化字段
				HashMap<String, Object> excelmap = new HashMap<String, Object>();
				// 初始化国际化表头以及对应的表值
				//销售姓名
				excelmap.put("销售员", saler.getEmployeename());
				//销售电话
				//excelmap.put("销售电话", saler.getPhone());
				//销售量
				excelmap.put("销售量", String.valueOf(sale.getSaletotal()));
				//销售总金额
				excelmap.put("总金额", String.valueOf(sale.getSaletotalmoney()));
				objecList.add(excelmap);
			}
			
			String[] columntitle = {"销售员", "销售量", "总金额" };
			//form.setReturninfo(ExportExcel.resultSetToExcel(objecList, columntitle, "销售龙虎榜", response));
		}
		return null;
	}
	
	
	/**
	 * 查询销售总量龙虎榜
	 */
	@RequestMapping(value = "/findListByPersonalstat")
	public String findListByPersonalstat(Sale form) {
		
		String opertime = form.getOpertime();
		if(opertime == null || "".equals(opertime)){//页面查询时间默认为今年
			opertime = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			form.setOpertime(opertime);
		}
		
		List<Sale> buildlist = saleDao.findListByPersonalstat(form);
		Map<String,BigDecimal> personaltotalmoneymap = new HashMap<String,BigDecimal>(); 
		for (Sale sale : buildlist) {
			personaltotalmoneymap.put(sale.getOpertime(), sale.getSaletotalmoney());
		}
		form.setPersonaltotalmoneymap(personaltotalmoneymap);
		
		return "sale/findListByPersonalstat";
	}
	
	/**
	 * 查询销售员信息列表
	 */
	@RequestMapping(value = "/findSalerByList")
	public String findSalerByList(Sale form) {
		return "sale/findSalerList";
	}
	
	
	/**
	 * 查询销售单-生成任务单
	 */
	@RequestMapping(value = "/findSaleListForTask")
	public String findSaleListForTask(Userorder form) {
		return "sale/findSaleListForTask";
	}
	
	/**
	 * 查询销售员销售等级列表
	 */
	@RequestMapping(value = "/findSalerlevelByList")
	public String findSalerlevelByList(Sale form) {
		return "sale/findSalerlevelList";
	}
	
	/**
	 * 查询客户信息列表
	 */
	@RequestMapping(value = "/findUserByList")
	public String findUserByList(Sale form) {
		return "sale/findUserList";
	}
	
	/**
	 * 编辑销售单权限初始化页面
	 */
	@RequestMapping(value = "/saleExtendInit")
	public String saleExtendInit(Sale form) {
		String salercode = Tools.getStr(getRequest().getParameter("salercode"));
		Employee saler = null;
		if(StringUtils.isNotEmpty(salercode)){
			saler =  employeeDao.findByEmployeecodeStr(salercode);
		}
		
		if(saler == null){
			saler = new Employee();
		}
		getSession().setAttribute("Saler", saler);
		getRequest().setAttribute("salercode", saler.getEmployeecode());
		getRequest().setAttribute("salername", saler.getEmployeename());
		getRequest().setAttribute("salerphone", saler.getPhone());
		
		return "shopping/sellProductByWeb";
	}
	
	/**
	 *  客户购买订单
	 */
	@RequestMapping(value = "/userAddOrderInit")
	public String userAddOrderInit(User form) {
		String salercode = Tools.getStr(form.getSalercode());
		Employee saler = null;
		if(StringUtils.isNotEmpty(salercode)){
			saler =  employeeDao.findByEmployeecodeStr(salercode);
		}
		
		if(saler == null){
			saler = new Employee();
		}
		getSession().setAttribute("Saler", saler);
		getRequest().setAttribute("salercode", saler.getEmployeecode());
		getRequest().setAttribute("salername", saler.getEmployeename());
		getRequest().setAttribute("salerphone", saler.getPhone());
		
		String visittype = Tools.getStr(getRequest().getParameter("visittype"));
		if("0".equals(visittype)){//客户选择需要体验，公司派人上门讲解
        	return "shopping/addUserForVisittype0";
        }else{
        	//需要加载产品型号
        	Productmodel productmodelForm = new Productmodel();
        	productmodelForm.setEffectivetimeflag("1");
        	List<Productmodel> productmodelList = productmodelDao.queryByList(productmodelForm);
        	
        	getRequest().setAttribute("productmodelList", productmodelList);
        	
        	return "shopping/addUserForVisittype1_userinfo";
        }
		
	}
	
	/**
	 * 保存添加订户
	 */
	@RequestMapping(value = "/saveUserAddOrderForVisittype0")
	public String saveUserAddOrderForVisittype0(User form) {
		Employee saler = (Employee)getSession().getAttribute("Saler");
		
		if(saler == null){
			saler = new Employee();
		}
		
		//System.out.println(saler.getEmployeecode());
		
		if ("".equals(form.getUsername())) {
			form.setReturninfo("请输入姓名");
			return userAddOrderInit(form);
		} 
		
		if ("".equals(form.getAddress())) {
			form.setReturninfo("请输入安装地址");
			return userAddOrderInit(form);
		} 
		
		if ("".equals(Tools.getStr(form.getPhone()))) {
			form.setReturninfo("请输入电话号码");
			return userAddOrderInit(form);
		}
		
		Map<String, Object> responseMap = saleService.saveUserAddOrderForVisittype0(form, getRequest(), getSession());
		form.setReturninfo(String.valueOf(responseMap.get("result")));
		return userAddOrderInit(form);
	}
	
	/**
	 * 保存客户订户
	 */
	@RequestMapping(value = "/saveUserAddOrderForVisittype1_userinfo")
	public String saveUserAddOrderForVisittype1_userinfo(User form) {
		
		if ("".equals(form.getUsername())) {
			form.setReturninfo("客户姓名不能为空");
			return userAddOrderInit(form);
		} 
		
		if ("".equals(form.getAddress())) {
			form.setReturninfo("安装地址不能为空");
			return userAddOrderInit(form);
		} 
		
		if ("".equals(Tools.getStr(form.getPhone()))) {
			form.setReturninfo("电话号码不能为空");
			return userAddOrderInit(form);
		}
		
		//需要加载产品型号
    	Productmodel productmodelForm = new Productmodel();
    	productmodelForm.setEffectivetimeflag("1");
    	List<Productmodel> productmodelList = productmodelDao.queryByList(productmodelForm);
    	
    	getRequest().setAttribute("productmodelList", productmodelList);
		
		return "shopping/addUserForVisittype1_productinfo";
	}
	
	/**
	 * 保存产品信息
	 */
	@RequestMapping(value = "/saveUserAddOrderForVisittype1_productinfo")
	public String saveUserAddOrderForVisittype1_productinfo(User form) {
		
		if ("".equals(Tools.getStr(form.getModelcode()))) {
			form.setReturninfo("请选择产品型号");
			return userAddOrderInit(form);
		}
		
		if ("".equals(Tools.getStr(form.getProductcolor()))) {
			form.setReturninfo("请选择产品颜色");
			return userAddOrderInit(form);
		}
		
		return "shopping/addUserForVisittype1_doorinfo";
	}
	
	/**
	 * 保存添加订户
	 */
	@ResponseBody
	@RequestMapping(value = "/saveUserAddOrderForVisittype1")
	public Map<String, Object> saveUserAddOrderForVisittype1(User form,@RequestParam("files") MultipartFile[] files) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		
		Employee saler = (Employee)getSession().getAttribute("Saler");
		
		if(saler == null){
			saler = new Employee();
		}
		
		System.out.println(saler.getEmployeecode());
		
		if ("".equals(form.getUsername())) {
			responseMap.put("result", "客户姓名不能为空");
			return responseMap;
		} 
		
		if ("".equals(form.getAddress())) {
			responseMap.put("result", "安装地址不能为空");
			return responseMap;
		} 
		
		if ("".equals(Tools.getStr(form.getPhone()))) {
			responseMap.put("result", "电话号码不能为空");
			return responseMap;
		}
		
		if ("".equals(Tools.getStr(form.getModelcode()))) {
			responseMap.put("result", "请选择产品型号");
			return responseMap;
		}
		
		if ("".equals(Tools.getStr(form.getProductcolor()))) {
			responseMap.put("result", "请选择产品颜色");
			return responseMap;
		}
		responseMap = saleService.saveUserAddOrderForVisittype1(form, getRequest(), getSession(),files);
		return responseMap;
	}
	
	
	/**
	 * 点此按钮到下一步查看个人订单
	 */
	@RequestMapping(value = "/queryUserorderByShopping")
	public String queryUserorderByShopping(User form) {
		User usershopping = (User)getRequest().getAttribute("Usershopping"); //商城客户
		if(usershopping == null){ //先登录
			return "shopping/userLoginByShopping";
		}else{
			//查询未处理的信息
			User userform = new User();
			userform.setStatus("0,1"); //未生成订单的客户
			userform.setUsercode(usershopping.getUsercode());
			List<User> userList = userDao.findByList(userform);
			for (User user : userList) {
				user.setUsername(Tools.getTransValueFromStr(user.getUsername(),1,user.getUsername().length()-1,"*"));
			}
			
			getRequest().setAttribute("userList", userList);
			
			//查询客户订单信息
			Userorder userorderform = new Userorder();
			userorderform.setUsercode(usershopping.getUsercode());
			List<Userorder> userorderList = userorderDao.queryByList(userorderform);
			getRequest().setAttribute("userorderList", userorderList);
			
			return "shopping/findUserorderListByShopping";
		}
	}
	
	/**
	 * 个人登录
	 */
	@RequestMapping(value = "/userLoginByShopping")
	public String userLoginByShopping(User form) {
		User usershopping = userDao.findByPhoneStr(form.getPhone());
		if(usershopping == null){ //先登录
			form.setReturninfo("该电话号码没有申请订购产品记录");
			return "shopping/userLoginByShopping";
		}else{
			usershopping.setUsername(Tools.getTransValueFromStr(usershopping.getUsername(),1,usershopping.getUsername().length()-1,"*"));
            //把客户信息保存到session中
			getRequest().setAttribute("Usershopping", usershopping);
			
			//查询未处理的信息
			User userform = new User();
			userform.setStatus("0,1"); //未生成订单的客户
			userform.setUsercode(usershopping.getUsercode());
			List<User> userList = userDao.queryByList(userform);
			for (User user : userList) {
				user.setUsername(Tools.getTransValueFromStr(user.getUsername(),1,user.getUsername().length()-1,"*"));
			}
			
			getRequest().setAttribute("userList", userList);
			
			//查询客户订单信息
			Userorder userorderform = new Userorder();
			userorderform.setUsercode(usershopping.getUsercode());
			List<Userorder> userorderList = userorderDao.queryByList(userorderform);
			getRequest().setAttribute("userorderList", userorderList);
			
			return "shopping/findUserorderListByShopping";
		}
	}
	
	/**
	 * 上传文件测试
	 */
	@RequestMapping(value = "/uploadimage")
	public String uploadimage(User form) {
		return "shopping/uploadimage";
		
	}
	
	
	/**
	 * 扫码一元注册抢购
	 */
	@RequestMapping(value = "/addUserApplyByScancodeInit")
	public String addUserApplyByScancodeInit(User form) {
		return "shopping/addUserApplyByScancode";
	}
	
	/**
	 * 订单支付
	 */
	@RequestMapping(value="/savePaymentInfoInit_getCode")
	@ResponseBody
	public void savePaymentInfoInit_getCode(User form, HttpServletRequest request, HttpServletResponse response) throws IOException {

		User user = userDao.findByPhoneStr(form.getPhone());
		if(user == null){
			user = new User();
			//客户姓名
			user.setUsername(form.getUsername());
			//客户电话
			user.setPhone(form.getPhone());
			//客户地址
			user.setAddress(form.getAddress());
			//上门类型（0.公司派人讲解，1.亲自讲解）
			user.setVisittype("1");
			//销售员编号
			user.setSalercode("");
			//审核状态
			user.setCheckstatus("1");
			user.setSource("0");
			user.setStatus("0"); //已处理，生成了讲解单
			user.setAddtime(Tools.getCurrentTime());
			user.setDealresult("参加一元试用扫码活动申请,还未支付一元");
			//客户编号
			String usercode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_usercode);
			user.setUsercode(usercode);
			//保存客户信息
			userDao.save(user);
		}else{
			user.setUsername(form.getUsername());
			user.setAddress(form.getAddress());
			user.setDealresult("参加一元试用扫码活动申请,还未支付一元");
			user.setStatus("0");//
			userDao.update(user);//新修改
		}
		
		//获取code
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		//String stateJson = form.getOrdercode() + "-;" + form.getPayitem() + "-;" + form.getPaymoney() + "-;" + form.getPaytype();
		
		JsonObjectForPara para = new JsonObjectForPara();
		para.setUsercode(user.getUsercode());
		//para.setPayitem("1");
		para.setPaymoney("1");//1元抢购费用
		//para.setPaytype(form.getPaytype());
		para.setJumpurl("sale/addUserApplyByScancodeInit");
		String stateJson = new Gson().toJson(para);
		System.out.println(stateJson);
		
                //这里要将你的授权回调地址处理一下，否则微信识别不了
		String redirect_uri = URLEncoder.encode("http://www.shenyatech.com/shenfu/sale/savePaymentInfo_getOpenid", "UTF-8");
                //简单获取openid的话参数response_type与scope与state参数固定写死即可
		StringBuffer url=new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize?redirect_uri="+redirect_uri+
				"&appid=" + WeiXinConfig.appid_weixin +"&response_type=code&scope=snsapi_base&state=" + stateJson +"#wechat_redirect");
		
		System.out.println(url);
		
		response.sendRedirect(url.toString());//这里请不要使用get请求单纯的将页面跳转到该url即可
		
	}
	
	/**
	 * 订单支付前置-获取openid
	 */
	@RequestMapping(value = "/savePaymentInfo_getOpenid")
	public String savePaymentInfo_getOpenid(Userpaylog form, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String state = Tools.getStr(request.getParameter("state"));
		
		//state = "{\"paymoney\":\"1\",\"usercode\":\"181030000002\"}";
		
		//state = new String(state.getBytes("ISO-8859-1"), "UTF-8");
		//System.out.println("*********************************************state=\n"+state);
		
		//System.out.println("------1111111111--------state="+state);
		
		String code = request.getParameter("code");
		//System.out.println("------1111111111111111--------code="+code);
		
		//回调函数带的参数
		JSONObject stateJSON = new JSONObject(state);
		
		String usercode = stateJSON.getString("usercode");
		form.setUsercode(usercode);
		//String payitem = stateJSON.getString("payitem");
		//form.setPayitem(payitem);
		Integer paymoney = stateJSON.getInt("paymoney");
		form.setPaymoney(paymoney);
		//String paytype = stateJSON.getString("paytype");
		//form.setPaytype(paytype);
		String jumpurl = stateJSON.getString("jumpurl");
		form.setJumpurl(jumpurl);
		//获取openid
		String openid = "";
		List<Object> list = WeixinGetOpenid.accessToken(code);
		if (list.size() != 0) {
			openid = list.get(0).toString();
		}
        
		request.setAttribute("code", openid);
		request.setAttribute("openid", openid);

		//System.out.println("------2222222222222222--------openid="+openid);
		
		return "shopping/savePaymentInfo";
	}
	
	/**
	 * 订单支付
	 */
	@ResponseBody
	@RequestMapping(value = "/savePaymentInfo")
	public Map<String, Object> savePaymentInfo(Userpaylog form, HttpServletRequest request) throws IOException{
		String usercode = form.getUsercode();
		//String payitem = form.getPayitem();
		String paytype = form.getPaytype();
		Integer paymoney = form.getPaymoney();
		String payitemname = "一元试用扫码活动";
		
		//String code = request.getParameter("code");
		String openid = request.getParameter("openid");
		
		//操作员
		//Employee employee = (Employee)getSession().getAttribute("MobileOperator");
		
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		//封装申请微信支付数据
		//申请交易编码ID
		String orderNumber = usercode+ "_" + paytype + "_" + Tools.getRandomPass(6,("0123456789ABCDEFGHJKLMNPQRSTUVWXYZ").toCharArray());
		//String attach = orderNumber;
		
		JsonObjectForPara para = new JsonObjectForPara();
		para.setUsercode(form.getUsercode());
		//para.setPayitem(form.getPayitem());
		para.setPayitemname("一元试用扫码活动");
		para.setPaymoney(String.valueOf(form.getPaymoney()));
		para.setPaytype(form.getPaytype());
		//para.setOperatorcode(employee.getEmployeecode());
		//para.setReceivercode(employee.getEmployeecode());
		
		String attact_Json = new Gson().toJson(para);
		
		//System.out.println("--------********attact_Json************-----attact_Json=\n" + attact_Json);
		
		SortedMap<String, Object> parameters = new TreeMap<String, Object>();
		parameters.put("appid", WeiXinConfig.appid_weixin);//应用APPID
		parameters.put("attach", attact_Json); // 附加数据,原样返回
		parameters.put("body", payitemname);
		parameters.put("mch_id", WeiXinConfig.mch_id_weixin);//商户号
		parameters.put("nonce_str", WeiXinPayCommonUtil.genNonceStr());//随机字符串
		//parameters.put("notify_url", WeiXinConfig.notify_url); // 回调接口地址
		parameters.put("notify_url", "http://www.shenyatech.com/shenfu/sale/savePaymentInfo_notify"); // 回调接口地址
		parameters.put("out_trade_no", orderNumber);// 订单号
		parameters.put("spbill_create_ip", "192.168.0.1");// 用户的IP
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String starttime=sdf.format(new Date());
		parameters.put("time_start", starttime); // 订单生成时间
		parameters.put("total_fee", paymoney*100 + "");// 金额(转成分为单位）
		//parameters.put("total_fee", 1 + "");// 金额(转成分为单位）
		if("1".equals(paytype)){
			parameters.put("trade_type", "JSAPI"); // 交易类型 APP ， JSAPI公众号	
			parameters.put("openid", openid);
		}else if ("3".equals(paytype)){
			parameters.put("trade_type", "NATIVE"); // 交易类型 APP ， NATIVE二维码
			parameters.put("product_id", orderNumber);
		}
		
		
		// 生成签名
		parameters.put("sign", WeiXinPayCommonUtil.createSign("UTF-8", parameters));
		// 生成xml请求
		String reXml = WeiXinPayCommonUtil.getRequestXml(parameters);
		System.out.println(reXml);
		// 请求xml
		String xml = null;
		try {
			xml = HttpRequest.sendPost(WeiXinConfig.gatewayUrl, reXml);
			xml = new String(xml.getBytes("ISO-8859-1"), "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//System.out.println(xml);

		// 解析xml
		Map<String, String> map = null;
		try {
			map = XMLUtil.doXMLParse(xml);
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(map);
		String return_code = map.get("return_code");
		try {
			if (return_code.equals("FAIL")) {
				result.put("return_code", map.get("return_code"));
				result.put("return_msg", map.get("return_msg"));
			} else if (return_code.equals("SUCCESS")) {
				String result_code= map.get("result_code");
				if(result_code.equals("SUCCESS")) {
					result.put("return_code", map.get("return_code"));
					result.put("return_msg", map.get("return_msg"));
					result.put("appid", map.get("appid"));
					result.put("mch_id", map.get("mch_id"));
					result.put("nonce_str", map.get("nonce_str"));
					result.put("result_code", map.get("result_code"));
					result.put("prepay_id", map.get("prepay_id"));
					result.put("trade_type", map.get("trade_type"));
					
					Long timestamp = new Date().getTime();
					
					result.put("timestamp", timestamp);
					result.put("code_url", map.get("code_url"));//二维码链接
					
					//支付时，重新签名
					SortedMap<String, Object> parameters_pay = new TreeMap<String, Object>();
					parameters_pay.put("appId", WeiXinConfig.appid_weixin);//应用APPID
					parameters_pay.put("timeStamp", String.valueOf(timestamp)); // 时间搓
					parameters_pay.put("nonceStr", map.get("nonce_str"));//随机字符串
					parameters_pay.put("package", "prepay_id=" + map.get("prepay_id"));
					parameters_pay.put("signType", "MD5");
					
					// 生成签名
					result.put("sign", WeiXinPayCommonUtil.createSign("UTF-8", parameters_pay));
					
				}else if(result_code.equals("FAIL")) {
					result.put("return_code", map.get("return_code"));
					result.put("return_msg", map.get("return_msg"));
					result.put("appid", map.get("appid"));
					result.put("mch_id", map.get("mch_id"));
					result.put("nonce_str", map.get("nonce_str"));
					result.put("sign", map.get("sign"));
					result.put("result_code", map.get("result_code"));
					result.put("err_code", map.get("err_code"));
					result.put("err_code_des", map.get("err_code_des"));
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(map);
		
		return result;
	}
	
	/**
	 * 订单支付成功，微信回调解接口函数
	 */
	@RequestMapping(value="/savePaymentInfo_notify")
	@ResponseBody
	public void savePaymentInfo_notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//System.out.println("--------------------进入支付成功回调函数-------------------");
		
		// 读取参数
		StringBuffer sb = new StringBuffer();
		InputStream inputStream = request.getInputStream();
		String s;
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		while ((s = in.readLine()) != null) {
			sb.append(s);
		}
		in.close();
		inputStream.close();

		// 解析xml成map
		Map<String, String> m = new HashMap<String, String>();
		m = XMLUtil.doXMLParse(sb.toString());

		// 过滤空 设置 TreeMap
		SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
		Iterator it = m.keySet().iterator();
		while (it.hasNext()) {
			String parameter = (String) it.next();
			String parameterValue = m.get(parameter);

			String v = "";
			if (null != parameterValue) {
				v = parameterValue.trim();
			}
			packageParams.put(parameter, v);
		}

		// 账号信息
		String key = WeiXinConfig.key_weixin; // key,商户密钥

		System.out.println(packageParams);
		// 判断签名是否正确
		if (WeiXinPayCommonUtil.isTenpaySign("UTF-8", packageParams, key)) {
			// ------------------------------
			// 处理业务开始
			// ------------------------------
			String resXml = "";
			if ("SUCCESS".equals((String) packageParams.get("result_code"))) {
				// 这里是支付成功
				////////// 执行自己的业务逻辑////////////////
				String mch_id = (String) packageParams.get("mch_id");//商户账号
				String openid = (String) packageParams.get("openid");//用户在商户appid下的唯一标识
				String is_subscribe = (String) packageParams.get("is_subscribe");//用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
				String out_trade_no = (String) packageParams.get("out_trade_no");//唯一订单号
				String trade_type = (String) packageParams.get("trade_type");//交易类型 APP ， NATIVE二维码
				String total_fee = (String) packageParams.get("total_fee");//支付金额
				String transaction_id= (String) packageParams.get("transaction_id");//微信支付订单号
				String attach= (String) packageParams.get("attach");//附加的数据，原样返回
				String time_end= (String) packageParams.get("time_end");//支付完成时间，原样返回
				//先保存微信交易记录			
				Wechatpay wechatpay=new Wechatpay();
				wechatpay.setMchid(mch_id);
				wechatpay.setOpenid(openid);
				wechatpay.setOuttradeno(out_trade_no);
				wechatpay.setTimeend(time_end);
				wechatpay.setTotalfee(total_fee);
				wechatpay.setTradetype(trade_type);
				wechatpay.setTransactionid(transaction_id);
				
				//先本地数据库保存
				String usercode = "";
				String payitem = "";
				//回调参数
				JSONObject json = new JSONObject();
				try {
					json = new JSONObject(attach);
					usercode = json.getString("usercode");
					wechatpay.setOrdercode(usercode);
					//wechatpay.setPayitem(json.getString("payitem"));
					//wechatpay.setOperatorcode(json.getString("receivercode"));
					wechatpay.setPaytype(json.getString("paytype"));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				wechatpayDao.save(wechatpay);
				
				
				//修改客户为已支付
				User user  = userDao.findByUsercodeStr(usercode);
				if(user != null){
					user.setDealresult("参加一元试用扫码活动申请,已经支付一元");
					userDao.update(user);
				}
				
				System.out.println("--------------------保存成功");
				
				//Map resultHp = httpForMpsService.savePaymentInfo(json);//向OA保存订单数据

				System.out.println("支付成功");
				// 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
				resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
						+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

			} else {
				System.out.println("支付失败,错误信息：" + packageParams.get("err_code"));
				resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
						+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
			}
			// ------------------------------
			// 处理业务完毕
			// ------------------------------
			BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
			out.write(resXml.getBytes());
			out.flush();
			out.close();
		} else {
			System.out.println("通知签名验证失败");
		}
	}
	
	/**
	 * 下载产品试用说明书
	 */
	@RequestMapping(value = "/downloadTemplate")
	public String downloadTemplate(HttpServletResponse response) {
		try {
			String import_template_path = servletContext.getInitParameter("import_template_path");
			String folderpath = servletContext.getRealPath(File.separator) + import_template_path + File.separatorChar + "成都申亚科技公司产品试用协议.pdf";
			File excelTemplate = new File(folderpath);
			response.reset();
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Length", "" + excelTemplate.length()); // XML文件大小
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("成都申亚科技公司产品试用协议.pdf", "UTF-8"));
			FileInputStream fis = new FileInputStream(excelTemplate);
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			byte[] buffer = new byte[1024 * 1024];
			while (fis.read(buffer) > 0) {
				toClient.write(buffer); // 输出数据
			}
			fis.close();
			toClient.flush();
			toClient.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
}
