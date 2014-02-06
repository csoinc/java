<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/struts-view.tld" prefix="view"%>
<!-- the menu tree -->
<div id="myjquerymenu" class="jquerycssmenu">
<ul>
<li><a href="#"><view:message key="menu.bible" /></a>
  <ul>
  <li><a href="./bibleTree.do"><view:message key="menu.bible.list" /></a></li>
  <li><a href="./bibleList.do"><view:message key="menu.bible.list.selected" /></a></li>
  <li><a href="./bibleSearch.do"><view:message key="menu.bible.search" /></a></li>
  </ul>
</li>
<li><a href="#"><view:message key="menu.language" /></a>
  <ul>
  <li><a href="./index.do?lang=en"><view:message key="menu.english" /></a></li>
  <li><a href="./index.do?lang=cn"><view:message key="menu.chinese" /></a></li>
  <li><a href="./index.do?lang=tw"><view:message key="menu.cantonese" /></a></li>
  </ul>
</li>
</ul>
<!-- br style="clear: left" /-->
</div>
