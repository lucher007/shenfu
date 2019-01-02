package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Usercontract;

/**
 * 用户数据层接口
 */
public interface IUsercontractDao {
	/**
	 * 任务单添加
	 * 
	 * @param Usercontract
	 * @return
	 */
	public Integer save(Usercontract form);
	
	/**
	 * 任务单更新
	 * 
	 * @param Usercontract
	 * @return
	 */
	public Integer update(Usercontract form);
	
	/**
	 * 任务单删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 任务单分页查询
	 * 
	 * @param Usercontract
	 * @return
	 */
	public List<Usercontract> findByList(Usercontract form);
	
	/**
	 * 任务单全部查询
	 * 
	 * @param Usercontract
	 * @return
	 */
	public List<Usercontract> queryByList(Usercontract form);
	
	/**
	 * 任务单分页总数
	 * 
	 * @param Usercontract
	 * @return
	 */
	public Integer findByCount(Usercontract form);
	
	/**
	 * 任务单查询根据ID
	 * 
	 * @param Usercontract
	 * @return
	 */
	public Usercontract findById(Integer id);
	
	/**
	 * 删除合同根据订单编号
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteByOrdercode(String ordercode);

}
