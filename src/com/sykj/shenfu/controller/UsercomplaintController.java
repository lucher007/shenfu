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
import com.sykj.shenfu.dao.IEmployeeDao;
import com.sykj.shenfu.dao.IUsercomplaintDao;
import com.sykj.shenfu.dao.IUserDao;
import com.sykj.shenfu.po.Coderule;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.User;
import com.sykj.shenfu.po.Usercomplaint;
import com.sykj.shenfu.service.ICoderuleService;
import com.sykj.shenfu.service.IOperatorService;
import com.sykj.shenfu.service.IOperatorlogService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/usercomplaint")
@Transactional
public class UsercomplaintController extends BaseController {
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private IUsercomplaintDao usercomplaintDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private IOperatorService operatorService;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private ICoderuleService coderuleService;
   
	/*
     * 特殊的instance变量
     * 注：零长度的byte数组对象创建起来将比任何对象都经济――查看编译后的字节码：
     * 生成零长度的byte[]对象只需3条操作码，而Object lock = new Object()则需要7行操作码。
     */
    private static byte[] lock = new byte[0];   
	
	/**
	 * 查询投诉单单
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Usercomplaint form) {
		return "usercomplaint/findUsercomplaintList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Usercomplaint form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		
		
		Integer total = usercomplaintDao.findByCount(form);
		List<Usercomplaint> list = usercomplaintDao.findByList(form);
		for (Usercomplaint usercomplaint : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			
			//投诉单信息
			objectMap.put("id", usercomplaint.getId());
			objectMap.put("complaintcode", usercomplaint.getComplaintcode());
			objectMap.put("addtime", usercomplaint.getAddtime());
			objectMap.put("content", usercomplaint.getContent());
			objectMap.put("dealdate", usercomplaint.getDealdate());
			objectMap.put("dealresult", usercomplaint.getDealresult());
			objectMap.put("status", usercomplaint.getStatus());
			objectMap.put("remark", usercomplaint.getRemark());
			
			//订户信息
			User user = userDao.findByUsercodeStr(usercomplaint.getUsercode());
			if(user == null){
				user = new User();
			}
			
			objectMap.put("userid", user.getId());
			objectMap.put("username", user.getUsername());
			objectMap.put("phone", user.getPhone());
			objectMap.put("usersex", user.getUsersex());
			objectMap.put("address", user.getAddress());
			
			
			//投诉人信息
			Employee complainter = employeeDao.findByEmployeecodeStr(usercomplaint.getComplaintercode());
			if(complainter == null){
				complainter = new Employee();
			}
			objectMap.put("complaintercode", usercomplaint.getComplaintercode());
			objectMap.put("complaintername", complainter.getEmployeename());
			objectMap.put("complainterphone", complainter.getPhone());
			
			 //操作员信息
			Operator operator = operatorService.findByEmployeecode(usercomplaint.getOperatorcode());
			if(operator == null){
				operator = new Operator();
			}
			objectMap.put("operatorcode", usercomplaint.getOperatorcode());
			objectMap.put("operatorname", operator.getEmployee().getEmployeename());
			
			
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}

	/**
	 * 添加投诉单单页面初始化
	 */
	@RequestMapping(value = "/addInit")
	public String addInit(Usercomplaint form) {
		return "usercomplaint/addUsercomplaint";
	}

	/**
	 * 保存添加 投诉单单
	 */
	@RequestMapping(value = "/save")
	public String save(Usercomplaint form) {
		
		//加锁
		synchronized(lock) {
			
			Operator operator = (Operator)getSession().getAttribute("Operator");
		
			form.setOperatorcode(operator.getEmployeecode());
		
			//最大订单号
			String complaintcode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_complaintcode);
			form.setComplaintcode(complaintcode);
			form.setAddtime(Tools.getCurrentTime());
			form.setStatus("0");
			usercomplaintDao.save(form);
			
			//增加操作日记
			String content = "添加投诉单， 投诉单号:"+form.getComplaintcode();
			operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		} 
		
		form.setReturninfo("保存成功");
		
		return addInit(form);
	}

	/**
	 * 编辑 订单权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Usercomplaint form) {
		Usercomplaint usercomplaint = usercomplaintDao.findById(form.getId());
		//封装订户
		usercomplaint.setUser(userDao.findByUsercodeStr(usercomplaint.getUsercode()));
		
		//封装投诉员工
		Employee complainter = employeeDao.findByEmployeecodeStr(usercomplaint.getComplaintercode());
		if(complainter == null){
			complainter = new Employee();
		}
		usercomplaint.setComplainter(complainter);
		
		form.setUsercomplaint(usercomplaint);
		
		return "usercomplaint/updateUsercomplaint";
	}

	/**
	 * 保存编辑后 订单权限
	 */
	@RequestMapping(value = "/update")
	public String update(Usercomplaint form) {
		
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		usercomplaintDao.update(form);
		//增加操作日记
		String content = "修改投诉单信息， 投诉单单号:"+form.getComplaintcode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}

	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Usercomplaint form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Usercomplaint usercomplaint = usercomplaintDao.findById(form.getId());
		
		//删除投诉单
		usercomplaintDao.delete(form.getId());
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "删除投诉单， 投诉单号:"+usercomplaint.getComplaintcode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
}
