package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Salegaininfo;
import com.sykj.shenfu.po.Salegaininfo;

/**
 * 用户数据层接口
 */
public interface ISalegaininfoDao {
	/**
	 * 销售提成添加
	 * 
	 * @param Salegaininfo
	 * @return
	 */
	public Integer save(Salegaininfo form);
	
	/**
	 * 销售提成更新
	 * 
	 * @param Salegaininfo
	 * @return
	 */
	public Integer update(Salegaininfo form);
	
	/**
	 * 销售提成删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 销售提成分页查询
	 * 
	 * @param Salegaininfo
	 * @return
	 */
	public List<Salegaininfo> findByList(Salegaininfo form);
	
	/**
	 * 销售提成全部查询
	 * 
	 * @param Salegaininfo
	 * @return
	 */
	public List<Salegaininfo> queryByList(Salegaininfo form);
	
	/**
	 * 销售提成分页总数
	 * 
	 * @param Salegaininfo
	 * @return
	 */
	public Integer findByCount(Salegaininfo form);
	
	/**
	 * 销售提成查询根据ID
	 * 
	 * @param Salegaininfo
	 * @return
	 */
	public Salegaininfo findById(Integer id);
	
	
	/**
	 * 更改销售提成状态
	 * 
	 * @param Salegaininfo
	 * @return
	 */
	public Integer updateStatus(Salegaininfo form);
	
	/**
	 * 查询提成总金额
	 * 
	 * @param Intger
	 * @return
	 */
	public Long findGaintotalmoney(Salegaininfo form);
	
	/**
	 * 查询行动力总分
	 * 
	 * @param Intger
	 * @return
	 */
	public Long findEnergytotalscore(Salegaininfo form);
	
	/**
	 *员工提成汇总查询分页
	 * 
	 * @param Salegaininfo
	 * @return
	 */
	public List<Salegaininfo> findSalegaininfoStat(Salegaininfo form);
	
	/**
	 *员工提成汇总查询分页总数
	 * 
	 * @param Salegaininfo
	 * @return
	 */
	public Integer findSalegaininfoStatCount(Salegaininfo form);
	
	/**
	 *员工提成汇总查询不分页
	 * 
	 * @param Salegaininfo
	 * @return
	 */
	public List<Salegaininfo> querySalegaininfoStat(Salegaininfo form);
	
	/**
	 * 删除根据订单编号
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteByOrdercode(String ordercode);
	
}
