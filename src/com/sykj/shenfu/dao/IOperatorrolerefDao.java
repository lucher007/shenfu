package com.sykj.shenfu.dao;

import java.util.List;

import com.sykj.shenfu.po.Operatorroleref;


/**
 * 数据层接口
 */
public interface IOperatorrolerefDao {
	
	/**
	 * 操作员角色关系添加
	 * 
	 * @param ScaleConf
	 * @return
	 */
	public Integer save(Operatorroleref form);
	
	/**
	 * 操作员角色关系更新
	 * 
	 * @param Operatorroleref
	 * @return
	 */
	public Integer update(Operatorroleref form);
	
	/**
	 * 操作员角色关系删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 操作员角色关系分页查询
	 * 
	 * @param Operatorroleref
	 * @return
	 */
	public List<Operatorroleref> findByList(Operatorroleref form);
	
	/**
	 * 操作员角色关系全部查询
	 * 
	 * @param Operatorroleref
	 * @return
	 */
	public List<Operatorroleref> queryByList(Operatorroleref form);
	
	/**
	 * 操作员角色关系分页总数
	 * 
	 * @param Operatorroleref
	 * @return
	 */
	public Integer findByCount(Operatorroleref form);
	
	/**
	 * 操作员角色关系查询根据ID
	 * 
	 * @param Operatorroleref
	 * @return
	 */
	public Operatorroleref findById(Integer id);

	/**
	 * 操作员角色关系查询根据ID
	 * 
	 * @param Operatorroleref
	 * @return
	 */
	public Operatorroleref findByOperatorid(Integer id);


	
	
	
}
