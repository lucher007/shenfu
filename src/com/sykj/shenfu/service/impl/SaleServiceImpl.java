package com.sykj.shenfu.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.multipart.MultipartFile;

import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.IEmployeeDao;
import com.sykj.shenfu.dao.IProductDao;
import com.sykj.shenfu.dao.IProductmodelDao;
import com.sykj.shenfu.dao.IProductmodelrefDao;
import com.sykj.shenfu.dao.IUserDao;
import com.sykj.shenfu.dao.IUserbusinessDao;
import com.sykj.shenfu.dao.IUserdoorDao;
import com.sykj.shenfu.dao.IUserorderDao;
import com.sykj.shenfu.dao.IUserorderinfoDao;
import com.sykj.shenfu.dao.IUserproductDao;
import com.sykj.shenfu.dao.IUsertaskDao;
import com.sykj.shenfu.po.Coderule;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Product;
import com.sykj.shenfu.po.Productmodel;
import com.sykj.shenfu.po.Productmodelref;
import com.sykj.shenfu.po.User;
import com.sykj.shenfu.po.Userbusiness;
import com.sykj.shenfu.po.Userdoor;
import com.sykj.shenfu.po.Userorder;
import com.sykj.shenfu.po.Userorderinfo;
import com.sykj.shenfu.po.Userproduct;
import com.sykj.shenfu.po.Usertask;
import com.sykj.shenfu.service.ICoderuleService;
import com.sykj.shenfu.service.IOperatorlogService;
import com.sykj.shenfu.service.ISaleService;
import com.sykj.shenfu.service.ISystemparaService;
import com.sykj.shenfu.service.IUserService;

/**
 * @Title ProductdepotServiceImpl.java
 * @version 1.0 编码规则类
 */
@Service("saleService")
public class SaleServiceImpl implements ISaleService {
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
	private IProductDao productDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private ISystemparaService systemparaService;
	@Autowired
	private IUserdoorDao userdoorDao;
	@Autowired
	private IEmployeeDao employeeDao;
	
	/*
     * 特殊的instance变量
     * 注：零长度的byte数组对象创建起来将比任何对象都经济――查看编译后的字节码：
     * 生成零长度的byte[]对象只需3条操作码，而Object lock = new Object()则需要7行操作码。
     */
    private static byte[] lock = new byte[0];   
	
    /**
	 * 客户商城购买订单-讲解类型：0-需要体验
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveUserAddOrderForVisittype0(User userform, HttpServletRequest request,HttpSession session){
		
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
				String phone = Tools.getStr(userform.getPhone());
				//客户姓名
				String username = Tools.getStr(userform.getUsername());
				//客户地址
				String address = Tools.getStr(userform.getAddress());
				//销售人员
				String salercode = Tools.getStr(userform.getSalercode());
				//操作员编号
				String operatorcode = Tools.getStr(null);
				//上门类型
				String visittype = Tools.getStr(userform.getVisittype());
				//来源
				String source = Tools.getStr(userform.getSource());
				//备注信息
				String dealresult = Tools.getStr(userform.getDealresult());
				//操作时间
				String currenttime = Tools.getCurrentTime();
				
				//电话号码不能为空
				if(StringUtils.isEmpty(phone)){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "添加失败，电话号码不能为空");//
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
					} else {//客户已经处理了，可以再次下订单，修改客户的信息，上门类型，
						user.setUsername(username);
						user.setAddress(address);
						user.setSalercode(salercode);
						user.setVisittype(visittype);
						user.setSource(source);
						user.setDealresult(user.getDealresult() + "\n"+dealresult);
						user.setStatus("0");//已处理，生成了讲解单
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
					//上门类型（0.公司派人讲解，1.亲自讲解）
					user.setVisittype(visittype);
					//销售员编号
					user.setSalercode(salercode);
					//审核状态
					user.setCheckstatus("1");
					user.setSource(source);
					user.setStatus("0"); //已处理，生成了讲解单
					user.setAddtime(currenttime);
					user.setDealresult(dealresult);
					//客户编号
					String usercode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_usercode);
					user.setUsercode(usercode);
					//保存客户信息
					userDao.save(user);
				}
				
				//增加操作日记
				String content = "客户商城里自动下订单，订户姓名:"+user.getUsername();
				operatorlogService.saveOperatorlog(content, null);
				
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
				responseMap.put("result", "数据异常，客户添加失败");//
				return responseMap;
			}
		} 
	}
    
	/**
	 * 客户商城购买订单-讲解类型：1-直接购买订单
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveUserAddOrderForVisittype1(User userform, HttpServletRequest request,HttpSession session, MultipartFile[] files){
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("status", "0");//默认保存失败
		//加锁
		synchronized(lock) {
			
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			try{
				
				String phone = Tools.getStr(userform.getPhone());
				//客户姓名
				String username = Tools.getStr(userform.getUsername());
				//客户地址
				String address = Tools.getStr(userform.getAddress());
				//操作员编号
				String operatorcode = Tools.getStr(null);
				//销售人员
				String salercode = Tools.getStr(userform.getSalercode());
				//上门类型
				String visittype = Tools.getStr(userform.getVisittype());
				//来源
				String source = Tools.getStr(userform.getSource());
				//当前时间
				String currenttime =  Tools.getCurrentTime();
				//是否带机械锁芯
				String lockcoreflag = Tools.getStr(userform.getLockcoreflag());
				//产品颜色
				String productcolor = Tools.getStr(userform.getProductcolor());
				
				//备注信息
				String dealresult = Tools.getStr(userform.getDealresult());
				//产品型号
				String modelcode = Tools.getStr(userform.getModelcode());
				//支付类型
				String paytype = Tools.getStr("1"); //默认货到付款
				
				//电话号码不能为空
				if(StringUtils.isEmpty(phone)){
					responseMap.put("status", "0");//电话号码不能为空
					responseMap.put("result", "添加失败，电话号码不能为空");//
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
					}  else {//客户安装已处理，再次提交，修改客户的销售员编号，上门类型，
						user.setUsername(username);
						user.setAddress(address);
						user.setSalercode(salercode);
						user.setVisittype(visittype);
						user.setSource(source);//400操作
						user.setStatus("2");//已生成订单
						user.setDealresult(user.getDealresult()+ "\n" +dealresult);
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
					user.setSource(source);//400操作
					user.setStatus("2"); //已生成订单
					user.setAddtime(currenttime);
					user.setDealresult(dealresult);
					
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
				//任务单信息
				userorder.setSalercode(salercode); //销售员
				userorder.setTalkercode(salercode);//销售员作为讲解员
				userorder.setVisitorcode(null);//400操作员作为上门人员
				userorder.setVisitorstatus("0");//未派测量单
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
				if(StringUtils.isNotEmpty(user.getSalercode())){
					Employee saler = employeeDao.findByEmployeecodeStr(user.getSalercode());
					if(saler != null){
						userorder.setManagercode(saler.getManagercode());
					}
				}
				userorder.setOrderinfo(dealresult);
				//保存订单
				userorderDao.save(userorder);
				
				//增加生成订单业务记录
				Userbusiness userbusinessForOrder = new Userbusiness();
				userbusinessForOrder.setOperatorcode(null);
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
				usertask.setOperatorcode(operatorcode);
				usertask.setVisitstate("0");//访问状态（默认为0，若拒绝安装则改为1）
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
				userbusinessForTask.setOperatorcode(operatorcode);
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
				
				if(files == null || files.length <=0){
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "添加失败，请上传门锁图片信息");
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				for(int i=0; i<files.length; i++){
					if (!files[i].isEmpty()) {
						
						String filename = files[i].getOriginalFilename();
						String[] strArray = filename.split("[.]");
						String filetype = strArray[strArray.length - 1];
						
						String userdoor_path = systemparaService.findSystemParaByCodeStr("upload_file_path");
						
						//String userdoor_path = ConfigUtil.getConfigFilePath("userdoor_path", "system.properties");
						//String upload_extend_path = systemparaDao.findByCodeStr("upload_file_path").getValue();
						String folderpath = userdoor_path + File.separatorChar + "userdoor" + File.separatorChar + user.getUsercode();
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
				
				//增加操作日记
				String content = "添加订户，订户姓名:"+user.getUsername();
				operatorlogService.saveOperatorlog(content, null);
				
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
				responseMap.put("result", "数据异常，客户添加失败");//
				return responseMap;
			}
		} 
	}
    
}
