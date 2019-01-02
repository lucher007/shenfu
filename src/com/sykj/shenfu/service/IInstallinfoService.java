package com.sykj.shenfu.service;

import java.util.Map;

import com.sykj.shenfu.po.Installinfo;

/**
 * 操作员编码
 */
public interface IInstallinfoService {
	
	/**
	 * 保存生产信息
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> saveInstallinfo(Installinfo form);
	
	
}
