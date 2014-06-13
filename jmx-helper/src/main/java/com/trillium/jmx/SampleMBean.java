package com.trillium.jmx;

import java.util.List;

public interface SampleMBean {
	
	public String getValue();
	
	public List<String> getValues(String input); 
	
}