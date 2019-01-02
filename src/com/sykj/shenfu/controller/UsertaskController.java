package com.sykj.shenfu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.IEmployeeDao;
import com.sykj.shenfu.dao.ISaleDao;
import com.sykj.shenfu.dao.IUserDao;
import com.sykj.shenfu.dao.IUserbusinessDao;
import com.sykj.shenfu.dao.IUserorderDao;
import com.sykj.shenfu.dao.IUsertaskDao;
import com.sykj.shenfu.po.Coderule;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.User;
import com.sykj.shenfu.po.Userbusiness;
import com.sykj.shenfu.po.Userorder;
import com.sykj.shenfu.po.Usertask;
import com.sykj.shenfu.service.ICoderuleService;
import com.sykj.shenfu.service.IOperatorService;
import com.sykj.shenfu.service.IOperatorlogService;
import com.sykj.shenfu.service.IUsertaskService;

import fr.opensagres.xdocreport.core.utils.StringUtils;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/task")
@Transactional
public class UsertaskController extends BaseController {

	@Autowired
	private IUsertaskDao taskDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private IOperatorService operatorService;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private IUserorderDao userorderDao;
	@Autowired
	private IUserbusinessDao userbusinessDao;
	@Autowired
	private ICoderuleService coderuleService;
	@Autowired
	private IUsertaskService usertaskService;
    
	/*
     * 特殊的instance变量
     * 注：零长度的byte数组对象创建起来将比任何对象都经济――查看编译后的字节码：
     * 生成零长度的byte[]对象只需3条操作码，而Object lock = new Object()则需要7行操作码。
     */
    private static byte[] lock = new byte[0];   
	
	/**
	 * 查询 任务单
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Usertask form) {
		return "task/findTaskList";
	}
	
	/**
	 * 查询任务单信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Usertask form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Integer total = taskDao.findByCount(form);
		List<Usertask> list = taskDao.findByList(form);
		for (Usertask task : list) {

			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			
			objectMap.put("id", task.getId());
			objectMap.put("taskcode", task.getTaskcode());
			objectMap.put("ordercode", task.getOrdercode());
			//潜在订户信息
			objectMap.put("usercode", task.getUsercode());
			objectMap.put("username", task.getUsername());
			objectMap.put("phone", task.getPhone());
			objectMap.put("usersex", task.getUsersex());
			objectMap.put("usersexname", task.getUsersexname());
			objectMap.put("address", task.getAddress());
			objectMap.put("source", task.getSource());
			objectMap.put("sourcename", task.getSourcename());
			objectMap.put("visittype", task.getVisittype());
			objectMap.put("visittypename", task.getVisittypename());
			
			//销售员信息
			objectMap.put("salercode", Tools.getStr(task.getSalercode()));
			if(StringUtils.isNotEmpty(task.getSalercode())){
				Employee saler = employeeDao.findByEmployeecodeStr(task.getSalercode());
				if(saler == null){
					saler = new Employee();
				}
				objectMap.put("salername", saler.getEmployeename());
				objectMap.put("salerphone", saler.getPhone());
			}
			
			
			//讲解员
			objectMap.put("talkercode", task.getTalkercode());
			if(StringUtils.isNotEmpty(task.getTalkercode())){
				Employee talker = employeeDao.findByEmployeecodeStr(task.getTalkercode());
				if(talker == null){
					 talker = new Employee();
				}
				objectMap.put("talkername", talker.getEmployeename());
				objectMap.put("talkerphone", talker.getPhone());
			}
			
			//上门人员信息
			objectMap.put("visitorcode",task.getVisitorcode());
			if(StringUtils.isNotEmpty(task.getVisitorcode())){
				Employee visitor = employeeDao.findByEmployeecodeStr(task.getVisitorcode());
				if(visitor == null){
					visitor = new Employee();
				}
				objectMap.put("visitorname",visitor.getEmployeename());
				objectMap.put("visitorphone", visitor.getPhone());
			}
			
			//操作员信息
			objectMap.put("operatorcode", task.getOperatorcode());
			if(StringUtils.isNotEmpty(task.getOperatorcode())){
				Operator operator = operatorService.findByEmployeecode(task.getOperatorcode());
				if(operator == null){
					operator = new Operator();
				}
				objectMap.put("operatorname", operator.getEmployee().getEmployeename());
			}
			
			objectMap.put("visitstate", task.getVisitstate());
			objectMap.put("visitstatename", task.getVisitstatename());
			objectMap.put("visittime", StringUtils.isEmpty(task.getVisittime())?"":Tools.getStr(task.getVisittime()).substring(0, 19));
			objectMap.put("intention", task.getIntention());
			objectMap.put("status", task.getStatus());
			objectMap.put("statusname", task.getStatusname());
			objectMap.put("addtime", StringUtils.isEmpty(task.getAddtime())?"":Tools.getStr(task.getAddtime()).substring(0, 19));
			objectMap.put("dealdate", StringUtils.isEmpty(task.getDealdate())?"":Tools.getStr(task.getDealdate()).substring(0, 19));
			objectMap.put("dealresult", task.getDealresult());
			objectMap.put("tasktype", Tools.getStr(task.getTasktype()));
			objectMap.put("tasktypename", Tools.getStr(task.getTasktypename()));
			objectMap.put("remark", task.getRemark());
			
			objectlist.add(objectMap);
			
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}

	/**
	 * 添加 任务单页面初始化
	 */
	@RequestMapping(value = "/addInit")
	public String addInit(Usertask form) {
		if("batchAdd".equals(form.getJumpurl())){//公司派人上门讲解
			return "task/addTaskForBatch";
		}else if ("autoBatchAdd".equals(form.getJumpurl())){//销售员亲自上门讲解
			return "task/addTaskForAutoBatch";
		}else if ("batchAddToInstaller".equals(form.getJumpurl())){//已讲解，公司派人只需测量
			return "task/addTaskForBatchToInstaller";
		}else {//单个添加
			return "task/addTask";
		}
		
	}

	/**
	 * 保存添加 任务单
	 */
	@RequestMapping(value = "/save")
	public String save(Usertask form) {
		//加锁
		synchronized(lock) {
			Operator operator = (Operator)getSession().getAttribute("Operator");
			
			String jumpurl = form.getJumpurl();
			
			String usercodes = getRequest().getParameter("usercodes");
			if(StringUtils.isNotEmpty(usercodes)){
				String[] usercodeArr = usercodes.split(",");
				for (String usercode : usercodeArr) {
					//修改潜在订户的状态
					User user = userDao.findByUsercodeStr(usercode);
					user.setStatus("1");
					userDao.update(user);
					
					//最大任务单号
					String taskcode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_taskcode);
					form.setTaskcode("RW"+taskcode);
					//订户信息
					form.setUsercode(user.getUsercode());
					form.setUsername(user.getUsername());
					form.setUsersex(user.getUsersex());
					form.setPhone(user.getPhone());
					form.setAddress(user.getAddress());
					form.setSource(user.getSource());
					form.setVisittype(user.getVisittype());
					form.setSalercode(user.getSalercode());
					//自动添加,上门人员为销售人员
					if("autoBatchAdd".equals(jumpurl)){
						form.setVisitorcode(user.getSalercode());
					}
					form.setOperatorcode(operator.getEmployeecode());
					form.setVisitstate("0");//访问状态（默认为0，若拒绝安装则改为1）
					//默认状态为未处理0
					form.setStatus("0");
					form.setAddtime(Tools.getCurrentTime());
					//保存任务单
					taskDao.save(form);
					
					//增加业务记录
					Userbusiness userbusiness = new Userbusiness();
					userbusiness.setOperatorcode(operator.getEmployeecode());
					userbusiness.setBusinesstypekey("taskadd");
					userbusiness.setBusinesstypename("任务单生成");
					userbusiness.setUsercode(user.getUsercode());
					userbusiness.setTaskcode(form.getTaskcode());
					userbusiness.setOrdercode(null);
					userbusiness.setDispatchcode(null);
					userbusiness.setAddtime(Tools.getCurrentTime());
					userbusiness.setContent("生成任务单;任务单号为： "+form.getTaskcode());
					userbusiness.setSource("1");//PC平台操作
					userbusinessDao.save(userbusiness);
					
					//增加操作日记
					String content = "添加 任务单， 任务单号:"+form.getTaskcode();
					operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
				}
			}else{
				form.setReturninfo("请选择订户");
				return addInit(form);
			}
		} 
		
		form.setReturninfo("保存成功");
		
		return addInit(form);
	}
	
	/**
	 * 公司派人上门讲解和测量的订单派工
	 */
	@RequestMapping(value = "/saveTaskForBatchAdd")
	public String saveTaskForBatchAdd(Usertask form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		Map<String, Object> responseMap = usertaskService.saveTaskForBatchAdd(form, getRequest(), operator);
		form.setReturninfo(String.valueOf(responseMap.get("result")));
		return addInit(form);
	}
	
	/**
	 * 已讲解，公司派人上门测量的订单派工
	 */
	@RequestMapping(value = "/saveTaskForBatchToInstaller")
	public String saveTaskForBatchToInstaller(Usertask form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		Map<String, Object> responseMap = usertaskService.saveTaskForBatchToInstaller(form, getRequest(), operator);
		form.setReturninfo(String.valueOf(responseMap.get("result")));
		return addInit(form);
	}

	/**
	 * 编辑 任务单权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Usertask form) {
		Usertask task = taskDao.findById(form.getId());
		//task.setUser(userDao.findByUsercodeStr(task.getUsercode()));
		//上门人员
		Employee visitor = employeeDao.findByEmployeecodeStr(task.getVisitorcode());
		if(visitor == null ){
			visitor = new Employee();
		}
		task.setVisitor(visitor);
		//销售人员
		Employee saler = employeeDao.findByEmployeecodeStr(task.getSalercode());
		if(saler == null ){
			saler = new Employee();
		}
			task.setSaler(saler);
		//讲解人员
		Employee talker = employeeDao.findByEmployeecodeStr(task.getTalkercode());
		if(talker == null ){
			talker = new Employee();
		}
		task.setTalker(talker);
		
		form.setUsertask(task);
		return "task/updateTask";
	}

	/**
	 * 保存编辑后 任务单权限
	 */
	@RequestMapping(value = "/update")
	public String update(Usertask form) {
		
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		//修改潜在订户的状态
		Usertask usertask = taskDao.findById(form.getId());
		
		if(usertask == null){
			form.setReturninfo("修改失败，此任务单不存在");
			return updateInit(form);
		}
		
		//任务单已经处理，不能提交
		if("1".equals(usertask)){
			form.setReturninfo("修改失败，任务单已经处理，不能修改");
			return updateInit(form);
		}
		
		if(StringUtils.isEmpty(form.getUsername())){
			form.setReturninfo("修改失败，客户姓名不能为空");
			return updateInit(form);
		}
		if(StringUtils.isEmpty(form.getAddress())){
			form.setReturninfo("修改失败，客户安装地址不能为空");
			return updateInit(form);
		}
		
		usertask.setUsername(form.getUsername());
		usertask.setAddress(form.getAddress());
		if("0".equals(usertask.getTasktype())){//讲解单
			if(StringUtils.isEmpty(form.getTalkercode())){
				form.setReturninfo("修改失败，请选择上门人员");
				return updateInit(form);
			}
			usertask.setTalkercode(form.getTalkercode());
			usertask.setVisittime(StringUtils.isEmpty(form.getVisittime())?null:form.getVisittime());
		}else{//测量单
			if(StringUtils.isEmpty(form.getVisitorcode())){
				form.setReturninfo("修改失败，请选择上门人员");
				return updateInit(form);
			}
			usertask.setVisitorcode(form.getVisitorcode());
			usertask.setVisittime(StringUtils.isEmpty(form.getVisittime())?null:form.getVisittime());
		}
		
		taskDao.update(usertask);
		
		//增加操作日记
		String content = "修改 任务单信息， 任务单编号:"+form.getTaskcode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}

	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Usertask form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Usertask task = taskDao.findById(form.getId());
		
		if("1".equals(task.getStatus())){
			responseJson.put("flag", "0");//删除失败
			responseJson.put("msg", "此任务单为已处理状态，不能删除");
		}
		
		if(StringUtils.isNotEmpty(task.getOrdercode())){ //有订单号，表示此任务单从订单过来，需要修改订单信息为未派任务单
			Userorder userorder = userorderDao.findByOrdercode(task.getOrdercode());
			userorder.setVisitorstatus("0");
			userorder.setVisitorcode("");
			userorderDao.update(userorder);//修改订单
		}else{//此任务单还没有生成订单，修改客户为未处理
			User user = userDao.findByUsercodeStr(task.getUsercode());
			if("0".equals(user.getVisittype())){//公司派人上门讲解和测量，这种情况，只需要修改订户为未处理
				user.setStatus("0");
				userDao.update(user);
			}
		}
		
		//删除 任务单
		taskDao.delete(form.getId());
		
		//增加业务记录
		Userbusiness userbusiness = new Userbusiness();
		userbusiness.setOperatorcode(operator.getEmployeecode());
		userbusiness.setBusinesstypekey("taskdel");
		userbusiness.setBusinesstypename("任务单删除");
		userbusiness.setUsercode(task.getUsercode());
		userbusiness.setTaskcode(task.getTaskcode());
		userbusiness.setOrdercode(null);
		userbusiness.setDispatchcode(null);
		userbusiness.setAddtime(Tools.getCurrentTime());
		userbusiness.setContent("删除任务单;任务单号为： "+task.getTaskcode());
		userbusiness.setSource("1");//PC平台操作
		userbusinessDao.save(userbusiness);
		
		//增加操作日记
		String content = "删除 任务单， 任务单编号:"+task.getTaskcode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
	/**
	 * 查询任务单信息
	 */
	@RequestMapping(value = "/findUsertaskListDialog")
	public String findUsertaskListDialog(Usertask form) {
		return "task/findUsertaskListDialog";
	}
	
	/**
	 * 查询任务单-勘察菜单
	 */
	@RequestMapping(value = "/findUsertaskListForResearch")
	public String findUsertaskListForResearch(Usertask form) {
		return "task/findUsertaskListForResearch";
	}
	
	/**
	 * 查询任务单-任务单接收
	 */
	@RequestMapping(value = "/findUsertaskListForAccept")
	public String findUsertaskListForAccept(Usertask form) {
		return "task/findTaskListForAccept";
	}
	
	/**
	 * 确认任务单接收
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/updateForAccept")
	public Map<String,Object> updateForAccept(Usertask form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		//修改任务单状态
		Usertask task = taskDao.findById(form.getId());
		task.setStatus("1");
		taskDao.update(task);
		
		//增加业务记录
		Userbusiness userbusiness = new Userbusiness();
		userbusiness.setOperatorcode(operator.getEmployeecode());
		userbusiness.setBusinesstypekey("taskaccept");
		userbusiness.setBusinesstypename("任务单接收");
		userbusiness.setUsercode(task.getUsercode());
		userbusiness.setTaskcode(task.getTaskcode());
		userbusiness.setOrdercode(null);
		userbusiness.setDispatchcode(null);
		userbusiness.setAddtime(Tools.getCurrentTime());
		userbusiness.setContent("任务单已接收,任务单号为：" +task.getTaskcode());
		userbusiness.setSource("1");//PC平台操作
		userbusinessDao.save(userbusiness);
		
		//增加操作日记
		String content = "任务单接收， 任务单号为:"+task.getTaskcode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "任务单接收成功");
		
		return responseJson;
	}
	
	/**
	 * 查询任务单-手绘图管理
	 */
	@RequestMapping(value = "/findUsertaskListForHanddrawing")
	public String findUsertaskListForHanddrawing(Usertask form) {
		return "task/findUsertaskListForHanddrawing";
	}
	
	
	/**
	 * 确认完成手绘图上传
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/updateForHanddrawing")
	public Map<String,Object> updateForHanddrawing(Usertask form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		//修改任务单状态
		Usertask task = taskDao.findById(form.getId());
		task.setStatus("2");
		taskDao.update(task);
		
		//增加业务记录
		Userbusiness userbusiness = new Userbusiness();
		userbusiness.setOperatorcode(operator.getEmployeecode());
		userbusiness.setBusinesstypekey("handdrawing");
		userbusiness.setBusinesstypename("手绘图已完成");
		userbusiness.setUsercode(task.getUsercode());
		userbusiness.setTaskcode(task.getTaskcode());
		userbusiness.setOrdercode(null);
		userbusiness.setDispatchcode(null);
		userbusiness.setAddtime(Tools.getCurrentTime());
		userbusiness.setContent("任务单已完成上传手绘图,任务单号为：" +task.getTaskcode());
		userbusiness.setSource("1");//PC平台操作
		userbusinessDao.save(userbusiness);
		
		//增加操作日记
		String content = "完成上传手绘图， 任务单号为:"+task.getTaskcode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "完成手绘图操作成功");
		
		return responseJson;
	}
	
	/**
	 * 查询任务单-施工图管理
	 */
	@RequestMapping(value = "/findUsertaskListForBuilddrawing")
	public String findUsertaskListForBuilddrawing(Usertask form) {
		return "task/findUsertaskListForBuilddrawing";
	}
	
	
	/**
	 * 确认完成施工图上传
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/updateForBuilddrawing")
	public Map<String,Object> updateForBuilddrawing(Usertask form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		//修改任务单状态
		Usertask task = taskDao.findById(form.getId());
		task.setStatus("3");
		taskDao.update(task);
		
		//增加业务记录
		Userbusiness userbusiness = new Userbusiness();
		userbusiness.setOperatorcode(operator.getEmployeecode());
		userbusiness.setBusinesstypekey("builddrawing");
		userbusiness.setBusinesstypename("施工图已完成");
		userbusiness.setUsercode(task.getUsercode());
		userbusiness.setTaskcode(task.getTaskcode());
		userbusiness.setOrdercode(null);
		userbusiness.setDispatchcode(null);
		userbusiness.setAddtime(Tools.getCurrentTime());
		userbusiness.setContent("施工图已经完成,任务单号为：" +task.getTaskcode());
		userbusiness.setSource("1");//PC平台操作
		userbusinessDao.save(userbusiness);
		
		//增加操作日记
		String content = "施工图已经完成， 任务单号为:"+task.getTaskcode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "完成施工图操作成功");
		
		return responseJson;
	}
}
