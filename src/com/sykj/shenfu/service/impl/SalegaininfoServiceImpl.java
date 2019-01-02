package com.sykj.shenfu.service.impl;



import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.IEmployeeDao;
import com.sykj.shenfu.dao.ISalegaininfoDao;
import com.sykj.shenfu.dao.ISalegainlogDao;
import com.sykj.shenfu.po.Cellextend;
import com.sykj.shenfu.po.Coderule;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Productmodelref;
import com.sykj.shenfu.po.Salegaininfo;
import com.sykj.shenfu.po.Salegainlog;
import com.sykj.shenfu.service.ICoderuleService;
import com.sykj.shenfu.service.IOperatorlogService;
import com.sykj.shenfu.service.ISalegaininfoService;

/**
 * @Title ProductdepotServiceImpl.java
 * @version 1.0 产品库存实现类
 */
@Service("salegaininfoService")
public class SalegaininfoServiceImpl implements ISalegaininfoService {
	@Autowired
	private ISalegaininfoDao salegaininfoDao;
	@Autowired
	private ISalegainlogDao salegainlogDao;
	@Autowired
    private DataSourceTransactionManager txManager;
	@Autowired
	private ICoderuleService coderuleService;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private IEmployeeDao employeeDao;
	
	/*
     * 特殊的instance变量
     * 注：零长度的byte数组对象创建起来将比任何对象都经济――查看编译后的字节码：
     * 生成零长度的byte[]对象只需3条操作码，而Object lock = new Object()则需要7行操作码。
     */
    private static byte[] lock = new byte[0];   
	
	/**
	 * 提现
	 */
	public Map<String, Object> updateSalegaininfo(String gainercode, String ids,Integer totaltainmoney,String operatorcode) {
		   Map<String, Object> responseMap = new HashMap<String, Object>();	
		   //获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
		    //加锁
		   synchronized(lock) {
			   String gaintime = Tools.getCurrentTime();
			   try{
				   
				   //保存提成提取记录
				   //获取订单号
				   String gainlogcode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_gainlogcode);
				   gainlogcode = "TC" + gainlogcode;
				   
					//订单号
					String[] idArray = Tools.getStr(ids).split(",");
					if(idArray != null && idArray.length > 0){
						for (int i = 0; i < idArray.length; i++) {
							Salegaininfo salegaininfo = salegaininfoDao.findById(Integer.parseInt(idArray[i]));
							if("0".equals(salegaininfo.getStatus())){//未提取
								salegaininfo.setStatus("1");//更新提取状态
								salegaininfo.setGaintime(gaintime);//更新提成时间
								salegaininfo.setGainlogcode(gainlogcode);//更新提取编号
								salegaininfoDao.updateStatus(salegaininfo);
							}else{
								throw new Exception("1");
							}
						}
					}
					
					//保存提取记录
					Salegainlog salegainlog = new Salegainlog();
				    salegainlog.setGainlogcode(gainlogcode);
				    salegainlog.setGainercode(gainercode);
				    salegainlog.setGainmoney(totaltainmoney);
				    salegainlog.setGaintime(gaintime);
				    salegainlog.setOperatorcode(operatorcode);
				    salegainlog.setGaintype("");
				    salegainlogDao.save(salegainlog);
				    
				    //增加操作日记
					String content = "销售员提取提成，提取人编号为："+gainercode + "; 提取总金额："+totaltainmoney;
					operatorlogService.saveOperatorlog(content, operatorcode);
					
					//事务提交
					txManager.commit(status);
					responseMap.put("status", "1");//保存失败
					responseMap.put("result", "提取成功");//上传合同失败
					return responseMap;
				} catch(Exception e){
					//回滚
					txManager.rollback(status);
					if("1".equals(e.getMessage())){
						responseMap.put("status", "0");//保存失败
						responseMap.put("result", "选择的提成中有已提取的项，提成失败");//没有上传合同
						return responseMap;
					}else{
						responseMap.put("status", "0");//保存失败
						responseMap.put("result", "数据异常，提取失败");//没有上传合同
						return responseMap;
					}
				}
		  }
	}   
	
	/**
	 * 员工提成汇总提现
	 * 
	 * @param Userorder
	 * @return
	 */
	public Map<String, Object> saveTakeSalegaininfo(Salegaininfo form, HttpServletRequest request,Operator operator){
		Map<String, Object> responseMap = new HashMap<String, Object>();	
		   
		//获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
		    //加锁
		   synchronized(lock) {
			   String current = Tools.getCurrentTime();
			   try{
				   
				    //保存型号关联的产品类别信息
					String salegaininfoStatJson = form.getSalegaininfoStatJson();
					if(StringUtils.isEmpty(salegaininfoStatJson)){
						responseMap.put("status", "0");//电话号码不能为空
						responseMap.put("result", "操作失败，请重新选择需要提现的员工");//
						//回滚
						txManager.rollback(status);
						return responseMap;
					}
				   
					//增加关联的产品类别信息
					JSONArray salegaininfoStatJsonArry = JSONArray.fromObject(salegaininfoStatJson);
					for (int i = 0; i < salegaininfoStatJsonArry.size(); i++){
			            JSONObject salegaininfoStatObject = salegaininfoStatJsonArry.getJSONObject(i);
			            String gainercode = Tools.getStr(salegaininfoStatObject.getString("gainercode"));
			            String gainmoney = Tools.getStr(salegaininfoStatObject.getString("gainmoney"));
			            String ids = Tools.getStr(salegaininfoStatObject.getString("ids"));
			            
			            if(StringUtils.isEmpty(gainercode)){
			            	responseMap.put("status", "0");//电话号码不能为空
							responseMap.put("result", "操作失败，员工编号："+ gainercode + "的编号不能为空");//
							//回滚
							txManager.rollback(status);
							return responseMap;
			            }
			            
			            if(StringUtils.isEmpty(gainmoney)){
			            	responseMap.put("status", "0");//电话号码不能为空
							responseMap.put("result", "操作失败，员工编号："+ gainercode + "的本次提成金额不能为空");//
							//回滚
							txManager.rollback(status);
							return responseMap;
			            }
			            
			            if(StringUtils.isEmpty(ids)){
			            	responseMap.put("status", "0");//电话号码不能为空
							responseMap.put("result", "操作失败，员工编号："+ gainercode + "的提成记录出现错误，请核对之后再来提现");//
							//回滚
							txManager.rollback(status);
							return responseMap;
			            }
			            
			            Employee gainer = employeeDao.findByEmployeecodeStr(gainercode);
			            if(gainer == null){
			            	responseMap.put("status", "0");//电话号码不能为空
							responseMap.put("result", "操作失败，员工编号："+ gainercode + "的员工不存在");//
							//回滚
							txManager.rollback(status);
							return responseMap;
			            }
			            
			            //获取该员工的提成余额
//			            int salescore = 0;
//			            if(gainer.getSalescore() != null){
//			            	salescore = gainer.getSalescore().intValue();
//			            }
//			            if(Integer.parseInt(gainmoney) > salescore ){
//			            	responseMap.put("status", "0");//电话号码不能为空
//							responseMap.put("result", "操作失败，员工编号："+ gainercode + "的余额小于本次的提现金额");//
//							//回滚
//							txManager.rollback(status);
//							return responseMap;
//			            }
//			            
//			            //修改员工的余额
//					    employeeDao.updateSalescoreByEmployeecode(gainercode, 0-Integer.parseInt(gainmoney));
			            
					    //获取提现编号
						String gainlogcode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_gainlogcode);
						gainlogcode = "TX" + gainlogcode;
						//增加提现记录
						Salegainlog salegainlog = new Salegainlog();
					    salegainlog.setGainlogcode(gainlogcode);
					    salegainlog.setGainercode(gainercode);
					    salegainlog.setGainmoney(Integer.parseInt(gainmoney));
					    salegainlog.setGaintime(current);
					    salegainlog.setOperatorcode(operator.getEmployeecode());
					    salegainlog.setGaintype("");
					    salegainlog.setStatus("1");//默认已到账
					    salegainlogDao.save(salegainlog);
					    
					    //修改已有的提成明细记录为已经提取状态
					    String[] idArray = ids.split(",");
						if (idArray == null || idArray.length < 1) {
							responseMap.put("status", "0");//电话号码不能为空
							responseMap.put("result", "操作失败，员工编号："+ gainercode + "的提成记录出现错误，请核对之后再来提现");//
							//回滚
							txManager.rollback(status);
							return responseMap;
						}
						
						for (int j = 0; j < idArray.length; j++) {
							Salegaininfo salegaininfo = salegaininfoDao.findById(Integer.parseInt(idArray[j]));
							if(salegaininfo == null){
								responseMap.put("status", "0");//电话号码不能为空
								responseMap.put("result", "操作失败，员工编号："+ gainercode + "的提成记录出现错误，请核对之后再来提现");//
								//回滚
								txManager.rollback(status);
								return responseMap;
							}
							
							if("1".equals(salegaininfo.getStatus())){//已提取
								responseMap.put("status", "0");//电话号码不能为空
								responseMap.put("result", "操作失败，员工编号："+ gainercode + "的提成记录已经提现了");//
								//回滚
								txManager.rollback(status);
								return responseMap;
							}
							
							salegaininfo.setStatus("1");//更新提现状态
							salegaininfo.setGaintime(current);//更新提成时间
							salegaininfo.setGainlogcode(gainlogcode);//更新提取编号
							salegaininfoDao.updateStatus(salegaininfo);
							
						}
					}
					
					//增加操作日记
					String content = "员工提现";
					operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
					
					//事务提交
					txManager.commit(status);
					responseMap.put("status", "1");//保存失败
					responseMap.put("result", "操作成功");//上传合同失败
					return responseMap;
				} catch(Exception e){
					e.printStackTrace();
					//回滚
					txManager.rollback(status);
					responseMap.put("status", "0");//保存失败
					responseMap.put("result", "数据异常，操作失败");
					return responseMap;
				}
		  }
	}
	
}
