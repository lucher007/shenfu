package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Product;

/**
 * 用户数据层接口
 */
public interface IProductDao {
	/**
	 * 产品添加
	 * 
	 * @param ScaleConf
	 * @return
	 */
	public Integer save(Product form);
	
	/**
	 * 产品更新
	 * 
	 * @param Product
	 * @return
	 */
	public Integer update(Product form);
	
	/**
	 * 产品删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 产品分页查询
	 * 
	 * @param Product
	 * @return
	 */
	public List<Product> findByList(Product form);
	
	/**
	 * 产品全部查询
	 * 
	 * @param Product
	 * @return
	 */
	public List<Product> queryByList(Product form);
	
	/**
	 * 产品分页总数
	 * 
	 * @param Product
	 * @return
	 */
	public Integer findByCount(Product form);
	
	/**
	 * 产品查询根据ID
	 * 
	 * @param Product
	 * @return
	 */
	public Product findById(Integer id);
	
	/**
	 * 产品查询根据产品名称编号
	 * 
	 * @param Product
	 * @return
	 */
	public Product findByProductcode(String productcode);
	
	/**
	 * 产品全部查询根据产品类别
	 * 
	 * @param Product
	 * @return
	 */
	public List<Product> findByTypecode(String typecode);

}
