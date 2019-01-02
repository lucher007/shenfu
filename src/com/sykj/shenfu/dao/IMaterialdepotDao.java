package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Materialdepot;

/**
 * 用户数据层接口
 */
public interface IMaterialdepotDao {
	/**
	 * 材料库存添加
	 * 
	 * @param Materialdepot
	 * @return
	 */
	public Integer save(Materialdepot form);
	
	/**
	 * 材料库存更新
	 * 
	 * @param Materialdepot
	 * @return
	 */
	public Integer update(Materialdepot form);
	
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
	 * @param Materialdepot
	 * @return
	 */
	public List<Materialdepot> findByList(Materialdepot form);
	
	/**
	 * 材料库存全部查询
	 * 
	 * @param Materialdepot
	 * @return
	 */
	public List<Materialdepot> queryByList(Materialdepot form);
	
	/**
	 * 材料库存分页总数
	 * 
	 * @param Materialdepot
	 * @return
	 */
	public Integer findByCount(Materialdepot form);
	
	/**
	 * 材料库存查询根据ID
	 * 
	 * @param Materialdepot
	 * @return
	 */
	public Materialdepot findById(Integer id);
	
	
	/**
	 * 库存更新
	 * 
	 * @param Materialdepot
	 * @return
	 */
	public Integer updateDepotamount(Materialdepot form);
	
	/**
	 * 材料库存查询根据批次号
	 * 
	 * @param Materialdepot
	 * @return
	 */
	public Materialdepot findByBatchno(String batchno);
	
	/**
	 * 材料库存查询根据药材SN
	 * 
	 * @param Materialdepot
	 * @return
	 */
	public Materialdepot findByMaterialidentfy(String materialidentfy);
	
	/**
	 * 根据批次号分组查询材料库存的信息
	 * 
	 * @param Materialdepot
	 * @return
	 */
	public List<Materialdepot> queryMatarialListGroupByBatchno(Materialdepot form);
	
	/**
	 * 材料库存查询根据批次号分组
	 * 
	 * @param Materialdepot
	 * @return
	 */
	public Materialdepot findMatarialGroupByBatchno(Materialdepot form);
	
    
	/**
	 * 查询某一批次号最小的材料SN
	 * 
	 * @param Materialdepot
	 * @return
	 */
	public Materialdepot findMinIdentfyByBatchno(Materialdepot form);
}
