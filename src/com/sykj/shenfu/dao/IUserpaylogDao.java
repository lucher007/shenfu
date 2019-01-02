package com.sykj.shenfu.dao;


import java.util.ArrayList;
import java.util.List;

import com.sykj.shenfu.po.Userpaylog;

/**
 * 用户数据层接口
 */
public interface IUserpaylogDao {
	/**
	 * 添加
	 * 
	 * @param Userpaylog
	 * @return
	 */
	public Integer save(Userpaylog form);
	
	/**
	 * 更新
	 * 
	 * @param Userpaylog
	 * @return
	 */
	public Integer update(Userpaylog form);
	
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
	 * @param Userpaylog
	 * @return
	 */
	public List<Userpaylog> findByList(Userpaylog form);
	
	/**
	 * 全部查询
	 * 
	 * @param Userpaylog
	 * @return
	 */
	public List<Userpaylog> queryByList(Userpaylog form);
	
	/**
	 * 分页总数
	 * 
	 * @param Userpaylog
	 * @return
	 */
	public Integer findByCount(Userpaylog form);
	
	/**
	 * 查询根据ID
	 * 
	 * @param Userpaylog
	 * @return
	 */
	public Userpaylog findById(Integer id);
	
	/**
	 * 批量导入
	 * @param Userpaylog
	 * @return
	 */
	public Integer saveBatch(ArrayList<Userpaylog> userpayloglist);
	
	/**
	 * 客户支付到账统计
	 * 
	 * @param Userpaylog
	 * @return
	 */
	public List<Userpaylog> userpayarrivalStat(Userpaylog form);
    
}
