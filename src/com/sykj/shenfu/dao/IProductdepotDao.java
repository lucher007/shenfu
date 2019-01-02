package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Materialdepot;
import com.sykj.shenfu.po.Productdepot;

/**
 * 用户数据层接口
 */
public interface IProductdepotDao {
	/**
	 * 产品库存添加
	 * 
	 * @param Productdepot
	 * @return
	 */
	public Integer save(Productdepot form);
	
	/**
	 * 产品库存更新
	 * 
	 * @param Productdepot
	 * @return
	 */
	public Integer update(Productdepot form);
	
	/**
	 * 产品库存删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 产品库存分页查询
	 * 
	 * @param Productdepot
	 * @return
	 */
	public List<Productdepot> findByList(Productdepot form);
	
	/**
	 * 产品库存全部查询
	 * 
	 * @param Productdepot
	 * @return
	 */
	public List<Productdepot> queryByList(Productdepot form);
	
	/**
	 * 产品库存分页总数
	 * 
	 * @param Productdepot
	 * @return
	 */
	public Integer findByCount(Productdepot form);
	
	/**
	 * 产品库存查询根据ID
	 * 
	 * @param Productdepot
	 * @return
	 */
	public Productdepot findById(Integer id);
	
	
	/**
	 * 库存更新
	 * 
	 * @param Productdepot
	 * @return
	 */
	public Integer updateDepotamount(Productdepot form);
	
	/**
	 * 产品库存查询根据产品唯一标识号
	 * 
	 * @param Materialdepot
	 * @return
	 */
	public Productdepot findByProductidentfy(String productidentfy);
	
	/**
	 * 产品库存查询根据产品型号编码
	 * 
	 * @param Materialdepot
	 * @return
	 */
	public List<Productdepot> findByProductcode(String productcode);

}
