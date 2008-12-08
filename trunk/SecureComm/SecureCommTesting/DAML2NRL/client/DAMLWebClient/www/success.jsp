<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="stylesheets/DAMLWebClient.css" rel="stylesheet" type="text/css">
<title>DAML Web Client</title>
</head>
<body>
<div id="top"><jsp:include page="include/header.inc"></jsp:include></div>
<div id="box">
<div id="left">
<ul id="list">
	<li><a href="/DAMLWebClient/credreq.jsp">Credential Request</a></li>
	<li><a href="/DAMLWebClient/secmechreq.jsp">Security Mechanism</a></li>
	<li><a href="/DAMLWebClient/secnotreq.jsp">Security Notation</a></li>
</ul>
</div>
<div id="main">
<div id="successmsg">Your request has been submitted successfully!</div>
<div id="results">
<% String res = (String) request.getSession().getAttribute("results"); %> <%= res %></div>
</div>
</div>
<div id="footer"><jsp:include page="include/footer.inc"></jsp:include></div>
</body>
</html>