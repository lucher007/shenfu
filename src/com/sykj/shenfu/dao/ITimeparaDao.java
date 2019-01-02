package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Timepara;

/**
 * 用户数据层接口
 */
public interface ITimeparaDao {
	/**
	 * 时间参数添加
	 * 
	 * @param Timepara
	 * @return
	 */
	public Integer save(Timepara form);
	
	/**
	 * 时间参数更新
	 * 
	 * @param Timepara
	 * @return
	 */
	public Integer update(Timepara form);
	
	/**
	 * 时间参数删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 时间参数分页查询
	 * 
	 * @param Timepara
	 * @return
	 */
	public List<Timepara> findByList(Timepara form);
	
	/**
	 * 时间参数全部查询
	 * 
	 * @param Timepara
	 * @return
	 */
	public List<Timepara> queryByList(Timepara form);
	
	/**
	 * 时间参数分页总数
	 * 
	 * @param Timepara
	 * @return
	 */
	public Integer findByCount(Timepara form);
	
	/**
	 * 时间参数查询根据ID
	 * 
	 * @param Timepara
	 * @return
	 */
	public Timepara findById(Integer id);

}
