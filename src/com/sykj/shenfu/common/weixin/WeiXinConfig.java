package com.sykj.shenfu.common.weixin;

public class WeiXinConfig {
	//public static String key="obPuoU1WYZdxVTaGVu0FGb4QEKLM8tr7";//商户密钥
	//网关
	public static String gatewayUrl ="https://api.mch.weixin.qq.com/pay/unifiedorder";
	//appID
	public static String appid="wxcfeefced3677d68a";
	//公众账号ID
	public static String mch_id="1502454531";//商户号
	//设备号
	public static String device_info="";
	//随机字符串
	public static String nonce_str="";
	//签名
	public static String sign="";
	//签名类型
	public static String sign_type="MD5";
	//商品描述
	public static String body="呵呵呵呵";
	//商品详情
	public static String detail="奥斯卡建安费哈酒客服哈咖啡";
	//附加数据
	public static String attach="";
	//商城订单号
	public static String out_trade_no="";
	//币种
	public static String fee_type="CNY";
	//交易金额（为分）例如12.53 应该（12.53*100） 放入当前值
	public static int total_fee=1;
	//客户IP
	public static String spbill_create_ip="192.168.0.1";
	//交易起始时间yyyyMMddHHmmss
	public static String time_start="";
	//交易结束时间
	public static String time_expire="";
	//这个字段空着即可（订单优惠标记）
	public static String goods_tag="";
	//微信回调接口（重要）
	public static String notify_url="http://www.shenyatech.com/shenfu/mobilebusiness/savePaymentInfo_notify";
	//支付交易类型
	/**
	 * JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里
	 * MICROPAY--刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口
	 */
	public static String trade_type="APP";//APP ， NATIVE二维码
	//支付订单号
	public static String product_id="";
	//指定支付方式是否限定用户用户信用卡 (这个可以空着)
	public static String limit_pay="";
	//扫描支付不需要可以空着
	public static String openid="";
	//场景信息（可以空着）
	public static String scene_info="";
	
	
	//微信公众号支付
	//商户秘钥
	public static String key_weixin="shenyakejidejsapizhifumimashezhi";//商户密钥
	//appID
	public static String appid_weixin="wx6bb0b8bc1b55e1f2";
	//公众账号ID
	public static String mch_id_weixin="1497754022";//商户号
	//微信公众号支付申请密码
	public static String app_secret_weixin = "3afc25d969359fde7a5650184e5683fb";
	//获取code下单的回调页面www.shenyatech.com/payinterface/slkdjflkasjdf.hphp
	public static String apply_code_back = "http://www.shenyatech.com/shenfu/mobilebusiness/savePaymentInfo_getOpenid";
	//测试服的下单的回调页面
	public static String apply_code_back_test ="http://111.231.143.56:8080/shenfu/mobilebusiness/savePaymentInfo";
	//公司域名网址
	public static String company_domain_name = "http://www.shenyatech.com";
	//微信公众号下单链接推广
	public static String extend_userorder_url = "http://www.shenyatech.com/shenfu/sale/saleExtendInit?salercode=";

}
