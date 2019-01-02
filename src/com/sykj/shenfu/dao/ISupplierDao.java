package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Supplier;

/**
 * 用户数据层接口
 */
public interface ISupplierDao {
	/**
	 * 供应商添加
	 * 
	 * @param ScaleConf
	 * @return
	 */
	public Integer save(Supplier form);
	
	/**
	 * 供应商更新
	 * 
	 * @param Supplier
	 * @return
	 */
	public Integer update(Supplier form);
	
	/**
	 * 供应商删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 供应商分页查询
	 * 
	 * @param Supplier
	 * @return
	 */
	public List<Supplier> findByList(Supplier form);
	
	/**
	 * 供应商全部查询
	 * 
	 * @param Supplier
	 * @return
	 */
	public List<Supplier> queryByList(Supplier form);
	
	/**
	 * 供应商分页总数
	 * 
	 * @param Supplier
	 * @return
	 */
	public Integer findByCount(Supplier form);
	
	/**
	 * 供应商查询根据ID
	 * 
	 * @param Supplier
	 * @return
	 */
	public Supplier findById(Integer id);
	

}
