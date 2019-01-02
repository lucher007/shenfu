package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Productinout;

/**
 * 用户数据层接口
 */
public interface IProductinoutDao {
	/**
	 * 产品出入库添加
	 * 
	 * @param Productinout
	 * @return
	 */
	public Integer save(Productinout form);
	
	/**
	 * 产品出入库更新
	 * 
	 * @param Productinout
	 * @return
	 */
	public Integer update(Productinout form);
	
	/**
	 * 产品出入库删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 产品出入库分页查询
	 * 
	 * @param Productinout
	 * @return
	 */
	public List<Productinout> findByList(Productinout form);
	
	/**
	 * 产品出入库全部查询
	 * 
	 * @param Productinout
	 * @return
	 */
	public List<Productinout> queryByList(Productinout form);
	
	/**
	 * 产品出入库分页总数
	 * 
	 * @param Productinout
	 * @return
	 */
	public Integer findByCount(Productinout form);
	
	/**
	 * 产品出入库查询根据ID
	 * 
	 * @param Productinout
	 * @return
	 */
	public Productinout findById(Integer id);
	
	/**
	 * 产品出入库汇总查询分页
	 * 
	 * @param Materialinout
	 * @return
	 */
	public List<Productinout> findProductinoutStat(Productinout form);
	
	/**
	 * 产品出入库汇总查询分页总数
	 * 
	 * @param Productinout
	 * @return
	 */
	public Integer findProductinoutStatCount(Productinout form);
	
	/**
	 * 产品出入库汇总查询不分页
	 * 
	 * @param Productinout
	 * @return
	 */
	public List<Productinout> queryProductinoutStat(Productinout form);
    
}
