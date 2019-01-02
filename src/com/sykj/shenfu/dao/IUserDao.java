package com.sykj.shenfu.dao;


import java.util.List;

import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.User;

/**
 * 用户数据层接口
 */
public interface IUserDao {
	/**
	 * 客户添加
	 * 
	 * @param User
	 * @return
	 */
	public Integer save(User form);
	
	/**
	 * 客户更新
	 * 
	 * @param User
	 * @return
	 */
	public Integer update(User form);
	
	/**
	 * 客户删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 客户分页查询
	 * 
	 * @param User
	 * @return
	 */
	public List<User> findByList(User form);
	
	/**
	 * 客户全部查询
	 * 
	 * @param User
	 * @return
	 */
	public List<User> queryByList(User form);
	
	/**
	 * 客户分页总数
	 * 
	 * @param User
	 * @return
	 */
	public Integer findByCount(User form);
	
	/**
	 * 客户查询根据ID
	 * 
	 * @param User
	 * @return
	 */
	public User findById(Integer id);
	
	/**
	 * 客户查询根据电话号码
	 * 
	 * @param Employee
	 * @return
	 */
	public User findByPhone(User form);
	
	/**
	 * 客户查询根据电话号码字符串
	 * 
	 * @param Employee
	 * @return
	 */
	public User findByPhoneStr(String phone);
	
	/**
	 * 客户查询根据客户编号字符串
	 * 
	 * @param Employee
	 * @return
	 */
	public User findByUsercodeStr(String usercode);
}
