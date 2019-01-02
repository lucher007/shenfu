package com.sykj.shenfu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sykj.shenfu.dao.IStatisticsDao;
import com.sykj.shenfu.po.Statistics;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/statistics")
@Transactional
public class StatisticsController extends BaseController {

	@Autowired
	private IStatisticsDao statisticsDao;
    
	/**
	 * 查询物料供需统计
	 */
	@RequestMapping(value = "/findMaterialBalanceStat")
	public String findMaterialBalanceStat(Statistics form) {
		return "statistics/findMaterialBalanceStat";
	}
	
	/**
	 * 查询物料供需统计Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findMaterialBalanceStatJson")
	public Map<String, Object> findMaterialBalanceStatJson(Statistics form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		Integer total = statisticsDao.findMaterialBalanceStatCount(form);
		List<Statistics> list = statisticsDao.findMaterialBalanceStatList(form);
		for (Statistics statistics : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			//查询物料供需统计
			objectMap.put("materialid", statistics.getMaterialid());
			objectMap.put("materialcode", statistics.getMaterialcode());
			objectMap.put("materialname", statistics.getMaterialname());
			objectMap.put("orderamount", statistics.getOrderamount());
			objectMap.put("depotamount", statistics.getDepotamount());
			objectMap.put("purchaseamount", statistics.getPurchaseamount());
			
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}

}
