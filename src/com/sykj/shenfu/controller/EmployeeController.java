package com.sykj.shenfu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sykj.shenfu.common.ConfigUtil;
import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.IEmployeeDao;
import com.sykj.shenfu.dao.IOperatorDao;
import com.sykj.shenfu.dao.ISalelevelparaDao;
import com.sykj.shenfu.po.Coderule;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Salelevelpara;
import com.sykj.shenfu.service.ICoderuleService;
import com.sykj.shenfu.service.IOperatorlogService;


/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/employee")
@Transactional
public class EmployeeController extends BaseController {

	private static final String LOGIN_COOKIE_STARTNAME = "tfc_login_cookie_";// 用户首页cookie前缀名

	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private IOperatorDao operatorDao;
	@Autowired
	private ISalelevelparaDao salelevelparaDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private ICoderuleService coderuleService;
	
	/**
	 * 查询用户信息
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Employee form) {
		return "employee/findEmployeeList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Employee form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Integer total = employeeDao.findByCount(form);
		List<Employee> list = employeeDao.findByList(form);
		for (Employee employee : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("id", employee.getId());
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
			objectMap.put("addtime", StringUtils.isEmpty(employee.getAddtime())?"":Tools.getStr(employee.getAddtime()).substring(0, 19));
			objectMap.put("department", employee.getDepartment());
			objectMap.put("departmentname", employee.getDepartmentname());
			objectMap.put("salescore", employee.getSalescore());
			objectMap.put("employeetype", employee.getEmployeetype());
			objectMap.put("employeetypename", employee.getEmployeetypename());
			objectMap.put("bankcardname", employee.getBankcardname());
			objectMap.put("bankcardno", employee.getBankcardno());
			objectMap.put("employeecode", employee.getEmployeecode());
			objectMap.put("parentemployeecode", employee.getParentemployeecode());
			if(StringUtils.isNotEmpty(employee.getParentemployeecode())){
				Employee parentSaler = employeeDao.findByEmployeecodeStr(employee.getParentemployeecode());
				if(parentSaler != null){
					objectMap.put("parentworkname", parentSaler.getEmployeename());
				}
			}
			objectMap.put("rechargevipcode", employee.getRechargevipcode());
			objectMap.put("extendcode", Tools.getStr(employee.getExtendcode()));
			objectMap.put("managercode", Tools.getStr(employee.getManagercode()));
			if(StringUtils.isNotEmpty(employee.getManagercode())){
				Employee manager = employeeDao.findByEmployeecodeStr(employee.getManagercode());
				if(manager != null){
					objectMap.put("managername", manager.getEmployeename());
				}
			}
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
	public String addInit(Employee form) {
		if("forSaler".equals(form.getJumpurl())){//从销售员修改界面来的
        	return "sale/addEmployee";
        }else{
        	return "employee/addEmployee";
        }
	}
	
	/**
	 * 添加销售员初始化
	 * 
	 * @return
	 */
	@RequestMapping(value = "/addSalerInit")
	public String addSalerInit(Employee form) {
		return "sale/addEmployee";
	}

	/**
	 * 新增
	 */
	@RequestMapping(value = "/save")
	public String save(Employee form) {
		form.setStatus("1");// 在职
		form.setAddtime(Tools.getCurrentTime());// 取当前时间
		
		if ("".equals(form.getEmployeename())) {
			form.setReturninfo("员工姓名不能为空");
			return addInit(form);
		} 
		
		if ("".equals(Tools.getStr(form.getPhone()))) {
			form.setReturninfo("电话号码不能为空");
			return addInit(form);
		}else{
			//判断电话号码重复
			Employee oldEmployee = employeeDao.findByPhone(form);
			if (oldEmployee != null) {
				form.setReturninfo(getMessage("电话号码已经存在"));
				return addInit(form);
			}
		}
		
		//工号
		String employeecode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_employeecode);
		form.setEmployeecode(employeecode);
		
		String extendcode = "";
		Boolean flag = true;
		while(flag){
			extendcode = Tools.getRandomPass(6,("0123456789ABCDEFGHJKLMNPQRSTUVWXYZ").toCharArray());
			Employee employee = employeeDao.findByExtendcode(extendcode);
			if(employee == null){//数据库里没有此推广码，采用此推广码码，跳出循环
				form.setExtendcode(extendcode);
				flag = false;
			}
		}
		
		//设置登录APP的密码
		form.setApppassword(ConfigUtil.getConfigFilePath("app_password","system.properties"));
		employeeDao.save(form);
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "增加员工，员工姓名为:"+form.getEmployeename();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("添加成功");

		return addInit(form);
		
	}

	
	/**
	 * 更新初始化
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Employee form) {
        
		Employee employee = employeeDao.findById(form.getId());
		if(StringUtils.isNotEmpty(employee.getParentemployeecode())){
			Employee parentemployee =  employeeDao.findByEmployeecodeStr(employee.getParentemployeecode());
			employee.setParentemployee(parentemployee);
		}
		
		if(StringUtils.isNotEmpty(employee.getManagercode())){
			Employee manager =  employeeDao.findByEmployeecodeStr(employee.getManagercode());
			employee.setManager(manager);
		}
		
		form.setEmployee(employee);
         
		if("forSaler".equals(form.getJumpurl())){//从销售员修改界面来的
        	return "sale/updateEmployee";
        }else{
        	return "employee/updateEmployee";
        }
		
	}
	
	/**
	 * 更新
	 */
	@RequestMapping(value = "/update")
	public String update(Employee form) {
        
		if ("".equals(form.getEmployeename())) {
			form.setReturninfo("员工姓名不能为空");
			return addInit(form);
		} 
		
		if ("".equals(Tools.getStr(form.getPhone()))) {
			form.setReturninfo("电话号码不能为空");
			return updateInit(form);
		} else {
			//判断电话号码是否重复
			Employee oldEmployee = employeeDao.findByPhone(form);
			if (oldEmployee != null && !oldEmployee.getId().equals(form.getId())) {
				form.setReturninfo(getMessage("电话号码已经存在"));
				return updateInit(form);
			}
		}

		form.setApppassword(ConfigUtil.getConfigFilePath("app_password","system.properties"));
		
		//如果推广码为空
		if(StringUtils.isEmpty(form.getExtendcode())){
			String extendcode = "";
			Boolean flag = true;
			while(flag){
				extendcode = Tools.getRandomPass(6,("0123456789ABCDEFGHJKLMNPQRSTUVWXYZ").toCharArray());
				Employee employee = employeeDao.findByExtendcode(extendcode);
				if(employee == null){//数据库里没有此推广码，采用此推广码码，跳出循环
					form.setExtendcode(extendcode);
					flag = false;
				}
			}
		}
		
		// 修改网络信息
		employeeDao.update(form);
        
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "修改员工，员工姓名为:"+form.getEmployeename();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("修改成功");
		
		return updateInit(form);
		
		
	}

    
	/**
	 * 删除
	 * @return
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value="/delete")
	public Map<String,Object> delete(Employee form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		//删除员工，实际上就是修改state为0-无效
		Employee employeedmp = employeeDao.findById(form.getId());
		employeedmp.setStatus("0");//离职
		employeeDao.update(employeedmp);
		
		//删除操作员权限
		Operator employeeoperator = operatorDao.findByEmployeecode(employeedmp.getEmployeecode());
		if(employeeoperator != null){
			employeeoperator.setStatus("0");//失效
			operatorDao.update(employeeoperator);
		}
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "删除员工，员工姓名为:"+employeedmp.getEmployeename();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		return responseJson;
	}
	
	
	/**
	 * 查询用户信息
	 */
	@RequestMapping(value = "/findEmployeeListDialog")
	public String findEmployeeListDialog(Employee form) {
		return "employee/findEmployeeListDialog";
	}
	
	/**
	 * 查询销售员信息
	 */
	@RequestMapping(value = "/findSalerListDialog")
	public String findSalerListDialog(Employee form) {
		return "employee/findSalerListDialog";
	}
	
	/**
	 * 查询销售员信息
	 */
	@RequestMapping(value = "/findInstallerListDialog")
	public String findInstallerListDialog(Employee form) {
		return "employee/findInstallerListDialog";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findSalelevelListJson")
	public Map<String, Object> findSalelevelListJson(Employee form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Integer total = employeeDao.findByCount(form);
		List<Employee> list = employeeDao.findByList(form);
		for (Employee employee : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			
			objectMap.put("id", employee.getId());
			objectMap.put("employeename", employee.getEmployeename());
			objectMap.put("employeesex", employee.getEmployeesex());
			objectMap.put("birthday", employee.getBirthday());
			objectMap.put("identification", employee.getIdentification());
			objectMap.put("email", employee.getEmail());
			objectMap.put("address", employee.getAddress());
			objectMap.put("phone", employee.getPhone());
			objectMap.put("level", employee.getLevel());
			objectMap.put("status", employee.getStatus());
			objectMap.put("addtime", employee.getAddtime());
			objectMap.put("department", employee.getDepartment());
			objectMap.put("salescore", employee.getSalescore());
			
			//查询对应等级
			Salelevelpara salelevelpara = salelevelparaDao.findBySalescore(employee.getSalescore());
			objectMap.put("level", salelevelpara.getLevel());
			objectMap.put("minscore", salelevelpara.getMinscore());
			objectMap.put("maxscore", salelevelpara.getMaxscore());
			
			objectlist.add(objectMap);
		}
		result.put("total", total);
		result.put("rows", objectlist);
		return result;
	}
}
