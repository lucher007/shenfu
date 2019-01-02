package com.sykj.shenfu.dao;

import java.util.List;

import com.sykj.shenfu.po.Cellpaylog;
import com.sykj.shenfu.po.Userpaylog;

/**
 * 用户数据层接口
 */
public interface ICellpaylogDao {
	/**
	 * 扫楼支付添加
	 * 
	 * @param Cell
	 * @return
	 */
	public Integer save(Cellpaylog form);
	
	/**
	 * 扫楼支付更新
	 * 
	 * @param Cell
	 * @return
	 */
	public Integer update(Cellpaylog form);
	
	/**
	 * 扫楼支付删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 扫楼支付分页查询
	 * 
	 * @param Cell
	 * @return
	 */
	public List<Cellpaylog> findByList(Cellpaylog form);
	
	/**
	 * 扫楼支付全部查询
	 * 
	 * @param Cell
	 * @return
	 */
	public List<Cellpaylog> queryByList(Cellpaylog form);
	
	/**
	 * 扫楼支付分页总数
	 * 
	 * @param Cell
	 * @return
	 */
	public Integer findByCount(Cellpaylog form);
	
	/**
	 * 扫楼支付查询根据ID
	 * 
	 * @param Cell
	 * @return
	 */
	public Cellpaylog findById(Integer id);
	
	/**
	 * 扫楼支付到账统计
	 * 
	 * @param Userpaylog
	 * @return
	 */
	public List<Cellpaylog> cellpayarrivalStat(Cellpaylog form);
    
}
