package com.sykj.shenfu.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sykj.shenfu.util.express.GetExpressMsg;
import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.IUserDao;
import com.sykj.shenfu.dao.IUserdeliveryDao;
import com.sykj.shenfu.dao.IUserorderDao;
import com.sykj.shenfu.dao.IUserorderinfoDao;
import com.sykj.shenfu.dao.IUserproductDao;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.User;
import com.sykj.shenfu.po.Userdelivery;
import com.sykj.shenfu.po.Userorder;
import com.sykj.shenfu.po.Userorderinfo;
import com.sykj.shenfu.po.Userproduct;
import com.sykj.shenfu.service.IOperatorService;
import com.sykj.shenfu.service.IOperatorlogService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/userdelivery")
@Transactional
public class UserdeliveryController extends BaseController {

	@Autowired
	private IUserdeliveryDao userdeliveryDao;
	@Autowired
	private IUserorderDao userorderDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private IUserorderinfoDao userorderinfoDao;
	@Autowired
	private IUserproductDao userproductDao;

	/**
	 * 查询订户
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Userdelivery form) {
		return "userdelivery/findUserdeliveryList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Userdelivery form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		//封装List数据
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		Integer total = userdeliveryDao.findByCount(form);
		List<Userdelivery> list = userdeliveryDao.findByList(form);
		for (Userdelivery userdelivery : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			//潜在订户信息
			objectMap.put("id", userdelivery.getId());
			objectMap.put("usercode", userdelivery.getUsercode());
			objectMap.put("ordercode", userdelivery.getOrdercode());
			Userorder userorder = null;
			if(StringUtils.isNotEmpty(userdelivery.getOrdercode())){
				userorder = userorderDao.findByOrdercode(userdelivery.getOrdercode());
			}
			if(userorder == null ){
				userorder = new Userorder();
			}
			objectMap.put("username", Tools.getStr(userorder.getUsername()));
			objectMap.put("userphone", Tools.getStr(userorder.getPhone()));
			objectMap.put("address", Tools.getStr(userorder.getAddress()));
			objectMap.put("address", Tools.getStr(userorder.getAddress()));
			objectMap.put("totalmoney", userorder.getTotalmoney());
			objectMap.put("firstpayment", userorder.getFirstpayment());
			objectMap.put("firstarrivalflag", userorder.getFirstarrivalflag());
			objectMap.put("firstarrivalflagname", userorder.getFirstarrivalflagname());
			objectMap.put("finalpayment", userorder.getFinalpayment());
			objectMap.put("finalarrivalflag", userorder.getFinalarrivalflag());
			objectMap.put("finalarrivalflagname", userorder.getFinalarrivalflagname());
			
			List<Userproduct> userproductlist =  userproductDao.findUserproductListByOrdercode(userorder.getOrdercode());
			if(userproductlist != null && userproductlist.size() > 0){
				for (Userproduct userproduct : userproductlist) {
					if("01".equals(userproduct.getTypecode())){
						objectMap.put("productmodel", Tools.getStr(userproduct.getModelname()));
						objectMap.put("productcolor", Tools.getStr(userproduct.getProductname()));
						break;
					}
				}
			}
			objectMap.put("deliverycode", Tools.getStr(userdelivery.getDeliverycode()));
			objectMap.put("deliveryname", Tools.getStr(userdelivery.getDeliveryname()));
			objectMap.put("deliverytime", StringUtils.isEmpty(userdelivery.getDeliverytime())?"":Tools.getStr(userdelivery.getDeliverytime()).substring(0, 10));
			objectMap.put("deliverercode", Tools.getStr(userdelivery.getDeliverercode()));
			objectMap.put("deliverername", Tools.getStr(userdelivery.getDeliverername()));
			objectMap.put("delivererphone", Tools.getStr(userdelivery.getDelivererphone()));
			objectMap.put("content", Tools.getStr(userdelivery.getContent()));
			objectMap.put("status", Tools.getStr(userdelivery.getStatus()));
			objectMap.put("statusname", Tools.getStr(userdelivery.getStatusname()));
			objectMap.put("remark", userdelivery.getRemark());
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}

	/**
	 * 添加订户页面初始化
	 */
	@RequestMapping(value = "/addInit")
	public String addInit(Userdelivery form) {
		return "userdelivery/addUserdelivery";
	}

	/**
	 * 保存添加订户
	 */
	@RequestMapping(value = "/save")
	public String save(Userdelivery form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		//默认为未处理
		//form.setStatus("0");
		//form.setAddtime(Tools.getCurrentTime());
		userdeliveryDao.save(form);
		
		//增加操作日记
		String content = "添加快递单，订单号为:"+form.getOrdercode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("保存成功");
		
		return addInit(form);
	}

	/**
	 * 编辑订户权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Userdelivery form) {
		
		Userdelivery userdelivery = userdeliveryDao.findById(form.getId());
		Userorder userorder = null;
		User user = null;
		if(userdelivery != null){
			if(StringUtils.isNotEmpty(userdelivery.getOrdercode())){
				userorder = userorderDao.findByOrdercode(userdelivery.getOrdercode());
			}
			if(StringUtils.isNotEmpty(userdelivery.getUsercode())){
				user = userDao.findByUsercodeStr(userdelivery.getUsercode());
			}
			
		}
		if(userorder == null){
			userorder = new Userorder();
		}
		form.setUserorder(userorder);
		
		if(user == null){
			user = new User();
		}
		form.setUser(user);
		
		form.setUserorder(userorder);
		
		
		form.setUserdelivery(userdelivery);
		return "userdelivery/updateUserdelivery";
	}

	/**
	 * 保存编辑后订户权限
	 */
	@RequestMapping(value = "/update")
	public String update(Userdelivery form) {
		
		Operator operator = (Operator)getSession().getAttribute("Operator");
		Userdelivery userdelivery = userdeliveryDao.findById(form.getId());
		userdelivery.setDeliverytime(form.getDeliverytime());
		userdelivery.setDeliverycode(form.getDeliverycode());
		userdelivery.setDeliveryname(form.getDeliveryname());
		userdelivery.setDeliverercode(form.getDeliverercode());
		userdelivery.setDeliverername(form.getDeliverername());
		userdelivery.setDelivererphone(form.getDelivererphone());
		userdelivery.setContent(form.getContent());
		userdelivery.setStatus(form.getStatus());
		userdeliveryDao.update(userdelivery);
		//增加操作日记
		String content = "修改发货单信息，订单号为:"+userdelivery.getOrdercode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}

	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Userdelivery form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Userdelivery userdelivery = userdeliveryDao.findById(form.getId());
		
		//删除订户
		userdeliveryDao.delete(form.getId());
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "删除快递单，订单号为:"+userdelivery.getOrdercode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
	/**
	 * 查询快递信息
	 */
	@RequestMapping(value = "/findUserdeliveryInfo")
	public String findUserdeliveryInfo(Userdelivery form) {
		return "userdelivery/findUserdeliveryInfo";
	}
	
	/**查看物流跟踪记录
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/queryWuliujilu")
	@ResponseBody
	public Object queryWuliujilu(Userdelivery form){
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		String jsonStr="";
		String msg="ok";
		JSONObject json = null;
		try {
			jsonStr = GetExpressMsg.queryKuaidi100(form.getDeliverycode());
			json = new JSONObject(jsonStr);
			String data = json.toString();
			responseJson.put("value", data);
		} catch (Exception e) {
			msg="查询不到结果";
		}
		//System.out.println(jsonStr);
		//System.out.println(json.get("data"));
		responseJson.put("msg", msg);
		return responseJson;
	}
	
	
}
