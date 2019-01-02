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
import com.sykj.shenfu.dao.IProductDao;
import com.sykj.shenfu.dao.IProductdepotDao;
import com.sykj.shenfu.dao.IProductinoutDao;
import com.sykj.shenfu.dao.IProductrepairDao;
import com.sykj.shenfu.po.Coderule;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Product;
import com.sykj.shenfu.po.Productdepot;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Productinout;
import com.sykj.shenfu.po.Productmodel;
import com.sykj.shenfu.po.Userorder;
import com.sykj.shenfu.po.Userorderinfo;
import com.sykj.shenfu.po.Productrepair;
import com.sykj.shenfu.service.ICoderuleService;
import com.sykj.shenfu.service.IOperatorlogService;
import com.sykj.shenfu.service.IProductdepotService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/productdepot")
@Transactional
public class ProductdepotController extends BaseController {

	@Autowired
	private IProductdepotDao productdepotDao;
	@Autowired
	private IProductdepotService productdepotService;
	@Autowired
	private IProductDao productDao;
	@Autowired
	private IOperatorDao operatorDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private IProductinoutDao productinoutDao;
	@Autowired
	private ICoderuleService coderuleService;
	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private IProductrepairDao productrepairDao;
	
	/*
     * 特殊的instance变量
     * 注：零长度的byte数组对象创建起来将比任何对象都经济――查看编译后的字节码：
     * 生成零长度的byte[]对象只需3条操作码，而Object lock = new Object()则需要7行操作码。
     */
    private static byte[] lock = new byte[0];   
	
	/**
	 * 查询产品库存
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Productdepot form) {
		return "productdepot/findProductdepotList";
	}
	
	/**
	 * 查询产品库存Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Productdepot form) {
		//封装JSon的Map
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Integer total = productdepotDao.findByCount(form);
		List<Productdepot> list = productdepotDao.findByList(form);
		for (Productdepot productdepot : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			
			objectMap.put("id", productdepot.getId());
			objectMap.put("typecode", productdepot.getTypecode());
			objectMap.put("typename", productdepot.getTypename());
			objectMap.put("typename", productdepot.getTypename());
			objectMap.put("productcode", productdepot.getProductcode());
			objectMap.put("productname", productdepot.getProductname());
			objectMap.put("productsource", productdepot.getProductsource());
			objectMap.put("productsourcename", productdepot.getProductsourcename());
			objectMap.put("productidentfy", productdepot.getProductidentfy());
			objectMap.put("depotamount", productdepot.getDepotamount());
			objectMap.put("checkercode", productdepot.getCheckercode());
			objectMap.put("producercode", productdepot.getProducercode());
			objectMap.put("productstatus", productdepot.getProductstatus());
			objectMap.put("productstatusname", productdepot.getProductstatusname());
			objectMap.put("checkstatus", productdepot.getCheckstatus());
			objectMap.put("addtime", StringUtils.isEmpty(productdepot.getAddtime())?"":Tools.getStr(productdepot.getAddtime()).substring(0, 19));
			objectMap.put("depotstatus", productdepot.getDepotstatus());
			objectMap.put("depotstatusname", productdepot.getDepotstatusname());
			objectMap.put("depotrackcode", productdepot.getDepotrackcode());
			objectMap.put("suppliername", Tools.getStr(productdepot.getSuppliername()));
			objectMap.put("installinfo", Tools.getStr(productdepot.getInstallinfo()));
			objectMap.put("operatorcode", productdepot.getOperatorcode());
			//操作员信息
			Employee operator = employeeDao.findByEmployeecodeStr(productdepot.getOperatorcode());
			if(operator != null){
				objectMap.put("operatorname", operator.getEmployeename());
				objectMap.put("operatorphone", operator.getPhone());
			}
			objectMap.put("inoutercode", productdepot.getInoutercode());
			//入库负责人
			Employee inputer = employeeDao.findByEmployeecodeStr(productdepot.getInoutercode());
			if(inputer != null){
				objectMap.put("inoutername", inputer.getEmployeename());
				objectMap.put("inouterphone", inputer.getPhone());
			}
			
			objectMap.put("productversion", productdepot.getProductversion());
			
			objectlist.add(objectMap);
			
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}
    
	/**
	 * 产品入库
	 */
	@RequestMapping(value = "/indepot")
	public String indepot(Productdepot form) {
		return "productdepot/indepot";
	}
	
	/**
	 * 保存产品入库
	 */
	@RequestMapping(value = "/saveIndepot")
	public String saveIndepot(Productdepot form) {
		
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String currenttime = Tools.getCurrentTime();
		
        //入库的产品信息
		Product product = productDao.findByProductcode(form.getProductcode());
		if(product == null){
			form.setReturninfo("入库失败，入库的产品不存在");
			return indepot(form);
		}
		
		//自产的产品，具有唯一的识别号
		if("0".equals(product.getProductsource())){
			if(form.getDepotamount() != 1){//自产的产品一次只能入库一个，因为产品标识唯一
				form.setReturninfo("入库失败，自产的产品不能一次入库多个，会造成产品识别号不唯一");
				return indepot(form);
			}
		}else{
			if(form.getDepotamount() < 1){//外购的产品入库数量不能
				form.setReturninfo("入库失败，入库数量不正确");
				return indepot(form);
			}
		}
		
		//产品识别号
		String productidentfy = productdepotService.getProductidentfy(form.getProductcode());
				
		//组装产品唯一标识
		form.setProductidentfy(productidentfy);
		form.setTypecode(product.getTypecode());
		form.setTypename(product.getTypename());
		form.setProductcode(product.getProductcode());
		form.setProductname(product.getProductname());
		form.setProductsource(product.getProductsource());
		form.setProductstatus("1");
		form.setAddtime(currenttime);
		form.setDepotstatus("1");
		form.setOperatorcode(operator.getEmployeecode());
		
		//产品入库
		productdepotService.saveProductdepot_instor(form);
		
		//增加操作日记
		//Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "产品入库，产品名称:"+form.getProductname()+";产品标识号:"+form.getProductidentfy();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("入库成功");
		
		return indepot(form);
	}
    
	
	/**
	 * 产品出库
	 */
	@RequestMapping(value = "/outdepot")
	public String outdepot(Productdepot form) {
		return "productdepot/outdepot";
	}
	
	/**
	 * 产品出库
	 */
	@RequestMapping(value = "/addOutdepotInit")
	public String addOutdepotInit(Productdepot form) {
		form.setProductdepot(productdepotDao.findByProductidentfy(form.getProductidentfy()));
		return "productdepot/addOutdepot";
	}
	
	/**
	 * 保存产品出库
	 */
	@RequestMapping(value = "/saveOutdepot")
	public String saveOutdepot(Productdepot form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		//加锁
		synchronized(lock) {
			//查询产品库 存不存在此种产品
			Productdepot depot = productdepotDao.findByProductidentfy(form.getProductidentfy());
			if(depot != null){//有库存
				if("1".equals(depot.getProductstatus())){
					//修改该产品库存量
					//已有库存量
					int depotamount = depot.getDepotamount();
					//本次出库量
					int inoutamount = form.getInoutamount();
					depot.setDepotamount(depotamount-inoutamount);
					if(depotamount-inoutamount < 0){
						form.setReturninfo("出库数量不能大于库存量！");
						return addOutdepotInit(form);
					}
					if(depotamount-inoutamount == 0){
						depot.setDepotstatus("0"); //无库存
					}
					
					productdepotDao.update(depot);
					
					//增加产品出库记录
					Productinout productinout = new Productinout();
					productinout.setProductcode(depot.getProductcode());
					productinout.setProductname(depot.getProductname());
					productinout.setProductidentfy(depot.getProductidentfy());
					productinout.setType("1");
					productinout.setInoutercode(form.getInoutercode());
					productinout.setInoutamount(form.getInoutamount());
					productinout.setAddtime(Tools.getCurrentTime());
					productinout.setOperatorcode(operator.getEmployeecode());
					productinout.setInoutstatus("1");
					productinout.setDepotrackcode(form.getDepotrackcode());
					
					productinoutDao.save(productinout);
					
					//增加操作日记
					//Operator operator = (Operator)getSession().getAttribute("Operator");
					String content = "产品出库，产品名称:"+form.getProductname()+";产品标识号:"+form.getProductidentfy() + ";出库量:"+inoutamount;
					operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
					
					form.setReturninfo("出库成功");
				}else{ 
					form.setReturninfo("此产品不是库存状态！");
				}
			}else{
				form.setReturninfo("此产品在库存中不存在！");
			}
		}
		return addOutdepotInit(form);
	}
	
	/**
	 * 产品出库
	 */
	@RequestMapping(value = "/updateDepotamountInit")
	public String updateDepotamountInit(Productdepot form) {
		form.setProductdepot(productdepotDao.findByProductidentfy(form.getProductidentfy()));
		return "productdepot/updateDepotamount";
	}
	
	/**
	 * 保存产品出库
	 */
	@RequestMapping(value = "/updateDepotamount")
	public String updateDepotamount(Productdepot form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		//加锁
		synchronized(lock) {
			//查询产品库 存不存在此种产品
			Productdepot depot = productdepotDao.findByProductidentfy(form.getProductidentfy());
			if(depot == null){
				form.setReturninfo("此产品在库存中不存在！");
				return updateDepotamountInit(form);
			}
			
			//修改该产品库存量
			//已有库存量
			int depotamount = depot.getDepotamount();
			//本次出库量
			int inoutamount = form.getInoutamount();
			depot.setDepotamount(depotamount+inoutamount);
			if(depotamount+inoutamount > 0){
				depot.setProductstatus("1"); //无库存
			}
			
			productdepotDao.update(depot);
			
			//增加产品入库记录
			Productinout productinout = new Productinout();
			productinout.setProductcode(depot.getProductcode());
			productinout.setProductname(depot.getProductname());
			productinout.setProductidentfy(depot.getProductidentfy());
			productinout.setType("0"); //入库
			productinout.setInoutercode(operator.getEmployeecode());
			productinout.setInoutamount(form.getInoutamount());
			productinout.setAddtime(Tools.getCurrentTime());
			productinout.setOperatorcode(operator.getEmployeecode());
			productinout.setInoutstatus("1");
			productinout.setDepotrackcode(form.getDepotrackcode());
			
			productinoutDao.save(productinout);
			
			//增加操作日记
			//Operator operator = (Operator)getSession().getAttribute("Operator");
			String content = "产品入库，产品名称:"+form.getProductname()+";产品标识号:"+form.getProductidentfy() + ";入库量:"+inoutamount;
			operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
			
			form.setReturninfo("入库成功");
			return updateDepotamountInit(form);
		}
	}
	
	
	/**
	 * 编辑产品权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Productdepot form) {
		form.setProductdepot(productdepotDao.findById(form.getId()));
		return "productdepot/updateProductdepot";
	}

	/**
	 * 保存编辑后产品权限
	 */
	@RequestMapping(value = "/update")
	public String update(Productdepot form) {
		productdepotDao.update(form);
		form.setReturninfo("修改成功");
		return updateInit(form);
	}

	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Productdepot form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		//删除产品
		productdepotDao.delete(form.getId());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
	
	/**
     * 获取下拉列表框Json值
     */
	@ResponseBody //此标签表示返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	@RequestMapping(value="/getProductdepotComboBoxJson")
	public List<Map<String,Object>> getProductdepotComboBoxJson(Productdepot form) { 
	    List<Productdepot> productdepotList = productdepotDao.queryByList(form);
	    List<Map<String, Object>> objectComboBoxJson = new ArrayList<Map<String, Object>>();
	    //添加请选择项-默认为空，查询所有
	    //HashMap<String,Object> selectMap = new HashMap<String, Object>();
	    //selectMap.put("id", "");
	    //selectMap.put("text", "请选择");
	    //productComboBoxJson.add(selectMap);
	    
	    //封装属性结构信息
	    for(Productdepot productdepot:productdepotList){
	    	    HashMap<String,Object> objectMap = new HashMap<String, Object>();
	    	    objectMap.put("id", productdepot.getProductidentfy());
	    	    objectMap.put("text", productdepot.getProductidentfy() + "(" + productdepot.getProductname() + ")");
	    	    
	    	    objectComboBoxJson.add(objectMap);  
	    }  
	   return objectComboBoxJson;
	}  
	
	/**
	 * 订单打包
     * 获取下拉列表框Json值
     */
	@ResponseBody //此标签表示返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	@RequestMapping(value="/getProductdepotComboBoxJsonForPack")
	public List<Map<String,Object>> getProductdepotComboBoxJsonForPack(Productdepot form) { 
	    List<Productdepot> productdepotList = productdepotDao.queryByList(form);
	    List<Map<String, Object>> objectComboBoxJson = new ArrayList<Map<String, Object>>();
	    //添加请选择项-默认为空，查询所有
	    //HashMap<String,Object> selectMap = new HashMap<String, Object>();
	    //selectMap.put("id", "");
	    //selectMap.put("text", "请选择");
	    //productComboBoxJson.add(selectMap);
	    
	    //封装属性结构信息
	    for(Productdepot productdepot:productdepotList){
	    	    HashMap<String,Object> objectMap = new HashMap<String, Object>();
	    	    objectMap.put("id", productdepot.getProductidentfy());
	    	    objectMap.put("text", productdepot.getProductidentfy() + "_" + productdepot.getDepotamount() + "_" + productdepot.getDepotrackcode());
	    	    
	    	    objectComboBoxJson.add(objectMap);  
	    }  
	   return objectComboBoxJson;
	}  
	
	/**
	 * 查询产品库存信息
	 */
	@RequestMapping(value = "/findProductdepotListDialog")
	public String findProductdepotListDialog(Productdepot form) {
		return "productdepot/findProductdepotListDialog";
	}
	
	/**
	 * 修改产品信息初始化页面
	 */
	@RequestMapping(value = "/updateProductstatusInit")
	public String updateProductstatusInit(Productdepot form) {
		form.setProductdepot(productdepotDao.findByProductidentfy(form.getProductidentfy()));
		return "productdepot/updateProductstatus";
	}

	/**
	 * 保存修改产品信息
	 */
	@RequestMapping(value = "/updateProductstatus")
	public String updateProductstatus(Productdepot form) {
		Productdepot productdepot = productdepotDao.findByProductidentfy(form.getProductidentfy());
		productdepot.setProductstatus(form.getProductstatus());
		productdepot.setProductversion(form.getProductversion());
		productdepotDao.update(productdepot);
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "修改产品信息，产品名称:"+form.getProductname()+";产品标识号:"+form.getProductidentfy();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("修改产品信息成功");
		
		return updateProductstatusInit(form);
	}
	
	/**
	 * 产品维修初始化页面
	 */
	@RequestMapping(value = "/saveProductrepairInit")
	public String saveProductrepairInit(Productdepot form) {
		Productdepot productdepot = productdepotDao.findByProductidentfy(form.getProductidentfy());
		
		form.setProductdepot(productdepot);
		return "productdepot/repairProduct";
	}
	
	/**
	 * 保存产品维修
	 */
	@RequestMapping(value = "/saveProductrepair")
	public String saveProductrepair(Productdepot form) {
		
		//操作员
		Operator operator = (Operator)getSession().getAttribute("Operator");

		//Map<String, Object> responseMap = productdepotService.saveProductrepair(form, getRequest(), operator);
		
		Productdepot productdepot = productdepotDao.findByProductidentfy(form.getProductidentfy());
		
		Productrepair productrepair = new Productrepair();
		productrepair.setTypecode(productdepot.getTypecode());
		productrepair.setTypename(productdepot.getTypename());
		productrepair.setProductcode(productdepot.getProductcode());
		productrepair.setProductname(productdepot.getProductname());
		productrepair.setProductidentfy(form.getProductidentfy());
		productrepair.setAddtime(Tools.getCurrentTime());
		productrepair.setOperatorcode(operator.getEmployeecode());
		productrepair.setProductproblem(getRequest().getParameter("productproblem"));
		productrepair.setRepairinfo(getRequest().getParameter("repairinfo"));
		productrepair.setRepairstatus(getRequest().getParameter("repairstatus"));
		//保存产品更换记录
		productrepairDao.save(productrepair);
		
		if("1".equals(getRequest().getParameter("repairstatus"))){//已维修完成，需要修改产品状态
			productdepot.setProductstatus("1");//正常
			productdepotDao.update(productdepot);
		}
		
		//增加操作日记
		String content = "维修 产品信息， 产品识别号为:" + form.getProductidentfy();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("保存成功");
		
		return saveProductrepairInit(form);
	}
}
