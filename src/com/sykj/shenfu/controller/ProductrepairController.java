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
import com.sykj.shenfu.dao.IProductrepairDao;
import com.sykj.shenfu.po.Productrepair;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/productrepair")
@Transactional
public class ProductrepairController extends BaseController {

	@Autowired
	private IProductrepairDao productrepairDao;

	/**
	 * 查询订户
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Productrepair form) {
		return "productrepair/findProductrepairList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Productrepair form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		//封装List数据
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		Integer total = productrepairDao.findByCount(form);
		List<Productrepair> list = productrepairDao.findByList(form);
		for (Productrepair productrepair : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			//潜在订户信息
			objectMap.put("id", productrepair.getId());
			objectMap.put("typecode", productrepair.getTypecode());
			objectMap.put("typename", productrepair.getTypename());
			objectMap.put("productname", productrepair.getProductname());
			objectMap.put("productcode", productrepair.getProductcode());
			objectMap.put("productidentfy", productrepair.getProductidentfy());
			objectMap.put("addtime", StringUtils.isEmpty(productrepair.getAddtime())?"":Tools.getStr(productrepair.getAddtime()).substring(0, 10));
			objectMap.put("operatorcode", productrepair.getOperatorcode());
			objectMap.put("productproblem", Tools.getStr(productrepair.getProductproblem()));
			objectMap.put("repairinfo", Tools.getStr(productrepair.getRepairinfo()));
			objectMap.put("repairstatus", Tools.getStr(productrepair.getRepairstatus()));
			objectMap.put("repairstatusname", Tools.getStr(productrepair.getRepairstatusname()));
			objectMap.put("remark", Tools.getStr(productrepair.getRemark()));
			
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}

}
