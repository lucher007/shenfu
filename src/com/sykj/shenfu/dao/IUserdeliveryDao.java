package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Userdelivery;

/**
 * 用户数据层接口
 */
public interface IUserdeliveryDao {
	/**
	 * 快递添加
	 * 
	 * @param Userdelivery
	 * @return
	 */
	public Integer save(Userdelivery form);
	
	/**
	 * 快递更新
	 * 
	 * @param Userdelivery
	 * @return
	 */
	public Integer update(Userdelivery form);
	
	/**
	 * 快递删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 快递分页查询
	 * 
	 * @param Userdelivery
	 * @return
	 */
	public List<Userdelivery> findByList(Userdelivery form);
	
	/**
	 * 快递全部查询
	 * 
	 * @param Userdelivery
	 * @return
	 */
	public List<Userdelivery> queryByList(Userdelivery form);
	
	/**
	 * 快递分页总数
	 * 
	 * @param Userdelivery
	 * @return
	 */
	public Integer findByCount(Userdelivery form);
	
	/**
	 * 快递查询根据ID
	 * 
	 * @param Userdelivery
	 * @return
	 */
	public Userdelivery findById(Integer id);
	
	/**
	 * 删除快递单根据订单编号
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteByOrdercode(String ordercode);

}
