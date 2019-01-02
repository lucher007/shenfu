package com.sykj.shenfu.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;

import com.sykj.shenfu.common.uitl.Constrants;


public class BaseController {
	protected String message = "";
	protected boolean result = false;
	protected boolean errorFlags = false;
	
	public HttpServletRequest getRequest(){
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	public HttpSession getSession(){
		getRequest().getSession().setMaxInactiveInterval(14400);//单位秒，设置Session连接时间
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
	}
	
	public String getMessage(String name){
		RequestContext requestContext = new RequestContext(getRequest());
		return requestContext.getMessage(name);
	}
	
	/**
	 * 
	 * @Function: backView
	 * @Description: 返回结果类型
	 * 
	 * @param:参数描述
	 * @return：返回结果描述
	 * @throws：异常描述
	 * 
	 * @version: v1.0.0
	 * @author: master
	 * @date: 2015-5-13 下午1:31:44
	 * 
	 *        Modification History: Date Author Version Description
	 *        ---------------------------------------------------------*
	 *        2014-1-14 xuyiping v1.0.0 修改原因
	 */
	public ModelAndView backView(String view, Map map) {
		ModelAndView v = new ModelAndView(view);
		v.addAllObjects(map);
		return v;
	}
	
	protected ModelAndView reView(String string, ModelAndView list) {
		list.addObject(Constrants.MESSAGE_TIP_FLAGS, true);
		list.addObject(Constrants.MESSAGE_TIP, message);
		return list;
	}
	
	public ModelAndView messageViewNew(String message) {
		return new ModelAndView("error/message").addObject(Constrants.MESSAGE_TIP, message);
	}

	/**
	 * 
	 * @Function: messageView
	 * @Description: 信息提示视图
	 * 
	 * @param:参数描述
	 * @return：返回结果描述
	 * @throws：异常描述
	 * 
	 * @version: v1.0.0
	 * @author: master
	 * @date: 2015-5-15 下午2:54:29
	 * 
	 *        Modification History: Date Author Version Description
	 *        ---------------------------------------------------------*
	 */
	public String messageView(String message) {
		getRequest().setAttribute(Constrants.MESSAGE_TIP, message);
		return "error/message";
	}
	
	/**
	 * 
	 * @Function: successView
	 * @Description: 返回信息提示视图
	 * 
	 * @param:参数描述
	 * @return：返回结果描述
	 * @throws：异常描述
	 * 
	 * @version: v1.0.0
	 * @author: master
	 * @date: 2015-5-15 下午2:54:29
	 * 
	 *        Modification History: Date Author Version Description
	 *        ---------------------------------------------------------*
	 */
	public ModelAndView returnView(String message,String view) {
		return new ModelAndView(view).addObject(Constrants.MESSAGE_TIP_FLAGS, true)
				.addObject(Constrants.MESSAGE_TIP, message);
	}
}
