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
import com.sykj.shenfu.dao.ISaleenergyinfoDao;
import com.sykj.shenfu.dao.ISaleenergyruleDao;
import com.sykj.shenfu.dao.ISalegaininfoDao;
import com.sykj.shenfu.dao.ISalegainlogDao;
import com.sykj.shenfu.po.Coderule;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Saleenergyinfo;
import com.sykj.shenfu.po.Saleenergyrule;
import com.sykj.shenfu.po.Salegaininfo;
import com.sykj.shenfu.po.Salegainlog;
import com.sykj.shenfu.po.Salegainrule;
import com.sykj.shenfu.service.ICoderuleService;
import com.sykj.shenfu.service.IOperatorlogService;
import com.sykj.shenfu.service.ISaleenergyinfoService;

/**
 * @Title ProductdepotServiceImpl.java
 * @version 1.0 产品库存实现类
 */
@Service("saleenergyinfoService")
public class SaleenergyinfoServiceImpl implements ISaleenergyinfoService {
	@Autowired
	private ISalegaininfoDao salegaininfoDao;
	@Autowired
	private ISaleenergyinfoDao saleenergyinfoDao;
	@Autowired
	private ISaleenergyruleDao saleenergyruleDao;
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
	 * 销售员行动力兑换提成
	 * 
	 * @param Userorder
	 * @return
	 */
	public Map<String, Object> saveEnergyExchangeToGain(Saleenergyinfo form, HttpServletRequest request,Operator operator){
		    Map<String, Object> responseMap = new HashMap<String, Object>();	
		   
		    //获取配置的事务信息
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
			TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
		    //加锁
		   synchronized(lock) {
			   String currenttime = Tools.getCurrentTime();
			   try{
				   
				    //保存型号关联的产品类别信息
					String saleenergyinfoStatJson = form.getSaleenergyinfoStatJson();
					if(StringUtils.isEmpty(saleenergyinfoStatJson)){
						responseMap.put("status", "0");//电话号码不能为空
						responseMap.put("result", "操作失败，请重新选择需要兑换的行动力");//
						//回滚
						txManager.rollback(status);
						return responseMap;
					}
				   
					//增加关联的产品类别信息
					JSONArray saleenergyinfoStatJsonArry = JSONArray.fromObject(saleenergyinfoStatJson);
					for (int i = 0; i < saleenergyinfoStatJsonArry.size(); i++){
			            JSONObject saleenergyinfoStatObject = saleenergyinfoStatJsonArry.getJSONObject(i);
			            String gainercode = Tools.getStr(saleenergyinfoStatObject.getString("gainercode"));
			            String energyscore = Tools.getStr(saleenergyinfoStatObject.getString("energyscore"));
			            String ids = Tools.getStr(saleenergyinfoStatObject.getString("ids"));
			            
			            if(StringUtils.isEmpty(gainercode)){
			            	responseMap.put("status", "0");
							responseMap.put("result", "操作失败，员工编号："+ gainercode + "的编号不能为空");//
							//回滚
							txManager.rollback(status);
							return responseMap;
			            }
			            
			            if(StringUtils.isEmpty(energyscore)){
			            	responseMap.put("status", "0");//电话号码不能为空
							responseMap.put("result", "操作失败，员工编号："+ gainercode + "的本次行动力不能为空");//
							//回滚
							txManager.rollback(status);
							return responseMap;
			            }
			            
			            if(StringUtils.isEmpty(ids)){
			            	responseMap.put("status", "0");//电话号码不能为空
							responseMap.put("result", "操作失败，员工编号："+ gainercode + "的行动力记录出现错误，请核对之后再来兑换");//
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
			            
					    //修改已有的提成明细记录为已经提取状态
					    String[] idArray = ids.split(",");
						if (idArray == null || idArray.length < 1) {
							responseMap.put("status", "0");//电话号码不能为空
							responseMap.put("result", "操作失败，员工编号："+ gainercode + "的行动力记录出现错误，请核对之后再来提现");//
							//回滚
							txManager.rollback(status);
							return responseMap;
						}
						
						for (int j = 0; j < idArray.length; j++) {
							Saleenergyinfo saleenergyinfo = saleenergyinfoDao.findById(Integer.parseInt(idArray[j]));
							if(saleenergyinfo == null){
								responseMap.put("status", "0");//电话号码不能为空
								responseMap.put("result", "操作失败，员工编号："+ gainercode + "的行动力记录出现错误，请核对之后再来提现");//
								//回滚
								txManager.rollback(status);
								return responseMap;
							}
							
							if("1".equals(saleenergyinfo.getStatus())){//已兑换
								responseMap.put("status", "0");//电话号码不能为空
								responseMap.put("result", "操作失败，员工编号："+ gainercode + "的行动力已经兑换提成了");//
								//回滚
								txManager.rollback(status);
								return responseMap;
							}
							
							//行动力兑换提成比例
							Saleenergyrule saleenergy_recharge_vip = saleenergyruleDao.findByEnergycode(Saleenergyrule.energycode_energy_gain);
							if(saleenergy_recharge_vip  == null){
								responseMap.put("status", "0");//电话号码不能为空
								responseMap.put("result", "操作失败，系统未配置行动力兑换提成比例数据");//
								//回滚
								txManager.rollback(status);
								return responseMap;
							}
							if(saleenergy_recharge_vip.getEnergyrate() <= 0){//不大于0
								responseMap.put("status", "0");//电话号码不能为空
								responseMap.put("result", "操作失败，系统未开放行动力兑换提成活动");//
								//回滚
								txManager.rollback(status);
								return responseMap;
							}
							
							saleenergyinfo.setStatus("1");//更新兑换状态
							saleenergyinfo.setGainrate(saleenergy_recharge_vip.getEnergyrate());
							saleenergyinfo.setGainmoney(saleenergy_recharge_vip.getEnergyrate() * saleenergyinfo.getEnergyscore());
							saleenergyinfo.setExchangetime(currenttime);//更新兑换时间
							saleenergyinfo.setOperatorcode(operator.getEmployeecode());
							//保存兑换状态
							saleenergyinfoDao.update(saleenergyinfo);
							
							//添加提成记录
							Salegaininfo salegaininfo = new Salegaininfo();
							salegaininfo.setUsercode(saleenergyinfo.getUsercode());
							salegaininfo.setUsername(saleenergyinfo.getUsername());
							salegaininfo.setPhone(saleenergyinfo.getPhone());
							salegaininfo.setAddress(saleenergyinfo.getAddress());
							salegaininfo.setSource(saleenergyinfo.getSource());
							salegaininfo.setVisittype(saleenergyinfo.getVisittype());
							salegaininfo.setTaskcode(saleenergyinfo.getTaskcode());
							salegaininfo.setSalercode(saleenergyinfo.getSalercode());
							salegaininfo.setVisitorcode(saleenergyinfo.getVisitorcode());
							salegaininfo.setOrdercode(saleenergyinfo.getOrdercode());
							salegaininfo.setTotalmoney(saleenergyinfo.getTotalmoney());
							salegaininfo.setStatus("0");//未提取
							salegaininfo.setGainercode(saleenergyinfo.getGainercode());
							salegaininfo.setGaincode(saleenergy_recharge_vip.getEnergycode());
							salegaininfo.setGainname(saleenergy_recharge_vip.getEnergyname());
							salegaininfo.setGainrate(saleenergy_recharge_vip.getEnergyrate());
							Integer exchangegainmoney  = saleenergyinfo.getEnergyscore() * saleenergy_recharge_vip.getEnergyrate();
							salegaininfo.setGainmoney(exchangegainmoney);
							//行动力分
							salegaininfo.setEnergyscore(saleenergyinfo.getEnergyscore());
							salegaininfo.setEnergyinfoid(saleenergyinfo.getId());
							salegaininfo.setAddtime(saleenergyinfo.getAddtime());
							
							salegaininfoDao.save(salegaininfo);
							
							//直接增加销售员提成记录
							//employeeDao.updateSalescoreByEmployeecode(salegaininfo.getGainercode(), exchangegainmoney);
						}
					}
					
					//增加操作日记
					String content = "保存行动力兑换提成";
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
