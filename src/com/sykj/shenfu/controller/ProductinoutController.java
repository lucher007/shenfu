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
import com.sykj.shenfu.dao.IProductDao;
import com.sykj.shenfu.dao.IProductinoutDao;
import com.sykj.shenfu.dao.IOperatorDao;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Material;
import com.sykj.shenfu.po.Materialinout;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Product;
import com.sykj.shenfu.po.Productinout;
import com.sykj.shenfu.service.IOperatorService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/productinout")
@Transactional
public class ProductinoutController extends BaseController {

	@Autowired
	private IProductinoutDao productinoutDao;
	@Autowired
	private IProductDao productDao;
	@Autowired
	private IOperatorService operatorService;
	@Autowired
	private IEmployeeDao employeeDao;
	
	
	/**
	 * 查询产品库存
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Productinout form) {
		return "productinout/findProductinoutList";
	}
	
	/**
	 * 查询产品库存Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Productinout form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		Integer total = productinoutDao.findByCount(form);
		List<Productinout> list = productinoutDao.findByList(form);
		for (Productinout productinout : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			//产品出入库信息
			objectMap.put("id", productinout.getId());
			objectMap.put("productcode", productinout.getProductcode());
			objectMap.put("productname", productinout.getProductname());
			objectMap.put("productidentfy", productinout.getProductidentfy());
			objectMap.put("type", productinout.getType());
			objectMap.put("typename", productinout.getTypename());
			//入库负责人
			Employee inoutputer = employeeDao.findByEmployeecodeStr(productinout.getInoutercode());
			if(inoutputer != null){
				objectMap.put("inoutername", inoutputer.getEmployeename());
				objectMap.put("inouterphone", inoutputer.getPhone());
			}
			objectMap.put("inoutamount", productinout.getInoutamount());
			objectMap.put("addtime", StringUtils.isEmpty(productinout.getAddtime())?"":Tools.getStr(productinout.getAddtime()).substring(0, 19));
			objectMap.put("operatorcode", productinout.getOperatorcode());
			//操作员信息
			Employee operator = employeeDao.findByEmployeecodeStr(productinout.getOperatorcode());
			if(operator != null){
				objectMap.put("operatorname", operator.getEmployeename());
				objectMap.put("operatorphone", operator.getPhone());
			}
			objectMap.put("inoutstatus", productinout.getInoutstatus());
			objectMap.put("inoutstatusname", productinout.getInoutstatusname());
			objectMap.put("depotrackcode", productinout.getDepotrackcode());
			objectMap.put("inoutercode", productinout.getInoutercode());
			objectMap.put("remark", productinout.getRemark());
			
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
	public Map<String,Object> delete(Productinout form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		//删除产品
		productinoutDao.delete(form.getId());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
	
	/**
	 * 导出excel
	 */
	@RequestMapping(value = "/findProductinoutExportExcel")
	public String findProductinoutExportExcel(Productinout form, HttpServletResponse response) throws Exception {
		List<HashMap<String,Object>> objectList = new ArrayList<HashMap<String, Object>>();
		
//		Productinout productinout = productinoutDao.findById(form.getId());
//		if(productinout != null){
//			// 构建填充EXCEL文件所需资源包括数据库数据和国际化字段
//			HashMap<String, Object> excelmap = new HashMap<String, Object>();
//			// 初始化国际化表头以及对应的表值
//			excelmap.put("产品名称", Tools.getStr(productinout.getProductname()));
//			excelmap.put("类型", Tools.getStr(productinout.getTypename()));
//			excelmap.put("数量", Tools.getStr(String.valueOf(productinout.getInoutamount())));
//			String empoyeecode = productinout.getInoutercode();
//			String inoutername = "";
//			if(StringUtils.isNotEmpty(productinout.getInoutercode())){
//				Employee inouter = employeeDao.findByEmployeecodeStr(productinout.getInoutercode());
//				if(inouter != null){
//					inoutername = inouter.getEmployeename();
//				}
//			}
//			excelmap.put("负责人", Tools.getStr(inoutername));
//			excelmap.put("操作时间", StringUtils.isEmpty(productinout.getAddtime())?"":Tools.getStr(productinout.getAddtime()).substring(0, 16));
//			objectList.add(excelmap);
		
		List<Productinout> list = productinoutDao.queryByList(form);
		if (list != null && list.size() > 0) {
			Integer totalprice = 0;
			form.setProductinoutlist(list);
			for (Productinout productinout : list) {
				// 构建填充EXCEL文件所需资源包括数据库数据和国际化字段
				HashMap<String, Object> excelmap = new HashMap<String, Object>();
				// 初始化国际化表头以及对应的表值
				excelmap.put("产品名称", Tools.getStr(productinout.getProductname()));
				excelmap.put("类型", Tools.getStr(productinout.getTypename()));
				excelmap.put("数量", Tools.getStr(String.valueOf(productinout.getInoutamount())));
				String empoyeecode = productinout.getInoutercode();
				String inoutername = "";
				if(StringUtils.isNotEmpty(productinout.getInoutercode())){
					Employee inouter = employeeDao.findByEmployeecodeStr(productinout.getInoutercode());
					if(inouter != null){
						inoutername = inouter.getEmployeename();
					}
				}
				excelmap.put("负责人", Tools.getStr(inoutername));
				excelmap.put("操作时间", StringUtils.isEmpty(productinout.getAddtime())?"":Tools.getStr(productinout.getAddtime()).substring(0, 16));
				objectList.add(excelmap);
			}
		
		
			//标题栏
			String[] columntitle = {"产品名称", "类型", "数量", "负责人", "操作时间"};
			
			//条件
			List<HashMap<String, String>> conditionlist = new ArrayList<HashMap<String,String>>();
			if(StringUtils.isNotEmpty(form.getProductcode())){
				HashMap<String, String> conditionmap = new HashMap<String, String>();
				conditionmap.put("title", "产品名称");
				Product product = productDao.findByProductcode(form.getProductcode());
				conditionmap.put("content", Tools.getStr(product.getProductname()));
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
			form.setReturninfo(ExportExcel.resultSetToExcel(objectList, columntitle, "productinout", "产品出入库清单", response, conditionlist));
		}
		return null;
	}
	
	/**
	 *  获取打印列表信息
	 * @return
	 */
	@RequestMapping(value="/getPrintInfo")
	public String getPrintInfo(Productinout form, HttpServletRequest request) {
		Productinout productinout = productinoutDao.findById(form.getId());
		Employee inouter = null;
		if(StringUtils.isNotEmpty(productinout.getInoutercode())){
			inouter = employeeDao.findByEmployeecodeStr(productinout.getInoutercode());
		}
		if(inouter == null){
			inouter = new Employee();
		}
		//赋值出入库负责人
		productinout.setInouter(inouter); 
		
		List<Productinout> productinoutlist = new ArrayList<Productinout>();
		productinoutlist.add(productinout);
		form.setProductinoutlist(productinoutlist);
		return "productinout/printInfo";
	}
	
	/**
	 * 产品出入库汇总
	 */
	@RequestMapping(value = "/productinoutStat")
	public String productinoutStat(Materialinout form) {
		return "productinout/productinoutStat";
	}
	
	/**
	 * 产品出入库汇总Json
	 */
	@ResponseBody
	@RequestMapping(value = "/productinoutStatJson")
	public Map<String, Object> productinoutStatJson(Productinout form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Integer total = productinoutDao.findProductinoutStatCount(form);
		List<Productinout> list = productinoutDao.findProductinoutStat(form);
		for (Productinout productinout : list) {
			
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("productcode", productinout.getProductcode());
			objectMap.put("productname", productinout.getProductname());
			objectMap.put("type", productinout.getType());
			objectMap.put("typename", productinout.getTypename());
			objectMap.put("totalinoutamount", productinout.getTotalinoutamount());
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}
	
	
	/**
	 * 产品出入库统计导出excel
	 */
	@RequestMapping(value = "/productinoutStatExportExcel")
	public String productinoutStatExportExcel(Productinout form, HttpServletResponse response) throws Exception {
		List<HashMap<String,Object>> objectList = new ArrayList<HashMap<String, Object>>();
		
		List<Productinout> list = productinoutDao.queryProductinoutStat(form);
		if (list != null && list.size() > 0) {
			Integer totalprice = 0;
			form.setProductinoutlist(list);
			for (Productinout productinout : list) {
				// 构建填充EXCEL文件所需资源包括数据库数据和国际化字段
				HashMap<String, Object> excelmap = new HashMap<String, Object>();
				// 初始化国际化表头以及对应的表值
				excelmap.put("产品名称", Tools.getStr(productinout.getProductname()));
				excelmap.put("出入库类型", Tools.getStr(productinout.getTypename()));
				excelmap.put("总数量", Tools.getStr(String.valueOf(productinout.getTotalinoutamount())));
				objectList.add(excelmap);
			}
		
			//标题栏
			String[] columntitle = {"产品名称", "出入库类型", "总数量"};
			
			//条件
			List<HashMap<String, String>> conditionlist = new ArrayList<HashMap<String,String>>();
			if(StringUtils.isNotEmpty(form.getProductcode())){
				HashMap<String, String> conditionmap = new HashMap<String, String>();
				conditionmap.put("title", "物料名称");
				Product product = productDao.findByProductcode(form.getProductcode());
				conditionmap.put("content", Tools.getStr(product.getProductname()));
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
			form.setReturninfo(ExportExcel.resultSetToExcel(objectList, columntitle, "productinoutstat", "产品出入库汇总", response, conditionlist));
		}
		return null;
	}
}
