package com.oyou.common.hibernate;

import java.net.URL;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.oyou.common.hibernate.util.SessionConstants;

/**
 * Initial session factory and open the hibernate session
 * 
 * @author Owen Ou
 */
public class HibernateSession {
	private static HibernateSession hibernateSession = null;

	private static final Log log = LogFactory.getLog(HibernateSession.class);

	private static SessionFactory sessionFactory = null;

	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();

	/**
	 * Get the instance
	 * 
	 * @return
	 */
	public static HibernateSession getInstance() {
		if (hibernateSession == null)
			hibernateSession = new HibernateSession();
		return hibernateSession;
	}

	private boolean CMT = true;

	private HibernateSession() {
	}

	/**
	 * Set session for DAO. hold it when getCurrentSession not working<br>
	 * 1. the session may come from container transaction manager if this process is part of the transaction.<br>
	 * 2. an alternate way is open the new session if this is not part of any transaction.<br>
	 * 
	 * @return Session - Hibernate session
	 */
	private Session createSession() {
		Session session = null;
		try {
			if (CMT) {
				session = getSessionFactory().getCurrentSession();
			} else {
				session = getSessionFactory().openSession();
				threadLocal.set(session);
			}
		} catch (HibernateException he) {
			setCMT(false);
			log.warn("Can't get current session from CMT/JTA transaction manager, create new session alternately.");
			session = getSessionFactory().openSession();
			threadLocal.set(session);
		}
		return session;
	}

	/**
	 * get hibernate config file path from property file
	 * 
	 * @return
	 */
	private String getHibernateConfigFile() {
		return SessionConstants.HIBERNATE_FILENAME;
	}

	/**
	 * Get the HB list max results setting
	 * 
	 * @return
	 */
	public int getQueryMaxRows() {
		return SessionConstants.HIBERNATE_QUERY_MAX_ROWS;
	}

	/**
	 * Get the query timeout setting
	 * 
	 * @return
	 */
	public int getQueryTimeout() {
		return SessionConstants.HIBERNATE_QUERY_TIMEOUT;
	}

	/**
	 * @return Session - Hibernate session
	 */
	public Session getSession() {
		Session session = null;
		if (isCMT()) {
			session = createSession();
		} else {
			session = (Session) threadLocal.get();
			if (session == null) {
				session = createSession();
			}
		}
		return session;
	}

	/**
	 * Recommend method to get session factory
	 * 
	 * @return SessionFactory
	 */
	public SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				sessionFactory = (SessionFactory) new InitialContext().lookup(SessionConstants.JNDI_SESSION_FACTORY);
			} catch (NamingException e) {
				log.info("Can't get session factory from JNDI lookup.");
			}
			if (sessionFactory == null) {
				String filename = getHibernateConfigFile();
				if (filename == null || filename.equals("")) {
					filename = SessionConstants.HIBERNATE_FILENAME;
				}
				log.info("Build session factory from xml config file: " + filename);
				sessionFactory = getSessionFactory(filename);
			}
		}
		return sessionFactory;
	}

	/**
	 * Build session factory from config file
	 * 
	 * @param config
	 * @return
	 */
	public SessionFactory getSessionFactory(String config) {
		SessionFactory sessionFactory = null;
		URL url = Configuration.class.getClassLoader().getResource(config);
		if (url != null)
			sessionFactory = getSessionFactory(url);
		if (sessionFactory == null) {
			log.error("Fail to build session factory.");
		} else {
			log.info("Success to build session factory.");
		}
		return sessionFactory;
	}

	/**
	 * Build session factory from URL
	 * 
	 * @param config
	 * @return
	 */
	public SessionFactory getSessionFactory(URL url) {
		SessionFactory sessionFactory = null;
		try {
			sessionFactory = new Configuration().configure(url).buildSessionFactory();
		} catch (Exception e) {
			log.info("Error when build session factory: " + e.getMessage());
			log.info("Build session factory from no data source xml config file: " + SessionConstants.HIBERNATE_FILENAME);
			url = Configuration.class.getClassLoader().getResource(SessionConstants.HIBERNATE_FILENAME);
			sessionFactory = new Configuration().configure(url).buildSessionFactory();
		}
		return sessionFactory;
	}

	/**
	 * @return
	 */
	private boolean isCMT() {
		return CMT;
	}

	public void releaseSession() {
		threadLocal.remove();
		threadLocal.set(null);
	}

	/**
	 * @param b
	 */
	private void setCMT(boolean b) {
		CMT = b;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		HibernateSession.sessionFactory = sessionFactory;
	}

}
