package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Produceinfo;

/**
 * 用户数据层接口
 */
public interface IProduceinfoDao {
	/**
	 * 产品颜色添加
	 * 
	 * @param ScaleConf
	 * @return
	 */
	public Integer save(Produceinfo form);
	
	/**
	 * 产品颜色更新
	 * 
	 * @param Produceinfo
	 * @return
	 */
	public Integer update(Produceinfo form);
	
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
	 * @param Produceinfo
	 * @return
	 */
	public List<Produceinfo> findByList(Produceinfo form);
	
	/**
	 * 产品颜色全部查询
	 * 
	 * @param Produceinfo
	 * @return
	 */
	public List<Produceinfo> queryByList(Produceinfo form);
	
	/**
	 * 产品颜色分页总数
	 * 
	 * @param Produceinfo
	 * @return
	 */
	public Integer findByCount(Produceinfo form);
	
	/**
	 * 产品颜色查询根据ID
	 * 
	 * @param Produceinfo
	 * @return
	 */
	public Produceinfo findById(Integer id);
	
	/**
	 * 产品颜色查询根据生产批次号
	 * 
	 * @param Produceinfo
	 * @return
	 */
	public Produceinfo findByProducecode(String producecode);


}
