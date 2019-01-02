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
import com.sykj.shenfu.dao.ICellstationDao;
import com.sykj.shenfu.dao.ICellstationemployeeDao;
import com.sykj.shenfu.dao.ICellDao;
import com.sykj.shenfu.po.Cellstationemployee;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.service.ICellstationemployeeService;
import com.sykj.shenfu.service.IOperatorlogService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/cellstationemployee")
@Transactional
public class CellstationemployeeController extends BaseController {

	@Autowired
	private ICellDao cellDao;
	@Autowired
	private ICellstationDao cellstationDao;
	@Autowired
	private ICellstationemployeeDao cellstationemployeeDao;
	@Autowired
	private ICellstationemployeeService cellstationemployeeService;
	@Autowired
	private IOperatorlogService operatorlogService;
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Cellstationemployee form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		Integer total = cellstationemployeeDao.findByCount(form);
		List<Cellstationemployee> list = cellstationemployeeDao.findByList(form);
		for (Cellstationemployee cellstationemployee : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("id", cellstationemployee.getId());
			objectMap.put("extendcode", cellstationemployee.getExtendcode());
			objectMap.put("cellcode", cellstationemployee.getCellcode());
			objectMap.put("cellname", cellstationemployee.getCellname());
			objectMap.put("address", cellstationemployee.getAddress());
			objectMap.put("employeetype", cellstationemployee.getEmployeetype());
			objectMap.put("employeetypename", cellstationemployee.getEmployeetypename());
			objectMap.put("employeecode", cellstationemployee.getEmployeecode());
			objectMap.put("employeename", cellstationemployee.getEmployeename());
			objectMap.put("phone", cellstationemployee.getPhone());
			objectMap.put("remark", cellstationemployee.getRemark());
			
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}

	/**
	 * 添加小区驻点人员页面初始化
	 */
	@RequestMapping(value = "/addCellstationemployeeInit")
	public String addCellstationemployeeInit(Cellstationemployee form) {
		return "cellstation/findEmployeeListForStation";
	}
	
	/**
	 * 保存添加小区驻点人员
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/saveCellstationemployee")
	public Map<String,Object> saveCellstationemployee(Cellstationemployee form) {
		//封装返回给页面的json对象
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
        Map<String, Object> responseMap = cellstationemployeeService.saveCellstationemployee(form, getRequest(), operator);
		
		responseJson.put("flag", String.valueOf(responseMap.get("status")));//删除成功
		responseJson.put("msg", String.valueOf(responseMap.get("result")));
		
		return responseJson;
	}
	
	
	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Cellstationemployee form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Cellstationemployee cellstationemployee = cellstationemployeeDao.findById(form.getId());
		
		//删除小区驻点工作人员消息
		cellstationemployeeDao.delete(form.getId());
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "删除小区驻点工作人员信息，姓名为:"+cellstationemployee.getEmployeename();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
	/**
	 * 批量删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/deleteBatch")
	public Map<String,Object> deleteBatch(Cellstationemployee form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		String[] idArray = Tools.getStr(getRequest().getParameter("ids")).split(",");
		if (idArray == null || idArray.length < 1) {
			responseJson.put("flag", "0");//发布失败
			responseJson.put("msg", "请选择需要删除的小区驻点!");
			return responseJson;
		} else {
			for (int i = 0; i < idArray.length; i++) {
				Cellstationemployee cellstationemployee = cellstationemployeeDao.findById(Integer.parseInt(idArray[i]));
				//删除小区驻点
				cellstationemployeeDao.delete(Integer.parseInt(idArray[i]));
				
				//增加操作日记
				Operator operator = (Operator)getSession().getAttribute("Operator");
				String content = "删除小区驻点工作人员信息，姓名为:"+cellstationemployee.getEmployeename();
				operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
			}
		}
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "批量删除成功");
		
		return responseJson;
	}
	
	
}
