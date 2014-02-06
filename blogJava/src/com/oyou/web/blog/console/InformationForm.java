package com.oyou.web.blog.console;

import com.oyou.domain.blog.orm.BlogInformation;

public class InformationForm extends ConsoleForm {
	static final long serialVersionUID = 1;
	private BlogInformation blogInformation;
	private String information;
	
	public void reset() {
		super.reset();
	}

	public BlogInformation getBlogInformation() {
		return blogInformation;
	}

	public void setBlogInformation(BlogInformation blogInformation) {
		this.blogInformation = blogInformation;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}	
	
	

}
