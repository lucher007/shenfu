package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Materialinout;

/**
 * 用户数据层接口
 */
public interface IMaterialinoutDao {
	/**
	 * 材料出入库添加
	 * 
	 * @param Materialinout
	 * @return
	 */
	public Integer save(Materialinout form);
	
	/**
	 * 材料出入库更新
	 * 
	 * @param Materialinout
	 * @return
	 */
	public Integer update(Materialinout form);
	
	/**
	 * 材料出入库删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 材料出入库分页查询
	 * 
	 * @param Materialinout
	 * @return
	 */
	public List<Materialinout> findByList(Materialinout form);
	
	/**
	 * 材料出入库全部查询
	 * 
	 * @param Materialinout
	 * @return
	 */
	public List<Materialinout> queryByList(Materialinout form);
	
	/**
	 * 材料出入库分页总数
	 * 
	 * @param Materialinout
	 * @return
	 */
	public Integer findByCount(Materialinout form);
	
	/**
	 * 材料出入库查询根据ID
	 * 
	 * @param Materialinout
	 * @return
	 */
	public Materialinout findById(Integer id);
	
	/**
	 * 材料出入库汇总查询分页
	 * 
	 * @param Materialinout
	 * @return
	 */
	public List<Materialinout> findMaterialinoutStat(Materialinout form);
	
	/**
	 * 材料出入库汇总查询分页总数
	 * 
	 * @param Materialinout
	 * @return
	 */
	public Integer findMaterialinoutStatCount(Materialinout form);
	
	/**
	 * 材料出入库汇总查询不分页
	 * 
	 * @param Materialinout
	 * @return
	 */
	public List<Materialinout> queryMaterialinoutStat(Materialinout form);
}
