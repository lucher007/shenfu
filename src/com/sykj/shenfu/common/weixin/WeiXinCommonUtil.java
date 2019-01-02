package com.sykj.shenfu.common.weixin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jdom.JDOMException;
import org.json.JSONException;
import org.json.JSONObject;

public class WeiXinCommonUtil {
	public static String weixinPay(String orderNumber,String payitemname, String clientIP, int totalFee,String payType,String url,String attach) {
		SortedMap<String, Object> parameters = new TreeMap<String, Object>();
		parameters.put("appid", WeiXinConfig.appid);//应用APPID
		parameters.put("attach", attach); // 附加数据,原样返回
		parameters.put("body", payitemname);
		parameters.put("mch_id", WeiXinConfig.mch_id);//商户号
		parameters.put("nonce_str", WeiXinPayCommonUtil.genNonceStr());//随机字符串
//		parameters.put("notify_url", WeiXinConfig.notify_url); // 回调接口地址
		parameters.put("notify_url", url); // 回调接口地址
		parameters.put("out_trade_no", orderNumber);// 订单号
		parameters.put("spbill_create_ip", clientIP);// 用户的IP
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String starttime=sdf.format(new Date());
		parameters.put("time_start", starttime); // 订单生成时间
		parameters.put("total_fee", totalFee + "");// 金额
		parameters.put("trade_type", payType); // 交易类型 APP ， NATIVE二维码		

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
		JSONObject json = new JSONObject();
		try {
			if (return_code.equals("FAIL")) {
				json.put("return_code", map.get("return_code"));
				json.put("return_msg", map.get("return_msg"));
			} else if (return_code.equals("SUCCESS")) {
				String result_code= map.get("result_code");
				if(result_code.equals("SUCCESS")) {
					json.put("return_code", map.get("return_code"));
					json.put("return_msg", map.get("return_msg"));
					json.put("appid", map.get("appid"));
					json.put("mch_id", map.get("mch_id"));
					json.put("nonce_str", map.get("nonce_str"));
					json.put("sign", map.get("sign"));
					json.put("result_code", map.get("result_code"));
					json.put("prepay_id", map.get("prepay_id"));
					json.put("trade_type", map.get("trade_type"));
					json.put("timestamp", starttime);
					json.put("code_url", map.get("code_url"));//二维码链接
				}else if(result_code.equals("FAIL")) {
					json.put("return_code", map.get("return_code"));
					json.put("return_msg", map.get("return_msg"));
					json.put("appid", map.get("appid"));
					json.put("mch_id", map.get("mch_id"));
					json.put("nonce_str", map.get("nonce_str"));
					json.put("sign", map.get("sign"));
					json.put("result_code", map.get("result_code"));
					json.put("err_code", map.get("err_code"));
					json.put("err_code_des", map.get("err_code_des"));
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(map);
		System.out.println(json.toString());

		return json.toString();
	}
}
