package com.sykj.shenfu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sykj.shenfu.po.Materialbom;

/**
 * 数据层接口
 */
public interface IMaterialbomDao {
	
	/**
	 * Materialbom添加
	 * 
	 * @param ScaleConf
	 * @return
	 */
	public Integer save(Materialbom form);
	
	/**
	 * Materialbom更新
	 * 
	 * @param Materialbom
	 * @return
	 */
	public Integer update(Materialbom form);
	
	/**
	 * Materialbom删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * Materialbom分页查询
	 * 
	 * @param Materialbom
	 * @return
	 */
	public List<Materialbom> findByList(Materialbom form);
	
	/**
	 * 根Materialbom分页查询
	 * 
	 * @param Materialbom
	 * @return
	 */
	public List<Materialbom> findRootMaterialbom(Materialbom form);

	
	/**
	 * Materialbom全部查询
	 * 
	 * @param Materialbom
	 * @return
	 */
	public List<Materialbom> queryByList(Materialbom form);
	
	/**
	 * Materialbom分页总数
	 * 
	 * @param Materialbom
	 * @return
	 */
	public Integer findByCount(Materialbom form);
	
	/**
	 * Materialbom查询根据ID
	 * 
	 * @param Materialbom
	 * @return
	 */
	public Materialbom findById(Integer id);
	
	/**
	 * Materialbom查询根据Materialcode编号和元器件编号
	 * 
	 * @param Materialbom
	 * @return
	 */
	public Materialbom findByMaterialcodeAndComponentcode(@Param("materialcode") String materialcode, @Param("componentcode") String componentcode);
	
	/**
	 * 查询父亲根据Materialbom
	 * 
	 * @param Server
	 * @return
	 */
	public List<Materialbom> queryListByPid(Materialbom form);
	
	/**
	 * Materialbom批量删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteBatchByBomcode(Materialbom form);
	
}
