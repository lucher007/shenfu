package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Depotrackinfo;

/**
 * 用户数据层接口
 */
public interface IDepotrackinfoDao {
	/**
	 * 货柜添加
	 * 
	 * @param ScaleConf
	 * @return
	 */
	public Integer save(Depotrackinfo form);
	
	/**
	 * 货柜更新
	 * 
	 * @param Depotrackinfo
	 * @return
	 */
	public Integer update(Depotrackinfo form);
	
	/**
	 * 货柜删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 货柜信息删除，根据货柜编码
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteByDepotrackcode(String depotrackcode);
	
	/**
	 * 货柜分页查询
	 * 
	 * @param Depotrackinfo
	 * @return
	 */
	public List<Depotrackinfo> findByList(Depotrackinfo form);
	
	/**
	 * 货柜全部查询
	 * 
	 * @param Depotrackinfo
	 * @return
	 */
	public List<Depotrackinfo> queryByList(Depotrackinfo form);
	
	/**
	 * 货柜分页总数
	 * 
	 * @param Depotrackinfo
	 * @return
	 */
	public Integer findByCount(Depotrackinfo form);
	
	/**
	 * 货柜查询根据ID
	 * 
	 * @param Depotrackinfo
	 * @return
	 */
	public Depotrackinfo findById(Integer id);
	
	/**
	 * 货柜格子信息全部查询，通过货柜编号
	 * 
	 * @param Depotrackinfo
	 * @return
	 */
	public List<Depotrackinfo> fingDepotrackinfoListByDepotrackcode(String depotrackcode);
	
}
