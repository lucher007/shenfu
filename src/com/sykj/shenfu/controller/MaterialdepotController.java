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
import com.sykj.shenfu.dao.IMaterialDao;
import com.sykj.shenfu.dao.IMaterialdepotDao;
import com.sykj.shenfu.dao.IMaterialinoutDao;
import com.sykj.shenfu.po.Coderule;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Material;
import com.sykj.shenfu.po.Materialdepot;
import com.sykj.shenfu.po.Materialinout;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Productdepot;
import com.sykj.shenfu.po.Productinout;
import com.sykj.shenfu.service.ICoderuleService;
import com.sykj.shenfu.service.IMaterialdepotService;
import com.sykj.shenfu.service.IOperatorlogService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/materialdepot")
@Transactional
public class MaterialdepotController extends BaseController {

	@Autowired
	private IMaterialdepotDao materialdepotDao;
	@Autowired
	private IMaterialdepotService materialdepotService;
	@Autowired
	private IMaterialDao materialDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private ICoderuleService coderuleService;
	@Autowired
	private IMaterialinoutDao materialinoutDao;
	
	/*
     * 特殊的instance变量
     * 注：零长度的byte数组对象创建起来将比任何对象都经济――查看编译后的字节码：
     * 生成零长度的byte[]对象只需3条操作码，而Object lock = new Object()则需要7行操作码。
     */
    private static byte[] lock = new byte[0];
	
	/**
	 * 查询材料库存
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Materialdepot form) {
		return "materialdepot/findMaterialdepotList";
	}
	
	/**
	 * 查询材料库存Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Materialdepot form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Integer total = materialdepotDao.findByCount(form);
		List<Materialdepot> list = materialdepotDao.findByList(form);
		for (Materialdepot materialdepot : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("id", materialdepot.getId());
			objectMap.put("materialcode", materialdepot.getMaterialcode());
			objectMap.put("materialname", materialdepot.getMaterialname());
			objectMap.put("operatorcode", materialdepot.getOperatorcode());
			//操作员信息
			Employee operator = employeeDao.findByEmployeecodeStr(materialdepot.getOperatorcode());
			if(operator != null){
				objectMap.put("operatorname", operator.getEmployeename());
				objectMap.put("operatorphone", operator.getPhone());
			}
			objectMap.put("batchno", materialdepot.getBatchno());
			objectMap.put("depotamount", materialdepot.getDepotamount());
			objectMap.put("addtime", StringUtils.isEmpty(materialdepot.getAddtime())?"":Tools.getStr(materialdepot.getAddtime()).substring(0, 19));
			objectMap.put("inoutercode", materialdepot.getInoutercode());
			//入库负责人
			Employee inputer = employeeDao.findByEmployeecodeStr(materialdepot.getInoutercode());
			if(inputer != null){
				objectMap.put("inoutername", inputer.getEmployeename());
				objectMap.put("inouterphone", inputer.getPhone());
			}
			objectMap.put("depotstatus", materialdepot.getDepotstatus());
			objectMap.put("depotstatusname", materialdepot.getDepotstatusname());
			objectMap.put("depotrackcode", materialdepot.getDepotrackcode());
			objectMap.put("alarmdepotamount", materialdepot.getAlarmdepotamount());
			objectMap.put("suppliername", materialdepot.getSuppliername());
			objectMap.put("materialidentfy", materialdepot.getMaterialidentfy());
			objectMap.put("materialsource", materialdepot.getMaterialsource());
			objectMap.put("materialsourcename", materialdepot.getMaterialsourcename());
			//生产负责人
			objectMap.put("producercode", materialdepot.getProducercode());
			Employee producer = employeeDao.findByEmployeecodeStr(materialdepot.getProducercode());
			if(producer != null){
				objectMap.put("producername", producer.getEmployeename());
				objectMap.put("producerphone", producer.getPhone());
			}
			objectMap.put("materialstatus", materialdepot.getMaterialstatus());
			objectMap.put("materialstatusname", materialdepot.getMaterialstatusname());
			objectMap.put("installinfo", Tools.getStr(materialdepot.getInstallinfo()));
			objectMap.put("materialversion",Tools.getStr(materialdepot.getMaterialversion()));
			objectMap.put("remark", materialdepot.getRemark());
			
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}
    
	/**
	 * 材料入库
	 */
	@RequestMapping(value = "/indepot")
	public String indepot(Materialdepot form) {
		return "materialdepot/indepot";
	}
	
	/**
	 * 保存材料入库
	 */
	@RequestMapping(value = "/saveIndepot")
	public String saveIndepot(Materialdepot form) {
		
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String currenttime = Tools.getCurrentTime();
		Material material = materialDao.findByMaterialcodeStr(form.getMaterialcode());
		if(material == null){
			form.setReturninfo("入库失败，该材料不存在");
			return indepot(form);
		}
		form.setMaterialname(material.getMaterialname());
		//获取材料批次号
		String batchno = materialdepotService.getMaterialbatchno(form.getMaterialcode());
		//封装材料批次号
		form.setBatchno(batchno);
		//默认有库存
		form.setDepotstatus("1");
		//入库时间
		form.setAddtime(currenttime);
		//操作员
		form.setOperatorcode(operator.getEmployeecode());
		//操作员
		form.setMaterialsource("1");
		//材料状态
		form.setMaterialstatus("1");
		
		//材料入库保存
		materialdepotDao.save(form);
		
		//增加材料入库记录
		Materialinout materialinout = new Materialinout();
		materialinout.setMaterialcode(form.getMaterialcode());
		materialinout.setMaterialname(form.getMaterialname());
		materialinout.setOperatorcode(operator.getEmployeecode());
		materialinout.setBatchno(form.getBatchno());
		materialinout.setType("0");
		materialinout.setInoutamount(form.getDepotamount());
		materialinout.setAddtime(currenttime);
		materialinout.setInoutercode(form.getInoutercode());
		materialinout.setInoutstatus("1");
		materialinout.setDepotrackcode(form.getDepotrackcode());
		//入库记录保存
		materialinoutDao.save(materialinout);
		
		//增加操作日记
		//Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "材料入库，材料名称为:"+form.getMaterialname()+";入库量为："+form.getDepotamount();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("入库成功");
		
		return indepot(form);
	}
	
	/**
	 * 材料出库
	 */
	@RequestMapping(value = "/outdepot")
	public String outdepot(Materialdepot form) {
		form.setMaterialdepot(materialdepotDao.findById(form.getId()));
		return "materialdepot/addOutdepot";
	}
	
	/**
	 * 保存材料出库
	 */
	@RequestMapping(value = "/saveOutdepot")
	public String saveOutdepot(Materialdepot form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		//加锁
		synchronized(lock) {
			Materialdepot materialdepot = materialdepotDao.findById(form.getId());
			
			if(materialdepot == null){
				form.setReturninfo("出库失败，此产品在库存中不存在！");
				return outdepot(form);
			}
			
			if(!"1".equals(materialdepot.getDepotstatus())){
				form.setReturninfo("出库失败，此产品不是库存状态！");
				return outdepot(form);
			}
			
			if(!"1".equals(materialdepot.getMaterialstatus())){
				form.setReturninfo("出库失败，此产品不是正常状态！");
				return outdepot(form);
			}
			
			//修改该产品库存量
			//已有库存量
			int depotamount = materialdepot.getDepotamount();
			//本次出库量
			int inoutamount = form.getInoutamount();
			materialdepot.setDepotamount(depotamount-inoutamount);
			if(depotamount-inoutamount < 0){
				form.setReturninfo("出库失败，出库数量不能大于库存量！");
				return outdepot(form);
			}
			if(depotamount-inoutamount == 0){
				materialdepot.setDepotstatus("0"); //无库存
			}
			
			materialdepotDao.update(materialdepot);
			
			//增加产品出库记录
			Materialinout materialinout = new Materialinout();
			materialinout.setMaterialcode(form.getMaterialcode());
			materialinout.setMaterialname(form.getMaterialname());
			materialinout.setOperatorcode(operator.getEmployeecode());
			materialinout.setBatchno(form.getBatchno());
			materialinout.setMaterialidentfy(materialdepot.getMaterialidentfy());
			materialinout.setType("1");
			materialinout.setInoutamount(inoutamount);
			materialinout.setAddtime(Tools.getCurrentTime());
			materialinout.setInoutercode(form.getInoutercode());
			materialinout.setInoutstatus("1");
			materialinout.setDepotrackcode(materialdepot.getDepotrackcode());
			materialinoutDao.save(materialinout);
			
			//增加操作日记
			//Operator operator = (Operator)getSession().getAttribute("Operator");
			String content = "材料出库，材料名称为:"+form.getMaterialname()+"; 出库量为："+ inoutamount + "; 材料批次号为："+ form.getBatchno();
			if(StringUtils.isNotEmpty(materialdepot.getMaterialidentfy())){
				content = content + "; 材料SN码为："+ materialdepot.getMaterialidentfy();
			}
			operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
			
			form.setReturninfo("出库成功");
				
		}
		return outdepot(form);
	}
	
	/**
	 * 手动修改库存量
	 */
	@RequestMapping(value = "/updateDepotamountInit")
	public String updateDepotamountInit(Materialdepot form) {
		form.setMaterialdepot(materialdepotDao.findById(form.getId()));
		return "materialdepot/updateDepotamount";
	}
	
	/**
	 * 保存库存量的修改
	 */
	@RequestMapping(value = "/updateDepotamount")
	public String updateDepotamount(Materialdepot form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		//加锁
		synchronized(lock) {
			//查询材料库存不存在此种产品
			Materialdepot depot = materialdepotDao.findById(form.getId());
			
			if(depot == null){
				form.setReturninfo("此批次号不存在！");
				return updateDepotamountInit(form);
			}
			
			if(StringUtils.isNotEmpty(depot.getMaterialidentfy())){
				form.setReturninfo("此库存为组装生产入库，不能手动修改库存量！");
				return updateDepotamountInit(form);
			}
			
			//修改该产品库存量
			//已有库存量
			int depotamount = depot.getDepotamount();
			//本次入库量
			int inoutamount = form.getInoutamount();
			depot.setDepotamount(depotamount+inoutamount);
			
			if(depotamount+inoutamount > 0){
				depot.setDepotstatus("1"); //有库存
			}
			
			materialdepotDao.update(depot);
			
			//增加产品入库记录
			Materialinout materialinout = new Materialinout();
			materialinout.setMaterialcode(form.getMaterialcode());
			materialinout.setMaterialname(form.getMaterialname());
			materialinout.setOperatorcode(operator.getEmployeecode());
			materialinout.setBatchno(form.getBatchno());
			materialinout.setType("0"); //入库
			materialinout.setInoutamount(inoutamount);
			materialinout.setAddtime(Tools.getCurrentTime());
			materialinout.setInoutercode(form.getInoutercode());//操作员为负责人
			materialinout.setInoutstatus("1");
			materialinout.setDepotrackcode(depot.getDepotrackcode());
			
			materialinoutDao.save(materialinout);
			
			//增加操作日记
			//Operator operator = (Operator)getSession().getAttribute("Operator");
			String content = "同批次入库，材料名称为:"+form.getMaterialname()+";入库量为："+ inoutamount;
			operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
			
			form.setReturninfo("修改成功");
			return updateDepotamountInit(form);
		}
	}
	
	/**
	 * 编辑产品权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Materialdepot form) {
		form.setMaterialdepot(materialdepotDao.findById(form.getId()));
		return "materialdepot/updateMaterialdepot";
	}

	/**
	 * 保存编辑后产品权限
	 */
	@RequestMapping(value = "/update")
	public String update(Materialdepot form) {
		materialdepotDao.update(form);
		form.setReturninfo("修改成功");
		return updateInit(form);
	}

	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Materialdepot form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		//删除产品
		materialdepotDao.delete(form.getId());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
	
	/**
     * 获取下拉列表框Json值
     */
	@ResponseBody //此标签表示返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	@RequestMapping(value="/getMaterialdepotComboBoxJson")
	public List<Map<String,Object>> getMaterialdepotComboBoxJson(Materialdepot form) { 
	    List<Materialdepot> materialdepotList = materialdepotDao.queryByList(form);
	    List<Map<String, Object>> objectComboBoxJson = new ArrayList<Map<String, Object>>();
	    //添加请选择项-默认为空，查询所有
	    //HashMap<String,Object> selectMap = new HashMap<String, Object>();
	    //selectMap.put("id", "");
	    //selectMap.put("text", "请选择");
	    //productComboBoxJson.add(selectMap);
	    
	    //封装属性结构信息
	    for(Materialdepot materialdepot:materialdepotList){
	    	    HashMap<String,Object> objectMap = new HashMap<String, Object>();
	    	    objectMap.put("id", materialdepot.getBatchno());
	    	    objectMap.put("text", materialdepot.getBatchno() + "(" + materialdepot.getMaterialname() + ")");
	    	    
	    	    objectComboBoxJson.add(objectMap);  
	    }  
	   return objectComboBoxJson;
	}  
	
	/**
	 * 修改材料信息初始化页面
	 */
	@RequestMapping(value = "/updateMaterialstatusInit")
	public String updateMaterialstatusInit(Materialdepot form) {
		form.setMaterialdepot(materialdepotDao.findById(form.getId()));
		return "materialdepot/updateMaterialstatus";
	}

	/**
	 * 保存修改材料信息
	 */
	@RequestMapping(value = "/updateMaterialstatus")
	public String updateMaterialstatus(Materialdepot form) {
		Materialdepot materialdepot = materialdepotDao.findById(form.getId());
		materialdepot.setMaterialstatus(form.getMaterialstatus());
		materialdepot.setMaterialversion(form.getMaterialversion());
		materialdepotDao.update(materialdepot);
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "修改材料信息，材料名称:"+form.getMaterialname()+";材料批次号:"+form.getBatchno();
		if(StringUtils.isNotEmpty(materialdepot.getMaterialidentfy())){
			content = content + "; 材料SN码为："+ materialdepot.getMaterialidentfy();
		}
		
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("修改成功");
		
		return updateMaterialstatusInit(form);
	}
}
