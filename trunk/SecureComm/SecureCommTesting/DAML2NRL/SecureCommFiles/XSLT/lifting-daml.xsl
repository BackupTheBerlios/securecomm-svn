<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns="http://www.qualipso.org/ontologies/credential-security-daml.owl#" xmlns:time="http://www.isi.edu/~pan/damltime/time-entry.owl#"
	xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#" xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
	version="2.0">

	<xsl:output method="xml" indent="yes" omit-xml-declaration="yes" />

	<xsl:template match="/">
		<xsl:for-each select="*">

			<xsl:if test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Cookie']">
				<Cookie rdf:ID="someCookie">
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Cookie']/*[local-name()='name']">
						<name>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Cookie']/*[local-name()='name']" />
						</name>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Cookie']/*[local-name()='path']">
						<path>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Cookie']/*[local-name()='path']" />
						</path>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Cookie']/*[local-name()='value']">
						<value>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Cookie']/*[local-name()='value']" />
						</value>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Cookie']/*[local-name()='secConn']">
						<secConn>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Cookie']/*[local-name()='secConn']" />
						</secConn>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Cookie']/*[local-name()='dom']">
						<dom>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Cookie']/*[local-name()='dom']" />
						</dom>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Cookie']/*[local-name()='expDate']">
						<expDate>
							<time:Instant rdf:ID="Valid">
								<rdfs:comment xml:lang="en">Cookie expiration
									date</rdfs:comment>
							</time:Instant>
						</expDate>
					</xsl:if>
				</Cookie>
			</xsl:if>

			<xsl:if test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Badge']">
				<Badge rdf:ID="someBadge">
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Badge']/*[local-name()='cardID']">
						<cardID>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Badge']/*[local-name()='cardID']" />
						</cardID>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Badge']/*[local-name()='docAuthority']">
						<docAuthority>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Badge']/*[local-name()='docAuthority']" />
						</docAuthority>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Badge']/*[local-name()='expirationDate']">
						<expirationDate>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Badge']/*[local-name()='expirationDate']" />
						</expirationDate>
					</xsl:if>
				</Badge>
			</xsl:if>

			<xsl:if test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='CreditCard']">
				<CreditCard rdf:ID="someCreditCard">
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='CreditCard']/*[local-name()='cardID']">
						<cardID>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='CreditCard']/*[local-name()='cardID']" />
						</cardID>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='CreditCard']/*[local-name()='docAuthority']">
						<docAuthority>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='CreditCard']/*[local-name()='docAuthority']" />
						</docAuthority>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='CreditCard']/*[local-name()='expirationDate']">
						<expirationDate>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='CreditCard']/*[local-name()='expirationDate']" />
						</expirationDate>
					</xsl:if>
				</CreditCard>
			</xsl:if>

			<xsl:if test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='DebitCard']">
				<DebitCard rdf:ID="someDebitCard">
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='DebitCard']/*[local-name()='cardID']">
						<cardID>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='DebitCard']/*[local-name()='cardID']" />
						</cardID>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='DebitCard']/*[local-name()='docAuthority']">
						<docAuthority>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='DebitCard']/*[local-name()='docAuthority']" />
						</docAuthority>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='DebitCard']/*[local-name()='expirationDate']">
						<expirationDate>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='DebitCard']/*[local-name()='expirationDate']" />
						</expirationDate>
					</xsl:if>
				</DebitCard>
			</xsl:if>

			<xsl:if test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='DriversLicense']">
				<DriversLicense rdf:ID="someDriversLicense">
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='DriversLicense']/*[local-name()='cardID']">
						<cardID>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='DriversLicense']/*[local-name()='cardID']" />
						</cardID>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='DriversLicense']/*[local-name()='docAuthority']">
						<docAuthority>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='DriversLicense']/*[local-name()='docAuthority']" />
						</docAuthority>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='DriversLicense']/*[local-name()='expirationDate']">
						<expirationDate>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='DriversLicense']/*[local-name()='expirationDate']" />
						</expirationDate>
					</xsl:if>
				</DriversLicense>
			</xsl:if>

			<xsl:if test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Passport']">
				<Passport rdf:ID="somePassport">
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Passport']/*[local-name()='cardID']">
						<cardID>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Passport']/*[local-name()='cardID']" />
						</cardID>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Passport']/*[local-name()='docAuthority']">
						<docAuthority>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Passport']/*[local-name()='docAuthority']" />
						</docAuthority>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Passport']/*[local-name()='expirationDate']">
						<expirationDate>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Passport']/*[local-name()='expirationDate']" />
						</expirationDate>
					</xsl:if>
				</Passport>
			</xsl:if>

			<xsl:if
				test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='XMLSignatureX509v3Certificate']">
				<XMLSignatureX509v3Certificate rdf:ID="someXMLSignatureX509v3Certificate">
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='XMLSignatureX509v3Certificate']/*[local-name()='certSerial']">
						<certSerial>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='XMLSignatureX509v3Certificate']/*[local-name()='certSerial']" />
						</certSerial>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='XMLSignatureX509v3Certificate']/*[local-name()='certVersion']">
						<certVersion>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='XMLSignatureX509v3Certificate']/*[local-name()='certVersion']" />
						</certVersion>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='XMLSignatureX509v3Certificate']/*[local-name()='validFrom']">
						<validFrom>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='XMLSignatureX509v3Certificate']/*[local-name()='validFrom']" />
						</validFrom>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='XMLSignatureX509v3Certificate']/*[local-name()='validUntil']">
						<validUntil>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='XMLSignatureX509v3Certificate']/*[local-name()='validUntil']" />
						</validUntil>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='XMLSignatureX509v3Certificate']/*[local-name()='certIssuer']">
						<certIssuer>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='XMLSignatureX509v3Certificate']/*[local-name()='certIssuer']" />
						</certIssuer>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='XMLSignatureX509v3Certificate']/*[local-name()='associatedData']">
						<associatedData>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='XMLSignatureX509v3Certificate']/*[local-name()='associatedData']" />
						</associatedData>
					</xsl:if>
				</XMLSignatureX509v3Certificate>
			</xsl:if>

			<xsl:if test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='SmartCard']">
				<SmartCard rdf:ID="someSmartCard">
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='SmartCard']/*[local-name()='smartCardID']">
						<smartCardID>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='SmartCard']/*[local-name()='smartCardID']" />
						</smartCardID>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='SmartCard']/*[local-name()='smartCardIssuer']">
						<smartCardIssuer>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='SmartCard']/*[local-name()='smartCardIssuer']" />
						</smartCardIssuer>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='SmartCard']/*[local-name()='smartCardValidUntil']">
						<smartCardValidUntil>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='SmartCard']/*[local-name()='smartCardValidUntil']" />
						</smartCardValidUntil>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='SmartCard']/*[local-name()='credential']">
						<credential>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='SmartCard']/*[local-name()='credential']" />
						</credential>
					</xsl:if>
				</SmartCard>
			</xsl:if>

			<xsl:if test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='LoginWithPassphrase']">
				<LoginWithPassphrase rdf:ID="someLoginWithPassphrase">
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='LoginWithPassphrase']/*[local-name()='loginName']">
						<loginName>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='LoginWithPassphrase']/*[local-name()='loginName']" />
						</loginName>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='LoginWithPassphrase']/*[local-name()='passphrase']">
						<passphrase>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='LoginWithPassphrase']/*[local-name()='passphrase']" />
						</passphrase>
					</xsl:if>
				</LoginWithPassphrase>
			</xsl:if>

			<xsl:if test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='OneTimePassword']">
				<OneTimePassword rdf:ID="someOneTimePassword">
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='OneTimePassword']/*[local-name()='seed']">
						<seed>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='OneTimePassword']/*[local-name()='seed']" />
						</seed>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='OneTimePassword']/*[local-name()='hashFct']">
						<hashFct>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='OneTimePassword']/*[local-name()='hashFct']" />
						</hashFct>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='OneTimePassword']/*[local-name()='passphrase']">
						<passphrase>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='OneTimePassword']/*[local-name()='passphrase']" />
						</passphrase>
					</xsl:if>
				</OneTimePassword>
			</xsl:if>

			<xsl:if test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Fingerprint']">
				<Fingerprint rdf:ID="someFingerprint">
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Fingerprint']/*[local-name()='biometricSerial']">
						<biometricSerial>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Fingerprint']/*[local-name()='biometricSerial']" />
						</biometricSerial>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Fingerprint']/*[local-name()='imageData']">
						<imageData>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Fingerprint']/*[local-name()='imageData']" />
						</imageData>
					</xsl:if>
				</Fingerprint>
			</xsl:if>

			<xsl:if test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Voice']">
				<Voice rdf:ID="someVoice">
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Voice']/*[local-name()='biometricSerial']">
						<biometricSerial>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Voice']/*[local-name()='biometricSerial']" />
						</biometricSerial>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Voice']/*[local-name()='soundData']">
						<soundData>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Voice']/*[local-name()='soundData']" />
						</soundData>
					</xsl:if>
				</Voice>
			</xsl:if>

			<xsl:if test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Encryption']">
				<Encryption rdf:ID="someEncryption">
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Encryption']/*[local-name()='syntax']">
						<syntax>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Encryption']/*[local-name()='syntax']" />
						</syntax>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Encryption']/*[local-name()='enc']">
						<enc>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Encryption']/*[local-name()='enc']" />
						</enc>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Encryption']/*[local-name()='documentation']">
						<documentation>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Encryption']/*[local-name()='documentation']" />
						</documentation>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Encryption']/*[local-name()='relSecNotation']">
						<relSecNotation>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Encryption']/*[local-name()='relSecNotation']" />
						</relSecNotation>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Encryption']/*[local-name()='sig']">
						<sig>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Encryption']/*[local-name()='sig']" />
						</sig>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Encryption']/*[local-name()='reqCredential']">
						<reqCredential>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Encryption']/*[local-name()='reqCredential']" />
						</reqCredential>
					</xsl:if>
				</Encryption>
			</xsl:if>

			<xsl:if test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='DataTransferProtocol']">
				<DataTransferProtocol rdf:ID="someDataTransferProtocol">
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='DataTransferProtocol']/*[local-name()='syntax']">
						<syntax>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='DataTransferProtocol']/*[local-name()='syntax']" />
						</syntax>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='DataTransferProtocol']/*[local-name()='enc']">
						<enc>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='DataTransferProtocol']/*[local-name()='enc']" />
						</enc>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='DataTransferProtocol']/*[local-name()='documentation']">
						<documentation>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='DataTransferProtocol']/*[local-name()='documentation']" />
						</documentation>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='DataTransferProtocol']/*[local-name()='relSecNotation']">
						<relSecNotation>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='DataTransferProtocol']/*[local-name()='relSecNotation']" />
						</relSecNotation>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='DataTransferProtocol']/*[local-name()='sig']">
						<sig>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='DataTransferProtocol']/*[local-name()='sig']" />
						</sig>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='DataTransferProtocol']/*[local-name()='reqCredential']">
						<reqCredential>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='DataTransferProtocol']/*[local-name()='reqCredential']" />
						</reqCredential>
					</xsl:if>
				</DataTransferProtocol>
			</xsl:if>

			<xsl:if test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='KeyInformationProt']">
				<KeyInformationProt rdf:ID="someKeyInformationProt">
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='KeyInformationProt']/*[local-name()='syntax']">
						<syntax>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='KeyInformationProt']/*[local-name()='syntax']" />
						</syntax>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='KeyInformationProt']/*[local-name()='enc']">
						<enc>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='KeyInformationProt']/*[local-name()='enc']" />
						</enc>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='KeyInformationProt']/*[local-name()='documentation']">
						<documentation>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='KeyInformationProt']/*[local-name()='documentation']" />
						</documentation>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='KeyInformationProt']/*[local-name()='relSecNotation']">
						<relSecNotation>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='KeyInformationProt']/*[local-name()='relSecNotation']" />
						</relSecNotation>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='KeyInformationProt']/*[local-name()='sig']">
						<sig>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='KeyInformationProt']/*[local-name()='sig']" />
						</sig>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='KeyInformationProt']/*[local-name()='reqCredential']">
						<reqCredential>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='KeyInformationProt']/*[local-name()='reqCredential']" />
						</reqCredential>
					</xsl:if>
				</KeyInformationProt>
			</xsl:if>

			<xsl:if test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='KeyRegistrationProt']">
				<KeyRegistrationProt rdf:ID="someKeyRegistrationProt">
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='KeyRegistrationProt']/*[local-name()='syntax']">
						<syntax>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='KeyRegistrationProt']/*[local-name()='syntax']" />
						</syntax>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='KeyRegistrationProt']/*[local-name()='enc']">
						<enc>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='KeyRegistrationProt']/*[local-name()='enc']" />
						</enc>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='KeyRegistrationProt']/*[local-name()='documentation']">
						<documentation>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='KeyRegistrationProt']/*[local-name()='documentation']" />
						</documentation>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='KeyRegistrationProt']/*[local-name()='relSecNotation']">
						<relSecNotation>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='KeyRegistrationProt']/*[local-name()='relSecNotation']" />
						</relSecNotation>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='KeyRegistrationProt']/*[local-name()='sig']">
						<sig>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='KeyRegistrationProt']/*[local-name()='sig']" />
						</sig>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='KeyRegistrationProt']/*[local-name()='reqCredential']">
						<reqCredential>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='KeyRegistrationProt']/*[local-name()='reqCredential']" />
						</reqCredential>
					</xsl:if>
				</KeyRegistrationProt>
			</xsl:if>

			<xsl:if test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Signature']">
				<Signature rdf:ID="someSignature">
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Signature']/*[local-name()='syntax']">
						<syntax>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Signature']/*[local-name()='syntax']" />
						</syntax>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Signature']/*[local-name()='enc']">
						<enc>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Signature']/*[local-name()='enc']" />
						</enc>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Signature']/*[local-name()='documentation']">
						<documentation>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Signature']/*[local-name()='documentation']" />
						</documentation>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Signature']/*[local-name()='relSecNotation']">
						<relSecNotation>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Signature']/*[local-name()='relSecNotation']" />
						</relSecNotation>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Signature']/*[local-name()='sig']">
						<sig>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Signature']/*[local-name()='sig']" />
						</sig>
					</xsl:if>
					<xsl:if
						test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Signature']/*[local-name()='reqCredential']">
						<reqCredential>
							<xsl:value-of
								select="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Signature']/*[local-name()='reqCredential']" />
						</reqCredential>
					</xsl:if>
				</Signature>
			</xsl:if>

			<xsl:if test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Authentication']">
				<SecurityNotation rdf:ID="someAuthentication" />
			</xsl:if>
			<xsl:if test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Authorization']">
				<SecurityNotation rdf:ID="someAuthorization" />
			</xsl:if>
			<xsl:if test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='AccessControl']">
				<SecurityNotation rdf:ID="someAccessControl" />
			</xsl:if>
			<xsl:if test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='DataIntegrity']">
				<SecurityNotation rdf:ID="someDataIntegrity" />
			</xsl:if>
			<xsl:if test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Confidentiality']">
				<SecurityNotation rdf:ID="someConfidentiality" />
			</xsl:if>
			<xsl:if test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Privacy']">
				<SecurityNotation rdf:ID="somePrivacy" />
			</xsl:if>
			<xsl:if test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='ExposureControl']">
				<SecurityNotation rdf:ID="someExposureControl" />
			</xsl:if>
			<xsl:if test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Anonymity']">
				<SecurityNotation rdf:ID="someAnonymity" />
			</xsl:if>
			<xsl:if test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Negotiation']">
				<SecurityNotation rdf:ID="someNegotiation" />
			</xsl:if>
			<xsl:if test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='Policy']">
				<SecurityNotation rdf:ID="somePolicy" />
			</xsl:if>
			<xsl:if test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='PolicyLanguage']">
				<SecurityNotation rdf:ID="somePolicyLanguage" />
			</xsl:if>
			<xsl:if test="//*[name()='saml:SubjectConfirmationData']/*[local-name()='KeyDistribution']">
				<SecurityNotation rdf:ID="someKeyDistribution" />
			</xsl:if>

		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>