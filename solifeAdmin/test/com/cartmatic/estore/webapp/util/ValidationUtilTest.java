/*
 * create 2006-7-5
 * 
 * 
 */

package com.cartmatic.estore.webapp.util;

import junit.framework.TestCase;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.ValidatorAction;
import org.springframework.validation.BindException;

import com.cartmatic.estore.core.util.ValidationUtil;

/**
 * @author Ryan
 * 
 * 
 */
public class ValidationUtilTest extends TestCase {
	public class MockBean {
		String	testValue	= null;

		public String getTestValue() {
			return testValue;
		}

		public void setTestValue(String v) {
			testValue = v;
		}
	}

	public void testvalidateCode() {
		MockBean mock = new MockBean();
		// mock

		Field field = new Field();
		// field.setKey("testValue");
		field.setProperty("testValue");

		mock.setTestValue("asdfe_0923as");
		boolean b = ValidationUtil.validateCode(mock, new ValidatorAction(),
				field, new BindException(mock, "target"));
		assertTrue(b);

		mock.setTestValue("");
		b = ValidationUtil.validateCode(mock, new ValidatorAction(), field,
				new BindException(mock, "target"));
		assertTrue(b);

		mock.setTestValue("^asdfe_0923as");
		b = ValidationUtil.validateCode(mock, new ValidatorAction(), field,
				new BindException(mock, "target"));
		assertFalse(b);

		mock.setTestValue("asd(0923as");
		b = ValidationUtil.validateCode(mock, new ValidatorAction(), field,
				new BindException(mock, "target"));
		assertFalse(b);

		mock.setTestValue("asdfe_!923as");
		b = ValidationUtil.validateCode(mock, new ValidatorAction(), field,
				new BindException(mock, "target"));
		assertFalse(b);

		mock.setTestValue("asdfe在0923as");
		b = ValidationUtil.validateCode(mock, new ValidatorAction(), field,
				new BindException(mock, "target"));
		assertFalse(b);
	}

	public void testValidateNoHtml() {
		MockBean mock = new MockBean();
		// mock

		Field field = new Field();
		// field.setKey("testValue");
		field.setProperty("testValue");

		mock.setTestValue("asdfe0923a!s hei_sff ef");
		boolean b = ValidationUtil.validateNoHtml(mock, new ValidatorAction(),
				field, new BindException(mock, "target"));
		assertTrue(b);

		mock.setTestValue("teee<test>123</test>456");
		b = ValidationUtil.validateNoHtml(mock, new ValidatorAction(), field,
				new BindException(mock, "target"));
		assertFalse(b);

		mock.setTestValue("teee<div style='test'>123</div>456");
		b = ValidationUtil.validateNoHtml(mock, new ValidatorAction(), field,
				new BindException(mock, "target"));
		assertFalse(b);

		mock.setTestValue("teee<a href='test'>123</a>456");
		b = ValidationUtil.validateNoHtml(mock, new ValidatorAction(), field,
				new BindException(mock, "target"));
		assertFalse(b);

		mock.setTestValue("teee<test href='test'/>456");
		b = ValidationUtil.validateNoHtml(mock, new ValidatorAction(), field,
				new BindException(mock, "target"));
		assertFalse(b);

		mock.setTestValue("teee<br/>456");
		b = ValidationUtil.validateNoHtml(mock, new ValidatorAction(), field,
				new BindException(mock, "target"));
		assertFalse(b);
	}
}
