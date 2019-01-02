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

import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.ICellextendDao;
import com.sykj.shenfu.dao.IEmployeeDao;
import com.sykj.shenfu.dao.ICellDao;
import com.sykj.shenfu.po.Cellextend;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Cell;
import com.sykj.shenfu.service.ICellextendService;
import com.sykj.shenfu.service.ICoderuleService;
import com.sykj.shenfu.service.IOperatorlogService;

import fr.opensagres.xdocreport.utils.StringUtils;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/cellextend")
@Transactional
public class CellextendController extends BaseController {

	@Autowired
	private ICellDao cellDao;
	@Autowired
	private ICellextendDao cellextendDao;
	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private ICellextendService cellextendService;
	
	
	/**
	 * 查询小区
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Cellextend form) {
		return "cellextend/findCellextendList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Cellextend form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		Integer total = cellextendDao.findByCount(form);
		List<Cellextend> list = cellextendDao.findByList(form);
		for (Cellextend cellextend : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			//产品出入库信息
			objectMap.put("id", cellextend.getId());
			objectMap.put("extendcode", cellextend.getExtendcode());
			objectMap.put("cellcode", cellextend.getCellcode());
			objectMap.put("cellname", cellextend.getCellname());
			objectMap.put("address", cellextend.getAddress());
			objectMap.put("totalhouse", cellextend.getTotalhouse());
			objectMap.put("price", cellextend.getPrice()/(double)100);
			objectMap.put("totalmoney", cellextend.getTotalmoney()/(double)100);
			objectMap.put("extendercode", cellextend.getExtendercode());
			if(StringUtils.isNotEmpty(cellextend.getExtendercode())){
				Employee extender = employeeDao.findByEmployeecodeStr(cellextend.getExtendercode());
				if(extender != null){
					objectMap.put("extendername", extender.getEmployeename());
					objectMap.put("extenderphone", extender.getPhone());
				}
			}
			objectMap.put("extendtime", StringUtils.isEmpty(cellextend.getExtendtime())?"":Tools.getStr(cellextend.getExtendtime()).substring(0, 19));
			objectMap.put("starttime", StringUtils.isEmpty(cellextend.getStarttime())?"":Tools.getStr(cellextend.getStarttime()).substring(0, 19));
			objectMap.put("endtime", StringUtils.isEmpty(cellextend.getEndtime())?"":Tools.getStr(cellextend.getEndtime()).substring(0, 19));
			
			objectMap.put("acceptflag", cellextend.getAcceptflag());
			objectMap.put("acceptflagname", cellextend.getAcceptflagname());
			objectMap.put("acceptercode", cellextend.getAcceptercode());
			if(StringUtils.isNotEmpty(cellextend.getAcceptercode())){
				Employee accepter = employeeDao.findByEmployeecodeStr(cellextend.getAcceptercode());
				if(accepter != null){
					objectMap.put("acceptername", accepter.getEmployeename());
					objectMap.put("accepterphone", accepter.getPhone());
				}
			}
			objectMap.put("acceptertime", StringUtils.isEmpty(cellextend.getAcceptertime())?"":Tools.getStr(cellextend.getAcceptertime()).substring(0, 19));
			objectMap.put("payflag", cellextend.getPayflag());
			objectMap.put("payflagname", cellextend.getPayflagname());
			objectMap.put("paytime", StringUtils.isEmpty(cellextend.getPaytime())?"":Tools.getStr(cellextend.getPaytime()).substring(0, 19));
			objectMap.put("stationflag", cellextend.getStationflag());
			objectMap.put("stationflagname", cellextend.getStationflagname());
			
			objectMap.put("remark", cellextend.getRemark());
			
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}

	/**
	 * 添加小区页面初始化
	 */
	@RequestMapping(value = "/addInit")
	public String addInit(Cellextend form) {
		return "cellextend/findCellListForExtend";
	}

	/**
	 * 小区发布
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/saveExtendCell")
	public Map<String,Object> saveExtendCell(Cellextend form) {
		//封装返回给页面的json对象
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
        Map<String, Object> responseMap = cellextendService.saveCellextend(form, getRequest(), operator);
		
		responseJson.put("flag", String.valueOf(responseMap.get("status")));//删除成功
		responseJson.put("msg", String.valueOf(responseMap.get("result")));
		
		return responseJson;
	}
	
	
	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Cellextend form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Cellextend cellextend = cellextendDao.findById(form.getId());
		
		if(cellextend == null ){
			responseJson.put("flag", "0");//删除成功
			responseJson.put("msg", "删除失败，此发布信息不存在，请刷新页面");
			return responseJson;
		}
		
		if(StringUtils.isNotEmpty(cellextend.getAcceptercode())){
			responseJson.put("flag", "0");//删除成功
			responseJson.put("msg", "删除失败，此发布信息已经被接收");
			return responseJson;
		}
		
		//删除小区
		cellextendDao.delete(form.getId());
		
		//修改小区未发布
		Cell cell = cellDao.findByCellcode(cellextend.getCellcode());
		cell.setExtendflag("0"); //未发布
		cellDao.update(cell);
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "删除小区发布信息，小区名称:"+cellextend.getCellname();
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
	public Map<String,Object> deleteBatch(Cellextend form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		String[] idArray = Tools.getStr(getRequest().getParameter("ids")).split(",");
		if (idArray == null || idArray.length < 1) {
			responseJson.put("flag", "0");//发布失败
			responseJson.put("msg", "请选择需要删除的小区!");
			return responseJson;
		} else {
			for (int i = 0; i < idArray.length; i++) {
				Cellextend cellextend = cellextendDao.findById(Integer.parseInt(idArray[i]));
				//删除小区
				cellextendDao.delete(Integer.parseInt(idArray[i]));
				
				//增加操作日记
				Operator operator = (Operator)getSession().getAttribute("Operator");
				String content = "删除小区，小区名称:"+cellextend.getCellname();
				operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
			}
		}
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "批量删除成功");
		
		return responseJson;
	}
	
	/**
	 * 编辑小区扫楼价格修改初始化
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Cellextend form) {
		form.setCellextend(cellextendDao.findById(form.getId()));
		return "cellextend/updateCellextend";
	}

	/**
	 * 保存修改小区发布扫楼价格
	 */
	@RequestMapping(value = "/update")
	public String update(Cellextend form) {
		
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		if (form.getTotalmoney() == null) {
			form.setReturninfo("扫楼价格不能为空");
			return updateInit(form);
		} 
		
		Cellextend cellextend = cellextendDao.findById(form.getId());
		cellextend.setTotalmoney(form.getTotalmoney());
		cellextendDao.update(cellextend);
		//增加操作日记
		String content = "修改扫楼价格，小区:"+cellextend.getCellname()+";发布编号:"+cellextend.getExtendercode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}
	
}
