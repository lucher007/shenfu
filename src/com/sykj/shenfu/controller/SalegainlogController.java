package com.sykj.shenfu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.IEmployeeDao;
import com.sykj.shenfu.dao.ISalegainlogDao;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Product;
import com.sykj.shenfu.po.Producttype;
import com.sykj.shenfu.po.Salegaininfo;
import com.sykj.shenfu.po.Salegainlog;
import com.sykj.shenfu.service.IOperatorlogService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/salegainlog")
@Transactional
public class SalegainlogController extends BaseController {
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private ISalegainlogDao salegainlogDao;
	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private IOperatorlogService operatorlogService;
   
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
	public String findByList(Salegainlog form) {
		return "salegainlog/findSalegainlogList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Salegainlog form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		//easyui footer数据
		List<HashMap<String, Object>> footertlist = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> footerMap = new HashMap<String, Object>();
		Integer totaltainmoney = 0;//总提成
		
		Integer total = salegainlogDao.findByCount(form);
		List<Salegainlog> list = salegainlogDao.findByList(form);
		for (Salegainlog salegainlog : list) {
			
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			
			//潜在订户信息
			objectMap.put("id", salegainlog.getId());
			objectMap.put("gainlogcode", salegainlog.getGainlogcode());
			//objectMap.put("gainercode", salegainlog.getGainercode());
			//上门员信息
			Employee gainer = employeeDao.findByEmployeecodeStr(salegainlog.getGainercode());
			if(gainer == null){
				gainer = new Employee();
			}
			objectMap.put("gainercode", salegainlog.getGainercode());
			objectMap.put("gainername", gainer.getEmployeename());
			objectMap.put("gainerphone", gainer.getPhone());
			objectMap.put("gainmoney", salegainlog.getGainmoney());
			
			totaltainmoney = totaltainmoney + salegainlog.getGainmoney();
			
			objectMap.put("gaintime", StringUtils.isEmpty(salegainlog.getGaintime())?"":Tools.getStr(salegainlog.getGaintime()).substring(0, 19));
			Employee operator = employeeDao.findByEmployeecodeStr(salegainlog.getOperatorcode());
			if(operator == null){
				operator = new Employee();
			}
			objectMap.put("operatorcode", salegainlog.getOperatorcode());
			objectMap.put("operatorname", Tools.getStr(operator.getEmployeename()));
			objectMap.put("operatorphone", Tools.getStr(operator.getPhone()));
			objectMap.put("gaintype", salegainlog.getGaintype());
			objectMap.put("status", salegainlog.getStatus());
			objectMap.put("statusname", Tools.getStr(salegainlog.getStatusname()));
			objectMap.put("gaincontent", Tools.getStr(salegainlog.getGaincontent()));
			objectMap.put("remark", Tools.getStr(salegainlog.getRemark()));
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		
		//封装Easyui的footer数据
		footerMap.put("gainerphone", "已提取总金额");
		footerMap.put("gainmoney", totaltainmoney);
		footertlist.add(footerMap);
		result.put("footer", footertlist);
		return result;
	}
	
	/**
	 * 编辑产品权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Salegainlog form) {
		Salegainlog salegainlog = salegainlogDao.findById(form.getId()); 
		Employee gainer = null;
		if(StringUtils.isNotEmpty(salegainlog.getGainercode())){
			gainer = employeeDao.findByEmployeecodeStr(salegainlog.getGainercode());
		}
		if(gainer == null){
			gainer = new Employee();
		}
		salegainlog.setGainer(gainer);
		form.setSalegainlog(salegainlog);
		return "salegainlog/updateSalegainlog";
	}

	/**
	 * 保存编辑后产品权限
	 */
	@RequestMapping(value = "/update")
	public String update(Salegainlog form) {
		
		Salegainlog salegainlog = salegainlogDao.findById(form.getId());  
		if(salegainlog == null){
			form.setReturninfo("操作失败，该提现记录不存在");
			return updateInit(form);
		}
		salegainlog.setStatus(form.getStatus());
		salegainlog.setGaincontent(form.getGaincontent());
		
		salegainlogDao.update(salegainlog);
		
		//修改
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "提现记录修改，提现编号为:"+salegainlog.getGainlogcode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}
	
	
	/**
	 * 提现状态修改
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/updateStatus")
	public Map<String,Object> updateStatus(Salegainlog form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		//封装返回给页面的json对象
		Map<String, Object> responseMap = new HashMap<String,Object>();
		
		
		Salegainlog salegainlog = salegainlogDao.findById(form.getId());
		
		if(salegainlog == null){
			responseMap.put("status", "0");//保存失败
			responseMap.put("result", "操作失败，选择的提现记录不存在");//没有
			return responseMap;
		}
		
		String currentstatus = Tools.getStr(salegainlog.getStatus());
		if(currentstatus.equals(form.getStatus())){
			responseMap.put("status", "0");//保存失败
			responseMap.put("result", "操作失败，提现当前状态已经跟修改状态一样");//没有
			return responseMap;
		}
		
		salegainlog.setStatus(form.getStatus());
		salegainlogDao.updateStatus(form);
		
		responseMap.put("status", "1");//保存失败
		responseMap.put("result", "操作成功");//没有
		return responseMap;
		
	}
}
