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
import com.sykj.shenfu.dao.IComponentDao;
import com.sykj.shenfu.dao.IComponentdepotDao;
import com.sykj.shenfu.dao.IComponentinoutDao;
import com.sykj.shenfu.po.Coderule;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Component;
import com.sykj.shenfu.po.Componentdepot;
import com.sykj.shenfu.po.Componentinout;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Productdepot;
import com.sykj.shenfu.po.Productinout;
import com.sykj.shenfu.service.ICoderuleService;
import com.sykj.shenfu.service.IComponentdepotService;
import com.sykj.shenfu.service.IOperatorlogService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/componentdepot")
@Transactional
public class ComponentdepotController extends BaseController {

	@Autowired
	private IComponentdepotDao componentdepotDao;
	@Autowired
	private IComponentdepotService componentdepotService;
	@Autowired
	private IComponentDao componentDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private ICoderuleService coderuleService;
	@Autowired
	private IComponentinoutDao componentinoutDao;
	
	/*
     * 特殊的instance变量
     * 注：零长度的byte数组对象创建起来将比任何对象都经济――查看编译后的字节码：
     * 生成零长度的byte[]对象只需3条操作码，而Object lock = new Object()则需要7行操作码。
     */
    private static byte[] lock = new byte[0];
	
	/**
	 * 查询元器件库存
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Componentdepot form) {
		return "componentdepot/findComponentdepotList";
	}
	
	/**
	 * 查询元器件库存Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Componentdepot form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Integer total = componentdepotDao.findByCount(form);
		List<Componentdepot> list = componentdepotDao.findByList(form);
		for (Componentdepot componentdepot : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("componentcode", componentdepot.getComponentcode());
			objectMap.put("componentname", componentdepot.getComponentname());
			objectMap.put("componentmodel", componentdepot.getComponentmodel());
			objectMap.put("operatorcode", componentdepot.getOperatorcode());
			//操作员信息
			Employee operator = employeeDao.findByEmployeecodeStr(componentdepot.getOperatorcode());
			if(operator != null){
				objectMap.put("operatorname", operator.getEmployeename());
				objectMap.put("operatorphone", operator.getPhone());
			}
			objectMap.put("batchno", componentdepot.getBatchno());
			objectMap.put("depotamount", componentdepot.getDepotamount());
			objectMap.put("addtime", StringUtils.isEmpty(componentdepot.getAddtime())?"":Tools.getStr(componentdepot.getAddtime()).substring(0, 19));
			objectMap.put("inoutercode", componentdepot.getInoutercode());
			//入库负责人
			Employee inputer = employeeDao.findByEmployeecodeStr(componentdepot.getInoutercode());
			if(inputer != null){
				objectMap.put("inoutername", inputer.getEmployeename());
				objectMap.put("inouterphone", inputer.getPhone());
			}
			objectMap.put("depotstatus", componentdepot.getDepotstatus());
			objectMap.put("depotstatusname", componentdepot.getDepotstatusname());
			objectMap.put("depotrackcode", componentdepot.getDepotrackcode());
			objectMap.put("alarmdepotamount", componentdepot.getAlarmdepotamount());
			objectMap.put("suppliername", componentdepot.getSuppliername());
			objectMap.put("remark", componentdepot.getRemark());
			
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}
    
	/**
	 * 元器件入库
	 */
	@RequestMapping(value = "/indepot")
	public String indepot(Componentdepot form) {
		return "componentdepot/indepot";
	}
	
	/**
	 * 保存元器件入库
	 */
	@RequestMapping(value = "/saveIndepot")
	public String saveIndepot(Componentdepot form) {
		
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String currenttime = Tools.getCurrentTime();
		Component component = componentDao.findByComponentcode(form.getComponentcode());
		if(component == null){
			form.setReturninfo("入库失败，该元器件不存在");
			return indepot(form);
		}
		form.setComponentname(component.getComponentname());
		form.setComponentmodel(component.getComponentmodel());
		//获取元器件批次号
		String batchno = componentdepotService.getComponentbatchno(form.getComponentcode());
		//封装元器件批次号
		form.setBatchno(batchno);
		//默认有库存
		form.setDepotstatus("1");
		//入库时间
		form.setAddtime(currenttime);
		//操作员
		form.setOperatorcode(operator.getEmployeecode());
		//元器件入库保存
		componentdepotDao.save(form);
		
		//增加元器件入库记录
		Componentinout componentinout = new Componentinout();
		componentinout.setComponentcode(form.getComponentcode());
		componentinout.setComponentname(form.getComponentname());
		componentinout.setComponentmodel(form.getComponentmodel());
		componentinout.setOperatorcode(operator.getEmployeecode());
		componentinout.setBatchno(form.getBatchno());
		componentinout.setType("0");
		componentinout.setInoutamount(form.getDepotamount());
		componentinout.setAddtime(currenttime);
		componentinout.setInoutercode(form.getInoutercode());
		componentinout.setInoutstatus("1");
		componentinout.setDepotrackcode(form.getDepotrackcode());
		//入库记录保存
		componentinoutDao.save(componentinout);
		
		//增加操作日记
		//Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "元器件入库，元器件名称为:"+form.getComponentname()+";入库量为："+form.getDepotamount();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("入库成功");
		
		return indepot(form);
	}
	
	/**
	 * 元器件出库
	 */
	@RequestMapping(value = "/outdepot")
	public String outdepot(Componentdepot form) {
		form.setComponentdepot(componentdepotDao.findByBatchno(form.getBatchno()));
		return "componentdepot/addOutdepot";
	}
	
	/**
	 * 保存元器件出库
	 */
	@RequestMapping(value = "/saveOutdepot")
	public String saveOutdepot(Componentdepot form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		//加锁
		synchronized(lock) {
			//查询元器件库 存不存在此种产品
			Componentdepot depot = componentdepotDao.findByBatchno(form.getBatchno());
			if(depot != null){//有库存
				if("1".equals(depot.getDepotstatus())){
					//修改该产品库存量
					//已有库存量
					Integer depotamount = depot.getDepotamount();
					//本次出库量
					Integer inoutamount = form.getInoutamount();
					depot.setDepotamount(depotamount-inoutamount);
					if(depotamount-inoutamount < 0){
						form.setReturninfo("出库数量不能大于库存量！");
						return outdepot(form);
					}
					if(depotamount-inoutamount == 0){
						depot.setDepotstatus("0"); //无库存
					}
					
					componentdepotDao.update(depot);
					
					//增加产品出库记录
					Componentinout componentinout = new Componentinout();
					componentinout.setComponentcode(form.getComponentcode());
					componentinout.setComponentname(form.getComponentname());
					componentinout.setComponentmodel(form.getComponentmodel());
					componentinout.setOperatorcode(operator.getEmployeecode());
					componentinout.setBatchno(form.getBatchno());
					componentinout.setType("1");
					componentinout.setInoutamount(inoutamount);
					componentinout.setAddtime(Tools.getCurrentTime());
					componentinout.setInoutercode(form.getInoutercode());
					componentinout.setInoutstatus("1");
					componentinout.setDepotrackcode(depot.getDepotrackcode());
					
					componentinoutDao.save(componentinout);
					
					//增加操作日记
					//Operator operator = (Operator)getSession().getAttribute("Operator");
					String content = "元器件出库，元器件名称为:"+form.getComponentname()+";出库量为："+ inoutamount;
					operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
					
					form.setReturninfo("入库成功");
					
					
					form.setReturninfo("出库成功");
				}else{ 
					form.setReturninfo("此产品不是库存状态！");
				}
			}else{
				form.setReturninfo("此产品在库存中不存在！");
			}
		}
		return outdepot(form);
	}
	
	/**
	 * 手动修改库存量
	 */
	@RequestMapping(value = "/updateDepotamountInit")
	public String updateDepotamountInit(Componentdepot form) {
		form.setComponentdepot(componentdepotDao.findByBatchno(form.getBatchno()));
		return "componentdepot/updateDepotamount";
	}
	
	/**
	 * 保存库存量的修改
	 */
	@RequestMapping(value = "/updateDepotamount")
	public String updateDepotamount(Componentdepot form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		//加锁
		synchronized(lock) {
			//查询元器件库存不存在此种产品
			Componentdepot depot = componentdepotDao.findByBatchno(form.getBatchno());
			
			if(depot == null){
				form.setReturninfo("此批次号不存在！");
				return updateDepotamountInit(form);
			}
		
			//修改该产品库存量
			//已有库存量
			Integer depotamount = depot.getDepotamount();
			//本次入库量
			Integer inoutamount = form.getInoutamount();
			depot.setDepotamount(depotamount+inoutamount);
			
			if(depotamount+inoutamount > 0){
				depot.setDepotstatus("1"); //有库存
			}
			
			componentdepotDao.update(depot);
			
			//增加产品入库记录
			Componentinout componentinout = new Componentinout();
			componentinout.setComponentcode(form.getComponentcode());
			componentinout.setComponentname(form.getComponentname());
			componentinout.setComponentmodel(form.getComponentmodel());
			componentinout.setOperatorcode(operator.getEmployeecode());
			componentinout.setBatchno(form.getBatchno());
			componentinout.setType("0"); //入库
			componentinout.setInoutamount(inoutamount);
			componentinout.setAddtime(Tools.getCurrentTime());
			componentinout.setInoutercode(form.getInoutercode());//操作员为负责人
			componentinout.setInoutstatus("1");
			componentinout.setDepotrackcode(depot.getDepotrackcode());
			
			componentinoutDao.save(componentinout);
			
			//增加操作日记
			//Operator operator = (Operator)getSession().getAttribute("Operator");
			String content = "同批次入库，元器件名称为:"+form.getComponentname()+";入库量为："+ inoutamount;
			operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
			
			form.setReturninfo("修改成功");
			return updateDepotamountInit(form);
		}
	}
	
	/**
	 * 编辑产品权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Componentdepot form) {
		form.setComponentdepot(componentdepotDao.findById(form.getId()));
		return "componentdepot/updateComponentdepot";
	}

	/**
	 * 保存编辑后产品权限
	 */
	@RequestMapping(value = "/update")
	public String update(Componentdepot form) {
		componentdepotDao.update(form);
		form.setReturninfo("修改成功");
		return updateInit(form);
	}

	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Componentdepot form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		//删除产品
		componentdepotDao.delete(form.getId());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
	
	/**
     * 获取下拉列表框Json值
     */
	@ResponseBody //此标签表示返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	@RequestMapping(value="/getComponentdepotComboBoxJson")
	public List<Map<String,Object>> getComponentdepotComboBoxJson(Componentdepot form) {
	    List<Componentdepot> componentdepotList = componentdepotDao.queryByList(form);
	    List<Map<String, Object>> objectComboBoxJson = new ArrayList<Map<String, Object>>();
	    //添加请选择项-默认为空，查询所有
	    //HashMap<String,Object> selectMap = new HashMap<String, Object>();
	    //selectMap.put("id", "");
	    //selectMap.put("text", "请选择");
	    //productComboBoxJson.add(selectMap);
	    
	    //封装属性结构信息
	    for(Componentdepot componentdepot:componentdepotList){
	    	    HashMap<String,Object> objectMap = new HashMap<String, Object>();
	    	    objectMap.put("id", componentdepot.getBatchno());
	    	    objectMap.put("text", componentdepot.getBatchno() + "(" + componentdepot.getComponentname() + componentdepot.getComponentmodel() +")");
	    	    
	    	    objectComboBoxJson.add(objectMap);  
	    }  
	   return objectComboBoxJson;
	}  
}
