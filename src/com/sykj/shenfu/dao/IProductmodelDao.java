package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Productmodel;

/**
 * 用户数据层接口
 */
public interface IProductmodelDao {
	/**
	 * 产品型号添加
	 * 
	 * @param ScaleConf
	 * @return
	 */
	public Integer save(Productmodel form);
	
	/**
	 * 产品型号更新
	 * 
	 * @param Productmodel
	 * @return
	 */
	public Integer update(Productmodel form);
	
	/**
	 * 产品型号删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 产品型号分页查询
	 * 
	 * @param Productmodel
	 * @return
	 */
	public List<Productmodel> findByList(Productmodel form);
	
	/**
	 * 产品型号全部查询
	 * 
	 * @param Productmodel
	 * @return
	 */
	public List<Productmodel> queryByList(Productmodel form);
	
	/**
	 * 产品型号分页总数
	 * 
	 * @param Productmodel
	 * @return
	 */
	public Integer findByCount(Productmodel form);
	
	/**
	 * 产品型号查询根据ID
	 * 
	 * @param Productmodel
	 * @return
	 */
	public Productmodel findById(Integer id);
    
	/**
	 * 产品型号查询根据型号编码
	 * 
	 * @param Productmodel
	 * @return
	 */
	public Productmodel findByModelcode(String modelcode);
	
}
