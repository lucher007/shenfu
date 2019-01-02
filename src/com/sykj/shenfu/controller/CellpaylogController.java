package com.sykj.shenfu.controller;

import java.math.BigDecimal;
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
import com.sykj.shenfu.dao.IUserbusinessDao;
import com.sykj.shenfu.dao.IUserorderDao;
import com.sykj.shenfu.dao.ICellpaylogDao;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Salegaininfo;
import com.sykj.shenfu.po.Userbusiness;
import com.sykj.shenfu.po.Userorder;
import com.sykj.shenfu.po.Cellpaylog;
import com.sykj.shenfu.service.IOperatorlogService;

/**
 * 小区扫楼支付控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/cellpaylog")
@Transactional
public class CellpaylogController extends BaseController {

	@Autowired
	private ICellpaylogDao cellpaylogDao;
	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private IUserorderDao userorderDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private IUserbusinessDao userbusinessDao;
	
	/**
	 * 查询小区扫楼支付
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Cellpaylog form) {
		return "cellpaylog/findCellpaylogList";
	}
	
	/**
	 * 查询小区扫楼支付信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Cellpaylog form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		//easyui footer数据
		List<HashMap<String, Object>> footertlist = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> footerMap = new HashMap<String, Object>();
		Integer totalpaymoney = 0;//总支付金额
		
		Integer total = cellpaylogDao.findByCount(form);
		List<Cellpaylog> list = cellpaylogDao.findByList(form);
		for (Cellpaylog cellpaylog : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			//客户信息
			objectMap.put("id", cellpaylog.getId());
			objectMap.put("extendcode", cellpaylog.getExtendercode());
			objectMap.put("cellcode", cellpaylog.getCellcode());
			objectMap.put("cellname", cellpaylog.getCellname());
			objectMap.put("address", cellpaylog.getAddress());
			objectMap.put("totalhouse", cellpaylog.getTotalhouse());
			objectMap.put("price", cellpaylog.getPrice()/(double)100);
			objectMap.put("totalmoney", cellpaylog.getTotalmoney()/(double)100);
			objectMap.put("extendercode", cellpaylog.getExtendercode());
			if(StringUtils.isNotEmpty(cellpaylog.getExtendercode())){
				Employee extender = employeeDao.findByEmployeecodeStr(cellpaylog.getExtendercode());
				if(extender != null){
					objectMap.put("extendername", extender.getEmployeename());
					objectMap.put("extenderphone", extender.getPhone());
				}
			}
			objectMap.put("extendtime", StringUtils.isEmpty(cellpaylog.getExtendtime())?"":Tools.getStr(cellpaylog.getExtendtime()).substring(0, 19));
			objectMap.put("starttime", StringUtils.isEmpty(cellpaylog.getStarttime())?"":Tools.getStr(cellpaylog.getStarttime()).substring(0, 19));
			objectMap.put("endtime", StringUtils.isEmpty(cellpaylog.getEndtime())?"":Tools.getStr(cellpaylog.getEndtime()).substring(0, 19));
			
			objectMap.put("acceptercode", cellpaylog.getAcceptercode());
			if(StringUtils.isNotEmpty(cellpaylog.getAcceptercode())){
				Employee accepter = employeeDao.findByEmployeecodeStr(cellpaylog.getAcceptercode());
				if(accepter != null){
					objectMap.put("acceptername", accepter.getEmployeename());
					objectMap.put("accepterphone", accepter.getPhone());
				}
			}
			objectMap.put("acceptertime", StringUtils.isEmpty(cellpaylog.getAcceptertime())?"":Tools.getStr(cellpaylog.getAcceptertime()).substring(0, 19));
			
			objectMap.put("payercode", cellpaylog.getPayercode());
			if(StringUtils.isNotEmpty(cellpaylog.getPayercode())){
				Employee payer = employeeDao.findByEmployeecodeStr(cellpaylog.getPayercode());
				if(payer != null){
					objectMap.put("payername", payer.getEmployeename());
					objectMap.put("payerphone", payer.getPhone());
				}
			}
			objectMap.put("paytime", StringUtils.isEmpty(cellpaylog.getPaytime())?"":Tools.getStr(cellpaylog.getPaytime()).substring(0, 19));
			objectMap.put("paytype", cellpaylog.getPaytype());
			objectMap.put("paytypename", cellpaylog.getPaytypename());
			objectMap.put("paymoney", cellpaylog.getPaymoney()/(double)100);
			objectMap.put("paysource", cellpaylog.getPaysource());
			objectMap.put("paysourcename", cellpaylog.getPaysourcename());
			objectMap.put("checkercode", cellpaylog.getCheckercode());
			if(StringUtils.isNotEmpty(cellpaylog.getCheckercode())){
				Employee checker = employeeDao.findByEmployeecodeStr(cellpaylog.getCheckercode());
				if(checker != null){
					objectMap.put("checkername", checker.getEmployeename());
					objectMap.put("checkerphone", checker.getPhone());
				}
			}
			objectMap.put("checktime", StringUtils.isEmpty(cellpaylog.getChecktime())?"":Tools.getStr(cellpaylog.getChecktime()).substring(0, 19));
			
			objectMap.put("remark", cellpaylog.getRemark());
			
			//计算支付总金额
			totalpaymoney = totalpaymoney + cellpaylog.getPaymoney();
			
			objectlist.add(objectMap);
			
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		
		//封装Easyui的footer数据
		footerMap.put("paytypename", "支付总金额：");
		footerMap.put("paymoney", totalpaymoney/(double)100);
		footertlist.add(footerMap);
		result.put("footer", footertlist);
		
		return result;
	}

	/**
	 * 添加小区扫楼支付初始化
	 */
	@RequestMapping(value = "/addInit")
	public String addInit(Cellpaylog form) {
		return "cellpaylog/addCellpaylog";
	}

	/**
	 * 保存添加小区扫楼支付
	 */
	@RequestMapping(value = "/save")
	public String save(Cellpaylog form) {
		
		cellpaylogDao.save(form);
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "增加小区扫楼支付信息，小区名称:"+form.getCellname();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		
		form.setReturninfo("保存成功");
		
		return addInit(form);
	}

	/**
	 * 编辑小区扫楼支付权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Cellpaylog form) {
		form.setCellpaylog(cellpaylogDao.findById(form.getId()));
		return "cellpaylog/updateCellpaylog";
	}

	/**
	 * 保存编辑后小区扫楼支付权限
	 */
	@RequestMapping(value = "/update")
	public String update(Cellpaylog form) {
		
		cellpaylogDao.update(form);
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "修改小区扫楼支付信息，客户名称:"+form.getCellname();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}

	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Cellpaylog form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Cellpaylog cellpaylog = cellpaylogDao.findById(form.getId());
		if(cellpaylog != null){
				//删除小区扫楼支付列表
				cellpaylogDao.delete(form.getId());
				
				//增加操作日记
				Operator operator = (Operator)getSession().getAttribute("Operator");
				String content = "删除小区扫楼支付信息，客户名称:"+cellpaylog.getCellname();
				operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
				
				responseJson.put("flag", "1");//删除成功
				responseJson.put("msg", "删除成功");
			
		}else{
			responseJson.put("flag", "0");//删除成功
			responseJson.put("msg", "删除失败，该小区扫楼支付已经不存在");
		}
		
		
		//删除小区扫楼支付
		cellpaylogDao.delete(form.getId());
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "删除小区扫楼支付信息，客户名称:"+cellpaylog.getCellname();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
	/**
	 * 审核支付款到账
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/updateCheckstatus")
	public Map<String,Object> updateCheckstatus(Salegaininfo form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		//封装返回给页面的json对象
		Map<String, Object> responseMap = new HashMap<String,Object>();
		
		String ids  = Tools.getStr(getRequest().getParameter("ids"));
		String[] idArray = ids.split(",");
		if(idArray == null || idArray.length < 1){
			responseMap.put("status", "0");//保存失败
			responseMap.put("result", "请选择需要审核到账的支付信息");
			return responseMap;
		}
		
		String currenttime = Tools.getCurrentTime();
		
		for (String id : idArray) {
			Cellpaylog cellpaylog = cellpaylogDao.findById(Integer.valueOf(id));
			if(cellpaylog != null){
				cellpaylog.setCheckercode(operator.getEmployeecode());
				cellpaylog.setChecktime(currenttime);
				cellpaylog.setCheckstatus("1");
				cellpaylogDao.update(cellpaylog);
				
				
				////增加操作日记
				String content = "小区扫楼支付到账确认， 小区名称:"+cellpaylog.getCellname();
				operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
				
				responseMap.put("status", "1");//审核成功
				responseMap.put("result", "审核成功");
			}
		}
		
		return responseMap;
		
	}
	
	/**
	 * 操作员业务统计信息
	 */
	@RequestMapping(value = "/cellpayStat")
	public String cellpayStat(Cellpaylog form) {
		return "cellpaylog/cellpayStat";
	}
	
	/**
	 * 操作员业务统计Json
	 */
	@ResponseBody
	@RequestMapping(value = "/cellpayStatJson")
	public Map<String, Object> cellpayStatJson(Cellpaylog form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		//easyui footer数据
		List<HashMap<String, Object>> footertlist = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> footerMap = new HashMap<String, Object>();
		Integer addcount = 0;//总数
		Integer addmoney = 0;//总金
		
		List<Cellpaylog> list = cellpaylogDao.cellpayarrivalStat(form);
		
		for (Cellpaylog cellpaylog : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			
				objectMap.put("paytype", cellpaylog.getPaytype());
				objectMap.put("paytypename", cellpaylog.getPaytypename());
				objectMap.put("totalcount", cellpaylog.getTotalcount());
				objectMap.put("totalmoney", cellpaylog.getTotalmoney()/(double)100);
				
				addcount = addcount + cellpaylog.getTotalcount();
				addmoney = addmoney + cellpaylog.getTotalmoney();
				objectlist.add(objectMap);
		}
		
		footerMap.put("paytypename", "总和");
		footerMap.put("totalcount", addcount);
		footerMap.put("totalmoney", addmoney/(double)100);
		footertlist.add(footerMap);
		
		result.put("rows", objectlist);
		result.put("footer", footertlist);
		return result;
	}
	
	
}
