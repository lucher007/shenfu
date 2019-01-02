package com.sykj.shenfu.util.express;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.sykj.shenfu.common.HttpRequest;
import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.util.express.constant.Constants;
import com.sykj.shenfu.util.express.constant.HttpHeader;
import com.sykj.shenfu.util.express.constant.HttpSchema;
import com.sykj.shenfu.util.express.enums.Method;

import fr.opensagres.xdocreport.core.utils.StringUtils;

/**	获取物流信息
*  创建人：FH Q313596790
 * 创建时间：2016年10月11日
 */
public class GetExpressMsg {

	//购买地址：https://market.aliyun.com/products/57126001/cmapi011120.html#sku=yuncode512000008
	//APP KEY	 参数一 （阿里巴巴支付后获得）
    private final static String APP_KEY = "23476499";
    // APP密钥	 参数二 （阿里巴巴支付后获得）
    private final static String APP_SECRET = "1014f09d5dd5f1993fc471b066ea8969";
    //API域名
    private final static String HOST = "jisukdcx.market.alicloudapi.com";
    //自定义参与签名Header前缀（可选,默认只有"X-Ca-"开头的参与到Header签名）
    private final static List<String> CUSTOM_HEADERS_TO_SIGN_PREFIX = new ArrayList<String>();
    
    /**
     * HTTP GET
     *
     * @throws Exception
     */
    public static String get(String number) throws Exception {
        //请求path
        String path = "/express/query";
        //String path = "/express/type";

        Map<String, String> headers = new HashMap<String, String>();
        //（必填）根据期望的Response内容类型设置
        headers.put(HttpHeader.HTTP_HEADER_ACCEPT, "application/json");
        headers.put("a-header1", "header1Value");
        headers.put("b-header2", "header2Value");
        
        CUSTOM_HEADERS_TO_SIGN_PREFIX.clear();
        CUSTOM_HEADERS_TO_SIGN_PREFIX.add("a-header1");
        CUSTOM_HEADERS_TO_SIGN_PREFIX.add("a-header2");
        
        Request request = new Request(Method.GET, HttpSchema.HTTP + HOST, path, APP_KEY, APP_SECRET, Constants.DEFAULT_TIMEOUT);
        request.setHeaders(headers);
        request.setSignHeaderPrefixList(CUSTOM_HEADERS_TO_SIGN_PREFIX);
        
        //请求的query
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("number", number);
        querys.put("type", "auto");
        request.setQuerys(querys);
        
        //调用服务端
        Response response = Client.execute(request);

        //System.out.println(JSON.toJSONString(response));
        return JSON.toJSONString(response);
    }
    
    
    public static String get1(String urlString,String token) {
    	try {
	    	URL url = new URL(urlString);
	    	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    	conn.setConnectTimeout(5 * 1000);
	    	conn.setReadTimeout(5 * 1000);
	    	conn.setDoInput(true);
	    	conn.setDoOutput(true);
	    	conn.setUseCaches(false);
	    	conn.setInstanceFollowRedirects(false);
	    	conn.setRequestMethod("GET");
	    	conn.setRequestProperty("token",token);
	    	int responseCode = conn.getResponseCode();
	    	if (responseCode == 200) {
		    	StringBuilder builder = new StringBuilder();
		    	BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
		    	for (String s = br.readLine(); s != null; s = br.readLine()) {
		    		builder.append(s);
		    	}
		    	br.close();
		    	return builder.toString();
	    	}
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    public static String queryKuaidi(String number){
    	String url="http://api.ip138.com/express/info/?no="+number;
    	String token="7eaa431ce562d9e88ac9878483494f97";
    	return get1(url,token);
    }
    
  //快递100
    public static String queryKuaidi100Company(String number){
    	
    	//查询快递的公司名称
    	String companynamekey = HttpRequest.sendPost("http://www.kuaidi100.com/autonumber/auto", "num=" + number);
    	if(StringUtils.isNotEmpty(companynamekey)){
    		JSONArray companynamekeyJsonArray = new JSONArray(companynamekey);
    		if(companynamekeyJsonArray != null && companynamekeyJsonArray.length() > 0){
    			JSONObject companynamekeyinfo = companynamekeyJsonArray.getJSONObject(0);
    			return companynamekeyinfo.getString("comCode");
    		}
    	}else{
    		return "无此快递公司信息";
    	}
    	
    	return companynamekey;
    }
    
    //快递100
    public static String queryKuaidi100(String number){
    	
    	//查询快递的公司名称
    	String companynamekey = HttpRequest.sendPost("http://www.kuaidi100.com/autonumber/auto", "num=" + number);
    	if(StringUtils.isNotEmpty(companynamekey)){
    		JSONArray companynamekeyJsonArray = new JSONArray(companynamekey);
    		if(companynamekeyJsonArray != null && companynamekeyJsonArray.length() > 0){
    			JSONObject companynamekeyinfo = companynamekeyJsonArray.getJSONObject(0);
    			//得到快递公司名称KEY
    			String comCode = Tools.getStr(companynamekeyinfo.getString("comCode"));
                String expressinfo = HttpRequest.sendPost("http://www.kuaidi100.com/query", "type="+comCode+"&postid="+ number);
                return expressinfo;
    		}
    	}else{
    		return "无此快递公司信息";
    	}
    	
    	return companynamekey;
    }
    
    public static void main(String[] args) throws Exception {

    	System.out.println(GetExpressMsg.queryKuaidi100("3354523521870"));

	}
    
}
