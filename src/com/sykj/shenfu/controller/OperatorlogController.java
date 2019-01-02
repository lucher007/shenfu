package com.sykj.shenfu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.IEmployeeDao;
import com.sykj.shenfu.dao.IOperatorDao;
import com.sykj.shenfu.dao.IOperatorlogDao;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Operatorlog;
import com.sykj.shenfu.service.IOperatorService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/operatorlog")
@Transactional
public class OperatorlogController extends BaseController {

	@Autowired
	private IOperatorlogDao operatorlogDao;
	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private IOperatorService operatorservice;

	/**
	 * 查询操作员日志
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Operatorlog form) {
		return "operatorlog/findOperatorlogList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Operatorlog form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Integer total = operatorlogDao.findByCount(form);
		List<Operatorlog> list = operatorlogDao.findByList(form);
		for (Operatorlog operatorlog : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("id", operatorlog.getId());
			objectMap.put("operatorcode", Tools.getStr(operatorlog.getOperatorcode()));
			if(StringUtils.isNotEmpty(operatorlog.getOperatorcode())){
				Employee employee = employeeDao.findByEmployeecodeStr(operatorlog.getOperatorcode());
				if(employee != null){
					objectMap.put("operatorname", Tools.getStr(employee.getEmployeename()));
					objectMap.put("operatorphone", Tools.getStr(employee.getPhone()));
				}
			}
			objectMap.put("content", Tools.getStr(operatorlog.getContent()));
			objectMap.put("addtime", StringUtils.isEmpty(operatorlog.getAddtime())?"":Tools.getStr(operatorlog.getAddtime()).substring(0, 19));
			objectMap.put("remark", Tools.getStr(operatorlog.getRemark()));
			
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}
	
}
