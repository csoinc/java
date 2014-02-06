<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-view.tld" prefix="view"%>
<%@ page import="com.oyou.domain.blog.orm.BlogLanguageType"%>
<%@ page import="com.oyou.web.blog.GroupForm"%>

<logic:present name="groupForm" property="groups">
	<layout:collection name="groupForm" property="groups" id="blogGroup" styleClass="ARRAY" styleClass2="ARRAY1" width="100%">
	  <layout:collectionItem>
	  	<logic:equal name="blogGroup" property="blogLanguageType.id" value="<%=BlogLanguageType.MANDARIN.toString() %>">
	  		<view:message key="label.language.mandarin" />
	  	</logic:equal>
	  	<logic:equal name="blogGroup" property="blogLanguageType.id" value="<%=BlogLanguageType.ENGLISH.toString() %>">
	  		<view:message key="label.language.english" />
	  	</logic:equal>
	  	<logic:equal name="blogGroup" property="blogLanguageType.id" value="<%=BlogLanguageType.CANTONESE.toString() %>">
	  		<view:message key="label.language.cantonese" />
	  	</logic:equal>
	  </layout:collectionItem>	
	  <layout:collectionItem>
	  	 <layout:link action="/message" paramId="gid" paramName="blogGroup" paramProperty="id" titleKey="button.enter">
	  		<bean:define id="gName" name="blogGroup" property="groupName"/>
	  		<%=gName%>
	  	 </layout:link>
	  </layout:collectionItem>
	  <layout:collectionItem>
	  	 <layout:link action="/message" paramId="gid" paramName="blogGroup" paramProperty="id" titleKey="button.enter">
	  		<bean:define id="gDesc" name="blogGroup" property="description"/>
	  		<%=gDesc%>
	  	 </layout:link>
	  </layout:collectionItem>
	  <layout:collectionItem>
        <layout:img srcName="edit.gif" border="0"/>
	  	<layout:link action="group?reqCode=updateList" paramId="id" paramName="blogGroup" paramProperty="id" titleKey="button.update">
	  		<view:message key="button.update" />
	  	</layout:link>
	  </layout:collectionItem>
	  <layout:collectionItem>
		  <layout:img srcName="DIR.gif" border="0"/>	  
	  	  <layout:link action="/message" paramId="gid" paramName="blogGroup" paramProperty="id" titleKey="button.enter">
	  		<view:message key="button.enter" />
	  		<logic:notEmpty name="blogGroup" property="blogMessages">
	  			<bean:size id="mSize" name="blogGroup" property="blogMessages"/>
	  			(<%=mSize%>)
	  		</logic:notEmpty>
	  		<logic:empty name="blogGroup" property="blogMessages">
	  			(0)
	  		</logic:empty>
	  	  </layout:link>
		  <logic:equal name="blogGroup" property="blogGroupType.id" value="0">
  			<layout:img srcName="icon-star.gif" border="0"/>
		  </logic:equal>
	  </layout:collectionItem>
	</layout:collection>
</logic:present>

<layout:space/>

<logic:notEmpty name="groupForm" property="method">
	<logic:equal name="groupForm" property="method" value="login">
		<jsp:include page="/jsps/security/groupLogin.jsp" flush="true" />
	</logic:equal>
	<logic:notEqual name="groupForm" property="method" value="login">
		<layout:form action="/group" styleClass="FORM" key="form.group.title" reqCode="" focus="groupName" width="100%">
			<layout:text key="field.group.name" property="groupName" cols="110" maxlength="50" isRequired="true" />
			<layout:text key="field.group.description" property="description" cols="110" isRequired="false" />
				<div>
					<span style="width: 150px">
						<b><view:message key="label.language.option" /></b>
					</span>
					<logic:present name="groupForm" property="languageOptList">
					<span style="width: 200px">
						<html:select name="groupForm" property="languageOpt" size="1">
							<html:optionsCollection name="groupForm" property="languageOptList" value="id" label="name" />
						</html:select>
					</span>	
					</logic:present>
					&nbsp;&nbsp;
					<span style="width: 150px">
						<b><view:message key="label.group.type.option" /></b>
					</span>
					<logic:present name="groupForm" property="groupTypeOptList">
					<span style="width: 200px">
						<html:select name="groupForm" property="groupTypeOpt" size="1">
							<html:optionsCollection name="groupForm" property="groupTypeOptList" value="id" label="name" />
						</html:select>
					</span>	
					</logic:present>
				</div>	
				<logic:equal name="groupForm" property="method" value="edit">
			    <div>
			    	<span style="width: 150px">
						<b><view:message key="label.update.option" /></b>
					</span>
			    	<span style="width: 200px">
						<html:radio name="groupForm" property="updateOpt" value="<%=GroupForm.UPDATE_MESSAGE %>">
							<view:message key="label.message" />
						</html:radio>
					</span>
			    	<span style="width: 200px">
						<html:radio name="groupForm" property="updateOpt" value="<%=GroupForm.UPDATE_ALL %>">
							<view:message key="label.all" />
						</html:radio>
					</span>
				</div>		
				<layout:submit reqCode="button.update">
					<view:message key="button.update" />
				</layout:submit>
			</logic:equal>
			<logic:equal name="groupForm" property="method" value="create">
				<layout:submit reqCode="button.create">
					<view:message key="button.create" />
				</layout:submit>
			</logic:equal>
		</layout:form>
	</logic:notEqual>
</logic:notEmpty>

