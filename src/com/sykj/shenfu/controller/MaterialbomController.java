package com.sykj.shenfu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sykj.shenfu.dao.IMaterialbomDao;
import com.sykj.shenfu.dao.IComponentDao;
import com.sykj.shenfu.dao.IMaterialDao;
import com.sykj.shenfu.po.Materialbom;
import com.sykj.shenfu.po.Component;
import com.sykj.shenfu.po.Material;


/**
 * Materialbom控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/materialbom")
@Transactional
public class MaterialbomController extends BaseController{
	
	@Autowired 
	private ServletContext servletContext;
	@Autowired
	private IMaterialbomDao materialbomDao; 
	@Autowired
	private IMaterialDao materialDao; 
	@Autowired
	private IComponentDao componentDao; 

	/**
	 * 查询Materialbom信息
	 */
	@RequestMapping(value="/findByList")
	public String findByList(Materialbom form) {
		
		// 构建网络Map对象
		List<Material> list = materialDao.queryByList(new Material());
		Map<String, String> map = new HashMap<String, String>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Material material = (Material) iterator.next();
			map.put(material.getMaterialcode(), material.getMaterialname()+"("+material.getMaterialcode()+")");
		}
		form.setMaterialmap(map);
		
		return "materialbom/findMaterialbomList";
	}
	
	/**
	 * 添加Materialbom信息初始化
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/addInit")
	public String addInit(Materialbom form) {
		
		if(form.getPid()==null){//属于顶层Materialbom增加
			Material material = materialDao.findByMaterialcodeStr(form.getMaterialcode());
			form.setMaterial(material);
		}else if(form.getPid() != null){
			Materialbom parentmaterialbom = materialbomDao.findById(form.getPid());
			Material material = materialDao.findByMaterialcodeStr(parentmaterialbom.getMaterialcode());
			form.setMaterial(material);
			form.setParentbom(parentmaterialbom);
		}
		
		return "materialbom/addMaterialbom";
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public String save(Materialbom form){
		if (form.getComponentcode() == null) {
			form.setReturninfo("请选择元器件名称");
			return addInit(form);
		} else {
			Materialbom oldMaterialbom = materialbomDao.findByMaterialcodeAndComponentcode(form.getMaterialcode(),form.getComponentcode());
			if (oldMaterialbom != null) {
				form.setReturninfo("该元器件已经存在BOM清单中");
				return addInit(form);
			}
		}
		
		Component component = componentDao.findByComponentcode(form.getComponentcode());
		if(component == null){
			form.setReturninfo("该元器件编号不存在");
			return addInit(form);
		}
		
		form.setComponentname(component.getComponentname());
		form.setComponentmodel(component.getComponentmodel());
		
		// 设置Materialbom的BOMCODE
		if (form.getPid() == null || "".equals(form.getPid())) {// 属于顶级Materialbom，编码就是materialbomcode
			form.setBomcode(StringUtils.leftPad(form.getComponentcode().toString(), 4, "0"));
		} else {// 不属于顶级Materialbom,它的编码既为父Materialbom的编码+"-"+该Materialbom的materialbomcode
			Materialbom ParentMaterialbom = materialbomDao.findById(form.getPid());
			form.setBomcode(ParentMaterialbom.getBomcode() + "-" + StringUtils.leftPad(form.getComponentcode().toString(), 4, "0"));
		}
		
		materialbomDao.save(form);
		form.setReturninfo("添加成功");
		form.setFlag("1");//保存成功
		return addInit(form);
	}
	
	/**
	 * 更新初始化
	 */
	@RequestMapping(value="/updateInit")
	public String updateInit(Materialbom form){
		
		Materialbom materialbom = materialbomDao.findById(form.getId());
		if(materialbom==null){
			materialbom = new Materialbom();
		}
		Material material = materialDao.findByMaterialcodeStr(materialbom.getMaterialcode());
		form.setMaterial(material);
		form.setMaterialbom(materialbom);
		if(materialbom.getPid() != null){
			Materialbom parentMaterialbom = materialbomDao.findById(materialbom.getPid());
			form.setParentbom(parentMaterialbom);
		}
		
		return "materialbom/updateMaterialbom";
	}
	
	/**
	 * 更新
	 */
	@RequestMapping(value="/update")
	public String update(Materialbom form){
		if ("".equals(form.getComponentcode())) {
			form.setReturninfo("请选择元器件");
			return updateInit(form);
		} 
		
		Materialbom oldMaterialbom = materialbomDao.findByMaterialcodeAndComponentcode(form.getMaterialcode(),form.getComponentcode());
		if (oldMaterialbom != null && !oldMaterialbom.getId().equals(form.getId())) {
			form.setReturninfo("该元器件已经存在BOM清单中");
			return updateInit(form);
		}
		
        // 设置Materialbom的BOMCODE
 		if (form.getPid() == null || "".equals(form.getPid())) {// 属于顶级Materialbom，编码就是materialbomcode
 			form.setBomcode(StringUtils.leftPad(form.getComponentcode().toString(), 4, "0"));
 		} else {// 不属于顶级Materialbom,它的编码既为父Materialbom的编码+"-"+该Materialbom的materialbomcode
 			Materialbom parentMaterialbom = materialbomDao.findById(form.getPid());
 			form.setBomcode(parentMaterialbom.getBomcode() + "-" + StringUtils.leftPad(form.getComponentcode().toString(), 4, "0"));
 		}
        
 		Component component = componentDao.findByComponentcode(form.getComponentcode());
		if(component == null){
			form.setReturninfo("该元器件编号不存在");
			return addInit(form);
		}
		
		form.setComponentname(component.getComponentname());
		form.setComponentmodel(component.getComponentmodel());
 		
        //修改网络信息
		Integer id = materialbomDao.update(form);
		
		form.setReturninfo("修改成功");
		
		form.setFlag("1");//保存成功
		
		return updateInit(form);
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public String delete(Materialbom form) {
        //得到将要删除的Materialbom
		Materialbom materialbom = materialbomDao.findById(form.getId());
		//查询该区下所有的子Materialbom
		List<Materialbom> materialbomList = materialbomDao.queryListByPid(materialbom);
		
		if(materialbomList != null && materialbomList.size() > 0){
			form.setReturninfo("该BOM节点存在子节点，请先删除子节点");
			return findByList(form);
		}
		
		// 删除
		materialbomDao.delete(form.getId());
		
		form.setReturninfo("删除成功");
		return findByList(form);
	}
	
	/**
	 * 批量删除
	 */
	@ResponseBody //此标签表示返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	@RequestMapping(value="/deleteBatchByCode")
	public Map<String,Object> deleteBatchByCode(Materialbom form) {
		//封装返回给页面的json对象
		HashMap<String,Object> materialbomJson = new HashMap<String,Object>();
		
		Materialbom materialbom = materialbomDao.findById(form.getId());
		materialbom.setPid(materialbom.getId());
		//查询该区下所有的子Materialbom
		List<Materialbom> materialbomList = materialbomDao.queryListByPid(materialbom);
		
		if(materialbomList != null && materialbomList.size() > 0){
			materialbomJson.put("deleteflag", false);
			materialbomJson.put("info", getMessage("该BOM节点存在子节点，请先删除子节点"));
			return materialbomJson;
		}
		
		//批量删除
		materialbomDao.deleteBatchByBomcode(materialbom);
		
		materialbomJson.put("deleteflag", true);
		materialbomJson.put("info", "删除成功");
		
		return materialbomJson;
	}
	
	/**
     * 初始化materialbom的下拉列表框值
     */
	@ResponseBody //此标签表示返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	@RequestMapping(value="/initMaterialbomJson")
    public Map<String,Object> initMaterialbomJson(Materialbom form) {
		//封装返回给页面的json对象
		HashMap<String,Object> materialbomJson = new HashMap<String,Object>();
        
        // 构建网络Map对象
 		List<Materialbom> materialbomlist = materialbomDao.queryByList(form);
 		
 		for (Iterator iterator = materialbomlist.iterator(); iterator.hasNext();) {
 			Materialbom materialbom = (Materialbom) iterator.next();
 			materialbomJson.put(materialbom.getComponentcode(), materialbom.getComponentname());
 		}
 		return materialbomJson;
    }
	
	
	/**
     * 初始化materialbom树形结构
     */
	@ResponseBody //此标签表示返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	@RequestMapping(value="/getTreeBom")
    public List<Map<String,Object>> getTreeBom(Materialbom form) {
		//封装返回给页面的json对象
		List<Map<String, Object>> materialbomJSON = new ArrayList<Map<String, Object>>();
		
        //封装顶层产品型号信息
		Material material = materialDao.findByMaterialcodeStr(form.getMaterialcode());
		if(material != null){
	        // 构建网络Map对象
	 		List<Materialbom> materialbomlist = materialbomDao.queryListByPid(form);
	 		for (Materialbom materialbom : materialbomlist) {
	 			HashMap<String,Object> materialbomMap = new HashMap<String, Object>();
	 			materialbomMap.put("id", materialbom.getId());
	 			materialbomMap.put("pId", materialbom.getPid());
	 			materialbomMap.put("name", materialbom.getComponentname() + "_"+materialbom.getComponentmodel() + "("+materialbom.getComponentcode()+")" + "_"+materialbom.getAmount());
	 			materialbomMap.put("path", "materialbom/updateInit?id="+materialbom.getId());
	 			materialbomMap.put("isParent", true);
	 			materialbomMap.put("type", "2");
	 			materialbomJSON.add(materialbomMap);
	        }
		}
 		
 		return materialbomJSON;
    }
	
	/**
     * 初始化materialbom的下拉列表框值
     */
	@ResponseBody //此标签表示返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	@RequestMapping(value="/getNextMenu")
    public List<Map<String,Object>> getNextMenu(Materialbom form) {
		//封装返回给页面的json对象
		List<Map<String, Object>> materialbomJSON = new ArrayList<Map<String, Object>>();
		
		Materialbom dmpMaterialbom = materialbomDao.findById(form.getId());
		Materialbom materialbomForm = new Materialbom();
		if(dmpMaterialbom !=null && dmpMaterialbom.getId() != null){
			materialbomForm.setMaterialcode(dmpMaterialbom.getMaterialcode());
			materialbomForm.setPid(dmpMaterialbom.getId());
		}
		
        // 构建网络Map对象
 		List<Materialbom> materialbomlist = materialbomDao.queryListByPid(materialbomForm);
 		for (Materialbom materialbom : materialbomlist) {
 			HashMap<String,Object> materialbomMap = new HashMap<String, Object>();
 			materialbomMap.put("id", materialbom.getId());
 			materialbomMap.put("pId", materialbom.getPid());
 			materialbomMap.put("name", materialbom.getComponentname()+ "_"+materialbom.getComponentmodel() + "("+materialbom.getComponentcode()+")" + "_"+materialbom.getAmount());
 			materialbomMap.put("path", "materialbom/updateInit?id="+materialbom.getId());
 			materialbomMap.put("isParent", true);
 			materialbomMap.put("type", "2");
 			materialbomJSON.add(materialbomMap);
        }
 		return materialbomJSON;
    }
	
	
	/** 
	 * 构建Materialbom树型菜单json数据 
	 */  
	public List<Map<String, Object>> buildNode(int pid,List<Materialbom> materialbomList){  
	    List<Map<String, Object>> materialbomTreeJSON = new ArrayList<Map<String, Object>>();
	    for(Materialbom materialbom:materialbomList){  
	    	 HashMap<String,Object> materialbomMap = new HashMap<String, Object>();
	        if(null != materialbom.getPid() && materialbom.getPid().equals(pid)){  
	        	materialbomMap.put("id", materialbom.getBomcode());
	    	    materialbomMap.put("text", materialbom.getComponentname()+ "_" + materialbom.getComponentmodel() + "("+materialbom.getComponentcode()+")" + "_"+materialbom.getAmount());
	    	    List<Map<String, Object>> children = buildNode(materialbom.getId(),materialbomList);  
	            if(null != children && 0 < children.size()){ 
	            	materialbomMap.put("state", "closed");
	            	materialbomMap.put("children", children);
	            }  
	            materialbomTreeJSON.add(materialbomMap);   
	        }  
	    }  
	    return materialbomTreeJSON;  
	} 
	
	/**
     * 获取materialbom的树形下拉列表框值
     */
	@ResponseBody //此标签表示返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	@RequestMapping(value="/getMaterialbomTreeJson")
	public List<Map<String,Object>> getMaterialbomTreeJson(Materialbom form) {  
	    //Materialbom对象
		List<Map<String, Object>> materialbomTreeJSON = new ArrayList<Map<String, Object>>();
	    //添加请选择项-默认为空，查询所有
	    HashMap<String,Object> selectMap = new HashMap<String, Object>();
	    selectMap.put("id", "");
	    selectMap.put("text", getMessage("page.select"));
	    materialbomTreeJSON.add(selectMap);
	    
	    //先判断网络有没有选择
	    if(form.getMaterialcode() != null){
	    	List<Materialbom> materialbomList = materialbomDao.queryByList(form);
		    //封装Materialbom属性结构信息
		    for(Materialbom materialbom:materialbomList){  
		    	if(null == materialbom.getPid()){ 
		    	    HashMap<String,Object> materialbomMap = new HashMap<String, Object>();
		    	    materialbomMap.put("id", materialbom.getBomcode());
		    	    materialbomMap.put("text", materialbom.getComponentname() + "_"+materialbom.getComponentmodel() +"("+materialbom.getComponentcode()+")" + "_" +materialbom.getAmount());
		    	    
		    	    List<Map<String, Object>> children = buildNode(materialbom.getId(),materialbomList);  
		    	    if(null != children && 0 < children.size()){ 
		            	materialbomMap.put("state", "closed");
		            	materialbomMap.put("children", children);
		            }  
		            materialbomTreeJSON.add(materialbomMap);  
		    	}
		    }  
	    }
	    
	   return materialbomTreeJSON;
	}  
}
