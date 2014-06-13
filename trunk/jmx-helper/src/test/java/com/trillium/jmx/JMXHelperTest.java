package com.trillium.jmx;

import java.io.IOException;
import java.util.List;

import javax.management.MalformedObjectNameException;
import javax.management.remote.JMXConnector;

import junit.framework.TestCase;

public class JMXHelperTest  extends TestCase  {

	static final String HOST = "host.com";
	static final String PORT = "1234";
	static final String USER = "user";
	static final String PASSWORD = "pass";
	static final String SAMPLEMBeanName = "com.trillium.jmx:type=jmx.monitor,name=status";
	
	public void testFetchNames() throws MalformedObjectNameException, IOException {	
		JMXHelper.fetchNames(HOST, PORT, USER, PASSWORD);
	}
	
	@SuppressWarnings("unused")
	public void testFetchBean() throws MalformedObjectNameException, IOException {	
		JMXConnector jmxConnector = null;
		String mbeanName = SAMPLEMBeanName;
		SampleMBean mbeanProxy = (SampleMBean)JMXHelper.fetchBean(HOST, PORT, USER, PASSWORD, mbeanName, SampleMBean.class, jmxConnector);		
		System.out.println("value::"
				+ mbeanProxy.getValue());
		List<String> keys = mbeanProxy
				.getValues("input");
		for (String key : keys) {
			System.out.println(key);
		}		
		if(jmxConnector!=null) {
			jmxConnector.close();	
		}
		
	}	
	
}
