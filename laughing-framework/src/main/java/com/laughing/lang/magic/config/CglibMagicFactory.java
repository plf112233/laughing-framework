package com.laughing.lang.magic.config;

import com.laughing.lang.magic.MagicClass;
import com.laughing.lang.magic.MagicObject;
import com.laughing.lang.magic.cglib.CglibMagicClass;
import com.laughing.lang.magic.cglib.CglibMagicObject;

public class CglibMagicFactory extends MagicFactory {

	public CglibMagicFactory() {
	}

	public MagicClass newMagicClass(Class<?> clazz) {
		return CglibMagicClass.fromClass(clazz);
	}


	@Override
	public String getName() {
		return "cglib";
	}

	@Override
	public MagicObject newMagicObject(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

}
