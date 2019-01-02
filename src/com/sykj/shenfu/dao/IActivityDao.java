package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Activity;

/**
 * 用户数据层接口
 */
public interface IActivityDao {
	/**
	 * 添加
	 * 
	 * @param Activity
	 * @return
	 */
	public Integer save(Activity form);
	
	/**
	 * 更新
	 * 
	 * @param Activity
	 * @return
	 */
	public Integer update(Activity form);
	
	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 分页查询
	 * 
	 * @param Activity
	 * @return
	 */
	public List<Activity> findByList(Activity form);
	
	/**
	 * 全部查询
	 * 
	 * @param Activity
	 * @return
	 */
	public List<Activity> queryByList(Activity form);
	
	/**
	 * 分页总数
	 * 
	 * @param Activity
	 * @return
	 */
	public Integer findByCount(Activity form);
	
	/**
	 * 查询根据ID
	 * 
	 * @param Activity
	 * @return
	 */
	public Activity findById(Integer id);
	
	/**
	 * 查询根据编号
	 * 
	 * @param Activity
	 * @return
	 */
	public Activity findByActivitycode(String activitycode);
	
}
