<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-view.tld" prefix="view"%>

<bean:define id="blogMessage" name="replyMessageSearchForm" property="blogMessage"/>
<layout:img srcName="arrow-right.gif" border="0"/>&nbsp;
<html:link action="/blogSearch" paramId="gid" paramName="blogMessage" paramProperty="blogGroup.id" titleKey="button.enter">
	<bean:write name="blogMessage" property="blogGroup.groupName" filter="false"/>
</html:link>
<layout:space/>

<div align="left">

<view:message key="label.message.reply" />
<bean:write name="blogMessage" property="id" filter="false"/>:
<b><bean:write name="blogMessage" property="title" filter="false"/></b><br>
<bean:write name="blogMessage" property="summary" filter="false"/><br>

<logic:equal name="blogMessage" property="blogMessageType.name" value="Image">
   	<html:img action="/image" paramId="mid" paramName="blogMessage" paramProperty="id" border="0"/>
    <layout:space/>
  	<bean:write name="blogMessage" property="message" filter="false"/>
</logic:equal>

<logic:equal name="blogMessage" property="blogMessageType.name" value="Link">
	<bean:write name="blogMessage" property="message" filter="false"/>
	<layout:img srcName="HTML.gif" border="0"/>
	<layout:link action="link" paramId="mid" paramName="blogMessage" paramProperty="id" target="_blank" titleKey="button.enter">
		<bean:write name="blogMessage" property="linkURL" filter="false"/>
		<view:message key="button.enter" />
	</layout:link>
   <layout:space/>
</logic:equal>

<logic:equal name="blogMessage" property="blogMessageType.name" value="Text">
	<!-- view:text action="text" paramId="mid" paramName="blogMessage" paramProperty="id" /-->
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
    <layout:space/>
</logic:equal>

<logic:equal name="blogMessage" property="blogMessageType.name" value="Word">
	<bean:write name="blogMessage" property="message" filter="false"/>
	<layout:img srcName="HTML.gif" border="0"/>
	<layout:link action="link" paramId="mid" paramName="blogMessage" paramProperty="id" target="_blank" titleKey="button.download">
		<bean:write name="blogMessage" property="uploadFile" filter="false"/>
		<view:message key="button.download" />
	</layout:link>
    <layout:space/>
</logic:equal>

<logic:equal name="blogMessage" property="blogMessageType.name" value="HTML">
	<bean:write name="blogMessage" property="message" filter="false"/>
	<layout:img srcName="HTML.gif" border="0"/>
	<layout:link action="link" paramId="mid" paramName="blogMessage" paramProperty="id" target="_blank" titleKey="button.download">
		<bean:write name="blogMessage" property="uploadFile" filter="false"/>
		<view:message key="button.download" />
	</layout:link>
    <layout:space/>
</logic:equal>

<logic:equal name="blogMessage" property="blogMessageType.name" value="Plain">
	<bean:write name="blogMessage" property="message" filter="false"/>
	<layout:img srcName="HTML.gif" border="0"/>
	<layout:link action="link" paramId="mid" paramName="blogMessage" paramProperty="id" target="_blank" titleKey="button.download">
		<bean:write name="blogMessage" property="uploadFile" filter="false"/>
		<view:message key="button.download" />
	</layout:link>
    <layout:space/>
</logic:equal>

<logic:equal name="blogMessage" property="blogMessageType.name" value="File">
	<bean:write name="blogMessage" property="message" filter="false"/>
	<layout:img srcName="HTML.gif" border="0"/>
	<layout:link action="link" paramId="mid" paramName="blogMessage" paramProperty="id" target="_blank" titleKey="button.download">
		<bean:write name="blogMessage" property="uploadFile" filter="false"/>
		<view:message key="button.download" />
	</layout:link>
    <layout:space/>
</logic:equal>

<logic:notEmpty name="blogMessage" property="linkURL">
    <layout:space/>
	<bean:write name="blogMessage" property="linkURL" filter="false"/>
	<layout:img srcName="HTML.gif" border="0"/>
	<a href="<bean:write name="blogMessage" property="linkURL" filter="false"/>" target="_blank"" title="Enter">
 		<view:message key="button.enter" />
	</a>
   <layout:space/>
</logic:notEmpty>	

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

<logic:present name="replyMessageSearchForm" property="messages">
	<layout:collection name="replyMessageSearchForm" property="messages" id="blogReplyMessage" styleClass="ARRAY" styleClass2="ARRAY1" width="100%">
	  <layout:collectionItem>
		    <view:message key="label.message.reply" />
  	   	  	<bean:write name="blogReplyMessage" property="id" filter="false"/>:
     	  	<layout:space/>
 
  	      	<logic:equal name="blogReplyMessage" property="blogMessageType.name" value="Image">
	       		<logic:equal name="replyMessageSearchForm" property="imageAsIcon" value="true">
		  		  <layout:link action="/viewReplyMessage" paramId="rid" paramName="blogReplyMessage" paramProperty="id">
	 	    		<html:img action="/icon" paramId="rid" paramName="blogReplyMessage" paramProperty="id" border="0"/>
	 	    	  </layout:link>	
				</logic:equal>
	      		<logic:notEqual name="replyMessageSearchForm" property="imageAsIcon" value="true">
		    		<html:img action="/image" paramId="rid" paramName="blogReplyMessage" paramProperty="id" border="0"/>
				</logic:notEqual>
   		    	<bean:write name="blogReplyMessage" property="message" filter="false"/>
				<layout:img srcName="HTML.gif" border="0"/>
		  		<layout:link action="/viewReplyMessage" paramId="rid" paramName="blogReplyMessage" paramProperty="id" titleKey="button.download">
					<bean:write name="blogReplyMessage" property="uploadFile" filter="false"/>
					<view:message key="button.download" />
				</layout:link>
	      	</logic:equal>

			<logic:equal name="blogReplyMessage" property="blogMessageType.name" value="Link">
				<bean:write name="blogReplyMessage" property="message" filter="false"/>
				<layout:img srcName="HTML.gif" border="0"/>
					<view:link action="link" paramId="rid" paramName="blogReplyMessage" paramProperty="id" target="_blank" titleKey="button.enter">
						<bean:write name="blogReplyMessage" property="linkURL" filter="false"/>
						<view:message key="button.enter" />
					</view:link>
			</logic:equal>

			<logic:equal name="blogReplyMessage" property="blogMessageType.name" value="Text">
				<bean:write name="blogReplyMessage" property="message" filter="false"/>
			</logic:equal>

			<logic:equal name="blogReplyMessage" property="blogMessageType.name" value="Music">
				<bean:write name="blogReplyMessage" property="message" filter="false"/>
				<layout:img srcName="HTML.gif" border="0"/>
				<layout:link action="link" paramId="rid" paramName="blogReplyMessage" paramProperty="id" target="_blank" titleKey="button.download">
					<bean:write name="blogReplyMessage" property="uploadFile" filter="false"/>
					<view:message key="button.download" />
				</layout:link>
			</logic:equal>

			<logic:equal name="blogReplyMessage" property="blogMessageType.name" value="Word">
				<bean:write name="blogReplyMessage" property="message" filter="false"/>
				<layout:img srcName="HTML.gif" border="0"/>
				<layout:link action="link" paramId="rid" paramName="blogReplyMessage" paramProperty="id" target="_blank" titleKey="button.download">
					<bean:write name="blogReplyMessage" property="uploadFile" filter="false"/>
					<view:message key="button.download" />
				</layout:link>
			</logic:equal>

			<logic:equal name="blogReplyMessage" property="blogMessageType.name" value="HTML">
				<bean:write name="blogReplyMessage" property="message" filter="false"/>
				<layout:img srcName="HTML.gif" border="0"/>
				<layout:link action="link" paramId="rid" paramName="blogReplyMessage" paramProperty="id" target="_blank" titleKey="button.detail">
					<bean:write name="blogReplyMessage" property="uploadFile" filter="false"/>
					<view:message key="button.detail" />
				</layout:link>
			</logic:equal>

			<logic:equal name="blogReplyMessage" property="blogMessageType.name" value="Plain">
				<bean:write name="blogReplyMessage" property="message" filter="false"/>
				<layout:img srcName="HTML.gif" border="0"/>
				<layout:link action="link" paramId="rid" paramName="blogReplyMessage" paramProperty="id" target="_blank" titleKey="button.detail">
					<bean:write name="blogReplyMessage" property="uploadFile" filter="false"/>
					<view:message key="button.detail" />
				</layout:link>
			</logic:equal>

			<logic:equal name="blogReplyMessage" property="blogMessageType.name" value="File">
				<bean:write name="blogReplyMessage" property="message" filter="false"/>
				<layout:img srcName="HTML.gif" border="0"/>
				<layout:link action="link" paramId="rid" paramName="blogReplyMessage" paramProperty="id" target="_blank" titleKey="button.download">
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

	  	  <layout:space/>
	      <logic:equal name="blogReplyMessage" property="status" value="true">
		      <view:message key="label.published"/>		  
	      </logic:equal>		  
	      <logic:equal name="blogReplyMessage" property="status" value="false">
	        <font color="red"><view:message key="label.private"/></font>		  
	      </logic:equal>

	  </layout:collectionItem>	  
	</layout:collection>
</logic:present>

<layout:space/>

