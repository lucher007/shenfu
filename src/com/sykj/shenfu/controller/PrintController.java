package com.sykj.shenfu.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 下载打印插件
 */
@Controller
@Scope("prototype")
@RequestMapping("/print")
@Transactional
public class PrintController extends BaseController {

	@Autowired
	private ServletContext servletContext;

	/**
	 * 打印插件传输
	 */
	@RequestMapping(value = "/printPluginInstall")
	public String printPluginInstall(HttpServletResponse response, HttpServletRequest request) {
		String plugin_version = request.getParameter("plugin_version");
		String plugin_folder_path = servletContext.getRealPath(File.separator) + "js" + File.separatorChar + "lodop" + File.separatorChar;
		String plugin_file_path = plugin_folder_path + plugin_version;
		try {
			File plugin = new File(plugin_file_path);
			response.reset();
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Length", "" + plugin.length()); // 文件大小
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("printPlugin.exe", "UTF-8"));
			FileInputStream fis = new FileInputStream(plugin);
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
