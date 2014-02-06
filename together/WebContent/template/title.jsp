<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/struts-view.tld" prefix="view"%>
<!-- the menu tab -->
<div class="animatedtabs">
<ul>
<li id="album"><a href="./group.do?tid=1" title="<view:message key="menu.album" />"><span><view:message key="menu.album" /></span></a></li>
<li id="blogger"><a href="./group.do?tid=2" title="<view:message key="menu.cell" />"><span><view:message key="menu.cell" /></span></a></li>
<li id="business"><a href="./group.do?tid=3" title="<view:message key="menu.church" />"><span><view:message key="menu.church" /></span></a></li>
<li id="careers"><a href="./group.do?tid=4" title="<view:message key="menu.fellowship" />"><span><view:message key="menu.fellowship" /></span></a></li>
<li id="community"><a href="./group.do?tid=5" title="<view:message key="menu.generic" />"><span><view:message key="menu.generic" /></span></a></li>
<li id="news"><a href="./group.do?tid=6" title="<view:message key="menu.worship" />"><span><view:message key="menu.worship" /></span></a></li>
</ul>
</div>
