<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/struts-view.tld" prefix="view"%>

<h3>Bible Search</h3>
<layout:form action="/bibleSearch" reqCode="">
	<layout:text key="" name="bibleSearchForm" property="pageList.what" cols="14" maxlength="28" />
	<layout:submit reqCode="button.search">
		<view:message key="button.search" />
	</layout:submit>
</layout:form> 
