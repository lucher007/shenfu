package com.sykj.shenfu.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sykj.shenfu.common.ConfigUtil;
import com.sykj.shenfu.common.DateUtils;
import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.IActivityDao;
import com.sykj.shenfu.dao.ICellextendDao;
import com.sykj.shenfu.dao.ICellstationDao;
import com.sykj.shenfu.dao.ICellstationemployeeDao;
import com.sykj.shenfu.dao.IEmployeeDao;
import com.sykj.shenfu.dao.IHelpinfoDao;
import com.sykj.shenfu.dao.IProductDao;
import com.sykj.shenfu.dao.IProductcolorDao;
import com.sykj.shenfu.dao.IProductmodelDao;
import com.sykj.shenfu.dao.IProductmodelrefDao;
import com.sykj.shenfu.dao.IProducttypeDao;
import com.sykj.shenfu.dao.ISaleenergyinfoDao;
import com.sykj.shenfu.dao.ISaleenergyruleDao;
import com.sykj.shenfu.dao.ISalegaininfoDao;
import com.sykj.shenfu.dao.ISalegainlogDao;
import com.sykj.shenfu.dao.IUserDao;
import com.sykj.shenfu.dao.IUserdeliveryDao;
import com.sykj.shenfu.dao.IUserdispatchDao;
import com.sykj.shenfu.dao.IUserdoorDao;
import com.sykj.shenfu.dao.IUserdoordataDao;
import com.sykj.shenfu.dao.IUserorderDao;
import com.sykj.shenfu.dao.IUserorderinfoDao;
import com.sykj.shenfu.dao.IUserpaylogDao;
import com.sykj.shenfu.dao.IUserproductDao;
import com.sykj.shenfu.dao.IUsertaskDao;
import com.sykj.shenfu.po.Activity;
import com.sykj.shenfu.po.Cellextend;
import com.sykj.shenfu.po.Cellstation;
import com.sykj.shenfu.po.Cellstationemployee;
import com.sykj.shenfu.po.Coderule;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Helpinfo;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Product;
import com.sykj.shenfu.po.Productcolor;
import com.sykj.shenfu.po.Productmodel;
import com.sykj.shenfu.po.Productmodelref;
import com.sykj.shenfu.po.Producttype;
import com.sykj.shenfu.po.Saleenergyinfo;
import com.sykj.shenfu.po.Saleenergyrule;
import com.sykj.shenfu.po.Salegaininfo;
import com.sykj.shenfu.po.Salegainlog;
import com.sykj.shenfu.po.Userdelivery;
import com.sykj.shenfu.po.Userdispatch;
import com.sykj.shenfu.po.Userdoor;
import com.sykj.shenfu.po.Userdoordata;
import com.sykj.shenfu.po.Userorder;
import com.sykj.shenfu.po.Userorderinfo;
import com.sykj.shenfu.po.Userproduct;
import com.sykj.shenfu.po.Usertask;
import com.sykj.shenfu.service.ICoderuleService;
import com.sykj.shenfu.service.IHttpForMpsService;
import com.sykj.shenfu.service.IOperatorlogService;

import fr.opensagres.xdocreport.core.utils.StringUtils;


/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/httpForMps")
@Transactional
public class HttpForMpsController extends BaseController {

	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IUsertaskDao taskDao;
	@Autowired
	private ICoderuleService coderuleService;
	@Autowired
	private IProductmodelDao productmodelDao;
	@Autowired
	private IUserproductDao userproductDao;
	@Autowired
	private IUserorderDao userorderDao;
	@Autowired
	private IUserorderinfoDao userorderinfoDao;
	@Autowired
	private IUserdispatchDao userdispatchDao;
	@Autowired
	private IUserdeliveryDao userdeliveryDao;
	@Autowired
	private IProductmodelrefDao productmodelrefDao;
	@Autowired
	private IProductDao productDao;
	@Autowired
	private IProducttypeDao producttypeDao;
	@Autowired
	private IHttpForMpsService httpForMpsService;
	@Autowired
	private ISalegaininfoDao salegaininfoDao;
	@Autowired
	private ISaleenergyinfoDao saleenergyinfoDao;
	@Autowired
	private ISaleenergyruleDao saleenergyruleDao;
	@Autowired
	private IHelpinfoDao helpinfoDao;
	@Autowired
	private ICellextendDao cellextendDao;
	@Autowired
	private ICellstationDao cellstationDao;
	@Autowired
	private ICellstationemployeeDao cellstationemployeeDao;
	@Autowired
	private IActivityDao activityDao;
	@Autowired
	private IProductcolorDao productcolorDao;
	@Autowired
	private ISalegainlogDao salegainlogDao;
	@Autowired
	private IUserdoorDao userdoorDao;
	@Autowired
	private IUserdoordataDao userdoordataDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	
	/*
     * 特殊的instance变量
     * 注：零长度的byte数组对象创建起来将比任何对象都经济――查看编译后的字节码：
     * 生成零长度的byte[]对象只需3条操作码，而Object lock = new Object()则需要7行操作码。
     */
    private static byte[] lock = new byte[0];   
	
	@ResponseBody
	@RequestMapping(value = "/commServerForSaleMPS", method = RequestMethod.POST)
	// 销售服务器请求总接口
	// http://localhost:8080/shenfu/httpForMps/commServerForMPS?methodname=?&parajson={}
	public Map<String, Object> commServerForSaleMPS(HttpServletRequest request,HttpServletResponse response) {
		
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		String methodname = Tools.getStr(request.getParameter("methodname")).trim();
		String parajson = Tools.getStr(request.getParameter("parajson"));
		JSONObject jsonObj = null;
		
		try{
			jsonObj = new JSONObject(parajson);
		}catch(Exception e){
			e.printStackTrace();
			responseMap.put("status", "0");//-请输入正确的JSON请求格式
			responseMap.put("result", "请输入正确的JSON请求格式");//-
			return responseMap;
		}
		try{
			
			String flag = "1";
			
			if("1".equals(flag)){
				responseMap.put("status", "0");//-请输入正确的请求格式
				responseMap.put("result", "APP目前暂停使用，请您前往 申亚科技 微信公众号-后台管理，进行登录操作");
				return responseMap;
			}
			
			if("operatorLogin".equals(methodname)){//销售APP登录
				/***************** 封装userbusiness主表 ******************/
				return saveOperatorLogin(jsonObj);
			}else if("saveOperatorRegister".equals(methodname)){//销售员注册
				/***************** 封装userbusiness主表 ******************/
				return saveOperatorRegister(jsonObj);
			}else if("saveUserRegister".equals(methodname)){//客户注册，上门类型0-公司派人讲解测量
				/***************** 封装userbusiness主表 ******************/
				return saveUserRegister(jsonObj);
			}else if("getTaskListInfo".equals(methodname)){//获取任务信息
				/***************** 封装userbusiness主表 ******************/
				return getTaskListInfo(jsonObj);
			}else if("getSalerTaskListInfo".equals(methodname)){//获取任务信息
				/***************** 封装userbusiness主表 ******************/
				return getSalerTaskListInfo(jsonObj);
			}else if("saveTaskdata".equals(methodname)){//上传上门数据
				/***************** 封装userbusiness主表 ******************/
				return saveTaskdata(jsonObj);
			}else if("getProducttypeInfo".equals(methodname)){//查询所有产品类别
				/***************** 封装userbusiness主表 ******************/
				return getProducttypeInfo(jsonObj);
			}else if("getProductInfo".equals(methodname)){//查询所有产品信息
				/***************** 封装userbusiness主表 ******************/
				return getProductInfo(jsonObj);
			}else if("getProductmodelInfo".equals(methodname)){//查询所有产品型号
				/***************** 封装userbusiness主表 ******************/
				return getProductmodelInfo(jsonObj);
			}else if("findProducttypeAndProudctInfoByModelcode".equals(methodname)){//查询所有产品型号
				/***************** 封装userbusiness主表 ******************/
				return findProducttypeAndProudctInfoByModelcode(jsonObj);
			}else if("getDispatchListInfo".equals(methodname)){//获取工单信息
				/***************** 封装userbusiness主表 ******************/
				return getDispatchListInfo(jsonObj);
			}else if("saveDispatchdata".equals(methodname)){//上传二次上门安装数据
				/***************** 封装userbusiness主表 ******************/
				return saveDispatchdata(jsonObj);
			}else if("getSalegaintotalmoney".equals(methodname)){//查询提成总金额
				/***************** 封装userbusiness主表 ******************/
				return getSalegaintotalmoney(jsonObj);
			}else if("getSalegaininfoList".equals(methodname)){//查询提成明细列表
				/***************** 封装userbusiness主表 ******************/
				return getSalegaininfoList(jsonObj);
			}else if("getSaleScoreInfo".equals(methodname)){//查询行动力总和
				/***************** 封装userbusiness主表 ******************/
				return getSaleScoreInfo(jsonObj);
			}else if("findSaleenergyinfoList".equals(methodname)){//查询行动力明细列表
				/***************** 封装userbusiness主表 ******************/
				return findSaleenergyinfoList(jsonObj);
			}else if("savePaymentInfo".equals(methodname)){//保存支付信息
				/***************** 封装userbusiness主表 ******************/
				return savePaymentInfo(jsonObj);
			}else if("getTalkerAndVisitorListInfo".equals(methodname)){//讲解上门查询
				/***************** 封装userbusiness主表 ******************/
				return getTalkerAndVisitorListInfo(jsonObj);
			}else if("findUserproductListInfo".equals(methodname)){//查询客户订单对应产品信息
				/***************** 封装userbusiness主表 ******************/
				return findUserproductListInfo(jsonObj);
			}else if("saveTaskdataForTalkerAndVisitor".equals(methodname)){//只保存订单产品信息，已讲解员身份进入保存
				/***************** 封装userbusiness主表 ******************/
				return saveTaskdataForTalkerAndVisitor(jsonObj);
			}else if("findEnergyHelpInfo".equals(methodname)){//查询行动力介绍信息
				/***************** 封装userbusiness主表 ******************/
				return findEnergyHelpInfo(jsonObj);
			}else if("saveRefuseUsertask".equals(methodname)){//任务单处理失败
				/***************** 封装userbusiness主表 ******************/
				return saveRefuseUsertask(jsonObj);
			}else if("findCellextendList".equals(methodname)){//查询小区发布信息
				/***************** 封装userbusiness主表 ******************/
				return findCellextendList(jsonObj);
			}else if("findCellextendInfoByExtendcode".equals(methodname)){//根据小区发布编号查询小区信息
				/***************** 封装userbusiness主表 ******************/
				return findCellextendInfoByExtendcode(jsonObj);
			}else if("saveAcceptCellextend".equals(methodname)){//小区发布接收
				/***************** 封装userbusiness主表 ******************/
				return saveAcceptCellextend(jsonObj);
			}else if("findCellextendBySalercode".equals(methodname)){//查看销售员抢购的楼盘信息
				/***************** 封装userbusiness主表 ******************/
				return findCellextendBySalercode(jsonObj);
			}else if("saveCellpayinfo".equals(methodname)){//查询小区支付
				/***************** 封装userbusiness主表 ******************/
				return saveCellpayinfo(jsonObj);
			}else if("saveCellstation".equals(methodname)){//保存驻点申请
				/***************** 封装userbusiness主表 ******************/
				return saveCellstation(jsonObj);
			}else if("findCellstationByExtendcode".equals(methodname)){//通过发布单单号查询驻点小区信息
				/***************** 封装userbusiness主表 ******************/
				return findCellstationByExtendcode(jsonObj);
			}else if("findEmployeeListByStationcode".equals(methodname)){//查看驻点工作人员信息
				/***************** 封装userbusiness主表 ******************/
				return findEmployeeListByStationcode(jsonObj);
			}else if("findCellstationListByEmployeecode".equals(methodname)){//查看驻点人员编号查询驻点列表
				/***************** 封装userbusiness主表 ******************/
				return findCellstationListByEmployeecode(jsonObj);
			}else if("findCellstationInfoByStationcode".equals(methodname)){//查看驻点工作人员信息
				/***************** 封装userbusiness主表 ******************/
				return findCellstationInfoByStationcode(jsonObj);
			}else if("findCellstationEmployeeListByStationcode".equals(methodname)){//查看驻点的工作人员列表信息
				/***************** 封装userbusiness主表 ******************/
				return findCellstationEmployeeListByStationcode(jsonObj);
			}else if("saveUserRegisterForCellstation".equals(methodname)){//注册客户通过驻点人员
				/***************** 封装userbusiness主表 ******************/
				return saveUserRegisterForCellstation(jsonObj);
			}else if("findChildrenSalerInfoListBySalercode".equals(methodname)){//查询销售员的下级成员信息（团队）
				/***************** 封装userbusiness主表 ******************/
				return httpForMpsService.findChildrenSalerInfoListBySalercode(jsonObj);
			}else if("findSalegaininfoStat".equals(methodname)){//查询销售员的下级成员信息（团队）
				/***************** 封装userbusiness主表 ******************/
				return httpForMpsService.findSalegaininfoStat(jsonObj);
			}else if("findActivityList".equals(methodname)){//查询活动列表
				/***************** 封装userbusiness主表 ******************/
				return findActivityList(jsonObj);
			}else if("findUserorderListByStationcode".equals(methodname)){//查看驻点的工作人员列表信息
				/***************** 封装userbusiness主表 ******************/
				return findUserorderListByStationcode(jsonObj);
			}else if("findUserdeliveryList".equals(methodname)){//获取送货单信息
				/***************** 封装userbusiness主表 ******************/
				return findUserdeliveryList(jsonObj);
			}else if("saveAcceptUserdelivery".equals(methodname)){//已到货，客户已经接收送货单
				/***************** 封装userbusiness主表 ******************/
				return saveAcceptUserdelivery(jsonObj);
			}else if("saveRefuseUserdelivery".equals(methodname)){//客户拒绝接收送货单
				/***************** 封装userbusiness主表 ******************/
				return saveRefuseUserdelivery(jsonObj);
			}else if("findProductcolorList".equals(methodname)){//获取送货单信息
				/***************** 封装userbusiness主表 ******************/
				return findProductcolorList(jsonObj);
			}else if("saveUserorder".equals(methodname)){//添加订单
				/***************** 封装userbusiness主表 ******************/
				return saveUserorder(jsonObj);
			}else if("findUserAndUserorderListBySalercode".equals(methodname)){//渠道方查询客户以及客户的订单信息
				/***************** 封装userbusiness主表 ******************/
				return findUserAndUserorderListBySalercode(jsonObj);
			}else if("findUserorderInfoByOrdercode".equals(methodname)){//查询订单详情-通过订单号
				/***************** 封装userbusiness主表 ******************/
				return findUserorderInfoByOrdercode(jsonObj);
			}else if("findUsertaskListByTalkercode".equals(methodname)){//查询讲解任务单-通过讲解员编号
				/***************** 封装userbusiness主表 ******************/
				return findUsertaskListByTalkercode(jsonObj);
			}else if("saveUserTaskByTalkercode".equals(methodname)){//客户注册，上门类型0-公司派人讲解测量
				/***************** 封装userbusiness主表 ******************/
				return saveUserTaskByTalkercode(jsonObj);
			}else if("findEmployeeinfoByPhone".equals(methodname)){//通过电话号码，查询员工信息
				/***************** 封装userbusiness主表 ******************/
				return findEmployeeinfoByPhone(jsonObj);
			}else if("updateUserorderPrice".equals(methodname)){//讲解员修改客户的订单价格
				/***************** 封装userbusiness主表 ******************/
				return updateUserorderPrice(jsonObj);
			}else if("findSalegainlogList".equals(methodname)){//查询提成发放记录列表
				/***************** 封装userbusiness主表 ******************/
				return findSalegainlogList(jsonObj);
			}else if("findSalegaininfoListByGainlogcode".equals(methodname)){//通过提成发放编号来查询提成明细信息
				/***************** 封装userbusiness主表 ******************/
				return findSalegaininfoListByGainlogcode(jsonObj);
			}else if("findUserdoorListByOrdercode".equals(methodname)){//通过订单编号查询门锁图片
				/***************** 封装userbusiness主表 ******************/
				return findUserdoorListByOrdercode(jsonObj);
			}else if("findUserdoordataByOrdercode".equals(methodname)){//通过订单编号查询门锁数据
				/***************** 封装userbusiness主表 ******************/
				return findUserdoordataByOrdercode(jsonObj);
			}else if("deleteUserdoor".equals(methodname)){//APP删除门锁图片
				/***************** 封装userbusiness主表 ******************/
				return deleteUserdoor(jsonObj);
			}else if("saveUserdoor".equals(methodname)){//APP添加门锁图片
				/***************** 封装userbusiness主表 ******************/
				return saveUserdoor(jsonObj);
			}else if("updateUserdoordata".equals(methodname)){//APP修改门锁数据
				/***************** 封装userbusiness主表 ******************/
				return updateUserdoordata(jsonObj);
			}else{
				responseMap.put("status", "0");//-请输入正确的请求格式
				responseMap.put("result", "请输入正确的请求格式");
				return responseMap;
			}
		}catch(Exception e){
			e.printStackTrace();
			responseMap.put("status", "0");//-请输入正确的请求格式
			responseMap.put("result", "请求处理失败");
			return responseMap;
		}
	}
	
	// 操作员登录
	// http://115.159.71.126:8080/shenfu/httpForMps/commServerForSaleMPS?methodname=operatorLogin&parajson={"user_phone":"123456789","password":"123456"}
	public Map<String, Object> saveOperatorLogin(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("status", "1");
		String user_phone = Tools.getStr(jsonObj.getString("user_phone"));
		String password = Tools.getStr(jsonObj.getString("password"));
		//封装登录信息
		Employee employee = employeeDao.findByPhoneStr(user_phone);
		
		if (employee != null) {
			if(password.equals(employee.getApppassword())){
				String userstate = employee.getStatus();
				if ("0".equals(userstate)) {
					responseMap.put("status", "0");//该操作员已被注销
					responseMap.put("result", "该操作员已被注销");//
				} else if("1".equals(userstate)){
					responseMap.put("status", "1");//登录成功
					// 封装用户信息
					HashMap<String, Object> employeeMap = new HashMap<String, Object>();
					employeeMap.put("employeename", Tools.getStr(employee.getEmployeename()));
					employeeMap.put("employeesex", Tools.getStr(employee.getEmployeesex()));
					employeeMap.put("birthday", Tools.getStr(employee.getBirthday()));
					employeeMap.put("identification", Tools.getStr(employee.getIdentification()));
					employeeMap.put("email", Tools.getStr(employee.getEmail()));
					employeeMap.put("address", Tools.getStr(employee.getAddress()));
					employeeMap.put("phone", Tools.getStr(employee.getPhone()));
					//employeeMap.put("level", employee.getLevel());
					employeeMap.put("status", Tools.getStr(employee.getStatus()));
					employeeMap.put("addtime", Tools.getStr(employee.getAddtime()));
					employeeMap.put("department", Tools.getStr(employee.getDepartment()));
					employeeMap.put("salescore", Tools.getStr(String.valueOf(employee.getSalescore())));
					employeeMap.put("apppassword", Tools.getStr(employee.getApppassword()));
					employeeMap.put("employeetype", employee.getEmployeetype());
					employeeMap.put("bankcardname", employee.getBankcardname());
					employeeMap.put("bankcardno", employee.getBankcardno());
					employeeMap.put("employeecode", employee.getEmployeecode());
					employeeMap.put("parentemployeecode", employee.getParentemployeecode());
					responseMap.put("employeeinfo", employeeMap);
				}
			}else{
				responseMap.put("status", "0");//登录密码错误
				responseMap.put("result", "登录密码错误");//
			}
		} else {
			responseMap.put("status", "0");//
			responseMap.put("result", "登录账号错误");//
		}
		
		return responseMap;
	}
	
	
	// 销售员注册
	// http://localhost:8080/shenfu/httpForMps/commServerForSaleMPS?methodname=userRegister&parajson={"user_phone":"18654214775","user_name":"chenguoqing"}
	public Map<String, Object> saveOperatorRegister(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		
		responseMap.put("status", "1");//注册成功
		
		//销售员电话
		String phone = Tools.getStr(jsonObj.getString("user_phone"));
		//部门
		String department = Tools.getStr(jsonObj.getString("department"));
		if(StringUtils.isNotEmpty(phone)){
			Employee employee = employeeDao.findByPhoneStr(phone);
			if(employee != null){
				responseMap.put("status", "0");//电话号码已经注册
				responseMap.put("result", "电话号码已经注册");//
				return responseMap;
			}
		}else{
			responseMap.put("status", "0");//电话号码不能为空
			responseMap.put("result", "电话号码不能为空");//
			return responseMap;
		}
				
		Employee form = new Employee();
		//销售员密码-默认从系统参数中配置
		String password = ConfigUtil.getConfigFilePath("saler_app_password", "system.properties");
		form.setApppassword(Tools.getStr(password));
		//销售员电话
		form.setPhone(phone);
		//销售员姓名
		form.setEmployeename(Tools.getStr(jsonObj.getString("user_name")));
		//销售员类型（0-兼职；1-全职）
		form.setEmployeetype(Tools.getStr(jsonObj.getString("user_type")));
		//销售员身份证号
		form.setIdentification(Tools.getStr(jsonObj.getString("user_idcard")));
		//销售员邮箱
		//form.setEmail(Tools.getStr(jsonObj.getString("user_email")));
		//销售员银行卡号
		//form.setBankcardno(Tools.getStr(jsonObj.getString("user_bankcard")));
		//上级推荐人ID
		String parentid = Tools.getStr(jsonObj.getString("user_parentid"));
		if(StringUtils.isNotEmpty(parentid)){
			form.setParentemployeecode(parentid);
		}
		//工号
		String employeecode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_employeecode);
		
		form.setEmployeecode(employeecode);
		//部门
		form.setDepartment(department);
		form.setStatus("1");
		form.setSalescore(new Integer("0"));
		
		//保存销售员信息
		employeeDao.save(form);
		responseMap.put("status", "1");//注册成功
		responseMap.put("parajson", "{'employeecode':'"+employeecode+"','password':'"+password+"'}");//注册成功
		
		return responseMap;
	}
	
	//销售员修改
	//http://localhost:8080/shenfu/httpForMps/commServerForSaleMPS?methodname=updateOperator&parajson={"user_phone":"18654214775","user_name":"chenguoqing"}
	public Map<String, Object> updateOperator(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		
		responseMap.put("status", "1");//修改成功
		
		//销售员电话
		String phone = Tools.getStr(jsonObj.getString("user_phone"));
		if(StringUtils.isNotEmpty(phone)){
			Employee employee = employeeDao.findByPhoneStr(phone);
			if(employee == null){
				responseMap.put("status", "0");
				responseMap.put("result", "此电话未注册");//
				return responseMap;
			}else{//修改此销售员的信息
				//销售员密码
				employee.setApppassword(Tools.getStr(jsonObj.getString("password")));
				//销售员类型（0-兼职；1-全职）
				//employee.setEmployeetype(Tools.getStr(jsonObj.getString("user_type")));
				//销售员姓名
				//employee.setEmployeename(Tools.getStr(jsonObj.getString("user_name")));
				//销售员身份证号
				//employee.setIdentification(Tools.getStr(jsonObj.getString("user_idcard")));
				//销售员邮箱
				//employee.setEmail(Tools.getStr(jsonObj.getString("user_email")));
				//销售员银行卡号
				//employee.setBankcardno(Tools.getStr(jsonObj.getString("user_bankcard")));
				employeeDao.update(employee);
				responseMap.put("status", "1");//修改成功
				responseMap.put("result", "修改成功");//修改成功
				return responseMap;
			}
		}else{
			responseMap.put("status", "0");//电话号码不能为空
			responseMap.put("result", "电话号码不能为空");//
			return responseMap;
		}
	}
	
	// 客户注册
	// http://localhost:8080/shenfu/httpForMps/commServerForSaleMPS?methodname=salerRegister&parajson={"client_phone":"12334234","client_name":"chenguoqing"}
	public Map<String, Object> saveUserRegister(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		
		responseMap.put("status", "0");//提交失败
		responseMap.put("result", "提交失败");//注册成功
		
		//上门类型
		String visittype = Tools.getStr(jsonObj.getString("client_visittype"));
		
		if("0".equals(visittype)){ //上门类型-0.公司派人讲解测量
			return httpForMpsService.saveUserRegisterForVisitorcode0(jsonObj);
		}else if("1".equals(visittype)){//上门类型-1.亲自讲解测量
			return httpForMpsService.saveUserRegisterForVisitorcode1(jsonObj);
		}else if("2".equals(visittype)){//上门类型-2.已讲解，公司派人上门
			return httpForMpsService.saveUserRegisterForVisitorcode2(jsonObj);
		}
		
		return responseMap;
	}


	// 查询任务单
	// test url:
	// http://localhost:8080/shenfu/httpForMps/getSalerTaskListInfo?userid=00000027
	public Map<String, Object> getSalerTaskListInfo(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> taskMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		//上门员编号
		String salercode =  Tools.getStr(jsonObj.getString("salercode"));
		//请求页码
		String page =  Tools.getStr(jsonObj.getString("page"));
		//每页显示行数
		String rows =  Tools.getStr(jsonObj.getString("rows"));
		
		Usertask taskform = new Usertask();
		taskform.setSalercode(salercode);//渠道
		taskform.setPage(Integer.valueOf(page));
		taskform.setRows(Integer.valueOf(rows));
		
		List<Usertask> tasklist = taskDao.findByList(taskform);
		
		for (Usertask task : tasklist) {
			HashMap<String, Object> taskMap = new HashMap<String, Object>();
			taskMap.put("id", task.getId());
			taskMap.put("taskcode", task.getTaskcode());
			taskMap.put("usercode", task.getUsercode());
			taskMap.put("username", task.getUsername());
			taskMap.put("usersex", task.getUsersex());
			taskMap.put("phone", task.getPhone());
			taskMap.put("address", task.getAddress());
			taskMap.put("source", task.getSource());
			taskMap.put("salercode", task.getSalercode());
			if(StringUtils.isNotEmpty(task.getSalercode())){
				Employee saler = employeeDao.findByEmployeecodeStr(task.getSalercode());
				if(saler == null){
					saler = new Employee();
				}
				taskMap.put("salername", saler.getEmployeename());
				taskMap.put("salerphone", saler.getPhone());
			}else{
				Employee saler = new Employee();
				taskMap.put("salername", saler.getEmployeename());
				taskMap.put("salerphone", saler.getPhone());
			}
			taskMap.put("visitorcode", task.getVisitorcode());
			if(StringUtils.isNotEmpty(task.getVisitorcode())){
				Employee visitor = employeeDao.findByEmployeecodeStr(task.getVisitorcode());
				if(visitor == null){
					visitor = new Employee();
				}
				taskMap.put("visitorname", visitor.getEmployeename());
				taskMap.put("visitorphone", visitor.getPhone());
			}else{
				Employee visitor = new Employee();
				taskMap.put("visitorname", visitor.getEmployeename());
				taskMap.put("visitorphone", visitor.getPhone());
			}
			taskMap.put("intention", task.getIntention());
			taskMap.put("status", task.getStatus());
			taskMap.put("addtime", StringUtils.isEmpty(task.getAddtime())?"":Tools.getStr(task.getAddtime()).substring(0, 19));
			taskMap.put("dealdate", StringUtils.isEmpty(task.getDealdate())?"":Tools.getStr(task.getDealdate()).substring(0, 19));
			taskMap.put("visittime", StringUtils.isEmpty(task.getVisittime())?"":Tools.getStr(task.getVisittime()).substring(0, 19));
			//查询订单状态
			Userorder userorder = userorderDao.findByOrdercode(task.getOrdercode());
			if(userorder != null){
				taskMap.put("ordercode", userorder.getOrdercode());
				taskMap.put("orderstatusname", userorder.getStatusname());
				taskMap.put("productfee", userorder.getProductfee());
				taskMap.put("otherfee", userorder.getOtherfee());
				taskMap.put("totalmoney", userorder.getTotalmoney());
				taskMap.put("firstpayment", userorder.getFirstpayment());
				taskMap.put("firstarrivalflag", userorder.getFirstarrivalflag());
				taskMap.put("firstarrivalflagname", userorder.getFirstarrivalflagname());
				taskMap.put("finalpayment", userorder.getFinalpayment());
				taskMap.put("finalarrivalflag", userorder.getFinalarrivalflag());
				taskMap.put("finalarrivalflagname", userorder.getFinalarrivalflagname());
			}else{
				taskMap.put("orderstatusname", task.getStatusname());
			}
			
			taskMaplist.add(taskMap);
		}
		
		responseMap.put("status", "1");//返回成功
		responseMap.put("result", "请求成功");
		responseMap.put("tasksinfo", taskMaplist);
		
		return responseMap;
	}
	
	// 查询任务单
	// test url:
	// http://localhost:8080/shenfu/httpForMps/getTaskListInfo?userid=00000027
	public Map<String, Object> getTaskListInfo(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> taskMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		//上门员编号
		String visitorcode =  Tools.getStr(jsonObj.getString("visitorcode"));
		//请求状态（0-未传数据;1-已传数据）
		String status =  Tools.getStr(jsonObj.getString("status"));
		//请求页码
		String page =  Tools.getStr(jsonObj.getString("page"));
		//每页显示行数
		String rows =  Tools.getStr(jsonObj.getString("rows"));
		
		Usertask taskform = new Usertask();
		taskform.setVisitorcode(visitorcode);
		taskform.setStatus(status);
		taskform.setPage(Integer.valueOf(page));
		taskform.setRows(Integer.valueOf(rows));
		if("0".equals(status)){//查询未处理任务单
			taskform.setSort("addtime");
		}else if("1".equals(status)){//查询已处理任务单
			taskform.setSort("dealdate");
		}
		
		List<Usertask> tasklist = taskDao.findByList(taskform);
		
		for (Usertask task : tasklist) {
			HashMap<String, Object> taskMap = new HashMap<String, Object>();
			taskMap.put("id", task.getId());
			taskMap.put("taskcode", task.getTaskcode());
			taskMap.put("usercode", task.getUsercode());
			taskMap.put("username", task.getUsername());
			taskMap.put("usersex", task.getUsersex());
			taskMap.put("phone", task.getPhone());
			taskMap.put("address", task.getAddress());
			taskMap.put("source", task.getSource());
			taskMap.put("salercode", task.getSalercode());
			if(StringUtils.isNotEmpty(task.getSalercode())){
				Employee saler = employeeDao.findByEmployeecodeStr(task.getSalercode());
				if(saler == null){
					saler = new Employee();
				}
				taskMap.put("salername", saler.getEmployeename());
				taskMap.put("salerphone", saler.getPhone());
			}else{
				Employee saler = new Employee();
				taskMap.put("salername", saler.getEmployeename());
				taskMap.put("salerphone", saler.getPhone());
			}
			taskMap.put("visitorcode", task.getVisitorcode());
			if(StringUtils.isNotEmpty(task.getVisitorcode())){
				Employee visitor = employeeDao.findByEmployeecodeStr(task.getVisitorcode());
				if(visitor == null){
					visitor = new Employee();
				}
				taskMap.put("visitorname", visitor.getEmployeename());
				taskMap.put("visitorphone", visitor.getPhone());
			}else{
				Employee visitor = new Employee();
				taskMap.put("visitorname", visitor.getEmployeename());
				taskMap.put("visitorphone", visitor.getPhone());
			}
			taskMap.put("intention", task.getIntention());
			taskMap.put("status", task.getStatus());
			taskMap.put("addtime", StringUtils.isEmpty(task.getAddtime())?"":Tools.getStr(task.getAddtime()).substring(0, 19));
			taskMap.put("dealdate", StringUtils.isEmpty(task.getDealdate())?"":Tools.getStr(task.getDealdate()).substring(0, 19));
			taskMap.put("visittime", StringUtils.isEmpty(task.getVisittime())?"":Tools.getStr(task.getVisittime()).substring(0, 19));
			//查询订单状态
			Userorder userorder = userorderDao.findByOrdercode(task.getOrdercode());
			if(userorder != null){
				taskMap.put("ordercode", userorder.getOrdercode());
				taskMap.put("orderstatus", userorder.getStatus());
				taskMap.put("orderstatusname", userorder.getStatusname());
				taskMap.put("productfee", userorder.getProductfee());
				taskMap.put("otherfee", userorder.getOtherfee());
				taskMap.put("totalmoney", userorder.getTotalmoney());
				taskMap.put("firstpayment", userorder.getFirstpayment());
				taskMap.put("firstarrivalflag", userorder.getFirstarrivalflag());
				taskMap.put("firstarrivalflagname", userorder.getFirstarrivalflagname());
				taskMap.put("finalpayment", userorder.getFinalpayment());
				taskMap.put("finalarrivalflag", userorder.getFinalarrivalflag());
				taskMap.put("finalarrivalflagname", userorder.getFinalarrivalflagname());
			}else{
				taskMap.put("orderstatusname", task.getStatusname());
			}
			
			taskMaplist.add(taskMap);
		}
		
		responseMap.put("status", "1");//返回成功
		responseMap.put("result", "请求成功");
		responseMap.put("tasksinfo", taskMaplist);
		
		return responseMap;
	}

	// 保存上门数据
	// http://localhost:8080/shenfu/httpForMps/commServerForSaleMPS?methodname=salerRegister&parajson={"client_phone":"12334234","client_name":"chenguoqing"}
	public Map<String, Object> saveTaskdata(JSONObject jsonObj) {
		//调用service，使其事务手动控制
		return httpForMpsService.saveTaskdata(jsonObj);
	}
	
	// 查询行动力帮助信息
	// test url:
	// http://localhost:8080/shenfu/httpForMps/getProducttypeInfo?userid=00000027
	@ResponseBody
	@RequestMapping(value = "/findEnergyHelpInfo")
	public Map<String, Object> findEnergyHelpInfo(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		Helpinfo helpinfoform = new Helpinfo();
		helpinfoform.setType("0");//行动力
		//查询所有的产品型号信息
		List<Helpinfo> helpinfolist = helpinfoDao.queryByList(helpinfoform);
		
		for (Helpinfo helpinfo : helpinfolist) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("id", helpinfo.getId());
			objectMap.put("question", helpinfo.getQuestion());
			objectMap.put("answer", helpinfo.getAnswer());
			objectMap.put("remark", helpinfo.getRemark());
			objectMaplist.add(objectMap);
		}
		responseMap.put("status", "1");
		responseMap.put("result", "请求成功");
		responseMap.put("helpinfo", objectMaplist);
		
		return responseMap;
	}
	
	// 同步所有产品
	// test url:
	// http://localhost:8080/shenfu/httpForMps/getProductInfo?userid=00000027
	@ResponseBody
	@RequestMapping(value = "/findProductcolorList")
	public Map<String, Object> findProductcolorList(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		Productcolor productcolorform = new Productcolor();
		//查询所有的产品型号信息
		List<Productcolor> productcolorlist = productcolorDao.queryByList(productcolorform);
		
		for (Productcolor productcolor : productcolorlist) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("id", productcolor.getId());
			objectMap.put("productcode", productcolor.getColorcode());
			objectMap.put("productname", productcolor.getColorname());
			objectMap.put("remark", productcolor.getRemark());
			objectMaplist.add(objectMap);
		}
		responseMap.put("status", "1");
		responseMap.put("result", "请求成功");
		responseMap.put("productcolorinfo", objectMaplist);
		
		return responseMap;
	}
	
	// 同步所有产品型号
	// test url:
	// http://localhost:8080/shenfu/httpForMps/getProductmodelInfo?userid=00000027
	@ResponseBody
	@RequestMapping(value = "/getProductmodelInfo")
	public Map<String, Object> getProductmodelInfo(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		//查询所有的产品型号信息
		Productmodel productmodelform = new Productmodel();
		productmodelform.setEffectivetimeflag("1");
		productmodelform.setStatus("1");
		List<Productmodel> productmodellist = productmodelDao.queryByList(productmodelform);
		for (Productmodel productmodel : productmodellist) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("id", productmodel.getId());
			objectMap.put("modelname", productmodel.getModelname());
			objectMap.put("modelcode", productmodel.getModelcode());
			objectMap.put("price", productmodel.getPrice());
			objectMap.put("lockcoreflag", productmodel.getLockcoreflag());
			objectMap.put("content", productmodel.getContent());
			objectMap.put("remark", productmodel.getRemark());
			objectMaplist.add(objectMap);
		}
		responseMap.put("productmodelinfo", objectMaplist);
		
		//查询所有的型号关联的产品信息
		List<HashMap<String, Object>> productmodelrefMaplist = new ArrayList<HashMap<String, Object>>();
		Productmodelref productmodelrefform = new Productmodelref();
		List<Productmodelref> productmodelreflist = productmodelrefDao.queryByList(productmodelrefform);
		for (Productmodelref productmodelref : productmodelreflist) {
			HashMap<String, Object> productmodelrefMap = new HashMap<String, Object>();
			productmodelrefMap.put("id", productmodelref.getId());
			productmodelrefMap.put("modelcode", productmodelref.getModelcode());
			productmodelrefMap.put("modelname", productmodelref.getModelname());
			productmodelrefMap.put("typecode", productmodelref.getTypecode());
			productmodelrefMap.put("typename", productmodelref.getTypename());
			productmodelrefMap.put("remark", productmodelref.getRemark());
			productmodelrefMaplist.add(productmodelrefMap);
		}
		responseMap.put("status", "1");
		responseMap.put("result", "请求成功");
		responseMap.put("productmodelrefinfo", productmodelrefMaplist);
		
		return responseMap;
	}
	
	
	/**
	 * 查询产品型号关联对应的产品类别以及产品信息
	 * 
	 * @param String
	 * @return
	 */
	public Map<String, Object> findProducttypeAndProudctInfoByModelcode(JSONObject jsonObj){
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");//提交失败
		responseMap.put("result", "查询成功");//注册成功
		
		//上门类型
		String modelcode = Tools.getStr(jsonObj.getString("modelcode"));
		if(StringUtils.isEmpty(modelcode)){
			responseMap.put("status", "0");//提交失败
			responseMap.put("result", "查询失败，型号编码不能为空");
		}
		
		Productmodel productmodel = productmodelDao.findByModelcode(modelcode);
		if(productmodel == null){
			responseMap.put("status", "0");//提交失败
			responseMap.put("result", "查询失败，该产品型号不存在");
		}
		
		List<Productmodelref> productmodelreflist = productmodelrefDao.findByModelcode(modelcode);
		
		if(productmodelreflist == null || productmodelreflist.size() < 1){
			responseMap.put("status", "0");//提交失败
			responseMap.put("result", "查询失败，该产品型号未配置任何产品信息");
		}
		for (Productmodelref productmodelref : productmodelreflist) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			if(StringUtils.isEmpty(productmodelref.getProductcode())){//把没有包含产品的列表封装发送给APP
				objectMap.put("modelcode", productmodelref.getModelcode());
				objectMap.put("modelname", productmodelref.getModelname());
				objectMap.put("typecode", productmodelref.getTypecode());
				objectMap.put("typename", productmodelref.getTypename());
				List<HashMap<String, Object>> objectproductlist = new ArrayList<HashMap<String, Object>>();
				List<Product> productlist = productDao.findByTypecode(productmodelref.getTypecode());
				for (Product product : productlist) {
					HashMap<String, Object> productMap = new HashMap<String, Object>();
					productMap.put("productcode", product.getProductcode());
					productMap.put("productname", product.getProductname());
					productMap.put("imageUrl", product.getImageurl());
					productMap.put("price", product.getPrice());
					productMap.put("salesCount", "0");
					objectproductlist.add(productMap);
				}
				objectMap.put("productList", objectproductlist);
				objectlist.add(objectMap);
			}
		}
		
		responseMap.put("results", objectlist);
		
		return responseMap;
	}
	
	// 查询工单
	// test url:
	// http://localhost:8080/shenfu/httpForMps/getDispatchListInfo?userid=00000027
	public Map<String, Object> getDispatchListInfo(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		//上门员编号
		String installercode =  Tools.getStr(jsonObj.getString("installercode"));
		//请求状态（0-未传数据;1-已传数据）
		String status =  Tools.getStr(jsonObj.getString("status"));
		//请求页码
		String page =  Tools.getStr(jsonObj.getString("page"));
		//每页显示行数
		String rows =  Tools.getStr(jsonObj.getString("rows"));
		
		Userdispatch dispatchform = new Userdispatch();
		dispatchform.setInstallercode(installercode);
		
		if("0".equals(status)){//APP查看未处理的工单
			dispatchform.setStatus("12");
		}else if("1".equals(status)){//查询处理的工单
			dispatchform.setStatus("34");
		}
		dispatchform.setPage(Integer.valueOf(page));
		dispatchform.setRows(Integer.valueOf(rows));
		if("0".equals(status)){//查询未处理任务单
			dispatchform.setSort("addtime");
		}else if("1".equals(status)){//查询已处理任务单
			dispatchform.setSort("dealdate");
		}
		
		List<Userdispatch> dispatchlist = userdispatchDao.findByList(dispatchform);
		
		for (Userdispatch dispatch : dispatchlist) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("id", dispatch.getId());
			objectMap.put("dispatchcode", dispatch.getDispatchcode());
			objectMap.put("usercode", dispatch.getUsercode());
			objectMap.put("username", dispatch.getUsername());
			objectMap.put("usersex", dispatch.getUsersex());
			objectMap.put("phone", dispatch.getPhone());
			objectMap.put("address", dispatch.getAddress());
			objectMap.put("source", dispatch.getSource());
			objectMap.put("ordercode", dispatch.getOrdercode());
			
			Userorder userorder = null;
			if(StringUtils.isNotEmpty(dispatch.getOrdercode())){
				userorder = userorderDao.findByOrdercode(dispatch.getOrdercode());
			}
			if(userorder == null){
				userorder = new Userorder();
			}
			objectMap.put("productfee", userorder.getProductfee());
			objectMap.put("otherfee", userorder.getOtherfee());
			objectMap.put("totalmoney", userorder.getTotalmoney());
			objectMap.put("firstpayment", userorder.getFirstpayment());
			objectMap.put("firstarrivalflag", userorder.getFirstarrivalflag());
			objectMap.put("firstarrivalflagname", userorder.getFirstarrivalflagname());
			objectMap.put("finalpayment", userorder.getFinalpayment());
			objectMap.put("finalarrivalflag", userorder.getFinalarrivalflag());
			objectMap.put("finalarrivalflagname", userorder.getFinalarrivalflagname());
			objectMap.put("orderstatus", userorder.getStatus());
			objectMap.put("orderstatusname", userorder.getStatusname());
				
			objectMap.put("installercode", dispatch.getInstallercode());
			if(StringUtils.isNotEmpty(dispatch.getInstallercode())){
				Employee installer = employeeDao.findByEmployeecodeStr(dispatch.getInstallercode());
				if(installer == null){
					installer = new Employee();
				}
				objectMap.put("installername", installer.getEmployeename());
				objectMap.put("installerphone", installer.getPhone());
			}else{
				Employee installer = new Employee();
				objectMap.put("installername", installer.getEmployeename());
				objectMap.put("installerphone", installer.getPhone());
			}
			objectMap.put("addtime", StringUtils.isEmpty(dispatch.getAddtime())?"":Tools.getStr(dispatch.getAddtime()).substring(0, 19));
			objectMap.put("installtime", StringUtils.isEmpty(dispatch.getInstalltime())?"":Tools.getStr(dispatch.getInstalltime()).substring(0, 19));
			objectMap.put("dealdate", StringUtils.isEmpty(dispatch.getDealdate())?"":Tools.getStr(dispatch.getDealdate()).substring(0, 19));
			objectMap.put("content", dispatch.getContent());
			objectMap.put("dealresult", dispatch.getDealresult());
			objectMap.put("status", dispatch.getStatus());
			objectMap.put("statusname", dispatch.getStatusname());
			objectMap.put("checkstatus", dispatch.getCheckstatus());
			objectMap.put("checkstatusname", dispatch.getCheckstatusname());
			objectMap.put("checkresult", dispatch.getCheckresult());
			
			//查询该工单对应订单包含的产品
			List<HashMap<String, Object>> userproductMaplist = new ArrayList<HashMap<String, Object>>();
			Userproduct userproductform = new Userproduct();
			userproductform.setOrdercode(dispatch.getOrdercode());
			List<Userproduct> userproductlist = userproductDao.queryByList(userproductform);
			if(userproductlist != null && userproductlist.size() > 0){
				for (Userproduct userproduct : userproductlist) {
					HashMap<String, Object> userproductMap = new HashMap<String, Object>();
					userproductMap.put("modelname", userproduct.getModelname());
					userproductMap.put("modelcode", userproduct.getModelcode());
					userproductMap.put("productname", userproduct.getProductname());
					userproductMap.put("productcode", userproduct.getProductcode());
					userproductMap.put("productidentfy", userproduct.getProductidentfy());
					userproductMap.put("buytype", userproduct.getBuytype());
					userproductMaplist.add(userproductMap);
				}
			}
			//该工单包含的产品信息
			objectMap.put("productsinfo", userproductMaplist);
			
			//查询该工单对应快递信息
			List<HashMap<String, Object>> userdeliveryMaplist = new ArrayList<HashMap<String, Object>>();
			Userdelivery userdeliveryform = new Userdelivery();
			userdeliveryform.setOrdercode(dispatch.getOrdercode());
			List<Userdelivery> userdeliverylist = userdeliveryDao.queryByList(userdeliveryform);
			if(userdeliverylist != null && userdeliverylist.size() >0){
				for (Userdelivery userdelivery : userdeliverylist) {
					HashMap<String, Object> userdeliveryMap = new HashMap<String, Object>();
					userdeliveryMap.put("deliverycode", userdelivery.getDeliverycode());
					userdeliveryMap.put("deliveryname", userdelivery.getDeliveryname());
					userdeliveryMap.put("deliverynamekey", "");
					userdeliveryMap.put("deliverytime", userdelivery.getDeliverytime());
					userdeliveryMap.put("content", userdelivery.getContent());
					userdeliveryMap.put("deliverercode", userdelivery.getDeliverercode());
					userdeliveryMap.put("deliverername", userdelivery.getDeliverername());
					userdeliveryMap.put("delivererphone", userdelivery.getDelivererphone());
					userdeliveryMaplist.add(userdeliveryMap);
				}
			}
			//该工单包含的产品信息
			objectMap.put("deliverysinfo", userdeliveryMaplist);
			
			
			//查询该工单对应订单的明细
			List<HashMap<String, Object>> orderinfoMaplist = new ArrayList<HashMap<String, Object>>();
			//订单明细
			List<Userorderinfo> orderinfolist = userorderinfoDao.findByOrdercode(dispatch.getOrdercode());
			if(orderinfolist != null && orderinfolist.size() >0){
				for (Userorderinfo userorderinfo : orderinfolist) {
					HashMap<String, Object> orderinfoMap = new HashMap<String, Object>();
					orderinfoMap.put("price", userorderinfo.getPrice());
					orderinfoMap.put("buytype", userorderinfo.getBuytype());
					orderinfoMap.put("modelname", userorderinfo.getModelname());
					orderinfoMap.put("modelcode", userorderinfo.getModelcode());
					orderinfoMap.put("productname", userorderinfo.getProductname());
					orderinfoMap.put("productcode", userorderinfo.getProductcode());
					orderinfoMaplist.add(orderinfoMap);
				}
			}
			//该工单包含的订单详细
			objectMap.put("orderinfos", orderinfoMaplist);
			
			objectMaplist.add(objectMap);
			
		}
		responseMap.put("status", "1");//返回成功
		responseMap.put("result", "请求成功");
		responseMap.put("dispatchsinfo", objectMaplist);
		
		return responseMap;
	}
	
	// 保存二次安装数据
	// http://localhost:8080/shenfu/httpForMps/commServerForSaleMPS?methodname=salerRegister&parajson={"client_phone":"12334234","client_name":"chenguoqing"}
	public Map<String, Object> saveDispatchdata(JSONObject jsonObj) {
		//调用service，使其事务手动控制
		return httpForMpsService.saveDispatchdata(jsonObj);
	}
	
	// 查询提成总金额
	// test url:
	// http://localhost:8080/shenfu/httpForMps/getProductmodelInfo?userid=00000027
	public Map<String, Object> getSalegaintotalmoney(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		//List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		
		//请求状态（0-未提取;1-已提取）
		String status =  Tools.getStr(jsonObj.getString("status"));
		String gainercode = Tools.getStr(jsonObj.getString("gainercode"));
		
		//查询所有的产品型号信息
		Salegaininfo salegaininfoform = new Salegaininfo();
		salegaininfoform.setStatus(status);
		salegaininfoform.setGainercode(gainercode);
		
		if("1".equals(status)){ //查询上个月已提取的提成金额
			Date date = new Date();
			//当月的第一天
			String firstDateOfSeason = DateUtils.formatDate(DateUtils.getFirstDateOfMonth(date));
			//上月的第一天
			String firstDateOfMonth_pre_one = Tools.getSpecifiedDayAfterMonth(firstDateOfSeason,"yyyy-MM-dd",-1);
			//将上月第一天转化为时间格式
			Date firstDateOfSeason_pre_one_Date = DateUtils.parseDate(firstDateOfMonth_pre_one);
			//上月的最后一天
			String lastDateOfSeason_pre_one = DateUtils.formatDate(DateUtils.getLastDateOfMonth(firstDateOfSeason_pre_one_Date));
			salegaininfoform.setBegintime(firstDateOfMonth_pre_one);
			salegaininfoform.setEndtime(lastDateOfSeason_pre_one);
		}
		
		Long gaintotalmoney = salegaininfoDao.findGaintotalmoney(salegaininfoform);
		if(gaintotalmoney == null){
			gaintotalmoney = new Long(0);
		}
		
		responseMap.put("status", "1");
		responseMap.put("result", "请求成功");
		responseMap.put("totalmoney", gaintotalmoney.toString());
		
		return responseMap;
	}
	
	// 查询提成明细列表
	// test url:
	// http://localhost:8080/shenfu/httpForMps/getProductmodelInfo?userid=00000027
	public Map<String, Object> getSalegaininfoList(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		//请求状态（0-未提取;1-已提取）
		String status =  Tools.getStr(jsonObj.getString("status"));
		String gainercode = Tools.getStr(jsonObj.getString("gainercode"));
		//请求页码
		String page =  Tools.getStr(jsonObj.getString("page"));
		//每页显示行数
		String rows =  Tools.getStr(jsonObj.getString("rows"));
		
		//查询所有的产品型号信息
		Salegaininfo salegaininfoform = new Salegaininfo();
		salegaininfoform.setStatus(status);
		salegaininfoform.setGainercode(gainercode);
		salegaininfoform.setPage(Integer.valueOf(page));
		salegaininfoform.setRows(Integer.valueOf(rows));
		
		if("1".equals(status)){ //查询已提取上个月的提成金额明细
			Date date = new Date();
			//当月的第一天
			String firstDateOfSeason = DateUtils.formatDate(DateUtils.getFirstDateOfMonth(date));
			//当月的最后一天
			String lastDateOfSeason = DateUtils.formatDate(DateUtils.getLastDateOfMonth(date));
			//上月的第一天
			String firstDateOfMonth_pre_one = Tools.getSpecifiedDayAfterMonth(firstDateOfSeason,"yyyy-MM-dd",-1);
			//上月的最后一天
			String lastDateOfMonth_pre_one = Tools.getSpecifiedDayAfterMonth(lastDateOfSeason,"yyyy-MM-dd",-1);
			salegaininfoform.setBegintime(firstDateOfMonth_pre_one);
			salegaininfoform.setEndtime(lastDateOfMonth_pre_one);
		}
		
		List<Salegaininfo> salegaininfolist  = salegaininfoDao.findByList(salegaininfoform);
		for (Salegaininfo salegaininfo : salegaininfolist) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("id", salegaininfo.getId());
			//objectMap.put("gainercode", salegaininfo.getGainercode());
			//上门员信息
			Employee gainer = employeeDao.findByEmployeecodeStr(salegaininfo.getGainercode());
			if(gainer == null){
				gainer = new Employee();
			}
			objectMap.put("gainercode", salegaininfo.getGainercode());
			objectMap.put("gainername", gainer.getEmployeename());
			objectMap.put("gainerphone", gainer.getPhone());
			
			objectMap.put("usercode", salegaininfo.getUsercode());
			objectMap.put("username", salegaininfo.getUsername());
			objectMap.put("phone", salegaininfo.getPhone());
			objectMap.put("address", salegaininfo.getAddress());
			objectMap.put("source", salegaininfo.getSource());
			objectMap.put("sourcename", salegaininfo.getSourcename());
			objectMap.put("visittype", salegaininfo.getVisittype());
			objectMap.put("visittypename", salegaininfo.getVisittypename());
			objectMap.put("taskcode", salegaininfo.getTaskcode());
			//销售员信息
			if(StringUtils.isNotEmpty(salegaininfo.getSalercode())){
				Employee saler = employeeDao.findByEmployeecodeStr(salegaininfo.getSalercode());
				if(saler != null){
					objectMap.put("salercode", salegaininfo.getSalercode());
					objectMap.put("salername", saler.getEmployeename());
					objectMap.put("salerphone", saler.getPhone());
				}
			}
			//上门员信息
			Employee visitor = employeeDao.findByEmployeecodeStr(salegaininfo.getVisitorcode());
			if(visitor == null){
				visitor = new Employee();
			}
			objectMap.put("visitorcode", salegaininfo.getVisitorcode());
			objectMap.put("visitorname", visitor.getEmployeename());
			objectMap.put("visitorphone", visitor.getPhone());
			
			objectMap.put("ordercode", salegaininfo.getOrdercode());
			objectMap.put("totalmoney", salegaininfo.getTotalmoney());
			objectMap.put("gaincode", salegaininfo.getGaincode());
			objectMap.put("gainname", salegaininfo.getGainname());
			objectMap.put("gainrate", salegaininfo.getGainrate());
			objectMap.put("gainmoney", salegaininfo.getGainmoney());
			objectMap.put("addtime", StringUtils.isEmpty(salegaininfo.getAddtime())?"":Tools.getStr(salegaininfo.getAddtime()).substring(0, 19));
			objectMap.put("status", salegaininfo.getStatus());
			objectMap.put("statusname", salegaininfo.getStatusname());
			objectMap.put("gaintime", StringUtils.isEmpty(salegaininfo.getGaintime())?"":Tools.getStr(salegaininfo.getGaintime()).substring(0, 19));
			objectMap.put("remark", salegaininfo.getRemark());
			objectMaplist.add(objectMap);
		}
		
		responseMap.put("status", "1");
		responseMap.put("result", "请求成功");
		responseMap.put("salegaininfolist", objectMaplist);
		
		return responseMap;
	}
	
	
	// 查询提成行动力
	// test url:
	// http://localhost:8080/shenfu/httpForMps/getProductmodelInfo?userid=00000027
	public Map<String, Object> getSaleScoreInfo(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		//List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		
		String gainercode = Tools.getStr(jsonObj.getString("gainercode"));
		if(StringUtils.isEmpty(gainercode)){
			responseMap.put("status", "0");
			responseMap.put("result", "请求失败，销售员编号不能为空");
		}
		
		Saleenergyinfo saleenergyinfoForm = new Saleenergyinfo();
		saleenergyinfoForm.setGainercode(gainercode);
		//查询当月的行动力分
		Date date = new Date();
		//当月的第一天
		String firstDateOfSeason = DateUtils.formatDate(DateUtils.getFirstDateOfMonth(date));
		//当月的最后一天
		String lastDateOfSeason = DateUtils.formatDate(DateUtils.getLastDateOfMonth(date));
		//上月的第一天
		String firstDateOfSeason_pre_one = Tools.getSpecifiedDayAfterMonth(firstDateOfSeason,"yyyy-MM-dd",-1);
		//将上月第一天转化为时间格式
		Date firstDateOfSeason_pre_one_Date = DateUtils.parseDate(firstDateOfSeason_pre_one);
		//上月的最后一天
		String lastDateOfSeason_pre_one = DateUtils.formatDate(DateUtils.getLastDateOfMonth(firstDateOfSeason_pre_one_Date));
		//上上月的第一天
		String firstDateOfSeason_pre_two = Tools.getSpecifiedDayAfterMonth(firstDateOfSeason,"yyyy-MM-dd",-2);
		//将上上月第一天转化为时间格式
		Date firstDateOfSeason_pre_two_Date = DateUtils.parseDate(firstDateOfSeason_pre_two);
		//上上月的最后一天
		String lastDateOfSeason_pre_two = DateUtils.formatDate(DateUtils.getLastDateOfMonth(firstDateOfSeason_pre_two_Date));
		
		//当季的行动力分
		saleenergyinfoForm.setBegintime(firstDateOfSeason);
		saleenergyinfoForm.setEndtime(lastDateOfSeason);
		Long saleenergy_current =  saleenergyinfoDao.findEnergytotalscore(saleenergyinfoForm);
		responseMap.put("saleenergy_current", saleenergy_current==null?0:saleenergy_current);
		
		//上季行动力分
		saleenergyinfoForm.setBegintime(firstDateOfSeason_pre_one);
		saleenergyinfoForm.setEndtime(lastDateOfSeason_pre_one);
		Long saleenergy_pre_one =  saleenergyinfoDao.findEnergytotalscore(saleenergyinfoForm);
		responseMap.put("saleenergy_pre_one", saleenergy_pre_one==null?0:saleenergy_pre_one);
		
		//上上季行动力分
		saleenergyinfoForm.setBegintime(firstDateOfSeason_pre_two);
		saleenergyinfoForm.setEndtime(lastDateOfSeason_pre_two);
		Long saleenergy_pre_two =  saleenergyinfoDao.findEnergytotalscore(saleenergyinfoForm);
		responseMap.put("saleenergy_pre_two", saleenergy_pre_two==null?0:saleenergy_pre_two);
		
		//每月合格行动力分
		Saleenergyrule saleenertyrule = saleenergyruleDao.findByEnergycode(Saleenergyrule.energycode_saleenergy_standard);
		if(saleenertyrule == null){
			saleenertyrule = new Saleenergyrule();
		}
		responseMap.put("saleenergy_standard", saleenertyrule.getEnergyrate()==null?0:saleenertyrule.getEnergyrate());
		
		//查询行动力总和
		saleenergyinfoForm.setBegintime(null);
		saleenergyinfoForm.setEndtime(null);
		//未兑换提成的行动力
		saleenergyinfoForm.setStatus("0");
		Long saleenergy_unexchange =  saleenergyinfoDao.findEnergytotalscore(saleenergyinfoForm);
		responseMap.put("saleenergy_unexchange", saleenergy_unexchange==null?0:saleenergy_unexchange);
		
		//行动力兑换提成比例
		Saleenergyrule energycode_energy_gain = saleenergyruleDao.findByEnergycode(Saleenergyrule.energycode_energy_gain);
		//未兑换提成的行动力对应的提成金额
		responseMap.put("saleenergy_unexchange_gain", saleenergy_unexchange==null?0:saleenergy_unexchange * energycode_energy_gain.getEnergyrate());
		
		//已兑换提成的行动力
		saleenergyinfoForm.setStatus("1");
		Long saleenergy_exchangeed =  saleenergyinfoDao.findEnergytotalscore(saleenergyinfoForm);
		responseMap.put("saleenergy_exchangeed", saleenergy_exchangeed==null?0:saleenergy_exchangeed);
		//已兑换提成的行动力对应的提成金额
		responseMap.put("saleenergy_exchangeed_gain", saleenergy_exchangeed==null?0:saleenergy_exchangeed * energycode_energy_gain.getEnergyrate());
		
		responseMap.put("status", "1");
		responseMap.put("result", "请求成功");
		
		return responseMap;
	}
	
	// 查询行动力明细列表信息
	// test url:
	// http://localhost:8080/shenfu/httpForMps/getProductmodelInfo?userid=00000027
	public Map<String, Object> findSaleenergyinfoList(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		//请求状态（0-未兑换;1-已兑换）
		String status =  Tools.getStr(jsonObj.getString("status"));
		String gainercode = Tools.getStr(jsonObj.getString("gainercode"));
		//请求页码
		String page =  Tools.getStr(jsonObj.getString("page"));
		//每页显示行数
		String rows =  Tools.getStr(jsonObj.getString("rows"));
		
		//查询所有的产品型号信息
		Saleenergyinfo saleenergyinfoform = new Saleenergyinfo();
		saleenergyinfoform.setStatus(status);
		saleenergyinfoform.setGainercode(gainercode);
		saleenergyinfoform.setPage(Integer.valueOf(page));
		saleenergyinfoform.setRows(Integer.valueOf(rows));
		
		List<Saleenergyinfo> saleenergyinfolist  = saleenergyinfoDao.findByList(saleenergyinfoform);
		for (Saleenergyinfo saleenergyinfo : saleenergyinfolist) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("id", saleenergyinfo.getId());
			//objectMap.put("gainercode", salegaininfo.getGainercode());
			//上门员信息
			Employee gainer = employeeDao.findByEmployeecodeStr(saleenergyinfo.getGainercode());
			if(gainer == null){
				gainer = new Employee();
			}
			objectMap.put("gainercode", saleenergyinfo.getGainercode());
			objectMap.put("gainername", gainer.getEmployeename());
			objectMap.put("gainerphone", gainer.getPhone());
			
			objectMap.put("usercode", saleenergyinfo.getUsercode());
			objectMap.put("username", saleenergyinfo.getUsername());
			objectMap.put("phone", saleenergyinfo.getPhone());
			objectMap.put("address", saleenergyinfo.getAddress());
			objectMap.put("source", saleenergyinfo.getSource());
			objectMap.put("sourcename", saleenergyinfo.getSourcename());
			objectMap.put("visittype", saleenergyinfo.getVisittype());
			objectMap.put("visittypename", saleenergyinfo.getVisittypename());
			objectMap.put("taskcode", saleenergyinfo.getTaskcode());
			//销售员信息
			if(StringUtils.isNotEmpty(saleenergyinfo.getSalercode())){
				Employee saler = employeeDao.findByEmployeecodeStr(saleenergyinfo.getSalercode());
				if(saler != null){
					objectMap.put("salercode", saleenergyinfo.getSalercode());
					objectMap.put("salername", saler.getEmployeename());
					objectMap.put("salerphone", saler.getPhone());
				}
			}
			//上门员信息
			Employee visitor = employeeDao.findByEmployeecodeStr(saleenergyinfo.getVisitorcode());
			if(visitor == null){
				visitor = new Employee();
			}
			objectMap.put("visitorcode", saleenergyinfo.getVisitorcode());
			objectMap.put("visitorname", visitor.getEmployeename());
			objectMap.put("visitorphone", visitor.getPhone());
			
			objectMap.put("ordercode", saleenergyinfo.getOrdercode());
			objectMap.put("totalmoney", saleenergyinfo.getTotalmoney());
			objectMap.put("gaincode", saleenergyinfo.getGaincode());
			objectMap.put("gainname", saleenergyinfo.getGainname());
			objectMap.put("gainrate", saleenergyinfo.getGainrate());
			objectMap.put("gainmoney", saleenergyinfo.getGainmoney()==null?0:saleenergyinfo.getGainmoney());
			objectMap.put("energyscore", saleenergyinfo.getEnergyscore()==null?0:saleenergyinfo.getEnergyscore());
			objectMap.put("addtime", StringUtils.isEmpty(saleenergyinfo.getAddtime())?"":Tools.getStr(saleenergyinfo.getAddtime()).substring(0, 10));
			objectMap.put("status", saleenergyinfo.getStatus());
			objectMap.put("statusname", saleenergyinfo.getStatusname());
			objectMap.put("exchangetime", StringUtils.isEmpty(saleenergyinfo.getExchangetime())?"":Tools.getStr(saleenergyinfo.getExchangetime()).substring(0, 10));
			objectMap.put("remark", saleenergyinfo.getRemark());
			objectMaplist.add(objectMap);
		}
		
		responseMap.put("status", "1");
		responseMap.put("result", "请求成功");
		responseMap.put("saleenergyinfolist", objectMaplist);
		
		return responseMap;
	}
	
	// APP付款
	// http://115.159.71.126:8080/shenfu/httpForMps/commServerForSaleMPS?methodname=operatorLogin&parajson={"user_phone":"123456789","password":"123456"}
	public Map<String, Object> savePaymentInfo(JSONObject jsonObj) {
		return httpForMpsService.savePaymentInfo(jsonObj);
	}
	
	//查询讲解上门信息
	public Map<String, Object> getTalkerAndVisitorListInfo(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		
		responseMap.put("status", "0");//提交失败
		responseMap.put("result", "提交失败");//注册成功
		
		//上门类型
		String selectstate = Tools.getStr(jsonObj.getString("selectstate"));
		
		if("0".equals(selectstate)){ //0-只讲解（已讲解公司派人上门的讲解员登陆查询）
			return httpForMpsService.findTalkerListInfo(jsonObj);
		}else if("1".equals(selectstate)){ //1-只测量（已讲解公司派人上门的测量人员登陆查询）
			return httpForMpsService.findVisitorListInfo(jsonObj);
		}else if("2".equals(selectstate)){ //2-讲解加测量(公司派人上门和亲自上门都调用此接口查询)
			return httpForMpsService.findTalkerAndVisitorListInfo(jsonObj);
		}
		return responseMap;
	}
	
	//查询订户选择的产品型号信息
	public Map<String, Object> findUserproductListInfo(JSONObject jsonObj) {
		return httpForMpsService.findUserproductListInfo(jsonObj);
	}
	
	// 讲解员身份保存订单上门数据
	// http://localhost:8080/shenfu/httpForMps/commServerForSaleMPS?methodname=salerRegister&parajson={"client_phone":"12334234","client_name":"chenguoqing"}
	public Map<String, Object> saveTaskdataForTalkerAndVisitor(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("status", "0");//提交失败
		responseMap.put("result", "提交失败");//注册成功
		
		//上门类型
		String selectstate = Tools.getStr(jsonObj.getString("selectstate"));
		if("0".equals(selectstate)){ //0-只讲解保存订户产品信息
			return httpForMpsService.saveTaskdataForTalker(jsonObj);
		}else if("1".equals(selectstate)){ //1-只测量保存订户产品信息
			return httpForMpsService.saveTaskdataForVisitor(jsonObj);
		}else if("2".equals(selectstate)){ //2-讲解加测量保存订户产品信息
			return httpForMpsService.saveTaskdataForTalkerAndVisitor(jsonObj);
		}
		//调用service，使其事务手动控制
		return responseMap;
	}
	
	
	// 同步所有产品类别
	// test url:
	// http://localhost:8080/shenfu/httpForMps/getProducttypeInfo?userid=00000027
	@ResponseBody
	public Map<String, Object> getProducttypeInfo(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		Producttype producttypeform = new Producttype();
		
		//查询所有的产品型号信息
		List<Producttype> producttypelist = producttypeDao.queryByList(producttypeform);
		
		for (Producttype producttype : producttypelist) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("id", producttype.getId());
			objectMap.put("typecode", producttype.getTypecode());
			objectMap.put("typename", producttype.getTypename());
			objectMap.put("remark", producttype.getRemark());
			objectMaplist.add(objectMap);
		}
		responseMap.put("status", "1");
		responseMap.put("result", "请求成功");
		responseMap.put("producttypeinfo", objectMaplist);
		
		return responseMap;
	}
	
	//任务单拒绝安装
	// http://localhost:8080/shenfu/httpForMps/commServerForSaleMPS?methodname=salerRegister&parajson={"client_phone":"12334234","client_name":"chenguoqing"}
	public Map<String, Object> saveRefuseUsertask(JSONObject jsonObj) {
		//调用service，使其事务手动控制
		return httpForMpsService.saveRefuseUsertask(jsonObj);
	}
	
	// 查询发布小区
	// test url:
	// http://localhost:8080/shenfu/httpForMps/getProductInfo?userid=00000027
	@ResponseBody
	@RequestMapping(value = "/findCellextendList")
	public Map<String, Object> findCellextendList(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		
		//请求页码
		String page =  Tools.getStr(jsonObj.getString("page"));
		//每页显示行数
		String rows =  Tools.getStr(jsonObj.getString("rows"));
		
		Cellextend cellextendform = new Cellextend();
		cellextendform.setAcceptflag("0");
		cellextendform.setPage(Integer.valueOf(page));
		cellextendform.setRows(Integer.valueOf(rows));
		
		//查询所有的产品型号信息
		List<Cellextend> cellextendlist = cellextendDao.findByList(cellextendform);
		
		for (Cellextend cellextend : cellextendlist) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("id", cellextend.getId());
			objectMap.put("extendcode", cellextend.getExtendcode());
			objectMap.put("cellcode", cellextend.getCellcode());
			objectMap.put("cellname", cellextend.getCellname());
			objectMap.put("address", cellextend.getAddress());
			objectMap.put("totalhouse", cellextend.getTotalhouse());
			objectMap.put("price", cellextend.getPrice());
			objectMap.put("totalmoney", cellextend.getTotalmoney());
			objectMap.put("extendercode", cellextend.getExtendercode());
			if(StringUtils.isNotEmpty(cellextend.getExtendercode())){
				Employee extender = employeeDao.findByEmployeecodeStr(cellextend.getExtendercode());
				if(extender != null){
					objectMap.put("extendername", extender.getEmployeename());
					objectMap.put("extenderphone", extender.getPhone());
				}
			}
			objectMap.put("extendtime", StringUtils.isEmpty(cellextend.getExtendtime())?"":Tools.getStr(cellextend.getExtendtime()).substring(0, 19));
			objectMap.put("starttime", StringUtils.isEmpty(cellextend.getStarttime())?"":Tools.getStr(cellextend.getStarttime()).substring(0, 19));
			objectMap.put("endtime", StringUtils.isEmpty(cellextend.getEndtime())?"":Tools.getStr(cellextend.getEndtime()).substring(0, 19));
			
			objectMap.put("acceptflag", cellextend.getAcceptflag());
			objectMap.put("acceptflagname", cellextend.getAcceptflagname());
			objectMap.put("acceptercode", cellextend.getAcceptercode());
			if(StringUtils.isNotEmpty(cellextend.getAcceptercode())){
				Employee accepter = employeeDao.findByEmployeecodeStr(cellextend.getAcceptercode());
				if(accepter != null){
					objectMap.put("acceptername", accepter.getEmployeename());
					objectMap.put("accepterphone", accepter.getPhone());
				}
			}
			objectMap.put("acceptertime", StringUtils.isEmpty(cellextend.getAcceptertime())?"":Tools.getStr(cellextend.getAcceptertime()).substring(0, 19));
			objectMap.put("payflag", cellextend.getPayflag());
			objectMap.put("payflagname", cellextend.getPayflagname());
			objectMap.put("paytime", StringUtils.isEmpty(cellextend.getPaytime())?"":Tools.getStr(cellextend.getPaytime()).substring(0, 19));
			objectMap.put("remark", cellextend.getRemark());
			objectMaplist.add(objectMap);
		}
		responseMap.put("status", "1");
		responseMap.put("result", "请求成功");
		responseMap.put("cellextendlist", objectMaplist);
		
		return responseMap;
	}
	
	// 根据小区发布编号查询小区发布信息
	// test url:
	// http://localhost:8080/shenfu/httpForMps/getProductmodelInfo?userid=00000027
	public Map<String, Object> findCellextendInfoByExtendcode(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		//List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		
		//小区发布编号
		String extendcode =  Tools.getStr(jsonObj.getString("extendcode"));
		
		//电话号码不能为空
		if(StringUtils.isEmpty(extendcode)){
			responseMap.put("status", "0");//电话号码不能为空
			responseMap.put("result", "查询失败，请输入小区发布编号");//
			return responseMap;
		}
		
		//查询所有的产品型号信息
		Cellextend cellextend = cellextendDao.findByExtendcode(extendcode);
		if(cellextend == null){
			responseMap.put("status", "0");//电话号码不能为空
			responseMap.put("result", "查询失败，小区编号发布编号不存在");//
			return responseMap;
		}
		
		HashMap<String, Object> objectMap = new HashMap<String, Object>();
		objectMap.put("id", cellextend.getId());
		objectMap.put("extendcode", cellextend.getExtendcode());
		objectMap.put("cellcode", cellextend.getCellcode());
		objectMap.put("cellname", cellextend.getCellname());
		objectMap.put("address", cellextend.getAddress());
		objectMap.put("totalhouse", cellextend.getTotalhouse());
		objectMap.put("price", cellextend.getPrice());
		objectMap.put("totalmoney", cellextend.getTotalmoney());
		objectMap.put("extendercode", cellextend.getExtendercode());
		if(StringUtils.isNotEmpty(cellextend.getExtendercode())){
			Employee extender = employeeDao.findByEmployeecodeStr(cellextend.getExtendercode());
			if(extender != null){
				objectMap.put("extendername", extender.getEmployeename());
				objectMap.put("extenderphone", extender.getPhone());
			}
		}
		objectMap.put("extendtime", StringUtils.isEmpty(cellextend.getExtendtime())?"":Tools.getStr(cellextend.getExtendtime()).substring(0, 19));
		objectMap.put("starttime", StringUtils.isEmpty(cellextend.getStarttime())?"":Tools.getStr(cellextend.getStarttime()).substring(0, 19));
		objectMap.put("endtime", StringUtils.isEmpty(cellextend.getEndtime())?"":Tools.getStr(cellextend.getEndtime()).substring(0, 19));
		
		objectMap.put("acceptflag", cellextend.getAcceptflag());
		objectMap.put("acceptflagname", cellextend.getAcceptflagname());
		objectMap.put("acceptercode", cellextend.getAcceptercode());
		if(StringUtils.isNotEmpty(cellextend.getAcceptercode())){
			Employee accepter = employeeDao.findByEmployeecodeStr(cellextend.getAcceptercode());
			if(accepter != null){
				objectMap.put("acceptername", accepter.getEmployeename());
				objectMap.put("accepterphone", accepter.getPhone());
			}
		}
		objectMap.put("acceptertime", StringUtils.isEmpty(cellextend.getAcceptertime())?"":Tools.getStr(cellextend.getAcceptertime()).substring(0, 19));
		objectMap.put("payflag", cellextend.getPayflag());
		objectMap.put("payflagname", cellextend.getPayflagname());
		objectMap.put("paytime", StringUtils.isEmpty(cellextend.getPaytime())?"":Tools.getStr(cellextend.getPaytime()).substring(0, 19));
		objectMap.put("stationflag", cellextend.getStationflag());
		objectMap.put("stationflagname", cellextend.getStationflagname());
		objectMap.put("remark", cellextend.getRemark());
		
		responseMap.put("status", "1");
		responseMap.put("result", "请求成功");
		responseMap.put("cellextendInfo", objectMap);
		
		return responseMap;
	}
	
	//保存小区抢购
	// http://localhost:8080/shenfu/httpForMps/commServerForSaleMPS?methodname=salerRegister&parajson={"client_phone":"12334234","client_name":"chenguoqing"}
	public Map<String, Object> saveAcceptCellextend(JSONObject jsonObj) {
		//调用service，使其事务手动控制
		return httpForMpsService.saveAcceptCellextend(jsonObj);
	}
	
	// 查询销售员抢购的扫楼信息
	// test url:
	// http://localhost:8080/shenfu/httpForMps/getProductInfo?userid=00000027
	@ResponseBody
	@RequestMapping(value = "/findCellextendBySalercode")
	public Map<String, Object> findCellextendBySalercode(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		
		//销售员
		String salercode = Tools.getStr(jsonObj.getString("salercode"));
		//支付状态
		String payflag = Tools.getStr(jsonObj.getString("payflag"));
		//请求页码
		String page =  Tools.getStr(jsonObj.getString("page"));
		//每页显示行数
		String rows =  Tools.getStr(jsonObj.getString("rows"));
		
		Cellextend cellextendform = new Cellextend();
		cellextendform.setAcceptercode(salercode);
		cellextendform.setPayflag(payflag);
		cellextendform.setPage(Integer.valueOf(page));
		cellextendform.setRows(Integer.valueOf(rows));
		
		//查询所有的产品型号信息
		List<Cellextend> cellextendlist = cellextendDao.findByList(cellextendform);
		
		for (Cellextend cellextend : cellextendlist) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("id", cellextend.getId());
			objectMap.put("extendcode", cellextend.getExtendcode());
			objectMap.put("cellcode", cellextend.getCellcode());
			objectMap.put("cellname", cellextend.getCellname());
			objectMap.put("address", cellextend.getAddress());
			objectMap.put("totalhouse", cellextend.getTotalhouse());
			objectMap.put("price", cellextend.getPrice());
			objectMap.put("totalmoney", cellextend.getTotalmoney());
			objectMap.put("extendercode", cellextend.getExtendercode());
			if(StringUtils.isNotEmpty(cellextend.getExtendercode())){
				Employee extender = employeeDao.findByEmployeecodeStr(cellextend.getExtendercode());
				if(extender != null){
					objectMap.put("extendername", extender.getEmployeename());
					objectMap.put("extenderphone", extender.getPhone());
				}
			}
			objectMap.put("extendtime", StringUtils.isEmpty(cellextend.getExtendtime())?"":Tools.getStr(cellextend.getExtendtime()).substring(0, 19));
			objectMap.put("starttime", StringUtils.isEmpty(cellextend.getStarttime())?"":Tools.getStr(cellextend.getStarttime()).substring(0, 19));
			objectMap.put("endtime", StringUtils.isEmpty(cellextend.getEndtime())?"":Tools.getStr(cellextend.getEndtime()).substring(0, 19));
			
			objectMap.put("acceptflag", cellextend.getAcceptflag());
			objectMap.put("acceptflagname", cellextend.getAcceptflagname());
			objectMap.put("acceptercode", cellextend.getAcceptercode());
			if(StringUtils.isNotEmpty(cellextend.getAcceptercode())){
				Employee accepter = employeeDao.findByEmployeecodeStr(cellextend.getAcceptercode());
				if(accepter != null){
					objectMap.put("acceptername", accepter.getEmployeename());
					objectMap.put("accepterphone", accepter.getPhone());
				}
			}
			objectMap.put("acceptertime", StringUtils.isEmpty(cellextend.getAcceptertime())?"":Tools.getStr(cellextend.getAcceptertime()).substring(0, 19));
			objectMap.put("payflag", cellextend.getPayflag());
			objectMap.put("payflagname", cellextend.getPayflagname());
			objectMap.put("paytime", StringUtils.isEmpty(cellextend.getPaytime())?"":Tools.getStr(cellextend.getPaytime()).substring(0, 19));
			objectMap.put("stationflag", cellextend.getStationflag());
			objectMap.put("stationflagname", cellextend.getStationflagname());
			objectMap.put("remark", cellextend.getRemark());
			objectMaplist.add(objectMap);
		}
		responseMap.put("status", "1");
		responseMap.put("result", "请求成功");
		responseMap.put("cellextendlist", objectMaplist);
		
		return responseMap;
	}
	
	// 扫楼支付
	public Map<String, Object> saveCellpayinfo(JSONObject jsonObj) {
		return httpForMpsService.saveCellpayInfo(jsonObj);
	}
	
	//保存小区驻点申请
	// http://localhost:8080/shenfu/httpForMps/commServerForSaleMPS?methodname=salerRegister&parajson={"client_phone":"12334234","client_name":"chenguoqing"}
	public Map<String, Object> saveCellstation(JSONObject jsonObj) {
		//调用service，使其事务手动控制
		return httpForMpsService.saveCellstation(jsonObj);
	}
	
	//查询某楼盘发布的驻点信息
	@ResponseBody
	@RequestMapping(value = "/findCellstationByExtendcode")
	public Map<String, Object> findCellstationByExtendcode(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		
		//发布单号
		String extendcode = Tools.getKeyValueFromJsonObj("extendcode", jsonObj);
		//请求页码
		String page =  Tools.getStr(jsonObj.getString("page"));
		//每页显示行数
		String rows =  Tools.getStr(jsonObj.getString("rows"));
		
		Cellstation cellstationform = new Cellstation();
		cellstationform.setExtendcode(extendcode);
		cellstationform.setPage(Integer.valueOf(page));
		cellstationform.setRows(Integer.valueOf(rows));
		
		//查询所有的产品型号信息
		List<Cellstation> cellstationlist = cellstationDao.findByList(cellstationform);
		
		for (Cellstation cellstation : cellstationlist) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("id", cellstation.getId());
			objectMap.put("stationcode", cellstation.getStationcode());
			objectMap.put("extendcode", cellstation.getExtendcode());
			objectMap.put("cellcode", cellstation.getCellcode());
			objectMap.put("cellname", cellstation.getCellname());
			objectMap.put("address", cellstation.getAddress());
			objectMap.put("totalhouse", cellstation.getTotalhouse());
			objectMap.put("price", cellstation.getPrice());
			objectMap.put("totalmoney", cellstation.getTotalmoney());
			objectMap.put("extendercode", cellstation.getExtendercode());
			if(StringUtils.isNotEmpty(cellstation.getExtendercode())){
				Employee extender = employeeDao.findByEmployeecodeStr(cellstation.getExtendercode());
				if(extender != null){
					objectMap.put("extendername", extender.getEmployeename());
					objectMap.put("extenderphone", extender.getPhone());
				}
			}
			objectMap.put("extendtime", StringUtils.isEmpty(cellstation.getExtendtime())?"":Tools.getStr(cellstation.getExtendtime()).substring(0, 19));
			objectMap.put("acceptercode", cellstation.getAcceptercode());
			if(StringUtils.isNotEmpty(cellstation.getAcceptercode())){
				Employee accepter = employeeDao.findByEmployeecodeStr(cellstation.getAcceptercode());
				if(accepter != null){
					objectMap.put("acceptername", accepter.getEmployeename());
					objectMap.put("accepterphone", accepter.getPhone());
				}
			}
			objectMap.put("acceptertime", StringUtils.isEmpty(cellstation.getAcceptertime())?"":Tools.getStr(cellstation.getAcceptertime()).substring(0, 19));
			objectMap.put("paytime", StringUtils.isEmpty(cellstation.getPaytime())?"":Tools.getStr(cellstation.getPaytime()).substring(0, 19));
			objectMap.put("starttime", StringUtils.isEmpty(cellstation.getStarttime())?"":Tools.getStr(cellstation.getStarttime()).substring(0, 19));
			objectMap.put("endtime", StringUtils.isEmpty(cellstation.getEndtime())?"":Tools.getStr(cellstation.getEndtime()).substring(0, 19));
			objectMap.put("talkernumber", cellstation.getTalkernumber());
			objectMap.put("visitornumber", cellstation.getVisitornumber());
			objectMap.put("addtime", StringUtils.isEmpty(cellstation.getAddtime())?"":Tools.getStr(cellstation.getAddtime()).substring(0, 19));
			objectMap.put("remark", cellstation.getRemark());
			objectMaplist.add(objectMap);
		}
		responseMap.put("status", "1");
		responseMap.put("result", "请求成功");
		responseMap.put("cellstationlist", objectMaplist);
		
		return responseMap;
	}
	
	//查询某楼盘发布的驻点信息
	@ResponseBody
	@RequestMapping(value = "/findEmployeeListByStationcode")
	public Map<String, Object> findEmployeeListByStationcode(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		
		//驻点单号
		String stationcode = Tools.getKeyValueFromJsonObj("stationcode", jsonObj);
		//请求页码
		String page = Tools.getKeyValueFromJsonObj("page", jsonObj);
		//每页显示行数
		String rows = Tools.getKeyValueFromJsonObj("rows", jsonObj);
		
		Cellstationemployee cellstationemployeeform = new Cellstationemployee();
		cellstationemployeeform.setStationcode(stationcode);
		cellstationemployeeform.setPage(Integer.valueOf(page));
		cellstationemployeeform.setRows(Integer.valueOf(rows));
		
		//查询所有的产品型号信息
		List<Cellstationemployee> cellstationemployeelist = cellstationemployeeDao.findByList(cellstationemployeeform);
		
		for (Cellstationemployee cellstationemployee : cellstationemployeelist) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("id", cellstationemployee.getId());
			objectMap.put("stationcode", cellstationemployee.getStationcode());
			objectMap.put("extendcode", cellstationemployee.getExtendcode());
			objectMap.put("cellcode", cellstationemployee.getCellcode());
			objectMap.put("cellname", cellstationemployee.getCellname());
			objectMap.put("address", cellstationemployee.getAddress());
			objectMap.put("employeetype", cellstationemployee.getEmployeetype());
			objectMap.put("employeetypename", cellstationemployee.getEmployeetypename());
			objectMap.put("employeecode", cellstationemployee.getEmployeecode());
			objectMap.put("employeename", cellstationemployee.getEmployeename());
			objectMap.put("phone", cellstationemployee.getPhone());
			objectMap.put("remark", cellstationemployee.getRemark());
			objectMaplist.add(objectMap);
		}
		responseMap.put("status", "1");
		responseMap.put("result", "请求成功");
		responseMap.put("cellstationemployeelist", objectMaplist);
		
		return responseMap;
	}
	
	//根据驻点人员信息查询某楼盘驻点信息
	@ResponseBody
	@RequestMapping(value = "/findCellstationListByEmployeecode")
	public Map<String, Object> findCellstationListByEmployeecode(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		
		//驻点人员编号
		String employeecode = Tools.getKeyValueFromJsonObj("employeecode", jsonObj);
		//驻点人类型
		String employeetype = Tools.getKeyValueFromJsonObj("employeetype", jsonObj);
		if(StringUtils.isEmpty(employeetype)){//如果为空，默认查询讲解员的驻点
			employeetype = "talker";
		}
		//请求页码
		String page = Tools.getKeyValueFromJsonObj("page", jsonObj);
		//每页显示行数
		String rows = Tools.getKeyValueFromJsonObj("rows", jsonObj);
		
		Cellstationemployee cellstationemployeeform = new Cellstationemployee();
		cellstationemployeeform.setEmployeecode(employeecode);
		cellstationemployeeform.setEmployeetype(employeetype);
		cellstationemployeeform.setPage(Integer.valueOf(page));
		cellstationemployeeform.setRows(Integer.valueOf(rows));
		
		//查询所有的产品型号信息
		List<Cellstationemployee> cellstationemployeelist = cellstationemployeeDao.findByList(cellstationemployeeform);
		
		for (Cellstationemployee cellstationemployee : cellstationemployeelist) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("id", cellstationemployee.getId());
			objectMap.put("stationcode", cellstationemployee.getStationcode());
			objectMap.put("extendcode", cellstationemployee.getExtendcode());
			objectMap.put("cellcode", cellstationemployee.getCellcode());
			objectMap.put("cellname", cellstationemployee.getCellname());
			objectMap.put("address", cellstationemployee.getAddress());
			objectMap.put("employeetype", cellstationemployee.getEmployeetype());
			objectMap.put("employeetypename", cellstationemployee.getEmployeetypename());
			objectMap.put("employeecode", cellstationemployee.getEmployeecode());
			objectMap.put("employeename", cellstationemployee.getEmployeename());
			objectMap.put("phone", cellstationemployee.getPhone());
			objectMap.put("remark", cellstationemployee.getRemark());
			
			objectMaplist.add(objectMap);
		}
		responseMap.put("status", "1");
		responseMap.put("result", "请求成功");
		responseMap.put("cellstationemployeelist", objectMaplist);
		
		return responseMap;
	}
	
	//根据驻点编号信息查询某楼盘驻点信息
	@ResponseBody
	@RequestMapping(value = "/findCellstationInfoByStationcode")
	public Map<String, Object> findCellstationInfoByStationcode(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		
		//驻点单号
		String stationcode = Tools.getKeyValueFromJsonObj("stationcode", jsonObj);
		if(StringUtils.isEmpty(stationcode)){
			responseMap.put("status", "0");
			responseMap.put("result", "请求失败,请输入驻点单号");
			return responseMap;
		}
		
		//查询驻点单信息
		Cellstation cellstation = cellstationDao.findByStationcode(stationcode);
		
		HashMap<String, Object> objectMap = new HashMap<String, Object>();
		objectMap.put("id", cellstation.getId());
		objectMap.put("stationcode", cellstation.getStationcode());
		objectMap.put("extendcode", cellstation.getExtendcode());
		objectMap.put("cellcode", cellstation.getCellcode());
		objectMap.put("cellname", cellstation.getCellname());
		objectMap.put("address", cellstation.getAddress());
		objectMap.put("totalhouse", cellstation.getTotalhouse());
		objectMap.put("price", cellstation.getPrice());
		objectMap.put("totalmoney", cellstation.getTotalmoney());
		objectMap.put("extendercode", cellstation.getExtendercode());
		if(StringUtils.isNotEmpty(cellstation.getExtendercode())){
			Employee extender = employeeDao.findByEmployeecodeStr(cellstation.getExtendercode());
			if(extender != null){
				objectMap.put("extendername", extender.getEmployeename());
				objectMap.put("extenderphone", extender.getPhone());
			}
		}
		objectMap.put("extendtime", StringUtils.isEmpty(cellstation.getExtendtime())?"":Tools.getStr(cellstation.getExtendtime()).substring(0, 19));
		objectMap.put("acceptercode", cellstation.getAcceptercode());
		if(StringUtils.isNotEmpty(cellstation.getAcceptercode())){
			Employee accepter = employeeDao.findByEmployeecodeStr(cellstation.getAcceptercode());
			if(accepter != null){
				objectMap.put("acceptername", accepter.getEmployeename());
				objectMap.put("accepterphone", accepter.getPhone());
			}
		}
		objectMap.put("acceptertime", StringUtils.isEmpty(cellstation.getAcceptertime())?"":Tools.getStr(cellstation.getAcceptertime()).substring(0, 19));
		objectMap.put("paytime", StringUtils.isEmpty(cellstation.getPaytime())?"":Tools.getStr(cellstation.getPaytime()).substring(0, 19));
		objectMap.put("starttime", StringUtils.isEmpty(cellstation.getStarttime())?"":Tools.getStr(cellstation.getStarttime()).substring(0, 19));
		objectMap.put("endtime", StringUtils.isEmpty(cellstation.getEndtime())?"":Tools.getStr(cellstation.getEndtime()).substring(0, 19));
		objectMap.put("talkernumber", cellstation.getTalkernumber());
		objectMap.put("visitornumber", cellstation.getVisitornumber());
		objectMap.put("addtime", StringUtils.isEmpty(cellstation.getAddtime())?"":Tools.getStr(cellstation.getAddtime()).substring(0, 19));
		objectMap.put("remark", cellstation.getRemark());
		
		responseMap.put("status", "1");
		responseMap.put("result", "请求成功");
		responseMap.put("cellstationInfo", objectMap);
		
		return responseMap;
	}
	
	//根据驻点编号信息查询某楼盘驻点信息
	@ResponseBody
	@RequestMapping(value = "/findCellstationEmployeeListByStationcode")
	public Map<String, Object> findCellstationEmployeeListByStationcode(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		
		//驻点单号
		String stationcode = Tools.getKeyValueFromJsonObj("stationcode", jsonObj);
		//驻点单号
		String employeetype = Tools.getKeyValueFromJsonObj("employeetype", jsonObj);
		if(StringUtils.isEmpty(employeetype)){//默认为
			employeetype = "visitor";
		}
		
		if(StringUtils.isEmpty(stationcode)){
			responseMap.put("status", "0");
			responseMap.put("result", "请求失败,请输入驻点单号");
			return responseMap;
		}
		
		//查询驻点单信息
		Cellstationemployee Cellstationemployeeform = new Cellstationemployee();
		Cellstationemployeeform.setStationcode(stationcode);
		//Cellstationemployeeform.setEmployeetype(employeetype);
		
		List<Cellstationemployee> cellstationemployeeList = cellstationemployeeDao.queryByList(Cellstationemployeeform);
		
		for (Cellstationemployee cellstationemployee : cellstationemployeeList) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("id", cellstationemployee.getId());
			objectMap.put("stationcode", cellstationemployee.getStationcode());
			objectMap.put("extendcode", cellstationemployee.getExtendcode());
			objectMap.put("cellcode", cellstationemployee.getCellcode());
			objectMap.put("cellname", cellstationemployee.getCellname());
			objectMap.put("address", cellstationemployee.getAddress());
			objectMap.put("employeetype", cellstationemployee.getEmployeetype());
			objectMap.put("employeetypename", cellstationemployee.getEmployeetypename());
			objectMap.put("employeecode", cellstationemployee.getEmployeecode());
			objectMap.put("employeename", cellstationemployee.getEmployeename());
			objectMap.put("phone", cellstationemployee.getPhone());
			objectMap.put("remark", cellstationemployee.getRemark());
			objectMaplist.add(objectMap);
		}
		
		responseMap.put("status", "1");
		responseMap.put("result", "请求成功");
		responseMap.put("cellstationemployeeList", objectMaplist);
		
		return responseMap;
	}
	
	//小区驻点现场客户注册
	public Map<String, Object> saveUserRegisterForCellstation(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		
		responseMap.put("status", "0");//提交失败
		responseMap.put("result", "提交失败");//注册成功
		
		//上门类型
		//String visittype = Tools.getStr(jsonObj.getString("client_visittype"));
		
		return httpForMpsService.saveUserRegisterForCellstation(jsonObj);
		
	}
    
	//查询活动消息
	@ResponseBody
	public Map<String, Object> findActivityList(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		//请求页码
		String page = Tools.getKeyValueFromJsonObj("page", jsonObj);
		if(StringUtils.isEmpty(page)){
			page = "1";
		}
		//每页显示行数
		String rows = Tools.getKeyValueFromJsonObj("rows", jsonObj);
		if(StringUtils.isEmpty(rows)){
			rows = "10";
		}
		
		Activity activityform = new Activity();
		activityform.setPage(Integer.valueOf(page));
		activityform.setRows(Integer.valueOf(rows));
		
		//查询所有的产品型号信息
		List<Activity> activitylist = activityDao.findByList(activityform);
		
		for (Activity activity : activitylist) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("id", activity.getId());
			objectMap.put("activitycode", activity.getActivitycode());
			objectMap.put("title", activity.getTitle());
			objectMap.put("content", activity.getContent());
			objectMap.put("addtime", StringUtils.isEmpty(activity.getAddtime())?"":Tools.getStr(activity.getAddtime()).substring(0, 19));
			objectMap.put("status", activity.getStatus());
			objectMap.put("statusname", activity.getStatusname());
			objectMap.put("remark", activity.getRemark());
			
			objectMaplist.add(objectMap);
		}
		responseMap.put("status", "1");
		responseMap.put("result", "请求成功");
		responseMap.put("activitylist", objectMaplist);
		
		return responseMap;
	}
	
	//根据驻点编号信息查询某楼盘驻点信息
	@ResponseBody
	@RequestMapping(value = "/findUserorderListByStationcode")
	public Map<String, Object> findUserorderListByStationcode(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		
		//驻点单号
		String stationcode = Tools.getKeyValueFromJsonObj("stationcode", jsonObj);
		//请求页码
		String page =  Tools.getStr(jsonObj.getString("page"));
		//每页显示行数
		String rows =  Tools.getStr(jsonObj.getString("rows"));
		
		if(StringUtils.isEmpty(stationcode)){
			responseMap.put("status", "0");
			responseMap.put("result", "请求失败,请输入驻点单号");
			return responseMap;
		}
		
		Userorder userorderform = new Userorder();
		userorderform.setStationcode(stationcode);//驻点编号
		userorderform.setPage(Integer.valueOf(page));
		userorderform.setRows(Integer.valueOf(rows));
		
		//查询驻点的订单信息
		List<Userorder> userorderList = userorderDao.findByList(userorderform);
		
		for (Userorder userorder : userorderList) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("id", userorder.getId());
			objectMap.put("stationcode", userorder.getStationcode());
			objectMap.put("usercode", userorder.getUsercode());
			objectMap.put("username", userorder.getUsername());
			objectMap.put("usersex", userorder.getUsersex());
			objectMap.put("phone", userorder.getPhone());
			objectMap.put("address", userorder.getAddress());
			objectMap.put("source", userorder.getSource());
			objectMap.put("sourcename", userorder.getSourcename());
			
			objectMap.put("ordercode", userorder.getOrdercode());
			objectMap.put("status", userorder.getStatusname());
			objectMap.put("orderstatusname", userorder.getStatusname());
			objectMap.put("productfee", userorder.getProductfee());
			objectMap.put("otherfee", userorder.getOtherfee());
			objectMap.put("totalmoney", userorder.getTotalmoney());
			objectMap.put("firstpayment", userorder.getFirstpayment());
			objectMap.put("firstarrivalflag", userorder.getFirstarrivalflag());
			objectMap.put("firstarrivalflagname", userorder.getFirstarrivalflagname());
			objectMap.put("finalpayment", userorder.getFinalpayment());
			objectMap.put("finalarrivalflag", userorder.getFinalarrivalflag());
			objectMap.put("finalarrivalflagname", userorder.getFinalarrivalflagname());
			objectMap.put("remark", userorder.getRemark());
			objectMaplist.add(objectMap);
		}
		
		responseMap.put("status", "1");
		responseMap.put("result", "请求成功");
		responseMap.put("userorderList", objectMaplist);
		
		return responseMap;
	}
	
	// 查询送货单
	// test url:
	// http://localhost:8080/shenfu/httpForMps/getDispatchListInfo?userid=00000027
	public Map<String, Object> findUserdeliveryList(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		//上门员编号
		String deliverercode =  Tools.getStr(jsonObj.getString("deliverercode"));
		//请求状态（0-未到货;1-已到货）
		String status =  Tools.getStr(jsonObj.getString("status"));
		//请求页码
		String page =  Tools.getStr(jsonObj.getString("page"));
		//每页显示行数
		String rows =  Tools.getStr(jsonObj.getString("rows"));
		
		Userdelivery userdeliveryform = new Userdelivery();
		userdeliveryform.setDeliverercode(deliverercode);
		userdeliveryform.setStatus(status);
		userdeliveryform.setPage(Integer.valueOf(page));
		userdeliveryform.setRows(Integer.valueOf(rows));
		
		List<Userdelivery> userdeliverylist = userdeliveryDao.findByList(userdeliveryform);
		
		for (Userdelivery userdelivery : userdeliverylist) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("id", userdelivery.getId());
			objectMap.put("deliverycode", userdelivery.getDeliverycode());
			objectMap.put("deliveryname", userdelivery.getDeliveryname());
			objectMap.put("deliverytime", StringUtils.isEmpty(userdelivery.getDeliverytime())?"":Tools.getStr(userdelivery.getDeliverytime()).substring(0, 19));
			objectMap.put("content", Tools.getStr(userdelivery.getContent()));
			objectMap.put("deliverercode", Tools.getStr(userdelivery.getDeliverycode()));
			objectMap.put("deliverername", Tools.getStr(userdelivery.getDeliverername()));
			objectMap.put("delivererphone", Tools.getStr(userdelivery.getDelivererphone()));
			objectMap.put("status", Tools.getStr(userdelivery.getStatus()));
			objectMap.put("statusname", Tools.getStr(userdelivery.getStatusname()));
			objectMap.put("usercode", userdelivery.getUsercode());
			objectMap.put("ordercode", userdelivery.getOrdercode());
			if(StringUtils.isNotEmpty(userdelivery.getOrdercode())){
				Userorder userorder = userorderDao.findByOrdercode(userdelivery.getOrdercode());
				if(userorder == null){
					userorder = new Userorder();
				}
				objectMap.put("username", Tools.getStr(userorder.getUsername()));
				objectMap.put("usersex", userorder.getUsersex());
				objectMap.put("usersexname", userorder.getUsersexname());
				objectMap.put("phone", Tools.getStr(userorder.getPhone()));
				objectMap.put("address", Tools.getStr(userorder.getAddress()));
				objectMap.put("source", Tools.getStr(userorder.getSource()));
				objectMap.put("productfee", userorder.getProductfee());
				objectMap.put("otherfee", userorder.getOtherfee());
				objectMap.put("totalmoney", userorder.getTotalmoney());
				objectMap.put("firstpayment", userorder.getFirstpayment());
				objectMap.put("firstarrivalflag", userorder.getFirstarrivalflag());
				objectMap.put("firstarrivalflagname", userorder.getFirstarrivalflagname());
				objectMap.put("finalpayment", userorder.getFinalpayment());
				objectMap.put("finalarrivalflag", userorder.getFinalarrivalflag());
				objectMap.put("finalarrivalflagname", userorder.getFinalarrivalflagname());
				objectMap.put("orderstatus", userorder.getStatus());
				objectMap.put("orderstatusname", userorder.getStatusname());
			}else{
				responseMap.put("status", "0");//保存失败
				responseMap.put("result", "订单号不能为空");
				return responseMap;
			}
			
			//查询该送货单对应订单包含的产品
			List<HashMap<String, Object>> userproductMaplist = new ArrayList<HashMap<String, Object>>();
			Userproduct userproductform = new Userproduct();
			userproductform.setOrdercode(userdelivery.getOrdercode());
			List<Userproduct> userproductlist = userproductDao.queryByList(userproductform);
			if(userproductlist != null && userproductlist.size() > 0){
				for (Userproduct userproduct : userproductlist) {
					HashMap<String, Object> userproductMap = new HashMap<String, Object>();
					userproductMap.put("modelname", userproduct.getModelname());
					userproductMap.put("modelcode", userproduct.getModelcode());
					userproductMap.put("productname", userproduct.getProductname());
					userproductMap.put("productcode", userproduct.getProductcode());
					userproductMap.put("productidentfy", userproduct.getProductidentfy());
					userproductMap.put("buytype", userproduct.getBuytype());
					userproductMaplist.add(userproductMap);
				}
			}
			//该工单包含的产品信息
			objectMap.put("productsinfo", userproductMaplist);
			
			//查询该工单对应订单的明细
			List<HashMap<String, Object>> orderinfoMaplist = new ArrayList<HashMap<String, Object>>();
			//订单明细
			List<Userorderinfo> orderinfolist = userorderinfoDao.findByOrdercode(userdelivery.getOrdercode());
			if(orderinfolist != null && orderinfolist.size() >0){
				for (Userorderinfo userorderinfo : orderinfolist) {
					HashMap<String, Object> orderinfoMap = new HashMap<String, Object>();
					orderinfoMap.put("price", userorderinfo.getPrice());
					orderinfoMap.put("buytype", userorderinfo.getBuytype());
					orderinfoMap.put("modelname", userorderinfo.getModelname());
					orderinfoMap.put("modelcode", userorderinfo.getModelcode());
					orderinfoMap.put("productname", userorderinfo.getProductname());
					orderinfoMap.put("productcode", userorderinfo.getProductcode());
					orderinfoMaplist.add(orderinfoMap);
				}
			}
			//该工单包含的订单详细
			objectMap.put("orderinfos", orderinfoMaplist);
			
			objectMaplist.add(objectMap);
			
		}
		responseMap.put("status", "1");//返回成功
		responseMap.put("result", "请求成功");
		responseMap.put("userdeliveryList", objectMaplist);
		
		return responseMap;
	}
	
	//处理送货单为已送货，客户接收
	public Map<String, Object> saveAcceptUserdelivery(JSONObject jsonObj) {
		//调用service，使其事务手动控制
		return httpForMpsService.saveAcceptUserdelivery(jsonObj);
	}
	
	//处理送货单为已送货，客户接收
	public Map<String, Object> saveRefuseUserdelivery(JSONObject jsonObj) {
		//调用service，使其事务手动控制
		return httpForMpsService.saveRefuseUserdelivery(jsonObj);
	}
	
	// 同步所有产品
	// test url:
	// http://localhost:8080/shenfu/httpForMps/getProductInfo?userid=00000027
	@ResponseBody
	@RequestMapping(value = "/getProductInfo")
	public Map<String, Object> getProductInfo(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		Product productform = new Product();
		
		//查询所有的产品型号信息
		List<Product> productlist = productDao.queryByList(productform);
		
		for (Product product : productlist) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("id", product.getId());
			objectMap.put("productcode", product.getProductcode());
			objectMap.put("productname", product.getProductname());
			objectMap.put("typecode", product.getTypecode());
			objectMap.put("typename", product.getTypename());
			objectMap.put("price", product.getPrice());
			objectMap.put("productsource", product.getProductsource());
			objectMap.put("depotamount", product.getDepotamount());
			objectMap.put("imageurl", product.getImageurl());
			objectMap.put("productunit", product.getProductunit());
			objectMap.put("remark", product.getRemark());
			objectMaplist.add(objectMap);
		}
		responseMap.put("status", "1");
		responseMap.put("result", "请求成功");
		responseMap.put("productinfo", objectMaplist);
		
		return responseMap;
	}
	
	// 保存订单添加
	// http://localhost:8080/shenfu/httpForMps/commServerForSaleMPS?methodname=salerRegister&parajson={"client_phone":"12334234","client_name":"chenguoqing"}
	public Map<String, Object> saveUserorder(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		
		responseMap.put("status", "0");//提交失败
		responseMap.put("result", "提交失败");//注册成功
		
		//讲解类型
		String visittype = Tools.getStr(jsonObj.getString("visittype"));
		
		if("0".equals(visittype)){ //上门类型-0.公司派人讲解测量
			return httpForMpsService.saveUserorderForVisittype0(jsonObj);
		}else if("1".equals(visittype)){//上门类型-1.亲自讲解测量
			return httpForMpsService.saveUserorderForVisittype1(jsonObj);
		}
		return responseMap;
	}
	
	// 渠道方查询客户以及订单信息
	// http://localhost:8080/shenfu/httpForMps/commServerForSaleMPS?methodname=salerRegister&parajson={"client_phone":"12334234","client_name":"chenguoqing"}
	public Map<String, Object> findUserAndUserorderListBySalercode(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		
		responseMap.put("status", "0");//提交失败
		responseMap.put("result", "提交失败");//注册成功
		
		//查询类型类型(0-查询未生成订单的客户；1-查询已经生产订单的客户订单信息)
		String querytype = Tools.getStr(jsonObj.getString("querytype"));
		
		if("0".equals(querytype)){ //查询未生成订单的客户信息
			return httpForMpsService.findUserListBySalercode(jsonObj);
		}else if("1".equals(querytype)){//查询订单信息
			return httpForMpsService.findUserorderListBySalercode(jsonObj);
		}
		return responseMap;
	}
	
	// 查询订单详情-通过订单号
	public Map<String, Object> findUserorderInfoByOrdercode(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		//List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		
		//小区发布编号
		String ordercode =  Tools.getStr(jsonObj.getString("ordercode"));
		
		//电话号码不能为空
		if(StringUtils.isEmpty(ordercode)){
			responseMap.put("status", "0");//电话号码不能为空
			responseMap.put("result", "查询失败，请输入订单编号");//
			return responseMap;
		}
		
		//查询所有的产品型号信息
		Userorder userorder = userorderDao.findByOrdercode(ordercode);
		if(userorder == null){
			responseMap.put("status", "0");//电话号码不能为空
			responseMap.put("result", "查询失败，该订单不存在");//
			return responseMap;
		}
		
		HashMap<String, Object> objectMap = new HashMap<String, Object>();
		objectMap.put("id", userorder.getId());
		objectMap.put("ordercode", userorder.getOrdercode());
		objectMap.put("usercode", userorder.getUsercode());
		objectMap.put("username", Tools.getStr(userorder.getUsername()));
		objectMap.put("phone", Tools.getStr(userorder.getPhone()));
		objectMap.put("address", Tools.getStr(userorder.getAddress()));
		objectMap.put("status", userorder.getStatus());
		objectMap.put("statusname", userorder.getStatusname());
		objectMap.put("visittype", userorder.getVisittype());
		objectMap.put("visittypename", userorder.getVisittypename());
		objectMap.put("productfee", userorder.getProductfee());
		objectMap.put("otherfee", userorder.getOtherfee());
		objectMap.put("gaintotalmoney", userorder.getGaintotalmoney()==null?0:userorder.getGaintotalmoney());
		objectMap.put("totalmoney", userorder.getTotalmoney()==null?0: userorder.getTotalmoney());
		objectMap.put("firstpayment", userorder.getFirstpayment());
		objectMap.put("firstarrivalflag", userorder.getFirstarrivalflag());
		objectMap.put("firstarrivalflagname", userorder.getFirstarrivalflagname());
		objectMap.put("finalpayment", userorder.getFinalpayment());
		objectMap.put("finalarrivalflag", userorder.getFinalarrivalflag());
		objectMap.put("finalarrivalflagname", userorder.getFinalarrivalflagname());
		objectMap.put("productcolorcode", userorder.getProductcolor());
		objectMap.put("productcolorname", userorder.getProductcolorname());
		objectMap.put("lockcoreflag", userorder.getLockcoreflag());
		objectMap.put("lockcoreflagname", userorder.getLockcoreflagname());
		
		//此订单获取的提成
		Salegaininfo Salegaininfoform = new Salegaininfo();
		Salegaininfoform.setOrdercode(userorder.getOrdercode());
		Long gaintotalmoney = salegaininfoDao.findGaintotalmoney(Salegaininfoform);
		if(gaintotalmoney == null ){
			gaintotalmoney = Long.valueOf(0);
		}
		objectMap.put("gainmoney", gaintotalmoney);
		
		//查询订单详情
		List<Userorderinfo> userorderinfoList = userorderinfoDao.findByOrdercode(userorder.getOrdercode());
		Userorderinfo userorderinfo = null;
		if(userorderinfoList != null && userorderinfoList.size() > 0){
			userorderinfo = userorderinfoList.get(0);
		} else {
			userorderinfo = new Userorderinfo();
		}
		objectMap.put("modelcode", userorderinfo.getModelcode());
		objectMap.put("modelname", userorderinfo.getModelname());
		
		responseMap.put("status", "1");
		responseMap.put("result", "请求成功");
		responseMap.put("userorderInfo", objectMap);
		
		return responseMap;
	}
	
	// 查询讲解任务单，通过讲解人编号
	@ResponseBody
	@RequestMapping(value = "/findUsertaskListByTalkercode")
	public Map<String, Object> findUsertaskListByTalkercode(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		
		//上门员编号
		String talkercode =  Tools.getStr(jsonObj.getString("talkercode"));
		//请求状态（0-未处理;1-已处理）
		String status =  Tools.getStr(jsonObj.getString("status"));
		//请求页码
		String page =  Tools.getStr(jsonObj.getString("page"));
		//每页显示行数
		String rows =  Tools.getStr(jsonObj.getString("rows"));
		
		Usertask taskform = new Usertask();
		taskform.setTalkercode(talkercode);
		taskform.setQuerystateflag(status);
		taskform.setPage(Integer.valueOf(page));
		taskform.setRows(Integer.valueOf(rows));
		if("0".equals(status)){//查询未处理任务单
			taskform.setSort("addtime");
		}else if("1".equals(status)){//查询已处理任务单
			taskform.setSort("dealdate");
		}
		
		List<Usertask> tasklist = taskDao.findByList(taskform);
		
		for (Usertask task : tasklist) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("id", task.getId());
			objectMap.put("taskcode", task.getTaskcode());
			objectMap.put("usercode", task.getUsercode());
			objectMap.put("username", task.getUsername());
			objectMap.put("usersex", task.getUsersex());
			objectMap.put("phone", task.getPhone());
			objectMap.put("address", task.getAddress());
			objectMap.put("source", task.getSource());
			objectMap.put("salercode", task.getSalercode());
			if(StringUtils.isNotEmpty(task.getSalercode())){
				Employee saler = employeeDao.findByEmployeecodeStr(task.getSalercode());
				if(saler == null){
					saler = new Employee();
				}
				objectMap.put("salername", saler.getEmployeename());
				objectMap.put("salerphone", saler.getPhone());
			}else{
				Employee saler = new Employee();
				objectMap.put("salername", saler.getEmployeename());
				objectMap.put("salerphone", saler.getPhone());
			}
			objectMap.put("visitorcode", task.getVisitorcode());
			if(StringUtils.isNotEmpty(task.getVisitorcode())){
				Employee visitor = employeeDao.findByEmployeecodeStr(task.getVisitorcode());
				if(visitor == null){
					visitor = new Employee();
				}
				objectMap.put("visitorname", visitor.getEmployeename());
				objectMap.put("visitorphone", visitor.getPhone());
			}else{
				Employee visitor = new Employee();
				objectMap.put("visitorname", visitor.getEmployeename());
				objectMap.put("visitorphone", visitor.getPhone());
			}
			objectMap.put("intention", task.getIntention());
			objectMap.put("status", task.getStatus());
			objectMap.put("statusname", task.getStatusname());
			objectMap.put("addtime", StringUtils.isEmpty(task.getAddtime())?"":Tools.getStr(task.getAddtime()).substring(0, 19));
			objectMap.put("dealdate", StringUtils.isEmpty(task.getDealdate())?"":Tools.getStr(task.getDealdate()).substring(0, 19));
			objectMap.put("visittime", StringUtils.isEmpty(task.getVisittime())?"":Tools.getStr(task.getVisittime()).substring(0, 19));
			//查询订单状态
			Userorder userorder = userorderDao.findByOrdercode(task.getOrdercode());
			if(userorder != null){
				objectMap.put("ordercode", userorder.getOrdercode());
				objectMap.put("orderstatus", userorder.getStatus());
				objectMap.put("orderstatusname", userorder.getStatusname());
				objectMap.put("productfee", userorder.getProductfee()==null?0:userorder.getProductfee());
				objectMap.put("otherfee", userorder.getOtherfee()==null?0:userorder.getOtherfee());
				objectMap.put("gaintotalmoney", userorder.getGaintotalmoney()==null?0:userorder.getGaintotalmoney());
				objectMap.put("totalmoney", userorder.getTotalmoney()==null?0: userorder.getTotalmoney());
				objectMap.put("firstpayment", userorder.getFirstpayment());
				objectMap.put("firstarrivalflag", userorder.getFirstarrivalflag());
				objectMap.put("firstarrivalflagname", userorder.getFirstarrivalflagname());
				objectMap.put("finalpayment", userorder.getFinalpayment());
				objectMap.put("finalarrivalflag", userorder.getFinalarrivalflag());
				objectMap.put("finalarrivalflagname", userorder.getFinalarrivalflagname());
				objectMap.put("productcolorcode", userorder.getProductcolor());
				objectMap.put("productcolorname", userorder.getProductcolorname());
				objectMap.put("lockcoreflag", userorder.getLockcoreflag());
				objectMap.put("lockcoreflagname", userorder.getLockcoreflagname());
			}
			objectMaplist.add(objectMap);
		}
		
		responseMap.put("status", "1");//返回成功
		responseMap.put("result", "请求成功");
		responseMap.put("usertaskList", objectMaplist);
		
		return responseMap;
	}
	
	// 保存上门数据
	// http://localhost:8080/shenfu/httpForMps/commServerForSaleMPS?methodname=salerRegister&parajson={"client_phone":"12334234","client_name":"chenguoqing"}
	public Map<String, Object> saveUserTaskByTalkercode(JSONObject jsonObj) {
		//调用service，使其事务手动控制
		return httpForMpsService.saveUserTaskByTalkercode(jsonObj);
	}
	
	//通过电话号码查询员工信息
	@ResponseBody
	@RequestMapping(value = "/findEmployeeinfoByPhone")
	public Map<String, Object> findEmployeeinfoByPhone(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		
		//驻点单号
		String phone = Tools.getKeyValueFromJsonObj("phone", jsonObj);
		if(StringUtils.isEmpty(phone)){
			responseMap.put("status", "0");
			responseMap.put("result", "请求失败,请输入电话号码");
			return responseMap;
		}
		
		//员工信息
		Employee employee = employeeDao.findByPhoneStr(phone);
		if(employee == null){
			responseMap.put("status", "0");
			responseMap.put("result", "该电话号码不存在");
			return responseMap;
		}
		
		HashMap<String, Object> objectMap = new HashMap<String, Object>();
		objectMap.put("id", employee.getId());
		objectMap.put("employeename", employee.getEmployeename());
		objectMap.put("employeesex", employee.getEmployeesex());
		objectMap.put("employeesexname", employee.getEmployeesexname());
		objectMap.put("birthday", StringUtils.isEmpty(employee.getBirthday())?"":Tools.getStr(employee.getBirthday()).substring(0, 10));
		objectMap.put("identification", Tools.getStr(employee.getIdentification()));
		objectMap.put("email", employee.getEmail());
		objectMap.put("address", Tools.getStr(employee.getAddress()));
		objectMap.put("phone", employee.getPhone());
		objectMap.put("level", employee.getLevel());
		objectMap.put("status", employee.getStatus());
		objectMap.put("statusname", employee.getStatusname());
		objectMap.put("addtime", StringUtils.isEmpty(employee.getAddtime())?"":Tools.getStr(employee.getAddtime()).substring(0, 19));
		objectMap.put("department", employee.getDepartment());
		objectMap.put("departmentname", employee.getDepartmentname());
		objectMap.put("salescore", employee.getSalescore());
		objectMap.put("employeetype", employee.getEmployeetype());
		objectMap.put("employeetypename", employee.getEmployeetypename());
		objectMap.put("bankcardname", employee.getBankcardname());
		objectMap.put("bankcardno", employee.getBankcardno());
		objectMap.put("employeecode", employee.getEmployeecode());
		objectMap.put("parentemployeecode", employee.getParentemployeecode());
		if(StringUtils.isNotEmpty(employee.getParentemployeecode())){
			Employee parentSaler = employeeDao.findByEmployeecodeStr(employee.getParentemployeecode());
			if(parentSaler != null){
				objectMap.put("parentworkname", parentSaler.getEmployeename());
			}
		}
		objectMap.put("rechargevipcode", Tools.getStr(employee.getRechargevipcode()));
		objectMap.put("extendcode", Tools.getStr(employee.getExtendcode()));
		
		responseMap.put("status", "1");
		responseMap.put("result", "请求成功");
		responseMap.put("employeeInfo", objectMap);
		
		return responseMap;
	}
	
	// 销售员修改订单价格
	// http://localhost:8080/shenfu/httpForMps/commServerForSaleMPS?methodname=salerRegister&parajson={"client_phone":"12334234","client_name":"chenguoqing"}
	public Map<String, Object> updateUserorderPrice(JSONObject jsonObj) {
		//调用service，使其事务手动控制
		return httpForMpsService.updateUserorderPrice(jsonObj);
	}
	
	//查询活动消息
	@ResponseBody
	public Map<String, Object> findSalegainlogList(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		//请求页码
		String page = Tools.getKeyValueFromJsonObj("page", jsonObj);
		if(StringUtils.isEmpty(page)){
			page = "1";
		}
		//每页显示行数
		String rows = Tools.getKeyValueFromJsonObj("rows", jsonObj);
		if(StringUtils.isEmpty(rows)){
			rows = "10";
		}
		
		String app_loginer = Tools.getKeyValueFromJsonObj("app_loginer", jsonObj);
		
		Salegainlog salegainlogform = new Salegainlog();
		salegainlogform.setPage(Integer.valueOf(page));
		salegainlogform.setRows(Integer.valueOf(rows));
		salegainlogform.setGainercode(app_loginer);
		
		//查询所有的产品型号信息
		List<Salegainlog> salegainloglist = salegainlogDao.findByList(salegainlogform);
		
		for (Salegainlog salegainlog : salegainloglist) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("id", salegainlog.getId());
			objectMap.put("gainlogcode", salegainlog.getGainlogcode());
			//objectMap.put("gainercode", salegainlog.getGainercode());
			//提成获得者信息
			Employee gainer = employeeDao.findByEmployeecodeStr(salegainlog.getGainercode());
			if(gainer == null){
				gainer = new Employee();
			}
			objectMap.put("gainercode", salegainlog.getGainercode());
			objectMap.put("gainername", gainer.getEmployeename());
			objectMap.put("gainerphone", gainer.getPhone());
			objectMap.put("gainmoney", salegainlog.getGainmoney());
			objectMap.put("gaintime", StringUtils.isEmpty(salegainlog.getGaintime())?"":Tools.getStr(salegainlog.getGaintime()).substring(0, 19));
			Employee operator = employeeDao.findByEmployeecodeStr(salegainlog.getOperatorcode());
			if(operator == null){
				operator = new Employee();
			}
			objectMap.put("operatorcode", salegainlog.getOperatorcode());
			objectMap.put("operatorname", Tools.getStr(operator.getEmployeename()));
			objectMap.put("operatorphone", Tools.getStr(operator.getPhone()));
			objectMap.put("gaintype", salegainlog.getGaintype());
			objectMap.put("status", salegainlog.getStatus());
			objectMap.put("statusname", Tools.getStr(salegainlog.getStatusname()));
			objectMap.put("gaincontent", Tools.getStr(salegainlog.getGaincontent()));
			objectMap.put("remark", Tools.getStr(salegainlog.getRemark()));
			
			objectMaplist.add(objectMap);
		}
		responseMap.put("status", "1");
		responseMap.put("result", "请求成功");
		responseMap.put("salegainloglist", objectMaplist);
		
		return responseMap;
	}
	
	//通过提成发放编号来查询它所包含的提成明细记录
	@ResponseBody
	public Map<String, Object> findSalegaininfoListByGainlogcode (JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		
		//提成发送明细
		String gainlogcode  = Tools.getKeyValueFromJsonObj("gainlogcode", jsonObj);
		//查询所有的产品型号信息
		Salegaininfo salegaininfoform = new Salegaininfo();
		salegaininfoform.setGainlogcode(gainlogcode);
		
		//通过提成发放编号来查询对应的提成明细信息
		List<Salegaininfo> salegaininfolist  = salegaininfoDao.queryByList(salegaininfoform);
		for (Salegaininfo salegaininfo : salegaininfolist) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("id", salegaininfo.getId());
			//上门员信息
			Employee gainer = employeeDao.findByEmployeecodeStr(salegaininfo.getGainercode());
			if(gainer == null){
				gainer = new Employee();
			}
			objectMap.put("gainercode", salegaininfo.getGainercode());
			objectMap.put("gainername", gainer.getEmployeename());
			objectMap.put("gainerphone", gainer.getPhone());
			
			objectMap.put("usercode", salegaininfo.getUsercode());
			objectMap.put("username", salegaininfo.getUsername());
			objectMap.put("phone", salegaininfo.getPhone());
			objectMap.put("address", salegaininfo.getAddress());
			objectMap.put("source", salegaininfo.getSource());
			objectMap.put("sourcename", salegaininfo.getSourcename());
			objectMap.put("visittype", salegaininfo.getVisittype());
			objectMap.put("visittypename", salegaininfo.getVisittypename());
			objectMap.put("taskcode", salegaininfo.getTaskcode());
			//销售员信息
			if(StringUtils.isNotEmpty(salegaininfo.getSalercode())){
				Employee saler = employeeDao.findByEmployeecodeStr(salegaininfo.getSalercode());
				if(saler != null){
					objectMap.put("salercode", salegaininfo.getSalercode());
					objectMap.put("salername", saler.getEmployeename());
					objectMap.put("salerphone", saler.getPhone());
				}
			}
			//上门员信息
			Employee visitor = employeeDao.findByEmployeecodeStr(salegaininfo.getVisitorcode());
			if(visitor == null){
				visitor = new Employee();
			}
			objectMap.put("visitorcode", salegaininfo.getVisitorcode());
			objectMap.put("visitorname", visitor.getEmployeename());
			objectMap.put("visitorphone", visitor.getPhone());
			
			objectMap.put("ordercode", salegaininfo.getOrdercode());
			objectMap.put("totalmoney", salegaininfo.getTotalmoney());
			objectMap.put("gaincode", salegaininfo.getGaincode());
			objectMap.put("gainname", salegaininfo.getGainname());
			objectMap.put("gainrate", salegaininfo.getGainrate());
			objectMap.put("gainmoney", salegaininfo.getGainmoney());
			objectMap.put("addtime", StringUtils.isEmpty(salegaininfo.getAddtime())?"":Tools.getStr(salegaininfo.getAddtime()).substring(0, 19));
			objectMap.put("status", salegaininfo.getStatus());
			objectMap.put("statusname", salegaininfo.getStatusname());
			objectMap.put("gaintime", StringUtils.isEmpty(salegaininfo.getGaintime())?"":Tools.getStr(salegaininfo.getGaintime()).substring(0, 19));
			objectMap.put("remark", salegaininfo.getRemark());
			objectMaplist.add(objectMap);
		}
		responseMap.put("status", "1");
		responseMap.put("result", "请求成功");
		responseMap.put("salegaininfolist", objectMaplist);
		
		return responseMap;
	}
	
	//根据订单编码查询门锁图片
	@ResponseBody
	@RequestMapping(value = "/findUserdoorListByOrdercode")
	public Map<String, Object> findUserdoorListByOrdercode(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		
		//驻点单号
		String ordercode = Tools.getKeyValueFromJsonObj("ordercode", jsonObj);
		
		Userdoor userdoorform = new Userdoor();
		userdoorform.setOrdercode(ordercode);
		
		//查询所有的产品型号信息
		List<Userdoor> userdoorlist = userdoorDao.queryByList(userdoorform);
		
		for (Userdoor userdoor : userdoorlist) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			//潜在订户信息
			objectMap.put("id", userdoor.getId());
			objectMap.put("usercode", userdoor.getUsercode());
			objectMap.put("ordercode", userdoor.getOrdercode());
			objectMap.put("filename", userdoor.getFilename());
			objectMap.put("preservefilename", userdoor.getPreservefilename());
			objectMap.put("preserveurl", userdoor.getPreserveurl());
			objectMap.put("addtime", StringUtils.isEmpty(userdoor.getAddtime())?"":Tools.getStr(userdoor.getAddtime()).substring(0, 19));
			objectMap.put("remark", userdoor.getRemark());
			objectMaplist.add(objectMap);
		}
		responseMap.put("status", "1");
		responseMap.put("result", "请求成功");
		responseMap.put("userdoorlist", objectMaplist);
		
		return responseMap;
	}
	
	//根据订单编码查询门锁数据
	@ResponseBody
	@RequestMapping(value = "/findUserdoordataByOrdercode")
	public Map<String, Object> findUserdoordataByOrdercode(JSONObject jsonObj) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		
		//驻点单号
		String ordercode = Tools.getKeyValueFromJsonObj("ordercode", jsonObj);
		Userdoordata userdoordataform = new Userdoordata();
		userdoordataform.setOrdercode(ordercode);
		//查询所有的产品型号信息
		Userdoordata userdoordata = userdoordataDao.findByOrdercode(ordercode);
		if(userdoordata == null){
			userdoordata = new Userdoordata();
		}
		
		HashMap<String, Object> objectMap = new HashMap<String, Object>();
		//潜在订户信息
		objectMap.put("usercode", userdoordata.getUsercode());
		objectMap.put("ordercode", userdoordata.getOrdercode());
		objectMap.put("locklength", Tools.getStr(userdoordata.getLocklength()));
		objectMap.put("lockwidth", Tools.getStr(userdoordata.getLockwidth()));
		objectMap.put("remark", Tools.getStr(userdoordata.getRemark()));
		
		responseMap.put("status", "1");
		responseMap.put("result", "请求成功");
		responseMap.put("userdoordata", objectMap);
		
		return responseMap;
	}
	
	
	/**
	 * 门锁图片下载或者查看
	 */
	@RequestMapping(value = "/findUserdoor")
	public String findUserdoor(Userdoor form,HttpServletResponse response){
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求成功");
		
		//门锁图片
		Userdoor userdoor =  userdoorDao.findById(form.getId());
		if(userdoor == null){
			return null;
		}
		
        try {
			File excelTemplate = new File(userdoor.getPreserveurl());
			response.reset();
			
			//图片文件，直接在页面显示图片
			if (Tools.isImage(excelTemplate)) {  
				response.setHeader("Accept-Ranges", "bytes");  
	            response.setHeader("Pragma", "no-cache");  
	            response.setHeader("Cache-Control", "no-cache");  
	            response.setDateHeader("Expires", 0);  
			}else{//非图片文件，先下载
				response.setContentType("application/octet-stream");
				response.addHeader("Content-Length", "" + excelTemplate.length()); // 文件大小
				response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(userdoor.getFilename(), "UTF-8"));
			}
			
			FileInputStream fis = new FileInputStream(excelTemplate);
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			byte[] buffer = new byte[1024 * 1024];
			while (fis.read(buffer) > 0) {
				toClient.write(buffer);
			}
			fis.close();
			toClient.flush();
			toClient.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
    }
	
	/**
	 * 门锁图片删除
	 */
	public Map<String,Object> deleteUserdoor(JSONObject jsonObj) {
		//调用service，使其事务手动控制
		return httpForMpsService.deleteUserdoor(jsonObj);
	}
	
	/**
	 * 门锁图片添加
	 */
	public Map<String,Object> saveUserdoor(JSONObject jsonObj) {
		//调用service，使其事务手动控制
		return httpForMpsService.saveUserdoor(jsonObj);
	}
	
	/**
	 * 修改门锁数据
	 */
	public Map<String,Object> updateUserdoordata(JSONObject jsonObj) {
		//调用service，使其事务手动控制
		return httpForMpsService.updateUserdoordata(jsonObj);
	}
	
}
