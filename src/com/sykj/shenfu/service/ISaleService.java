package com.sykj.shenfu.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.sykj.shenfu.po.User;

/**
 * 操作员编码
 */
public interface ISaleService {
	
	/**
	 * 客户商城购买-上门类型-0：需要体验
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveUserAddOrderForVisittype0(User userform, HttpServletRequest request,HttpSession session);
	
	/**
	 * 客户商城购买-上门类型-1：销售员亲自讲解
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveUserAddOrderForVisittype1(User userform, HttpServletRequest request,HttpSession session,MultipartFile[] file);
	
}
