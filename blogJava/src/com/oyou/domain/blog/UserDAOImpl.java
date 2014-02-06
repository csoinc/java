package com.oyou.domain.blog;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;

import com.oyou.common.hibernate.dao.CommonDAOImpl;
import com.oyou.domain.blog.orm.BlogUser;
import com.oyou.domain.blog.orm.BlogUserType;

public class UserDAOImpl extends CommonDAOImpl implements UserDAO {
	private static final Log log = LogFactory.getLog(UserDAOImpl.class);

	public BlogUser getBlogUserByHomePhoneAndEmail(String phoneHome, String email) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bu from BlogUser as bu");
		sb.append(" where bu.phoneHome = :phoneHome");
		sb.append(" or bu.email = :email");
		Query query = getSession().createQuery(sb.toString());
		query.setString("phoneHome", phoneHome);
		query.setString("email", email);
		return (BlogUser) executeHQObject(query);
	}

	public BlogUser getBlogUserByID(Serializable id) {
		return (BlogUser)load(BlogUser.class, id);
	}

	public BlogUser getBlogUserByLoginNameAndPassword(String loginName, String loginPassword) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bu from BlogUser as bu");
		sb.append(" where bu.loginName = :loginName");
		sb.append(" and bu.loginPassword = :loginPassword");
		Query query = getSession().createQuery(sb.toString());
		query.setString("loginName", loginName);
		query.setString("loginPassword", loginPassword);
		return (BlogUser) executeHQObject(query);
	}

	public BlogUser getBlogUserByEmailAndPassword(String email, String loginPassword) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bu from BlogUser as bu");
		sb.append(" where bu.email = :email");
		sb.append(" and bu.loginPassword = :loginPassword");
		Query query = getSession().createQuery(sb.toString());
		query.setString("email", email);
		query.setString("loginPassword", loginPassword);
		return (BlogUser) executeHQObject(query);
	}
	
	public BlogUser getBlogUserByLoginName(String loginName) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bu from BlogUser as bu");
		sb.append(" where bu.loginName = :loginName");
		Query query = getSession().createQuery(sb.toString());
		query.setString("loginName", loginName);
		return (BlogUser) executeHQObject(query);
	}
	
	public List getBlogUsersByUserType(Long userType) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bu from BlogUser as bu");
		sb.append(" where bu.blogUserType.id = :userType");
		sb.append(" order by bu.lastname, bu.firstname");
		Query query = getSession().createQuery(sb.toString());
		query.setLong("userType", userType);
		return executeHQ(query);
	}

	public BlogUserType getBlogUserTypeByID(Serializable id) {
		return (BlogUserType)load(BlogUserType.class, id);
	}
	
	public boolean isNewBlogUserEmailAddress(String email) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bu from BlogUser as bu");
		sb.append(" where bu.email = :email");
		Query query = getSession().createQuery(sb.toString());
		query.setString("email", email);
		List list = executeHQ(query);
		if (list.size() > 0) return false;
		else return true;
	}

	public boolean isNewBlogUserLoginName(String loginName) {
		log.debug(">>isNewBlogUserLoginName()");
		StringBuffer sb = new StringBuffer();
		sb.append("select bu from BlogUser as bu");
		sb.append(" where bu.loginName = :loginName");
		Query query = getSession().createQuery(sb.toString());
		query.setString("loginName", loginName);
		List list = executeHQ(query);
		log.debug("<<isNewBlogUserLoginName()");
		if (list.size() > 0) return false;
		else return true;
	}
	
}
