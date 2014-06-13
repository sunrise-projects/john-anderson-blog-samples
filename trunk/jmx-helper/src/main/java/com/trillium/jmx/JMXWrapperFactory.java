package com.trillium.jmx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;


public class JMXWrapperFactory implements JMXWrapper {

	MBeanServerConnection mbeanServerConnection = null;
	JMXConnector jmxConnector = null;

	public MBeanServerConnection getMBeanServerConnection() {
		return mbeanServerConnection;
	}
	@Override
	public void open(String url, String username, String password)
			throws IOException {
		Map<String, String[]> env = new HashMap<>();
		String[] credentials = { username, password };
		env.put(JMXConnector.CREDENTIALS, credentials);

		JMXServiceURL serviceUrl = new JMXServiceURL(url);

		jmxConnector = JMXConnectorFactory.connect(serviceUrl, env);
		mbeanServerConnection = jmxConnector.getMBeanServerConnection();

	}

	public Set<ObjectName> getObjectNamesMbean() throws IOException {
		final Set<ObjectName> mbeans = new HashSet<ObjectName>();
		mbeans.addAll(mbeanServerConnection.queryNames(null, null));
		return mbeans;
	}

	public List<String> getObjectNamesString() throws IOException {
		List<String> result = new ArrayList<String>();
		final Set<ObjectName> mbeans = getObjectNamesMbean();
		for (final ObjectName mbean : mbeans) {
			result.add(mbean.getCanonicalName());
		}
		return result;
	}

	public void findObjectName(Object in, String... patterns)
			throws IOException {
		final Set<ObjectName> mbeans = getObjectNamesMbean();
		for (final ObjectName mbean : mbeans) {
			String input = mbean.getCanonicalName();
			int match = 0;
			for (String pattern : patterns) {
				if (input.contains(pattern)) {
					match++;
					continue;
				} else {
					break;
				}
			}
			if (match == patterns.length) {
				if (in instanceof StringBuilder) {
					StringBuilder v = (StringBuilder) in;
					v.append(input);
				} else if (in instanceof List) {
					List<ObjectName> l = (List<ObjectName>) in;
					l.add(mbean);
				} else {
					in = null;
				}
				break;
			}
		}

	}

	public Map<String, AttributeTypeValue> getMBeanAttributes(ObjectName mbean)
			throws InstanceNotFoundException, IntrospectionException,
			ReflectionException, IOException, AttributeNotFoundException,
			MBeanException {
		Map<String, AttributeTypeValue> map = new HashMap<String, AttributeTypeValue>();
		final MBeanAttributeInfo[] attributes = mbeanServerConnection
				.getMBeanInfo(mbean).getAttributes();
		for (final MBeanAttributeInfo attribute : attributes) {
			final Object value = mbeanServerConnection.getAttribute(mbean,
					attribute.getName());
			AttributeTypeValue vals = new AttributeTypeValue();
			vals.setType(attribute.getType());
			vals.setValue(value);
			map.put(attribute.getName(), vals);
		}
		return map;
	}

	public AttributeTypeValue findObjectNameAndGetMBeanAttributeValue(
			String attibuteName, String... patterns) throws IOException,
			InstanceNotFoundException, IntrospectionException,
			AttributeNotFoundException, ReflectionException, MBeanException {
		List<ObjectName> res = new ArrayList<ObjectName>();
		findObjectName(res, patterns);
		ObjectName mbean = (ObjectName) res.get(0);
		Map<String, AttributeTypeValue> map = getMBeanAttributes(mbean);
		AttributeTypeValue vals = map.get(attibuteName);
		return vals;
	}
	
	public void close() {
		if (jmxConnector != null)
			try {
				jmxConnector.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
