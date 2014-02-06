<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-view.tld" prefix="view"%>
<%@ page import="com.oyou.web.bible.SelectionForm" %>

<html:form action="/bibleList">
	<div>
		<span>
			<logic:present name="selectionForm" property="languageList">
				<html:select name="selectionForm" property="languages" size="4" multiple="true" >
					<html:optionsCollection name="selectionForm" property="languageList" value="id" label="name" />
				</html:select>
			</logic:present>
		</span>
		<span>
			<logic:present name="selectionForm" property="testaments">
				<html:select name="selectionForm" property="testament" size="4" onchange="document.forms['selectionForm'].submit()">
					<html:optionsCollection name="selectionForm" property="testaments" value="id" label="name" />
				</html:select>
			</logic:present>
		</span>
		<span>
			<logic:present name="selectionForm" property="books">
				<html:select name="selectionForm" property="book" size="4" onchange="document.forms['selectionForm'].submit()">
					<html:optionsCollection name="selectionForm" property="books" value="id" label="name" />
				</html:select>
			</logic:present>
		</span>
		<span>
			<logic:present name="selectionForm" property="chapterList">
				<html:select name="selectionForm" property="chapters" size="4" multiple="true" >
					<html:optionsCollection name="selectionForm" property="chapterList" value="id" label="name" />
				</html:select>
			</logic:present>
		</span>
		<span>
			<logic:present name="selectionForm" property="chapterList">
				<layout:submit reqCode="button.update" onchange="document.forms['selectionForm'].submit()">
					<view:message key="button.read" />
				</layout:submit>
			</logic:present>
		</span>
	</div>
</html:form> 

<layout:pager maxPageItems="25">
	<logic:present name="selectionForm" property="lines">
		<layout:collection name="selectionForm" property="lines" id="bibleLine" indexId="id" styleClass="FORM">
			<layout:collectionItem>
				<logic:equal name="selectionForm" property="english" value="<%=SelectionForm.LANGUAGE_ENGLISH %>">
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
					<layout:space/>
				</logic:equal>

				<logic:equal name="selectionForm" property="chineseCN" value="<%=SelectionForm.LANGUAGE_CHINESE_CN %>">
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
				</logic:equal>
				
				<logic:equal name="selectionForm" property="chineseTW" value="<%=SelectionForm.LANGUAGE_CHINESE_TW %>">
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
				</logic:equal>
			</layout:collectionItem>
		</layout:collection>
	</logic:present>
</layout:pager>
