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

import com.sykj.shenfu.dao.IUserbusinessDao;
import com.sykj.shenfu.po.Userbusiness;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/userbusiness")
@Transactional
public class UserbusinessController extends BaseController {
    
	@Autowired
	private IUserbusinessDao userbusinessDao;
    
	/*
     * 特殊的instance变量
     * 注：零长度的byte数组对象创建起来将比任何对象都经济――查看编译后的字节码：
     * 生成零长度的byte[]对象只需3条操作码，而Object lock = new Object()则需要7行操作码。
     */
    private static byte[] lock = new byte[0];   
	
	/**
	 * 查询订户
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Userbusiness form) {
		return "userbusiness/findUserbusinessList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Userbusiness form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		//封装List数据
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		Integer total = userbusinessDao.findByCount(form);
		List<Userbusiness> list = userbusinessDao.findByList(form);
		for (Userbusiness userbusiness : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			//潜在订户信息
			objectMap.put("id", userbusiness.getId());
			objectMap.put("operatorcode", userbusiness.getOperatorcode());
			objectMap.put("businesstypekey", userbusiness.getBusinesstypekey());
			objectMap.put("businesstypename", userbusiness.getBusinesstypename());
			objectMap.put("usercode", userbusiness.getUsercode());
			objectMap.put("taskcode", userbusiness.getTaskcode());
			objectMap.put("ordercode", userbusiness.getOrdercode());
			objectMap.put("dispatchcode", userbusiness.getDispatchcode());
			objectMap.put("addtime", userbusiness.getAddtime());
			objectMap.put("content", userbusiness.getContent());
			objectMap.put("source", userbusiness.getSource());
			objectMap.put("remark", userbusiness.getRemark());
			
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}

	/**
	 * 添加订户页面初始化
	 */
	@RequestMapping(value = "/addInit")
	public String addInit(Userbusiness form) {
		return "userbusiness/addUserbusiness";
	}
	
}
