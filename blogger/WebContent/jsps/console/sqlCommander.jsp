<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-view.tld" prefix="view"%>

<logic:notEmpty name="sqlCommanderForm" property="results">
    <div class="listbox_center">
        <bean:write name="sqlCommanderForm" property="results" filter="false" />
    </div>
</logic:notEmpty>
<layout:space/>

<layout:form action="/sqlCommander" styleClass="FORM" reqCode="" key="form.sqlcommander.title"
	focus="commanders">
	<logic:present name="sqlCommanderForm" property="databases">
		<div>
			<span>
				<html:select name="sqlCommanderForm" property="database" size="1">
					<html:optionsCollection name="sqlCommanderForm" property="databases" value="id" label="name" />
				</html:select>
			</span>
			<span>
				<layout:submit reqCode="button.execute">
					<view:message key="button.execute" />
				</layout:submit>
			</span>
		</div>
	</logic:present>
	<layout:textarea key="" property="commanders" rows="25" cols="90" isRequired="true" />
	<layout:submit reqCode="button.executeSQL">
		<view:message key="button.executeSQL" />
	</layout:submit>
</layout:form>
