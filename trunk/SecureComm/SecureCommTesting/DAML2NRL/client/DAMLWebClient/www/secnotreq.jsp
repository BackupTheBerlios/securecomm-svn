<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="stylesheets/DAMLWebClient.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="javascript/DAMLWebClient.js"></script>
<title>DAML Web Client</title>
</head>
<body>
<div id="top"><jsp:include page="include/header.inc"></jsp:include></div>
<div id="box">
<div id="left">
<ul id="list">
	<li><a href="/DAMLWebClient/credreq.jsp">Credential Request</a></li>
	<li><a href="/DAMLWebClient/secmechreq.jsp">Security Mechanism</a></li>
	<li><strong>Security Notation</strong></li>
</ul>
</div>
<div id="main">
<h2>DAML Security Notation Request</h2>
<form id="damlwebform" name="damlwebform" action="DAMLReqMan" method="get">
<p id="subject"><label>Subject:</label> <input type="text" name="subjectname"
	value="Security Notation" readonly></p>
<fieldset id="Securitynotation"><legend>Security Notation</legend>
<table class="confirmationdata">
	<tr>
		<td><input type="checkbox" name="authentication"></td>
		<td><label>Authentication</label></td>
	</tr>
	<tr>
		<td><input type="checkbox" name="authorization"></td>
		<td><label>Authorization</label></td>
	</tr>
	<tr>
		<td><input type="checkbox" name="accesscontrol"></td>
		<td><label>Access Control</label></td>
	</tr>
	<tr>
		<td><input type="checkbox" name="dataintegrity"></td>
		<td><label>Data Integrity</label></td>
	</tr>
	<tr>
		<td><input type="checkbox" name="confidentiality"></td>
		<td><label>Confidentiality</label></td>
	</tr>
	<tr>
		<td><input type="checkbox" name="privacy"></td>
		<td><label>Privacy</label></td>
	</tr>
	<tr>
		<td><input type="checkbox" name="exposurecontrol"></td>
		<td><label>Exposure Control</label></td>
	</tr>
	<tr>
		<td><input type="checkbox" name="anonymity"></td>
		<td><label>Anonymity</label></td>
	</tr>
	<tr>
		<td><input type="checkbox" name="negotiation"></td>
		<td><label>Negotiation</label></td>
	</tr>
	<tr>
		<td><input type="checkbox" name="policy"></td>
		<td><label>Policy</label></td>
	</tr>
	<tr>
		<td><input type="checkbox" name="policylanguage"></td>
		<td><label>Policy Language</label></td>
	</tr>
	<tr>
		<td><input type="checkbox" name="keydistribution"></td>
		<td><label>Key Distribution</label></td>
	</tr>
</table>
</fieldset>
<input type="submit" id="btn" name="secnot_submit" value="Submit Request"
	title="Submit DAML Request"></form>
</div>
</div>
<div id="footer"><jsp:include page="include/footer.inc"></jsp:include></div>
</body>
</html>