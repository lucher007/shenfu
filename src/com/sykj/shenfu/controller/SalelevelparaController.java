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

import com.sykj.shenfu.dao.ISalelevelparaDao;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Salelevelpara;
import com.sykj.shenfu.service.IOperatorlogService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/salelevelpara")
@Transactional
public class SalelevelparaController extends BaseController {

	@Autowired
	private ISalelevelparaDao salelevelparaDao;
	@Autowired
	private IOperatorlogService operatorlogService;
    
	/*
     * 特殊的instance变量
     * 注：零长度的byte数组对象创建起来将比任何对象都经济――查看编译后的字节码：
     * 生成零长度的byte[]对象只需3条操作码，而Object lock = new Object()则需要7行操作码。
     */
    private static byte[] lock = new byte[0];   
	
	/**
	 * 查询 任务单
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Salelevelpara form) {
		return "salelevelpara/findSalelevelparaList";
	}
	
	/**
	 * 查询任务单信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Salelevelpara form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		Integer total = salelevelparaDao.findByCount(form);
		List<Salelevelpara> list = salelevelparaDao.findByList(form);
		for (Salelevelpara salelevelpara : list) {

			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			
			objectMap.put("id", salelevelpara.getId());
			objectMap.put("level", salelevelpara.getLevel());
			objectMap.put("minscore", salelevelpara.getMinscore());
			objectMap.put("maxscore", salelevelpara.getMaxscore());
			objectMap.put("reduce", salelevelpara.getReduce());
			objectMap.put("state", salelevelpara.getState());
			objectMap.put("remark", salelevelpara.getRemark());
			
			objectlist.add(objectMap);
			
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}

	/**
	 * 添加 任务单页面初始化
	 */
	@RequestMapping(value = "/addInit")
	public String addInit(Salelevelpara form) {
		return "salelevelpara/addSalelevelpara";
	}

	/**
	 * 保存添加 任务单
	 */
	@RequestMapping(value = "/save")
	public String save(Salelevelpara form) {
		
		//加锁
		synchronized(lock) {
			Operator operator = (Operator)getSession().getAttribute("Operator");
			
			salelevelparaDao.save(form);
			
			//增加操作日记
			String content = "添加销售等级参数， 销售等级为:"+form.getLevel()+"; " + form.getMinscore()+ " =< 等级积分 < "+form.getMaxscore();
			operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		} 
		
		form.setReturninfo("保存成功");
		
		return addInit(form);
	}

	/**
	 * 编辑 任务单权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Salelevelpara form) {
		form.setSalelevelpara(salelevelparaDao.findById(form.getId()));
		return "salelevelpara/updateSalelevelpara";
	}

	/**
	 * 保存编辑后 任务单权限
	 */
	@RequestMapping(value = "/update")
	public String update(Salelevelpara form) {
		
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		salelevelparaDao.update(form);
		//增加操作日记
		String content = "修改 销售等级参数， 销售等级为:"+form.getLevel()+"; "+ form.getMinscore()+ " =< 等级积分 < "+form.getMaxscore();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}

	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Salelevelpara form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Salelevelpara salelevelpara = salelevelparaDao.findById(form.getId());
		
		//删除 任务单
		salelevelparaDao.delete(form.getId());
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "删除 销售等级参数， 销售等级为:"+salelevelpara.getLevel();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
}
