<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/struts-view.tld" prefix="view"%>

<a href="./uploadAdmin.do"><view:message key="menu.upload.admin" /></a> | 
<a href="./userAdmin.do"><view:message key="menu.user.admin" /></a> | 
<a href="./email.do"><view:message key="menu.console.email" /></a> |
<a href="./sqlCommander.do"><view:message key="menu.sqlCommander" /></a> |
<a href="./infoAdmin.do?id=3"><view:message key="menu.sqlCommander.buffer" /></a> |
<a href="./hqlQuery.do"><view:message key="menu.hqlQuery" /></a> |
<a href="./infoAdmin.do?id=2"><view:message key="menu.hqlQuery.buffer" /></a> |
<a href="./infoAdmin.do?id=6"><view:message key="menu.log.buffer" /></a> |
<a href="./infoAdmin.do?id=1"><view:message key="menu.announcement" /></a> |
<a href="./infoAdmin.do?id=4"><view:message key="menu.aboutus" /></a> |
<a href="./infoAdmin.do?id=5"><view:message key="menu.help" /></a>
<br>
<hr>
<layout:link styleClass="barmenu" action="/sqlCommander" titleKey="menu.sqlCommander">
	<view:message key="menu.console" />
</layout:link>|<a href=""><view:message key="foot.application.title" /></a>

<!-- jsp:include page="/gwt/org.oyosoft.gwt.banner.Banner/Banner.html" flush="true" /-->

