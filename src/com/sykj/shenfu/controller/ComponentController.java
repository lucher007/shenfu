package com.sykj.shenfu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.IComponentDao;
import com.sykj.shenfu.dao.IComponentdepotDao;
import com.sykj.shenfu.po.Component;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.service.IOperatorlogService;

/**
 * 元器件控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/component")
@Transactional
public class ComponentController extends BaseController {

	@Autowired
	private IComponentDao componentDao;
	@Autowired
	private IComponentdepotDao componentdepotDao;
	@Autowired
	private IOperatorlogService operatorlogService;

	/**
	 * 查询元器件
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Component form) {
		return "component/findComponentList";
	}
	
	/**
	 * 查询元器件信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Component form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		Integer total = componentDao.findByCount(form);
		List<Component> list = componentDao.findByList(form);
		
		result.put("total", total);//页面总数
		result.put("rows", list);
		return result;
	}

	/**
	 * 添加元器件页面初始化
	 */
	@RequestMapping(value = "/addInit")
	public String addInit(Component form) {
		return "component/addComponent";
	}

	/**
	 * 保存添加元器件
	 */
	@RequestMapping(value = "/save")
	public String save(Component form) {
		
		if ("".equals(form.getComponentcode())) {
			form.setReturninfo("请输入元器件编号");
			return addInit(form);
		} else {
			Component component = componentDao.findByComponentcode(form.getComponentcode());
			if (component != null) {
				form.setReturninfo("此元器件编号已经存在");
				return addInit(form);
			}
		}
		
		//添加原元器件的时候，默认报警量量为10
		if(StringUtils.isEmpty(form.getDepotamount()) || form.getDepotamount() == 0){
			form.setDepotamount(10);
		}
		
		componentDao.save(form);
		
		form.setReturninfo("保存成功");
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "增加元器件信息，元器件编号为:"+form.getComponentcode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		return addInit(form);
	}

	/**
	 * 编辑元器件权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Component form) {
		form.setComponent(componentDao.findById(form.getId()));
		return "component/updateComponent";
	}

	/**
	 * 保存编辑后元器件权限
	 */
	@RequestMapping(value = "/update")
	public String update(Component form) {
		
		if ("".equals(Tools.getStr(form.getComponentname()))) {
			form.setReturninfo("元器件名称不能为空");
			return updateInit(form);
		}
		
		
		//添加原元器件的时候，默认报警量量为10
		if(StringUtils.isEmpty(form.getDepotamount()) || form.getDepotamount() == 0){
			form.setDepotamount(10);
		}
		
		componentDao.update(form);
		form.setReturninfo("修改成功");
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "修改元器件信息，元器件编号为:"+form.getComponentcode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		return updateInit(form);
	}

	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Component form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Component component = componentDao.findById(form.getId());
		
		if(component != null){
			//删除元器件
			componentDao.delete(form.getId());
		} else {
			responseJson.put("flag", "0");//删除失败
			responseJson.put("msg", "删除失败，该元器件不存在");
			return responseJson;
		}
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "删除元器件信息，元器件名称为:"+component.getComponentname();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
	/**
     * 获取元器件的下拉列表框Json值
     */
	@ResponseBody //此标签表示返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	@RequestMapping(value="/getComponentComboBoxJson")
	public List<Map<String,Object>> getComponentComboBoxJson(Component form) {  
	    List<Component> componentList = componentDao.queryByList(form);
	    List<Map<String, Object>> componentComboBoxJson = new ArrayList<Map<String, Object>>();
	    //添加请选择项-默认为空，查询所有
	    HashMap<String,Object> selectMap = new HashMap<String, Object>();
	    selectMap.put("id", "");
	    selectMap.put("text", "请选择");
	    componentComboBoxJson.add(selectMap);
	    
	    //封装区域属性结构信息
	    for(Component component:componentList){  
    	    HashMap<String,Object> componentMap = new HashMap<String, Object>();
    	    componentMap.put("id", component.getComponentcode());
    	    componentMap.put("text", component.getComponentname() + "_"+ component.getComponentmodel() + "("+component.getComponentcode()+")");
    	    
    	    componentComboBoxJson.add(componentMap);  
	    }  
	   return componentComboBoxJson;
	}  
	
	/**
     * 获取元器件的下拉列表框表格显示Json值
     */
	@ResponseBody //此标签表示返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	@RequestMapping(value="/getComponentCombogridJson")
	public List<Map<String,Object>> getComponentCombogridJson(Component form) {  
		List<Component> componentList = componentDao.queryByList(form);
	    List<Map<String, Object>> componentCombogridJson = new ArrayList<Map<String, Object>>();
	   
	    //封装区域属性结构信息
	    for(Component component:componentList){
	    	    HashMap<String,Object> componentMap = new HashMap<String, Object>();
	    	    componentMap.put("id", component.getId());
	    	    componentMap.put("componentcode", component.getComponentcode());
	    	    componentMap.put("componentname", component.getComponentname());
	    	    componentMap.put("componentmodel", component.getComponentmodel());
	    	    componentMap.put("componentunit", component.getComponentunit());
	    	    componentMap.put("depotamount", component.getDepotamount());
	    	    componentMap.put("componentstatus", component.getComponentstatus());
	    	    componentCombogridJson.add(componentMap);  
	    }  
	   return componentCombogridJson;
	}  
	
}
