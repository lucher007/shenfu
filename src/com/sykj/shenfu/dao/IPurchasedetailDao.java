package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Purchasedetail;
import com.sykj.shenfu.po.Userproduct;

/**
 * 用户数据层接口
 */
public interface IPurchasedetailDao {
	/**
	 * 采购单明细添加
	 * 
	 * @param ScaleConf
	 * @return
	 */
	public Integer save(Purchasedetail form);
	
	/**
	 * 采购单明细更新
	 * 
	 * @param Purchasedetail
	 * @return
	 */
	public Integer update(Purchasedetail form);
	
	/**
	 * 采购单明细删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 采购单明细分页查询
	 * 
	 * @param Purchasedetail
	 * @return
	 */
	public List<Purchasedetail> findByList(Purchasedetail form);
	
	/**
	 * 采购单明细全部查询
	 * 
	 * @param Purchasedetail
	 * @return
	 */
	public List<Purchasedetail> queryByList(Purchasedetail form);
	
	/**
	 * 采购单明细分页总数
	 * 
	 * @param Purchasedetail
	 * @return
	 */
	public Integer findByCount(Purchasedetail form);
	
	/**
	 * 采购单明细查询根据ID
	 * 
	 * @param Purchasedetail
	 * @return
	 */
	public Purchasedetail findById(Integer id);
	
	/**
	 * 删除采购单物料明细根据采购单ID
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteByPurchaseid(Integer purchaseid);
	
	/**
	 * 删除采购单物料明细根据采购单编号
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteByPurchasecode(String purchasecode);
	
	/**
	 * 采购单明细查询根据采购单ID
	 * 
	 * @param Purchasedetail
	 * @return
	 */
	public List<Purchasedetail> findListByPurchaseid(Integer purchaseid);
	
	/**
	 * 采购单明细查询根据采购单编号
	 * 
	 * @param Purchasedetail
	 * @return
	 */
	public List<Purchasedetail> findListByPurchasecode(String purchasecode);
}
