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

import com.sykj.shenfu.dao.ISalerrechargevipinfoDao;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Productcolor;
import com.sykj.shenfu.po.Salerrechargevipinfo;
import com.sykj.shenfu.service.IOperatorlogService;


/**
 * 系统参数控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/salerrechargevipinfo")
@Transactional
public class SalerrechargevipinfoController extends BaseController{
	
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private ISalerrechargevipinfoDao salerrechargevipinfoDao; 

	/**
	 * 查询系统参数信息
	 */
	@RequestMapping(value="/findByList")
	public String findByList(Salerrechargevipinfo form) {
		return "salerrechargevipinfo/findSalerrechargevipinfoList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Salerrechargevipinfo form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		Integer total = salerrechargevipinfoDao.findByCount(form);
		List<Salerrechargevipinfo> list = salerrechargevipinfoDao.findByList(form);
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
	public String addInit(Salerrechargevipinfo form) {
		return "salerrechargevipinfo/addSalerrechargevipinfo";
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public String save(Salerrechargevipinfo form){
		if ("".equals(form.getEmployeecode())) {
			form.setReturninfo("员工编号不能为空");
			return addInit(form);
		} else {
			Salerrechargevipinfo oldSalerrechargevipinfo = salerrechargevipinfoDao.findByEmployeecode(form.getEmployeecode());
			if (oldSalerrechargevipinfo != null) {
				form.setReturninfo("员工编号已经存在");
				return addInit(form);
			}
		}
		salerrechargevipinfoDao.save(form);
		form.setReturninfo("保存成功");
		return addInit(form);
	}
	
	/**
	 * 更新初始化
	 */
	@RequestMapping(value="/updateInit")
	public String updateInit(Salerrechargevipinfo form){
		form.setSalerrechargevipinfo(salerrechargevipinfoDao.findById(form.getId()));
		return "salerrechargevipinfo/updateSalerrechargevipinfo";
	}
	
	/**
	 * 更新
	 */
	@RequestMapping(value="/update")
	public String update(Salerrechargevipinfo form){
		if ("".equals(form.getEmployeecode())) {
			form.setReturninfo("员工编号不能为空");
			return updateInit(form);
		} 
		
		Salerrechargevipinfo oldSalerrechargevipinfo = salerrechargevipinfoDao.findByEmployeecode(form.getEmployeecode());
		if (oldSalerrechargevipinfo != null && !oldSalerrechargevipinfo.getId().equals(form.getId())) {
			form.setReturninfo("员工编号已经存在");
			return updateInit(form);
		}
       
      	//修改网络信息
		Integer id = salerrechargevipinfoDao.update(form);
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}
	
	
	/**
	 * 删除
	 * @return
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value="/delete")
	public Map<String,Object> delete(Salerrechargevipinfo form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		//查询
		Salerrechargevipinfo salerrechargevipinfo = salerrechargevipinfoDao.findById(form.getId());
		// 删除
		salerrechargevipinfoDao.delete(form.getId());
	
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "删除充值VIP提成信息，销售员编码为:"+salerrechargevipinfo.getEmployeecode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		return responseJson;
	}
}
