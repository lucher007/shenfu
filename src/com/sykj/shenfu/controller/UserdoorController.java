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
import com.sykj.shenfu.dao.IUserDao;
import com.sykj.shenfu.dao.IUserdoorDao;
import com.sykj.shenfu.dao.IUserdoordataDao;
import com.sykj.shenfu.dao.IUserorderDao;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Userdoor;
import com.sykj.shenfu.po.Userdoordata;
import com.sykj.shenfu.po.Userorder;
import com.sykj.shenfu.service.IOperatorlogService;
import com.sykj.shenfu.service.ISystemparaService;

import fr.opensagres.xdocreport.core.utils.StringUtils;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/userdoor")
@Transactional
public class UserdoorController extends BaseController {
    
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private IUserdoorDao userdoorDao;
	@Autowired
	private IUserdoordataDao userdoordataDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IUserorderDao userorderDao;
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
	public String findByList(Userdoor form) {
		Userorder userorder = userorderDao.findByOrdercode(form.getOrdercode());
		if(userorder == null){
			userorder = new Userorder();
		}
		
		Userdoordata userdoordata = userdoordataDao.findByOrdercode(form.getOrdercode());
		if(userdoordata == null){
			userdoordata = new Userdoordata();
		}
		
		form.setUserorder(userorder);
		form.setUserdoordata(userdoordata);
		
		return "userdoor/findUserdoorList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Userdoor form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		//封装List数据
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		Integer total = userdoorDao.findByCount(form);
		List<Userdoor> list = userdoorDao.findByList(form);
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
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}

	/**
	 * 添加订户页面初始化
	 */
	@RequestMapping(value = "/addInit")
	public String addInit(Userdoor form) {
		return "userdoor/addUserdoor";
	}

	/**
	 * 保存添加订户
	 */
	@RequestMapping(value = "/save")
	public String save(Userdoor form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		//默认为未处理
		//form.setStatus("0");
		//form.setAddtime(Tools.getCurrentTime());
		userdoorDao.save(form);
		
		//增加操作日记
		String content = "添加门锁，客户编码为:"+form.getUsercode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("保存成功");
		
		return addInit(form);
	}

	/**
	 * 编辑订户权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Userdoor form) {
		form.setUserdoor(userdoorDao.findById(form.getId()));
		return "userdoor/updateUserdoor";
	}

	/**
	 * 保存编辑后订户权限
	 */
	@RequestMapping(value = "/update")
	public String update(Userdoor form) {
		
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		userdoorDao.update(form);
		//增加操作日记
		String content = "修改门锁，客户编号为:"+form.getUserdoor();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}

	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Userdoor form) {
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
	@RequestMapping(value = "/saveUserdoorInfo")
	public String saveUserdoorInfo(@RequestParam("uploadfile") MultipartFile file, Userdoor form,
			     HttpServletResponse response) throws IllegalStateException, IOException {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		try {
			if (!file.isEmpty()) {
				
				Userorder userorder = userorderDao.findByOrdercode(form.getOrdercode());
				if(userorder == null){
					userorder = new Userorder();
				}
				String filename = file.getOriginalFilename();
				String[] strArray = filename.split("[.]");
				String filetype = strArray[strArray.length - 1];
				
				String userdoor_path = systemparaService.findSystemParaByCodeStr("upload_file_path");
				
				//String userdoor_path = ConfigUtil.getConfigFilePath("userdoor_path", "system.properties");
				//String upload_extend_path = systemparaDao.findByCodeStr("upload_file_path").getValue();
				String folderpath = userdoor_path + File.separatorChar + "userdoor" + File.separatorChar + userorder.getUsercode() + File.separatorChar + userorder.getOrdercode();
				File folder = new File(folderpath);
				if (!folder.exists()) {
					folder.mkdirs();
				}
				String preservefilename = Tools.getNowRandom() + "." + filetype;
				String preservepath = folderpath + File.separatorChar + preservefilename;
				File savefile = new File(preservepath);
				file.transferTo(savefile);
				form.setFilename(filename);
				form.setPreservefilename(preservefilename);
				form.setPreserveurl(preservepath);
				form.setUsercode(userorder.getUsercode());
				form.setOrdercode(userorder.getOrdercode());
				form.setAddtime(Tools.getCurrentTime());
				userdoorDao.save(form);
			}
		} catch (Exception e) {
			response.setStatus(500);
			return null;
		}
		return null;
	}
	
	/**
	 * 门锁内容下载或者查看
	 */
	@RequestMapping(value = "/lookUserdoor")
	public String lookUserdoor(Userdoor form,HttpServletResponse response){
		Userdoor userdoor =  userdoorDao.findById(form.getId());
        
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
	 * 保存订单门锁数据
	 */
	@RequestMapping(value = "/saveUserdoordata")
	public String saveUserdoordata(Userdoor form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");

		Userdoordata userdoordata = userdoordataDao.findByOrdercode(form.getOrdercode());
		if(userdoordata == null){//插入数据
			userdoordata = new Userdoordata();
			userdoordata.setUsercode(form.getUsercode());
			userdoordata.setOrdercode(form.getOrdercode());
			userdoordata.setLocklength(Tools.getStr(getRequest().getParameter("locklength")));
			userdoordata.setLockwidth(Tools.getStr(getRequest().getParameter("lockwidth")));
			userdoordata.setAddercode(operator.getEmployeecode());
			userdoordata.setAddtime(Tools.getCurrentTime());
			userdoordataDao.save(userdoordata);
		}else{ //修改门锁数据
			userdoordata.setLocklength(Tools.getStr(getRequest().getParameter("locklength")));
			userdoordata.setLockwidth(Tools.getStr(getRequest().getParameter("lockwidth")));
			userdoordataDao.update(userdoordata);
		}
		
		//增加操作日记
		String content = "修改门锁数据，订单编号为:"+form.getOrdercode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("操作成功");
		
		return findByList(form);
	}
	
}
