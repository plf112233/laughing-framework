package com.laughing.lang.magic.config;

import com.laughing.lang.magic.MagicClass;
import com.laughing.lang.magic.MagicObject;
import com.laughing.lang.utils.LibUtil;

public class AutoMagicFactory extends MagicFactory {
	
	private MagicFactory magicFactory;

	public AutoMagicFactory() {
		if (LibUtil.isCglibExist()) {
			try {
				magicFactory = (MagicFactory)Class.forName("wint.lang.magic.config.CglibMagicFactory").newInstance();
			} catch (Exception e) {
				magicFactory = new ReflectMagicFactory();
			}
		} else {
			magicFactory = new ReflectMagicFactory();
		}
	}
	
	@Override
	public String getName() {
		return magicFactory.getName();
	}

	public MagicClass newMagicClass(Class<?> clazz) {
		return magicFactory.newMagicClass(clazz);
	}

	@Override
	public MagicObject newMagicObject(Object object) {
		// TODO Auto-generated method stub
		return null;
	}


}
