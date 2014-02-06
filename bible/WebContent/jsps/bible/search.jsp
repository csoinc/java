<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-view.tld" prefix="view"%>
<%@ page import="com.oyou.web.bible.BibleSearchForm" %>

<html:form action="/bibleSearch">
	<div style="width: 999px" align="left">
		<span><layout:text key="" property="pageList.what" cols="25" maxlength="100"/></span>
		&nbsp;IN&nbsp;
		<span>
			<layout:suggest key="" layout="bookSuggest" layoutId="bookSuggest" property="pageList.where" cols="20" suggestAction="/bookSuggests.do" styleId="bookSuggest" maxlength="100" minWordLength="1" suggestCount="8" suggestEncoding="UTF-8" isRequired="false" />
		</span>
		&nbsp;OR&nbsp;
		<span>
			<html:select name="bibleSearchForm" property="cats" multiple="true" size="3">
				<html:optionsCollection name="bibleSearchForm" property="catList" value="id" label="name" />
			</html:select>
		</span>
		&nbsp;OR&nbsp;
		<span>
			<html:select name="bibleSearchForm" property="books" multiple="true" size="3">
				<html:optionsCollection name="bibleSearchForm" property="bookList" value="id" label="name" />
			</html:select>
		</span>
		&nbsp;&nbsp;
		<layout:submit reqCode="button.search">
			<view:message key="button.search" />
		</layout:submit>
	</div>
</html:form>	
<hr/>
<logic:present name="bibleSearchForm" property="pageList.resultSet">
	<layout:collection name="bibleSearchForm" property="pageList.resultSet" id="bibleLine" indexId="id" styleClass="FORM">
		<layout:collectionItem action="/bibleList" paramId="t,b,c" paramProperty="bibleBook.testament,bibleBook.sequence,chapter">
			<logic:equal name="bibleSearchForm" property="language" value="<%=BibleSearchForm.LANGUAGE_ENGLISH %>">
				<small>
					<layout:write name="bibleLine" property="chapter" />
					<layout:cell>:</layout:cell>
					<layout:write name="bibleLine" property="section" />
					<layout:cell>&nbsp;</layout:cell>
				</small>
				<layout:write name="bibleLine" property="content" />
				<small>
					<layout:cell>&nbsp;-</layout:cell>
					<layout:write name="bibleLine" property="bibleBook.name" />
					<layout:cell></layout:cell>
				</small>
			</logic:equal>
			<logic:equal name="bibleSearchForm" property="language" value="<%=BibleSearchForm.LANGUAGE_CHINESE_CN %>">
				<small>
					<layout:write name="bibleLine" property="chapter" />
					<layout:cell>:</layout:cell>
					<layout:write name="bibleLine" property="section" />
					<layout:cell>&nbsp;</layout:cell>
				</small>
				<layout:write name="bibleLine" property="contentCN" />
				<small>
					<layout:cell>&nbsp;-</layout:cell>
					<layout:write name="bibleLine" property="bibleBook.nameCN" />
					<layout:cell></layout:cell>
				</small>
				<layout:space/>
				<small>
					<layout:write name="bibleLine" property="chapter" />
					<layout:cell>:</layout:cell>
					<layout:write name="bibleLine" property="section" />
					<layout:cell>&nbsp;</layout:cell>
				</small>
				<layout:write name="bibleLine" property="content" />
				<small>
					<layout:cell>&nbsp;-</layout:cell>
					<layout:write name="bibleLine" property="bibleBook.name" />
					<layout:cell></layout:cell>
				</small>
			</logic:equal>
			<logic:equal name="bibleSearchForm" property="language" value="<%=BibleSearchForm.LANGUAGE_CHINESE_TW %>">
				<small>
					<layout:write name="bibleLine" property="chapter" />
					<layout:cell>:</layout:cell>
					<layout:write name="bibleLine" property="section" />
					<layout:cell>&nbsp;</layout:cell>
				</small>
				<layout:write name="bibleLine" property="contentTW" />
				<small>
					<layout:cell>&nbsp;-</layout:cell>
					<layout:write name="bibleLine" property="bibleBook.nameTW" />
					<layout:cell></layout:cell>
				</small>
				<layout:space/>
				<small>
					<layout:write name="bibleLine" property="chapter" />
					<layout:cell>:</layout:cell>
					<layout:write name="bibleLine" property="section" />
					<layout:cell>&nbsp;</layout:cell>
				</small>
				<layout:write name="bibleLine" property="content" />
				<small>
					<layout:cell>&nbsp;-</layout:cell>
					<layout:write name="bibleLine" property="bibleBook.name" />
					<layout:cell></layout:cell>
				</small>
			</logic:equal>
		</layout:collectionItem>
	</layout:collection>
<br>
<div id="pages" align="center"><div id="links"></div></div>
<script type="text/javascript">
	blogger.initBlogger(
		0,
		0);  
    blogger.createPageLinksElements(
    	"pages",
    	"links",
    	<bean:write name="bibleSearchForm" property="pageList.total"/>,
    	<bean:write name="bibleSearchForm" property="pageList.number"/>,
    	10,
    	"line",
    	"bibleSearch.do");
</script>

</logic:present>

<br>

