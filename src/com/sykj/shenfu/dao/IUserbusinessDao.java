package com.sykj.shenfu.dao;

import java.util.List;

import com.sykj.shenfu.po.Userbusiness;

/**
 * 数据层接口
 */
public interface IUserbusinessDao {
	
	/**
	 * 添加
	 * 
	 * @param ScaleConf
	 * @return
	 */
	public Integer save(Userbusiness form);
	
	/**
	 * 更新
	 * 
	 * @param Userbusiness
	 * @return
	 */
	public Integer update(Userbusiness form);
	
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
	 * @param Userbusiness
	 * @return
	 */
	public List<Userbusiness> findByList(Userbusiness form);
	
	/**
	 * 全部查询
	 * 
	 * @param Userbusiness
	 * @return
	 */
	public List<Userbusiness> queryByList(Userbusiness form);
	
	/**
	 * 分页总数
	 * 
	 * @param Userbusiness
	 * @return
	 */
	public Integer findByCount(Userbusiness form);
	
	/**
	 * 查询根据ID
	 * 
	 * @param Userbusiness
	 * @return
	 */
	public Userbusiness findById(Integer id);
	
}
