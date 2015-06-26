package com.cartmatic.estore.core.controller;

import java.beans.Introspector;

import org.springframework.util.ClassUtils;
import org.springmodules.validation.commons.DefaultBeanValidator;

public class GenericBeanValidator extends DefaultBeanValidator{

	
	/**
	 * 重载DefaultBeanValidator的getFormName方法.
	 * 当class是经过hibernate的增强后,class名会自动加上_$$,所以要用getUserClass来获得原始实体的class对像.
	 */
	@Override
	protected String getFormName(Class cls) {
        return Introspector.decapitalize(ClassUtils.getShortName(ClassUtils.getUserClass(cls)));
    }
}
