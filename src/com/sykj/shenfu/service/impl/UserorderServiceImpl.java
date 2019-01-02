package com.sykj.shenfu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.IEmployeeDao;
import com.sykj.shenfu.dao.IGiftcardDao;
import com.sykj.shenfu.dao.IProductDao;
import com.sykj.shenfu.dao.IProductdepotDao;
import com.sykj.shenfu.dao.IProductinoutDao;
import com.sykj.shenfu.dao.IProductmodelDao;
import com.sykj.shenfu.dao.IProductmodelrefDao;
import com.sykj.shenfu.dao.IRechargevipDao;
import com.sykj.shenfu.dao.ISaleenergyinfoDao;
import com.sykj.shenfu.dao.ISaleenergyruleDao;
import com.sykj.shenfu.dao.ISalegaininfoDao;
import com.sykj.shenfu.dao.ISalegainruleDao;
import com.sykj.shenfu.dao.ISalerrechargevipinfoDao;
import com.sykj.shenfu.dao.IUserDao;
import com.sykj.shenfu.dao.IUserbusinessDao;
import com.sykj.shenfu.dao.IUserdeliveryDao;
import com.sykj.shenfu.dao.IUserdispatchDao;
import com.sykj.shenfu.dao.IUserorderDao;
import com.sykj.shenfu.dao.IUserorderinfoDao;
import com.sykj.shenfu.dao.IUserproductDao;
import com.sykj.shenfu.dao.IUserproductreplaceDao;
import com.sykj.shenfu.dao.IUsertaskDao;
import com.sykj.shenfu.po.Coderule;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Giftcard;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Product;
import com.sykj.shenfu.po.Productdepot;
import com.sykj.shenfu.po.Productinout;
import com.sykj.shenfu.po.Productmodel;
import com.sykj.shenfu.po.Productmodelref;
import com.sykj.shenfu.po.Rechargevip;
import com.sykj.shenfu.po.Saleenergyinfo;
import com.sykj.shenfu.po.Saleenergyrule;
import com.sykj.shenfu.po.Salegaininfo;
import com.sykj.shenfu.po.Salegainrule;
import com.sykj.shenfu.po.Salerrechargevipinfo;
import com.sykj.shenfu.po.User;
import com.sykj.shenfu.po.Userbusiness;
import com.sykj.shenfu.po.Userdelivery;
import com.sykj.shenfu.po.Userdispatch;
import com.sykj.shenfu.po.Userorder;
import com.sykj.shenfu.po.Userorderinfo;
import com.sykj.shenfu.po.Userproduct;
import com.sykj.shenfu.po.Userproductreplace;
import com.sykj.shenfu.po.Usertask;
import com.sykj.shenfu.service.ICoderuleService;
import com.sykj.shenfu.service.IOperatorlogService;
import com.sykj.shenfu.service.ISystemparaService;
import com.sykj.shenfu.service.IUserorderService;


/**
 * @Title ProductdepotServiceImpl.java
 * @version 1.0 产品库存实现类
 */
@Service("userorderService")
public class UserorderServiceImpl implements IUserorderService {
	@Autowired
	private IUserorderDao userorderDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private ICoderuleService coderuleService;
	@Autowired
	private IProductmodelDao productmodelDao;
	@Autowired
    private DataSourceTransactionManager txManager;
	@Autowired
	private IUsertaskDao taskDao;
	@Autowired
	private IUserbusinessDao userbusinessDao;
	@Autowired
	private IUserorderinfoDao userorderinfoDao;
	@Autowired
	private IProductmodelrefDao productmodelrefDao;
	@Autowired
	private IProductDao productDao;
	@Autowired
	private IUserproductDao userproductDao;
	@Autowired
	private IProductdepotDao productdepotDao;
	@Autowired
	private IProductinoutDao productinoutDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private ISaleenergyruleDao saleenergyruleDao;
	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private ISalegainruleDao salegainruleDao;
	@Autowired
	private ISalegaininfoDao salegaininfoDao;
	@Autowired
	private ISaleenergyinfoDao saleenergyinfoDao;
	@Autowired
	private IRechargevipDao rechargevipDao;
	@Autowired
	private ISalerrechargevipinfoDao salerrechargevipinfoDao;
	@Autowired
	private IUserdispatchDao dispatchDao;
	@Autowired
	private IUserdeliveryDao userdeliveryDao;
	@Autowired
	private ISystemparaService systemparaService;
	@Autowired
	private IGiftcardDao giftcardDao;
	@Autowired
	private IUserproductreplaceDao userproductreplaceDao;
	
	/*
     * 特殊的instance变量
     * 注：零长度的byte数组对象创建起来将比任何对象都经济――查看编译后的字节码：
     * 生成零长度的byte[]对象只需3条操作码，而Object lock = new Object()则需要7行操作码。
     */
    private static byte[] lock = new byte[0];   
	
	/**
	 * 保存订单
	 */
	public Map<String, Object> saveUserorder(Userorder userorderform, HttpServletRequest request, Operator operator) {
		//加锁
		synchronized(lock) {
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			
			Map<String, Object> responseMap = new HashMap<String, Object>();
			
			//当前时间
			String currenttime = Tools.getCurrentTime();
			
			try{
				
				//产品型号
				String modelcode = Tools.getStr(userorderform.getModelcode());
				//是否带机械锁芯
				String lockcoreflag = Tools.getStr(userorderform.getLockcoreflag());
				//产品颜色
				String productcolor = Tools.getStr(userorderform.getProductcolor());
				//支付类型
				String paytype = Tools.getStr(userorderform.getPaytype());
				
				Usertask usertask = taskDao.findByTaskcodeStr(userorderform.getTaskcode());
				if(usertask == null){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，任务单不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if("1".equals(usertask.getStatus())){ //如果任务单已处理
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，任务单已经处理");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(StringUtils.isNotEmpty(usertask.getOrdercode())){ //如果任务单已处理
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，从任务单已经生成订单");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
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
				
				User user = userDao.findByUsercodeStr(usertask.getUsercode());
				user.setStatus("2");//修改成已生成订单
				userDao.update(user);
				
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
				//任务单信息
				userorder.setSalercode(usertask.getSalercode());
				userorder.setTalkercode(usertask.getTalkercode());
				userorder.setVisitorcode(null);
				userorder.setVisitorstatus("0");//未派任务单
				userorder.setProductfee(productfee);
				userorder.setOtherfee(Integer.valueOf(otherfee));
				userorder.setGaintotalmoney(gaintotalmoney);
				userorder.setTotalmoney(totalmoney);//总金额
				userorder.setFirstpayment(Integer.valueOf(firstpayment));//首付金额
				userorder.setFinalpayment(totalmoney-Integer.valueOf(firstpayment));//尾款金额
				userorder.setFirstarrivalflag("0");
				userorder.setFinalarrivalflag("0");
				userorder.setPaytype(paytype);
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
				userbusinessForOrder.setOperatorcode(null);
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
//					for (int i = 0; i < productinfoArray.size(); i++) {
//						JSONObject productJsonObject = productinfoArray.getJSONObject(i);
//						String typecode_app = productJsonObject.getString("typecode");
//						String productcode_app = productJsonObject.getString("productcode");
//						if(typecode_OA.equals(typecode_app)){
//							if(StringUtils.isNotEmpty(productcode_app)){//页面选择了产品信息
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
//								userproduct.setPrice(Integer.valueOf(0));
//								userproduct.setSaleprice(Integer.valueOf(0));
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
				
				//增加操作日记
				String content = "添加 订单， 订单号:"+userorder.getOrdercode();
				operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
				
				//事务提交
				txManager.commit(status);
				responseMap.put("status", "1");//注册成功
				responseMap.put("result", "任务单处理成功");
				return responseMap;
			}catch(Exception e){
			    e.printStackTrace();
				//回滚
				txManager.rollback(status);
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，任务单处理失败");//
				return responseMap;
			}
		} 
	}   
	
	/**
	 * 完善订单
	 */
	public Map<String, Object> saveFillUserorder(Userorder userorderform, HttpServletRequest request, Operator operator) {
		//加锁
		synchronized(lock) {
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			
			Map<String, Object> responseMap = new HashMap<String, Object>();
			
			//当前时间
			String currenttime = Tools.getCurrentTime();
			
			try{
				
				Usertask usertask = taskDao.findByTaskcodeStr(userorderform.getTaskcode());
				if(usertask == null){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，任务单不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				
				if(StringUtils.isEmpty(usertask.getOrdercode())){ //如果订单号不为空
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，此任务单未关联订单号");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				String modelcode = userorderform.getModelcode();
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
				
				//保存型号关联的产品类别信息
				String productJson = request.getParameter("productJson");
				if(StringUtils.isEmpty(productJson)){
					//回滚
					txManager.rollback(status);
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "请选择关联的产品信息");//
					return responseMap;
				}
				
				//增加关联的产品信息
				JSONArray productinfoArray = JSONArray.fromObject(productJson);
				
				//修改任务单状态
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
				
				//找出数据库里存在的订单产品信息
				List<Userproduct> userproductlist = userproductDao.findUserproductListByOrdercode(usertask.getOrdercode());
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
					for (int i = 0; i < productinfoArray.size(); i++) {
						JSONObject productJsonObject = productinfoArray.getJSONObject(i);
						String typecode_app = productJsonObject.getString("typecode");
						String productcode_app = productJsonObject.getString("productcode");
						if(typecode_OA.equals(typecode_app)){
							if(StringUtils.isNotEmpty(productcode_app)){//如果不为空，即更新
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
				
				
				//增加操作日记
				String content = "处理任务单， 任务单号:"+usertask.getTaskcode();
				operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
				
				//事务提交
				txManager.commit(status);
				responseMap.put("status", "1");//注册成功
				responseMap.put("result", "任务单处理成功");
				return responseMap;
			}catch(Exception e){
			    e.printStackTrace();
				//回滚
				txManager.rollback(status);
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，任务单处理失败");//
				return responseMap;
			}
		} 
	}   
	
	
	/**
	 * 订单打包
	 */
	public Map<String, Object> savePackUserorder(Userorder userorderform, HttpServletRequest request, Operator operator) {
		//加锁
		synchronized(lock) {
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			
			Map<String, Object> responseMap = new HashMap<String, Object>();
			
			//当前时间
			String currenttime = Tools.getCurrentTime();
			
			try{
				
				//修改订单的状态
				Userorder userorder = userorderDao.findById(userorderform.getId());
				if(!"0".equals(userorder.getStatus())){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "打包失败，此订单不是新生成状态");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
//				if("0".equals(userorder.getPaytype()) && !"2".equals(userorder.getFirstarrivalflag())){
//					responseMap.put("status", "0");//保存失败
//					responseMap.put("result", "打包失败，此订单首付款还未到账");
//					//回滚
//					txManager.rollback(status);
//					return responseMap;
//				}
				
				userorder.setStatus("1");//已打包
				userorder.setOperatetime(currenttime);
				userorderDao.updateStatus(userorder);
				
				//修改订单关联的产品信息
				String userproductJson = userorderform.getUserproductJson();
				JSONArray userproductJsonArry = JSONArray.fromObject(userproductJson);
				for (int i = 0; i < userproductJsonArry.size(); i++){
		            JSONObject userproductJsonObject = userproductJsonArry.getJSONObject(i);
		            String userproductid = userproductJsonObject.getString("id");
					String productidentfy = userproductJsonObject.getString("productidentfy");
					String depotrackcode = userproductJsonObject.getString("depotrackcode");
					
					//修改库存量
					if(StringUtils.isEmpty(productidentfy)){
						responseMap.put("status", "0");//保存失败
						responseMap.put("result", "打包失败，请输入库存中的产品唯一标识码");
						//回滚
						txManager.rollback(status);
						return responseMap;
					}
					//获取产品库信息
					Productdepot depotForm = productdepotDao.findByProductidentfy(productidentfy);
					if(depotForm == null){
						responseMap.put("status", "0");//保存失败
						responseMap.put("result", "打包失败，该产品唯一标识码不存在");
						//回滚
						txManager.rollback(status);
						return responseMap;
					}
					
					if(depotForm.getDepotamount() < 1 || "0".equals(depotForm.getDepotstatus()) ){
						responseMap.put("status", "0");//保存失败
						responseMap.put("result", "打包失败，该产品唯一标识码已经无库存");
						//回滚
						txManager.rollback(status);
						return responseMap;
					}
					
					//现有库存量
					int depotamount = depotForm.getDepotamount();
					//修改现有库存量
					depotForm.setDepotamount(depotamount-1);
					//如果库存量等于1，修改库存状态为0
					if(depotamount == 1){
						depotForm.setDepotstatus("0"); //无库存
					}
					
					productdepotDao.update(depotForm);
					
					
					//修改订单的产品标识
					Userproduct userproductForm = new Userproduct();
					userproductForm.setId(Integer.valueOf(userproductid));
					userproductForm = userproductDao.findById(Integer.valueOf(userproductid));
					userproductForm.setProductidentfy(productidentfy);
					userproductForm.setDepotrackcode(depotrackcode);
					//userproductForm.setAddtime(Tools.getCurrentTime());
					userproductForm.setInoutercode(operator.getEmployeecode());//谁操作系统就是谁出入库
					//修改产品软件版本号
					userproductForm.setProductversion(depotForm.getProductversion());
					userproductDao.update(userproductForm);
					
					//增加产品出库记录
					Productinout productinout = new Productinout();
					productinout.setProductcode(depotForm.getProductcode());
					productinout.setProductname(depotForm.getProductname());
					productinout.setProductidentfy(depotForm.getProductidentfy());
					productinout.setType("1");
					productinout.setInoutercode(operator.getEmployeecode());
					productinout.setInoutamount(1);//出库量
					productinout.setAddtime(currenttime);
					productinout.setOperatorcode(operator.getEmployeecode());
					productinout.setInoutstatus("1");
					productinout.setDepotrackcode(depotForm.getDepotrackcode());
					productinoutDao.save(productinout);
					
				}
				
				//增加业务记录
				Userbusiness userbusiness = new Userbusiness();
				userbusiness.setOperatorcode(operator.getEmployeecode());
				userbusiness.setBusinesstypekey("orderpack");
				userbusiness.setBusinesstypename("订单打包");
				userbusiness.setUsercode(userorder.getUsercode());
				userbusiness.setOrdercode(userorder.getOrdercode());
				userbusiness.setTaskcode(null);
				userbusiness.setDispatchcode(null);
				userbusiness.setAddtime(Tools.getCurrentTime());
				userbusiness.setContent("订单打包;订单号为： "+userorder.getOrdercode());
				userbusiness.setSource("0");//PC平台操作
				userbusinessDao.save(userbusiness);
				
				//增加操作日记
				String contentlog = "订单打包， 订单号:"+userorder.getOrdercode();
				operatorlogService.saveOperatorlog(contentlog, operator.getEmployeecode());
				
				//事务提交
				txManager.commit(status);
				responseMap.put("status", "1");//注册成功
				responseMap.put("result", "订单打包成功");
				return responseMap;
			}catch(Exception e){
			    e.printStackTrace();
				//回滚
				txManager.rollback(status);
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，订单打包失败");//
				return responseMap;
			}
		} 
	}  
	
	/**
	 * 修改订单产品型号
	 */
	public Map<String, Object> updateUserorder(Userorder userorderform, HttpServletRequest request, Operator operator) {
		//加锁
		synchronized(lock) {
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			
			Map<String, Object> responseMap = new HashMap<String, Object>();
			//当前时间
			String currenttime = Tools.getCurrentTime();
			
			try{
				
				//产品型号
				String modelcode = Tools.getStr(userorderform.getModelcode());
				//是否带机械锁芯
				String lockcoreflag = Tools.getStr(userorderform.getLockcoreflag());
				//产品颜色
				String productcolor = Tools.getStr(userorderform.getProductcolor());
				//支付类型
				String paytype = Tools.getStr(userorderform.getPaytype());
				
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
				
				Userorder userorder = userorderDao.findById(userorderform.getId());
				
				if(userorder == null){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，此订单不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(!"0".equals(userorder.getStatus())){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，此订单产品已经打包，不能修改");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(!"0".equals(userorder.getFinalarrivalflag())){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，此订单已经支付尾款，不能修改");
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
				
				//产品原价
				Integer productfee_old = productmodel.getPrice();
				//获取提成的总金额
				Integer gaintotalmoney = productfee_old + Integer.valueOf(otherfee);
				//产品销售价格
				Integer productfee = productmodel.getSellprice();
				//总金额=产品费用+其他费用
				Integer totalmoney = productfee + Integer.valueOf(otherfee);
				
				//首付金额
				Integer firstpayment = userorder.getFirstpayment();//提取数据库旧首付款
				userorder.setProductfee(productfee);
				userorder.setOtherfee(otherfee);
				userorder.setGaintotalmoney(gaintotalmoney);//总金额
				userorder.setDiscountfee(0);//清空优惠金额
				userorder.setTotalmoney(totalmoney);//总金额
				userorder.setFirstpayment(Integer.valueOf(firstpayment));//首付金额
				userorder.setFinalpayment(totalmoney-Integer.valueOf(firstpayment));//尾款金额
				userorder.setProductcolor(productcolor);
				userorder.setLockcoreflag(lockcoreflag);
				userorder.setPaytype(paytype);
				if("1".equals(lockcoreflag)){//带机械锁心，必须上门测量
					userorder.setVisitorflag("1");//需要派人上门测量
				}else{
					userorder.setVisitorflag("0");//不需要派人上门测量
				}
				
				//销售提成重置
				userorder.setDiscountgain(productmodel.getDiscountgain()); //优惠权限
				userorder.setFixedgain(productmodel.getFixedgain());       //固定返利
				userorder.setManagergain(productmodel.getManagergain());   //销售管家提成
				if(StringUtils.isNotEmpty(userorder.getSalercode())){      
					Employee saler = employeeDao.findByEmployeecodeStr(userorder.getSalercode());
					if(saler != null){
						userorder.setManagercode(saler.getManagercode());  //销售管家编号
					}
				}
				
				//保存订单修改
				userorderDao.update(userorder);
				
				//先删除订单详情
				userorderinfoDao.deleteByOrdercode(userorder.getOrdercode());
				//在添加订单详情
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
				userorderinfo.setAddtime(Tools.getCurrentTime());
				userorderinfo.setPrice(productmodel.getPrice());
				userorderinfo.setSellprice(productmodel.getSellprice());
				userorderinfo.setBuytype("0");//型号整体购买
				userorderinfo.setModelcode(productmodel.getModelcode());
				userorderinfo.setModelname(productmodel.getModelname());
				userorderinfoDao.save(userorderinfo);
				
				//增加订单产品信息
				//1-先删除订单的产品
				userproductDao.deleteByOrdercode(userorder.getOrdercode());
				
				//找到该型号对于的产品类别
				List<Productmodelref> productmodelreflist = productmodelrefDao.findByModelcode(modelcode);
				if(productmodelreflist == null || productmodelreflist.size() < 1){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，该产品型号未设置产品信息");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
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
				
				//增加操作日记
				String content = "修改 订单信息， 订单编号:"+userorder.getOrdercode();
				operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
				
				//事务提交
				txManager.commit(status);
				responseMap.put("status", "1");//注册成功
				responseMap.put("result", "订单修改成功");
				return responseMap;
			}catch(Exception e){
			    e.printStackTrace();
				//回滚
				txManager.rollback(status);
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，订单修改失败");//
				return responseMap;
			}
		} 
	} 
	
	/**
	 * 修改订单产品参数
	 */
	public Map<String, Object> updateUserproduct(Userorder userorderform, HttpServletRequest request, Operator operator) {
		//加锁
		synchronized(lock) {
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			
			Map<String, Object> responseMap = new HashMap<String, Object>();
			
			//当前时间
			String currenttime = Tools.getCurrentTime();
			
			try{
				
				//保存型号关联的产品类别信息
				String productJson = request.getParameter("productJson");
				if(StringUtils.isEmpty(productJson)){
					//回滚
					txManager.rollback(status);
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "请选择关联的产品信息");//
					return responseMap;
				}
				
				//增加关联的产品信息
				JSONArray productinfoArray = JSONArray.fromObject(productJson);
				
				Userorder userorder = userorderDao.findById(userorderform.getId());
				if(userorder == null){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，此订单不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(!"0".equals(userorder.getStatus())){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，此订单产品已经打包，不能修改");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
//				if(!"0".equals(userorder.getFinalarrivalflag())){
//					responseMap.put("status", "0");//保存失败
//					responseMap.put("result", "提交失败，此订单已经支付尾款，不能修改");
//					//回滚
//					txManager.rollback(status);
//					return responseMap;
//				}
				
				//是否需要修改订单信息
				Boolean updateOrderflag = false;
				//修改订单的支付类型
				if(!userorderform.getPaytype().equals(userorder.getPaytype())){
					userorder.setPaytype(userorderform.getPaytype());
					updateOrderflag = true;
				}
				
				//修改订单是否需要上门测量
				if(!userorderform.getVisitorflag().equals(userorder.getVisitorflag())){
					userorder.setVisitorflag(userorderform.getVisitorflag());
					updateOrderflag = true;
				}
				
				if(StringUtils.isNotEmpty(userorderform.getTalkercode())){
					//修改订单的讲解人
					if(!userorderform.getTalkercode().equals(userorder.getTalkercode())){
						userorder.setTalkercode(userorderform.getTalkercode());
						updateOrderflag = true;
					}
				}
				
				if(StringUtils.isNotEmpty(userorderform.getOrderinfo())){
					//修改订单详情
					if(!userorderform.getOrderinfo().equals(userorder.getOrderinfo())){
						userorder.setOrderinfo(userorderform.getOrderinfo());
						updateOrderflag = true;
					}
				}
				
				
				if(updateOrderflag){
					userorderDao.update(userorder);
				}
				
				
				//增加订单产品信息
				//1-先删除订单的产品
				//userproductDao.deleteByOrdercode(userorder.getOrdercode());
				
				//找出数据库里存在的订单产品信息
				List<Userproduct> userproductlist = userproductDao.findUserproductListByOrdercode(userorder.getOrdercode());
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
					for (int i = 0; i < productinfoArray.size(); i++) {
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
				
				//增加操作日记
				String content = "修改 订单产品信息， 订单编号:"+userorder.getOrdercode();
				operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
				
				//事务提交
				txManager.commit(status);
				responseMap.put("status", "1");//注册成功
				responseMap.put("result", "订单产品修改成功");
				return responseMap;
			}catch(Exception e){
			    e.printStackTrace();
				//回滚
				txManager.rollback(status);
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，订单产品修改失败");//
				return responseMap;
			}
		} 
	} 
	
	/**
	 * 保存订单结单
	 */
	public Map<String, Object> saveCheckUserorder(Userorder userorderform, HttpServletRequest request, Operator operator) {
		//加锁
		synchronized(lock) {
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			
			Map<String, Object> responseMap = new HashMap<String, Object>();
			
			//当前时间
			String currenttime = Tools.getCurrentTime();
			
			try{
				
				//修改订单状态
				Userorder order = userorderDao.findById(userorderform.getId());
				if(order == null){
					//回滚
					txManager.rollback(status);
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "结单失败，此订单不存在");//
					return responseMap;
				}
				
				if(!"5".equals(order.getStatus())){
					//回滚
					txManager.rollback(status);
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "此订单不是完成安装状态，不能结单");
					return responseMap;
				}
				
				order.setStatus("6");//修改订单为审核结单状态
				order.setOperatetime(currenttime); 
				userorderDao.updateStatus(order);
				
				
				//修改客户为安装完成状态
				User user = userDao.findByUsercodeStr(order.getUsercode());
				if(user != null){
					user.setStatus("3");//已完成安装
					user.setSalercode("");//清空关联销售员
					user.setVisittype("0");//修改上门类型为公司派人讲解和测量
					userDao.update(user);
				}
				
				//增加业务记录
				Userbusiness userbusiness = new Userbusiness();
				userbusiness.setOperatorcode(operator.getEmployeecode());
				userbusiness.setBusinesstypekey("ordercheck");
				userbusiness.setBusinesstypename("订单结单");
				userbusiness.setUsercode(order.getUsercode());
				userbusiness.setTaskcode(null);
				userbusiness.setOrdercode(order.getOrdercode());
				userbusiness.setDispatchcode(null);
				userbusiness.setAddtime(currenttime);
				userbusiness.setContent("订单已结单;订单号为： "+order.getOrdercode());
				userbusiness.setSource("1");//PC平台操作
				userbusinessDao.save(userbusiness);
				
				//增加操作日记
				String content = "订单结单， 订单号为:"+order.getOrdercode();
				operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
				
				//查询销售员的行动力积分
				HashMap<String,Integer> saleenergyruleHp = new HashMap<String,Integer>();
				List<Saleenergyrule> saleenergyrulelist = saleenergyruleDao.queryByList(new Saleenergyrule());
				for (Saleenergyrule saleenergyrule : saleenergyrulelist) {
					saleenergyruleHp.put(saleenergyrule.getEnergycode(), saleenergyrule.getEnergyrate());
				}
				
				//销售员提成计算
				Salegaininfo salegaininfo = new Salegaininfo();
				salegaininfo.setUsercode(order.getUsercode());
				salegaininfo.setUsername(order.getUsername());
				salegaininfo.setPhone(order.getPhone());
				salegaininfo.setAddress(order.getAddress());
				salegaininfo.setSource(order.getSource());
				salegaininfo.setVisittype(order.getVisittype());
				salegaininfo.setTaskcode(order.getTaskcode());
				salegaininfo.setSalercode(order.getSalercode());
				salegaininfo.setVisitorcode(order.getVisitorcode());
				salegaininfo.setOrdercode(order.getOrdercode());
				salegaininfo.setTotalmoney(order.getGaintotalmoney());
				salegaininfo.setStatus("0");//未提取
				salegaininfo.setEnergyscore(0);
				
				//行动力积分
//				Saleenergyinfo saleenergyinfo = new Saleenergyinfo();
//				saleenergyinfo.setUsercode(order.getUsercode());
//				saleenergyinfo.setUsername(order.getUsername());
//				saleenergyinfo.setPhone(order.getPhone());
//				saleenergyinfo.setAddress(order.getAddress());
//				saleenergyinfo.setSource(order.getSource());
//				saleenergyinfo.setVisittype(order.getVisittype());
//				saleenergyinfo.setTaskcode(order.getTaskcode());
//				saleenergyinfo.setSalercode(order.getSalercode());
//				saleenergyinfo.setVisitorcode(order.getVisitorcode());
//				saleenergyinfo.setOrdercode(order.getOrdercode());
//				saleenergyinfo.setTotalmoney(order.getGaintotalmoney());
//				saleenergyinfo.setStatus("0");//未兑换提成
//				saleenergyinfo.setGainrate(0); //兑换比例暂时为0
//				saleenergyinfo.setGainmoney(0);//兑换提成默认为0
				
//				//1-销售来源的订单渠道提成
//				if(StringUtils.isNotEmpty(order.getSalercode()) && order.getGaintotalmoney() > 0){//销售渠道,且销售员不为空,且销售金额大于0
//					//查找该销售员的上级销售员
//					Employee saler = employeeDao.findByEmployeecodeStr(order.getSalercode());
//					if(saler != null){//销售员存在
//						//销售员销售渠道提成
//						Salegainrule salegain_sale_channel = salegainruleDao.findByGaincode(Salegainrule.gaincode_sale_channel);
//						if(salegain_sale_channel != null && salegain_sale_channel.getGainrate() > 0){
//							salegaininfo.setGainercode(order.getSalercode());
//							salegaininfo.setGaincode(salegain_sale_channel.getGaincode());
//							salegaininfo.setGainname(salegain_sale_channel.getGainname());
//							salegaininfo.setGainrate(salegain_sale_channel.getGainrate());
//							Integer gainmoney = 0;
//							if("0".equals(salegain_sale_channel.getGainform())){//百分比
//								gainmoney = order.getGaintotalmoney() * salegain_sale_channel.getGainrate() / 100;
//							}else if("1".equals(salegain_sale_channel.getGainform())){//绝对数值
//								gainmoney = salegain_sale_channel.getGainrate();
//							}
//							
//							salegaininfo.setGainmoney(gainmoney);
//							salegaininfo.setAddtime(currenttime);
//							salegaininfoDao.save(salegaininfo);
//							
//							//直接增加销售员提成记录
//							//employeeDao.updateSalescoreByEmployeecode(salegaininfo.getGainercode(), gainmoney);
//						}
//						
//						//销售员销售渠道行动力积分
//						if(saleenergyruleHp.get(Salegainrule.gaincode_sale_channel) > 0){
//							saleenergyinfo.setGainercode(order.getSalercode());
//							saleenergyinfo.setGaincode(salegain_sale_channel.getGaincode());
//							saleenergyinfo.setGainname(salegain_sale_channel.getGainname());
//							saleenergyinfo.setEnergyscore(saleenergyruleHp.get(Salegainrule.gaincode_sale_channel));
//							saleenergyinfo.setAddtime(currenttime);
//							saleenergyinfoDao.save(saleenergyinfo);
//						}
						
						//销售员充值VIP提成
//						Salegainrule salegain_recharge_vip = salegainruleDao.findByGaincode(Salegainrule.gaincode_recharge_vip);
//						if(salegain_recharge_vip != null && salegain_recharge_vip.getGainrate() > 0){
//							if(StringUtils.isNotEmpty(saler.getRechargevipcode())){//销售的充值VIP等级不为空
//								//查询销售员的充值VIP的提成次数规则信息
//								Rechargevip rechargevip = rechargevipDao.findByRechargevipcode(saler.getRechargevipcode());
//								if(rechargevip != null && rechargevip.getGaintimes() > 0){
//									//获取当月字符串
//									String currentmonth = Tools.getCurrentTimeByFormat("yyyy-MM");
//									//获取销售员的提成次数信息
//									Salerrechargevipinfo salerrechargevipinfo = salerrechargevipinfoDao.findByEmployeecode(order.getSalercode());
//									
//									boolean gainflag = false;//本次能够获得充值VIP提成标志，默认不能
//									
//									//如果该销售员还没有提成次数，则新插入一条提成次数
//									if(salerrechargevipinfo == null){
//										salerrechargevipinfo = new Salerrechargevipinfo();
//										salerrechargevipinfo.setEmployeecode(order.getSalercode());
//										salerrechargevipinfo.setPhone(order.getPhone());
//										salerrechargevipinfo.setGainmonth(currentmonth);
//										salerrechargevipinfo.setGaintimes(1);
//										//新插入
//										salerrechargevipinfoDao.save(salerrechargevipinfo);
//										
//										//本次能获得充值VIP提成
//										gainflag = true;
//										
//									//如果销售员提成信息月份不是当月月份，修改提成月份和提成次数
//									} else if (!currentmonth.equals(salerrechargevipinfo.getGainmonth())){//修改提成次数信息
//										salerrechargevipinfo.setGainmonth(currentmonth);//更新提成月份
//										salerrechargevipinfo.setGaintimes(1);//更新提成次数
//										//更新
//										salerrechargevipinfoDao.update(salerrechargevipinfo);
//										
//										//本次能获得充值VIP提成
//										gainflag = true;
//										
//									} else if (rechargevip.getGaintimes() > salerrechargevipinfo.getGaintimes()){
//										int gaintimes = salerrechargevipinfo.getGaintimes();
//										salerrechargevipinfo.setGaintimes(gaintimes + 1);//更新提成次数,增加一次
//										//更新
//										salerrechargevipinfoDao.update(salerrechargevipinfo);
//										
//										//本次能获得充值VIP提成
//										gainflag = true;
//									}
//									
//									//判断是否能够获得充值VIP提成
//									if(gainflag){//能够
//										salegaininfo.setGainercode(order.getSalercode());
//										salegaininfo.setGaincode(salegain_recharge_vip.getGaincode());
//										salegaininfo.setGainname(salegain_recharge_vip.getGainname());
//										salegaininfo.setGainrate(salegain_recharge_vip.getGainrate());
//										Integer gainmoney  = order.getTotalmoney() * salegain_recharge_vip.getGainrate() / 100;
//										salegaininfo.setGainmoney(gainmoney);
//										//行动力分
//										salegaininfo.setEnergyscore(0);
//										
//										salegaininfo.setAddtime(currenttime);
//										//salegaininfo.setStatus("0");//未提取
//										salegaininfoDao.save(salegaininfo);
//										
//										//直接增加销售员提成记录
//										employeeDao.updateSalescoreByEmployeecode(salegaininfo.getGainercode(), gainmoney);
//									}
//								}
//							}
//						}
						
//		                //上级销售员提成
//						if(StringUtils.isNotEmpty(saler.getParentemployeecode())){
//							//找出上一级销售员
//							Employee saler_upper_one = employeeDao.findByEmployeecodeStr(saler.getParentemployeecode());
//							if(saler_upper_one != null){
//								Salegainrule salegain_upper_one = salegainruleDao.findByGaincode(Salegainrule.gaincode_upper_one);
//								if(salegain_upper_one != null && salegain_upper_one.getGainrate() > 0 ){
//										salegaininfo.setGainercode(saler_upper_one.getEmployeecode());
//										salegaininfo.setGaincode(salegain_upper_one.getGaincode());
//										salegaininfo.setGainname(salegain_upper_one.getGainname());
//										salegaininfo.setGainrate(salegain_upper_one.getGainrate());
//										
//										Integer gainmoney = 0;
//										if("0".equals(salegain_upper_one.getGainform())){//百分比
//											gainmoney = order.getGaintotalmoney() * salegain_upper_one.getGainrate() / 100;
//										}else if("1".equals(salegain_upper_one.getGainform())){//绝对数值
//											gainmoney = salegain_upper_one.getGainrate();
//										}
//										
//										salegaininfo.setGainmoney(gainmoney);
//										salegaininfo.setAddtime(currenttime);
//										salegaininfoDao.save(salegaininfo);
//										
//										//直接增加销售员提成记录
//										//employeeDao.updateSalescoreByEmployeecode(salegaininfo.getGainercode(), gainmoney);
//								}
//								
//								//行动力积分
//								if(saleenergyruleHp.get(Salegainrule.gaincode_upper_one) > 0){
//									saleenergyinfo.setGainercode(saler_upper_one.getEmployeecode());
//									saleenergyinfo.setGaincode(salegain_upper_one.getGaincode());
//									saleenergyinfo.setGainname(salegain_upper_one.getGainname());
//									saleenergyinfo.setEnergyscore(saleenergyruleHp.get(Salegainrule.gaincode_upper_one));
//									saleenergyinfo.setAddtime(currenttime);
//									saleenergyinfoDao.save(saleenergyinfo);
//								}
//								
//								
//								//5-上上级提成
//								if(StringUtils.isNotEmpty(saler_upper_one.getParentemployeecode())){
//									//找出上上级销售员
//									Employee saler_upper_two = employeeDao.findByEmployeecodeStr(saler_upper_one.getParentemployeecode());
//									if(saler_upper_two != null){
//										Salegainrule salegain_upper_two = salegainruleDao.findByGaincode(Salegainrule.gaincode_upper_two);
//										if(salegain_upper_two != null && salegain_upper_two.getGainrate() > 0 ){
//												salegaininfo.setGainercode(saler_upper_two.getEmployeecode());
//												salegaininfo.setGaincode(salegain_upper_two.getGaincode());
//												salegaininfo.setGainname(salegain_upper_two.getGainname());
//												salegaininfo.setGainrate(salegain_upper_two.getGainrate());
//												
//												Integer gainmoney = 0;
//												if("0".equals(salegain_upper_two.getGainform())){//百分比
//													gainmoney = order.getGaintotalmoney() * salegain_upper_two.getGainrate() / 100;
//												}else if("1".equals(salegain_upper_two.getGainform())){//绝对数值
//													gainmoney = salegain_upper_two.getGainrate();
//												}
//												
//												salegaininfo.setGainmoney(gainmoney);
//												salegaininfo.setAddtime(currenttime);
//												salegaininfoDao.save(salegaininfo);
//												//直接增加销售员提成记录
//												//employeeDao.updateSalescoreByEmployeecode(salegaininfo.getGainercode(), gainmoney);
//										}
//										
//										//行动力积分
//										if(saleenergyruleHp.get(Salegainrule.gaincode_upper_two) > 0){
//											saleenergyinfo.setGainercode(saler_upper_two.getEmployeecode());
//											saleenergyinfo.setGaincode(salegain_upper_two.getGaincode());
//											saleenergyinfo.setGainname(salegain_upper_two.getGainname());
//											saleenergyinfo.setEnergyscore(saleenergyruleHp.get(Salegainrule.gaincode_upper_two));
//											saleenergyinfo.setAddtime(currenttime);
//											saleenergyinfoDao.save(saleenergyinfo);
//										}
//									}
//								}
//							}
//						}
//					}
//				}
				
//				//2-讲解提成
//				if(order.getTotalmoney() > 0){//销售金额大于0
//					Salegainrule salegain_explain_sell = salegainruleDao.findByGaincode(Salegainrule.gaincode_explain_sell);
//					if(salegain_explain_sell != null && salegain_explain_sell.getGainrate() > 0 ){
//						if(StringUtils.isNotEmpty(order.getTalkercode())){//有讲解员
//							if("1".equals(order.getSource())){ //400_OA系统录入的客户
//								if("0".equals(order.getVisittype())){ //公司派人讲解测量
//									salegaininfo.setGainercode(order.getTalkercode());
//									salegaininfo.setGaincode(salegain_explain_sell.getGaincode());
//									salegaininfo.setGainname(salegain_explain_sell.getGainname());
//									salegaininfo.setGainrate(salegain_explain_sell.getGainrate());
//									
//									Integer gainmoney = 0;
//									if("0".equals(salegain_explain_sell.getGainform())){//百分比
//										gainmoney = order.getGaintotalmoney() * salegain_explain_sell.getGainrate() / 100;
//									}else if("1".equals(salegain_explain_sell.getGainform())){//绝对数值
//										gainmoney = salegain_explain_sell.getGainrate();
//									}
//									
//									//讲解提成
//									salegaininfo.setGainmoney(gainmoney);
//									//行动力分
//									salegaininfo.setEnergyscore(0);
//									salegaininfo.setAddtime(currenttime);
//									//salegaininfo.setStatus("0");//未提取
//									salegaininfoDao.save(salegaininfo);
//									
//									//直接增加
//									//employeeDao.updateSalescoreByEmployeecode(salegaininfo.getGainercode(), gainmoney);
//								}else{ //400讲解，提成归纳为400部门虚拟员工
//									salegaininfo.setGainercode("000000000002");//400部门虚拟员工账号为000000000002
//									salegaininfo.setGaincode(salegain_explain_sell.getGaincode());
//									salegaininfo.setGainname(salegain_explain_sell.getGainname());
//									salegaininfo.setGainrate(salegain_explain_sell.getGainrate());
//									
//									Integer gainmoney = 0;
//									if("0".equals(salegain_explain_sell.getGainform())){//百分比
//										gainmoney = order.getGaintotalmoney() * salegain_explain_sell.getGainrate() / 100;
//									}else if("1".equals(salegain_explain_sell.getGainform())){//绝对数值
//										gainmoney = salegain_explain_sell.getGainrate();
//									}
//									
//									//讲解提成
//									salegaininfo.setGainmoney(gainmoney);
//									//行动力分
//									salegaininfo.setEnergyscore(0);
//									salegaininfo.setAddtime(currenttime);
//									//salegaininfo.setStatus("0");//未提取
//									salegaininfoDao.save(salegaininfo);
//									
//									//直接增加
//									//employeeDao.updateSalescoreByEmployeecode(salegaininfo.getGainercode(), gainmoney);
//								}
//							}else{
//								salegaininfo.setGainercode(order.getTalkercode());
//								salegaininfo.setGaincode(salegain_explain_sell.getGaincode());
//								salegaininfo.setGainname(salegain_explain_sell.getGainname());
//								salegaininfo.setGainrate(salegain_explain_sell.getGainrate());
//								
//								Integer gainmoney = 0;
//								if("0".equals(salegain_explain_sell.getGainform())){//百分比
//									gainmoney = order.getGaintotalmoney() * salegain_explain_sell.getGainrate() / 100;
//								}else if("1".equals(salegain_explain_sell.getGainform())){//绝对数值
//									gainmoney = salegain_explain_sell.getGainrate();
//								}
//								
//								//讲解提成
//								salegaininfo.setGainmoney(gainmoney);
//								//行动力分
//								salegaininfo.setEnergyscore(0);
//								salegaininfo.setAddtime(currenttime);
//								//salegaininfo.setStatus("0");//未提取
//								salegaininfoDao.save(salegaininfo);
//								
//								//直接增加
//								//employeeDao.updateSalescoreByEmployeecode(salegaininfo.getGainercode(), gainmoney);
//							}
//						}
//					}
//				}
				
//				//3-上门测量数据提成
//				if(order.getTotalmoney() > 0){//销售金额大于0
//					Salegainrule salegain_measure_calculation = salegainruleDao.findByGaincode(Salegainrule.gaincode_measure_calculation);
//					if(salegain_measure_calculation != null && salegain_measure_calculation.getGainrate() > 0 ){
//						
//						if(StringUtils.isNotEmpty(order.getVisitorcode())){
//							salegaininfo.setGainercode(order.getVisitorcode());
//							salegaininfo.setGaincode(salegain_measure_calculation.getGaincode());
//							salegaininfo.setGainname(salegain_measure_calculation.getGainname());
//							salegaininfo.setGainrate(salegain_measure_calculation.getGainrate());
//							
//							Integer gainmoney = 0;
//							if("0".equals(salegain_measure_calculation.getGainform())){//百分比
//								gainmoney = order.getGaintotalmoney() * salegain_measure_calculation.getGainrate() / 100;
//							}else if("1".equals(salegain_measure_calculation.getGainform())){//绝对数值
//								gainmoney = salegain_measure_calculation.getGainrate();
//							}
//							
//							salegaininfo.setGainmoney(gainmoney);
//							//行动力分
//							salegaininfo.setEnergyscore(0);
//							
//							salegaininfo.setAddtime(currenttime);
//							//salegaininfo.setStatus("0");//未提取
//							salegaininfoDao.save(salegaininfo);
//							
//							//直接增加
//							//employeeDao.updateSalescoreByEmployeecode(salegaininfo.getGainercode(), gainmoney);
//						}
//					}
//				}
				
				//客服服务费
//				if(order.getTotalmoney() > 0){//销售金额大于0
//					Salegainrule salegain_customer_service = salegainruleDao.findByGaincode(Salegainrule.gaincode_customer_service);
//					if(salegain_customer_service != null && salegain_customer_service.getGainrate() > 0 ){
//						salegaininfo.setGainercode("000000000003");//客服服务费员工账号为000000000003
//						salegaininfo.setGaincode(salegain_customer_service.getGaincode());
//						salegaininfo.setGainname(salegain_customer_service.getGainname());
//						salegaininfo.setGainrate(salegain_customer_service.getGainrate());
//						
//						Integer gainmoney = 0;
//						if("0".equals(salegain_customer_service.getGainform())){//百分比
//							gainmoney = order.getGaintotalmoney() * salegain_customer_service.getGainrate() / 100;
//						}else if("1".equals(salegain_customer_service.getGainform())){//绝对数值
//							gainmoney = salegain_customer_service.getGainrate();
//						}
//						
//						//客服服务费提成
//						salegaininfo.setGainmoney(gainmoney);
//						//行动力分
//						salegaininfo.setEnergyscore(0);
//						salegaininfo.setAddtime(currenttime);
//						//salegaininfo.setStatus("0");//未提取
//						salegaininfoDao.save(salegaininfo);
//						
//						//直接增加
//						//employeeDao.updateSalescoreByEmployeecode(salegaininfo.getGainercode(), gainmoney);
//					}
//				}
				
				//价格利润提成
//				if(!"1".equals(order.getSource()) && order.getTotalmoney() > 0){//不是400录入的订单且销售金额大于0
//					if(StringUtils.isNotEmpty(order.getModelcode())){
//						Productmodel productmodel = productmodelDao.findByModelcode(order.getModelcode());
//						if(productmodel != null){
//							//价格利润
//							Integer price_profit = order.getProductfee() - productmodel.getMinprice();
//							if(price_profit > 0){ //有利润，分配给渠道方和讲解人
//								//价格利润渠道方提成
//								if(StringUtils.isNotEmpty(order.getSalercode())){
//									//查找该销售员的上级销售员
//									Employee saler = employeeDao.findByEmployeecodeStr(order.getSalercode());
//									if(saler != null){//销售员存在
//										Salegainrule sale_channel_profit = salegainruleDao.findByGaincode(Salegainrule.sale_channel_profit);
//										if(sale_channel_profit != null && sale_channel_profit.getGainrate() > 0 ){
//											salegaininfo.setGainercode(order.getSalercode());
//											salegaininfo.setGaincode(sale_channel_profit.getGaincode());
//											salegaininfo.setGainname(sale_channel_profit.getGainname());
//											salegaininfo.setGainrate(sale_channel_profit.getGainrate());
//											
//											Integer gainmoney = 0;
//											if("0".equals(sale_channel_profit.getGainform())){//百分比
//												gainmoney = (order.getGaintotalmoney()-order.getProductfee()) * sale_channel_profit.getGainrate() / 100;
//											}else if("1".equals(sale_channel_profit.getGainform())){//绝对数值
//												gainmoney = sale_channel_profit.getGainrate();
//											}
//											
//											//提成金额
//											salegaininfo.setGainmoney(gainmoney);
//											//行动力分
//											salegaininfo.setEnergyscore(0);
//											salegaininfo.setAddtime(currenttime);
//											//salegaininfo.setStatus("0");//未提取
//											salegaininfoDao.save(salegaininfo);
//											
//											//直接增加
//											//employeeDao.updateSalescoreByEmployeecode(salegaininfo.getGainercode(), gainmoney);
//										}
//									}
//								}
//								
//								//价格利润讲解方提成
//								if(StringUtils.isNotEmpty(order.getTalkercode())){
//									//查找该销售员的上级销售员
//									Employee talker = employeeDao.findByEmployeecodeStr(order.getTalkercode());
//									if(talker != null){//讲解员存在
//										Salegainrule explain_sell_profit = salegainruleDao.findByGaincode(Salegainrule.explain_sell_profit);
//										if(explain_sell_profit != null && explain_sell_profit.getGainrate() > 0 ){
//											salegaininfo.setGainercode(order.getTalkercode());
//											salegaininfo.setGaincode(explain_sell_profit.getGaincode());
//											salegaininfo.setGainname(explain_sell_profit.getGainname());
//											salegaininfo.setGainrate(explain_sell_profit.getGainrate());
//											
//											Integer gainmoney = 0;
//											if("0".equals(explain_sell_profit.getGainform())){//百分比
//												gainmoney = (order.getGaintotalmoney()-order.getProductfee()) * explain_sell_profit.getGainrate() / 100;
//											}else if("1".equals(explain_sell_profit.getGainform())){//绝对数值
//												gainmoney = explain_sell_profit.getGainrate();
//											}
//											
//											//提成金额
//											salegaininfo.setGainmoney(gainmoney);
//											//行动力分
//											salegaininfo.setEnergyscore(0);
//											salegaininfo.setAddtime(currenttime);
//											//salegaininfo.setStatus("0");//未提取
//											salegaininfoDao.save(salegaininfo);
//											
//											//直接增加
//											//employeeDao.updateSalescoreByEmployeecode(salegaininfo.getGainercode(), gainmoney);
//										}
//									}
//								}
//							}
//						}
//					}
//				}
				
				Salegainrule salegain_install_lock = salegainruleDao.findByGaincode(Salegainrule.gaincode_install_lock);
				if(salegain_install_lock != null && "1".equals(salegain_install_lock.getStatus())){//有效
					if(StringUtils.isNotEmpty(order.getInstallercode())){//安装人不为空
						salegaininfo.setGainercode(order.getInstallercode());
						salegaininfo.setGaincode(salegain_install_lock.getGaincode());
						salegaininfo.setGainname(salegain_install_lock.getGainname());
						salegaininfo.setGainrate(salegain_install_lock.getGainrate());
						
						Integer gainmoney = 0;
						if("0".equals(salegain_install_lock.getGainform())){//百分比
							gainmoney = order.getGaintotalmoney() * salegain_install_lock.getGainrate() / 100;
						}else if("1".equals(salegain_install_lock.getGainform())){//绝对数值
							gainmoney = salegain_install_lock.getGainrate();
						}
						
						salegaininfo.setGainmoney(gainmoney);
						//行动力分
						salegaininfo.setEnergyscore(0);
						
						salegaininfo.setAddtime(currenttime);
						//salegaininfo.setStatus("0");//未提取
						salegaininfoDao.save(salegaininfo);
					}
				}
				//优惠权限提成
				Salegainrule salegain_discount_gain = salegainruleDao.findByGaincode(Salegainrule.discount_gain);
				//有效
				if(salegain_discount_gain != null && "1".equals(salegain_discount_gain.getStatus())){
					if( order.getDiscountgain() != null && order.getDiscountgain() > 0 ){
						//获取订单的优惠金额
						Integer discountfee = 0;
						if(order.getDiscountfee() != null){
							discountfee = order.getDiscountfee();
						}
						//优惠权限提成大于优惠的金额，剩下的金额才有提成
						if(order.getDiscountgain() - order.getDiscountfee() > 0){
							if(StringUtils.isNotEmpty(order.getSalercode())){//销售渠道,且销售员不为空,且销售金额大于0
								//查找该销售员的销售员
								Employee saler = employeeDao.findByEmployeecodeStr(order.getSalercode());
								if(saler != null){//销售员存在
									salegaininfo.setGainercode(order.getSalercode());
									salegaininfo.setGaincode(salegain_discount_gain.getGaincode());
									salegaininfo.setGainname(salegain_discount_gain.getGainname());
									salegaininfo.setGainrate(salegain_discount_gain.getGainrate());
									
									Integer gainmoney = 0;
									if("0".equals(salegain_discount_gain.getGainform())){//百分比
										gainmoney = (order.getDiscountgain()-order.getDiscountfee()) * salegain_discount_gain.getGainrate() / 100;
									}else if("1".equals(salegain_discount_gain.getGainform())){//绝对数值
										gainmoney = order.getDiscountgain() - order.getDiscountfee();
									}
									salegaininfo.setGainmoney(gainmoney);
									//行动力分
									salegaininfo.setEnergyscore(0);
									salegaininfo.setAddtime(currenttime);
									//salegaininfo.setStatus("0");//未提取
									salegaininfoDao.save(salegaininfo);
								}
							
							}
						}
					}
				}
				
				//固定权限提成
				Salegainrule salegain_fixed_gain = salegainruleDao.findByGaincode(Salegainrule.fixed_gain);
				if(salegain_fixed_gain != null && "1".equals(salegain_fixed_gain.getStatus())){//有效
					if( order.getFixedgain() != null && order.getFixedgain() > 0 ){
						if(StringUtils.isNotEmpty(order.getSalercode())){//销售渠道,且销售员不为空,且销售金额大于0
							//查找该销售员的销售员
							Employee saler = employeeDao.findByEmployeecodeStr(order.getSalercode());
							if(saler != null){//销售员存在
								salegaininfo.setGainercode(order.getSalercode());
								salegaininfo.setGaincode(salegain_fixed_gain.getGaincode());
								salegaininfo.setGainname(salegain_fixed_gain.getGainname());
								salegaininfo.setGainrate(salegain_fixed_gain.getGainrate());
								Integer gainmoney = 0;
								if("0".equals(salegain_fixed_gain.getGainform())){//百分比
									gainmoney = order.getFixedgain() * salegain_fixed_gain.getGainrate() / 100;
								}else if("1".equals(salegain_fixed_gain.getGainform())){//绝对数值
									gainmoney = order.getFixedgain();
								}
								salegaininfo.setGainmoney(gainmoney);
								//行动力分
								salegaininfo.setEnergyscore(0);
								salegaininfo.setAddtime(currenttime);
								//salegaininfo.setStatus("0");//未提取
								salegaininfoDao.save(salegaininfo);
							}
						}
					}
				}
				
				//销售管家提成
				Salegainrule salegain_manager_gain = salegainruleDao.findByGaincode(Salegainrule.manager_gain);
				if("1".equals(salegain_manager_gain.getStatus())){//有效
					if( order.getManagergain() != null && order.getManagergain() > 0 ){
						if(StringUtils.isNotEmpty(order.getManagercode())){//销售管家编号不能为空,且销售员不为空,且销售金额大于0
							//查找该销售员的销售员
							Employee manager = employeeDao.findByEmployeecodeStr(order.getManagercode());
							if(manager != null){//销售管家存在
								salegaininfo.setGainercode(order.getManagercode());
								salegaininfo.setGaincode(salegain_manager_gain.getGaincode());
								salegaininfo.setGainname(salegain_manager_gain.getGainname());
								salegaininfo.setGainrate(salegain_manager_gain.getGainrate());
								Integer gainmoney = 0;
								if("0".equals(salegain_manager_gain.getGainform())){//百分比
									gainmoney = order.getManagergain() * salegain_manager_gain.getGainrate() / 100;
								}else if("1".equals(salegain_manager_gain.getGainform())){//绝对数值
									gainmoney = order.getManagergain();
								}
								salegaininfo.setGainmoney(gainmoney);
								//行动力分
								salegaininfo.setEnergyscore(0);
								salegaininfo.setAddtime(currenttime);
								//salegaininfo.setStatus("0");//未提取
								salegaininfoDao.save(salegaininfo);
							}
						}
					}
				}
				
				//事务提交
				txManager.commit(status);
				responseMap.put("status", "1");//注册成功
				responseMap.put("result", "订单结单成功");
				return responseMap;
			}catch(Exception e){
			    e.printStackTrace();
				//回滚
				txManager.rollback(status);
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，订单结单失败");//
				return responseMap;
			}
		} 
	} 
	
	/**
	 * 保存订单发货
	 */
	public Map<String, Object> saveDeliveryUserorder(Userorder userorderform, HttpServletRequest request, Operator operator) {
		//加锁
		synchronized(lock) {
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			
			Map<String, Object> responseMap = new HashMap<String, Object>();
			
			//当前时间
			String currenttime = Tools.getCurrentTime();
			
			try{
				
				//快递信息
				String deliverytime = Tools.getStr(userorderform.getDeliverytime());
				if(StringUtils.isEmpty(deliverytime)){
					//回滚
					txManager.rollback(status);
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "请输入发货时间");//
					return responseMap;
				}
				
				//修改订单的状态
				Userorder userorder = userorderDao.findById(userorderform.getId());
				if(userorder == null){
					//回滚
					txManager.rollback(status);
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "此订单不存在，请核对订单信息");
					return responseMap;
				}
				
				if(!"1".equals(userorder.getStatus())){
					//回滚
					txManager.rollback(status);
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "此订单不是已打包状态，不能发货");
					return responseMap;
				}
				
				userorder.setStatus("2");//已发货
				userorder.setOperatetime(currenttime);
				userorderDao.updateStatus(userorder);
				
				//保存发送信息
				Userdelivery delivery = new Userdelivery();
				delivery.setUsercode(userorder.getUsercode());
				delivery.setOrdercode(userorder.getOrdercode());
				delivery.setDeliveryname(Tools.getStr(userorderform.getDeliveryname()));
				delivery.setDeliverycode(Tools.getStr(userorderform.getDeliverycode()));
				delivery.setDeliverytime(Tools.getStr(userorderform.getDeliverytime()));
				delivery.setContent(Tools.getStr(userorderform.getContent()));
				delivery.setDeliverercode(Tools.getStr(userorderform.getDeliverercode()));
				delivery.setDeliverername(Tools.getStr(userorderform.getDeliverername()));
				delivery.setDelivererphone(Tools.getStr(userorderform.getDelivererphone()));
				delivery.setStatus("0");//默认未到货
				userdeliveryDao.save(delivery);
				
				//查询关联的任务单号
				//Usertask task =  taskDao.findByTaskcodeStr(userorder.getTaskcode());
				//增加业务记录
				Userbusiness userbusiness = new Userbusiness();
				userbusiness.setOperatorcode(operator.getEmployeecode());
				userbusiness.setBusinesstypekey("orderdelivery");
				userbusiness.setBusinesstypename("订单发货");
				userbusiness.setUsercode(userorder.getUsercode());
				//userbusiness.setTaskcode(userorder.getTaskcode());
				userbusiness.setOrdercode(userorder.getOrdercode());
				userbusiness.setDispatchcode(null);
				userbusiness.setAddtime(currenttime);
				userbusiness.setContent("订单发货;订单号为： "+userorder.getOrdercode() + " ;送货人编号为：" + userorderform.getDeliverercode() + " ;送货人姓名为：" + userorderform.getDeliverername());
				userbusiness.setSource("1");//PC平台操作
				userbusinessDao.save(userbusiness);
				
				//增加操作日记
				String content = "订单发货;订单号为： "+userorder.getOrdercode() + " ;送货人编号为：" + userorderform.getDeliverercode() + " ;送货人姓名为：" + userorderform.getDeliverername();
				operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
				
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
	 * 修改订单产品参数
	 */
	public Map<String, Object> updateUserproductconfirm(Userorder userorderform, HttpServletRequest request, Operator operator) {
		//加锁
		synchronized(lock) {
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			
			Map<String, Object> responseMap = new HashMap<String, Object>();
			
			//当前时间
			String currenttime = Tools.getCurrentTime();
			
			try{
				
				//保存型号关联的产品类别信息
				String productJson = request.getParameter("productJson");
				if(StringUtils.isEmpty(productJson)){
					//回滚
					txManager.rollback(status);
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "请选择关联的产品信息");//
					return responseMap;
				}
				
				//增加关联的产品信息
				JSONArray productinfoArray = JSONArray.fromObject(productJson);
				
				Userorder userorder = userorderDao.findById(userorderform.getId());
				if(userorder == null){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，此订单不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(!"0".equals(userorder.getStatus())){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，此订单产品已经打包，不能修改");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
//				if(!"0".equals(userorder.getFinalarrivalflag())){
//					responseMap.put("status", "0");//保存失败
//					responseMap.put("result", "提交失败，此订单已经支付尾款，不能修改");
//					//回滚
//					txManager.rollback(status);
//					return responseMap;
//				}
				
				//修改订单的支付类型
				if(!userorderform.getPaytype().equals(userorder.getPaytype())){
					userorder.setPaytype(userorderform.getPaytype());
				}
				//修改订单是否需要派工
				if(!userorderform.getVisitorflag().equals(userorder.getVisitorflag())){
					userorder.setVisitorflag(userorderform.getVisitorflag());
				}
				
				//修改订单状态为已经确认产品信息
				userorder.setProductconfirm("1");
				userorderDao.update(userorder);
				
				//增加订单产品信息
				//1-先删除订单的产品
				//userproductDao.deleteByOrdercode(userorder.getOrdercode());
				
				//找出数据库里存在的订单产品信息
				List<Userproduct> userproductlist = userproductDao.findUserproductListByOrdercode(userorder.getOrdercode());
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
					for (int i = 0; i < productinfoArray.size(); i++) {
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
				
				//增加操作日记
				String content = "确认 订单产品信息， 订单编号:"+userorder.getOrdercode();
				operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
				
				//事务提交
				txManager.commit(status);
				responseMap.put("status", "1");//注册成功
				responseMap.put("result", "订单产品确认成功");
				return responseMap;
			}catch(Exception e){
			    e.printStackTrace();
				//回滚
				txManager.rollback(status);
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，订单产品确认失败");//
				return responseMap;
			}
		} 
	}
	
	/**
	 * 保存订单产品更换
	 */
	public Map<String, Object> saveUserproductreplace(Userorder userorderform, HttpServletRequest request, Operator operator) {
		//加锁
		synchronized(lock) {
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			
			Map<String, Object> responseMap = new HashMap<String, Object>();
			
			//当前时间
			String currenttime = Tools.getCurrentTime();
			
			try{
				
				//保存型号关联的产品类别信息
				String userproductJson = request.getParameter("userproductJson");
				if(StringUtils.isEmpty(userproductJson)){
					//回滚
					txManager.rollback(status);
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "请选择需要更换的旧产品");//
					return responseMap;
				}
				
				//新的订单号
				String ordercode = Tools.getStr(request.getParameter("ordercode"));
				
				//增加关联的产品信息
				JSONObject userproductJsonObject = JSONObject.fromObject(userproductJson);
				//需要更换的旧产品编码
				String productcode = userproductJsonObject.getString("productcode");
				//需要更换的订户产品ID
				String id =  userproductJsonObject.getString("id");
				//需要更换的订户旧产品识别号
				String productidentfy_old = userproductJsonObject.getString("productidentfy");
				//旧产品回收状态
				String productstatus = Tools.getStr(request.getParameter("productstatus"));
				//新的产品识别号
				String productidentfy_new = Tools.getStr(request.getParameter("productidentfy"));
				
				//找出旧产品的库存状态
				Productdepot productdepot_old = productdepotDao.findByProductidentfy(productidentfy_old);
				productdepot_old.setDepotstatus("1");//修改成有库存
				productdepot_old.setDepotamount(productdepot_old.getDepotamount() + 1);//库存量+1
				productdepot_old.setProductstatus(productstatus);
				//修改旧产品库存量和库存状态
				productdepotDao.update(productdepot_old);
				
				//增加产品入库记录
				Productinout productinout_old = new Productinout();
				productinout_old.setProductcode(productdepot_old.getProductcode());
				productinout_old.setProductname(productdepot_old.getProductname());
				productinout_old.setProductidentfy(productdepot_old.getProductidentfy());
				productinout_old.setType("0"); //入库
				productinout_old.setInoutercode(operator.getEmployeecode());
				productinout_old.setInoutamount(1);//入库量为1
				productinout_old.setAddtime(currenttime);
				productinout_old.setOperatorcode(operator.getEmployeecode());
				productinout_old.setInoutstatus("1");
				productinout_old.setDepotrackcode(productdepot_old.getDepotrackcode());
				productinoutDao.save(productinout_old);
				
				//新产品验证
				if(StringUtils.isEmpty(productidentfy_new)){
					//回滚
					txManager.rollback(status);
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "请选择新的产品识别号");//
					return responseMap;
				}
				
				//验证新产品库存状态
				Productdepot productdepot_new = productdepotDao.findByProductidentfy(productidentfy_new);
				if(productdepot_new == null){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "更换失败，新选择的产品唯一标识码不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(!"1".equals(productdepot_new.getProductstatus())){//新选择的产品不是正常状态
					//回滚
					txManager.rollback(status);
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "更换失败，新选择的产品不是正常状态，不能出库");//
					return responseMap;
				}
				
				if(productdepot_new.getDepotamount() < 1 || "0".equals(productdepot_new.getDepotstatus()) ){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "更换失败，该产品唯一标识码已经无库存");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
//				if(!productcode.equals(productdepot_new.getProductcode())){
//					//回滚
//					txManager.rollback(status);
//					responseMap.put("status", "0");//保存失败
//					responseMap.put("result", "新选择的产品需要更换的旧产品不一样，不能更换");//
//					return responseMap;
//				}
				
				//现有库存量
				int depotamount = productdepot_new.getDepotamount();
				//修改现有库存量
				productdepot_new.setDepotamount(depotamount-1);
				//如果库存量等于1，修改库存状态为0
				if(depotamount == 1){
					productdepot_new.setDepotstatus("0"); //无库存
				}
				
				//新产品出库
				productdepotDao.update(productdepot_new);
				
				
				//增加产品出库记录
				Productinout productinout_new = new Productinout();
				productinout_new.setProductcode(productdepot_new.getProductcode());
				productinout_new.setProductname(productdepot_new.getProductname());
				productinout_new.setProductidentfy(productdepot_new.getProductidentfy());
				productinout_new.setType("1");
				productinout_new.setInoutercode(operator.getEmployeecode());
				productinout_new.setInoutamount(1);//出库量
				productinout_new.setAddtime(currenttime);
				productinout_new.setOperatorcode(operator.getEmployeecode());
				productinout_new.setInoutstatus("1");
				productinout_new.setDepotrackcode(productdepot_new.getDepotrackcode());
				productinoutDao.save(productinout_new);
				
				//订户产品信息
				Userproduct userproduct = userproductDao.findById(Integer.parseInt(id));
				userproduct.setProductidentfy(productidentfy_new);//更新产品SN码
				userproduct.setTypecode(productdepot_new.getTypecode());//更新产品类型编号
				userproduct.setTypename(productdepot_new.getTypename());//更新产品类型名称
				userproduct.setProductcode(productdepot_new.getProductcode());//更新产品编号
				userproduct.setProductname(productdepot_new.getProductname());//更新产品名称
				userproduct.setDepotrackcode(productdepot_new.getDepotrackcode());//更新产品库存位置
				userproduct.setProductversion(productdepot_new.getProductversion());//更新产品软件版本号
				//修改订户产品信息
				userproductDao.update(userproduct);
				
				//增加产品更新日志
				Userorder userorder = userorderDao.findByOrdercode(ordercode);
				
				Userproductreplace userproductreplace = new Userproductreplace();
				userproductreplace.setUsercode(userorder.getUsercode());
				userproductreplace.setOrdercode(userorder.getOrdercode());
				userproductreplace.setModelname(userorder.getModelname());
				userproductreplace.setModelcode(userorder.getModelcode());
				userproductreplace.setTypecode(userproduct.getTypecode());
				userproductreplace.setTypename(userproduct.getTypename());
				userproductreplace.setProductcode(userproduct.getProductcode());
				userproductreplace.setProductname(userproduct.getProductname());
				userproductreplace.setAddtime(currenttime);
				userproductreplace.setOperatorcode(operator.getEmployeecode());
				userproductreplace.setVisittime(StringUtils.isEmpty(Tools.getStr(request.getParameter("visittime")))?null:Tools.getStr(request.getParameter("visittime")));
				userproductreplace.setVisitreasons(Tools.getStr(request.getParameter("visitreasons")));
				userproductreplace.setReplacetype("0");//产品更换
				userproductreplace.setOldproductidentfy(productidentfy_old);
				userproductreplace.setOldproductversion(productdepot_old.getProductversion());
				userproductreplace.setNewproductidentfy(productidentfy_new);
				userproductreplace.setNewproductversion(productdepot_new.getProductversion());
				userproductreplace.setOldproductstatus(productstatus);
				userproductreplace.setOldproductproblem(Tools.getStr(request.getParameter("oldproductproblem")));
				userproductreplace.setRebackremark(Tools.getStr(request.getParameter("rebackremark")));
				
				//保存产品更换记录
				userproductreplaceDao.save(userproductreplace);
				
				//增加操作日记
				String content = "更换 订单产品信息， 订单编号:"+ordercode +"; 旧产品识别号为:"+productidentfy_old+"; 新产品识别号为:"+productidentfy_new;
				operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
				
				//事务提交
				txManager.commit(status);
				responseMap.put("status", "1");//注册成功
				responseMap.put("result", "订单产品更换成功");
				return responseMap;
			}catch(Exception e){
			    e.printStackTrace();
				//回滚
				txManager.rollback(status);
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，订单产品更换失败");//
				return responseMap;
			}
		} 
	}
	
	
	/**
	 * 保存订单产品软包版本升级
	 */
	public Map<String, Object> saveUserproductChangeVersion(Userorder userorderform, HttpServletRequest request, Operator operator) {
		//加锁
		synchronized(lock) {
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			
			Map<String, Object> responseMap = new HashMap<String, Object>();
			
			//当前时间
			String currenttime = Tools.getCurrentTime();
			
			try{
				
				//保存型号关联的产品类别信息
				String userproductJson = request.getParameter("userproductJson");
				if(StringUtils.isEmpty(userproductJson)){
					//回滚
					txManager.rollback(status);
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "请选择需要更换的旧产品");//
					return responseMap;
				}
				
				//新的订单号
				String ordercode = Tools.getStr(request.getParameter("ordercode"));
				
				//增加关联的产品信息
				JSONObject userproductJsonObject = JSONObject.fromObject(userproductJson);
				//需要更换的旧产品编码
				//String productcode = userproductJsonObject.getString("productcode");
				//需要更换的订户产品ID
				String id =  userproductJsonObject.getString("id");
				//需要更换的订户旧产品识别号
				String productidentfy_old = userproductJsonObject.getString("productidentfy");
				
				//订户产品信息
				Userproduct userproduct = userproductDao.findById(Integer.parseInt(id));
				//产品旧版本
				String oldproductversion = userproduct.getProductversion();
				//修改产品新版本
				userproduct.setProductversion(request.getParameter("newproductversion"));//更新产品软件版本号
				//修改订户产品信息
				userproductDao.update(userproduct);
				
				//增加产品软件版本更新日志
				Userorder userorder = userorderDao.findByOrdercode(ordercode);
				Userproductreplace userproductreplace = new Userproductreplace();
				userproductreplace.setUsercode(userorder.getUsercode());
				userproductreplace.setOrdercode(userorder.getOrdercode());
				userproductreplace.setModelname(userorder.getModelname());
				userproductreplace.setModelcode(userorder.getModelcode());
				userproductreplace.setTypecode(userproduct.getTypecode());
				userproductreplace.setTypename(userproduct.getTypename());
				userproductreplace.setProductcode(userproduct.getProductcode());
				userproductreplace.setProductname(userproduct.getProductname());
				userproductreplace.setAddtime(currenttime);
				userproductreplace.setOperatorcode(operator.getEmployeecode());
				userproductreplace.setVisittime(request.getParameter("visittime"));
				userproductreplace.setVisitreasons(request.getParameter("visitreasons"));
				userproductreplace.setReplacetype("1");//产品软件版本升级
				userproductreplace.setOldproductidentfy(productidentfy_old);
				userproductreplace.setOldproductversion(oldproductversion);
				userproductreplace.setNewproductidentfy(productidentfy_old);
				userproductreplace.setNewproductversion(request.getParameter("newproductversion"));
				userproductreplace.setOldproductstatus(request.getParameter("oldproductstatus"));
				userproductreplace.setOldproductproblem(request.getParameter("oldproductproblem"));
				userproductreplace.setRebackremark(request.getParameter("rebackremark"));
				
				//保存产品更换记录
				userproductreplaceDao.save(userproductreplace);
				
				
				//增加操作日记
				String content = "客户产品软件版本升级， 订单编号:"+ordercode +"; 产品SN/PN为:"+productidentfy_old+"; 旧软件版本为:"+oldproductversion+"; 新软件版本为:"+request.getParameter("newproductversion");
				operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
				
				//事务提交
				txManager.commit(status);
				responseMap.put("status", "1");//注册成功
				responseMap.put("result", "产品软包版本升级成功");
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
	 * 保存订单价格修改
	 */
	public Map<String, Object> updateProductfee(Userorder userorderform, HttpServletRequest request, Operator operator) {
		//加锁
		synchronized(lock) {
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			
			Map<String, Object> responseMap = new HashMap<String, Object>();
			
			//当前时间
			String currenttime = Tools.getCurrentTime();
			
			try{
				
				//订单号
				String ordercode = Tools.getStr(request.getParameter("ordercode"));
				//订单新金额
				String newtotalmoney = Tools.getStr(request.getParameter("newtotalmoney"));
				
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
				
				
//				if(Integer.valueOf(newproductfee) > productmodel.getMaxprice()){
//					responseMap.put("status", "0");//保存失败
//					responseMap.put("result", "提交失败，产品价格不能大于该产品型号的最大上浮价格："+productmodel.getMaxprice());
//					//回滚
//					txManager.rollback(status);
//					return responseMap;
//				}
//				
//				if(Integer.valueOf(newproductfee) < productmodel.getMinprice()){
//					responseMap.put("status", "0");//保存失败
//					responseMap.put("result", "提交失败，产品价格不能小于该产品型号的最小下浮价格："+productmodel.getMinprice());
//					//回滚
//					txManager.rollback(status);
//					return responseMap;
//				}
				
				//订单原金额
				Integer totalmonet_old = userorder.getProductfee() + userorder.getOtherfee();
				//订单新金额
				Integer totalmoney_new = Integer.valueOf(newtotalmoney);
				userorder.setTotalmoney(totalmoney_new);//订单新总金额
				userorder.setDiscountfee(totalmonet_old - totalmoney_new);//订单原金额-订单新金额=优惠价
				userorder.setFinalpayment(totalmoney_new - Integer.valueOf(userorder.getFirstpayment()));//尾款金额
				//保存订单
				userorderDao.update(userorder);
				
				//增加生成订单业务记录
				Userbusiness userbusinessForOrder = new Userbusiness();
				userbusinessForOrder.setOperatorcode(operator.getEmployeecode());
				userbusinessForOrder.setBusinesstypekey("productfeeupdate");
				userbusinessForOrder.setBusinesstypename("修改订单金额");
				userbusinessForOrder.setUsercode(userorder.getUsercode());
				userbusinessForOrder.setTaskcode(null);
				userbusinessForOrder.setOrdercode(userorder.getOrdercode());
				userbusinessForOrder.setDispatchcode(null);
				userbusinessForOrder.setAddtime(currenttime);
				userbusinessForOrder.setContent("修改订单总金额费用：订单号为： "+userorder.getOrdercode() + "; 产品订单金额为:"+ totalmoney_new);
				userbusinessForOrder.setSource("0");//APP平台操作
				userbusinessDao.save(userbusinessForOrder);
				
				//增加操作日记
				String content = "订单金额修改，客户电话为:"+userorder.getPhone()+";客户姓名为："+ userorder.getUsername()+";订单编号为："+ userorder.getOrdercode()+";订单新金额为："+ userorder.getTotalmoney();
				operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
				
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
	 * 保存订单详情
	 */
	public Map<String, Object> updateOrderinfo(Userorder userorderform, HttpServletRequest request, Operator operator) {
		//加锁
		synchronized(lock) {
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			
			Map<String, Object> responseMap = new HashMap<String, Object>();
			
			//当前时间
			String currenttime = Tools.getCurrentTime();
			
			try{
				
				//订单号
				String ordercode = Tools.getStr(request.getParameter("ordercode"));
				//订单新金额
				String newtotalmoney = Tools.getStr(request.getParameter("newtotalmoney"));
				
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
				
				userorder.setOrderinfo(userorderform.getOrderinfo());
				
				//保存订单
				userorderDao.update(userorder);
				
				//增加生成订单业务记录
				Userbusiness userbusinessForOrder = new Userbusiness();
				userbusinessForOrder.setOperatorcode(operator.getEmployeecode());
				userbusinessForOrder.setBusinesstypekey("updateOrderinfo");
				userbusinessForOrder.setBusinesstypename("修改订单详情");
				userbusinessForOrder.setUsercode(userorder.getUsercode());
				userbusinessForOrder.setTaskcode(null);
				userbusinessForOrder.setOrdercode(userorder.getOrdercode());
				userbusinessForOrder.setDispatchcode(null);
				userbusinessForOrder.setAddtime(currenttime);
				userbusinessForOrder.setContent("修改订单详情：订单号为： "+userorder.getOrdercode() + "; 产品订单详情为:"+ userorderform.getOrderinfo());
				userbusinessForOrder.setSource("0");//APP平台操作
				userbusinessDao.save(userbusinessForOrder);
				
				//增加操作日记
				String content = "订单产品详情修改，客户电话为:"+userorder.getPhone()+";客户姓名为："+ userorder.getUsername()+";订单编号为："+ userorder.getOrdercode()+";产品新详情为："+ userorder.getOrderinfo();
				operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
				
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
	 * 保存使用优惠卡
	 */
	public Map<String, Object> saveUseGiftcard(Userorder userorderform, HttpServletRequest request, Operator operator) {
		//加锁
		synchronized(lock) {
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			
			Map<String, Object> responseMap = new HashMap<String, Object>();
			
			//当前时间
			String currenttime = Tools.getCurrentTime();
			
			try{
				
				//订单号
				String ordercode = Tools.getStr(request.getParameter("ordercode"));
				//优惠卡号
				String giftcardno = Tools.getStr(request.getParameter("giftcardno"));
				
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
				
				if(!"0".equals(userorder.getFinalarrivalflag())){//订单尾款已经支付，不能使用优惠卡
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，此订单已经支付尾款，不能使用优惠卡");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(userorder.getDiscountfee() != null && userorder.getDiscountfee() > 0){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，此订单已经使用过优惠卡了，不能再次使用优惠卡");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(StringUtils.isEmpty(giftcardno)){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，请输入优惠卡号");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				Giftcard giftcard = giftcardDao.findByGiftcardno(giftcardno);
				
				if(giftcard == null){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，此优惠卡号不存在");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if("1".equals(giftcard.getStatus())){//优惠卡已经使用
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，此优惠卡号已经使用过");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				
//				if(StringUtils.isNotEmpty(giftcard.getStarttime())){
//					String starttime = giftcard.getStarttime().substring(0,10);
//					if(currenttime.compareTo(starttime) < 0){ //当前时间小于有效开始时间
//						responseMap.put("status", "0");//保存失败
//						responseMap.put("result", "提交失败，还未到有效开始时间");
//						//回滚
//						txManager.rollback(status);
//						return responseMap;
//					}
//				}
//				
//				if(StringUtils.isNotEmpty(giftcard.getEndtime())){
//					String endtime = giftcard.getEndtime().substring(0,10);
//					if(currenttime.compareTo(endtime) > 0){ //当前时间大于有效结束时间
//						responseMap.put("status", "0");//保存失败
//						responseMap.put("result", "提交失败，此优惠券已经过期");
//						//回滚
//						txManager.rollback(status);
//						return responseMap;
//					}
//				}
				
				
				if(!giftcard.getModelcode().equals(userorder.getModelcode())){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，此订单的产品型号与该优惠卡的产品型号不符合");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				if(userorder.getDiscountgain() != null){
					if(giftcard.getAmount() > userorder.getDiscountgain()){
						responseMap.put("status", "0");//保存失败
						responseMap.put("result", "提交失败，优惠卡的价格大于该产品型号的优惠权限最大价格");
						//回滚
						txManager.rollback(status);
						return responseMap;
					}
				}
				
				if(!giftcard.getEmployeecode().equals(userorder.getSalercode())){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "提交失败，此优惠卡对应的销售员不是该订单的销售员");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				
				//修改优惠卡信息
				giftcard.setStatus("1");//改成已经使用
				giftcard.setUsercode(userorder.getUsercode());//赋值客户编号
				giftcard.setOrdercode(userorder.getOrdercode());//赋值订单编号
				giftcard.setUsetime(currenttime);//使用时间
				
				//保存优惠卡
				giftcardDao.update(giftcard);
				
				//修改订单信息
				Integer totalmoney = userorder.getTotalmoney() - giftcard.getAmount();
				userorder.setTotalmoney(totalmoney);//总金额
				userorder.setDiscountfee(userorder.getDiscountfee() + giftcard.getAmount());//优惠金额
				userorder.setFinalpayment(totalmoney - Integer.valueOf(userorder.getFirstpayment()));//尾款金额
				//保存订单
				userorderDao.update(userorder);
				
				//增加生成订单业务记录
				Userbusiness userbusinessForOrder = new Userbusiness();
				userbusinessForOrder.setOperatorcode(operator.getEmployeecode());
				userbusinessForOrder.setBusinesstypekey("usegiftcard");
				userbusinessForOrder.setBusinesstypename("使用优惠卡");
				userbusinessForOrder.setUsercode(userorder.getUsercode());
				userbusinessForOrder.setTaskcode(null);
				userbusinessForOrder.setOrdercode(userorder.getOrdercode());
				userbusinessForOrder.setDispatchcode(null);
				userbusinessForOrder.setAddtime(currenttime);
				userbusinessForOrder.setContent("使用优惠卡：优惠金额为： " + giftcard.getAmount());
				userbusinessForOrder.setSource("0");//APP平台操作
				userbusinessDao.save(userbusinessForOrder);
				
				//增加操作日记
				String content = "使用优惠卡，客户电话为:"+userorder.getPhone()+";客户姓名为："+ userorder.getUsername()+";订单编号为："+ userorder.getOrdercode()+";优惠金额为："+ giftcard.getAmount();
				operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
				
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
}
