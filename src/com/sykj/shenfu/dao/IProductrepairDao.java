package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Productrepair;

/**
 * 用户数据层接口
 */
public interface IProductrepairDao {
	/**
	 * 订单产品更换添加
	 * 
	 * @param Userproduct
	 * @return
	 */
	public Integer save(Productrepair form);
	
	/**
	 * 订单产品更换更新
	 * 
	 * @param Productrepair
	 * @return
	 */
	public Integer update(Productrepair form);
	
	/**
	 * 订单产品更换删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 订单产品更换分页查询
	 * 
	 * @param Productrepair
	 * @return
	 */
	public List<Productrepair> findByList(Productrepair form);
	
	/**
	 * 订单产品更换全部查询
	 * 
	 * @param Productrepair
	 * @return
	 */
	public List<Productrepair> queryByList(Productrepair form);
	
	/**
	 * 订单产品更换分页总数
	 * 
	 * @param Productrepair
	 * @return
	 */
	public Integer findByCount(Productrepair form);
	
	/**
	 * 订单产品更换查询根据ID
	 * 
	 * @param Productrepair
	 * @return
	 */
	public Productrepair findById(Integer id);

}
