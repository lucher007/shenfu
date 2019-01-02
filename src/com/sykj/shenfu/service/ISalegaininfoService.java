package com.sykj.shenfu.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sykj.shenfu.po.Cellextend;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Salegaininfo;
import com.sykj.shenfu.po.Userorder;

/**
 * 产品库存接口
 */
public interface ISalegaininfoService {
	
	/**
	 * 订单保存
	 * 
	 * @param Userorder
	 * @return
	 */
	public Map<String, Object> updateSalegaininfo(String gainercode, String ids, Integer totaltainmoney, String operatorcode);
	
	/**
	 * 员工提成汇总提现
	 * 
	 * @param Userorder
	 * @return
	 */
	public Map<String, Object> saveTakeSalegaininfo(Salegaininfo form, HttpServletRequest request,Operator operator);
}
