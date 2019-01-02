package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Componentdepot;

/**
 * 用户数据层接口
 */
public interface IComponentdepotDao {
	/**
	 * 材料库存添加
	 * 
	 * @param Componentdepot
	 * @return
	 */
	public Integer save(Componentdepot form);
	
	/**
	 * 材料库存更新
	 * 
	 * @param Componentdepot
	 * @return
	 */
	public Integer update(Componentdepot form);
	
	/**
	 * 材料库存删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 材料库存分页查询
	 * 
	 * @param Componentdepot
	 * @return
	 */
	public List<Componentdepot> findByList(Componentdepot form);
	
	/**
	 * 材料库存全部查询
	 * 
	 * @param Componentdepot
	 * @return
	 */
	public List<Componentdepot> queryByList(Componentdepot form);
	
	/**
	 * 材料库存分页总数
	 * 
	 * @param Componentdepot
	 * @return
	 */
	public Integer findByCount(Componentdepot form);
	
	/**
	 * 材料库存查询根据ID
	 * 
	 * @param Componentdepot
	 * @return
	 */
	public Componentdepot findById(Integer id);
	
	
	/**
	 * 库存更新
	 * 
	 * @param Componentdepot
	 * @return
	 */
	public Integer updateDepotamount(Componentdepot form);
	
	/**
	 * 材料库存查询根据批次号
	 * 
	 * @param Componentdepot
	 * @return
	 */
	public Componentdepot findByBatchno(String batchno);

}
