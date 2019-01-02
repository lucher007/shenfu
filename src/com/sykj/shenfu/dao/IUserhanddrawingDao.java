package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Userhanddrawing;

/**
 * 用户数据层接口
 */
public interface IUserhanddrawingDao {
	/**
	 * 添加
	 * 
	 * @param Userhanddrawing
	 * @return
	 */
	public Integer save(Userhanddrawing form);
	
	/**
	 * 更新
	 * 
	 * @param Userhanddrawing
	 * @return
	 */
	public Integer update(Userhanddrawing form);
	
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
	 * @param Userhanddrawing
	 * @return
	 */
	public List<Userhanddrawing> findByList(Userhanddrawing form);
	
	/**
	 * 全部查询
	 * 
	 * @param Userhanddrawing
	 * @return
	 */
	public List<Userhanddrawing> queryByList(Userhanddrawing form);
	
	/**
	 * 分页总数
	 * 
	 * @param Userhanddrawing
	 * @return
	 */
	public Integer findByCount(Userhanddrawing form);
	
	/**
	 * 查询根据ID
	 * 
	 * @param Userhanddrawing
	 * @return
	 */
	public Userhanddrawing findById(Integer id);
	
	/**
	 * 删除根据任务单ID
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteByTaskid(Integer taskid);

}
