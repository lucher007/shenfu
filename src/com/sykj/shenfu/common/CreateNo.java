package com.sykj.shenfu.common;

import java.text.SimpleDateFormat;  
import java.util.Date;  

public class CreateNo {
	private static long orderNum = 0l;  
    private static String date ;  
      
    public static void main(String[] args) throws InterruptedException {  
        for (int i = 0; i < 10; i++) {  
            System.out.println(CreateNo.getOrderNo());  
            //Thread.sleep(1000);  
        }  
    }  
  
    /** 
     * 生成订单编号 
     * @return 
     */  
    public static synchronized String getOrderNo() {  
        String str = new SimpleDateFormat("yyMMddHHmm").format(new Date());  
        if(date==null||!date.equals(str)){  
            date = str;  
            orderNum  = 0l;  
        }  
        orderNum ++;  
        long orderNo = Long.parseLong((date)) * 10000;  
        orderNo += orderNum;;  
        return orderNo+"";  
    }  
}
