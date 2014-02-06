<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/struts-view.tld" prefix="view"%>
<view:errors />
<!-- the menu tree -->
<div id="myjquerymenu" class="jquerycssmenu">
<ul>
<li><a href="#"><view:message key="menu.login" /></a>
  <ul>
  <li><a href="./logout.do"><view:message key="menu.login" /></a></li>
  <li><a href="./forgotPassword.do"><view:message key="menu.password.forgot" /></a></li>
  </ul>
</li>

<li><a href="./emailGroup.do"><view:message key="menu.user.email" /></a>
</li>

<li><a href="./language.do?lang=en"><view:message key="menu.english" /></a>
</li>

<li><a href="./language.do?lang=cn"><view:message key="menu.chinese" /></a>
</li>

<li><a href="#"><view:message key="menu.logout" /></a>
  <ul>
  <li><a href="./password.do"><view:message key="menu.password" /></a></li>
  <li><a href="./logout.do"><view:message key="menu.logout" /></a></li>
  </ul>
</li>
</ul>

<!-- br style="clear: left" /-->
</div>
