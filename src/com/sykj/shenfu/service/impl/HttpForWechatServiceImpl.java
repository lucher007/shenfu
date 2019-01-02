package com.sykj.shenfu.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.IUserbusinessDao;
import com.sykj.shenfu.dao.IUserorderDao;
import com.sykj.shenfu.dao.IUserpaylogDao;
import com.sykj.shenfu.po.Userbusiness;
import com.sykj.shenfu.po.Userorder;
import com.sykj.shenfu.po.Userpaylog;
import com.sykj.shenfu.service.IHttpForWechatService;
import com.sykj.shenfu.service.IOperatorlogService;

/**
 * @Title ProductdepotServiceImpl.java
 * @version 1.0 编码规则类
 */
@Service("httpForWechatService")
public class HttpForWechatServiceImpl implements IHttpForWechatService {
	@Autowired
    private DataSourceTransactionManager txManager;
	@Autowired
	private IUserpaylogDao userpaylogDao;
	@Autowired
	private IUserorderDao userorderDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private IUserbusinessDao userbusinessDao;
	
	/*
     * 特殊的instance变量
     * 注：零长度的byte数组对象创建起来将比任何对象都经济――查看编译后的字节码：
     * 生成零长度的byte[]对象只需3条操作码，而Object lock = new Object()则需要7行操作码。
     */
    private static byte[] lock = new byte[0];   
	
	/**
	 * 保存客户支付
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> savePaymentInfo(JSONObject jsonObj){
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("status", "1");
		responseMap.put("result", "支付成功");
		//加锁
		synchronized(lock) {
				
			//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
			try{
				String currenttime = Tools.getCurrentTime();
				//订单编号
				String ordercode = Tools.getKeyValueFromJsonObj("ordercode", jsonObj);
				//付款方式(0-现金;1-微信在线;2-支付宝在线；3-微信静态码；4-支付宝静态码)
				String paytype = Tools.getKeyValueFromJsonObj("paytype", jsonObj);
				//付款项目(1-首付款；2-尾款; 3-全款)
				String payitem = Tools.getKeyValueFromJsonObj("payitem", jsonObj);
				//付款金额
				String paymoney = Tools.getKeyValueFromJsonObj("paymoney", jsonObj);
				//付款接收人
				//String receivercode = Tools.getKeyValueFromJsonObj("receivercode", jsonObj);
				
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
				userpaylog.setReceivercode(null);
				userpaylog.setPaytime(currenttime);
				//微信端支付
				userpaylog.setPaysource("1");
				
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
				userbusinessForUserpay.setOperatorcode(null);
				userbusinessForUserpay.setBusinesstypekey("userpay");
				userbusinessForUserpay.setBusinesstypename("客户微信公众号支付");
				userbusinessForUserpay.setUsercode(userorder.getUsercode());
				userbusinessForUserpay.setTaskcode(null);
				userbusinessForUserpay.setOrdercode(userorder.getOrdercode());
				userbusinessForUserpay.setDispatchcode(null);
				userbusinessForUserpay.setAddtime(currenttime);
				userbusinessForUserpay.setContent("客户微信公众号支付，订单号:" + ordercode + " 支付类型:"+userpaylog.getPayitemname()+";支付金额："+ userpaylog.getPaymoney());
				userbusinessForUserpay.setSource("0");//APP平台操作
				userbusinessDao.save(userbusinessForUserpay);
				
				//增加操作日记
				String content = "客户微信公众号支付，订单号:" + ordercode + " 支付类型:"+userpaylog.getPayitemname()+";支付金额："+ userpaylog.getPaymoney();
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
	
}
