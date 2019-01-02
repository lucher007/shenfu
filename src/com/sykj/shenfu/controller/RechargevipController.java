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

import com.sykj.shenfu.dao.IRechargevipDao;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Productcolor;
import com.sykj.shenfu.po.Rechargevip;
import com.sykj.shenfu.service.IOperatorlogService;


/**
 * 系统参数控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/rechargevip")
@Transactional
public class RechargevipController extends BaseController{
	
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private IRechargevipDao rechargevipDao; 

	/**
	 * 查询系统参数信息
	 */
	@RequestMapping(value="/findByList")
	public String findByList(Rechargevip form) {
		return "rechargevip/findRechargevipList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Rechargevip form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		Integer total = rechargevipDao.findByCount(form);
		List<Rechargevip> list = rechargevipDao.findByList(form);
		result.put("total", total);
		result.put("rows", list);
		return result;
	}
	
	/**
	 * 添加系统参数信息初始化
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/addInit")
	public String addInit(Rechargevip form) {
		return "rechargevip/addRechargevip";
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public String save(Rechargevip form){
		if ("".equals(form.getRechargevipcode())) {
			form.setReturninfo("充值VIP编码不能为空");
			return addInit(form);
		} else {
			Rechargevip oldRechargevip = rechargevipDao.findByRechargevipcode(form.getRechargevipcode());
			if (oldRechargevip != null) {
				form.setReturninfo("充值VIP编码已经存在");
				return addInit(form);
			}
		}
		rechargevipDao.save(form);
		form.setReturninfo("保存成功");
		return addInit(form);
	}
	
	/**
	 * 更新初始化
	 */
	@RequestMapping(value="/updateInit")
	public String updateInit(Rechargevip form){
		form.setRechargevip(rechargevipDao.findById(form.getId()));
		return "rechargevip/updateRechargevip";
	}
	
	/**
	 * 更新
	 */
	@RequestMapping(value="/update")
	public String update(Rechargevip form){
		if ("".equals(form.getRechargevipcode())) {
			form.setReturninfo("充值VIP编码不能为空");
			return updateInit(form);
		} 
		
		Rechargevip oldRechargevip = rechargevipDao.findByRechargevipcode(form.getRechargevipcode());
		if (oldRechargevip != null && !oldRechargevip.getId().equals(form.getId())) {
			form.setReturninfo("充值VIP编码已经存在");
			return updateInit(form);
		}
       
      	//修改网络信息
		Integer id = rechargevipDao.update(form);
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}
	
	
	/**
	 * 删除
	 * @return
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value="/delete")
	public Map<String,Object> delete(Rechargevip form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		//查询
		Rechargevip rechargevip = rechargevipDao.findById(form.getId());
		// 删除
		rechargevipDao.delete(form.getId());
	
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "删除充值VIP，编码为:"+rechargevip.getRechargevipcode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		return responseJson;
	}
	
	/**
     * 获取材料的下拉列表框Json值
     */
	@ResponseBody //此标签表示返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
	@RequestMapping(value="/getRechargevipComboBoxJson")
	public List<Map<String,Object>> getRechargevipComboBoxJson(Rechargevip form) {  
	    List<Rechargevip> rechargevipList = rechargevipDao.queryByList(form);
	    List<Map<String, Object>> objectComboBoxJson = new ArrayList<Map<String, Object>>();
	    //添加请选择项-默认为空，查询所有
	    HashMap<String,Object> selectMap = new HashMap<String, Object>();
	    selectMap.put("id", "");
	    selectMap.put("text", "请选择");
	    objectComboBoxJson.add(selectMap);
	    
	    //封装区域属性结构信息
	    for(Rechargevip rechargevip:rechargevipList){  
	    	    HashMap<String,Object> objectMap = new HashMap<String, Object>();
	    	    objectMap.put("id", rechargevip.getRechargevipcode());
	    	    objectMap.put("text", rechargevip.getRechargevipname());
	    	    objectComboBoxJson.add(objectMap);  
	    }  
	   return objectComboBoxJson;
	}  
}
