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
import com.sykj.shenfu.dao.IHelpinfoDao;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Helpinfo;
import com.sykj.shenfu.service.IOperatorlogService;


/**
 * 系统参数控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/helpinfo")
@Transactional
public class HelpinfoController extends BaseController{
	
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private IHelpinfoDao helpinfoDao; 

	/**
	 * 查询系统参数信息
	 */
	@RequestMapping(value="/findByList")
	public String findByList(Helpinfo form) {
		return "helpinfo/findHelpinfoList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Helpinfo form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Integer total = helpinfoDao.findByCount(form);
		List<Helpinfo> list = helpinfoDao.findByList(form);
		for (Helpinfo helpinfo : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			
			//产品信息
			objectMap.put("id", helpinfo.getId());
			objectMap.put("type", helpinfo.getType());
			objectMap.put("typename", helpinfo.getTypename());
			objectMap.put("question", helpinfo.getQuestion());
			objectMap.put("answer", helpinfo.getAnswer());
			objectMap.put("status", helpinfo.getStatusname());
			objectMap.put("addtime", StringUtils.isEmpty(helpinfo.getAddtime())?"":Tools.getStr(helpinfo.getAddtime()).substring(0, 19));
			objectMap.put("showorder", helpinfo.getShoworder());
			objectlist.add(objectMap);
		}
		result.put("total", total);
		result.put("rows", objectlist);
		return result;
	}
	
	/**
	 * 添加系统参数信息初始化
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/addInit")
	public String addInit(Helpinfo form) {
		return "helpinfo/addHelpinfo";
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public String save(Helpinfo form){
		
		String currenttime = Tools.getCurrentTime();
		form.setAddtime(currenttime);
		form.setStatus("1");
		
		helpinfoDao.save(form);
		form.setReturninfo("保存成功");
		return addInit(form);
	}
	
	/**
	 * 更新初始化
	 */
	@RequestMapping(value="/updateInit")
	public String updateInit(Helpinfo form){
		form.setHelpinfo(helpinfoDao.findById(form.getId()));
		return "helpinfo/updateHelpinfo";
	}
	
	/**
	 * 更新
	 */
	@RequestMapping(value="/update")
	public String update(Helpinfo form){
       
      	//修改网络信息
		Integer id = helpinfoDao.update(form);
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}
	
	
	/**
	 * 删除
	 * @return
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value="/delete")
	public Map<String,Object> delete(Helpinfo form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		//查询
		Helpinfo helpinfo = helpinfoDao.findById(form.getId());
		// 删除
		helpinfoDao.delete(form.getId());
	
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
}
