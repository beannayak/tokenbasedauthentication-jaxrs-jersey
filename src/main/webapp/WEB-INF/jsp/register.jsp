<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<c:url var="homeBase" value="/" />
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
</head>
<body>
	<form:form method="post" modelAttribute="user" action="${homeBase}register">
            Username:
            <form:input path="username" type="text" /> 
            <form:errors path="username" /><br />
            
            Password:
            <form:input path="password" type="password" /> 
            <form:errors path="password" /><br />
                        
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="submit" value="Submit" />
    </form:form>
</body>
</html>