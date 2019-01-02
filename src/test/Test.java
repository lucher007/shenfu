package test;

import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.sykj.shenfu.common.DateUtils;
import com.sykj.shenfu.common.Tools;
import com.sykj.shenfu.po.JsonObjectForPara;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JsonObjectForPara para = new JsonObjectForPara();
		para.setOrdercode("1");
		para.setPayitem("2");
		para.setPayitemname("3");
		para.setPaymoney("4");
		para.setPaytype("5");
		
		
		
		String stateJson = new Gson().toJson(para);
		
		String string  = "2018-09-13 00:00:00 0";
		String endtime = "2018-09-14";
		
		
		
		
		
		System.out.println(endtime.compareTo(string.substring(0, 10)));
	}

}
