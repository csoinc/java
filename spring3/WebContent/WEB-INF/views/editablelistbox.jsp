<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
	<title><fmt:message key="jec.title"/></title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8" />
	<link rel="stylesheet" href="<c:url value="/resources/css/screen.css" />" type="text/css" media="screen, projection">
	<link rel="stylesheet" href="<c:url value="/resources/css/print.css" />" type="text/css" media="print">
	<link rel="stylesheet" href="<c:url value="/resources/css/popup.css" />" type="text/css" media="print">
	<script src="<c:url value="/resources/js/jquery-1.7.2.min.js" />" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/jquery.maskedinput-1.3.min.js" />" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/jquery.jec-1.3.4.js" />" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/module.js" />" type="text/javascript"></script>
</head>
<body>
  <h1>Editable Listbox</h1>
  
  <code>$('#listbox1').jec({maxLength: 10});</code>
  <select id="listbox1" >
       <option value="1">BMW X7</option>
       <option value="2">Ford</option>
       <option value="3">Mercedes</option>
       <option value="4">Opel</option>
       <option value="5">Peugeot</option>
       <option value="6">Renault</option>
  </select>  
  <hr/>
</body>
</html>