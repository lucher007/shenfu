package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Component;

/**
 * 用户数据层接口
 */
public interface IComponentDao {
	/**
	 * 材料添加
	 * 
	 * @param ScaleConf
	 * @return
	 */
	public Integer save(Component form);
	
	/**
	 * 材料更新
	 * 
	 * @param Component
	 * @return
	 */
	public Integer update(Component form);
	
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
	 * @param Component
	 * @return
	 */
	public List<Component> findByList(Component form);
	
	/**
	 * 材料全部查询
	 * 
	 * @param Component
	 * @return
	 */
	public List<Component> queryByList(Component form);
	
	/**
	 * 材料分页总数
	 * 
	 * @param Component
	 * @return
	 */
	public Integer findByCount(Component form);
	
	/**
	 * 材料查询根据ID
	 * 
	 * @param Component
	 * @return
	 */
	public Component findById(Integer id);
	
	
	/**
	 * 库存更新
	 * 
	 * @param Component
	 * @return
	 */
	public Integer updateDepotamount(Component form);
   
	
	/**
	 * 材料查询根据ID
	 * 
	 * @param Component
	 * @return
	 */
	public Component findByComponentcode(String componentcode);
}
