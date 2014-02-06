package com.oyou.common.spring;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContextAware;

public interface SpringDAO extends ApplicationContextAware {
	
	public SessionFactory getSessionFactory();

	public void setSessionFactory(SessionFactory sessionFactory);

}
