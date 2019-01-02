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

import com.sykj.shenfu.dao.IProductcolorDao;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Productcolor;
import com.sykj.shenfu.service.IOperatorlogService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/productcolor")
@Transactional
public class ProductcolorController extends BaseController {

	@Autowired
	private IProductcolorDao productcolorDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	
	/**
	 * 查询产品
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Productcolor form) {
		return "productcolor/findProductcolorList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Productcolor form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		Integer total = productcolorDao.findByCount(form);
		List<Productcolor> list = productcolorDao.findByList(form);
		
		result.put("total", total);//页面总数
		result.put("rows", list);
		return result;
	}

	/**
	 * 添加产品页面初始化
	 */
	@RequestMapping(value = "/addInit")
	public String addInit(Productcolor form) {
		return "productcolor/addProductcolor";
	}

	/**
	 * 保存添加产品
	 */
	@RequestMapping(value = "/save")
	public String save(Productcolor form) {
		
		productcolorDao.save(form);
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "增加产品颜色，颜色为:"+form.getColorname();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		
		form.setReturninfo("保存成功");
		
		return addInit(form);
	}

	/**
	 * 编辑产品权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Productcolor form) {
		form.setProductcolor(productcolorDao.findById(form.getId()));
		return "productcolor/updateProductcolor";
	}

	/**
	 * 保存编辑后产品权限
	 */
	@RequestMapping(value = "/update")
	public String update(Productcolor form) {
		productcolorDao.update(form);
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "修改产品颜色，颜色为:"+form.getColorname();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}

	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Productcolor form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Productcolor productcolor = productcolorDao.findById(form.getId());
		
		//删除产品
		productcolorDao.delete(form.getId());
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "删除产品颜色，颜色为:"+productcolor.getColorname();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
	/**
     * 获取材料的下拉列表框Json值
     */
	@ResponseBody //此标签表示返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	@RequestMapping(value="/getProductcolorComboBoxJson")
	public List<Map<String,Object>> getProductcolorComboBoxJson(Productcolor form) {  
	    List<Productcolor> productcolorList = productcolorDao.queryByList(form);
	    List<Map<String, Object>> productcolorComboBoxJson = new ArrayList<Map<String, Object>>();
	    //添加请选择项-默认为空，查询所有
	    HashMap<String,Object> selectMap = new HashMap<String, Object>();
	    selectMap.put("id", "");
	    selectMap.put("text", "请选择");
	    productcolorComboBoxJson.add(selectMap);
	    
	    //封装区域属性结构信息
	    for(Productcolor productcolor:productcolorList){
    	    HashMap<String,Object> productcolorMap = new HashMap<String, Object>();
    	    productcolorMap.put("id", productcolor.getColorcode());
    	    productcolorMap.put("text", productcolor.getColorname());
    	    productcolorComboBoxJson.add(productcolorMap);  
	    }  
	   return productcolorComboBoxJson;
	}  
	
}
