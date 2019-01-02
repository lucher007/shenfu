package com.sykj.shenfu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sykj.shenfu.common.ExportExcel;
import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.IEmployeeDao;
import com.sykj.shenfu.dao.IComponentDao;
import com.sykj.shenfu.dao.IComponentinoutDao;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Component;
import com.sykj.shenfu.po.Componentinout;
import com.sykj.shenfu.service.IOperatorService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/componentinout")
@Transactional
public class ComponentinoutController extends BaseController {

	@Autowired
	private IComponentinoutDao componentinoutDao;
	@Autowired
	private IOperatorService operatorService;
	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private IComponentDao componentDao;
	
	
	/**
	 * 查询元器件库存
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Componentinout form) {
		return "componentinout/findComponentinoutList";
	}
	
	/**
	 * 查询元器件库存Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Componentinout form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Integer total = componentinoutDao.findByCount(form);
		List<Componentinout> list = componentinoutDao.findByList(form);
		for (Componentinout componentinout : list) {
			
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("id", componentinout.getId());
			objectMap.put("componentcode", componentinout.getComponentcode());
			objectMap.put("componentname", componentinout.getComponentname());
			objectMap.put("componentmodel", componentinout.getComponentmodel());
			objectMap.put("operatorcode", componentinout.getOperatorcode());
			//操作员信息
			Employee operator = employeeDao.findByEmployeecodeStr(componentinout.getOperatorcode());
			if(operator != null){
				objectMap.put("operatorname", operator.getEmployeename());
				objectMap.put("operatorphone", operator.getPhone());
			}
			objectMap.put("batchno", componentinout.getBatchno());
			objectMap.put("type", componentinout.getType());
			objectMap.put("typename", componentinout.getTypename());
			objectMap.put("inoutamount", componentinout.getInoutamount());
			objectMap.put("addtime", StringUtils.isEmpty(componentinout.getAddtime())?"":Tools.getStr(componentinout.getAddtime()).substring(0, 19));
			objectMap.put("inoutercode", componentinout.getInoutercode());
			//入库负责人
			Employee inoutputer = employeeDao.findByEmployeecodeStr(componentinout.getInoutercode());
			if(inoutputer != null){
				objectMap.put("inoutername", inoutputer.getEmployeename());
				objectMap.put("inouterphone", inoutputer.getPhone());
			}
			objectMap.put("inoutstatus", componentinout.getInoutstatus());
			objectMap.put("inoutstatusname", componentinout.getInoutstatusname());
			objectMap.put("depotrackcode", componentinout.getDepotrackcode());
			
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}
	
	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Componentinout form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		//删除产品
		componentinoutDao.delete(form.getId());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
	/**
	 * 导出excel
	 */
	@RequestMapping(value = "/findComponentinoutExportExcel")
	public String findComponentinoutExportExcel(Componentinout form, HttpServletResponse response) throws Exception {
		List<HashMap<String,Object>> objectList = new ArrayList<HashMap<String, Object>>();
		
//		Componentinout componentinout = componentinoutDao.findById(form.getId());
//		if(componentinout != null){
//			// 构建填充EXCEL文件所需资源包括数据库数据和国际化字段
//			HashMap<String, Object> excelmap = new HashMap<String, Object>();
//			// 初始化国际化表头以及对应的表值
//			excelmap.put("元器件名称", Tools.getStr(componentinout.getComponentname()));
//			excelmap.put("类型", Tools.getStr(componentinout.getTypename()));
//			excelmap.put("数量", Tools.getStr(String.valueOf(componentinout.getInoutamount())));
//			String empoyeecode = componentinout.getInoutercode();
//			String inoutername = "";
//			if(StringUtils.isNotEmpty(componentinout.getInoutercode())){
//				Employee inouter = employeeDao.findByEmployeecodeStr(componentinout.getInoutercode());
//				if(inouter != null){
//					inoutername = inouter.getEmployeename();
//				}
//			}
//			excelmap.put("负责人", Tools.getStr(inoutername));
//			excelmap.put("操作时间", StringUtils.isEmpty(componentinout.getAddtime())?"":Tools.getStr(componentinout.getAddtime()).substring(0, 16));
//			objectList.add(excelmap);
		
		List<Componentinout> list = componentinoutDao.queryByList(form);
		if (list != null && list.size() > 0) {
			Integer totalprice = 0;
			form.setComponentinoutlist(list);
			for (Componentinout componentinout : list) {
				// 构建填充EXCEL文件所需资源包括数据库数据和国际化字段
				HashMap<String, Object> excelmap = new HashMap<String, Object>();
				// 初始化国际化表头以及对应的表值
				excelmap.put("元器件封装", Tools.getStr(componentinout.getComponentname()));
				excelmap.put("元器件规格", Tools.getStr(componentinout.getComponentmodel()));
				excelmap.put("类型", Tools.getStr(componentinout.getTypename()));
				excelmap.put("数量", Tools.getStr(String.valueOf(componentinout.getInoutamount())));
				String empoyeecode = componentinout.getInoutercode();
				String inoutername = "";
				if(StringUtils.isNotEmpty(componentinout.getInoutercode())){
					Employee inouter = employeeDao.findByEmployeecodeStr(componentinout.getInoutercode());
					if(inouter != null){
						inoutername = inouter.getEmployeename();
					}
				}
				excelmap.put("负责人", Tools.getStr(inoutername));
				excelmap.put("操作时间", StringUtils.isEmpty(componentinout.getAddtime())?"":Tools.getStr(componentinout.getAddtime()).substring(0, 16));
				objectList.add(excelmap);
			}
		
		
			//标题栏
			String[] columntitle = {"元器件封装", "元器件规格", "类型", "数量", "负责人", "操作时间"};
			
			//条件
			List<HashMap<String, String>> conditionlist = new ArrayList<HashMap<String,String>>();
			if(StringUtils.isNotEmpty(form.getComponentcode())){
				HashMap<String, String> conditionmap = new HashMap<String, String>();
				conditionmap.put("title", "元器件名称");
				Component component = componentDao.findByComponentcode(form.getComponentcode());
				conditionmap.put("content", Tools.getStr(component.getComponentname()));
				conditionlist.add(conditionmap);
			}
			if(StringUtils.isNotEmpty(form.getType())){
				HashMap<String, String> conditionmap = new HashMap<String, String>();
				conditionmap.put("title", "出入库类型");
				conditionmap.put("content", form.getTypename());
				conditionlist.add(conditionmap);
			}
			if(StringUtils.isNotEmpty(form.getBegintime())){
				HashMap<String, String> conditionmap = new HashMap<String, String>();
				conditionmap.put("title", "查询开始时间");
				conditionmap.put("content", form.getBegintime());
				conditionlist.add(conditionmap);
			}
			if(StringUtils.isNotEmpty(form.getEndtime())){
				HashMap<String, String> conditionmap = new HashMap<String, String>();
				conditionmap.put("title", "查询结束时间");
				conditionmap.put("content", form.getEndtime());
				conditionlist.add(conditionmap);
			}
			form.setReturninfo(ExportExcel.resultSetToExcel(objectList, columntitle, "materialinout", "元器件出入库清单", response, conditionlist));
		}
		return null;
	}
	
	/**
	 *  获取打印列表信息
	 * @return
	 */
	@RequestMapping(value="/getPrintInfo")
	public String getPrintInfo(Componentinout form, HttpServletRequest request) {
		Componentinout componentinout = componentinoutDao.findById(form.getId());
		Employee inouter = null;
		if(StringUtils.isNotEmpty(componentinout.getInoutercode())){
			inouter = employeeDao.findByEmployeecodeStr(componentinout.getInoutercode());
		}
		if(inouter == null){
			inouter = new Employee();
		}
		//赋值出入库负责人
		componentinout.setInouter(inouter); 
		
		List<Componentinout> componentinoutlist = new ArrayList<Componentinout>();
		componentinoutlist.add(componentinout);
		form.setComponentinoutlist(componentinoutlist);
		return "componentinout/printInfo";
	}
	
	/**
	 * 元器件出入库汇总
	 */
	@RequestMapping(value = "/componentinoutStat")
	public String componentinoutStat(Componentinout form) {
		return "componentinout/componentinoutStat";
	}
	
	/**
	 * 元器件出入库汇总Json
	 */
	@ResponseBody
	@RequestMapping(value = "/componentinoutStatJson")
	public Map<String, Object> componentinoutStatJson(Componentinout form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Integer total = componentinoutDao.findComponentinoutStatCount(form);
		List<Componentinout> list = componentinoutDao.findComponentinoutStat(form);
		for (Componentinout componentinout : list) {
			
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("componentcode", componentinout.getComponentcode());
			objectMap.put("componentname", componentinout.getComponentname());
			objectMap.put("componentmodel", componentinout.getComponentmodel());
			objectMap.put("type", componentinout.getType());
			objectMap.put("typename", componentinout.getTypename());
			objectMap.put("totalinoutamount", componentinout.getTotalinoutamount());
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}
	
	
	/**
	 * 元器件出入库统计导出excel
	 */
	@RequestMapping(value = "/componentinoutStatExportExcel")
	public String componentinoutStatExportExcel(Componentinout form, HttpServletResponse response) throws Exception {
		List<HashMap<String,Object>> objectList = new ArrayList<HashMap<String, Object>>();
		
		List<Componentinout> list = componentinoutDao.queryComponentinoutStat(form);
		if (list != null && list.size() > 0) {
			Integer totalprice = 0;
			form.setComponentinoutlist(list);
			for (Componentinout componentinout : list) {
				// 构建填充EXCEL文件所需资源包括数据库数据和国际化字段
				HashMap<String, Object> excelmap = new HashMap<String, Object>();
				// 初始化国际化表头以及对应的表值
				excelmap.put("元器件封装", Tools.getStr(componentinout.getComponentname()));
				excelmap.put("元器件规格", Tools.getStr(componentinout.getComponentmodel()));
				excelmap.put("出入库类型", Tools.getStr(componentinout.getTypename()));
				excelmap.put("总数量", Tools.getStr(String.valueOf(componentinout.getTotalinoutamount())));
				objectList.add(excelmap);
			}
		
			//标题栏
			String[] columntitle = {"元器件封装", "元器件规格", "出入库类型", "总数量"};
			
			//条件
			List<HashMap<String, String>> conditionlist = new ArrayList<HashMap<String,String>>();
			if(StringUtils.isNotEmpty(form.getComponentcode())){
				HashMap<String, String> conditionmap = new HashMap<String, String>();
				conditionmap.put("title", "物料名称");
				Component component = componentDao.findByComponentcode(form.getComponentcode());
				conditionmap.put("content", Tools.getStr(component.getComponentname()));
				conditionlist.add(conditionmap);
			}
			if(StringUtils.isNotEmpty(form.getType())){
				HashMap<String, String> conditionmap = new HashMap<String, String>();
				conditionmap.put("title", "出入库类型");
				conditionmap.put("content", form.getTypename());
				conditionlist.add(conditionmap);
			}
			if(StringUtils.isNotEmpty(form.getBegintime())){
				HashMap<String, String> conditionmap = new HashMap<String, String>();
				conditionmap.put("title", "查询开始时间");
				conditionmap.put("content", form.getBegintime());
				conditionlist.add(conditionmap);
			}
			if(StringUtils.isNotEmpty(form.getEndtime())){
				HashMap<String, String> conditionmap = new HashMap<String, String>();
				conditionmap.put("title", "查询结束时间");
				conditionmap.put("content", form.getEndtime());
				conditionlist.add(conditionmap);
			}
			form.setReturninfo(ExportExcel.resultSetToExcel(objectList, columntitle, "materialinoutstat", "元器件出入库汇总", response, conditionlist));
		}
		return null;
	}
}
