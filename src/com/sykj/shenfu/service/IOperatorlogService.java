package com.sykj.shenfu.service;

/**
 * 材料库存接口
 */
public interface IOperatorlogService {
	
	/**
	 * 保存操作日志
	 * 
	 * @param Materialdepot
	 * @return
	 */
	public void saveOperatorlog(String content,String operatorcode);
	
}
