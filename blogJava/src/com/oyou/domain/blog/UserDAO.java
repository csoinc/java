package com.oyou.domain.blog;

import java.io.Serializable;
import java.util.List;


import com.oyou.common.hibernate.dao.CommonDAO;
import com.oyou.domain.blog.orm.BlogUser;
import com.oyou.domain.blog.orm.BlogUserType;

public interface UserDAO extends CommonDAO {

	public BlogUser getBlogUserByHomePhoneAndEmail(String phoneHome, String email);

	public BlogUser getBlogUserByID(Serializable id);

	public BlogUser getBlogUserByLoginNameAndPassword(String loginName, String loginPassword);

	public BlogUser getBlogUserByEmailAndPassword(String email, String loginPassword);
	
	public BlogUser getBlogUserByLoginName(String loginName);

	public List<BlogUser> getBlogUsersByUserType(Long userType);

	public BlogUserType getBlogUserTypeByID(Serializable id);
	
	public boolean isNewBlogUserEmailAddress(String email);

	public boolean isNewBlogUserLoginName(String loginName);
	

}
