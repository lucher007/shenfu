package com.sykj.shenfu.dao;


import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Userorder;
import com.sykj.shenfu.po.Usertask;

/**
 * 用户数据层接口
 */
public interface IHttpForMpsDao {
	
	
	/**
	 * 
	 * 查询讲解上门单-查询类型-0：只讲解人查询
	 * 
	 * @param Usertask
	 * @return
	 */
	public List<Userorder> findTalkerListInfo(Userorder form);
	
	/**
	 * 查询讲解上门单-查询类型-1：只上门人员查询
	 * 
	 * @param String
	 * @return
	 */
	public List<Usertask> findVisitorListInfo(Usertask form);
	
	/**
	 * 查询讲解上门单-查询类型-2：讲解和上门为同一个人查询
	 * 
	 * @param String
	 * @return
	 */
	public List<Usertask> findTalkerAndVisitorListInfo(Usertask form);
	
	/**
	 * 查询下级销售员的渠道给自己的提成总额
	 * 
	 * @param String
	 * @return
	 */
	public List<Employee> findChildrenSalerInfoListBySalercode(Employee employee);
	
	/**
	 * 查询下级销售员的团队给自己的提成总额
	 * 
	 * @param String
	 * @return
	 */
	public Employee findTotalChildrenSalerInfoListBySalercode(Employee employee);
	
	/**
	 * 查询提成统计排名
	 * 
	 * @param String
	 * @return
	 */
	public List<Employee> findSalegaininfoStat(Employee employee);
	
	/**
	 * 通过员工编号查询提成排名
	 * 
	 * @param String
	 * @return
	 */
	public Employee findSalegaininfoStatByEmployeecode(Employee employee);
}
