package com.dripop.nuonuoinvoice.constants;

/**
 * 异常静态数据
 * @author sdk.jss.com.cn
 * @version 1.1
 * @since jdk1.7
 */
public class ExConstants {
	
	public final static String security_Decryption = "#0701";
	public final static String security_Encryption_Msg = "加密异常";
	
	public final static String security_Decryption_arithmeticNULL = "#070103";
	public final static String security_Decryption_arithmeticNULL_Msg = "无法识别加密算法";
	
	public final static String input_Data_Null = "#0101";
	public final static String input_Data_Null_Msg = "入参为空";
	
	public final static String input_Data_Type = "#0201";
	public final static String input_Data_Type_Msg = "输入参数格式有误";
	public final static String input_Data_Type_Msg1 = "必填项输入参数空";

	
	public final static String http_invoke_Interrupt = "#040102";
	public final static String http_invoke_Interrupt_Msg = "http连接异常";
}
