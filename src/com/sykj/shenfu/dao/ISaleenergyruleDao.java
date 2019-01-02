package com.sykj.shenfu.dao;

import java.util.List;

import com.sykj.shenfu.po.Saleenergyrule;

/**
 * 用户数据层接口
 */
public interface ISaleenergyruleDao {
	/**
	 * 添加
	 * 
	 * @param Saleenergyrule
	 * @return
	 */
	public Integer save(Saleenergyrule form);
	
	/**
	 * 更新
	 * 
	 * @param Saleenergyrule
	 * @return
	 */
	public Integer update(Saleenergyrule form);
	
	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 分页查询
	 * 
	 * @param Saleenergyrule
	 * @return
	 */
	public List<Saleenergyrule> findByList(Saleenergyrule form);
	
	/**
	 * 全部查询
	 * 
	 * @param Saleenergyrule
	 * @return
	 */
	public List<Saleenergyrule> queryByList(Saleenergyrule form);
	
	/**
	 * 分页总数
	 * 
	 * @param Saleenergyrule
	 * @return
	 */
	public Integer findByCount(Saleenergyrule form);
	
	/**
	 * 查询根据ID
	 * 
	 * @param Saleenergyrule
	 * @return
	 */
	public Saleenergyrule findById(Integer id);
	
	/**
	 * 查询根据编码
	 * 
	 * @param Saleenergyrule
	 * @return
	 */
	public Saleenergyrule findByEnergycode(String energycode);

}
