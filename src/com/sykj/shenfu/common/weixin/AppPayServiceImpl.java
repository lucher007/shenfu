package com.sykj.shenfu.common.weixin;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;


public class AppPayServiceImpl {

	private String serverurl = "";

	//订单支付
	public String appPay(String str) {
		// TODO Auto-generated method stub
		try {
			JSONObject json = new JSONObject(str);
			String ordercode = json.getString("ordercode");
			String payitem = json.getString("payitem");// 付款项目 1，首付 2，尾款 3，全款
			String payitemname = json.getString("payitemname");// 付款项目 1，首付 2，尾款 3，全款
			String clientIP = json.getString("clientIP");
			String paytype = json.getString("paytype");// 支付方式 0.现金支付 1.在线微信 2.在线支付宝 3.微信静态码 4.支付宝静态码
			String paymoney = json.getString("paymoney");
			int totalFee = Integer.parseInt(paymoney)*100;
			//int totalFee = 1;
			String url = serverurl + "/order/weixin_notify";
			String result = null;
			json.remove("payitemname");
			json.remove("clientIP");
			if (paytype.equals("1")) {
				result = WeiXinCommonUtil.weixinPay(ordercode + "_" + payitem+ "_" + paytype+"_"+RandomUtil.time(8),payitemname, clientIP, totalFee, "APP", url,json.toString());
			} else if (paytype.equals("3")) {
				result = WeiXinCommonUtil.weixinPay(ordercode + "_" + payitem+ "_" + paytype+"_"+RandomUtil.time(8),payitemname, clientIP, totalFee, "NATIVE", url,json.toString());
			}
			return result;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	//订单支付成功，微信回调解接口函数
	public void weixin_notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
				//先本地数据库保存			
//				WeixinPay weixinPay=new WeixinPay();
//				weixinPay.setMch_id(mch_id);
//				weixinPay.setOpenid(openid);
//				weixinPay.setOut_trade_no(out_trade_no);
//				weixinPay.setPay_name("微信支付");
//				weixinPay.setTime_end(time_end);
//				weixinPay.setTotal_fee(total_fee);
//				weixinPay.setTrade_type(trade_type);
//				weixinPay.setTransaction_id(transaction_id);
//				//先本地数据库保存
//				try {
//					JSONObject json=new JSONObject(attach);
//					weixinPay.setOrder_code(json.getString("ordercode"));
//					weixinPay.setPay_item(json.getString("payitem"));
//					weixinPay.setReceivercode(json.getString("receivercode"));
//					weixinPay.setPaytype(json.getString("paytype"));
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//				}
				//weixinPayMapper.insert(weixinPay);
				//String result=orderService.savePaymentInfo(attach);//向OA保存订单数据
//				try {
//					JSONObject resultJson=new JSONObject(result);
//					if(resultJson.get("status").toString().equals("1")) {
//						weixinPayMapper.update(weixinPay);
//					}
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//				}
				////////// 执行自己的业务逻辑////////////////

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

	
	//扫楼支付
	public String appPayScanBuild(String str) {
		// TODO Auto-generated method stub
		try {
			JSONObject json = new JSONObject(str);
			String extendcode = json.getString("extendcode");
			String payitemname = json.getString("payitemname");// 付款项目 
			String clientIP = json.getString("clientIP");
			String paytype = json.getString("paytype");// 支付方式 0.现金支付 1.在线微信 2.在线支付宝 3.微信静态码 4.支付宝静态码
			String paymoney = json.getString("paymoney");
			int totalFee = Integer.parseInt(paymoney);
			//int totalFee = 1;
			String url = serverurl + "/appPay/weixin_myscan";
			String result = null;
			json.remove("payitem");
			json.remove("clientIP");
			if (paytype.equals("1")) {
				result = WeiXinCommonUtil.weixinPay(extendcode + "_"+ paytype+"_"+RandomUtil.time(8),payitemname, clientIP, totalFee, "APP", url,json.toString());
			} else if (paytype.equals("3")) {
				result = WeiXinCommonUtil.weixinPay(extendcode + "_"+ paytype+"_"+RandomUtil.time(8),payitemname, clientIP, totalFee, "NATIVE", url,json.toString());
			}
			return result;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//扫楼支付成功之后，微信回调接口
	public void weixin_myscan(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
				System.out.println("mch_id:" + mch_id);
				System.out.println("openid:" + openid);
				System.out.println("is_subscribe:" + is_subscribe);
				System.out.println("out_trade_no:" + out_trade_no);
				System.out.println("total_fee:" + total_fee);
				System.out.println("trade_type:" + trade_type);
				System.out.println("transaction_id:" + transaction_id);
				System.out.println("attach:" + attach);
				System.out.println("time_end:" + time_end);
				//先本地数据库保存			
//				WeixinPay weixinPay=new WeixinPay();
//				weixinPay.setMch_id(mch_id);
//				weixinPay.setOpenid(openid);
//				weixinPay.setOut_trade_no(out_trade_no);
//				weixinPay.setPay_name("微信支付");
//				weixinPay.setTime_end(time_end);
//				weixinPay.setTotal_fee(total_fee);
//				weixinPay.setTrade_type(trade_type);
//				weixinPay.setTransaction_id(transaction_id);
//				//先本地数据库保存
//				try {
//					JSONObject json=new JSONObject(attach);
//					weixinPay.setOrder_code(json.getString("extendcode"));
//					weixinPay.setPay_item(json.getString("payitemname"));
//					weixinPay.setReceivercode(json.getString("payercode"));
//					weixinPay.setPaytype(json.getString("paytype"));
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//				}
//				weixinPayMapper.insert(weixinPay);
//				String result=scanBuildService.saveCellpayinfo(attach);//向OA保存订单数据
//				try {
//					JSONObject resultJson=new JSONObject(result);
//					if(resultJson.get("status").toString().equals("1")) {
//						weixinPayMapper.update(weixinPay);
//					}
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//				}
				////////// 执行自己的业务逻辑////////////////

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

}
