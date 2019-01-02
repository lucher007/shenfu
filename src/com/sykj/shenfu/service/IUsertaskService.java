package com.sykj.shenfu.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Usertask;

/**
 * 操作员编码
 */
public interface IUsertaskService {
	
	/**
	 * 公司派人上门讲解和测量
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveTaskForBatchAdd(Usertask taskform, HttpServletRequest request,Operator operator);
	
	 /**
	 * 已讲解，公司派人上门测量的订单派工
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveTaskForBatchToInstaller(Usertask taskform, HttpServletRequest request,Operator operator);
	
}
