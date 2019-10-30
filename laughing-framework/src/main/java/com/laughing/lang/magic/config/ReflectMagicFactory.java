package com.laughing.lang.magic.config;

import com.laughing.lang.magic.MagicClass;
import com.laughing.lang.magic.MagicObject;
import com.laughing.lang.magic.reflect.ReflectMagicClass;
import com.laughing.lang.magic.reflect.ReflectMagicObject;

public class ReflectMagicFactory extends MagicFactory {

	public ReflectMagicFactory() {
	}

	public MagicClass newMagicClass(Class<?> clazz) {
		return new ReflectMagicClass(clazz);
	}

	public MagicObject newMagicObject(Object object) {
		return new ReflectMagicObject(object);
	}

	@Override
	public String getName() {
		return "java";
	}

}
