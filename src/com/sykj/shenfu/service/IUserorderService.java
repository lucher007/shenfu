package com.sykj.shenfu.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Userorder;

/**
 * 产品库存接口
 */
public interface IUserorderService {
	
	/**
	 * 订单保存-任务单转成订单
	 * 
	 * @param Userorder
	 * @return
	 */
	public Map<String, Object> saveUserorder(Userorder userorderform, HttpServletRequest request, Operator operator);
	
	/**
	 * 补充订单产品参数信息-已经有订单的情况下，上门完善信息
	 * 
	 * @param Userorder
	 * @return
	 */
	public Map<String, Object> saveFillUserorder(Userorder userorderform, HttpServletRequest request, Operator operator);
	
	/**
	 * 订单打包
	 */
	public Map<String, Object> savePackUserorder(Userorder userorderform, HttpServletRequest request, Operator operator);
	
	/**
	 * 订单修改产品型号
	 * 
	 * @param Userorder
	 * @return
	 */
	public Map<String, Object> updateUserorder(Userorder userorderform, HttpServletRequest request, Operator operator);
	
	/**
	 * 订单修改产品参数
	 * 
	 * @param Userorder
	 * @return
	 */
	public Map<String, Object> updateUserproduct(Userorder userorderform, HttpServletRequest request, Operator operator);
	
	/**
	 * 订单结单
	 * 
	 * @param Userorder
	 * @return
	 */
	public Map<String, Object> saveCheckUserorder(Userorder userorderform, HttpServletRequest request, Operator operator);
	
	/**
	 * 订单发货
	 * 
	 * @param Userorder
	 * @return
	 */
	public Map<String, Object> saveDeliveryUserorder(Userorder userorderform, HttpServletRequest request, Operator operator);
	
	/**
	 * 确认订单产品正确
	 * 
	 * @param Userorder
	 * @return
	 */
	public Map<String, Object> updateUserproductconfirm(Userorder userorderform, HttpServletRequest request, Operator operator);
	
	/**
	 * 保存订单产品更换
	 * 
	 * @param Userorder
	 * @return
	 */
	public Map<String, Object> saveUserproductreplace(Userorder userorderform, HttpServletRequest request, Operator operator);
	
	/**
	 * 保存订单产品软件版本升级
	 * 
	 * @param Userorder
	 * @return
	 */
	public Map<String, Object> saveUserproductChangeVersion(Userorder userorderform, HttpServletRequest request, Operator operator);
	
	/**
	 * 订单修改产品价格
	 * 
	 * @param Userorder
	 * @return
	 */
	public Map<String, Object> updateProductfee(Userorder userorderform, HttpServletRequest request, Operator operator);
	
	/**
	 * 订单修改产品详情
	 * 
	 * @param Userorder
	 * @return
	 */
	public Map<String, Object> updateOrderinfo(Userorder userorderform, HttpServletRequest request, Operator operator);
	
	/**
	 * 订单使用优惠卡
	 * 
	 * @param Userorder
	 * @return
	 */
	public Map<String, Object> saveUseGiftcard(Userorder userorderform, HttpServletRequest request, Operator operator);
}
