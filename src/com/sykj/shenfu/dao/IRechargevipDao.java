package com.sykj.shenfu.dao;

import java.util.List;

import com.sykj.shenfu.po.Rechargevip;

/**
 * 用户数据层接口
 */
public interface IRechargevipDao {
	/**
	 * 充值VIP添加
	 * 
	 * @param Rechargevip
	 * @return
	 */
	public Integer save(Rechargevip form);
	
	/**
	 * 充值VIP更新
	 * 
	 * @param Rechargevip
	 * @return
	 */
	public Integer update(Rechargevip form);
	
	/**
	 * 充值VIP删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 充值VIP分页查询
	 * 
	 * @param Rechargevip
	 * @return
	 */
	public List<Rechargevip> findByList(Rechargevip form);
	
	/**
	 * 充值VIP全部查询
	 * 
	 * @param Rechargevip
	 * @return
	 */
	public List<Rechargevip> queryByList(Rechargevip form);
	
	/**
	 * 充值VIP分页总数
	 * 
	 * @param Rechargevip
	 * @return
	 */
	public Integer findByCount(Rechargevip form);
	
	/**
	 * 充值VIP查询根据ID
	 * 
	 * @param Rechargevip
	 * @return
	 */
	public Rechargevip findById(Integer id);
	
	/**
	 * 充值VIP查询根据充值VIP编码
	 * 
	 * @param Rechargevip
	 * @return
	 */
	public Rechargevip findByRechargevipcode(String rechargevipcode);

}
