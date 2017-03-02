<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
	<title><fmt:message key="hello.title"/></title>
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
  <tr><td class="addborder rowheader">Date</td><td class="addborder" align="left"><input id="date" type="text" size="40" tabindex="3" /></td><td class="addborder">MM/DD/YYYY</td></tr>
  <tr><td class="addborder rowheader">Money</td><td class="addborder" align="left"><input id="money" type="text" size="40" tabindex="4" /></td><td class="addborder">$[+-]9,999.99</td></tr>


  <tr><td class="addborder rowheader">&nbsp;</td><td class="addborder"></td><td class="addborder">&nbsp;</td></tr>

  <tr><td class="addborder rowheader">Site Details</td><td class="addborder" colspan="2">
  <div class="addborder">
  <a here="#" onClick="showDetails('acas', 2)";>Show Details</a>&nbsp;&nbsp;&nbsp;&nbsp;
  <a here="#" onClick="hideDetails('acas', 2)";>Hide Details</a>
  </div>
  </td>
  </tr>
  <tr><td class="addborder" colspan="2" align="left">
  <div id="acas1" class="addborder" onmouseover="popupBox('acas1','acas1box')">Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed...</div>
  <div id="acas1box" class="pop-up" align="left">Lorem ipsum dolor sit amet, consetetur sadipscing elitr,sed diam nonumy eirmod tempor invidunt ut labore et dolore
magna aliquyam erat, sed diam voluptua. At vero eos et Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore
magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo</div></td><td class="addborder">30/12/2011</td></tr>
  <tr><td class="addborder" colspan="2" align="left">
  <div id="acas2" class="addborder" onmouseover="popupBox('acas2','acas2box')">consetetur sadipscing elitr, sed...consetetur sadipscing elitr, sed</div>
  <div id="acas2box" class="pop-up">consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore
magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore
magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo</div></td><td class="addborder">30/01/2012</td></tr>										      


  </tbody></table>
  
  </center>
</body>
</html>