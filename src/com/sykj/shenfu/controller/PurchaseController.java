package com.sykj.shenfu.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sykj.shenfu.common.PrimaryGenerater;
import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.IEmployeeDao;
import com.sykj.shenfu.dao.IMaterialDao;
import com.sykj.shenfu.dao.IPurchaseDao;
import com.sykj.shenfu.dao.IPurchasedetailDao;
import com.sykj.shenfu.dao.ISupplierDao;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Materialdepot;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Purchase;
import com.sykj.shenfu.po.Purchasedetail;
import com.sykj.shenfu.po.Supplier;
import com.sykj.shenfu.po.Userorder;
import com.sykj.shenfu.service.IMaterialdepotService;
import com.sykj.shenfu.service.IOperatorService;
import com.sykj.shenfu.service.IOperatorlogService;
import com.sykj.shenfu.service.IPurchaseService;

import fr.opensagres.xdocreport.core.utils.StringUtils;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/purchase")
@Transactional
public class PurchaseController extends BaseController {

	@Autowired
	private IPurchaseDao purchaseDao;
	@Autowired
	private IPurchasedetailDao purchasedetailDao;
	@Autowired
	private ISupplierDao supplierDao;
	@Autowired
	private IOperatorService operatorService;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private IMaterialDao materialDao;
	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private IMaterialdepotService materialdepotService;
	@Autowired
	private IPurchaseService purchaseService;
	
	
	/*
     * 特殊的instance变量
     * 注：零长度的byte数组对象创建起来将比任何对象都经济――查看编译后的字节码：
     * 生成零长度的byte[]对象只需3条操作码，而Object lock = new Object()则需要7行操作码。
     */
    private static byte[] lock = new byte[0];   
	
	/**
	 * 查询订户
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Purchase form) {
		return "purchase/findPurchaseList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Purchase form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		//封装List数据
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		Integer total = purchaseDao.findByCount(form);
		List<Purchase> list = purchaseDao.findByList(form);
		for (Purchase purchase : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			//潜在订户信息
			objectMap.put("id", purchase.getId());
			objectMap.put("purchasecode", purchase.getPurchasecode());
			objectMap.put("supplierid", purchase.getSupplierid());
			objectMap.put("suppliername", purchase.getSuppliername());
			objectMap.put("supplierinfo", purchase.getSupplierinfo());
			objectMap.put("materailmoney", purchase.getMaterailmoney());
			objectMap.put("deliverymoney", purchase.getDeliverymoney());
			objectMap.put("discountmoney", purchase.getDiscountmoney());
			objectMap.put("totalmoney", purchase.getTotalmoney());
			objectMap.put("plandelivertime", StringUtils.isEmpty(purchase.getPlandelivertime())?"":Tools.getStr(purchase.getPlandelivertime()).substring(0, 10));
			objectMap.put("planarrivaltime", StringUtils.isEmpty(purchase.getPlanarrivaltime())?"":Tools.getStr(purchase.getPlanarrivaltime()).substring(0, 10));
			objectMap.put("actualdelivertime", StringUtils.isEmpty(purchase.getActualdelivertime())?"":Tools.getStr(purchase.getActualdelivertime()).substring(0, 19));
			objectMap.put("actualarrivaltime", StringUtils.isEmpty(purchase.getActualarrivaltime())?"":Tools.getStr(purchase.getActualarrivaltime()).substring(0, 19));
			objectMap.put("status", purchase.getStatus());
			objectMap.put("statusname", purchase.getStatusname());
			//操作员信息
			if(StringUtils.isNotEmpty(purchase.getOperatorcode())){
				Employee operator = employeeDao.findByEmployeecodeStr(purchase.getOperatorcode());
				if(operator != null){
					objectMap.put("operatorcode", purchase.getOperatorcode());
					objectMap.put("operatorname", operator.getEmployeename());
					objectMap.put("operatorphone", operator.getPhone());
				}
			}
			objectMap.put("addtime", StringUtils.isEmpty(purchase.getAddtime())?"":Tools.getStr(purchase.getAddtime()).substring(0, 19));
			objectMap.put("billtype", purchase.getBilltype());
			objectMap.put("billtypename", purchase.getBilltypename());
			objectMap.put("billstatus", purchase.getBillstatus());
			objectMap.put("billstatusname", purchase.getBillstatusname());
			objectMap.put("invoicecode", Tools.getStr(purchase.getInvoicecode()));
			objectMap.put("remark", Tools.getStr(purchase.getRemark()));
			
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
	public String addInit(Purchase form) {
		return "purchase/addPurchase";
	}

	/**
	 * 保存添加订户
	 */
	@RequestMapping(value = "/save")
	public String save(Purchase form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		String[] types = getRequest().getParameterValues("type");
		String[] materialcodes = getRequest().getParameterValues("materialcode");
		String[] materialnames = getRequest().getParameterValues("materialname");
		String[] models = getRequest().getParameterValues("model");
		String[] prices = getRequest().getParameterValues("price");
		String[] amounts = getRequest().getParameterValues("amount");
		String[] moneys = getRequest().getParameterValues("money");
		

		if (materialcodes == null || materialcodes.length == 0 || materialnames == null || materialnames.length == 0) {
			form.setReturninfo("请增加采购物料信息");
			return addInit(form);
		}
		
		//加锁
		synchronized(lock) {
			//最大订单号
			String maxOrdercode = purchaseDao.findMaxPurchasecode();
			form.setPurchasecode(PrimaryGenerater.generaterNextNumber(maxOrdercode));
			form.setOperatorcode(operator.getEmployeecode());
			form.setAddtime(Tools.getCurrentTime());
			form.setStatus("0");//未到货
			form.setBillstatus("0");//票据未到
			form.setInvoicecode("");//
			
			purchaseDao.save(form);
			
			//增加采购明细物料信息
			for (int i = 0; i < materialcodes.length; i++) {
				Purchasedetail purchasedetail = new Purchasedetail();
				purchasedetail.setPurchaseid(form.getId());
				purchasedetail.setPurchasecode(form.getPurchasecode());
				purchasedetail.setType(types[i]);
				purchasedetail.setMaterialcode(materialcodes[i]);
				purchasedetail.setMaterialname(materialnames[i]);
				purchasedetail.setModel(models[i]);
				purchasedetail.setPrice(new Double(prices[i]));
				purchasedetail.setAmount(new Double(amounts[i]));
				purchasedetail.setMoney(new Double(moneys[i]));
				
				purchasedetailDao.save(purchasedetail);
			}
			
			//增加操作日记
			String content = "添加 采购单， 采购单号:"+form.getPurchasecode();
			operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		} 
		
		form.setReturninfo("保存成功");
		
		return addInit(form);
	}
	
	/**
	 * 查看 订单权限初始化页面
	 */
	@RequestMapping(value = "/lookInit")
	public String lookInit(Purchase form) {
		Purchase purchase = purchaseDao.findById(form.getId());
		//封装供货商
		Supplier supplier = null;
		if(purchase.getSupplierid() != null){
			supplier = supplierDao.findById(purchase.getSupplierid());
		}
		if(supplier == null){
			supplier = new Supplier();
		}
		purchase.setSupplier(supplier);
		
		form.setPurchase(purchase);
		return "purchase/lookPurchase";
	}
	
	/**
	 * 编辑订户权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Purchase form) {
		form.setPurchase(purchaseDao.findById(form.getId()));
		return "purchase/updatePurchase";
	}

	/**
	 * 保存编辑后订户权限
	 */
	@RequestMapping(value = "/update")
	public String update(Purchase form) {
		
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		String[] materialids = getRequest().getParameterValues("materialid");
		String[] materialcodes = getRequest().getParameterValues("materialcode");
		String[] materialnames = getRequest().getParameterValues("materialname");
		String[] prices = getRequest().getParameterValues("price");
		String[] amounts = getRequest().getParameterValues("amount");
		String[] moneys = getRequest().getParameterValues("money");
		
		if (materialids == null || materialids.length == 0 || materialnames == null || materialnames.length == 0) {
			form.setReturninfo("请增加采购物料");
			return updateInit(form);
		}
		
		//保存采购单修改
		purchaseDao.update(form);
		//增加采购单明细物料信息
		//1-先删除采购单明细物料
		purchasedetailDao.deleteByPurchaseid(form.getId());
		//2-更新采购单的物料明细
		for (int i = 0; i < materialids.length; i++) {
			Purchasedetail purchasedetail = new Purchasedetail();
			purchasedetail.setPurchaseid(form.getId());
			purchasedetail.setPurchasecode(form.getPurchasecode());
			purchasedetail.setMaterialcode(materialcodes[i]);
			purchasedetail.setMaterialname(materialnames[i]);
			purchasedetail.setPrice(new Double(prices[i]));
			purchasedetail.setAmount(new Double(amounts[i]));
			purchasedetail.setMoney(new Double(moneys[i]));
			purchasedetailDao.save(purchasedetail);
		}
		
		//增加操作日记
		String content = "修改采购单信息， 采购单号:"+form.getPurchasecode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}

	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Purchase form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Purchase purchase = purchaseDao.findById(form.getId());
		
		//删除 采购单
		purchaseDao.delete(form.getId());
		
		//删除采购单物料明细
		purchasedetailDao.deleteByPurchaseid(form.getId());
		
		//增加操作日记
		String content = "删除 采购单， 采购单号:"+purchase.getPurchasecode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
	/**
	 * 查询采购单-弹出框
	 */
	@RequestMapping(value = "/findPurchaseListDialog")
	public String findPurchaseListDialog(Purchase form) {
		return "purchase/findPurchaseListDialog";
	}
	
	/**
	 * 查询采购单-采购单到货
	 */
	@RequestMapping(value = "/findPurchaseListForArrival")
	public String findPurchaseListForArrival(Purchase form) {
		return "purchase/findPurchaseListForArrival";
	}
	
	/**
	 * 确认采购单到货
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/updatePurchaseArrival")
	public Map<String,Object> updatePurchaseArrival(Userorder form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Purchase purchase =purchaseDao.findById(form.getId());
		
		purchase.setStatus("1");
		purchase.setActualarrivaltime(Tools.getCurrentTime());
		
		//修改订单首付款到账
		purchaseDao.update(purchase);
		
		//增加操作日记
		String content = "采购单到货， 采购单编号:"+purchase.getPurchasecode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "操作成功");
		
		return responseJson;
	}
	
	/**
	 * 查询采购单-采购单入库
	 */
	@RequestMapping(value = "/findPurchaseListForIndepot")
	public String findPurchaseListForIndepot(Purchase form) {
		return "purchase/findPurchaseListForIndepot";
	}
	
	/**
	 * 查看 订单权限初始化页面
	 */
	@RequestMapping(value = "/indepotPurchaseInit")
	public String indepotPurchaseInit(Purchase form) {
		Purchase purchase = purchaseDao.findById(form.getId());
		//封装供货商
		Supplier supplier = null;
		if(purchase.getSupplierid() != null){
			supplier = supplierDao.findById(purchase.getSupplierid());
		}
		if(supplier == null){
			supplier = new Supplier();
		}
		purchase.setSupplier(supplier);
		
		form.setPurchase(purchase);
		return "purchase/indepotPurchase";
	}
	
	/**
	 * 确认采购物品入库
	 */
	@RequestMapping(value = "/saveIndepot")
	public String saveIndepot(Purchase form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		Map<String, Object> responseMap = purchaseService.saveIndepot(form, getRequest(), operator);
		form.setReturninfo(String.valueOf(responseMap.get("result")));
		
		return indepotPurchaseInit(form);
		
	}
}
