package com.laughing.help.tools.gen.common;

import java.util.ArrayList;
import java.util.List;


public enum StatusEnum {
	NO("否", "0"),
	YES("是", "1");

	private String name;
	private String index;

	// 构造方法
	private StatusEnum(String name, String index) {
		this.name = name;
		this.index = index;
	}
	
	public static String getName(String index){
		for (StatusEnum statusEnum : StatusEnum.values()) {
			if(statusEnum.index.equals(index)){
				return statusEnum.name;
			}
		}
		return null;
	}
	
	public static List<MappingModel> getStatusEnumList(){
		List<MappingModel> list=new ArrayList<MappingModel>();
		for (StatusEnum statusEnum : StatusEnum.values()) {
			MappingModel mappingModel=new MappingModel();
			mappingModel.setKey(statusEnum.index);
			mappingModel.setValue(statusEnum.name);
			list.add(mappingModel);
		}
		return list;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	

}
