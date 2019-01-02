package com.sykj.shenfu.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sykj.shenfu.common.PrimaryGenerater;
import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.IEmployeeDao;
import com.sykj.shenfu.dao.IUserrepairDao;
import com.sykj.shenfu.dao.IProductdepotDao;
import com.sykj.shenfu.dao.IProductinoutDao;
import com.sykj.shenfu.dao.IUserDao;
import com.sykj.shenfu.dao.IUserorderDao;
import com.sykj.shenfu.dao.IUserproductDao;
import com.sykj.shenfu.po.Coderule;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.User;
import com.sykj.shenfu.po.Userrepair;
import com.sykj.shenfu.po.Productdepot;
import com.sykj.shenfu.po.Productinout;
import com.sykj.shenfu.po.Userorder;
import com.sykj.shenfu.po.Userproduct;
import com.sykj.shenfu.service.ICoderuleService;
import com.sykj.shenfu.service.IOperatorService;
import com.sykj.shenfu.service.IOperatorlogService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/userrepair")
@Transactional
public class UserrepairController extends BaseController {
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private IUserrepairDao userrepairDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private IOperatorService operatorService;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private IUserorderDao userorderDao;
	@Autowired
	private IProductdepotDao productdepotDao;
	@Autowired
	private IProductinoutDao productinputDao;
	@Autowired
	private IUserproductDao userproductDao;
	@Autowired
	private ICoderuleService coderuleService;
   
	/*
     * 特殊的instance变量
     * 注：零长度的byte数组对象创建起来将比任何对象都经济――查看编译后的字节码：
     * 生成零长度的byte[]对象只需3条操作码，而Object lock = new Object()则需要7行操作码。
     */
    private static byte[] lock = new byte[0];   
	
	/**
	 * 查询派维修单
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Userrepair form) {
		return "userrepair/findUserrepairList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Userrepair form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		
		Integer total = userrepairDao.findByCount(form);
		List<Userrepair> list = userrepairDao.findByList(form);
		for (Userrepair userrepair : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			
			//维修单信息
			objectMap.put("id", userrepair.getId());
			objectMap.put("repaircode", userrepair.getRepaircode());
			objectMap.put("addtime", userrepair.getAddtime());
			objectMap.put("repairtime", userrepair.getRepairtime());
			objectMap.put("content", userrepair.getContent());
			objectMap.put("dealdate", userrepair.getDealdate());
			objectMap.put("dealresult", userrepair.getDealresult());
			objectMap.put("status", userrepair.getStatus());
			objectMap.put("remark", userrepair.getRemark());
			
			//订单信息
			Userorder userorder = userorderDao.findByOrdercode(userrepair.getOrdercode());
			if(userorder == null){
				userorder = new Userorder();
			}
			objectMap.put("ordercode", userrepair.getOrdercode());
			objectMap.put("usercode", userorder.getUsercode());
			objectMap.put("username", userorder.getUsername());
			objectMap.put("phone", userorder.getPhone());
			objectMap.put("usersex", userorder.getUsersex());
			objectMap.put("address", userorder.getAddress());
			
			
			//维修产品信息
			Userproduct userproduct = userproductDao.findByProductidentfy(userrepair.getProductidentfy());
			if(userproduct == null){
				userproduct = new Userproduct();
			}
			objectMap.put("productidentfy", userproduct.getProductidentfy());
			objectMap.put("productname", userproduct.getProductname());
			objectMap.put("modelname", userproduct.getModelname());
			objectMap.put("ordercode", userproduct.getOrdercode());
			
			//维修人信息
			Employee repairer = employeeDao.findByEmployeecodeStr(userrepair.getRepairercode());
			if(repairer == null){
				repairer = new Employee();
			}
			objectMap.put("installerid", userrepair.getRepairercode());
			objectMap.put("installername", repairer.getEmployeename());
			objectMap.put("installerphone", repairer.getPhone());
			
			 //操作员信息
			Operator operator = operatorService.findByEmployeecode(userrepair.getOperatorcode());
			if(operator == null){
				operator = new Operator();
			}
			objectMap.put("operatorid", userrepair.getOperatorcode());
			objectMap.put("operatorname", operator.getEmployee().getEmployeename());
			
			
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}

	/**
	 * 添加派维修单页面初始化
	 */
	@RequestMapping(value = "/addInit")
	public String addInit(Userrepair form) {
		return "userrepair/addUserrepair";
	}

	/**
	 * 保存添加 派维修单
	 */
	@RequestMapping(value = "/save")
	public String save(Userrepair form) {
		
		//加锁
		synchronized(lock) {
			
			Operator operator = (Operator)getSession().getAttribute("Operator");
		
			form.setOperatorcode(operator.getEmployeecode());
		
			//报修单
			String repaircode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_repaircode);
			form.setRepaircode(repaircode);
			form.setAddtime(Tools.getCurrentTime());
			form.setStatus("0");
			userrepairDao.save(form);
			
			//增加操作日记
			String content = "添加维修单， 维修单号:"+form.getRepaircode();
			operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		} 
		
		form.setReturninfo("保存成功");
		
		return addInit(form);
	}

	/**
	 * 编辑 订单权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Userrepair form) {
		Userrepair userrepair = userrepairDao.findById(form.getId());
		//封装订户
		userrepair.setUser(userDao.findByUsercodeStr(userrepair.getUsercode()));
		//封装产品
		Userproduct userproduct = userproductDao.findByProductidentfy(userrepair.getProductidentfy());
		if(userproduct == null){
			userproduct = new Userproduct();
		}
		userrepair.setUserproduct(userproduct);
		
		//封装维修人
		Employee repairer = employeeDao.findByEmployeecodeStr(userrepair.getRepairercode());
		if(repairer == null){
			repairer = new Employee();
		}
		userrepair.setRepairer(repairer);
		
		form.setUserrepair(userrepair);
		
		return "userrepair/updateUserrepair";
	}

	/**
	 * 保存编辑后 订单权限
	 */
	@RequestMapping(value = "/update")
	public String update(Userrepair form) {
		
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		userrepairDao.update(form);
		//增加操作日记
		String content = "修改维修单信息， 派维修单号:"+form.getRepaircode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}

	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Userrepair form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Userrepair userrepair = userrepairDao.findById(form.getId());
		
		//删除维修单
		userrepairDao.delete(form.getId());
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "删除维修单， 维修单号:"+userrepair.getRepaircode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
}
