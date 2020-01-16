package com.xnj.ticketgrab.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 上下文提供类
 * 
 * @author w24882
 * @date 2019年10月14日
 */
@Component
public class ContextProvider implements ApplicationContextAware {

	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		ContextProvider.context = arg0;
	}

	public static ApplicationContext getContext() {
		return ContextProvider.context;
	}

	public static Object getBean(String beanName) {
		return getContext().getBean(beanName);
	}

	public static <T> T getBean(Class<T> className) {
		return getContext().getBean(className);
	}

	public static HttpServletRequest getCurrentRequest() {
		HttpServletRequest servletRequest = null;
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes instanceof ServletRequestAttributes) {
			servletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
		}
		return servletRequest;
	}

}
