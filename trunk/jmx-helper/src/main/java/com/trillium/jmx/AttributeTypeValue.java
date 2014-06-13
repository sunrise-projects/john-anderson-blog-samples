package com.trillium.jmx;

public class AttributeTypeValue {

	private String type;
	private Object value;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "AttributeTypeValue [type=" + type + ", value=" + value + "]";
	}
	
}
