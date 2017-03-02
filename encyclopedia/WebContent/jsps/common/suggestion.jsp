<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout" %>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<TITLE>Suggestion</TITLE>
</HEAD>

<BODY>
<P>Book Suggestions.</P>

<layout:html>

	<layout:suggest suggestAction="/bookSuggests.do" name="" key="book" property="book" styleId="" value="" suggestCount="8"/>

</layout:html>

</BODY>

