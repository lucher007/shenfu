package com.sykj.shenfu.common;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code128Encoder;
import org.jbarcode.encode.Code39Encoder;
import org.jbarcode.encode.EAN13Encoder;
import org.jbarcode.encode.InvalidAtributeException;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.EAN13TextPainter;
import org.jbarcode.paint.WideRatioCodedPainter;
import org.jbarcode.paint.WidthCodedPainter;

public class BarCodeServlet extends HttpServlet {  
    private static final long serialVersionUID = 1L;  
    private JBarcode jBarcode;  
  
    /** 
     * 初始化条形码对象 
     */  
    public void init(ServletConfig conf) throws ServletException {  
        super.init(conf);  
        // 默认生成code39类型条形码  
        jBarcode = new JBarcode(Code39Encoder.getInstance(),  
                WideRatioCodedPainter.getInstance(),  
                BaseLineTextPainter.getInstance());  
        jBarcode.setShowText(true);// 显示图片下字符串内容  
        jBarcode.setShowCheckDigit(true);// 显示字符串内容中是否显示检查码内容  
        jBarcode.setCheckDigit(false);// 不生成检查码  
    }  
  
    public void doGet(HttpServletRequest req, HttpServletResponse resp)  
            throws ServletException, IOException {  
        resp.setContentType("image/png");  
        resp.setHeader("Pragma", "no-cache");  
        resp.setHeader("Cache-Control", "no-cache");  
        // 传入的参数，  
        // 如果是判断条件，则用0,1表示，‘0’即无效或者不现实，‘1’即有效或者显示  
        // 字符串参数  
        String msg = req.getParameter("msg");  
        // 是否显示图片下字符串内容  
        String showText = req.getParameter("st");  
        // 是否显示检查码内容  
        String showCheckDigit = req.getParameter("scd");  
        // 是否生成检查码  
        String checkDigit = req.getParameter("cd");  
        // 生成条形码类型  
        String codeType = req.getParameter("codeType");  
        //设置条形码barHeight  
        String barH = req.getParameter("barH");  
        //设置XDimension  
        String barXD = req.getParameter("barXD");  
        // 判断字符串内容  
        if (msg == null)  
            //msg = "788515004012";  
        // 判断显示图片下字符串内容  
        if ("0".equals(showText))  {
        	jBarcode.setShowText(false);  
        }
        //判断barHeight  
        if(barH != null && !barH.equals("")){  
            double x = Double.valueOf(barH);  
            jBarcode.setBarHeight(x);  
        }  
        if(barXD != null && !barXD.equals("")){  
            try {  
                //jBarcode.setWideRatio(5);  
                jBarcode.setXDimension(Double.valueOf(barXD));  
            } catch (InvalidAtributeException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }else{  
            try {  
                //jBarcode.setWideRatio(5);  
                jBarcode.setXDimension(Double.valueOf(0.5));  
            } catch (InvalidAtributeException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
          
        // 判断显示检查码内容  
        if ("1".equals(showCheckDigit))  
            jBarcode.setShowText(true);  
        // 判断生成检查码  
        if ("1".equals(checkDigit))  
            jBarcode.setShowText(true);  
        // 判断生成条形码类型  
        if(codeType != null && !"".equals(codeType)){  
            if (codeType.equalsIgnoreCase("EAN13")) {  
                // EAN13条形码基本属性，必要添加条件  
                jBarcode.setEncoder(EAN13Encoder.getInstance());  
                jBarcode.setPainter(WidthCodedPainter.getInstance());  
                jBarcode.setTextPainter(EAN13TextPainter.getInstance());  
                //必须需要以下属性  
                jBarcode.setCheckDigit(true);  
            } else if(codeType.equalsIgnoreCase("code128")){  
                jBarcode.setEncoder(Code128Encoder.getInstance());  
                jBarcode.setPainter(WidthCodedPainter.getInstance());  
                jBarcode.setTextPainter(BaseLineTextPainter.getInstance());  
                jBarcode.setCheckDigit(false);  
                jBarcode.setShowCheckDigit(false);  
            }else{  
                // 这里设置 根据不同的条件设定生成的条形码基本属性，如EAN13。  
                // 通过if...else...来判断即可  
                // 默认生成的是Code39  
                // 默认生成code39类型条形码  
            }  
        }  
        // 生成条形码，并通过输出来展示在页面上  
        try {  
            ByteArrayOutputStream out = new ByteArrayOutputStream();  
            BufferedImage localBufferedImage = jBarcode.createBarcode(msg);  
            ImageIO.write(localBufferedImage, "png", out);  
            byte[] b = out.toByteArray();  
            resp.getOutputStream().write(b);  
        } catch (InvalidAtributeException e) {  
            e.printStackTrace();  
        }  
    }  
  
    public void doPost(HttpServletRequest req, HttpServletResponse resp)  
            throws ServletException, IOException {  
        try {  
            doGet(req, resp);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
}  
