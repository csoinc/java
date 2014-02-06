<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/struts-view.tld" prefix="view"%>

<a href="https://oyou.from-ca.com/">OYOU</a> | 
<a href="https://oyou.blogsite.org/">Encyclopedia</a> |
<a href="http://owenou.tripod.com/">OwenOuyang</a>
<br>
<hr>
<layout:link styleClass="barmenu" action="/sqlCommander" titleKey="menu.sqlCommander">
	<view:message key="menu.console" />
</layout:link>|<view:message key="foot.application.title" />

