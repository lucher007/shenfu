package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Sale;

/**
 * 用户数据层接口
 */
public interface ISaleDao {
	/**
	 * 销售单添加
	 * 
	 * @param Sale
	 * @return
	 */
	public Integer save(Sale form);
	
	/**
	 * 销售单更新
	 * 
	 * @param Sale
	 * @return
	 */
	public Integer update(Sale form);
	
	/**
	 * 销售单删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 销售单分页查询
	 * 
	 * @param Sale
	 * @return
	 */
	public List<Sale> findByList(Sale form);
	
	/**
	 * 销售单全部查询
	 * 
	 * @param Sale
	 * @return
	 */
	public List<Sale> queryByList(Sale form);
	
	/**
	 * 销售单分页总数
	 * 
	 * @param Sale
	 * @return
	 */
	public Integer findByCount(Sale form);
	
	/**
	 * 销售单查询根据ID
	 * 
	 * @param Sale
	 * @return
	 */
	public Sale findById(Integer id);
	
	/**
	 * 销售单查询根据ID
	 * 
	 * @param Sale
	 * @return
	 */
	public Sale findBySalenoStr(String saleno);
	
	/**
	 * 查询最大销售单号
	 * 
	 * @param Sale
	 * @return
	 */
	public String findMaxSaleno();
	
	//-销售总量统计
	/**
	 * 销售单分页查询
	 * 
	 * @param Sale
	 * @return
	 */
	public List<Sale> findListByTotalstat(Sale form);
	
	/**
	 * 销售单分页总数
	 * 
	 * @param Sale
	 * @return
	 */
	public Integer findCountByTotalstat(Sale form);
	
	/**
	 * 销售总量统计-查询全部
	 * 
	 * @param Sale
	 * @return
	 */
	public List<Sale> queryListByTotalstat(Sale form);
	
	
	//-个人销售总量统计
	/**
	 * 个人销售量统计分页查询
	 * 
	 * @param Sale
	 * @return
	 */
	public List<Sale> findListByPersonalstat(Sale form);

}
