<%@page language="java"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-view.tld" prefix="view"%>

<TITLE>STRUTS TAGS</TITLE>

<layout:html styleClass="FORM" key="form.register.title">

<layout:form action="/register" reqCode="button.register">
	<layout:tabs styleClass="FORM" width="400" >
		<layout:tab key="<U>L</U>ogon" width="50">
			<layout:text key="field.login.name" property="loginName" cols="30" maxlength="20" isRequired="true" />
			<layout:text key="field.register.email.address" property="email" cols="50" maxlength="50" isRequired="true" />
		</layout:tab>
		<layout:tab key="<U>N</U>ame" width="50">
			<layout:text key="field.register.firstname" property="firstnameEN" cols="30" maxlength="30" isRequired="true" />
			<layout:text key="field.register.lastname" property="lastnameEN" cols="30" maxlength="30" isRequired="true" />
		</layout:tab>
		<layout:tab key="<U>P</U>hone" width="50">
			<layout:text key="field.register.phone.home" hint="hint.register.phone.home" tooltip="hint.register.phone.home"
				property="phoneHome" cols="20" maxlength="20" isRequired="true" />
			<layout:text key="field.register.phone.cell" property="phoneCell" cols="20" maxlength="20" isRequired="false" />
		</layout:tab>
		<layout:tab key="<U>A</U>ddress" width="50">
			<layout:text key="field.register.unit" property="unit" cols="20" maxlength="20" isRequired="false" />
			<layout:text key="field.register.street" property="street" cols="80" maxlength="80" isRequired="false" />
			<layout:text key="field.register.city" property="city" cols="30" maxlength="50" isRequired="false" />
			<layout:text key="field.register.province" property="province" cols="30" maxlength="50" isRequired="false" />
			<layout:text key="field.register.country" property="country" cols="30" maxlength="50" isRequired="false" />
		</layout:tab>
	</layout:tabs>
	<layout:submit property="reqCode">
		<bean:message key="button.register" />
	</layout:submit>

</layout:form>
</layout:html>
