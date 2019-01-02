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

import com.sykj.shenfu.dao.ISaleenergyruleDao;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Saleenergyrule;
import com.sykj.shenfu.service.IOperatorlogService;


/**
 * 系统参数控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/saleenergyrule")
@Transactional
public class SaleenergyruleController extends BaseController{
	
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private ISaleenergyruleDao saleenergyruleDao; 

	/**
	 * 查询系统参数信息
	 */
	@RequestMapping(value="/findByList")
	public String findByList(Saleenergyrule form) {
		return "saleenergyrule/findSaleenergyruleList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Saleenergyrule form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		Integer total = saleenergyruleDao.findByCount(form);
		List<Saleenergyrule> list = saleenergyruleDao.findByList(form);
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
	public String addInit(Saleenergyrule form) {
		return "saleenergyrule/addSaleenergyrule";
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public String save(Saleenergyrule form){
		if ("".equals(form.getEnergycode())) {
			form.setReturninfo("编码不能为空");
			return addInit(form);
		} else {
			Saleenergyrule oldSaleenergyrule = saleenergyruleDao.findByEnergycode(form.getEnergycode());
			if (oldSaleenergyrule != null) {
				form.setReturninfo("编码已经存在");
				return addInit(form);
			}
		}
		saleenergyruleDao.save(form);
		form.setReturninfo("保存成功");
		return addInit(form);
	}
	
	/**
	 * 更新初始化
	 */
	@RequestMapping(value="/updateInit")
	public String updateInit(Saleenergyrule form){
		form.setSaleenergyrule(saleenergyruleDao.findById(form.getId()));
		return "saleenergyrule/updateSaleenergyrule";
	}
	
	/**
	 * 更新
	 */
	@RequestMapping(value="/update")
	public String update(Saleenergyrule form){
		if ("".equals(form.getEnergycode())) {
			form.setReturninfo("编码不能为空");
			return updateInit(form);
		} 
		
		Saleenergyrule oldSaleenergyrule = saleenergyruleDao.findByEnergycode(form.getEnergycode());
		if (oldSaleenergyrule != null && !oldSaleenergyrule.getId().equals(form.getId())) {
			form.setReturninfo("编码已经存在");
			return updateInit(form);
		}
       
      	//修改网络信息
		Integer id = saleenergyruleDao.update(form);
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}
	
	
	/**
	 * 删除
	 * @return
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value="/delete")
	public Map<String,Object> delete(Saleenergyrule form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		//查询
		Saleenergyrule saleenergyrule = saleenergyruleDao.findById(form.getId());
		// 删除
		saleenergyruleDao.delete(form.getId());
	
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "删除行动力规则，编码为:"+saleenergyrule.getEnergycode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		return responseJson;
	}
	
}
