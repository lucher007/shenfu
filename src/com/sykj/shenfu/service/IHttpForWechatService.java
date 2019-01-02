package com.sykj.shenfu.service;

import java.util.Map;

import org.json.JSONObject;

/**
 * 操作员编码
 */
public interface IHttpForWechatService {
	/**
	 * 保存客户支付信息
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> savePaymentInfo(JSONObject jsonObj);
}
