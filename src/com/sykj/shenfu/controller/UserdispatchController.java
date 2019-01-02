package com.sykj.shenfu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.IEmployeeDao;
import com.sykj.shenfu.dao.IProductdepotDao;
import com.sykj.shenfu.dao.IProductinoutDao;
import com.sykj.shenfu.dao.ISaleDao;
import com.sykj.shenfu.dao.IUserDao;
import com.sykj.shenfu.dao.IUserbusinessDao;
import com.sykj.shenfu.dao.IUserdeliveryDao;
import com.sykj.shenfu.dao.IUserdispatchDao;
import com.sykj.shenfu.dao.IUserorderDao;
import com.sykj.shenfu.dao.IUserpaylogDao;
import com.sykj.shenfu.dao.IUserproductDao;
import com.sykj.shenfu.dao.IUsertaskDao;
import com.sykj.shenfu.po.Coderule;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Productdepot;
import com.sykj.shenfu.po.Userbusiness;
import com.sykj.shenfu.po.Userdelivery;
import com.sykj.shenfu.po.Userdispatch;
import com.sykj.shenfu.po.Userorder;
import com.sykj.shenfu.po.Userpaylog;
import com.sykj.shenfu.service.ICoderuleService;
import com.sykj.shenfu.service.IOperatorService;
import com.sykj.shenfu.service.IOperatorlogService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/userdispatch")
@Transactional
public class UserdispatchController extends BaseController {
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private IUserdispatchDao dispatchDao;
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
	private IProductdepotDao productdepotDao;
	@Autowired
	private IProductinoutDao productinputDao;
	@Autowired
	private IUserproductDao userproductDao;
	@Autowired
	private IUsertaskDao taskDao;
	@Autowired
	private ISaleDao saleDao;
	@Autowired
	private IUserdeliveryDao userdeliveryDao;
	@Autowired
	private IUserbusinessDao userbusinessDao;
	@Autowired
	private ICoderuleService coderuleService;
	@Autowired
	private IUserpaylogDao userpaylogDao;
   
	/*
     * 特殊的instance变量
     * 注：零长度的byte数组对象创建起来将比任何对象都经济――查看编译后的字节码：
     * 生成零长度的byte[]对象只需3条操作码，而Object lock = new Object()则需要7行操作码。
     */
    private static byte[] lock = new byte[0];   
	
	/**
	 * 查询派工单
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Userdispatch form) {
		return "dispatch/findDispatchList";
	}
	
	/**
	 * 工单派工查询
	 */
	@RequestMapping(value = "/findByListForAssign")
	public String findByListForAssign(Userdispatch form) {
		return "dispatch/findDispatchListForAssign";
	}
	
	/**
	 * 工单回单查询
	 */
	@RequestMapping(value = "/findByListForReply")
	public String findByListForReply(Userdispatch form) {
		return "dispatch/findDispatchListForReply";
	}
	
	/**
	 *工单审核查询
	 */
	@RequestMapping(value = "/findByListForCheck")
	public String findByListForCheck(Userdispatch form) {
		return "dispatch/findDispatchListForCheck";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Userdispatch form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		
		Integer total = dispatchDao.findByCount(form);
		List<Userdispatch> list = dispatchDao.findByList(form);
		for (Userdispatch dispatch : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			
			//工单信息
			objectMap.put("id", dispatch.getId());
			objectMap.put("usercode", dispatch.getUsercode());
			objectMap.put("username", dispatch.getUsername());
			objectMap.put("usersex", dispatch.getUsersex());
			objectMap.put("phone", dispatch.getPhone());
			objectMap.put("address", dispatch.getAddress());
			objectMap.put("source", dispatch.getSource());
			objectMap.put("dispatchcode", dispatch.getDispatchcode());
			objectMap.put("addtime", StringUtils.isEmpty(dispatch.getAddtime())?"":Tools.getStr(dispatch.getAddtime()).substring(0, 19));
			objectMap.put("installtime", dispatch.getInstalltime());
			objectMap.put("content", dispatch.getContent());
			objectMap.put("dealdate", dispatch.getDealdate());
			objectMap.put("dealresult", dispatch.getDealresult());
			objectMap.put("status", dispatch.getStatus());
			objectMap.put("statusname", dispatch.getStatusname());
			objectMap.put("checkstatus", dispatch.getCheckstatus());
			objectMap.put("checkstatusname", dispatch.getCheckstatusname());
			objectMap.put("checkresult", dispatch.getCheckresult());
			objectMap.put("remark", dispatch.getRemark());
			
			//订单信息
			Userorder userorder = userorderDao.findByOrdercode(dispatch.getOrdercode());
			if(userorder == null){
				userorder = new Userorder();
			}
			objectMap.put("ordercode", dispatch.getOrdercode());
			objectMap.put("orderid", userorder.getId());
			//安装人信息
			Employee installer = employeeDao.findByEmployeecodeStr(dispatch.getInstallercode());
			if(installer == null){
				installer = new Employee();
			}
			objectMap.put("installercode", dispatch.getInstallercode());
			objectMap.put("installername", installer.getEmployeename());
			objectMap.put("installerphone", installer.getPhone());
			objectMap.put("installertime", dispatch.getAddtime());
			 //操作员信息
			Operator operator = operatorService.findByEmployeecode(dispatch.getOperatorcode());
			if(operator == null){
				operator = new Operator();
			}
			objectMap.put("operatorcode", dispatch.getOperatorcode());
			objectMap.put("operatorname", operator.getEmployee().getEmployeename());
			
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}

	/**
	 * 添加派工单页面初始化
	 */
	@RequestMapping(value = "/addInit")
	public String addInit(Userdispatch form) {
		return "dispatch/addDispatch";
	}

	/**
	 * 保存添加 派工单
	 */
	@RequestMapping(value = "/save")
	public String save(Userdispatch form) {
		
		//加锁
		synchronized(lock) {
			
			Operator operator = (Operator)getSession().getAttribute("Operator");
		
			form.setOperatorcode(operator.getEmployeecode());
		
			//工单号
			String employeecode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_employeecode);
			form.setDispatchcode(employeecode);
			form.setAddtime(Tools.getCurrentTime());
			form.setStatus("1");//未派工
			form.setCheckstatus("0");//（0-未审核；1-已审核）
			dispatchDao.save(form);
			
			//修改订单的状态
			Userorder userorder = userorderDao.findByOrdercode(form.getOrdercode());
			userorder.setStatus("3");//已派工
			userorder.setOperatetime(Tools.getCurrentTime());
			userorderDao.updateStatus(userorder);
			
			//增加业务记录
			Userbusiness userbusiness = new Userbusiness();
			userbusiness.setOperatorcode(operator.getEmployeecode());
			userbusiness.setBusinesstypekey("orderassign");
			userbusiness.setBusinesstypename("订单派工");
			userbusiness.setUsercode(userorder.getUsercode());
			userbusiness.setOrdercode(userorder.getOrdercode());
			userbusiness.setDispatchcode(form.getDispatchcode());
			userbusiness.setAddtime(Tools.getCurrentTime());
			userbusiness.setContent("订单派工:订单号为： "+userorder.getOrdercode());
			userbusiness.setSource("1");//PC平台操作
			userbusinessDao.save(userbusiness);
			
			//增加操作日记
			String content = "添加派工单， 工单号:"+form.getDispatchcode();
			operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		} 
		
		form.setReturninfo("保存成功");
		
		return addInit(form);
	}

	/**
	 * 编辑 订单权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Userdispatch form) {
		Userdispatch dispatch = dispatchDao.findById(form.getId());
		//封装订户
		dispatch.setUser(userDao.findByUsercodeStr(dispatch.getUsercode()));
		//封装订单
		Userorder userorder = userorderDao.findByOrdercode(dispatch.getOrdercode());
		if(userorder == null){
			userorder = new Userorder();
		}
		//封装勘察人员
		Employee visitor = employeeDao.findByEmployeecodeStr(userorder.getVisitorcode());
		if(visitor == null){
			visitor = new Employee();
		}
		userorder.setVisitor(visitor);
		
		//封装快递信息
		Userdelivery deliveryform = new Userdelivery();
		deliveryform.setUsercode(userorder.getUsercode());
		deliveryform.setOrdercode(userorder.getOrdercode());
		List<Userdelivery> userdeliverylist = userdeliveryDao.queryByList(deliveryform);
		userorder.setUserdeliverylist(userdeliverylist);
		if(userdeliverylist!=null && userdeliverylist.size()>0){
			userorder.setUserdelivery(userdeliverylist.get(0));//默认取第一个
		}else{
			userorder.setUserdelivery(new Userdelivery());
		}
		
		dispatch.setUserorder(userorder);
		//封装安装人
		Employee installer = employeeDao.findByEmployeecodeStr(dispatch.getInstallercode());
		dispatch.setInstaller(installer);
		
		form.setUserdispatch(dispatch);
		
		return "dispatch/updateDispatch";
	}

	/**
	 * 保存编辑后 订单权限
	 */
	@RequestMapping(value = "/update")
	public String update(Userdispatch form) {
		//加锁
		synchronized(lock) {
			Operator operator = (Operator)getSession().getAttribute("Operator");
			
			Userdispatch userdispatch = dispatchDao.findById(form.getId());
			userdispatch.setInstallercode(form.getInstallercode());
			userdispatch.setContent(form.getContent());
			//修改工单
			dispatchDao.update(userdispatch);
			
			//增加操作日记
			String content = "修改工单， 工单号:"+form.getDispatchcode();
			operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		} 
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}

	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Userdispatch form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Userdispatch dispatch = dispatchDao.findById(form.getId());
		if(dispatch == null){
			responseJson.put("flag", "0");//删除成功
			responseJson.put("msg", "删除失败，此工单不存在");
			return responseJson;
		}
		
		if(!"0".equals(dispatch.getStatus()) && !"1".equals(dispatch.getStatus()) && !"2".equals(dispatch.getStatus())){
			responseJson.put("flag", "0");//删除成功
			responseJson.put("msg", "删除失败，此工单已经处理");
			return responseJson;
		}
		
		//删除派工单
		dispatchDao.delete(form.getId());
		
		//修改订单状态
		Userorder userorder = userorderDao.findByOrdercode(dispatch.getOrdercode());
		userorder.setStatus("2");//发货状态
		userorderDao.update(userorder);
		
		//增加业务记录
		Userbusiness userbusiness = new Userbusiness();
		userbusiness.setOperatorcode(operator.getEmployeecode());
		userbusiness.setBusinesstypekey("assigndel");
		userbusiness.setBusinesstypename("派工单删除");
		userbusiness.setUsercode(userorder.getUsercode());
		userbusiness.setOrdercode(userorder.getOrdercode());
		userbusiness.setDispatchcode(dispatch.getDispatchcode());
		userbusiness.setAddtime(Tools.getCurrentTime());
		userbusiness.setContent("删除派工单:工单号为： "+dispatch.getOrdercode());
		userbusiness.setSource("1");//PC平台操作
		userbusinessDao.save(userbusiness);
		
		//增加操作日记
		String content = "删除 派工单， 派工单号:"+dispatch.getDispatchcode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
	/**
	 * 查询当前订户的订购产品的授权结束时间
	 * @return
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value="/checkProductidentfy")
	public Map<String,Object> checkProductidentfy(Productdepot form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Productdepot productdepot = productdepotDao.findByProductidentfy(form.getProductidentfy());
		
		responseJson.put("flag", "0");//失败
		
		if(productdepot == null){
			responseJson.put("returninfo", "库存中不存在该SN码！");
		}else if(!"1".equals(productdepot.getProductstatus())){
			responseJson.put("returninfo", "此产品处于 "+productdepot.getProductstatusname()+"状态，不能打包！");	
		}else if(!(form.getProductcode().equals(productdepot.getProductcode()))){
			responseJson.put("returninfo", "此产品标识不是"+form.getProductname());
		}else if(!"1".equals(productdepot.getDepotstatus())){
			responseJson.put("returninfo", "此产品不是库存状态！");
		}else{
			responseJson.put("flag", "1");//成功
		}
		return responseJson;
	}
	
	/**
	 * 派单初始化
	 */
	@RequestMapping(value = "/assignInit")
	public String assignInit(Userdispatch form) {
		Userdispatch dispatch = dispatchDao.findById(form.getId());
		//封装订户
		dispatch.setUser(userDao.findByUsercodeStr(dispatch.getUsercode()));
		//封装订单
		Userorder userorder = userorderDao.findByOrdercode(dispatch.getOrdercode());
		if(userorder == null){
			userorder = new Userorder();
		}
		//封装勘察人员
		Employee visitor = employeeDao.findByEmployeecodeStr(userorder.getVisitorcode());
		if(visitor == null){
			visitor = new Employee();
		}
		userorder.setVisitor(visitor);
		dispatch.setUserorder(userorder);
		
		form.setUserdispatch(dispatch);
		return "dispatch/assignDispatch";
	}

	/**
	 * 派发工单
	 */
	@RequestMapping(value = "/saveAssign")
	public String saveAssign(Userdispatch form) {
		form.setStatus("1");// 设置工单状态为1(已派单)
		dispatchDao.saveAssign(form);
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "工单派工， 工单号:"+form.getDispatchcode() +";安装人："+form.getInstallername();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("派工成功");
		return assignInit(form);
	}
	
	/**
	 * 回单初始化
	 */
	@RequestMapping(value = "/replyInit")
	public String replyInit(Userdispatch form) {
		Userdispatch dispatch = dispatchDao.findById(form.getId());
		//封装订户
		dispatch.setUser(userDao.findByUsercodeStr(dispatch.getUsercode()));
		//封装订单
		Userorder userorder = userorderDao.findByOrdercode(dispatch.getOrdercode());
		if(userorder == null){
			userorder = new Userorder();
		}
		//封装勘察人员
		Employee visitor = employeeDao.findByEmployeecodeStr(userorder.getVisitorcode());
		if(visitor == null){
			visitor = new Employee();
		}
		userorder.setVisitor(visitor);
		dispatch.setUserorder(userorder);
		
		//封装安装人
		Employee installer = employeeDao.findByEmployeecodeStr(dispatch.getInstallercode());
		dispatch.setInstaller(installer);
		
		
		form.setUserdispatch(dispatch);
		return "dispatch/replyDispatch";
	}

	/**
	 * 工单处理
	 */
	@RequestMapping(value = "/saveReply")
	public String saveReply(Userdispatch form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String currenttime = Tools.getCurrentTime();
		Userdispatch dispatch = dispatchDao.findById(form.getId());
		
		dispatch.setStatus(form.getStatus());// 设置工单状态为3(处理成功)
		dispatch.setDealresult(form.getDealresult());
		dispatch.setDealdate(currenttime);
		dispatchDao.saveReply(dispatch);
		
		//修改订单状态
		Userorder order = userorderDao.findByOrdercode(dispatch.getOrdercode());

		if("3".equals(form.getStatus())){//安装完成，将订单状态修改为安装完成
			//order.setStatus("5");
			//安装成功，保存支付信息
//			Userpaylog userpaylog = new Userpaylog();
//			userpaylog.setUsercode(order.getUsercode());
//			userpaylog.setUsername(order.getUsername());
//			userpaylog.setPhone(order.getPhone());
//			userpaylog.setSource(order.getSource());
//			userpaylog.setAddress(order.getAddress());
//			userpaylog.setOrdercode(order.getOrdercode());
//			userpaylog.setPaytype(form.getPaytype());
//			userpaylog.setReceivercode(dispatch.getInstallercode());
//			userpaylog.setPaytime(currenttime);
//			userpaylog.setPayitem("2");//尾款
//			userpaylog.setPaymoney(Integer.valueOf(Integer.valueOf(order.getFinalpayment())));
//			userpaylog.setCheckstatus("0");
//			userpaylogDao.save(userpaylog);
//			//修改尾款已支付
//			order.setFinalarrivalflag("1");
//			userorderDao.updateFinalarrival(order);
			
		}else if("4".equals(form.getStatus())){//安装失败，将订单状态修改为安装失败
			//order.setStatus("10");
		}
		//order.setOperatetime(currenttime);
		//userorderDao.updateStatus(order);
		
		//查询关联的任务单号
		//Usertask task =  taskDao.findByTaskcodeStr(order.getTaskcode());
		//增加业务记录
		Userbusiness userbusiness = new Userbusiness();
		userbusiness.setOperatorcode(operator.getEmployeecode());
		userbusiness.setBusinesstypekey("dispatchreply");
		userbusiness.setBusinesstypename("工单处理");
		userbusiness.setUsercode(order.getUsercode());
		userbusiness.setOrdercode(order.getOrdercode());
		userbusiness.setDispatchcode(dispatch.getDispatchcode());
		userbusiness.setAddtime(currenttime);
		userbusiness.setContent("工单已处理:工单号为： "+dispatch.getDispatchcode() + "处理结果为: "+ form.getStatusname());
		userbusiness.setSource("1");//PC平台操作
		userbusinessDao.save(userbusiness);
		
		//增加操作日记
		String content = "工单处理， 工单号:"+form.getDispatchcode() +";安装人："+form.getInstallername();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("工单处理成功");
		return replyInit(form);
	}
	
	/**
	 * 审核初始化
	 */
	@RequestMapping(value = "/checkInit")
	public String checkInit(Userdispatch form) {
		Userdispatch dispatch = dispatchDao.findById(form.getId());
		//封装订户
		dispatch.setUser(userDao.findByUsercodeStr(dispatch.getUsercode()));
		//封装订单
		Userorder userorder = userorderDao.findByOrdercode(dispatch.getOrdercode());
		if(userorder == null){
			userorder = new Userorder();
		}
		//封装勘察人员
		Employee visitor = employeeDao.findByEmployeecodeStr(userorder.getVisitorcode());
		if(visitor == null){
			visitor = new Employee();
		}
		userorder.setVisitor(visitor);
		
		dispatch.setUserorder(userorder);
		
		//封装安装人信息
		Employee installer = employeeDao.findByEmployeecodeStr(dispatch.getInstallercode());
		dispatch.setInstaller(installer);
		
		form.setUserdispatch(dispatch);
		return "dispatch/checkDispatch";
	}

	/**
	 * 工单审核
	 */
	@RequestMapping(value = "/saveCheck")
	public String saveCheck(Userdispatch form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String currenttime = Tools.getCurrentTime();
		
		Userdispatch dispatch = dispatchDao.findById(form.getId());
		//修改工单未审核状态
		dispatch.setCheckresult(form.getCheckresult());
		dispatch.setCheckstatus("1");//已审核
		dispatchDao.saveCheck(dispatch);
		//修改订单状态
		Userorder order = userorderDao.findByOrdercode(form.getOrdercode());
		if("3".equals(dispatch.getStatus())){//安装完成，将订单状态修改为工单安装完成
			order.setInstallercode(dispatch.getInstallercode());//反写安装人员
			order.setStatus("5");
			order.setOperatetime(currenttime);
			userorderDao.updateCompleteState(order); //修改订单状态以及反写安装人员
		}else if("4".equals(dispatch.getStatus())){//安装失败，将订单状态修改为安装失败
			order.setStatus("10");
			order.setOperatetime(currenttime);
			userorderDao.updateStatus(order); //修改安装状态
		}
		
		//增加业务记录
		Userbusiness userbusiness = new Userbusiness();
		userbusiness.setOperatorcode(operator.getEmployeecode());
		userbusiness.setBusinesstypekey("dispatchcheck");
		userbusiness.setBusinesstypename("工单审核");
		userbusiness.setUsercode(order.getUsercode());
		userbusiness.setOrdercode(order.getOrdercode());
		userbusiness.setDispatchcode(dispatch.getDispatchcode());
		userbusiness.setAddtime(currenttime);
		userbusiness.setContent("工单已审核:工单号为： "+dispatch.getDispatchcode() + ";审核内容：" +dispatch.getCheckresult());
		userbusiness.setSource("1");//PC平台操作
		userbusinessDao.save(userbusiness);
		
		//增加操作日记
		String content = "工单审核， 工单号:"+form.getDispatchcode() +";审核内容："+dispatch.getCheckresult();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("审核成功");
		return checkInit(form);
	}
	
	
	/**
	 * 查询工单-工单接收
	 */
	@RequestMapping(value = "/findDispatchListForAccept")
	public String findUserdispatchListForAccept(Userdispatch form) {
		return "dispatch/findDispatchListForAccept";
	}
	
	/**
	 * 确认工单接收
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/updateForAccept")
	public Map<String,Object> updateForAccept(Userdispatch form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String currenttime = Tools.getCurrentTime();
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		//修改工单状态
		Userdispatch dispatch = dispatchDao.findById(form.getId());
		dispatch.setStatus("2");
		dispatchDao.saveAccept(dispatch);
		
		//修改订单状态
		Userorder order = userorderDao.findByOrdercode(dispatch.getOrdercode());
		order.setStatus("4");//派工接收
		order.setOperatetime(currenttime);
		userorderDao.updateStatus(order);
		
		//查询关联的任务单号
		//Usertask task =  taskDao.findByTaskcodeStr(order.getTaskcode());
		//增加业务记录
		Userbusiness userbusiness = new Userbusiness();
		userbusiness.setOperatorcode(operator.getEmployeecode());
		userbusiness.setBusinesstypekey("dispatchaccept");
		userbusiness.setBusinesstypename("工单接收");
		userbusiness.setUsercode(order.getUsercode());
		userbusiness.setOrdercode(order.getOrdercode());
		userbusiness.setDispatchcode(dispatch.getDispatchcode());
		userbusiness.setAddtime(currenttime);
		userbusiness.setContent("工单已接收:工单号为： "+dispatch.getDispatchcode());
		userbusiness.setSource("1");//PC平台操作
		userbusinessDao.save(userbusiness);
		
		//增加操作日记
		String content = "工单接收， 订单号为:"+order.getOrdercode()+";工单号为:"+dispatch.getDispatchcode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "工单接收成功");
		
		return responseJson;
	}
}
