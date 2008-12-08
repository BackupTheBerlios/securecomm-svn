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
	<li><strong>Credential Request</strong></li>
	<li><a href="/DAMLWebClient/secmechreq.jsp">Security Mechanism</a></li>
	<li><a href="/DAMLWebClient/secnotreq.jsp">Security Notation</a></li>
</ul>
</div>
<div id="main">
<h2>DAML Credential Request</h2>
<form id="damlwebform" name="damlwebform" action="DAMLReqMan" method="get">
<p id="subject"><label>Subject:</label> <input type="text" name="subjectname" value="John Smith"></p>
<fieldset id="IDCard"><legend>ID Card</legend>
<table class="confirmationdata">
	<tr>
		<td colspan="2"><input type="checkbox" name="badge" onclick="set_disable(this);">Badge
		ID Card</td>
	</tr>
	<tr>
		<td>Card ID:</td>
		<td><input type="text" class="userdatainput" name="badge_cardid" value="12345" disabled></td>
	</tr>
	<tr>
		<td>Doc Authority:</td>
		<td><input type="text" class="userdatainput" name="badge_docauthority" value="ED" disabled></td>
	</tr>
	<tr>
		<td>Expiration Data:</td>
		<td><input type="text" class="userdatainput" name="badge_expirationdate" value="2009-08-03"
			disabled></td>
	</tr>
</table>
<table class="confirmationdata">
	<tr>
		<td colspan="2"><input type="checkbox" name="creditcard" onclick="set_disable(this);">Credit
		Card</td>
	</tr>
	<tr>
		<td>Card ID:</td>
		<td><input type="text" class="userdatainput" name="credit_cardid" value="6789" disabled></td>
	</tr>
	<tr>
		<td>Doc Authority:</td>
		<td><input type="text" class="userdatainput" name="credit_docauthority" value="NTUA" disabled></td>
	</tr>
	<tr>
		<td>Expiration Data:</td>
		<td><input type="text" class="userdatainput" name="credit_expirationdate" value="2010-09-15"
			disabled></td>
	</tr>
</table>
<table class="confirmationdata">
	<tr>
		<td colspan="2"><input type="checkbox" name="debitcard" onclick="set_disable(this);">Debit
		Card</td>
	</tr>
	<tr>
		<td>Card ID:</td>
		<td><input type="text" class="userdatainput" name="debit_cardid" value="4567" disabled></td>
	</tr>
	<tr>
		<td>Doc Authority:</td>
		<td><input type="text" class="userdatainput" name="debit_docauthority" value="EC" disabled></td>
	</tr>
	<tr>
		<td>Expiration Data:</td>
		<td><input type="text" class="userdatainput" name="debit_expirationdate" value="2011-09-15"
			disabled></td>
	</tr>
</table>
<table class="confirmationdata">
	<tr>
		<td colspan="2"><input type="checkbox" name="driverslicense" onclick="set_disable(this);">Drivers
		License</td>
	</tr>
	<tr>
		<td>Card ID:</td>
		<td><input type="text" class="userdatainput" name="drivers_cardid" value="102938" disabled></td>
	</tr>
	<tr>
		<td>Doc Authority:</td>
		<td><input type="text" class="userdatainput" name="drivers_docauthority" value="US" disabled></td>
	</tr>
	<tr>
		<td>Expiration Data:</td>
		<td><input type="text" class="userdatainput" name="drivers_expirationdate" value="2012-08-15"
			disabled></td>
	</tr>
</table>
<table class="confirmationdata">
	<tr>
		<td colspan="2"><input type="checkbox" name="passport" onclick="set_disable(this);">Passport</td>
	</tr>
	<tr>
		<td>Card ID:</td>
		<td><input type="text" class="userdatainput" name="passport_cardid" value="40320942" disabled></td>
	</tr>
	<tr>
		<td>Doc Authority:</td>
		<td><input type="text" class="userdatainput" name="passport_docauthority" value="UK" disabled></td>
	</tr>
	<tr>
		<td>Expiration Data:</td>
		<td><input type="text" class="userdatainput" name="passport_expirationdate"
			value="2012-01-01" disabled></td>
	</tr>
</table>
</fieldset>
<fieldset id="Certificate"><legend>Certificate</legend>
<table class="confirmationdata">
	<tr>
		<td colspan="2"><input type="checkbox" name="xmlsignaturex509v3certificate"
			onclick="set_disable(this);">X.509 Certificate</td>
	</tr>
	<tr>
		<td>Certificate Serial:</td>
		<td><input type="text" class="userdatainput" name="xmlsig_certserial" value="152" disabled></td>
	</tr>
	<tr>
		<td>Version:</td>
		<td><input type="text" class="userdatainput" name="xmlsig_certversion" value="1.0" disabled></td>
	</tr>
	<tr>
		<td>Valid From:</td>
		<td><input type="text" class="userdatainput" name="xmlsig_validfrom" value="2008-06-01"
			disabled></td>
	</tr>
	<tr>
		<td>Valid Until:</td>
		<td><input type="text" class="userdatainput" name="xmlsig_validuntil" value="2009-06-01"
			disabled></td>
	</tr>
	<tr>
		<td>Issuer:</td>
		<td><input type="text" class="userdatainput" name="xmlsig_certissuer"
			value="My Sign Authority" disabled></td>
	</tr>
	<tr>
		<td>Associated Data:</td>
		<td><input type="text" class="userdatainput" name="xmlsig_associateddata"
			value="rPqkLERTQeplW1yN0vdcoPkQ" disabled></td>
	</tr>
</table>
</fieldset>
<fieldset id="Cookie"><legend>Cookie</legend>
<table class="confirmationdata">
	<tr>
		<td colspan="2"><input type="checkbox" name="cookie" onclick="set_disable(this);">Cookie</td>
	</tr>
	<tr>
		<td>Name:</td>
		<td><input type="text" class="userdatainput" name="cookie_name" value="mycookie" disabled></td>
	</tr>
	<tr>
		<td>Value:</td>
		<td><input type="text" class="userdatainput" name="cookie_value" value="previous1226404880"
			disabled></td>
	</tr>
	<tr>
		<td>Expiration Date:</td>
		<td><input type="text" class="userdatainput" name="cookie_expdate" value="2009-12-20"
			disabled></td>
	</tr>
	<tr>
		<td>Path:</td>
		<td><input type="text" class="userdatainput" name="cookie_path" value="/mydir" disabled></td>
	</tr>
	<tr>
		<td>Secure:</td>
		<td><input type="text" class="userdatainput" name="cookie_secconn" value="No" disabled></td>
	</tr>
	<tr>
		<td>Domain:</td>
		<td><input type="text" class="userdatainput" name="cookie_dom" value=".www.qualipso.org"
			disabled></td>
	</tr>
</table>
</fieldset>
<fieldset id="Smartcard"><legend>Smart Card</legend>
<table class="confirmationdata">
	<tr>
		<td colspan="2"><input type="checkbox" name="smartcard" onclick="set_disable(this);">Smart
		Card</td>
	</tr>
	<tr>
		<td>Smart Card ID:</td>
		<td><input type="text" class="userdatainput" name="sc_smartcardid" value="589423847" disabled></td>
	</tr>
	<tr>
		<td>Valid Until:</td>
		<td><input type="text" class="userdatainput" name="sc_smartcardvaliduntil" value="2009-05-21"
			disabled></td>
	</tr>
	<tr>
		<td>Issuer:</td>
		<td><input type="text" class="userdatainput" name="sc_smartcardissuer" value="SC Authority"
			disabled></td>
	</tr>
	<tr>
		<td>Credential:</td>
		<td><input type="text" class="userdatainput" name="sc_credential" value="L9KwlWRhsQvA"
			disabled></td>
	</tr>
</table>
</fieldset>
<fieldset id="Login"><legend>Login</legend>
<table class="logindata">
	<tr>
		<td colspan="2"><input type="checkbox" name="loginwithpassphrase"
			onclick="set_disable(this);">Login with passphrase</td>
	</tr>
	<tr>
		<td>Login name:</td>
		<td><input type="text" class="userdatainput" name="login_loginname" value="qualipso" disabled></td>
	</tr>
	<tr>
		<td>Passphrase:</td>
		<td><input type="password" class="userdatainput" name="login_passphrase" value="letmein"
			disabled></td>
	</tr>
</table>
<table class="logindata">
	<tr>
		<td colspan="2"><input type="checkbox" name="onetimepassword" onclick="set_disable(this);">One
		Time Password</td>
	</tr>
	<tr>
		<td>Seed:</td>
		<td><input type="text" class="userdatainput" name="onetime_seed" value="4123453" disabled></td>
	</tr>
	<tr>
		<td>Hash Function:</td>
		<td><input type="text" class="userdatainput" name="onetime_hashfct" value="MD5" disabled></td>
	</tr>
	<tr>
		<td>Passphrase:</td>
		<td><input type="text" class="userdatainput" name="onetime_passphrase" value="RdxnMBnRPd"
			disabled></td>
	</tr>
</table>
</fieldset>
<fieldset id="Biometric"><legend>Biometric Data</legend>
<table class="confirmationdata">
	<tr>
		<td colspan="2"><input type="checkbox" name="fingerprint" onclick="set_disable(this);">Fingerprint</td>
	</tr>
	<tr>
		<td>Biometric Serial:</td>
		<td><input type="text" class="userdatainput" name="finger_biometricserial" value="7432"
			disabled></td>
	</tr>
	<tr>
		<td>Image Data:</td>
		<td><input type="text" class="userdatainput" name="finger_imagedata"
			value="iVBORw0KGgoAAAANSUhEUgAAAFAAAA" disabled></td>
	</tr>
</table>
<table class="confirmationdata">
	<tr>
		<td colspan="2"><input type="checkbox" name="voice" onclick="set_disable(this);">Voice</td>
	</tr>
	<tr>
		<td>Biometric Serial:</td>
		<td><input type="text" class="userdatainput" name="voice_biometricserial" value="35623"
			disabled></td>
	</tr>
	<tr>
		<td>Voice Data:</td>
		<td><input type="text" class="userdatainput" name="voice_sounddata"
			value="sH89caA4t3PZso4XMFw0alB9Fqxhrs" disabled></td>
	</tr>
</table>
</fieldset>
<input type="submit" id="btn" name="cred_submit" value="Submit Request" title="Submit DAML Request"></form>
</div>
</div>
<div id="footer"><jsp:include page="include/footer.inc"></jsp:include></div>
</body>
</html>