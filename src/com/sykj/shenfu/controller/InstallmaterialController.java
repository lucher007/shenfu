package com.sykj.shenfu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.IInstallinfoDao;
import com.sykj.shenfu.dao.IInstallcomponentDao;
import com.sykj.shenfu.dao.IInstallmaterialDao;
import com.sykj.shenfu.dao.IMaterialDao;
import com.sykj.shenfu.dao.IMaterialcheckDao;
import com.sykj.shenfu.dao.IMaterialdepotDao;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Materialcheck;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Installinfo;
import com.sykj.shenfu.po.Installcomponent;
import com.sykj.shenfu.po.Installmaterial;
import com.sykj.shenfu.po.Material;
import com.sykj.shenfu.po.Materialdepot;
import com.sykj.shenfu.service.IOperatorlogService;
import com.sykj.shenfu.service.IMaterialdepotService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/installmaterial")
@Transactional
public class InstallmaterialController extends BaseController {

	@Autowired
	private IInstallmaterialDao installmaterialDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private IMaterialDao materialDao;
	@Autowired
	private IInstallinfoDao installinfoDao;
	@Autowired
	private IMaterialdepotDao materialdepotDao;
	@Autowired
	private IInstallcomponentDao installcomponentDao;
	@Autowired
	private IMaterialdepotService materialdepotService;
	@Autowired
	private IMaterialcheckDao materialcheckDao;
	
	/**
	 * 查询客户支付
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Installmaterial form) {
		return "installmaterial/findInstallmaterialList";
	}
	
	/**
	 * 查询信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Installmaterial form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Integer total = installmaterialDao.findByCount(form);
		List<Installmaterial> list = installmaterialDao.findByList(form);
		for (Installmaterial installmaterial : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			//客户信息
			objectMap.put("id", installmaterial.getId());
			objectMap.put("producecode", installmaterial.getProducecode());
			objectMap.put("materialcode", installmaterial.getMaterialcode());
			objectMap.put("materialname", installmaterial.getMaterialname());
			objectMap.put("batchno", installmaterial.getBatchno());
			objectMap.put("materialidentfy", installmaterial.getMaterialidentfy());
			objectMap.put("depotamount", installmaterial.getDepotamount());
			objectMap.put("materialstatus", installmaterial.getMaterialstatus());
			objectMap.put("materialstatusname", installmaterial.getMaterialstatusname());
			objectMap.put("printflag", installmaterial.getPrintflagname());
			objectMap.put("printflagname", installmaterial.getPrintflag());
			objectMap.put("addtime", StringUtils.isEmpty(installmaterial.getAddtime())?"":Tools.getStr(installmaterial.getAddtime()).substring(0, 19));
			objectMap.put("remark", installmaterial.getRemark());
			
			Installinfo installinfo = installinfoDao.findByProducecode(installmaterial.getProducecode());
			if(installinfo != null){
				objectMap.put("materialversion", Tools.getStr(installinfo.getMaterialversion()));
				objectMap.put("installinformation", Tools.getStr(installinfo.getInstallinformation()));
			}
			
			objectlist.add(objectMap);
			
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		
		return result;
	}
	
	/**
	 * 添加客户支付页面初始化
	 */
	@RequestMapping(value = "/indepotInit")
	public String indepotInit(Installmaterial form) {
		Installmaterial installmaterial = installmaterialDao.findByMaterialidentfy(form.getMaterialidentfy());
		form.setInstallmaterial(installmaterial);
		return "installmaterial/indepot";
	}

	/**
	 * 保存添加客户支付
	 */
	@RequestMapping(value = "/saveIndepot")
	public String saveIndepot(Installmaterial form) {
		
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		Materialdepot materialdepotform =  materialdepotDao.findByMaterialidentfy(form.getMaterialidentfy());
		if(materialdepotform != null){
			form.setReturninfo("入库失败，该材料SN码已经存在库存中");
			return indepotInit(form);
		}
		
		//修改生产材料的状态为已经入库
		Installmaterial installmaterial = installmaterialDao.findByMaterialidentfy(form.getMaterialidentfy());
		installmaterial.setMaterialstatus("3");
		installmaterialDao.update(installmaterial);
		
		//当前时间
		String currenttime = Tools.getCurrentTime();
		
		//生产信息
		Installinfo installinfo = installinfoDao.findByProducecode(installmaterial.getProducecode());
		
		//材料信息
		//Material material = materialDao.findByMaterialcodeStr(installmaterial.getMaterialcode());
		
		//增加材料入库
		Materialdepot materialdepot = new Materialdepot();
		materialdepot.setMaterialcode(installmaterial.getMaterialcode());
		materialdepot.setMaterialname(installmaterial.getMaterialname());
		materialdepot.setOperatorcode(operator.getEmployeecode());
		materialdepot.setBatchno(installmaterial.getBatchno());
		materialdepot.setDepotamount(installmaterial.getDepotamount());
		materialdepot.setAddtime(currenttime);
		materialdepot.setInoutercode(form.getInoutercode());
		materialdepot.setDepotstatus("1");//有库存
		materialdepot.setDepotrackcode(form.getDepotrackcode());
		materialdepot.setMaterialidentfy(installmaterial.getMaterialidentfy());
		materialdepot.setMaterialsource("0");//自产
		materialdepot.setProducercode(installinfo.getProducercode());
		if("0".equals(getRequest().getParameter("checkstatus"))){//不合格入库，材料库存变成维修状态
			materialdepot.setMaterialstatus("3");
		}else{
			materialdepot.setMaterialstatus("1"); //正常
		}
		materialdepot.setInstallinfo(installinfo.getInstallinformation());
		materialdepot.setMaterialversion(installinfo.getMaterialversion());
		
		//材料入库
		materialdepotService.saveMaterialdepot_instorForInstall(materialdepot);
		
		//保存材料检测记录
		Materialcheck materialcheck = new Materialcheck();
		materialcheck.setMaterialcode(installmaterial.getMaterialcode());
		materialcheck.setMaterialname(installmaterial.getMaterialname());
		materialcheck.setBatchno(installmaterial.getBatchno());
		materialcheck.setMaterialidentfy(installmaterial.getMaterialidentfy());
		materialcheck.setCheckinfo(getRequest().getParameter("checkinfo"));
		materialcheck.setRepairinfo(getRequest().getParameter("repairinfo"));
		materialcheck.setCheckercode(getRequest().getParameter("checkercode"));
		materialcheck.setChecktime(currenttime);
		materialcheck.setCheckstatus(getRequest().getParameter("checkstatus"));
		if("0063".equals(installmaterial.getMaterialcode())){//按键板
			materialcheck.setHightemp(getRequest().getParameter("hightemp"));
			materialcheck.setCurrentvoltage(getRequest().getParameter("currentvoltage"));
			materialcheck.setKeystrokesound(getRequest().getParameter("keystrokesound"));
		}else if("0066".equals(installmaterial.getMaterialcode())){//主板
			materialcheck.setHightemp(getRequest().getParameter("hightemp"));
			materialcheck.setCurrentvoltage(getRequest().getParameter("currentvoltage"));
			materialcheck.setDisplayscreen(getRequest().getParameter("displayscreen"));
			materialcheck.setTouchscreen(getRequest().getParameter("touchscreen"));
			materialcheck.setMessageconnect(getRequest().getParameter("messageconnect"));
			materialcheck.setOpenclosedoor(getRequest().getParameter("openclosedoor"));
			materialcheck.setKeystrokesound(getRequest().getParameter("keystrokesound"));
			materialcheck.setFingerprint(getRequest().getParameter("fingerprint"));
		}else if("0067".equals(installmaterial.getMaterialcode())){//备板子
			materialcheck.setHightemp(getRequest().getParameter("hightemp"));
			materialcheck.setCurrentvoltage(getRequest().getParameter("currentvoltage"));
			materialcheck.setOpenclosedoor(getRequest().getParameter("openclosedoor"));
			materialcheck.setKeystrokesound(getRequest().getParameter("keystrokesound"));
		}
		//保存材料检验信息
		materialcheckDao.save(materialcheck);
		
		//增加操作日记
		//Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "材料组装入库，材料名称:"+materialdepot.getMaterialname()+";材料批次号:"+materialdepot.getBatchno()+";材料SN:"+materialdepot.getMaterialidentfy();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("组装入库成功");
		return indepotInit(form);
	}
	
	/**
	 * 查看 订单权限初始化页面
	 */
	@RequestMapping(value = "/lookInit")
	public String lookInit(Installmaterial form) {
		Installmaterial installmaterial = installmaterialDao.findByMaterialidentfy(form.getMaterialidentfy());
		form.setInstallmaterial(installmaterial);
		return "installmaterial/lookInstallmaterial";
	}
	
	/**
	 * 查询生产材料信息JSON
	 */
	@ResponseBody
	@RequestMapping(value = "/findInstallcomponentJson")
	public Map<String, Object> findInstallcomponentJson(Installcomponent form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		form.setSort("componentcode");
		form.setOrder("asc");
		Integer total = installcomponentDao.findByCount(form);
		List<Installcomponent> installcomponentlist = installcomponentDao.queryByList(form);
		for (Installcomponent installcomponent : installcomponentlist) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			//产品信息
			objectMap.put("materialcode", installcomponent.getMaterialcode());
			objectMap.put("materialname", installcomponent.getMaterialname());
			objectMap.put("componentcode", installcomponent.getComponentcode());
			objectMap.put("componentname", installcomponent.getComponentname());
			objectMap.put("componentmodel", installcomponent.getComponentmodel());
			objectMap.put("amount", installcomponent.getAmount());
			objectMap.put("batchno", installcomponent.getBatchno());
			objectMap.put("depotrackcode", installcomponent.getDepotrackcode());
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}
}
