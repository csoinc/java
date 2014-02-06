package com.oyou.common.hibernate.util;

/**
 * Hold the constants of hibernate session related params
 * 
 * @author Owen Ou
 */
public interface SessionConstants {
	static final String HIBERNATE_FILENAME = "hibernate3.cfg.xml";
	static final String JNDI_SESSION_FACTORY = "hibernate/sessionFactory";
//	static final int HIBERNATE_QUERY_MAX_ROWS = 0;
//	static final int HIBERNATE_QUERY_TIMEOUT = 0;
	static final int HIBERNATE_QUERY_MAX_ROWS = 2000; //2000 rows
	static final int HIBERNATE_QUERY_TIMEOUT = 120; //120 seconds
}
