package com.sykj.shenfu.dao;

import java.util.ArrayList;
import java.util.List;

import com.sykj.shenfu.po.Productmodelref;


/**
 * 数据层接口
 */
public interface IProductmodelrefDao {
	
	/**
	 * 产品型号与产品关系添加
	 * 
	 * @param ScaleConf
	 * @return
	 */
	public Integer save(Productmodelref form);
	
	/**
	 * 产品型号与产品关系更新
	 * 
	 * @param Productmodelref
	 * @return
	 */
	public Integer update(Productmodelref form);
	
	/**
	 * 产品型号与产品关系删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 产品型号与产品关系分页查询
	 * 
	 * @param Productmodelref
	 * @return
	 */
	public List<Productmodelref> findByList(Productmodelref form);
	
	/**
	 * 产品型号与产品关系全部查询
	 * 
	 * @param Productmodelref
	 * @return
	 */
	public List<Productmodelref> queryByList(Productmodelref form);
	
	/**
	 * 产品型号与产品关系分页总数
	 * 
	 * @param Productmodelref
	 * @return
	 */
	public Integer findByCount(Productmodelref form);
	
	/**
	 * 产品型号与产品关系查询根据ID
	 * 
	 * @param Productmodelref
	 * @return
	 */
	public Productmodelref findById(Integer id);

	/**
	 * 产品型号与产品关系查询根据型号编码
	 * 
	 * @param Productmodelref
	 * @return
	 */
	public List<Productmodelref> findByModelcode(String modelcode);
	
	/**
	 * 批量导入
	 * @param Productmodelref
	 * @return
	 */
	public Integer saveBatch(ArrayList<Productmodelref> productmodelreflist);
	
	/**
	 * 删除根据型号编码
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteByModelcode(String modelcode);
	
}
