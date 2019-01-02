package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Purchase;

/**
 * 用户数据层接口
 */
public interface IPurchaseDao {
	/**
	 * 采购单添加
	 * 
	 * @param ScaleConf
	 * @return
	 */
	public Integer save(Purchase form);
	
	/**
	 * 采购单更新
	 * 
	 * @param Purchase
	 * @return
	 */
	public Integer update(Purchase form);
	
	/**
	 * 采购单删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 采购单分页查询
	 * 
	 * @param Purchase
	 * @return
	 */
	public List<Purchase> findByList(Purchase form);
	
	/**
	 * 采购单全部查询
	 * 
	 * @param Purchase
	 * @return
	 */
	public List<Purchase> queryByList(Purchase form);
	
	/**
	 * 采购单分页总数
	 * 
	 * @param Purchase
	 * @return
	 */
	public Integer findByCount(Purchase form);
	
	/**
	 * 采购单查询根据ID
	 * 
	 * @param Purchase
	 * @return
	 */
	public Purchase findById(Integer id);
	
	/**
	 * 查询最大采购单号
	 * 
	 * @param Purchase
	 * @return
	 */
	public String findMaxPurchasecode();

}
