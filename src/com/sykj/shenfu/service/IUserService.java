package com.sykj.shenfu.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.User;

/**
 * 操作员编码
 */
public interface IUserService {
	
	public Map<String, Object> saveUserRegister(User userform, HttpServletRequest request,Operator operator);
	/**
	 * 客户注册-上门类型-0：公司派人讲解测量
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveUserRegisterForVisittype0(User userform, HttpServletRequest request,Operator operator);
	
	/**
	 * 客户注册-上门类型-1：销售员亲自讲解测量
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveUserRegisterForVisittype1(User userform, HttpServletRequest request,Operator operator);
	
	/**
	 * 客户注册-上门类型-2：销售员亲自讲解测量
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveUserRegisterForVisittype2(User userform, HttpServletRequest request,Operator operator);
	
}
