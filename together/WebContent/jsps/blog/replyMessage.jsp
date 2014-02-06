<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-view.tld" prefix="view"%>
<%@ page import="com.oyou.web.blog.ReplyMessageForm" %>

<bean:define id="blogMessage" name="replyMessageForm" property="blogMessage"/>
<layout:img srcName="arrow-right.gif" border="0"/>&nbsp;
<html:link action="/message" paramId="gid" paramName="blogMessage" paramProperty="blogGroup.id" titleKey="button.enter">
	<bean:write name="blogMessage" property="blogGroup.groupName" filter="false"/>
</html:link>

<div align="left">

<bean:write name="blogMessage" property="id" filter="false"/>:
<b><bean:write name="blogMessage" property="title" filter="false"/></b><br>
<bean:write name="blogMessage" property="summary" filter="false"/><br>
<logic:equal name="blogMessage" property="blogMessageType.name" value="Image">
	<html:img action="/image" paramId="mid" paramName="blogMessage" paramProperty="id" border="0"/>
	<bean:write name="blogMessage" property="message" filter="false"/>
</logic:equal>

<logic:equal name="blogMessage" property="blogMessageType.name" value="Link">
	<bean:write name="blogMessage" property="message" filter="false"/>
	<view:link action="link" paramId="mid" paramName="blogMessage" paramProperty="id" target="_blank" titleKey="button.enter">
	    <layout:img srcName="HTML.gif" border="0"/>
	    <bean:write name="blogMessage" property="linkURL" filter="false"/>
	    <view:message key="button.enter" />
	</view:link>
</logic:equal>

<logic:equal name="blogMessage" property="blogMessageType.name" value="Text">
	<bean:write name="blogMessage" property="message" filter="false"/>
</logic:equal>

<logic:equal name="blogMessage" property="blogMessageType.name" value="Music">
	<bean:write name="blogMessage" property="message" filter="false"/>
	<layout:link action="link" paramId="mid" paramName="blogMessage" paramProperty="id" target="_blank" titleKey="button.download">
	    <layout:img srcName="HTML.gif" border="0"/>
		<bean:write name="blogMessage" property="uploadFile" filter="false"/>
		<view:message key="button.download" />
	</layout:link>
</logic:equal>

<logic:equal name="blogMessage" property="blogMessageType.name" value="Word">
	<bean:write name="blogMessage" property="message" filter="false"/>
	<layout:link action="link" paramId="mid" paramName="blogMessage" paramProperty="id" target="_blank" titleKey="button.download">
	    <layout:img srcName="HTML.gif" border="0"/>
		<bean:write name="blogMessage" property="uploadFile" filter="false"/>
		<view:message key="button.download" />
	</layout:link>
</logic:equal>

<logic:equal name="blogMessage" property="blogMessageType.name" value="HTML">
	<bean:write name="blogMessage" property="message" filter="false"/>
	<layout:link action="link" paramId="mid" paramName="blogMessage" paramProperty="id" target="_blank" titleKey="button.detail">
	    <layout:img srcName="HTML.gif" border="0"/>
		<bean:write name="blogMessage" property="uploadFile" filter="false"/>
		<view:message key="button.detail" />
	</layout:link>
</logic:equal>

<logic:equal name="blogMessage" property="blogMessageType.name" value="Plain">
	<bean:write name="blogMessage" property="message" filter="false"/>
	<layout:link action="link" paramId="mid" paramName="blogMessage" paramProperty="id" target="_blank" titleKey="button.detail">
	    <layout:img srcName="HTML.gif" border="0"/>
		<bean:write name="blogMessage" property="uploadFile" filter="false"/>
		<view:message key="button.detail" />
	</layout:link>
</logic:equal>

<logic:equal name="blogMessage" property="blogMessageType.name" value="File">
	<bean:write name="blogMessage" property="message" filter="false"/>
	<layout:link action="link" paramId="mid" paramName="blogMessage" paramProperty="id" target="_blank" titleKey="button.download">
	    <layout:img srcName="HTML.gif" border="0"/>
		<bean:write name="blogMessage" property="uploadFile" filter="false"/>
		<view:message key="button.download" />
	</layout:link>
</logic:equal>

<!-- view:text action="text" paramId="mid" paramName="blogMessage" paramProperty="id" /-->
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
</div>

<logic:present name="replyMessageForm" property="messages">
	<layout:collection name="replyMessageForm" property="messages" id="blogReplyMessage" styleClass="ARRAY" styleClass2="ARRAY1" width="100%">
	  	<layout:collectionItem>
		    <view:message key="label.message.reply" />
  	   	  	<bean:write name="blogReplyMessage" property="id" filter="false"/>:
     	  	<layout:space/>
			
  	      	<logic:equal name="blogReplyMessage" property="blogMessageType.name" value="Image">
	       		<logic:equal name="replyMessageForm" property="imageAsIcon" value="true">
		  		    <layout:link action="/viewReplyMessage" paramId="rid" paramName="blogReplyMessage" paramProperty="id">
	 	    		    <html:img action="/icon" paramId="rid" paramName="blogReplyMessage" paramProperty="id" border="0"/>
	 	    		</layout:link>    
				</logic:equal>
	      		<logic:notEqual name="replyMessageForm" property="imageAsIcon" value="true">
		    		<html:img action="/image" paramId="rid" paramName="blogReplyMessage" paramProperty="id" border="0"/>
				</logic:notEqual>
   		    	<bean:write name="blogReplyMessage" property="message" filter="false"/>
		  		<layout:link action="/viewReplyMessage" paramId="rid" paramName="blogReplyMessage" paramProperty="id" titleKey="button.download">
				    <layout:img srcName="HTML.gif" border="0"/>
					<bean:write name="blogReplyMessage" property="uploadFile" filter="false"/>
					<view:message key="button.download" />
				</layout:link>
	      	</logic:equal>

			<logic:equal name="blogReplyMessage" property="blogMessageType.name" value="Link">
				<bean:write name="blogReplyMessage" property="message" filter="false"/>
				<view:link action="link" paramId="rid" paramName="blogReplyMessage" paramProperty="id" target="_blank" titleKey="button.enter">
					<layout:img srcName="HTML.gif" border="0"/>
					<bean:write name="blogReplyMessage" property="linkURL" filter="false"/>
					<view:message key="button.enter" />
				</view:link>
			</logic:equal>

			<logic:equal name="blogReplyMessage" property="blogMessageType.name" value="Text">
				<bean:write name="blogReplyMessage" property="message" filter="false"/>
			</logic:equal>

			<logic:equal name="blogReplyMessage" property="blogMessageType.name" value="Music">
				<bean:write name="blogReplyMessage" property="message" filter="false"/>
				<layout:link action="link" paramId="rid" paramName="blogReplyMessage" paramProperty="id" target="_blank" titleKey="button.download">
				    <layout:img srcName="HTML.gif" border="0"/>
					<bean:write name="blogReplyMessage" property="uploadFile" filter="false"/>
					<view:message key="button.download" />
				</layout:link>
			</logic:equal>

			<logic:equal name="blogReplyMessage" property="blogMessageType.name" value="Word">
				<bean:write name="blogReplyMessage" property="message" filter="false"/>
				<layout:link action="link" paramId="rid" paramName="blogReplyMessage" paramProperty="id" target="_blank" titleKey="button.download">
					<layout:img srcName="HTML.gif" border="0"/>
					<bean:write name="blogReplyMessage" property="uploadFile" filter="false"/>
					<view:message key="button.download" />
				</layout:link>
			</logic:equal>

			<logic:equal name="blogReplyMessage" property="blogMessageType.name" value="HTML">
				<bean:write name="blogReplyMessage" property="message" filter="false"/>
				<layout:link action="link" paramId="rid" paramName="blogReplyMessage" paramProperty="id" target="_blank" titleKey="button.detail">
					<layout:img srcName="HTML.gif" border="0"/>
					<bean:write name="blogReplyMessage" property="uploadFile" filter="false"/>
					<view:message key="button.detail" />
				</layout:link>
			</logic:equal>

			<logic:equal name="blogReplyMessage" property="blogMessageType.name" value="Plain">
				<bean:write name="blogReplyMessage" property="message" filter="false"/>
				<layout:link action="link" paramId="rid" paramName="blogReplyMessage" paramProperty="id" target="_blank" titleKey="button.detail">
					<layout:img srcName="HTML.gif" border="0"/>
					<bean:write name="blogReplyMessage" property="uploadFile" filter="false"/>
					<view:message key="button.detail" />
				</layout:link>
			</logic:equal>

			<logic:equal name="blogReplyMessage" property="blogMessageType.name" value="File">
				<bean:write name="blogReplyMessage" property="message" filter="false"/>
				<layout:link action="link" paramId="rid" paramName="blogReplyMessage" paramProperty="id" target="_blank" titleKey="button.download">
					<layout:img srcName="HTML.gif" border="0"/>
					<bean:write name="blogReplyMessage" property="uploadFile" filter="false"/>
					<view:message key="button.download" />
				</layout:link>
			</logic:equal>

      	  	<layout:space/>
      	  	<layout:space/>

  		  <bean:write name="blogReplyMessage" property="updateTime"/>
  		  <bean:write name="blogReplyMessage" property="blogUser.nickname"/>
  		  <logic:notEmpty name="blogReplyMessage" property="blogReplyMessageStatistic">
  			<small>
  			(<view:message key="label.viewed" />
	  		<bean:write name="blogReplyMessage" property="blogReplyMessageStatistic.viewTimes"/>
  			<view:message key="label.times" />
  			<view:message key="label.updated" />
	  		<bean:write name="blogReplyMessage" property="blogReplyMessageStatistic.updateTimes"/>
  			<view:message key="label.times" />)
  			</small>
		  </logic:notEmpty>	
  		  
		  <layout:space/>
	  	  <layout:link action="replyMessage?reqCode=updateList" paramId="id" paramName="blogReplyMessage" paramProperty="id" titleKey="button.update">
	  		<layout:img srcName="edit.gif" border="0"/>
			<view:message key="button.update" />
	  	  </layout:link>

		  <logic:equal name="blogMessage" property="status" value="true">
 		    <view:message key="label.published"/>		  
		  </logic:equal>		  
		  <logic:equal name="blogMessage" property="status" value="false">
		    <font color="red"><view:message key="label.private"/></font>		  
		  </logic:equal>
	  	  <layout:space/>
	  	</layout:collectionItem>	  
	</layout:collection>
</logic:present>

<logic:present name="replyMessageForm" property="blogReplyMessage">
	<bean:define id="blogReplyMessage" name="replyMessageForm" property="blogReplyMessage"/>
  	<layout:space/>
    <logic:notEmpty name="blogReplyMessage" property="uploadFile">
 	    <layout:space/>
 	    <logic:equal name="blogReplyMessage" property="blogMessageType.name" value="Image">
  	    	<layout:space/>
 		    <html:img action="/image" paramId="rid" paramName="blogReplyMessage" paramProperty="id" border="0"/>
  	    	<layout:space/>
  	    	<layout:space/>
 	    </logic:equal>
    </logic:notEmpty>
</logic:present>
<layout:space/>

<layout:form action="/replyMessage" styleClass="FORM" reqCode="" key="form.message.reply.title" 
width="100%" enctype="multipart/form-data" method="POST">
	<logic:empty name="replyMessageForm" property="method">
		<div  id="listOption">
			<span>
				<b><view:message key="label.list.option" /></b>
				<logic:present name="replyMessageForm" property="imageAsIconOptList">
					<html:select name="replyMessageForm" property="imageAsIconOpt"
						size="1" onchange="document.forms['replyMessageForm'].submit()">
						<html:optionsCollection name="replyMessageForm"
							property="imageAsIconOptList" value="id" label="name" />
					</html:select>
				</logic:present>
			</span>
			<span>
				<logic:present name="replyMessageForm" property="pageMaxLinesOptList">
					<html:select name="replyMessageForm" property="pageMaxLinesOpt"
						size="1" onchange="document.forms['replyMessageForm'].submit()">
						<html:optionsCollection name="replyMessageForm"
							property="pageMaxLinesOptList" value="id" label="name" />
					</html:select>
				</logic:present>
			</span>
		</div>
	</logic:empty>

    <div id="statusOption">
		<span style="width: 400px">
			<b><view:message key="label.status.option" /></b>		   		
			<html:checkbox name="replyMessageForm" property="statusOpt" value="<%=ReplyMessageForm.UPDATE_PUBLISHED %>">
				<view:message key="label.published"/>
			</html:checkbox>		   				   		
   		</span>
   	</div>	
	<!-- layout:file key="field.message.upload"  fileKey="field.message.upload" fileName="uploadName" property="uploadFile"/-->
	<layout:textarea layoutId="message" key="field.message.content" property="message" rows="20" cols="110" isRequired="true"/>
	<!-- layout:text key="field.message.link"  property="linkURL" cols="110" maxlength="200" /-->
	<logic:notEmpty name="replyMessageForm" property="method">
	   	<logic:equal name="replyMessageForm" property="method" value="edit">
			<div>
			  <span style="width: 400px">
				<b><view:message key="label.update.option" /></b>
		   		<html:radio name="replyMessageForm" property="updateOpt" value="<%=ReplyMessageForm.UPDATE_MESSAGE %>">
					<view:message key="label.message" />
		   		</html:radio>
		   		<html:radio name="replyMessageForm" property="updateOpt" value="<%=ReplyMessageForm.UPDATE_ALL %>">
					<view:message key="label.all" />
		   		</html:radio>
	   		  </span>
	   		  &nbsp;&nbsp;
			  <span style="width: 400px">
				<b><view:message key="label.status.option" /></b>		   		
				<html:checkbox name="messageForm" property="statusOpt" value="<%=ReplyMessageForm.UPDATE_PUBLISHED %>">
					<view:message key="label.published"/>
				</html:checkbox>		   				   		
	   		  </span>	   		  

	   		</div>
			<layout:submit reqCode="button.update">
				<view:message key="button.update" />
			</layout:submit>
		</logic:equal>
	</logic:notEmpty>
   	<logic:empty name="replyMessageForm" property="method">
		<layout:submit reqCode="button.create">
			<view:message key="button.create" />
		</layout:submit>
	</logic:empty>
</layout:form>
