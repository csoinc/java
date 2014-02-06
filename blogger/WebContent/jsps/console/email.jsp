<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-view.tld" prefix="view"%>

<layout:form action="/email" styleClass="FORM" reqCode="" key="form.email.title"
	focus="content">
	<logic:present name="emailForm" property="users">
		<div>
			<span>
				<html:select name="emailForm" property="usersSelected" size="5" multiple="true">
					<html:optionsCollection name="emailForm" property="users" value="id" label="name" />
				</html:select>
			</span>
			<span>
				<layout:submit reqCode="button.send">
					<view:message key="button.send" />
				</layout:submit>
			</span>
		</div>
	</logic:present>
	<div>
		<span>
			<layout:text key="field.email.subject" property="subject" cols="70" isRequired="false" />
		</span>	
	</div>
	<div>
		<span>
			<layout:textarea key="" property="content" rows="20" cols="90" isRequired="true" />
		</span>	
	</div>
	<div>
		<span>
			<layout:submit reqCode="button.send"><view:message key="button.send" /></layout:submit>
		</span>	
	</div>
</layout:form>
