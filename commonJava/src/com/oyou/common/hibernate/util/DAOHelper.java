package com.oyou.common.hibernate.util;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import com.oyou.common.hibernate.HibernateSession;

/**
 * Low level hibernate DAO helper class
 * @author Owen Ou
 */
public class DAOHelper {
	private static final Log log = LogFactory.getLog(DAOHelper.class);

	/**
	 * delete the object in the database by type and id
	 * @param session
	 * @param cls
	 * @param id
	 */
	public static void deletePersistenceObject(Session session, Class cls, Serializable id) {
		Object obj = session.load(cls, id);
		deletePersistenceObject(session, obj);
	}

	/**
	 * delete the object in the database
	 * @param session
	 * @param obj
	 */
	public static void deletePersistenceObject(Session session, Object obj) {
		if (obj != null) {
			session.delete(obj);
		}
	}

	/**
	 * execute the hibernate query
	 * @param query
	 * @return List
	 */
	public static List<Object> executeHQLListQuery(Query query) {
		return executeHQLListQuery(query, 0, 0);
	}

	/**
	 * @param query
	 * @param limit
	 * @return
	 */
	public static List<Object> executeHQLListQuery(Query query, int limit) {
		return executeHQLListQuery(query, limit, HibernateSession.getInstance().getQueryTimeout());
	}

	/**
	 * execute the hibernate query with the max results limit and timeout
	 * @param query
	 * @param limit
	 * @param timeout
	 * @return List
	 */
	public static List<Object> executeHQLListQuery(Query query, int limit, int timeout) {
		long start = 0, end = 0;
		if (log.isDebugEnabled()) {
			start = System.currentTimeMillis();
		}
		if (limit > 0) {
			query.setMaxResults(limit);
		}
		if (timeout > 0) {
			query.setTimeout(timeout);
		}

		List list = null;
		list = query.list();

		if (log.isDebugEnabled()) {
			end = System.currentTimeMillis();
			log.debug(">>>>Query cost time: " + (end - start) + " millis.");
		}
		return list;
	}

	/**
	 * execute the hibernate query string
	 * @param session
	 * @param hsql
	 * @return List
	 */
	public static List<Object> executeHQLListQuery(Session session, String hsql) {
		return executeHQLListQuery(session, hsql, 0, 0);
	}

	/**
	 * keep for not broken current test cases
	 * @param session
	 * @param hsql
	 * @param limit
	 * @return
	 */
	public static List<Object> executeHQLListQuery(Session session, String hsql, int limit) {
		return executeHQLListQuery(session, hsql, limit, HibernateSession.getInstance().getQueryTimeout());
	}

	/**
	 * execute the hibernate query string with max results limit and timeout
	 * @param session
	 * @param hsql
	 * @param limit
	 * @param timeout
	 * @return List
	 */
	public static List<Object> executeHQLListQuery(Session session, String hsql, int limit, int timeout) {
		Query query = session.createQuery(hsql);
		return executeHQLListQuery(query, limit, timeout);
	}

	/**
	 * execute the hibernate
	 * @param query
	 * @param ht
	 * @return List
	 */
	public static List<Object> executeHQLListQueryByCriteria(Query query, Hashtable ht) {
		return executeHQLListQueryByCriteria(query, 0, 0, ht);
	}

	/**
	 * @param query
	 * @param limit
	 * @param ht
	 * @return
	 */
	public static List<Object> executeHQLListQueryByCriteria(Query query, int limit, Hashtable ht) {
		return executeHQLListQueryByCriteria(query, limit, HibernateSession.getInstance().getQueryTimeout(), ht);
	}

	/**
	 * execute the hibernate query with criteria
	 * @param query
	 * @param limit
	 * @param timeout
	 * @param ht
	 * @return List
	 */
	public static List<Object> executeHQLListQueryByCriteria(Query query, int limit, int timeout, Hashtable ht) {
		query = setQueryCriteria(query, ht);
		if (limit > 0) {
			query.setMaxResults(limit);
		}
		if (timeout > 0) {
			query.setTimeout(timeout);
		}
		return query.list();
	}

	/**
	 * execute the hibernate query string with criterias
	 * @param session
	 * @param hsql
	 * @param ht
	 * @return List
	 */
	public static List<Object> executeHQLListQueryByCriteria(Session session, String hsql, Hashtable ht) {
		Query query = session.createQuery(hsql);
		return executeHQLListQueryByCriteria(query, ht);
	}

	/**
	 * execute the hibernate query string with criteria and limit and timout
	 * @param session
	 * @param hsql
	 * @param limit
	 * @param timeout
	 * @param ht
	 * @return List
	 */
	public static List<Object> executeHQLListQueryByCriteria(Session session, String hsql, int limit, int timeout, Hashtable ht) {
		Query query = session.createQuery(hsql);
		return executeHQLListQueryByCriteria(query, limit, timeout, ht);
	}

	/**
	 * execute hibernate query
	 * @param query
	 * @return Object
	 */
	public static Object executeHQLObjectQuery(Query query) {
		return query.uniqueResult();
	}

	/**
	 * execute hibernate query string
	 * @param session
	 * @param hsql
	 * @return Object
	 */
	public static Object executeHQLObjectQuery(Session session, String hsql) {
		Query query = session.createQuery(hsql);
		return executeHQLObjectQuery(query);
	}

	/**
	 * execute hibernate query with criterias
	 * @param query
	 * @param ht
	 * @return Object
	 */
	public static Object executeHQLObjectQueryByCriteria(Query query, Hashtable ht) {
		query = setQueryCriteria(query, ht);
		return query.uniqueResult();
	}

	/**
	 * execute hibernate query string with criteria
	 * @param session
	 * @param query
	 * @param ht
	 * @return Object
	 */
	public static Object executeHQLObjectQueryByCriteria(Session session, String query, Hashtable ht) {
		Query qry = session.createQuery(query);
		return executeHQLObjectQueryByCriteria(qry, ht);
	}

	/**
	 * get list of object for the giving type
	 * @param session
	 * @param cls
	 * @return List
	 */
	public static List<Object> getPersistenceList(Session session, Class cls) {
		return getPersistenceList(session, cls, 0, 0);
	}

	/**
	 * @param session
	 * @param cls
	 * @param limit
	 * @return
	 */
	public static List<Object> getPersistenceList(Session session, Class cls, int limit) {
		return getPersistenceList(session, cls, limit, HibernateSession.getInstance().getQueryTimeout());
	}

	/**
	 * get list of object for the giving type with max results limit
	 * @param session
	 * @param cls
	 * @param limit
	 * @param timeout
	 * @return List
	 */
	public static List<Object> getPersistenceList(Session session, Class cls, int limit, int timeout) {
		Criteria criteria = session.createCriteria(cls);
		if (limit > 0) {
			criteria.setMaxResults(limit);
		}
		if (timeout > 0) {
			criteria.setTimeout(timeout);
		}
		return criteria.list();
	}

	/**
	 * get list of object by criteria
	 * @param criteria
	 * @return List
	 */
	public static List<Object> getPersistenceListByCriteria(Criteria criteria) {
		return getPersistenceListByCriteria(criteria, 0, 0);
	}

	/**
	 * get list of object by criteria with max results limit
	 * @param criteria
	 * @param limit
	 * @param timeout
	 * @return List
	 */
	public static List<Object> getPersistenceListByCriteria(Criteria criteria, int limit, int timeout) {
		if (limit > 0) {
			criteria.setMaxResults(limit);
		}
		if (timeout > 0) {
			criteria.setTimeout(timeout);
		}
		return criteria.list();
	}

	/**
	 * get object by criteria
	 * @param criteria
	 * @return Object
	 */
	public static Object getPersistenceObjectByCriteria(Criteria criteria) {
		Object obj = null;
		obj = criteria.uniqueResult();
		return obj;
	}

	/**
	 * get object with the giving id and type, when an exception occurs, using Session.get() is safe.
	 * @param session
	 * @param id
	 * @param cls
	 * @return Object
	 */
	public static Object getPersistenceObjectById(Session session, Class cls, Serializable id) {
		Object obj = session.get(cls, id);
		if (obj != null) {
			Hibernate.initialize(obj);
		}
		return obj;
	}

	/**
	 * insert object in database
	 * @param session
	 * @param obj - The persistence object need be insert
	 */
	public static Serializable insertPersistenceObject(Session session, Object obj) {
		return session.save(obj);
	}


	/**
	 * load object with the giving id and type
	 * @param session
	 * @param id
	 * @param cls
	 * @return Object
	 */
	public static Object loadPersistenceObjectById(Session session, Class cls, Serializable id) {
		Object obj = session.load(cls, id);
		if (obj != null) {
			Hibernate.initialize(obj);
		}
		return obj;
	}

	/**
	 * Save or Update the object in database
	 * @param session - The current hibernate session
	 * @param obj - The object to be update
	 */
	public static void saveOrUpdatePersistenceObject(Session session, Object obj) {
		session.saveOrUpdate(obj);
	}

	/**
	 * set criteria to hibernate query
	 * @param query - The hibernate query to be process
	 * @param ht - The hashtable hold query criteria
	 * @return Query - The hibernate query
	 */
	protected static Query setQueryCriteria(Query query, Hashtable<String,Object> ht) {
		Set<String> keys = ht.keySet();
		for (Iterator<String> iter = keys.iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			Object value = ht.get(key);
			query.setParameter(key, value);
		}
		return query;
	}

	/**
	 * Update the object in database
	 * @param session - The current hibernate session
	 * @param obj - The object to be update
	 */
	public static void updatePersistenceObject(Session session, Object obj) {
		session.saveOrUpdate(obj);
	}

}
