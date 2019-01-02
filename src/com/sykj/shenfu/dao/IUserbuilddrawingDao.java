package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Userbuilddrawing;

/**
 * 用户数据层接口
 */
public interface IUserbuilddrawingDao {
	/**
	 * 添加
	 * 
	 * @param Userbuilddrawing
	 * @return
	 */
	public Integer save(Userbuilddrawing form);
	
	/**
	 * 更新
	 * 
	 * @param Userbuilddrawing
	 * @return
	 */
	public Integer update(Userbuilddrawing form);
	
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
	 * @param Userbuilddrawing
	 * @return
	 */
	public List<Userbuilddrawing> findByList(Userbuilddrawing form);
	
	/**
	 * 全部查询
	 * 
	 * @param Userbuilddrawing
	 * @return
	 */
	public List<Userbuilddrawing> queryByList(Userbuilddrawing form);
	
	/**
	 * 分页总数
	 * 
	 * @param Userbuilddrawing
	 * @return
	 */
	public Integer findByCount(Userbuilddrawing form);
	
	/**
	 * 查询根据ID
	 * 
	 * @param Userbuilddrawing
	 * @return
	 */
	public Userbuilddrawing findById(Integer id);
	
	/**
	 * 删除根据任务单编号
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteByTaskcodeStr(String taskcode);

}
