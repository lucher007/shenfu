package com.sykj.shenfu.dao;


import java.util.ArrayList;
import java.util.List;

import com.sykj.shenfu.po.Wechatpay;

/**
 * 用户数据层接口
 */
public interface IWechatpayDao {
	/**
	 * 微信支付添加
	 * 
	 * @param Wechatpay
	 * @return
	 */
	public Integer save(Wechatpay form);
	
	/**
	 * 微信支付更新
	 * 
	 * @param Wechatpay
	 * @return
	 */
	public Integer update(Wechatpay form);
	
	/**
	 * 微信支付删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 微信支付分页查询
	 * 
	 * @param Wechatpay
	 * @return
	 */
	public List<Wechatpay> findByList(Wechatpay form);
	
	/**
	 * 微信支付全部查询
	 * 
	 * @param Wechatpay
	 * @return
	 */
	public List<Wechatpay> queryByList(Wechatpay form);
	
	/**
	 * 微信支付分页总数
	 * 
	 * @param Wechatpay
	 * @return
	 */
	public Integer findByCount(Wechatpay form);
	
	/**
	 * 微信支付查询根据ID
	 * 
	 * @param Wechatpay
	 * @return
	 */
	public Wechatpay findById(Integer id);
	
	
}
