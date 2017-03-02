<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-view.tld" prefix="view"%>

<layout:form action="/password" styleClass="FORM" key="form.password.title" 
	reqCode="button.password" focus="loginPassword">
	<layout:text key="field.login.name" property="loginName" cols="30" maxlength="20" disabled="true" />
	<layout:password key="field.password.new" property="loginPassword" cols="30" maxlength="20" isRequired="true" />
	<layout:password key="field.password.confirm" property="confirmPassword" cols="30" maxlength="20" isRequired="true" />
	<layout:submit property="reqCode">
		<view:message key="button.password"/>
	</layout:submit>
</layout:form>

