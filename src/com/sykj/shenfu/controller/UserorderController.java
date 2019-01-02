package com.sykj.shenfu.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.IEmployeeDao;
import com.sykj.shenfu.dao.IProductDao;
import com.sykj.shenfu.dao.IProductdepotDao;
import com.sykj.shenfu.dao.IProductinoutDao;
import com.sykj.shenfu.dao.IProductmodelDao;
import com.sykj.shenfu.dao.IProductmodelrefDao;
import com.sykj.shenfu.dao.IRechargevipDao;
import com.sykj.shenfu.dao.ISaleenergyruleDao;
import com.sykj.shenfu.dao.ISalegaininfoDao;
import com.sykj.shenfu.dao.ISalegainruleDao;
import com.sykj.shenfu.dao.ISalerrechargevipinfoDao;
import com.sykj.shenfu.dao.ITimeparaDao;
import com.sykj.shenfu.dao.IUserbusinessDao;
import com.sykj.shenfu.dao.IUsercontractDao;
import com.sykj.shenfu.dao.IUserdeliveryDao;
import com.sykj.shenfu.dao.IUserdispatchDao;
import com.sykj.shenfu.dao.IUserdoorDao;
import com.sykj.shenfu.dao.IUserorderDao;
import com.sykj.shenfu.dao.IUserDao;
import com.sykj.shenfu.dao.IUserorderinfoDao;
import com.sykj.shenfu.dao.IUserpaylogDao;
import com.sykj.shenfu.dao.IUserproductDao;
import com.sykj.shenfu.dao.IUsertaskDao;
import com.sykj.shenfu.po.Coderule;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Product;
import com.sykj.shenfu.po.Productdepot;
import com.sykj.shenfu.po.Productinout;
import com.sykj.shenfu.po.Productmodel;
import com.sykj.shenfu.po.Productmodelref;
import com.sykj.shenfu.po.Rechargevip;
import com.sykj.shenfu.po.Saleenergyrule;
import com.sykj.shenfu.po.Salegaininfo;
import com.sykj.shenfu.po.Salegainrule;
import com.sykj.shenfu.po.Salerrechargevipinfo;
import com.sykj.shenfu.po.Timepara;
import com.sykj.shenfu.po.Userbusiness;
import com.sykj.shenfu.po.Userdelivery;
import com.sykj.shenfu.po.Userdispatch;
import com.sykj.shenfu.po.Userorder;
import com.sykj.shenfu.po.User;
import com.sykj.shenfu.po.Userorderinfo;
import com.sykj.shenfu.po.Userpaylog;
import com.sykj.shenfu.po.Userproduct;
import com.sykj.shenfu.po.Usertask;
import com.sykj.shenfu.service.ICoderuleService;
import com.sykj.shenfu.service.IOperatorlogService;
import com.sykj.shenfu.service.IUserorderService;
import com.sykj.shenfu.service.IUsertaskService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/userorder")
@Transactional
public class UserorderController extends BaseController {
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private IUserorderDao userorderDao;
	@Autowired
	private IUserorderinfoDao userorderinfoDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private IProductdepotDao productdepotDao;
	@Autowired
	private IProductDao productDao;
	@Autowired
	private IProductmodelDao productmodelDao;
	@Autowired
	private IUserproductDao userproductDao;
	@Autowired
	private IUsertaskDao taskDao;
	@Autowired
	private IUsercontractDao usercontractDao;
	@Autowired
	private IUserdeliveryDao userdeliveryDao;
	@Autowired
	private IProductinoutDao productinoutDao;
	@Autowired
	private IUserdispatchDao dispatchDao;
	@Autowired
	private ITimeparaDao timeparaDao;
	@Autowired
	private IUserbusinessDao userbusinessDao;
	@Autowired
	private ICoderuleService coderuleService;
	@Autowired
	private IProductmodelrefDao productmodelrefDao;
	@Autowired
	private ISalegainruleDao salegainruleDao;
	@Autowired
	private ISalegaininfoDao salegaininfoDao;
	@Autowired
	private IRechargevipDao rechargevipDao;
	@Autowired
	private ISalerrechargevipinfoDao salerrechargevipinfoDao;
	@Autowired
	private ISaleenergyruleDao saleenergyruleDao;
	@Autowired
	private IUserpaylogDao userpaylogDao;
	@Autowired
	private IUserorderService userorderService;
	@Autowired
	private IUserdoorDao userdoorDao;
	@Autowired
	private IUserdispatchDao userdispatchDao;
	
	/*
     * 特殊的instance变量
     * 注：零长度的byte数组对象创建起来将比任何对象都经济――查看编译后的字节码：
     * 生成零长度的byte[]对象只需3条操作码，而Object lock = new Object()则需要7行操作码。
     */
    private static byte[] lock = new byte[0];   
	
	/**
	 * 查询 订单
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Userorder form) {
		return "userorder/findUserorderList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Userorder form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		//获取时效性等级信息
		Timepara timepara = new Timepara();
		List<Timepara> timeparalist = timeparaDao.queryByList(timepara);
		Integer time1 = timeparalist.get(0).getTime();
		Integer time2 = timeparalist.get(1).getTime();
		Integer time3 = timeparalist.get(2).getTime();
		if("0".equals(form.getTimelevel())){
			form.setMinopteratetime(0);
			form.setMaxopteratetime(time1);
		}else if("1".equals(form.getTimelevel())){
			form.setMinopteratetime(time1);
			form.setMaxopteratetime(time2);
		}else if("2".equals(form.getTimelevel())){
			form.setMinopteratetime(time2);
			form.setMaxopteratetime(time3);
		}else if("3".equals(form.getTimelevel())){
			form.setMinopteratetime(time3);
		}
		
		Integer total = userorderDao.findByCount(form);
		List<Userorder> list = userorderDao.findByList(form);
		for (Userorder order : list) {
			
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			
			objectMap.put("id", order.getId());
			objectMap.put("ordercode", order.getOrdercode());
			objectMap.put("productfee", order.getProductfee());
			objectMap.put("otherfee", order.getOtherfee());
			objectMap.put("discountfee", order.getDiscountfee());
			objectMap.put("totalmoney", order.getTotalmoney());
			objectMap.put("firstpayment", order.getFirstpayment());
			objectMap.put("firstarrivalflag", order.getFirstarrivalflag());
			objectMap.put("firstarrivalflagname", order.getFirstarrivalflagname());
			objectMap.put("finalpayment", order.getFinalpayment());
			objectMap.put("finalarrivalflag", order.getFinalarrivalflag());
			objectMap.put("finalarrivalflagname", order.getFinalarrivalflagname());
			objectMap.put("paytype", order.getPaytype());
			objectMap.put("paytypename", order.getPaytypename());
			objectMap.put("addtime", StringUtils.isEmpty(order.getAddtime())?"":Tools.getStr(order.getAddtime()).substring(0, 19));
			objectMap.put("status", order.getStatus());
			objectMap.put("statusname", order.getStatusname());
			objectMap.put("filingflag", order.getFilingflag());
			objectMap.put("filingflagname", order.getFilingflagname());
			objectMap.put("operatetime", Tools.getSubString(order.getOperatetime(), 0, 16));
			//获取订单状态操作时间与现状的时间差
			long difftime = Tools.diffTime(Tools.getCurrentTime(), order.getOperatetime(), "yyyy-MM-dd HH:mm:ss");
			DecimalFormat df = new DecimalFormat("0.00");
			Float operatortimeStr  = Float.valueOf(df.format((float)difftime/3600));
			//得到当前该条数据的时效性等级
			if(operatortimeStr < time1){
				objectMap.put("timelevel", "0");
			}else if(operatortimeStr >= time1 && operatortimeStr < time2){
				objectMap.put("timelevel", "1");
			}else if(operatortimeStr >=time2 &&  operatortimeStr < time3){
				objectMap.put("timelevel", "2");
			}else{
				objectMap.put("timelevel", "3");
			}
			
			//潜在订户信息
			objectMap.put("usercode", order.getUsercode());
			objectMap.put("username", order.getUsername());
			objectMap.put("phone", order.getPhone());
			objectMap.put("usersex", order.getUsersex());
			objectMap.put("usersexname", order.getUsersexname());
			objectMap.put("source", order.getSource());
			objectMap.put("sourcename", order.getSourcename());
			objectMap.put("visittype", order.getVisittype());
			objectMap.put("visittypename", order.getVisittypename());
			objectMap.put("address", order.getAddress());
			//上门员信息
			Employee visitor = employeeDao.findByEmployeecodeStr(order.getVisitorcode());
			if(visitor == null){
				visitor = new Employee();
			}
			objectMap.put("visitorcode", order.getVisitorcode());
			objectMap.put("visitorname", visitor.getEmployeename());
			objectMap.put("visitorphone", visitor.getPhone());
			objectMap.put("visitorstatus", order.getVisitorstatus());
			objectMap.put("visitorstatusname", order.getVisitorstatusname());
			//销售员信息
			if(StringUtils.isNotEmpty(order.getSalercode())){
				Employee saler = employeeDao.findByEmployeecodeStr(order.getSalercode());
				if(saler != null){
					objectMap.put("salercode", order.getSalercode());
					objectMap.put("salername", saler.getEmployeename());
					objectMap.put("salerphone", saler.getPhone());
				}
			}
			//讲解人员信息
			if(StringUtils.isNotEmpty(order.getTalkercode())){
				Employee taalker = employeeDao.findByEmployeecodeStr(order.getTalkercode());
				if(taalker != null){
					objectMap.put("talkercode", order.getTalkercode());
					objectMap.put("talkername", taalker.getEmployeename());
					objectMap.put("talkerphone", taalker.getPhone());
				}
			}
			
			objectMap.put("visitorflag", order.getVisitorflag());
			objectMap.put("visitorflagname", order.getVisitorflagname());
			objectMap.put("productcolorcode", order.getProductcolor());
			objectMap.put("productcolorname", order.getProductcolorname());
			objectMap.put("visitorflag", order.getVisitorflag());
			objectMap.put("visitorflagname", order.getVisitorflagname());
			objectMap.put("lockcoreflag", order.getLockcoreflag());
			objectMap.put("lockcoreflagname", order.getLockcoreflagname());
			objectMap.put("productconfirm", order.getProductconfirm());
			objectMap.put("productconfirmname", order.getProductconfirmname());
			objectMap.put("modelcode", order.getModelcode());
			objectMap.put("modelname", order.getModelname());
			objectMap.put("discountgain", order.getDiscountgain());
			objectMap.put("fixedgain", order.getFixedgain());
			objectMap.put("managergain", order.getManagergain());
			objectMap.put("managercode", order.getManagercode());
			if(StringUtils.isNotEmpty(order.getManagercode())){
				Employee manager = employeeDao.findByEmployeecodeStr(order.getManagercode());
				if(manager != null){
					objectMap.put("managername", manager.getEmployeename());
				}
			}
			
			objectMap.put("orderinfo", Tools.getStr(order.getOrderinfo()));
			objectMap.put("remark", order.getRemark());
			
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}
    
	public String getTimeparaLevel(Float time){
		Timepara timepara = new Timepara();
		List<Timepara> timeparalist = timeparaDao.queryByList(timepara);
		Integer time1 = timeparalist.get(0).getTime();
		Integer time2 = timeparalist.get(1).getTime();
		Integer time3 = timeparalist.get(2).getTime();
		if(time < time1){
			return "0";
		}else if(time >= time1 && time < time2){
			return "1";
		}else if(time >=time2 &&  time < time3){
			return "2";
		}else{
			return "3";
		}
	}
	
	
	/**
	 * 添加 订单页面初始化
	 */
	@RequestMapping(value = "/addInit")
	public String addInit(Userorder form) {
		Usertask usertask = taskDao.findByTaskcodeStr(form.getTaskcode());
		Employee saler = null;
		if(StringUtils.isNotEmpty(usertask.getSalercode())){
			saler = employeeDao.findByEmployeecodeStr(usertask.getSalercode());
		}
		if(saler == null){
			saler = new Employee();
		}
		
		Employee talker = null;
		if(StringUtils.isNotEmpty(usertask.getTalkercode())){
			talker = employeeDao.findByEmployeecodeStr(usertask.getTalkercode());
		}
		if(talker == null){
			talker = new Employee();
		}
		
		Employee visitor = null;
		if(StringUtils.isNotEmpty(usertask.getVisitorcode())){
			visitor = employeeDao.findByEmployeecodeStr(usertask.getVisitorcode());
		}
		if(visitor == null){
			visitor = new Employee();
		}
		
		usertask.setSaler(saler);
		usertask.setTalker(talker);
		usertask.setVisitor(visitor);
		form.setUsertask(usertask);
		
		if("0".equals(usertask.getTasktype())){//讲解单
			return "userorder/addUserorder";
		}else{//如果此任务单上门类型为2-已讲解，上门只需测量任务单，就不能修改产品型号，
			Userorder  userorder = userorderDao.findByOrdercode(usertask.getOrdercode());
			List<Userorderinfo> userorderinfolist =  userorderinfoDao.findByOrdercode(usertask.getOrdercode());
			userorder.setUserorderinfolist(userorderinfolist);
			form.setUserorder(userorder);
			return "userorder/fillUserorder";
		}
	}

	/**
	 * 保存添加 订单
	 */
	@RequestMapping(value = "/save")
	public String save(Userorder form) {
		//操作员
		Operator operator = (Operator)getSession().getAttribute("Operator");
		Map<String, Object> responseMap = userorderService.saveUserorder(form, getRequest(), operator);
		
		form.setReturninfo(String.valueOf(responseMap.get("result")));
		
		return addInit(form);
	}
	
	/**
	 * 完善任务单上门测量信息
	 */
	@RequestMapping(value = "/saveFill")
	public String saveFill(Userorder form) {
		//操作员
		Operator operator = (Operator)getSession().getAttribute("Operator");
		Map<String, Object> responseMap = userorderService.saveFillUserorder(form, getRequest(), operator);
		
		form.setReturninfo(String.valueOf(responseMap.get("result")));
		
		return addInit(form);
	}
    
	/**
	 * 查看 订单权限初始化页面
	 */
	@RequestMapping(value = "/lookInit")
	public String lookInit(Userorder form) {
		Userorder userorder = userorderDao.findById(form.getId());
		//封装订户
		User user = userDao.findByUsercodeStr(userorder.getUsercode());
		if(user == null){
			user = new User();
		}
		userorder.setUser(user);
		
		//销售员
		Employee saler = null;
		if(StringUtils.isNotEmpty(userorder.getSalercode())){
			saler = employeeDao.findByEmployeecodeStr(userorder.getSalercode());
		}
		if(saler == null){
			saler = new Employee();
		}
		userorder.setSaler(saler);
		
		//封装测量讲解
		Employee talker = null;
		if(StringUtils.isNotEmpty(userorder.getTalkercode())){
			talker = employeeDao.findByEmployeecodeStr(userorder.getTalkercode());
		}
		if(talker == null){
			talker = new Employee();
		}
		userorder.setTalker(talker);
		
		//封装测量人员
		Employee visitor = null;
		if(StringUtils.isNotEmpty(userorder.getVisitorcode())){
			visitor = employeeDao.findByEmployeecodeStr(userorder.getVisitorcode());
		}
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
		
		//封装安装信息
		Userdispatch dispatch = dispatchDao.findValidByOrdercode(userorder.getOrdercode());
		if(dispatch != null){
			dispatch.setInstaller(employeeDao.findByEmployeecodeStr(dispatch.getInstallercode()));
			userorder.setUserdispatch(dispatch);
		}else{
			userorder.setUserdispatch(new Userdispatch());
		}
		
		form.setUserorder(userorder);
		return "userorder/lookUserorder";
	}
	
	/**
	 * 编辑 订单权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Userorder form) {
		Userorder order = userorderDao.findById(form.getId());
		//order.setUser(userDao.findById(order.getUsercode()));
		Userproduct userproductForm = new Userproduct();
		userproductForm.setOrdercode(order.getOrdercode());
		List<Userproduct> userproductList =  userproductDao.queryByList(userproductForm);
		order.setUserproductList(userproductList);
		Employee visitor  = employeeDao.findByEmployeecodeStr(order.getVisitorcode());
		if(visitor == null){
			visitor = new Employee();
		}
		order.setVisitor(visitor);
		//初始化销售员
		Employee saler = null;
		if(StringUtils.isNotEmpty(order.getSalercode())){
			saler  = employeeDao.findByEmployeecodeStr(order.getSalercode());
		}
		if(saler == null){
			saler = new Employee();
		}
		order.setSaler(saler);
		
		form.setUserorder(order);
		return "userorder/updateUserorder";
	}

	/**
	 * 保存编辑后 订单权限
	 */
	@RequestMapping(value = "/update")
	public String update(Userorder form, Productmodel productmodelform) {
		
		//操作员
		Operator operator = (Operator)getSession().getAttribute("Operator");

		Map<String, Object> responseMap = userorderService.updateUserorder(form, getRequest(), operator);
		
		form.setReturninfo(String.valueOf(responseMap.get("result")));
		
		return updateInit(form);
	}
	
	/**
	 * 编辑 订单权限初始化页面
	 */
	@RequestMapping(value = "/updateUserproductInit")
	public String updateUserproductInit(Userorder form) {
		Userorder userorder = userorderDao.findById(form.getId());
		
		//初始化销售员
		Employee saler = null;
		if(StringUtils.isNotEmpty(userorder.getSalercode())){
			saler  = employeeDao.findByEmployeecodeStr(userorder.getSalercode());
		}
		if(saler == null){
			saler = new Employee();
		}
		userorder.setSaler(saler);
		
		//讲解员
		Employee talker = null;
		if(StringUtils.isNotEmpty(userorder.getTalkercode())){
			talker  = employeeDao.findByEmployeecodeStr(userorder.getTalkercode());
		}
		if(talker == null){
			talker = new Employee();
		}
		userorder.setTalker(talker);
		
		List<Userorderinfo> userorderinfolist =  userorderinfoDao.findByOrdercode(userorder.getOrdercode());
		userorder.setUserorderinfolist(userorderinfolist);
		
		
		form.setUserorder(userorder);
		return "userorder/updateUserproduct";
	}

	/**
	 * 保存编辑后 订单权限
	 */
	@RequestMapping(value = "/updateUserproduct")
	public String updateUserproduct(Userorder form, Productmodel productmodelform) {
		
		//操作员
		Operator operator = (Operator)getSession().getAttribute("Operator");

		Map<String, Object> responseMap = userorderService.updateUserproduct(form, getRequest(), operator);
		
		form.setReturninfo(String.valueOf(responseMap.get("result")));
		
		return updateUserproductInit(form);
	}

	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Userorder form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Userorder order = userorderDao.findById(form.getId());
		
		//修改客户状态为未处理状态
		User user = userDao.findByUsercodeStr(order.getUsercode());
		user.setStatus("0");
		userDao.update(user);
		
		//删除 订单
		userorderDao.delete(form.getId());
		//删除 订单详情
		userorderinfoDao.deleteByOrdercode(order.getOrdercode());
		//删除订单产品
		userproductDao.deleteByOrdercode(order.getOrdercode());
		//删除合同
		usercontractDao.deleteByOrdercode(order.getOrdercode());
		//删除任务单
		taskDao.deleteByOrdercode(order.getOrdercode());
		//删除提成
		salegaininfoDao.deleteByOrdercode(order.getOrdercode());
		//删除门锁图片
		userdoorDao.deleteByOrdercode(order.getOrdercode());
		
		//增加业务记录
		Userbusiness userbusiness = new Userbusiness();
		userbusiness.setOperatorcode(operator.getEmployeecode());
		userbusiness.setBusinesstypekey("orderdel");
		userbusiness.setBusinesstypename("订单删除");
		userbusiness.setUsercode(order.getUsercode());
		userbusiness.setTaskcode(order.getTaskcode());
		userbusiness.setOrdercode(order.getOrdercode());
		userbusiness.setDispatchcode(null);
		userbusiness.setAddtime(Tools.getCurrentTime());
		userbusiness.setContent("删除订单;订单号为： "+order.getOrdercode());
		userbusiness.setSource("1");//PC平台操作
		userbusinessDao.save(userbusiness);
		
		//增加操作日记
		String content = "删除 订单， 订单编号:"+order.getOrdercode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
	/**
	 * 查询订单信息
	 */
	@RequestMapping(value = "/findUserorderListDialog")
	public String findUserorderListDialog(Userorder form) {
		return "userorder/findUserorderListDialog";
	}
	
	/**
	 * 订单结单
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/saveComplete")
	public Map<String,Object> saveComplete(Userorder form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Userorder order = userorderDao.findById(form.getId());
		order.setStatus("2");//订单状态修改为已结单
		//保存修改
		userorderDao.update(order);
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "订单结单， 订单编号:"+order.getOrdercode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "结单成功");
		
		return responseJson;
	}
	
	/**
	 * 订单作废
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/saveCancel")
	public Map<String,Object> saveCancel(Userorder form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Userorder order = userorderDao.findById(form.getId());
		order.setStatus("21");//订单状态修改为订单取消
		//保存修改
		userorderDao.update(order);
		//更新订户的产品
		Userproduct userproductForm = new Userproduct();
		userproductForm.setOrdercode(form.getOrdercode());
		List<Userproduct> userproductlist = userproductDao.queryByList(userproductForm);
		for (Userproduct userproduct : userproductlist) {
			//修改产品的库存状态为已出库
			Productdepot depotForm = productdepotDao.findByProductidentfy(userproduct.getProductidentfy());
			depotForm.setProductstatus("1"); //修改库存
			depotForm.setDepotamount(depotForm.getDepotamount()+1);//库存+1
			productdepotDao.update(depotForm);
			
			//清空订单关联的产品标识
			userproduct.setProductidentfy("");
			userproduct.setInoutercode(null);
			userproductForm.setAddtime(null);
			userproductForm.setProductversion(null);
			userproductDao.update(userproduct);
		}
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "订单取消， 订单编号:"+order.getOrdercode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "取消成功");
		
		return responseJson;
	}
	
	/**
	 * 查询 订单-工程部
	 */
	@RequestMapping(value = "/findUserorderListForResearch")
	public String findUserorderListForResearch(Userorder form) {
		return "userorder/findUserorderListForResearch";
	}
	
	/**
	 * 查询 订单-首付款确认到账
	 */
	@RequestMapping(value = "/findUserorderListForFirstarrival")
	public String findUserorderListForFirstarrival(Userorder form) {
		return "userorder/findUserorderListForFirstarrival";
	}
	
	/**
	 * 确认首款到账
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/updateFirstArrival")
	public Map<String,Object> updateFirstArrival(Userorder form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Userorder order = userorderDao.findById(form.getId());
		
		order.setFirstarrivalflag("1");
		order.setStatus("1");
		order.setOperatetime(Tools.getCurrentTime());
		
		//修改订单首付款到账
		userorderDao.updateFirstarrival(order);
		
		//查询关联的任务单号
		//Usertask task =  taskDao.findByTaskcodeStr(order.getTaskcode());
		//增加业务记录
		Userbusiness userbusiness = new Userbusiness();
		userbusiness.setOperatorcode(operator.getEmployeecode());
		userbusiness.setBusinesstypekey("firstarrival");
		userbusiness.setBusinesstypename("首款到账");
		userbusiness.setUsercode(order.getUsercode());
		//userbusiness.setTaskcode(task.getTaskcode());
		userbusiness.setOrdercode(order.getOrdercode());
		userbusiness.setDispatchcode(null);
		userbusiness.setAddtime(Tools.getCurrentTime());
		userbusiness.setContent("首款到账;订单号为： "+order.getOrdercode()+ ";金额为:"+ order.getFirstpayment());
		userbusiness.setSource("1");//PC平台操作
		userbusinessDao.save(userbusiness);
		
		
		//增加操作日记
		String content = "首款到账确认， 订单编号:"+order.getOrdercode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "操作成功");
		
		return responseJson;
	}
	
	/**
	 * 查询 订单-尾款款确认到账
	 */
	@RequestMapping(value = "/findUserorderListForFinalarrival")
	public String findUserorderListForFinalarrival(Userorder form) {
		return "userorder/findUserorderListForFinalarrival";
	}
	
	/**
	 * 确认尾款到账
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/updateFinalArrival")
	public Map<String,Object> updateFinalArrival(Userorder form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Userorder order = userorderDao.findById(form.getId());
		order.setFinalarrivalflag("1");
		order.setStatus("7");
		order.setOperatetime(Tools.getCurrentTime());
		
		//修改订单尾款到账
		userorderDao.updateFinalarrival(order);
		
		//查询关联的任务单号
		//Usertask task =  taskDao.findByTaskcodeStr(order.getTaskcode());
		//增加业务记录
		Userbusiness userbusiness = new Userbusiness();
		userbusiness.setOperatorcode(operator.getEmployeecode());
		userbusiness.setBusinesstypekey("finalarrival");
		userbusiness.setBusinesstypename("尾款到账");
		userbusiness.setUsercode(order.getUsercode());
		//userbusiness.setTaskcode(order.getTaskcode());
		userbusiness.setOrdercode(order.getOrdercode());
		userbusiness.setDispatchcode(null);
		userbusiness.setAddtime(Tools.getCurrentTime());
		userbusiness.setContent("尾款已到账;订单号为： "+order.getOrdercode() + ";金额为:"+ order.getFinalpayment());
		userbusiness.setSource("1");//PC平台操作
		userbusinessDao.save(userbusiness);
		
		//增加操作日记
		String content = "尾款到账确认， 订单编号:"+order.getOrdercode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "操作成功");
		
		return responseJson;
	}
	
	/**
	 * 查询订单-订单产品确认
	 */
	@RequestMapping(value = "/findUserorderListForProductconfirm")
	public String findUserorderListForProductconfirm(Userorder form) {
		return "userorder/findUserorderListForProductconfirm";
	}
	
	/**
	 * 订单产品确认初始化页面
	 */
	@RequestMapping(value = "/updateUserproductconfirmInit")
	public String updateUserproductconfirmInit(Userorder form) {
		Userorder userorder = userorderDao.findById(form.getId());
		
		List<Userorderinfo> userorderinfolist =  userorderinfoDao.findByOrdercode(userorder.getOrdercode());
		userorder.setUserorderinfolist(userorderinfolist);
		
		form.setUserorder(userorder);
		return "userorder/updateUserproductconfirm";
	}
	
	/**
	 * 保存订单产品确认
	 */
	@RequestMapping(value = "/updateUserproductconfirm")
	public String updateUserproductconfirm(Userorder form, Productmodel productmodelform) {
		
		//操作员
		Operator operator = (Operator)getSession().getAttribute("Operator");

		Map<String, Object> responseMap = userorderService.updateUserproductconfirm(form, getRequest(), operator);
		
		form.setReturninfo(String.valueOf(responseMap.get("result")));
		
		return updateUserproductInit(form);
	}
	
	
	
	/**
	 * 查询订单-订单打包
	 */
	@RequestMapping(value = "/findUserorderListForPack")
	public String findUserorderListForPack(Userorder form) {
		return "userorder/findUserorderListForPack";
	}
	
	/**
	 * 订单打包初始化
	 */
	@RequestMapping(value = "/packInit")
	public String packInit(Userorder form) {
		Userorder userorder = userorderDao.findById(form.getId());
		//封装订户
		if(StringUtils.isNotEmpty(userorder.getUsercode())){
			User user = userDao.findByUsercodeStr(userorder.getUsercode());
			if(user == null){
				 user = new User();
			}
			userorder.setUser(user);
		}else{
			User user = new User();
			userorder.setUser(user);
		}
		
		//封装上门人员
		if(StringUtils.isNotEmpty(userorder.getVisitorcode())){
			Employee visitor = employeeDao.findByEmployeecodeStr(userorder.getVisitorcode());
			if(visitor == null){
				visitor = new Employee();
			}
			userorder.setVisitor(visitor);
		}else{
			Employee visitor = new Employee();
			userorder.setVisitor(visitor);
		}
		
		form.setUserorder(userorder);
		return "userorder/packUserorder";
	}
	
	/**
	 * 保存订单打包
	 */
	@RequestMapping(value = "/savePackUserorder")
	public String savePackUserorder(Userorder form) {
		//操作员
		Operator operator = (Operator)getSession().getAttribute("Operator");
		Map<String, Object> responseMap = userorderService.savePackUserorder(form, getRequest(), operator);
				
		form.setReturninfo(String.valueOf(responseMap.get("result")));
		
		return packInit(form);
	}
	
	
	/**
	 * 查询订单-订单发货
	 */
	@RequestMapping(value = "/findUserorderListForDelivery")
	public String findUserorderListForDelivery(Userorder form) {
		return "userorder/findUserorderListForDelivery";
	}
	
	/**
	 * 订单发货初始化
	 */
	@RequestMapping(value = "/deliveryInit")
	public String deliveryInit(Userorder form) {
		Userorder userorder = userorderDao.findById(form.getId());
		//封装订户
		if(StringUtils.isNotEmpty(userorder.getUsercode())){
			User user = userDao.findByUsercodeStr(userorder.getUsercode());
			if(user == null){
				 user = new User();
			}
			userorder.setUser(user);
		}else{
			User user = new User();
			userorder.setUser(user);
		}
		
		//封装上门人员
		if(StringUtils.isNotEmpty(userorder.getVisitorcode())){
			Employee visitor = employeeDao.findByEmployeecodeStr(userorder.getVisitorcode());
			if(visitor == null){
				visitor = new Employee();
			}
			userorder.setVisitor(visitor);
		}else{
			Employee visitor = new Employee();
			userorder.setVisitor(visitor);
		}
		
		form.setUserorder(userorder);
		
		//设置默认快递时间为今天
		String deliverytime = getRequest().getParameter("deliverytime") != "" && getRequest().getParameter("deliverytime") != null ? getRequest().getParameter("deliverytime") : Tools.getDateMonthDayStrTwo();
		form.setDeliverytime(deliverytime);
		return "userorder/deliveryUserorder";
	}
	
	
	/**
	 * 保存订单发货
	 */
	@RequestMapping(value = "/saveDeliveryUserorder")
	public String saveDeliveryUserorder(Userorder form) {
		
		Operator operator = (Operator)getSession().getAttribute("Operator");
		Map<String, Object> responseMap = userorderService.saveDeliveryUserorder(form, getRequest(), operator);
		form.setReturninfo(String.valueOf(responseMap.get("result")));
		
		return deliveryInit(form);
	}
	
	/**
	 * 查询订单-订单派工
	 */
	@RequestMapping(value = "/findUserorderListForAssign")
	public String findUserorderListForAssign(Userorder form) {
		return "userorder/findUserorderListForAssign";
	}
	
	/**
	 * 订单派工初始化
	 */
	@RequestMapping(value = "/assignInit")
	public String assignInit(Userorder form) {
		Userorder userorder = userorderDao.findById(form.getId());
		//封装订户
		userorder.setUser(userDao.findByUsercodeStr(userorder.getUsercode()));
		
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
		
		form.setUserorder(userorder);
		return "userorder/assignUserorder";
	}
	
	/**
	 * 保存订单派工
	 */
	@RequestMapping(value = "/saveAssignUserorder")
	public String saveAssignUserorder(Userorder form) {
		//加锁
		synchronized(lock) {
			Operator operator = (Operator)getSession().getAttribute("Operator");
			String currenttime = Tools.getCurrentTime();
			
			//派工信息
			String installercode = Tools.getStr(getRequest().getParameter("installercode"));
			String installername = Tools.getStr(getRequest().getParameter("installername"));
			String content = Tools.getStr(getRequest().getParameter("content"));
			
			//修改订单的状态
			Userorder userorder = userorderDao.findById(form.getId());
			
			if(!"2".equals(userorder.getStatus())){
				form.setReturninfo("此订单不是已发货状态，不能派工");
				return assignInit(form);
			}
			
			userorder.setStatus("3");//已派工
			userorder.setOperatetime(currenttime);
			userorderDao.updateStatus(userorder);
			
			//先将其他工单作废
			Userdispatch dispatchqueryform = new Userdispatch();
			dispatchqueryform.setUsercode(userorder.getUsercode());
			dispatchqueryform.setOperatorcode(userorder.getOrdercode());
			List<Userdispatch> dispatchlist =  dispatchDao.queryByList(dispatchqueryform);
			for (Userdispatch userdispatch : dispatchlist) {
				userdispatch.setValidstatus("0");
				dispatchDao.updateValidstatus(userdispatch);
			}
			
			//保存工单
			Userdispatch dispatchform = new Userdispatch();
			//工单号
			String dispatchcode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_dispatchcode);
			dispatchform.setDispatchcode("GD"+dispatchcode);
			dispatchform.setUsercode(form.getUsercode());
			dispatchform.setUsername(form.getUsername());
			dispatchform.setUsersex(form.getUsersex());
			dispatchform.setPhone(form.getPhone());
			dispatchform.setAddress(form.getAddress());
			dispatchform.setSource(form.getSource());
			dispatchform.setOrdercode(form.getOrdercode());
			dispatchform.setInstallercode(installercode);
			dispatchform.setOperatorcode(operator.getEmployeecode());
			dispatchform.setAddtime(Tools.getCurrentTime());
			dispatchform.setInstalltime(currenttime);
			dispatchform.setContent(content);
			dispatchform.setStatus("1");
			//dispatchform.setDealdate("");
			dispatchform.setDealresult("");
			dispatchform.setCheckstatus("0");
			dispatchform.setCheckresult("");
			dispatchform.setValidstatus("1");//默认有效
			dispatchDao.save(dispatchform);
			
			//查询关联的任务单号
			//Usertask task =  taskDao.findByTaskcodeStr(userorder.getTaskcode());
			//增加业务记录
			Userbusiness userbusiness = new Userbusiness();
			userbusiness.setOperatorcode(operator.getEmployeecode());
			userbusiness.setBusinesstypekey("orderassign");
			userbusiness.setBusinesstypename("订单派工");
			userbusiness.setUsercode(userorder.getUsercode());
			//userbusiness.setTaskcode(userorder.getTaskcode());
			userbusiness.setOrdercode(userorder.getOrdercode());
			userbusiness.setDispatchcode(dispatchform.getDispatchcode());
			userbusiness.setAddtime(currenttime);
			userbusiness.setContent("订单派工:订单号为： "+userorder.getOrdercode());
			userbusiness.setSource("1");//PC平台操作
			userbusinessDao.save(userbusiness);
			
			//增加操作日记
			String installercontent = "订单派工， 订单号:"+form.getOrdercode() +";安装人："+installername;
			operatorlogService.saveOperatorlog(installercontent, operator.getEmployeecode());
		}
		
		form.setReturninfo("订单派工成功");
		return assignInit(form);
	}
	
	
	/**
	 * 查询订单-订单结单
	 */
	@RequestMapping(value = "/findUserorderListForCheck")
	public String findUserorderListForCheck(Userorder form) {
		return "userorder/findUserorderListForCheck";
	}
	
	/**
	 * 确认订单结单
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/updateForCheck")
	public Map<String,Object> updateForCheck(Userorder form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
        Map<String, Object> responseMap = userorderService.saveCheckUserorder(form, getRequest(), operator);
		
        responseJson.put("flag", responseMap.get("status"));
		responseJson.put("msg", responseMap.get("result"));
		
		return responseJson;
	}
	
	/**
	 * 查询订单-订单归档
	 */
	@RequestMapping(value = "/findUserorderListForFiling")
	public String findUserorderListForFiling(Userorder form) {
		return "userorder/findUserorderListForFiling";
	}
	
	/**
	 * 确认订单归档
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/updateForFiling")
	public Map<String,Object> updateForFiling(Userorder form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		String ids = getRequest().getParameter("ids");
		if(StringUtils.isEmpty(ids)){
			responseJson.put("flag", "0");//删除成功
			responseJson.put("msg", "请选择需要归档的订单");
		}
		
		String[] idArray = ids.split(",");
		for (String idStr : idArray) {
			//修改订单是否归档
			Userorder order = userorderDao.findById(Integer.parseInt(idStr));
			order.setFilingflag("1");
			userorderDao.updatefilingflag(order);
			
			//查询关联的任务单号
			//Usertask task = taskDao.findByTaskcodeStr(order.getTaskcode());
			//增加业务记录
			Userbusiness userbusiness = new Userbusiness();
			userbusiness.setOperatorcode(operator.getEmployeecode());
			userbusiness.setBusinesstypekey("orderfiling");
			userbusiness.setBusinesstypename("订单归档");
			userbusiness.setUsercode(order.getUsercode());
			//userbusiness.setTaskcode(task.getTaskcode());
			userbusiness.setOrdercode(order.getOrdercode());
			userbusiness.setDispatchcode(null);
			userbusiness.setAddtime(Tools.getCurrentTime());
			userbusiness.setContent("订单已归档;订单号为： "+order.getOrdercode());
			userbusiness.setSource("1");//PC平台操作
			userbusinessDao.save(userbusiness);
			
			//增加操作日记
			String content = "订单归档， 订单号为:"+order.getOrdercode();
			operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		}
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "工单归档成功");
		
		return responseJson;
	}
	
	/**
	 * 查询订单-快递查询
	 */
	@RequestMapping(value = "/findUserorderListForDeliveryQuery")
	public String findUserorderListForDeliveryQuery(Userorder form) {
		return "userorder/findUserorderListForDeliveryQuery";
	}
	
	/**
	 * 查询订单-合同管理
	 */
	@RequestMapping(value = "/findUserorderListForContract")
	public String findUserorderListForContract(Userorder form) {
		return "userorder/findUserorderListForContract";
	}
	
	/**
	 * 查询订单-合同查询
	 */
	@RequestMapping(value = "/findUserorderListForContractQuery")
	public String findUserorderListForContractQuery(Userorder form) {
		return "userorder/findUserorderListForContractQuery";
	}
	
	/**
	 * 查询 订单-订单付款界面
	 */
	@RequestMapping(value = "/findUserorderListForPay")
	public String findUserorderListForPay(Userorder form) {
		return "userorder/findUserorderListForPay";
	}
	
	/**
	 * 确认支付信息
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/savePaymentInfo")
	public Map<String,Object> savePaymentInfo(Userorder form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		//订单
		Userorder userorder = userorderDao.findById(form.getId());
		//付款项目(1-首付款；2-尾款)
		String payitem = Tools.getStr(form.getPayitem());
		String paytype = Tools.getStr(form.getPaytype());//付款方式(0-现金;1-微信在线;2-支付宝在线；3-微信静态码；4-支付宝静态码；5-POS机刷卡)
		//支付信息
		Userpaylog userpaylog = new Userpaylog();
		userpaylog.setUsercode(userorder.getUsercode());
		userpaylog.setUsername(userorder.getUsername());
		userpaylog.setPhone(userorder.getPhone());
		userpaylog.setSource(userorder.getSource());
		userpaylog.setAddress(userorder.getAddress());
		userpaylog.setOrdercode(userorder.getOrdercode());
		userpaylog.setPaytype(paytype); //现金支付
		userpaylog.setReceivercode(operator.getEmployeecode());
		userpaylog.setPaytime(Tools.getCurrentTime());
		//微信端支付
		userpaylog.setPaysource("1");
		userpaylog.setCheckstatus("1"); //财务直接确认审核
		userpaylog.setChecktime(Tools.getCurrentTime());
		
		if("1".equals(payitem)){//首付款
			if(!"0".equals(userorder.getFirstarrivalflag())){
				responseJson.put("flag", "0");//删除成功
				responseJson.put("msg", "支付失败，订单中首款已经支付");
				return responseJson;
			}
			userorder.setFirstarrivalflag("2");
			userorderDao.updateFirstarrival(userorder);
			//增加首款支付记录
			userpaylog.setPayitem("1");
			userpaylog.setPaymoney(userorder.getFirstpayment());
			userpaylogDao.save(userpaylog);
		}else if("2".equals(payitem)){//尾款
			
			if(!"0".equals(userorder.getFinalarrivalflag())){
				responseJson.put("flag", "0");//删除成功
				responseJson.put("msg", "支付失败，订单中尾款已经支付");
				return responseJson;
			}
					
			userorder.setFinalarrivalflag("2");
			userorderDao.updateFinalarrival(userorder);
			
			//增加尾款支付记录
			userpaylog.setPayitem("2");
			userpaylog.setPaymoney(userorder.getFinalpayment());
			userpaylogDao.save(userpaylog);
		}
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "操作成功");
		
		return responseJson;
	}
	
	
	/**
	 * 查询订单-订单产品更换
	 */
	@RequestMapping(value = "/findUserorderListForProductreplace")
	public String findUserorderListForProductreplace(Userorder form) {
		return "userorder/findUserorderListForProductreplace";
	}
	
	/**
	 * 订单产品更换初始化页面
	 */
	@RequestMapping(value = "/saveUserproductreplaceInit")
	public String saveUserproductreplaceInit(Userorder form) {
		Userorder userorder = userorderDao.findById(form.getId());
		
		List<Userorderinfo> userorderinfolist =  userorderinfoDao.findByOrdercode(userorder.getOrdercode());
		userorder.setUserorderinfolist(userorderinfolist);
		
		form.setUserorder(userorder);
		return "userorder/replaceUserorderproduct";
	}
	
	/**
	 * 保存订单产品更换
	 */
	@RequestMapping(value = "/saveUserproductreplace")
	public String saveUserproductreplace(Userorder form, Productmodel productmodelform) {
		
		//操作员
		Operator operator = (Operator)getSession().getAttribute("Operator");

		Map<String, Object> responseMap = userorderService.saveUserproductreplace(form, getRequest(), operator);
		
		form.setReturninfo(String.valueOf(responseMap.get("result")));
		
		return saveUserproductreplaceInit(form);
	}
	
	/**
	 * 订单产品软件升级初始化页面
	 */
	@RequestMapping(value = "/saveUserproductChangeVersionInit")
	public String saveUserproductChangeVersionInit(Userorder form) {
		Userorder userorder = userorderDao.findById(form.getId());
		
		List<Userorderinfo> userorderinfolist =  userorderinfoDao.findByOrdercode(userorder.getOrdercode());
		userorder.setUserorderinfolist(userorderinfolist);
		
		form.setUserorder(userorder);
		return "userorder/changeUserorderproductVersion";
	}
	
	/**
	 * 保存订单产品软件升级
	 */
	@RequestMapping(value = "/saveUserproductChangeVersion")
	public String saveUserproductChangeVersion(Userorder form, Productmodel productmodelform) {
		
		//操作员
		Operator operator = (Operator)getSession().getAttribute("Operator");

		Map<String, Object> responseMap = userorderService.saveUserproductChangeVersion(form, getRequest(), operator);
		
		form.setReturninfo(String.valueOf(responseMap.get("result")));
		
		return saveUserproductreplaceInit(form);
	}
	
	
	/**
	 * 修改订单产品价格初始化页面
	 */
	@RequestMapping(value = "/updateProductfeeInit")
	public String updateProductfeeInit(Userorder form) {
		Userorder userorder = userorderDao.findById(form.getId());
		
		List<Userorderinfo> userorderinfolist =  userorderinfoDao.findByOrdercode(userorder.getOrdercode());
		userorder.setUserorderinfolist(userorderinfolist);
		
		form.setUserorder(userorder);
		return "userorder/updateProductfee";
	}

	/**
	 * 保存订单产品价格修改
	 */
	@RequestMapping(value = "/updateProductfee")
	public String updateProductfee(Userorder form, Productmodel productmodelform) {
		
		//操作员
		Operator operator = (Operator)getSession().getAttribute("Operator");

		Map<String, Object> responseMap = userorderService.updateProductfee(form, getRequest(), operator);
		
		form.setReturninfo(String.valueOf(responseMap.get("result")));
		
		return updateProductfeeInit(form);
	}
	
	/**
	 * 修改订单详情初始化页面
	 */
	@RequestMapping(value = "/updateOrderinfoInit")
	public String updateOrderinfoInit(Userorder form) {
		Userorder userorder = userorderDao.findById(form.getId());
		
		List<Userorderinfo> userorderinfolist =  userorderinfoDao.findByOrdercode(userorder.getOrdercode());
		userorder.setUserorderinfolist(userorderinfolist);
		
		form.setUserorder(userorder);
		return "userorder/updateOrderinfo";
	}

	/**
	 * 保存订单详情修改
	 */
	@RequestMapping(value = "/updateOrderinfo")
	public String updateOrderinfo(Userorder form, Productmodel productmodelform) {
		
		//操作员
		Operator operator = (Operator)getSession().getAttribute("Operator");

		Map<String, Object> responseMap = userorderService.updateOrderinfo(form, getRequest(), operator);
		
		form.setReturninfo(String.valueOf(responseMap.get("result")));
		
		return updateOrderinfoInit(form);
	}
	
	/**
	 * 使用优惠卡初始化页面
	 */
	@RequestMapping(value = "/useGiftcardInit")
	public String useGiftcardInit(Userorder form) {
		Userorder userorder = userorderDao.findById(form.getId());
		
		List<Userorderinfo> userorderinfolist =  userorderinfoDao.findByOrdercode(userorder.getOrdercode());
		userorder.setUserorderinfolist(userorderinfolist);
		
		form.setUserorder(userorder);
		return "userorder/useGiftcard";
	}

	/**
	 * 保存使用优惠卡
	 */
	@RequestMapping(value = "/saveUseGiftcard")
	public String saveUseGiftcard(Userorder form, Productmodel productmodelform) {
		
		//操作员
		Operator operator = (Operator)getSession().getAttribute("Operator");

		Map<String, Object> responseMap = userorderService.saveUseGiftcard(form, getRequest(), operator);
		
		form.setReturninfo(String.valueOf(responseMap.get("result")));
		
		return useGiftcardInit(form);
	}
}

