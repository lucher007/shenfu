package com.sykj.shenfu.common.weixin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;


public class WeixinGetOpenid {
	public static List<Object> accessToken(String code) throws IOException {
		List<Object> list = new ArrayList<Object>();
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WeiXinConfig.appid_weixin + "&secret="
		+ WeiXinConfig.app_secret_weixin + "&code=" + code + "&grant_type=authorization_code";
		GetMethod get = null;
		get = new GetMethod(url);

		HttpClient client = new HttpClient();
		client.getParams().setParameter("http.protocol.content-charset", "utf-8");
		client.getParams().setBooleanParameter("http.protocol.expect-continue", false);
		client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
		client.getHttpConnectionManager().getParams().setSoTimeout(30000);

		get.addRequestHeader("Content-type", "te|xt/html;charset=UTF-8");

		int statusCode = 0;
		try {
			statusCode = client.executeMethod(get);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (statusCode == 200) {
			InputStream resInputStream = null;
			try {
				resInputStream = get.getResponseBodyAsStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(resInputStream, "utf-8"));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println("line:" + line);
				JSONObject jsonObject = new JSONObject();
				try {
					jsonObject = JSONObject.fromObject(line);
					String openid = jsonObject.getString("openid");
					String access_token = jsonObject.getString("access_token");
					System.out.println("openid:" + openid);
					list.add(openid);
					list.add(access_token);
				} catch (Exception e) {
					jsonObject = JSONObject.fromObject(line);
					String errcode = jsonObject.getString("errcode");
					System.out.println("errcode:" + errcode);
				}
			}
		}
		return list;

		}
}
