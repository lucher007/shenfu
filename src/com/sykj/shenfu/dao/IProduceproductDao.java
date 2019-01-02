package com.sykj.shenfu.dao;


import java.util.ArrayList;
import java.util.List;

import com.sykj.shenfu.po.Produceproduct;

/**
 * 用户数据层接口
 */
public interface IProduceproductDao {
	/**
	 * 添加
	 * 
	 * @param ScaleConf
	 * @return
	 */
	public Integer save(Produceproduct form);
	
	/**
	 * 批量添加
	 * @param Stb
	 * @return
	 */
	public Integer saveBatch(ArrayList<Produceproduct> produceproductlist);
	
	/**
	 * 更新
	 * 
	 * @param Produceproduct
	 * @return
	 */
	public Integer update(Produceproduct form);
	
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
	 * @param Produceproduct
	 * @return
	 */
	public List<Produceproduct> findByList(Produceproduct form);
	
	/**
	 * 全部查询
	 * 
	 * @param Produceproduct
	 * @return
	 */
	public List<Produceproduct> queryByList(Produceproduct form);
	
	/**
	 * 分页总数
	 * 
	 * @param Produceproduct
	 * @return
	 */
	public Integer findByCount(Produceproduct form);
	
	/**
	 * 查询根据ID
	 * 
	 * @param Produceproduct
	 * @return
	 */
	public Produceproduct findById(Integer id);

	
	/**
	 * 根据条件查询
	 * 
	 * @param Produceproduct
	 * @return
	 */
	public List<Produceproduct> findByProducecode(String producecode);
	
	/**
	 * 查询根据产品唯一识别号
	 * 
	 * @param Produceproduct
	 * @return
	 */
	public Produceproduct findByProductidentfy(String productidentfy);

}
