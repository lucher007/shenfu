package com.sykj.shenfu.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;



import com.googlecode.jsonplugin.JSONException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.impl.MultiPartWriter;

public class JerseyClient {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws JSONException 
	 */

	public static void main(String[] args) throws IOException, JSONException {
		//file.createNewFile();
	   // System.out.println(file.exists());
	    FormDataMultiPart part = new FormDataMultiPart();

//	    part.bodyPart(new FileDataBodyPart("file", file));
//	    part.bodyPart(new FileDataBodyPart("file", file));
	    part.bodyPart(new FormDataBodyPart("methodname", "userRegister"));

	    //装json信息
		
		

	   String jsonStr = "{'client_phone':'1234567890','client_name':'王二武','client_address':'成都市高新西区','client_checktime':'2018-02-01 11:52:50','client_visitstate':'1','user_workid':'18020100000001'}";
	   //String jsonStr1 = "{'totalMoney':'20','payMoney':'20','terminalid':'1234565796','terminaltype':'1','billDetail':[{'id':'1','type':'1','buytype':'buyproduct','totalmoney':'20.00','starttime':'2017-01-19','endtime':'2017-02-19'}]}";
	    
		//Map<String, Object> responseMap = new HashMap<String, Object>();
		//responseMap.put("methodname", value)
	    //jsonStr = JSONUtil.serialize(responseMap);
    	part.bodyPart(new FormDataBodyPart("parajson", jsonStr));
    	ClientConfig cc = new DefaultClientConfig();
	    cc.getClasses().add(MultiPartWriter.class);
	    Client writerClient = Client.create(cc);
	    // 处理文件将超时设置为10S
	    writerClient.setConnectTimeout(new Integer(30000));
	    writerClient.setReadTimeout(new Integer(30000));
	    try {
	        WebResource resource = writerClient.resource("http://localhost:8080/shenfu/httpForMps/commServerForSaleMPS");
	        String response = resource.type(MediaType.MULTIPART_FORM_DATA_TYPE).post(String.class, part);
	        System.out.println(response);
	    } finally {
	        writerClient.destroy();
	    }
	}
	
	
	
	
}
