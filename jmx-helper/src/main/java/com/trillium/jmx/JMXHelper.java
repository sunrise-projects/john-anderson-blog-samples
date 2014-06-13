package com.trillium.jmx;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class JMXHelper {

	public static <T> T fetchBean(String host, String port, String user,
			String password, String mbeanNameIn, Class<T> customBeanClass, JMXConnector jmxConnector) throws IOException,
			MalformedObjectNameException {
		JMXServiceURL url = new JMXServiceURL("service:jmx:remoting-jmx://"
				+ host + ":" + port);
		// for passing credentials for password
		Map<String, String[]> env = new HashMap<String, String[]>();
		String[] credentials = { user, password };
		env.put(JMXConnector.CREDENTIALS, credentials);
		jmxConnector = JMXConnectorFactory.connect(url, env);
		MBeanServerConnection mbeanServerConnection = jmxConnector
				.getMBeanServerConnection();
		// ObjectName should be same as your MBean name
		ObjectName mbeanName = new ObjectName(mbeanNameIn);
		// Get MBean proxy instance that will be used to make calls to
		// registered MBean
		@SuppressWarnings("unchecked")
		T mbeanProxy = (T) MBeanServerInvocationHandler
				.newProxyInstance(mbeanServerConnection, mbeanName,
						customBeanClass, true);
		// close the connection
		//jmxConnector.close();		
		return mbeanProxy;
	}

	public static void fetchNames(String host, String port, String user,
			String password) throws IOException, MalformedObjectNameException {
		JMXServiceURL url = new JMXServiceURL("service:jmx:remoting-jmx://"
				+ host + ":" + port);

		// for passing credentials for password
		Map<String, String[]> env = new HashMap<String, String[]>();
		String[] credentials = { user, password };
		env.put(JMXConnector.CREDENTIALS, credentials);

		JMXConnector jmxConnector = JMXConnectorFactory.connect(url, env);
		MBeanServerConnection mbeanServerConnection = jmxConnector
				.getMBeanServerConnection();

		final Set<ObjectName> mbeans = new HashSet<ObjectName>();
		mbeans.addAll(mbeanServerConnection.queryNames(null, null));
		for (final ObjectName mbean : mbeans) {
			System.out.println(mbean);
		}
		jmxConnector.close();
	}
}
