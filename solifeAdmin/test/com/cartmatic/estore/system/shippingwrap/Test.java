/*
 * 创建日期 2006-8-9
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package com.cartmatic.estore.system.shippingwrap;

import com.cartmatic.estore.common.model.system.Region;

/**
 * @author CartMatic
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class Test {

	public static void main(String[] args) {
		
		Region region1=new Region();
		region1.setRegionId(new Integer(1));
		Region region2=new Region();
		region2.setRegionId(new Integer(1));
		
		
		System.out.println(region1.equals(region2));
		
		
		
	}
}
