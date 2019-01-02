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
import com.sykj.shenfu.dao.IEmployeeDao;
import com.sykj.shenfu.dao.IOperatorDao;
import com.sykj.shenfu.dao.ISupplierDao;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Material;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Supplier;
import com.sykj.shenfu.service.IOperatorService;
import com.sykj.shenfu.service.IOperatorlogService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/supplier")
@Transactional
public class SupplierController extends BaseController {

	@Autowired
	private ISupplierDao supplierDao;
	@Autowired
	private IOperatorService operatorService;
	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private IOperatorlogService operatorlogService;

	/**
	 * 查询供应商
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Supplier form) {
		return "supplier/findSupplierList";
	}
	
	/**
	 * 查询供应商弹出页
	 */
	@RequestMapping(value = "/findSupplierListDialog")
	public String findSupplierListDialog(Supplier form) {
		return "supplier/findSupplierListDialog";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Supplier form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Integer total = supplierDao.findByCount(form);
		List<Supplier> list = supplierDao.findByList(form);
		for (Supplier supplier : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("id", supplier.getId());
			objectMap.put("name", supplier.getName());
			objectMap.put("fullname", supplier.getFullname());
			objectMap.put("address", supplier.getAddress());
			objectMap.put("phone", supplier.getPhone());
			objectMap.put("contactname", supplier.getContactname());
			objectMap.put("contactphone", supplier.getContactphone());
			objectMap.put("contactmail", supplier.getContactmail());
			objectMap.put("status", supplier.getStatus());
			objectMap.put("statusname", supplier.getStatusname());
			objectMap.put("creatercode", supplier.getCreatercode());
			if(StringUtils.isNotEmpty(supplier.getCreatercode())){
				Employee creater = employeeDao.findByEmployeecodeStr(supplier.getCreatercode());
				if(creater != null){
					objectMap.put("creatername", creater.getEmployeename());
					objectMap.put("createrphone", creater.getPhone());
				}
			}
			objectMap.put("createdate", StringUtils.isEmpty(supplier.getCreatedate())?"":Tools.getStr(supplier.getCreatedate()).substring(0, 19));
			
			if(StringUtils.isNotEmpty(supplier.getModifiercode())){
				Employee modifier = employeeDao.findByEmployeecodeStr(supplier.getModifiercode());
				if(modifier != null){
					objectMap.put("modifiername", modifier.getEmployeename());
					objectMap.put("modifierphone", modifier.getPhone());
				}
			}
			objectMap.put("modifiercode", StringUtils.isEmpty(supplier.getModifydate())?"":Tools.getStr(supplier.getModifydate()).substring(0, 19));
			
			objectMap.put("introduceinfo", supplier.getIntroduceinfo());
			objectMap.put("remark", supplier.getRemark());
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}

	/**
	 * 添加供应商页面初始化
	 */
	@RequestMapping(value = "/addInit")
	public String addInit(Supplier form) {
		return "supplier/addSupplier";
	}

	/**
	 * 保存添加供应商
	 */
	@RequestMapping(value = "/save")
	public String save(Supplier form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		form.setStatus("1");//默认添加的都为有效
		form.setCreatercode(operator.getEmployeecode());
		form.setCreatedate(Tools.getCurrentTime());
		form.setModifiercode(operator.getEmployeecode());
		form.setModifydate(Tools.getCurrentTime());
		supplierDao.save(form);
		
		//增加操作日记
		String content = "添加供应商，供应商全称:"+form.getFullname();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("保存成功");
		
		return addInit(form);
	}

	/**
	 * 编辑供应商权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Supplier form) {
		form.setSupplier(supplierDao.findById(form.getId()));
		return "supplier/updateSupplier";
	}

	/**
	 * 保存编辑后供应商权限
	 */
	@RequestMapping(value = "/update")
	public String update(Supplier form) {
		
		Operator operator = (Operator)getSession().getAttribute("Operator");
		//增加修改人信息
		form.setModifiercode(operator.getEmployeecode());
		form.setModifydate(Tools.getCurrentTime());
		supplierDao.update(form);
		//增加操作日记
		String content = "修改供应商信息，供应商全称:"+form.getFullname();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}

	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Supplier form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Supplier supplier = supplierDao.findById(form.getId());
		
		//删除供应商
		supplierDao.delete(form.getId());
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "删除供应商，供应商全称:"+supplier.getFullname();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
	/**
     * 获取材料的下拉列表框Json值
     */
	@ResponseBody //此标签表示返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	@RequestMapping(value="/getSupplierComboBoxJson")
	public List<Map<String,Object>> getSupplierComboBoxJson(Supplier form) {  
	    List<Supplier> supplierList = supplierDao.queryByList(form);
	    List<Map<String, Object>> comboBoxJson = new ArrayList<Map<String, Object>>();
	    //添加请选择项-默认为空，查询所有
	    HashMap<String,Object> selectMap = new HashMap<String, Object>();
	    selectMap.put("id", "");
	    selectMap.put("text", "请选择");
	    comboBoxJson.add(selectMap);
	    
	    //封装区域属性结构信息
	    for(Supplier supplier:supplierList){ 
    	    HashMap<String,Object> materialMap = new HashMap<String, Object>();
    	    materialMap.put("id", supplier.getName());
    	    materialMap.put("text", supplier.getName()+"("+supplier.getFullname()+")");
    	    
    	    comboBoxJson.add(materialMap);  
	    }  
	   return comboBoxJson;
	} 
	
}
