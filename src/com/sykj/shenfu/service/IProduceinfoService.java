package com.sykj.shenfu.service;

import java.util.Map;

import com.sykj.shenfu.po.Produceinfo;

/**
 * 操作员编码
 */
public interface IProduceinfoService {
	
	/**
	 * 保存生产信息
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveProduceinfo(Produceinfo form);
	
	
}
