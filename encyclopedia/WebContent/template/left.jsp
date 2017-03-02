<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/struts-view.tld" prefix="view"%>

<h3>Site Search</h3>
<layout:form action="/blogSearch" reqCode="">
	<layout:text key="" name="messageSearchForm" property="pageList.word" cols="14" maxlength="28" />
	<layout:submit reqCode="button.search">
		<view:message key="button.search" />
	</layout:submit>
</layout:form> 

<h3>Hot Articles</h3>
<ul>
<li><a href="./blogSearch.do?pageList.word=eclipse">Eclipse</a></li>
<li><a href="./blogSearch.do?pageList.word=java">Java</a></li>
<li><a href="./blogSearch.do?pageList.word=php">PHP</a></li>
<li><a href="./blogSearch.do?pageList.word=web2">Web2</a></li>
<li><a href="./blogSearch.do?pageList.word=websphere">WebSphere</a></li>
<li><a href="./blogSearch.do?pageList.word=mysql">MySQL</a></li>
<li><a href="./blogSearch.do?pageList.word=oracle">Oracle</a></li>
<li><a href="./blogSearch.do?pageList.word=ajax">AJAX</a></li>
<li><a href="./blogSearch.do?pageList.word=javascript">JavaScript</a></li>
<li><a href="./blogSearch.do?pageList.word=web%20service">Web Service</a></li>
<li><a href="./blogSearch.do?pageList.word=persistence">Persistence</a></li>
<li><a href="./blogSearch.do?pageList.word=ubuntu">Ubuntu</a></li>
<li><a href="./blogSearch.do?pageList.word=unix">Unix</a></li>
</ul>

<div class='featurebox_side'>Click the above items to view the related articles.</div>

