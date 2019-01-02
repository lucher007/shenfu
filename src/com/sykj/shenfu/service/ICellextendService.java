package com.sykj.shenfu.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sykj.shenfu.po.Cellextend;
import com.sykj.shenfu.po.Operator;

/**
 * 操作员编码
 */
public interface ICellextendService {
	
	public Map<String, Object> saveCellextend(Cellextend cellextend, HttpServletRequest request,Operator operator);
	
	
}
