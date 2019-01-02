package com.sykj.shenfu.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.IProductDao;
import com.sykj.shenfu.dao.IProductmodelDao;
import com.sykj.shenfu.dao.IProductmodelrefDao;
import com.sykj.shenfu.dao.IUserDao;
import com.sykj.shenfu.dao.IUserbusinessDao;
import com.sykj.shenfu.dao.IUserorderDao;
import com.sykj.shenfu.dao.IUserorderinfoDao;
import com.sykj.shenfu.dao.IUserproductDao;
import com.sykj.shenfu.dao.IUsertaskDao;
import com.sykj.shenfu.po.Coderule;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.User;
import com.sykj.shenfu.po.Userbusiness;
import com.sykj.shenfu.po.Userorder;
import com.sykj.shenfu.po.Usertask;
import com.sykj.shenfu.service.ICoderuleService;
import com.sykj.shenfu.service.IOperatorlogService;
import com.sykj.shenfu.service.IUsertaskService;

/**
 * @Title ProductdepotServiceImpl.java
 * @version 1.0 编码规则类
 */
@Service("usertaskService")
public class UsertaskServiceImpl implements IUsertaskService {
	@Autowired
    private DataSourceTransactionManager txManager;
	@Autowired
	private ICoderuleService coderuleService;
	@Autowired
	private IUsertaskDao taskDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IUserorderDao userorderDao;
	@Autowired
	private IUserbusinessDao userbusinessDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	/*
     * 特殊的instance变量
     * 注：零长度的byte数组对象创建起来将比任何对象都经济――查看编译后的字节码：
     * 生成零长度的byte[]对象只需3条操作码，而Object lock = new Object()则需要7行操作码。
     */
    private static byte[] lock = new byte[0];   
	
    /**
	 * 公司派人上门讲解和测量
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveTaskForBatchAdd(Usertask taskform, HttpServletRequest request,Operator operator){
		
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
				
				//上门人员
				String visitorcode = request.getParameter("visitorcode");
				//上门时间
				String visittime = request.getParameter("visittime");
				if(StringUtils.isEmpty(visitorcode)){
					responseMap.put("status", "0");//
					responseMap.put("result", "派单失败，请选择上门人员");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//需要派任务单的订单号
				String usercodes = request.getParameter("usercodes");
				if(StringUtils.isEmpty(usercodes)){
					responseMap.put("status", "0");//
					responseMap.put("result", "派单失败，请选择需要派测量任务的客户");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				String[] usercodeArr = usercodes.split(",");
				if(usercodeArr == null || usercodeArr.length < 1){
					responseMap.put("status", "0");//
					responseMap.put("result", "派单失败，请选择需要派测量任务的客户");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				for (String usercode : usercodeArr) {
					//修改潜在订户的状态
					User user = userDao.findByUsercodeStr(usercode);
					if("1".equals(user.getStatus())){//已开始安装
						responseMap.put("status", "0");//
						responseMap.put("result", "派单失败,"+user.getUsername() + " 的已经开始安装了");//
						//回滚
						txManager.rollback(status);
						return responseMap;
					}
					user.setStatus("1");//修改成已派讲解单
					userDao.update(user);
					
					//最大任务单号
					String taskcode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_taskcode);
					taskform.setTaskcode("RW"+taskcode);
					//订户信息
					taskform.setUsercode(user.getUsercode());
					taskform.setUsername(user.getUsername());
					taskform.setUsersex(user.getUsersex());
					taskform.setPhone(user.getPhone());
					taskform.setAddress(user.getAddress());
					taskform.setSource(user.getSource());
					taskform.setVisittype(user.getVisittype());
					taskform.setSalercode(user.getSalercode());
					taskform.setTalkercode(visitorcode);
					taskform.setVisitorcode(null);
					taskform.setOperatorcode(operator.getEmployeecode());
					taskform.setVisitstate("0");//访问状态（默认为0，若拒绝安装则改为1）
					taskform.setVisittime(StringUtils.isEmpty(visittime)?null:visittime);
					//默认状态为未处理0
					taskform.setStatus("0");
					taskform.setAddtime(currenttime);
					taskform.setTasktype("0");//讲解单
					//保存任务单
					taskDao.save(taskform);
					
					//增加业务记录
					Userbusiness userbusiness = new Userbusiness();
					userbusiness.setOperatorcode(operator.getEmployeecode());
					userbusiness.setBusinesstypekey("taskadd");
					userbusiness.setBusinesstypename("任务单生成");
					userbusiness.setUsercode(taskform.getUsercode());
					userbusiness.setTaskcode(taskform.getTaskcode());
					userbusiness.setOrdercode(null);
					userbusiness.setDispatchcode(null);
					userbusiness.setAddtime(Tools.getCurrentTime());
					userbusiness.setContent("生成任务单;任务单号为： "+taskform.getTaskcode());
					userbusiness.setSource("0");//PC平台操作
					userbusinessDao.save(userbusiness);
					
					//增加操作日记
					String content = "添加 任务单， 任务单号:"+taskform.getTaskcode();
					operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
				}
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");//注册成功
				responseMap.put("result", "派任务单成功");//注册成功
				
				return responseMap;
				
			}catch(Exception e){
				//回滚
				txManager.rollback(status);
				
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，派单失败");//
				return responseMap;
			}
		} 
	}
    
    /**
	 * 已讲解，公司派人上门测量的订单派工
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveTaskForBatchToInstaller(Usertask taskform, HttpServletRequest request,Operator operator){
		
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
				
				//上门人员
				String visitorcode = taskform.getVisitorcode();
				//上门时间
				String visittime = taskform.getVisittime();
				
				if(StringUtils.isEmpty(visitorcode)){
					responseMap.put("status", "0");//
					responseMap.put("result", "派单失败，请选择上门测量人员");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				//需要派任务单的订单号
				String ordercodes = request.getParameter("ordercodes");
				if(StringUtils.isEmpty(ordercodes)){
					responseMap.put("status", "0");//
					responseMap.put("result", "派单失败，请选择需要派测量任务的订单");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				String[] ordercodeArr = ordercodes.split(",");
				if(ordercodeArr == null || ordercodeArr.length < 1){
					responseMap.put("status", "0");//
					responseMap.put("result", "派单失败，请选择需要派测量任务的订单");//
					//回滚
					txManager.rollback(status);
					return responseMap;
				}
				
				for (String ordercode : ordercodeArr) {
					//修改潜在订户的状态
					Userorder userorder = userorderDao.findByOrdercode(ordercode);
					if("1".equals(userorder.getVisitorstatus())){//已派测量任务单
						responseMap.put("status", "0");//
						responseMap.put("result", "保存失败,"+userorder.getOrdercode() + " 的订单已经派测量任务单了");//
						//回滚
						txManager.rollback(status);
						return responseMap;
					}
					userorder.setVisitorcode(taskform.getVisitorcode());//修改订单中的测量人员
					userorder.setVisitorstatus("1"); //已派测量人员
					userorder.setVisitorflag("1"); //需要上门测量
					userorderDao.update(userorder);
					
					//最大任务单号
					String taskcode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_taskcode);
					taskform.setTaskcode("RW"+taskcode);
					//订户信息
					taskform.setOrdercode(userorder.getOrdercode());
					taskform.setUsercode(userorder.getUsercode());
					taskform.setUsername(userorder.getUsername());
					taskform.setUsersex(userorder.getUsersex());
					taskform.setPhone(userorder.getPhone());
					taskform.setAddress(userorder.getAddress());
					taskform.setSource(userorder.getSource());
					taskform.setVisittype(userorder.getVisittype());
					taskform.setSalercode(userorder.getSalercode());
					taskform.setTalkercode(null);
					taskform.setVisitorcode(visitorcode);
					taskform.setOperatorcode(operator.getEmployeecode());
					taskform.setVisitstate("0");//访问状态（默认为0，若拒绝安装则改为1）
					taskform.setVisittime(StringUtils.isEmpty(taskform.getVisittime())?null:taskform.getVisittime());
					//默认状态为未处理0
					taskform.setStatus("0");
					taskform.setAddtime(currenttime);
					taskform.setTasktype("1");//默认为上门测量单
					//保存任务单
					taskDao.save(taskform);
					
					//增加业务记录
					Userbusiness userbusiness = new Userbusiness();
					userbusiness.setOperatorcode(operator.getEmployeecode());
					userbusiness.setBusinesstypekey("taskadd");
					userbusiness.setBusinesstypename("任务单生成");
					userbusiness.setUsercode(taskform.getUsercode());
					userbusiness.setTaskcode(taskform.getTaskcode());
					userbusiness.setOrdercode(null);
					userbusiness.setDispatchcode(null);
					userbusiness.setAddtime(Tools.getCurrentTime());
					userbusiness.setContent("生成任务单;任务单号为： "+taskform.getTaskcode());
					userbusiness.setSource("0");//PC平台操作
					userbusinessDao.save(userbusiness);
					
					//增加操作日记
					String content = "添加 任务单， 任务单号:"+taskform.getTaskcode();
					operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
				}
				
				//事务提交
				txManager.commit(status);
				
				responseMap.put("status", "1");//注册成功
				responseMap.put("result", "派任务单成功");//注册成功
				
				return responseMap;
				
			}catch(Exception e){
				//回滚
				txManager.rollback(status);
				
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "数据异常，派单失败");//
				return responseMap;
			}
		} 
	}
}
