package com.sykj.shenfu.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Purchase;
import com.sykj.shenfu.po.Userorder;

/**
 * 产品库存接口
 */
public interface IPurchaseService {
	
	/**
	 * 采购单入库
	 * 
	 * @param Userorder
	 * @return
	 */
	public Map<String, Object> saveIndepot(Purchase purchaseform, HttpServletRequest request, Operator operator);
	
}
