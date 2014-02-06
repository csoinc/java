package com.oyou.common.hibernate.dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

import com.oyou.common.domain.PageList;
import com.oyou.common.spring.SpringDAO;

public interface CommonDAO extends SpringDAO {
	public void addBatchSQL(Statement statement, String nsql);

	/**
	 * delete object
	 */
	public void delete(Object obj);

	public int[] executeBatchSQL(Statement statement) throws SQLException;

	/**
	 * execute hibernate query, return list of objects
	 * 
	 * @param query
	 * @return
	 */
	public List<Object> executeHQ(Query query);

	/**
	 * execute hibernate query with max results setting
	 * 
	 * @param query
	 * @param max
	 * @return
	 */
	public List<Object> executeHQ(Query query, int max);

	/**
	 * execute hibernate query criteria, return list of objects
	 * 
	 * @param criteria
	 * @return
	 */
	public List<Object> executeHQC(Criteria criteria);

	/**
	 * execute hibernate query criteria with max results setting
	 * 
	 * @param criteria
	 * @param max
	 * @return
	 */
	public List<Object> executeHQC(Criteria criteria, int max);

	/**
	 * execute the hibernate query criteria and return one object
	 * 
	 * @param query
	 * @return
	 */
	public Object executeHQCObject(Criteria criteria);

	/**
	 * execute hibernate query language and return the list of objects
	 * 
	 * @param hql
	 * @return
	 */
	public List<Object> executeHQL(String hql);

	/**
	 * execute hibernate query language and return one object
	 * 
	 * @param hql
	 * @return
	 */
	public Object executeHQLObject(String hql);

	/**
	 * execute the hibernate query and return one object
	 * 
	 * @param query
	 * @return
	 */
	public Object executeHQObject(Query query);

	/**
	 * execute sql
	 * 
	 * @param nsql
	 */
	public Object executeSQL(String nsql) throws SQLException;

	public void flushSession();

	/**
	 * get object by type and id
	 * 
	 * @param cls
	 * @param id
	 * @reture Object
	 */
	public Object get(Class cls, Serializable id);

	/**
	 * get the column value by name and type
	 * 
	 * @param rs
	 * @param column
	 * @param type
	 * @return
	 */
	//public Object getColumnValue(ResultSet rs, String column, int type);

	/**
	 * Get one row data and save them to HashMap
	 * 
	 * @param rs
	 * @return
	 */
	public Map<String,Object> getColumnValues(ResultSet rs);

	/**
	 * 
	 * @param rs
	 * @return
	 */
	public List<Map<String,Object>> getResultSetList(ResultSet rs);
	
	/**
	 * @return
	 */
	public int getMAX();

	/**
	 * @return
	 */
	public int getQueryTimeout();

	/**
	 * @return
	 */
	public Session getSession();

	/**
	 * insert object
	 * 
	 * @param obj
	 */
	public Serializable insert(Object obj);

	/**
	 * load object by type and id
	 * 
	 * @param cls
	 * @param id
	 */
	public Object load(Class cls, Serializable id);

	public void releaseSession();

	/**
	 * save or update object
	 * 
	 * @param obj
	 */
	public void saveOrUpdate(Object obj);

	/**
	 * select object
	 * 
	 * @param cls
	 */
	public List<Object> select(Class cls);

	/**
	 * set max result return from DB
	 * 
	 * @param max
	 */
	public void setMAX(int max);

	/**
	 * @param i
	 */
	public void setQueryTimeout(int i);

	/**
	 * update object
	 * 
	 * @param obj
	 */
	public void update(Object obj);

	/**
	 * 
	 * @param cls
	 * @return
	 */
	public Long getObjectMaxID(Class cls);
	
}
