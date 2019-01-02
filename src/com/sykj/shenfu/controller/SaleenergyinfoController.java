package com.sykj.shenfu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
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
import com.sykj.shenfu.dao.IProductcolorDao;
import com.sykj.shenfu.dao.IProductdepotDao;
import com.sykj.shenfu.dao.IProductinoutDao;
import com.sykj.shenfu.dao.IProductmodelDao;
import com.sykj.shenfu.dao.IProductmodelrefDao;
import com.sykj.shenfu.dao.ISaleDao;
import com.sykj.shenfu.dao.ISaleenergyruleDao;
import com.sykj.shenfu.dao.ISystemparaDao;
import com.sykj.shenfu.dao.ITimeparaDao;
import com.sykj.shenfu.dao.IUserbusinessDao;
import com.sykj.shenfu.dao.IUsercontractDao;
import com.sykj.shenfu.dao.IUserdeliveryDao;
import com.sykj.shenfu.dao.IUserdispatchDao;
import com.sykj.shenfu.dao.ISaleenergyinfoDao;
import com.sykj.shenfu.dao.IUserDao;
import com.sykj.shenfu.dao.IUserproductDao;
import com.sykj.shenfu.dao.IUsertaskDao;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Material;
import com.sykj.shenfu.po.Saleenergyinfo;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Saleenergyinfo;
import com.sykj.shenfu.service.IOperatorService;
import com.sykj.shenfu.service.IOperatorlogService;
import com.sykj.shenfu.service.ISaleenergyinfoService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/saleenergyinfo")
@Transactional
public class SaleenergyinfoController extends BaseController {
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private ISaleenergyinfoDao saleenergyinfoDao;
	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private ISaleenergyinfoService saleenergyinfoService;
   
	/*
     * 特殊的instance变量
     * 注：零长度的byte数组对象创建起来将比任何对象都经济――查看编译后的字节码：
     * 生成零长度的byte[]对象只需3条操作码，而Object lock = new Object()则需要7行操作码。
     */
    private static byte[] lock = new byte[0];   
	
	/**
	 * 查询 订单
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Saleenergyinfo form) {
		return "saleenergyinfo/findSaleenergyinfoList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Saleenergyinfo form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		//easyui footer数据
		List<HashMap<String, Object>> footertlist = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> footerMap = new HashMap<String, Object>();
		Integer energyscore = 0;//总行动力积分
		
		Integer total = saleenergyinfoDao.findByCount(form);
		List<Saleenergyinfo> list = saleenergyinfoDao.findByList(form);
		for (Saleenergyinfo saleenergyinfo : list) {
			
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			
			//潜在订户信息
			objectMap.put("id", saleenergyinfo.getId());
			//objectMap.put("energyercode", saleenergyinfo.getenergyercode());
			//上门员信息
			Employee gainer = employeeDao.findByEmployeecodeStr(saleenergyinfo.getGainercode());
			if(gainer == null){
				gainer = new Employee();
			}
			objectMap.put("gainercode", saleenergyinfo.getGainercode());
			objectMap.put("gainername", gainer.getEmployeename());
			objectMap.put("gainerphone", gainer.getPhone());
			objectMap.put("usercode", saleenergyinfo.getUsercode());
			objectMap.put("username", saleenergyinfo.getUsername());
			objectMap.put("phone", saleenergyinfo.getPhone());
			objectMap.put("address", saleenergyinfo.getAddress());
			objectMap.put("source", saleenergyinfo.getSource());
			objectMap.put("sourcename", saleenergyinfo.getSourcename());
			objectMap.put("visittype", saleenergyinfo.getVisittype());
			objectMap.put("visittypename", saleenergyinfo.getVisittypename());
			objectMap.put("taskcode", saleenergyinfo.getTaskcode());
			//销售员信息
			if(StringUtils.isNotEmpty(saleenergyinfo.getSalercode())){
				Employee saler = employeeDao.findByEmployeecodeStr(saleenergyinfo.getSalercode());
				if(saler != null){
					objectMap.put("salercode", saleenergyinfo.getSalercode());
					objectMap.put("salername", saler.getEmployeename());
					objectMap.put("salerphone", saler.getPhone());
				}
			}
			//上门员信息
			Employee visitor = employeeDao.findByEmployeecodeStr(saleenergyinfo.getVisitorcode());
			if(visitor == null){
				visitor = new Employee();
			}
			objectMap.put("visitorcode", saleenergyinfo.getVisitorcode());
			objectMap.put("visitorname", visitor.getEmployeename());
			objectMap.put("visitorphone", visitor.getPhone());
			objectMap.put("ordercode", saleenergyinfo.getOrdercode());
			objectMap.put("totalmoney", saleenergyinfo.getTotalmoney());
			objectMap.put("gaincode", saleenergyinfo.getGaincode());
			objectMap.put("gainname", saleenergyinfo.getGainname());
			objectMap.put("gainrate", saleenergyinfo.getGainrate());
			objectMap.put("gainmoney", saleenergyinfo.getGainmoney());
			objectMap.put("energyscore", saleenergyinfo.getEnergyscore());
			energyscore = energyscore + saleenergyinfo.getEnergyscore();
			objectMap.put("addtime", StringUtils.isEmpty(saleenergyinfo.getAddtime())?"":Tools.getStr(saleenergyinfo.getAddtime()).substring(0, 19));
			objectMap.put("status", saleenergyinfo.getStatus());
			objectMap.put("statusname", saleenergyinfo.getStatusname());
			objectMap.put("exchangetime", StringUtils.isEmpty(saleenergyinfo.getExchangetime())?"":Tools.getStr(saleenergyinfo.getExchangetime()).substring(0, 19));
			objectMap.put("statusname", saleenergyinfo.getStatusname());
			//操作员信息
			Employee operator = employeeDao.findByEmployeecodeStr(saleenergyinfo.getOperatorcode());
			if(operator == null){
				operator = new Employee();
			}
			objectMap.put("opertorcode", saleenergyinfo.getOperatorcode());
			objectMap.put("opertorname", operator.getEmployeename());
			objectMap.put("opertorphone", operator.getPhone());
			
			objectMap.put("remark", saleenergyinfo.getRemark());
			
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		
		//封装Easyui的footer数据
		footerMap.put("energyname", "总行动力积分");
		footerMap.put("energyscore", energyscore);
		footertlist.add(footerMap);
		result.put("footer", footertlist);
		return result;
	}
	
	/**
	 * 员工提成汇总汇总
	 */
	@RequestMapping(value = "/saleenergyinfoStat")
	public String saleenergyinfoStat(Saleenergyinfo form) {
		return "saleenergyinfo/saleenergyinfoStat";
	}
	
	/**
	 * 员工提成汇总汇总Json
	 */
	@ResponseBody
	@RequestMapping(value = "/saleenergyinfoStatJson")
	public Map<String, Object> saleenergyinfoStatJson(Saleenergyinfo form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Integer total = saleenergyinfoDao.findSaleenergyinfoStatCount(form);
		List<Saleenergyinfo> list = saleenergyinfoDao.findSaleenergyinfoStat(form);
		for (Saleenergyinfo saleenergyinfo : list) {
			
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("id", saleenergyinfo.getGainercode());
			objectMap.put("gainercode", saleenergyinfo.getGainercode());
			Employee gianer = null;
			if(StringUtils.isNotEmpty(saleenergyinfo.getGainercode())){
				gianer = employeeDao.findByEmployeecodeStr(saleenergyinfo.getGainercode());
			}
			if(gianer != null){
				objectMap.put("gainername", Tools.getStr(gianer.getEmployeename()));
				objectMap.put("gainerphone", Tools.getStr(gianer.getPhone()));
			}else{
				objectMap.put("gainername", "");
				objectMap.put("gainerphone", "");
			}
			
			objectMap.put("ids", saleenergyinfo.getIds());
			objectMap.put("gainmoney", saleenergyinfo.getGainmoney());
			objectMap.put("energyscore", saleenergyinfo.getEnergyscore());
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}
	
	
	/**
	 * 员工提成汇总统计导出excel
	 */
	@RequestMapping(value = "/saleenergyinfoStatExportExcel")
	public String saleenergyinfoStatExportExcel(Saleenergyinfo form, HttpServletResponse response) throws Exception {
		List<HashMap<String,Object>> objectList = new ArrayList<HashMap<String, Object>>();
		
		List<Saleenergyinfo> list = saleenergyinfoDao.querySaleenergyinfoStat(form);
		if (list != null && list.size() > 0) {
			Integer totalprice = 0;
			form.setSaleenergyinfolist(list);
			for (Saleenergyinfo saleenergyinfo : list) {
				// 构建填充EXCEL文件所需资源包括数据库数据和国际化字段
				HashMap<String, Object> excelmap = new HashMap<String, Object>();
				// 初始化国际化表头以及对应的表值
				excelmap.put("员工编号", Tools.getStr(saleenergyinfo.getGainercode()));
				Employee energyer = null;
				if(StringUtils.isNotEmpty(saleenergyinfo.getGainercode())){
					energyer = employeeDao.findByEmployeecodeStr(saleenergyinfo.getGainercode());
				}
				if(energyer == null){
					energyer = new Employee();
				}
				excelmap.put("员工姓名", Tools.getStr(energyer.getEmployeename()));
				excelmap.put("员工电话", Tools.getStr(energyer.getPhone()));
				excelmap.put("行动力积分", saleenergyinfo.getEnergyscore());
				objectList.add(excelmap);
			}
		
			//标题栏
			String[] columntitle = {"员工编号", "员工姓名", "员工电话", "行动力积分"};
			
			//条件
			List<HashMap<String, String>> conditionlist = new ArrayList<HashMap<String,String>>();
			if(StringUtils.isNotEmpty(form.getGainercode())){
				HashMap<String, String> conditionmap = new HashMap<String, String>();
				conditionmap.put("title", "提成人");
				Employee energyer = employeeDao.findByEmployeecodeStr(form.getGainercode());
				conditionmap.put("content", Tools.getStr(energyer.getEmployeename()));
				conditionlist.add(conditionmap);
			}
			if(StringUtils.isNotEmpty(form.getStatus())){
				HashMap<String, String> conditionmap = new HashMap<String, String>();
				conditionmap.put("title", "兑换类型");
				conditionmap.put("content", form.getStatusname());
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
			form.setReturninfo(ExportExcel.resultSetToExcel(objectList, columntitle, "salegaininfostat", "员工行动力汇总", response, conditionlist));
		}
		return null;
	}
	
	/**
	 * 行动力兑换提成
	 */
	@RequestMapping(value = "/saveEnergyExchangeToGain")
	public String saveEnergyExchangeToGain(Saleenergyinfo form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		//保存型号关联的产品类别信息
		String saleenergyinfoStatJson = form.getSaleenergyinfoStatJson();
		if(StringUtils.isEmpty(saleenergyinfoStatJson)){
			form.setReturninfo("请选择需要兑换提成的行动力记录");
			return saleenergyinfoStat(form);
		}
		
		 Map<String, Object> responseMap = saleenergyinfoService.saveEnergyExchangeToGain(form, getRequest(), operator);
			
		form.setReturninfo(String.valueOf(responseMap.get("result")));
		
		return saleenergyinfoStat(form);
		
	}
}
