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

import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.IDepotrackDao;
import com.sykj.shenfu.dao.IDepotrackinfoDao;
import com.sykj.shenfu.po.Depotrackinfo;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Depotrack;
import com.sykj.shenfu.service.IOperatorlogService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/depotrack")
@Transactional
public class DepotrackController extends BaseController {

	@Autowired
	private IDepotrackDao depotrackDao;
	@Autowired
	private IDepotrackinfoDao depotrackinfoDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	
	/**
	 * 查询货柜
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Depotrack form) {
		return "depotrack/findDepotrackList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Depotrack form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		//封装List数据
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Integer total = depotrackDao.findByCount(form);
		List<Depotrack> list = depotrackDao.findByList(form);
		for (Depotrack depotrack : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			//潜在订户信息
			objectMap.put("id", depotrack.getId());
			objectMap.put("depotrackcode", depotrack.getDepotrackcode());
			objectMap.put("depotrackname", depotrack.getDepotrackname());
			objectMap.put("rownums", depotrack.getRownums());
			objectMap.put("columnnums", depotrack.getColumnnums());
			objectMap.put("racknum", depotrack.getRacknum());
			objectMap.put("remark", depotrack.getRemark());
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}

	/**
	 * 添加货柜页面初始化
	 */
	@RequestMapping(value = "/addInit")
	public String addInit(Depotrack form) {
		return "depotrack/addDepotrack";
	}

	/**
	 * 保存添加货柜
	 */
	@RequestMapping(value = "/save")
	public String save(Depotrack form) {
		
		if ("".equals(form.getDepotrackcode())) {
			form.setReturninfo("添加失败，请输入货柜编码");
			return addInit(form);
		} else {
			Depotrack depotrack = depotrackDao.findByDepotrackcode(form.getDepotrackcode());
			if (depotrack != null) {
				form.setReturninfo("添加失败，此货柜编码已经存在");
				return addInit(form);
			}
		}
		
		//保存货柜信息
		Integer depotrackId = depotrackDao.save(form);
		
		//保存成功
		if(depotrackId != 0){
			List<String> strList = form.getStrList();
			for (int i = 1; i < strList.size(); i++) {
				Depotrackinfo depotrackinfo = new Depotrackinfo();
				depotrackinfo.setDepotrackcode(form.getDepotrackcode());
				depotrackinfo.setDepotrackname(form.getDepotrackname());
				depotrackinfo.setRownum(Integer.parseInt(strList.get(i).split("-")[1]));
				depotrackinfo.setColumnnum(Integer.parseInt(strList.get(i).split("-")[2]));
				depotrackinfo.setCodeinfo(strList.get(i));
				depotrackinfoDao.save(depotrackinfo);
			}
		}
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "增加货柜信息，货柜名称:"+form.getDepotrackname();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		
		form.setReturninfo("保存成功");
		
		return addInit(form);
	}

	/**
	 * 编辑货柜权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Depotrack form) {
		
		Depotrack depotrack = depotrackDao.findById(form.getId());
		form.setDepotrack(depotrack);
		
		List<Depotrackinfo> depotrackinfoList = depotrackinfoDao.fingDepotrackinfoListByDepotrackcode(depotrack.getDepotrackcode());
		form.setDepotrackinfolist(depotrackinfoList);
		
		return "depotrack/updateDepotrack";
	}

	/**
	 * 保存编辑后货柜权限
	 */
	@RequestMapping(value = "/update")
	public String update(Depotrack form) {
		if ("".equals(Tools.getStr(form.getDepotrackcode()))) {
			form.setReturninfo("货柜编码不能为空");
			return updateInit(form);
		} else {
			//判断材料编号是否重复
			Depotrack oldDepotrack = depotrackDao.findByDepotrackcode(form.getDepotrackcode());
			if (oldDepotrack != null && !oldDepotrack.getId().equals(form.getId())) {
				form.setReturninfo(getMessage("货柜编码已经存在"));
				return updateInit(form);
			}
		}
		
		Integer flag =  depotrackDao.update(form);
		if(flag != 0){//修改成功
			//删除旧的仓位
			depotrackinfoDao.deleteByDepotrackcode(form.getDepotrackcode());
			//依次增加新的仓位
			List<String> strList = form.getStrList();
			for (int i = 1; i < strList.size(); i++) {
				Depotrackinfo depotrackinfo = new Depotrackinfo();
				depotrackinfo.setDepotrackcode(form.getDepotrackcode());
				depotrackinfo.setDepotrackname(form.getDepotrackname());
				depotrackinfo.setRownum(Integer.parseInt(strList.get(i).split("-")[1]));
				depotrackinfo.setColumnnum(Integer.parseInt(strList.get(i).split("-")[2]));
				depotrackinfo.setCodeinfo(strList.get(i));
				depotrackinfoDao.save(depotrackinfo);
			}
		}
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "修改货柜信息，货柜名称:"+form.getDepotrackname();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}

	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Depotrack form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Depotrack depotrack = depotrackDao.findById(form.getId());
		
		//删除货柜
		depotrackDao.delete(form.getId());
		
		//删除旧的仓位
		depotrackinfoDao.deleteByDepotrackcode(depotrack.getDepotrackcode());
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "删除货柜信息，货柜名称:"+depotrack.getDepotrackname();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
	/**
     * 获取下拉列表框Json值
     */
	@ResponseBody //此标签表示返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	@RequestMapping(value="/getDepotrackComboBoxJson")
	public List<Map<String,Object>> getDepotrackComboBoxJson(Depotrack form) {  
	    List<Depotrack> depotrackList = depotrackDao.queryByList(form);
	    List<Map<String, Object>> depotrackComboBoxJson = new ArrayList<Map<String, Object>>();
	    //添加请选择项-默认为空，查询所有
	    HashMap<String,Object> selectMap = new HashMap<String, Object>();
	    selectMap.put("id", "");
	    selectMap.put("text", "请选择");
	    depotrackComboBoxJson.add(selectMap);
	    
	    //封装区域属性结构信息
	    for(Depotrack depotrack:depotrackList){  
	    	    HashMap<String,Object> depotrackMap = new HashMap<String, Object>();
	    	    depotrackMap.put("id", depotrack.getId());
	    	    depotrackMap.put("text", depotrack.getDepotrackname());
	    	    
	    	    depotrackComboBoxJson.add(depotrackMap);  
	    }  
	   return depotrackComboBoxJson;
	}  
	
	/**
     * 获取下拉列表框Json值
     */
	@ResponseBody //此标签表示返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	@RequestMapping(value="/getDepotrackCombogridJson")
	public List<Map<String,Object>> getDepotrackCombogridJson(Depotrack form) {  
	    List<Depotrack> depotrackList = depotrackDao.queryByList(form);
	    List<Map<String, Object>> depotrackCombogridJson = new ArrayList<Map<String, Object>>();
	    //封装属性结构信息
	    for(Depotrack depotrack:depotrackList){  
	    	    HashMap<String,Object> depotrackMap = new HashMap<String, Object>();
	    	    depotrackMap.put("id", depotrack.getId());
	    	    depotrackMap.put("depotrackcode", depotrack.getDepotrackcode());
	    	    depotrackMap.put("depotrackname", depotrack.getDepotrackname());
	    	    depotrackMap.put("depotrackunit", depotrack.getDepotrackname());
	    	    depotrackMap.put("depotracktype", depotrack.getDepotrackname());
	    	    depotrackCombogridJson.add(depotrackMap);  
	    }  
	   return depotrackCombogridJson;
	}  
	
	/**
     * 获取下拉列表框Json值
     */
	@ResponseBody //此标签表示返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	@RequestMapping(value="/getDepotrackinfoComboBoxJson")
	public List<Map<String,Object>> getDepotrackinfoComboBoxJson(Depotrackinfo form) {  
		form.setSort("codeinfo");
		form.setOrder("asc");
	    List<Depotrackinfo> depotrackinfoList = depotrackinfoDao.queryByList(form);
	    List<Map<String, Object>> depotrackComboBoxJson = new ArrayList<Map<String, Object>>();
	    //添加请选择项-默认为空，查询所有
	    HashMap<String,Object> selectMap = new HashMap<String, Object>();
	    selectMap.put("id", "");
	    selectMap.put("text", "请选择");
	    depotrackComboBoxJson.add(selectMap);
	    
	    //封装区域属性结构信息
	    for(Depotrackinfo depotrackinfo:depotrackinfoList){
	    	    HashMap<String,Object> depotrackMap = new HashMap<String, Object>();
	    	    depotrackMap.put("id", depotrackinfo.getCodeinfo());
	    	    depotrackMap.put("text", depotrackinfo.getCodeinfo());
	    	    depotrackComboBoxJson.add(depotrackMap);  
	    }  
	   return depotrackComboBoxJson;
	}  
}
