package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Userproduct;

/**
 * 用户数据层接口
 */
public interface IUserproductDao {
	/**
	 * 订单产品添加
	 * 
	 * @param Userproduct
	 * @return
	 */
	public Integer save(Userproduct form);
	
	/**
	 * 订单产品更新
	 * 
	 * @param Userproduct
	 * @return
	 */
	public Integer update(Userproduct form);
	
	/**
	 * 订单产品标识更新
	 * 
	 * @param Userproduct
	 * @return
	 */
	public Integer updateProductidentfy(Userproduct form);
	
	/**
	 * 订单产品删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 订单产品分页查询
	 * 
	 * @param Userproduct
	 * @return
	 */
	public List<Userproduct> findByList(Userproduct form);
	
	/**
	 * 订单产品全部查询
	 * 
	 * @param Userproduct
	 * @return
	 */
	public List<Userproduct> queryByList(Userproduct form);
	
	/**
	 * 订单产品分页总数
	 * 
	 * @param Userproduct
	 * @return
	 */
	public Integer findByCount(Userproduct form);
	
	/**
	 * 订单产品查询根据ID
	 * 
	 * @param Userproduct
	 * @return
	 */
	public Userproduct findById(Integer id);
	
	/**
	 * 订单产品查询根据ID
	 * 
	 * @param Userproduct
	 * @return
	 */
	public Userproduct findByProductidentfy(String productidentfy);
	
	/**
	 * 删除订户产品根据订单ID
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteByOrdercode(String ordercode);
	
	/**
	 * 订单产品查询根据订单编码
	 * 
	 * @param Userproduct
	 * @return
	 */
	public List<Userproduct> findUserproductListByOrdercode(String ordercode);

}
