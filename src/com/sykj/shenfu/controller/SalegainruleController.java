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
import com.sykj.shenfu.dao.ISalegainruleDao;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Salegainlog;
import com.sykj.shenfu.po.Salegainrule;
import com.sykj.shenfu.service.IOperatorlogService;


/**
 * 系统参数控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/salegainrule")
@Transactional
public class SalegainruleController extends BaseController{
	
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private ISalegainruleDao salegainruleDao; 

	/**
	 * 查询系统参数信息
	 */
	@RequestMapping(value="/findByList")
	public String findByList(Salegainrule form) {
		return "salegainrule/findSalegainruleList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Salegainrule form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		Integer total = salegainruleDao.findByCount(form);
		List<Salegainrule> list = salegainruleDao.findByList(form);
		for (Salegainrule salegainrule : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			//潜在订户信息
			objectMap.put("id", salegainrule.getId());
			objectMap.put("gaincode", salegainrule.getGaincode());
			objectMap.put("gainname", salegainrule.getGainname());
			objectMap.put("gainrate", salegainrule.getGainrate());
			objectMap.put("gainform", salegainrule.getGainform());
			objectMap.put("gainformname", salegainrule.getGainformname());
			objectMap.put("gaintype", salegainrule.getGaintype());
			objectMap.put("gaintypename", salegainrule.getGaintypemname());
			objectMap.put("remark", salegainrule.getRemark());
			objectMap.put("status", salegainrule.getStatus());
			objectMap.put("statusname", salegainrule.getStatusname());
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
	@RequestMapping(value="/addInit")
	public String addInit(Salegainrule form) {
		return "salegainrule/addSalegainrule";
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public String save(Salegainrule form){
		if ("".equals(form.getGaincode())) {
			form.setReturninfo("编码不能为空");
			return addInit(form);
		} else {
			Salegainrule oldSalegainrule = salegainruleDao.findByGaincode(form.getGaincode());
			if (oldSalegainrule != null) {
				form.setReturninfo("编码已经存在");
				return addInit(form);
			}
		}
		salegainruleDao.save(form);
		form.setReturninfo("保存成功");
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "添加销售提成规则，编码为:"+form.getGaincode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		return addInit(form);
	}
	
	/**
	 * 更新初始化
	 */
	@RequestMapping(value="/updateInit")
	public String updateInit(Salegainrule form){
		form.setSalegainrule(salegainruleDao.findById(form.getId()));
		return "salegainrule/updateSalegainrule";
	}
	
	/**
	 * 更新
	 */
	@RequestMapping(value="/update")
	public String update(Salegainrule form){
		if ("".equals(form.getGaincode())) {
			form.setReturninfo("编码不能为空");
			return updateInit(form);
		} 
		
		Salegainrule oldSalegainrule = salegainruleDao.findByGaincode(form.getGaincode());
		if (oldSalegainrule != null && !oldSalegainrule.getId().equals(form.getId())) {
			form.setReturninfo("编码已经存在");
			return updateInit(form);
		}
       
      	//修改网络信息
		Integer id = salegainruleDao.update(form);
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "修改销售提成规则，编码为:"+form.getGaincode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}
	
	
	/**
	 * 删除
	 * @return
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value="/delete")
	public Map<String,Object> delete(Salegainrule form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		//查询
		Salegainrule salegainrule = salegainruleDao.findById(form.getId());
		// 删除
		salegainruleDao.delete(form.getId());
	
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "删除提成规则，编码为:"+salegainrule.getGaincode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		return responseJson;
	}
	
}
