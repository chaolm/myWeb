package com.dripop.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

@Component
public class SpringContextUtil implements ApplicationContextAware, EmbeddedValueResolverAware {

	private static ApplicationContext CONTEXT;

	private static StringValueResolver stringValueResolver;
	
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		// TODO Auto-generated method stub
		CONTEXT = context;
	}
	
	public static ApplicationContext getContext() {
		return CONTEXT;
	}

	public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
		this.stringValueResolver = stringValueResolver;
	}

	public static String getPropertiesValue(String name){
		name = "${" + name + "}";
		return stringValueResolver.resolveStringValue(name);
	}
}
