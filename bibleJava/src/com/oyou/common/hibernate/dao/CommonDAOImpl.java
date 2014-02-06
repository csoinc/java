package com.oyou.common.hibernate.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.oyou.common.Entity;
import com.oyou.common.hibernate.HibernateSession;
import com.oyou.common.hibernate.util.DAOHelper;
import com.oyou.common.hibernate.util.JDBCHelper;
import com.oyou.common.hibernate.util.SessionConstants;
import com.oyou.common.spring.SpringDAOImpl;

public class CommonDAOImpl extends SpringDAOImpl implements CommonDAO {
	protected static Log log = LogFactory.getLog(CommonDAOImpl.class);
	protected static int MAX;
	protected static int queryTimeout;

	/**
	 * constructor
	 */
	public CommonDAOImpl() {
		if (getSessionFactory() != null) {
			HibernateSession.getInstance().setSessionFactory(getSessionFactory());
		}
		setMAX(HibernateSession.getInstance().getQueryMaxRows());
		setQueryTimeout(HibernateSession.getInstance().getQueryTimeout());
	}

	/**
	 * constructor and set the max result return from DB
	 * 
	 * @param max
	 */
	public CommonDAOImpl(int max, int timeout) {
		if (getSessionFactory() != null) {
			HibernateSession.getInstance().setSessionFactory(getSessionFactory());
		}
		setMAX(max);
		setQueryTimeout(timeout);
	}

	public void addBatchSQL(Statement statement, String nsql) {
		try {
			statement.addBatch(nsql);
		} catch (SQLException se) {
			se.printStackTrace();
			log.error(se.getMessage());
		}
	}

	/**
	 * delete object
	 */
	public void delete(Object obj) {
		DAOHelper.deletePersistenceObject(getSession(), obj);
	}

	public int[] executeBatchSQL(Statement statement) throws SQLException {
		int[] results = null;
		results = statement.executeBatch();
		return results;
	}

	/**
	 * execute hibernate query, return list of objects
	 * 
	 * @param query
	 * @return
	 */
	public List<Object> executeHQ(Query query) {
		return DAOHelper.executeHQLListQuery(query);
	}

	/**
	 * execute hibernate query with max results setting
	 * 
	 * @param query
	 * @param max
	 * @return
	 */
	public List<Object> executeHQ(Query query, int max) {
		return DAOHelper.executeHQLListQuery(query, max, queryTimeout);
	}

	/**
	 * execute hibernate query criteria, return list of objects
	 * 
	 * @param criteria
	 * @return
	 */
	public List<Object> executeHQC(Criteria criteria) {
		return DAOHelper.getPersistenceListByCriteria(criteria);
	}

	/**
	 * execute hibernate query criteria with max results setting
	 * 
	 * @param criteria
	 * @param max
	 * @return
	 */
	public List<Object> executeHQC(Criteria criteria, int max) {
		return DAOHelper.getPersistenceListByCriteria(criteria, max, queryTimeout);
	}

	/**
	 * execute the hibernate query criteria and return one object
	 * 
	 * @param query
	 * @return
	 */
	public Object executeHQCObject(Criteria criteria) {
		return DAOHelper.getPersistenceObjectByCriteria(criteria);
	}

	/**
	 * execute hibernate query language and return the list of objects
	 * 
	 * @param hql
	 * @return
	 */
	public List<Object> executeHQL(String hql) {
		return DAOHelper.executeHQLListQuery(getSession(), hql, SessionConstants.HIBERNATE_QUERY_MAX_ROWS, SessionConstants.HIBERNATE_QUERY_TIMEOUT);
	}

	/**
	 * execute hibernate query language and return one object
	 * 
	 * @param hql
	 * @return
	 */
	public Object executeHQLObject(String hql) {
		Query query = getSession().createQuery(hql);
		return DAOHelper.executeHQLObjectQuery(query);
	}

	/**
	 * execute the hibernate query and return one object
	 * 
	 * @param query
	 * @return
	 */
	public Object executeHQObject(Query query) {
		return DAOHelper.executeHQLObjectQuery(query);
	}

	/**
	 * execute sql
	 * 
	 * @param nsql
	 */
	public Object executeSQL(String nsql) throws SQLException {
		boolean result = false;
		Connection connection = getSession().connection();
		connection.setAutoCommit(false);
		Statement stmt = connection.createStatement();
		result = stmt.execute(nsql);
		if (result) {
			ResultSet rs = stmt.executeQuery(nsql);
			List list = this.getResultSetList(rs);
			return list;
		}
		return result;
	}

	public void flushSession() {
		Session session = getSession();
		session.flush();
		session.clear();
	}

	/**
	 * get object by type and id
	 * 
	 * @param cls
	 * @param id
	 * @reture Object
	 */
	public Object get(Class cls, Serializable id) {
		Object obj = DAOHelper.getPersistenceObjectById(getSession(), cls, id);
		return obj;
	}

	/**
	 * get the column value by name and type
	 * 
	 * @param rs
	 * @param column
	 * @param type
	 * @return
	 */
	protected Object getColumnValue(ResultSet rs, String column, int type) {
		return JDBCHelper.getColumnValue(rs, column, type);
	}

	/**
	 * Get one row data and save them to HashMap
	 * 
	 * @param rs
	 * @return
	 */
	public Map<String, Object> getColumnValues(ResultSet rs) {
		return JDBCHelper.getColumnValues(rs);
	}

	public List<Map<String, Object>> getResultSetList(ResultSet rs) {
		return JDBCHelper.getResultSetList(rs);
	}

	/**
	 * @return
	 */
	public int getMAX() {
		return MAX;
	}

	/**
	 * @return
	 */
	public int getQueryTimeout() {
		return queryTimeout;
	}

	/**
	 * @return
	 */
	public Session getSession() {
		if (this.getSessionFactory() != null) {
			return this.getSessionFactory().getCurrentSession();
		}
		return HibernateSession.getInstance().getSession();
	}

	/**
	 * insert object
	 * 
	 * @param obj
	 */
	public Serializable insert(Object obj) {
		return DAOHelper.insertPersistenceObject(getSession(), obj);
	}

	/**
	 * load object by type and id
	 * 
	 * @param cls
	 * @param id
	 */
	public Object load(Class cls, Serializable id) {
		return DAOHelper.loadPersistenceObjectById(getSession(), cls, id);
	}

	public void releaseSession() {
		Session session = getSession();
		session.close();
		HibernateSession.getInstance().releaseSession();
	}

	/**
	 * save or update object
	 * 
	 * @param obj
	 */
	public void saveOrUpdate(Object obj) {
		DAOHelper.saveOrUpdatePersistenceObject(getSession(), obj);
	}

	/**
	 * select object
	 * 
	 * @param cls
	 */
	public List<Object> select(Class cls) {
		return DAOHelper.getPersistenceList(getSession(), cls, MAX, queryTimeout);
	}

	/**
	 * set max result return from DB
	 * 
	 * @param max
	 */
	public void setMAX(int max) {
		MAX = max;
		if (MAX <= 0)
			MAX = HibernateSession.getInstance().getQueryMaxRows();
	}

	/**
	 * @param i
	 */
	public void setQueryTimeout(int i) {
		queryTimeout = i;
	}

	/**
	 * update object
	 * 
	 * @param obj
	 */
	public void update(Object obj) {
		DAOHelper.updatePersistenceObject(getSession(), obj);
	}

	public Long getObjectMaxID(Class cls) {
		Long id = null;
		Entity entity = (Entity) DAOHelper.getPersistenceObjectByCriteria(getSession().createCriteria(cls).addOrder(Order.desc("id")).setMaxResults(1));
		if (entity != null)
			id = entity.getId();
		return id;
	}

}
