package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Userorderinfo;

/**
 * 用户数据层接口
 */
public interface IUserorderinfoDao {
	/**
	 * 订单详情添加
	 * 
	 * @param Userorderinfo
	 * @return
	 */
	public Integer save(Userorderinfo form);
	
	/**
	 * 订单详情更新
	 * 
	 * @param Userorderinfo
	 * @return
	 */
	public Integer update(Userorderinfo form);
	
	/**
	 * 订单详情删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 订单详情分页查询
	 * 
	 * @param Userorderinfo
	 * @return
	 */
	public List<Userorderinfo> findByList(Userorderinfo form);
	
	/**
	 * 订单详情全部查询
	 * 
	 * @param Userorderinfo
	 * @return
	 */
	public List<Userorderinfo> queryByList(Userorderinfo form);
	
	/**
	 * 订单详情分页总数
	 * 
	 * @param Userorderinfo
	 * @return
	 */
	public Integer findByCount(Userorderinfo form);
	
	/**
	 * 订单详情查询根据ID
	 * 
	 * @param Userorderinfo
	 * @return
	 */
	public Userorderinfo findById(Integer id);
	
	/**
	 * 订单详情查询根据订单详情号
	 * 
	 * @param Userorderinfo
	 * @return
	 */
	public List<Userorderinfo> findByOrdercode(String ordercode);
	
	/**
	 * 删除订户产品根据订单ID
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteByOrdercode(String ordercode);
	
}
