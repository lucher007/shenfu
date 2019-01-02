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

import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.IProductDao;
import com.sykj.shenfu.dao.IProducttypeDao;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Product;
import com.sykj.shenfu.po.Producttype;
import com.sykj.shenfu.service.IOperatorlogService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/producttype")
@Transactional
public class ProducttypeController extends BaseController {

	@Autowired
	private IProducttypeDao producttypeDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private IProductDao productDao;
	
	/**
	 * 查询产品
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Producttype form) {
		return "producttype/findProducttypeList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Producttype form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		Integer total = producttypeDao.findByCount(form);
		List<Producttype> list = producttypeDao.findByList(form);
		
		result.put("total", total);//页面总数
		result.put("rows", list);
		return result;
	}

	/**
	 * 添加产品页面初始化
	 */
	@RequestMapping(value = "/addInit")
	public String addInit(Producttype form) {
		return "producttype/addProducttype";
	}

	/**
	 * 保存添加产品
	 */
	@RequestMapping(value = "/save")
	public String save(Producttype form) {
		
		if ("".equals(form.getTypecode())) {
			form.setReturninfo("请输入产品类别编码");
			return addInit(form);
		} else {
			Producttype producttype = producttypeDao.findByTypecode(form.getTypecode());
			if (producttype != null) {
				form.setReturninfo("此产品类别编码已经存在");
				return addInit(form);
			}
		}
		
		
		producttypeDao.save(form);
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "增加产品类别信息，产品类别名称:"+form.getTypename();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		
		form.setReturninfo("保存成功");
		
		return addInit(form);
	}

	/**
	 * 编辑产品权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Producttype form) {
		form.setProducttype(producttypeDao.findById(form.getId()));
		return "producttype/updateProducttype";
	}

	/**
	 * 保存编辑后产品权限
	 */
	@RequestMapping(value = "/update")
	public String update(Producttype form) {
		if ("".equals(Tools.getStr(form.getTypecode()))) {
			form.setReturninfo("产品类别编码不能为空");
			return updateInit(form);
		} else {
			//判断材料编号是否重复
			Producttype oldProducttype = producttypeDao.findByTypecode(form.getTypecode());
			if (oldProducttype != null && !oldProducttype.getId().equals(form.getId())) {
				form.setReturninfo(getMessage("产品类别编码已经存在"));
				return updateInit(form);
			}
		}
		
		producttypeDao.update(form);
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "修改产品类别信息，产品类别名称:"+form.getTypename();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}

	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Producttype form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Producttype producttype = producttypeDao.findById(form.getId());
		if(producttype != null){
			List<Product> productlist = productDao.findByTypecode(producttype.getTypecode());
			if(productlist == null || productlist.size() == 0){
				//删除产品列表
				producttypeDao.delete(form.getId());
				
				//增加操作日记
				Operator operator = (Operator)getSession().getAttribute("Operator");
				String content = "删除产品类别信息，产品列表名称:"+producttype.getTypename();
				operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
				
				responseJson.put("flag", "1");//删除成功
				responseJson.put("msg", "删除成功");
			}else{
				responseJson.put("flag", "0");//删除成功
				responseJson.put("msg", "删除失败，该产品类别已经有产品信息存在");
			}
		}else{
			responseJson.put("flag", "0");//删除成功
			responseJson.put("msg", "删除失败，该产品类别已经不存在");
		}
		return responseJson;
	}
	
	/**
     * 获取下拉列表框Json值
     */
	@ResponseBody //此标签表示返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	@RequestMapping(value="/getProducttypeComboBoxJson")
	public List<Map<String,Object>> getProducttypeComboBoxJson(Producttype form) {  
	    List<Producttype> producttypeList = producttypeDao.queryByList(form);
	    List<Map<String, Object>> producttypeComboBoxJson = new ArrayList<Map<String, Object>>();
	    //添加请选择项-默认为空，查询所有
	    HashMap<String,Object> selectMap = new HashMap<String, Object>();
	    selectMap.put("typecode", "");
	    selectMap.put("text", "请选择");
	    producttypeComboBoxJson.add(selectMap);
	    
	    //封装区域属性结构信息
	    for(Producttype producttype:producttypeList){  
	    	    HashMap<String,Object> producttypeMap = new HashMap<String, Object>();
	    	    producttypeMap.put("typecode", producttype.getTypecode());
	    	    producttypeMap.put("text", producttype.getTypename()+"("+producttype.getTypecode()+")");
	    	    producttypeComboBoxJson.add(producttypeMap);  
	    }  
	   return producttypeComboBoxJson;
	}  
	
	/**
     * 获取下拉列表框Json值
     */
	@ResponseBody //此标签表示返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	@RequestMapping(value="/getProducttypeCombogridJson")
	public List<Map<String,Object>> getProducttypeCombogridJson(Producttype form) {  
	    List<Producttype> producttypeList = producttypeDao.queryByList(form);
	    List<Map<String, Object>> producttypeCombogridJson = new ArrayList<Map<String, Object>>();
	    //封装属性结构信息
	    for(Producttype producttype:producttypeList){  
	    	    HashMap<String,Object> producttypeMap = new HashMap<String, Object>();
	    	    producttypeMap.put("id", producttype.getId());
	    	    producttypeMap.put("typecode", producttype.getTypecode());
	    	    producttypeMap.put("typename", producttype.getTypename());
	    	    producttypeCombogridJson.add(producttypeMap);  
	    }  
	   return producttypeCombogridJson;
	}  
	
	
}
