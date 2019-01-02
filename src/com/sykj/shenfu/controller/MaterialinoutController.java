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
import com.sykj.shenfu.dao.IMaterialDao;
import com.sykj.shenfu.dao.IMaterialinoutDao;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Material;
import com.sykj.shenfu.po.Materialinout;
import com.sykj.shenfu.service.IOperatorService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/materialinout")
@Transactional
public class MaterialinoutController extends BaseController {

	@Autowired
	private IMaterialinoutDao materialinoutDao;
	@Autowired
	private IOperatorService operatorService;
	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private IMaterialDao materialDao;
	
	
	/**
	 * 查询材料库存
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Materialinout form) {
		return "materialinout/findMaterialinoutList";
	}
	
	/**
	 * 查询材料库存Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Materialinout form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Integer total = materialinoutDao.findByCount(form);
		List<Materialinout> list = materialinoutDao.findByList(form);
		for (Materialinout materialinout : list) {
			
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("id", materialinout.getId());
			objectMap.put("materialcode", materialinout.getMaterialcode());
			objectMap.put("materialname", materialinout.getMaterialname());
			objectMap.put("operatorcode", materialinout.getOperatorcode());
			//操作员信息
			Employee operator = employeeDao.findByEmployeecodeStr(materialinout.getOperatorcode());
			if(operator != null){
				objectMap.put("operatorname", operator.getEmployeename());
				objectMap.put("operatorphone", operator.getPhone());
			}
			objectMap.put("batchno", materialinout.getBatchno());
			objectMap.put("type", materialinout.getType());
			objectMap.put("typename", materialinout.getTypename());
			objectMap.put("inoutamount", materialinout.getInoutamount());
			objectMap.put("addtime", StringUtils.isEmpty(materialinout.getAddtime())?"":Tools.getStr(materialinout.getAddtime()).substring(0, 19));
			objectMap.put("inoutercode", materialinout.getInoutercode());
			//入库负责人
			Employee inoutputer = employeeDao.findByEmployeecodeStr(materialinout.getInoutercode());
			if(inoutputer != null){
				objectMap.put("inoutername", inoutputer.getEmployeename());
				objectMap.put("inouterphone", inoutputer.getPhone());
			}
			objectMap.put("inoutstatus", materialinout.getInoutstatus());
			objectMap.put("inoutstatusname", materialinout.getInoutstatusname());
			objectMap.put("depotrackcode", materialinout.getDepotrackcode());
			objectMap.put("materialidentfy", materialinout.getMaterialidentfy());
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
	public Map<String,Object> delete(Materialinout form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		//删除产品
		materialinoutDao.delete(form.getId());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
	/**
	 * 导出excel
	 */
	@RequestMapping(value = "/findMaterialinoutExportExcel")
	public String findMaterialinoutExportExcel(Materialinout form, HttpServletResponse response) throws Exception {
		List<HashMap<String,Object>> objectList = new ArrayList<HashMap<String, Object>>();
		
//		Materialinout materialinout = materialinoutDao.findById(form.getId());
//		if(materialinout != null){
//			// 构建填充EXCEL文件所需资源包括数据库数据和国际化字段
//			HashMap<String, Object> excelmap = new HashMap<String, Object>();
//			// 初始化国际化表头以及对应的表值
//			excelmap.put("材料名称", Tools.getStr(materialinout.getMaterialname()));
//			excelmap.put("类型", Tools.getStr(materialinout.getTypename()));
//			excelmap.put("数量", Tools.getStr(String.valueOf(materialinout.getInoutamount())));
//			String empoyeecode = materialinout.getInoutercode();
//			String inoutername = "";
//			if(StringUtils.isNotEmpty(materialinout.getInoutercode())){
//				Employee inouter = employeeDao.findByEmployeecodeStr(materialinout.getInoutercode());
//				if(inouter != null){
//					inoutername = inouter.getEmployeename();
//				}
//			}
//			excelmap.put("负责人", Tools.getStr(inoutername));
//			excelmap.put("操作时间", StringUtils.isEmpty(materialinout.getAddtime())?"":Tools.getStr(materialinout.getAddtime()).substring(0, 16));
//			objectList.add(excelmap);
		
		List<Materialinout> list = materialinoutDao.queryByList(form);
		if (list != null && list.size() > 0) {
			Integer totalprice = 0;
			form.setMaterialinoutlist(list);
			for (Materialinout materialinout : list) {
				// 构建填充EXCEL文件所需资源包括数据库数据和国际化字段
				HashMap<String, Object> excelmap = new HashMap<String, Object>();
				// 初始化国际化表头以及对应的表值
				excelmap.put("材料名称", Tools.getStr(materialinout.getMaterialname()));
				excelmap.put("类型", Tools.getStr(materialinout.getTypename()));
				excelmap.put("数量", Tools.getStr(String.valueOf(materialinout.getInoutamount())));
				String empoyeecode = materialinout.getInoutercode();
				String inoutername = "";
				if(StringUtils.isNotEmpty(materialinout.getInoutercode())){
					Employee inouter = employeeDao.findByEmployeecodeStr(materialinout.getInoutercode());
					if(inouter != null){
						inoutername = inouter.getEmployeename();
					}
				}
				excelmap.put("负责人", Tools.getStr(inoutername));
				excelmap.put("操作时间", StringUtils.isEmpty(materialinout.getAddtime())?"":Tools.getStr(materialinout.getAddtime()).substring(0, 16));
				objectList.add(excelmap);
			}
		
		
			//标题栏
			String[] columntitle = {"材料名称", "类型", "数量", "负责人", "操作时间"};
			
			//条件
			List<HashMap<String, String>> conditionlist = new ArrayList<HashMap<String,String>>();
			if(StringUtils.isNotEmpty(form.getMaterialcode())){
				HashMap<String, String> conditionmap = new HashMap<String, String>();
				conditionmap.put("title", "物料名称");
				Material material = materialDao.findByMaterialcodeStr(form.getMaterialcode());
				conditionmap.put("content", Tools.getStr(material.getMaterialname()));
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
			form.setReturninfo(ExportExcel.resultSetToExcel(objectList, columntitle, "materialinout", "材料出入库清单", response, conditionlist));
		}
		return null;
	}
	
	/**
	 *  获取打印列表信息
	 * @return
	 */
	@RequestMapping(value="/getPrintInfo")
	public String getPrintInfo(Materialinout form, HttpServletRequest request) {
		Materialinout materialinout = materialinoutDao.findById(form.getId());
		Employee inouter = null;
		if(StringUtils.isNotEmpty(materialinout.getInoutercode())){
			inouter = employeeDao.findByEmployeecodeStr(materialinout.getInoutercode());
		}
		if(inouter == null){
			inouter = new Employee();
		}
		//赋值出入库负责人
		materialinout.setInouter(inouter); 
		
		List<Materialinout> materialinoutlist = new ArrayList<Materialinout>();
		materialinoutlist.add(materialinout);
		form.setMaterialinoutlist(materialinoutlist);
		return "materialinout/printInfo";
	}
	
	/**
	 * 材料出入库汇总
	 */
	@RequestMapping(value = "/materialinoutStat")
	public String materialinoutStat(Materialinout form) {
		return "materialinout/materialinoutStat";
	}
	
	/**
	 * 材料出入库汇总Json
	 */
	@ResponseBody
	@RequestMapping(value = "/materialinoutStatJson")
	public Map<String, Object> materialinoutStatJson(Materialinout form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Integer total = materialinoutDao.findMaterialinoutStatCount(form);
		List<Materialinout> list = materialinoutDao.findMaterialinoutStat(form);
		for (Materialinout materialinout : list) {
			
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("materialcode", materialinout.getMaterialcode());
			objectMap.put("materialname", materialinout.getMaterialname());
			objectMap.put("type", materialinout.getType());
			objectMap.put("typename", materialinout.getTypename());
			objectMap.put("totalinoutamount", materialinout.getTotalinoutamount());
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}
	
	
	/**
	 * 材料出入库统计导出excel
	 */
	@RequestMapping(value = "/materialinoutStatExportExcel")
	public String materialinoutStatExportExcel(Materialinout form, HttpServletResponse response) throws Exception {
		List<HashMap<String,Object>> objectList = new ArrayList<HashMap<String, Object>>();
		
		List<Materialinout> list = materialinoutDao.queryMaterialinoutStat(form);
		if (list != null && list.size() > 0) {
			Integer totalprice = 0;
			form.setMaterialinoutlist(list);
			for (Materialinout materialinout : list) {
				// 构建填充EXCEL文件所需资源包括数据库数据和国际化字段
				HashMap<String, Object> excelmap = new HashMap<String, Object>();
				// 初始化国际化表头以及对应的表值
				excelmap.put("材料名称", Tools.getStr(materialinout.getMaterialname()));
				excelmap.put("出入库类型", Tools.getStr(materialinout.getTypename()));
				excelmap.put("总数量", Tools.getStr(String.valueOf(materialinout.getTotalinoutamount())));
				objectList.add(excelmap);
			}
		
			//标题栏
			String[] columntitle = {"材料名称", "出入库类型", "总数量"};
			
			//条件
			List<HashMap<String, String>> conditionlist = new ArrayList<HashMap<String,String>>();
			if(StringUtils.isNotEmpty(form.getMaterialcode())){
				HashMap<String, String> conditionmap = new HashMap<String, String>();
				conditionmap.put("title", "物料名称");
				Material material = materialDao.findByMaterialcodeStr(form.getMaterialcode());
				conditionmap.put("content", Tools.getStr(material.getMaterialname()));
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
			form.setReturninfo(ExportExcel.resultSetToExcel(objectList, columntitle, "materialinoutstat", "材料出入库汇总", response, conditionlist));
		}
		return null;
	}
}
