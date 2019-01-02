package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Userdispatchinstaller;

/**
 * 用户数据层接口
 */
public interface IUserdispatchinstallerDao {
	/**
	 * 任务单添加
	 * 
	 * @param Userdispatchinstaller
	 * @return
	 */
	public Integer save(Userdispatchinstaller form);
	
	/**
	 * 任务单更新
	 * 
	 * @param Userdispatchinstaller
	 * @return
	 */
	public Integer update(Userdispatchinstaller form);
	
	/**
	 * 任务单删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 任务单分页查询
	 * 
	 * @param Userdispatchinstaller
	 * @return
	 */
	public List<Userdispatchinstaller> findByList(Userdispatchinstaller form);
	
	/**
	 * 任务单全部查询
	 * 
	 * @param Userdispatchinstaller
	 * @return
	 */
	public List<Userdispatchinstaller> queryByList(Userdispatchinstaller form);
	
	/**
	 * 任务单分页总数
	 * 
	 * @param Userdispatchinstaller
	 * @return
	 */
	public Integer findByCount(Userdispatchinstaller form);
	
	/**
	 * 任务单查询根据ID
	 * 
	 * @param Userdispatchinstaller
	 * @return
	 */
	public Userdispatchinstaller findById(Integer id);

}
