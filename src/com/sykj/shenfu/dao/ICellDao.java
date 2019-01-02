package com.sykj.shenfu.dao;


import java.util.ArrayList;
import java.util.List;

import com.sykj.shenfu.po.Cell;

/**
 * 用户数据层接口
 */
public interface ICellDao {
	/**
	 * 小区添加
	 * 
	 * @param Cell
	 * @return
	 */
	public Integer save(Cell form);
	
	/**
	 * 小区更新
	 * 
	 * @param Cell
	 * @return
	 */
	public Integer update(Cell form);
	
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
	public List<Cell> findByList(Cell form);
	
	/**
	 * 小区全部查询
	 * 
	 * @param Cell
	 * @return
	 */
	public List<Cell> queryByList(Cell form);
	
	/**
	 * 小区分页总数
	 * 
	 * @param Cell
	 * @return
	 */
	public Integer findByCount(Cell form);
	
	/**
	 * 小区查询根据ID
	 * 
	 * @param Cell
	 * @return
	 */
	public Cell findById(Integer id);
	
	/**
	 * 批量导入
	 * @param Cell
	 * @return
	 */
	public Integer saveBatch(ArrayList<Cell> cells);
    
	/**
	 * 小区查询根据小区编号
	 * 
	 * @param Cell
	 * @return
	 */
	public Cell findByCellcode(String cellcode);
}
