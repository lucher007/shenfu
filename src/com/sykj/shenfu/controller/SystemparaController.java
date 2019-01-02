package com.sykj.shenfu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sykj.shenfu.dao.ISystemparaDao;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Systempara;
import com.sykj.shenfu.service.IOperatorlogService;


/**
 * 系统参数控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/systempara")
@Transactional
public class SystemparaController extends BaseController{
	
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private ISystemparaDao systemparaDao; 

	/**
	 * 查询系统参数信息
	 */
	@RequestMapping(value="/findByList")
	public String findByList(Systempara form) {
		return "systempara/findSystemparaList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Systempara form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		form.setState("1");
		Integer total = systemparaDao.findByCount(form);
		List<Systempara> list = systemparaDao.findByList(form);
		
		result.put("total", total);
		result.put("rows", list);
		return result;
	}
	
	/**
	 * 添加系统参数信息初始化
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/addInit")
	public String addInit(Systempara form) {
		return "systempara/addSystempara";
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public String save(Systempara form){
		if ("".equals(form.getCode())) {
			form.setReturninfo("参数编码不能为空");
			return addInit(form);
		} else {
			Systempara oldSystempara = systemparaDao.findByCode(form);
			if (oldSystempara != null) {
				form.setReturninfo("参数编码已经存在");
				return addInit(form);
			}
		}
		systemparaDao.save(form);
		form.setReturninfo("保存成功");
		return addInit(form);
	}
	
	/**
	 * 更新初始化
	 */
	@RequestMapping(value="/updateInit")
	public String updateInit(Systempara form){
		form.setSystempara(systemparaDao.findById(form.getId()));
		return "systempara/updateSystempara";
	}
	
	/**
	 * 更新
	 */
	@RequestMapping(value="/update")
	public String update(Systempara form){
		if ("".equals(form.getCode())) {
			form.setReturninfo("参数编码不能为空");
			return updateInit(form);
		} 
		
		Systempara oldSystempara = systemparaDao.findByCode(form);
		if (oldSystempara != null && !oldSystempara.getId().equals(form.getId())) {
			form.setReturninfo("参数编码已经存在");
			return updateInit(form);
		}
       
      	//修改网络信息
		Integer id = systemparaDao.update(form);
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}
	
	
	/**
	 * 删除
	 * @return
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value="/delete")
	public Map<String,Object> delete(Systempara form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		//查询
		Systempara systempara = systemparaDao.findById(form.getId());
		// 删除
		systemparaDao.delete(form.getId());
	
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "删除系统参数，参数名称为:"+systempara.getName();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		return responseJson;
	}
}
