package com.sykj.shenfu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.IEmployeeDao;
import com.sykj.shenfu.dao.IUserorderDao;
import com.sykj.shenfu.dao.IUserproductDao;
import com.sykj.shenfu.dao.IUserDao;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Materialdepot;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Product;
import com.sykj.shenfu.po.Productdepot;
import com.sykj.shenfu.po.Productmodel;
import com.sykj.shenfu.po.Productmodelref;
import com.sykj.shenfu.po.Userorder;
import com.sykj.shenfu.po.Userproduct;
import com.sykj.shenfu.service.IOperatorService;
import com.sykj.shenfu.service.IOperatorlogService;
import com.sykj.shenfu.service.IProductdepotService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/userproduct")
@Transactional
public class UserproductController extends BaseController {

	@Autowired
	private IUserproductDao userproductDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private IOperatorService operatorService;
	@Autowired
	private IProductdepotService productdepotService;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private IUserorderDao userorderDao;
    
	/*
     * 特殊的instance变量
     * 注：零长度的byte数组对象创建起来将比任何对象都经济――查看编译后的字节码：
     * 生成零长度的byte[]对象只需3条操作码，而Object lock = new Object()则需要7行操作码。
     */
    private static byte[] lock = new byte[0];   
	
	/**
	 * 查询 订单产品
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Userproduct form) {
		return "userproduct/findUserproductList";
	}
	
	/**
	 * 查询订单产品信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Userproduct form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Integer total = userproductDao.findByCount(form);
		List<Userproduct> list = userproductDao.queryByList(form);
		for (Userproduct userproduct : list) {

			Employee inouter = employeeDao.findByEmployeecodeStr(userproduct.getInoutercode());
			if(inouter == null){
				inouter = new Employee();
			}
			
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			
			//产品信息
			objectMap.put("id", userproduct.getId());
			objectMap.put("productidentfy", userproduct.getProductidentfy());
			objectMap.put("modelcode", userproduct.getModelcode());
			objectMap.put("modelname", userproduct.getModelname());
			objectMap.put("typecode", userproduct.getTypecode());
			objectMap.put("typename", userproduct.getTypename());
			objectMap.put("productname", userproduct.getProductname());
			objectMap.put("productcode", userproduct.getProductcode());
			objectMap.put("productunit", userproduct.getProductunit());
			objectMap.put("price", userproduct.getPrice());
			objectMap.put("saleprice", userproduct.getSaleprice());
			objectMap.put("addtime", StringUtils.isEmpty(userproduct.getAddtime())?"":Tools.getStr(userproduct.getAddtime()).substring(0, 19));
			//出入库人信息
			objectMap.put("inoutercode",userproduct.getInoutercode());
			objectMap.put("inoutername",inouter.getEmployeename());
			objectMap.put("inouterphone", inouter.getPhone());
			objectMap.put("buytype", userproduct.getBuytype());
			objectMap.put("buytypename", userproduct.getBuytypename());
			objectMap.put("depotrackcode", userproduct.getDepotrackcode());
			objectMap.put("productversion", userproduct.getProductversion());
			objectMap.put("remark", userproduct.getRemark());
			
			//订单信息
			Userorder userorder = userorderDao.findByOrdercode(userproduct.getOrdercode());
			if(userorder == null){
				userorder = new Userorder();
			}
			objectMap.put("ordercode", userproduct.getOrdercode());
			objectMap.put("usercode", userorder.getUsercode());
			objectMap.put("username", userorder.getUsername());
			objectMap.put("phone", userorder.getPhone());
			objectMap.put("usersex", userorder.getUsersex());
			objectMap.put("address", userorder.getAddress());
			
			objectlist.add(objectMap);
			
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}
 
	
	/**
	 * 查询客户需要上门测量的产品信息
	 */
	@ResponseBody
	@RequestMapping(value = "/findVolidUserproductListJson")
	public Map<String, Object> findVolidUserproductListJson(Userproduct form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		form.setProductsource("1");
		Integer total = userproductDao.findByCount(form);
		List<Userproduct> userproductlist = userproductDao.queryByList(form);
		
		for (Userproduct userproduct : userproductlist) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			//产品信息
			objectMap.put("modelcode", userproduct.getModelcode());
			objectMap.put("modelname", userproduct.getModelname());
			objectMap.put("typecode", userproduct.getTypecode());
			objectMap.put("typename", userproduct.getTypename());
			objectMap.put("productcode", userproduct.getProductcode());
			objectMap.put("productname", userproduct.getProductname());
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}
	
	/**产品打包
	 * 查询订单产品信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJsonForPack")
	public Map<String, Object> findListJsonForPack(Userproduct form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Integer total = userproductDao.findByCount(form);
		List<Userproduct> list = userproductDao.queryByList(form);
		
		//查询库存中的产品库存
		Productdepot productdepotForm = new Productdepot();
		productdepotForm.setDepotstatus("1");//有库存
		productdepotForm.setProductstatus("1");//正常
		HashMap<String,ArrayList<Productdepot>> productdepotHp = productdepotService.findProductdepotHashmap(productdepotForm);
		
		for (Userproduct userproduct : list) {

			Employee inouter = employeeDao.findByEmployeecodeStr(userproduct.getInoutercode());
			if(inouter == null){
				inouter = new Employee();
			}
			
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			
			//产品信息
			objectMap.put("id", userproduct.getId());
			objectMap.put("productidentfy", userproduct.getProductidentfy());
			objectMap.put("modelname", userproduct.getModelname());
			objectMap.put("modelcode", userproduct.getModelcode());
			objectMap.put("productname", userproduct.getProductname());
			objectMap.put("productcode", userproduct.getProductcode());
			objectMap.put("productunit", userproduct.getProductunit());
			objectMap.put("price", userproduct.getPrice());
			objectMap.put("saleprice", userproduct.getSaleprice());
			objectMap.put("addtime", StringUtils.isEmpty(userproduct.getAddtime())?"":Tools.getStr(userproduct.getAddtime()).substring(0, 19));
			//出入库人信息
			objectMap.put("inoutercode",userproduct.getInoutercode());
			objectMap.put("inoutername",inouter.getEmployeename());
			objectMap.put("inouterphone", inouter.getPhone());
			
			objectMap.put("buytype", userproduct.getBuytype());
			objectMap.put("buytypename", userproduct.getBuytypename());
			objectMap.put("depotrackcode", userproduct.getDepotrackcode());
			objectMap.put("remark", userproduct.getRemark());
			//订单信息
			Userorder userorder = userorderDao.findByOrdercode(userproduct.getOrdercode());
			if(userorder == null){
				userorder = new Userorder();
			}
			objectMap.put("ordercode", userproduct.getOrdercode());
			objectMap.put("usercode", userorder.getUsercode());
			objectMap.put("username", userorder.getUsername());
			objectMap.put("phone", userorder.getPhone());
			objectMap.put("usersex", userorder.getUsersex());
			objectMap.put("address", userorder.getAddress());
			
			//自动找出库存中符合出库条件的最早的批次号
			productdepotForm.setProductcode(userproduct.getProductcode());
			productdepotForm.setInoutamount(1);
			Productdepot procutdepotOldest = productdepotService.findProductdepotOldest(productdepotHp, productdepotForm);
			if(procutdepotOldest != null){
				objectMap.put("productidentfy", procutdepotOldest.getProductidentfy());
				objectMap.put("depotamount", procutdepotOldest.getDepotamount());
				objectMap.put("depotrackcode", procutdepotOldest.getDepotrackcode());
				objectMap.put("result", "1");
			}
			
			objectlist.add(objectMap);
			
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}
	/**
	 * 添加 订单产品页面初始化
	 */
	@RequestMapping(value = "/addInit")
	public String addInit(Userproduct form) {
		return "userproduct/addUserproduct";
	}

	/**
	 * 保存添加 订单产品
	 */
	@RequestMapping(value = "/save")
	public String save(Userproduct form) {
		
		//加锁
		synchronized(lock) {
			Operator operator = (Operator)getSession().getAttribute("Operator");
			
		} 
		
		form.setReturninfo("保存成功");
		
		return addInit(form);
	}

	/**
	 * 编辑 订单产品权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Userproduct form) {
		
		return "userproduct/updateUserproduct";
	}

	/**
	 * 保存编辑后 订单产品权限
	 */
	@RequestMapping(value = "/update")
	public String update(Userproduct form) {
		
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}

	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Userproduct form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Userproduct userproduct = userproductDao.findById(form.getId());
		
		//删除 订单产品
		userproductDao.delete(form.getId());
		
		
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
	/**
	 * 查询订单产品信息
	 */
	@RequestMapping(value = "/findUserproductListDialog")
	public String findUserproductListDialog(Userproduct form) {
		return "userproduct/findUserproductListDialog";
	}
	
}
