package com.sykj.shenfu.dao;


import java.math.BigDecimal;
import java.util.List;

import com.sykj.shenfu.po.Salelevelpara;

/**
 * 用户数据层接口
 */
public interface ISalelevelparaDao {
	/**
	 * 销售等级参数添加
	 * 
	 * @param Salelevelpara
	 * @return
	 */
	public Integer save(Salelevelpara form);
	
	/**
	 * 销售等级参数更新
	 * 
	 * @param Salelevelpara
	 * @return
	 */
	public Integer update(Salelevelpara form);
	
	/**
	 * 销售等级参数删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 销售等级参数分页查询
	 * 
	 * @param Salelevelpara
	 * @return
	 */
	public List<Salelevelpara> findByList(Salelevelpara form);
	
	/**
	 * 销售等级参数全部查询
	 * 
	 * @param Salelevelpara
	 * @return
	 */
	public List<Salelevelpara> queryByList(Salelevelpara form);
	
	/**
	 * 销售等级参数分页总数
	 * 
	 * @param Salelevelpara
	 * @return
	 */
	public Integer findByCount(Salelevelpara form);
	
	/**
	 * 销售等级参数查询根据ID
	 * 
	 * @param Salelevelpara
	 * @return
	 */
	public Salelevelpara findById(Integer id);
	
	/**
	 * 销售等级参数查询根据ID
	 * 
	 * @param Salelevelpara
	 * @return
	 */
	public Salelevelpara findBySalescore(Integer salescore);

}
