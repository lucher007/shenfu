package com.sykj.shenfu.common.weixin;

public class RandomUtil {

	//取时间戳后几位
	public static String time(int num) {
		String time=System.currentTimeMillis()+"";		
		return time.substring(time.length()-num,time.length());
	}
}
