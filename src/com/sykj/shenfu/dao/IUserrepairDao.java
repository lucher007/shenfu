package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Userrepair;

/**
 * 用户数据层接口
 */
public interface IUserrepairDao {
	/**
	 * 维修单添加
	 * 
	 * @param Userrepair
	 * @return
	 */
	public Integer save(Userrepair form);
	
	/**
	 * 维修单更新
	 * 
	 * @param Userrepair
	 * @return
	 */
	public Integer update(Userrepair form);
	
	/**
	 * 维修单删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 维修单分页查询
	 * 
	 * @param Userrepair
	 * @return
	 */
	public List<Userrepair> findByList(Userrepair form);
	
	/**
	 * 维修单全部查询
	 * 
	 * @param Userrepair
	 * @return
	 */
	public List<Userrepair> queryByList(Userrepair form);
	
	/**
	 * 维修单分页总数
	 * 
	 * @param Userrepair
	 * @return
	 */
	public Integer findByCount(Userrepair form);
	
	/**
	 * 维修单查询根据ID
	 * 
	 * @param Userrepair
	 * @return
	 */
	public Userrepair findById(Integer id);
	
	
	/**
	 * 维修单查询根据报修单号
	 * 
	 * @param Userrepair
	 * @return
	 */
	public Userrepair findByRepaircode(String repaircode);
}
