package com.sykj.shenfu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sykj.shenfu.dao.IUserdoordataDao;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Userdoordata;
import com.sykj.shenfu.service.IOperatorlogService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/userdoordata")
@Transactional
public class UserdoordataController extends BaseController {
    
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private IUserdoordataDao userdoordataDao;
	@Autowired
	private IOperatorlogService operatorlogService;
    
	
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
	public String findByList(Userdoordata form) {
		return "Userdoordata/findUserdoordataList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Userdoordata form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		//封装List数据
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		Integer total = userdoordataDao.findByCount(form);
		List<Userdoordata> list = userdoordataDao.findByList(form);
		for (Userdoordata Userdoordata : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			//潜在订户信息
			objectMap.put("id", Userdoordata.getId());
			objectMap.put("usercode", Userdoordata.getUsercode());
			objectMap.put("ordercode", Userdoordata.getOrdercode());
			objectMap.put("locklength", Userdoordata.getLocklength());
			objectMap.put("lockwidth", Userdoordata.getLockwidth());
			objectMap.put("doorlength", Userdoordata.getDoorlength());
			objectMap.put("doorwidth", Userdoordata.getDoorwidth());
			objectMap.put("addtime", Userdoordata.getAddtime());
			objectMap.put("remark", Userdoordata.getRemark());
			
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
	public String addInit(Userdoordata form) {
		return "Userdoordata/addUserdoordata";
	}

	/**
	 * 保存添加订户
	 */
	@RequestMapping(value = "/save")
	public String save(Userdoordata form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		//默认为未处理
		//form.setStatus("0");
		//form.setAddtime(Tools.getCurrentTime());
		userdoordataDao.save(form);
		
		//增加操作日记
		String content = "添加门锁数据，客户编码为:"+form.getUsercode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("保存成功");
		
		return addInit(form);
	}

	/**
	 * 编辑订户权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Userdoordata form) {
		form.setUserdoordata(userdoordataDao.findById(form.getId()));
		return "Userdoordata/updateUserdoordata";
	}

	/**
	 * 保存编辑后订户权限
	 */
	@RequestMapping(value = "/update")
	public String update(Userdoordata form) {
		
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		userdoordataDao.update(form);
		//增加操作日记
		String content = "修改门锁数据，订单编号为:"+form.getOrdercode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}

	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Userdoordata form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Userdoordata userdoordata = userdoordataDao.findById(form.getId());
		
		//删除门锁
		userdoordataDao.delete(form.getId());
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "删除门锁数据，客户编号为:"+userdoordata.getUsercode() + ";订单编号:" + userdoordata.getOrdercode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
}
