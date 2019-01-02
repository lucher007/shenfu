package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Productcolor;

/**
 * 用户数据层接口
 */
public interface IProductcolorDao {
	/**
	 * 产品颜色添加
	 * 
	 * @param ScaleConf
	 * @return
	 */
	public Integer save(Productcolor form);
	
	/**
	 * 产品颜色更新
	 * 
	 * @param Productcolor
	 * @return
	 */
	public Integer update(Productcolor form);
	
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
	 * @param Productcolor
	 * @return
	 */
	public List<Productcolor> findByList(Productcolor form);
	
	/**
	 * 产品颜色全部查询
	 * 
	 * @param Productcolor
	 * @return
	 */
	public List<Productcolor> queryByList(Productcolor form);
	
	/**
	 * 产品颜色分页总数
	 * 
	 * @param Productcolor
	 * @return
	 */
	public Integer findByCount(Productcolor form);
	
	/**
	 * 产品颜色查询根据ID
	 * 
	 * @param Productcolor
	 * @return
	 */
	public Productcolor findById(Integer id);
	
	/**
	 * 产品颜色查询颜色编码
	 * 
	 * @param Productcolor
	 * @return
	 */
	public Productcolor findProductColorByColorcode(String colorcode);
	

}
