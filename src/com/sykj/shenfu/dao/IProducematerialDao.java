package com.sykj.shenfu.dao;


import java.util.ArrayList;
import java.util.List;

import com.sykj.shenfu.po.Producematerial;

/**
 * 用户数据层接口
 */
public interface IProducematerialDao {
	/**
	 * 添加
	 * 
	 * @param ScaleConf
	 * @return
	 */
	public Integer save(Producematerial form);
	
	/**
	 * 批量添加
	 * @param Stb
	 * @return
	 */
	public Integer saveBatch(ArrayList<Producematerial> producemateriallist);
	
	/**
	 * 更新
	 * 
	 * @param Producematerial
	 * @return
	 */
	public Integer update(Producematerial form);
	
	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 批量删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteBatchByProducecode(String producecode);
	
	/**
	 * 批量删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteBatchByProductidentfy(String productidentfy);
	
	/**
	 * 分页查询
	 * 
	 * @param Producematerial
	 * @return
	 */
	public List<Producematerial> findByList(Producematerial form);
	
	/**
	 * 全部查询
	 * 
	 * @param Producematerial
	 * @return
	 */
	public List<Producematerial> queryByList(Producematerial form);
	
	/**
	 * 分页总数
	 * 
	 * @param Producematerial
	 * @return
	 */
	public Integer findByCount(Producematerial form);
	
	/**
	 * 查询根据ID
	 * 
	 * @param Producematerial
	 * @return
	 */
	public Producematerial findById(Integer id);

	
	/**
	 * 根据条件查询
	 * 
	 * @param Producematerial
	 * @return
	 */
	public List<Producematerial> findByProducecode(String producecode);
	
	/**
	 * 根据条件查询
	 * 
	 * @param Producematerial
	 * @return
	 */
	public List<Producematerial> findByProductidentfy(String productidentfy);
	
	
	/**
	 * 全部查询
	 * 
	 * @param Producematerial
	 * @return
	 */
	public List<Producematerial> queryListByProducecode(Producematerial form);
	
	/**
	 * 分页总数
	 * 
	 * @param Producematerial
	 * @return
	 */
	public Integer findCountByProducecode(Producematerial form);

}
