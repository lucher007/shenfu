package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Userorder;

/**
 * 用户数据层接口
 */
public interface IUserorderDao {
	/**
	 * 订单添加
	 * 
	 * @param Userorder
	 * @return
	 */
	public Integer save(Userorder form);
	
	/**
	 * 订单更新
	 * 
	 * @param Userorder
	 * @return
	 */
	public Integer update(Userorder form);
	
	/**
	 * 订单删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 订单分页查询
	 * 
	 * @param Userorder
	 * @return
	 */
	public List<Userorder> findByList(Userorder form);
	
	/**
	 * 订单全部查询
	 * 
	 * @param Userorder
	 * @return
	 */
	public List<Userorder> queryByList(Userorder form);
	
	/**
	 * 订单分页总数
	 * 
	 * @param Userorder
	 * @return
	 */
	public Integer findByCount(Userorder form);
	
	/**
	 * 订单查询根据ID
	 * 
	 * @param Userorder
	 * @return
	 */
	public Userorder findById(Integer id);
	
	/**
	 * 订单查询根据订单号
	 * 
	 * @param Userorder
	 * @return
	 */
	public Userorder findByOrdercode(String ordercode);
	
	/**
	 * 更改订单状态
	 * 
	 * @param Userorder
	 * @return
	 */
	public Integer updateStatus(Userorder form);
	
	/**
	 * 更改订单完成状态
	 * 
	 * @param Userorder
	 * @return
	 */
	public Integer updateCompleteState(Userorder form);
	
	/**
	 * 首付款到账
	 * 
	 * @param Userorder
	 * @return
	 */
	public Integer updateFirstarrival(Userorder form);
	
	/**
	 * 尾款到账
	 * 
	 * @param Userorder
	 * @return
	 */
	public Integer updateFinalarrival(Userorder form);
	
	/**
	 * 订单归档
	 * 
	 * @param Userorder
	 * @return
	 */
	public Integer updatefilingflag(Userorder form);
	
	/**
	 * 订单查询通过电话号码
	 * 
	 * @param Userorder
	 * @return
	 */
	public List<Userorder> findByPhone(String phone);

}
