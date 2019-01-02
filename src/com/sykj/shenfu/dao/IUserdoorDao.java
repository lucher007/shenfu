package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Userdoor;

/**
 * 用户数据层接口
 */
public interface IUserdoorDao {
	/**
	 * 添加
	 * 
	 * @param Userdoor
	 * @return
	 */
	public Integer save(Userdoor form);
	
	/**
	 * 更新
	 * 
	 * @param Userdoor
	 * @return
	 */
	public Integer update(Userdoor form);
	
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
	 * @param Userdoor
	 * @return
	 */
	public List<Userdoor> findByList(Userdoor form);
	
	/**
	 * 全部查询
	 * 
	 * @param Userdoor
	 * @return
	 */
	public List<Userdoor> queryByList(Userdoor form);
	
	/**
	 * 分页总数
	 * 
	 * @param Userdoor
	 * @return
	 */
	public Integer findByCount(Userdoor form);
	
	/**
	 * 查询根据ID
	 * 
	 * @param Userdoor
	 * @return
	 */
	public Userdoor findById(Integer id);
	
	/**
	 * 删除合同根据客户编号
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteByUsercode(String usercode);
	
	/**
	 * 删除合同根据订单编号
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteByOrdercode(String ordercode);

}
