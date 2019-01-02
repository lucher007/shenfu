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
import com.sykj.shenfu.dao.IUserDao;
import com.sykj.shenfu.dao.IUserproductreplaceDao;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.User;
import com.sykj.shenfu.po.Userproductreplace;
import com.sykj.shenfu.service.ICoderuleService;
import com.sykj.shenfu.service.IOperatorService;
import com.sykj.shenfu.service.IOperatorlogService;
import com.sykj.shenfu.service.IUserService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/userproductreplace")
@Transactional
public class UserproductreplaceController extends BaseController {

	@Autowired
	private IUserDao userDao;
	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private IOperatorService operatorService;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private ICoderuleService coderuleService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IUserproductreplaceDao userproductreplaceDao;

	/**
	 * 查询订户
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Userproductreplace form) {
		return "userproductreplace/findUserproductreplaceList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Userproductreplace form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		//封装List数据
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		Integer total = userproductreplaceDao.findByCount(form);
		List<Userproductreplace> list = userproductreplaceDao.findByList(form);
		for (Userproductreplace userproductreplace : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			//潜在订户信息
			objectMap.put("id", userproductreplace.getId());
			objectMap.put("usercode", userproductreplace.getUsercode());
			User user = userDao.findByUsercodeStr(userproductreplace.getUsercode());
			if(user != null){
				objectMap.put("username", user.getUsername());
				objectMap.put("phone", user.getPhone());
				objectMap.put("address", user.getAddress());
			}
			objectMap.put("ordercode", userproductreplace.getOrdercode());
			objectMap.put("modelname", userproductreplace.getModelname());
			objectMap.put("modelcode", userproductreplace.getModelcode());
			objectMap.put("typecode", userproductreplace.getTypecode());
			objectMap.put("typename", userproductreplace.getTypename());
			objectMap.put("productname", userproductreplace.getProductname());
			objectMap.put("productcode", userproductreplace.getProductcode());
			objectMap.put("addtime", StringUtils.isEmpty(userproductreplace.getAddtime())?"":Tools.getStr(userproductreplace.getAddtime()).substring(0, 10));
			objectMap.put("operatorcode", userproductreplace.getOperatorcode());
			objectMap.put("visittime", StringUtils.isEmpty(userproductreplace.getVisittime())?"":Tools.getStr(userproductreplace.getVisittime()).substring(0, 10));
			objectMap.put("visitreasons", userproductreplace.getVisitreasons());
			objectMap.put("replacetype", userproductreplace.getReplacetype());
			objectMap.put("replacetypename", userproductreplace.getReplacetypename());
			objectMap.put("oldproductidentfy", userproductreplace.getOldproductidentfy());
			objectMap.put("oldproductversion", userproductreplace.getOldproductversion());
			objectMap.put("newproductidentfy", userproductreplace.getNewproductidentfy());
			objectMap.put("newproductversion", userproductreplace.getNewproductversion());
			objectMap.put("oldproductstatus", userproductreplace.getOldproductstatus());
			objectMap.put("oldproductstatusname", userproductreplace.getOldproductstatusname());
			objectMap.put("oldproductproblem", userproductreplace.getOldproductproblem());
			objectMap.put("rebackremark", userproductreplace.getRebackremark());
			objectMap.put("remark", Tools.getStr(userproductreplace.getRemark()));
			
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}

}
