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
import com.sykj.shenfu.dao.ICellextendDao;
import com.sykj.shenfu.dao.ICellstationDao;
import com.sykj.shenfu.dao.ICellstationemployeeDao;
import com.sykj.shenfu.dao.IEmployeeDao;
import com.sykj.shenfu.dao.ICellDao;
import com.sykj.shenfu.po.Cellextend;
import com.sykj.shenfu.po.Cellstation;
import com.sykj.shenfu.po.Cellstationemployee;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.service.IOperatorlogService;

import fr.opensagres.xdocreport.utils.StringUtils;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/cellstation")
@Transactional
public class CellstationController extends BaseController {

	@Autowired
	private ICellDao cellDao;
	@Autowired
	private ICellstationDao cellstationDao;
	@Autowired
	private ICellstationemployeeDao cellstationemployeeDao;
	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private ICellextendDao cellextendDao;
	
	/**
	 * 查询小区驻点
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Cellstation form) {
		return "cellstation/findCellstationList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Cellstation form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		Integer total = cellstationDao.findByCount(form);
		List<Cellstation> list = cellstationDao.findByList(form);
		for (Cellstation cellstation : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("id", cellstation.getId());
			objectMap.put("stationcode", cellstation.getStationcode());
			objectMap.put("extendcode", cellstation.getExtendcode());
			objectMap.put("cellcode", cellstation.getCellcode());
			objectMap.put("cellname", cellstation.getCellname());
			objectMap.put("address", cellstation.getAddress());
			objectMap.put("totalhouse", cellstation.getTotalhouse());
			objectMap.put("price", cellstation.getPrice()/(double)100);
			objectMap.put("totalmoney", cellstation.getTotalmoney()/(double)100);
			objectMap.put("extendercode", cellstation.getExtendercode());
			if(StringUtils.isNotEmpty(cellstation.getExtendercode())){
				Employee extender = employeeDao.findByEmployeecodeStr(cellstation.getExtendercode());
				if(extender != null){
					objectMap.put("extendername", extender.getEmployeename());
					objectMap.put("extenderphone", extender.getPhone());
				}
			}
			objectMap.put("extendtime", StringUtils.isEmpty(cellstation.getExtendtime())?"":Tools.getStr(cellstation.getExtendtime()).substring(0, 19));
			objectMap.put("acceptercode", cellstation.getAcceptercode());
			if(StringUtils.isNotEmpty(cellstation.getAcceptercode())){
				Employee accepter = employeeDao.findByEmployeecodeStr(cellstation.getAcceptercode());
				if(accepter != null){
					objectMap.put("acceptername", accepter.getEmployeename());
					objectMap.put("accepterphone", accepter.getPhone());
				}
			}
			objectMap.put("acceptertime", StringUtils.isEmpty(cellstation.getAcceptertime())?"":Tools.getStr(cellstation.getAcceptertime()).substring(0, 19));
			objectMap.put("paytime", StringUtils.isEmpty(cellstation.getPaytime())?"":Tools.getStr(cellstation.getPaytime()).substring(0, 19));
			objectMap.put("starttime", StringUtils.isEmpty(cellstation.getStarttime())?"":Tools.getStr(cellstation.getStarttime()).substring(0, 10));
			objectMap.put("endtime", StringUtils.isEmpty(cellstation.getEndtime())?"":Tools.getStr(cellstation.getEndtime()).substring(0, 10));
			objectMap.put("talkernumber", cellstation.getTalkernumber());
			objectMap.put("visitornumber", cellstation.getVisitornumber());
			objectMap.put("addtime", StringUtils.isEmpty(cellstation.getAddtime())?"":Tools.getStr(cellstation.getAddtime()).substring(0, 19));
			
			objectMap.put("remark", cellstation.getRemark());
			
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}


	/**
	 * 保存添加小区驻点
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/saveCellstation")
	public Map<String,Object> saveCellstation(Cellstation form) {
		//封装返回给页面的json对象
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
       // Map<String, Object> responseMap = cellstationService.saveCellstation(form, getRequest(), operator);
		
		//responseJson.put("flag", String.valueOf(responseMap.get("status")));//删除成功
		//responseJson.put("msg", String.valueOf(responseMap.get("result")));
		
		return responseJson;
	}
	
	
	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Cellstation form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Cellstation cellstation = cellstationDao.findById(form.getId());
		
		//修改小区发布单状态改成未申请驻点
		Cellextend cellextend = cellextendDao.findByExtendcode(cellstation.getExtendcode());
		if(cellextend != null){
			cellextend.setStationflag("0");//未申请驻点
			cellextendDao.update(cellextend);
		}
		
		//删除小区驻点消息
		cellstationDao.delete(form.getId());
		
		//删除小区驻点人员
		cellstationemployeeDao.deleteByStationcode(cellstation.getStationcode());
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "删除小区驻点信息，小区名称:"+cellstation.getCellname();
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
	public Map<String,Object> deleteBatch(Cellstation form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		String[] idArray = Tools.getStr(getRequest().getParameter("ids")).split(",");
		if (idArray == null || idArray.length < 1) {
			responseJson.put("flag", "0");//发布失败
			responseJson.put("msg", "请选择需要删除的小区驻点!");
			return responseJson;
		} else {
			for (int i = 0; i < idArray.length; i++) {
				Cellstation cellstation = cellstationDao.findById(Integer.parseInt(idArray[i]));
				//删除小区驻点
				cellstationDao.delete(Integer.parseInt(idArray[i]));
				
				//删除小区驻点人员
				cellstationemployeeDao.deleteByStationcode(cellstation.getStationcode());
				
				//增加操作日记
				Operator operator = (Operator)getSession().getAttribute("Operator");
				String content = "删除小区驻点，小区名称:"+cellstation.getCellname();
				operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
			}
		}
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "批量删除成功");
		
		return responseJson;
	}
	
	
}
