package com.sykj.shenfu.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sykj.shenfu.common.ConfigUtil;
import com.sykj.shenfu.common.PrimaryGenerater;
import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.contract.GenerateContractDocx;
import com.sykj.shenfu.dao.IUsercontractDao;
import com.sykj.shenfu.dao.IUserorderDao;
import com.sykj.shenfu.dao.IUserproductDao;
import com.sykj.shenfu.po.Coderule;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Usercontract;
import com.sykj.shenfu.po.Userorder;
import com.sykj.shenfu.po.Userproduct;
import com.sykj.shenfu.service.ICoderuleService;
import com.sykj.shenfu.service.IOperatorService;
import com.sykj.shenfu.service.IOperatorlogService;
import com.sykj.shenfu.service.ISystemparaService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/usercontract")
@Transactional
public class UsercontractController extends BaseController {
    
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private IUsercontractDao usercontractDao;
	@Autowired
	private IOperatorService operatorService;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private IUserorderDao userorderDao;
	@Autowired
	private IUserproductDao userproductDao;
	@Autowired
	private ICoderuleService coderuleService;
	@Autowired
	private ISystemparaService systemparaService;
    
	
	/*
     * 特殊的instance变量
     * 注：零长度的byte数组对象创建起来将比任何对象都经济――查看编译后的字节码：
     * 生成零长度的byte[]对象只需3条操作码，而Object lock = new Object()则需要7行操作码。
     */
    private static byte[] lock = new byte[0];   
	
	/**
	 * 查询订户
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Usercontract form) {
		return "usercontract/findUsercontractList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Usercontract form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		//封装List数据
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		Integer total = usercontractDao.findByCount(form);
		List<Usercontract> list = usercontractDao.findByList(form);
		for (Usercontract usercontract : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			//潜在订户信息
			objectMap.put("id", usercontract.getId());
			objectMap.put("usercode", usercontract.getUsercode());
			objectMap.put("ordercode", usercontract.getOrdercode());
			objectMap.put("contractcode", usercontract.getContractcode());
			objectMap.put("filename", usercontract.getFilename());
			objectMap.put("preservefilename", usercontract.getPreservefilename());
			objectMap.put("preserveurl", usercontract.getPreserveurl());
			objectMap.put("addtime", usercontract.getAddtime());
			objectMap.put("remark", usercontract.getRemark());
			
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
	public String addInit(Usercontract form) {
		return "usercontract/addUsercontract";
	}

	/**
	 * 保存添加订户
	 */
	@RequestMapping(value = "/save")
	public String save(Usercontract form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		//默认为未处理
		//form.setStatus("0");
		//form.setAddtime(Tools.getCurrentTime());
		usercontractDao.save(form);
		
		//增加操作日记
		String content = "添加合同，订单号为:"+form.getOrdercode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("保存成功");
		
		return addInit(form);
	}

	/**
	 * 编辑订户权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Usercontract form) {
		form.setUsercontract(usercontractDao.findById(form.getId()));
		return "usercontract/updateUsercontract";
	}

	/**
	 * 保存编辑后订户权限
	 */
	@RequestMapping(value = "/update")
	public String update(Usercontract form) {
		
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		usercontractDao.update(form);
		//增加操作日记
		String content = "修改合同，订单号为:"+form.getOrdercode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}

	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Usercontract form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Usercontract usercontract = usercontractDao.findById(form.getId());
		
		 //删除服务器所在文件
		File tmpFile = new File(usercontract.getPreserveurl());
		tmpFile.delete();
		
		//删除合同
		usercontractDao.delete(form.getId());
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "删除合同，订单号为:"+usercontract.getOrdercode() + ";合同文件名称为:" + usercontract.getFilename();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
	
	/**
	 * 合同上传
	 */
	@RequestMapping(value = "/saveUsercontractInfo")
	public String saveUsercontractInfo(@RequestParam("uploadfile") MultipartFile file, Usercontract form,
			     HttpServletResponse response) throws IllegalStateException, IOException {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		try {
			if (!file.isEmpty()) {
				
				//获取合同号
				String contractcode = "HT" + coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_contractcode);
				
				Userorder userorder = userorderDao.findByOrdercode(form.getOrdercode());
				String filename = file.getOriginalFilename();
				String[] strArray = filename.split("[.]");
				String filetype = strArray[strArray.length - 1];
				
				String usercontract_path = systemparaService.findSystemParaByCodeStr("upload_file_path");
				
				//String usercontract_path = ConfigUtil.getConfigFilePath("usercontract_path", "system.properties");
				//String upload_extend_path = systemparaDao.findByCodeStr("upload_file_path").getValue();
				String folderpath = usercontract_path + File.separatorChar + "usercontract" + File.separatorChar + userorder.getOrdercode();
				File folder = new File(folderpath);
				if (!folder.exists()) {
					folder.mkdirs();
				}
				String preservefilename = contractcode + "_" + Tools.getNowRandom() +"." +filetype;
				String preservepath = folderpath + File.separatorChar + preservefilename;
				File savefile = new File(preservepath);
				file.transferTo(savefile);
				form.setFilename(filename);
				form.setPreservefilename(preservefilename);
				form.setPreserveurl(preservepath);
				form.setUsercode(userorder.getUsercode());
				form.setOrdercode(userorder.getOrdercode());
				form.setContractcode(preservefilename);
				form.setAddtime(Tools.getCurrentTime());
				usercontractDao.save(form);
			}
		} catch (Exception e) {
			response.setStatus(500);
			return null;
		}
		return null;
	}
	
	/**
	 * 合同内容下载或者查看
	 */
	@RequestMapping(value = "/lookUsercontract")
	public String lookUsercontract(Usercontract form,HttpServletResponse response){
		Usercontract usercontract =  usercontractDao.findById(form.getId());
        
        try {
			File excelTemplate = new File(usercontract.getPreserveurl());
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
				response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(usercontract.getFilename(), "UTF-8"));
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
	 * 生成合同
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/saveCreateContract")
	public Map<String,Object> saveCreateContract(Usercontract form) {
		
		
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		//加锁
		synchronized(lock) {
		
			//查询订单信息
			Userorder userorder = userorderDao.findByOrdercode(form.getOrdercode());
			//删除以前的旧合同
			List<Usercontract> usercontractlist = usercontractDao.queryByList(form);
			for (Usercontract usercontract : usercontractlist) {
				//删除数据库合同信息
				usercontractDao.delete(usercontract.getId());
				//删除服务器所在文件
				File tmpFile = new File(usercontract.getPreserveurl());
				tmpFile.delete();
			}
			
			
			//合同编号
			String contractcode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_contractcode);
			
			//合同模板存放地址
			String import_template_path = servletContext.getInitParameter("import_template_path");
			String folder_template_path = servletContext.getRealPath(File.separator) + import_template_path + File.separatorChar + "contract_tmp.docx";
			
			//生成合同存放地址
			String usercontract_path = ConfigUtil.getConfigFilePath("usercontract_path", "system.properties");
			String preservefolderpath = usercontract_path + File.separatorChar + "usercontract" + File.separatorChar + "ordercode_" + userorder.getOrdercode();
			File folder = new File(preservefolderpath);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			//合同文件名称
			String preservefilename = contractcode + ".docx";
			//合同存放路径
			String preservepath = preservefolderpath + File.separatorChar + preservefilename;
			
			//封装订单的产品
			Userproduct userproductForm = new Userproduct();
			userproductForm.setOrdercode(form.getOrdercode());
			List<Userproduct> userproductList = userproductDao.queryByList(userproductForm);
			userorder.setUserproductList(userproductList);
			
			//生成合同
			GenerateContractDocx.generateDocx(folder_template_path, preservepath, userorder);
			
			//增加操作日记
			Operator operator = (Operator)getSession().getAttribute("Operator");
			String content = "生成合同，订单号为:"+userorder.getOrdercode() + ";合同编号为:" + contractcode;
			operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
			
			//保存合同入库
			form.setFilename(preservefilename);
			form.setPreservefilename(preservefilename);
			form.setPreserveurl(preservepath);
			form.setUsercode(userorder.getUsercode());
			form.setOrdercode(userorder.getOrdercode());
			form.setAddtime(Tools.getCurrentTime());
			form.setContractcode(contractcode);
			usercontractDao.save(form);
		}
		responseJson.put("flag", "1");//生成成功
		responseJson.put("msg", "生成合同成功");
		
		return responseJson;
	}
}
