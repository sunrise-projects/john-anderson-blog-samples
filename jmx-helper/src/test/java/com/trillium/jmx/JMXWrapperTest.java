package com.trillium.jmx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanException;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

public class JMXWrapperTest {

	public static void main(String args[]) throws InstanceNotFoundException,
			IntrospectionException, AttributeNotFoundException,
			MalformedObjectNameException, ReflectionException, MBeanException {

		List<String> servers = new ArrayList<String>();
		servers.add("app1.domain.com:1090");
		servers.add("app1.domain.com:2090");

		for (String server : servers) {
			checkFromList(server, "12345");
		}

	}

	public static void checkFromList(String server, String key)
			throws InstanceNotFoundException, IntrospectionException,
			AttributeNotFoundException, ReflectionException, MBeanException,
			MalformedObjectNameException {
		JMXWrapper client = new JMXWrapperFactory();
		try {
			client.open("service:jmx:remoting-jmx://" + server, "admin",
					"passwd");
			ObjectName mbeanName = new ObjectName(
					"com.domain.modules:type=global.value,name=status");
			SampleMBean mbeanProxy = (SampleMBean) MBeanServerInvocationHandler
					.newProxyInstance(client.getMBeanServerConnection(),
							mbeanName, SampleMBean.class, true);
			List<String> keys = mbeanProxy
					.getValues("inputValue");
			System.out.print(server + "::inputValue::" + key + "::");
			if (keys.contains(key)) {
				System.out.println("Exist.");
			} else {
				System.out.println("Does not exist.");
			}

		} catch (IOException e) {
			
			e.printStackTrace();
		} finally {
			client.close();
		}
	}

	public static void checkEachServerAttributeValue(String server) {

		JMXWrapper client = new JMXWrapperFactory();
		try {
			client.open("service:jmx:remoting-jmx://" + server, "admin",
					"passwd");
			AttributeTypeValue vals;
			try {
				vals = client.findObjectNameAndGetMBeanAttributeValue(
						"ObjectCount", new String[] { "StatValue",
								"inputValue" });
				System.out
						.println(server
								+ "::StatValue::inputValue::ObjectCount::"
								+ vals.getValue());
			} catch (InstanceNotFoundException e) {
				
				e.printStackTrace();
			} catch (IntrospectionException e) {
				
				e.printStackTrace();
			} catch (AttributeNotFoundException e) {
				
				e.printStackTrace();
			} catch (ReflectionException e) {
				
				e.printStackTrace();
			} catch (MBeanException e) {
				
				e.printStackTrace();
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		} finally {
			client.close();
		}

	}

	public static void main6(String args[]) {

		List<String> servers = new ArrayList<String>();
		servers.add("app1.domain.com:1090");
		servers.add("app1.domain.com:2090");

		for (String server : servers) {
			checkEachServerAttributeValue(server);
		}

	}

	public static void main5(String args[]) throws InstanceNotFoundException,
			IntrospectionException, AttributeNotFoundException,
			ReflectionException, MBeanException {
		JMXWrapper client = new JMXWrapperFactory();
		try {
			client.open("service:jmx:remoting-jmx://app10.domain.com:2090",
					"admin", "passwd");
			AttributeTypeValue vals = client
					.findObjectNameAndGetMBeanAttributeValue("ObjectCount",
							new String[] { "StatValue",
									"inputValue" });
			System.out.println(vals.getValue());
		} catch (IOException e) {
			
			e.printStackTrace();
		} finally {
			client.close();
		}
	}

	public static void main4(String args[]) throws InstanceNotFoundException,
			IntrospectionException, AttributeNotFoundException,
			ReflectionException, MBeanException {
		JMXWrapper client = new JMXWrapperFactory();
		try {
			client.open("service:jmx:remoting-jmx://app10.domain.com:2090",
					"admin", "passwd");
			List<ObjectName> res = new ArrayList<ObjectName>();
			client.findObjectName(res, new String[] { "StatValue",
					"inputValue" });
			ObjectName mbean = (ObjectName) res.get(0);
			Map<String, AttributeTypeValue> map = client
					.getMBeanAttributes(mbean);
			AttributeTypeValue vals = map.get("ObjectCount");
			System.out.println(vals.getValue());

		} catch (IOException e) {
			
			e.printStackTrace();
		} finally {
			client.close();
		}
	}

	public static void main3(String args[]) throws InstanceNotFoundException,
			IntrospectionException, AttributeNotFoundException,
			ReflectionException, MBeanException {
		JMXWrapper client = new JMXWrapperFactory();
		try {
			client.open(
					"service:jmx:remoting-jmx://app1.domain.com:1090",
					"admin", "passwd");
			List<ObjectName> res = new ArrayList<ObjectName>();
			client.findObjectName(res, new String[] { "StatValue",
					"inputValue" });
			ObjectName mbean = (ObjectName) res.get(0);
			System.out.println(mbean.toString());

			Map<String, AttributeTypeValue> map = client
					.getMBeanAttributes(mbean);
			System.out.println(map.toString());

			AttributeTypeValue vals = map.get("ObjectCount");
			System.out.println(vals.getValue());

		} catch (IOException e) {
			
			e.printStackTrace();
		} finally {
			client.close();
		}
	}

	public static void main2(String args[]) {
		JMXWrapper client = new JMXWrapperFactory();
		try {
			client.open(
					"service:jmx:remoting-jmx://app1.domain.com:1090",
					"admin", "passwd");
			List<ObjectName> res = new ArrayList<ObjectName>();
			client.findObjectName(res, new String[] { "StatValue",
					"sampleInput2" });
			ObjectName a = (ObjectName) res.get(0);
			System.out.println(a.toString());

		} catch (IOException e) {
			
			e.printStackTrace();
		} finally {
			client.close();
		}
	}

	public static void main1(String args[]) {
		JMXWrapper client = new JMXWrapperFactory();
		try {
			client.open(
					"service:jmx:remoting-jmx://app1.domain.com:1090",
					"admin", "passwd");
			List<String> objectNames = client.getObjectNamesString();
			for (String objectName : objectNames) {
				System.out.println(objectName);
			}

		} catch (IOException e) {
			
			e.printStackTrace();
		} finally {
			client.close();
		}
	}
}
