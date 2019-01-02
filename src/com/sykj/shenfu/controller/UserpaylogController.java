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
import com.sykj.shenfu.dao.IUserpaylogDao;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Salegaininfo;
import com.sykj.shenfu.po.Userbusiness;
import com.sykj.shenfu.po.Userorder;
import com.sykj.shenfu.po.Userpaylog;
import com.sykj.shenfu.service.IOperatorlogService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/userpaylog")
@Transactional
public class UserpaylogController extends BaseController {

	@Autowired
	private IUserpaylogDao userpaylogDao;
	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private IUserorderDao userorderDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private IUserbusinessDao userbusinessDao;
	
	/**
	 * 查询客户支付
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Userpaylog form) {
		return "userpaylog/findUserpaylogList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Userpaylog form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		//easyui footer数据
		List<HashMap<String, Object>> footertlist = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> footerMap = new HashMap<String, Object>();
		Integer totalpaymoney = 0;//总支付金额
		
		Integer total = userpaylogDao.findByCount(form);
		List<Userpaylog> list = userpaylogDao.findByList(form);
		for (Userpaylog userpaylog : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			//客户信息
			objectMap.put("id", userpaylog.getId());
			objectMap.put("usercode", userpaylog.getUsercode());
			objectMap.put("username", userpaylog.getUsername());
			objectMap.put("phone", userpaylog.getPhone());
			objectMap.put("source", userpaylog.getSource());
			objectMap.put("sourcename", userpaylog.getSourcename());
			objectMap.put("address", userpaylog.getAddress());
			objectMap.put("ordercode", userpaylog.getOrdercode());
			objectMap.put("paytype", userpaylog.getPaytype());
			objectMap.put("paytypename", userpaylog.getPaytypename());
			objectMap.put("payitem", userpaylog.getPayitem());
			objectMap.put("payitemname", userpaylog.getPayitemname());
			objectMap.put("paymoney", userpaylog.getPaymoney());
			objectMap.put("paytime", StringUtils.isEmpty(userpaylog.getPaytime())?"":Tools.getStr(userpaylog.getPaytime()).substring(0, 19));
			objectMap.put("checktime", StringUtils.isEmpty(userpaylog.getChecktime())?"":Tools.getStr(userpaylog.getChecktime()).substring(0, 19));
			objectMap.put("paysource", userpaylog.getPaysource());
			objectMap.put("paysourcename", userpaylog.getPaysourcename());
			objectMap.put("remark", userpaylog.getRemark());
			
			if(StringUtils.isNotEmpty(userpaylog.getReceivercode())){
				Employee receiver = employeeDao.findByEmployeecodeStr(userpaylog.getReceivercode());
				if(receiver == null){
					receiver = new Employee();
				}
				objectMap.put("receivercode", userpaylog.getReceivercode());
				objectMap.put("receivername", receiver.getEmployeename());
				objectMap.put("receiverphone", receiver.getPhone());
			}
			if(StringUtils.isNotEmpty(userpaylog.getCheckercode())){
				Employee rechecker = employeeDao.findByEmployeecodeStr(userpaylog.getCheckercode());
				if(rechecker == null){
					rechecker = new Employee();
				}
				objectMap.put("recheckercode", userpaylog.getCheckercode());
				objectMap.put("recheckername", rechecker.getEmployeename());
				objectMap.put("recheckerphone", rechecker.getPhone());
			}
			
			//计算支付总金额
			totalpaymoney = totalpaymoney + userpaylog.getPaymoney();
			
			objectlist.add(objectMap);
			
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		
		//封装Easyui的footer数据
		footerMap.put("paytypename", "支付总金额：");
		footerMap.put("paymoney", totalpaymoney);
		footertlist.add(footerMap);
		result.put("footer", footertlist);
		
		return result;
	}

	/**
	 * 添加客户支付页面初始化
	 */
	@RequestMapping(value = "/addInit")
	public String addInit(Userpaylog form) {
		return "userpaylog/addUserpaylog";
	}

	/**
	 * 保存添加客户支付
	 */
	@RequestMapping(value = "/save")
	public String save(Userpaylog form) {
		
		userpaylogDao.save(form);
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "增加客户支付信息，客户支付名称:"+form.getUsername();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		
		form.setReturninfo("保存成功");
		
		return addInit(form);
	}

	/**
	 * 编辑客户支付权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Userpaylog form) {
		form.setUserpaylog(userpaylogDao.findById(form.getId()));
		return "userpaylog/updateUserpaylog";
	}

	/**
	 * 保存编辑后客户支付权限
	 */
	@RequestMapping(value = "/update")
	public String update(Userpaylog form) {
		
		userpaylogDao.update(form);
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "修改客户支付信息，客户名称:"+form.getUsername();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}

	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Userpaylog form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Userpaylog userpaylog = userpaylogDao.findById(form.getId());
		if(userpaylog != null){
				//删除客户支付列表
				userpaylogDao.delete(form.getId());
				
				//增加操作日记
				Operator operator = (Operator)getSession().getAttribute("Operator");
				String content = "删除客户支付信息，客户名称:"+userpaylog.getUsername();
				operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
				
				responseJson.put("flag", "1");//删除成功
				responseJson.put("msg", "删除成功");
			
		}else{
			responseJson.put("flag", "0");//删除成功
			responseJson.put("msg", "删除失败，该客户支付已经不存在");
		}
		
		
		//删除客户支付
		userpaylogDao.delete(form.getId());
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "删除客户支付信息，客户名称:"+userpaylog.getUsername();
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
			Userpaylog userpaylog = userpaylogDao.findById(Integer.valueOf(id));
			
			if(userpaylog == null){
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "操作失败，选择的支付记录不存在，请先刷新页面");
				return responseMap;
			}
			
			if(!"0".equals(userpaylog.getCheckstatus())){
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "操作失败，选择的支付记录已经审核到账");
				return responseMap;
			}
			
			userpaylog.setCheckercode(operator.getEmployeecode());
			userpaylog.setChecktime(currenttime);
			userpaylog.setCheckstatus("1");
			userpaylogDao.update(userpaylog);
			
			//修改订单状态
			Userorder userorder = userorderDao.findByOrdercode(userpaylog.getOrdercode());
			
			if("1".equals(userpaylog.getPayitem())){//首付款
				userorder.setFirstarrivalflag("2");
				userorderDao.updateFirstarrival(userorder);
			}else if("2".equals(userpaylog.getPayitem())){//尾款
				userorder.setFinalarrivalflag("2");
				userorderDao.updateFinalarrival(userorder);
			}
			//修改订单付款到账
			userorderDao.updateStatus(userorder);
			
			////增加业务记录
			Userbusiness userbusiness = new Userbusiness();
			userbusiness.setOperatorcode(operator.getEmployeecode());
			userbusiness.setBusinesstypekey("firstarrival");
			userbusiness.setBusinesstypename(userpaylog.getPayitemname() + "已审核到账");
			userbusiness.setUsercode(userorder.getUsercode());
			//userbusiness.setTaskcode(userorder.getTaskcode());
			userbusiness.setOrdercode(userorder.getOrdercode());
			userbusiness.setDispatchcode(null);
			userbusiness.setAddtime(currenttime);
			userbusiness.setContent("首款到账;订单号为： "+userorder.getOrdercode()+ ";金额为:"+ userpaylog.getPaymoney());
			userbusiness.setSource("0");//PC平台操作
			userbusinessDao.save(userbusiness);
			
			////增加操作日记
			String content = userpaylog.getPayitemname() + "到账确认， 订单编号:"+userorder.getOrdercode();
			operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
			
			responseMap.put("status", "1");//审核成功
			responseMap.put("result", "审核成功");
			
		}
		
		return responseMap;
		
	}
	
	/**
	 * 操作员业务统计信息
	 */
	@RequestMapping(value = "/userpayarrivalStat")
	public String operatorBusinessStat(Userpaylog form) {
		return "userpaylog/userpayarrivalStat";
	}
	
	/**
	 * 操作员业务统计Json
	 */
	@ResponseBody
	@RequestMapping(value = "/userpayarrivalStatJson")
	public Map<String, Object> userpayarrivalStatJson(Userpaylog form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		//easyui footer数据
		List<HashMap<String, Object>> footertlist = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> footerMap = new HashMap<String, Object>();
		Integer addcount = 0;//总数
		Integer addmoney = 0;//总金
		
		List<Userpaylog> list = userpaylogDao.userpayarrivalStat(form);
		
		for (Userpaylog userpaylog : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			
				objectMap.put("paytype", userpaylog.getPaytype());
				objectMap.put("paytypename", userpaylog.getPaytypename());
				objectMap.put("totalcount", userpaylog.getTotalcount());
				objectMap.put("totalmoney", userpaylog.getTotalmoney());
				
				addcount = addcount + userpaylog.getTotalcount();
				addmoney = addmoney + userpaylog.getTotalmoney();
				objectlist.add(objectMap);
		}
		
		footerMap.put("paytypename", "总和");
		footerMap.put("totalcount", addcount);
		footerMap.put("totalmoney", addmoney);
		footertlist.add(footerMap);
		
		result.put("rows", objectlist);
		result.put("footer", footertlist);
		return result;
	}
	
	
}
