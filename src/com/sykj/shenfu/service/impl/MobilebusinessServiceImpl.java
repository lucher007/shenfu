package com.sykj.shenfu.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.multipart.MultipartFile;

import com.sykj.shenfu.common.DeleteDirectory;
import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.ICellDao;
import com.sykj.shenfu.dao.ICellextendDao;
import com.sykj.shenfu.dao.ICellstationDao;
import com.sykj.shenfu.dao.IEmployeeDao;
import com.sykj.shenfu.dao.IGiftcardDao;
import com.sykj.shenfu.dao.IProductDao;
import com.sykj.shenfu.dao.IProductmodelDao;
import com.sykj.shenfu.dao.IProductmodelrefDao;
import com.sykj.shenfu.dao.IUserDao;
import com.sykj.shenfu.dao.IUserbusinessDao;
import com.sykj.shenfu.dao.IUserdispatchDao;
import com.sykj.shenfu.dao.IUserdispatchfileDao;
import com.sykj.shenfu.dao.IUserdoorDao;
import com.sykj.shenfu.dao.IUserdoordataDao;
import com.sykj.shenfu.dao.IUserorderDao;
import com.sykj.shenfu.dao.IUserorderinfoDao;
import com.sykj.shenfu.dao.IUserproductDao;
import com.sykj.shenfu.dao.IUsertaskDao;
import com.sykj.shenfu.po.Cell;
import com.sykj.shenfu.po.Cellextend;
import com.sykj.shenfu.po.Cellstation;
import com.sykj.shenfu.po.Coderule;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Giftcard;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Product;
import com.sykj.shenfu.po.Productmodel;
import com.sykj.shenfu.po.Productmodelref;
import com.sykj.shenfu.po.User;
import com.sykj.shenfu.po.Userbusiness;
import com.sykj.shenfu.po.Userdispatch;
import com.sykj.shenfu.po.Userdispatchfile;
import com.sykj.shenfu.po.Userdoor;
import com.sykj.shenfu.po.Userdoordata;
import com.sykj.shenfu.po.Userorder;
import com.sykj.shenfu.po.Userorderinfo;
import com.sykj.shenfu.po.Userproduct;
import com.sykj.shenfu.po.Usertask;
import com.sykj.shenfu.service.ICoderuleService;
import com.sykj.shenfu.service.IMobilebusinessService;
import com.sykj.shenfu.service.IOperatorlogService;
import com.sykj.shenfu.service.ISystemparaService;

/**
 * @Title ProductdepotServiceImpl.java
 * @version 1.0 编码规则类
 */
@Service("mobilebusinessService")
public class MobilebusinessServiceImpl implements IMobilebusinessService {
	@Autowired
    private DataSourceTransactionManager txManager;
	@Autowired
	private ICellDao cellDao;
	@Autowired
	private ICellextendDao cellextendDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private ISystemparaService systemparaService;
	@Autowired
	private ICoderuleService coderuleService;
	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private IUsertaskDao taskDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IUserdoorDao userdoorDao;
	@Autowired
	private IProductDao productDao;
	@Autowired
	private IProductmodelDao productmodelDao;
	@Autowired
	private IUserorderDao userorderDao;
	@Autowired
	private IUserbusinessDao userbusinessDao;
	@Autowired
	private IUserorderinfoDao userorderinfoDao;
	@Autowired
	private IUserproductDao userproductDao;
	@Autowired
	private IProductmodelrefDao productmodelrefDao;
	@Autowired
	private IUserdoordataDao userdoordataDao;
	@Autowired
	private ICellstationDao cellstationDao;
	@Autowired
	private IGiftcardDao giftcardDao;
	@Autowired
	private IUserdispatchDao userdispatchDao;
	@Autowired
	private IUserdispatchfileDao userdispatchfileDao;
	
	/*
     * 特殊的instance变量
     * 注：零长度的byte数组对象创建起来将比任何对象都经济――查看编译后的字节码：
     * 生成零长度的byte[]对象只需3条操作码，而Object lock = new Object()则需要7行操作码。
     */
    private static byte[] lock = new byte[0];   
	
    /**
	 * 小区发布抢购
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveBuyCellextend(Cellextend cellextendform, HttpServletRequest request,Employee mobileoperator){
		
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
				
				String extendcode = Tools.getStr(request.getParameter("extendcode"));
				if(StringUtils.isEmpty(extendcode)){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "操作失败，请选择发布的小区编号");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
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
				cellextend.setAcceptercode(mobileoperator.getEmployeecode());//设置接收人
				cellextend.setAcceptertime(currenttime);
				cellextendDao.update(cellextend);
				
				//增加操作日记
				String content = "小区扫楼抢购，小区扫楼编号:" + extendcode + "; 小区名称:"+cellextend.getCellname();
				operatorlogService.saveOperatorlog(content, mobileoperator.getEmployeecode());
				
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");
				responseMap.put("result", "扫楼抢购成功");
				
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
	 * 移动端订单录入-公司派人上门
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveUserorderForVisittype0(Userorder form, HttpServletRequest request,HttpSession session){
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("status", "0");//默认保存失败
		//加锁
		synchronized(lock) {
			
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			try{
				
				//移动端操作员
				Employee  mobileOperator = (Employee)session.getAttribute("MobileOperator");
				
				String phone = Tools.getStr(form.getPhone());
				//客户姓名
				String username = Tools.getStr(form.getUsername());
				//客户地址
				String address = Tools.getStr(form.getAddress());
				//上门类型
				String visittype = Tools.getStr(form.getVisittype());
				//当前时间
				String currenttime =  Tools.getCurrentTime();
				
				
				//判断销售员的信息状态
				Employee saler = employeeDao.findByEmployeecodeStr(mobileOperator.getEmployeecode());
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
					responseMap.put("result", "提交失败,电话号码不能为空");//
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
						user.setSalercode(mobileOperator.getEmployeecode());
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
					user.setSalercode(mobileOperator.getEmployeecode());
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
				operatorlogService.saveOperatorlog(content, mobileOperator.getEmployeecode());
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");//注册成功
				responseMap.put("result", "提交成功");//注册成功
				responseMap.put("usercode", user.getUsercode());
				
				return responseMap;
				
			}catch(Exception e){
				
				e.printStackTrace();
				//回滚
				txManager.rollback(status);
				
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，提交失败");//
				return responseMap;
			}
		} 
	}
	
	/**
	 * 移动端订单录入-亲自上门
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveUserorderForVisittype1(Userorder form, HttpServletRequest request,HttpSession session){
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("status", "0");//默认保存失败
		//加锁
		synchronized(lock) {
			
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			try{
				
				//移动端操作员
				Employee  mobileOperator = (Employee)session.getAttribute("MobileOperator");
				
				String phone = Tools.getStr(form.getPhone());
				//客户姓名
				String username = Tools.getStr(form.getUsername());
				//客户地址
				String address = Tools.getStr(form.getAddress());
				//上门类型
				String visittype = Tools.getStr(form.getVisittype());
				//是否带机械锁芯
				String lockcoreflag = Tools.getStr(form.getLockcoreflag());
				//产品颜色
				String productcolor = Tools.getStr(form.getProductcolor());
				//产品型号
				String modelcode = Tools.getStr(form.getModelcode());
				//锁侧板长度
				String locklength = Tools.getStr(form.getLocklength());
				//产品型号
				String lockwidth = Tools.getStr(form.getLockwidth());
				
				//当前时间
				String currenttime =  Tools.getCurrentTime();
				
				//判断销售员的信息状态
				Employee saler = employeeDao.findByEmployeecodeStr(mobileOperator.getEmployeecode());
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
					responseMap.put("result", "提交失败，电话号码不能为空");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				User user = userDao.findByPhoneStr(phone);
				if(user != null){//客户存在
					if("0".equals(user.getStatus())){
						responseMap.put("status", "0");//
						responseMap.put("result", "提交失败，客户已经提交过，还未处理，请勿再次提交。如确定需要再次提交，请联系公司客服");//
						//回滚
						txManager.rollback(status);
						return responseMap;
					}else if("1".equals(user.getStatus()) || "2".equals(user.getStatus())){
						responseMap.put("status", "0");//
						responseMap.put("result", "提交失败，客户正在安装中，请勿再次提交。如确定需要再次提交，请联系公司客服");//
						//回滚
						txManager.rollback(status);
						return responseMap;
					} else {//客户安装已处理，再次提交，修改客户的销售员编号，上门类型，
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
							user.setSalercode(mobileOperator.getEmployeecode());
							user.setVisittype(visittype);
							user.setSource("0");
							user.setStatus("2");//已生成订单
							userDao.update(user);//修改订户信息
						}
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
					user.setSalercode(mobileOperator.getEmployeecode());
					//审核状态
					user.setCheckstatus("1");
					user.setSource("0");//
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
				
				//产品型号列表
				//String productJson = Tools.getStr(request.getParameter("productJson"));;
				
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
					responseMap.put("result", "提交失败，产品颜色不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(StringUtils.isEmpty(modelcode)){//产品型号为空
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，未选择产品型号");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//查询产品型号信息
				Productmodel productmodel = productmodelDao.findByModelcode(modelcode);
				if(productmodel == null ){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，产品型号不存在");
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
				//首付金额
				String firstpayment = "200";//默认首付款200
				
				//订户信息
				userorder.setUsercode(user.getUsercode());
				userorder.setUsername(user.getUsername());
				userorder.setUsersex(user.getUsersex());
				userorder.setPhone(user.getPhone());
				userorder.setAddress(user.getAddress());
				userorder.setSource(user.getSource());
				userorder.setVisittype(user.getVisittype());
				//订单信息
				userorder.setSalercode(mobileOperator.getEmployeecode()); //销售员
				userorder.setTalkercode(mobileOperator.getEmployeecode());//销售员作为讲解员
				userorder.setVisitorcode(null);//
				userorder.setVisitorstatus("0");//未派测量单
				userorder.setProductfee(productfee);
				userorder.setOtherfee(Integer.valueOf(otherfee));
				userorder.setGaintotalmoney(gaintotalmoney);
				userorder.setTotalmoney(totalmoney);//总金额
				userorder.setFirstpayment(Integer.valueOf(firstpayment));//首付金额
				userorder.setFinalpayment(totalmoney-Integer.valueOf(firstpayment));//尾款金额
				userorder.setFirstarrivalflag("0");
				userorder.setFinalarrivalflag("0");
				userorder.setPaytype("0");
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
				userbusinessForOrder.setOperatorcode(mobileOperator.getEmployeecode());
				userbusinessForOrder.setBusinesstypekey("orderadd");
				userbusinessForOrder.setBusinesstypename("订单生成");
				userbusinessForOrder.setUsercode(user.getUsercode());
				userbusinessForOrder.setTaskcode(null);
				userbusinessForOrder.setOrdercode(userorder.getOrdercode());
				userbusinessForOrder.setDispatchcode(null);
				userbusinessForOrder.setAddtime(currenttime);
				userbusinessForOrder.setContent("生成订单;订单号为： "+userorder.getOrdercode());
				userbusinessForOrder.setSource(user.getSource());//400生成
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
				usertask.setOperatorcode(mobileOperator.getEmployeecode());
				usertask.setVisitstate("0");//访问状态（默认为0，若拒绝安装则改为10）
				usertask.setVisittime(currenttime);//默认已完成上门单上门时间为当前时间
				//默认状态为已处理1
				usertask.setStatus("1");  //已处理
				usertask.setAddtime(currenttime);
				usertask.setDealdate(currenttime);
				usertask.setTasktype("0");//讲解单
				//保存任务单
				taskDao.save(usertask);
				
				//增加业务记录
				Userbusiness userbusinessForTask = new Userbusiness();
				userbusinessForTask.setOperatorcode(mobileOperator.getEmployeecode());
				userbusinessForTask.setBusinesstypekey("taskadd");
				userbusinessForTask.setBusinesstypename("任务单生成");
				userbusinessForTask.setUsercode(user.getUsercode());
				userbusinessForTask.setTaskcode(usertask.getTaskcode());
				userbusinessForTask.setOrdercode(usertask.getOrdercode());
				userbusinessForTask.setDispatchcode(null);
				userbusinessForTask.setAddtime(currenttime);
				userbusinessForTask.setContent("生成任务单;任务单号为： "+usertask.getTaskcode());
				userbusinessForTask.setSource(user.getSource());//400生成
				userbusinessDao.save(userbusinessForTask);
				
				//找到该型号对应的产品类别信息
//				if(StringUtils.isEmpty(productJson)){
//					responseMap.put("status", "0");//保存失败
//					responseMap.put("result", "添加失败，请选择产品信息");
//					//回滚
//					txManager.rollback(status);
//					return responseMap;
//				}
//				//增加关联的产品信息
//				JSONArray productJsonArry = JSONArray.fromObject(productJson);
//				if(productJsonArry == null || productJsonArry.size()< 1){
//					responseMap.put("status", "0");//保存失败
//					responseMap.put("result", "添加失败，请选择产品信息");
//					//回滚
//					txManager.rollback(status);
//					return responseMap;
//				}
				
				//找到该型号对于的产品类别
				List<Productmodelref> productmodelreflist = productmodelrefDao.findByModelcode(modelcode);
				if(productmodelreflist == null || productmodelreflist.size() < 1){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "添加失败，该产品型号未设置产品信息");
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
					} else {
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
				
				//文件参数
				MultipartFile[] files = form.getFiles();
				
				if((StringUtils.isEmpty(locklength) || StringUtils.isEmpty(lockwidth)) && (files == null || files.length <=0)){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "添加失败，门锁图片和门锁数据至少选择一样录入！");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(files != null){
					for(int i=0; i<files.length; i++){
						if (!files[i].isEmpty()) {
							
							String filename = files[i].getOriginalFilename();
							String[] strArray = filename.split("[.]");
							String filetype = strArray[strArray.length - 1];
							
							String userdoor_path = systemparaService.findSystemParaByCodeStr("upload_file_path");
							
							//String userdoor_path = ConfigUtil.getConfigFilePath("userdoor_path", "system.properties");
							//String upload_extend_path = systemparaDao.findByCodeStr("upload_file_path").getValue();
							String folderpath = userdoor_path + File.separatorChar + "userdoor" + File.separatorChar + user.getUsercode()+ File.separatorChar + userorder.getOrdercode();
							File folder = new File(folderpath);
							if (!folder.exists()) {
								folder.mkdirs();
							}
							String preservefilename = Tools.getNowRandom() + "." + filetype;
							String preservepath = folderpath + File.separatorChar + preservefilename;
							File savefile = new File(preservepath);
							files[i].transferTo(savefile);
							
							Userdoor doorform =  new Userdoor();
							doorform.setUsercode(userorder.getUsercode());
							doorform.setOrdercode(userorder.getOrdercode());
							doorform.setFilename(filename);
							doorform.setPreservefilename(preservefilename);
							doorform.setPreserveurl(preservepath);
							doorform.setAddtime(currenttime);
							userdoorDao.save(doorform);
						}
					}
				}
				
				//门锁数据
				if(StringUtils.isNotEmpty(locklength) || StringUtils.isNotEmpty(lockwidth)){
					Userdoordata userdoordata = new Userdoordata();
					userdoordata.setUsercode(userorder.getUsercode());
					userdoordata.setOrdercode(userorder.getOrdercode());
					userdoordata.setLocklength(locklength);
					userdoordata.setLockwidth(lockwidth);
					userdoordata.setAddercode(mobileOperator.getEmployeecode());
					userdoordata.setAddtime(currenttime);
					userdoordataDao.save(userdoordata);
				}
				
				//增加操作日记
				String content = "订单提交，客户电话为:"+phone+";上门类型为："+ user.getVisittypename();
				operatorlogService.saveOperatorlog(content, mobileOperator.getEmployeecode());
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");//注册成功
				responseMap.put("result", "提交成功");//注册成功
				responseMap.put("usercode", user.getUsercode());
				
				return responseMap;
				
			}catch(Exception e){
				
				e.printStackTrace();
				//回滚
				txManager.rollback(status);
				
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，提交失败");//
				return responseMap;
			}
		} 
	}
	
	 /**
	 * 修改门锁数据
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> updateUserdoordata(Userdoordata form, HttpServletRequest request,Employee mobileoperator){
		
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
				
				if(mobileoperator == null){
					responseMap.put("status", "0");//提交失败
					responseMap.put("result", "提交失败,操作员不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}else if("0".equals(mobileoperator.getStatus())){
					responseMap.put("status", "0");//提交失败
					responseMap.put("result", "提交失败,该操作员已被注销");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				Userorder userorder = userorderDao.findByOrdercode(form.getOrdercode());
				if(userorder == null){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "提交失败,订单不存在");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(!"0".equals(userorder.getStatus())){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "提交失败,该订单产品已经打包，不能修改门锁数据");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				Userdoordata userdoordata = userdoordataDao.findByOrdercode(form.getOrdercode());
				if(userdoordata == null){//插入数据
					userdoordata = new Userdoordata();
					userdoordata.setUsercode(form.getUsercode());
					userdoordata.setOrdercode(form.getOrdercode());
					userdoordata.setLocklength(Tools.getStr(form.getLocklength()));
					userdoordata.setLockwidth(Tools.getStr(form.getLockwidth()));
					userdoordata.setAddercode(mobileoperator.getEmployeecode());
					userdoordata.setAddtime(Tools.getCurrentTime());
					userdoordataDao.save(userdoordata);
				}else{ //修改门锁数据
					userdoordata.setLocklength(Tools.getStr(form.getLocklength()));
					userdoordata.setLockwidth(Tools.getStr(form.getLockwidth()));
					userdoordataDao.update(userdoordata);
				}
				
				//增加操作日记
				String content = "修改门锁数据，订单编号为:"+userorder.getOrdercode();
				operatorlogService.saveOperatorlog(content, mobileoperator.getEmployeecode());
				
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
	 * 修改产品价格
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> updateProductfee(Userorder form, HttpServletRequest request,Employee mobileoperator){
		
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
				
				//订单号
				String ordercode = Tools.getStr(request.getParameter("ordercode"));
				//新的产品费用
				String newproductfee = Tools.getStr(request.getParameter("newproductfee"));
				
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
				
				
				if(Integer.valueOf(newproductfee) > productmodel.getMaxprice()){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，产品价格不能大于该产品型号的最大上浮价格："+productmodel.getMaxprice());
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(Integer.valueOf(newproductfee) < productmodel.getMinprice()){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，产品价格不能小于该产品型号的最小下浮价格："+productmodel.getMinprice());
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				Integer totalmoney = Integer.valueOf(newproductfee) + userorder.getOtherfee();
				userorder.setProductfee(Integer.valueOf(newproductfee));
				userorder.setTotalmoney(totalmoney);//总金额
				userorder.setFinalpayment(totalmoney-Integer.valueOf(userorder.getFirstpayment()));//尾款金额
				
				//保存订单
				userorderDao.update(userorder);
				
				//增加生成订单业务记录
				Userbusiness userbusinessForOrder = new Userbusiness();
				userbusinessForOrder.setOperatorcode(mobileoperator.getEmployeecode());
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
				operatorlogService.saveOperatorlog(content, mobileoperator.getEmployeecode());
				
				//事务提交
				txManager.commit(status);
				responseMap.put("status", "1");//注册成功
				responseMap.put("result", "操作成功");
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
	 * 修改门锁图片-上传新的门锁图片
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveUserdoorInfo(Userorder form, HttpServletRequest request,HttpSession session){
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("status", "0");//默认保存失败
		//加锁
		synchronized(lock) {
			
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			try{
				
				//移动端操作员
				Employee  mobileOperator = (Employee)session.getAttribute("MobileOperator");
				//当前时间
				String currenttime =  Tools.getCurrentTime();
				
				//订单号
				String ordercode = Tools.getStr(form.getOrdercode());
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
					responseMap.put("result", "提交失败，订单号不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//文件参数
				MultipartFile[] files = form.getFiles();
				
				if(files == null || files.length <=0){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "上传失败，请选择需要上传的图片！");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(files != null){
					for(int i=0; i<files.length; i++){
						if (!files[i].isEmpty()) {
							
							String filename = files[i].getOriginalFilename();
							String[] strArray = filename.split("[.]");
							String filetype = strArray[strArray.length - 1];
							
							String userdoor_path = systemparaService.findSystemParaByCodeStr("upload_file_path");
							
							//String userdoor_path = ConfigUtil.getConfigFilePath("userdoor_path", "system.properties");
							//String upload_extend_path = systemparaDao.findByCodeStr("upload_file_path").getValue();
							String folderpath = userdoor_path + File.separatorChar + "userdoor" + File.separatorChar + userorder.getUsercode()+ File.separatorChar + userorder.getOrdercode();
							File folder = new File(folderpath);
							if (!folder.exists()) {
								folder.mkdirs();
							}
							String preservefilename = Tools.getNowRandom() + "." + filetype;
							String preservepath = folderpath + File.separatorChar + preservefilename;
							File savefile = new File(preservepath);
							files[i].transferTo(savefile);
							
							Userdoor doorform =  new Userdoor();
							doorform.setUsercode(userorder.getUsercode());
							doorform.setOrdercode(userorder.getOrdercode());
							doorform.setFilename(filename);
							doorform.setPreservefilename(preservefilename);
							doorform.setPreserveurl(preservepath);
							doorform.setAddtime(currenttime);
							userdoorDao.save(doorform);
						}
					}
				}
				
				//增加操作日记
				String content = "门锁图片提交，客户电话为:"+userorder.getPhone()+";订单编号为："+ userorder.getOrdercode();
				operatorlogService.saveOperatorlog(content, mobileOperator.getEmployeecode());
				
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
				responseMap.put("result", "数据异常，提交失败");//
				return responseMap;
			}
		} 
	}
	
	
	/**
	 * 保存订单-处理任务单
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveUserorderForUsertask(Userorder form, HttpServletRequest request,HttpSession session){
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("status", "0");//默认保存失败
		//加锁
		synchronized(lock) {
			
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			try{
				
				//移动端操作员
				Employee  mobileOperator = (Employee)session.getAttribute("MobileOperator");
				//任务单编号
				String taskcode =  Tools.getStr(form.getTaskcode());
				//是否带机械锁芯
				String lockcoreflag = Tools.getStr(form.getLockcoreflag());
				//产品颜色
				String productcolor = Tools.getStr(form.getProductcolor());
				//产品型号
				String modelcode = Tools.getStr(form.getModelcode());
				//锁侧板长度
				String locklength = Tools.getStr(form.getLocklength());
				//产品型号
				String lockwidth = Tools.getStr(form.getLockwidth());
				
				//当前时间
				String currenttime =  Tools.getCurrentTime();
				
				//判断销售员的信息状态
				Employee saler = employeeDao.findByEmployeecodeStr(mobileOperator.getEmployeecode());
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
				
				//任务单编号不能为空
				if(StringUtils.isEmpty(taskcode)){
					responseMap.put("status", "0");//
					responseMap.put("result", "提交失败,任务单号不能为空");//
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
						otherfee = 1000;
					}
				}
				
				//产品型号列表
				//String productJson = Tools.getStr(request.getParameter("productJson"));;
				
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
					responseMap.put("result", "提交失败，产品颜色不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(StringUtils.isEmpty(modelcode)){//产品型号为空
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，未选择产品型号");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//查询产品型号信息
				Productmodel productmodel = productmodelDao.findByModelcode(modelcode);
				if(productmodel == null ){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，产品型号不存在");
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
				//首付金额
				String firstpayment = "200";//默认首付款200
				
				//订户信息
				userorder.setUsercode(usertask.getUsercode());
				userorder.setUsername(usertask.getUsername());
				userorder.setUsersex(usertask.getUsersex());
				userorder.setPhone(usertask.getPhone());
				userorder.setAddress(usertask.getAddress());
				userorder.setSource(usertask.getSource());
				userorder.setVisittype(usertask.getVisittype());
				//订单信息
				userorder.setSalercode(mobileOperator.getEmployeecode()); //销售员
				userorder.setTalkercode(mobileOperator.getEmployeecode());//销售员作为讲解员
				userorder.setVisitorcode(null);//
				userorder.setVisitorstatus("0");//未派测量单
				userorder.setProductfee(productfee);
				userorder.setOtherfee(Integer.valueOf(otherfee));
				userorder.setGaintotalmoney(gaintotalmoney);
				userorder.setTotalmoney(totalmoney);//总金额
				userorder.setFirstpayment(Integer.valueOf(firstpayment));//首付金额
				userorder.setFinalpayment(totalmoney-Integer.valueOf(firstpayment));//尾款金额
				userorder.setFirstarrivalflag("0");
				userorder.setFinalarrivalflag("0");
				userorder.setPaytype("0");
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
				userbusinessForOrder.setOperatorcode(mobileOperator.getEmployeecode());
				userbusinessForOrder.setBusinesstypekey("orderadd");
				userbusinessForOrder.setBusinesstypename("订单生成");
				userbusinessForOrder.setUsercode(usertask.getUsercode());
				userbusinessForOrder.setTaskcode(null);
				userbusinessForOrder.setOrdercode(userorder.getOrdercode());
				userbusinessForOrder.setDispatchcode(null);
				userbusinessForOrder.setAddtime(currenttime);
				userbusinessForOrder.setContent("生成订单;订单号为： "+userorder.getOrdercode());
				userbusinessForOrder.setSource(usertask.getSource());
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
				
				//找到该型号对于的产品类别
				List<Productmodelref> productmodelreflist = productmodelrefDao.findByModelcode(modelcode);
				if(productmodelreflist == null || productmodelreflist.size() < 1){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "添加失败，该产品型号未设置产品信息");
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
					} else {
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
				
				//文件参数
				MultipartFile[] files = form.getFiles();
				
				if((StringUtils.isEmpty(locklength) || StringUtils.isEmpty(lockwidth)) && (files == null || files.length <=0)){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "添加失败，门锁图片和门锁数据至少选择一样录入！");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(files != null){
					for(int i=0; i<files.length; i++){
						if (!files[i].isEmpty()) {
							
							String filename = files[i].getOriginalFilename();
							String[] strArray = filename.split("[.]");
							String filetype = strArray[strArray.length - 1];
							
							String userdoor_path = systemparaService.findSystemParaByCodeStr("upload_file_path");
							
							//String userdoor_path = ConfigUtil.getConfigFilePath("userdoor_path", "system.properties");
							//String upload_extend_path = systemparaDao.findByCodeStr("upload_file_path").getValue();
							String folderpath = userdoor_path + File.separatorChar + "userdoor" + File.separatorChar + user.getUsercode()+ File.separatorChar + userorder.getOrdercode();
							File folder = new File(folderpath);
							if (!folder.exists()) {
								folder.mkdirs();
							}
							String preservefilename = Tools.getNowRandom() + "." + filetype;
							String preservepath = folderpath + File.separatorChar + preservefilename;
							File savefile = new File(preservepath);
							files[i].transferTo(savefile);
							
							Userdoor doorform =  new Userdoor();
							doorform.setUsercode(userorder.getUsercode());
							doorform.setOrdercode(userorder.getOrdercode());
							doorform.setFilename(filename);
							doorform.setPreservefilename(preservefilename);
							doorform.setPreserveurl(preservepath);
							doorform.setAddtime(currenttime);
							userdoorDao.save(doorform);
						}
					}
				}
				
				//门锁数据
				if(StringUtils.isNotEmpty(locklength) || StringUtils.isNotEmpty(lockwidth)){
					Userdoordata userdoordata = new Userdoordata();
					userdoordata.setUsercode(userorder.getUsercode());
					userdoordata.setOrdercode(userorder.getOrdercode());
					userdoordata.setLocklength(locklength);
					userdoordata.setLockwidth(lockwidth);
					userdoordata.setAddercode(mobileOperator.getEmployeecode());
					userdoordata.setAddtime(currenttime);
					userdoordataDao.save(userdoordata);
				}
				
				//增加操作日记
				String content = "任务单处理，客户电话为:"+usertask.getPhone() + ";任务单号为："+ usertask.getTaskcode();
				operatorlogService.saveOperatorlog(content, mobileOperator.getEmployeecode());
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");//注册成功
				responseMap.put("result", "提交成功");//注册成功
				responseMap.put("usercode", user.getUsercode());
				
				return responseMap;
				
			}catch(Exception e){
				
				e.printStackTrace();
				//回滚
				txManager.rollback(status);
				
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，提交失败");//
				return responseMap;
			}
		} 
	}
	
	/**
	 * 保存扫楼驻点
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveCellstation(Cellstation form, HttpServletRequest request,Employee mobileoperator){
		
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
				String salercode = mobileoperator.getEmployeecode();
				//小区发布编号
				String extendcode = form.getExtendcode();
				//驻点开始时间
				String starttime = form.getStarttime();
				//驻点结束时间
				String endtime = form.getEndtime();
				//讲解人员数目
				Integer talkernumber = form.getTalkernumber();
				//测量人员数目
				Integer visitornumber = form.getVisitornumber();
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
				cellstation.setTalkernumber(talkernumber);
				cellstation.setVisitornumber(visitornumber);
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
	 * 保存优惠卡生成
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveGiftcard(Giftcard form, HttpServletRequest request,Employee mobileoperator){
		
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
				String salercode = mobileoperator.getEmployeecode();
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
				
				//产品型号为空
				if(StringUtils.isEmpty(form.getModelcode())){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "提交失败,产品型号不能为空");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//查询订户信息
				Productmodel productmodel = productmodelDao.findByModelcode(form.getModelcode());
				if(productmodel == null){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "提交失败,选择的产品型号不存在");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				//输入的优惠金额不能大于产品型号允许的最大优惠金额
				if(form.getAmount() > productmodel.getDiscountgain()){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "提交失败,输入的优惠金额不能大于产品型号允许的最大优惠金额");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//有效开始时间
				if(StringUtils.isEmpty(form.getStarttime())){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "提交失败,有效开始时间不能为空");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//有效结束时间
				if(StringUtils.isEmpty(form.getEndtime())){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "提交失败,有效结束时间不能为空");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(form.getEndtime().compareTo(form.getStarttime()) < 0){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "提交失败,有效结束时间不能早于开始时间");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//保存驻点单对象
				Giftcard giftcard = new Giftcard();
				//获取推广号码
				String extendcode = mobileoperator.getExtendcode();
				//优惠卡金额
				Integer amount = form.getAmount();
				//将优惠金额转成4位字符串，不足4位前面补0
				String amountStr = StringUtils.leftPad(amount.toString(), 4, "0");
				//优惠卡号
				String giftcardno = "";
				Boolean flag = true;
				while(flag){
					String date = Tools.getCurrentTimeByFormat("yyMMdd");
					String randomStr = Tools.getRandomPass(6,("0123456789ABCDEFGHJKLMNPQRSTUVWXYZ").toCharArray());
					
					//优惠卡号=6推广号+4位优惠卡金额+4位时间数+6位随机数
					giftcardno = extendcode + amountStr + date + randomStr;
					
					Giftcard giftcardForm = giftcardDao.findByGiftcardno(giftcardno);
					if(giftcardForm == null){//数据库里没有此优惠卡号，采用此优惠卡号，跳出循环
						giftcard.setGiftcardno(giftcardno);
						flag = false;
					}
				}
				giftcard.setModelcode(form.getModelcode());
				giftcard.setModelname(productmodel.getModelname());
				giftcard.setAmount(form.getAmount());
				giftcard.setStatus("0");//未使用
				giftcard.setEmployeecode(mobileoperator.getEmployeecode());
				giftcard.setAddtime(Tools.getCurrentTime());
				giftcard.setStarttime(form.getStarttime() + " 00:00:00");
				giftcard.setEndtime(form.getEndtime() + " 23:59:59");
				
				giftcardDao.save(giftcard);
				
				//增加操作日记
				String content = "生成优惠卡号，优惠卡号为:" + giftcard.getGiftcardno() + "; 优惠金额:" + giftcard.getAmount();
				operatorlogService.saveOperatorlog(content, salercode);
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");//注册成功
				responseMap.put("result", "提交成功");
				responseMap.put("giftcardno", giftcard.getGiftcardno());//返回优惠卡号
				responseMap.put("addtime", giftcard.getAddtime());//返回优惠卡号
				
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
	 * 工单处理
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveDispatchdata(Userdispatch form, HttpServletRequest request,Employee mobileoperator){
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
				String currenttime =  Tools.getCurrentTime();
				
				//订单号
				String dispatchcode = Tools.getStr(form.getDispatchcode());
				//订单不能为空
				if(StringUtils.isEmpty(dispatchcode)){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "提交失败，工单号不能为空");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				Userdispatch userdispatch = userdispatchDao.findByDispatchcode(dispatchcode);
				if(userdispatch == null){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，该工单不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				
				//修改工单回单状态
				userdispatch.setStatus(form.getStatus());// 设置工单状态为1(已派单)
				userdispatch.setDealdate(currenttime);
				userdispatch.setDealresult(form.getDealresult());
				userdispatchDao.saveReply(userdispatch);
				
				//增加业务记录
				Userbusiness userbusiness = new Userbusiness();
				userbusiness.setOperatorcode(userdispatch.getInstallercode());
				userbusiness.setBusinesstypekey("dispatchreply");
				userbusiness.setBusinesstypename("工单已处理");
				userbusiness.setUsercode(userdispatch.getUsercode());
				//userbusiness.setTaskcode(order.getTaskcode());
				userbusiness.setOrdercode(userdispatch.getOrdercode());
				userbusiness.setDispatchcode(userdispatch.getDispatchcode());
				userbusiness.setAddtime(currenttime);
				userbusiness.setContent("工单已处理:工单号为： "+userdispatch.getDispatchcode());
				userbusiness.setSource("1");//APP平台操作
				userbusinessDao.save(userbusiness);
				
				//文件参数
				MultipartFile[] files = form.getFiles();
				
				if(files == null || files.length <=0){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "上传失败，请选择需要上传的图片！");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(files != null){
					
					String upload_file_path = systemparaService.findSystemParaByCodeStr("upload_file_path");
					
					//String userdoor_path = ConfigUtil.getConfigFilePath("userdoor_path", "system.properties");
					//String upload_extend_path = systemparaDao.findByCodeStr("upload_file_path").getValue();
					String folderpath = upload_file_path + File.separatorChar + "userdispatch" + File.separatorChar +  userdispatch.getOrdercode()+ File.separatorChar + userdispatch.getDispatchcode() ;
					File folder = new File(folderpath);
					if (!folder.exists()) {
						folder.mkdirs();
					}
					
					for(int i=0; i<files.length; i++){
						if (!files[i].isEmpty()) {
							
							String filename = files[i].getOriginalFilename();
							String[] strArray = filename.split("[.]");
							String filetype = strArray[strArray.length - 1];
							
							String preservefilename = Tools.getNowRandom() + "." + filetype;
							String preservepath = folderpath + File.separatorChar + preservefilename;
							File savefile = new File(preservepath);
							files[i].transferTo(savefile);
							
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
				}
				
				//增加操作日记
				String content = "工单安装处理，客户电话为:"+userdispatch.getPhone()+";订单编号为："+ userdispatch.getOrdercode()+";工单号号为："+ userdispatch.getDispatchcode();
				operatorlogService.saveOperatorlog(content, mobileoperator.getEmployeecode());
				
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
				responseMap.put("result", "数据异常，提交失败");//
				return responseMap;
			}
		} 
	}
	
	/**
	 * 修改门锁图片-上传新的门锁图片
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveUserdispatchfileInfo(Userdispatch form, HttpServletRequest request,HttpSession session){
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("status", "0");//默认保存失败
		//加锁
		synchronized(lock) {
			
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			try{
				
				//移动端操作员
				Employee  mobileOperator = (Employee)session.getAttribute("MobileOperator");
				//当前时间
				String currenttime =  Tools.getCurrentTime();
				
				//工单号
				String dispatchcode = Tools.getStr(form.getDispatchcode());
				//订单不能为空
				if(StringUtils.isEmpty(dispatchcode)){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "工单号不能为空");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				Userdispatch userdispatch = userdispatchDao.findByDispatchcode(dispatchcode);
				if(userdispatch == null){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，工单不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//文件参数
				MultipartFile[] files = form.getFiles();
				
				if(files == null || files.length <=0){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "上传失败，请选择需要上传的图片！");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(files != null){
					for(int i=0; i<files.length; i++){
						if (!files[i].isEmpty()) {
							
							String filename = files[i].getOriginalFilename();
							String[] strArray = filename.split("[.]");
							String filetype = strArray[strArray.length - 1];
							
							String upload_file_path = systemparaService.findSystemParaByCodeStr("upload_file_path");
							
							//String userdoor_path = ConfigUtil.getConfigFilePath("userdoor_path", "system.properties");
							//String upload_extend_path = systemparaDao.findByCodeStr("upload_file_path").getValue();
							String folderpath = upload_file_path + File.separatorChar + "userdispatch" + File.separatorChar +  userdispatch.getOrdercode()+ File.separatorChar + userdispatch.getDispatchcode() ;
							File folder = new File(folderpath);
							if (!folder.exists()) {
								folder.mkdirs();
							}
							String preservefilename = Tools.getNowRandom() + "." + filetype;
							String preservepath = folderpath + File.separatorChar + preservefilename;
							File savefile = new File(preservepath);
							files[i].transferTo(savefile);
							
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
				}
				
				//增加操作日记
				String content = "安装图片提交，客户电话为:"+userdispatch.getPhone()+";订单编号为："+ userdispatch.getOrdercode()+";工单编号为："+ userdispatch.getDispatchcode();
				operatorlogService.saveOperatorlog(content, mobileOperator.getEmployeecode());
				
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
				responseMap.put("result", "数据异常，提交失败");//
				return responseMap;
			}
		} 
	}
	
}
