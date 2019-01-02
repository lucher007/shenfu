package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Depotrack;

/**
 * 用户数据层接口
 */
public interface IDepotrackDao {
	/**
	 * 货柜添加
	 * 
	 * @param ScaleConf
	 * @return
	 */
	public Integer save(Depotrack form);
	
	/**
	 * 货柜更新
	 * 
	 * @param Depotrack
	 * @return
	 */
	public Integer update(Depotrack form);
	
	/**
	 * 货柜删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 货柜分页查询
	 * 
	 * @param Depotrack
	 * @return
	 */
	public List<Depotrack> findByList(Depotrack form);
	
	/**
	 * 货柜全部查询
	 * 
	 * @param Depotrack
	 * @return
	 */
	public List<Depotrack> queryByList(Depotrack form);
	
	/**
	 * 货柜分页总数
	 * 
	 * @param Depotrack
	 * @return
	 */
	public Integer findByCount(Depotrack form);
	
	/**
	 * 货柜查询根据ID
	 * 
	 * @param Depotrack
	 * @return
	 */
	public Depotrack findById(Integer id);
	
	/**
	 * 货柜查询根据货柜名称编号
	 * 
	 * @param Depotrack
	 * @return
	 */
	public Depotrack findByDepotrackcode(String depotrackcode);

}
