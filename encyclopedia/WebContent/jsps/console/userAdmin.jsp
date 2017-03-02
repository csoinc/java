<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-view.tld" prefix="view"%>

<div class="listbox_center">
<layout:form action="/userAdmin" reqCode="update" styleClass="PANEL">
<div>
	<layout:datagrid property="datagrid" selectionAllowed="true" multipleSelectionAllowed="false" model="datagrid" indexId="blogUser">
		<layout:datagridColumn title="ID" property="id" />
		<layout:datagridColumn title="Login Name" property="loginName" />
		<layout:datagridColumn title="Firstname" property="firstname" />			
		<layout:datagridColumn title="Email" property="email" />			
		<layout:datagridColumn title="Access Time" property="accessTime" />			
		<layout:datagridColumn title="User Type" property="blogUserType.name" />			
		<layout:datagridColumn title="Home Phone" property="phoneHome" />			
		<layout:datagridColumn title="Viewed Times" property="blogUserStatistic.viewTimes" />			
		<!-- layout:datagridColumn title="Status" property="status" /-->			
	</layout:datagrid>
	<!-- span-->
		<!-- layout:submit reqCode="button.update" -->
			<!-- view:message key="button.update" /-->
		<!-- /layout:submit-->
	<!-- /span-->
</div>
</layout:form>
</div>
