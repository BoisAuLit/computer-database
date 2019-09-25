<%@page import="com.excilys.cdb.domain.Person"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Test</title>
</head>

<body>
	<strong>All the computer comapanies</strong>
	<table border="1">
		<tr>
			<th>id</th>
			<th>name</th>
		</tr>
		<c:forEach var="company" items="${companies}">
			<tr>
				<td>${company.id}</td>
				<td>${company.name }</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>