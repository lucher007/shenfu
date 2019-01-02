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
import com.sykj.shenfu.dao.IActivityDao;
import com.sykj.shenfu.po.Activity;
import com.sykj.shenfu.po.Material;
import com.sykj.shenfu.service.IOperatorlogService;


/**
 * 系统参数控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/activity")
@Transactional
public class ActivityController extends BaseController{
	
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private IActivityDao activityDao; 

	/**
	 * 查询系统参数信息
	 */
	@RequestMapping(value="/findByList")
	public String findByList(Activity form) {
		return "activity/findActivityList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Activity form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Integer total = activityDao.findByCount(form);
		List<Activity> list = activityDao.findByList(form);
		for (Activity activity : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			
			//产品信息
			objectMap.put("id", activity.getId());
			objectMap.put("activitycode", activity.getActivitycode());
			objectMap.put("title", activity.getTitle());
			objectMap.put("content", activity.getContent());
			objectMap.put("addtime", StringUtils.isEmpty(activity.getAddtime())?"":Tools.getStr(activity.getAddtime()).substring(0, 19));
			objectMap.put("status", activity.getStatus());
			objectMap.put("statusname", activity.getStatusname());
			objectMap.put("remark", activity.getRemark());
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
	public String addInit(Activity form) {
		return "activity/addActivity";
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public String save(Activity form){
		if ("".equals(form.getActivitycode())) {
			form.setReturninfo("操作失败，请输入活动编号");
			return addInit(form);
		} else {
			Activity activity = activityDao.findByActivitycode(form.getActivitycode());
			if (activity != null) {
				form.setReturninfo("操作失败，此活动编号已经存在");
				return addInit(form);
			}
		}
		String currenttime = Tools.getCurrentTime();
		form.setAddtime(currenttime);
		form.setStatus("1");
		activityDao.save(form);
		form.setReturninfo("保存成功");
		return addInit(form);
	}
	
	/**
	 * 更新初始化
	 */
	@RequestMapping(value="/updateInit")
	public String updateInit(Activity form){
		form.setActivity(activityDao.findById(form.getId()));
		return "activity/updateActivity";
	}
	
	/**
	 * 更新
	 */
	@RequestMapping(value="/update")
	public String update(Activity form){
      	//修改网络信息
		Integer id = activityDao.update(form);
		form.setReturninfo("修改成功");
		return updateInit(form);
	}
	
	
	/**
	 * 删除
	 * @return
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value="/delete")
	public Map<String,Object> delete(Activity form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		//查询
		Activity activity = activityDao.findById(form.getId());
		// 删除
		activityDao.delete(form.getId());
	
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
}
