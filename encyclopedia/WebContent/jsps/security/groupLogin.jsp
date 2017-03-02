<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-view.tld" prefix="view"%>

<layout:form action="/login" styleClass="FORM" key="form.login.title" reqCode="button.login" focus="loginName" width="799">
	<layout:link styleClass="barmenu" action="/register">
		<view:message key="menu.register"/>
	</layout:link>
	<layout:text key="field.login.name" property="loginName" cols="40" maxlength="20" isRequired="true" />
	<layout:password key="field.login.password" property="loginPassword" cols="40" maxlength="20" isRequired="true" />
	<layout:submit property="reqCode">
		<view:message key="button.login"/>
	</layout:submit>
</layout:form>
