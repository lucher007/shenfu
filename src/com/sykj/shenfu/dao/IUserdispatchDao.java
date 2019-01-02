package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Userdispatch;

/**
 * 用户数据层接口
 */
public interface IUserdispatchDao {
	/**
	 * 派工单添加
	 * 
	 * @param Userdispatch
	 * @return
	 */
	public Integer save(Userdispatch form);
	
	/**
	 * 派工单更新
	 * 
	 * @param Userdispatch
	 * @return
	 */
	public Integer update(Userdispatch form);
	
	/**
	 * 派工单删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 派工单分页查询
	 * 
	 * @param Userdispatch
	 * @return
	 */
	public List<Userdispatch> findByList(Userdispatch form);
	
	/**
	 * 派工单全部查询
	 * 
	 * @param Userdispatch
	 * @return
	 */
	public List<Userdispatch> queryByList(Userdispatch form);
	
	/**
	 * 派工单分页总数
	 * 
	 * @param Userdispatch
	 * @return
	 */
	public Integer findByCount(Userdispatch form);
	
	/**
	 * 派工单查询根据ID
	 * 
	 * @param Userdispatch
	 * @return
	 */
	public Userdispatch findById(Integer id);
    
	/**
	 * 派发工单
	 * 
	 * @param Userdispatch
	 * @return
	 */
	public Integer saveAssign(Userdispatch form);
	
	/**
	 * 派工接收
	 * 
	 * @param Userdispatch
	 * @return
	 */
	public Integer saveAccept(Userdispatch form);
	
	/**
	 * 保存回单
	 * 
	 * @param Userdispatch
	 * @return
	 */
	public Integer saveReply(Userdispatch form);
	
	/**
	 * 保存审核
	 * 
	 * @param Userdispatch
	 * @return
	 */
	public Integer saveCheck(Userdispatch form);
	
	/**
	 * 查询工单通过工单号
	 * 
	 * @param Userdispatch
	 * @return
	 */
	public Userdispatch findByDispatchcode(String dispatchcode);
	
	/**
	 * 查询工单通过订单号
	 * 
	 * @param Userdispatch
	 * @return
	 */
	public List<Userdispatch> findByOrdercode(String ordercode);
	
	/**
	 * 查询有效工单通过工单号
	 * 
	 * @param Userdispatch
	 * @return
	 */
	public Userdispatch findValidByOrdercode(String ordercode);
	
	/**
	 * 修改工单有效状态
	 * 
	 * @param Userdispatch
	 * @return
	 */
	public Integer updateValidstatus(Userdispatch form);
	
}
