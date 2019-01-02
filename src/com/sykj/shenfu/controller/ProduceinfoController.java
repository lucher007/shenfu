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

import com.sykj.shenfu.common.ConfigUtil;
import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.IBomDao;
import com.sykj.shenfu.dao.IEmployeeDao;
import com.sykj.shenfu.dao.IMaterialDao;
import com.sykj.shenfu.dao.IMaterialdepotDao;
import com.sykj.shenfu.dao.IMaterialinoutDao;
import com.sykj.shenfu.dao.IProducematerialDao;
import com.sykj.shenfu.dao.IProduceproductDao;
import com.sykj.shenfu.dao.IProductDao;
import com.sykj.shenfu.dao.IProduceinfoDao;
import com.sykj.shenfu.po.Bom;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Materialdepot;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Produceinfo;
import com.sykj.shenfu.po.Producematerial;
import com.sykj.shenfu.service.ICoderuleService;
import com.sykj.shenfu.service.IMaterialdepotService;
import com.sykj.shenfu.service.IOperatorlogService;
import com.sykj.shenfu.service.IProduceinfoService;
import com.sykj.shenfu.service.IProductdepotService;
import com.sykj.shenfu.service.ISystemparaService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/produceinfo")
@Transactional
public class ProduceinfoController extends BaseController {

	@Autowired
	private IProduceinfoDao produceinfoDao;
	@Autowired
	private IProduceproductDao produceproductDao;
	@Autowired
	private IProducematerialDao producematerialDao;
	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private IProductDao productDao;
	@Autowired
	private IMaterialdepotDao materialdepotDao;
	@Autowired
	private IMaterialinoutDao materialinoutDao;
	@Autowired
	private IMaterialDao materialDao;
	@Autowired
	private IBomDao bomDao;
	@Autowired
	private ICoderuleService coderuleService;
	@Autowired
	private IProductdepotService productdepotService;
	@Autowired
	private IMaterialdepotService materialdepotService;
	@Autowired
	private ISystemparaService systemparaService;
	@Autowired
	private IProduceinfoService produceinfoService;
	
	/**
	 * 查询客户支付
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Produceinfo form) {
		return "produceinfo/findProduceinfoList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Produceinfo form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Integer total = produceinfoDao.findByCount(form);
		List<Produceinfo> list = produceinfoDao.findByList(form);
		for (Produceinfo produceinfo : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			//客户信息
			objectMap.put("id", produceinfo.getId());
			objectMap.put("producecode", produceinfo.getProducecode());
			objectMap.put("productcode", produceinfo.getProductcode());
			objectMap.put("productname", produceinfo.getProductname());
			objectMap.put("amount", produceinfo.getAmount());
			objectMap.put("installinfo", produceinfo.getInstallinfo());
			objectMap.put("productversion", produceinfo.getProductversion());
			objectMap.put("producercode", produceinfo.getProducercode());
			if(StringUtils.isNotEmpty(produceinfo.getProducercode())){
				Employee producer = employeeDao.findByEmployeecodeStr(produceinfo.getProducercode());
				if(producer == null){
					producer = new Employee();
				}
				objectMap.put("producername", producer.getEmployeename());
				objectMap.put("producerphone", producer.getPhone());
			}
			
			objectMap.put("operatorcode", produceinfo.getOperatorcode());
			if(StringUtils.isNotEmpty(produceinfo.getOperatorcode())){
				Employee operator = employeeDao.findByEmployeecodeStr(produceinfo.getOperatorcode());
				if(operator == null){
					operator = new Employee();
				}
				objectMap.put("operatorname", operator.getEmployeename());
				objectMap.put("operatorphone", operator.getPhone());
			}
			
			objectMap.put("addtime", StringUtils.isEmpty(produceinfo.getAddtime())?"":Tools.getStr(produceinfo.getAddtime()).substring(0, 19));
			objectMap.put("remark", produceinfo.getRemark());
			
			objectlist.add(objectMap);
			
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		
		return result;
	}

	/**
	 * 添加客户支付页面初始化
	 */
	@RequestMapping(value = "/addInit")
	public String addInit(Produceinfo form) {
		//组装信息
		String produce_installinfo = Tools.getStr(systemparaService.findSystemParaByCodeStr("produce_installinfo"));
		form.setInstallinfo(produce_installinfo);
		
		//产品版本号
		String produce_productversion = Tools.getStr(systemparaService.findSystemParaByCodeStr("produce_productversion"));
		form.setProductversion(produce_productversion);
		
		return "produceinfo/addProduceinfo";
	}

	/**
	 * 保存添加客户支付
	 */
	@RequestMapping(value = "/save")
	public String save(Produceinfo form) {
		Map<String, Object> resultHp = produceinfoService.saveProduceinfo(form);
		form.setReturninfo(resultHp.get("result").toString());
		return addInit(form);
	}
  
	
	/**
	 * 查看 订单权限初始化页面
	 */
	@RequestMapping(value = "/lookInit")
	public String lookInit(Produceinfo form) {
		Produceinfo produceinfo = produceinfoDao.findById(form.getId());
		
		//封装生产负责人
		Employee producer = employeeDao.findByEmployeecodeStr(produceinfo.getProducercode());
		if(producer == null){
			producer = new Employee();
		}
		produceinfo.setProducername(producer.getEmployeename());
		
		form.setProduceinfo(produceinfo);
		return "produceinfo/lookProduceinfo";
	}
	
	/**
	 * 编辑客户支付权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Produceinfo form) {
		Produceinfo produceinfo = produceinfoDao.findById(form.getId());
		if(StringUtils.isNotEmpty(produceinfo.getProducercode())){
			Employee producer = employeeDao.findByEmployeecodeStr(produceinfo.getProducercode());
			if(producer != null){
				produceinfo.setProducername(producer.getEmployeename());
			}
		}
		
		form.setProduceinfo(produceinfo);
		
		return "produceinfo/updateProduceinfo";
	}
    
	/**
	 * 更新
	 */
	@RequestMapping(value = "/update")
	public String update(Produceinfo form) {
        
		if ("".equals(form.getProducercode())) {
			form.setReturninfo("生产负责人不能为空");
			return addInit(form);
		} 

		// 修改网络信息
		produceinfoDao.update(form);
        
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "修改生产组装信息，生产批次号为:"+form.getProducecode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("修改成功");
		
		return updateInit(form);
		
		
	}
	
	
	/**
	 * 查询型号已经包含的产品信息-添加和修改订单时使用
	 */
	@ResponseBody
	@RequestMapping(value = "/findBomListJson")
	public Map<String, Object> findBomListJson(Produceinfo form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Bom bomform = new Bom();
		bomform.setProductcode(form.getProductcode());
		Integer total = bomDao.findByCount(bomform);
		List<Bom> bomlist = bomDao.queryByList(bomform);
		
		Materialdepot materialdepotForm = new Materialdepot();
		materialdepotForm.setDepotstatus("1");//有库存
		materialdepotForm.setMaterialstatus("1");//正常
		HashMap<String,ArrayList<Materialdepot>> materialdepotHp = materialdepotService.findMaterialdepotHashmap(materialdepotForm);
		
		for (Bom bom : bomlist) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			//产品信息
			objectMap.put("productcode", bom.getProductcode());
			objectMap.put("productname", bom.getProductname());
			objectMap.put("materialcode", bom.getMaterialcode());
			objectMap.put("materialname", bom.getMaterialname());
			objectMap.put("amount", bom.getAmount());
			objectMap.put("totalamount", bom.getAmount()*form.getAmount());//材料总数=单套所属材料数乘以产品生产套数
			
			//自动找出库存中符合出库条件的最早的批次号
			materialdepotForm.setMaterialcode(bom.getMaterialcode());
			materialdepotForm.setInoutamount(bom.getAmount()*form.getAmount());
			Materialdepot materialdepotOldest = materialdepotService.findMaterialdepotOldest(materialdepotHp, materialdepotForm);
			if(materialdepotOldest != null){
				objectMap.put("batchno", materialdepotOldest.getBatchno());
				objectMap.put("depotamount", materialdepotOldest.getDepotamount());
				objectMap.put("depotrackcode", materialdepotOldest.getDepotrackcode());
				objectMap.put("result", "1");
			}
			
			
			objectlist.add(objectMap);
			
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}
	
	
	/**
     * 获取下拉列表框Json值
     */
	@ResponseBody //此标签表示返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	@RequestMapping(value="/getMaterialdepotComboBoxJson")
	public List<Map<String,Object>> getMaterialdepotComboBoxJson(Materialdepot form) {
	    List<Materialdepot> materialdepotList = materialdepotDao.queryByList(form);
	    List<Map<String, Object>> objectComboBoxJson = new ArrayList<Map<String, Object>>();
	   
	    //封装区域属性结构信息
	    for(Materialdepot materialdepot:materialdepotList){  
	    	    HashMap<String,Object> objectMap = new HashMap<String, Object>();
	    	    objectMap.put("id", materialdepot.getBatchno());
	    	    objectMap.put("text", materialdepot.getBatchno()+"_"+ materialdepot.getDepotamount()+"_"+materialdepot.getDepotrackcode());
	    	    
	    	    objectComboBoxJson.add(objectMap);  
	    }  
	   return objectComboBoxJson;
	}  
	
	/**
     * 根据批次号分组进行查询
     */
	@ResponseBody //此标签表示返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	@RequestMapping(value="/getMaterialdepotComboBoxJsonGroupByMaterialcode")
	public List<Map<String,Object>> getMaterialdepotComboBoxJsonGroupByMaterialcode(Materialdepot form) {
	    List<Materialdepot> materialdepotList = materialdepotDao.queryMatarialListGroupByBatchno(form);
	    List<Map<String, Object>> objectComboBoxJson = new ArrayList<Map<String, Object>>();
	   
	    //封装区域属性结构信息
	    for(Materialdepot materialdepot:materialdepotList){
	    	    HashMap<String,Object> objectMap = new HashMap<String, Object>();
	    	    objectMap.put("id", materialdepot.getBatchno());
	    	    objectMap.put("text", materialdepot.getBatchno()+"_"+ materialdepot.getDepotamount()+"_"+materialdepot.getDepotrackcode());
	    	    
	    	    objectComboBoxJson.add(objectMap);  
	    }  
	   return objectComboBoxJson;
	}  
	
	
	/**
	 * 核对材料库存量是否满足
	 * @return
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value="/checkMaterialdepotamount")
	public Map<String,Object> checkMaterialdepotamount(Materialdepot form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		form.setDepotstatus("1");
		form.setMaterialstatus("1");
		Materialdepot materialdepot = materialdepotDao.findMatarialGroupByBatchno(form);
		responseJson.put("flag", "0");//失败
		if(materialdepot == null){
			responseJson.put("returninfo", "库存中不存在该批次号！");
		}else if(!(form.getMaterialcode().equals(materialdepot.getMaterialcode()))){
			responseJson.put("returninfo", "此批次号不是"+form.getMaterialname());
		}else if(!"1".equals(materialdepot.getDepotstatus())){
			responseJson.put("returninfo", "此批次号不是库存状态！");
		}else if(!"1".equals(materialdepot.getMaterialstatus())){
			responseJson.put("returninfo", "此批次号不是正常状态！");
		}else if(materialdepot.getDepotamount().intValue() < form.getInoutamount().intValue()){
			responseJson.put("returninfo", "此批次库存量不够！");
		}else{
			responseJson.put("flag", "1");//成功
		}
		return responseJson;
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
		Integer total = producematerialDao.findCountByProducecode(form);
		List<Producematerial> producemateriallist = producematerialDao.queryListByProducecode(form);
		for (Producematerial producematerial : producemateriallist) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			//产品信息
			objectMap.put("productcode", producematerial.getProductcode());
			objectMap.put("productname", producematerial.getProductname());
			objectMap.put("materialcode", producematerial.getMaterialcode());
			objectMap.put("materialname", producematerial.getMaterialname());
			objectMap.put("amount", producematerial.getAmount());
			objectMap.put("totalamount", producematerial.getTotalamount());//材料总数=单套所属材料数乘以产品生产套数
			objectMap.put("batchno", producematerial.getBatchno());
			objectMap.put("depotrackcode", producematerial.getDepotrackcode());
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}
}
