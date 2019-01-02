package com.sykj.shenfu.service;

import java.util.Map;

import org.json.JSONObject;

/**
 * 操作员编码
 */
public interface IHttpForMpsService {
	
	/**
	 * 客户注册-上门类型-0：公司派人讲解测量
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveUserRegisterForVisitorcode0(JSONObject jsonObj);
	
	/**
	 * 客户注册-上门类型-1：销售员亲自讲解测量
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveUserRegisterForVisitorcode1(JSONObject jsonObj);
	
	/**
	 * 客户注册-上门类型-2：已讲解，公司派人上门
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveUserRegisterForVisitorcode2(JSONObject jsonObj);
	
	
	/**
	 * 处理任务生成订单
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveTaskdata(JSONObject jsonObj);
	
	/**
	 * 处理工单完成
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveDispatchdata(JSONObject jsonObj);
	
	/**
	 * 查询讲解上门单-查询类型-0：只讲解人查询
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> findTalkerListInfo(JSONObject jsonObj);
	
	/**
	 * 查询讲解上门单-查询类型-1：只上门人员查询
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> findVisitorListInfo(JSONObject jsonObj);
	
	/**
	 * 查询讲解上门单-查询类型-2：讲解和上门为同一个人查询
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> findTalkerAndVisitorListInfo(JSONObject jsonObj);
	
	/**
	 * 查询客户的订单对应的产品型号以及产品信息
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> findUserproductListInfo(JSONObject jsonObj);
	
	/**
	 * 只讲解保存任务单信息
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveTaskdataForTalker(JSONObject jsonObj);
	
	/**
	 * 只测量保存任务单信息
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveTaskdataForVisitor(JSONObject jsonObj);
	
	/**
	 * 讲解加测量保存订户产品信息
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveTaskdataForTalkerAndVisitor(JSONObject jsonObj);
	
	/**
	 * 保存客户支付信息
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> savePaymentInfo(JSONObject jsonObj);
	
	/**
	 * 保存拒绝任务单安装
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveRefuseUsertask(JSONObject jsonObj);
	/**
	 * 小区抢购
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveAcceptCellextend(JSONObject jsonObj);
	
	
	/**
	 * 保存扫楼支付信息
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveCellpayInfo(JSONObject jsonObj);
	
	/**
	 * 保存小区驻点
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveCellstation(JSONObject jsonObj);
	
	/**
	 * 客户注册-小区驻点
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveUserRegisterForCellstation(JSONObject jsonObj);
	
	/**
	 * 查询销售员下级成员信息（团队）
	 * @param String
	 * @return
	 */
	public Map<String, Object> findChildrenSalerInfoListBySalercode(JSONObject jsonObj);
	
	/**
	 * 查询销售员提成统计信息
	 * @param String
	 * @return
	 */
	public Map<String, Object> findSalegaininfoStat(JSONObject jsonObj);
	
	/**
	 * 处理送货单为已送货
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveAcceptUserdelivery(JSONObject jsonObj);
	
	/**
	 * 客户拒绝收货
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveRefuseUserdelivery(JSONObject jsonObj);
	
	/**
	 * 保存订单购买-讲解类型-0：公司派人讲解
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveUserorderForVisittype0(JSONObject jsonObj);
	
	/**
	 * 保存订单购买-讲解类型-1：销售员亲自讲解
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveUserorderForVisittype1(JSONObject jsonObj);
	
	/**
	 * 查询未生成订单的客户信息-渠道方
	 * @param String
	 * @return
	 */
	public Map<String, Object> findUserListBySalercode(JSONObject jsonObj);
	
	/**
	 * 查询已生成订单的客户订单信息-渠道方
	 * @param String
	 * @return
	 */
	public Map<String, Object> findUserorderListBySalercode(JSONObject jsonObj);
	
	/**
	 * 保存讲解人员上门任务单提交
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveUserTaskByTalkercode(JSONObject jsonObj);
	
	/**
	 * 讲解员修改客户订单价格
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> updateUserorderPrice(JSONObject jsonObj);
	
	/**
	 * APP删除门锁照片
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> deleteUserdoor(JSONObject jsonObj);
	
	/**
	 * APP添加门锁图片
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveUserdoor(JSONObject jsonObj);
	
	/**
	 * APP修改门锁数据
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> updateUserdoordata(JSONObject jsonObj);
}
