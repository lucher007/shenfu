package com.sykj.shenfu.dao;

import java.util.List;

import com.sykj.shenfu.po.Role;

/**
 * 数据层接口
 */
public interface IRoleDao {
	
	/**
	 * 角色添加
	 * 
	 * @param ScaleConf
	 * @return
	 */
	public Integer save(Role form);
	
	/**
	 * 角色更新
	 * 
	 * @param Role
	 * @return
	 */
	public Integer update(Role form);
	
	/**
	 * 角色删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 角色分页查询
	 * 
	 * @param Role
	 * @return
	 */
	public List<Role> findByList(Role form);
	
	/**
	 * 角色全部查询
	 * 
	 * @param Role
	 * @return
	 */
	public List<Role> queryByList(Role form);
	
	/**
	 * 角色分页总数
	 * 
	 * @param Role
	 * @return
	 */
	public Integer findByCount(Role form);
	
	/**
	 * 角色查询根据ID
	 * 
	 * @param Role
	 * @return
	 */
	public Role findById(Integer id);

	/**
	 * 
	 * @param String
	 * @return
	 */
	public Role findByRolecode(String rolecode);
	

	
	
	
}
