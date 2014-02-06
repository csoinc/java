<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

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
<li><a href="./blogSearch.do?pageList.word=joy">Joy</a></li>
<li><a href="./blogSearch.do?pageList.word=new%20life">New Life</a></li>
<li><a href="./blogSearch.do?pageList.word=peace">Peace</a></li>
<li><a href="./blogSearch.do?pageList.word=love">Love</a></li>
<li><a href="./blogSearch.do?pageList.word=blessing">Blessing</a></li>
<li><a href="./blogSearch.do?pageList.word=grace">Grace</a></li>
<li><a href="./blogSearch.do?pageList.word=faithfulness">Faithfulness</a></li>
<li><a href="./blogSearch.do?pageList.word=仁爱">仁爱</a></li>
<li><a href="./blogSearch.do?pageList.word=新生命">新生命</a></li>
<li><a href="./blogSearch.do?pageList.word=蒙福">蒙福</a></li>
<li><a href="./blogSearch.do?pageList.word=喜乐">喜乐</a></li>
<li><a href="./blogSearch.do?pageList.word=恩典">恩典</a></li>
<li><a href="./blogSearch.do?pageList.word=平安">平安</a></li>
<li><a href="./blogSearch.do?pageList.word=信实">信实</a></li>
</ul>

<div class='featurebox_side'>Click the above items to view the related articles.</div>

