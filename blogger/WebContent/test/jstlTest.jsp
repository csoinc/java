<%@ page isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
<title><fmt:message key="ServerError"/></title>
</head>
<body bgcolor="white">
<br>
<c:if test="${'${'}param.test == 'a'}">
  <c:out value="The parameter is ${'${'}param.test}"/>
</c:if>
<br>
<c:if test="${empty sessionScope.userName}">
   <c:out value="No user name in the session."></c:out>
</c:if>
<br>

<c:out value="${4*20}"/>
<br>
<c:out value=" literalText ${' Owen'}${' Here'}"/>
<br>
<c:out value=" literalText"/>
<br>

<jsp:scriptlet>
	int count = 100;
</jsp:scriptlet>
<br>
<%! int OK = 120; %>
Color = ${cookie.colorPreference}<br>
Header = ${header["User-Agent"]}<br>
URL = ${pageContext.request.requestURL}<br>
ServerInfo = ${pageContext.servletContext.serverInfo}<br>

<h3>A sample <c:out value="${count}"/></h3>

<c:out value="OK = ${pageScope.OK}" /><br>

<c:choose>
<c:when test="${foo} == params.a}">
  <h3>Foo!</h3>
</c:when>
<c:otherwise>
  <h3>Bar!</h3>
</c:otherwise>
</c:choose>

</body>
</html> 
