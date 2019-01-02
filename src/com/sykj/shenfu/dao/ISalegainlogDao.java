package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Salegainlog;

/**
 * 用户数据层接口
 */
public interface ISalegainlogDao {
	/**
	 * 销售提成提取日记添加
	 * 
	 * @param Salegainlog
	 * @return
	 */
	public Integer save(Salegainlog form);
	
	/**
	 * 销售提成提取日记更新
	 * 
	 * @param Salegainlog
	 * @return
	 */
	public Integer update(Salegainlog form);
	
	/**
	 * 销售提成提取日记删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 销售提成提取日记分页查询
	 * 
	 * @param Salegainlog
	 * @return
	 */
	public List<Salegainlog> findByList(Salegainlog form);
	
	/**
	 * 销售提成提取日记全部查询
	 * 
	 * @param Salegainlog
	 * @return
	 */
	public List<Salegainlog> queryByList(Salegainlog form);
	
	/**
	 * 销售提成提取日记分页总数
	 * 
	 * @param Salegainlog
	 * @return
	 */
	public Integer findByCount(Salegainlog form);
	
	/**
	 * 销售提成提取日记查询根据ID
	 * 
	 * @param Salegainlog
	 * @return
	 */
	public Salegainlog findById(Integer id);
	
	/**
	 * 修改提现记录的状态
	 * 
	 * @param Salegainlog
	 * @return
	 */
	public Integer updateStatus(Salegainlog form);
	
	/**
	 * 删除根据订单编号
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteByOrdercode(String ordercode);
	
}
