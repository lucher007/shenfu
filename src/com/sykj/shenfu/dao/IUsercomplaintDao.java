package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Usercomplaint;

/**
 * 用户数据层接口
 */
public interface IUsercomplaintDao {
	/**
	 * 投诉单添加
	 * 
	 * @param Usercomplaint
	 * @return
	 */
	public Integer save(Usercomplaint form);
	
	/**
	 * 投诉单更新
	 * 
	 * @param Usercomplaint
	 * @return
	 */
	public Integer update(Usercomplaint form);
	
	/**
	 * 投诉单删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 投诉单分页查询
	 * 
	 * @param Usercomplaint
	 * @return
	 */
	public List<Usercomplaint> findByList(Usercomplaint form);
	
	/**
	 * 投诉单全部查询
	 * 
	 * @param Usercomplaint
	 * @return
	 */
	public List<Usercomplaint> queryByList(Usercomplaint form);
	
	/**
	 * 投诉单分页总数
	 * 
	 * @param Usercomplaint
	 * @return
	 */
	public Integer findByCount(Usercomplaint form);
	
	/**
	 * 投诉单查询根据ID
	 * 
	 * @param Usercomplaint
	 * @return
	 */
	public Usercomplaint findById(Integer id);
	
	/**
	 * 查询根据投诉单号
	 * 
	 * @param Dispatch
	 * @return
	 */
	public String findByComplaintcode(String complaintcode);

}
