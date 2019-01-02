package com.sykj.shenfu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.IProductDao;
import com.sykj.shenfu.dao.IProductdepotDao;
import com.sykj.shenfu.dao.IProductmodelDao;
import com.sykj.shenfu.dao.IProductmodelrefDao;
import com.sykj.shenfu.dao.IProducttypeDao;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Productmodel;
import com.sykj.shenfu.po.Productmodelref;
import com.sykj.shenfu.po.Producttype;
import com.sykj.shenfu.service.IOperatorlogService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/productmodel")
@Transactional
public class ProductmodelController extends BaseController {

	@Autowired
	private IProductmodelDao productmodelDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private IProductDao productDao;
	@Autowired
	private IProductdepotDao productdepotDao;
	@Autowired
	private IProductmodelrefDao productmodelrefDao;
	@Autowired
	private IProducttypeDao producttypeDao;
	
	/**
	 * 查询产品
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Productmodel form) {
		return "productmodel/findProductmodelList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Productmodel form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		//封装List数据
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Integer total = productmodelDao.findByCount(form);
		List<Productmodel> list = productmodelDao.findByList(form);
		for (Productmodel productmodel : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			//潜在订户信息
			objectMap.put("id", productmodel.getId());
			objectMap.put("modelname", productmodel.getModelname());
			objectMap.put("modelcode", productmodel.getModelcode());
			objectMap.put("price", productmodel.getPrice());
			objectMap.put("lockcoreflag", productmodel.getLockcoreflag());
			objectMap.put("lockcoreflagname", productmodel.getLockcoreflagname());
			objectMap.put("content", Tools.getStr(productmodel.getContent()));
			objectMap.put("sellprice", productmodel.getSellprice());
			objectMap.put("starttime", StringUtils.isEmpty(productmodel.getStarttime())?"":Tools.getStr(productmodel.getStarttime()).substring(0, 19));
			objectMap.put("endtime", StringUtils.isEmpty(productmodel.getEndtime())?"":Tools.getStr(productmodel.getEndtime()).substring(0, 19));
			objectMap.put("status", productmodel.getStatus());
			objectMap.put("statusname", productmodel.getStatusname());
			objectMap.put("maxprice", productmodel.getMaxprice());
			objectMap.put("minprice", productmodel.getMinprice());
			objectMap.put("discountgain", productmodel.getDiscountgain());
			objectMap.put("fixedgain", productmodel.getFixedgain());
			objectMap.put("managergain", productmodel.getManagergain());
			objectMap.put("remark", Tools.getStr(productmodel.getRemark()));
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}

	/**
	 * 添加产品页面初始化
	 */
	@RequestMapping(value = "/addInit")
	public String addInit(Productmodel form) {
		return "productmodel/addProductmodel";
	}

	/**
	 * 保存添加产品
	 */
	@RequestMapping(value = "/save")
	public String save(Productmodel form) {
		
		if ("".equals(form.getModelcode())) {
			form.setReturninfo("请输入型号编码");
			return addInit(form);
		} else {
			Productmodel productmodel = productmodelDao.findByModelcode(form.getModelcode());
			if (productmodel != null) {
				form.setReturninfo("此型号编码已经存在");
				return addInit(form);
			}
		}
		
		//保存型号关联的产品类别信息
		String producttypeJson = form.getProducttypeJson();
		if(StringUtils.isEmpty(producttypeJson)){
			form.setReturninfo("请选择关联的产品类别");
			return addInit(form);
		}
		
		//保存产品型号
		productmodelDao.save(form);
		
		//增加关联的产品类别信息
		JSONArray producttypeJsonArry = JSONArray.fromObject(producttypeJson);
		for (int i = 0; i < producttypeJsonArry.size(); i++){
            JSONObject producttypeJsonObject = producttypeJsonArry.getJSONObject(i);
            String typecode = Tools.getStr(producttypeJsonObject.getString("typecode"));
            String typename = Tools.getStr(producttypeJsonObject.getString("typename"));
            String productcode = Tools.getStr(producttypeJsonObject.getString("productcode"));
            String productname = Tools.getStr(producttypeJsonObject.getString("productname"));
            Productmodelref productmodelref = new Productmodelref();
            productmodelref.setModelcode(form.getModelcode());
            productmodelref.setModelname(form.getModelname());
            productmodelref.setTypecode(typecode);
            productmodelref.setTypename(typename);
            productmodelref.setProductcode(productcode);
            productmodelref.setProductname(productname);
            productmodelrefDao.save(productmodelref);
		}
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "增加产品型号，型号为:"+form.getModelname();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		
		form.setReturninfo("保存成功");
		
		return addInit(form);
	}

	/**
	 * 编辑产品权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Productmodel form) {
		form.setProductmodel(productmodelDao.findById(form.getId()));
		return "productmodel/updateProductmodel";
	}

	/**
	 * 保存编辑后产品权限
	 */
	@RequestMapping(value = "/update")
	public String update(Productmodel form) {
		
		if ("".equals(Tools.getStr(form.getModelcode()))) {
			form.setReturninfo("产品型号编码不能为空");
			return updateInit(form);
		} else {
			//判断材料编号是否重复
			Productmodel oldProductmodel = productmodelDao.findByModelcode(form.getModelcode());
			if (oldProductmodel != null && !oldProductmodel.getId().equals(form.getId())) {
				form.setReturninfo(getMessage("产品型号编码已经存在"));
				return updateInit(form);
			}
		}
		
		if(form.getPrice() == null){
			form.setPrice(0);
		}
		
		if(form.getSellprice() == null){
			form.setSellprice(form.getPrice());
		}
		
		if(form.getMaxprice() == null){
			form.setMaxprice(form.getPrice());
		}
		
		if(form.getMinprice() == null){
			form.setMinprice(form.getPrice());
		}
		
		
		//保存型号关联的产品类别信息
		String producttypeJson = form.getProducttypeJson();
		if(StringUtils.isEmpty(producttypeJson)){
			form.setReturninfo("请选择关联的产品类别");
			return updateInit(form);
		}
		
		//修改产品型号信息
		productmodelDao.update(form);
		
		//删除产品型号关联的旧产品类别
		productmodelrefDao.deleteByModelcode(form.getModelcode());
		//添加新关联的产品类别
		JSONArray producttypeJsonArry = JSONArray.fromObject(producttypeJson);
		for (int i = 0; i < producttypeJsonArry.size(); i++){
            JSONObject producttypeJsonObject = producttypeJsonArry.getJSONObject(i);
            String typecode = Tools.getStr(producttypeJsonObject.getString("typecode"));
            String typename = Tools.getStr(producttypeJsonObject.getString("typename"));
            String productcode = Tools.getStr(producttypeJsonObject.getString("productcode"));
            String productname = Tools.getStr(producttypeJsonObject.getString("productname"));
            Productmodelref productmodelref = new Productmodelref();
            productmodelref.setModelcode(form.getModelcode());
            productmodelref.setModelname(form.getModelname());
            productmodelref.setTypecode(typecode);
            productmodelref.setTypename(typename);
            productmodelref.setProductcode(productcode);
            productmodelref.setProductname(productname);
            productmodelrefDao.save(productmodelref);
		}
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "修改产品型号，型号为:"+form.getModelname();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}

	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Productmodel form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Productmodel productmodel = productmodelDao.findById(form.getId());
		
		//删除产品信号
		productmodelDao.delete(form.getId());
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "删除产品型号，型号为:"+productmodel.getModelname();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
	/**
     * 获取材料的下拉列表框Json值
     */
	@ResponseBody //此标签表示返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	@RequestMapping(value="/getProductmodelComboBoxJson")
	public List<Map<String,Object>> getProductmodelComboBoxJson(Productmodel form) {  
	    List<Productmodel> productmodelList = productmodelDao.queryByList(form);
	    List<Map<String, Object>> productmodelComboBoxJson = new ArrayList<Map<String, Object>>();
	    //添加请选择项-默认为空，查询所有
	    HashMap<String,Object> selectMap = new HashMap<String, Object>();
	    selectMap.put("id", "");
	    selectMap.put("text", "请选择");
	    productmodelComboBoxJson.add(selectMap);
	    
	    //封装区域属性结构信息
	    for(Productmodel productmodel:productmodelList){  
	    	    HashMap<String,Object> productmodelMap = new HashMap<String, Object>();
	    	    productmodelMap.put("id", productmodel.getId());
	    	    productmodelMap.put("text", productmodel.getModelname());
	    	    productmodelComboBoxJson.add(productmodelMap);  
	    }  
	   return productmodelComboBoxJson;
	}  
	
	/**
     * 获取材料的下拉列表框Json值
     */
	@ResponseBody //此标签表示返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	@RequestMapping(value="/getProductmodelCombogridJson")
	public List<Map<String,Object>> getProductmodelCombogridJson(Productmodel form) {
		
	    List<Productmodel> productmodelList = productmodelDao.queryByList(form);
	    List<Map<String, Object>> productmodelCombogridJson = new ArrayList<Map<String, Object>>();
	   
	    //封装区域属性结构信息
	    for(Productmodel productmodel:productmodelList){  
	    	    HashMap<String,Object> productmodelMap = new HashMap<String, Object>();
	    	    productmodelMap.put("id", productmodel.getId());
	    	    productmodelMap.put("modelname", productmodel.getModelname());
	    	    productmodelMap.put("modelcode", productmodel.getModelcode());
	    	    productmodelMap.put("price", productmodel.getPrice());
	    	    productmodelMap.put("sellprice", productmodel.getSellprice());
	    	    productmodelMap.put("lockcoreflag", productmodel.getLockcoreflag());
	    	    productmodelMap.put("content", productmodel.getContent());
	    	    productmodelCombogridJson.add(productmodelMap);  
	    }  
	   return productmodelCombogridJson;
	}  
	
	/**
     * 初始化productmodel的下拉列表框值
     */
	@ResponseBody //此标签表示返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	@RequestMapping(value="/initProductmodelJson")
    public List<Productmodel> initProductmodelJson(Productmodel form) {
		//封装返回给页面的json对象
		HashMap<String,Object> productmodelJson = new HashMap<String,Object>();
        
        // 构建网络Map对象
		
 		List<Productmodel> productmodellist = productmodelDao.queryByList(form);
 		
 		//productmodelJson.put("productmodel", productmodellist);
 		
 		return productmodellist;
    }
	
	/**
	 * 查询型号将要关联的产品类别信息Json-添加修改产品型号用到
	 */
	@ResponseBody
	@RequestMapping(value = "/findProductmoderefListJson")
	public Map<String, Object> findProductmoderefListJson(Productmodel form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Integer total = producttypeDao.findByCount(new Producttype());
		List<Producttype> producttypelist = producttypeDao.queryByList(new Producttype());
		
		Productmodelref userlevelproductform = new Productmodelref();
		userlevelproductform.setModelcode(form.getModelcode());
		List<Productmodelref> productmodelreflist = productmodelrefDao.queryByList(userlevelproductform);
		
		for (Producttype producttype : producttypelist) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			//产品信息
			objectMap.put("id", producttype.getTypecode());
			objectMap.put("typecode", producttype.getTypecode());
			objectMap.put("typename", producttype.getTypename());
			objectMap.put("productcode", "");//初始化产品编号为空
			objectMap.put("productname", "");//初始化产品名称为空
			for (Productmodelref productmodelref : productmodelreflist) {
				if(productmodelref.getTypecode().equals(producttype.getTypecode())){
					objectMap.put("checkedFlag", "1");//默认选中
					objectMap.put("productcode", productmodelref.getProductcode());//初始化产品编号
					objectMap.put("productname", productmodelref.getProductname());//初始化产品名称
				}
			}
			
			objectlist.add(objectMap);
			
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}
	
	
	/**
	 * 查询型号已经包含的产品信息-添加和修改订单时使用
	 */
	@ResponseBody
	@RequestMapping(value = "/findProductOfModerefedListJson")
	public Map<String, Object> findProductOfModerefedListJson(Productmodel form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Productmodelref productmodelrefform = new Productmodelref();
		productmodelrefform.setModelcode(form.getModelcode());
		Integer total = productmodelrefDao.findByCount(productmodelrefform);
		List<Productmodelref> productmodelreflist = productmodelrefDao.queryByList(productmodelrefform);
		
		for (Productmodelref productmodelref : productmodelreflist) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			//产品信息
			objectMap.put("modelcode", productmodelref.getModelcode());
			objectMap.put("modelname", productmodelref.getModelname());
			objectMap.put("typecode", productmodelref.getTypecode());
			objectMap.put("typename", productmodelref.getTypename());
			objectMap.put("productcode", productmodelref.getProductcode());
			objectMap.put("productname", productmodelref.getProductname());
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}
	
	/**
	 * 查询型号已经包含的产品信息-添加和修改订单时使用
	 */
	@ResponseBody
	@RequestMapping(value = "/findVolidProducttypeOfModerefedListJson")
	public Map<String, Object> findVolidProducttypeOfModerefedListJson(Productmodel form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Productmodelref productmodelrefform = new Productmodelref();
		productmodelrefform.setModelcode(form.getModelcode());
		Integer total = productmodelrefDao.findByCount(productmodelrefform);
		List<Productmodelref> productmodelreflist = productmodelrefDao.queryByList(productmodelrefform);
		
		for (Productmodelref productmodelref : productmodelreflist) {
			if(StringUtils.isEmpty(productmodelref.getProductcode())){//把没有包含产品的列表封装发送给APP
				HashMap<String, Object> objectMap = new HashMap<String, Object>();
				//产品信息
				objectMap.put("modelcode", productmodelref.getModelcode());
				objectMap.put("modelname", productmodelref.getModelname());
				objectMap.put("typecode", productmodelref.getTypecode());
				objectMap.put("typename", productmodelref.getTypename());
				objectMap.put("productcode", productmodelref.getProductcode());
				objectMap.put("productname", productmodelref.getProductname());
				
				objectlist.add(objectMap);
			}
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}
	
}
