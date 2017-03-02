<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-view.tld" prefix="view"%>

<logic:notEmpty name="hqlForm" property="results">
	<div class="listbox_center">
		<bean:write name="hqlForm" property="results" filter="false" />
	</div>
</logic:notEmpty>
<layout:space />

<layout:form action="/hqlQuery" styleClass="FORM" reqCode=""
	key="form.hqlquery.title" focus="hql">
	<logic:present name="hqlForm" property="databases">
		<div>
			<span>
				<html:select name="hqlForm" property="database" size="1">
					<html:optionsCollection name="hqlForm" property="databases"
						value="id" label="name" />
				</html:select>
			</span>
			<span>
				<layout:submit reqCode="button.executeHQL">
					<view:message key="button.executeHQL" />
				</layout:submit>
			</span>
		</div>
	</logic:present>
	<layout:textarea key="" property="hql" rows="3" cols="90"
		isRequired="true" />
	<layout:submit reqCode="button.executeHQL">
		<view:message key="button.executeHQL" />
	</layout:submit>
</layout:form>
