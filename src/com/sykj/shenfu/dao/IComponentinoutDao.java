package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Componentinout;

/**
 * 用户数据层接口
 */
public interface IComponentinoutDao {
	/**
	 * 原器件出入库添加
	 * 
	 * @param Componentinout
	 * @return
	 */
	public Integer save(Componentinout form);
	
	/**
	 * 原器件出入库更新
	 * 
	 * @param Componentinout
	 * @return
	 */
	public Integer update(Componentinout form);
	
	/**
	 * 原器件出入库删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 原器件出入库分页查询
	 * 
	 * @param Componentinout
	 * @return
	 */
	public List<Componentinout> findByList(Componentinout form);
	
	/**
	 * 原器件出入库全部查询
	 * 
	 * @param Componentinout
	 * @return
	 */
	public List<Componentinout> queryByList(Componentinout form);
	
	/**
	 * 原器件出入库分页总数
	 * 
	 * @param Componentinout
	 * @return
	 */
	public Integer findByCount(Componentinout form);
	
	/**
	 * 原器件出入库查询根据ID
	 * 
	 * @param Componentinout
	 * @return
	 */
	public Componentinout findById(Integer id);
	
	/**
	 * 原器件出入库汇总查询分页
	 * 
	 * @param Componentinout
	 * @return
	 */
	public List<Componentinout> findComponentinoutStat(Componentinout form);
	
	/**
	 * 原器件出入库汇总查询分页总数
	 * 
	 * @param Componentinout
	 * @return
	 */
	public Integer findComponentinoutStatCount(Componentinout form);
	
	/**
	 * 原器件出入库汇总查询不分页
	 * 
	 * @param Componentinout
	 * @return
	 */
	public List<Componentinout> queryComponentinoutStat(Componentinout form);
}
