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
import com.sykj.shenfu.dao.IComponentdepotDao;
import com.sykj.shenfu.dao.IInstallcomponentDao;
import com.sykj.shenfu.dao.IMaterialbomDao;
import com.sykj.shenfu.dao.IEmployeeDao;
import com.sykj.shenfu.dao.IMaterialbomDao;
import com.sykj.shenfu.dao.IMaterialdepotDao;
import com.sykj.shenfu.dao.IMaterialinoutDao;
import com.sykj.shenfu.dao.IProducematerialDao;
import com.sykj.shenfu.dao.IProduceproductDao;
import com.sykj.shenfu.dao.IProductDao;
import com.sykj.shenfu.dao.IInstallinfoDao;
import com.sykj.shenfu.po.Componentdepot;
import com.sykj.shenfu.po.Installcomponent;
import com.sykj.shenfu.po.Materialbom;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Materialbom;
import com.sykj.shenfu.po.Materialdepot;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Installinfo;
import com.sykj.shenfu.po.Producematerial;
import com.sykj.shenfu.service.ICoderuleService;
import com.sykj.shenfu.service.IComponentdepotService;
import com.sykj.shenfu.service.IMaterialdepotService;
import com.sykj.shenfu.service.IOperatorlogService;
import com.sykj.shenfu.service.IInstallinfoService;
import com.sykj.shenfu.service.IProductdepotService;
import com.sykj.shenfu.service.ISystemparaService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/installinfo")
@Transactional
public class InstallinfoController extends BaseController {

	@Autowired
	private IInstallinfoDao installinfoDao;
	@Autowired
	private IProduceproductDao produceproductDao;
	@Autowired
	private IInstallcomponentDao installcomponentDao;
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
	private IMaterialbomDao materialbomDao;
	@Autowired
	private ICoderuleService coderuleService;
	@Autowired
	private IProductdepotService productdepotService;
	@Autowired
	private IMaterialdepotService materialdepotService;
	@Autowired
	private ISystemparaService systemparaService;
	@Autowired
	private IInstallinfoService installinfoService;
	@Autowired
	private IComponentdepotService componentdepotService;
	@Autowired
	private IComponentdepotDao componentdepotDao;
	/**
	 * 查询客户支付
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Installinfo form) {
		return "installinfo/findInstallinfoList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Installinfo form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Integer total = installinfoDao.findByCount(form);
		List<Installinfo> list = installinfoDao.findByList(form);
		for (Installinfo installinfo : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			//客户信息
			objectMap.put("id", installinfo.getId());
			objectMap.put("producecode", installinfo.getProducecode());
			objectMap.put("materialcode", installinfo.getMaterialcode());
			objectMap.put("materialname", installinfo.getMaterialname());
			objectMap.put("amount", installinfo.getAmount());
			objectMap.put("installinformation", installinfo.getInstallinformation());
			objectMap.put("materialversion", installinfo.getMaterialversion());
			objectMap.put("producercode", installinfo.getProducercode());
			if(StringUtils.isNotEmpty(installinfo.getProducercode())){
				Employee producer = employeeDao.findByEmployeecodeStr(installinfo.getProducercode());
				if(producer == null){
					producer = new Employee();
				}
				objectMap.put("producername", producer.getEmployeename());
				objectMap.put("producerphone", producer.getPhone());
			}
			
			objectMap.put("operatorcode", installinfo.getOperatorcode());
			if(StringUtils.isNotEmpty(installinfo.getOperatorcode())){
				Employee operator = employeeDao.findByEmployeecodeStr(installinfo.getOperatorcode());
				if(operator == null){
					operator = new Employee();
				}
				objectMap.put("operatorname", operator.getEmployeename());
				objectMap.put("operatorphone", operator.getPhone());
			}
			
			objectMap.put("addtime", StringUtils.isEmpty(installinfo.getAddtime())?"":Tools.getStr(installinfo.getAddtime()).substring(0, 19));
			objectMap.put("remark", installinfo.getRemark());
			
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
	public String addInit(Installinfo form) {
		//组装信息
		String produce_installinfo = Tools.getStr(systemparaService.findSystemParaByCodeStr("produce_installinfo"));
		form.setInstallinformation(produce_installinfo);
		
		//产品版本号
		String produce_materialversion = Tools.getStr(systemparaService.findSystemParaByCodeStr("produce_materialversion"));
		form.setMaterialversion(produce_materialversion);
		
		return "installinfo/addInstallinfo";
	}

	/**
	 * 保存添加客户支付
	 */
	@RequestMapping(value = "/save")
	public String save(Installinfo form) {
		Map<String, Object> resultHp = installinfoService.saveInstallinfo(form);
		form.setReturninfo(resultHp.get("result").toString());
		return addInit(form);
	}
  
	
	/**
	 * 查看 订单权限初始化页面
	 */
	@RequestMapping(value = "/lookInit")
	public String lookInit(Installinfo form) {
		Installinfo installinfo = installinfoDao.findById(form.getId());
		
		//封装生产负责人
		Employee producer = employeeDao.findByEmployeecodeStr(installinfo.getProducercode());
		if(producer == null){
			producer = new Employee();
		}
		installinfo.setProducername(producer.getEmployeename());
		
		form.setInstallinfo(installinfo);
		return "installinfo/lookInstallinfo";
	}
	
	/**
	 * 编辑客户支付权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Installinfo form) {
		Installinfo installinfo = installinfoDao.findById(form.getId());
		if(StringUtils.isNotEmpty(installinfo.getProducercode())){
			Employee producer = employeeDao.findByEmployeecodeStr(installinfo.getProducercode());
			if(producer != null){
				installinfo.setProducername(producer.getEmployeename());
			}
		}
		
		form.setInstallinfo(installinfo);
		
		return "installinfo/updateInstallinfo";
	}
    
	/**
	 * 更新
	 */
	@RequestMapping(value = "/update")
	public String update(Installinfo form) {
        
		if ("".equals(form.getProducercode())) {
			form.setReturninfo("生产负责人不能为空");
			return addInit(form);
		} 

		// 修改网络信息
		installinfoDao.update(form);
        
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "修改生产组装信息，生产批次号为:"+form.getProducecode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("修改成功");
		
		return updateInit(form);
		
		
	}
	
	
	/**
	 * 查询型号已经包含的材料信息-添加和修改订单时使用
	 */
	@ResponseBody
	@RequestMapping(value = "/findBomListJson")
	public Map<String, Object> findBomListJson(Installinfo form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Materialbom bomform = new Materialbom();
		bomform.setMaterialcode(form.getMaterialcode());
		Integer total = materialbomDao.findByCount(bomform);
		List<Materialbom> bomlist = materialbomDao.queryByList(bomform);
		
		Componentdepot componentdepotForm = new Componentdepot();
		componentdepotForm.setDepotstatus("1");//有库存
		HashMap<String,ArrayList<Componentdepot>> componentdepotHp = componentdepotService.findComponentdepotHashmap(componentdepotForm);
		
		for (Materialbom bom : bomlist) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			//产品信息
			objectMap.put("materialcode", bom.getMaterialcode());
			objectMap.put("materialname", bom.getMaterialname());
			objectMap.put("componentcode", bom.getComponentcode());
			objectMap.put("componentname", bom.getComponentname());
			objectMap.put("componentmodel", bom.getComponentmodel());
			objectMap.put("amount", bom.getAmount());
			objectMap.put("totalamount", bom.getAmount()*form.getAmount());//材料总数=单套所属材料数乘以产品生产套数
			
			//自动找出库存中符合出库条件的最早的批次号
			componentdepotForm.setComponentcode(bom.getComponentcode());
			componentdepotForm.setInoutamount(bom.getAmount()*form.getAmount());
			Componentdepot componentdepotOldest = componentdepotService.findComponentdepotOldest(componentdepotHp, componentdepotForm);
			if(componentdepotOldest != null){
				objectMap.put("batchno", componentdepotOldest.getBatchno());
				objectMap.put("depotamount", componentdepotOldest.getDepotamount());
				objectMap.put("depotrackcode", componentdepotOldest.getDepotrackcode());
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
	@RequestMapping(value="/getComponentdepotComboBoxJson")
	public List<Map<String,Object>> getComponentdepotComboBoxJson(Componentdepot form) {  
	    List<Componentdepot> componentdepotList = componentdepotDao.queryByList(form);
	    List<Map<String, Object>> objectComboBoxJson = new ArrayList<Map<String, Object>>();
	   
	    //封装区域属性结构信息
	    for(Componentdepot componentdepot:componentdepotList){  
	    	    HashMap<String,Object> objectMap = new HashMap<String, Object>();
	    	    objectMap.put("id", componentdepot.getBatchno());
	    	    objectMap.put("text", componentdepot.getBatchno()+"_"+ componentdepot.getDepotamount()+"_"+componentdepot.getDepotrackcode());
	    	    
	    	    objectComboBoxJson.add(objectMap);  
	    }  
	   return objectComboBoxJson;
	}  
	
	
	/**
	 * 核对材料库存量是否满足
	 * @return
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value="/checkComponentdepotamount")
	public Map<String,Object> checkComponentdepotamount(Componentdepot form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Componentdepot componentdepot = componentdepotDao.findByBatchno(form.getBatchno());
		
		responseJson.put("flag", "0");//失败
		
		if(componentdepot == null){
			responseJson.put("returninfo", "库存中不存在该批次号！");
		}else if(!(form.getComponentcode().equals(componentdepot.getComponentcode()))){
			responseJson.put("returninfo", "此批次号不是"+form.getComponentname());
		}else if(!"1".equals(componentdepot.getDepotstatus())){
			responseJson.put("returninfo", "此批次号不是库存状态！");
		}else if(componentdepot.getDepotamount().intValue() < form.getInoutamount().intValue()){
			responseJson.put("returninfo", "此批次库存量不够！");
		}else{
			responseJson.put("flag", "1");//成功
		}
		return responseJson;
	}
	
	/**
	 * 查询生产元器件信息JSON
	 */
	@ResponseBody
	@RequestMapping(value = "/findInstallcomponentJson")
	public Map<String, Object> findInstallcomponentJson(Installcomponent form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		form.setSort("componentcode");
		form.setOrder("asc");
		Integer total = installcomponentDao.findCountByProducecode(form);
		List<Installcomponent> installcomponentlist = installcomponentDao.queryListByProducecode(form);
		for (Installcomponent installcomponent : installcomponentlist) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			//产品信息
			objectMap.put("materialcode", installcomponent.getMaterialcode());
			objectMap.put("materialname", installcomponent.getMaterialname());
			objectMap.put("componentcode", installcomponent.getComponentcode());
			objectMap.put("componentname", installcomponent.getComponentname());
			objectMap.put("componentmodel", installcomponent.getComponentmodel());
			objectMap.put("amount", installcomponent.getAmount());
			objectMap.put("totalamount", installcomponent.getTotalamount());//总数=单套所属数乘以生产套数
			objectMap.put("batchno", installcomponent.getBatchno());
			objectMap.put("depotrackcode", installcomponent.getDepotrackcode());
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}
}
