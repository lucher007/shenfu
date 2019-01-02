package com.sykj.shenfu.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.IOperatorlogDao;
import com.sykj.shenfu.po.Operatorlog;
import com.sykj.shenfu.service.IOperatorlogService;


/**
 * @Title MaterialdepotServiceImpl.java
 * @version 1.0 操作员日志实现类
 */
@Service("operatorlogService")
public class OperatorlogServiceImpl implements IOperatorlogService {
	@Autowired
	private IOperatorlogDao operatorlogDao;
	
	/**
	 * 保存操作日志
	 */
	public void saveOperatorlog(String content,String operatorcode) {
		
			Operatorlog log = new Operatorlog();
			log.setOperatorcode(operatorcode);
			log.setAddtime(Tools.getCurrentTime());
			log.setContent(content);
			
			operatorlogDao.save(log);
		
	}

}
