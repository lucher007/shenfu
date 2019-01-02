package com.sykj.shenfu.dao;

import java.util.ArrayList;
import java.util.List;

import com.sykj.shenfu.po.Rolemenuref;

/**
 * 数据层接口
 */
public interface IRolemenurefDao {
	
	/**
	 * 角色菜单关系添加
	 * 
	 * @param ScaleConf
	 * @return
	 */
	public Integer save(Rolemenuref form);
	
	/**
	 * 角色菜单关系更新
	 * 
	 * @param Rolemenuref
	 * @return
	 */
	public Integer update(Rolemenuref form);
	
	/**
	 * 角色菜单关系删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 角色菜单关系分页查询
	 * 
	 * @param Rolemenuref
	 * @return
	 */
	public List<Rolemenuref> findByList(Rolemenuref form);
	
	/**
	 * 角色菜单关系全部查询
	 * 
	 * @param Rolemenuref
	 * @return
	 */
	public List<Rolemenuref> queryByList(Rolemenuref form);
	
	/**
	 * 角色菜单关系分页总数
	 * 
	 * @param Rolemenuref
	 * @return
	 */
	public Integer findByCount(Rolemenuref form);
	
	/**
	 * 角色菜单关系查询根据ID
	 * 
	 * @param Rolemenuref
	 * @return
	 */
	public Rolemenuref findById(Integer id);
	
	/**
	 * @param roleid
	 * @return
	 */
	public List<Rolemenuref> findByRoleid(Integer roleid);
	

	/**
	 * 批量导入
	 * @param Rolemenuref
	 * @return
	 */
	public Integer saveBatch(ArrayList<Rolemenuref>reflist);
	
	
	/**
	 * 角色菜单关系删除
	 * 
	 * @param id
	 * @return
	 */
	public  Integer deleteByRoleid(Integer roleid);
	
	
	
}
