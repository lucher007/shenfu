package com.sykj.shenfu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.sykj.shenfu.dao.IEmployeeDao;
import com.sykj.shenfu.dao.ISalelevelparaDao;


@Controller
@Scope("prototype")
@Transactional
public class StatsalescoreController extends BaseController {

	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private ISalelevelparaDao salelevelparaDao;
	
	public void saveStatSalescore(){
		System.out.println("------------------线程启动正常打印日志--------------");
	}
	
	
	
}
