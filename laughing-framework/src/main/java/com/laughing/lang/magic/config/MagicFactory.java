package com.laughing.lang.magic.config;

import com.laughing.lang.magic.MagicClass;
import com.laughing.lang.magic.MagicObject;

public abstract class MagicFactory {
	
	protected MagicFactory() {
		super();
	}

	public abstract MagicClass newMagicClass(Class<?> clazz);

	public abstract MagicObject newMagicObject(Object object);
	
	public abstract String getName();
	
	public static MagicFactory getMagicFactory() {
		return MagicConfig.getMagicConfig().getMagicFactory();
	}
	
}
