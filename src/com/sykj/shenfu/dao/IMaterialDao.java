package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Material;

/**
 * 用户数据层接口
 */
public interface IMaterialDao {
	/**
	 * 材料添加
	 * 
	 * @param ScaleConf
	 * @return
	 */
	public Integer save(Material form);
	
	/**
	 * 材料更新
	 * 
	 * @param Material
	 * @return
	 */
	public Integer update(Material form);
	
	/**
	 * 材料删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 材料分页查询
	 * 
	 * @param Material
	 * @return
	 */
	public List<Material> findByList(Material form);
	
	/**
	 * 材料全部查询
	 * 
	 * @param Material
	 * @return
	 */
	public List<Material> queryByList(Material form);
	
	/**
	 * 材料分页总数
	 * 
	 * @param Material
	 * @return
	 */
	public Integer findByCount(Material form);
	
	/**
	 * 材料查询根据ID
	 * 
	 * @param Material
	 * @return
	 */
	public Material findById(Integer id);
	
	
	/**
	 * 库存更新
	 * 
	 * @param Material
	 * @return
	 */
	public Integer updateDepotamount(Material form);
   
	
	/**
	 * 材料查询根据ID
	 * 
	 * @param Material
	 * @return
	 */
	public Material findByMaterialcodeStr(String materialcode);
	
	/**
	 * 材料查询根据材料名称
	 * 
	 * @param Material
	 * @return
	 */
	public Material findByMaterialname(String materialname);
}
