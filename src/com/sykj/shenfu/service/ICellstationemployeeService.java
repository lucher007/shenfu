package com.sykj.shenfu.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sykj.shenfu.po.Cellstationemployee;
import com.sykj.shenfu.po.Operator;

/**
 * 操作员编码
 */
public interface ICellstationemployeeService {
	
	public Map<String, Object> saveCellstationemployee(Cellstationemployee cellstationemployeeform, HttpServletRequest request,Operator operator);
	
	
}
