<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-view.tld" prefix="view"%>
<%@ page import="com.oyou.web.bible.ListingForm" %>

<center>

<html:form action="/bibleTree.do?reqCode=update">
	<div style="width: 700px" align="center">
		<!-- bean:define id="english" name="listingForm" property="english" type="String" /-->
		<span style="width: 150px">
			<html:checkbox name="listingForm" property="english" value="<%=ListingForm.LANGUAGE_ENGLISH %>">
				<view:message key="label.language.english"/>
			</html:checkbox>
		</span>
		<span style="width: 150px">
			<html:checkbox name="listingForm" property="chineseCN" value="<%=ListingForm.LANGUAGE_CHINESE_CN %>">
				<view:message key="label.language.chinese.cn"/>
			</html:checkbox>
		</span>
		<span style="width: 150px">
			<html:checkbox name="listingForm" property="chineseTW" value="<%=ListingForm.LANGUAGE_CHINESE_TW %>">
				<view:message key="label.language.chinese.tw"/>
			</html:checkbox>
		</span>
		<span style="width: 150px">
			<layout:submit reqCode="button.update">
				<view:message key="button.read" />
			</layout:submit>
		</span>
	</div>
</html:form>

</center>

<div>
	<layout:write name="listingForm" property="chapter" styleClass="LABEL"/>
</div>

<layout:grid>
	<layout:panel styleClass="FORM">

		<logic:equal name="listingForm" property="menu" value="<%=ListingForm.MENU_BIBLE_CHINESE_TW %>">
			<small><layout:treeview name="bible_chinese_tw"/></small>
		</logic:equal>
		<logic:equal name="listingForm" property="menu" value="<%=ListingForm.MENU_BIBLE_CHINESE_CN %>">
			<small><layout:treeview name="bible_chinese_cn"/></small>
		</logic:equal>
		<logic:equal name="listingForm" property="menu" value="<%=ListingForm.MENU_BIBLE_ENGLISH %>">
			<small><layout:treeview name="bible_english"/></small>
		</logic:equal>
			
	</layout:panel>
		<logic:present name="listingForm" property="lines">
			<layout:collection name="listingForm" property="lines" id="bibleLine" indexId="id" styleClass="FORM">
		  		<layout:collectionItem>
					<logic:equal name="listingForm" property="english" value="<%=ListingForm.LANGUAGE_ENGLISH %>">
			  			<small>
							<layout:write name="bibleLine" property="chapter"/>
							<layout:cell>:</layout:cell>
							<layout:write name="bibleLine" property="section"/>
							<layout:cell>&nbsp;</layout:cell>
						</small>
						<layout:write name="bibleLine" property="content"/>
						<layout:space/>
					</logic:equal>
					<logic:equal name="listingForm" property="chineseCN" value="<%=ListingForm.LANGUAGE_CHINESE_CN %>">
			  			<small>
							<layout:write name="bibleLine" property="chapter"/>
							<layout:cell>:</layout:cell>
							<layout:write name="bibleLine" property="section"/>
							<layout:cell>&nbsp;</layout:cell>
						</small>
						<layout:write name="bibleLine" property="contentCN"/>
						<layout:space/>
					</logic:equal>
					<logic:equal name="listingForm" property="chineseTW" value="<%=ListingForm.LANGUAGE_CHINESE_TW %>">
			  			<small>
							<layout:write name="bibleLine" property="chapter"/>
							<layout:cell>:</layout:cell>
							<layout:write name="bibleLine" property="section"/>
							<layout:cell>&nbsp;</layout:cell>
						</small>
						<layout:write name="bibleLine" property="contentTW"/>
					</logic:equal>
				</layout:collectionItem>
		  </layout:collection>
	</logic:present>
</layout:grid>
<layout:space/>

<center>

<html:form action="/bibleTree.do?reqCode=update">
	<div style="width: 700px" align="center">
		<!-- bean:define id="english" name="listingForm" property="english" type="String" /-->
		<span style="width: 150px">
			<html:checkbox name="listingForm" property="english" value="<%=ListingForm.LANGUAGE_ENGLISH %>">
				<view:message key="label.language.english"/>
			</html:checkbox>
		</span>
		<span style="width: 150px">
			<html:checkbox name="listingForm" property="chineseCN" value="<%=ListingForm.LANGUAGE_CHINESE_CN %>">
				<view:message key="label.language.chinese.cn"/>
			</html:checkbox>
		</span>
		<span style="width: 150px">
			<html:checkbox name="listingForm" property="chineseTW" value="<%=ListingForm.LANGUAGE_CHINESE_TW %>">
				<view:message key="label.language.chinese.tw"/>
			</html:checkbox>
		</span>
		<span style="width: 150px">
			<layout:submit reqCode="button.update">
				<view:message key="button.read" />
			</layout:submit>
		</span>
	</div>
</html:form>

</center>

