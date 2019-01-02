package com.sykj.shenfu.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Saleenergyinfo;

/**
 * 产品库存接口
 */
public interface ISaleenergyinfoService {
	
	/**
	 * 保存行动力兑换提成
	 * 
	 * @param Userorder
	 * @return
	 */
	public Map<String, Object> saveEnergyExchangeToGain(Saleenergyinfo form, HttpServletRequest request,Operator operator);
}
