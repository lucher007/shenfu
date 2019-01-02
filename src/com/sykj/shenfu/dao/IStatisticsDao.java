package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Statistics;

/**
 * 用户数据层接口
 */
public interface IStatisticsDao {
	
	/**
	 * 材料供需统计分页总数
	 * 
	 * @param Cell
	 * @return
	 */
	public Integer findMaterialBalanceStatCount(Statistics form);
	
	/**
	 * 材料供需统计分页查询
	 * 
	 * @param Cell
	 * @return
	 */
	public List<Statistics> findMaterialBalanceStatList(Statistics form);


}
