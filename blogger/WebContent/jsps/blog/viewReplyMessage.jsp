<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-view.tld" prefix="view"%>

<div align="left">
<bean:define id="blogReplyMessage" name="viewReplyMessageForm" property="blogReplyMessage"/>
<layout:img srcName="arrow-right.gif" border="0"/>&nbsp;
<html:link action="/replyMessage" paramId="mid" paramName="blogReplyMessage" paramProperty="blogMessage.id" titleKey="button.back">
	<view:message key="button.back" />
</html:link>
<layout:space/>

<view:message key="label.message.reply" />
<bean:write name="blogReplyMessage" property="id" filter="false"/>:
<layout:space/>
<logic:equal name="blogReplyMessage" property="blogMessageType.name" value="Image">
   	<html:img action="/image" paramId="rid" paramName="blogReplyMessage" paramProperty="id" border="0"/>
    <layout:space/>
  	<bean:write name="blogReplyMessage" property="message" filter="false"/>
    <layout:space/>
</logic:equal>

<logic:equal name="blogReplyMessage" property="blogMessageType.name" value="Link">
	<bean:write name="blogReplyMessage" property="message" filter="false"/>
	<layout:img srcName="HTML.gif" border="0"/>
	<layout:link action="link" paramId="rid" paramName="blogReplyMessage" paramProperty="id" target="_blank" titleKey="button.enter">
		<bean:write name="blogReplyMessage" property="linkURL" filter="false"/>
		<view:message key="button.enter" />
	</layout:link>
   <layout:space/>
</logic:equal>

<logic:equal name="blogReplyMessage" property="blogMessageType.name" value="Text">
	<!-- view:text action="text" paramId="rid" paramName="blogReplyMessage" paramProperty="id" /-->
  	<bean:write name="blogReplyMessage" property="message" filter="false"/>
  	<layout:space/>
</logic:equal>

<logic:equal name="blogReplyMessage" property="blogMessageType.name" value="Music">
	<bean:write name="blogReplyMessage" property="message" filter="false"/>
	<layout:img srcName="HTML.gif" border="0"/>
	<layout:link action="link" paramId="rid" paramName="blogReplyMessage" paramProperty="id" target="_blank" titleKey="button.download">
		<bean:write name="blogReplyMessage" property="uploadFile" filter="false"/>
		<view:message key="button.download" />
	</layout:link>
    <layout:space/>
</logic:equal>

<logic:equal name="blogReplyMessage" property="blogMessageType.name" value="Word">
	<bean:write name="blogReplyMessage" property="message" filter="false"/>
	<layout:img srcName="HTML.gif" border="0"/>
	<layout:link action="link" paramId="rid" paramName="blogReplyMessage" paramProperty="id" target="_blank" titleKey="button.download">
		<bean:write name="blogReplyMessage" property="uploadFile" filter="false"/>
		<view:message key="button.download" />
	</layout:link>
    <layout:space/>
</logic:equal>

<logic:equal name="blogReplyMessage" property="blogMessageType.name" value="HTML">
	<bean:write name="blogReplyMessage" property="message" filter="false"/>
	<layout:img srcName="HTML.gif" border="0"/>
	<layout:link action="link" paramId="rid" paramName="blogReplyMessage" paramProperty="id" target="_blank" titleKey="button.download">
		<bean:write name="blogReplyMessage" property="uploadFile" filter="false"/>
		<view:message key="button.download" />
	</layout:link>
    <layout:space/>
</logic:equal>

<logic:equal name="blogReplyMessage" property="blogMessageType.name" value="Plain">
	<bean:write name="blogReplyMessage" property="message" filter="false"/>
	<layout:img srcName="HTML.gif" border="0"/>
	<layout:link action="link" paramId="rid" paramName="blogReplyMessage" paramProperty="id" target="_blank" titleKey="button.detail">
		<bean:write name="blogReplyMessage" property="uploadFile" filter="false"/>
		<view:message key="button.detail" />
	</layout:link>
    <layout:space/>
</logic:equal>

<logic:equal name="blogReplyMessage" property="blogMessageType.name" value="File">
	<bean:write name="blogReplyMessage" property="message" filter="false"/>
	<layout:img srcName="HTML.gif" border="0"/>
	<layout:link action="link" paramId="rid" paramName="blogReplyMessage" paramProperty="id" target="_blank" titleKey="button.download">
		<bean:write name="blogReplyMessage" property="uploadFile" filter="false"/>
		<view:message key="button.download" />
	</layout:link>
    <layout:space/>
</logic:equal>

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
</div>
