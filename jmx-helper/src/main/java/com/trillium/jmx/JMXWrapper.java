package com.trillium.jmx;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.ReflectionException;

public interface JMXWrapper {

	public void open(String url, String username, String password)
			throws IOException;

	public List<String> getObjectNamesString() throws IOException;

	public void findObjectName(Object in, String... patterns)
			throws IOException;

	public Map<String, AttributeTypeValue> getMBeanAttributes(ObjectName mbean)
			throws InstanceNotFoundException, IntrospectionException,
			ReflectionException, IOException, AttributeNotFoundException,
			MBeanException;

	public AttributeTypeValue findObjectNameAndGetMBeanAttributeValue(
			String attibuteName, String... patterns) throws IOException,
			InstanceNotFoundException, IntrospectionException,
			AttributeNotFoundException, ReflectionException, MBeanException;

	public MBeanServerConnection getMBeanServerConnection();
	
	public void close();

}
