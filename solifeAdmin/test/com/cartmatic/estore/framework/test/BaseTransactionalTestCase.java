package com.cartmatic.estore.framework.test;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.cartmatic.estore.core.util.Log4jUtil;

/**
 * 参考: http://static.springsource.org/spring/docs/3.0.x/reference/testing.html
 * 使用新的测试框架
 * 
 * @author Ryan
 *
 */
@ContextConfiguration(locations={"/conf/spring-context-test.xml"})
public class BaseTransactionalTestCase extends AbstractTransactionalJUnit4SpringContextTests {
	
	static long	startTime;
	protected final Log logger = LogFactory.getLog(getClass());
	static {
		System.out.println("Starting test case...");
		startTime = System.currentTimeMillis();
		Log4jUtil.initLogging(null);
	}
	
	@Before 
	public void setUp() { 
		System.out.println("Starting test method...");
		startTime = System.currentTimeMillis();		
	}
	
	@After
	public void onTearDownAfterTransaction() throws Exception {		
		System.out.println("Test method completed, time spent: ["+((System.currentTimeMillis() - startTime) / 1000)+
				" secs "+((System.currentTimeMillis() - startTime) % 1000)+" ms].") ;
		startTime = System.currentTimeMillis();
	}
}
