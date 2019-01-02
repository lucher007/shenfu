package com.sykj.shenfu.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.interfaces.RSAPrivateKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.jdom.JDOMException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.sykj.shenfu.common.AesSecret;
import com.sykj.shenfu.common.ConfigUtil;
import com.sykj.shenfu.common.DateUtils;
import com.sykj.shenfu.common.SystemUtils;
import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.common.loginencryption.RSAUtils;
import com.sykj.shenfu.common.qrcode.ZXingCode;
import com.sykj.shenfu.common.uitl.Log4jLogger;
import com.sykj.shenfu.common.uitl.SystemUtil;
import com.sykj.shenfu.common.weixin.HttpRequest;
import com.sykj.shenfu.common.weixin.WeiXinConfig;
import com.sykj.shenfu.common.weixin.WeiXinPayCommonUtil;
import com.sykj.shenfu.common.weixin.WeixinGetOpenid;
import com.sykj.shenfu.common.weixin.XMLUtil;
import com.sykj.shenfu.dao.IActivityDao;
import com.sykj.shenfu.dao.ICellDao;
import com.sykj.shenfu.dao.ICellextendDao;
import com.sykj.shenfu.dao.ICellstationDao;
import com.sykj.shenfu.dao.ICellstationemployeeDao;
import com.sykj.shenfu.dao.IEmployeeDao;
import com.sykj.shenfu.dao.IHelpinfoDao;
import com.sykj.shenfu.dao.IHttpForMpsDao;
import com.sykj.shenfu.dao.IMenuDao;
import com.sykj.shenfu.dao.IOperatorDao;
import com.sykj.shenfu.dao.IOperatorrolerefDao;
import com.sykj.shenfu.dao.IProductmodelDao;
import com.sykj.shenfu.dao.IRoleDao;
import com.sykj.shenfu.dao.ISaleenergyinfoDao;
import com.sykj.shenfu.dao.ISaleenergyruleDao;
import com.sykj.shenfu.dao.ISalegaininfoDao;
import com.sykj.shenfu.dao.ISalegainlogDao;
import com.sykj.shenfu.dao.IUserDao;
import com.sykj.shenfu.dao.IUserdispatchDao;
import com.sykj.shenfu.dao.IUserdispatchfileDao;
import com.sykj.shenfu.dao.IUserdoorDao;
import com.sykj.shenfu.dao.IUserdoordataDao;
import com.sykj.shenfu.dao.IUserorderDao;
import com.sykj.shenfu.dao.IUsertaskDao;
import com.sykj.shenfu.dao.IWechatpayDao;
import com.sykj.shenfu.po.Activity;
import com.sykj.shenfu.po.Cell;
import com.sykj.shenfu.po.Cellextend;
import com.sykj.shenfu.po.Cellpaylog;
import com.sykj.shenfu.po.Cellstation;
import com.sykj.shenfu.po.Cellstationemployee;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Giftcard;
import com.sykj.shenfu.po.Helpinfo;
import com.sykj.shenfu.po.JsonObjectForPara;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Product;
import com.sykj.shenfu.po.Productmodel;
import com.sykj.shenfu.po.Saleenergyinfo;
import com.sykj.shenfu.po.Saleenergyrule;
import com.sykj.shenfu.po.Salegaininfo;
import com.sykj.shenfu.po.Salegainlog;
import com.sykj.shenfu.po.User;
import com.sykj.shenfu.po.Userdelivery;
import com.sykj.shenfu.po.Userdispatch;
import com.sykj.shenfu.po.Userdispatchfile;
import com.sykj.shenfu.po.Userdoor;
import com.sykj.shenfu.po.Userdoordata;
import com.sykj.shenfu.po.Userorder;
import com.sykj.shenfu.po.Userorderinfo;
import com.sykj.shenfu.po.Userpaylog;
import com.sykj.shenfu.po.Userproduct;
import com.sykj.shenfu.po.Usertask;
import com.sykj.shenfu.po.Wechatpay;
import com.sykj.shenfu.service.IHttpForMpsService;
import com.sykj.shenfu.service.IMobilebusinessService;
import com.sykj.shenfu.service.IOperatorlogService;
import com.sykj.shenfu.service.ISystemparaService;


/**
 * 移动商务平台
 */
@Controller
@Scope("prototype")
@RequestMapping("/mobilebusiness")
@Transactional
public class MobilebusinessController extends BaseController {

	private static final String LOGIN_COOKIE_STARTNAME = "tfc_login_cookie_";// 用户首页cookie前缀名
	private static final Log4jLogger log = Log4jLogger.getLogger(UserController.class);
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private IOperatorDao operatorDao;
	@Autowired
	private IMenuDao menuDao;
	@Autowired
	private IRoleDao roleDao;
	@Autowired
	private IOperatorrolerefDao operatorrolerefDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private IMobilebusinessService mobilebusinessService;
	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private IActivityDao activityDao;
	@Autowired
	private ICellextendDao cellextendDao;
	@Autowired
	private ICellDao cellDao;
	@Autowired
	private IProductmodelDao productmodelDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IUserorderDao userorderDao;
	@Autowired
	private ISalegaininfoDao salegaininfoDao;
	@Autowired
	private IUserdoordataDao userdoordataDao;
	@Autowired
	private IUserdoorDao userdoorDao;
	@Autowired
	private ISystemparaService systemparaService;
	@Autowired
	private IUsertaskDao usertaskDao;
	@Autowired
	private IHttpForMpsDao httpForMpsDao;
	@Autowired
	private ISaleenergyinfoDao saleenergyinfoDao;
	@Autowired
	private ISaleenergyruleDao saleenergyruleDao;
	@Autowired
	private ISalegainlogDao salegainlogDao;
	@Autowired
	private IHelpinfoDao helpinfoDao;
	@Autowired
	private ICellstationDao cellstationDao;
	@Autowired
	private ICellstationemployeeDao cellstationemployeeDao;
	@Autowired
	private IWechatpayDao wechatpayDao;
	@Autowired
	private IHttpForMpsService httpForMpsService;
	@Autowired
	private IUserdispatchDao userdispatchDao;
	@Autowired
	private IUserdispatchfileDao userdispatchfileDao;
	
	/**
	 * 初始化登录验证
	 */
	@RequestMapping(value = "/initLogin")
	public String initLogin(HttpSession httpSession, HttpServletRequest request, Model model) throws UnsupportedEncodingException {
		// 判断是否需要验证码
		String captcha_check = ConfigUtil.getConfigFilePath("captcha_check", "system.properties");
		model.addAttribute("captcha_check", captcha_check);
		
		//如果session为空，请到登录界面
		if(getSession() == null){
			return "mobilebusiness/login";
		}
		
		//如果未登录，请到登录界面
		Employee employee = (Employee)getSession().getAttribute("MobileOperator");
		if(employee != null){
			return "mobilebusiness/employeeinfo/employeeinfo";
		}
		
		return "mobilebusiness/login";
	}
	
	/**
	 * 
		* @Function: loginDeal
		* @Description: 用户登录数据处理
		*
		* @param:参数描述
		* @return：返回结果描述
		* @throws：异常描述
		*
		* @version: v1.0.0
		* @author: lucher
		* @date: 2018-7-3   上午9:54:40
	 */
	@RequestMapping("/login")
	public ModelAndView loginDeal(String phone,String encrypedPwd,String logincode, HttpSession session, HttpServletRequest request)  throws Exception{
		try {
			
//			if(1==1){
//				String ip = SystemUtils.getIpAddr(request);
//				Employee employee1  = employeeDao.findByPhoneStr("18980880647");
//				// 保存操作员到session中
//				session.setAttribute("MobileOperator", employee1);
//				
//				return new ModelAndView("mobilebusiness/activityinfo/activityinfo");
//			}
//			
//			
//			System.out.println(phone);
			
			request.setAttribute("phone", phone);
			request.setAttribute("logincode", logincode);
			
			RSAPrivateKey privateKey = (RSAPrivateKey)getSession().getAttribute("privateKey");  
			String descrypedPwd = RSAUtils.decryptByPrivateKey(encrypedPwd, privateKey); //解密后的密码,password是提交过来的密码  
			
			request.setAttribute("password", descrypedPwd);
			
			System.out.println(descrypedPwd);
			
			//将页面输入的密码加密
			String passwordEncrypt = AesSecret.aesEncrypt(descrypedPwd, AesSecret.key);
			
			
			if (SystemUtil.validparamsNULL(phone) || SystemUtil.validparamsNULL(encrypedPwd) || SystemUtil.validparamsNULL(logincode)) {
				return returnView("电话号码和密码不能为空", "mobilebusiness/login");
			}
			
			// 判断是否需要验证码
			String captcha_check = ConfigUtil.getConfigFilePath("captcha_check", "system.properties");
			if ("1".equals(captcha_check)) {// 系统要求输入验证码
				if (!logincode.equalsIgnoreCase(session.getAttribute("captcha").toString())) {
					return returnView("验证码不正确", "mobilebusiness/login");
				}
			}
			
			Employee employee = employeeDao.findByPhoneStr(phone);
			if (employee == null) {
				return returnView("电话号码不正确", "mobilebusiness/login");
			} else {
				if (!"1".equals(employee.getStatus())) {
					return returnView("无效的员工", "mobilebusiness/login");
				} else if (!employee.getApppassword().equals(descrypedPwd)) {
					return returnView("登录密码不正确", "mobilebusiness/login");
				}
				
				// 保存操作员到session中
				session.setAttribute("MobileOperator", employee);
				
				return new ModelAndView("mobilebusiness/employeeinfo/employeeinfo");
			}
		} catch (Exception e) {
			log.error("mobilebusiness login error:", e);
			throw e;
		}
	}
	
	/**
	 * 重新回到登录界面（SESSION断掉）
	 */
	@RequestMapping(value = "/noPermission")
	public String noPermission(HttpSession httpSession, HttpServletRequest request, Model model) throws UnsupportedEncodingException {
		return "mobilebusiness/noPermission";
	}
	
	/**
	 * 退出系统
	 */
	@RequestMapping(value = "/logout")
	public String logout(HttpSession httpSession, HttpServletRequest request, Model model) throws UnsupportedEncodingException {
		
		//清空操作员
		httpSession.setAttribute("MobileOperator", null);
		
		// 判断是否需要验证码
		String captcha_check = ConfigUtil.getConfigFilePath("captcha_check", "system.properties");
		model.addAttribute("captcha_check", captcha_check);
		return "mobilebusiness/login";
	}
	
	/**
	 * 进入活动初始化页面
	 */
	@RequestMapping(value="/activityinfoInit")
	public String activityinfoInit(Activity form) {
//		Activity activityform = new Activity();
//		activityform.setPage(Integer.valueOf(1));
//		activityform.setRows(Integer.valueOf(10));
//		
		//查询所有的活动信息
//		List<Activity> activitylist = activityDao.findByList(form);
//		getRequest().setAttribute("activitylist", activitylist);
		return "mobilebusiness/activityinfo/activityinfo";
	}
	
	/**
	 * 查询销售初始化界面
	 */
	@RequestMapping(value="/saleinfoInit")
	public String saleinfoInit(Activity form) {
		return "mobilebusiness/saleinfo/saleinfo";
	}
	
	/**
	 * 查询团队初始化界面
	 */
	@RequestMapping(value="/teaminfoInit")
	public String teaminfoInit(Activity form) {
		return "mobilebusiness/teaminfo/teaminfo";
	}
	
	/**
	 * 查询系统参数信息
	 */
	@RequestMapping(value="/employeeinfoInit")
	public String employeeinfoInit(Employee form) {
		//操作员
		Employee employee = (Employee)getSession().getAttribute("MobileOperator");
		//1-查询未领提成总金额
		Salegaininfo salegaininfoform = new Salegaininfo();
		salegaininfoform.setStatus("0");//未领取
		salegaininfoform.setGainercode(employee.getEmployeecode());
		Long gaintotalmoney = salegaininfoDao.findGaintotalmoney(salegaininfoform);
		if(gaintotalmoney == null){
			gaintotalmoney = new Long(0);
		}
		getRequest().setAttribute("gaintotalmoney", gaintotalmoney.toString());//未领取的总提成
		
		//2-行动力查询
		//Saleenergyinfo saleenergyinfoForm = new Saleenergyinfo();
		//saleenergyinfoForm.setGainercode(employee.getEmployeecode());
		//查询当月的行动力分
		//Date date = new Date();
		//当月的第一天
		//String firstDateOfSeason = DateUtils.formatDate(DateUtils.getFirstDateOfMonth(date));
		//当月的最后一天
		//String lastDateOfSeason = DateUtils.formatDate(DateUtils.getLastDateOfMonth(date));
		
		//上月的第一天
		//String firstDateOfSeason_pre_one = Tools.getSpecifiedDayAfterMonth(firstDateOfSeason,"yyyy-MM-dd",-1);
		//将上月第一天转化为时间格式
		//Date firstDateOfSeason_pre_one_Date = DateUtils.parseDate(firstDateOfSeason_pre_one);
		//上月的最后一天
		//String lastDateOfSeason_pre_one = DateUtils.formatDate(DateUtils.getLastDateOfMonth(firstDateOfSeason_pre_one_Date));
		//上上月的第一天
		//String firstDateOfSeason_pre_two = Tools.getSpecifiedDayAfterMonth(firstDateOfSeason,"yyyy-MM-dd",-2);
		//将上上月第一天转化为时间格式
		//Date firstDateOfSeason_pre_two_Date = DateUtils.parseDate(firstDateOfSeason_pre_two);
		//上上月的最后一天
		//String lastDateOfSeason_pre_two = DateUtils.formatDate(DateUtils.getLastDateOfMonth(firstDateOfSeason_pre_two_Date));
		
		//当季的行动力分
		//saleenergyinfoForm.setBegintime(firstDateOfSeason);
		//saleenergyinfoForm.setEndtime(lastDateOfSeason);
		//Long saleenergy_current =  saleenergyinfoDao.findEnergytotalscore(saleenergyinfoForm);
		//getRequest().setAttribute("saleenergy_current", saleenergy_current==null?0:saleenergy_current);
		
		//上季行动力分
		//saleenergyinfoForm.setBegintime(firstDateOfSeason_pre_one);
		//saleenergyinfoForm.setEndtime(lastDateOfSeason_pre_one);
		//Long saleenergy_pre_one =  saleenergyinfoDao.findEnergytotalscore(saleenergyinfoForm);
		//getRequest().setAttribute("saleenergy_pre_one", saleenergy_pre_one==null?0:saleenergy_pre_one);
		
		//上上季行动力分
		//saleenergyinfoForm.setBegintime(firstDateOfSeason_pre_two);
		//saleenergyinfoForm.setEndtime(lastDateOfSeason_pre_two);
		//Long saleenergy_pre_two =  saleenergyinfoDao.findEnergytotalscore(saleenergyinfoForm);
		//getRequest().setAttribute("saleenergy_pre_two", saleenergy_pre_two==null?0:saleenergy_pre_two);
		
		//每月合格行动力分
		//Saleenergyrule saleenertyrule = saleenergyruleDao.findByEnergycode(Saleenergyrule.energycode_saleenergy_standard);
		//if(saleenertyrule == null){
		//	saleenertyrule = new Saleenergyrule();
		//}
		//getRequest().setAttribute("saleenergy_standard", saleenertyrule.getEnergyrate()==null?0:saleenertyrule.getEnergyrate());
		
		//查询行动力总和
		//未兑换提成的行动力分
		//saleenergyinfoForm.setBegintime(null);
		//saleenergyinfoForm.setEndtime(null);
		//saleenergyinfoForm.setStatus("0");
		//Long saleenergy_unexchange =  saleenergyinfoDao.findEnergytotalscore(saleenergyinfoForm);
		//getRequest().setAttribute("saleenergy_unexchange", saleenergy_unexchange==null?0:saleenergy_unexchange);
		
		//行动力兑换提成比例
		//Saleenergyrule energycode_energy_gain = saleenergyruleDao.findByEnergycode(Saleenergyrule.energycode_energy_gain);
		//未兑换提成的行动力对应的提成金额
		//getRequest().setAttribute("saleenergy_unexchange_gain", saleenergy_unexchange==null?0:saleenergy_unexchange * energycode_energy_gain.getEnergyrate());
		
		//已兑换提成的行动力
		//saleenergyinfoForm.setStatus("1");
		//Long saleenergy_exchangeed =  saleenergyinfoDao.findEnergytotalscore(saleenergyinfoForm);
		//getRequest().setAttribute("saleenergy_exchangeed", saleenergy_exchangeed==null?0:saleenergy_exchangeed);
		//已兑换提成的行动力对应的提成金额
		//getRequest().setAttribute("saleenergy_exchangeed_gain", saleenergy_exchangeed==null?0:saleenergy_exchangeed * energycode_energy_gain.getEnergyrate());
		
		
		return "mobilebusiness/employeeinfo/employeeinfo";
	}
	
	/**
	 * 查询活动信息
	 * @return
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value="/findActivityListJSON")
	public Map<String,Object> findActivityListJSON(Activity form) {
		//封装返回给页面的json对象
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		
		//查询活动信息
		List<Activity> activitylist = activityDao.findByList(form);
		
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
		
		responseMap.put("lists", objectMaplist);
		
		return responseMap;
	}
	
	
	/**
	 * 查询小区发布信息
	 * @return
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value="/findCellextendListJSON")
	public Map<String,Object> findCellextendListJSON(Cellextend form) {
		//封装返回给页面的json对象
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		
		//查询所有的小区发布信息
		List<Cellextend> cellextendlist = cellextendDao.findByList(form);
		
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
		
		responseMap.put("lists", objectMaplist);
		
		return responseMap;
	}
	
	/**
	 * 查询活动信息查看
	 */
	@RequestMapping(value="/lookActivity")
	public String lookActivityinfo(Activity form) {
		Activity activity = activityDao.findById(form.getId());
		form.setActivity(activity);
		return "mobilebusiness/activityinfo/lookActivity";
	}
	
	/**
	 * 抢购小区扫楼发布信息
	 */
	@RequestMapping(value="/buyCellextendInit")
	public String buyCellextendInit(Cellextend form) {
		Cellextend cellextend = cellextendDao.findById(form.getId());
		Cell cell = cellDao.findByCellcode(cellextend.getCellcode());
		if(cell == null){
			cell = new Cell();
		}
		cellextend.setCell(cell);
		
		form.setCellextend(cellextend);
		return "mobilebusiness/activityinfo/buyCellextend";
	}
	
	/**
	 * 保存小区扫楼抢购
	 */
	@RequestMapping(value = "/saveBuyCellextend")
	public String saveBuyCellextend(Cellextend form) {
		
		//操作员
		Employee employee = (Employee)getSession().getAttribute("MobileOperator");

		Map<String, Object> responseMap = mobilebusinessService.saveBuyCellextend(form, getRequest(), employee);
		
		form.setReturninfo(String.valueOf(responseMap.get("result")));
		
		return buyCellextendInit(form);
	}
	
	/**
	 * 订单录入初始化
	 */
	@RequestMapping(value="/addUserorderInit")
	public String addUserorderInit(Userorder form) {
		//需要加载产品型号
    	Productmodel productmodelForm = new Productmodel();
    	productmodelForm.setEffectivetimeflag("1");
    	List<Productmodel> productmodelList = productmodelDao.queryByList(productmodelForm);
    	
    	getRequest().setAttribute("productmodelList", productmodelList);
		return "mobilebusiness/saleinfo/addUserorder";
	}
	
	/**
	 * 保存订单录入信息
	 */
	@ResponseBody
	@RequestMapping(value = "/saveUserorder")
	public Map<String, Object> saveUserorder(Userorder form) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("status", "0");//提交失败
		responseMap.put("result", "提交失败");//注册成功
		
		if ("".equals(form.getUsername())) {
			responseMap.put("result", "客户姓名不能为空");
			return responseMap;
		} 
		
		if ("".equals(form.getAddress())) {
			responseMap.put("result", "安装地址不能为空");
			return responseMap;
		} 
		
		if ("".equals(Tools.getStr(form.getPhone()))) {
			responseMap.put("result", "电话号码不能为空");
			return responseMap;
		}
		
		//讲解类型
		String visittype = Tools.getStr(form.getVisittype());
		if ("".equals(visittype)) {
			responseMap.put("result", "请选择上门类型");
			return responseMap;
		}
		
		if("0".equals(visittype)){ //上门类型-0.公司派人讲解测量
			return mobilebusinessService.saveUserorderForVisittype0(form, getRequest(), getSession());
		}else if("1".equals(visittype)){//上门类型-1.亲自讲解测量
			
			if ("".equals(Tools.getStr(form.getModelcode()))) {
				responseMap.put("result", "请选择产品型号");
				return responseMap;
			}
			
			if ("".equals(Tools.getStr(form.getProductcolor()))) {
				responseMap.put("result", "请选择产品颜色");
				return responseMap;
			}
			
			return mobilebusinessService.saveUserorderForVisittype1(form,getRequest(), getSession());
		}
		return responseMap;
	}
	
	/**
	 * 订单管理
	 */
	@RequestMapping(value="/manageUserorder")
	public String userorderManage(Userorder form) {
		return "mobilebusiness/saleinfo/manageUserorder";
	}
	
	/**
	 * 查询未处理的订单页面
	 */
	@RequestMapping(value="/findUserList")
	public String findUserList(Activity form) {
		return "mobilebusiness/saleinfo/findUserList";
	}
	
	/**
	 * 查询进行中订单页面
	 */
	@RequestMapping(value="/findUserorderList_ing")
	public String findUserorderList_ing(Activity form) {
		return "mobilebusiness/saleinfo/findUserorderList_ing";
	}
	
	/**
	 * 查询已处理的订单页面
	 */
	@RequestMapping(value="/findUserorderList_ed")
	public String findUserorderList_ed(Activity form) {
		return "mobilebusiness/saleinfo/findUserorderList_ed";
	}
	
	/**
	 * 查询未处理的订单
	 * @return
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value="/findUserListJSON")
	public Map<String,Object> findUserListJSON(User form) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		
		try{
			//销售员编号
			Employee  mobileOperator = (Employee)getSession().getAttribute("MobileOperator");
			String salercode = mobileOperator.getEmployeecode();
			if(StringUtils.isEmpty(salercode)){
				responseMap.put("status", "0");
				responseMap.put("result", "请求失败,请输入销售员编号");
				return responseMap;
			}
			
			//未生成订单的客户
			form.setStatus("0,1"); 
			form.setSalercode(salercode);//销售员
			
			List<User> userList = userDao.findByList(form);
			for (User user : userList) {
				HashMap<String, Object> objectMap = new HashMap<String, Object>();
				objectMap.put("usercode", user.getUsercode());
				objectMap.put("username", Tools.getStr(user.getUsername()));
				objectMap.put("phone", Tools.getStr(user.getPhone()));
				objectMap.put("address", Tools.getStr(user.getAddress()));
				objectMap.put("status", Tools.getStr(user.getStatus()));
				objectMap.put("statusname", Tools.getStr(user.getStatusname()));
				objectMaplist.add(objectMap);
			}
			
			responseMap.put("status", "1");
			responseMap.put("result", "请求成功");
			responseMap.put("userList", objectMaplist);
			
			return responseMap;
		}catch(Exception e){
			e.printStackTrace();
			responseMap.put("status", "0");//保存失败
			responseMap.put("result", "数据异常，操作失败");//
			return responseMap;
		}
	}
	
	/**
	 * 查询进行中或者已经完成的订单
	 * @return
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value="/findUserorderListJSON")
	public Map<String, Object> findUserorderListJSON(Userorder form){
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		
		try{
		
			//销售员编号
			Employee  mobileOperator = (Employee)getSession().getAttribute("MobileOperator");
			String salercode = mobileOperator.getEmployeecode();
			//订单状态(0-未结单；1-已结单)
			String orderstatus = getRequest().getParameter("orderstatus");
			
			if(StringUtils.isEmpty(salercode)){
				responseMap.put("status", "0");
				responseMap.put("result", "请求失败,请输入销售员编号");
				return responseMap;
			}
			
			//查询驻点单信息
			form.setSalercode(salercode);//销售员
			if("0".equals(orderstatus)){//未结单
				form.setStatus("0,1,2,3,4,5");
			}else if("1".equals(orderstatus)){
				form.setStatus("6,7");
			}
			
			List<Userorder> userorderList = userorderDao.findByList(form);
			for (Userorder userorder : userorderList) {
				HashMap<String, Object> objectMap = new HashMap<String, Object>();
				objectMap.put("ordercode", userorder.getOrdercode());
				objectMap.put("usercode", userorder.getUsercode());
				objectMap.put("username", Tools.getStr(userorder.getUsername()));
				objectMap.put("phone", Tools.getStr(userorder.getPhone()));
				objectMap.put("address", Tools.getStr(userorder.getAddress()));
				objectMap.put("status", userorder.getStatus());
				objectMap.put("statusname", userorder.getStatusname());
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
				objectMap.put("modelcode", userorder.getModelcode());
				objectMap.put("modelname", userorder.getModelname());
				
				//此订单获取的提成
				Salegaininfo Salegaininfoform = new Salegaininfo();
				Salegaininfoform.setOrdercode(userorder.getOrdercode());
				Salegaininfoform.setGainercode(mobileOperator.getEmployeecode());//登陆移动端APP的提成
				Long gaintotalmoney = salegaininfoDao.findGaintotalmoney(Salegaininfoform);
				if(gaintotalmoney == null ){
					gaintotalmoney = Long.valueOf(0);
				}
				objectMap.put("gainmoney", gaintotalmoney);
				
				objectMaplist.add(objectMap);
			}
			
			responseMap.put("status", "1");
			responseMap.put("result", "请求成功");
			responseMap.put("userorderList", objectMaplist);
			
			return responseMap;
		}catch(Exception e){
			e.printStackTrace();
			responseMap.put("status", "0");//保存失败
			responseMap.put("result", "数据异常，操作失败");//
			return responseMap;
		}
	}
	
	
	/**
	 * 查看进行中的订单信息
	 */
	@RequestMapping(value="/lookUserorder_ing")
	public String lookUserorder_ing(Userorder form) {
		form.setUserorder(userorderDao.findByOrdercode(form.getOrdercode()));
		return "mobilebusiness/saleinfo/lookUserorderInfo_ing";
	}
	
	/**
	 * 查看已完结的订单信息
	 */
	@RequestMapping(value="/lookUserorder_ed")
	public String lookUserorder_ed(Userorder form) {
		
		//销售员编号
		Employee  mobileOperator = (Employee)getSession().getAttribute("MobileOperator");
		Userorder userorder = userorderDao.findByOrdercode(form.getOrdercode());
		if(userorder == null ){
			userorder = new Userorder();
		}
		form.setUserorder(userorder);
		//此订单获取的提成
		Salegaininfo Salegaininfoform = new Salegaininfo();
		Salegaininfoform.setOrdercode(userorder.getOrdercode());
		Salegaininfoform.setGainercode(mobileOperator.getEmployeecode());//登陆移动端APP的提成
		Long gaintotalmoney = salegaininfoDao.findGaintotalmoney(Salegaininfoform);
		if(gaintotalmoney == null ){
			gaintotalmoney = Long.valueOf(0);
		}
		form.setGaintotalmoney(gaintotalmoney.intValue());
		return "mobilebusiness/saleinfo/lookUserorderInfo_ed";
	}
	
	/**
	 * 订单支付
	 */
	@RequestMapping(value="/savePaymentInfoInit")
	public String savePaymentInfoInit(Userpaylog form) {
		//订单
    	Userorder userorder = userorderDao.findByOrdercode(form.getOrdercode());
    	if(userorder == null){
    		userorder = new Userorder();
    	}
    	
    	if("1".equals(form.getPayitem())){
    		form.setPaymoney(userorder.getFirstpayment()); //首付款金额
    	}else if("2".equals(form.getPayitem())){
    		form.setPaymoney(userorder.getFinalpayment());//尾款金额
    	}else if("3".equals(form.getPayitem())){
    		form.setPaymoney(userorder.getTotalmoney());//全款金额
    	}
    	
		return "mobilebusiness/saleinfo/savePaymentInfo";
	}
	
	
	/**
	 * 初始化门锁数据修改
	 */
	@RequestMapping(value="/updateUserdoordataInit")
	public String updateUserdoordataInit(Userdoordata form) {
		
		Userorder userorder = userorderDao.findByOrdercode(form.getOrdercode());
		if(userorder == null){
			userorder = new Userorder();
		}
		form.setUserorder(userorder);
		
		Userdoordata userdoordata = userdoordataDao.findByOrdercode(form.getOrdercode());
		if(userdoordata == null){
			userdoordata = new Userdoordata();
		}
		form.setUserdoordata(userdoordata);
		
		
		return "mobilebusiness/saleinfo/updateUserdoordata";
	}
	
	/**
	 * 保存订单门锁数据
	 */
	@RequestMapping(value = "/updateUserdoordata")
	public String updateUserdoordata(Userdoordata form) {
		
		//操作员
		Employee employee = (Employee)getSession().getAttribute("MobileOperator");

		Map<String, Object> responseMap = mobilebusinessService.updateUserdoordata(form, getRequest(), employee);
		
		form.setReturninfo(String.valueOf(responseMap.get("result")));
		
		return updateUserdoordataInit(form);
		
	}
	
	/**
	 * 初始化产品价格修改
	 */
	@RequestMapping(value="/updateProductfeeInit")
	public String updateProductfeeInit(Userorder form) {
		
		Userorder userorder = userorderDao.findByOrdercode(form.getOrdercode());
		if(userorder == null){
			userorder = new Userorder();
		}
		form.setUserorder(userorder);
		
		return "mobilebusiness/saleinfo/updateProductfee";
	}
	
	/**
	 * 保存产品价格修改
	 */
	@RequestMapping(value = "/updateProductfee")
	public String updateProductfee(Userorder form) {
		
		//操作员
		Employee employee = (Employee)getSession().getAttribute("MobileOperator");

		Map<String, Object> responseMap = mobilebusinessService.updateProductfee(form, getRequest(), employee);
		
		form.setReturninfo(String.valueOf(responseMap.get("result")));
		
		return updateProductfeeInit(form);
		
	}
	
	/**
	 * 初始化门锁图片修改
	 */
	@RequestMapping(value="/updateUserdoorInit")
	public String updateUserdoorInit(Userdoor form) {
		
		Userorder userorder = userorderDao.findByOrdercode(form.getOrdercode());
		if(userorder == null){
			userorder = new Userorder();
		}
		form.setUserorder(userorder);
		
		return "mobilebusiness/saleinfo/updateUserdoor";
	}
	
	/**
	 * 查询订单门锁图片Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findUserdoorListJSON")
	public Map<String, Object> findUserdoorListJSON(Userdoor form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		//封装List数据
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		List<Userdoor> list = userdoorDao.queryByList(form);
		for (Userdoor userdoor : list) {
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
			objectlist.add(objectMap);
		}
		
		result.put("userdoorList", objectlist);
		return result;
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
	 * 单个订户门锁图片删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/deleteUserdoor")
	public Map<String,Object> deleteUserdoor(Userdoor form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Userdoor userdoor = userdoorDao.findById(form.getId());
		
		 //删除服务器所在文件
		File tmpFile = new File(userdoor.getPreserveurl());
		tmpFile.delete();
		
		//删除门锁
		userdoorDao.delete(form.getId());
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "删除门锁，客户编号为:"+userdoor.getUsercode() + ";门锁文件名称为:" + userdoor.getFilename();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
	/**
	 * 门锁上传
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/saveUserdoorInfo")
	public Map<String, Object> saveUserdoorInfo(Userorder form){
		return mobilebusinessService.saveUserdoorInfo(form, getRequest(), getSession());
	}
	
	/**
	 * 任务单管理
	 */
	@RequestMapping(value="/manageUsertask")
	public String manageUsertask(Userorder form) {
		return "mobilebusiness/saleinfo/manageUsertask";
	}
	
	// 查询讲解任务单，通过讲解人编号
	@ResponseBody
	@RequestMapping(value = "/findUsertaskListJSON")
	public Map<String, Object> findUsertaskListJSON(Usertask form) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		
		//销售员编号
		Employee  mobileOperator = (Employee)getSession().getAttribute("MobileOperator");
		String salercode = mobileOperator.getEmployeecode();
		
		if(StringUtils.isEmpty(salercode)){
			responseMap.put("status", "0");
			responseMap.put("result", "请求失败,请输入销售员编号");
			return responseMap;
		}
		
		//查询讲解员的任务单
		form.setTalkercode(salercode);
		
		if("0".equals(form.getQuerystateflag())){//查询未处理任务单
			form.setSort("addtime");
		}else if("1".equals(form.getQuerystateflag())){//查询已处理任务单
			form.setSort("dealdate");
		}
		
		List<Usertask> usertasklist = usertaskDao.findByList(form);
		
		for (Usertask usertask : usertasklist) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			objectMap.put("id", usertask.getId());
			objectMap.put("taskcode", usertask.getTaskcode());
			objectMap.put("usercode", usertask.getUsercode());
			objectMap.put("username", usertask.getUsername());
			objectMap.put("usersex", usertask.getUsersex());
			objectMap.put("phone", usertask.getPhone());
			objectMap.put("address", usertask.getAddress());
			objectMap.put("source", usertask.getSource());
			//销售员
			Employee saler = null;
			if(StringUtils.isNotEmpty(usertask.getSalercode())){
				saler = employeeDao.findByEmployeecodeStr(usertask.getSalercode());
			}
			if(saler == null){
				saler = new Employee();
			}
			objectMap.put("salercode", usertask.getSalercode());
			objectMap.put("salername", saler.getEmployeename());
			objectMap.put("salerphone", saler.getPhone());
			
			//讲解人
			Employee talker = null;
			if(StringUtils.isNotEmpty(usertask.getTalkercode())){
				talker = employeeDao.findByEmployeecodeStr(usertask.getTalkercode());
			}
			if(talker == null){
				talker = new Employee();
			}
			objectMap.put("visitorcode", usertask.getVisitorcode());
			objectMap.put("visitorname", talker.getEmployeename());
			objectMap.put("visitorphone", talker.getPhone());
			
			objectMap.put("intention", usertask.getIntention());
			objectMap.put("status", usertask.getStatus());
			objectMap.put("statusname", usertask.getStatusname());
			objectMap.put("addtime", StringUtils.isEmpty(usertask.getAddtime())?"":Tools.getStr(usertask.getAddtime()).substring(0, 19));
			objectMap.put("dealdate", StringUtils.isEmpty(usertask.getDealdate())?"":Tools.getStr(usertask.getDealdate()).substring(0, 19));
			objectMap.put("visittime", StringUtils.isEmpty(usertask.getVisittime())?"":Tools.getStr(usertask.getVisittime()).substring(0, 19));
			//查询订单状态
			Userorder userorder = userorderDao.findByOrdercode(usertask.getOrdercode());
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
	
	/**
	 * 查看未处理的任务单信息
	 */
	@RequestMapping(value="/lookUsertask_ing")
	public String lookUsertask_ing(Userorder form) {
		form.setUsertask(usertaskDao.findByTaskcodeStr(form.getTaskcode()));
		
		//需要加载产品型号
    	Productmodel productmodelForm = new Productmodel();
    	productmodelForm.setEffectivetimeflag("1");
    	List<Productmodel> productmodelList = productmodelDao.queryByList(productmodelForm);
    	
    	getRequest().setAttribute("productmodelList", productmodelList);
		
		return "mobilebusiness/saleinfo/lookUsertaskInfo_ing";
	}
	
	/**
	 * 保存订单录入信息-处理任务单
	 */
	@ResponseBody
	@RequestMapping(value = "/saveUserorder_forUsertask")
	public Map<String, Object> saveUserorder_forUsertask(Userorder form) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("status", "0");//提交失败
		responseMap.put("result", "提交失败");//注册成功
		
		if ("".equals(form.getTaskcode())) {
			responseMap.put("result", "提交失败，请上传任务单编号");
			return responseMap;
		} 
		
		if ("".equals(Tools.getStr(form.getModelcode()))) {
			responseMap.put("result", "提交失败，请选择产品型号");
			return responseMap;
		}
		
		if ("".equals(Tools.getStr(form.getProductcolor()))) {
			responseMap.put("result", "提交失败，请选择产品颜色");
			return responseMap;
		}
			
		return mobilebusinessService.saveUserorderForUsertask(form, getRequest(), getSession());
		
	}
	
	/**
	 * 查看已处理的任务单信息
	 */
	@RequestMapping(value="/lookUsertask_ed")
	public String lookUsertask_ed(Userorder form) {
		form.setUserorder(userorderDao.findByOrdercode(form.getOrdercode()));
		return "mobilebusiness/saleinfo/lookUsertaskInfo_ed";
	}
	
	
	/**
	 * 查询下级贡献信息
	 * @return
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value="/findChildrenSalerGainListJSON")
	public Map<String,Object> findChildrenSalerGainListJSON(Employee form) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		
		try{
			//销售员编号
			Employee  mobileOperator = (Employee)getSession().getAttribute("MobileOperator");
			String salercode = mobileOperator.getEmployeecode();
			
			if(StringUtils.isEmpty(salercode)){
				responseMap.put("status", "0");
				responseMap.put("result", "请求失败,请输入销售员编号");
				return responseMap;
			}
			
			//赋值销售员
			form.setEmployeecode(salercode);
			
			List<Employee> childrenSalerInfoList = httpForMpsDao.findChildrenSalerInfoListBySalercode(form);
			
			for (Employee childrenSaler : childrenSalerInfoList) {
				HashMap<String, Object> objectMap = new HashMap<String, Object>();
				objectMap.put("id", childrenSaler.getId());
				objectMap.put("employeecode", childrenSaler.getEmployeecode());
				objectMap.put("employeename", childrenSaler.getEmployeename());
				objectMap.put("phone", Tools.getTransValueFromStr(childrenSaler.getPhone(), 3, 4, "*"));
				objectMap.put("totalmoney", (childrenSaler.getTotalmoney() == null?0:childrenSaler.getTotalmoney()));
				objectMap.put("totalcount", childrenSaler.getTotalcount());
				
				Employee employeeform = new Employee();
				employeeform.setParentemployeecode(childrenSaler.getEmployeecode());//赋值上级销售员编号
				Employee chiledrenemployee = httpForMpsDao.findTotalChildrenSalerInfoListBySalercode(employeeform);
				if(chiledrenemployee == null){
					chiledrenemployee = new Employee();
				}
				
				objectMap.put("childrentotalmoney", (chiledrenemployee.getTotalmoney() == null?0:chiledrenemployee.getTotalmoney()));
				objectMap.put("childrentotalcount", chiledrenemployee.getTotalcount());
				
				objectMaplist.add(objectMap);
			}
			
			responseMap.put("status", "1");
			responseMap.put("result", "请求成功");
			responseMap.put("childsalergainList", objectMaplist);
			
			return responseMap;
		}catch(Exception e){
			e.printStackTrace();
			responseMap.put("status", "0");//保存失败
			responseMap.put("result", "数据异常，操作失败");//
			return responseMap;
		}
	}
	
	/**
	 * 查询销售提成信息统计
	 * @return
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value="/findSalegainStatJSON")
	public Map<String,Object> findSalegainStatJSON(Employee form) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		
		try{
			//销售员编号
			Employee  mobileOperator = (Employee)getSession().getAttribute("MobileOperator");
			String salercode = mobileOperator.getEmployeecode();
			
			if(StringUtils.isEmpty(salercode)){
				responseMap.put("status", "0");
				responseMap.put("result", "请求失败,请输入销售员编号");
				return responseMap;
			}
			
			//form.setDepartment("2");//只查询销售员的
			form.setEmployeecode(salercode);//赋值销售员编号
			//当前操作员的提成统计排名
			Employee currentSalerInfo = httpForMpsDao.findSalegaininfoStatByEmployeecode(form);
			HashMap<String, Object> objectMapForCurrent = new HashMap<String, Object>();
			objectMapForCurrent.put("employeecode", currentSalerInfo.getEmployeecode());
			objectMapForCurrent.put("employeename", Tools.getTransValueFromStr(currentSalerInfo.getEmployeename(), 1, currentSalerInfo.getEmployeename().length()-1, "*"));
			objectMapForCurrent.put("phone", Tools.getTransValueFromStr(currentSalerInfo.getPhone(), 3, 4, "*"));
			objectMapForCurrent.put("totalmoney", currentSalerInfo.getTotalmoney());
			objectMapForCurrent.put("ranking", currentSalerInfo.getRanking()>100?"100+":currentSalerInfo.getRanking());//如果大于100，就显示100+
			objectMaplist.add(objectMapForCurrent);
			
			List<Employee> salerInfoList = httpForMpsDao.findSalegaininfoStat(form);
			for (Employee saler : salerInfoList) {
				HashMap<String, Object> objectMap = new HashMap<String, Object>();
				objectMap.put("employeecode", saler.getEmployeecode());
				objectMap.put("employeename", Tools.getTransValueFromStr(saler.getEmployeename(), 1, saler.getEmployeename().length()-1, "*"));
				objectMap.put("phone", Tools.getTransValueFromStr(saler.getPhone(), 3, 4, "*"));
				objectMap.put("totalmoney", saler.getTotalmoney());
				objectMap.put("ranking", saler.getRanking());
				objectMaplist.add(objectMap);
			}
			
			responseMap.put("status", "1");
			responseMap.put("result", "请求成功");
			responseMap.put("salegainStatList", objectMaplist);
			
			return responseMap;
		}catch(Exception e){
			e.printStackTrace();
			responseMap.put("status", "0");//保存失败
			responseMap.put("result", "数据异常，操作失败");//
			return responseMap;
		}
	}
	
	/**
	 * 查询未领提成记录信息List
	 */
	@RequestMapping(value="/findSalegaininfoList")
	public String findSalegaininfoList(Salegaininfo form) {
		return "mobilebusiness/employeeinfo/findSalegaininfoList";
	}
	
	//查询未提成明细列表
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value="/getSalegaininfoListJSON")
	public Map<String, Object> getSalegaininfoListJSON(Salegaininfo form) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		
		try{
			//销售员编号
			Employee  mobileOperator = (Employee)getSession().getAttribute("MobileOperator");
			String salercode = mobileOperator.getEmployeecode();
			
			//查询所有的产品型号信息
			form.setGainercode(salercode);
			
			if("1".equals(form.getStatus())){ //查询已提取上个月的提成金额明细
				Date date = new Date();
				//当月的第一天
				String firstDateOfSeason = DateUtils.formatDate(DateUtils.getFirstDateOfMonth(date));
				//上月的第一天
				String firstDateOfMonth_pre_one = Tools.getSpecifiedDayAfterMonth(firstDateOfSeason,"yyyy-MM-dd",-1);
				//将上月第一天转化为时间格式
				Date firstDateOfSeason_pre_one_Date = DateUtils.parseDate(firstDateOfMonth_pre_one);
				//上月的最后一天
				String lastDateOfSeason_pre_one = DateUtils.formatDate(DateUtils.getLastDateOfMonth(firstDateOfSeason_pre_one_Date));
				
				form.setBegintime(firstDateOfMonth_pre_one);
				form.setEndtime(lastDateOfSeason_pre_one);
			}
			
			List<Salegaininfo> salegaininfolist  = salegaininfoDao.findByList(form);
			for (Salegaininfo salegaininfo : salegaininfolist) {
				HashMap<String, Object> objectMap = new HashMap<String, Object>();
				objectMap.put("id", salegaininfo.getId());
				//提成人信息
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
				objectMap.put("ordercode", salegaininfo.getOrdercode());
				objectMap.put("totalmoney", salegaininfo.getTotalmoney());
				objectMap.put("gaincode", salegaininfo.getGaincode());
				objectMap.put("gainname", salegaininfo.getGainname());
				objectMap.put("gainrate", salegaininfo.getGainrate());
				objectMap.put("gainmoney", salegaininfo.getGainmoney());
				objectMap.put("addtime", StringUtils.isEmpty(salegaininfo.getAddtime())?"":Tools.getStr(salegaininfo.getAddtime()).substring(0, 10));
				objectMap.put("status", salegaininfo.getStatus());
				objectMap.put("statusname", salegaininfo.getStatusname());
				objectMap.put("gaintime", StringUtils.isEmpty(salegaininfo.getGaintime())?"":Tools.getStr(salegaininfo.getGaintime()).substring(0, 10));
				objectMap.put("remark", salegaininfo.getRemark());
				objectMaplist.add(objectMap);
			}
			
			responseMap.put("status", "1");
			responseMap.put("result", "请求成功");
			responseMap.put("salegaininfolist", objectMaplist);
			
			return responseMap;
			
		}catch(Exception e){
			e.printStackTrace();
			responseMap.put("status", "0");//保存失败
			responseMap.put("result", "数据异常，操作失败");//
			return responseMap;
		}
	}
	
	/**
	 * 查询提成领取记录信息List
	 */
	@RequestMapping(value="/findSalegainlogList")
	public String findSalegainlogList(Salegainlog form) {
		return "mobilebusiness/employeeinfo/findSalegainlogList";
	}
	
	//查询提成领取记录列表
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value="/getSalegainlogListJSON")
	public Map<String, Object> getSalegainlogListJSON(Salegainlog form) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		
		try{
			//销售员编号
			Employee  mobileOperator = (Employee)getSession().getAttribute("MobileOperator");
			String salercode = mobileOperator.getEmployeecode();
			
			//查询所有的产品型号信息
			form.setGainercode(salercode);
			
			//查询所有的产品型号信息
			List<Salegainlog> salegainloglist = salegainlogDao.findByList(form);
			
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
				objectMap.put("gaintime", StringUtils.isEmpty(salegainlog.getGaintime())?"":Tools.getStr(salegainlog.getGaintime()).substring(0, 10));
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
			
		}catch(Exception e){
			e.printStackTrace();
			responseMap.put("status", "0");//保存失败
			responseMap.put("result", "数据异常，操作失败");//
			return responseMap;
		}
	}
	
	/**
	 * 通过提成发放编号来查询它所包含的提成明细记录
	 */
	@RequestMapping(value="/findSalegaininfoListByGainlogcode")
	public String findSalegaininfoListByGainlogcode(Salegaininfo form) {
		return "mobilebusiness/employeeinfo/findSalegaininfoListByGainlogcode";
	}
	
	//通过提成发放编号来查询它所包含的提成明细记录
	@ResponseBody
	@RequestMapping(value="/findSalegaininfoListJSONByGainlogcode")
	public Map<String, Object> findSalegaininfoListJSONByGainlogcode(Salegaininfo form) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		
		try{
			//销售员编号
			Employee  mobileOperator = (Employee)getSession().getAttribute("MobileOperator");
			String salercode = mobileOperator.getEmployeecode();
			//赋值操作员
			form.setGainercode(salercode);
			
			//通过提成发放编号来查询对应的提成明细信息
			List<Salegaininfo> salegaininfolist  = salegaininfoDao.findByList(form);
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
				objectMap.put("ordercode", salegaininfo.getOrdercode());
				objectMap.put("totalmoney", salegaininfo.getTotalmoney());
				objectMap.put("gaincode", salegaininfo.getGaincode());
				objectMap.put("gainname", salegaininfo.getGainname());
				objectMap.put("gainrate", salegaininfo.getGainrate());
				objectMap.put("gainmoney", salegaininfo.getGainmoney());
				objectMap.put("addtime", StringUtils.isEmpty(salegaininfo.getAddtime())?"":Tools.getStr(salegaininfo.getAddtime()).substring(0, 10));
				objectMap.put("status", salegaininfo.getStatus());
				objectMap.put("statusname", salegaininfo.getStatusname());
				objectMap.put("gaintime", StringUtils.isEmpty(salegaininfo.getGaintime())?"":Tools.getStr(salegaininfo.getGaintime()).substring(0, 10));
				objectMap.put("remark", salegaininfo.getRemark());
				objectMaplist.add(objectMap);
			}
			responseMap.put("status", "1");
			responseMap.put("result", "请求成功");
			responseMap.put("salegaininfolist", objectMaplist);
			
			return responseMap;
		}catch(Exception e){
			e.printStackTrace();
			responseMap.put("status", "0");//保存失败
			responseMap.put("result", "数据异常，操作失败");//
			return responseMap;
		}
	}
	
	/**
	 * 行动力明细查询
	 */
	@RequestMapping(value="/findSaleenergyinfoList")
	public String findSaleenergyinfoList(Saleenergyinfo form) {
		return "mobilebusiness/employeeinfo/findSaleenergyinfoList";
	}
	
	//行动力明细查询JSON
	@ResponseBody
	@RequestMapping(value="/findSaleenergyinfoListJSON")
	public Map<String, Object> findSaleenergyinfoListJSON(Saleenergyinfo form) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		
		try{
			//销售员编号
			Employee  mobileOperator = (Employee)getSession().getAttribute("MobileOperator");
			String salercode = mobileOperator.getEmployeecode();
			//赋值操作员
			form.setGainercode(salercode);
			
			List<Saleenergyinfo> saleenergyinfolist  = saleenergyinfoDao.findByList(form);
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
				objectMap.put("visitorcode", saleenergyinfo.getVisitorcode());
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
		}catch(Exception e){
			e.printStackTrace();
			responseMap.put("status", "0");//保存失败
			responseMap.put("result", "数据异常，操作失败");//
			return responseMap;
		}
	}
	
	/**
	 * 查询行动力规则
	 */
	@RequestMapping(value="/findEnergyHelpInfoList")
	public String findSaleenergyinfoList(Helpinfo form) {
		form.setType("0");//行动力
		//查询所有的产品型号信息
		List<Helpinfo> helpinfolist = helpinfoDao.queryByList(form);
		form.setHelpinfolist(helpinfolist);
		return "mobilebusiness/employeeinfo/findEnergyHelpInfoList";
	}
	
	/**
	 * 查询我的楼盘信息
	 */
	@RequestMapping(value="/findMyCellextendList")
	public String findMyCellextendList(Salegaininfo form) {
		return "mobilebusiness/employeeinfo/findMyCellextendList";
	}
	
	//查询我的楼盘信息JSON
	@ResponseBody
	@RequestMapping(value="/findMyCellextendListJSON")
	public Map<String, Object> findMyCellextendListJSON(Cellextend form) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		
		try{
			//销售员编号
			Employee  mobileOperator = (Employee)getSession().getAttribute("MobileOperator");
			String salercode = mobileOperator.getEmployeecode();
			//赋值抢购员
			form.setAcceptercode(salercode);
			
			//查询所有的产品型号信息
			List<Cellextend> cellextendlist = cellextendDao.findByList(form);
			
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
		}catch(Exception e){
			e.printStackTrace();
			responseMap.put("status", "0");//保存失败
			responseMap.put("result", "数据异常，操作失败");//
			return responseMap;
		}
	}
	
	/**
	 * 查询抢购的楼盘信息
	 */
	@RequestMapping(value="/lookCellextend")
	public String lookCellextend(Cellextend form) {
		Cellextend cellextend = cellextendDao.findByExtendcode(form.getExtendcode());
		Cell cell = null;
		if(cellextend != null){
			cell = cellDao.findByCellcode(cellextend.getCellcode());
		}
		if(cellextend == null){
			cellextend = new Cellextend();
		}
		if(cell == null){
			cell = new Cell();
		}
		cellextend.setCell(cell);
		form.setCellextend(cellextend);
		return "mobilebusiness/employeeinfo/lookCellextend";
	}
	
	/**
	 * 扫楼支付
	 */
	@RequestMapping(value="/savePaymentInfoForCellextendInit")
	public String savePaymentInfoForCellextendInit(Cellpaylog form) {
		//订单
    	Cellextend cellextend = cellextendDao.findByExtendcode(form.getExtendcode());
    	if(cellextend == null){
    		cellextend = new Cellextend();
    	}
    	form.setPaymoney(cellextend.getTotalmoney());
    	
		return "mobilebusiness/employeeinfo/savePaymentInfoForCellextend";
	}
	
	/**
	 * 申请驻点页面初始化
	 */
	@RequestMapping(value="/saveCellstationInit")
	public String saveCellstationInit(Cellstation form) {
		return "mobilebusiness/employeeinfo/saveCellstation";
	}
	
	/**
	 * 保存小区驻点信息
	 */
	@RequestMapping(value = "/saveCellstation")
	public String saveCellstation(Cellstation form) {
		
		//操作员
		Employee employee = (Employee)getSession().getAttribute("MobileOperator");

		Map<String, Object> responseMap = mobilebusinessService.saveCellstation(form, getRequest(), employee);
		
		form.setReturninfo(String.valueOf(responseMap.get("result")));
		
		return saveCellstationInit(form);
		
	}
	
	/**
	 * 查询抢购的楼盘信息
	 */
	@RequestMapping(value="/lookCellstation")
	public String lookCellstation(Cellstation form) {
		Cellstation cellstation = cellstationDao.findByExtendcode(form.getExtendcode());
		if(cellstation == null){
			cellstation = new Cellstation();
		}
		form.setCellstation(cellstation);
		return "mobilebusiness/employeeinfo/lookCellstation";
	}
	
	/**
	 * 通过驻点编号查询驻点人员
	 */
	@RequestMapping(value="/findEmployeeListByStationcode")
	public String findEmployeeListByStationcode(Cellstationemployee form) {
		return "mobilebusiness/employeeinfo/findEmployeeListByStationcode";
	}
	
	//通过驻点编号查询驻点人员JSON
	@ResponseBody
	@RequestMapping(value="/findEmployeeListJSONByStationcode")
	public Map<String, Object> findEmployeeListJSONByStationcode(Cellstationemployee form) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		
		try{
			
			//通过驻点编号查询驻点人员信息
			List<Cellstationemployee> cellstationemployeelist = cellstationemployeeDao.findByList(form);
			
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
		}catch(Exception e){
			e.printStackTrace();
			responseMap.put("status", "0");//保存失败
			responseMap.put("result", "数据异常，操作失败");//
			return responseMap;
		}
	}
	
	
	/**
	 * 订单支付
	 */
	@RequestMapping(value="/savePaymentInfoInit_getCode")
	@ResponseBody
	public void savePaymentInfoInit_getCode(Userpaylog form, HttpServletRequest request, HttpServletResponse response) throws IOException {

		//订单
    	Userorder userorder = userorderDao.findByOrdercode(form.getOrdercode());
    	if(userorder == null){
    		userorder = new Userorder();
    	}
    	
    	if("1".equals(form.getPayitem())){
    		form.setPaymoney(userorder.getFirstpayment()); //首付款金额
    	}else if("2".equals(form.getPayitem())){
    		form.setPaymoney(userorder.getFinalpayment());//尾款金额
    	}else if("3".equals(form.getPayitem())){
    		form.setPaymoney(userorder.getTotalmoney());//全款金额
    	}
		
		//获取code
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		//String stateJson = form.getOrdercode() + "-;" + form.getPayitem() + "-;" + form.getPaymoney() + "-;" + form.getPaytype();
		
		JsonObjectForPara para = new JsonObjectForPara();
		para.setOrdercode(form.getOrdercode());
		para.setPayitem(form.getPayitem());
		//para.setPayitemname(form.getPayitemname());
		para.setPaymoney(String.valueOf(form.getPaymoney()));
		//para.setPaytype(form.getPaytype());
		para.setJumpurl(form.getJumpurl());
		
		String stateJson = new Gson().toJson(para);
		
		System.out.println(stateJson);
		
                //这里要将你的授权回调地址处理一下，否则微信识别不了
		String redirect_uri = URLEncoder.encode(WeiXinConfig.apply_code_back, "UTF-8");
                //简单获取openid的话参数response_type与scope与state参数固定写死即可
		StringBuffer url=new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize?redirect_uri="+redirect_uri+
				"&appid=" + WeiXinConfig.appid_weixin +"&response_type=code&scope=snsapi_base&state=" + stateJson +"#wechat_redirect");
		
		System.out.println(url);
		
		response.sendRedirect(url.toString());//这里请不要使用get请求单纯的将页面跳转到该url即可
		
	}
	
	
	/**
	 * 订单支付前置-获取openid
	 */
	@RequestMapping(value = "/savePaymentInfo_getOpenid")
	public String savePaymentInfo_getOpenid(Userpaylog form, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String state = request.getParameter("state");
		//state = new String(state.getBytes("ISO-8859-1"), "UTF-8");
		//System.out.println("*********************************************state=\n"+state);
		
		String code = request.getParameter("code");
		//System.out.println("------1111111111111111--------code="+code);
		
		//回调函数带的参数
		JSONObject stateJSON = new JSONObject(state);
		
		String ordercode = stateJSON.getString("ordercode");
		form.setOrdercode(ordercode);
		String payitem = stateJSON.getString("payitem");
		form.setPayitem(payitem);
		Integer paymoney = stateJSON.getInt("paymoney");
		form.setPaymoney(paymoney);
		//String paytype = stateJSON.getString("paytype");
		//form.setPaytype(paytype);
		String jumpurl = stateJSON.getString("jumpurl");
		form.setJumpurl(jumpurl);
		//获取openid
		String openid = "";
		List<Object> list = WeixinGetOpenid.accessToken(code);
		if (list.size() != 0) {
			openid = list.get(0).toString();
		}
        
		request.setAttribute("code", openid);
		request.setAttribute("openid", openid);

		//System.out.println("------2222222222222222--------openid="+openid);
		
		return "mobilebusiness/saleinfo/savePaymentInfo";
	}
	
	/**
	 * 订单支付
	 */
	@ResponseBody
	@RequestMapping(value = "/savePaymentInfo")
	public Map<String, Object> savePaymentInfo(Userpaylog form, HttpServletRequest request) throws IOException{
		String ordercode = form.getOrdercode();
		String payitem = form.getPayitem();
		String paytype = form.getPaytype();
		Integer paymoney = form.getPaymoney();
		String payitemname = form.getPayitemname();
		
		//String code = request.getParameter("code");
		String openid = request.getParameter("openid");
		
		//操作员
		Employee employee = (Employee)getSession().getAttribute("MobileOperator");
		
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		//封装申请微信支付数据
		//申请交易编码ID
		String orderNumber = ordercode + "_" + payitem + "_" + paytype + "_" + Tools.getRandomPass(6,("0123456789ABCDEFGHJKLMNPQRSTUVWXYZ").toCharArray());
		//String attach = orderNumber;
		
		JsonObjectForPara para = new JsonObjectForPara();
		para.setOrdercode(form.getOrdercode());
		para.setPayitem(form.getPayitem());
		//para.setPayitemname(form.getPayitemname());
		para.setPaymoney(String.valueOf(form.getPaymoney()));
		para.setPaytype(form.getPaytype());
		//para.setOperatorcode(employee.getEmployeecode());
		para.setReceivercode(employee.getEmployeecode());
		
		String attact_Json = new Gson().toJson(para);
		
		System.out.println("--------********attact_Json************-----attact_Json=\n" + attact_Json);
		
		
		SortedMap<String, Object> parameters = new TreeMap<String, Object>();
		parameters.put("appid", WeiXinConfig.appid_weixin);//应用APPID
		parameters.put("attach", attact_Json); // 附加数据,原样返回
		parameters.put("body", payitemname);
		parameters.put("mch_id", WeiXinConfig.mch_id_weixin);//商户号
		parameters.put("nonce_str", WeiXinPayCommonUtil.genNonceStr());//随机字符串
		parameters.put("notify_url", WeiXinConfig.notify_url); // 回调接口地址
		//parameters.put("notify_url", ""); // 回调接口地址
		parameters.put("out_trade_no", orderNumber);// 订单号
		parameters.put("spbill_create_ip", "192.168.0.1");// 用户的IP
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String starttime=sdf.format(new Date());
		parameters.put("time_start", starttime); // 订单生成时间
		parameters.put("total_fee", paymoney*100 + "");// 金额(转成分为单位）
		//parameters.put("total_fee", 1 + "");// 金额(转成分为单位）
		if("1".equals(paytype)){
			parameters.put("trade_type", "JSAPI"); // 交易类型 APP ， JSAPI公众号	
			parameters.put("openid", openid);
		}else if ("3".equals(paytype)){
			parameters.put("trade_type", "NATIVE"); // 交易类型 APP ， NATIVE二维码
			parameters.put("product_id", orderNumber);
		}
		
		
		// 生成签名
		parameters.put("sign", WeiXinPayCommonUtil.createSign("UTF-8", parameters));
		// 生成xml请求
		String reXml = WeiXinPayCommonUtil.getRequestXml(parameters);
		System.out.println(reXml);
		// 请求xml
		String xml = null;
		try {
			xml = HttpRequest.sendPost(WeiXinConfig.gatewayUrl, reXml);
			xml = new String(xml.getBytes("ISO-8859-1"), "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(xml);

		// 解析xml
		Map<String, String> map = null;
		try {
			map = XMLUtil.doXMLParse(xml);
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(map);
		String return_code = map.get("return_code");
		try {
			if (return_code.equals("FAIL")) {
				result.put("return_code", map.get("return_code"));
				result.put("return_msg", map.get("return_msg"));
			} else if (return_code.equals("SUCCESS")) {
				String result_code= map.get("result_code");
				if(result_code.equals("SUCCESS")) {
					result.put("return_code", map.get("return_code"));
					result.put("return_msg", map.get("return_msg"));
					result.put("appid", map.get("appid"));
					result.put("mch_id", map.get("mch_id"));
					result.put("nonce_str", map.get("nonce_str"));
					result.put("result_code", map.get("result_code"));
					result.put("prepay_id", map.get("prepay_id"));
					result.put("trade_type", map.get("trade_type"));
					
					Long timestamp = new Date().getTime();
					
					result.put("timestamp", timestamp);
					result.put("code_url", map.get("code_url"));//二维码链接
					
					//支付时，重新签名
					SortedMap<String, Object> parameters_pay = new TreeMap<String, Object>();
					parameters_pay.put("appId", WeiXinConfig.appid_weixin);//应用APPID
					parameters_pay.put("timeStamp", timestamp); // 时间搓
					parameters_pay.put("nonceStr", map.get("nonce_str"));//随机字符串
					parameters_pay.put("package", "prepay_id=" + map.get("prepay_id"));
					parameters_pay.put("signType", "MD5");
					
					// 生成签名
					result.put("sign", WeiXinPayCommonUtil.createSign("UTF-8", parameters_pay));
					
				}else if(result_code.equals("FAIL")) {
					result.put("return_code", map.get("return_code"));
					result.put("return_msg", map.get("return_msg"));
					result.put("appid", map.get("appid"));
					result.put("mch_id", map.get("mch_id"));
					result.put("nonce_str", map.get("nonce_str"));
					result.put("sign", map.get("sign"));
					result.put("result_code", map.get("result_code"));
					result.put("err_code", map.get("err_code"));
					result.put("err_code_des", map.get("err_code_des"));
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(map);
		
		return result;
	}
	
	/**
	 * 订单支付成功，微信回调解接口函数
	 */
	@RequestMapping(value="/savePaymentInfo_notify")
	@ResponseBody
	public void savePaymentInfo_notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//System.out.println("--------------------进入支付成功回调函数-------------------");
		
		// 读取参数
		StringBuffer sb = new StringBuffer();
		InputStream inputStream = request.getInputStream();
		String s;
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		while ((s = in.readLine()) != null) {
			sb.append(s);
		}
		in.close();
		inputStream.close();

		// 解析xml成map
		Map<String, String> m = new HashMap<String, String>();
		m = XMLUtil.doXMLParse(sb.toString());

		// 过滤空 设置 TreeMap
		SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
		Iterator it = m.keySet().iterator();
		while (it.hasNext()) {
			String parameter = (String) it.next();
			String parameterValue = m.get(parameter);

			String v = "";
			if (null != parameterValue) {
				v = parameterValue.trim();
			}
			packageParams.put(parameter, v);
		}

		// 账号信息
		String key = WeiXinConfig.key_weixin; // key,商户密钥

		System.out.println(packageParams);
		// 判断签名是否正确
		if (WeiXinPayCommonUtil.isTenpaySign("UTF-8", packageParams, key)) {
			// ------------------------------
			// 处理业务开始
			// ------------------------------
			String resXml = "";
			if ("SUCCESS".equals((String) packageParams.get("result_code"))) {
				// 这里是支付成功
				////////// 执行自己的业务逻辑////////////////
				String mch_id = (String) packageParams.get("mch_id");//商户账号
				String openid = (String) packageParams.get("openid");//用户在商户appid下的唯一标识
				String is_subscribe = (String) packageParams.get("is_subscribe");//用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
				String out_trade_no = (String) packageParams.get("out_trade_no");//唯一订单号
				String trade_type = (String) packageParams.get("trade_type");//交易类型 APP ， NATIVE二维码
				String total_fee = (String) packageParams.get("total_fee");//支付金额
				String transaction_id= (String) packageParams.get("transaction_id");//微信支付订单号
				String attach= (String) packageParams.get("attach");//附加的数据，原样返回
				String time_end= (String) packageParams.get("time_end");//支付完成时间，原样返回
				//先保存微信交易记录			
				Wechatpay wechatpay=new Wechatpay();
				wechatpay.setMchid(mch_id);
				wechatpay.setOpenid(openid);
				wechatpay.setOuttradeno(out_trade_no);
				wechatpay.setTimeend(time_end);
				wechatpay.setTotalfee(total_fee);
				wechatpay.setTradetype(trade_type);
				wechatpay.setTransactionid(transaction_id);
				
				//先本地数据库保存
				
				String ordercode = "";
				String payitem = "";
				
				//回调参数
				JSONObject json = new JSONObject();
				try {
					json = new JSONObject(attach);
					ordercode = json.getString("ordercode");
					wechatpay.setOrdercode(ordercode);
					wechatpay.setPayitem(json.getString("payitem"));
					wechatpay.setOperatorcode(json.getString("receivercode"));
					wechatpay.setPaytype(json.getString("paytype"));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				wechatpayDao.save(wechatpay);
				
				System.out.println("--------------------保存成功");
					
				Map resultHp = httpForMpsService.savePaymentInfo(json);//向OA保存订单数据

				System.out.println("支付成功");
				// 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
				resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
						+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

			} else {
				System.out.println("支付失败,错误信息：" + packageParams.get("err_code"));
				resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
						+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
			}
			// ------------------------------
			// 处理业务完毕
			// ------------------------------
			BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
			out.write(resXml.getBytes());
			out.flush();
			out.close();
		} else {
			System.out.println("通知签名验证失败");
		}

	}
	
	
	
	/**
	 * 查询引入人信息
	 */
	@RequestMapping(value="/findParentSalerInfo")
	public String findParentSalerInfo(Employee form) {
		//操作员
		Employee employee = (Employee)getSession().getAttribute("MobileOperator");
		
		//上级操作员不为空
		Employee parentEmployee = null;
		if(StringUtils.isNotEmpty(employee.getParentemployeecode())){
			parentEmployee = employeeDao.findByEmployeecodeStr(employee.getParentemployeecode());
		}
		
		if(parentEmployee == null){
			parentEmployee = new Employee();
			form.setReturninfo("没有引入人！");
			
		}
		
		form.setEmployee(parentEmployee);
		
		return "mobilebusiness/employeeinfo/findParentSalerInfo";
	}
	
	/**
	 * 查询个人信息
	 */
	@RequestMapping(value="/findEmployeeInfo")
	public String findEmployeeInfo(Employee form) {
		//操作员
		Employee employee = (Employee)getSession().getAttribute("MobileOperator");
		
		form.setEmployee(employee);
		
		return "mobilebusiness/employeeinfo/findEmployeeInfo";
	}
	
	/**
	 * 查询推广码
	 */
	@RequestMapping(value="/findEmployeeExtendcode")
	public String findEmployeeExtendcode(Employee form) {
		//操作员
		Employee employee = (Employee)getSession().getAttribute("MobileOperator");
		
		form.setEmployee(employee);
		
		return "mobilebusiness/employeeinfo/findEmployeeExtendcode";
	}
	
	/**
	 * 生成二维码图片
	 */
	@RequestMapping(value="/findMySaleQrcode")
	public String findMySaleQrcode(Employee form) {
		//操作员
		Employee employee = (Employee)getSession().getAttribute("MobileOperator");
		
		form.setEmployee(employee);
		
		String remark = WeiXinConfig.company_domain_name + "/shenfu/sale/saleExtendInit?salercode="+employee.getEmployeecode();
		
		String imageBase64QRCode =  ZXingCode.getLogoQRCode(remark, getRequest(), "申亚智能安防锁");//这里表示扫描二维码后出现的网页，只需要把网址放上就可以了
		form.setRemark(imageBase64QRCode);//保存下单链接
		return "mobilebusiness/employeeinfo/findMySaleQrcode";
	}
	
	/**
	 * @Description: 生成二维码
	 */
	@RequestMapping(value = "/getQrcodeImg", method = { RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String getQrcodeImg(String url, String content,HttpServletRequest request, HttpServletResponse response) {
	    try {
	    	return ZXingCode.getLogoQRCode(url, request, content);//这里表示扫描二维码后出现的网页，只需要把网址放上就可以了
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	/**
	 * 修改面初始化
	 */
	@RequestMapping(value="/updateApppasswordInit")
	public String updateApppasswordInit(Employee form) {
		//操作员
		Employee employee = (Employee)getSession().getAttribute("MobileOperator");
		
		form.setEmployee(employee);
				
		return "mobilebusiness/employeeinfo/updateApppassword";
	}
	
	/**
	 * 修改面初始化
	 */
	@RequestMapping(value="/updateApppassword")
	public String updateApppassword(Employee form) {
		//操作员
		Employee employee = employeeDao.findById(form.getId());
		//数据库的原密码
		String oldpassword = Tools.getStr(employee.getApppassword());
		//页面输入的原密码
		String oldpassword_input = Tools.getStr(getRequest().getParameter("oldPassword"));
		//将页面输入的密码加密
		//String oldpasswordEncrypt = AesSecret.aesEncrypt(oldpassword_input, AesSecret.key);
		
		if (!oldpassword_input.equals(oldpassword)) {
			form.setReturninfo("原密码输入不正确");
			return updateApppasswordInit(form);
		}
		
		//页面输入的新密码
		String password_input = Tools.getStr(getRequest().getParameter("apppassword"));
		//将页面输入的密码加密
		//String passwordEncrypt = AesSecret.aesEncrypt(password_input, AesSecret.key);
		employee.setApppassword(password_input);
		
		Integer updateFlag = employeeDao.update(employee);
		
		form.setReturninfo("修改成功");
				
		return updateApppasswordInit(form);
	}
	
	/**
	 * 下单链接推广
	 */
	@RequestMapping(value="/extendUserorderUrl")
	@ResponseBody
	public void extendUserorderUrl(Userpaylog form, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		//操作员
		Employee employee = (Employee)getSession().getAttribute("MobileOperator");
		
		//微信下单链接分享
		String extend_userorder_url = WeiXinConfig.extend_userorder_url + employee.getEmployeecode();
		
	}
	
	
	/**
	 * 优惠卡生成初始化
	 */
	@RequestMapping(value="/addGiftcardInit")
	public String addGiftcardInit(Giftcard form) {
		
		//需要加载产品型号
    	Productmodel productmodelForm = new Productmodel();
    	productmodelForm.setEffectivetimeflag("1");
    	List<Productmodel> productmodelList = productmodelDao.queryByList(productmodelForm);
    	
    	getRequest().setAttribute("productmodelList", productmodelList);
		
		return "mobilebusiness/employeeinfo/saveGiftcard";
	}
	
	/**
	 * 保存优惠卡生成
	 */
	@RequestMapping(value="/saveGiftcard")
	public String saveGiftcard(Giftcard form) {
		
		//操作员
		Employee employee = (Employee)getSession().getAttribute("MobileOperator");
        
		if(StringUtils.isEmpty(form.getModelcode())){
			form.setReturninfo("请选择产品型号");
			return addGiftcardInit(form);
		}
        
		if(form.getAmount() == null){
			form.setReturninfo("请输入优惠金额");
			return addGiftcardInit(form);
		}
		
		Productmodel productmodel = productmodelDao.findByModelcode(form.getModelcode());
		if(productmodel == null){
			form.setReturninfo("选择的产品型号不存在");
			return addGiftcardInit(form);
		}
		
		if(form.getAmount() > productmodel.getDiscountgain() ){//页面输入的优惠金额大于产品型号本身规定的优惠金额
			form.setReturninfo("输入的优惠金额不能大于产品型号允许的最大优惠金额");
			return addGiftcardInit(form);
		}

		//有效开始时间
		if(StringUtils.isEmpty(form.getStarttime())){
			form.setReturninfo("提交失败,有效开始时间不能为空");
			return addGiftcardInit(form);
		}
		
		//有效结束时间
		if(StringUtils.isEmpty(form.getEndtime())){
			form.setReturninfo("提交失败,有效结束时间不能为空");
			return addGiftcardInit(form);
			
		}
		
		if(form.getEndtime().compareTo(form.getStarttime()) < 0){
			form.setReturninfo("提交失败,有效结束时间不能早于开始时间");
			return addGiftcardInit(form);
		}
		
		Map<String, Object> responseMap = mobilebusinessService.saveGiftcard(form, getRequest(), employee);
		
		form.setGiftcardno(String.valueOf(responseMap.get("giftcardno")));//赋值新生成的优惠卡号
		form.setAddtime(String.valueOf(responseMap.get("addtime")));  //赋值保存结果
		form.setReturninfo(String.valueOf(responseMap.get("result")));  //赋值保存结果
		
		return "mobilebusiness/employeeinfo/saveGiftcardToSend";
	}
	
	/**
	 * 查询工单初始化界面
	 */
	@RequestMapping(value="/dispatchinfo")
	public String dispatchinfoInit(Userdispatch form) {
		return "mobilebusiness/dispatchinfo/dispatchinfo";
	}
	
	/**
	 * 查询未处理工单初始化界面
	 */
	@RequestMapping(value="/findUserdispatchListForStatus12")
	public String findUserdispatchListForStatus12(Userdispatch form) {
		return "mobilebusiness/dispatchinfo/findUserdispatchListForStatus12";
	}
	
	/**
	 * 查询已处理工单初始化界面
	 */
	@RequestMapping(value="/findUserdispatchListForStatus34")
	public String findUserdispatchListForStatus34(Userdispatch form) {
		return "mobilebusiness/dispatchinfo/findUserdispatchListForStatus34";
	}
	
	// 查询工单
	@RequestMapping(value="/findUserdispatchListJSON")
	@ResponseBody
	public Map<String, Object> findUserdispatchListJSON(Userdispatch form) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> objectMaplist = new ArrayList<HashMap<String, Object>>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求失败");
		//操作员
		Employee employee = (Employee)getSession().getAttribute("MobileOperator");
		//请求状态（0-未传数据;1-已传数据）
		form.setInstallercode(employee.getEmployeecode());
		
		if("12".equals(form.getStatus())){//查询未处理任务单
			form.setSort("addtime");
		}else if("34".equals(form.getStatus())){//查询已处理任务单
			form.setSort("dealdate");
		}
		
		List<Userdispatch> dispatchlist = userdispatchDao.findByList(form);
		
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
			
			//订单信息
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
			objectMap.put("lockcoreflag", userorder.getLockcoreflag());
			objectMap.put("lockcoreflagname", userorder.getLockcoreflagname());
			objectMap.put("productcolor", userorder.getProductcolor());
			objectMap.put("productcolorname", userorder.getProductcolorname());
			objectMap.put("modelcode", userorder.getModelcode());
			objectMap.put("modelname", userorder.getModelname());
			objectMap.put("discountgain", userorder.getDiscountgain());
			objectMap.put("fixedgain", userorder.getFixedgain());
			objectMap.put("managergain", userorder.getManagergain());
			objectMap.put("managercode", userorder.getManagercode());
			objectMaplist.add(objectMap);
			
		}
		responseMap.put("status", "1");//返回成功
		responseMap.put("result", "请求成功");
		responseMap.put("userdispatchlist", objectMaplist);
		
		return responseMap;
	}
	
	/**
	 * 工单处理初始化
	 */
	@RequestMapping(value="/saveDispatchdataInit")
	public String saveDispatchdataInit(Userdispatch form) {
		Userdispatch userdispatch = userdispatchDao.findByDispatchcode(form.getDispatchcode());
		if(userdispatch == null){
			userdispatch = new Userdispatch();
		}
		form.setUserdispatch(userdispatch);
		
		Userorder userorder = userorderDao.findByOrdercode(userdispatch.getOrdercode());
		form.setUserorder(userorder);
		
		return "mobilebusiness/dispatchinfo/saveDispatchdata";
	}
	
	/**
	 * 保存工单处理
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/saveDispatchdata")
	public Map<String, Object> saveDispatchdata(Userdispatch form) {
		
		//操作员
		Employee employee = (Employee)getSession().getAttribute("MobileOperator");

		return mobilebusinessService.saveDispatchdata(form, getRequest(), employee);
		
	}
	
	/**
	 * 查看工单详情
	 */
	@RequestMapping(value="/lookUserdispatchInfo")
	public String lookUserdispatchInfo(Userdispatch form) {
		Userdispatch userdispatch = userdispatchDao.findByDispatchcode(form.getDispatchcode());
		if(userdispatch == null){
			userdispatch = new Userdispatch();
		}
		form.setUserdispatch(userdispatch);
		
		Userorder userorder = userorderDao.findByOrdercode(userdispatch.getOrdercode());
		form.setUserorder(userorder);
		
		return "mobilebusiness/dispatchinfo/lookUserdispatchInfo";
	}
	
	/**
	 * 初始化工单安装图片修改
	 */
	@RequestMapping(value="/updateDispatchfileInit")
	public String updateDispatchfileInit(Userdispatchfile form) {
		
		Userdispatch userdispatch = userdispatchDao.findByDispatchcode(form.getDispatchcode());
		if(userdispatch == null){
			userdispatch = new Userdispatch();
		}
		form.setUserdispatch(userdispatch);
		return "mobilebusiness/dispatchinfo/updateUserdispatchfile";
	}
	
	/**
	 * 查询工单安装图片Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findUserdispatchfileListJSON")
	public Map<String, Object> findUserdispatchfileListJSON(Userdispatchfile form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		//封装List数据
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		List<Userdispatchfile> list = userdispatchfileDao.queryByList(form);
		for (Userdispatchfile userdispatchfile : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			//潜在订户信息
			objectMap.put("id", userdispatchfile.getId());
			objectMap.put("usercode", userdispatchfile.getUsercode());
			objectMap.put("ordercode", userdispatchfile.getOrdercode());
			objectMap.put("dispatchcode", userdispatchfile.getDispatchcode());
			objectMap.put("filename", userdispatchfile.getFilename());
			objectMap.put("preservefilename", userdispatchfile.getPreservefilename());
			objectMap.put("preserveurl", userdispatchfile.getPreserveurl());
			objectMap.put("addtime", StringUtils.isEmpty(userdispatchfile.getAddtime())?"":Tools.getStr(userdispatchfile.getAddtime()).substring(0, 19));
			objectMap.put("remark", userdispatchfile.getRemark());
			objectlist.add(objectMap);
		}
		
		result.put("userdispatchfileList", objectlist);
		return result;
	}
	
	/**
	 * 工单安装图片下载或者查看
	 */
	@RequestMapping(value = "/findUserdispatchfile")
	public String findUserdispatchfile(Userdispatchfile form,HttpServletResponse response){
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("status", "0");
		responseMap.put("result", "请求成功");
		
		//门锁图片
		Userdispatchfile userdispatchfile =  userdispatchfileDao.findById(form.getId());
		if(userdispatchfile == null){
			return null;
		}
		
        try {
			File excelTemplate = new File(userdispatchfile.getPreserveurl());
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
				response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(userdispatchfile.getFilename(), "UTF-8"));
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
	 * 单个工单安装图片删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/deleteUserdispatchfile")
	public Map<String,Object> deleteUserdispatchfile(Userdispatchfile form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Userdispatchfile userdispatchfile = userdispatchfileDao.findById(form.getId());
		
		 //删除服务器所在文件
		File tmpFile = new File(userdispatchfile.getPreserveurl());
		tmpFile.delete();
		
		//删除门锁
		userdispatchfileDao.delete(form.getId());
		
		//增加操作日记
		//操作员
		Employee mobileOperator = (Employee)getSession().getAttribute("MobileOperator");
		String content = "删除安装图片，订单编号为:"+userdispatchfile.getOrdercode() + ";工单编号为:" + userdispatchfile.getDispatchcode() + ";门锁文件名称为:" + userdispatchfile.getFilename();
		operatorlogService.saveOperatorlog(content, mobileOperator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
	/**
	 * 安装图片上传
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/saveUserdispatchfileInfo")
	public Map<String, Object> saveUserdispatchfileInfo(Userdispatch form){
		return mobilebusinessService.saveUserdispatchfileInfo(form, getRequest(), getSession());
	}
	
}
