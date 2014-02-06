<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/struts-view.tld" prefix="view"%>
<view:errors />
<!-- the menu tree -->
<div id="myjquerymenu" class="jquerycssmenu">
<ul>
<li><a href="#"><view:message key="menu.user" /></a>
  <ul>
  <li><a href="./logout.do"><view:message key="menu.login" /></a></li>
  <li><a href="./register.do"><view:message key="menu.register" /></a></li>
  <li><a href="./password.do"><view:message key="menu.password" /></a></li>
  <li><a href="./forgotPassword.do"><view:message key="menu.password.forgot" /></a></li>
  <li><a href="./profile.do"><view:message key="menu.profile" /></a></li>
  <li><a href="./logout.do"><view:message key="menu.logout" /></a></li>
  </ul>
</li>
<li><a href="#"><view:message key="menu.function" /></a>
  <ul>
  <li><a href="./blogSearch.do"><view:message key="menu.search" /></a></li>
  <li><a href="./emailGroup.do"><view:message key="menu.user.email" /></a></li>
  <li><a href="./gwt/org.oyosoft.gwt.blog.Blog/Blog.html"><view:message key="menu.blogger" /></a></li>
  </ul>
</li>
<li><a href="#"><view:message key="menu.language" /></a>
  <ul>
  <li><a href="./language.do?lang=en"><view:message key="menu.english" /></a></li>
  <li><a href="./language.do?lang=cn"><view:message key="menu.chinese" /></a></li>
  <li><a href="./language.do?lang=tw"><view:message key="menu.cantonese" /></a></li>
  </ul>
</li>
</ul>
<!-- br style="clear: left" /-->
</div>
