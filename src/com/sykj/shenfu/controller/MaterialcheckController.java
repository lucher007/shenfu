package com.sykj.shenfu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.IEmployeeDao;
import com.sykj.shenfu.dao.IMaterialDao;
import com.sykj.shenfu.dao.IMaterialcheckDao;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Materialcheck;
import com.sykj.shenfu.service.IOperatorService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/materialcheck")
@Transactional
public class MaterialcheckController extends BaseController {

	@Autowired
	private IMaterialcheckDao materialcheckDao;
	@Autowired
	private IOperatorService operatorService;
	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private IMaterialDao materialDao;
	
	
	/**
	 * 查询材料库存
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Materialcheck form) {
		return "materialcheck/findMaterialcheckList";
	}
	
	/**
	 * 查询材料库存Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Materialcheck form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Integer total = materialcheckDao.findByCount(form);
		List<Materialcheck> list = materialcheckDao.findByList(form);
		for (Materialcheck materialcheck : list) {
			
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("id", materialcheck.getId());
			objectMap.put("materialcode", materialcheck.getMaterialcode());
			objectMap.put("materialname", materialcheck.getMaterialname());
			objectMap.put("batchno", materialcheck.getBatchno());
			objectMap.put("materialidentfy", materialcheck.getMaterialidentfy());
			objectMap.put("checkinfo", Tools.getStr(materialcheck.getCheckinfo()));
			objectMap.put("repairinfo", Tools.getStr(materialcheck.getRepairinfo()));
			objectMap.put("checkercode", materialcheck.getCheckercode());
			//操作员信息
			Employee checker = employeeDao.findByEmployeecodeStr(materialcheck.getCheckercode());
			if(checker != null){
				objectMap.put("checkername", checker.getEmployeename());
				objectMap.put("checkerphone", checker.getPhone());
			}
			objectMap.put("checktime", StringUtils.isEmpty(materialcheck.getChecktime())?"":Tools.getStr(materialcheck.getChecktime()).substring(0, 19));
			objectMap.put("checkstatus", materialcheck.getCheckstatus());
			objectMap.put("checkstatusname", materialcheck.getCheckstatusname());
			objectMap.put("hightemp", materialcheck.getHightemp());
			objectMap.put("hightempname", materialcheck.getHightempname());
			objectMap.put("currentvoltage", materialcheck.getCurrentvoltage());
			objectMap.put("currentvoltagename", materialcheck.getCurrentvoltagename());
			objectMap.put("displayscreen", materialcheck.getDisplayscreen());
			objectMap.put("displayscreenname", materialcheck.getDisplayscreenname());
			objectMap.put("touchscreen", materialcheck.getTouchscreen());
			objectMap.put("touchscreenname", materialcheck.getTouchscreenname());
			objectMap.put("messageconnect", materialcheck.getMessageconnect());
			objectMap.put("messageconnectname", materialcheck.getMessageconnectname());
			objectMap.put("openclosedoor", materialcheck.getOpenclosedoor());
			objectMap.put("openclosedoorname", materialcheck.getOpenclosedoorname());
			objectMap.put("keystrokesound", materialcheck.getKeystrokesound());
			objectMap.put("keystrokesoundname", materialcheck.getKeystrokesoundname());
			objectMap.put("fingerprint", materialcheck.getFingerprint());
			objectMap.put("fingerprintname", materialcheck.getFingerprintname());
			objectMap.put("remark", materialcheck.getRemark());
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}
	
	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Materialcheck form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		//删除产品
		materialcheckDao.delete(form.getId());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
}
