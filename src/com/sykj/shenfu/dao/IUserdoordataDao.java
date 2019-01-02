package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Userdoordata;

/**
 * 用户数据层接口
 */
public interface IUserdoordataDao {
	/**
	 * 添加
	 * 
	 * @param Userdoordata
	 * @return
	 */
	public Integer save(Userdoordata form);
	
	/**
	 * 更新
	 * 
	 * @param Userdoordata
	 * @return
	 */
	public Integer update(Userdoordata form);
	
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
	 * @param Userdoordata
	 * @return
	 */
	public List<Userdoordata> findByList(Userdoordata form);
	
	/**
	 * 全部查询
	 * 
	 * @param Userdoordata
	 * @return
	 */
	public List<Userdoordata> queryByList(Userdoordata form);
	
	/**
	 * 分页总数
	 * 
	 * @param Userdoordata
	 * @return
	 */
	public Integer findByCount(Userdoordata form);
	
	/**
	 * 查询根据ID
	 * 
	 * @param Userdoordata
	 * @return
	 */
	public Userdoordata findById(Integer id);
	
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
	
	/**
	 * 根据订单编号查询门锁数据
	 * 
	 * @param id
	 * @return
	 */
	public Userdoordata findByOrdercode(String ordercode);

}
