package com.sykj.shenfu.dao;


import java.util.ArrayList;
import java.util.List;

import com.sykj.shenfu.po.Cellextend;

/**
 * 用户数据层接口
 */
public interface ICellextendDao {
	/**
	 * 小区添加
	 * 
	 * @param Cell
	 * @return
	 */
	public Integer save(Cellextend form);
	
	/**
	 * 小区更新
	 * 
	 * @param Cell
	 * @return
	 */
	public Integer update(Cellextend form);
	
	/**
	 * 小区删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 小区分页查询
	 * 
	 * @param Cell
	 * @return
	 */
	public List<Cellextend> findByList(Cellextend form);
	
	/**
	 * 小区全部查询
	 * 
	 * @param Cell
	 * @return
	 */
	public List<Cellextend> queryByList(Cellextend form);
	
	/**
	 * 小区分页总数
	 * 
	 * @param Cell
	 * @return
	 */
	public Integer findByCount(Cellextend form);
	
	/**
	 * 小区查询根据ID
	 * 
	 * @param Cell
	 * @return
	 */
	public Cellextend findById(Integer id);
	
	/**
	 * 批量导入
	 * @param Cell
	 * @return
	 */
	public Integer saveBatch(ArrayList<Cellextend> cellextendList);
	
	/**
	 * 小区发布查询根据小区发布编号
	 * 
	 * @param Cell
	 * @return
	 */
	public Cellextend findByExtendcode(String cellcode);
}
