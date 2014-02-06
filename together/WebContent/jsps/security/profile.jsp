<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-view.tld" prefix="view"%>

<layout:form action="/profile" styleClass="FORM" key="form.profile.title" reqCode="button.profile" focus="email">
	<layout:text key="field.login.name" property="loginName" cols="30" maxlength="20" disabled="true" />
	<layout:text key="field.register.email.address" property="email" cols="50" maxlength="50" isRequired="true" />
	<layout:text key="field.register.firstname" property="firstname" cols="30" maxlength="30" isRequired="true" />
	<layout:text key="field.register.lastname" property="lastname" cols="30" maxlength="30" isRequired="true" />
	<layout:text key="field.register.nickname" property="nickname" cols="30" maxlength="30" isRequired="false" />
	<layout:text key="field.register.phone.home" hint="hint.register.phone.home" tooltip="hint.register.phone.home"
		property="phoneHome" cols="20" maxlength="20" isRequired="true" />
	<layout:text key="field.register.phone.cell" property="phoneCell" cols="20" maxlength="20" isRequired="false" />
	<layout:text key="field.register.unit" property="unit" cols="20" maxlength="20" isRequired="false" />
	<layout:text key="field.register.street" property="street" cols="80" maxlength="80" isRequired="false" />
	<layout:text key="field.register.city" property="city" cols="30" maxlength="50" isRequired="false" />
	<layout:text key="field.register.province" property="province" cols="30" maxlength="50" isRequired="false" />
	<layout:text key="field.register.country" property="country" cols="30" maxlength="50" isRequired="false" />
	<layout:submit property="reqCode">
		<view:message key="button.profile"/>
	</layout:submit>
</layout:form>
