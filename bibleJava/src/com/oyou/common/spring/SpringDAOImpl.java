package com.oyou.common.spring;

import org.hibernate.SessionFactory;

public class SpringDAOImpl extends SpringAwareImpl implements SpringDAO {
	protected SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}	

}
