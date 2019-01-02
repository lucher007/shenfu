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

import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.IUserdispatchDao;
import com.sykj.shenfu.dao.IUserdispatchfileDao;
import com.sykj.shenfu.dao.IUserproductDao;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Userdispatch;
import com.sykj.shenfu.po.Userdispatchfile;
import com.sykj.shenfu.service.ICoderuleService;
import com.sykj.shenfu.service.IOperatorService;
import com.sykj.shenfu.service.IOperatorlogService;
import com.sykj.shenfu.service.ISystemparaService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/userdispatchfile")
@Transactional
public class UserdispatchfileController extends BaseController {
    
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private IUserdispatchfileDao userdispatchfileDao;
	@Autowired
	private IOperatorService operatorService;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private IUserdispatchDao dispatchDao;
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
	public String findByList(Userdispatchfile form) {
		return "dispatchfile/findUserdispatchfileList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Userdispatchfile form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		//封装List数据
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		Integer total = userdispatchfileDao.findByCount(form);
		List<Userdispatchfile> list = userdispatchfileDao.findByList(form);
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
			objectMap.put("addtime", userdispatchfile.getAddtime());
			objectMap.put("remark", userdispatchfile.getRemark());
			
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
	public String addInit(Userdispatchfile form) {
		return "dispatchfile/addUserdispatchfile";
	}

	/**
	 * 保存添加订户
	 */
	@RequestMapping(value = "/save")
	public String save(Userdispatchfile form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		//默认为未处理
		//form.setStatus("0");
		//form.setAddtime(Tools.getCurrentTime());
		userdispatchfileDao.save(form);
		
		//增加操作日记
		String content = "添加工单安装图片，工单号为:"+form.getDispatchcode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("保存成功");
		
		return addInit(form);
	}

	/**
	 * 编辑订户权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Userdispatchfile form) {
		form.setUserdispatchfile(userdispatchfileDao.findById(form.getId()));
		return "dispatchfile/updateUserdispatchfile";
	}

	/**
	 * 保存编辑后订户权限
	 */
	@RequestMapping(value = "/update")
	public String update(Userdispatchfile form) {
		
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		userdispatchfileDao.update(form);
		//增加操作日记
		String content = "修改工单安装图片，工单号为:"+form.getDispatchcode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}

	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Userdispatchfile form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Userdispatchfile userdispatchfile = userdispatchfileDao.findById(form.getId());
		
		 //删除服务器所在文件
		File tmpFile = new File(userdispatchfile.getPreserveurl());
		tmpFile.delete();
		
		//删除合同
		userdispatchfileDao.delete(form.getId());
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "删除工单安装图片，工单号为:"+userdispatchfile.getOrdercode() + ";合同文件名称为:" + userdispatchfile.getFilename();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
	
	/**
	 * 合同上传
	 */
	@RequestMapping(value = "/saveUserdispatchfileInfo")
	public String saveUserdispatchfileInfo(@RequestParam("uploadfile") MultipartFile file, Userdispatchfile form,
			     HttpServletResponse response) throws IllegalStateException, IOException {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		try {
			if (!file.isEmpty()) {
				Userdispatch userdispatch = dispatchDao.findByDispatchcode(form.getDispatchcode());
				String filename = file.getOriginalFilename();
				String[] strArray = filename.split("[.]");
				String filetype = strArray[strArray.length - 1];
				
				String userdispatchfile_path = systemparaService.findSystemParaByCodeStr("upload_file_path");
				
				//String userdispatchfile_path = ConfigUtil.getConfigFilePath("userdispatchfile_path", "system.properties");
				//String upload_extend_path = systemparaDao.findByCodeStr("upload_file_path").getValue();
				String folderpath = userdispatchfile_path + File.separatorChar + "userdispatch" + File.separatorChar + userdispatch.getOrdercode()+ File.separatorChar + userdispatch.getDispatchcode() ;
				File folder = new File(folderpath);
				if (!folder.exists()) {
					folder.mkdirs();
				}
				String preservefilename = Tools.getNowRandom() + "_" + filename;
				String preservepath = folderpath + File.separatorChar + preservefilename;
				File savefile = new File(preservepath);
				file.transferTo(savefile);
				form.setFilename(filename);
				form.setPreservefilename(preservefilename);
				form.setPreserveurl(preservepath);
				form.setUsercode(userdispatch.getUsercode());
				form.setOrdercode(userdispatch.getOrdercode());
				form.setDispatchcode(userdispatch.getDispatchcode());
				form.setAddtime(Tools.getCurrentTime());
				userdispatchfileDao.save(form);
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
	@RequestMapping(value = "/lookUserdispatchfile")
	public String lookUserdispatchfile(Userdispatchfile form,HttpServletResponse response){
		Userdispatchfile userdispatchfile =  userdispatchfileDao.findById(form.getId());
        
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
	
}
