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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.IMaterialDao;
import com.sykj.shenfu.dao.IMaterialdepotDao;
import com.sykj.shenfu.po.Material;
import com.sykj.shenfu.po.Materialdepot;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.service.IOperatorlogService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/material")
@Transactional
public class MaterialController extends BaseController {

	@Autowired
	private IMaterialDao materialDao;
	@Autowired
	private IMaterialdepotDao materialdepotDao;
	@Autowired
	private IOperatorlogService operatorlogService;

	/**
	 * 查询材料
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Material form) {
		return "material/findMaterialList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Material form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		Integer total = materialDao.findByCount(form);
		List<Material> list = materialDao.findByList(form);
		
		result.put("total", total);//页面总数
		result.put("rows", list);
		return result;
	}

	/**
	 * 添加材料页面初始化
	 */
	@RequestMapping(value = "/addInit")
	public String addInit(Material form) {
		return "material/addMaterial";
	}

	/**
	 * 保存添加材料
	 */
	@RequestMapping(value = "/save")
	public String save(Material form) {
		
		if ("".equals(form.getMaterialcode())) {
			form.setReturninfo("请输入材料编号");
			return addInit(form);
		} else {
			Material material = materialDao.findByMaterialcodeStr(form.getMaterialcode());
			if (material != null) {
				form.setReturninfo("此材料编号已经存在");
				return addInit(form);
			}
		}
		
		if ("".equals(form.getMaterialname())) {
			form.setReturninfo("请输入材料名称");
			return addInit(form);
		} else {
			Material material = materialDao.findByMaterialname(form.getMaterialname());
			if (material != null) {
				form.setReturninfo("此材料名称已经存在");
				return addInit(form);
			}
		}
		
		//添加原材料的时候，默认报警量量为10
		if(StringUtils.isEmpty(form.getDepotamount()) || form.getDepotamount() == 0){
			form.setDepotamount(10);
		}
		
		materialDao.save(form);
		
		form.setReturninfo("保存成功");
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "增加材料信息，材料名称为:"+form.getMaterialname();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		return addInit(form);
	}

	/**
	 * 编辑材料权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Material form) {
		form.setMaterial(materialDao.findById(form.getId()));
		return "material/updateMaterial";
	}

	/**
	 * 保存编辑后材料权限
	 */
	@RequestMapping(value = "/update")
	public String update(Material form) {
		
		if ("".equals(Tools.getStr(form.getMaterialname()))) {
			form.setReturninfo("材料名称不能为空");
			return updateInit(form);
		} else {
			//判断材料编号是否重复
			Material oldMaterial = materialDao.findByMaterialname(form.getMaterialname());
			if (oldMaterial != null && !oldMaterial.getId().equals(form.getId())) {
				form.setReturninfo(getMessage("材料名称已经存在"));
				return updateInit(form);
			}
		}
		
		//添加原材料的时候，默认报警量量为10
		if(StringUtils.isEmpty(form.getDepotamount()) || form.getDepotamount() == 0){
			form.setDepotamount(10);
		}
		
		materialDao.update(form);
		form.setReturninfo("修改成功");
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "修改材料信息，材料名称为:"+form.getMaterialname();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		return updateInit(form);
	}

	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Material form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Material material = materialDao.findById(form.getId());
		
		if(material != null){
			//删除材料
			materialDao.delete(form.getId());
		} else {
			responseJson.put("flag", "0");//删除失败
			responseJson.put("msg", "删除失败，该材料不存在");
			return responseJson;
		}
		
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "删除材料信息，材料名称为:"+material.getMaterialname();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
	/**
     * 获取材料的下拉列表框Json值
     */
	@ResponseBody //此标签表示返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	@RequestMapping(value="/getMaterialComboBoxJson")
	public List<Map<String,Object>> getMaterialComboBoxJson(Material form) {  
	    List<Material> materialList = materialDao.queryByList(form);
	    List<Map<String, Object>> materialComboBoxJson = new ArrayList<Map<String, Object>>();
	    //添加请选择项-默认为空，查询所有
	    HashMap<String,Object> selectMap = new HashMap<String, Object>();
	    selectMap.put("id", "");
	    selectMap.put("text", "请选择");
	    materialComboBoxJson.add(selectMap);
	    
	    //封装区域属性结构信息
	    for(Material material:materialList){  
    	    HashMap<String,Object> materialMap = new HashMap<String, Object>();
    	    materialMap.put("id", material.getMaterialcode());
    	    materialMap.put("text", material.getMaterialname()+"("+material.getMaterialcode()+")");
    	    
    	    materialComboBoxJson.add(materialMap);  
	    }  
	   return materialComboBoxJson;
	}  
	
	/**
     * 获取材料的下拉列表框表格显示Json值
     */
	@ResponseBody //此标签表示返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	@RequestMapping(value="/getMaterialCombogridJson")
	public List<Map<String,Object>> getMaterialCombogridJson(Material form) {  
		List<Material> materialList = materialDao.queryByList(form);
	    List<Map<String, Object>> materialCombogridJson = new ArrayList<Map<String, Object>>();
	   
	    //封装区域属性结构信息
	    for(Material material:materialList){  
	    	    HashMap<String,Object> materialMap = new HashMap<String, Object>();
	    	    materialMap.put("id", material.getId());
	    	    materialMap.put("materialcode", material.getMaterialcode());
	    	    materialMap.put("materialname", material.getMaterialname());
	    	    materialMap.put("materialunit", material.getMaterialunit());
	    	    materialMap.put("depotamount", material.getDepotamount());
	    	    materialMap.put("materialstatus", material.getMaterialstatus());
	    	    materialMap.put("materialsource", material.getMaterialsource());
	    	    materialMap.put("materialsourcename", material.getMaterialsourcename());
	    	    materialCombogridJson.add(materialMap);  
	    }  
	   return materialCombogridJson;
	}  
	
}
