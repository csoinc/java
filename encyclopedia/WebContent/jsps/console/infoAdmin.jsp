<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-view.tld" prefix="view"%>
<%@ page import="com.oyou.domain.blog.orm.BlogInformation" %>

<bean:define id="blogInformation" name="informationForm" property="blogInformation"/>
<div align="left">
<logic:equal name="blogInformation" property="informationType" value="<%=BlogInformation.SQL_TYPE%>">
	<b><view:message key="menu.sqlCommander.buffer" /></b>
    <layout:space/>
  	<bean:write name="blogInformation" property="information" filter="false"/>
    <layout:space/>
    <layout:space/>
    <layout:space/>
</logic:equal>
<logic:equal name="blogInformation" property="informationType" value="<%=BlogInformation.HSQL_TYPE%>">
	<b><view:message key="menu.hqlQuery.buffer" /></b>
    <layout:space/>
  	<bean:write name="blogInformation" property="information" filter="false"/>
    <layout:space/>
    <layout:space/>
    <layout:space/>
</logic:equal>
<logic:equal name="blogInformation" property="informationType" value="<%=BlogInformation.LOG_TYPE%>">
	<b><view:message key="menu.log.buffer" /></b>
    <layout:space/>
  	<bean:write name="blogInformation" property="information" filter="false"/>
    <layout:space/>
    <layout:space/>
    <layout:space/>
</logic:equal>
</div>

<logic:equal name="informationForm" property="blogInformation.informationType" value="<%=BlogInformation.ANNOUNCEMENT_TYPE%>">
<layout:form action="/infoAdmin" styleClass="FORM" reqCode="" key="form.information.title"
	focus="information">
	<b><view:message key="field.information.announcement" /></b>
	<layout:textarea key="" property="information" rows="15" cols="90" isRequired="true" />
	<layout:submit reqCode="button.update">
		<view:message key="button.update" />
	</layout:submit>
</layout:form>
</logic:equal>

<logic:equal name="informationForm" property="blogInformation.informationType" value="<%=BlogInformation.ABOUTUS_TYPE%>">
<layout:form action="/infoAdmin" styleClass="FORM" reqCode="" key="form.information.title"
	focus="information">
	<b><view:message key="field.information.aboutus" /></b>
	<layout:textarea key="" property="information" rows="15" cols="90" isRequired="true" />
	<layout:submit reqCode="button.update">
		<view:message key="button.update" />
	</layout:submit>
</layout:form>
</logic:equal>

<logic:equal name="informationForm" property="blogInformation.informationType" value="<%=BlogInformation.HELP_TYPE%>">
<layout:form action="/infoAdmin" styleClass="FORM" reqCode="" key="form.information.title"
	focus="information">
	<b><view:message key="field.information.help" /></b>
	<layout:textarea key="" property="information" rows="15" cols="90" isRequired="true" />
	<layout:submit reqCode="button.update">
		<view:message key="button.update" />
	</layout:submit>
</layout:form>
</logic:equal>
