<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-view.tld" prefix="view"%>

<h1>Bible Version</h1>
<ul>
	<li><a href="./language.do?lang=en"><view:message key="label.english.version" /><br></a></li>
	<li><a href="./language.do?lang=cn"><view:message key="label.mandarin.version" /><br></a></li>
	<li><a href="./language.do?lang=tw"><view:message key="label.cantonese.version" /></a></li>
</ul>
