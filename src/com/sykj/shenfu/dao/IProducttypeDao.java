package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Producttype;

/**
 * 用户数据层接口
 */
public interface IProducttypeDao {
	/**
	 * 产品类别添加
	 * 
	 * @param ScaleConf
	 * @return
	 */
	public Integer save(Producttype form);
	
	/**
	 * 产品类别更新
	 * 
	 * @param Producttype
	 * @return
	 */
	public Integer update(Producttype form);
	
	/**
	 * 产品类别删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 产品类别分页查询
	 * 
	 * @param Producttype
	 * @return
	 */
	public List<Producttype> findByList(Producttype form);
	
	/**
	 * 产品类别全部查询
	 * 
	 * @param Producttype
	 * @return
	 */
	public List<Producttype> queryByList(Producttype form);
	
	/**
	 * 产品类别分页总数
	 * 
	 * @param Producttype
	 * @return
	 */
	public Integer findByCount(Producttype form);
	
	/**
	 * 产品类别查询根据ID
	 * 
	 * @param Producttype
	 * @return
	 */
	public Producttype findById(Integer id);
	
	/**
	 * 产品类别查询根据产品类别名称编号
	 * 
	 * @param Producttype
	 * @return
	 */
	public Producttype findByTypecode(String typecode);

}
