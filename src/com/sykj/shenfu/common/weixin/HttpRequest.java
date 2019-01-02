package com.sykj.shenfu.common.weixin;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

@SuppressWarnings("deprecation")
public class HttpRequest {
	// 创建HttpClient对象
	public static HttpClient httpClient = new DefaultHttpClient();
	/*
	 * @param url 发送请求的URL
	 * 
	 * @return 服务器响应字符串
	 * 
	 * @throws Exception
	 */
	public static String getRequest(final String url) throws Exception {
		FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
			@Override
			public String call() throws Exception {
				// 创建HttpGet对象
				HttpGet get = new HttpGet(url);
				// 发送GET请求
				HttpResponse httpResponse = httpClient.execute(get);
				// 如果服务器成功地返回响应
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					// 获取服务器响应字符串
					String result = EntityUtils.toString(httpResponse.getEntity());
					return result;
				}
				return null;
			}
		});
		new Thread(task).start();
		return task.get();
	}

	// post请求
	public static String sendPost(final String url, final String request) throws Exception {
		FutureTask<String> task = new FutureTask<String>(new Callable<String>() {

			@Override
			public String call() throws Exception {
				// 创建HttpPost对象
				HttpPost post = new HttpPost(url);
				// 计算网络超时用
				HttpParams par = httpClient.getParams();
				HttpConnectionParams.setConnectionTimeout(par, 3000);
				// 如果传递参数个数比较多，可以对传递的参数进行封装

				System.out.println(request);
				 //StringEntity entity = new StringEntity(xml,"iso8859-1");
				post.setEntity(new StringEntity(request, "UTF-8"));
				post.addHeader("Content-Type", "text/xml");
				// 发送POST请求
				HttpResponse httpResponse = httpClient.execute(post);
				// 如果服务器成功地返回响应
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					// 获取服务器响应字符串
					String result = EntityUtils.toString(httpResponse.getEntity());
					System.out.println(result);
					return result;
				}
				return null;
			}
		});
		new Thread(task).start();
		return task.get();
	}

}
