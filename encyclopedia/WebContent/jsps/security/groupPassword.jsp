<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-view.tld" prefix="view"%>

<layout:form action="/forgotPassword" styleClass="FORM" key="form.password.forgot.title" reqCode="button.search" focus="email" width="799">
	<layout:text key="field.register.email.address" property="email" cols="50" maxlength="50" isRequired="true" />
	<layout:text key="field.register.phone.home" hint="hint.register.phone.home" tooltip="hint.register.phone.home"
		property="phoneHome" cols="40" maxlength="20" isRequired="false" />
	<layout:submit property="reqCode">
		<view:message key="button.search" />
	</layout:submit>
</layout:form>

