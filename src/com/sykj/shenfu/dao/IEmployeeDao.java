package com.sykj.shenfu.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sykj.shenfu.po.Employee;

/**
 * 用户数据层接口
 */
public interface IEmployeeDao {
	/**
	 * 员工添加
	 * 
	 * @param ScaleConf
	 * @return
	 */
	public Integer save(Employee form);
	
	/**
	 * 员工更新
	 * 
	 * @param Employee
	 * @return
	 */
	public Integer update(Employee form);
	
	/**
	 * 员工删除
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 员工分页查询
	 * 
	 * @param Employee
	 * @return
	 */
	public List<Employee> findByList(Employee form);
	
	/**
	 * 员工全部查询
	 * 
	 * @param Employee
	 * @return
	 */
	public List<Employee> queryByList(Employee form);
	
	/**
	 * 员工分页总数
	 * 
	 * @param Employee
	 * @return
	 */
	public Integer findByCount(Employee form);
	
	/**
	 * 员工查询根据ID
	 * 
	 * @param Employee
	 * @return
	 */
	public Employee findById(Integer id);
	
	/**
	 * 员工查询根据电话号码
	 * 
	 * @param Employee
	 * @return
	 */
	public Employee findByPhone(Employee form);
	
	/**
	 * 员工查询根据电话号码字符串
	 * 
	 * @param Employee
	 * @return
	 */
	public Employee findByPhoneStr(String phone);
	
	/**
	 * 更新销售员的销售积分
	 * 
	 * @param Employee
	 * @return
	 */
	public Integer updateSalescore(Employee form);
	
	/**
	 * 员工查询根据工号
	 * 
	 * @param Employee
	 * @return
	 */
	public Employee findByEmployeecodeStr(String employeecode);
	
	
	/**
	 * 更新销售员的销售积分
	 * 
	 * @param Employee
	 * @return
	 */
	public Integer updateSalescoreByEmployeecode(@Param("employeecode")String employeecode, @Param("salescore")Integer salescore);
	
	/**
	 * 员工查询根据工号
	 * 
	 * @param Employee
	 * @return
	 */
	public Employee findByExtendcode(String extendcode);
	
}
