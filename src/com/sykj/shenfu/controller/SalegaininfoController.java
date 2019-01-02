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
import com.sykj.shenfu.dao.ISalegainruleDao;
import com.sykj.shenfu.dao.ISystemparaDao;
import com.sykj.shenfu.dao.ITimeparaDao;
import com.sykj.shenfu.dao.IUserbusinessDao;
import com.sykj.shenfu.dao.IUsercontractDao;
import com.sykj.shenfu.dao.IUserdeliveryDao;
import com.sykj.shenfu.dao.IUserdispatchDao;
import com.sykj.shenfu.dao.ISalegaininfoDao;
import com.sykj.shenfu.dao.IUserDao;
import com.sykj.shenfu.dao.IUserproductDao;
import com.sykj.shenfu.dao.IUsertaskDao;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Material;
import com.sykj.shenfu.po.Salegaininfo;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Salegaininfo;
import com.sykj.shenfu.service.IOperatorService;
import com.sykj.shenfu.service.IOperatorlogService;
import com.sykj.shenfu.service.ISalegaininfoService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/salegaininfo")
@Transactional
public class SalegaininfoController extends BaseController {
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private ISalegaininfoDao salegaininfoDao;
	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private ISalegaininfoService salegaininfoService;
   
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
	public String findByList(Salegaininfo form) {
		return "salegaininfo/findSalegaininfoList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Salegaininfo form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		//easyui footer数据
		List<HashMap<String, Object>> footertlist = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> footerMap = new HashMap<String, Object>();
		Integer totaltainmoney = 0;//总提成
		
		Integer total = salegaininfoDao.findByCount(form);
		List<Salegaininfo> list = salegaininfoDao.findByList(form);
		for (Salegaininfo salegaininfo : list) {
			
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			
			//潜在订户信息
			objectMap.put("id", salegaininfo.getId());
			//objectMap.put("gainercode", salegaininfo.getGainercode());
			//上门员信息
			Employee gainer = employeeDao.findByEmployeecodeStr(salegaininfo.getGainercode());
			if(gainer == null){
				gainer = new Employee();
			}
			objectMap.put("gainercode", salegaininfo.getGainercode());
			objectMap.put("gainername", gainer.getEmployeename());
			objectMap.put("gainerphone", gainer.getPhone());
			
			objectMap.put("usercode", salegaininfo.getUsercode());
			objectMap.put("username", salegaininfo.getUsername());
			objectMap.put("phone", salegaininfo.getPhone());
			objectMap.put("address", salegaininfo.getAddress());
			objectMap.put("source", salegaininfo.getSource());
			objectMap.put("sourcename", salegaininfo.getSourcename());
			objectMap.put("visittype", salegaininfo.getVisittype());
			objectMap.put("visittypename", salegaininfo.getVisittypename());
			objectMap.put("taskcode", salegaininfo.getTaskcode());
			//销售员信息
			if(StringUtils.isNotEmpty(salegaininfo.getSalercode())){
				Employee saler = employeeDao.findByEmployeecodeStr(salegaininfo.getSalercode());
				if(saler != null){
					objectMap.put("salercode", salegaininfo.getSalercode());
					objectMap.put("salername", saler.getEmployeename());
					objectMap.put("salerphone", saler.getPhone());
				}
			}
			//上门员信息
			Employee visitor = employeeDao.findByEmployeecodeStr(salegaininfo.getVisitorcode());
			if(visitor == null){
				visitor = new Employee();
			}
			objectMap.put("visitorcode", salegaininfo.getVisitorcode());
			objectMap.put("visitorname", visitor.getEmployeename());
			objectMap.put("visitorphone", visitor.getPhone());
			
			objectMap.put("ordercode", salegaininfo.getOrdercode());
			objectMap.put("totalmoney", salegaininfo.getTotalmoney());
			objectMap.put("gaincode", salegaininfo.getGaincode());
			objectMap.put("gainname", salegaininfo.getGainname());
			objectMap.put("gainrate", salegaininfo.getGainrate());
			objectMap.put("gainmoney", salegaininfo.getGainmoney());
			totaltainmoney = totaltainmoney + salegaininfo.getGainmoney();
			objectMap.put("addtime", StringUtils.isEmpty(salegaininfo.getAddtime())?"":Tools.getStr(salegaininfo.getAddtime()).substring(0, 19));
			objectMap.put("status", salegaininfo.getStatus());
			objectMap.put("statusname", salegaininfo.getStatusname());
			objectMap.put("gaintime", StringUtils.isEmpty(salegaininfo.getGaintime())?"":Tools.getStr(salegaininfo.getGaintime()).substring(0, 19));
			objectMap.put("gainlogcode", salegaininfo.getGainlogcode());
			objectMap.put("remark", salegaininfo.getRemark());
			
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		
		//封装Easyui的footer数据
		footerMap.put("gainname", "总提成");
		footerMap.put("gainmoney", totaltainmoney);
		footertlist.add(footerMap);
		result.put("footer", footertlist);
		return result;
	}
    
	
	/**
	 * 查询当前选择的提成的总金额
	 * @return
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value="/getGaintotalmoneyForSalegaininfo")
	public Map<String,Object> getGaintotalmoneyForSalegaininfo(Salegaininfo form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		if(StringUtils.isEmpty(form.getGainercode())){
			responseJson.put("flag", "0");
			responseJson.put("result", "请选择需要提取提成的员工");
			return responseJson;
		}
		Employee employee = employeeDao.findByEmployeecodeStr(form.getGainercode());
		if(employee != null){
			//页面能选择的最早时间不能超过今天
			responseJson.put("flag", "1");
			responseJson.put("gaintotalmoney", employee.getSalescore());
		}else{
			responseJson.put("flag", "0");
			responseJson.put("result", "提取提成的员工不存在");
			return responseJson;
		}
		
		return responseJson;
	}
	
	
	/**
	 * 销售提成提取
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/updateStatus")
	public Map<String,Object> updateStatus(Salegaininfo form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		//封装返回给页面的json对象
		Map<String, Object> responseMap = new HashMap<String,Object>();
		
		String ids  = Tools.getStr(getRequest().getParameter("ids"));
		String[] idArray = ids.split(",");
		if(idArray == null || idArray.length < 1){
			responseMap.put("status", "0");//保存失败
			responseMap.put("result", "请选择需要提成");//没有
			return responseMap;
		}
		
		if(StringUtils.isEmpty(form.getGainercode())){
			responseMap.put("status", "0");//保存失败
			responseMap.put("result", "请选择提取人");//没有
			return responseMap;
		}
		
		responseMap = salegaininfoService.updateSalegaininfo(form.getGainercode(),ids,form.getGaintotalmoney(),operator.getEmployeecode());
		
		return responseMap;
		
	}
	
	/**
	 * 员工提成汇总汇总
	 */
	@RequestMapping(value = "/salegaininfoStat")
	public String salegaininfoStat(Salegaininfo form) {
		return "salegaininfo/salegaininfoStat";
	}
	
	/**
	 * 员工提成汇总汇总Json
	 */
	@ResponseBody
	@RequestMapping(value = "/salegaininfoStatJson")
	public Map<String, Object> salegaininfoStatJson(Salegaininfo form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Integer total = salegaininfoDao.findSalegaininfoStatCount(form);
		List<Salegaininfo> list = salegaininfoDao.findSalegaininfoStat(form);
		for (Salegaininfo salegaininfo : list) {
			
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("id", salegaininfo.getGainercode());
			objectMap.put("gainercode", salegaininfo.getGainercode());
			Employee gainer = null;
			if(StringUtils.isNotEmpty(salegaininfo.getGainercode())){
				gainer = employeeDao.findByEmployeecodeStr(salegaininfo.getGainercode());
			}
			
			if(gainer != null){
				objectMap.put("gainername", Tools.getStr(gainer.getEmployeename()));
				objectMap.put("gainerphone", Tools.getStr(gainer.getPhone()));
				objectMap.put("restgainmoney", gainer.getSalescore()==null?0:gainer.getSalescore());
			}else{
				objectMap.put("gainername", "");
				objectMap.put("gainerphone", "");
				objectMap.put("restgainmoney", 0);
			}
			
			objectMap.put("ids", salegaininfo.getIds());
			objectMap.put("gainmoney", salegaininfo.getGainmoney());
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}
	
	
	/**
	 * 员工提成汇总统计导出excel
	 */
	@RequestMapping(value = "/salegaininfoStatExportExcel")
	public String salegaininfoStatExportExcel(Salegaininfo form, HttpServletResponse response) throws Exception {
		List<HashMap<String,Object>> objectList = new ArrayList<HashMap<String, Object>>();
		
		List<Salegaininfo> list = salegaininfoDao.querySalegaininfoStat(form);
		if (list != null && list.size() > 0) {
			Integer totalprice = 0;
			form.setSalegaininfolist(list);
			for (Salegaininfo salegaininfo : list) {
				// 构建填充EXCEL文件所需资源包括数据库数据和国际化字段
				HashMap<String, Object> excelmap = new HashMap<String, Object>();
				// 初始化国际化表头以及对应的表值
				excelmap.put("员工编号", Tools.getStr(salegaininfo.getGainercode()));
				Employee gainer = null;
				if(StringUtils.isNotEmpty(salegaininfo.getGainercode())){
					gainer = employeeDao.findByEmployeecodeStr(salegaininfo.getGainercode());
				}
				if(gainer == null){
					gainer = new Employee();
				}
				excelmap.put("员工姓名", Tools.getStr(gainer.getEmployeename()));
				excelmap.put("员工电话", Tools.getStr(gainer.getPhone()));
				excelmap.put("提成金额", salegaininfo.getGainmoney());
				objectList.add(excelmap);
			}
		
			//标题栏
			String[] columntitle = {"员工编号", "员工姓名", "员工电话", "提成金额"};
			
			//条件
			List<HashMap<String, String>> conditionlist = new ArrayList<HashMap<String,String>>();
			if(StringUtils.isNotEmpty(form.getGainercode())){
				HashMap<String, String> conditionmap = new HashMap<String, String>();
				conditionmap.put("title", "提成人");
				Employee gainer = employeeDao.findByEmployeecodeStr(form.getGainercode());
				conditionmap.put("content", Tools.getStr(gainer.getEmployeename()));
				conditionlist.add(conditionmap);
			}
			if(StringUtils.isNotEmpty(form.getStatus())){
				HashMap<String, String> conditionmap = new HashMap<String, String>();
				conditionmap.put("title", "提取类型");
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
			form.setReturninfo(ExportExcel.resultSetToExcel(objectList, columntitle, "salegaininfostat", "员工提成汇总", response, conditionlist));
		}
		return null;
	}
	
	/**
	 * 销售提成提取
	 */
	@RequestMapping(value = "/saveTakeSalegaininfo")
	public String saveTakeSalegaininfo(Salegaininfo form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		//保存型号关联的产品类别信息
		String salegaininfoStatJson = form.getSalegaininfoStatJson();
		if(StringUtils.isEmpty(salegaininfoStatJson)){
			form.setReturninfo("请选择需要提现的员工");
			return salegaininfoStat(form);
		}
		
		 Map<String, Object> responseMap = salegaininfoService.saveTakeSalegaininfo(form, getRequest(), operator);
			
		form.setReturninfo(String.valueOf(responseMap.get("result")));
		
		return salegaininfoStat(form);
		
	}
}
