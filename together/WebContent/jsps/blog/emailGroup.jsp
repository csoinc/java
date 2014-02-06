<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-view.tld" prefix="view"%>

<layout:form action="/emailGroup" styleClass="FORM" reqCode="" key="form.email.title" focus="content">
	<logic:present name="emailGroupForm" property="users">
		<div>
			<span style="width: 120px">
				<b><view:message key="label.email.to" /></b>
			</span>
			<span style="width: 300px">
				<html:select name="emailGroupForm" property="usersSelected" size="5" multiple="true">
					<html:optionsCollection name="emailGroupForm" property="users" value="id" label="name" />
				</html:select>
			</span>
			<span style="width: 100px">
				<layout:submit reqCode="button.send">
					<view:message key="button.send" />
				</layout:submit>
			</span>
		</div>
	</logic:present>
	<layout:text key="field.email.subject" property="subject" cols="70" isRequired="true" />
	<layout:textarea key="" property="content" rows="20" cols="90" isRequired="true" />
	<layout:submit reqCode="button.send"><view:message key="button.send" /></layout:submit>
</layout:form>
