package com.sykj.shenfu.dao;


import java.util.ArrayList;
import java.util.List;

import com.sykj.shenfu.po.Coderule;

/**
 * 用户数据层接口
 */
public interface ICoderuleDao {
	/**
	 * 添加
	 * 
	 * @param Coderule
	 * @return
	 */
	public Integer save(Coderule form);
	
	/**
	 * 更新
	 * 
	 * @param Coderule
	 * @return
	 */
	public Integer update(Coderule form);
	
	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 分页查询
	 * 
	 * @param Coderule
	 * @return
	 */
	public List<Coderule> findByList(Coderule form);
	
	/**
	 * 全部查询
	 * 
	 * @param Coderule
	 * @return
	 */
	public List<Coderule> queryByList(Coderule form);
	
	/**
	 * 分页总数
	 * 
	 * @param Coderule
	 * @return
	 */
	public Integer findByCount(Coderule form);
	
	/**
	 * 查询根据ID
	 * 
	 * @param Coderule
	 * @return
	 */
	public Coderule findById(Integer id);
    
	/**
	 * 查询根据规则类型
	 * 
	 * @param codetype
	 * @return
	 */
	public Coderule findByCodetypeStr(String codetype);
}
