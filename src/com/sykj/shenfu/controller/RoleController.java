package com.sykj.shenfu.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sykj.shenfu.dao.IMenuDao;
import com.sykj.shenfu.dao.IRoleDao;
import com.sykj.shenfu.dao.IRolemenurefDao;
import com.sykj.shenfu.po.Menu;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Role;
import com.sykj.shenfu.po.Rolemenuref;
import com.sykj.shenfu.service.IOperatorlogService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/role")
@Transactional
public class RoleController extends BaseController {

	@Autowired
	private IMenuDao menuDao;
	@Autowired
	private IRoleDao roleDao;
	@Autowired
	private IRolemenurefDao rolemenurefDao;
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private IOperatorlogService operatorlogService;

	/**
	 * 查询角色
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Role form) {
		form.setPager_openset(Integer.valueOf(servletContext.getInitParameter("pager_openset")));
		form.setPager_count(roleDao.findByCount(form));
		form.setRolelist(roleDao.findByList(form));
		return "role/findRoleList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Role form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		Integer total = roleDao.findByCount(form);
		List<Role> list = roleDao.findByList(form);
		
		result.put("total", total);
		result.put("rows", list);
		return result;
	}
	
	/**
	 * 编辑权限
	 */
	@RequestMapping(value = "/editRole")
	public String editRole(Role form, HttpServletRequest request) {
		form.setRole(roleDao.findById(form.getId()));
		return "role/editRole";
	}


	/**
	 * 查看权限
	 */
	@RequestMapping(value = "/checkRole")
	public String checkRole(Role form, HttpServletRequest request) {
		form.setRole(roleDao.findById(form.getId()));
		return "role/checkRole";
	}
	
	/**
	 * 添加角色页面初始化
	 */
	@RequestMapping(value = "/addRoleInit")
	public String addRoleInit(Role form) {
		return "role/addRole";
	}

	/**
	 * 保存添加角色
	 */
	@RequestMapping(value = "/saveRole")
	public String saveRole(Role form, HttpServletRequest request) {
		if (!(roleDao.findByRolecode(form.getRolecode()) == null)) {
			form.setReturninfo("角色编码已经存在");
		} else {
			form.setType("1");
			roleDao.save(form);
			/*************** 关联表批量插入 ****************/
			Integer roleid = form.getId();
			String[] menuids = request.getParameter("menuids").split(",");
			ArrayList<Rolemenuref> reflist = new ArrayList<Rolemenuref>();
			for (String menuid : menuids) {
				Rolemenuref ref = new Rolemenuref();
				ref.setRoleid(roleid);
				ref.setMeunuid(Integer.valueOf(menuid));
				reflist.add(ref);
			}
			rolemenurefDao.saveBatch(reflist);
			
			//增加操作日记
			Operator operator = (Operator)getSession().getAttribute("Operator");
			String content = "添加角色，角色名称:"+form.getRolename();
			operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
			
			form.setReturninfo("保存成功");
		}
		return addRoleInit(form);
	}

	/**
	 * 编辑角色权限初始化页面
	 */
	@RequestMapping(value = "/updateRoleInit")
	public String updateRoleInit(Role form) {
		return "role/editRole";
	}

	/**
	 * 保存编辑后角色权限
	 */
	@RequestMapping(value = "/updateRole")
	public String updateRole(Role form, HttpServletRequest request) {
		//form.setType("1");
		roleDao.update(form);
		Integer roleid = form.getId();
		rolemenurefDao.deleteByRoleid(roleid);
		/*************** 关联表批量插入 ****************/
		String[] menuids = request.getParameter("menuids").split(",");
		ArrayList<Rolemenuref> reflist = new ArrayList<Rolemenuref>();
		for (String menuid : menuids) {
			Rolemenuref ref = new Rolemenuref();
			ref.setRoleid(roleid);
			ref.setMeunuid(Integer.valueOf(menuid));
			reflist.add(ref);
		}
		rolemenurefDao.saveBatch(reflist);
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "修改角色，角色名称:"+form.getRolename();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("修改成功");
		return updateRoleInit(form);
	}

	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Role form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Role role = roleDao.findById(form.getId());
		
		//先删除角色包含的菜单
		rolemenurefDao.deleteByRoleid(form.getId());
		//再删除角色
		roleDao.delete(form.getId());
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "删除角色，角色名称:"+role.getRolename();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/deleteSelected")
	public String deleteSelected(Role form, HttpServletRequest request) {
		String[] idArray = request.getParameterValues("ids");
		if (idArray == null || idArray.length < 1) {
			form.setReturninfo("请选择一条记录");
		} else {
			for (int i = 0; i < idArray.length; i++) {
				System.out.println(idArray[i]);
				Integer roleid = Integer.valueOf(idArray[i]);
				roleDao.delete(roleid);
				rolemenurefDao.deleteByRoleid(roleid);
			}
			form.setReturninfo("删除成功");
		}
		return findByList(form);
	}

	@ResponseBody
	@RequestMapping(value = "/getMenuTreeJson")
	public List<Map<String, Object>> getMenuTreeJson(String roleid) {
		Menu menufinder = new Menu();
		menufinder.setState("1");
		List<Menu> menuList = menuDao.queryByList(menufinder);
		List<Map<String, Object>> menuTreeJSON = new ArrayList<Map<String, Object>>();
		for (Menu menu : menuList) {
			if (menu.getPid() == null) {
				HashMap<String, Object> menuMap = new HashMap<String, Object>();
				menuMap.put("id", menu.getId());
				menuMap.put("text", BigDecimal.valueOf(menu.getMenuorder()).stripTrailingZeros() + " " + menu.getMenuname());
				List<Map<String, Object>> children = buildNode(roleid, menu.getId(), menuList);
				if (null != children && 0 < children.size()) {
					String openflag = "closed";
					for (Map<String, Object> child : children) {
						if (child.containsKey("checked")) {
							openflag = "open";
							break;
						}
					}
					menuMap.put("state", openflag);
					menuMap.put("children", children);
				}
				menuTreeJSON.add(menuMap);
			}
		}
		return menuTreeJSON;
	}

	private List<Map<String, Object>> buildNode(String roleid, int pid, List<Menu> menuList) {
		List<Map<String, Object>> menuTreeJSON = new ArrayList<Map<String, Object>>();
		Set<Integer> set = new HashSet<Integer>();
		if (!(roleid == null || roleid.equals(""))) {
			List<Rolemenuref> refList = rolemenurefDao.findByRoleid(Integer.valueOf(roleid));
			for (Rolemenuref rolemenuref : refList) {
				set.add(rolemenuref.getMeunuid());
			}
		}
		for (Menu menu : menuList) {
			HashMap<String, Object> menuMap = new HashMap<String, Object>();
			if (menu.getPid() != null && menu.getPid().equals(pid)) {
				menuMap.put("id", menu.getId());
				menuMap.put("text", menu.getMenuorder() + " " +  menu.getMenuname());
				if (set != null) {
					if (set.contains(menu.getId())) {
						menuMap.put("checked", true);
					}
				}
				menuTreeJSON.add(menuMap);
			}
		}
		return menuTreeJSON;
	}

}
