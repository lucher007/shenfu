package com.sykj.shenfu.controller;

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

import com.sykj.shenfu.dao.IMaterialDao;
import com.sykj.shenfu.dao.IPurchasedetailDao;
import com.sykj.shenfu.po.Material;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Purchasedetail;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/purchasedetail")
@Transactional
public class PurchasedetailController extends BaseController {

	@Autowired
	private IPurchasedetailDao purchasedetailDao;
	@Autowired
	private IMaterialDao materialDao;
    
	/*
     * 特殊的instance变量
     * 注：零长度的byte数组对象创建起来将比任何对象都经济――查看编译后的字节码：
     * 生成零长度的byte[]对象只需3条操作码，而Object lock = new Object()则需要7行操作码。
     */
    private static byte[] lock = new byte[0];   
	
	/**
	 * 查询 采购明细
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Purchasedetail form) {
		return "purchasedetail/findPurchasedetailList";
	}
	
	/**
	 * 查询采购明细信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Purchasedetail form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Integer total = purchasedetailDao.findByCount(form);
		List<Purchasedetail> list = purchasedetailDao.queryByList(form);
		for (Purchasedetail purchasedetail : list) {
            
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			
			//产品信息
			objectMap.put("id", purchasedetail.getId());
			objectMap.put("purchaseid", purchasedetail.getPurchaseid());
			objectMap.put("purchasecode", purchasedetail.getPurchasecode());
			objectMap.put("type", purchasedetail.getType());
			objectMap.put("typename", purchasedetail.getTypename());
			objectMap.put("materialcode", purchasedetail.getMaterialcode());
			objectMap.put("materialname", purchasedetail.getMaterialname());
			objectMap.put("model", purchasedetail.getModel());
			objectMap.put("price", purchasedetail.getPrice());
			objectMap.put("amount", purchasedetail.getAmount());
			objectMap.put("money", purchasedetail.getMoney());
			objectMap.put("remark", purchasedetail.getRemark());
			objectlist.add(objectMap);
			
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}

	/**
	 * 添加 采购明细页面初始化
	 */
	@RequestMapping(value = "/addInit")
	public String addInit(Purchasedetail form) {
		return "purchasedetail/addPurchasedetail";
	}

	/**
	 * 保存添加 采购明细
	 */
	@RequestMapping(value = "/save")
	public String save(Purchasedetail form) {
		
		//加锁
		synchronized(lock) {
			Operator operator = (Operator)getSession().getAttribute("Operator");
			
		} 
		
		form.setReturninfo("保存成功");
		
		return addInit(form);
	}

	/**
	 * 编辑 采购明细权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Purchasedetail form) {
		
		return "purchasedetail/updatePurchasedetail";
	}

	/**
	 * 保存编辑后 采购明细权限
	 */
	@RequestMapping(value = "/update")
	public String update(Purchasedetail form) {
		
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}

	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Purchasedetail form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Purchasedetail purchasedetail = purchasedetailDao.findById(form.getId());
		
		//删除 采购明细
		purchasedetailDao.delete(form.getId());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
	/**
	 * 查询采购明细信息
	 */
	@RequestMapping(value = "/findPurchasedetailListDialog")
	public String findPurchasedetailListDialog(Purchasedetail form) {
		return "purchasedetail/findPurchasedetailListDialog";
	}
	
}
