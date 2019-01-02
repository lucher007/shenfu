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
import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.IUserhanddrawingDao;
import com.sykj.shenfu.dao.IUsertaskDao;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Userhanddrawing;
import com.sykj.shenfu.po.Usertask;
import com.sykj.shenfu.service.IOperatorlogService;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/userhanddrawing")
@Transactional
public class UserhanddrawingController extends BaseController {

	@Autowired
	private IUserhanddrawingDao handdrawingDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private IUsertaskDao taskDao;

	/**
	 * 查询
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Userhanddrawing form) {
		return "handdrawing/findUserhanddrawingList";
	}
	
	/**
	 * 查询信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Userhanddrawing form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		//封装List数据
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		Integer total = handdrawingDao.findByCount(form);
		List<Userhanddrawing> list = handdrawingDao.findByList(form);
		for (Userhanddrawing handdrawing : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			//潜在订户信息
			objectMap.put("id", handdrawing.getId());
			objectMap.put("usercode", handdrawing.getUsercode());
			objectMap.put("taskcode", handdrawing.getTaskcode());
			objectMap.put("filename", handdrawing.getFilename());
			objectMap.put("preservefilename", handdrawing.getPreservefilename());
			objectMap.put("preserveurl", handdrawing.getPreserveurl());
			objectMap.put("addtime", handdrawing.getAddtime());
			objectMap.put("remark", handdrawing.getRemark());
			
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
	public String addInit(Userhanddrawing form) {
		return "handdrawing/addUserhanddrawing";
	}

	/**
	 * 保存添加订户
	 */
	@RequestMapping(value = "/save")
	public String save(Userhanddrawing form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		//默认为未处理
		//form.setStatus("0");
		//form.setAddtime(Tools.getCurrentTime());
		handdrawingDao.save(form);
		
		//增加操作日记
		String content = "添加手绘图，任务单号为:"+form.getTaskcode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("保存成功");
		
		return addInit(form);
	}

	/**
	 * 编辑订户权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Userhanddrawing form) {
		form.setUserhanddrawing(handdrawingDao.findById(form.getId()));
		return "handdrawing/updateUserhanddrawing";
	}

	/**
	 * 保存编辑后订户权限
	 */
	@RequestMapping(value = "/update")
	public String update(Userhanddrawing form) {
		
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		handdrawingDao.update(form);
		//增加操作日记
		String content = "修改手绘图，任务单号为:"+form.getTaskcode();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}

	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Userhanddrawing form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Userhanddrawing handdrawing = handdrawingDao.findById(form.getId());
		
		//删除订户
		handdrawingDao.delete(form.getId());
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "删除手绘图，任务单号为:"+handdrawing.getTaskcode() + ";合同文件名称为:" + handdrawing.getFilename();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
	
	/**
	 * 合同上传
	 */
	@RequestMapping(value = "/saveUserhanddrawingInfo")
	public String saveUserhanddrawingInfo(@RequestParam("uploadfile") MultipartFile file, Userhanddrawing form,
			     HttpServletResponse response) throws IllegalStateException, IOException {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		try {
			if (!file.isEmpty()) {
				Usertask task = taskDao.findByTaskcodeStr(form.getTaskcode());
				String filename = file.getOriginalFilename();
				String[] strArray = filename.split("[.]");
				String filetype = strArray[strArray.length - 1];
				String handdrawing_path = ConfigUtil.getConfigFilePath("handdrawing_path", "system.properties");
				//String upload_extend_path = systemparaDao.findByCodeStr("upload_file_path").getValue();
				String folderpath = handdrawing_path + File.separatorChar + "handdrawing" + File.separatorChar + "taskcode_" + task.getTaskcode();
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
				form.setUsercode(task.getUsercode());
				form.setTaskcode(task.getTaskcode());
				form.setAddtime(Tools.getCurrentTime());
				handdrawingDao.save(form);
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
	@RequestMapping(value = "/lookUserhanddrawing")
	public String lookUserhanddrawing(Userhanddrawing form,HttpServletResponse response){
		Userhanddrawing handdrawing =  handdrawingDao.findById(form.getId());
        
        try {
			File excelTemplate = new File(handdrawing.getPreserveurl());
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
				response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(handdrawing.getFilename(), "UTF-8"));
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
