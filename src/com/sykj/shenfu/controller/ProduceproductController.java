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
import com.sykj.shenfu.dao.IProduceinfoDao;
import com.sykj.shenfu.dao.IProducematerialDao;
import com.sykj.shenfu.dao.IProduceproductDao;
import com.sykj.shenfu.dao.IProductDao;
import com.sykj.shenfu.dao.IProductdepotDao;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Produceinfo;
import com.sykj.shenfu.po.Producematerial;
import com.sykj.shenfu.po.Produceproduct;
import com.sykj.shenfu.po.Product;
import com.sykj.shenfu.po.Productdepot;
import com.sykj.shenfu.service.IOperatorlogService;
import com.sykj.shenfu.service.IProductdepotService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/produceproduct")
@Transactional
public class ProduceproductController extends BaseController {

	@Autowired
	private IProduceproductDao produceproductDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private IProductDao productDao;
	@Autowired
	private IProduceinfoDao produceinfoDao;
	@Autowired
	private IProductdepotDao productdepotDao;
	@Autowired
	private IProducematerialDao producematerialDao;
	@Autowired
	private IProductdepotService productdepotService;
	
	/**
	 * 查询客户支付
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Produceproduct form) {
		return "produceproduct/findProduceproductList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Produceproduct form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Integer total = produceproductDao.findByCount(form);
		List<Produceproduct> list = produceproductDao.findByList(form);
		for (Produceproduct produceproduct : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			//客户信息
			objectMap.put("id", produceproduct.getId());
			objectMap.put("producecode", produceproduct.getProducecode());
			objectMap.put("productcode", produceproduct.getProductcode());
			objectMap.put("productname", produceproduct.getProductname());
			objectMap.put("productidentfy", produceproduct.getProductidentfy());
			objectMap.put("depotamount", produceproduct.getDepotamount());
			objectMap.put("productstatus", produceproduct.getProductstatus());
			objectMap.put("productstatusname", produceproduct.getProductstatusname());
			objectMap.put("printflag", produceproduct.getPrintflagname());
			objectMap.put("printflagname", produceproduct.getPrintflag());
			objectMap.put("addtime", StringUtils.isEmpty(produceproduct.getAddtime())?"":Tools.getStr(produceproduct.getAddtime()).substring(0, 19));
			objectMap.put("remark", produceproduct.getRemark());
			
			Produceinfo produceinfo = produceinfoDao.findByProducecode(produceproduct.getProducecode());
			if(produceinfo != null){
				objectMap.put("installinfo", produceinfo.getInstallinfo());
				objectMap.put("productversion", produceinfo.getProductversion());
			}
			
			objectlist.add(objectMap);
			
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		
		return result;
	}
	
	/**
	 * 添加客户支付页面初始化
	 */
	@RequestMapping(value = "/indepotInit")
	public String indepotInit(Produceproduct form) {
		Produceproduct produceproduct = produceproductDao.findByProductidentfy(form.getProductidentfy());
		form.setProduceproduct(produceproduct);
		return "produceproduct/indepot";
	}

	/**
	 * 保存添加客户支付
	 */
	@RequestMapping(value = "/saveIndepot")
	public String saveIndepot(Produceproduct form) {
		
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		Productdepot productdepotform =  productdepotDao.findByProductidentfy(form.getProductidentfy());
		if(productdepotform != null){
			form.setReturninfo("入库失败，该产品SN码已经存在库存中");
			return indepotInit(form);
		}
		
		//修改生产产品的状态为已经入库
		Produceproduct produceproduct = produceproductDao.findByProductidentfy(form.getProductidentfy());
		produceproduct.setProductstatus("3");
		produceproductDao.update(produceproduct);
		
		//当前时间
		String currenttime = Tools.getCurrentTime();
		
		//生产信息
		Produceinfo produceinfo = produceinfoDao.findByProducecode(produceproduct.getProducecode());
		
		//产品信息
		Product product = productDao.findByProductcode(produceproduct.getProductcode());
		
		//增加产品入库
		Productdepot productdepot = new Productdepot();
		productdepot.setTypecode(product.getTypecode());
		productdepot.setTypename(product.getTypename());
		productdepot.setProductcode(produceproduct.getProductcode());
		productdepot.setProductname(produceproduct.getProductname());
		productdepot.setProductsource("0");//自产
		productdepot.setProductidentfy(produceproduct.getProductidentfy());
		productdepot.setDepotamount(produceproduct.getDepotamount());
		productdepot.setProducercode(produceinfo.getProducercode());
		productdepot.setProductstatus("1");//正常
		productdepot.setCheckstatus("0");
		productdepot.setAddtime(currenttime);
		productdepot.setDepotstatus("1");//有库存
		productdepot.setDepotrackcode(form.getDepotrackcode());
		productdepot.setInstallinfo(produceinfo.getInstallinfo());
		productdepot.setOperatorcode(operator.getEmployeecode());
		productdepot.setInoutercode(form.getInoutercode());
		productdepot.setProductversion(produceinfo.getProductversion());
		
		//产品入库
		productdepotService.saveProductdepot_instor(productdepot);
		
		//增加操作日记
		//Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "产品组装入库，产品名称:"+productdepot.getProductname()+";产品标识号:"+productdepot.getProductidentfy();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("组装入库成功");
		return indepotInit(form);
	}
	
	/**
	 * 查看 订单权限初始化页面
	 */
	@RequestMapping(value = "/lookInit")
	public String lookInit(Produceproduct form) {
		Produceproduct produceproduct = produceproductDao.findByProductidentfy(form.getProductidentfy());
		form.setProduceproduct(produceproduct);
		return "produceproduct/lookProduceproduct";
	}
	
	/**
	 * 查询生产材料信息JSON
	 */
	@ResponseBody
	@RequestMapping(value = "/findProducematerailJson")
	public Map<String, Object> findProducematerailJson(Producematerial form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		form.setSort("materialcode");
		form.setOrder("asc");
		Integer total = producematerialDao.findByCount(form);
		List<Producematerial> producemateriallist = producematerialDao.queryByList(form);
		for (Producematerial producematerial : producemateriallist) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			//产品信息
			objectMap.put("productcode", producematerial.getProductcode());
			objectMap.put("productname", producematerial.getProductname());
			objectMap.put("materialcode", producematerial.getMaterialcode());
			objectMap.put("materialname", producematerial.getMaterialname());
			objectMap.put("amount", producematerial.getAmount());
			objectMap.put("batchno", producematerial.getBatchno());
			objectMap.put("materialidentfy", producematerial.getMaterialidentfy());
			objectMap.put("depotrackcode", producematerial.getDepotrackcode());
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}
}
