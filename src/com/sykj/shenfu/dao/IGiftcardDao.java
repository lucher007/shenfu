package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Giftcard;

/**
 * 用户数据层接口
 */
public interface IGiftcardDao {
	/**
	 * 优惠卡添加
	 * 
	 * @param ScaleConf
	 * @return
	 */
	public Integer save(Giftcard form);
	
	/**
	 * 优惠卡更新
	 * 
	 * @param Giftcard
	 * @return
	 */
	public Integer update(Giftcard form);
	
	/**
	 * 优惠卡删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 优惠卡分页查询
	 * 
	 * @param Giftcard
	 * @return
	 */
	public List<Giftcard> findByList(Giftcard form);
	
	/**
	 * 优惠卡全部查询
	 * 
	 * @param Giftcard
	 * @return
	 */
	public List<Giftcard> queryByList(Giftcard form);
	
	/**
	 * 优惠卡分页总数
	 * 
	 * @param Giftcard
	 * @return
	 */
	public Integer findByCount(Giftcard form);
	
	/**
	 * 优惠卡查询根据ID
	 * 
	 * @param Giftcard
	 * @return
	 */
	public Giftcard findById(Integer id);
	
	/**
	 * 优惠卡查询根据优惠卡名称编号
	 * 
	 * @param Giftcard
	 * @return
	 */
	public Giftcard findByGiftcardno(String giftcardno);
	

}
