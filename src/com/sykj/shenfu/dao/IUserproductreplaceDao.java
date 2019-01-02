package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Userproductreplace;

/**
 * 用户数据层接口
 */
public interface IUserproductreplaceDao {
	/**
	 * 订单产品更换添加
	 * 
	 * @param Userproduct
	 * @return
	 */
	public Integer save(Userproductreplace form);
	
	/**
	 * 订单产品更换更新
	 * 
	 * @param Userproductreplace
	 * @return
	 */
	public Integer update(Userproductreplace form);
	
	/**
	 * 订单产品更换删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 订单产品更换分页查询
	 * 
	 * @param Userproductreplace
	 * @return
	 */
	public List<Userproductreplace> findByList(Userproductreplace form);
	
	/**
	 * 订单产品更换全部查询
	 * 
	 * @param Userproductreplace
	 * @return
	 */
	public List<Userproductreplace> queryByList(Userproductreplace form);
	
	/**
	 * 订单产品更换分页总数
	 * 
	 * @param Userproductreplace
	 * @return
	 */
	public Integer findByCount(Userproductreplace form);
	
	/**
	 * 订单产品更换查询根据ID
	 * 
	 * @param Userproductreplace
	 * @return
	 */
	public Userproductreplace findById(Integer id);

}
