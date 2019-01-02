package com.sykj.shenfu.dao;


import java.util.ArrayList;
import java.util.List;

import com.sykj.shenfu.po.Installcomponent;


/**
 * 用户数据层接口
 */
public interface IInstallcomponentDao {
	/**
	 * 添加
	 * 
	 * @param ScaleConf
	 * @return
	 */
	public Integer save(Installcomponent form);
	
	/**
	 * 批量添加
	 * @param Stb
	 * @return
	 */
	public Integer saveBatch(ArrayList<Installcomponent> installcomponentlist);
	
	/**
	 * 更新
	 * 
	 * @param Installcomponent
	 * @return
	 */
	public Integer update(Installcomponent form);
	
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
	public Integer deleteBatchByMaterialidentfy(String materialidentfy);
	
	/**
	 * 分页查询
	 * 
	 * @param Installcomponent
	 * @return
	 */
	public List<Installcomponent> findByList(Installcomponent form);
	
	/**
	 * 全部查询
	 * 
	 * @param Installcomponent
	 * @return
	 */
	public List<Installcomponent> queryByList(Installcomponent form);
	
	/**
	 * 分页总数
	 * 
	 * @param Installcomponent
	 * @return
	 */
	public Integer findByCount(Installcomponent form);
	
	/**
	 * 查询根据ID
	 * 
	 * @param Installcomponent
	 * @return
	 */
	public Installcomponent findById(Integer id);

	
	/**
	 * 根据条件查询
	 * 
	 * @param Installcomponent
	 * @return
	 */
	public List<Installcomponent> findByProducecode(String producecode);
	
	/**
	 * 根据条件查询
	 * 
	 * @param Installcomponent
	 * @return
	 */
	public List<Installcomponent> findByMaterialidentfy(String materialidentfy);
	
	
	/**
	 * 全部查询
	 * 
	 * @param Installcomponent
	 * @return
	 */
	public List<Installcomponent> queryListByProducecode(Installcomponent form);
	
	/**
	 * 分页总数
	 * 
	 * @param Installcomponent
	 * @return
	 */
	public Integer findCountByProducecode(Installcomponent form);

}
