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
import com.sykj.shenfu.dao.IEmployeeDao;
import com.sykj.shenfu.dao.IUserDao;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.User;
import com.sykj.shenfu.service.ICoderuleService;
import com.sykj.shenfu.service.IOperatorService;
import com.sykj.shenfu.service.IOperatorlogService;
import com.sykj.shenfu.service.IUserService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/user")
@Transactional
public class UserController extends BaseController {

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
	@Autowired
	private IUserService userService;

	/**
	 * 查询订户
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(User form) {
		return "user/findUserList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(User form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		//封装List数据
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		Integer total = userDao.findByCount(form);
		List<User> list = userDao.findByList(form);
		for (User user : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			//潜在订户信息
			objectMap.put("id", user.getId());
			objectMap.put("usercode", user.getUsercode());
			objectMap.put("username", user.getUsername());
			objectMap.put("usersex", user.getUsersex());
			objectMap.put("phone", user.getPhone());
			objectMap.put("address", Tools.getStr(user.getAddress()));
			objectMap.put("source", user.getSource());
			objectMap.put("sourcename", user.getSourcename());
			objectMap.put("addtime", StringUtils.isEmpty(user.getAddtime())?"":Tools.getStr(user.getAddtime()).substring(0, 10));
			objectMap.put("status", user.getStatus());
			objectMap.put("statusname", user.getStatusname());
			objectMap.put("visittype", user.getVisittype());
			objectMap.put("visittypename", user.getVisittypename());
			objectMap.put("salercode", user.getSalercode());
			if(StringUtils.isNotEmpty(user.getSalercode())){
				Employee saler = employeeDao.findByEmployeecodeStr(user.getSalercode());
				if(saler != null){
					objectMap.put("salername", saler.getEmployeename());
					objectMap.put("salerphone", saler.getPhone());
				}
			}
			objectMap.put("dealresult", Tools.getStr(user.getDealresult()));
			objectMap.put("remark", Tools.getStr(user.getRemark()));
			
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}

	/**
	 * 添加订户页面初始化
	 */
	@RequestMapping(value = "/addInit")
	public String addInit(User form) {
		if("forSaler".equals(form.getJumpurl())){//从销售员修改界面来的
        	return "sale/addUser";
        }else{
        	return "user/addUser";
        }
	}

	/**
	 * 保存添加订户
	 */
	@RequestMapping(value = "/save")
	public String save(User form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		if ("".equals(form.getUsername())) {
			form.setReturninfo("客户姓名不能为空");
			return addInit(form);
		} 
		
		if ("".equals(form.getAddress())) {
			form.setReturninfo("安装地址不能为空");
			return addInit(form);
		} 
		
		if ("".equals(Tools.getStr(form.getPhone()))) {
			form.setReturninfo("电话号码不能为空");
			return addInit(form);
		}
		
		if(!"1".equals(form.getVisittype())){//必须要派人上门
			String visitorcode = getRequest().getParameter("visitorcode");
			if(StringUtils.isEmpty(visitorcode)){
				form.setReturninfo("请选择上门人员");
				return addInit(form);
			}
		}
		
		Map<String, Object> responseMap = userService.saveUserRegister(form, getRequest(), operator);
		form.setReturninfo(String.valueOf(responseMap.get("result")));
		return addInit(form);
	}

	/**
	 * 编辑订户权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(User form) {
		
		User user = userDao.findById(form.getId());
		if(StringUtils.isNotEmpty(user.getSalercode())){
			Employee parentemployee =  employeeDao.findByEmployeecodeStr(user.getSalercode());
			user.setSaler(parentemployee);
		}
		form.setUser(user);
		
		if("forSaler".equals(form.getJumpurl())){//从销售员修改界面来的
        	return "sale/updateUser";
        }else{
        	return "user/updateUser";
        }
		
	}

	/**
	 * 保存编辑后订户权限
	 */
	@RequestMapping(value = "/update")
	public String update(User form) {
		
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		if ("".equals(form.getUsername())) {
			form.setReturninfo("客户姓名不能为空");
			return updateInit(form);
		} 
		
		if ("".equals(Tools.getStr(form.getPhone()))) {
			form.setReturninfo("电话号码不能为空");
			return updateInit(form);
		} else {
			//判断电话号码是否重复
			User oldUser = userDao.findByPhone(form);
			if (oldUser != null && !oldUser.getId().equals(form.getId())) {
				form.setReturninfo(getMessage("电话号码已经存在"));
				return updateInit(form);
			}
		}
		
		User user = userDao.findByUsercodeStr(form.getUsercode());
		user.setUsername(form.getUsername());
		user.setAddress(form.getAddress());
		user.setDealresult(form.getDealresult());
		
		userDao.update(user);
		//增加操作日记
		String content = "修改订户信息，订户姓名:"+form.getUsername();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}

	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(User form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		User user = userDao.findById(form.getId());
		
		//删除订户
		userDao.delete(form.getId());
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "删除订户，订户姓名:"+user.getUsername();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
	/**
	 * 查询用户信息
	 */
	@RequestMapping(value = "/findUserListDialog")
	public String findUserListDialog(User form) {
		return "user/findUserListDialog";
	}
	
	/**
	 * 添加订户页面初始化
	 */
	@RequestMapping(value = "/addUserorderInit")
	public String addUserorderInit(User form) {
	    User user  = userDao.findByUsercodeStr(form.getUsercode());
	    form.setUser(user);
    	return "user/addUserorder";
	}

	/**
	 * 保存添加订户
	 */
	@RequestMapping(value = "/saveUserorder")
	public String saveUserorder(User form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		if ("".equals(form.getUsername())) {
			form.setReturninfo("客户姓名不能为空");
			return addInit(form);
		} 
		
		if ("".equals(form.getAddress())) {
			form.setReturninfo("安装地址不能为空");
			return addInit(form);
		} 
		
		if ("".equals(Tools.getStr(form.getPhone()))) {
			form.setReturninfo("电话号码不能为空");
			return addInit(form);
		}
		
		if(!"1".equals(form.getVisittype())){//必须要派人上门
			String visitorcode = getRequest().getParameter("visitorcode");
			if(StringUtils.isEmpty(visitorcode)){
				form.setReturninfo("请选择上门人员");
				return addInit(form);
			}
		}
		
		Map<String, Object> responseMap = userService.saveUserRegister(form, getRequest(), operator);
		form.setReturninfo(String.valueOf(responseMap.get("result")));
		return addUserorderInit(form);
	}
	
}
