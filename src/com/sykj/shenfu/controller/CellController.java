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

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.dao.IEmployeeDao;
import com.sykj.shenfu.dao.ICellDao;
import com.sykj.shenfu.po.Coderule;
import com.sykj.shenfu.po.Employee;
import com.sykj.shenfu.po.Operator;
import com.sykj.shenfu.po.Cell;
import com.sykj.shenfu.service.ICoderuleService;
import com.sykj.shenfu.service.IOperatorlogService;

import fr.opensagres.xdocreport.utils.StringUtils;

/**
 * 用户控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/cell")
@Transactional
public class CellController extends BaseController {
    private static final Logger log = Logger.getLogger("CellController");
	@Autowired
	private ICellDao cellDao;
	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private IOperatorlogService operatorlogService;
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private ICoderuleService coderuleService;
	/**
	 * 查询小区
	 */
	@RequestMapping(value = "/findByList")
	public String findByList(Cell form) {
		return "cell/findCellList";
	}
	
	/**
	 * 查询用户信息Json
	 */
	@ResponseBody
	@RequestMapping(value = "/findListJson")
	public Map<String, Object> findListJson(Cell form) {
		//封装JSon的Map
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<HashMap<String, Object>> objectlist = new ArrayList<HashMap<String, Object>>();
		Integer total = cellDao.findByCount(form);
		List<Cell> list = cellDao.findByList(form);
		for (Cell cell : list) {
			HashMap<String, Object> objectMap = new HashMap<String, Object>();
			//产品出入库信息
			objectMap.put("id", cell.getId());
			objectMap.put("cellcode", Tools.getStr(cell.getCellcode()));
			objectMap.put("cellname", Tools.getStr(cell.getCellname()));
			objectMap.put("address", Tools.getStr(cell.getAddress()));
			objectMap.put("opentime", StringUtils.isEmpty(cell.getOpentime())?"":Tools.getStr(cell.getOpentime()).substring(0, 10));
			objectMap.put("handtime", StringUtils.isEmpty(cell.getHandtime())?"":Tools.getStr(cell.getHandtime()).substring(0, 10));
			objectMap.put("safelevel", Tools.getStr(cell.getSafelevel()));
			objectMap.put("safelevelname", Tools.getStr(cell.getSafelevelname()));
			objectMap.put("developer", Tools.getStr(cell.getDeveloper()));
			objectMap.put("province", Tools.getStr(cell.getProvince()));
			objectMap.put("city", Tools.getStr(cell.getCity()));
			objectMap.put("area", Tools.getStr(cell.getArea()));
			objectMap.put("longitude", Tools.getStr(cell.getLongitude()));
			objectMap.put("latitude", Tools.getStr(cell.getLatitude()));
			objectMap.put("building", Tools.getStr(cell.getBuilding()));
			objectMap.put("floor", Tools.getStr(cell.getFloor()));
			objectMap.put("totalhouse", cell.getTotalhouse());
			objectMap.put("layout", Tools.getStr(cell.getLayout()));
			objectMap.put("extendflag", Tools.getStr(cell.getExtendflag()));
			objectMap.put("extendflagname", Tools.getStr(cell.getExtendflagname()));
			objectMap.put("userate", Tools.getStr(cell.getUserate()));
			objectMap.put("highlow", Tools.getStr(cell.getHighlow()));
			objectMap.put("property", Tools.getStr(cell.getProperty()));
			objectMap.put("doorinfo", Tools.getStr(cell.getDoorinfo()));
			objectMap.put("researchercode", Tools.getStr(cell.getResearchercode()));
			Employee researcher = null;
			if(StringUtils.isNotEmpty(cell.getResearchercode())){
				researcher = employeeDao.findByEmployeecodeStr(cell.getResearchercode());
				if(researcher != null){
					objectMap.put("researchername", Tools.getStr(researcher.getEmployeename()));
					objectMap.put("researcherphone", Tools.getStr(researcher.getPhone()));
				}
			}
			objectMap.put("advertisement", Tools.getStr(cell.getAdvertisement()));
			objectMap.put("allowstation", Tools.getStr(cell.getAllowstation()));
			objectMap.put("allowstationname", Tools.getStr(cell.getAllowstationname()));
			objectMap.put("remark", Tools.getStr(cell.getRemark()));
			objectlist.add(objectMap);
		}
		
		result.put("total", total);//页面总数
		result.put("rows", objectlist);
		return result;
	}

	/**
	 * 添加小区页面初始化
	 */
	@RequestMapping(value = "/addInit")
	public String addInit(Cell form) {
		return "cell/addCell";
	}

	/**
	 * 保存添加小区
	 */
	@RequestMapping(value = "/save")
	public String save(Cell form) {
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		if ("".equals(form.getCellname())) {
			form.setReturninfo("请输入小区名称");
			return addInit(form);
		}
		
		//获取小区编号
		String cellcode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_cellcode);
		form.setCellcode(cellcode);
		
		//默认未发布
		form.setExtendflag("0");
		
		cellDao.save(form);
		
		//增加操作日记
		String content = "添加小区，小区名称:"+form.getCellname();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("保存成功");
		
		return addInit(form);
	}

	/**
	 * 编辑小区权限初始化页面
	 */
	@RequestMapping(value = "/updateInit")
	public String updateInit(Cell form) {
		form.setCell(cellDao.findById(form.getId()));
		return "cell/updateCell";
	}

	/**
	 * 保存编辑后小区权限
	 */
	@RequestMapping(value = "/update")
	public String update(Cell form) {
		
		Operator operator = (Operator)getSession().getAttribute("Operator");
		
		if ("".equals(Tools.getStr(form.getCellname()))) {
			form.setReturninfo("小区名称不能为空");
			return updateInit(form);
		} 
		
		cellDao.update(form);
		//增加操作日记
		String content = "修改小区信息，小区名称:"+form.getCellname();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		form.setReturninfo("修改成功");
		return updateInit(form);
	}

	/**
	 * 单个删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/delete")
	public Map<String,Object> delete(Cell form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		Cell cell = cellDao.findById(form.getId());
		
		//删除小区
		cellDao.delete(form.getId());
		
		//增加操作日记
		Operator operator = (Operator)getSession().getAttribute("Operator");
		String content = "删除小区，小区名称:"+cell.getCellname();
		operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "删除成功");
		
		return responseJson;
	}
	
	/**
	 * 批量删除
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/deleteBatch")
	public Map<String,Object> deleteBatch(Cell form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		String[] idArray = Tools.getStr(getRequest().getParameter("ids")).split(",");
		if (idArray == null || idArray.length < 1) {
			responseJson.put("flag", "0");//发布失败
			responseJson.put("msg", "请选择需要删除的小区!");
			return responseJson;
		} else {
			for (int i = 0; i < idArray.length; i++) {
				//Cell cell = cellDao.findById(Integer.parseInt(idArray[i]));
				//删除小区
				cellDao.delete(Integer.parseInt(idArray[i]));
				
				//增加操作日记
				//Operator operator = (Operator)getSession().getAttribute("Operator");
				//String content = "删除小区，小区名称:"+cell.getCellname();
				//operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
			}
		}
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "批量删除成功");
		
		return responseJson;
	}
	
	/**
	 * 查询小区--小区发布
	 */
	@RequestMapping(value = "/findCellListForExtend")
	public String findCellListForExtend(Cell form) {
		return "cell/findCellListForExtend";
	}
	
	
	/**
	 * 小区发布
	 */
	@ResponseBody //此标志就是返回jesion数据给页面的标志
	@RequestMapping(value = "/saveExtendCell")
	public Map<String,Object> saveExtendCell(Cell form) {
		//封装返回给页面的json对象
		HashMap<String,Object> responseJson = new HashMap<String,Object>();
		
		String[] idArray = Tools.getStr(getRequest().getParameter("ids")).split(",");
		if (idArray == null || idArray.length < 1) {
			responseJson.put("flag", "0");//发布失败
			responseJson.put("msg", "请选择需要发布的小区!");
			return responseJson;
		} else {
			for (int i = 0; i < idArray.length; i++) {
				Cell cell = cellDao.findById(Integer.parseInt(idArray[i]));
				cell.setExtendflag("1");//已发布
				//修改小区
				cellDao.update(cell);
				
				//增加操作日记
				Operator operator = (Operator)getSession().getAttribute("Operator");
				String content = "小区发布，小区名称:"+cell.getCellname();
				operatorlogService.saveOperatorlog(content, operator.getEmployeecode());
			}
		}
		
		responseJson.put("flag", "1");//删除成功
		responseJson.put("msg", "发布成功");
		
		return responseJson;
	}
	
	/**
	 * 添加小区文件导入初始化
	 */
	@RequestMapping(value = "/addByImportfileInit")
	public String addByImportfileInit(Cell form) {
		return "cell/addCellByImportfile";
	}
	
	/**
	 * 导入文件信息
	 */
	@RequestMapping(value = "/saveByImportFile")
	public String saveByImportFile(Cell form, @RequestParam("uploadfile") MultipartFile file) throws IOException {
		// XML上传服务器流文件
		String filename = file.getOriginalFilename();
		if (filename == null || "".equals(filename)) {
			form.setReturninfo("请选择文件");
			return addByImportfileInit(form);
		}
		// 文件类型
		String[] strArray = filename.split("[.]");
		String type = strArray[strArray.length - 1].toLowerCase();
		if (!"xls".equals(type) && !"XLS".equals(type) && !"xlsx".equals(type) && !"XLSX".equals(type)) {
			form.setReturninfo("文件类型错误");
			return addByImportfileInit(form);
		}
		String import_template_path = servletContext.getInitParameter("import_template_path");
		String fullpath = servletContext.getRealPath(File.separator) + import_template_path + File.separatorChar + Tools.getNowRandom() + filename;
		File tmpFile = new File(fullpath);
		file.transferTo(tmpFile);
		
		try {
			FileInputStream fis = new FileInputStream(tmpFile);
			ArrayList<Cell> celllist = new ArrayList<Cell>();
			if("xls".equals(type) || "XLS".equals(type)){
				HSSFWorkbook excel = new HSSFWorkbook(fis);
				HSSFSheet sheet0 = excel.getSheetAt(0);
				for (Row row : sheet0) {
					if (row.getRowNum() == 0) {
						continue; // 第一排表头跳过不读
					}
					Cell cellRow = fillCellData(row);
					/*** 提示用户读取出错地点 ***/
					String returnInfo = cellRow.getReturninfo();
					
					if (returnInfo != null && returnInfo != "") {
						continue;
					} else {
						//获取小区编号
						String cellcode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_cellcode);
						cellRow.setCellcode(cellcode);
						//默认未发布
						cellRow.setExtendflag("0");
						celllist.add(cellRow);
						
						//每1000个保存一次数据库
						if(celllist.size() >=500){
							cellDao.saveBatch(celllist);
							celllist.clear();
						}
						
					}
				}
			}else if("xlsx".equals(type)||"XLSX".equals(type)){
				XSSFWorkbook excel = new XSSFWorkbook(fis);
				XSSFSheet sheet0 = excel.getSheetAt(0);
				for (Row row : sheet0) {
					if (row.getRowNum() == 0) {
						continue; // 第一排表头跳过不读
					}
					Cell cellRow = fillCellData(row);
					/*** 提示用户读取出错地点 ***/
					String returnInfo = cellRow.getReturninfo();
					
					if (returnInfo != null && returnInfo != "") {
						continue;
					} else {
						//获取小区编号
						String cellcode = coderuleService.getSystemcodenoByCodetypeStr(Coderule.CODETYPE_cellcode);
						cellRow.setCellcode(cellcode);
						//默认未发布
						cellRow.setExtendflag("0");
						celllist.add(cellRow);
						
						//每1000个保存一次数据库
						if(celllist.size() >=500){
							cellDao.saveBatch(celllist);
							celllist.clear();
						}
						
					}
				}
			}
			
			//批量保存小区信息
			if(celllist.size() > 0){
				cellDao.saveBatch(celllist);
			}
			
			form.setReturninfo("导入成功");
		} catch (Exception e) {
			e.printStackTrace();
			form.setReturninfo("导入失败");
			tmpFile.delete();
			return addByImportfileInit(form);
		}
		tmpFile.delete();
		return addByImportfileInit(form);
	}
	
	/**
	 * 下载导入模板
	 */
	@RequestMapping(value = "/downloadTemplate")
	public String downloadTemplate(Cell form, HttpServletResponse response) {
		try {
			String import_template_path = servletContext.getInitParameter("import_template_path");
			String folderpath = servletContext.getRealPath(File.separator) + import_template_path + File.separatorChar + "cell_info_template.xlsx";
			File excelTemplate = new File(folderpath);
			response.reset();
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Length", "" + excelTemplate.length()); // 文件大小
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("cell_info_template.xlsx", "UTF-8"));
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
	 * 封装EXCEL中的Product信息
	 */
	private Cell fillCellData(Row row) {
		Cell cell = new Cell();
		//获取小区名称
		String cellname = Tools.cellValue(row.getCell(0));
		if (StringUtils.isEmpty(cellname)) {
			cell.setReturninfo("小区名称为空");
			return cell;
		}else{
			cell.setCellname(cellname);
		}
		//获取楼栋
		cell.setBuilding(Tools.cellValue(row.getCell(2)));
		//获取楼层
		cell.setFloor(Tools.cellValue(row.getCell(3)));
		//获取总户数
		if(StringUtils.isNotEmpty(Tools.cellValue(row.getCell(4)))){
			cell.setTotalhouse(Integer.parseInt(Tools.cellValue(row.getCell(4))));
		}else{
			cell.setTotalhouse(0);
		}
		//获取户型
		cell.setLayout(Tools.cellValue(row.getCell(5)));
		//获取地址
		cell.setAddress(Tools.cellValue(row.getCell(6)));
		//获取安防级别
		cell.setSafelevel(Tools.cellValue(row.getCell(7)));
		//交房时间
		String handtime = Tools.cellValue(row.getCell(8));
		if(StringUtils.isNotEmpty(handtime)){
			cell.setHandtime(handtime);
		}
		
		return cell;
	}
}
