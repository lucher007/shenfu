package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Userdispatchfile;

/**
 * 用户数据层接口
 */
public interface IUserdispatchfileDao {
	/**
	 * 任务单添加
	 * 
	 * @param Userdispatchfile
	 * @return
	 */
	public Integer save(Userdispatchfile form);
	
	/**
	 * 任务单更新
	 * 
	 * @param Userdispatchfile
	 * @return
	 */
	public Integer update(Userdispatchfile form);
	
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
	 * @param Userdispatchfile
	 * @return
	 */
	public List<Userdispatchfile> findByList(Userdispatchfile form);
	
	/**
	 * 任务单全部查询
	 * 
	 * @param Userdispatchfile
	 * @return
	 */
	public List<Userdispatchfile> queryByList(Userdispatchfile form);
	
	/**
	 * 任务单分页总数
	 * 
	 * @param Userdispatchfile
	 * @return
	 */
	public Integer findByCount(Userdispatchfile form);
	
	/**
	 * 工单查询根据ID
	 * 
	 * @param Userdispatchfile
	 * @return
	 */
	public Userdispatchfile findById(Integer id);
	
	/**
	 * 删除工单根据订单编号
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteByOrdercode(String ordercode);
	
	/**
	 * 删除工单根据订单编号
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteByDispatchcode(String dispatchcode);

}
