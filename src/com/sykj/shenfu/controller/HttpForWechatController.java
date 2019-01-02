package com.sykj.shenfu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.IUserorderDao;
import com.sykj.shenfu.dao.IUserpaylogDao;
import com.sykj.shenfu.po.Userorder;
import com.sykj.shenfu.po.Userpaylog;
import com.sykj.shenfu.service.IHttpForMpsService;
import com.sykj.shenfu.service.IHttpForWechatService;

import fr.opensagres.xdocreport.core.utils.StringUtils;


/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/httpForWechat")
@Transactional
public class HttpForWechatController extends BaseController {

	@Autowired
	private IUserorderDao userorderDao;
	@Autowired
	private IUserpaylogDao userpaylogDao;
	@Autowired
	private IHttpForWechatService httpForWechatService;
	
	@ResponseBody
	@RequestMapping(value = "/commServerForWechat")
	// 销售服务器请求总接口
	// http://localhost:8080/shenfu/httpForMps/commServerForWechat?methodname=?&parajson={}
	public Map<String, Object> commServerForWechat(HttpServletRequest request, @RequestBody String body) {
		
		System.out.println(body);
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		String methodname = Tools.getStr(request.getParameter("methodname")).trim();
		String parajson = Tools.getStr(request.getParameter("parajson"));
		JSONObject jsonObj = null;
		try{
			jsonObj = new JSONObject(parajson);
		}catch(Exception e){
			e.printStackTrace();
			responseMap.put("status", "0");//-请输入正确的JSON请求格式
			responseMap.put("result", "请输入正确的JSON请求格式");//-
			return responseMap;
		}
		try{
			if("findUserorderInfoList".equals(methodname)){//查询订单信息
				/***************** 封装userbusiness主表 ******************/
				return findUserorderInfoList(jsonObj);
			}else if("savePaymentInfo".equals(methodname)){//微信支付
				/***************** 封装userbusiness主表 ******************/
				return savePaymentInfo(jsonObj);
			}else{
				responseMap.put("status", "0");//-请输入正确的请求格式
				responseMap.put("result", "请输入正确的请求格式");
				return responseMap;
			}
		}catch(Exception e){
			e.printStackTrace();
			responseMap.put("status", "0");//-请输入正确的请求格式
			responseMap.put("result", "请求处理失败");
			return responseMap;
		}
	}
    
	// 查询订单-根据电话号码
	// test url:
	// http://localhost:8080/shenfu/httpForWechat/commServerForWechat?methodname=findUserorderInfoList&parajson={"phone":"18980881234"}
	public Map<String, Object> findUserorderInfoList(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		//上门员编号
		String phone =  Tools.getStr(jsonObj.getString("phone"));
		if(StringUtils.isEmpty(phone)){
			responseMap.put("status", "0");//返回成功
			responseMap.put("result", "请上传电话号码");
		}
		
		//通过电话号码来查询订单
		List<Userorder> userorderlist = userorderDao.findByPhone(phone);
		for (Userorder userorder : userorderlist) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("usercode", userorder.getUsercode());
			objectMap.put("username", userorder.getUsername());
			objectMap.put("address", userorder.getAddress());
			objectMap.put("phone", userorder.getPhone());
			objectMap.put("ordercode", userorder.getOrdercode());
			objectMap.put("status", userorder.getStatus());
			objectMap.put("statusname", userorder.getStatusname());
			objectMap.put("totalmoney", userorder.getTotalmoney());
			objectMap.put("firstpayment", userorder.getFirstpayment());
			objectMap.put("firstarrivalflag", userorder.getFirstarrivalflag());
			objectMap.put("firstarrivalflagname", userorder.getFirstarrivalflagname());
			objectMap.put("finalpayment", userorder.getFinalpayment());
			objectMap.put("finalarrivalflag", userorder.getFinalarrivalflag());
			objectMap.put("finalarrivalflagname", userorder.getFinalarrivalflagname());
			objectMaplist.add(objectMap);
		}
		
		responseMap.put("status", "1");//返回成功
		responseMap.put("result", "请求成功");
		responseMap.put("userorderinfolist", objectMaplist);
		
		return responseMap;
	}
	
	// APP付款
	// http://115.159.71.126:8080/shenfu/httpForMps/commServerForSaleMPS?methodname=operatorLogin&parajson={"user_phone":"123456789","password":"123456"}
	public Map<String, Object> savePaymentInfo(JSONObject jsonObj) {
		return httpForWechatService.savePaymentInfo(jsonObj);
	}
	
}
