<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
	<title><fmt:message key="mask.title"/></title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8" />
	<link rel="stylesheet" href="<c:url value="/resources/css/screen.css" />" type="text/css" media="screen, projection">
	<link rel="stylesheet" href="<c:url value="/resources/css/print.css" />" type="text/css" media="print">
	<link rel="stylesheet" href="<c:url value="/resources/css/popup.css" />" type="text/css" media="print">
	<script src="<c:url value="/resources/js/jquery-1.7.2.min.js" />" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/jquery.maskedinput-1.3.min.js" />" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/module.js" />" type="text/javascript"></script>
</head>
<body>
  <h1>${message} </h1>
  <center>
  <table width="750"  border="0"><tbody>
  <tr><td class="addborder rowheader">Phone</td><td class="addborder" align="left"><input id="phone" type="text" size="40" tabindex="1" /></td><td class="addborder">(999) 999-9999</td></tr>
  <tr><td class="addborder rowheader">Phone + Ext</td><td class="addborder" align="left"><input id="phoneext" type="text" size="40" tabindex="2" /></td><td class="addborder">(999) 999-9999? x99999</td></tr>

  </tbody></table>
  
  </center>
</body>
</html>