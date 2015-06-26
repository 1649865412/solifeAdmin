/*
 * Created on Oct 24, 2006
 *
 */
package com.cartmatic.estore;

import java.lang.reflect.Method;

import org.junit.Test;

import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;

/**
 * @author Ryan
 *  
 */
public class GenAllMethodNames  extends BaseTransactionalTestCase {
	
	@Test
    public void testGenAllMethodNames() {
        String[] beanNames = super.applicationContext.getBeanDefinitionNames();
        for (int i = 0; i < beanNames.length; i++) {
            String name = beanNames[i];
            if (name.indexOf("Manager") > 0 || name.indexOf("Dwr") > 0) {
                Object bean = applicationContext.getBean(name);
                if (bean != null) {
                    Class clazz = null;
                    Class[] intf = bean.getClass().getInterfaces();
                    for (int j = 1; j < intf.length; j++) {
                        Class class1 = intf[j];
                        if (!class1.getName().endsWith("Base")
                                && class1.getName().indexOf("BaseManager") == -1) {
                            clazz = intf[j];
                            break;
                        }
                    }

                    if (clazz == null) {
                        clazz = bean.getClass();
                    }
                    Method[] methods = clazz.getMethods();
                    for (int j = 0; j < methods.length; j++) {
                        Method method = methods[j];
                        logger.info(clazz.getName() + ","
                                + method.getModifiers() + ","
                                + method.getName());
                    }
                }
            }
        }
    }
}
