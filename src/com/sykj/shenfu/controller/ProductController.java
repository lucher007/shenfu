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
import com.sykj.shenfu.dao.IProductdepotDao;
import com.sykj.shenfu.dao.IProducttypeDao;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Product;
import com.sykj.shenfu.po.Productmodel;
import com.sykj.shenfu.po.Producttype;
import com.sykj.shenfu.service.IOperatorlogService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/product")
@Transactional
public class ProductController extends BaseController {

	@Autowired
	private IProductDao productDao;
	@Autowired
	private IProductdepotDao productdepotDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private IProducttypeDao producttypeDao;
	
	/**
	 * 查询产品
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Product form) {
		return "product/findProductList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Product form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		Integer total = productDao.findByCount(form);
		List<Product> list = productDao.findByList(form);
		
		result.put("total", total);//页面总数
		result.put("rows", list);
		return result;
	}

	/**
	 * 添加产品页面初始化
	 */
	@RequestMapping(value = "/addInit")
	public String addInit(Product form) {
		return "product/addProduct";
	}

	/**
	 * 保存添加产品
	 */
	@RequestMapping(value = "/save")
	public String save(Product form) {
		
		//添加产品的时候，默认库存量为0
		form.setDepotamount(0);
		
		if ("".equals(form.getProductcode())) {
			form.setReturninfo("请输入产品编码");
			return addInit(form);
		} else {
			Product product = productDao.findByProductcode(form.getProductcode());
			if (product != null) {
				form.setReturninfo("此产品编码已经存在");
				return addInit(form);
			}
		}
		
		Producttype producttype = producttypeDao.findByTypecode(form.getTypecode());  
		if(producttype == null){
			form.setReturninfo(getMessage("产品类别不存在"));
			return addInit(form);
		}
		
		form.setTypename(producttype.getTypename());
		
		productDao.save(form);
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "增加产品信息，产品名称:"+form.getProductname();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		
		form.setReturninfo("保存成功");
		
		return addInit(form);
	}

	/**
	 * 编辑产品权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Product form) {
		form.setProduct(productDao.findById(form.getId()));
		return "product/updateProduct";
	}

	/**
	 * 保存编辑后产品权限
	 */
	@RequestMapping(value = "/update")
	public String update(Product form) {
		if ("".equals(Tools.getStr(form.getProductcode()))) {
			form.setReturninfo("产品编码不能为空");
			return updateInit(form);
		} else {
			//判断材料编号是否重复
			Product oldProduct = productDao.findByProductcode(form.getProductcode());
			if (oldProduct != null && !oldProduct.getId().equals(form.getId())) {
				form.setReturninfo(getMessage("产品编码已经存在"));
				return updateInit(form);
			}
		}
		
		Producttype producttype = producttypeDao.findByTypecode(form.getTypecode());  
		if(producttype == null){
			form.setReturninfo(getMessage("产品类别不存在"));
			return updateInit(form);
		}
		
		form.setTypename(producttype.getTypename());
		
		productDao.update(form);
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "修改产品信息，产品名称:"+form.getProductname();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}

	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Product form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Product product = productDao.findById(form.getId());
		if(product != null){
				//删除产品列表
				productDao.delete(form.getId());
				
				//增加操作日记
				Operator operator = (Operator)getSession().getAttribute("Operator");
				String content = "删除产品信息，产品名称:"+product.getProductname();
				operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
				
				responseJson.put("flag", "1");//删除成功
				responseJson.put("msg", "删除成功");
			
		}else{
			responseJson.put("flag", "0");//删除成功
			responseJson.put("msg", "删除失败，该产品已经不存在");
		}
		
		
		//删除产品
		productDao.delete(form.getId());
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "删除产品信息，产品名称:"+product.getProductname();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
	/**
     * 获取下拉列表框Json值
     */
	@ResponseBody //此标签表示返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	@RequestMapping(value="/getProductComboBoxJson")
	public List<Map<String,Object>> getProductComboBoxJson(Product form) {  
	    List<Product> productList = productDao.queryByList(form);
	    List<Map<String, Object>> productComboBoxJson = new ArrayList<Map<String, Object>>();
	    //添加请选择项-默认为空，查询所有
	    HashMap<String,Object> selectMap = new HashMap<String, Object>();
	    selectMap.put("id", "");
	    selectMap.put("text", "请选择");
	    productComboBoxJson.add(selectMap);
	    
	    //封装区域属性结构信息
	    for(Product product:productList){  
	    	    HashMap<String,Object> productMap = new HashMap<String, Object>();
	    	    productMap.put("id", product.getProductcode());
	    	    productMap.put("text", product.getProductname() + "(" + product.getProductcode() + ")");
	    	    
	    	    productComboBoxJson.add(productMap);  
	    }  
	   return productComboBoxJson;
	}  
	
	/**
     * 获取下拉列表框Json值
     */
	@ResponseBody //此标签表示返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	@RequestMapping(value="/getProductCombogridJson")
	public List<Map<String,Object>> getProductCombogridJson(Product form) {  
	    List<Product> productList = productDao.queryByList(form);
	    List<Map<String, Object>> productCombogridJson = new ArrayList<Map<String, Object>>();
	    //封装属性结构信息
	    for(Product product:productList){  
	    	    HashMap<String,Object> productMap = new HashMap<String, Object>();
	    	    productMap.put("id", product.getId());
	    	    productMap.put("productcode", product.getProductcode());
	    	    productMap.put("productname", product.getProductname());
	    	    productMap.put("typecode", product.getTypecode());
	    	    productMap.put("typename", product.getTypename());
	    	    productMap.put("price", product.getPrice());
	    	    productMap.put("productsource", product.getProductsource());
	    	    productMap.put("productsourcename", product.getProductsourcename());
	    	    productMap.put("depotamount", product.getDepotamount());
	    	    productMap.put("productunit", product.getProductunit());
	    	    productCombogridJson.add(productMap);  
	    }  
	   return productCombogridJson;
	}  
	
}
