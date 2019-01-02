package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Operatorlog;

/**
 * 用户数据层接口
 */
public interface IOperatorlogDao {
	/**
	 * 材料添加
	 * 
	 * @param ScaleConf
	 * @return
	 */
	public Integer save(Operatorlog form);
	
	/**
	 * 材料更新
	 * 
	 * @param Operatorlog
	 * @return
	 */
	public Integer update(Operatorlog form);
	
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
	 * @param Operatorlog
	 * @return
	 */
	public List<Operatorlog> findByList(Operatorlog form);
	
	/**
	 * 材料全部查询
	 * 
	 * @param Operatorlog
	 * @return
	 */
	public List<Operatorlog> queryByList(Operatorlog form);
	
	/**
	 * 材料分页总数
	 * 
	 * @param Operatorlog
	 * @return
	 */
	public Integer findByCount(Operatorlog form);
	
	/**
	 * 材料查询根据ID
	 * 
	 * @param Operatorlog
	 * @return
	 */
	public Operatorlog findById(Integer id);

}
