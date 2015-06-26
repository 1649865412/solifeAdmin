package com.cartmatic.estore.catalog.service.impl;

import org.apache.commons.lang.StringUtils;

import com.cartmatic.estore.catalog.dao.ProductDao;
import com.cartmatic.estore.catalog.service.ProductCodeGenerator;
import com.cartmatic.estore.common.util.DateUtil;

public class ProductCodeGeneratorImpl implements ProductCodeGenerator
{
	
	private ProductDao productDao = null;
	private final static Object GenLock = new Object();


	private static String thisDate = "";

	private static int code_seq = -1;
	private static int code_seq_len = 3;
	
	
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	@Override
	public String generateCode() {
		init();  //检查是否需要初始化
		String code = "";
		String strNumber = "";
		int number = getCode_seq();
		if (number < 10)
		{
			strNumber = "00" + number;
		}
		else if (number < 100)
		{
			strNumber = "0" + number;
		}
		/*else if (number < 1000)
		{
			strNumber = "0" + number;
		}*/
		else
		{
			strNumber = String.valueOf(number);
		}
		
		code =  DateUtil.fmtTodayToFive() + strNumber ;

		return code;
	}

	private int getCode_seq()
	{
		synchronized (GenLock)
		{
			code_seq++;
		}
		return code_seq;
	}

	private synchronized void init()
	{
		if (code_seq == -1)
		{
			String maxCode = getMaxCode();
			if (StringUtils.isBlank(maxCode))
			{
				thisDate = DateUtil.fmtTodayToFive();
				code_seq = 0;
			}
			else
			{			
				try
				{
					String orderDate = maxCode.substring(0,5);
					thisDate = DateUtil.fmtTodayToFive();
					if (orderDate.equals(thisDate))
					{
						String strMaxNumber = maxCode.substring(5, 5+code_seq_len);
						int maxNumber = Integer.valueOf(strMaxNumber).intValue();
						code_seq = maxNumber;
					}
					else
					{
						code_seq = 0;
					}
				}
				catch (Exception e)			
				{
					code_seq=0;
				}
			}
		}
		else
		{
			if (!thisDate.equals(DateUtil.fmtTodayToFive()))
			{
				code_seq = 0;
				thisDate = DateUtil.fmtTodayToFive();
			}
			
		}		
	}
	
	private String getMaxCode(){
		String maxCode="";
		maxCode=productDao.getMaxAutoCode(DateUtil.fmtTodayToFive());
		return maxCode;
//		return "131073510";
//		return "13107040";
	}
	
	
	public static void main(String[] args){
		/*String maxOrderNo="S071520008";
		System.out.println(maxOrderNo.substring("S".length(), "S".length() +5));
		System.out.println(maxOrderNo.substring("S".length()+5, "S".length() +5+4));*/
		ProductCodeGenerator codeGenerator=new ProductCodeGeneratorImpl();
		for (int i = 0; i < 10; i++)
		{
			System.out.println(codeGenerator.generateCode());
		}
	}


}
