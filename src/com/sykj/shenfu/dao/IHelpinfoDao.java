package com.sykj.shenfu.dao;

import java.util.List;

import com.sykj.shenfu.po.Helpinfo;

/**
 * 数据层接口
 */
public interface IHelpinfoDao {
	
	/**
	 *  帮助信息参数添加
	 * 
	 * @param ScaleConf
	 * @return
	 */
	public Integer save(Helpinfo form);
	
	/**
	 *  帮助信息参数更新
	 * 
	 * @param Helpinfo
	 * @return
	 */
	public Integer update(Helpinfo form);
	
	/**
	 *  帮助信息参数删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 *  帮助信息参数分页查询
	 * 
	 * @param Helpinfo
	 * @return
	 */
	public List<Helpinfo> findByList(Helpinfo form);
	
	/**
	 *  帮助信息参数全部查询
	 * 
	 * @param Helpinfo
	 * @return
	 */
	public List<Helpinfo> queryByList(Helpinfo form);
	
	/**
	 *  帮助信息参数分页总数
	 * 
	 * @param Helpinfo
	 * @return
	 */
	public Integer findByCount(Helpinfo form);
	
	/**
	 *  帮助信息参数查询根据ID
	 * 
	 * @param Helpinfo
	 * @return
	 */
	public Helpinfo findById(Integer id);
	
}
