package com.sykj.shenfu.dao;


import java.util.ArrayList;
import java.util.List;

import com.sykj.shenfu.po.Installmaterial;

/**
 * 用户数据层接口
 */
public interface IInstallmaterialDao {
	/**
	 * 添加
	 * 
	 * @param ScaleConf
	 * @return
	 */
	public Integer save(Installmaterial form);
	
	/**
	 * 批量添加
	 * @param Stb
	 * @return
	 */
	public Integer saveBatch(ArrayList<Installmaterial> produceproductlist);
	
	/**
	 * 更新
	 * 
	 * @param Installmaterial
	 * @return
	 */
	public Integer update(Installmaterial form);
	
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
	 * 分页查询
	 * 
	 * @param Installmaterial
	 * @return
	 */
	public List<Installmaterial> findByList(Installmaterial form);
	
	/**
	 * 全部查询
	 * 
	 * @param Installmaterial
	 * @return
	 */
	public List<Installmaterial> queryByList(Installmaterial form);
	
	/**
	 * 分页总数
	 * 
	 * @param Installmaterial
	 * @return
	 */
	public Integer findByCount(Installmaterial form);
	
	/**
	 * 查询根据ID
	 * 
	 * @param Installmaterial
	 * @return
	 */
	public Installmaterial findById(Integer id);

	
	/**
	 * 根据条件查询
	 * 
	 * @param Installmaterial
	 * @return
	 */
	public List<Installmaterial> findByProducecode(String producecode);
	
	/**
	 * 查询根据产品唯一识别号
	 * 
	 * @param Installmaterial
	 * @return
	 */
	public Installmaterial findByMaterialidentfy(String materialidentfy);

}
