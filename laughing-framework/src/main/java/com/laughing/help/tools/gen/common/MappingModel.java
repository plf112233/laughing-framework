package com.laughing.help.tools.gen.common;

/**
* @ClassName: MappingModel 
* @author lifei.pan
* @email plfnet@163.com
* @date 2017年8月7日 下午4:18:28 
*
 */
public class MappingModel {
	private String key;
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "MappingModel [key=" + key + ",value=" + value + "]";
	}
}
