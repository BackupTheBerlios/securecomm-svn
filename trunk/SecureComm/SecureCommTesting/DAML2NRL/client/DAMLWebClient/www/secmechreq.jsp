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
	<li><strong>Security Mechanism</strong></li>
	<li><a href="/DAMLWebClient/secnotreq.jsp">Security Notation</a></li>
</ul>
</div>
<div id="main">
<h2>DAML Security Mechanism Request</h2>
<form id="damlwebform" name="damlwebform" action="DAMLReqMan" method="get">
<p id="subject"><label>Subject:</label> <input type="text" name="subjectname"
	value="Security Mechanism" readonly></p>
<fieldset id="Encryption"><legend>Encryption</legend>
<table class="confirmationdata">
	<tr>
		<td colspan="2"><input type="checkbox" name="encryption" onclick="set_disable(this);">Encryption</td>
	</tr>
	<tr>
		<td>Syntax:</td>
		<td><input type="text" class="userdatainput" name="encryption_syntax" value="OWL" disabled></td>
	</tr>
	<tr>
		<td>Encryption:</td>
		<td><input type="text" class="userdatainput" name="encryption_enc" value="XML-ENC" disabled></td>
	</tr>
	<tr>
		<td>Documentation:</td>
		<td><input type="text" class="userdatainput" name="encryption_documentation"
			value="OpenPGP Reference" disabled></td>
	</tr>
	<tr>
		<td>Relative Sec Notation:</td>
		<td><input type="text" class="userdatainput" name="encryption_relsecnotation"
			value="Anonymity" disabled></td>
	</tr>
	<tr>
		<td>Signature:</td>
		<td><input type="text" class="userdatainput" name="encryption_signature" value="SMIME-SIG"
			disabled></td>
	</tr>
	<tr>
		<td>Request Credentials:</td>
		<td><input type="text" class="userdatainput" name="encryption_reqcredential"
			value="ComposedCredential" disabled></td>
	</tr>
</table>
</fieldset>
<fieldset id="Datatransferprotocol"><legend>Data Transfer Protocol</legend>
<table class="confirmationdata">
	<tr>
		<td colspan="2"><input type="checkbox" name="datatransferprotocol"
			onclick="set_disable(this);">Data Transfer Prorocol</td>
	</tr>
	<tr>
		<td>Syntax:</td>
		<td><input type="text" class="userdatainput" name="data_syntax" value="SOAP" disabled></td>
	</tr>
	<tr>
		<td>Encryption:</td>
		<td><input type="text" class="userdatainput" name="data_enc" value="SSL" disabled></td>
	</tr>
	<tr>
		<td>Documentation:</td>
		<td><input type="text" class="userdatainput" name="data_documentation" value="SOAP Reference"
			disabled></td>
	</tr>
	<tr>
		<td>Relative Sec Notation:</td>
		<td><input type="text" class="userdatainput" name="data_relsecnotation" value="N/A" disabled></td>
	</tr>
	<tr>
		<td>Signature:</td>
		<td><input type="text" class="userdatainput" name="data_signature" value="HTTPS" disabled></td>
	</tr>
	<tr>
		<td>Request Credentials:</td>
		<td><input type="text" class="userdatainput" name="data_reqcredential" value="HTTPAccess"
			disabled></td>
	</tr>
</table>
</fieldset>
<fieldset id="Keyinformationprot"><legend>Key Information Protocol</legend>
<table class="confirmationdata">
	<tr>
		<td colspan="2"><input type="checkbox" name="keyinformationprot" onclick="set_disable(this);">Key
		Information Protocol</td>
	</tr>
	<tr>
		<td>Syntax:</td>
		<td><input type="text" class="userdatainput" name="keyinfo_syntax" value="X-KISS" disabled></td>
	</tr>
	<tr>
		<td>Encryption:</td>
		<td><input type="text" class="userdatainput" name="keyinfo_enc" value="SSL" disabled></td>
	</tr>
	<tr>
		<td>Documentation:</td>
		<td><input type="text" class="userdatainput" name="keyinfo_documentation"
			value="XKMS Reference" disabled></td>
	</tr>
	<tr>
		<td>Relative Sec Notation:</td>
		<td><input type="text" class="userdatainput" name="keyinfo_relsecnotation" value="Key Info"
			disabled></td>
	</tr>
	<tr>
		<td>Signature:</td>
		<td><input type="text" class="userdatainput" name="keyinfo_signature" value="Sig" disabled></td>
	</tr>
	<tr>
		<td>Request Credentials:</td>
		<td><input type="text" class="userdatainput" name="keyinfo_reqcredential" value="Login"
			disabled></td>
	</tr>
</table>
</fieldset>
<fieldset id="Keyregistrationprot"><legend>Key Registration Protocol</legend>
<table class="confirmationdata">
	<tr>
		<td colspan="2"><input type="checkbox" name="keyregistrationprot"
			onclick="set_disable(this);">Key Registration Protocol</td>
	</tr>
	<tr>
		<td>Syntax:</td>
		<td><input type="text" class="userdatainput" name="keyreg_syntax" value="X-KISS" disabled></td>
	</tr>
	<tr>
		<td>Encryption:</td>
		<td><input type="text" class="userdatainput" name="keyreg_enc" value="SSL" disabled></td>
	</tr>
	<tr>
		<td>Documentation:</td>
		<td><input type="text" class="userdatainput" name="keyreg_documentation"
			value="XKMS Reference" disabled></td>
	</tr>
	<tr>
		<td>Relative Sec Notation:</td>
		<td><input type="text" class="userdatainput" name="keyreg_relsecnotation" value="Key Reg"
			disabled></td>
	</tr>
	<tr>
		<td>Signature:</td>
		<td><input type="text" class="userdatainput" name="keyreg_signature" value="Sig" disabled></td>
	</tr>
	<tr>
		<td>Request Credentials:</td>
		<td><input type="text" class="userdatainput" name="keyreg_reqcredential" value="Login"
			disabled></td>
	</tr>
</table>
</fieldset>
<fieldset id="Signature"><legend>Signature</legend>
<table class="confirmationdata">
	<tr>
		<td colspan="2"><input type="checkbox" name="signature" onclick="set_disable(this);">Signature</td>
	</tr>
	<tr>
		<td>Syntax:</td>
		<td><input type="text" class="userdatainput" name="signature_syntax" value="PGP" disabled></td>
	</tr>
	<tr>
		<td>Encryption:</td>
		<td><input type="text" class="userdatainput" name="signature_enc" value="Sig SSL" disabled></td>
	</tr>
	<tr>
		<td>Documentation:</td>
		<td><input type="text" class="userdatainput" name="signature_documentation"
			value="OpenPGP Reference" disabled></td>
	</tr>
	<tr>
		<td>Relative Sec Notation:</td>
		<td><input type="text" class="userdatainput" name="signature_relsecnotation" value="Sig"
			disabled></td>
	</tr>
	<tr>
		<td>Signature:</td>
		<td><input type="text" class="userdatainput" name="signature_signature" value="Inline"
			disabled></td>
	</tr>
	<tr>
		<td>Request Credentials:</td>
		<td><input type="text" class="userdatainput" name="signature_reqcredential" value="Compound"
			disabled></td>
	</tr>
</table>
</fieldset>
<input type="submit" id="btn" name="secmech_submit" value="Submit Request"
	title="Submit DAML Request"></form>
</div>
</div>
<div id="footer"><jsp:include page="include/footer.inc"></jsp:include></div>
</body>
</html>