<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-view.tld" prefix="view"%>

<logic:present name="messageSearchForm" property="pageList.resultSet">
	<layout:collection name="messageSearchForm" property="pageList.resultSet" id="blogMessage" styleClass="ARRAY"
		styleClass2="ARRAY1" width="100%">
		<layout:collectionItem>
			
   		  <bean:write name="blogMessage" property="id" filter="false"/>:
   		  <b><bean:write name="blogMessage" property="title" filter="false"/></b><br>
   		  <bean:write name="blogMessage" property="summary" filter="false"/><br>
  	      <logic:equal name="blogMessage" property="blogMessageType.name" value="Image">
  	      	<logic:equal name="messageSearchForm" property="imageAsIcon" value="true">
   		      <layout:link action="/blogReplySearch" paramId="mid" paramName="blogMessage" paramProperty="id">
  		    	<html:img action="/icon" paramId="mid" paramName="blogMessage" paramProperty="id" border="0"/>
  		      </layout:link>	
			</logic:equal>
  	      	<logic:notEqual name="messageSearchForm" property="imageAsIcon" value="true">
  		    	<html:img action="/image" paramId="mid" paramName="blogMessage" paramProperty="id" border="0"/>
			</logic:notEqual>
   		    <bean:write name="blogMessage" property="message" filter="false"/>
   		    <layout:link action="/blogReplySearch" paramId="mid" paramName="blogMessage" paramProperty="id" titleKey="button.download">
   		      <layout:img srcName="HTML.gif" border="0"/>
			  <bean:write name="blogMessage" property="uploadFile" filter="false"/>
	  		  <view:message key="button.download" />
	        </layout:link>
  	      </logic:equal>
	      
		  <logic:equal name="blogMessage" property="blogMessageType.name" value="Link">
	        <bean:write name="blogMessage" property="message" filter="false"/>
			<layout:img srcName="HTML.gif" border="0"/>
			<bean:write name="blogMessage" property="linkURL" filter="false"/>
            <view:link action="link" paramId="mid" paramName="blogMessage" paramProperty="id" target="_blank" titleKey="button.enter">
	          <view:message key="button.enter" />
            </view:link>
             </logic:equal>

            <logic:equal name="blogMessage" property="blogMessageType.name" value="Text">
 	            <bean:write name="blogMessage" property="message" filter="false"/>
 	            <layout:space/>
             </logic:equal>

           <logic:equal name="blogMessage" property="blogMessageType.name" value="Music">
            <bean:write name="blogMessage" property="message" filter="false"/>
			<layout:img srcName="HTML.gif" border="0"/>
            <layout:link action="link" paramId="mid" paramName="blogMessage" paramProperty="id" target="_blank" titleKey="button.download">
			  <bean:write name="blogMessage" property="uploadFile" filter="false"/>
	          <view:message key="button.download" />
            </layout:link>
          </logic:equal>

          <logic:equal name="blogMessage" property="blogMessageType.name" value="Word">
            <bean:write name="blogMessage" property="message" filter="false"/>
			<layout:img srcName="HTML.gif" border="0"/>
            <layout:link action="link" paramId="mid" paramName="blogMessage" paramProperty="id" target="_blank" titleKey="button.download">
			  <bean:write name="blogMessage" property="uploadFile" filter="false"/>
	          <view:message key="button.download" />
            </layout:link>
             </logic:equal>

          <logic:equal name="blogMessage" property="blogMessageType.name" value="HTML">
            <bean:write name="blogMessage" property="message" filter="false"/>
			<layout:img srcName="HTML.gif" border="0"/>
            <layout:link action="link" paramId="mid" paramName="blogMessage" paramProperty="id" target="_blank" titleKey="button.detail">
			  <bean:write name="blogMessage" property="uploadFile" filter="false"/>
	          <view:message key="button.detail" />
            </layout:link>
             </logic:equal>

          <logic:equal name="blogMessage" property="blogMessageType.name" value="Plain">
			<bean:write name="blogMessage" property="message" filter="false"/>
			<layout:img srcName="HTML.gif" border="0"/>
			<layout:link action="link" paramId="mid" paramName="blogMessage" paramProperty="id" target="_blank" titleKey="button.detail">
			  <bean:write name="blogMessage" property="uploadFile" filter="false"/>
			  <view:message key="button.detail" />
			</layout:link>
		 </logic:equal>
		
		 <logic:equal name="blogMessage" property="blogMessageType.name" value="File">
			<bean:write name="blogMessage" property="message" filter="false"/>
			<layout:img srcName="HTML.gif" border="0"/>
			<layout:link action="link" paramId="mid" paramName="blogMessage" paramProperty="id" target="_blank" titleKey="button.download">
			  <bean:write name="blogMessage" property="uploadFile" filter="false"/>
			  <view:message key="button.download" />
			</layout:link>
		 </logic:equal>
			
 	     <layout:space/>
			<layout:space />

			<bean:write name="blogMessage" property="updateTime" />
			<bean:write name="blogMessage" property="blogUser.nickname" />

			<logic:notEmpty name="blogMessage" property="blogMessageStatistic">
				<small> (<view:message key="label.viewed" /> <bean:write name="blogMessage"
					property="blogMessageStatistic.viewTimes" /> <view:message key="label.times" /> <view:message key="label.updated" />
				<bean:write name="blogMessage" property="blogMessageStatistic.updateTimes" /> <view:message key="label.times" />) </small>
			</logic:notEmpty>

			<layout:space />

			<layout:link action="message?reqCode=updateList" paramId="id" paramName="blogMessage" paramProperty="id"
				titleKey="button.update">
				<layout:img srcName="edit.gif" border="0" />
				<view:message key="button.update" />
			</layout:link>

			<layout:link action="/blogReplySearch" paramId="mid" paramName="blogMessage" paramProperty="id" titleKey="button.reply">
		  		<layout:img srcName="DIR.gif" border="0"/>
				<view:message key="button.reply" />
			</layout:link>
			<logic:notEmpty name="blogMessage" property="blogReplyMessages">
				<bean:size id="mSize" name="blogMessage" property="blogReplyMessages" />
		  		(<%=mSize%>)
		    </logic:notEmpty>
			<logic:empty name="blogMessage" property="blogReplyMessages">
		  		(0)
		    </logic:empty>
		    <logic:equal name="blogMessage" property="status" value="true">
 		      <view:message key="label.published"/>		  
		    </logic:equal>		  
		    <logic:equal name="blogMessage" property="status" value="false">
		      <font color="red"><view:message key="label.private"/></font>		  
		    </logic:equal>
			&nbsp;&nbsp;
			<layout:img srcName="arrow-right.gif" border="0" />
			<layout:link action="/message" paramId="gid" paramName="blogMessage" paramProperty="blogGroup.id"
				titleKey="button.enter">
				<bean:write name="blogMessage" property="blogGroup.groupName" />
			</layout:link>

			<layout:space />
		</layout:collectionItem>
	</layout:collection>
	<hr>
	<div id="pages" align="center">
	<div id="links"></div>
	</div>
	<script type="text/javascript">
	blogger.initBlogger(
		0,
		0);  
    blogger.createPageLinksElements(
    	"pages",
    	"links",
    	<bean:write name="messageSearchForm" property="pageList.total"/>,
    	<bean:write name="messageSearchForm" property="pageList.number"/>,
    	10,
    	"search",
    	"blogSearch.do");
</script>
</logic:present>
<layout:space />

<layout:form action="/blogSearch" reqCode="" width="100%">
	<div>
		<span> 
			<b><view:message key="label.list.option" /></b>
		</span> 
		<logic:present name="messageSearchForm" property="imageAsIconOptList">
		<span>
			<html:select name="messageSearchForm" property="imageAsIconOpt" size="1">
				<html:optionsCollection name="messageSearchForm" property="imageAsIconOptList" value="id" label="name" />
			</html:select>
		</span>
		</logic:present> 
		<logic:present name="messageSearchForm" property="pageMaxLinesOptList">
		<span>
			<html:select name="messageSearchForm" property="pageMaxLinesOpt" size="1">
				<html:optionsCollection name="messageSearchForm" property="pageMaxLinesOptList" value="id" label="name" />
			</html:select>
		</span>
		</logic:present> 
		<logic:present name="messageSearchForm" property="orderByOptList">
		<span>
			<html:select name="messageSearchForm" property="orderByOpt" size="1">
				<html:optionsCollection name="messageSearchForm" property="orderByOptList" value="id" label="name" />
			</html:select>
		</span>
		</logic:present>
	</div>
	<layout:text key="field.input.word" property="pageList.word" cols="45" maxlength="150" />
	<layout:submit reqCode="button.search">
		<view:message key="button.search" />
	</layout:submit>
</layout:form>
