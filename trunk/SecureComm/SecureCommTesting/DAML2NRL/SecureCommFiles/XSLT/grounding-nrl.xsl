<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
	xmlns:exsl="http://exslt.org/common" xmlns:env="http://schemas.xmlsoap.org/soap/envelope/"
	xmlns="http://www.qualipso.org/ontologies/credential-security-nrl.owl#" version="2.0">

	<xsl:output method="xml" media-type="text/xml" indent="yes" omit-xml-declaration="yes" />

	<xsl:param name="BRIDGE_RESULT">
		NULL
	</xsl:param>

	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
		</xsl:copy>
	</xsl:template>

	<xsl:template priority="2" match="*">
		<xsl:variable name="elem">
			<xsl:value-of select="name(.)" />
		</xsl:variable>
		<xsl:element name="{$elem}" namespace="{namespace-uri()}">
			<xsl:apply-templates select="@*|node()" />
		</xsl:element>
	</xsl:template>

	<!--<xsl:template match="@*[local-name()=mustUnderstand and .='1']"> -->
	<xsl:template priority="10" match="@*[local-name()='mustUnderstand' and .='1']">
		<xsl:variable name="mustUnd">
			<xsl:value-of select="name(.)" />
		</xsl:variable>
		<xsl:attribute name="{$mustUnd}">0</xsl:attribute>
	</xsl:template>

	<!-- delete these elements since they are mapped to other names -->
	<!--
		<xsl:template priority="10" match="*[local-name()='name' or local-name()='path' or
		local-name()='value' or local-name()='expDate' or local-name()='secConn' or local-name()='dom' or
		local-name()='Assertion' or local-name()='Subject' or local-name()='SubjectConfirmation' or
		local-name()='SubjectConfirmationData' or local-name()='NameID']">
	-->
	<xsl:template priority="10" match="*[local-name()='SubjectConfirmationData']//node()">
		<xsl:apply-templates select="*" />
	</xsl:template>

	<xsl:template priority="10" match="*[local-name()='Cookie'] ">
		<Cookie>
			<xsl:apply-templates select="*" />
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='Cookie']/*[local-name()='name']">
				<xsl:element name="name">
					<xsl:value-of select="document($BRIDGE_RESULT)//*[local-name()='Cookie']/*[local-name()='name']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='Cookie']/*[local-name()='path']">
				<xsl:element name="path">
					<xsl:value-of select="document($BRIDGE_RESULT)//*[local-name()='Cookie']/*[local-name()='path']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='Cookie']/*[local-name()='value']">
				<xsl:element name="value">
					<xsl:value-of select="document($BRIDGE_RESULT)//*[local-name()='Cookie']/*[local-name()='value']" />
				</xsl:element>
			</xsl:if>
		</Cookie>
	</xsl:template>

	<xsl:template priority="10" match="*[local-name()='Badge'] ">
		<Badge>
			<xsl:apply-templates select="*" />
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='Badge']/*[local-name()='physicalTokenID']">
				<xsl:element name="physicalTokenID">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='Badge']/*[local-name()='physicalTokenID']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='Badge']/*[local-name()='physTokenIssuer']">
				<xsl:element name="physTokenIssuer">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='Badge']/*[local-name()='physTokenIssuer']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='Badge']/*[local-name()='expDate']">
				<xsl:element name="expDate">
					<xsl:value-of select="document($BRIDGE_RESULT)//*[local-name()='Badge']/*[local-name()='expDate']" />
				</xsl:element>
			</xsl:if>
		</Badge>
	</xsl:template>

	<xsl:template priority="10" match="*[local-name()='CreditCard'] ">
		<CreditCard>
			<xsl:apply-templates select="*" />
			<xsl:if
				test="document($BRIDGE_RESULT)//*[local-name()='CreditCard']/*[local-name()='physicalTokenID']">
				<xsl:element name="physicalTokenID">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='CreditCard']/*[local-name()='physicalTokenID']" />
				</xsl:element>
			</xsl:if>
			<xsl:if
				test="document($BRIDGE_RESULT)//*[local-name()='CreditCard']/*[local-name()='physTokenIssuer']">
				<xsl:element name="physTokenIssuer">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='CreditCard']/*[local-name()='physTokenIssuer']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='CreditCard']/*[local-name()='expDate']">
				<xsl:element name="expDate">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='CreditCard']/*[local-name()='expDate']" />
				</xsl:element>
			</xsl:if>
		</CreditCard>
	</xsl:template>

	<xsl:template priority="10" match="*[local-name()='DebitCard'] ">
		<DebitCard>
			<xsl:apply-templates select="*" />
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='DebitCard']/*[local-name()='physicalTokenID']">
				<xsl:element name="physicalTokenID">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='DebitCard']/*[local-name()='physicalTokenID']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='DebitCard']/*[local-name()='physTokenIssuer']">
				<xsl:element name="physTokenIssuer">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='DebitCard']/*[local-name()='physTokenIssuer']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='DebitCard']/*[local-name()='expDate']">
				<xsl:element name="expDate">
					<xsl:value-of select="document($BRIDGE_RESULT)//*[local-name()='DebitCard']/*[local-name()='expDate']" />
				</xsl:element>
			</xsl:if>
		</DebitCard>
	</xsl:template>

	<xsl:template priority="10" match="*[local-name()='DriversLicense'] ">
		<DriversLicense>
			<xsl:apply-templates select="*" />
			<xsl:if
				test="document($BRIDGE_RESULT)//*[local-name()='DriversLicense']/*[local-name()='physicalTokenID']">
				<xsl:element name="physicalTokenID">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='DriversLicense']/*[local-name()='physicalTokenID']" />
				</xsl:element>
			</xsl:if>
			<xsl:if
				test="document($BRIDGE_RESULT)//*[local-name()='DriversLicense']/*[local-name()='physTokenIssuer']">
				<xsl:element name="physTokenIssuer">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='DriversLicense']/*[local-name()='physTokenIssuer']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='DriversLicense']/*[local-name()='expDate']">
				<xsl:element name="expDate">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='DriversLicense']/*[local-name()='expDate']" />
				</xsl:element>
			</xsl:if>
		</DriversLicense>
	</xsl:template>

	<xsl:template priority="10" match="*[local-name()='Passport'] ">
		<Passport>
			<xsl:apply-templates select="*" />
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='Passport']/*[local-name()='physicalTokenID']">
				<xsl:element name="physicalTokenID">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='Passport']/*[local-name()='physicalTokenID']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='Passport']/*[local-name()='physTokenIssuer']">
				<xsl:element name="physTokenIssuer">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='Passport']/*[local-name()='physTokenIssuer']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='Passport']/*[local-name()='expDate']">
				<xsl:element name="expDate">
					<xsl:value-of select="document($BRIDGE_RESULT)//*[local-name()='Passport']/*[local-name()='expDate']" />
				</xsl:element>
			</xsl:if>
		</Passport>
	</xsl:template>

	<xsl:template priority="10" match="*[local-name()='XMLSignatureX509v3Certificate'] ">
		<X.509Certificate>
			<xsl:apply-templates select="*" />
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='XMLSignatureX509v3Certificate']/*[local-name()='serialNumber']">
				<xsl:element name="serialNumber">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='XMLSignatureX509v3Certificate']/*[local-name()='serialNumber']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='XMLSignatureX509v3Certificate']/*[local-name()='version']">
				<xsl:element name="version">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='XMLSignatureX509v3Certificate']/*[local-name()='version']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='XMLSignatureX509v3Certificate']/*[local-name()='notBefore']">
				<xsl:element name="notBefore">
					<xsl:value-of select="document($BRIDGE_RESULT)//*[local-name()='XMLSignatureX509v3Certificate']/*[local-name()='notBefore']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='XMLSignatureX509v3Certificate']/*[local-name()='notAfter']">
				<xsl:element name="notAfter">
					<xsl:value-of select="document($BRIDGE_RESULT)//*[local-name()='XMLSignatureX509v3Certificate']/*[local-name()='notAfter']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='XMLSignatureX509v3Certificate']/*[local-name()='issuer']">
				<xsl:element name="issuer">
					<xsl:value-of select="document($BRIDGE_RESULT)//*[local-name()='XMLSignatureX509v3Certificate']/*[local-name()='issuer']" />
				</xsl:element>
			</xsl:if>
		</X.509Certificate>
	</xsl:template>

	<xsl:template priority="10" match="*[local-name()='SmartCard'] ">
		<SmartCard>
			<xsl:apply-templates select="*" />
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='SmartCard']/*[local-name()='physicalTokenID']">
				<xsl:element name="physicalTokenID">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='SmartCard']/*[local-name()='physicalTokenID']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='SmartCard']/*[local-name()='physTokenIssuer']">
				<xsl:element name="physTokenIssuer">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='SmartCard']/*[local-name()='physTokenIssuer']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='SmartCard']/*[local-name()='expDate']">
				<xsl:element name="expDate">
					<xsl:value-of select="document($BRIDGE_RESULT)//*[local-name()='SmartCard']/*[local-name()='expDate']" />
				</xsl:element>
			</xsl:if>
		</SmartCard>
	</xsl:template>

	<xsl:template priority="10" match="*[local-name()='ComposedCredential'] ">
		<Password>
			<xsl:apply-templates select="*" />
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='ComposedCredential']/*[local-name()='userName']">
				<xsl:element name="userName">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='ComposedCredential']/*[local-name()='userName']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='ComposedCredential']/*[local-name()='passValue']">
				<xsl:element name="passValue">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='ComposedCredential']/*[local-name()='passValue']" />
				</xsl:element>
			</xsl:if>
		</Password>
	</xsl:template>

	<xsl:template priority="10" match="*[local-name()='OneTimePassword'] ">
		<OneTimePassword>
			<xsl:apply-templates select="*" />
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='OneTimePassword']/*[local-name()='userName']">
				<xsl:element name="userName">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='OneTimePassword']/*[local-name()='userName']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='OneTimePassword']/*[local-name()='paswSeed']">
				<xsl:element name="paswSeed">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='OneTimePassword']/*[local-name()='paswSeed']" />
				</xsl:element>
			</xsl:if>
		</OneTimePassword>
	</xsl:template>

	<xsl:template priority="10" match="*[local-name()='Fingerprint'] ">
		<Fingerprint>
			<xsl:apply-templates select="*" />
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='Fingerprint']/*[local-name()='bioTokenSerial']">
				<xsl:element name="bioTokenSerial">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='Fingerprint']/*[local-name()='bioTokenSerial']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='Fingerprint']/*[local-name()='bioTokenData']">
				<xsl:element name="bioTokenData">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='Fingerprint']/*[local-name()='bioTokenData']" />
				</xsl:element>
			</xsl:if>
		</Fingerprint>
	</xsl:template>

	<xsl:template priority="10" match="*[local-name()='Voice'] ">
		<Voice>
			<xsl:apply-templates select="*" />
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='Voice']/*[local-name()='bioTokenSerial']">
				<xsl:element name="bioTokenSerial">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='Voice']/*[local-name()='bioTokenSerial']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='Voice']/*[local-name()='bioTokenData']">
				<xsl:element name="bioTokenData">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='Voice']/*[local-name()='bioTokenData']" />
				</xsl:element>
			</xsl:if>
		</Voice>
	</xsl:template>

	<xsl:template priority="10" match="*[local-name()='EncryptionProtocol'] ">
		<EncryptionProtocol>
			<xsl:apply-templates select="*" />
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='EncryptionProtocol']/*[local-name()='hasAlgorithm']">
				<xsl:element name="hasAlgorithm">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='EncryptionProtocol']/*[local-name()='hasAlgorithm']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='EncryptionProtocol']/*[local-name()='supportsSecurityObjective']">
				<xsl:element name="supportsSecurityObjective">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='EncryptionProtocol']/*[local-name()='supportsSecurityObjective']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='EncryptionProtocol']/*[local-name()='hasEncryptionAlgorithm']">
				<xsl:element name="hasEncryptionAlgorithm">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='EncryptionProtocol']/*[local-name()='hasEncryptionAlgorithm']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='EncryptionProtocol']/*[local-name()='reqCredential']">
				<xsl:element name="reqCredential">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='EncryptionProtocol']/*[local-name()='reqCredential']" />
				</xsl:element>
			</xsl:if>
		</EncryptionProtocol>
	</xsl:template>

	<xsl:template priority="10" match="*[local-name()='KeyManagementProtocol'] ">
		<KeyManagementProtocol>
			<xsl:apply-templates select="*" />
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='KeyManagementProtocol']/*[local-name()='hasAlgorithm']">
				<xsl:element name="hasAlgorithm">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='KeyManagementProtocol']/*[local-name()='hasAlgorithm']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='KeyManagementProtocol']/*[local-name()='supportsSecurityObjective']">
				<xsl:element name="supportsSecurityObjective">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='KeyManagementProtocol']/*[local-name()='supportsSecurityObjective']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='KeyManagementProtocol']/*[local-name()='hasEncryptionAlgorithm']">
				<xsl:element name="hasEncryptionAlgorithm">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='KeyManagementProtocol']/*[local-name()='hasEncryptionAlgorithm']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='KeyManagementProtocol']/*[local-name()='reqCredential']">
				<xsl:element name="reqCredential">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='KeyManagementProtocol']/*[local-name()='reqCredential']" />
				</xsl:element>
			</xsl:if>
		</KeyManagementProtocol>
	</xsl:template>

	<xsl:template priority="10" match="*[local-name()='KeyManagementProtocol'] ">
		<KeyManagementProtocol>
			<xsl:apply-templates select="*" />
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='KeyManagementProtocol']/*[local-name()='hasAlgorithm']">
				<xsl:element name="hasAlgorithm">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='KeyManagementProtocol']/*[local-name()='hasAlgorithm']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='KeyManagementProtocol']/*[local-name()='supportsSecurityObjective']">
				<xsl:element name="supportsSecurityObjective">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='KeyManagementProtocol']/*[local-name()='supportsSecurityObjective']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='KeyManagementProtocol']/*[local-name()='hasEncryptionAlgorithm']">
				<xsl:element name="hasEncryptionAlgorithm">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='KeyManagementProtocol']/*[local-name()='hasEncryptionAlgorithm']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='KeyManagementProtocol']/*[local-name()='reqCredential']">
				<xsl:element name="reqCredential">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='KeyManagementProtocol']/*[local-name()='reqCredential']" />
				</xsl:element>
			</xsl:if>
		</KeyManagementProtocol>
	</xsl:template>

	<xsl:template priority="10" match="*[local-name()='SignatureProtocol'] ">
		<SignatureProtocol>
			<xsl:apply-templates select="*" />
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='SignatureProtocol']/*[local-name()='hasAlgorithm']">
				<xsl:element name="hasAlgorithm">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='SignatureProtocol']/*[local-name()='hasAlgorithm']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='SignatureProtocol']/*[local-name()='supportsSecurityObjective']">
				<xsl:element name="supportsSecurityObjective">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='SignatureProtocol']/*[local-name()='supportsSecurityObjective']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='SignatureProtocol']/*[local-name()='hasEncryptionAlgorithm']">
				<xsl:element name="hasEncryptionAlgorithm">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='SignatureProtocol']/*[local-name()='hasEncryptionAlgorithm']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='SignatureProtocol']/*[local-name()='reqCredential']">
				<xsl:element name="reqCredential">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='SignatureProtocol']/*[local-name()='reqCredential']" />
				</xsl:element>
			</xsl:if>
		</SignatureProtocol>
	</xsl:template>

	<xsl:template priority="10" match="*[local-name()='SignatureProtocol'] ">
		<SignatureProtocol>
			<xsl:apply-templates select="*" />
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='SignatureProtocol']/*[local-name()='hasAlgorithm']">
				<xsl:element name="hasAlgorithm">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='SignatureProtocol']/*[local-name()='hasAlgorithm']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='SignatureProtocol']/*[local-name()='supportsSecurityObjective']">
				<xsl:element name="supportsSecurityObjective">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='SignatureProtocol']/*[local-name()='supportsSecurityObjective']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='SignatureProtocol']/*[local-name()='hasEncryptionAlgorithm']">
				<xsl:element name="hasEncryptionAlgorithm">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='SignatureProtocol']/*[local-name()='hasEncryptionAlgorithm']" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="document($BRIDGE_RESULT)//*[local-name()='SignatureProtocol']/*[local-name()='reqCredential']">
				<xsl:element name="reqCredential">
					<xsl:value-of
						select="document($BRIDGE_RESULT)//*[local-name()='SignatureProtocol']/*[local-name()='reqCredential']" />
				</xsl:element>
			</xsl:if>
		</SignatureProtocol>
	</xsl:template>

	<xsl:template priority="10" match="*[local-name()='Confidentiality'] ">
		<Confidentiality rdf:ID="someConfidentiality" />
	</xsl:template>
	<xsl:template priority="10" match="*[local-name()='Availability'] ">
		<Availability rdf:ID="someAvailability" />
	</xsl:template>
	<xsl:template priority="10" match="*[local-name()='UserAuthentication'] ">
		<UserAuthentication rdf:ID="someUserAuthentication" />
	</xsl:template>
	<xsl:template priority="10" match="*[local-name()='MessageAuthentication'] ">
		<MessageAuthentication rdf:ID="someMessageAuthentication" />
	</xsl:template>
	<xsl:template priority="10" match="*[local-name()='Authorization'] ">
		<Authorization rdf:ID="someAuthorization" />
	</xsl:template>
	<xsl:template priority="10" match="*[local-name()='MessageIntegrity'] ">
		<MessageIntegrity rdf:ID="someMessageIntegrity" />
	</xsl:template>
	<xsl:template priority="10" match="*[local-name()='KeyManagement'] ">
		<KeyManagement rdf:ID="someKeyManagement" />
	</xsl:template>
	<xsl:template priority="10" match="*[local-name()='Separation'] ">
		<Separation rdf:ID="someSeparation" />
	</xsl:template>
	<xsl:template priority="10" match="*[local-name()='TrafficHiding'] ">
		<TrafficHiding rdf:ID="someTrafficHiding" />
	</xsl:template>
	<xsl:template priority="10" match="*[local-name()='Anonymity'] ">
		<Anonymity rdf:ID="someAnonymity" />
	</xsl:template>

</xsl:stylesheet>