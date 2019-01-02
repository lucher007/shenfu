package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Installinfo;

/**
 * 用户数据层接口
 */
public interface IInstallinfoDao {
	/**
	 * 产品颜色添加
	 * 
	 * @param ScaleConf
	 * @return
	 */
	public Integer save(Installinfo form);
	
	/**
	 * 产品颜色更新
	 * 
	 * @param Installinfo
	 * @return
	 */
	public Integer update(Installinfo form);
	
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
	 * @param Installinfo
	 * @return
	 */
	public List<Installinfo> findByList(Installinfo form);
	
	/**
	 * 产品颜色全部查询
	 * 
	 * @param Installinfo
	 * @return
	 */
	public List<Installinfo> queryByList(Installinfo form);
	
	/**
	 * 产品颜色分页总数
	 * 
	 * @param Installinfo
	 * @return
	 */
	public Integer findByCount(Installinfo form);
	
	/**
	 * 产品颜色查询根据ID
	 * 
	 * @param Installinfo
	 * @return
	 */
	public Installinfo findById(Integer id);
	
	/**
	 * 产品颜色查询根据生产批次号
	 * 
	 * @param Installinfo
	 * @return
	 */
	public Installinfo findByProducecode(String producecode);


}
