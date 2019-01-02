package com.sykj.shenfu.service;

import com.sykj.shenfu.po.Operator;

/**
 * 材料库存接口
 */
public interface IOperatorService {
	
	/**
	 * 查询操作员信息
	 * 
	 * @param Materialdepot
	 * @return
	 */
	public Operator findByEmployeecode(String employeecode);
	
	
}
