<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-view.tld" prefix="view"%>
<%@ page import="com.oyou.web.blog.MessageForm" %>
<%@ page import="com.oyou.common.util.StrutsSession" %>

<bean:define id="blogGroup" name="messageForm" property="blogGroup"/>
<bean:define id="blogGroupType" name="messageForm" property="blogGroup.blogGroupType"/>
<div id="msgTopLink">
	<span style="width: 200px">
    <logic:equal name="messageForm" property="region" value="<%=StrutsSession.LANGUAGE_EN%>">
		<html:link action="/group" paramId="tid" paramName="blogGroupType" paramProperty="id" titleKey="button.enter">
			<bean:write name="blogGroupType" property="name"/>
		</html:link>
	</logic:equal>
    <logic:equal name="messageForm" property="region" value="<%=StrutsSession.LANGUAGE_CN%>">
		<html:link action="/group" paramId="tid" paramName="blogGroupType" paramProperty="id" titleKey="button.enter">
			<bean:write name="blogGroupType" property="nameCN"/>
		</html:link>
	</logic:equal>
    <logic:equal name="messageForm" property="region" value="<%=StrutsSession.LANGUAGE_TW%>">
		<html:link action="/group" paramId="tid" paramName="blogGroupType" paramProperty="id" titleKey="button.enter">
			<bean:write name="blogGroupType" property="nameTW"/>
		</html:link>
	</logic:equal>
	</span>
	<span style="width: 10px">
	<layout:img srcName="arrow-right.gif" border="0"/>
	</span>
	<span style="width: 200px">
	<html:link action="/message" paramId="gid" paramName="blogGroup" paramProperty="id" titleKey="button.enter">
		<bean:write name="blogGroup" property="groupName"/>
	</html:link>
	</span>
</div>	

<logic:present name="messageForm" property="pageList.resultSet">
	<layout:collection name="messageForm" property="pageList.resultSet" id="blogMessage" styleClass="ARRAY" styleClass2="ARRAY1" width="100%">
	  <layout:collectionItem>
	  	  <layout:space/>
   		  <bean:write name="blogMessage" property="id" filter="false"/>:
   		  <b><bean:write name="blogMessage" property="title" filter="false"/></b><br>
   		  <bean:write name="blogMessage" property="summary" filter="false"/><br>
  	      <logic:equal name="blogMessage" property="blogMessageType.name" value="Image">
  	      	<logic:equal name="messageForm" property="imageAsIcon" value="true">
   		    	<layout:link action="/replyMessage" paramId="mid" paramName="blogMessage" paramProperty="id">
  		    		<html:img action="/icon" paramId="mid" paramName="blogMessage" paramProperty="id" border="0"/>
				</layout:link>
			</logic:equal>
  	      	<logic:notEqual name="messageForm" property="imageAsIcon" value="true">
  		    	<html:img action="/image" paramId="mid" paramName="blogMessage" paramProperty="id" border="0"/>
			</logic:notEqual>
   		    <bean:write name="blogMessage" property="message" filter="false"/>
   		    <layout:link action="/replyMessage" paramId="mid" paramName="blogMessage" paramProperty="id" titleKey="button.download">
   		    	<layout:img srcName="HTML.gif" border="0"/>
			  	<bean:write name="blogMessage" property="uploadFile" filter="false"/>
	  		  	<view:message key="button.download" />
	        </layout:link>
               <layout:space/>   		    
  	      </logic:equal>
  	      
		  <logic:equal name="blogMessage" property="blogMessageType.name" value="Link">
	        <bean:write name="blogMessage" property="message" filter="false"/>
			<bean:write name="blogMessage" property="linkURL" filter="false"/>
            <view:link action="link" paramId="mid" paramName="blogMessage" paramProperty="id" target="_blank" titleKey="button.enter">
			  <layout:img srcName="HTML.gif" border="0"/>
	          <view:message key="button.enter" />
            </view:link>
               <layout:space/>
             </logic:equal>
              <logic:equal name="blogMessage" property="blogMessageType.name" value="Text">
 	            <bean:write name="blogMessage" property="message" filter="false"/>
 	            <layout:space/>
             </logic:equal>
              <logic:equal name="blogMessage" property="blogMessageType.name" value="Music">
            <bean:write name="blogMessage" property="message" filter="false"/>
            <layout:link action="link" paramId="mid" paramName="blogMessage" paramProperty="id" target="_blank" titleKey="button.download">
			  <layout:img srcName="HTML.gif" border="0"/>
			  <bean:write name="blogMessage" property="uploadFile" filter="false"/>
	          <view:message key="button.download" />
            </layout:link>
               <layout:space/>
             </logic:equal>
              <logic:equal name="blogMessage" property="blogMessageType.name" value="Word">
            <bean:write name="blogMessage" property="message" filter="false"/>
			<layout:img srcName="HTML.gif" border="0"/>
            <layout:link action="link" paramId="mid" paramName="blogMessage" paramProperty="id" target="_blank" titleKey="button.download">
			  <layout:img srcName="HTML.gif" border="0"/>
			  <bean:write name="blogMessage" property="uploadFile" filter="false"/>
	          <view:message key="button.download" />
            </layout:link>
               <layout:space/>
             </logic:equal>
              <logic:equal name="blogMessage" property="blogMessageType.name" value="HTML">
            <bean:write name="blogMessage" property="message" filter="false"/>
			<layout:img srcName="HTML.gif" border="0"/>
            <layout:link action="link" paramId="mid" paramName="blogMessage" paramProperty="id" target="_blank" titleKey="button.detail">
			  <layout:img srcName="HTML.gif" border="0"/>
			  <bean:write name="blogMessage" property="uploadFile" filter="false"/>
	          <view:message key="button.detail" />
            </layout:link>
               <layout:space/>
             </logic:equal>
              <logic:equal name="blogMessage" property="blogMessageType.name" value="Plain">
			<bean:write name="blogMessage" property="message" filter="false"/>
			<layout:link action="link" paramId="mid" paramName="blogMessage" paramProperty="id" target="_blank" titleKey="button.detail">
			  <layout:img srcName="HTML.gif" border="0"/>
			  <bean:write name="blogMessage" property="uploadFile" filter="false"/>
			  <view:message key="button.detail" />
			</layout:link>
		    <layout:space/>
		 </logic:equal>
		
		 <logic:equal name="blogMessage" property="blogMessageType.name" value="File">
			<bean:write name="blogMessage" property="message" filter="false"/>
			<layout:link action="link" paramId="mid" paramName="blogMessage" paramProperty="id" target="_blank" titleKey="button.download">
			  <layout:img srcName="HTML.gif" border="0"/>
			  <bean:write name="blogMessage" property="uploadFile" filter="false"/>
			  <view:message key="button.download" />
			</layout:link>
		    <layout:space/>
		 </logic:equal>
 	     <layout:space/>
 		  <bean:write name="blogMessage" property="updateTime"/>
 		  <bean:write name="blogMessage" property="blogUser.nickname"/>
 		  
  		  <logic:notEmpty name="blogMessage" property="blogMessageStatistic">
  			<small>
  			(<view:message key="label.viewed" />
	  		<bean:write name="blogMessage" property="blogMessageStatistic.viewTimes"/>
  			<view:message key="label.times" />
  			<view:message key="label.updated" />
	  		<bean:write name="blogMessage" property="blogMessageStatistic.updateTimes"/>
  			<view:message key="label.times" />)
  			</small>
		  </logic:notEmpty>	
  		  
 		  <layout:space/>
		  <layout:link action="message?reqCode=updateList" paramId="id" paramName="blogMessage" paramProperty="id" titleKey="button.update">
		  		<layout:img srcName="edit.gif" border="0"/>
		  		<view:message key="button.update" />
		  </layout:link>
		  <layout:link action="/replyMessage" paramId="mid" paramName="blogMessage" paramProperty="id" titleKey="button.reply">
		  		<layout:img srcName="DIR.gif" border="0"/>
		  		<view:message key="button.reply" />
		  </layout:link>
	  	  <logic:notEmpty name="blogMessage" property="blogReplyMessages">
		  		<bean:size id="mSize" name="blogMessage" property="blogReplyMessages"/>
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
	  	  <layout:space/>
	  </layout:collectionItem>
	</layout:collection>
<hr>
<div id="pages" align="center"><div id="links"></div></div>
<script type="text/javascript">
	//blogger.initBlogger(groupId, messageId);
    //blogger.createPageLinksElements(elemId,childId,totalPages,currPage,pageLength,idPrefix,actionName);
	blogger.initBlogger(
		<bean:write name="messageForm" property="blogGroup.id"/>,
		0);  
    blogger.createPageLinksElements(
    	"pages",
    	"links",
    	<bean:write name="messageForm" property="pageList.total"/>,
    	<bean:write name="messageForm" property="pageList.number"/>,
    	10,
    	"message",
    	"message.do");
</script>

</logic:present>

<logic:present name="messageForm" property="blogMessage">
	<bean:define id="blogMessage" name="messageForm" property="blogMessage"/>
  	  <layout:space/>
	  <logic:notEmpty name="blogMessage" property="uploadFile">
  	    <layout:space/>
  	      <logic:equal name="blogMessage" property="blogMessageType.name" value="Image">
	  	    <layout:space/>
  		    <html:img action="/image" paramId="mid" paramName="blogMessage" paramProperty="id" border="0"/>
	  	    <layout:space/>
	  	    <layout:space/>
  	      </logic:equal>
      </logic:notEmpty>
</logic:present>

<layout:space/>

<layout:form action="/message" styleClass="FORM" reqCode="" key="form.message.title"  
width="100%" enctype="multipart/form-data" method="POST">
	<logic:empty name="messageForm" property="method">
	    <div id="Option">
			<span style="width: 200px">
				<b><view:message key="label.list.option" /></b>
			</span>
			<span style="width: 200px">
				<logic:present name="messageForm" property="imageAsIconOptList">
					<html:select name="messageForm" property="imageAsIconOpt" size="1"
						onchange="document.forms['messageForm'].submit()">
						<html:optionsCollection name="messageForm"
							property="imageAsIconOptList" value="id" label="name" />
					</html:select>
				</logic:present>
			</span>
			&nbsp;&nbsp;	
			<span style="width: 200px">
				<logic:present name="messageForm" property="pageMaxLinesOptList">
					<html:select name="messageForm" property="pageMaxLinesOpt" size="1"
						onchange="document.forms['messageForm'].submit()">
						<html:optionsCollection name="messageForm"
							property="pageMaxLinesOptList" value="id" label="name" />
					</html:select>
				</logic:present>
			</span>
			&nbsp;&nbsp;	
			<span style="width: 400px">
				<b><view:message key="label.status.option" /></b>		   		
				<html:checkbox name="messageForm" property="statusOpt" value="<%=MessageForm.UPDATE_PUBLISHED %>">
					<view:message key="label.published"/>
				</html:checkbox>		   				   		
	   		</span>
		</div>
	</logic:empty>
	<layout:text key="field.message.title"  property="title" cols="110" maxlength="250"/>
	<layout:text key="field.message.summary"  property="summary" cols="110" maxlength="500" isRequired="false"/>
	<layout:file key="field.message.upload"  fileKey="field.message.upload" fileName="uploadName" property="uploadFile"/>
	<layout:textarea layoutId="message" key="field.message.content" property="message" rows="40" cols="110" isRequired="true"/>
	<layout:text key="field.message.link"  property="linkURL" cols="110" maxlength="200" />
	<logic:notEmpty name="messageForm" property="method">
	   	<logic:equal name="messageForm" property="method" value="edit">
			<div>
			  <span style="width: 400px">
				<b><view:message key="label.update.option" /></b>
		   		<html:radio name="messageForm" property="updateOpt" value="<%=MessageForm.UPDATE_MESSAGE %>">
					<view:message key="label.message" />
		   		</html:radio>
		   		<html:radio name="messageForm" property="updateOpt" value="<%=MessageForm.UPDATE_ALL %>">
					<view:message key="label.all" />
		   		</html:radio>
	   		  </span>
	   		  &nbsp;&nbsp;
			  <logic:present name="messageForm" property="groupTypeOptList">
				<html:select name="messageForm" property="groupTypeOpt" size="1" onchange="document.forms['messageForm'].submit()">
					<html:optionsCollection name="messageForm" property="groupTypeOptList" value="id" label="name" />
				</html:select>
			  </logic:present>
			  &nbsp;&nbsp;	
			  <logic:present name="messageForm" property="groupOptList">
				<html:select name="messageForm" property="groupOpt" size="1">
					<html:optionsCollection name="messageForm" property="groupOptList" value="id" label="name" />
				</html:select>
			  </logic:present>
			  &nbsp;&nbsp;	
			  <span style="width: 400px">
				<b><view:message key="label.status.option" /></b>		   		
				<html:checkbox name="messageForm" property="statusOpt" value="<%=MessageForm.UPDATE_PUBLISHED %>">
					<view:message key="label.published"/>
				</html:checkbox>		   				   		
	   		  </span>
	   		</div>
			<layout:submit reqCode="button.update">
				<view:message key="button.update" />
			</layout:submit>
		</logic:equal>
	</logic:notEmpty>
   	<logic:empty name="messageForm" property="method">
		<layout:submit reqCode="button.create">
			<view:message key="button.create" />
		</layout:submit>
	</logic:empty>
	
</layout:form>


