package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Materialcheck;

/**
 * 用户数据层接口
 */
public interface IMaterialcheckDao {
	/**
	 * 产品颜色添加
	 * 
	 * @param ScaleConf
	 * @return
	 */
	public Integer save(Materialcheck form);
	
	/**
	 * 产品颜色更新
	 * 
	 * @param Materialcheck
	 * @return
	 */
	public Integer update(Materialcheck form);
	
	/**
	 * 产品颜色删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 产品颜色分页查询
	 * 
	 * @param Materialcheck
	 * @return
	 */
	public List<Materialcheck> findByList(Materialcheck form);
	
	/**
	 * 产品颜色全部查询
	 * 
	 * @param Materialcheck
	 * @return
	 */
	public List<Materialcheck> queryByList(Materialcheck form);
	
	/**
	 * 产品颜色分页总数
	 * 
	 * @param Materialcheck
	 * @return
	 */
	public Integer findByCount(Materialcheck form);
	
	/**
	 * 产品颜色查询根据ID
	 * 
	 * @param Materialcheck
	 * @return
	 */
	public Materialcheck findById(Integer id);
	
}
