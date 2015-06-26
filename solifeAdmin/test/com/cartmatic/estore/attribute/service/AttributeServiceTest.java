package com.cartmatic.estore.attribute.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cartmatic.estore.common.model.attribute.ProductAttrValue;
import com.cartmatic.estore.common.service.AttributeService;
import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;

public class AttributeServiceTest extends BaseTransactionalTestCase {

	@Autowired
	private AttributeService attributeService;
	
	
	@Test
	public void testGetProductAttributeValue() {
		ProductAttrValue pav = attributeService.getProductAttributeValue(10, 1);
		if(pav!=null){
		    Object av =  pav.getAttributeValue();
		    System.out.println(av.toString()+"   type:"+pav.getAttributeValueDataType());
		}
		else
			System.out.println("null");
	}

}
