package com.sykj.shenfu.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sykj.shenfu.dao.ISystemparaDao;
import com.sykj.shenfu.po.Systempara;
import com.sykj.shenfu.service.ISystemparaService;

/**
 * @Title SystemparaServiceImpl.java
 * @version 1.0 高斯贝尔高安Ca实现类
 */
@Service("systemparaService")
public class SystemparaServiceImpl implements ISystemparaService {
	@Autowired
	private ISystemparaDao systemparaDao;
	
	/**
	 * 根据CODE查询系统参数值
	 */
	public String  findSystemParaByCodeStr(String paracode) {
		
		Systempara para = new Systempara();
		para.setCode(paracode);
		para = systemparaDao.findByCode(para);
		if(para != null && !"".equals(para.getValue())){
			return para.getValue();
		}else{
			return "";
		}
	}
}
