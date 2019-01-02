package com.sykj.shenfu.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.sykj.shenfu.common.Base64;
import com.sykj.shenfu.common.DeleteDirectory;
import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.ICellextendDao;
import com.sykj.shenfu.dao.ICellpaylogDao;
import com.sykj.shenfu.dao.ICellstationDao;
import com.sykj.shenfu.dao.IEmployeeDao;
import com.sykj.shenfu.dao.IHttpForMpsDao;
import com.sykj.shenfu.dao.IProductDao;
import com.sykj.shenfu.dao.IProductmodelDao;
import com.sykj.shenfu.dao.IProductmodelrefDao;
import com.sykj.shenfu.dao.ISalegaininfoDao;
import com.sykj.shenfu.dao.ISalegainruleDao;
import com.sykj.shenfu.dao.IUserDao;
import com.sykj.shenfu.dao.IUserbusinessDao;
import com.sykj.shenfu.dao.IUsercontractDao;
import com.sykj.shenfu.dao.IUserdeliveryDao;
import com.sykj.shenfu.dao.IUserdispatchDao;
import com.sykj.shenfu.dao.IUserdispatchfileDao;
import com.sykj.shenfu.dao.IUserdoorDao;
import com.sykj.shenfu.dao.IUserdoordataDao;
import com.sykj.shenfu.dao.IUserorderDao;
import com.sykj.shenfu.dao.IUserorderinfoDao;
import com.sykj.shenfu.dao.IUserpaylogDao;
import com.sykj.shenfu.dao.IUserproductDao;
import com.sykj.shenfu.dao.IUsertaskDao;
import com.sykj.shenfu.po.Cellextend;
import com.sykj.shenfu.po.Cellpaylog;
import com.sykj.shenfu.po.Cellstation;
import com.sykj.shenfu.po.Cellstationemployee;
import com.sykj.shenfu.po.Coderule;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Product;
import com.sykj.shenfu.po.Productmodel;
import com.sykj.shenfu.po.Productmodelref;
import com.sykj.shenfu.po.Salegaininfo;
import com.sykj.shenfu.po.Salegainrule;
import com.sykj.shenfu.po.User;
import com.sykj.shenfu.po.Userbusiness;
import com.sykj.shenfu.po.Usercontract;
import com.sykj.shenfu.po.Userdelivery;
import com.sykj.shenfu.po.Userdispatch;
import com.sykj.shenfu.po.Userdispatchfile;
import com.sykj.shenfu.po.Userdoor;
import com.sykj.shenfu.po.Userdoordata;
import com.sykj.shenfu.po.Userorder;
import com.sykj.shenfu.po.Userorderinfo;
import com.sykj.shenfu.po.Userpaylog;
import com.sykj.shenfu.po.Userproduct;
import com.sykj.shenfu.po.Usertask;
import com.sykj.shenfu.service.ICoderuleService;
import com.sykj.shenfu.service.IHttpForMpsService;
import com.sykj.shenfu.service.IOperatorlogService;
import com.sykj.shenfu.service.ISystemparaService;

/**
 * @Title ProductdepotServiceImpl.java
 * @version 1.0 编码规则类
 */
@Service("httpForMpsService")
public class HttpForMpsServiceImpl implements IHttpForMpsService {
	@Autowired
    private DataSourceTransactionManager txManager;
	@Autowired
	private IProductmodelDao productmodelDao;
	@Autowired
	private ICoderuleService coderuleService;
	@Autowired
	private IUsertaskDao taskDao;
	@Autowired
	private IUserorderDao userorderDao;
	@Autowired
	private IUserbusinessDao userbusinessDao;
	@Autowired
	private IUserorderinfoDao userorderinfoDao;
	@Autowired
	private IProductmodelrefDao productmodelrefDao;
	@Autowired
	private IUserproductDao userproductDao;
	@Autowired
	private ISystemparaService systemparaService;
	@Autowired
	private IUsercontractDao usercontractDao;
	@Autowired
	private IProductDao productDao;
	@Autowired
	private IUserdispatchDao userdispatchDao;
	@Autowired
	private IUserdispatchfileDao userdispatchfileDao;
	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IUserdoorDao userdoorDao;
	@Autowired
	private IHttpForMpsDao httpForMpsDao;
	@Autowired
	private ICellextendDao cellextendDao;
	@Autowired
	private IUserpaylogDao userpaylogDao;
	@Autowired
	private ICellpaylogDao cellpaylogDao;
	@Autowired
	private ICellstationDao cellstationDao;
	@Autowired
	private ISalegainruleDao salegainruleDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private IUserdeliveryDao userdeliveryDao;
	@Autowired
	private ISalegaininfoDao salegaininfoDao;
	@Autowired
	private IUserdoordataDao userdoordataDao;
	
	
	/*
     * 特殊的instance变量
     * 注：零长度的byte数组对象创建起来将比任何对象都经济――查看编译后的字节码：
     * 生成零长度的byte[]对象只需3条操作码，而Object lock = new Object()则需要7行操作码。
     */
    private static byte[] lock = new byte[0];   
	
    /**
	 * 客户注册-上门类型-0：公司派人讲解测量
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveUserRegisterForVisitorcode0(JSONObject jsonObj){
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("status", "0");//默认保存失败
		//加锁
		synchronized(lock) {
				
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			try{
				//客户电话
				String phone = Tools.getStr(jsonObj.getString("client_phone"));
				//客户姓名
				String username = Tools.getStr(jsonObj.getString("client_name"));
				//客户地址
				String address = Tools.getStr(jsonObj.getString("client_address"));
				//销售员编号
				String salercode = Tools.getStr(jsonObj.getString("user_workid"));
				//上门类型
				String visittype = Tools.getStr(jsonObj.getString("client_visittype"));
				//门锁信息
				//JSONArray doorinfoArray = jsonObj.getJSONArray("doorinfo");
				
				//操作时间
				String currenttime = Tools.getCurrentTime();
				
				//判断销售员的信息状态
				Employee saler = employeeDao.findByEmployeecodeStr(salercode);
				if(saler == null){
					responseMap.put("status", "0");//提交失败
					responseMap.put("result", "提交失败,销售员不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}else if("0".equals(saler.getStatus())){
					responseMap.put("status", "0");//提交失败
					responseMap.put("result", "提交失败,该销售员已被注销");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//电话号码不能为空
				if(StringUtils.isEmpty(phone)){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "电话号码不能为空");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//查询订户信息
				User user = userDao.findByPhoneStr(phone);
				if(user != null){//客户存在
					if("0".equals(user.getStatus())){
						responseMap.put("status", "0");//
						responseMap.put("result", "客户已经提交过，还未处理，请勿再次提交。如确定需要再次提交，请联系公司客服");//
						//回滚
						txManager.rollback(status);
						return responseMap;
					}else if("1".equals(user.getStatus())){
						responseMap.put("status", "0");//
						responseMap.put("result", "客户正在安装中，请勿再次提交。如确定需要再次提交，请联系公司客服");//
						//回滚
						txManager.rollback(status);
						return responseMap;
					} else {//客户已经安装完成，再次提交，修改客户的销售员编号，上门类型，
						user.setUsername(username);
						user.setAddress(address);
						user.setSalercode(salercode);
						user.setVisittype(visittype);
						user.setSource("0");
						user.setStatus("0");//未处理
						userDao.update(user);//新修改
					}
				}else{//客户不存在，新添加
					user = new User();
					//客户姓名
					user.setUsername(username);
					//客户电话
					user.setPhone(phone);
					//客户地址
					user.setAddress(address);
					//审核时间
					//form.setChecktime(Tools.getStr(jsonObj.getString("client_checktime")));
					//上门类型（0.公司派人讲解和测量，1.销售员亲自讲解和测量; 2-无需讲解，公司派人只需测量）
					user.setVisittype(visittype);
					//销售员编号
					user.setSalercode(salercode);
					//审核状态
					user.setCheckstatus("1");
					user.setStatus("0"); //未处理
					user.setSource("0");
					user.setAddtime(currenttime);
					//客户编号
					String usercode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_usercode);
					user.setUsercode(usercode);
					//保存客户信息
					userDao.save(user);
				}
				
				//增加客户
				Userbusiness userbusinessForUser = new Userbusiness();
				userbusinessForUser.setOperatorcode(salercode);
				userbusinessForUser.setBusinesstypekey("useradd");
				userbusinessForUser.setBusinesstypename("APP客户提交");
				userbusinessForUser.setUsercode(user.getUsercode());
				userbusinessForUser.setTaskcode(null);
				userbusinessForUser.setOrdercode(null);
				userbusinessForUser.setDispatchcode(null);
				userbusinessForUser.setAddtime(currenttime);
				userbusinessForUser.setContent("客户添加;客户电话： "+phone);
				userbusinessForUser.setSource("0");//APP平台操作
				userbusinessDao.save(userbusinessForUser);
				
				//增加操作日记
				String content = "客户提交，电话为:"+phone+";上门类型为："+ user.getVisittypename();
				operatorlogService.saveOperatorlog(content, salercode);
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");//注册成功
				responseMap.put("result", "客户注册成功");//注册成功
				responseMap.put("usercode", user.getUsercode());
				
				return responseMap;
				
			}catch(Exception e){
				e.printStackTrace();
				//回滚
				txManager.rollback(status);
				
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，客户注册失败");//
				return responseMap;
			}
		} 
	}
    
	/**
	 * 客户注册-上门类型-1：销售员亲自讲解测量
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveUserRegisterForVisitorcode1(JSONObject jsonObj){
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("status", "0");//默认保存失败
		//加锁
		synchronized(lock) {
			
			//当前时间
			String currenttime =  Tools.getCurrentTime();
			
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			try{
				
				String phone = Tools.getStr(jsonObj.getString("client_phone"));
				//客户姓名
				String username = Tools.getStr(jsonObj.getString("client_name"));
				//客户地址
				String address = Tools.getStr(jsonObj.getString("client_address"));
				//销售员编号
				String salercode = Tools.getStr(jsonObj.getString("user_workid"));
				//上门类型
				String visittype = Tools.getStr(jsonObj.getString("client_visittype"));
				
				//判断销售员的信息状态
				Employee saler = employeeDao.findByEmployeecodeStr(salercode);
				if(saler == null){
					responseMap.put("status", "0");//提交失败
					responseMap.put("result", "提交失败,销售员不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}else if("0".equals(saler.getStatus())){
					responseMap.put("status", "0");//提交失败
					responseMap.put("result", "提交失败,该销售员已被注销");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//电话号码不能为空
				if(StringUtils.isEmpty(phone)){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "电话号码不能为空");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				User user = userDao.findByPhoneStr(phone);
				if(user != null){//客户存在
					if("0".equals(user.getStatus())){
						responseMap.put("status", "0");//
						responseMap.put("result", "客户已经提交过，还未处理，请勿再次提交。如确定需要再次提交，请联系公司客服");//
						//回滚
						txManager.rollback(status);
						return responseMap;
					}else if("1".equals(user.getStatus())){
						responseMap.put("status", "0");//
						responseMap.put("result", "客户正在安装中，请勿再次提交。如确定需要再次提交，请联系公司客服");//
						//回滚
						txManager.rollback(status);
						return responseMap;
					} else {//客户已经安装完成，再次提交，修改客户的销售员编号，上门类型，
						user.setUsername(username);
						user.setAddress(address);
						user.setSalercode(salercode);
						user.setVisittype(visittype);
						user.setSource("0");
						user.setStatus("1");//将客户状态修改成安装进行中
						userDao.update(user);//修改订户信息
					}
				}else{//客户不存在，新添加
					user = new User();
					//客户姓名
					user.setUsername(username);
					//客户电话
					user.setPhone(phone);
					//客户地址
					user.setAddress(address);
					//审核时间
					//form.setChecktime(Tools.getStr(jsonObj.getString("client_checktime")));
					//上门类型（0.公司派人讲解和测量，1.销售员亲自讲解和测量; 2-无需讲解，公司派人只需测量）
					user.setVisittype(visittype);
					//销售员编号
					user.setSalercode(salercode);
					//审核状态
					user.setCheckstatus("1");
					user.setSource("0");
					user.setStatus("1"); //安装进行中
					user.setAddtime(currenttime);
					//客户编号
					String usercode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_usercode);
					user.setUsercode(usercode);
					//保存客户信息
					userDao.save(user);
				}
				
				//增加客户
				Userbusiness userbusinessForUser = new Userbusiness();
				userbusinessForUser.setOperatorcode(salercode);
				userbusinessForUser.setBusinesstypekey("useradd");
				userbusinessForUser.setBusinesstypename("APP客户提交");
				userbusinessForUser.setUsercode(user.getUsercode());
				userbusinessForUser.setTaskcode(null);
				userbusinessForUser.setOrdercode(null);
				userbusinessForUser.setDispatchcode(null);
				userbusinessForUser.setAddtime(currenttime);
				userbusinessForUser.setContent("客户添加;客户电话： "+phone);
				userbusinessForUser.setSource("0");//APP平台操作
				userbusinessDao.save(userbusinessForUser);
				
				//订单信息
				//产品型号
				String modelcode = Tools.getStr(jsonObj.getString("modelcode"));
				//其他费用
				String otherfee = Tools.getStr(jsonObj.getString("otherfee"));
				//首付金额
				String firstpayment = "200";//默认首付款200
				//产品型号列表
				JSONArray productinfoArray = jsonObj.getJSONArray("productinfo");
				//合同信号列表
				JSONArray contractinfoArray = jsonObj.getJSONArray("contractinfo");
				//门锁信息
				JSONArray doorinfoArray = jsonObj.getJSONArray("doorinfo");
				
				if(StringUtils.isEmpty(modelcode)){//产品型号为空
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "未选择产品型号");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//查询产品型号信息
				Productmodel productmodel = productmodelDao.findByModelcode(modelcode);
				if(productmodel == null ){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "产品型号不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//产品费用
				Integer productfee = productmodel.getPrice();
				
				//总金额=产品费用+其他费用
				Integer totalmoney = productfee + Integer.valueOf(otherfee);
				//订单信息
				Userorder userorder = new Userorder();
				//获取订单号
				String ordercode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_ordercode);
				userorder.setOrdercode("DD"+ordercode);
				
				//订户信息
				userorder.setUsercode(user.getUsercode());
				userorder.setUsername(user.getUsername());
				userorder.setUsersex(user.getUsersex());
				userorder.setPhone(user.getPhone());
				userorder.setAddress(user.getAddress());
				userorder.setSource(user.getSource());
				userorder.setVisittype(user.getVisittype());
				//任务单信息
				userorder.setSalercode(user.getSalercode());
				userorder.setTalkercode(user.getSalercode());
				userorder.setVisitorcode(user.getSalercode());
				userorder.setVisitorstatus("1");//已派任务单
				userorder.setProductfee(productfee);
				userorder.setOtherfee(Integer.valueOf(otherfee));
				userorder.setTotalmoney(totalmoney);//总金额
				userorder.setFirstpayment(Integer.valueOf(firstpayment));//首付金额
				userorder.setFinalpayment(totalmoney-Integer.valueOf(firstpayment));//尾款金额
				userorder.setFirstarrivalflag("0");
				userorder.setFinalarrivalflag("0");
				userorder.setPaytype("0");//默认支付定金
				userorder.setAddtime(currenttime);
				userorder.setOperatetime(userorder.getAddtime());
				userorder.setStatus("0");
				userorder.setFilingflag("0");
				userorder.setProductconfirm("0");
				userorder.setModelcode(productmodel.getModelcode());
				userorder.setModelname(productmodel.getModelname());
				userorder.setDiscountgain(productmodel.getDiscountgain());
				userorder.setFixedgain(productmodel.getFixedgain());
				userorder.setManagergain(productmodel.getManagergain());
				userorder.setManagercode(saler.getManagercode());
				
				//保存订单
				userorderDao.save(userorder);
				
				//增加生成订单业务记录
				Userbusiness userbusinessForOrder = new Userbusiness();
				userbusinessForOrder.setOperatorcode(salercode);
				userbusinessForOrder.setBusinesstypekey("orderadd");
				userbusinessForOrder.setBusinesstypename("订单生成");
				userbusinessForOrder.setUsercode(user.getUsercode());
				userbusinessForOrder.setTaskcode(null);
				userbusinessForOrder.setOrdercode(userorder.getOrdercode());
				userbusinessForOrder.setDispatchcode(null);
				userbusinessForOrder.setAddtime(currenttime);
				userbusinessForOrder.setContent("生成订单;订单号为： "+userorder.getOrdercode());
				userbusinessForOrder.setSource("0");//APP平台操作
				userbusinessDao.save(userbusinessForOrder);
				
				//保存订单详情
				Userorderinfo userorderinfo = new Userorderinfo();
				userorderinfo.setOrdercode(userorder.getOrdercode());
				userorderinfo.setUsercode(userorder.getUsercode());
				userorderinfo.setUsername(userorder.getUsername());
				userorderinfo.setUsersex(userorder.getUsersex());
				userorderinfo.setPhone(userorder.getPhone());
				userorderinfo.setAddress(userorder.getAddress());
				userorderinfo.setSource(userorder.getSource());
				userorderinfo.setVisittype(userorder.getVisittype());
				userorderinfo.setSalercode(userorder.getSalercode());
				userorderinfo.setTalkercode(userorder.getTalkercode());
				userorderinfo.setVisitorcode(userorder.getVisitorcode());
				userorderinfo.setAddtime(currenttime);
				userorderinfo.setPrice(productmodel.getPrice());
				userorderinfo.setSellprice(productmodel.getSellprice());
				userorderinfo.setBuytype("0");//型号整体购买
				userorderinfo.setModelcode(productmodel.getModelcode());
				userorderinfo.setModelname(productmodel.getModelname());
				userorderinfoDao.save(userorderinfo);
				
				//保存任务单号
				Usertask usertask = new Usertask();
				//最大任务单号
				String taskcode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_taskcode);
				usertask.setTaskcode("RW"+taskcode);
				//任务单订户信息
				usertask.setOrdercode(userorder.getOrdercode());
				usertask.setUsercode(user.getUsercode());
				usertask.setUsername(user.getUsername());
				usertask.setUsersex(user.getUsersex());
				usertask.setPhone(user.getPhone());
				usertask.setAddress(user.getAddress());
				usertask.setSource(user.getSource());
				usertask.setVisittype(user.getVisittype());
				usertask.setSalercode(user.getSalercode());
				usertask.setTalkercode(user.getSalercode());//讲解人员为销售员
				usertask.setVisitorcode(user.getSalercode());//上门人员为销售员
				usertask.setOperatorcode("");
				usertask.setVisitstate("0");//访问状态（默认为0，若拒绝安装则改为1）
				usertask.setVisittime(currenttime);//默认已完成上门单上门时间为当前时间
				//默认状态为已处理1
				usertask.setStatus("1");
				usertask.setAddtime(currenttime);
				usertask.setDealdate(currenttime);
				//保存任务单
				taskDao.save(usertask);
				
				//增加业务记录
				Userbusiness userbusinessForTask = new Userbusiness();
				userbusinessForTask.setOperatorcode(salercode);
				userbusinessForTask.setBusinesstypekey("taskadd");
				userbusinessForTask.setBusinesstypename("任务单生成");
				userbusinessForTask.setUsercode(user.getUsercode());
				userbusinessForTask.setTaskcode(usertask.getTaskcode());
				userbusinessForTask.setOrdercode(usertask.getOrdercode());
				userbusinessForTask.setDispatchcode(null);
				userbusinessForTask.setAddtime(currenttime);
				userbusinessForTask.setContent("生成任务单;任务单号为： "+usertask.getTaskcode());
				userbusinessForTask.setSource("0");//APP自动生产
				userbusinessDao.save(userbusinessForTask);
				
				//找到该型号对应的产品类别信息
				if(productinfoArray == null || productinfoArray.length()< 1){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "请选择产品信息");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//找到该型号对于的产品类别
				List<Productmodelref> productmodelreflist = productmodelrefDao.findByModelcode(modelcode);
				if(productmodelreflist == null || productmodelreflist.size() < 1){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "注册失败，该产品型号未设置产品信息");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//保存订户产品信息
				for (Productmodelref productmodelref : productmodelreflist) {
					String typecode_OA = Tools.getStr(productmodelref.getTypecode());
					Userproduct userproduct = new Userproduct();
					userproduct.setUsercode(userorder.getUsercode());
					userproduct.setOrdercode(userorder.getOrdercode());
					userproduct.setModelcode(productmodel.getModelcode());
					userproduct.setModelname(productmodel.getModelname());
					userproduct.setTypecode(productmodelref.getTypecode());
					userproduct.setTypename(productmodelref.getTypename());
					userproduct.setBuytype("0");//型号整体购买
					userproduct.setAddtime(currenttime);
					userproduct.setPrice(Integer.valueOf(0));
					userproduct.setSaleprice(Integer.valueOf(0));
					
					if(StringUtils.isNotEmpty(productmodelref.getProductcode())){ //不为空，表示产品型号中已经设置好了产品编码
						Product product = productDao.findByProductcode(productmodelref.getProductcode());
						userproduct.setProductcode(product.getProductcode());
						userproduct.setProductname(product.getProductname());
						userproduct.setProductunit(product.getProductunit());
						userproduct.setProductsource("0");
					}else{
						userproduct.setProductcode("");
						userproduct.setProductname("");
						userproduct.setProductunit("");
						userproduct.setProductsource("1");
					}
					
					//从传过来的数据中获取该产品类别信息
					for (int i = 0; i < productinfoArray.length(); i++) {
						JSONObject productJsonObject = productinfoArray.getJSONObject(i);
						String typecode_app = productJsonObject.getString("typecode");
						String productcode_app = productJsonObject.getString("productcode");
						if(typecode_OA.equals(typecode_app)){
							if(StringUtils.isNotEmpty(productcode_app)){
								 //查询产品信息
					            Product product = productDao.findByProductcode(productcode_app);
					            if(product == null){
					            	responseMap.put("status", "0");//保存失败
									responseMap.put("result", "注册失败，产品无效，产品编码为"+productcode_app);
									//回滚
									txManager.rollback(status);
									return responseMap;
					            }
								userproduct.setProductcode(product.getProductcode());
								userproduct.setProductname(product.getProductname());
								userproduct.setProductunit(product.getProductunit());
								break;
							}
						}
					}
					//保存订户产品信息
					userproductDao.save(userproduct);
				}
				
				//循环合同信息
				if(contractinfoArray != null && contractinfoArray.length() > 0){
					// 创建临时路径
					String usercontract_path = systemparaService.findSystemParaByCodeStr("upload_file_path");
					//String upload_extend_path = systemparaDao.findByCodeStr("upload_file_path").getValue();
					String folderpath = usercontract_path + File.separatorChar + "usercontract" + File.separatorChar +  userorder.getOrdercode();
					File folder = new File(folderpath);
					if (!folder.exists()) {
						folder.mkdirs();
					}else{
						//先删除该目录
						DeleteDirectory.deleteDir(folder);
						//在重新创建
						folder.mkdirs();
					}
					
					//先清除数据的合同信息
					usercontractDao.deleteByOrdercode(userorder.getOrdercode());
					
					for (int i = 0; i < contractinfoArray.length(); i++) {
						JSONObject contractinfo = contractinfoArray.getJSONObject(i);
						//合同文件-base64编码
						String contractfile = Tools.getStr(contractinfo.getString("contractfile"));
						if (contractfile != null) {
							
							//获取合同号
							String contractcode = "HT"+ coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_contractcode);
							byte[] contractfileBytes = Base64.decodeLines(contractfile);
							//合同编号
							String preservefilename = contractcode + "_"+Tools.getNowRandom() + ".jpg";
							String preservepath = folderpath + File.separatorChar + preservefilename;
							
							// 写入的照片路径
							FileOutputStream fos = new FileOutputStream(preservepath);
							fos.write(contractfileBytes);
							fos.flush();
							fos.close();
							
							//保存合同信息
							Usercontract usercontract = new Usercontract();
							usercontract.setFilename(preservefilename);
							usercontract.setPreservefilename(preservefilename);
							usercontract.setPreserveurl(preservepath);
							usercontract.setUsercode(userorder.getUsercode());
							usercontract.setOrdercode(userorder.getOrdercode());
							usercontract.setContractcode(contractcode);
							usercontract.setAddtime(currenttime);
							//保存合同号
							usercontractDao.save(usercontract);
						}
					}
				}
				
				//循环门锁信息
				if(doorinfoArray != null && doorinfoArray.length() > 0){
					// 创建临时路径
					String usercontract_path = systemparaService.findSystemParaByCodeStr("upload_file_path");
					//String upload_extend_path = systemparaDao.findByCodeStr("upload_file_path").getValue();
					String folderpath = usercontract_path + File.separatorChar + "userdoor" + File.separatorChar +  userorder.getUsercode();
					File folder = new File(folderpath);
					if (!folder.exists()) {
						folder.mkdirs();
					}else{
						//先删除该目录
						DeleteDirectory.deleteDir(folder);
						//在重新创建
						folder.mkdirs();
					}
					
					//先清除数据的门锁图片信息
					userdoorDao.deleteByUsercode(userorder.getUsercode());
					
					for (int i = 0; i < doorinfoArray.length(); i++) {
						JSONObject doorinfo = doorinfoArray.getJSONObject(i);
						//合同文件-base64编码
						String doorfile = Tools.getStr(doorinfo.getString("doorfile"));
						if (doorfile != null) {
								
							byte[] doorfileBytes = Base64.decodeLines(doorfile);
							//合同编号
							String preservefilename = userorder.getUsercode() + "_" + i + "_"+Tools.getNowRandom() + ".jpg";
							String preservepath = folderpath + File.separatorChar + preservefilename;
							
							// 写入的照片路径
							FileOutputStream fos = new FileOutputStream(preservepath);
							fos.write(doorfileBytes);
							fos.flush();
							fos.close();
							
							//保存门锁信息
							Userdoor userdoor = new Userdoor();
							userdoor.setUsercode(userorder.getUsercode());
							userdoor.setOrdercode(userorder.getOrdercode());
							userdoor.setFilename(preservefilename);
							userdoor.setPreservefilename(preservefilename);
							userdoor.setPreserveurl(preservepath);
							userdoor.setAddtime(currenttime);
							userdoor.setAddercode(userorder.getVisitorcode());
							//保存合同号
							userdoorDao.save(userdoor);
						}
					}
				}

				//增加操作日记
				String content = "客户提交，电话为:"+phone+";上门类型为："+ user.getVisittypename();
				operatorlogService.saveOperatorlog(content, salercode);
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");//注册成功
				responseMap.put("result", "客户注册成功");//注册成功
				responseMap.put("usercode", user.getUsercode());
				
				return responseMap;
				
			}catch(Exception e){
				
				e.printStackTrace();
				//回滚
				txManager.rollback(status);
				
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，客户注册失败");//
				return responseMap;
			}
		} 
	}
	
	/**
	 * 客户注册-上门类型-2：已讲解，公司派人只需测量
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveUserRegisterForVisitorcode2(JSONObject jsonObj){
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("status", "0");//默认保存失败
		//加锁
		synchronized(lock) {
			
			//当前时间
			String currenttime =  Tools.getCurrentTime();
			
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			try{
				
				String phone = Tools.getStr(jsonObj.getString("client_phone"));
				//客户姓名
				String username = Tools.getStr(jsonObj.getString("client_name"));
				//客户地址
				String address = Tools.getStr(jsonObj.getString("client_address"));
				//销售员编号
				String salercode = Tools.getStr(jsonObj.getString("user_workid"));
				//上门类型
				String visittype = Tools.getStr(jsonObj.getString("client_visittype"));
				
				//判断销售员的信息状态
				Employee saler = employeeDao.findByEmployeecodeStr(salercode);
				if(saler == null){
					responseMap.put("status", "0");//提交失败
					responseMap.put("result", "提交失败,销售员不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}else if("0".equals(saler.getStatus())){
					responseMap.put("status", "0");//提交失败
					responseMap.put("result", "提交失败,该销售员已被注销");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//电话号码不能为空
				if(StringUtils.isEmpty(phone)){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "电话号码不能为空");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				User user = userDao.findByPhoneStr(phone);
				if(user != null){//客户存在
					if("0".equals(user.getStatus())){
						responseMap.put("status", "0");//
						responseMap.put("result", "客户已经提交过，还未处理，请勿再次提交。如确定需要再次提交，请联系公司客服");//
						//回滚
						txManager.rollback(status);
						return responseMap;
					}else if("1".equals(user.getStatus())){
						responseMap.put("status", "0");//
						responseMap.put("result", "客户正在安装中，请勿再次提交。如确定需要再次提交，请联系公司客服");//
						//回滚
						txManager.rollback(status);
						return responseMap;
					} else {//客户已经安装完成，再次提交，修改客户的销售员编号，上门类型，
						user.setUsername(username);
						user.setAddress(address);
						user.setSalercode(salercode);
						user.setVisittype(visittype);
						user.setSource("0");
						user.setStatus("1");//将客户状态修改成安装进行中
						userDao.update(user);//修改订户信息
					}
				}else{//客户不存在，新添加
					user = new User();
					//客户姓名
					user.setUsername(username);
					//客户电话
					user.setPhone(phone);
					//客户地址
					user.setAddress(address);
					//审核时间
					//form.setChecktime(Tools.getStr(jsonObj.getString("client_checktime")));
					//上门类型（0.公司派人讲解和测量，1.销售员亲自讲解和测量; 2-无需讲解，公司派人只需测量）
					user.setVisittype(visittype);
					//销售员编号
					user.setSalercode(salercode);
					//审核状态
					user.setCheckstatus("1");
					user.setSource("0");
					user.setStatus("1"); //安装进行中
					user.setAddtime(currenttime);
					//客户编号
					String usercode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_usercode);
					user.setUsercode(usercode);
					//保存客户信息
					userDao.save(user);
				}
				
				//增加客户
				Userbusiness userbusinessForUser = new Userbusiness();
				userbusinessForUser.setOperatorcode(salercode);
				userbusinessForUser.setBusinesstypekey("useradd");
				userbusinessForUser.setBusinesstypename("APP客户提交");
				userbusinessForUser.setUsercode(user.getUsercode());
				userbusinessForUser.setTaskcode(null);
				userbusinessForUser.setOrdercode(null);
				userbusinessForUser.setDispatchcode(null);
				userbusinessForUser.setAddtime(currenttime);
				userbusinessForUser.setContent("客户添加;客户电话： "+phone);
				userbusinessForUser.setSource("0");//APP平台操作
				userbusinessDao.save(userbusinessForUser);
				
				//订单信息
				//产品型号
				String modelcode = Tools.getStr(jsonObj.getString("modelcode"));
				//其他费用
				String otherfee = Tools.getStr(jsonObj.getString("otherfee"));
				//首付金额
				String firstpayment = "200";//默认首付款200
				//产品型号列表
				JSONArray productinfoArray = jsonObj.getJSONArray("productinfo");
				//合同信号列表
				JSONArray contractinfoArray = jsonObj.getJSONArray("contractinfo");
				//门锁信息
				JSONArray doorinfoArray = jsonObj.getJSONArray("doorinfo");
				
				if(StringUtils.isEmpty(modelcode)){//产品型号为空
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "未选择产品型号");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//查询产品型号信息
				Productmodel productmodel = productmodelDao.findByModelcode(modelcode);
				if(productmodel == null ){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "产品型号不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//产品费用
				Integer productfee = productmodel.getPrice();
				
				//总金额=产品费用+其他费用
				Integer totalmoney = productfee + Integer.valueOf(otherfee);
				//订单信息
				Userorder userorder = new Userorder();
				//获取订单号
				String ordercode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_ordercode);
				userorder.setOrdercode("DD"+ordercode);
				//订户信息
				userorder.setUsercode(user.getUsercode());
				userorder.setUsername(user.getUsername());
				userorder.setUsersex(user.getUsersex());
				userorder.setPhone(user.getPhone());
				userorder.setAddress(user.getAddress());
				userorder.setSource(user.getSource());
				userorder.setVisittype(user.getVisittype());
				userorder.setSalercode(user.getSalercode());
				userorder.setTalkercode(user.getSalercode());
				userorder.setVisitorcode(null);
				userorder.setVisitorstatus("0"); //未派单
				userorder.setProductfee(productfee);
				userorder.setOtherfee(Integer.valueOf(otherfee));
				userorder.setTotalmoney(totalmoney);//总金额
				userorder.setFirstpayment(Integer.valueOf(firstpayment));//首付金额
				userorder.setFinalpayment(totalmoney-Integer.valueOf(firstpayment));//尾款金额
				userorder.setFirstarrivalflag("0");
				userorder.setFinalarrivalflag("0");
				userorder.setPaytype("0");//默认支付定金
				userorder.setAddtime(currenttime);
				userorder.setOperatetime(userorder.getAddtime());
				userorder.setStatus("0");
				userorder.setFilingflag("0");
				userorder.setProductconfirm("0");
				userorder.setModelcode(productmodel.getModelcode());
				userorder.setModelname(productmodel.getModelname());
				userorder.setDiscountgain(productmodel.getDiscountgain());
				userorder.setFixedgain(productmodel.getFixedgain());
				userorder.setManagergain(productmodel.getManagergain());
				userorder.setManagercode(saler.getManagercode());
				//保存订单
				userorderDao.save(userorder);
				
				//增加生成订单业务记录
				Userbusiness userbusinessForOrder = new Userbusiness();
				userbusinessForOrder.setOperatorcode(salercode);
				userbusinessForOrder.setBusinesstypekey("orderadd");
				userbusinessForOrder.setBusinesstypename("订单生成");
				userbusinessForOrder.setUsercode(user.getUsercode());
				userbusinessForOrder.setTaskcode(null);
				userbusinessForOrder.setOrdercode(userorder.getOrdercode());
				userbusinessForOrder.setDispatchcode(null);
				userbusinessForOrder.setAddtime(currenttime);
				userbusinessForOrder.setContent("生成订单;订单号为： "+userorder.getOrdercode());
				userbusinessForOrder.setSource("0");//APP平台操作
				userbusinessDao.save(userbusinessForOrder);
				
				//保存订单详情
				Userorderinfo userorderinfo = new Userorderinfo();
				userorderinfo.setOrdercode(userorder.getOrdercode());
				userorderinfo.setUsercode(userorder.getUsercode());
				userorderinfo.setUsername(userorder.getUsername());
				userorderinfo.setUsersex(userorder.getUsersex());
				userorderinfo.setPhone(userorder.getPhone());
				userorderinfo.setAddress(userorder.getAddress());
				userorderinfo.setSource(userorder.getSource());
				userorderinfo.setVisittype(userorder.getVisittype());
				userorderinfo.setSalercode(userorder.getSalercode());
				userorderinfo.setTalkercode(userorder.getTalkercode());
				userorderinfo.setVisitorcode(userorder.getVisitorcode());
				userorderinfo.setAddtime(currenttime);
				userorderinfo.setPrice(productmodel.getPrice());
				userorderinfo.setSellprice(productmodel.getSellprice());
				userorderinfo.setBuytype("0");//型号整体购买
				userorderinfo.setModelcode(productmodel.getModelcode());
				userorderinfo.setModelname(productmodel.getModelname());
				userorderinfoDao.save(userorderinfo);
				
				//找到该型号对应的产品类别信息
				if(productinfoArray == null){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "请选择产品信息");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//找到该型号对于的产品类别
				List<Productmodelref> productmodelreflist = productmodelrefDao.findByModelcode(modelcode);
				if(productmodelreflist == null || productmodelreflist.size() < 1){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "注册失败，该产品型号未设置产品信息");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				//保存订户产品信息
				for (Productmodelref productmodelref : productmodelreflist) {
					String typecode_OA = Tools.getStr(productmodelref.getTypecode());
					Userproduct userproduct = new Userproduct();
					userproduct.setUsercode(userorder.getUsercode());
					userproduct.setOrdercode(userorder.getOrdercode());
					userproduct.setModelcode(productmodel.getModelcode());
					userproduct.setModelname(productmodel.getModelname());
					userproduct.setTypecode(productmodelref.getTypecode());
					userproduct.setTypename(productmodelref.getTypename());
					userproduct.setBuytype("0");//型号整体购买
					userproduct.setAddtime(currenttime);
					userproduct.setPrice(Integer.valueOf(0));
					userproduct.setSaleprice(Integer.valueOf(0));
					
					if(StringUtils.isNotEmpty(productmodelref.getProductcode())){ //不为空，表示产品型号中已经设置好了产品编码
						Product product = productDao.findByProductcode(productmodelref.getProductcode());
						userproduct.setProductcode(product.getProductcode());
						userproduct.setProductname(product.getProductname());
						userproduct.setProductunit(product.getProductunit());
						userproduct.setProductsource("0");
					}else{
						userproduct.setProductcode("");
						userproduct.setProductname("");
						userproduct.setProductunit("");
						userproduct.setProductsource("1");
					}
					
					//从传过来的数据中获取该产品类别信息
					for (int i = 0; i < productinfoArray.length(); i++) {
						JSONObject productJsonObject = productinfoArray.getJSONObject(i);
						String typecode_app = productJsonObject.getString("typecode");
						String productcode_app = productJsonObject.getString("productcode");
						if(typecode_OA.equals(typecode_app)){
							if(StringUtils.isNotEmpty(productcode_app)){
								//查询产品信息
					            Product product = productDao.findByProductcode(productcode_app);
					            if(product == null){
					            	responseMap.put("status", "0");//保存失败
									responseMap.put("result", "注册失败，产品无效，产品编码为"+productcode_app);
									//回滚
									txManager.rollback(status);
									return responseMap;
					            }
								userproduct.setProductcode(product.getProductcode());
								userproduct.setProductname(product.getProductname());
								userproduct.setProductunit(product.getProductunit());
								break;
							}
						}
					}
					//保存订户产品信息
					userproductDao.save(userproduct);
				}
				
				//循环合同信息
				if(contractinfoArray != null && contractinfoArray.length() > 0){
					// 创建临时路径
					String usercontract_path = systemparaService.findSystemParaByCodeStr("upload_file_path");
					//String upload_extend_path = systemparaDao.findByCodeStr("upload_file_path").getValue();
					String folderpath = usercontract_path + File.separatorChar + "usercontract" + File.separatorChar +  userorder.getOrdercode();
					File folder = new File(folderpath);
					if (!folder.exists()) {
						folder.mkdirs();
					}else{
						//先删除该目录
						DeleteDirectory.deleteDir(folder);
						//在重新创建
						folder.mkdirs();
					}
					
					//删除旧合同
					usercontractDao.deleteByOrdercode(userorder.getOrdercode());
					
					for (int i = 0; i < contractinfoArray.length(); i++) {
						JSONObject contractinfo = contractinfoArray.getJSONObject(i);
						//合同文件-base64编码
						String contractfile = Tools.getStr(contractinfo.getString("contractfile"));
						if (contractfile != null) {
							
							//获取合同号
							String contractcode = "HT"+ coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_contractcode);
							
							byte[] contractfileBytes = Base64.decodeLines(contractfile);
							//合同编号
							String preservefilename = contractcode + "_" + Tools.getNowRandom() + ".jpg";
							String preservepath = folderpath + File.separatorChar + preservefilename;
							
							// 写入的照片路径
							FileOutputStream fos = new FileOutputStream(preservepath);
							fos.write(contractfileBytes);
							fos.flush();
							fos.close();
							
							//保存合同信息
							Usercontract usercontract = new Usercontract();
							usercontract.setFilename(preservefilename);
							usercontract.setPreservefilename(preservefilename);
							usercontract.setPreserveurl(preservepath);
							usercontract.setUsercode(userorder.getUsercode());
							usercontract.setOrdercode(userorder.getOrdercode());
							usercontract.setContractcode(contractcode);
							usercontract.setAddtime(currenttime);
							//保存合同号
							usercontractDao.save(usercontract);
						}
					}
				}
				
				//循环门锁信息
				if(doorinfoArray != null && doorinfoArray.length() > 0){
					// 创建临时路径
					String usercontract_path = systemparaService.findSystemParaByCodeStr("upload_file_path");
					//String upload_extend_path = systemparaDao.findByCodeStr("upload_file_path").getValue();
					String folderpath = usercontract_path + File.separatorChar + "userdoor" + File.separatorChar +  userorder.getUsercode();
					File folder = new File(folderpath);
					if (!folder.exists()) {
						folder.mkdirs();
					}else{
						//先删除该目录
						DeleteDirectory.deleteDir(folder);
						//在重新创建
						folder.mkdirs();
					}
					
					//删除旧合同
					userdoorDao.deleteByUsercode(userorder.getUsercode());
					
					for (int i = 0; i < doorinfoArray.length(); i++) {
						JSONObject doorinfo = doorinfoArray.getJSONObject(i);
						//合同文件-base64编码
						String doorfile = Tools.getStr(doorinfo.getString("doorfile"));
						if (doorfile != null) {
								
							byte[] doorfileBytes = Base64.decodeLines(doorfile);
							//合同编号
							String preservefilename = userorder.getUsercode() + "_" + i + "_"+Tools.getNowRandom() + ".jpg";
							String preservepath = folderpath + File.separatorChar + preservefilename;
							
							// 写入的照片路径
							FileOutputStream fos = new FileOutputStream(preservepath);
							fos.write(doorfileBytes);
							fos.flush();
							fos.close();
							
							//保存合同信息
							Userdoor userdoor = new Userdoor();
							userdoor.setUsercode(userorder.getUsercode());
							userdoor.setOrdercode(userorder.getOrdercode());
							userdoor.setFilename(preservefilename);
							userdoor.setPreservefilename(preservefilename);
							userdoor.setPreserveurl(preservepath);
							userdoor.setAddtime(currenttime);
							userdoor.setAddercode(userorder.getVisitorcode());
							//保存合同号
							userdoorDao.save(userdoor);
						}
					}
				}
				
				//增加操作日记
				String content = "客户注册，电话为:"+phone+";上门类型为："+ user.getVisittypename();
				operatorlogService.saveOperatorlog(content, salercode);
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");//注册成功
				responseMap.put("result", "客户注册成功");//注册成功
				responseMap.put("usercode", user.getUsercode());
				
				return responseMap;
				
			}catch(Exception e){
				e.printStackTrace();
				
				//回滚
				txManager.rollback(status);
				
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，客户注册失败");//
				return responseMap;
			}
		} 
	}
	
	
    /**
	 * 处理任务生成订单
	 * 
	 * @param String
	 * @return
	 */
    public Map<String, Object> saveTaskdata(JSONObject jsonObj) {
		//加锁
		synchronized(lock) {
				
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			
			Map<String, Object> responseMap = new HashMap<String, Object>();
			try{
				responseMap.put("status", "0");//保存失败
				//任务单号
				String taskcode = Tools.getStr(jsonObj.getString("taskcode"));
				//产品型号
				String modelcode = Tools.getStr(jsonObj.getString("modelcode"));
				//其他费用
				String otherfee = Tools.getStr(jsonObj.getString("otherfee"));
				//首付金额
				String firstpayment = "200";//默认首付款200
				//产品型号列表
				JSONArray productinfoArray = jsonObj.getJSONArray("productinfo");
				//合同信号列表
				JSONArray contractinfoArray = jsonObj.getJSONArray("contractinfo");
				//门锁信息
				JSONArray doorinfoArray = jsonObj.getJSONArray("doorinfo");
				
				if(StringUtils.isEmpty(modelcode)){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，未选择产品型号");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				Productmodel productmodel = productmodelDao.findByModelcode(modelcode);
				if(productmodel == null){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，产品型号不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//任务单信息
				if(StringUtils.isEmpty(taskcode)){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，未上传任务单号");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				Usertask usertask = taskDao.findByTaskcodeStr(taskcode);
				if(usertask == null){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，上传的任务单号不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if("1".equals(usertask.getStatus())){//任务单已经处理完成
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，此任务单已经处理完成");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//产品费用
				Integer productfee = productmodel.getPrice();
				
				//总金额=产品费用+其他费用
				Integer totalmoney = productfee + Integer.valueOf(otherfee);
				//订单信息
				Userorder userorder = new Userorder();
				//获取订单号
				String ordercode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_ordercode);
				userorder.setOrdercode("DD"+ordercode);
				
				String currenttime = Tools.getCurrentTime();
				
				//订户信息
				userorder.setUsercode(usertask.getUsercode());
				userorder.setUsername(usertask.getUsername());
				userorder.setUsersex(usertask.getUsersex());
				userorder.setPhone(usertask.getPhone());
				userorder.setAddress(usertask.getAddress());
				userorder.setSource(usertask.getSource());
				userorder.setVisittype(usertask.getVisittype());
				//任务单信息
				userorder.setSalercode(usertask.getSalercode());
				userorder.setTalkercode(usertask.getTalkercode());
				userorder.setVisitorcode(usertask.getVisitorcode());
				userorder.setVisitorstatus("1");//已派任务单
				userorder.setProductfee(productfee);
				userorder.setOtherfee(Integer.valueOf(otherfee));
				userorder.setTotalmoney(totalmoney);//总金额
				userorder.setFirstpayment(Integer.valueOf(firstpayment));//首付金额
				userorder.setFinalpayment(totalmoney-Integer.valueOf(firstpayment));//尾款金额
				userorder.setFirstarrivalflag("0");
				userorder.setFinalarrivalflag("0");
				userorder.setAddtime(currenttime);
				userorder.setOperatetime(userorder.getAddtime());
				userorder.setStatus("0");
				userorder.setFilingflag("0");
				userorder.setProductconfirm("0");
				userorder.setModelcode(productmodel.getModelcode());
				userorder.setModelname(productmodel.getModelname());
				userorder.setDiscountgain(productmodel.getDiscountgain());
				userorder.setFixedgain(productmodel.getFixedgain());
				userorder.setManagergain(productmodel.getManagergain());
				if(StringUtils.isNotEmpty(usertask.getSalercode())){
					Employee saler = employeeDao.findByEmployeecodeStr(usertask.getSalercode());
					if(saler != null){
						userorder.setManagercode(saler.getManagercode());
					}
				}
				
				//保存订单
				userorderDao.save(userorder);
				
				//增加生成订单业务记录
				Userbusiness userbusinessForOrder = new Userbusiness();
				userbusinessForOrder.setOperatorcode(usertask.getTalkercode());
				userbusinessForOrder.setBusinesstypekey("orderadd");
				userbusinessForOrder.setBusinesstypename("订单生成");
				userbusinessForOrder.setUsercode(usertask.getUsercode());
				userbusinessForOrder.setTaskcode(null);
				userbusinessForOrder.setOrdercode(userorder.getOrdercode());
				userbusinessForOrder.setDispatchcode(null);
				userbusinessForOrder.setAddtime(currenttime);
				userbusinessForOrder.setContent("生成订单;订单号为： "+userorder.getOrdercode());
				userbusinessForOrder.setSource("0");//APP平台操作
				userbusinessDao.save(userbusinessForOrder);
				
				//保存订单详情
				Userorderinfo userorderinfo = new Userorderinfo();
				userorderinfo.setOrdercode(userorder.getOrdercode());
				userorderinfo.setUsercode(userorder.getUsercode());
				userorderinfo.setUsername(userorder.getUsername());
				userorderinfo.setUsersex(userorder.getUsersex());
				userorderinfo.setPhone(userorder.getPhone());
				userorderinfo.setAddress(userorder.getAddress());
				userorderinfo.setSource(userorder.getSource());
				userorderinfo.setVisittype(userorder.getVisittype());
				userorderinfo.setSalercode(userorder.getSalercode());
				userorderinfo.setTalkercode(userorder.getTalkercode());
				userorderinfo.setVisitorcode(userorder.getVisitorcode());
				userorderinfo.setAddtime(currenttime);
				userorderinfo.setPrice(productmodel.getPrice());
				userorderinfo.setSellprice(productmodel.getSellprice());
				userorderinfo.setBuytype("0");//型号整体购买
				userorderinfo.setModelcode(productmodel.getModelcode());
				userorderinfo.setModelname(productmodel.getModelname());
				userorderinfoDao.save(userorderinfo);
				
				//修改任务单状态
				usertask.setOrdercode(userorder.getOrdercode());
				usertask.setStatus("1");
				usertask.setDealdate(currenttime);
				taskDao.update(usertask);
				
				//找到该型号对于的产品类别
				List<Productmodelref> productmodelreflist = productmodelrefDao.findByModelcode(modelcode);
				if(productmodelreflist == null || productmodelreflist.size() < 1){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，该产品型号未设置产品信息");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//保存订户产品信息
				for (Productmodelref productmodelref : productmodelreflist) {
					String typecode_OA = Tools.getStr(productmodelref.getTypecode());
					Userproduct userproduct = new Userproduct();
					userproduct.setUsercode(userorder.getUsercode());
					userproduct.setOrdercode(userorder.getOrdercode());
					userproduct.setModelcode(productmodel.getModelcode());
					userproduct.setModelname(productmodel.getModelname());
					userproduct.setTypecode(productmodelref.getTypecode());
					userproduct.setTypename(productmodelref.getTypename());
					userproduct.setBuytype("0");//型号整体购买
					userproduct.setAddtime(currenttime);
					//从传过来的数据中获取该产品类别信息
					for (int i = 0; i < productinfoArray.length(); i++) {
						JSONObject productJsonObject = productinfoArray.getJSONObject(i);
						String typecode_app = productJsonObject.getString("typecode");
						String productcode_app = productJsonObject.getString("productcode");
						if(typecode_OA.equals(typecode_app)){
							 //查询产品信息
				            Product product = productDao.findByProductcode(productcode_app);
				            if(product == null){
				            	responseMap.put("status", "0");//保存失败
								responseMap.put("result", "注册失败，产品无效，产品编码为"+productcode_app);
								//回滚
								txManager.rollback(status);
								return responseMap;
				            }
							userproduct.setProductcode(product.getProductcode());
							userproduct.setProductname(product.getProductname());
							userproduct.setProductunit(product.getProductunit());
							userproduct.setPrice(Integer.valueOf(0));
							userproduct.setSaleprice(Integer.valueOf(0));
							break;
						}
					}
					//保存订户产品信息
					userproductDao.save(userproduct);
				}
				
				//循环合同信息
				if(contractinfoArray != null && contractinfoArray.length() > 0){
					// 创建临时路径
					String usercontract_path = systemparaService.findSystemParaByCodeStr("upload_file_path");
					//String upload_extend_path = systemparaDao.findByCodeStr("upload_file_path").getValue();
					String folderpath = usercontract_path + File.separatorChar + "usercontract" + File.separatorChar +  userorder.getOrdercode();
					File folder = new File(folderpath);
					if (!folder.exists()) {
						folder.mkdirs();
					}else{
						//先删除该目录
						DeleteDirectory.deleteDir(folder);
						//在重新创建
						folder.mkdirs();
					}
					
					//先清除数据的合同信息
					usercontractDao.deleteByOrdercode(userorder.getOrdercode());
					
					for (int i = 0; i < contractinfoArray.length(); i++) {
						JSONObject contractinfo = contractinfoArray.getJSONObject(i);
						//合同文件-base64编码
						String contractfile = Tools.getStr(contractinfo.getString("contractfile"));
						if (contractfile != null) {
							
							//获取合同号
							String contractcode = "HT"+ coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_contractcode);
							
							byte[] contractfileBytes = Base64.decodeLines(contractfile);
							//合同编号
							String preservefilename = contractcode + "_" + Tools.getNowRandom() + ".jpg";
							String preservepath = folderpath + File.separatorChar + preservefilename;
							
							// 写入的照片路径
							FileOutputStream fos = new FileOutputStream(preservepath);
							fos.write(contractfileBytes);
							fos.flush();
							fos.close();
							
							//保存合同信息
							Usercontract usercontract = new Usercontract();
							usercontract.setFilename(preservefilename);
							usercontract.setPreservefilename(preservefilename);
							usercontract.setPreserveurl(preservepath);
							usercontract.setUsercode(userorder.getUsercode());
							usercontract.setOrdercode(userorder.getOrdercode());
							usercontract.setContractcode(contractcode);
							usercontract.setAddtime(currenttime);
							//保存合同号
							usercontractDao.save(usercontract);
						}
					}
				}
				
				//循环门锁信息
				if(doorinfoArray != null && doorinfoArray.length() > 0){
					
					//首选删除旧的门锁图片信息
					userdoorDao.deleteByUsercode(userorder.getUsercode());
					
					// 创建临时路径
					String usercontract_path = systemparaService.findSystemParaByCodeStr("upload_file_path");
					//String upload_extend_path = systemparaDao.findByCodeStr("upload_file_path").getValue();
					String folderpath = usercontract_path + File.separatorChar + "userdoor" + File.separatorChar +  userorder.getOrdercode();
					File folder = new File(folderpath);
					if (!folder.exists()) {
						folder.mkdirs();
					}else{
						//先删除该目录
						DeleteDirectory.deleteDir(folder);
						//在重新创建
						folder.mkdirs();
					}
					
					for (int i = 0; i < doorinfoArray.length(); i++) {
						JSONObject doorinfo = doorinfoArray.getJSONObject(i);
						//合同文件-base64编码
						String doorfile = Tools.getStr(doorinfo.getString("doorfile"));
						if (doorfile != null) {
								
							byte[] doorfileBytes = Base64.decodeLines(doorfile);
							//合同编号
							String preservefilename = userorder.getOrdercode() + "_" + i + "_"+Tools.getNowRandom() + ".jpg";
							String preservepath = folderpath + File.separatorChar + preservefilename;
							
							// 写入的照片路径
							FileOutputStream fos = new FileOutputStream(preservepath);
							fos.write(doorfileBytes);
							fos.flush();
							fos.close();
							
							//保存门锁信息
							Userdoor userdoor = new Userdoor();
							userdoor.setFilename(preservefilename);
							userdoor.setPreservefilename(preservefilename);
							userdoor.setPreserveurl(preservepath);
							userdoor.setUsercode(userorder.getUsercode());
							userdoor.setOrdercode(userorder.getOrdercode());
							userdoor.setAddtime(currenttime);
							userdoor.setAddercode(userorder.getVisitorcode());
							//保存合同号
							userdoorDao.save(userdoor);
						}
					}
				}
				
				//增加操作日记
				String content = "任务单处理数据，任务单号:"+usertask.getTaskcode()+";处理人编号："+ usertask.getVisitorcode();
				operatorlogService.saveOperatorlog(content, usertask.getVisitorcode());
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");//保存失败
				responseMap.put("result", "上传数据成功");//
				return responseMap;
			}catch(Exception e){
				e.printStackTrace();
				//回滚
				txManager.rollback(status);
				
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，处理任务单失败");//
				return responseMap;
			}
		} 
	}   
    
    /**
	 * 处理工单完成数据
	 * 
	 * @param String
	 * @return
	 */
    public Map<String, Object> saveDispatchdata(JSONObject jsonObj) {
		//加锁
		synchronized(lock) {
				
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			
			Map<String, Object> responseMap = new HashMap<String, Object>();
			try{
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "提交失败");
				//工单号
				String dispatchcode = Tools.getStr(jsonObj.getString("dispatchcode"));
				//处理内容
				String dealresult = Tools.getStr(jsonObj.getString("dealresult"));
				//处理状态
				String dispatchstatus = Tools.getStr(jsonObj.getString("status"));
				//安装完工照片信息
				JSONArray dispatchinfoArray = jsonObj.getJSONArray("dispatchinfo");
				
				//任务单信息
				Userdispatch userdispatch = userdispatchDao.findByDispatchcode(dispatchcode);
				if(userdispatch != null){
					
					String currenttime = Tools.getCurrentTime();
					
					//修改工单回单状态
					userdispatch.setStatus(dispatchstatus);// 设置工单状态为1(已派单)
					userdispatch.setDealdate(currenttime);
					userdispatch.setDealresult(dealresult);
					userdispatchDao.saveReply(userdispatch);
					
					//修改订单状态
					Userorder order = userorderDao.findByOrdercode(userdispatch.getOrdercode());
					if("3".equals(dispatchstatus)){//安装完成，将订单状态修改为安装完成
						//order.setStatus("5");
					}else if("4".equals(dispatchstatus)){//安装失败，将订单状态修改为安装失败
						//order.setStatus("10");
					}
					//order.setOperatetime(Tools.getCurrentTime());
					//userorderDao.updateStatus(order);
					
					//增加业务记录
					Userbusiness userbusiness = new Userbusiness();
					userbusiness.setOperatorcode(userdispatch.getInstallercode());
					userbusiness.setBusinesstypekey("dispatchreply");
					userbusiness.setBusinesstypename("工单已处理");
					userbusiness.setUsercode(order.getUsercode());
					//userbusiness.setTaskcode(order.getTaskcode());
					userbusiness.setOrdercode(order.getOrdercode());
					userbusiness.setDispatchcode(userdispatch.getDispatchcode());
					userbusiness.setAddtime(currenttime);
					userbusiness.setContent("工单已处理:工单号为： "+userdispatch.getDispatchcode());
					userbusiness.setSource("1");//APP平台操作
					userbusinessDao.save(userbusiness);
					
					//循环安装完工照片信息
					if(dispatchinfoArray != null){
						// 创建临时路径
						String usercontract_path = systemparaService.findSystemParaByCodeStr("upload_file_path");
						//String upload_extend_path = systemparaDao.findByCodeStr("upload_file_path").getValue();
						String folderpath = usercontract_path + File.separatorChar + "userdispatch" + File.separatorChar +  userdispatch.getOrdercode()+ File.separatorChar + userdispatch.getDispatchcode() ;
						File folder = new File(folderpath);
						if (!folder.exists()) {
							folder.mkdirs();
						}else{
							//先删除该目录
							DeleteDirectory.deleteDir(folder);
							//在重新创建
							folder.mkdirs();
						}
						
						//首选删除旧的门锁图片信息
						userdispatchfileDao.deleteByDispatchcode(userdispatch.getDispatchcode());
						
						for (int i = 0; i < dispatchinfoArray.length(); i++) {
							JSONObject contractinfo = dispatchinfoArray.getJSONObject(i);
							//合同文件-base64编码
							String contractfile = Tools.getStr(contractinfo.getString("contractfile"));
							if (contractfile != null) {
									
								byte[] contractfileBytes = Base64.decodeLines(contractfile);
								//合同编号
								String preservefilename = userdispatch.getDispatchcode() + "_" + i + "_"+Tools.getNowRandom() + ".jpg";
								String preservepath = folderpath + File.separatorChar + preservefilename;
								
								// 写入的照片路径
								FileOutputStream fos = new FileOutputStream(preservepath);
								fos.write(contractfileBytes);
								fos.flush();
								fos.close();
								
								//保存安装完工照片信息
								Userdispatchfile userdispatchfile = new Userdispatchfile();
								userdispatchfile.setFilename(preservefilename);
								userdispatchfile.setPreservefilename(preservefilename);
								userdispatchfile.setPreserveurl(preservepath);
								userdispatchfile.setUsercode(userdispatch.getUsercode());
								userdispatchfile.setOrdercode(userdispatch.getOrdercode());
								userdispatchfile.setDispatchcode(userdispatch.getDispatchcode());
								userdispatchfile.setAddtime(currenttime);
								//保存安装完工照片信息
								userdispatchfileDao.save(userdispatchfile);
							}
						}
					}else{
						responseMap.put("status", "0");//保存失败
						responseMap.put("result", "没有上传合同");//没有上传合同
						//回滚
						txManager.rollback(status);
						return responseMap;
					}
					
				}else{
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "任务单不存在");//没有上传合同
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//增加操作日记
				String content = "工单已处理，派工单号:"+userdispatch.getDispatchcode()+";处理人编号："+ userdispatch.getInstallercode();
				operatorlogService.saveOperatorlog(content, userdispatch.getInstallercode());
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");//保存失败
				responseMap.put("result", "上传数据成功");//上传合同失败
				return responseMap;
			}catch(Exception e){
				//回滚
				txManager.rollback(status);
				//输出异常
				e.printStackTrace();
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，处理任务单失败");//没有上传合同
				return responseMap;
			}
		} 
	}  
    
 	
 	 // 查询讲解上门单-查询类型-0：只讲解单子
 	public Map<String, Object> findTalkerListInfo(JSONObject jsonObj) {
 		Map<String, Object> responseMap = new HashMap<String, Object>();
 		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
 		responseMap.put("status", "0");
 		//上门员编号
 		String visitorcode =  Tools.getStr(jsonObj.getString("visitorcode"));
 		String talkercode =  Tools.getStr(jsonObj.getString("talkercode"));
 		//请求状态（0-未传数据;1-已传数据）
 		String status =  Tools.getStr(jsonObj.getString("status"));
 		//请求页码
 		String page =  Tools.getStr(jsonObj.getString("page"));
 		//每页显示行数
 		String rows =  Tools.getStr(jsonObj.getString("rows"));
 		
 		Userorder orderform = new Userorder();
 		orderform.setVisitorcode(visitorcode);
 		orderform.setTalkercode(talkercode);
 		orderform.setPage(Integer.valueOf(page));
 		orderform.setRows(Integer.valueOf(rows));
 		orderform.setSort("addtime");
 		orderform.setOrder("desc");
 		
 		List<Userorder> userorderlist = httpForMpsDao.findTalkerListInfo(orderform);
 		
 		for (Userorder order : userorderlist) {
 			HashMap<String, Object> objectMap = new HashMap<String, Object>();
 			objectMap.put("id", order.getId());
 			objectMap.put("ordercode", order.getOrdercode());
 			objectMap.put("usercode", order.getUsercode());
 			objectMap.put("username", order.getUsername());
 			objectMap.put("usersex", order.getUsersex());
 			objectMap.put("phone", order.getPhone());
 			objectMap.put("address", order.getAddress());
 			objectMap.put("source", order.getSource());
 			objectMap.put("salercode", order.getSalercode());
 			if(StringUtils.isNotEmpty(order.getSalercode())){
 				Employee saler = employeeDao.findByEmployeecodeStr(order.getSalercode());
 				if(saler == null){
 					saler = new Employee();
 				}
 				objectMap.put("salername", saler.getEmployeename());
 				objectMap.put("salerphone", saler.getPhone());
 			}else{
 				objectMap.put("salername", "");
 				objectMap.put("salerphone", "");
 			}
 			objectMap.put("visitorcode", order.getVisitorcode());
 			if(StringUtils.isNotEmpty(order.getVisitorcode())){
 				Employee visitor = employeeDao.findByEmployeecodeStr(order.getVisitorcode());
 				if(visitor == null){
 					visitor = new Employee();
 				}
 				objectMap.put("visitorname", visitor.getEmployeename());
 				objectMap.put("visitorphone", visitor.getPhone());
 			}else{
 				objectMap.put("visitorname", "");
 				objectMap.put("visitorphone", "");
 			}
 			
 			objectMap.put("talkercode", order.getTalkercode());
 			if(StringUtils.isNotEmpty(order.getTalkercode())){
 				Employee talker = employeeDao.findByEmployeecodeStr(order.getTalkercode());
 				if(talker == null){
 					talker = new Employee();
 				}
 				objectMap.put("talkername", talker.getEmployeename());
 				objectMap.put("talkerphone", talker.getPhone());
 			}else{
 				objectMap.put("talkername", "");
 				objectMap.put("talkerphone", "");
 			}
 			
 			objectMap.put("intention", "");
 			objectMap.put("addtime", StringUtils.isEmpty(order.getAddtime())?"":Tools.getStr(order.getAddtime()).substring(0, 19));
 			
			objectMap.put("orderstatus", order.getStatus());
			objectMap.put("orderstatusname", order.getStatusname());
			objectMap.put("productfee", order.getProductfee());
			objectMap.put("otherfee", order.getOtherfee());
			objectMap.put("totalmoney", order.getTotalmoney());
			objectMap.put("firstpayment", order.getFirstpayment());
			objectMap.put("firstarrivalflag", order.getFirstarrivalflag());
			objectMap.put("firstarrivalflagname", order.getFirstarrivalflagname());
			objectMap.put("finalpayment", order.getFinalpayment());
			objectMap.put("finalarrivalflag", order.getFinalarrivalflag());
			objectMap.put("finalarrivalflagname", order.getFinalarrivalflagname());
 			
			List<Userorderinfo> userorderinfoList =  userorderinfoDao.findByOrdercode(order.getOrdercode());
			if(userorderinfoList != null && userorderinfoList.size() > 0){
				Userorderinfo userorderinfo = userorderinfoList.get(0);
				objectMap.put("modelcode", userorderinfo.getModelcode());
				objectMap.put("modelname", userorderinfo.getModelname());
			}else{
				objectMap.put("modelcode", "");
				objectMap.put("modelname", "");
			}
 			objectMaplist.add(objectMap);
 		}
 		
 		responseMap.put("status", "1");//返回成功
 		responseMap.put("result", "请求成功");
 		responseMap.put("tasksinfo", objectMaplist);
 		
 		return responseMap;
 	}
    
    // 查询讲解上门单-查询类型-1：只上门单子
 	public Map<String, Object> findVisitorListInfo(JSONObject jsonObj) {
 		Map<String, Object> responseMap = new HashMap<String, Object>();
 		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
 		responseMap.put("status", "0");
 		//上门员编号
 		String visitorcode =  Tools.getStr(jsonObj.getString("visitorcode"));
 		//请求状态（0-未传数据;1-已传数据）
 		String status =  Tools.getStr(jsonObj.getString("status"));
 		//请求页码
 		String page =  Tools.getStr(jsonObj.getString("page"));
 		//每页显示行数
 		String rows =  Tools.getStr(jsonObj.getString("rows"));
 		
 		Usertask taskform = new Usertask();
 		taskform.setVisitorcode(visitorcode);
 		taskform.setStatus(status);
 		taskform.setPage(Integer.valueOf(page));
 		taskform.setRows(Integer.valueOf(rows));
 		if("0".equals(status)){//查询未处理任务单
 			taskform.setSort("addtime");
 		}else if("1".equals(status)){//查询已处理任务单
 			taskform.setSort("dealdate");
 		}
 		
 		List<Usertask> usertasklist = httpForMpsDao.findVisitorListInfo(taskform);
 		
 		for (Usertask usertask : usertasklist) {
 			HashMap<String, Object> objectMap = new HashMap<String, Object>();
 			objectMap.put("id", usertask.getId());
 			objectMap.put("taskcode", usertask.getTaskcode());
 			objectMap.put("usercode", usertask.getUsercode());
 			objectMap.put("username", usertask.getUsername());
 			objectMap.put("usersex", usertask.getUsersex());
 			objectMap.put("phone", usertask.getPhone());
 			objectMap.put("address", usertask.getAddress());
 			objectMap.put("source", usertask.getSource());
 			objectMap.put("salercode", usertask.getSalercode());
 			if(StringUtils.isNotEmpty(usertask.getSalercode())){
 				Employee saler = employeeDao.findByEmployeecodeStr(usertask.getSalercode());
 				if(saler == null){
 					saler = new Employee();
 				}
 				objectMap.put("salername", saler.getEmployeename());
 				objectMap.put("salerphone", saler.getPhone());
 			}else{
 				objectMap.put("salername", "");
 				objectMap.put("salerphone", "");
 			}
 			objectMap.put("visitorcode", usertask.getVisitorcode());
 			if(StringUtils.isNotEmpty(usertask.getVisitorcode())){
 				Employee visitor = employeeDao.findByEmployeecodeStr(usertask.getVisitorcode());
 				if(visitor == null){
 					visitor = new Employee();
 				}
 				objectMap.put("visitorname", visitor.getEmployeename());
 				objectMap.put("visitorphone", visitor.getPhone());
 			}else{
 				objectMap.put("visitorname", "");
 				objectMap.put("visitorphone", "");
 			}
 			objectMap.put("talkercode", usertask.getTalkercode());
 			if(StringUtils.isNotEmpty(usertask.getTalkercode())){
 				Employee talker = employeeDao.findByEmployeecodeStr(usertask.getTalkercode());
 				if(talker == null){
 					talker = new Employee();
 				}
 				objectMap.put("talkername", talker.getEmployeename());
 				objectMap.put("talkerphone", talker.getPhone());
 			}else{
 				objectMap.put("talkername", "");
 				objectMap.put("talkerphone", "");
 			}
 			
 			objectMap.put("intention", usertask.getIntention());
 			objectMap.put("status", usertask.getStatus());
 			objectMap.put("addtime", StringUtils.isEmpty(usertask.getAddtime())?"":Tools.getStr(usertask.getAddtime()).substring(0, 19));
 			objectMap.put("dealdate", StringUtils.isEmpty(usertask.getDealdate())?"":Tools.getStr(usertask.getDealdate()).substring(0, 19));
 			objectMap.put("visittime", StringUtils.isEmpty(usertask.getVisittime())?"":Tools.getStr(usertask.getVisittime()).substring(0, 19));
 			//查询订单状态
 			Userorder userorder = userorderDao.findByOrdercode(usertask.getOrdercode());
 			if(userorder != null){
 				objectMap.put("ordercode", userorder.getOrdercode());
 				objectMap.put("orderstatus", userorder.getStatus());
 				objectMap.put("orderstatusname", userorder.getStatusname());
 				objectMap.put("productfee", userorder.getProductfee());
 				objectMap.put("otherfee", userorder.getOtherfee());
 				objectMap.put("totalmoney", userorder.getTotalmoney());
 				objectMap.put("firstpayment", userorder.getFirstpayment());
 				objectMap.put("firstarrivalflag", userorder.getFirstarrivalflag());
 				objectMap.put("firstarrivalflagname", userorder.getFirstarrivalflagname());
 				objectMap.put("finalpayment", userorder.getFinalpayment());
 				objectMap.put("finalarrivalflag", userorder.getFinalarrivalflag());
 				objectMap.put("finalarrivalflagname", userorder.getFinalarrivalflagname());
 				
 				List<Userorderinfo> userorderinfoList =  userorderinfoDao.findByOrdercode(userorder.getOrdercode());
 				if(userorderinfoList != null && userorderinfoList.size() > 0){
 					Userorderinfo userorderinfo = userorderinfoList.get(0);
 					objectMap.put("modelcode", userorderinfo.getModelcode());
 					objectMap.put("modelname", userorderinfo.getModelname());
 				}else{
 					objectMap.put("modelcode", "");
 					objectMap.put("modelname", "");
 				}
 			}else{
 				objectMap.put("orderstatusname", usertask.getStatusname());
 			}
 			
 			objectMaplist.add(objectMap);
 		}
 		
 		responseMap.put("status", "1");//返回成功
 		responseMap.put("result", "请求成功");
 		responseMap.put("tasksinfo", objectMaplist);
 		
 		return responseMap;
 	}
 	
 	
    // 查询讲解上门单-查询类型-2：讲解和上门为同一个人查询
  	public Map<String, Object> findTalkerAndVisitorListInfo(JSONObject jsonObj) {
  		Map<String, Object> responseMap = new HashMap<String, Object>();
  		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
  		responseMap.put("status", "0");
  		//上门员编号
  		String visitorcode =  Tools.getStr(jsonObj.getString("visitorcode"));
  		String talkercode =  Tools.getStr(jsonObj.getString("talkercode"));
  		//请求状态（0-未传数据;1-已传数据）
  		String status =  Tools.getStr(jsonObj.getString("status"));
  		//请求页码
  		String page =  Tools.getStr(jsonObj.getString("page"));
  		//每页显示行数
  		String rows =  Tools.getStr(jsonObj.getString("rows"));
  		
  		Usertask usertaskform = new Usertask();
  		usertaskform.setVisitorcode(visitorcode);
  		usertaskform.setTalkercode(talkercode);
  		usertaskform.setStatus(status);
  		usertaskform.setPage(Integer.valueOf(page));
  		usertaskform.setRows(Integer.valueOf(rows));
  		if("0".equals(status)){//查询未处理任务单
  			usertaskform.setSort("addtime");
  		}else if("1".equals(status)){//查询已处理任务单
  			usertaskform.setSort("dealdate");
  		}
  		
  		List<Usertask> usertasklist = httpForMpsDao.findTalkerAndVisitorListInfo(usertaskform);
  		
  		for (Usertask usertask : usertasklist) {
  			HashMap<String, Object> objectMap = new HashMap<String, Object>();
  			objectMap.put("id", usertask.getId());
  			objectMap.put("taskcode", usertask.getTaskcode());
  			objectMap.put("usercode", usertask.getUsercode());
  			objectMap.put("username", usertask.getUsername());
  			objectMap.put("usersex", usertask.getUsersex());
  			objectMap.put("phone", usertask.getPhone());
  			objectMap.put("address", usertask.getAddress());
  			objectMap.put("source", usertask.getSource());
  			objectMap.put("salercode", usertask.getSalercode());
  			if(StringUtils.isNotEmpty(usertask.getSalercode())){
  				Employee saler = employeeDao.findByEmployeecodeStr(usertask.getSalercode());
  				if(saler == null){
  					saler = new Employee();
  				}
  				objectMap.put("salername", saler.getEmployeename());
  				objectMap.put("salerphone", saler.getPhone());
  			}else{
  				objectMap.put("salername", "");
  				objectMap.put("salerphone", "");
  			}
  			objectMap.put("visitorcode", usertask.getVisitorcode());
  			if(StringUtils.isNotEmpty(usertask.getVisitorcode())){
  				Employee visitor = employeeDao.findByEmployeecodeStr(usertask.getVisitorcode());
  				if(visitor == null){
  					visitor = new Employee();
  				}
  				objectMap.put("visitorname", visitor.getEmployeename());
  				objectMap.put("visitorphone", visitor.getPhone());
  			}else{
  				objectMap.put("visitorname", "");
  				objectMap.put("visitorphone", "");
  			}
  			
  			objectMap.put("intention", usertask.getIntention());
  			objectMap.put("status", usertask.getStatus());
  			objectMap.put("addtime", StringUtils.isEmpty(usertask.getAddtime())?"":Tools.getStr(usertask.getAddtime()).substring(0, 19));
  			objectMap.put("dealdate", StringUtils.isEmpty(usertask.getDealdate())?"":Tools.getStr(usertask.getDealdate()).substring(0, 19));
  			objectMap.put("visittime", StringUtils.isEmpty(usertask.getVisittime())?"":Tools.getStr(usertask.getVisittime()).substring(0, 19));
  			//查询订单状态
  			Userorder userorder = userorderDao.findByOrdercode(usertask.getOrdercode());
  			if(userorder != null){
  				objectMap.put("ordercode", userorder.getOrdercode());
  				objectMap.put("orderstatus", userorder.getStatus());
  				objectMap.put("orderstatusname", userorder.getStatusname());
  				objectMap.put("productfee", userorder.getProductfee());
  				objectMap.put("otherfee", userorder.getOtherfee());
  				objectMap.put("totalmoney", userorder.getTotalmoney());
  				objectMap.put("firstpayment", userorder.getFirstpayment());
  				objectMap.put("firstarrivalflag", userorder.getFirstarrivalflag());
  				objectMap.put("firstarrivalflagname", userorder.getFirstarrivalflagname());
  				objectMap.put("finalpayment", userorder.getFinalpayment());
  				objectMap.put("finalarrivalflag", userorder.getFinalarrivalflag());
  				objectMap.put("finalarrivalflagname", userorder.getFinalarrivalflagname());
  				
  				List<Userorderinfo> userorderinfoList =  userorderinfoDao.findByOrdercode(userorder.getOrdercode());
 				if(userorderinfoList != null && userorderinfoList.size() > 0){
 					Userorderinfo userorderinfo = userorderinfoList.get(0);
 					objectMap.put("modelcode", userorderinfo.getModelcode());
 					objectMap.put("modelname", userorderinfo.getModelname());
 				}else{
 					objectMap.put("modelcode", "");
 					objectMap.put("modelname", "");
 				}
  			}else{
  				objectMap.put("orderstatusname", usertask.getStatusname());
  			}
  			
  			objectMaplist.add(objectMap);
  		}
  		
  		responseMap.put("status", "1");//返回成功
  		responseMap.put("result", "请求成功");
  		responseMap.put("tasksinfo", objectMaplist);
  		
  		return responseMap;
  	}
	
  	/**
	 * 查询客户的订单对应的产品型号以及产品信息
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> findUserproductListInfo(JSONObject jsonObj){
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");//提交失败
		responseMap.put("result", "查询成功");//注册成功
		
		//上门类型
		String ordercode = Tools.getStr(jsonObj.getString("ordercode"));
		if(StringUtils.isEmpty(ordercode)){
			responseMap.put("status", "0");//提交失败
			responseMap.put("result", "查询失败，订单号不能为空");
		}
		
		Userorder userorder = userorderDao.findByOrdercode(ordercode);
		if(userorder == null){
			responseMap.put("status", "0");//提交失败
			responseMap.put("result", "查询失败，该订单号不存在");
		}
		
		
		Userproduct userproductform = new Userproduct();
		userproductform.setOrdercode(ordercode);
		userproductform.setProductsource("1");
		List<Userproduct> userproductlist = userproductDao.queryByList(userproductform);
		
		if(userproductlist == null || userproductlist.size() < 1){
			responseMap.put("status", "0");//提交失败
			responseMap.put("result", "查询失败，该订单号未包含任何产品信息");
		}
		for (Userproduct userproduct : userproductlist) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("typecode", userproduct.getTypecode());
			objectMap.put("typename", userproduct.getTypename());
			List<HashMap<String, Object>> objectproductlist = new ArrayList<HashMap<String, Object>>();
			List<Product> productlist = productDao.findByTypecode(userproduct.getTypecode());
			for (Product product : productlist) {
				HashMap<String, Object> productMap = new HashMap<String, Object>();
				productMap.put("productcode", product.getProductcode());
				productMap.put("productname", product.getProductname());
				productMap.put("imageUrl", product.getImageurl());
				productMap.put("price", product.getPrice());
				if(product.getProductcode().equals(userproduct.getProductcode())){
					productMap.put("salesCount", "1");
				}else{
					productMap.put("salesCount", "0");
				}
				objectproductlist.add(productMap);
			}
			objectMap.put("productList", objectproductlist);
			objectlist.add(objectMap);
		}
		
		responseMap.put("results", objectlist);
		
		return responseMap;
	}
  	
	/**
	 * 只讲解保存任务单信息
	 * 
	 * @param String
	 * @return
	 */
    public Map<String, Object> saveTaskdataForTalker(JSONObject jsonObj) {
		//加锁
		synchronized(lock) {
				
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			
			Map<String, Object> responseMap = new HashMap<String, Object>();
			try{
				responseMap.put("status", "0");//保存失败
				//任务单号
				String ordercode = Tools.getStr(jsonObj.getString("ordercode"));
				//产品型号
				String modelcode = Tools.getStr(jsonObj.getString("modelcode"));
				//其他费用
				//String otherfee = Tools.getStr(jsonObj.getString("otherfee"));
				//首付金额
				//String firstpayment = "200";//默认首付款200
				//产品型号列表
				JSONArray productinfoArray = jsonObj.getJSONArray("productinfo");
				//合同信号列表
				JSONArray contractinfoArray = jsonObj.getJSONArray("contractinfo");
				//门锁信息
				JSONArray doorinfoArray = jsonObj.getJSONArray("doorinfo");
				
				if(StringUtils.isEmpty(modelcode)){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，请上传产品型号编码");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				Productmodel productmodel = productmodelDao.findByModelcode(modelcode);
				if(productmodel == null ){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，上传的产品型号不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//查询订单信息
				if(StringUtils.isEmpty(ordercode)){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，请上传订单编号");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				Userorder userorder = userorderDao.findByOrdercode(ordercode);
				if(userorder == null ){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，上传的订单编号不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(!"0".equals(userorder.getStatus())){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，该订单已经打包，不能修改");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				String currenttime = Tools.getCurrentTime();
				
				//找出数据库里存在的订单产品信息
				List<Userproduct> userproductlist = userproductDao.findUserproductListByOrdercode(ordercode);
				if(userproductlist == null || userproductlist.size() < 1){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，该订单未关联任何产品信息");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//保存订户产品信息
				for (Userproduct userproduct : userproductlist) {
					String typecode_OA = Tools.getStr(userproduct.getTypecode());
					//从传过来的数据中获取该产品类别信息
					for (int i = 0; i < productinfoArray.length(); i++) {
						JSONObject productJsonObject = productinfoArray.getJSONObject(i);
						String typecode_app = productJsonObject.getString("typecode");
						String productcode_app = productJsonObject.getString("productcode");
						if(typecode_OA.equals(typecode_app)){
							if(StringUtils.isNotEmpty(productcode_app)){
								 //查询产品信息
					            Product product = productDao.findByProductcode(productcode_app);
					            if(product == null){
					            	responseMap.put("status", "0");//保存失败
									responseMap.put("result", "注册失败，产品无效，产品编码为"+productcode_app);
									//回滚
									txManager.rollback(status);
									return responseMap;
					            }
								userproduct.setProductcode(product.getProductcode());
								userproduct.setProductname(product.getProductname());
								userproduct.setProductunit(product.getProductunit());
								userproduct.setPrice(Integer.valueOf(0));
								userproduct.setSaleprice(Integer.valueOf(0));
								break;
							}
						}
					}
					//修改订户产品信息
					userproductDao.update(userproduct);
				}
				
				//循环合同信息
				if(contractinfoArray != null && contractinfoArray.length() > 0){
					
					//首选删除旧合同
					usercontractDao.deleteByOrdercode(ordercode);
					
					// 创建临时路径
					String usercontract_path = systemparaService.findSystemParaByCodeStr("upload_file_path");
					//String upload_extend_path = systemparaDao.findByCodeStr("upload_file_path").getValue();
					String folderpath = usercontract_path + File.separatorChar + "usercontract" + File.separatorChar +  userorder.getOrdercode();
					File folder = new File(folderpath);
					if (!folder.exists()) {
						folder.mkdirs();
					}else{
						//先删除该目录
						DeleteDirectory.deleteDir(folder);
						//在重新创建
						folder.mkdirs();
					}
					
					//首选删除旧的合同信息
					usercontractDao.deleteByOrdercode(userorder.getOrdercode());
					
					for (int i = 0; i < contractinfoArray.length(); i++) {
						JSONObject contractinfo = contractinfoArray.getJSONObject(i);
						//合同文件-base64编码
						String contractfile = Tools.getStr(contractinfo.getString("contractfile"));
						if (contractfile != null) {
							
							//获取合同号
							String contractcode = "HT"+ coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_contractcode);
							
							byte[] contractfileBytes = Base64.decodeLines(contractfile);
							//合同编号
							String preservefilename = contractcode + "_" + Tools.getNowRandom() + ".jpg";
							String preservepath = folderpath + File.separatorChar + preservefilename;
							
							// 写入的照片路径
							FileOutputStream fos = new FileOutputStream(preservepath);
							fos.write(contractfileBytes);
							fos.flush();
							fos.close();
							
							//保存合同信息
							Usercontract usercontract = new Usercontract();
							usercontract.setFilename(preservefilename);
							usercontract.setPreservefilename(preservefilename);
							usercontract.setPreserveurl(preservepath);
							usercontract.setUsercode(userorder.getUsercode());
							usercontract.setOrdercode(userorder.getOrdercode());
							usercontract.setContractcode(contractcode);
							usercontract.setAddtime(currenttime);
							//保存合同号
							usercontractDao.save(usercontract);
						}
					}
				}
				
				//循环门锁信息
				if(doorinfoArray != null && doorinfoArray.length() > 0){
					
					// 创建临时路径
					String usercontract_path = systemparaService.findSystemParaByCodeStr("upload_file_path");
					//String upload_extend_path = systemparaDao.findByCodeStr("upload_file_path").getValue();
					String folderpath = usercontract_path + File.separatorChar + "userdoor" + File.separatorChar +  userorder.getOrdercode();
					File folder = new File(folderpath);
					if (!folder.exists()) {
						folder.mkdirs();
					}else{
						//先删除该目录
						DeleteDirectory.deleteDir(folder);
						//在重新创建
						folder.mkdirs();
					}
					
					//首选删除旧的门锁图片信息
					userdoorDao.deleteByUsercode(userorder.getUsercode());
					
					for (int i = 0; i < doorinfoArray.length(); i++) {
						JSONObject doorinfo = doorinfoArray.getJSONObject(i);
						//合同文件-base64编码
						String doorfile = Tools.getStr(doorinfo.getString("doorfile"));
						if (doorfile != null) {
								
							byte[] doorfileBytes = Base64.decodeLines(doorfile);
							//合同编号
							String preservefilename = userorder.getOrdercode() + "_" + i + "_"+Tools.getNowRandom() + ".jpg";
							String preservepath = folderpath + File.separatorChar + preservefilename;
							
							// 写入的照片路径
							FileOutputStream fos = new FileOutputStream(preservepath);
							fos.write(doorfileBytes);
							fos.flush();
							fos.close();
							
							//保存门锁信息
							Userdoor userdoor = new Userdoor();
							userdoor.setFilename(preservefilename);
							userdoor.setPreservefilename(preservefilename);
							userdoor.setPreserveurl(preservepath);
							userdoor.setUsercode(userorder.getUsercode());
							userdoor.setOrdercode(userorder.getOrdercode());
							userdoor.setAddtime(currenttime);
							userdoor.setAddercode(userorder.getTalkercode());
							//保存合同号
							userdoorDao.save(userdoor);
						}
					}
				}
				
				//增加操作日记
				String content = "只讲解处理任务单，任务单号:"+userorder.getTaskcode()+";讲解员编号："+ userorder.getTalkercode();
				operatorlogService.saveOperatorlog(content, userorder.getTalkercode());
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");//保存失败
				responseMap.put("result", "上传数据成功");//
				return responseMap;
			}catch(Exception e){
				e.printStackTrace();
				//回滚
				txManager.rollback(status);
				
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，处理任务单失败");//
				return responseMap;
			}
		} 
	}
	
	/**
	 * 只测量保存任务单信息
	 * 
	 * @param String
	 * @return
	 */
    public Map<String, Object> saveTaskdataForVisitor(JSONObject jsonObj) {
		//加锁
		synchronized(lock) {
				
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			
			Map<String, Object> responseMap = new HashMap<String, Object>();
			try{
				responseMap.put("status", "0");//保存失败
				//任务单号
				String taskcode = Tools.getStr(jsonObj.getString("taskcode"));
				//产品型号
				String modelcode = Tools.getStr(jsonObj.getString("modelcode"));
				//其他费用
				//String otherfee = Tools.getStr(jsonObj.getString("otherfee"));
				//首付金额
				//String firstpayment = "200";//默认首付款200
				//产品型号列表
				JSONArray productinfoArray = jsonObj.getJSONArray("productinfo");
				//合同信号列表
				JSONArray contractinfoArray = jsonObj.getJSONArray("contractinfo");
				//门锁信息
				JSONArray doorinfoArray = jsonObj.getJSONArray("doorinfo");
				
				if(StringUtils.isEmpty(modelcode)){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，请上传产品型号编码");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				Productmodel productmodel = productmodelDao.findByModelcode(modelcode);
				if(productmodel == null ){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，上传的产品型号不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				String currenttime = Tools.getCurrentTime();
				
				//查询订单信息
				if(StringUtils.isEmpty(taskcode)){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，请上传任务单编号");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				Usertask usertask = taskDao.findByTaskcodeStr(taskcode);
				if(usertask ==null){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，上传的任务单不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//修改任务单状态
				usertask.setStatus("1");
				usertask.setDealdate(currenttime);
				taskDao.update(usertask);
				
				//获取订单编号
				String ordercode = Tools.getStr(usertask.getOrdercode());
				if(StringUtils.isEmpty(ordercode)){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，上传的任务单没有关联订单信息");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				Userorder userorder = userorderDao.findByOrdercode(ordercode);
				if(userorder == null){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，上传的任务单关联的订单号不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(!"0".equals(userorder.getStatus())){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，该订单已经打包，不能修改");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//找出数据库里存在的订单产品信息
				//找出数据库里存在的订单产品信息
				List<Userproduct> userproductlist = userproductDao.findUserproductListByOrdercode(ordercode);
				if(userproductlist == null || userproductlist.size() < 1){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，该订单未关联任何产品信息");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//保存订户产品信息
				for (Userproduct userproduct : userproductlist) {
					String typecode_OA = Tools.getStr(userproduct.getTypecode());
					//从传过来的数据中获取该产品类别信息
					for (int i = 0; i < productinfoArray.length(); i++) {
						JSONObject productJsonObject = productinfoArray.getJSONObject(i);
						String typecode_app = productJsonObject.getString("typecode");
						String productcode_app = productJsonObject.getString("productcode");
						if(typecode_OA.equals(typecode_app)){
							if(StringUtils.isNotEmpty(productcode_app)){
								 //查询产品信息
					            Product product = productDao.findByProductcode(productcode_app);
					            if(product == null){
					            	responseMap.put("status", "0");//保存失败
									responseMap.put("result", "注册失败，产品无效，产品编码为"+productcode_app);
									//回滚
									txManager.rollback(status);
									return responseMap;
					            }
								userproduct.setProductcode(product.getProductcode());
								userproduct.setProductname(product.getProductname());
								userproduct.setProductunit(product.getProductunit());
								userproduct.setPrice(Integer.valueOf(0));
								userproduct.setSaleprice(Integer.valueOf(0));
								break;
							}
						}
					}
					//修改订户产品信息
					userproductDao.update(userproduct);
				}
				
				//循环合同信息
				if(contractinfoArray != null && contractinfoArray.length() > 0){
					
					// 创建临时路径
					String usercontract_path = systemparaService.findSystemParaByCodeStr("upload_file_path");
					//String upload_extend_path = systemparaDao.findByCodeStr("upload_file_path").getValue();
					String folderpath = usercontract_path + File.separatorChar + "usercontract" + File.separatorChar +  userorder.getOrdercode();
					File folder = new File(folderpath);
					if (!folder.exists()) {
						folder.mkdirs();
					}else{
						//先删除该目录
						DeleteDirectory.deleteDir(folder);
						//在重新创建
						folder.mkdirs();
					}
					
					//首选删除旧合同
					usercontractDao.deleteByOrdercode(userorder.getOrdercode());
					
					for (int i = 0; i < contractinfoArray.length(); i++) {
						JSONObject contractinfo = contractinfoArray.getJSONObject(i);
						//合同文件-base64编码
						String contractfile = Tools.getStr(contractinfo.getString("contractfile"));
						if (contractfile != null) {
							
							//获取合同号
							String contractcode = "HT"+ coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_contractcode);
							
							byte[] contractfileBytes = Base64.decodeLines(contractfile);
							//合同编号
							String preservefilename = contractcode + "_" + Tools.getNowRandom() + ".jpg";
							String preservepath = folderpath + File.separatorChar + preservefilename;
							
							// 写入的照片路径
							FileOutputStream fos = new FileOutputStream(preservepath);
							fos.write(contractfileBytes);
							fos.flush();
							fos.close();
							
							//保存合同信息
							Usercontract usercontract = new Usercontract();
							usercontract.setFilename(preservefilename);
							usercontract.setPreservefilename(preservefilename);
							usercontract.setPreserveurl(preservepath);
							usercontract.setUsercode(userorder.getUsercode());
							usercontract.setOrdercode(userorder.getOrdercode());
							usercontract.setContractcode(contractcode);
							usercontract.setAddtime(currenttime);
							//保存合同号
							usercontractDao.save(usercontract);
						}
					}
				}
				
				//循环门锁信息
				if(doorinfoArray != null && doorinfoArray.length() > 0){
					
					// 创建临时路径
					String usercontract_path = systemparaService.findSystemParaByCodeStr("upload_file_path");
					//String upload_extend_path = systemparaDao.findByCodeStr("upload_file_path").getValue();
					String folderpath = usercontract_path + File.separatorChar + "userdoor" + File.separatorChar +  userorder.getOrdercode();
					File folder = new File(folderpath);
					if (!folder.exists()) {
						folder.mkdirs();
					}else{
						//先删除该目录
						DeleteDirectory.deleteDir(folder);
						//在重新创建
						folder.mkdirs();
					}
					
					//首选删除旧的门锁图片信息
					userdoorDao.deleteByUsercode(userorder.getUsercode());
					
					for (int i = 0; i < doorinfoArray.length(); i++) {
						JSONObject doorinfo = doorinfoArray.getJSONObject(i);
						//合同文件-base64编码
						String doorfile = Tools.getStr(doorinfo.getString("doorfile"));
						if (doorfile != null) {
								
							byte[] doorfileBytes = Base64.decodeLines(doorfile);
							//合同编号
							String preservefilename = userorder.getOrdercode() + "_" + i + "_"+Tools.getNowRandom() + ".jpg";
							String preservepath = folderpath + File.separatorChar + preservefilename;
							
							// 写入的照片路径
							FileOutputStream fos = new FileOutputStream(preservepath);
							fos.write(doorfileBytes);
							fos.flush();
							fos.close();
							
							//保存门锁信息
							Userdoor userdoor = new Userdoor();
							userdoor.setFilename(preservefilename);
							userdoor.setPreservefilename(preservefilename);
							userdoor.setPreserveurl(preservepath);
							userdoor.setUsercode(userorder.getUsercode());
							userdoor.setOrdercode(userorder.getOrdercode());
							userdoor.setAddtime(currenttime);
							userdoor.setAddercode(userorder.getVisitorcode());
							//保存合同号
							userdoorDao.save(userdoor);
						}
					}
				}
				
				//增加操作日记
				String content = "只测量处理任务单，任务单号:"+userorder.getTaskcode()+";上门人员编号："+ userorder.getVisitorcode();
				operatorlogService.saveOperatorlog(content, userorder.getVisitorcode());
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");//保存失败
				responseMap.put("result", "上传数据成功");//
				return responseMap;
			}catch(Exception e){
				e.printStackTrace();
				//回滚
				txManager.rollback(status);
				
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，处理任务单失败");//
				return responseMap;
			}
		} 
	}  
    
    /**
	 * 讲解和测量保存数据
	 * 
	 * @param String
	 * @return
	 */
    public Map<String, Object> saveTaskdataForTalkerAndVisitor(JSONObject jsonObj) {
		//加锁
		synchronized(lock) {
				
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			
			Map<String, Object> responseMap = new HashMap<String, Object>();
			try{
				responseMap.put("status", "0");//保存失败
				//任务单号
				String taskcode = Tools.getStr(jsonObj.getString("taskcode"));
				//产品型号
				String modelcode = Tools.getStr(jsonObj.getString("modelcode"));
				//其他费用
				String otherfee = Tools.getStr(jsonObj.getString("otherfee"));
				//首付金额
				String firstpayment = "200";//默认首付款200
				//产品型号列表
				JSONArray productinfoArray = jsonObj.getJSONArray("productinfo");
				//合同信号列表
				JSONArray contractinfoArray = jsonObj.getJSONArray("contractinfo");
				//门锁信息
				JSONArray doorinfoArray = jsonObj.getJSONArray("doorinfo");
				
				if(StringUtils.isEmpty(modelcode)){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，未选择产品型号");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				Productmodel productmodel = productmodelDao.findByModelcode(modelcode);
				if(productmodel == null){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，产品型号不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//任务单信息
				if(StringUtils.isEmpty(taskcode)){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，未上传任务单号");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				Usertask usertask = taskDao.findByTaskcodeStr(taskcode);
				if(usertask == null){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，上传的任务单号不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if("1".equals(usertask.getStatus())){//任务单已经处理完成
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，此任务单已经处理完成");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				User user = userDao.findByUsercodeStr(usertask.getUsercode());
				user.setStatus("2");//修改成已生成订单
				userDao.update(user);
				
				//产品费用
				Integer productfee = productmodel.getPrice();
				
				//总金额=产品费用+其他费用
				Integer totalmoney = productfee + Integer.valueOf(otherfee);
				//订单信息
				Userorder userorder = new Userorder();
				//获取订单号
				String ordercode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_ordercode);
				userorder.setOrdercode("DD"+ordercode);
				
				String currenttime = Tools.getCurrentTime();
				
				//订户信息
				userorder.setUsercode(usertask.getUsercode());
				userorder.setUsername(usertask.getUsername());
				userorder.setUsersex(usertask.getUsersex());
				userorder.setPhone(usertask.getPhone());
				userorder.setAddress(usertask.getAddress());
				userorder.setSource(usertask.getSource());
				userorder.setVisittype(usertask.getVisittype());
				//任务单信息
				userorder.setSalercode(usertask.getSalercode());
				userorder.setTalkercode(usertask.getTalkercode());
				userorder.setVisitorcode(usertask.getVisitorcode());
				userorder.setVisitorstatus("1");//已派任务单
				userorder.setProductfee(productfee);
				userorder.setOtherfee(Integer.valueOf(otherfee));
				userorder.setTotalmoney(totalmoney);//总金额
				userorder.setFirstpayment(Integer.valueOf(firstpayment));//首付金额
				userorder.setFinalpayment(totalmoney-Integer.valueOf(firstpayment));//尾款金额
				userorder.setFirstarrivalflag("0");
				userorder.setFinalarrivalflag("0");
				userorder.setPaytype("0");//默认支付定金
				userorder.setAddtime(currenttime);
				userorder.setOperatetime(userorder.getAddtime());
				userorder.setStatus("0");
				userorder.setFilingflag("0");
				userorder.setProductconfirm("0");
				userorder.setModelcode(productmodel.getModelcode());
				userorder.setModelname(productmodel.getModelname());
				userorder.setDiscountgain(productmodel.getDiscountgain());
				userorder.setFixedgain(productmodel.getFixedgain());
				userorder.setManagergain(productmodel.getManagergain());
				if(StringUtils.isNotEmpty(usertask.getSalercode())){
					Employee saler = employeeDao.findByEmployeecodeStr(usertask.getSalercode());
					if(saler != null){
						userorder.setManagercode(saler.getManagercode());
					}
				}
				//保存订单
				userorderDao.save(userorder);
				
				//增加生成订单业务记录
				Userbusiness userbusinessForOrder = new Userbusiness();
				userbusinessForOrder.setOperatorcode(usertask.getTalkercode());
				userbusinessForOrder.setBusinesstypekey("orderadd");
				userbusinessForOrder.setBusinesstypename("订单生成");
				userbusinessForOrder.setUsercode(usertask.getUsercode());
				userbusinessForOrder.setTaskcode(null);
				userbusinessForOrder.setOrdercode(userorder.getOrdercode());
				userbusinessForOrder.setDispatchcode(null);
				userbusinessForOrder.setAddtime(currenttime);
				userbusinessForOrder.setContent("生成订单;订单号为： "+userorder.getOrdercode());
				userbusinessForOrder.setSource("0");//APP平台操作
				userbusinessDao.save(userbusinessForOrder);
				
				//保存订单详情
				Userorderinfo userorderinfo = new Userorderinfo();
				userorderinfo.setOrdercode(userorder.getOrdercode());
				userorderinfo.setUsercode(userorder.getUsercode());
				userorderinfo.setUsername(userorder.getUsername());
				userorderinfo.setUsersex(userorder.getUsersex());
				userorderinfo.setPhone(userorder.getPhone());
				userorderinfo.setAddress(userorder.getAddress());
				userorderinfo.setSource(userorder.getSource());
				userorderinfo.setVisittype(userorder.getVisittype());
				userorderinfo.setSalercode(userorder.getSalercode());
				userorderinfo.setTalkercode(userorder.getTalkercode());
				userorderinfo.setVisitorcode(userorder.getVisitorcode());
				userorderinfo.setAddtime(currenttime);
				userorderinfo.setPrice(productmodel.getPrice());
				userorderinfo.setSellprice(productmodel.getSellprice());
				userorderinfo.setBuytype("0");//型号整体购买
				userorderinfo.setModelcode(productmodel.getModelcode());
				userorderinfo.setModelname(productmodel.getModelname());
				userorderinfoDao.save(userorderinfo);
				
				//修改任务单状态
				usertask.setOrdercode(userorder.getOrdercode());
				usertask.setStatus("1");
				usertask.setDealdate(currenttime);
				taskDao.update(usertask);
				
				//找到该型号对于的产品类别
				List<Productmodelref> productmodelreflist = productmodelrefDao.findByModelcode(modelcode);
				if(productmodelreflist == null || productmodelreflist.size() < 1){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，该产品型号未设置产品信息");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//保存订户产品信息
				for (Productmodelref productmodelref : productmodelreflist) {
					String typecode_OA = Tools.getStr(productmodelref.getTypecode());
					Userproduct userproduct = new Userproduct();
					userproduct.setUsercode(userorder.getUsercode());
					userproduct.setOrdercode(userorder.getOrdercode());
					userproduct.setModelcode(productmodel.getModelcode());
					userproduct.setModelname(productmodel.getModelname());
					userproduct.setTypecode(productmodelref.getTypecode());
					userproduct.setTypename(productmodelref.getTypename());
					userproduct.setBuytype("0");//型号整体购买
					userproduct.setAddtime(currenttime);
					userproduct.setPrice(Integer.valueOf(0));
					userproduct.setSaleprice(Integer.valueOf(0));
					
					if(StringUtils.isNotEmpty(productmodelref.getProductcode())){//不为空，表示产品型号中已经设置好了产品编码
						Product product = productDao.findByProductcode(productmodelref.getProductcode());
						userproduct.setProductcode(product.getProductcode());
						userproduct.setProductname(product.getProductname());
						userproduct.setProductunit(product.getProductunit());
						userproduct.setProductsource("0");
					}else{
						userproduct.setProductcode("");
						userproduct.setProductname("");
						userproduct.setProductunit("");
						userproduct.setProductsource("1");
					}
					
					//从传过来的数据中获取该产品类别信息
					for (int i = 0; i < productinfoArray.length(); i++) {
						JSONObject productJsonObject = productinfoArray.getJSONObject(i);
						String typecode_app = productJsonObject.getString("typecode");
						String productcode_app = productJsonObject.getString("productcode");
						if(typecode_OA.equals(typecode_app)){
							if(StringUtils.isNotEmpty(productcode_app)){
								 //查询产品信息
					            Product product = productDao.findByProductcode(productcode_app);
					            if(product == null){
					            	responseMap.put("status", "0");//保存失败
									responseMap.put("result", "注册失败，产品无效，产品编码为"+productcode_app);
									//回滚
									txManager.rollback(status);
									return responseMap;
					            }
								userproduct.setProductcode(product.getProductcode());
								userproduct.setProductname(product.getProductname());
								userproduct.setProductunit(product.getProductunit());
								userproduct.setPrice(Integer.valueOf(0));
								userproduct.setSaleprice(Integer.valueOf(0));
								break;
							}
						}
					}
					//保存订户产品信息
					userproductDao.save(userproduct);
				}
				
				//循环合同信息
				if(contractinfoArray != null && contractinfoArray.length() > 0){
					// 创建临时路径
					String usercontract_path = systemparaService.findSystemParaByCodeStr("upload_file_path");
					//String upload_extend_path = systemparaDao.findByCodeStr("upload_file_path").getValue();
					String folderpath = usercontract_path + File.separatorChar + "usercontract" + File.separatorChar +  userorder.getOrdercode();
					File folder = new File(folderpath);
					if (!folder.exists()) {
						folder.mkdirs();
					}else{
						//先删除该目录
						DeleteDirectory.deleteDir(folder);
						//在重新创建
						folder.mkdirs();
					}
					
					//首选删除旧的合同信息
					usercontractDao.deleteByOrdercode(userorder.getOrdercode());
					
					for (int i = 0; i < contractinfoArray.length(); i++) {
						JSONObject contractinfo = contractinfoArray.getJSONObject(i);
						//合同文件-base64编码
						String contractfile = Tools.getStr(contractinfo.getString("contractfile"));
						if (contractfile != null) {
							
							//获取合同号
							String contractcode = "HT"+ coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_contractcode);
							
							byte[] contractfileBytes = Base64.decodeLines(contractfile);
							//合同编号
							String preservefilename = contractcode + "_" + Tools.getNowRandom() + ".jpg";
							String preservepath = folderpath + File.separatorChar + preservefilename;
							
							// 写入的照片路径
							FileOutputStream fos = new FileOutputStream(preservepath);
							fos.write(contractfileBytes);
							fos.flush();
							fos.close();
							
							//保存合同信息
							Usercontract usercontract = new Usercontract();
							usercontract.setFilename(preservefilename);
							usercontract.setPreservefilename(preservefilename);
							usercontract.setPreserveurl(preservepath);
							usercontract.setUsercode(userorder.getUsercode());
							usercontract.setOrdercode(userorder.getOrdercode());
							usercontract.setContractcode(contractcode);
							usercontract.setAddtime(currenttime);
							//保存合同号
							usercontractDao.save(usercontract);
						}
					}
				}
				
				//循环门锁信息
				if(doorinfoArray != null && doorinfoArray.length() > 0){
					
					// 创建临时路径
					String usercontract_path = systemparaService.findSystemParaByCodeStr("upload_file_path");
					//String upload_extend_path = systemparaDao.findByCodeStr("upload_file_path").getValue();
					String folderpath = usercontract_path + File.separatorChar + "userdoor" + File.separatorChar +  userorder.getOrdercode();
					File folder = new File(folderpath);
					if (!folder.exists()) {
						folder.mkdirs();
					}else{
						//先删除该目录
						DeleteDirectory.deleteDir(folder);
						//在重新创建
						folder.mkdirs();
					}
					
					//首选删除旧的门锁图片信息
					userdoorDao.deleteByUsercode(userorder.getUsercode());
					
					for (int i = 0; i < doorinfoArray.length(); i++) {
						JSONObject doorinfo = doorinfoArray.getJSONObject(i);
						//合同文件-base64编码
						String doorfile = Tools.getStr(doorinfo.getString("doorfile"));
						if (doorfile != null) {
								
							byte[] doorfileBytes = Base64.decodeLines(doorfile);
							//合同编号
							String preservefilename = userorder.getOrdercode() + "_" + i + "_"+Tools.getNowRandom() + ".jpg";
							String preservepath = folderpath + File.separatorChar + preservefilename;
							
							// 写入的照片路径
							FileOutputStream fos = new FileOutputStream(preservepath);
							fos.write(doorfileBytes);
							fos.flush();
							fos.close();
							
							//保存门锁信息
							Userdoor userdoor = new Userdoor();
							userdoor.setFilename(preservefilename);
							userdoor.setPreservefilename(preservefilename);
							userdoor.setPreserveurl(preservepath);
							userdoor.setUsercode(userorder.getUsercode());
							userdoor.setOrdercode(userorder.getOrdercode());
							userdoor.setAddtime(currenttime);
							userdoor.setAddercode(userorder.getVisitorcode());
							//保存合同号
							userdoorDao.save(userdoor);
						}
					}
				}
				
				//增加操作日记
				String content = "只测量处理任务单，任务单号:"+userorder.getTaskcode()+";上门人员编号："+ userorder.getVisitorcode();
				operatorlogService.saveOperatorlog(content, userorder.getVisitorcode());
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");//保存失败
				responseMap.put("result", "上传数据成功");//
				return responseMap;
			}catch(Exception e){
				e.printStackTrace();
				//回滚
				txManager.rollback(status);
				
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，处理任务单失败");//
				return responseMap;
			}
		} 
	}  
    
    /**
	 * 保存客户支付
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> savePaymentInfo(JSONObject jsonObj){
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("status", "0");//默认保存失败
		//加锁
		synchronized(lock) {
				
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			try{
				String currenttime = Tools.getCurrentTime();
				//订单编号
				String ordercode = Tools.getStr(jsonObj.getString("ordercode"));
				//付款方式(0-现金;1-微信在线;2-支付宝在线；3-微信静态码；4-支付宝静态码)
				String paytype = Tools.getStr(jsonObj.getString("paytype"));
				//付款项目(1-首付款；2-尾款; 3-全款)
				String payitem = Tools.getStr(jsonObj.getString("payitem"));
				//付款金额
				String paymoney = Tools.getStr(jsonObj.getString("paymoney"));
				//付款接收人
				String receivercode = Tools.getStr(jsonObj.getString("receivercode"));
				
				if("0".equals(paytype)){
					responseMap.put("status", "0");
					responseMap.put("result", "支付失败，目前版本不支持现金支付");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if("2".equals(paytype) || "4".equals(paytype)){
					responseMap.put("status", "0");
					responseMap.put("result", "支付失败，目前版本不支持支付宝支付");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//查询订单信息
				Userorder userorder = userorderDao.findByOrdercode(ordercode);
				if(userorder == null){
					responseMap.put("status", "0");//
					responseMap.put("result", "付款失败，订单不存在");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//保存支付信息
				Userpaylog userpaylog = new Userpaylog();
				userpaylog.setUsercode(userorder.getUsercode());
				userpaylog.setUsername(userorder.getUsername());
				userpaylog.setPhone(userorder.getPhone());
				userpaylog.setSource(userorder.getSource());
				userpaylog.setAddress(userorder.getAddress());
				userpaylog.setOrdercode(userorder.getOrdercode());
				userpaylog.setPaytype(paytype);
				userpaylog.setReceivercode(receivercode);
				userpaylog.setPaytime(currenttime);
				//APP端支付
				userpaylog.setPaysource("0");
				
				if(!"0".equals(paytype)){//在线支付直接审核
					userpaylog.setCheckstatus("1");
					userpaylog.setChecktime(Tools.getCurrentTime());
				}else{
					userpaylog.setCheckstatus("0");
				}
				
				if("1".equals(payitem)){//首付款
					if("0".equals(userorder.getFirstarrivalflag())){//订单中首付款未支付
						if(Integer.parseInt(paymoney) == userorder.getFirstpayment()){
							
							if("1".equals(paytype) || "2".equals(paytype) || "3".equals(paytype)|| "4".equals(paytype)){//在线支付直接已审核到账
								userorder.setFirstarrivalflag("2");
							}else{
								userorder.setFirstarrivalflag("1");
							}
							userorderDao.updateFirstarrival(userorder);
							
							//增加首款支付记录
							userpaylog.setPayitem("1");
							userpaylog.setPaymoney(Integer.valueOf(paymoney));
							userpaylogDao.save(userpaylog);
							
						}else{
							responseMap.put("status", "0");//
							responseMap.put("result", "支付失败，支付金额不等于首款金额");//
							//回滚
							txManager.rollback(status);
							return responseMap;
						}
					}else{
						responseMap.put("status", "0");//
						responseMap.put("result", "支付失败，订单中首款已经支付");//
						//回滚
						txManager.rollback(status);
						return responseMap;
					}
				}else if("2".equals(payitem)){//尾款
					if("0".equals(userorder.getFinalarrivalflag())){//订单中尾款未支付
	 					if(Integer.parseInt(paymoney) == userorder.getFinalpayment()){
							
	 						if("1".equals(paytype) || "2".equals(paytype) || "3".equals(paytype)|| "4".equals(paytype)){//在线支付直接已审核到账
	 							userorder.setFinalarrivalflag("2");
							}else{
								userorder.setFinalarrivalflag("1");
							}
							userorderDao.updateFinalarrival(userorder);
							
							//增加尾款支付记录
							userpaylog.setPayitem("2");
							userpaylog.setPaymoney(Integer.valueOf(paymoney));
							userpaylogDao.save(userpaylog);
						}else{
							responseMap.put("status", "0");//
							responseMap.put("result", "支付失败，支付金额不等于尾款金额");//
							//回滚
							txManager.rollback(status);
							return responseMap;
						}
					}else{
						responseMap.put("status", "0");//
						responseMap.put("result", "支付失败，订单中尾款已经支付");//
						//回滚
						txManager.rollback(status);
						return responseMap;
					}
				}else if("3".equals(payitem)){
					if(!"0".equals(userorder.getFirstarrivalflag())){
						responseMap.put("status", "0");//
						responseMap.put("result", "支付失败，订单中首款已经支付");//
						//回滚
						txManager.rollback(status);
						return responseMap;
					}
					
					if(!"0".equals(userorder.getFinalarrivalflag())){
						responseMap.put("status", "0");//
						responseMap.put("result", "支付失败，订单中尾款已经支付");//
						//回滚
						txManager.rollback(status);
						return responseMap;
					}
					
					if(Integer.parseInt(paymoney) == userorder.getTotalmoney()){
						
						//修改首款已支付
						if("1".equals(paytype) || "2".equals(paytype) || "3".equals(paytype)|| "4".equals(paytype)){//在线支付直接已审核到账
								userorder.setFirstarrivalflag("2");
						}else{
							userorder.setFirstarrivalflag("1");
						}
						userorderDao.updateFirstarrival(userorder);
						//增加首款记录
						userpaylog.setPayitem("1");
						userpaylog.setPaymoney(userorder.getFirstpayment());
						userpaylogDao.save(userpaylog);
						
						//修改尾款已支付
						if("1".equals(paytype) || "2".equals(paytype)){//在线支付直接已审核到账
								userorder.setFinalarrivalflag("2");
						}else{
							userorder.setFinalarrivalflag("1");
						}
						userorderDao.updateFinalarrival(userorder);
						//增加尾款记录
						userpaylog.setPayitem("2");
						userpaylog.setPaymoney(userorder.getFinalpayment());
						userpaylogDao.save(userpaylog);
						
					}else{
						responseMap.put("status", "0");//
						responseMap.put("result", "支付失败，支付金额不等于订单总金额");//
						//回滚
						txManager.rollback(status);
						return responseMap;
					}
				}
				
				//增加生成订单业务记录
				Userbusiness userbusinessForUserpay = new Userbusiness();
				userbusinessForUserpay.setOperatorcode(receivercode);
				userbusinessForUserpay.setBusinesstypekey("userpay");
				userbusinessForUserpay.setBusinesstypename("客户支付");
				userbusinessForUserpay.setUsercode(userorder.getUsercode());
				userbusinessForUserpay.setTaskcode(null);
				userbusinessForUserpay.setOrdercode(userorder.getOrdercode());
				userbusinessForUserpay.setDispatchcode(null);
				userbusinessForUserpay.setAddtime(currenttime);
				userbusinessForUserpay.setContent("客户支付，订单号:" + ordercode + " 支付类型:"+userpaylog.getPayitemname()+";支付金额："+ userpaylog.getPaymoney());
				userbusinessForUserpay.setSource("0");//APP平台操作
				userbusinessDao.save(userbusinessForUserpay);
				
				//增加操作日记
				String content = "客户支付，订单号:" + ordercode + " 支付类型:"+userpaylog.getPayitemname()+";支付金额："+ userpaylog.getPaymoney();
				operatorlogService.saveOperatorlog(content, userorder.getVisitorcode());
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");//注册成功
				responseMap.put("result", "支付成功");
				
				return responseMap;
				
			}catch(Exception e){
				e.printStackTrace();
				//回滚
				txManager.rollback(status);
				
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，操作失败");//
				return responseMap;
			}
		} 
	}
    
    /**
	 * 保存拒绝任务单安装
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveRefuseUsertask(JSONObject jsonObj){
		//加锁
		synchronized(lock) {
				
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			
			Map<String, Object> responseMap = new HashMap<String, Object>();
			try{
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "操作失败");
				//拒绝负责人
				String refusercode = Tools.getStr(jsonObj.getString("refusercode"));
				//任务单号
				String taskcode = Tools.getStr(jsonObj.getString("taskcode"));
				//处理内容
				String dealresult = Tools.getStr(jsonObj.getString("dealresult"));
				
				//任务单信息
				if(StringUtils.isEmpty(taskcode)){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "操作失败，任务单号不能为空");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				Usertask usertask = taskDao.findByTaskcodeStr(taskcode);
				if(usertask == null){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "操作失败，此任务单号不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				String currenttime = Tools.getCurrentTime();
				
				if("1".equals(usertask.getStatus())){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "此任务单号已经处理，不能取消");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//修改任务单状态
				usertask.setStatus("10");
				usertask.setDealdate(currenttime);
				usertask.setDealresult(dealresult);
				taskDao.update(usertask);
				
				//增加生成订单业务记录
				Userbusiness userbusinessForTaskrefuse = new Userbusiness();
				userbusinessForTaskrefuse.setOperatorcode(refusercode);
				userbusinessForTaskrefuse.setBusinesstypekey("taskrefuce");
				userbusinessForTaskrefuse.setBusinesstypename("任务单取消");
				userbusinessForTaskrefuse.setUsercode(usertask.getUsercode());
				userbusinessForTaskrefuse.setTaskcode(usertask.getTaskcode());
				userbusinessForTaskrefuse.setOrdercode(null);
				userbusinessForTaskrefuse.setDispatchcode(null);
				userbusinessForTaskrefuse.setAddtime(currenttime);
				userbusinessForTaskrefuse.setContent("任务单取消;任务单号为： "+ usertask.getTaskcode());
				userbusinessForTaskrefuse.setSource("1");//APP平台操作
				userbusinessDao.save(userbusinessForTaskrefuse);
				
				//增加操作日记
				String content = "任务单取消，任务单号:" + taskcode;
				operatorlogService.saveOperatorlog(content, refusercode);
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");//保存失败
				responseMap.put("result", "操作成功");//上传合同失败
				return responseMap;
			}catch(Exception e){
				e.printStackTrace();
				//回滚
				txManager.rollback(status);
				//输出异常
				e.printStackTrace();
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，处理任务单失败");//没有上传合同
				return responseMap;
			}
		} 
	}
    
	/**
	 * 小区抢购
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveAcceptCellextend(JSONObject jsonObj){
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("status", "0");//默认保存失败
		//加锁
		synchronized(lock) {
				
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			try{
				//抢购人
				String salercode = Tools.getStr(jsonObj.getString("salercode"));
				//小区发布编号
				String extendcode = Tools.getStr(jsonObj.getString("extendcode"));
				//操作时间
				String currenttime = Tools.getCurrentTime();
				//判断销售员的信息状态
				Employee saler = employeeDao.findByEmployeecodeStr(salercode);
				if(saler == null){
					responseMap.put("status", "0");//提交失败
					responseMap.put("result", "提交失败,销售员不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}else if("0".equals(saler.getStatus())){
					responseMap.put("status", "0");//提交失败
					responseMap.put("result", "提交失败,该销售员已被注销");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//电话号码不能为空
				if(StringUtils.isEmpty(extendcode)){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "小区发布编号不能为空");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//查询订户信息
				Cellextend cellextend = cellextendDao.findByExtendcode(extendcode);
				
				if(cellextend == null){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "小区发布编号不存在");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if("1".equals(cellextend.getAcceptflag())){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "该小区已经被抢购，不能再次抢购");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				cellextend.setAcceptflag("1");//已接收
				cellextend.setAcceptercode(salercode);//设置接收人
				cellextend.setAcceptertime(currenttime);
				cellextendDao.update(cellextend);
				
				//增加操作日记
				String content = "小区扫楼抢购，小区扫楼编号:" + extendcode + "; 小区名称:"+cellextend.getCellname();
				operatorlogService.saveOperatorlog(content, salercode);
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");//注册成功
				responseMap.put("result", "小区认购成功，请立马支付扫楼费用");
				
				return responseMap;
				
			}catch(Exception e){
				e.printStackTrace();
				//回滚
				txManager.rollback(status);
				
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，操作失败");//
				return responseMap;
			}
		} 
	}
	
	/**
	 * 保存扫楼支付
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveCellpayInfo(JSONObject jsonObj){
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("status", "0");//默认保存失败
		//加锁
		synchronized(lock) {
				
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			try{
				
				//当前时间
				String currenttime = Tools.getCurrentTime();
				
				//小区发布编号
				String extendcode = Tools.getStr(jsonObj.getString("extendcode"));
				//付款方式(0-现金;1-微信在线;2-支付宝在线；3-微信静态码；4-支付宝静态码)
				String paytype = Tools.getStr(jsonObj.getString("paytype"));
				//付款金额
				String paymoney = Tools.getStr(jsonObj.getString("paymoney"));
				//付款人
				String payercode = Tools.getStr(jsonObj.getString("payercode"));
				
				if("0".equals(paytype)){
					responseMap.put("status", "0");
					responseMap.put("result", "支付失败，目前版本不支持现金支付");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if("2".equals(paytype) || "4".equals(paytype)){
					responseMap.put("status", "0");
					responseMap.put("result", "支付失败，目前版本不支持支付宝支付");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(StringUtils.isEmpty(extendcode)){
					responseMap.put("status", "0");//
					responseMap.put("result", "付款失败，请上传小区发布编号");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//查询订单信息
				Cellextend cellextend = cellextendDao.findByExtendcode(extendcode);
				if(cellextend == null){
					responseMap.put("status", "0");//
					responseMap.put("result", "付款失败，该楼盘未发布扫楼信息");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//保存支付信息
				Cellpaylog cellpaylog = new Cellpaylog();
				cellpaylog.setExtendercode(cellextend.getExtendcode());
				cellpaylog.setCellcode(cellextend.getCellcode());
				cellpaylog.setCellname(cellextend.getCellname());
				cellpaylog.setAddress(cellextend.getAddress());
				cellpaylog.setTotalhouse(cellextend.getTotalhouse());
				cellpaylog.setPrice(cellextend.getPrice());
				cellpaylog.setTotalmoney(cellextend.getTotalmoney());
				cellpaylog.setExtendcode(cellextend.getExtendcode());
				cellpaylog.setExtendtime(cellextend.getExtendtime());
				cellpaylog.setStarttime(cellextend.getStarttime());
				cellpaylog.setEndtime(cellextend.getEndtime());
				cellpaylog.setAcceptercode(cellextend.getAcceptercode());
				cellpaylog.setPayercode(payercode);
				cellpaylog.setPaytime(currenttime);
				cellpaylog.setPaytype(paytype);
				//APP端支付
				cellpaylog.setPaysource("0");
				if(!"0".equals(paytype)){//在线支付直接审核
					cellpaylog.setCheckstatus("1");
					cellpaylog.setChecktime(Tools.getCurrentTime());
				}else{
					cellpaylog.setCheckstatus("0");
				}
				
				if("1".equals(cellextend.getPayflag())){//此小区发布信息已经支付
					responseMap.put("status", "0");//
					responseMap.put("result", "支付失败，此小区扫楼费用已经支付");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(Integer.parseInt(paymoney) == cellextend.getTotalmoney()){
					cellextend.setPayflag("1");//支付成功
					cellextend.setPaytime(currenttime);
					cellextendDao.update(cellextend);
					
					//扫楼支付记录
					cellpaylog.setPaymoney(Integer.valueOf(paymoney));
					cellpaylogDao.save(cellpaylog);
					
				}else{
					responseMap.put("status", "0");//
					responseMap.put("result", "支付失败，支付金额不等于扫楼费用");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//增加操作日记
				String content = "小区扫楼支付，小区扫楼编号:" + extendcode + "; 小区名称:"+cellextend.getCellname() +"; 支付金额："+cellpaylog.getPaymoney();
				operatorlogService.saveOperatorlog(content, payercode);
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");//注册成功
				responseMap.put("result", "支付成功");
				
				return responseMap;
				
			}catch(Exception e){
				e.printStackTrace();
				//回滚
				txManager.rollback(status);
				
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，操作失败");//
				return responseMap;
			}
		} 
	}
	
	
	/**
	 * 小区驻点
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveCellstation(JSONObject jsonObj){
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("status", "0");//默认保存失败
		//加锁
		synchronized(lock) {
				
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			try{
				//销售人员
				String salercode = Tools.getKeyValueFromJsonObj("salercode", jsonObj);
				//小区发布编号
				String extendcode = Tools.getKeyValueFromJsonObj("extendcode", jsonObj);
				//驻点开始时间
				String starttime = Tools.getKeyValueFromJsonObj("starttime", jsonObj);
				//驻点结束时间
				String endtime = Tools.getKeyValueFromJsonObj("endtime", jsonObj);
				//讲解人员数目
				String talkernumber = Tools.getKeyValueFromJsonObj("talkernumber", jsonObj);
				//测量人员数目
				String visitornumber = Tools.getKeyValueFromJsonObj("visitornumber", jsonObj);
				//操作时间
				String currenttime = Tools.getCurrentTime();
				//判断销售员的信息状态
				Employee saler = employeeDao.findByEmployeecodeStr(salercode);
				if(saler == null){
					responseMap.put("status", "0");//提交失败
					responseMap.put("result", "提交失败,销售员不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}else if("0".equals(saler.getStatus())){
					responseMap.put("status", "0");//提交失败
					responseMap.put("result", "提交失败,该销售员已被注销");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//小区扫楼发布为空
				if(StringUtils.isEmpty(extendcode)){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "提交失败,小区发布编号不能为空");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//查询订户信息
				Cellextend cellextend = cellextendDao.findByExtendcode(extendcode);
				
				if(cellextend == null){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "提交失败,小区发布编号不存在");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if("0".equals(cellextend.getPayflag())){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "提交失败,该小区扫楼还未支付，请先支付在申请驻点");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if("1".equals(cellextend.getStationflag())){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "提交失败,该小区发布已经申请驻点了");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//小区驻点信息开始时间
				if(StringUtils.isEmpty(starttime)){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "提交失败,小区开始驻点时间不能为空");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//小区驻点信息开始时间
				if(StringUtils.isEmpty(endtime)){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "提交失败,小区结束驻点时间不能为空");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(endtime.compareTo(starttime) < 0){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "提交失败,小区驻点结束时间不能早于开始时间");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				cellextend.setStationflag("1"); //申请已驻点
				cellextendDao.update(cellextend);
				
				//保存驻点单
				Cellstation cellstation = new Cellstation();
				//获取小区驻点点编号
				String cellstationcode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_cellstationcode);
				cellstation.setStationcode("ZD"+cellstationcode);
				cellstation.setExtendcode(cellextend.getExtendcode());
				cellstation.setCellcode(cellextend.getCellcode());
				cellstation.setCellname(cellextend.getCellname());
				cellstation.setAddress(cellextend.getAddress());
				cellstation.setTotalhouse(cellextend.getTotalhouse());
				cellstation.setPrice(Integer.valueOf(cellextend.getPrice()));
				cellstation.setTotalmoney(cellextend.getTotalmoney());
				cellstation.setExtendercode(cellextend.getExtendcode());
				cellstation.setExtendtime(cellextend.getExtendtime());
				cellstation.setAcceptercode(cellextend.getAcceptercode());
				cellstation.setAcceptertime(cellextend.getAcceptertime());
				cellstation.setPaytime(cellextend.getPaytime());
				cellstation.setStarttime(starttime);
				cellstation.setEndtime(endtime);
				cellstation.setTalkernumber(Integer.parseInt(talkernumber));
				cellstation.setVisitornumber(Integer.parseInt(visitornumber));
				cellstation.setAddtime(currenttime);
				cellstationDao.save(cellstation);
				
				//增加操作日记
				String content = "小区驻点申请，小区扫楼编号:" + cellextend.getExtendcode() + "; 小区名称:"+cellextend.getCellname() +"; 驻点编号："+cellstation.getStationcode();
				operatorlogService.saveOperatorlog(content, salercode);
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");//注册成功
				responseMap.put("result", "提交成功");
				
				return responseMap;
				
			}catch(Exception e){
				e.printStackTrace();
				//回滚
				txManager.rollback(status);
				
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，操作失败");//
				return responseMap;
			}
		} 
	}
	
	/**
	 * 客户注册-现场驻点
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveUserRegisterForCellstation(JSONObject jsonObj){
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("status", "0");//默认保存失败
		//加锁
		synchronized(lock) {
			
			//当前时间
			String currenttime =  Tools.getCurrentTime();
			
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			try{
				String phone = Tools.getKeyValueFromJsonObj("client_phone",jsonObj);
				//客户姓名
				String username = Tools.getKeyValueFromJsonObj("client_name",jsonObj);
				//客户地址
				String address = Tools.getKeyValueFromJsonObj("client_address",jsonObj);
				//讲解员编号
				String talkercode = Tools.getKeyValueFromJsonObj("talkercode",jsonObj);
				//测量人员编号
				//String visitorcode = Tools.getKeyValueFromJsonObj("visitorcode",jsonObj);
				//上门类型
				String visittype = Tools.getKeyValueFromJsonObj("visittype", jsonObj);
				//产品型号
				String modelcode = Tools.getKeyValueFromJsonObj("modelcode",jsonObj);
				//是否带机械锁芯
				String lockcoreflag = Tools.getStr(jsonObj.getString("lockcoreflag"));
				//产品颜色
				String productcolor = Tools.getStr(jsonObj.getString("productcolor"));
				//门锁信息
				JSONArray doorinfoArray = jsonObj.getJSONArray("doorinfo");
				
				if(StringUtils.isEmpty(visittype)){
					visittype = "1";
				}
				//驻点编号
				String stationcode = Tools.getKeyValueFromJsonObj("stationcode",jsonObj);
				
				//验证小区驻点信息
				if(StringUtils.isEmpty(stationcode)){//驻点编号不能为空
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "请选择驻点编号信息");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				//查询产品型号信息
				Cellstation cellstation = cellstationDao.findByStationcode(stationcode);
				if(cellstation == null ){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "此小区驻点信息不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//判断讲解员的信息状态
				Employee talker = employeeDao.findByEmployeecodeStr(talkercode);
				if(talker == null){
					responseMap.put("status", "0");//提交失败
					responseMap.put("result", "提交失败,讲解员不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}else if("0".equals(talker.getStatus())){
					responseMap.put("status", "0");//提交失败
					responseMap.put("result", "提交失败,该讲解员已被注销");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//电话号码不能为空
				if(StringUtils.isEmpty(phone)){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "电话号码不能为空");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				User user = userDao.findByPhoneStr(phone);
				if(user != null){//客户存在
					if("0".equals(user.getStatus())){
						responseMap.put("status", "0");//
						responseMap.put("result", "客户已经提交过，还未处理，请勿再次提交。如确定需要再次提交，请联系公司客服");//
						//回滚
						txManager.rollback(status);
						return responseMap;
					}else if("1".equals(user.getStatus()) || "2".equals(user.getStatus())){
						responseMap.put("status", "0");//
						responseMap.put("result", "客户正在安装中，请勿再次提交。如确定需要再次提交，请联系公司客服");//
						//回滚
						txManager.rollback(status);
						return responseMap;
					} else {//客户已经安装完成，再次提交，修改客户的销售员编号，上门类型，
						user.setUsername(username);
						user.setAddress(address);
						user.setSalercode(cellstation.getAcceptercode());
						user.setVisittype(visittype);
						user.setSource("2"); //小区驻点
						user.setStatus("2");//已生成订单
						userDao.update(user);//修改订户信息
					}
				}else{//客户不存在，新添加
					user = new User();
					//客户姓名
					user.setUsername(username);
					//客户电话
					user.setPhone(phone);
					//客户地址
					user.setAddress(address);
					//审核时间
					//form.setChecktime(Tools.getStr(jsonObj.getString("client_checktime")));
					//上门类型（0.公司派人讲解和测量，1.销售员亲自讲解和测量; 2-无需讲解，公司派人只需测量）
					user.setVisittype(visittype);
					//销售员编号
					user.setSalercode(cellstation.getAcceptercode());
					//审核状态
					user.setCheckstatus("1");
					user.setSource("2");//小区驻点
					user.setStatus("2"); //已生成订单
					user.setAddtime(currenttime);
					//客户编号
					String usercode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_usercode);
					user.setUsercode(usercode);
					//保存客户信息
					userDao.save(user);
				}
				
				//其他费用
				Integer otherfee = 0;
				if("1".equals(lockcoreflag)){//购买机械锁心
					//查找出系统设置中的扫楼价格，单位为分
					String lockcore_price = systemparaService.findSystemParaByCodeStr("lockcore_price");
					if(StringUtils.isNotEmpty(lockcore_price)){
						otherfee = Integer.parseInt(lockcore_price); //500元的机械锁心费用
					} else {
						otherfee = 500;
					}
				}
				//首付金额
				String firstpayment = "200";//默认首付款200
				//产品型号列表
				//JSONArray productinfoArray = jsonObj.getJSONArray("productinfo");
				//合同信号列表
				//JSONArray contractinfoArray = jsonObj.getJSONArray("contractinfo");
				
				if(StringUtils.isEmpty(productcolor)){//产品颜色为空
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，未选择产品颜色");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if("0".equals(lockcoreflag)){//不带机械锁心
					productcolor = "0" + String.valueOf(Integer.parseInt(productcolor) + 3); //如果是机械锁心，颜色+3，即对应相应的产品编号
				}
				
				Product productcolerObject  = productDao.findByProductcode(productcolor);
				if(productcolerObject == null){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，此颜色的产品外壳不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(StringUtils.isEmpty(modelcode)){//产品型号为空
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "未选择产品型号");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//查询产品型号信息
				Productmodel productmodel = productmodelDao.findByModelcode(modelcode);
				if(productmodel == null ){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "产品型号不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//产品原价
				Integer productfee_old = productmodel.getPrice();
				//获取提成的总金额
				Integer gaintotalmoney = productfee_old + Integer.valueOf(otherfee);
				//产品销售价格
				Integer productfee = productmodel.getSellprice();
				//总金额=产品费用+其他费用
				Integer totalmoney = productfee + Integer.valueOf(otherfee);
				//订单信息
				Userorder userorder = new Userorder();
				//获取订单号
				String ordercode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_ordercode);
				userorder.setOrdercode("DD"+ordercode);
				//订户信息
				userorder.setUsercode(user.getUsercode());
				userorder.setUsername(user.getUsername());
				userorder.setUsersex(user.getUsersex());
				userorder.setPhone(user.getPhone());
				userorder.setAddress(user.getAddress());
				userorder.setSource(user.getSource());
				userorder.setVisittype(user.getVisittype());
				userorder.setSalercode(user.getSalercode());
				userorder.setTalkercode(talkercode);
				userorder.setVisitorcode(null);
				userorder.setVisitorstatus("0"); //已派单
				userorder.setProductfee(productfee);
				userorder.setOtherfee(Integer.valueOf(otherfee));
				userorder.setGaintotalmoney(gaintotalmoney);//总金额
				userorder.setTotalmoney(totalmoney);//总金额
				userorder.setFirstpayment(Integer.valueOf(firstpayment));//首付金额
				userorder.setFinalpayment(totalmoney-Integer.valueOf(firstpayment));//尾款金额
				userorder.setFirstarrivalflag("0");
				userorder.setFinalarrivalflag("0");
				userorder.setAddtime(currenttime);
				userorder.setOperatetime(userorder.getAddtime());
				userorder.setStatus("0");
				userorder.setFilingflag("0");
				userorder.setStationcode(stationcode);
				userorder.setLockcoreflag(lockcoreflag); //是否带机械锁心
				userorder.setProductcolor(productcolor); //产品颜色
				if("1".equals(lockcoreflag)){//带机械锁心，必须上门测量
					userorder.setVisitorflag("1");//需要派人上门测量
				}else{
					userorder.setVisitorflag("0");//不需要派人上门测量
				}
				userorder.setRemark(null);
				userorder.setProductconfirm("0");
				userorder.setModelcode(productmodel.getModelcode());
				userorder.setModelname(productmodel.getModelname());
				userorder.setDiscountgain(productmodel.getDiscountgain());
				userorder.setFixedgain(productmodel.getFixedgain());
				userorder.setManagergain(productmodel.getManagergain());
				if(StringUtils.isNotEmpty(user.getSalercode())){
					Employee saler = employeeDao.findByEmployeecodeStr(user.getSalercode());
					if(saler != null){
						userorder.setManagercode(saler.getManagercode());
					}
				}
				//保存订单
				userorderDao.save(userorder);
				
				//增加生成订单业务记录
				Userbusiness userbusinessForOrder = new Userbusiness();
				userbusinessForOrder.setOperatorcode(talkercode);
				userbusinessForOrder.setBusinesstypekey("orderadd");
				userbusinessForOrder.setBusinesstypename("订单生成");
				userbusinessForOrder.setUsercode(user.getUsercode());
				userbusinessForOrder.setTaskcode(null);
				userbusinessForOrder.setOrdercode(userorder.getOrdercode());
				userbusinessForOrder.setDispatchcode(null);
				userbusinessForOrder.setAddtime(currenttime);
				userbusinessForOrder.setContent("生成订单;订单号为： "+userorder.getOrdercode());
				userbusinessForOrder.setSource(userorder.getSource());//APP平台操作
				userbusinessDao.save(userbusinessForOrder);
				
				//保存订单详情
				Userorderinfo userorderinfo = new Userorderinfo();
				userorderinfo.setOrdercode(userorder.getOrdercode());
				userorderinfo.setUsercode(userorder.getUsercode());
				userorderinfo.setUsername(userorder.getUsername());
				userorderinfo.setUsersex(userorder.getUsersex());
				userorderinfo.setPhone(userorder.getPhone());
				userorderinfo.setAddress(userorder.getAddress());
				userorderinfo.setSource(userorder.getSource());
				userorderinfo.setVisittype(userorder.getVisittype());
				userorderinfo.setSalercode(userorder.getSalercode());
				userorderinfo.setTalkercode(userorder.getTalkercode());
				userorderinfo.setVisitorcode(userorder.getVisitorcode());
				userorderinfo.setAddtime(currenttime);
				userorderinfo.setPrice(productmodel.getPrice());
				userorderinfo.setSellprice(productmodel.getSellprice());
				userorderinfo.setBuytype("0");//型号整体购买
				userorderinfo.setModelcode(productmodel.getModelcode());
				userorderinfo.setModelname(productmodel.getModelname());
				userorderinfoDao.save(userorderinfo);
				
				//保存任务单号
				Usertask usertask = new Usertask();
				//最大任务单号
				String taskcode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_taskcode);
				usertask.setTaskcode("RW"+taskcode);
				//任务单订户信息
				usertask.setOrdercode(userorder.getOrdercode());
				usertask.setUsercode(user.getUsercode());
				usertask.setUsername(user.getUsername());
				usertask.setUsersex(user.getUsersex());
				usertask.setPhone(user.getPhone());
				usertask.setAddress(user.getAddress());
				usertask.setSource(user.getSource());
				usertask.setVisittype(user.getVisittype());
				usertask.setSalercode(userorder.getSalercode());
				usertask.setTalkercode(userorder.getTalkercode());
				usertask.setVisitorcode(null);
				usertask.setOperatorcode("");
				usertask.setVisitstate("0");//访问状态（默认为0，若拒绝安装则改为1）
				usertask.setVisittime(currenttime);//默认已完成上门单上门时间为当前时间
				//默认状态为已处理1
				usertask.setStatus("1");//已处理
				usertask.setAddtime(currenttime);
				usertask.setDealdate(currenttime);
				usertask.setTasktype("0");//讲解单
				//保存任务单
				taskDao.save(usertask);
				
				//增加业务记录
				Userbusiness userbusinessForTask = new Userbusiness();
				userbusinessForTask.setOperatorcode(userorder.getTalkercode());
				userbusinessForTask.setBusinesstypekey("taskadd");
				userbusinessForTask.setBusinesstypename("任务单生成");
				userbusinessForTask.setUsercode(user.getUsercode());
				userbusinessForTask.setTaskcode(usertask.getTaskcode());
				userbusinessForTask.setOrdercode(usertask.getOrdercode());
				userbusinessForTask.setDispatchcode(null);
				userbusinessForTask.setAddtime(currenttime);
				userbusinessForTask.setContent("生成任务单;任务单号为： "+usertask.getTaskcode());
				userbusinessForTask.setSource(user.getSource());//APP自动生产
				userbusinessDao.save(userbusinessForTask);
				
				//找到该型号对于的产品类别
				List<Productmodelref> productmodelreflist = productmodelrefDao.findByModelcode(modelcode);
				if(productmodelreflist == null || productmodelreflist.size() < 1){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "注册失败，该产品型号未设置产品信息");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				//保存订户产品信息
				for (Productmodelref productmodelref : productmodelreflist) {
					String typecode_OA = Tools.getStr(productmodelref.getTypecode());
					Userproduct userproduct = new Userproduct();
					userproduct.setUsercode(userorder.getUsercode());
					userproduct.setOrdercode(userorder.getOrdercode());
					userproduct.setModelcode(productmodel.getModelcode());
					userproduct.setModelname(productmodel.getModelname());
					userproduct.setTypecode(productmodelref.getTypecode());
					userproduct.setTypename(productmodelref.getTypename());
					userproduct.setBuytype("0");//型号整体购买
					userproduct.setAddtime(currenttime);
					userproduct.setPrice(Integer.valueOf(0));
					userproduct.setSaleprice(Integer.valueOf(0));
					
					if(StringUtils.isNotEmpty(productmodelref.getProductcode())){ //不为空，表示产品型号中已经设置好了产品编码
						Product product = productDao.findByProductcode(productmodelref.getProductcode());
						userproduct.setProductcode(product.getProductcode());
						userproduct.setProductname(product.getProductname());
						userproduct.setProductunit(product.getProductunit());
						userproduct.setProductsource("0");
					}else{
						userproduct.setProductcode("");
						userproduct.setProductname("");
						userproduct.setProductunit("");
						userproduct.setProductsource("1");
					}
					
					//修改产品颜色
					if("01".equals(productmodelref.getTypecode())){
						userproduct.setProductcode(productcolor);
						userproduct.setProductname(productcolerObject.getProductname());
						userproduct.setProductunit(productcolerObject.getProductunit());
						userproduct.setProductsource("1");
					}
					
					//从传过来的数据中获取该产品类别信息
//					for (int i = 0; i < productinfoArray.length(); i++) {
//						JSONObject productJsonObject = productinfoArray.getJSONObject(i);
//						String typecode_app = productJsonObject.getString("typecode");
//						String productcode_app = productJsonObject.getString("productcode");
//						if(typecode_OA.equals(typecode_app)){
//							if(StringUtils.isNotEmpty(productcode_app)){
//								//查询产品信息
//					            Product product = productDao.findByProductcode(productcode_app);
//					            if(product == null){
//					            	responseMap.put("status", "0");//保存失败
//									responseMap.put("result", "注册失败，产品无效，产品编码为"+productcode_app);
//									//回滚
//									txManager.rollback(status);
//									return responseMap;
//					            }
//								userproduct.setProductcode(product.getProductcode());
//								userproduct.setProductname(product.getProductname());
//								userproduct.setProductunit(product.getProductunit());
//								break;
//							}
//						}
//					}
					//保存订户产品信息
					userproductDao.save(userproduct);
				}
				
				//循环合同信息
//				if(contractinfoArray != null && contractinfoArray.length() > 0){
//					// 创建临时路径
//					String usercontract_path = systemparaService.findSystemParaByCodeStr("upload_file_path");
//					//String upload_extend_path = systemparaDao.findByCodeStr("upload_file_path").getValue();
//					String folderpath = usercontract_path + File.separatorChar + "usercontract" + File.separatorChar +  userorder.getOrdercode();
//					File folder = new File(folderpath);
//					if (!folder.exists()) {
//						folder.mkdirs();
//					}else{
//						//先删除该目录
//						DeleteDirectory.deleteDir(folder);
//						//在重新创建
//						folder.mkdirs();
//					}
//					
//					//删除旧合同
//					usercontractDao.deleteByOrdercode(userorder.getOrdercode());
					
//					for (int i = 0; i < contractinfoArray.length(); i++) {
//						JSONObject contractinfo = contractinfoArray.getJSONObject(i);
//						//合同文件-base64编码
//						String contractfile = Tools.getStr(contractinfo.getString("contractfile"));
//						if (contractfile != null) {
//							
//							//获取合同号
//							String contractcode = "HT"+ coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_contractcode);
//							
//							byte[] contractfileBytes = Base64.decodeLines(contractfile);
//							//合同编号
//							String preservefilename = contractcode + "_" + Tools.getNowRandom() + ".jpg";
//							String preservepath = folderpath + File.separatorChar + preservefilename;
//							
//							// 写入的照片路径
//							FileOutputStream fos = new FileOutputStream(preservepath);
//							fos.write(contractfileBytes);
//							fos.flush();
//							fos.close();
//							
//							//保存合同信息
//							Usercontract usercontract = new Usercontract();
//							usercontract.setFilename(preservefilename);
//							usercontract.setPreservefilename(preservefilename);
//							usercontract.setPreserveurl(preservepath);
//							usercontract.setUsercode(userorder.getUsercode());
//							usercontract.setOrdercode(userorder.getOrdercode());
//							usercontract.setContractcode(contractcode);
//							usercontract.setAddtime(currenttime);
//							//保存合同号
//							usercontractDao.save(usercontract);
//						}
//					}
//				}
				
				//循环门锁信息
				if(doorinfoArray != null && doorinfoArray.length() > 0){
					// 创建临时路径
					String usercontract_path = systemparaService.findSystemParaByCodeStr("upload_file_path");
					//String upload_extend_path = systemparaDao.findByCodeStr("upload_file_path").getValue();
					String folderpath = usercontract_path + File.separatorChar + "userdoor" + File.separatorChar +  userorder.getUsercode();
					File folder = new File(folderpath);
					if (!folder.exists()) {
						folder.mkdirs();
					}else{
						//先删除该目录
						DeleteDirectory.deleteDir(folder);
						//在重新创建
						folder.mkdirs();
					}
					
					//删除旧合同
					userdoorDao.deleteByUsercode(userorder.getUsercode());
					
					for (int i = 0; i < doorinfoArray.length(); i++) {
						JSONObject doorinfo = doorinfoArray.getJSONObject(i);
						//合同文件-base64编码
						String doorfile = Tools.getStr(doorinfo.getString("doorfile"));
						if (doorfile != null) {
								
							byte[] doorfileBytes = Base64.decodeLines(doorfile);
							//合同编号
							String preservefilename = userorder.getUsercode() + "_" + i + "_"+Tools.getNowRandom() + ".jpg";
							String preservepath = folderpath + File.separatorChar + preservefilename;
							
							// 写入的照片路径
							FileOutputStream fos = new FileOutputStream(preservepath);
							fos.write(doorfileBytes);
							fos.flush();
							fos.close();
							
							//保存合同信息
							Userdoor userdoor = new Userdoor();
							userdoor.setUsercode(userorder.getUsercode());
							userdoor.setOrdercode(userorder.getOrdercode());
							userdoor.setFilename(preservefilename);
							userdoor.setPreservefilename(preservefilename);
							userdoor.setPreserveurl(preservepath);
							userdoor.setAddtime(currenttime);
							userdoor.setAddercode(userorder.getVisitorcode());
							//保存合同号
							userdoorDao.save(userdoor);
						}
					}
				}
				
				//增加操作日记
				String content = "现场驻点提交客户，客户电话:" + user.getPhone();
				operatorlogService.saveOperatorlog(content, talkercode);
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");//注册成功
				responseMap.put("result", "操作成功");//注册成功
				responseMap.put("usercode", user.getUsercode());
				
				return responseMap;
				
			}catch(Exception e){
				e.printStackTrace();
				
				//回滚
				txManager.rollback(status);
				
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，操作失败");//
				return responseMap;
			}
		} 
	}
	
	/**
	 * 查询销售员下级成员信息（团队）
	 * @param String
	 * @return
	 */
	public Map<String, Object> findChildrenSalerInfoListBySalercode(JSONObject jsonObj){
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		
		try{
			//销售员编号
			String salercode = Tools.getKeyValueFromJsonObj("salercode", jsonObj);
			//请求页码
			String page = Tools.getKeyValueFromJsonObj("page", jsonObj);
			//每页显示行数
			String rows = Tools.getKeyValueFromJsonObj("rows", jsonObj);
			
			if(StringUtils.isEmpty(salercode)){
				responseMap.put("status", "0");
				responseMap.put("result", "请求失败,请输入销售员编号");
				return responseMap;
			}
			
			Employee employeeform = new Employee();
			//employeeform.setParentemployeecode(salercode);//赋值上级销售员编号
			employeeform.setEmployeecode(salercode);
			employeeform.setPage(Integer.valueOf(page));
			employeeform.setRows(Integer.valueOf(rows));
			
			List<Employee> childrenSalerInfoList = httpForMpsDao.findChildrenSalerInfoListBySalercode(employeeform);
			
			for (Employee childrenSaler : childrenSalerInfoList) {
				HashMap<String, Object> objectMap = new HashMap<String, Object>();
				objectMap.put("id", childrenSaler.getId());
				objectMap.put("employeecode", childrenSaler.getEmployeecode());
				objectMap.put("employeename", childrenSaler.getEmployeename());
				objectMap.put("phone", Tools.getTransValueFromStr(childrenSaler.getPhone(), 3, 4, "*"));
				objectMap.put("totalmoney", (childrenSaler.getTotalmoney() == null?0:childrenSaler.getTotalmoney()));
				objectMap.put("totalcount", childrenSaler.getTotalcount());
				
				employeeform.setParentemployeecode(childrenSaler.getEmployeecode());//赋值上级销售员编号
				Employee chiledrenemployee = httpForMpsDao.findTotalChildrenSalerInfoListBySalercode(employeeform);
				if(chiledrenemployee == null){
					chiledrenemployee = new Employee();
				}
				
				objectMap.put("childrentotalmoney", (chiledrenemployee.getTotalmoney() == null?0:chiledrenemployee.getTotalmoney()));
				objectMap.put("childrentotalcount", chiledrenemployee.getTotalcount());
				
				objectMaplist.add(objectMap);
			}
			
			responseMap.put("status", "1");
			responseMap.put("result", "请求成功");
			responseMap.put("saleInfoList", objectMaplist);
			
			return responseMap;
		}catch(Exception e){
			e.printStackTrace();
			responseMap.put("status", "0");//保存失败
			responseMap.put("result", "数据异常，操作失败");//
			return responseMap;
		}
	}
	
	/**
	 * 查询销售员提成统计信息
	 * @param String
	 * @return
	 */
	public Map<String, Object> findSalegaininfoStat(JSONObject jsonObj){
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		
		try{
		
			//销售员编号
			String salercode = Tools.getKeyValueFromJsonObj("salercode", jsonObj);
			//请求页码
			String page = "1";
			//每页显示行数
			String rows = "10";
			
			if(StringUtils.isEmpty(salercode)){
				responseMap.put("status", "0");
				responseMap.put("result", "请求失败,请输入销售员编号");
				return responseMap;
			}
			
			//查询驻点单信息
			Employee employeeform = new Employee();
			//employeeform.setDepartment("2");//销售员提成
			employeeform.setEmployeecode(salercode);//赋值上级销售员编号
			employeeform.setPage(Integer.valueOf(page));
			employeeform.setRows(Integer.valueOf(rows));
			
			//当前操作员的提成统计排名
			Employee currentSalerInfo = httpForMpsDao.findSalegaininfoStatByEmployeecode(employeeform);
			HashMap<String, Object> objectMapForCurrent = new HashMap<String, Object>();
			objectMapForCurrent.put("employeecode", currentSalerInfo.getEmployeecode());
			objectMapForCurrent.put("employeename", Tools.getTransValueFromStr(currentSalerInfo.getEmployeename(), 1, currentSalerInfo.getEmployeename().length()-1, "*"));
			objectMapForCurrent.put("phone", Tools.getTransValueFromStr(currentSalerInfo.getPhone(), 3, 4, "*"));
			objectMapForCurrent.put("totalmoney", currentSalerInfo.getTotalmoney());
			objectMapForCurrent.put("ranking", currentSalerInfo.getRanking()>100?"100+":currentSalerInfo.getRanking());//如果大于100，就显示100+
			objectMaplist.add(objectMapForCurrent);
			
			List<Employee> salerInfoList = httpForMpsDao.findSalegaininfoStat(employeeform);
			for (Employee saler : salerInfoList) {
				HashMap<String, Object> objectMap = new HashMap<String, Object>();
				objectMap.put("employeecode", saler.getEmployeecode());
				objectMap.put("employeename", Tools.getTransValueFromStr(saler.getEmployeename(), 1, saler.getEmployeename().length()-1, "*"));
				objectMap.put("phone", Tools.getTransValueFromStr(saler.getPhone(), 3, 4, "*"));
				objectMap.put("totalmoney", saler.getTotalmoney());
				objectMap.put("ranking", saler.getRanking());
				objectMaplist.add(objectMap);
			}
			
			responseMap.put("status", "1");
			responseMap.put("result", "请求成功");
			responseMap.put("gainInfoList", objectMaplist);
			
			return responseMap;
		}catch(Exception e){
			e.printStackTrace();
			responseMap.put("status", "0");//保存失败
			responseMap.put("result", "数据异常，操作失败");//
			return responseMap;
		}
	}
	
	
	 /**
	 * 保存送货单已到货,客户接收
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveAcceptUserdelivery(JSONObject jsonObj){
		//加锁
		synchronized(lock) {
				
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			
			Map<String, Object> responseMap = new HashMap<String, Object>();
			try{
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "操作失败");
				//送货单ID
				String id = Tools.getStr(jsonObj.getString("id"));
				//送货人
				String deliverercode = Tools.getStr(jsonObj.getString("deliverercode"));
				
				//任务单信息
				if(StringUtils.isEmpty(id)){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "操作失败，送货单不能为空");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				Userdelivery userdelivery = userdeliveryDao.findById(Integer.parseInt(id));
				if(userdelivery == null){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "操作失败，送货单不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				String currenttime = Tools.getCurrentTime();
				
				
				//修改任务单状态
				userdelivery.setStatus("1");
				userdeliveryDao.update(userdelivery);
				
				//增加生成订单业务记录
				Userbusiness userbusinessForTaskrefuse = new Userbusiness();
				userbusinessForTaskrefuse.setOperatorcode(deliverercode);
				userbusinessForTaskrefuse.setBusinesstypekey("acceptdelivery");
				userbusinessForTaskrefuse.setBusinesstypename("送货单已接收");
				userbusinessForTaskrefuse.setUsercode(userdelivery.getUsercode());
				userbusinessForTaskrefuse.setTaskcode(null);
				userbusinessForTaskrefuse.setOrdercode(userdelivery.getOrdercode());
				userbusinessForTaskrefuse.setDispatchcode(null);
				userbusinessForTaskrefuse.setAddtime(currenttime);
				userbusinessForTaskrefuse.setContent("送货单接收;订单号为： "+ userdelivery.getOrdercode());
				userbusinessForTaskrefuse.setSource("0");//APP平台操作
				userbusinessDao.save(userbusinessForTaskrefuse);
				
				//增加操作日记
				String content = "送货单接收，订单号为:" +  userdelivery.getOrdercode();
				operatorlogService.saveOperatorlog(content, deliverercode);
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");//保存失败
				responseMap.put("result", "操作成功");//上传合同失败
				return responseMap;
			}catch(Exception e){
				e.printStackTrace();
				//回滚
				txManager.rollback(status);
				//输出异常
				e.printStackTrace();
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，处理失败");//没有上传合同
				return responseMap;
			}
		} 
	}
	
   /**
	 * 保存拒绝接收送货单,客户拒绝接收
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveRefuseUserdelivery(JSONObject jsonObj){
		//加锁
		synchronized(lock) {
				
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			
			Map<String, Object> responseMap = new HashMap<String, Object>();
			try{
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "操作失败");
				//送货单ID
				String id = Tools.getStr(jsonObj.getString("id"));
				//送货人
				String deliverercode = Tools.getStr(jsonObj.getString("deliverercode"));
				
				//任务单信息
				if(StringUtils.isEmpty(id)){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "操作失败，送货单不能为空");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				Userdelivery userdelivery = userdeliveryDao.findById(Integer.parseInt(id));
				if(userdelivery == null){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "操作失败，送货单不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				String currenttime = Tools.getCurrentTime();
				
				
				//修改任务单状态
				userdelivery.setStatus("10");
				userdeliveryDao.update(userdelivery);
				
				//增加生成订单业务记录
				Userbusiness userbusinessForTaskrefuse = new Userbusiness();
				userbusinessForTaskrefuse.setOperatorcode(deliverercode);
				userbusinessForTaskrefuse.setBusinesstypekey("refusedelivery");
				userbusinessForTaskrefuse.setBusinesstypename("送货单拒绝接收");
				userbusinessForTaskrefuse.setUsercode(userdelivery.getUsercode());
				userbusinessForTaskrefuse.setTaskcode(null);
				userbusinessForTaskrefuse.setOrdercode(userdelivery.getOrdercode());
				userbusinessForTaskrefuse.setDispatchcode(null);
				userbusinessForTaskrefuse.setAddtime(currenttime);
				userbusinessForTaskrefuse.setContent("送货单拒绝接收;订单号为： "+ userdelivery.getOrdercode());
				userbusinessForTaskrefuse.setSource("0");//APP平台操作
				userbusinessDao.save(userbusinessForTaskrefuse);
				
				//增加操作日记
				String content = "送货单拒绝接收，订单号为:" +  userdelivery.getOrdercode();
				operatorlogService.saveOperatorlog(content, deliverercode);
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");//保存失败
				responseMap.put("result", "操作成功");//上传合同失败
				return responseMap;
			}catch(Exception e){
				e.printStackTrace();
				//回滚
				txManager.rollback(status);
				//输出异常
				e.printStackTrace();
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，处理失败");//没有上传合同
				return responseMap;
			}
		} 
	}
	
	/**
	 * 保存订单购买-讲解类型-0：公司派人讲解测量
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveUserorderForVisittype0(JSONObject jsonObj){
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("status", "0");//默认保存失败
		//加锁
		synchronized(lock) {
				
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			try{
				//客户电话
				String phone = Tools.getStr(jsonObj.getString("client_phone"));
				//客户姓名
				String username = Tools.getStr(jsonObj.getString("client_name"));
				//客户地址
				String address = Tools.getStr(jsonObj.getString("client_address"));
				//销售员编号
				String salercode = Tools.getStr(jsonObj.getString("salercode"));
				//上门类型
				String visittype = Tools.getStr(jsonObj.getString("visittype"));
				//门锁信息
				//JSONArray doorinfoArray = jsonObj.getJSONArray("doorinfo");
				
				//操作时间
				String currenttime = Tools.getCurrentTime();
				
				//判断销售员的信息状态
				Employee saler = employeeDao.findByEmployeecodeStr(salercode);
				if(saler == null){
					responseMap.put("status", "0");//提交失败
					responseMap.put("result", "提交失败,销售员不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}else if("0".equals(saler.getStatus())){
					responseMap.put("status", "0");//提交失败
					responseMap.put("result", "提交失败,该销售员已被注销");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//电话号码不能为空
				if(StringUtils.isEmpty(phone)){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "电话号码不能为空");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//查询订户信息
				User user = userDao.findByPhoneStr(phone);
				if(user != null){//客户存在
					if("0".equals(user.getStatus())){
						responseMap.put("status", "0");//
						responseMap.put("result", "客户已经提交过，还未处理，请勿再次提交。如确定需要再次提交，请联系公司客服");//
						//回滚
						txManager.rollback(status);
						return responseMap;
					}else if("1".equals(user.getStatus()) || "2".equals(user.getStatus())){
						responseMap.put("status", "0");//
						responseMap.put("result", "客户安装正在进行中，请勿再次提交。如确定需要再次提交，请联系公司客服");//
						//回滚
						txManager.rollback(status);
						return responseMap;
					} else {//客户已经安装完成，再次提交，修改客户的销售员编号，上门类型，
						user.setUsername(username);
						user.setAddress(address);
						user.setSalercode(salercode);
						user.setVisittype(visittype);
						user.setSource("0");
						user.setStatus("0");//未处理
						userDao.update(user);//新修改
					}
				}else{//客户不存在，新添加
					user = new User();
					//客户姓名
					user.setUsername(username);
					//客户电话
					user.setPhone(phone);
					//客户地址
					user.setAddress(address);
					//审核时间
					//form.setChecktime(Tools.getStr(jsonObj.getString("client_checktime")));
					//上门类型（0.公司派人讲解和测量，1.销售员亲自讲解和测量; 2-无需讲解，公司派人只需测量）
					user.setVisittype(visittype);
					//销售员编号
					user.setSalercode(salercode);
					//审核状态
					user.setCheckstatus("1");
					user.setStatus("0"); //未处理
					user.setSource("0");
					user.setAddtime(currenttime);
					//客户编号
					String usercode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_usercode);
					user.setUsercode(usercode);
					//保存客户信息
					userDao.save(user);
				}
				
				//增加操作日记
				String content = "客户提交，电话为:"+phone+";上门类型为："+ user.getVisittypename();
				operatorlogService.saveOperatorlog(content, salercode);
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");//注册成功
				responseMap.put("result", "客户注册成功");//注册成功
				responseMap.put("usercode", user.getUsercode());
				
				return responseMap;
				
			}catch(Exception e){
				e.printStackTrace();
				//回滚
				txManager.rollback(status);
				
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，客户注册失败");//
				return responseMap;
			}
		} 
	}
    
	/**
	 * 保存订单购买-讲解类型-1：销售员亲自讲解
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveUserorderForVisittype1(JSONObject jsonObj){
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("status", "0");//默认保存失败
		//加锁
		synchronized(lock) {
			
			//当前时间
			String currenttime =  Tools.getCurrentTime();
			
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			try{
				
				String phone = Tools.getStr(jsonObj.getString("client_phone"));
				//客户姓名
				String username = Tools.getStr(jsonObj.getString("client_name"));
				//客户地址
				String address = Tools.getStr(jsonObj.getString("client_address"));
				//销售员编号
				String salercode = Tools.getStr(jsonObj.getString("salercode"));
				//上门类型
				String visittype = Tools.getStr(jsonObj.getString("visittype"));
				//产品型号
				String modelcode = Tools.getStr(jsonObj.getString("modelcode"));
				//是否带机械锁芯
				String lockcoreflag = Tools.getStr(jsonObj.getString("lockcoreflag"));
				//产品颜色
				String productcolor = Tools.getStr(jsonObj.getString("productcolor"));
				//门锁图片
				JSONArray doorinfoArray = jsonObj.getJSONArray("doorinfo");
				
				//门锁数据
				String locklong = Tools.getStr(jsonObj.getString("locklong")); //锁侧板长度
				String lockwidth = Tools.getStr(jsonObj.getString("lockwidth")); //锁侧板宽度
				
				//判断销售员的信息状态
				Employee saler = employeeDao.findByEmployeecodeStr(salercode);
				if(saler == null){
					responseMap.put("status", "0");//提交失败
					responseMap.put("result", "提交失败,销售员不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}else if("0".equals(saler.getStatus())){
					responseMap.put("status", "0");//提交失败
					responseMap.put("result", "提交失败,该销售员已被注销");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//电话号码不能为空
				if(StringUtils.isEmpty(phone)){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "电话号码不能为空");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				User user = userDao.findByPhoneStr(phone);
				if(user != null){//客户存在
					if("0".equals(user.getStatus())){
						responseMap.put("status", "0");//
						responseMap.put("result", "客户已经提交过，还未处理，请勿再次提交。如确定需要再次提交，请联系公司客服");//
						//回滚
						txManager.rollback(status);
						return responseMap;
					}else if("1".equals(user.getStatus()) || "2".equals(user.getStatus())){
						responseMap.put("status", "0");//
						responseMap.put("result", "客户正在安装中，请勿再次提交。如确定需要再次提交，请联系公司客服");//
						//回滚
						txManager.rollback(status);
						return responseMap;
					} else {//客户已经安装完成，再次提交，修改客户的销售员编号，上门类型，
						user.setUsername(username);
						user.setAddress(address);
						user.setSalercode(salercode);
						user.setVisittype(visittype);
						user.setSource("0");
						user.setStatus("2");//已生成订单
						userDao.update(user);//修改订户信息
					}
				}else{//客户不存在，新添加
					user = new User();
					//客户姓名
					user.setUsername(username);
					//客户电话
					user.setPhone(phone);
					//客户地址
					user.setAddress(address);
					//审核时间
					//form.setChecktime(Tools.getStr(jsonObj.getString("client_checktime")));
					//上门类型（0.公司派人讲解和测量，1.销售员亲自讲解和测量; 2-无需讲解，公司派人只需测量）
					user.setVisittype(visittype);
					//销售员编号
					user.setSalercode(salercode);
					//审核状态
					user.setCheckstatus("1");
					user.setSource("0");
					user.setStatus("2"); //已生成订单
					user.setAddtime(currenttime);
					//客户编号
					String usercode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_usercode);
					user.setUsercode(usercode);
					//保存客户信息
					userDao.save(user);
				}
				
				
				//其他费用
				Integer otherfee = 0;
				if("1".equals(lockcoreflag)){//购买机械锁心
					//查找出系统设置中的扫楼价格，单位为分
					String lockcore_price = systemparaService.findSystemParaByCodeStr("lockcore_price");
					if(StringUtils.isNotEmpty(lockcore_price)){
						otherfee = Integer.parseInt(lockcore_price); //500元的机械锁心费用
					} else {
						otherfee = 1000;
					}
				}
				//首付金额
				String firstpayment = "200";//默认首付款200
				//产品型号列表
				//JSONArray productinfoArray = jsonObj.getJSONArray("productinfo");
				//合同信号列表
				//JSONArray contractinfoArray = jsonObj.getJSONArray("contractinfo");
				
				if(StringUtils.isEmpty(productcolor)){//产品颜色为空
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，未选择产品颜色");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if("0".equals(lockcoreflag)){//不带机械锁心
					productcolor = "0" + String.valueOf(Integer.parseInt(productcolor) + 3); //如果是机械锁心，颜色+3，即对应相应的产品编号
				}
				
				Product productcolerObject  = productDao.findByProductcode(productcolor);
				if(productcolerObject == null){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，此颜色的产品外壳不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(StringUtils.isEmpty(modelcode)){//产品型号为空
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "未选择产品型号");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//查询产品型号信息
				Productmodel productmodel = productmodelDao.findByModelcode(modelcode);
				if(productmodel == null ){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "产品型号不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//产品原价
				Integer productfee_old = productmodel.getPrice();
				//获取提成的总金额
				Integer gaintotalmoney = productfee_old + Integer.valueOf(otherfee);
				//产品销售价格
				Integer productfee = productmodel.getSellprice();
				//总金额=产品费用+其他费用
				Integer totalmoney = productfee + Integer.valueOf(otherfee);
				//订单信息
				Userorder userorder = new Userorder();
				//获取订单号
				String ordercode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_ordercode);
				userorder.setOrdercode("DD"+ordercode);
				
				//订户信息
				userorder.setUsercode(user.getUsercode());
				userorder.setUsername(user.getUsername());
				userorder.setUsersex(user.getUsersex());
				userorder.setPhone(user.getPhone());
				userorder.setAddress(user.getAddress());
				userorder.setSource(user.getSource());
				userorder.setVisittype(user.getVisittype());
				//任务单信息
				userorder.setSalercode(user.getSalercode());
				userorder.setTalkercode(user.getSalercode());
				userorder.setVisitorcode(null);
				userorder.setVisitorstatus("0");//未派测量单
				userorder.setProductfee(productfee);
				userorder.setOtherfee(Integer.valueOf(otherfee));
				userorder.setGaintotalmoney(gaintotalmoney);
				userorder.setTotalmoney(totalmoney);//总金额
				userorder.setFirstpayment(Integer.valueOf(firstpayment));//首付金额
				userorder.setFinalpayment(totalmoney-Integer.valueOf(firstpayment));//尾款金额
				userorder.setFirstarrivalflag("0");
				userorder.setFinalarrivalflag("0");
				userorder.setPaytype("0");//默认支付定金
				userorder.setAddtime(currenttime);
				userorder.setOperatetime(userorder.getAddtime());
				userorder.setStatus("0");
				userorder.setFilingflag("0");
				userorder.setLockcoreflag(lockcoreflag); //是否带机械锁心
				userorder.setProductcolor(productcolor); //产品颜色
				if("1".equals(lockcoreflag)){//带机械锁心，必须上门测量
					userorder.setVisitorflag("1");//需要派人上门测量
				}else{
					userorder.setVisitorflag("0");//不需要派人上门测量
				}
				userorder.setProductconfirm("0");
				userorder.setModelcode(productmodel.getModelcode());
				userorder.setModelname(productmodel.getModelname());
				userorder.setDiscountgain(productmodel.getDiscountgain());
				userorder.setFixedgain(productmodel.getFixedgain());
				userorder.setManagergain(productmodel.getManagergain());
				userorder.setManagercode(saler.getManagercode());
				//保存订单
				userorderDao.save(userorder);
				
				//增加生成订单业务记录
				Userbusiness userbusinessForOrder = new Userbusiness();
				userbusinessForOrder.setOperatorcode(salercode);
				userbusinessForOrder.setBusinesstypekey("orderadd");
				userbusinessForOrder.setBusinesstypename("订单生成");
				userbusinessForOrder.setUsercode(user.getUsercode());
				userbusinessForOrder.setTaskcode(null);
				userbusinessForOrder.setOrdercode(userorder.getOrdercode());
				userbusinessForOrder.setDispatchcode(null);
				userbusinessForOrder.setAddtime(currenttime);
				userbusinessForOrder.setContent("生成订单;订单号为： "+userorder.getOrdercode());
				userbusinessForOrder.setSource("0");//APP平台操作
				userbusinessDao.save(userbusinessForOrder);
				
				//保存订单详情
				Userorderinfo userorderinfo = new Userorderinfo();
				userorderinfo.setOrdercode(userorder.getOrdercode());
				userorderinfo.setUsercode(userorder.getUsercode());
				userorderinfo.setUsername(userorder.getUsername());
				userorderinfo.setUsersex(userorder.getUsersex());
				userorderinfo.setPhone(userorder.getPhone());
				userorderinfo.setAddress(userorder.getAddress());
				userorderinfo.setSource(userorder.getSource());
				userorderinfo.setVisittype(userorder.getVisittype());
				userorderinfo.setSalercode(userorder.getSalercode());
				userorderinfo.setTalkercode(userorder.getTalkercode());
				userorderinfo.setVisitorcode(userorder.getVisitorcode());
				userorderinfo.setAddtime(currenttime);
				userorderinfo.setPrice(productmodel.getPrice());
				userorderinfo.setSellprice(productmodel.getSellprice());
				userorderinfo.setBuytype("0");//型号整体购买
				userorderinfo.setModelcode(productmodel.getModelcode());
				userorderinfo.setModelname(productmodel.getModelname());
				userorderinfoDao.save(userorderinfo);
				
				//保存任务单号
				Usertask usertask = new Usertask();
				//最大任务单号
				String taskcode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_taskcode);
				usertask.setTaskcode("RW"+taskcode);
				//任务单订户信息
				usertask.setOrdercode(userorder.getOrdercode());
				usertask.setUsercode(user.getUsercode());
				usertask.setUsername(user.getUsername());
				usertask.setUsersex(user.getUsersex());
				usertask.setPhone(user.getPhone());
				usertask.setAddress(user.getAddress());
				usertask.setSource(user.getSource());
				usertask.setVisittype(user.getVisittype());
				usertask.setSalercode(user.getSalercode());
				usertask.setTalkercode(user.getSalercode());//讲解人员为销售员
				usertask.setVisitorcode(null);//未派测量单
				usertask.setOperatorcode("");
				usertask.setVisitstate("0");//访问状态（默认为0，若拒绝安装则改为1）
				usertask.setVisittime(currenttime);//默认已完成上门单上门时间为当前时间
				//默认状态为已处理1
				usertask.setStatus("1");
				usertask.setAddtime(currenttime);
				usertask.setDealdate(currenttime);
				usertask.setTasktype("0");//讲解单
				//保存任务单
				taskDao.save(usertask);
				
				//增加业务记录
				Userbusiness userbusinessForTask = new Userbusiness();
				userbusinessForTask.setOperatorcode(salercode);
				userbusinessForTask.setBusinesstypekey("taskadd");
				userbusinessForTask.setBusinesstypename("任务单生成");
				userbusinessForTask.setUsercode(user.getUsercode());
				userbusinessForTask.setTaskcode(usertask.getTaskcode());
				userbusinessForTask.setOrdercode(usertask.getOrdercode());
				userbusinessForTask.setDispatchcode(null);
				userbusinessForTask.setAddtime(currenttime);
				userbusinessForTask.setContent("生成任务单;任务单号为： "+usertask.getTaskcode());
				userbusinessForTask.setSource("0");//APP自动生产
				userbusinessDao.save(userbusinessForTask);
				
				//找到该型号对应的产品类别信息
//				if(productinfoArray == null || productinfoArray.length()< 1){
//					responseMap.put("status", "0");//保存失败
//					responseMap.put("result", "请选择产品信息");
//					//回滚
//					txManager.rollback(status);
//					return responseMap;
//				}
//				
//				//找到该型号对于的产品类别
				List<Productmodelref> productmodelreflist = productmodelrefDao.findByModelcode(modelcode);
				if(productmodelreflist == null || productmodelreflist.size() < 1){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "注册失败，该产品型号未设置产品信息");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//保存订户产品信息
				for (Productmodelref productmodelref : productmodelreflist) {
					String typecode_OA = Tools.getStr(productmodelref.getTypecode());
					Userproduct userproduct = new Userproduct();
					userproduct.setUsercode(userorder.getUsercode());
					userproduct.setOrdercode(userorder.getOrdercode());
					userproduct.setModelcode(productmodel.getModelcode());
					userproduct.setModelname(productmodel.getModelname());
					userproduct.setTypecode(productmodelref.getTypecode());
					userproduct.setTypename(productmodelref.getTypename());
					userproduct.setBuytype("0");//型号整体购买
					userproduct.setAddtime(currenttime);
					userproduct.setPrice(Integer.valueOf(0));
					userproduct.setSaleprice(Integer.valueOf(0));
					
					if(StringUtils.isNotEmpty(productmodelref.getProductcode())){ //不为空，表示产品型号中已经设置好了产品编码
						Product product = productDao.findByProductcode(productmodelref.getProductcode());
						userproduct.setProductcode(product.getProductcode());
						userproduct.setProductname(product.getProductname());
						userproduct.setProductunit(product.getProductunit());
						userproduct.setProductsource("0");
					}else{
						userproduct.setProductcode("");
						userproduct.setProductname("");
						userproduct.setProductunit("");
						userproduct.setProductsource("1");
					}
					
					//修改产品颜色
					if("01".equals(productmodelref.getTypecode())){
						userproduct.setProductcode(productcolor);
						userproduct.setProductname(productcolerObject.getProductname());
						userproduct.setProductunit(productcolerObject.getProductunit());
						userproduct.setProductsource("1");
					}
					
					//从传过来的数据中获取该产品类别信息
//					for (int i = 0; i < productinfoArray.length(); i++) {
//						JSONObject productJsonObject = productinfoArray.getJSONObject(i);
//						String typecode_app = productJsonObject.getString("typecode");
//						String productcode_app = productJsonObject.getString("productcode");
//						if(typecode_OA.equals(typecode_app)){
//							if(StringUtils.isNotEmpty(productcode_app)){
//								 //查询产品信息
//					            Product product = productDao.findByProductcode(productcode_app);
//					            if(product == null){
//					            	responseMap.put("status", "0");//保存失败
//									responseMap.put("result", "注册失败，产品无效，产品编码为"+productcode_app);
//									//回滚
//									txManager.rollback(status);
//									return responseMap;
//					            }
//								userproduct.setProductcode(product.getProductcode());
//								userproduct.setProductname(product.getProductname());
//								userproduct.setProductunit(product.getProductunit());
//								break;
//							}
//						}
//					}
					//保存订户产品信息
					userproductDao.save(userproduct);
				}
				
				if("1".equals(lockcoreflag)){//购买机械锁心,需要增加机械锁心产品
					Userproduct userproduct = new Userproduct();
					userproduct.setUsercode(userorder.getUsercode());
					userproduct.setOrdercode(userorder.getOrdercode());
					userproduct.setModelcode(productmodel.getModelcode());
					userproduct.setModelname(productmodel.getModelname());
					userproduct.setTypecode("03");
					userproduct.setTypename("锁芯");
					userproduct.setBuytype("0");//型号整体购买
					userproduct.setAddtime(currenttime);
					userproduct.setPrice(Integer.valueOf(0));
					userproduct.setSaleprice(Integer.valueOf(0));
					userproduct.setProductcode("");
					userproduct.setProductname("");
					userproduct.setProductunit("");
					userproduct.setProductsource("1");
					
					//保存机械锁心
					userproductDao.save(userproduct);
				}
				
				
//				//循环合同信息
//				if(contractinfoArray != null && contractinfoArray.length() > 0){
//					// 创建临时路径
//					String usercontract_path = systemparaService.findSystemParaByCodeStr("upload_file_path");
//					//String upload_extend_path = systemparaDao.findByCodeStr("upload_file_path").getValue();
//					String folderpath = usercontract_path + File.separatorChar + "usercontract" + File.separatorChar +  userorder.getOrdercode();
//					File folder = new File(folderpath);
//					if (!folder.exists()) {
//						folder.mkdirs();
//					}else{
//						//先删除该目录
//						DeleteDirectory.deleteDir(folder);
//						//在重新创建
//						folder.mkdirs();
//					}
//					
//					//先清除数据的合同信息
//					usercontractDao.deleteByOrdercode(userorder.getOrdercode());
//					
//					for (int i = 0; i < contractinfoArray.length(); i++) {
//						JSONObject contractinfo = contractinfoArray.getJSONObject(i);
//						//合同文件-base64编码
//						String contractfile = Tools.getStr(contractinfo.getString("contractfile"));
//						if (contractfile != null) {
//							
//							//获取合同号
//							String contractcode = "HT"+ coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_contractcode);
//							byte[] contractfileBytes = Base64.decodeLines(contractfile);
//							//合同编号
//							String preservefilename = contractcode + "_"+Tools.getNowRandom() + ".jpg";
//							String preservepath = folderpath + File.separatorChar + preservefilename;
//							
//							// 写入的照片路径
//							FileOutputStream fos = new FileOutputStream(preservepath);
//							fos.write(contractfileBytes);
//							fos.flush();
//							fos.close();
//							
//							//保存合同信息
//							Usercontract usercontract = new Usercontract();
//							usercontract.setFilename(preservefilename);
//							usercontract.setPreservefilename(preservefilename);
//							usercontract.setPreserveurl(preservepath);
//							usercontract.setUsercode(userorder.getUsercode());
//							usercontract.setOrdercode(userorder.getOrdercode());
//							usercontract.setContractcode(contractcode);
//							usercontract.setAddtime(currenttime);
//							//保存合同号
//							usercontractDao.save(usercontract);
//						}
//					}
//				}
				
				//循环门锁信息
				if(doorinfoArray != null && doorinfoArray.length() > 0){
					// 创建临时路径
					String usercontract_path = systemparaService.findSystemParaByCodeStr("upload_file_path");
					//String upload_extend_path = systemparaDao.findByCodeStr("upload_file_path").getValue();
					String folderpath = usercontract_path + File.separatorChar + "userdoor" + File.separatorChar +  userorder.getUsercode()+ File.separatorChar + userorder.getOrdercode();
					File folder = new File(folderpath);
					if (!folder.exists()) {
						folder.mkdirs();
					}else{
						//先删除该目录
						DeleteDirectory.deleteDir(folder);
						//在重新创建
						folder.mkdirs();
					}
					
					//先清除数据的门锁图片信息
					userdoorDao.deleteByUsercode(userorder.getUsercode());
					
					for (int i = 0; i < doorinfoArray.length(); i++) {
						JSONObject doorinfo = doorinfoArray.getJSONObject(i);
						//合同文件-base64编码
						String doorfile = Tools.getStr(doorinfo.getString("doorfile"));
						if (doorfile != null) {
								
							byte[] doorfileBytes = Base64.decodeLines(doorfile);
							//合同编号
							String preservefilename = Tools.getNowRandom() + ".jpg";
							String preservepath = folderpath + File.separatorChar + preservefilename;
							
							// 写入的照片路径
							FileOutputStream fos = new FileOutputStream(preservepath);
							fos.write(doorfileBytes);
							fos.flush();
							fos.close();
							
							//保存门锁信息
							Userdoor userdoor = new Userdoor();
							userdoor.setUsercode(userorder.getUsercode());
							userdoor.setOrdercode(userorder.getOrdercode());
							userdoor.setFilename(preservefilename);
							userdoor.setPreservefilename(preservefilename);
							userdoor.setPreserveurl(preservepath);
							userdoor.setAddtime(currenttime);
							userdoor.setAddercode(userorder.getVisitorcode());
							//保存合同号
							userdoorDao.save(userdoor);
						}
					}
				}

				//门锁数据
				if(StringUtils.isNotEmpty(locklong) || StringUtils.isNotEmpty(lockwidth)){
					Userdoordata userdoordata = new Userdoordata();
					userdoordata.setUsercode(userorder.getUsercode());
					userdoordata.setOrdercode(userorder.getOrdercode());
					userdoordata.setLocklength(locklong);
					userdoordata.setLockwidth(lockwidth);
					userdoordata.setAddercode(salercode);
					userdoordata.setAddtime(currenttime);
					userdoordataDao.save(userdoordata);
				}
				
				//增加操作日记
				String content = "订单提交，客户电话为:"+phone+";上门类型为："+ user.getVisittypename();
				operatorlogService.saveOperatorlog(content, salercode);
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");//注册成功
				responseMap.put("result", "订单提交成功");//注册成功
				responseMap.put("usercode", user.getUsercode());
				
				return responseMap;
				
			}catch(Exception e){
				
				e.printStackTrace();
				//回滚
				txManager.rollback(status);
				
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，订单提交失败");//
				return responseMap;
			}
		} 
	}
	
	
	/**
	 * 查询未生成订单的客户信息-渠道方
	 * @param String
	 * @return
	 */
	public Map<String, Object> findUserListBySalercode(JSONObject jsonObj){
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		
		try{
		
			//销售员编号
			String salercode = Tools.getKeyValueFromJsonObj("salercode", jsonObj);
			//请求页码
			String page =  Tools.getStr(jsonObj.getString("page"));
			//每页显示行数
			String rows =  Tools.getStr(jsonObj.getString("rows"));
			
			if(StringUtils.isEmpty(salercode)){
				responseMap.put("status", "0");
				responseMap.put("result", "请求失败,请输入销售员编号");
				return responseMap;
			}
			
			//查询驻点单信息
			User userform = new User();
			userform.setStatus("0,1"); //未生成订单的客户
			userform.setSalercode(salercode);//销售员
			userform.setPage(Integer.valueOf(page));
			userform.setRows(Integer.valueOf(rows));
			
			List<User> userList = userDao.findByList(userform);
			for (User user : userList) {
				HashMap<String, Object> objectMap = new HashMap<String, Object>();
				objectMap.put("usercode", user.getUsercode());
				objectMap.put("username", Tools.getStr(user.getUsername()));
				objectMap.put("phone", Tools.getStr(user.getPhone()));
				objectMap.put("address", Tools.getStr(user.getAddress()));
				objectMap.put("status", Tools.getStr(user.getStatus()));
				objectMap.put("statusname", Tools.getStr(user.getStatusname()));
				objectMaplist.add(objectMap);
			}
			
			responseMap.put("status", "1");
			responseMap.put("result", "请求成功");
			responseMap.put("userList", objectMaplist);
			
			return responseMap;
		}catch(Exception e){
			e.printStackTrace();
			responseMap.put("status", "0");//保存失败
			responseMap.put("result", "数据异常，操作失败");//
			return responseMap;
		}
	}
	
	/**
	 * 查询已生成订单的客户订单信息-渠道方
	 * @param String
	 * @return
	 */
	public Map<String, Object> findUserorderListBySalercode(JSONObject jsonObj){
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		
		try{
		
			//销售员编号
			String salercode = Tools.getKeyValueFromJsonObj("salercode", jsonObj);
			//订单状态(0-未结单；1-已结单)
			String orderstatus = Tools.getKeyValueFromJsonObj("orderstatus", jsonObj);
			//请求页码
			String page =  Tools.getStr(jsonObj.getString("page"));
			//每页显示行数
			String rows =  Tools.getStr(jsonObj.getString("rows"));
			
			if(StringUtils.isEmpty(salercode)){
				responseMap.put("status", "0");
				responseMap.put("result", "请求失败,请输入销售员编号");
				return responseMap;
			}
			
			//查询驻点单信息
			Userorder userorderform = new Userorder();
			userorderform.setSalercode(salercode);//销售员
			if("0".equals(orderstatus)){//未结单
				userorderform.setStatus("0,1,2,3,4,5");
			}else if("1".equals(orderstatus)){
				userorderform.setStatus("6,7");
			}
			
			userorderform.setPage(Integer.valueOf(page));
			userorderform.setRows(Integer.valueOf(rows));
			
			List<Userorder> userorderList = userorderDao.findByList(userorderform);
			for (Userorder userorder : userorderList) {
				HashMap<String, Object> objectMap = new HashMap<String, Object>();
				objectMap.put("ordercode", userorder.getOrdercode());
				objectMap.put("usercode", userorder.getUsercode());
				objectMap.put("username", Tools.getStr(userorder.getUsername()));
				objectMap.put("phone", Tools.getStr(userorder.getPhone()));
				objectMap.put("address", Tools.getStr(userorder.getAddress()));
				objectMap.put("status", userorder.getStatus());
				objectMap.put("statusname", userorder.getStatusname());
				objectMap.put("productfee", userorder.getProductfee()==null?0:userorder.getProductfee());
				objectMap.put("otherfee", userorder.getOtherfee()==null?0:userorder.getOtherfee());
				objectMap.put("gaintotalmoney", userorder.getGaintotalmoney()==null?0:userorder.getGaintotalmoney());
				objectMap.put("totalmoney", userorder.getTotalmoney()==null?0: userorder.getTotalmoney());
				objectMap.put("firstpayment", userorder.getFirstpayment());
				objectMap.put("firstarrivalflag", userorder.getFirstarrivalflag());
				objectMap.put("firstarrivalflagname", userorder.getFirstarrivalflagname());
				objectMap.put("finalpayment", userorder.getFinalpayment());
				objectMap.put("finalarrivalflag", userorder.getFinalarrivalflag());
				objectMap.put("finalarrivalflagname", userorder.getFinalarrivalflagname());
				objectMap.put("productcolorcode", userorder.getProductcolor());
				objectMap.put("productcolorname", userorder.getProductcolorname());
				objectMap.put("lockcoreflag", userorder.getLockcoreflag());
				objectMap.put("lockcoreflagname", userorder.getLockcoreflagname());
				objectMap.put("modelcode", userorder.getModelcode());
				objectMap.put("modelname", userorder.getModelname());
				
				//此订单获取的提成
				Salegaininfo Salegaininfoform = new Salegaininfo();
				Salegaininfoform.setOrdercode(userorder.getOrdercode());
				Salegaininfoform.setGainercode(salercode);//登陆移动端APP的提成
				Long gaintotalmoney = salegaininfoDao.findGaintotalmoney(Salegaininfoform);
				if(gaintotalmoney == null ){
					gaintotalmoney = Long.valueOf(0);
				}
				objectMap.put("gainmoney", gaintotalmoney);
				
				objectMaplist.add(objectMap);
			}
			
			responseMap.put("status", "1");
			responseMap.put("result", "请求成功");
			responseMap.put("userorderList", objectMaplist);
			
			return responseMap;
		}catch(Exception e){
			e.printStackTrace();
			responseMap.put("status", "0");//保存失败
			responseMap.put("result", "数据异常，操作失败");//
			return responseMap;
		}
	}
	
	/**
	 * 保存讲解人员上门任务单提交
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveUserTaskByTalkercode(JSONObject jsonObj){
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("status", "0");//默认保存失败
		//加锁
		synchronized(lock) {
			
			//当前时间
			String currenttime =  Tools.getCurrentTime();
			
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			try{
				//任务单编号
				String taskcode = Tools.getStr(jsonObj.getString("taskcode"));
				//讲解员编号
				String talkercode = Tools.getStr(jsonObj.getString("talkercode"));
				//产品型号
				String modelcode = Tools.getStr(jsonObj.getString("modelcode"));
				//是否带机械锁芯
				String lockcoreflag = Tools.getStr(jsonObj.getString("lockcoreflag"));
				//产品颜色
				String productcolor = Tools.getStr(jsonObj.getString("productcolor"));
				//门锁信息
				JSONArray doorinfoArray = jsonObj.getJSONArray("doorinfo");
				//门锁数据
				String locklong = Tools.getStr(jsonObj.getString("locklong")); //锁侧板长度
				String lockwidth = Tools.getStr(jsonObj.getString("lockwidth")); //锁侧板宽度
				
				//判断销售员的信息状态
				Employee talker = employeeDao.findByEmployeecodeStr(talkercode);
				if(talker == null){
					responseMap.put("status", "0");//提交失败
					responseMap.put("result", "提交失败,销售员不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}else if("0".equals(talker.getStatus())){
					responseMap.put("status", "0");//提交失败
					responseMap.put("result", "提交失败,该销售员已被注销");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//电话号码不能为空
				if(StringUtils.isEmpty(taskcode)){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "任务单号不能为空");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				Usertask usertask = taskDao.findByTaskcodeStr(taskcode);
				if(usertask == null){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，上传的任务单号不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if("1".equals(usertask.getStatus())){//任务单已经处理完成
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，此任务单已经处理完成");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				
				//其他费用
				Integer otherfee = 0;
				if("1".equals(lockcoreflag)){//购买机械锁心
					//查找出系统设置中的扫楼价格，单位为分
					String lockcore_price = systemparaService.findSystemParaByCodeStr("lockcore_price");
					if(StringUtils.isNotEmpty(lockcore_price)){
						otherfee = Integer.parseInt(lockcore_price); //500元的机械锁心费用
					} else {
						otherfee = 500;
					}
				}
				//首付金额
				String firstpayment = "200";//默认首付款200
				//产品型号列表
				//JSONArray productinfoArray = jsonObj.getJSONArray("productinfo");
				//合同信号列表
				//JSONArray contractinfoArray = jsonObj.getJSONArray("contractinfo");
				
				if(StringUtils.isEmpty(productcolor)){//产品颜色为空
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，未选择产品颜色");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if("0".equals(lockcoreflag)){//不带机械锁心
					productcolor = "0" + String.valueOf(Integer.parseInt(productcolor) + 3); //如果是机械锁心，颜色+3，即对应相应的产品编号
				}
				
				Product productcolerObject  = productDao.findByProductcode(productcolor);
				if(productcolerObject == null){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，此颜色的产品外壳不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(StringUtils.isEmpty(modelcode)){//产品型号为空
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "未选择产品型号");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//查询产品型号信息
				Productmodel productmodel = productmodelDao.findByModelcode(modelcode);
				if(productmodel == null ){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "产品型号不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//产品原价
				Integer productfee_old = productmodel.getPrice();
				//获取提成的总金额
				Integer gaintotalmoney = productfee_old + Integer.valueOf(otherfee);
				//产品销售价格
				Integer productfee = productmodel.getSellprice();
				//总金额=产品费用+其他费用
				Integer totalmoney = productfee + Integer.valueOf(otherfee);
				//订单信息
				Userorder userorder = new Userorder();
				//获取订单号
				String ordercode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_ordercode);
				userorder.setOrdercode("DD"+ordercode);
				
				//订户信息
				userorder.setUsercode(usertask.getUsercode());
				userorder.setUsername(usertask.getUsername());
				userorder.setUsersex(usertask.getUsersex());
				userorder.setPhone(usertask.getPhone());
				userorder.setAddress(usertask.getAddress());
				userorder.setSource(usertask.getSource());
				userorder.setVisittype(usertask.getVisittype());
				//任务单信息
				userorder.setSalercode(usertask.getSalercode());
				userorder.setTalkercode(usertask.getTalkercode());
				userorder.setVisitorcode(null);
				userorder.setVisitorstatus("0");//未派测量单
				userorder.setProductfee(productfee);
				userorder.setOtherfee(Integer.valueOf(otherfee));
				userorder.setGaintotalmoney(gaintotalmoney);
				userorder.setTotalmoney(totalmoney);//总金额
				userorder.setFirstpayment(Integer.valueOf(firstpayment));//首付金额
				userorder.setFinalpayment(totalmoney-Integer.valueOf(firstpayment));//尾款金额
				userorder.setFirstarrivalflag("0");
				userorder.setFinalarrivalflag("0");
				userorder.setPaytype("0");//默认支付定金
				userorder.setAddtime(currenttime);
				userorder.setOperatetime(userorder.getAddtime());
				userorder.setStatus("0");
				userorder.setFilingflag("0");
				userorder.setLockcoreflag(lockcoreflag); //是否带机械锁心
				userorder.setProductcolor(productcolor); //产品颜色
				if("1".equals(lockcoreflag)){//带机械锁心，必须上门测量
					userorder.setVisitorflag("1");//需要派人上门测量
				}else{
					userorder.setVisitorflag("0");//不需要派人上门测量
				}
				userorder.setProductconfirm("0");
				userorder.setModelcode(productmodel.getModelcode());
				userorder.setModelname(productmodel.getModelname());
				userorder.setDiscountgain(productmodel.getDiscountgain());
				userorder.setFixedgain(productmodel.getFixedgain());
				userorder.setManagergain(productmodel.getManagergain());
				if(StringUtils.isNotEmpty(usertask.getSalercode())){
					Employee saler = employeeDao.findByEmployeecodeStr(usertask.getSalercode());
					if(saler != null){
						userorder.setManagercode(saler.getManagercode());
					}
				}
				//保存订单
				userorderDao.save(userorder);
				
				//增加生成订单业务记录
				Userbusiness userbusinessForOrder = new Userbusiness();
				userbusinessForOrder.setOperatorcode(talkercode);
				userbusinessForOrder.setBusinesstypekey("orderadd");
				userbusinessForOrder.setBusinesstypename("订单生成");
				userbusinessForOrder.setUsercode(usertask.getUsercode());
				userbusinessForOrder.setTaskcode(null);
				userbusinessForOrder.setOrdercode(userorder.getOrdercode());
				userbusinessForOrder.setDispatchcode(null);
				userbusinessForOrder.setAddtime(currenttime);
				userbusinessForOrder.setContent("生成订单;订单号为： "+userorder.getOrdercode());
				userbusinessForOrder.setSource("0");//APP平台操作
				userbusinessDao.save(userbusinessForOrder);
				
				//保存订单详情
				Userorderinfo userorderinfo = new Userorderinfo();
				userorderinfo.setOrdercode(userorder.getOrdercode());
				userorderinfo.setUsercode(userorder.getUsercode());
				userorderinfo.setUsername(userorder.getUsername());
				userorderinfo.setUsersex(userorder.getUsersex());
				userorderinfo.setPhone(userorder.getPhone());
				userorderinfo.setAddress(userorder.getAddress());
				userorderinfo.setSource(userorder.getSource());
				userorderinfo.setVisittype(userorder.getVisittype());
				userorderinfo.setSalercode(userorder.getSalercode());
				userorderinfo.setTalkercode(userorder.getTalkercode());
				userorderinfo.setVisitorcode(userorder.getVisitorcode());
				userorderinfo.setAddtime(currenttime);
				userorderinfo.setPrice(productmodel.getPrice());
				userorderinfo.setSellprice(productmodel.getSellprice());
				userorderinfo.setBuytype("0");//型号整体购买
				userorderinfo.setModelcode(productmodel.getModelcode());
				userorderinfo.setModelname(productmodel.getModelname());
				userorderinfoDao.save(userorderinfo);
				
				//修改任务单状态
				usertask.setOrdercode(userorder.getOrdercode());
				usertask.setStatus("1");
				usertask.setDealdate(currenttime);
				taskDao.update(usertask);
				
				//修改客户状态为已生成订单
				User user = userDao.findByUsercodeStr(usertask.getUsercode());
				user.setStatus("2");//修改成已生成订单
				userDao.update(user);
				
				//增加业务记录
				Userbusiness userbusinessForTask = new Userbusiness();
				userbusinessForTask.setOperatorcode(talkercode);
				userbusinessForTask.setBusinesstypekey("orderadd");
				userbusinessForTask.setBusinesstypename("订单生成");
				userbusinessForTask.setUsercode(usertask.getUsercode());
				userbusinessForTask.setTaskcode(usertask.getTaskcode());
				userbusinessForTask.setOrdercode(usertask.getOrdercode());
				userbusinessForTask.setDispatchcode(null);
				userbusinessForTask.setAddtime(currenttime);
				userbusinessForTask.setContent("生成订单;订单编号为： " + ordercode);
				userbusinessForTask.setSource("0");//APP自动生产
				userbusinessDao.save(userbusinessForTask);
	
				//找到该型号对于的产品类别
				List<Productmodelref> productmodelreflist = productmodelrefDao.findByModelcode(modelcode);
				if(productmodelreflist == null || productmodelreflist.size() < 1){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "注册失败，该产品型号未设置产品信息");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//保存订户产品信息
				for (Productmodelref productmodelref : productmodelreflist) {
					String typecode_OA = Tools.getStr(productmodelref.getTypecode());
					Userproduct userproduct = new Userproduct();
					userproduct.setUsercode(userorder.getUsercode());
					userproduct.setOrdercode(userorder.getOrdercode());
					userproduct.setModelcode(productmodel.getModelcode());
					userproduct.setModelname(productmodel.getModelname());
					userproduct.setTypecode(productmodelref.getTypecode());
					userproduct.setTypename(productmodelref.getTypename());
					userproduct.setBuytype("0");//型号整体购买
					userproduct.setAddtime(currenttime);
					userproduct.setPrice(Integer.valueOf(0));
					userproduct.setSaleprice(Integer.valueOf(0));
					
					if(StringUtils.isNotEmpty(productmodelref.getProductcode())){ //不为空，表示产品型号中已经设置好了产品编码
						Product product = productDao.findByProductcode(productmodelref.getProductcode());
						userproduct.setProductcode(product.getProductcode());
						userproduct.setProductname(product.getProductname());
						userproduct.setProductunit(product.getProductunit());
						userproduct.setProductsource("0");
					}else{
						userproduct.setProductcode("");
						userproduct.setProductname("");
						userproduct.setProductunit("");
						userproduct.setProductsource("1");
					}
					
					//修改产品颜色
					if("01".equals(productmodelref.getTypecode())){
						userproduct.setProductcode(productcolor);
						userproduct.setProductname(productcolerObject.getProductname());
						userproduct.setProductunit(productcolerObject.getProductunit());
						userproduct.setProductsource("1");
					}
					
					//从传过来的数据中获取该产品类别信息
//					for (int i = 0; i < productinfoArray.length(); i++) {
//						JSONObject productJsonObject = productinfoArray.getJSONObject(i);
//						String typecode_app = productJsonObject.getString("typecode");
//						String productcode_app = productJsonObject.getString("productcode");
//						if(typecode_OA.equals(typecode_app)){
//							if(StringUtils.isNotEmpty(productcode_app)){
//								 //查询产品信息
//					            Product product = productDao.findByProductcode(productcode_app);
//					            if(product == null){
//					            	responseMap.put("status", "0");//保存失败
//									responseMap.put("result", "注册失败，产品无效，产品编码为"+productcode_app);
//									//回滚
//									txManager.rollback(status);
//									return responseMap;
//					            }
//								userproduct.setProductcode(product.getProductcode());
//								userproduct.setProductname(product.getProductname());
//								userproduct.setProductunit(product.getProductunit());
//								break;
//							}
//						}
//					}
					//保存订户产品信息
					userproductDao.save(userproduct);
				}
				
				if("1".equals(lockcoreflag)){//购买机械锁心,需要增加机械锁心产品
					Userproduct userproduct = new Userproduct();
					userproduct.setUsercode(userorder.getUsercode());
					userproduct.setOrdercode(userorder.getOrdercode());
					userproduct.setModelcode(productmodel.getModelcode());
					userproduct.setModelname(productmodel.getModelname());
					userproduct.setTypecode("03");
					userproduct.setTypename("锁芯");
					userproduct.setBuytype("0");//型号整体购买
					userproduct.setAddtime(currenttime);
					userproduct.setPrice(Integer.valueOf(0));
					userproduct.setSaleprice(Integer.valueOf(0));
					userproduct.setProductcode("");
					userproduct.setProductname("");
					userproduct.setProductunit("");
					userproduct.setProductsource("1");
					
					//保存机械锁心
					userproductDao.save(userproduct);
				}
				
				
//				//循环合同信息
//				if(contractinfoArray != null && contractinfoArray.length() > 0){
//					// 创建临时路径
//					String usercontract_path = systemparaService.findSystemParaByCodeStr("upload_file_path");
//					//String upload_extend_path = systemparaDao.findByCodeStr("upload_file_path").getValue();
//					String folderpath = usercontract_path + File.separatorChar + "usercontract" + File.separatorChar +  userorder.getOrdercode();
//					File folder = new File(folderpath);
//					if (!folder.exists()) {
//						folder.mkdirs();
//					}else{
//						//先删除该目录
//						DeleteDirectory.deleteDir(folder);
//						//在重新创建
//						folder.mkdirs();
//					}
//					
//					//先清除数据的合同信息
//					usercontractDao.deleteByOrdercode(userorder.getOrdercode());
//					
//					for (int i = 0; i < contractinfoArray.length(); i++) {
//						JSONObject contractinfo = contractinfoArray.getJSONObject(i);
//						//合同文件-base64编码
//						String contractfile = Tools.getStr(contractinfo.getString("contractfile"));
//						if (contractfile != null) {
//							
//							//获取合同号
//							String contractcode = "HT"+ coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_contractcode);
//							byte[] contractfileBytes = Base64.decodeLines(contractfile);
//							//合同编号
//							String preservefilename = contractcode + "_"+Tools.getNowRandom() + ".jpg";
//							String preservepath = folderpath + File.separatorChar + preservefilename;
//							
//							// 写入的照片路径
//							FileOutputStream fos = new FileOutputStream(preservepath);
//							fos.write(contractfileBytes);
//							fos.flush();
//							fos.close();
//							
//							//保存合同信息
//							Usercontract usercontract = new Usercontract();
//							usercontract.setFilename(preservefilename);
//							usercontract.setPreservefilename(preservefilename);
//							usercontract.setPreserveurl(preservepath);
//							usercontract.setUsercode(userorder.getUsercode());
//							usercontract.setOrdercode(userorder.getOrdercode());
//							usercontract.setContractcode(contractcode);
//							usercontract.setAddtime(currenttime);
//							//保存合同号
//							usercontractDao.save(usercontract);
//						}
//					}
//				}
				
				//循环门锁信息
				if(doorinfoArray != null && doorinfoArray.length() > 0){
					// 创建临时路径
					String usercontract_path = systemparaService.findSystemParaByCodeStr("upload_file_path");
					//String upload_extend_path = systemparaDao.findByCodeStr("upload_file_path").getValue();
					String folderpath = usercontract_path + File.separatorChar + "userdoor" + File.separatorChar +  userorder.getUsercode();
					File folder = new File(folderpath);
					if (!folder.exists()) {
						folder.mkdirs();
					}else{
						//先删除该目录
						DeleteDirectory.deleteDir(folder);
						//在重新创建
						folder.mkdirs();
					}
					
					//先清除数据的门锁图片信息
					userdoorDao.deleteByUsercode(userorder.getUsercode());
					
					for (int i = 0; i < doorinfoArray.length(); i++) {
						JSONObject doorinfo = doorinfoArray.getJSONObject(i);
						//合同文件-base64编码
						String doorfile = Tools.getStr(doorinfo.getString("doorfile"));
						if (doorfile != null) {
								
							byte[] doorfileBytes = Base64.decodeLines(doorfile);
							//合同编号
							String preservefilename = userorder.getUsercode() + "_" + i + "_"+Tools.getNowRandom() + ".jpg";
							String preservepath = folderpath + File.separatorChar + preservefilename;
							
							// 写入的照片路径
							FileOutputStream fos = new FileOutputStream(preservepath);
							fos.write(doorfileBytes);
							fos.flush();
							fos.close();
							
							//保存门锁信息
							Userdoor userdoor = new Userdoor();
							userdoor.setUsercode(userorder.getUsercode());
							userdoor.setOrdercode(userorder.getOrdercode());
							userdoor.setFilename(preservefilename);
							userdoor.setPreservefilename(preservefilename);
							userdoor.setPreserveurl(preservepath);
							userdoor.setAddtime(currenttime);
							userdoor.setAddercode(userorder.getVisitorcode());
							//保存合同号
							userdoorDao.save(userdoor);
						}
					}
				}

				//门锁数据
				if(StringUtils.isNotEmpty(locklong) || StringUtils.isNotEmpty(lockwidth)){
					Userdoordata userdoordata = new Userdoordata();
					userdoordata.setUsercode(userorder.getUsercode());
					userdoordata.setOrdercode(userorder.getOrdercode());
					userdoordata.setLocklength(locklong);
					userdoordata.setLockwidth(lockwidth);
					userdoordata.setAddercode(talkercode);
					userdoordata.setAddtime(currenttime);
					userdoordataDao.save(userdoordata);
				}
				
				//增加操作日记
				String content = "任务单处理，客户电话为:"+usertask.getPhone()+";任务单号为："+ usertask.getTaskcode();
				operatorlogService.saveOperatorlog(content, talkercode);
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");//注册成功
				responseMap.put("result", "提交成功");//注册成功
				
				return responseMap;
				
			}catch(Exception e){
				
				e.printStackTrace();
				//回滚
				txManager.rollback(status);
				
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，操作失败");//
				return responseMap;
			}
		} 
	}
	
	
	/**
	 * 讲解员修改客户订单价格
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> updateUserorderPrice(JSONObject jsonObj){
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("status", "0");//默认保存失败
		//加锁
		synchronized(lock) {
			
			//当前时间
			String currenttime =  Tools.getCurrentTime();
			
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			try{
				//订单编号
				String ordercode = Tools.getStr(jsonObj.getString("ordercode"));
				//讲解员编号
				String talkercode = Tools.getStr(jsonObj.getString("talkercode"));
				//产品价格
				String productfee = Tools.getStr(jsonObj.getString("productfee"));
				
				//判断销售员的信息状态
				Employee talker = employeeDao.findByEmployeecodeStr(talkercode);
				if(talker == null){
					responseMap.put("status", "0");//提交失败
					responseMap.put("result", "提交失败,销售员不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}else if("0".equals(talker.getStatus())){
					responseMap.put("status", "0");//提交失败
					responseMap.put("result", "提交失败,该销售员已被注销");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//订单不能为空
				if(StringUtils.isEmpty(ordercode)){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "订单号不能为空");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				Userorder userorder = userorderDao.findByOrdercode(ordercode);
				if(userorder == null){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，上传的订单号不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(!"0".equals(userorder.getFinalarrivalflag())){//订单尾款已经支付，不能修改产品价格
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，此订单已经支付尾款，不能修改产品价格");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(StringUtils.isEmpty(userorder.getModelcode())){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，此订单的产品型号为空");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				Productmodel productmodel = productmodelDao.findByModelcode(userorder.getModelcode());
				
				if(productmodel == null){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，此订单对应的产品型号不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				
				if(Integer.valueOf(productfee) > productmodel.getMaxprice()){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，产品价格不能大于该产品型号的最大上浮价格："+productmodel.getMaxprice());
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(Integer.valueOf(productfee) < productmodel.getMinprice()){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，产品价格不能小于该产品型号的最小下浮价格："+productmodel.getMinprice());
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				Integer totalmoney = Integer.valueOf(productfee) + userorder.getOtherfee();
				userorder.setProductfee(Integer.valueOf(productfee));
				userorder.setTotalmoney(totalmoney);//总金额
				userorder.setFinalpayment(totalmoney-Integer.valueOf(userorder.getFirstpayment()));//尾款金额
				
				//保存订单
				userorderDao.update(userorder);
				
				//增加生成订单业务记录
				Userbusiness userbusinessForOrder = new Userbusiness();
				userbusinessForOrder.setOperatorcode(talkercode);
				userbusinessForOrder.setBusinesstypekey("productfeeupdate");
				userbusinessForOrder.setBusinesstypename("修改产品费用");
				userbusinessForOrder.setUsercode(userorder.getUsercode());
				userbusinessForOrder.setTaskcode(null);
				userbusinessForOrder.setOrdercode(userorder.getOrdercode());
				userbusinessForOrder.setDispatchcode(null);
				userbusinessForOrder.setAddtime(currenttime);
				userbusinessForOrder.setContent("修改产品费用：订单号为： "+userorder.getOrdercode() + "; 产品新的价格为:"+ userorder.getProductfee());
				userbusinessForOrder.setSource("0");//APP平台操作
				userbusinessDao.save(userbusinessForOrder);
				
				//增加操作日记
				String content = "订单产品费用修改，客户电话为:"+userorder.getPhone()+";客户姓名为："+ userorder.getUsername()+";订单编号为："+ userorder.getOrdercode()+";产品新价格为："+ userorder.getProductfee();
				operatorlogService.saveOperatorlog(content, talkercode);
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");//操作成功
				responseMap.put("result", "订单产品价格修改成功");//操作成功
				
				return responseMap;
				
			}catch(Exception e){
				
				e.printStackTrace();
				//回滚
				txManager.rollback(status);
				
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，操作失败");//
				return responseMap;
			}
		} 
	}
	
	/**
	 * APP删除门锁照片
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> deleteUserdoor(JSONObject jsonObj){
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("status", "0");//默认保存失败
		//加锁
		synchronized(lock) {
			
			//当前时间
			String currenttime =  Tools.getCurrentTime();
			
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			try{
				//APP操作员
				String operatorcode = Tools.getKeyValueFromJsonObj("operatorcode", jsonObj);
				//门锁图片ID
				String id = Tools.getKeyValueFromJsonObj("id", jsonObj);
				
				//判断销售员的信息状态
				Employee talker = employeeDao.findByEmployeecodeStr(operatorcode);
				if(talker == null){
					responseMap.put("status", "0");//提交失败
					responseMap.put("result", "提交失败,销售员不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}else if("0".equals(talker.getStatus())){
					responseMap.put("status", "0");//提交失败
					responseMap.put("result", "提交失败,该销售员已被注销");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//订单不能为空
				if(StringUtils.isEmpty(id)){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "门锁图片ID不能为空");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				Userdoor userdoor = userdoorDao.findById(Integer.parseInt(id));
				if(userdoor == null){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，上传的图片ID不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//删除门锁图片保存在服务器文件
				File tmpFile = new File(userdoor.getPreserveurl());
				tmpFile.delete();
				
				//删除门锁图片数据库信息
				userdoorDao.delete(Integer.parseInt(id));
				
				//增加操作日记
				String content = "删除门锁照片，客户编号为:"+userdoor.getUsercode() + ";门锁文件名称为:" + userdoor.getFilename();
				operatorlogService.saveOperatorlog(content, operatorcode);
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");//操作成功
				responseMap.put("result", "操作成功");//操作成功
				
				return responseMap;
				
			}catch(Exception e){
				
				e.printStackTrace();
				//回滚
				txManager.rollback(status);
				
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，操作失败");//
				return responseMap;
			}
		} 
	}
	
	/**
	 * APP添加门锁照片
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveUserdoor(JSONObject jsonObj){
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("status", "0");//默认保存失败
		//加锁
		synchronized(lock) {
			
			//当前时间
			String currenttime =  Tools.getCurrentTime();
			
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			try{
				//APP操作员
				String operatorcode = Tools.getKeyValueFromJsonObj("operatorcode", jsonObj);
				//订单编码
				String ordercode = Tools.getKeyValueFromJsonObj("ordercode", jsonObj);
				//门锁图片
				JSONArray doorinfoArray = jsonObj.getJSONArray("doorinfo");
				
				//判断销售员的信息状态
				Employee talker = employeeDao.findByEmployeecodeStr(operatorcode);
				if(talker == null){
					responseMap.put("status", "0");//提交失败
					responseMap.put("result", "提交失败,销售员不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}else if("0".equals(talker.getStatus())){
					responseMap.put("status", "0");//提交失败
					responseMap.put("result", "提交失败,该销售员已被注销");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//订单不能为空
				if(StringUtils.isEmpty(ordercode)){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "订单编号不能为空");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				Userorder userorder = userorderDao.findByOrdercode(ordercode);
				if(userorder == null){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，上传的订单编号不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//循环门锁信息
				if(doorinfoArray != null && doorinfoArray.length() > 0){
					// 创建临时路径
					String usercontract_path = systemparaService.findSystemParaByCodeStr("upload_file_path");
					//String upload_extend_path = systemparaDao.findByCodeStr("upload_file_path").getValue();
					String folderpath = usercontract_path + File.separatorChar + "userdoor" + File.separatorChar +  userorder.getUsercode();
					File folder = new File(folderpath);
					if (!folder.exists()) {
						folder.mkdirs();
					}
					
					for (int i = 0; i < doorinfoArray.length(); i++) {
						JSONObject doorinfo = doorinfoArray.getJSONObject(i);
						//合同文件-base64编码
						String doorfile = Tools.getStr(doorinfo.getString("doorfile"));
						if (doorfile != null) {
								
							byte[] doorfileBytes = Base64.decodeLines(doorfile);
							//合同编号
							String preservefilename = userorder.getUsercode() + "_" + i + "_"+Tools.getNowRandom() + ".jpg";
							String preservepath = folderpath + File.separatorChar + preservefilename;
							
							// 写入的照片路径
							FileOutputStream fos = new FileOutputStream(preservepath);
							fos.write(doorfileBytes);
							fos.flush();
							fos.close();
							
							//保存门锁信息
							Userdoor userdoor = new Userdoor();
							userdoor.setUsercode(userorder.getUsercode());
							userdoor.setOrdercode(userorder.getOrdercode());
							userdoor.setFilename(preservefilename);
							userdoor.setPreservefilename(preservefilename);
							userdoor.setPreserveurl(preservepath);
							userdoor.setAddtime(currenttime);
							userdoor.setAddercode(operatorcode);
							//保存合同号
							userdoorDao.save(userdoor);
						}
					}
				}
				
				//增加操作日记
				String content = "添加门锁照片，订单编号为:"+userorder.getUsercode();
				operatorlogService.saveOperatorlog(content, operatorcode);
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");//操作成功
				responseMap.put("result", "操作成功");//操作成功
				
				return responseMap;
				
			}catch(Exception e){
				
				e.printStackTrace();
				//回滚
				txManager.rollback(status);
				
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，操作失败");//
				return responseMap;
			}
		} 
	}
	
	/**
	 * APP修改门锁数据
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> updateUserdoordata(JSONObject jsonObj){
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("status", "0");//默认保存失败
		//加锁
		synchronized(lock) {
			
			//当前时间
			String currenttime =  Tools.getCurrentTime();
			
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			try{
				//APP操作员
				String operatorcode = Tools.getKeyValueFromJsonObj("operatorcode", jsonObj);
				//订单编码
				String ordercode = Tools.getKeyValueFromJsonObj("ordercode", jsonObj);
				//门锁数据
				String locklong = Tools.getStr(jsonObj.getString("locklong")); //锁侧板长度
				String lockwidth = Tools.getStr(jsonObj.getString("lockwidth")); //锁侧板宽度
				
				//判断销售员的信息状态
				Employee talker = employeeDao.findByEmployeecodeStr(operatorcode);
				if(talker == null){
					responseMap.put("status", "0");//提交失败
					responseMap.put("result", "提交失败,销售员不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}else if("0".equals(talker.getStatus())){
					responseMap.put("status", "0");//提交失败
					responseMap.put("result", "提交失败,该销售员已被注销");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//订单不能为空
				if(StringUtils.isEmpty(ordercode)){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "订单编号不能为空");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				Userorder userorder = userorderDao.findByOrdercode(ordercode);
				if(userorder == null){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，上传的订单编号不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(!"0".equals(userorder.getStatus())){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "该订单产品已经打包，不能修改门锁数据");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//门锁数据
				if(StringUtils.isNotEmpty(locklong) || StringUtils.isNotEmpty(lockwidth)){
					Userdoordata userdoordata = userdoordataDao.findByOrdercode(ordercode);
					if(userdoordata == null){//插入数据
						userdoordata = new Userdoordata();
						userdoordata.setUsercode(userorder.getUsercode());
						userdoordata.setOrdercode(userorder.getOrdercode());
						userdoordata.setLocklength(locklong);
						userdoordata.setLockwidth(lockwidth);
						userdoordata.setAddercode(operatorcode);
						userdoordata.setAddtime(currenttime);
						userdoordataDao.save(userdoordata);
					}else{ //修改门锁数据
						userdoordata.setLocklength(locklong);
						userdoordata.setLockwidth(lockwidth);
						userdoordataDao.update(userdoordata);
					}
				}
				
				//增加操作日记
				String content = "修改门锁数据，订单编号为:"+userorder.getOrdercode();
				operatorlogService.saveOperatorlog(content, operatorcode);
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");//操作成功
				responseMap.put("result", "操作成功");//操作成功
				
				return responseMap;
				
			}catch(Exception e){
				
				e.printStackTrace();
				//回滚
				txManager.rollback(status);
				
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，操作失败");//
				return responseMap;
			}
		} 
	}
}
