package com.sykj.shenfu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sykj.shenfu.po.Bom;

/**
 * 数据层接口
 */
public interface IBomDao {
	
	/**
	 * Bom添加
	 * 
	 * @param ScaleConf
	 * @return
	 */
	public Integer save(Bom form);
	
	/**
	 * Bom更新
	 * 
	 * @param Bom
	 * @return
	 */
	public Integer update(Bom form);
	
	/**
	 * Bom删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * Bom分页查询
	 * 
	 * @param Bom
	 * @return
	 */
	public List<Bom> findByList(Bom form);
	
	/**
	 * 根Bom分页查询
	 * 
	 * @param Bom
	 * @return
	 */
	public List<Bom> findRootBom(Bom form);

	
	/**
	 * Bom全部查询
	 * 
	 * @param Bom
	 * @return
	 */
	public List<Bom> queryByList(Bom form);
	
	/**
	 * Bom分页总数
	 * 
	 * @param Bom
	 * @return
	 */
	public Integer findByCount(Bom form);
	
	/**
	 * Bom查询根据ID
	 * 
	 * @param Bom
	 * @return
	 */
	public Bom findById(Integer id);
	
	/**
	 * Bom查询根据Bom编号
	 * 
	 * @param Bom
	 * @return
	 */
	public Bom findByProductcodeAndMaterialcode(@Param("productcode") String productcode, @Param("materialcode") String materialcode);
	
	/**
	 * 查询父亲根据Bom
	 * 
	 * @param Server
	 * @return
	 */
	public List<Bom> queryListByPid(Bom form);
	
	/**
	 * Bom批量删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteBatchByBomcode(Bom form);
	
}
