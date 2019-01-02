package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Saleenergyinfo;

/**
 * 用户数据层接口
 */
public interface ISaleenergyinfoDao {
	/**
	 * 销售行动力添加
	 * 
	 * @param Saleenergyinfo
	 * @return
	 */
	public Integer save(Saleenergyinfo form);
	
	/**
	 * 销售行动力更新
	 * 
	 * @param Saleenergyinfo
	 * @return
	 */
	public Integer update(Saleenergyinfo form);
	
	/**
	 * 销售行动力删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 销售行动力分页查询
	 * 
	 * @param Saleenergyinfo
	 * @return
	 */
	public List<Saleenergyinfo> findByList(Saleenergyinfo form);
	
	/**
	 * 销售行动力全部查询
	 * 
	 * @param Saleenergyinfo
	 * @return
	 */
	public List<Saleenergyinfo> queryByList(Saleenergyinfo form);
	
	/**
	 * 销售行动力分页总数
	 * 
	 * @param Saleenergyinfo
	 * @return
	 */
	public Integer findByCount(Saleenergyinfo form);
	
	/**
	 * 销售行动力查询根据ID
	 * 
	 * @param Saleenergyinfo
	 * @return
	 */
	public Saleenergyinfo findById(Integer id);
	
	
	/**
	 * 更改销售行动力状态
	 * 
	 * @param Saleenergyinfo
	 * @return
	 */
	public Integer updateStatus(Saleenergyinfo form);
	
	/**
	 * 查询行动力总金额
	 * 
	 * @param Intger
	 * @return
	 */
	public Long findGaintotalmoney(Saleenergyinfo form);
	
	/**
	 * 查询行动力总分
	 * 
	 * @param Intger
	 * @return
	 */
	public Long findEnergytotalscore(Saleenergyinfo form);
	
	/**
	 *员工行动力汇总查询分页
	 * 
	 * @param Saleenergyinfo
	 * @return
	 */
	public List<Saleenergyinfo> findSaleenergyinfoStat(Saleenergyinfo form);
	
	/**
	 *员工行动力汇总查询分页总数
	 * 
	 * @param Saleenergyinfo
	 * @return
	 */
	public Integer findSaleenergyinfoStatCount(Saleenergyinfo form);
	
	/**
	 *员工行动力汇总查询不分页
	 * 
	 * @param Saleenergyinfo
	 * @return
	 */
	public List<Saleenergyinfo> querySaleenergyinfoStat(Saleenergyinfo form);
	
}
