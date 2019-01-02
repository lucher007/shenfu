package com.sykj.shenfu.service.impl;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sykj.shenfu.dao.IEmployeeDao;
import com.sykj.shenfu.dao.IOperatorDao;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.service.IOperatorService;


/**
 * @Title MaterialdepotServiceImpl.java
 * @version 1.0 操作员实现类
 */
@Service("operatorService")
public class OperatorServiceImpl implements IOperatorService {
	@Autowired
	private IOperatorDao operatorDao;
	@Autowired
	private IEmployeeDao employeeDao;
	
	//定义锁对象
	private Lock lock = new ReentrantLock();
		
	/**
	 * 查询操作员信息
	 */
	public Operator findByEmployeecode(String employeecode) {
		try{
			//加锁
			lock.lock();
			
			Operator operator = operatorDao.findByEmployeecode(employeecode);
			if(operator != null){
				if("admin".equals(operator.getLoginname())){//系统超级管理员
					Employee employee = new Employee();
					employee.setEmployeename("超级管理员");
					operator.setEmployee(employee);
				}else{
					Employee employee = employeeDao.findByEmployeecodeStr(employeecode);
					operator.setEmployee(employee);
				}
			}
			return operator;
		} finally {
			
			//释放锁
			lock.unlock();
		}
	}

}
