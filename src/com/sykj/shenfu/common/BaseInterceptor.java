package com.sykj.shenfu.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 判断登录界面session失效
 */
public class BaseInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (request.getRequestURI() == null) {
			request.getRequestDispatcher("/shenfu/").forward(request, response);
			return false;
		}
		if (("/shenfu/").equals(request.getRequestURI())) {
			response.sendRedirect(request.getContextPath() + "/operator/initLogin");
			return false;
		}
		
		//移动端平台
		if (("/shenfu/mobilebusiness").equals(request.getRequestURI()) || ("/shenfu/mobilebusiness/").equals(request.getRequestURI())) {
			response.sendRedirect(request.getContextPath() + "/mobilebusiness/initLogin");
			return false;
		}
		
		if (!(request.getRequestURI().endsWith("/shenfu/operator/initLogin")
				|| request.getRequestURI().endsWith("/shenfu/operator/login")
				|| request.getRequestURI().endsWith("/shenfu/operator/logout")
				|| request.getRequestURI().endsWith("/shenfu/operator/changeLanguage")
				|| request.getRequestURI().endsWith("/shenfu/operator/noPermission")
				|| request.getRequestURI().endsWith("/shenfu/mobilebusiness/initLogin")
				|| request.getRequestURI().endsWith("/shenfu/mobilebusiness/login")
				|| request.getRequestURI().endsWith("/shenfu/mobilebusiness/logout")
				|| request.getRequestURI().endsWith("/shenfu/mobilebusiness/noPermission")
				|| request.getRequestURI().indexOf("/shenfu/mobilebusiness/savePaymentInfo") > -1 //订单支付
				|| request.getRequestURI().indexOf("/shenfu/mobilebusiness/savePaymentInfo_notify") > -1 //订单支付成功支付
				|| request.getRequestURI().indexOf("https://open.weixin.qq.com/") > -1 //请求微信接口
				|| request.getRequestURI().indexOf("shenfu/js") > -1
				|| request.getRequestURI().indexOf("shenfu/sale") > -1
				|| request.getRequestURI().indexOf("/shenfu/sale/savePaymentInfo_getOpenid") > -1 //订单支付
				|| request.getRequestURI().indexOf("/shenfu/sale/savePaymentInfo") > -1 //订单支付
				|| request.getRequestURI().indexOf("/shenfu/sale/savePaymentInfo_notify") > -1 //订单支付成功支付
				|| request.getRequestURI().indexOf("shenfu/style") > -1
				|| request.getRequestURI().indexOf("shenfu/images") > -1
				|| request.getRequestURI().indexOf("shenfu/shopping") > -1
				|| request.getRequestURI().indexOf("shenfu/httpForMps") > -1
				|| request.getRequestURI().indexOf("shenfu/httpForWechat") > -1
				|| request.getRequestURI().indexOf("shenfu/mobilebusiness/js") > -1
				
				|| request.getRequestURI().indexOf("material") > -1
				|| request.getRequestURI().indexOf("codearea") > -1)) {
			
			if(request.getRequestURI().indexOf("shenfu/mobilebusiness") > -1){ //是从移动平台登录进来的
				if (request.getSession().getAttribute("MobileOperator") == null) {
					response.sendRedirect(request.getContextPath()+"/mobilebusiness/noPermission"); 
					return false;
				}
			}else{
				if (request.getSession().getAttribute("Operator") == null) {
					response.sendRedirect(request.getContextPath()+"/operator/noPermission"); 
					return false;
				}
			}
		}
		return super.preHandle(request, response, handler);
	}
}
