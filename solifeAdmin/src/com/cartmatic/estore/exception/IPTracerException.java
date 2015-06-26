package com.cartmatic.estore.exception;

public class IPTracerException extends Exception{
	/**
	 * 登录失败
	 */
	public static final Short TYPE_0 =0;
	/**
	 * 请求查询IP访问页面失败
	 */
	public static final Short TYPE_1 =1;
	 
	
	/**
	 * 解析页面数据失败
	 */
	public static final Short TYPE_2 =2;
	
	
	/**
	 * 此IP无法解析
	 */
	public static final Short TYPE_3 =3;
	
	/**
	 * 检查IP账号没有配置好
	 */
	public static final Short TYPE_4 =4;
	
	private Short type;
	
	public IPTracerException(Short type,String message){
		super(message);
		this.type=type;
	}
	
	public IPTracerException(Short type,String message,Exception cause){
		super(message,cause);
		this.type=type;
	}

	public Short getType() {
		return type;
	}
	
	
	
}
