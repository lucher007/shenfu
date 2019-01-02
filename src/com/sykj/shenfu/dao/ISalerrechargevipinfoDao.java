package com.sykj.shenfu.dao;

import java.util.List;

import com.sykj.shenfu.po.Salerrechargevipinfo;

/**
 * 用户数据层接口
 */
public interface ISalerrechargevipinfoDao {
	/**
	 * 添加
	 * 
	 * @param Salerrechargevipinfo
	 * @return
	 */
	public Integer save(Salerrechargevipinfo form);
	
	/**
	 * 更新
	 * 
	 * @param Salerrechargevipinfo
	 * @return
	 */
	public Integer update(Salerrechargevipinfo form);
	
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
	 * @param Salerrechargevipinfo
	 * @return
	 */
	public List<Salerrechargevipinfo> findByList(Salerrechargevipinfo form);
	
	/**
	 * 全部查询
	 * 
	 * @param Salerrechargevipinfo
	 * @return
	 */
	public List<Salerrechargevipinfo> queryByList(Salerrechargevipinfo form);
	
	/**
	 * 分页总数
	 * 
	 * @param Salerrechargevipinfo
	 * @return
	 */
	public Integer findByCount(Salerrechargevipinfo form);
	
	/**
	 * 查询根据ID
	 * 
	 * @param Salerrechargevipinfo
	 * @return
	 */
	public Salerrechargevipinfo findById(Integer id);
	
	/**
	 * 查询根据编码
	 * 
	 * @param Salerrechargevipinfo
	 * @return
	 */
	public Salerrechargevipinfo findByEmployeecode(String employeecode);
	
	/**
	 * 查询根据编码
	 * 
	 * @param Salerrechargevipinfo
	 * @return
	 */
	public Salerrechargevipinfo findByPhone(String phone);

}
