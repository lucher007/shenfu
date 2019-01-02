package com.sykj.shenfu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sykj.shenfu.common.AesSecret;
import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.IEmployeeDao;
import com.sykj.shenfu.dao.IOperatorDao;
import com.sykj.shenfu.dao.IOperatorrolerefDao;
import com.sykj.shenfu.dao.IRoleDao;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Operatorroleref;
import com.sykj.shenfu.po.Role;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/menu")
@Transactional
public class MenuController extends BaseController {
	@Autowired
	private IOperatorDao operatorDao;
	@Autowired
	private IRoleDao roleDao;
	@Autowired
	private IOperatorrolerefDao operatorrolerefDao;
	@Autowired
	private IEmployeeDao employeeDao;
	

	/**
	 * 更新初始化
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Operator form, HttpSession session) {
		Operator operator = operatorDao.findById(form.getId());
		Employee employee = null;
		if(StringUtils.isNotEmpty(operator.getEmployeecode())){
			employee = employeeDao.findByEmployeecodeStr(operator.getEmployeecode());
		}
		if(employee == null){
			employee = new Employee();
		}
		operator.setEmployee(employee);
		form.setOperator(operator);
		
		Operatorroleref operatorroleref = operatorrolerefDao.findByOperatorid(operator.getId());
		if(operatorroleref == null){
			operatorroleref = new Operatorroleref();
		}

		form.setRoleid(operatorroleref.getRoleid());

		// 构建Role对象
		List<Role> roleList = roleDao.queryByList(new Role());
		Map<Integer, String> roleMap = new HashMap<Integer, String>();
		for (Role role : roleList) {
			roleMap.put(role.getId(), getMessage(role.getRolename()));
		}
		form.setRolemap(roleMap);
		return "main/updateOperator";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update")
	public String update(Operator form, HttpSession session) {
		if ("".equals(form.getLoginname())) {
			form.setReturninfo("登录名称不能为空");
			return updateInit(form,session);
		} else {
			Operator oldOperator = operatorDao.findByLoginname(form);
			if (oldOperator != null && !oldOperator.getId().equals(form.getId())) {
				form.setReturninfo(getMessage("登录名称已经存在"));
				return updateInit(form,session);
			}
		}
		// 修改网络信息
		operatorDao.update(form);
		form.setReturninfo("修改成功");
		return updateInit(form, session);
	}

	/**
	 * 密码修改初始化
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updatePasswordInit")
	public String updatePasswordInit(Operator form) {
		form.setOperator(operatorDao.findById(form.getId()));
		return "main/updatePassword";
	}

	/**
	 * 密码修改
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updatePassword")
	public String updatePassword(Operator form) {
		
		Operator oldOperator = operatorDao.findById(form.getId());
		//数据库的原密码
		String oldpassword = Tools.getStr(oldOperator.getPassword());
		//页面输入的原密码
		String oldpassword_input = Tools.getStr(getRequest().getParameter("oldPassword"));
		//将页面输入的密码加密
		String oldpasswordEncrypt = AesSecret.aesEncrypt(oldpassword_input, AesSecret.key);
		
		if (!oldpasswordEncrypt.equals(oldpassword)) {
			form.setReturninfo("原密码输入不正确");
			return updatePasswordInit(form);
		}
		
		//页面输入的新密码
		String password_input = Tools.getStr(getRequest().getParameter("password"));
		//将页面输入的密码加密
		String passwordEncrypt = AesSecret.aesEncrypt(password_input, AesSecret.key);
		form.setPassword(passwordEncrypt);
		
		Integer updateFlag = operatorDao.updatePassword(form);
		form.setReturninfo("修改成功");
		return updatePasswordInit(form);
	}
}
