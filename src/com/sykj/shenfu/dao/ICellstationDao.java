package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Cellstation;

/**
 * 用户数据层接口
 */
public interface ICellstationDao {
	/**
	 * 小区驻点添加
	 * 
	 * @param Cell
	 * @return
	 */
	public Integer save(Cellstation form);
	
	/**
	 * 小区驻点更新
	 * 
	 * @param Cell
	 * @return
	 */
	public Integer update(Cellstation form);
	
	/**
	 * 小区驻点删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 小区驻点分页查询
	 * 
	 * @param Cell
	 * @return
	 */
	public List<Cellstation> findByList(Cellstation form);
	
	/**
	 * 小区驻点全部查询
	 * 
	 * @param Cell
	 * @return
	 */
	public List<Cellstation> queryByList(Cellstation form);
	
	/**
	 * 小区驻点分页总数
	 * 
	 * @param Cell
	 * @return
	 */
	public Integer findByCount(Cellstation form);
	
	/**
	 * 小区驻点查询根据ID
	 * 
	 * @param Cell
	 * @return
	 */
	public Cellstation findById(Integer id);
	
	/**
	 * 小区驻点查询根据小区驻点编号
	 * 
	 * @param Cell
	 * @return
	 */
	public Cellstation findByStationcode(String stationcode);
	
	/**
	 * 小区驻点查询根据小区发布编号
	 * 
	 * @param Cell
	 * @return
	 */
	public Cellstation findByExtendcode(String extendcode);
}
