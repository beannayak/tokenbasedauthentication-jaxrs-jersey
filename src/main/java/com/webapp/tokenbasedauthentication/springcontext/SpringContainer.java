package com.webapp.tokenbasedauthentication.springcontext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContainer implements ApplicationContextAware {
	@Autowired
	private static ApplicationContext context;

	public void setApplicationContext(ApplicationContext context) throws BeansException {
		SpringContainer.context = context;
	}

	public static synchronized ApplicationContext getApplicationContext() {
		return context;

	}
}
