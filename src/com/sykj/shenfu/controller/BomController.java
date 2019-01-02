package com.sykj.shenfu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sykj.shenfu.dao.IBomDao;
import com.sykj.shenfu.dao.IMaterialDao;
import com.sykj.shenfu.dao.IProductDao;
import com.sykj.shenfu.dao.IProductDao;
import com.sykj.shenfu.po.Bom;
import com.sykj.shenfu.po.Material;
import com.sykj.shenfu.po.Product;
import com.sykj.shenfu.po.Product;


/**
 * Bom控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/bom")
@Transactional
public class BomController extends BaseController{
	
	@Autowired 
	private ServletContext servletContext;
	@Autowired
	private IBomDao bomDao; 
	@Autowired
	private IProductDao productDao; 
	@Autowired
	private IMaterialDao materialDao; 

	/**
	 * 查询Bom信息
	 */
	@RequestMapping(value="/findByList")
	public String findByList(Bom form) {
		
		// 构建网络Map对象
		List<Product> list = productDao.queryByList(new Product());
		Map<String, String> map = new HashMap<String, String>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Product product = (Product) iterator.next();
			map.put(product.getProductcode(), product.getProductname()+"("+product.getProductcode()+")");
		}
		form.setProductmap(map);
		
		return "bom/findBomList";
	}
	
	/**
	 * 添加Bom信息初始化
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/addInit")
	public String addInit(Bom form) {
		
		if(form.getPid()==null){//属于顶层Bom增加
			Product product = productDao.findByProductcode(form.getProductcode());
			form.setProduct(product);
		}else if(form.getPid() != null){
			Bom parentbom = bomDao.findById(form.getPid());
			Product product = productDao.findByProductcode(parentbom.getProductcode());
			form.setProduct(product);
			form.setParentbom(parentbom);
		}
		
		return "bom/addBom";
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public String save(Bom form){
		if (form.getMaterialcode() == null) {
			form.setReturninfo("请选择材料名称");
			return addInit(form);
		} else {
			Bom oldBom = bomDao.findByProductcodeAndMaterialcode(form.getProductcode(),form.getMaterialcode());
			if (oldBom != null) {
				form.setReturninfo("该材料已经存在BOM清单中");
				return addInit(form);
			}
		}
		
		Material materail = materialDao.findByMaterialcodeStr(form.getMaterialcode());
		if(materail == null){
			form.setReturninfo("该材料编号不存在");
			return addInit(form);
		}
		
		form.setMaterialname(materail.getMaterialname());
		
		// 设置Bom的BOMCODE
		if (form.getPid() == null || "".equals(form.getPid())) {// 属于顶级Bom，编码就是bomcode
			form.setBomcode(StringUtils.leftPad(form.getMaterialcode().toString(), 4, "0"));
		} else {// 不属于顶级Bom,它的编码既为父Bom的编码+"-"+该Bom的bomcode
			Bom ParentBom = bomDao.findById(form.getPid());
			form.setBomcode(ParentBom.getBomcode() + "-" + StringUtils.leftPad(form.getMaterialcode().toString(), 4, "0"));
		}
		
		bomDao.save(form);
		form.setReturninfo("添加成功");
		form.setFlag("1");//保存成功
		return addInit(form);
	}
	
	/**
	 * 更新初始化
	 */
	@RequestMapping(value="/updateInit")
	public String updateInit(Bom form){
		
		Bom bom = bomDao.findById(form.getId());
		if(bom==null){
			bom = new Bom();
		}
		Product product = productDao.findByProductcode(bom.getProductcode());
		form.setProduct(product);
		form.setBom(bom);
		if(bom.getPid() != null){
			Bom parentBom = bomDao.findById(bom.getPid());
			form.setParentbom(parentBom);
		}
		
		return "bom/updateBom";
	}
	
	/**
	 * 更新
	 */
	@RequestMapping(value="/update")
	public String update(Bom form){
		if ("".equals(form.getMaterialcode())) {
			form.setReturninfo("请选择材料");
			return updateInit(form);
		} 
		
		Bom oldBom = bomDao.findByProductcodeAndMaterialcode(form.getProductcode(),form.getMaterialcode());
		if (oldBom != null && !oldBom.getId().equals(form.getId())) {
			form.setReturninfo("该材料已经存在BOM清单中");
			return updateInit(form);
		}
		
        // 设置Bom的BOMCODE
 		if (form.getPid() == null || "".equals(form.getPid())) {// 属于顶级Bom，编码就是bomcode
 			form.setBomcode(StringUtils.leftPad(form.getMaterialcode().toString(), 4, "0"));
 		} else {// 不属于顶级Bom,它的编码既为父Bom的编码+"-"+该Bom的bomcode
 			Bom parentBom = bomDao.findById(form.getPid());
 			form.setBomcode(parentBom.getBomcode() + "-" + StringUtils.leftPad(form.getMaterialcode().toString(), 4, "0"));
 		}
        
 		Material materail = materialDao.findByMaterialcodeStr(form.getMaterialcode());
		if(materail == null){
			form.setReturninfo("该材料编号不存在");
			return addInit(form);
		}
		
		form.setMaterialname(materail.getMaterialname());
 		
        //修改网络信息
		Integer id = bomDao.update(form);
		
		form.setReturninfo("修改成功");
		
		form.setFlag("1");//保存成功
		
		return updateInit(form);
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public String delete(Bom form) {
        //得到将要删除的Bom
		Bom bom = bomDao.findById(form.getId());
		//查询该区下所有的子Bom
		List<Bom> bomList = bomDao.queryListByPid(bom);
		
		if(bomList != null && bomList.size() > 0){
			form.setReturninfo("该BOM节点存在子节点，请先删除子节点");
			return findByList(form);
		}
		
		// 删除
		bomDao.delete(form.getId());
		
		form.setReturninfo("删除成功");
		return findByList(form);
	}
	
	/**
	 * 批量删除
	 */
	@ResponseBody //此标签表示返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	@RequestMapping(value="/deleteBatchByCode")
	public Map<String,Object> deleteBatchByCode(Bom form) {
		//封装返回给页面的json对象
		HashMap<String,Object> bomJson = new HashMap<String,Object>();
		
		Bom bom = bomDao.findById(form.getId());
		bom.setPid(bom.getId());
		//查询该区下所有的子Bom
		List<Bom> bomList = bomDao.queryListByPid(bom);
		
		if(bomList != null && bomList.size() > 0){
			bomJson.put("deleteflag", false);
			bomJson.put("info", getMessage("该BOM节点存在子节点，请先删除子节点"));
			return bomJson;
		}
		
		//批量删除
		bomDao.deleteBatchByBomcode(bom);
		
		bomJson.put("deleteflag", true);
		bomJson.put("info", "删除成功");
		
		return bomJson;
	}
	
	/**
     * 初始化bom的下拉列表框值
     */
	@ResponseBody //此标签表示返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	@RequestMapping(value="/initBomJson")
    public Map<String,Object> initBomJson(Bom form) {
		//封装返回给页面的json对象
		HashMap<String,Object> bomJson = new HashMap<String,Object>();
        
        // 构建网络Map对象
 		List<Bom> bomlist = bomDao.queryByList(form);
 		
 		for (Iterator iterator = bomlist.iterator(); iterator.hasNext();) {
 			Bom bom = (Bom) iterator.next();
 			bomJson.put(bom.getMaterialcode(), bom.getMaterialname());
 		}
 		return bomJson;
    }
	
	
	/**
     * 初始化bom树形结构
     */
	@ResponseBody //此标签表示返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	@RequestMapping(value="/getTreeBom")
    public List<Map<String,Object>> getTreeBom(Bom form) {
		//封装返回给页面的json对象
		List<Map<String, Object>> bomJSON = new ArrayList<Map<String, Object>>();
		
        //封装顶层产品型号信息
		Product product = productDao.findByProductcode(form.getProductcode());
		if(product != null){
	        // 构建网络Map对象
	 		List<Bom> bomlist = bomDao.queryListByPid(form);
	 		for (Bom bom : bomlist) {
	 			HashMap<String,Object> bomMap = new HashMap<String, Object>();
	 			bomMap.put("id", bom.getId());
	 			bomMap.put("pId", bom.getPid());
	 			bomMap.put("name", bom.getMaterialname()+"("+bom.getMaterialcode()+")" + "_"+bom.getAmount());
	 			bomMap.put("path", "bom/updateInit?id="+bom.getId());
	 			bomMap.put("isParent", true);
	 			bomMap.put("type", "2");
	 			bomJSON.add(bomMap);
	        }
		}
 		
 		return bomJSON;
    }
	
	/**
     * 初始化bom的下拉列表框值
     */
	@ResponseBody //此标签表示返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	@RequestMapping(value="/getNextMenu")
    public List<Map<String,Object>> getNextMenu(Bom form) {
		//封装返回给页面的json对象
		List<Map<String, Object>> bomJSON = new ArrayList<Map<String, Object>>();
		
		Bom dmpBom = bomDao.findById(form.getId());
		Bom bomForm = new Bom();
		if(dmpBom !=null && dmpBom.getId() != null){
			bomForm.setProductcode(dmpBom.getProductcode());
			bomForm.setPid(dmpBom.getId());
		}
		
        // 构建网络Map对象
 		List<Bom> bomlist = bomDao.queryListByPid(bomForm);
 		for (Bom bom : bomlist) {
 			HashMap<String,Object> bomMap = new HashMap<String, Object>();
 			bomMap.put("id", bom.getId());
 			bomMap.put("pId", bom.getPid());
 			bomMap.put("name", bom.getMaterialname()+"("+bom.getMaterialcode()+")"+"_"+bom.getAmount());
 			bomMap.put("path", "bom/updateInit?id="+bom.getId());
 			bomMap.put("isParent", true);
 			bomMap.put("type", "2");
 			bomJSON.add(bomMap);
        }
 		return bomJSON;
    }
	
	
	/** 
	 * 构建Bom树型菜单json数据 
	 */  
	public List<Map<String, Object>> buildNode(int pid,List<Bom> bomList){  
	    List<Map<String, Object>> bomTreeJSON = new ArrayList<Map<String, Object>>();
	    for(Bom bom:bomList){  
	    	 HashMap<String,Object> bomMap = new HashMap<String, Object>();
	        if(null != bom.getPid() && bom.getPid().equals(pid)){  
	        	bomMap.put("id", bom.getBomcode());
	    	    bomMap.put("text", bom.getMaterialname()+"("+bom.getMaterialcode()+")" + "_"+bom.getAmount());
	    	    List<Map<String, Object>> children = buildNode(bom.getId(),bomList);  
	            if(null != children && 0 < children.size()){ 
	            	bomMap.put("state", "closed");
	            	bomMap.put("children", children);
	            }  
	            bomTreeJSON.add(bomMap);   
	        }  
	    }  
	    return bomTreeJSON;  
	} 
	
	/**
     * 获取bom的树形下拉列表框值
     */
	@ResponseBody //此标签表示返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	@RequestMapping(value="/getBomTreeJson")
	public List<Map<String,Object>> getBomTreeJson(Bom form) {  
	    //Bom对象
		List<Map<String, Object>> bomTreeJSON = new ArrayList<Map<String, Object>>();
	    //添加请选择项-默认为空，查询所有
	    HashMap<String,Object> selectMap = new HashMap<String, Object>();
	    selectMap.put("id", "");
	    selectMap.put("text", getMessage("page.select"));
	    bomTreeJSON.add(selectMap);
	    
	    //先判断网络有没有选择
	    if(form.getProductcode() != null){
	    	List<Bom> bomList = bomDao.queryByList(form);
		    //封装Bom属性结构信息
		    for(Bom bom:bomList){  
		    	if(null == bom.getPid()){ 
		    	    HashMap<String,Object> bomMap = new HashMap<String, Object>();
		    	    bomMap.put("id", bom.getBomcode());
		    	    bomMap.put("text", bom.getMaterialname()+"("+bom.getMaterialcode()+")" + "_" +bom.getAmount());
		    	    
		    	    List<Map<String, Object>> children = buildNode(bom.getId(),bomList);  
		    	    if(null != children && 0 < children.size()){ 
		            	bomMap.put("state", "closed");
		            	bomMap.put("children", children);
		            }  
		            bomTreeJSON.add(bomMap);  
		    	}
		    }  
	    }
	    
	   return bomTreeJSON;
	}  
}
