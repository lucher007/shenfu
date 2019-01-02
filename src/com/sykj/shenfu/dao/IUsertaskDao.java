package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Usertask;

/**
 * 用户数据层接口
 */
public interface IUsertaskDao {
	/**
	 * 任务单添加
	 * 
	 * @param Usertask
	 * @return
	 */
	public Integer save(Usertask form);
	
	/**
	 * 任务单更新
	 * 
	 * @param Usertask
	 * @return
	 */
	public Integer update(Usertask form);
	
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
	 * @param Usertask
	 * @return
	 */
	public List<Usertask> findByList(Usertask form);
	
	/**
	 * 任务单全部查询
	 * 
	 * @param Usertask
	 * @return
	 */
	public List<Usertask> queryByList(Usertask form);
	
	/**
	 * 任务单分页总数
	 * 
	 * @param Usertask
	 * @return
	 */
	public Integer findByCount(Usertask form);
	
	/**
	 * 任务单查询根据ID
	 * 
	 * @param Usertask
	 * @return
	 */
	public Usertask findById(Integer id);
    
	/**
	 * 任务单查询根据任务单号
	 * 
	 * @param Sale
	 * @return
	 */
	public Usertask findByTaskcodeStr(String taskcode);
	
	/**
	 * 删除根据订单编号
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteByOrdercode(String ordercode);
}
