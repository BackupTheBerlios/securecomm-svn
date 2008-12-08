package org.qualipso.damlservice.client;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.impl.OMNamespaceImpl;
import org.apache.axiom.soap.SOAPFactory;
import org.opensaml.saml2.core.Subject;

public class DAMLCredentialRequest extends SAMLSOAPClient {
	public boolean hasBadge;
	private String badgeCardID;
	private String badgeDocAuthority;
	private String badgeExpirationDate;
	public boolean hasCreditCard;
	private String creditCardID;
	private String creditDocAuthority;
	private String creditExpirationDate;
	public boolean hasDebitCard;
	private String debitCardID;
	private String debitDocAuthority;
	private String debitExpirationDate;
	public boolean hasDriversLicense;
	private String driversCardID;
	private String driversDocAuthority;
	private String driversExpirationDate;
	public boolean hasPassport;
	private String passportCardID;
	private String passportDocAuthority;
	private String passportExpirationDate;
	public boolean hasLoginWithPassphrase;
	private String loginLoginName;
	private String loginPassphrase;
	public boolean hasOneTimePassword;
	private String onetimeSeed;
	private String onetimeHashFct;
	private String onetimePassphrase;
	public boolean hasXMLSignatureX509v3Certificate;
	private String xmlsigCertSerial;
	private String xmlsigCertVersion;
	private String xmlsigValidFrom;
	private String xmlsigValidUntil;
	private String xmlsigCertIssuer;
	private String xmlsigAssociatedData;
	public boolean hasSmartCard;
	private String scSmartCardID;
	private String scSmartCardValidUntil;
	private String scSmartCardIssuer;
	private String scCredential;
	public boolean hasFingerPrint;
	private String fingerBiometricSerial;
	private String fingerImageData;
	public boolean hasVoice;
	private String voiceBiometricSerial;
	private String voiceSoundData;
	public boolean hasCookie;
	private String cookieName;
	private String cookieValue;
	private String cookieExpDate;
	private String cookiePath;
	private String cookieSecConn;
	private String cookieDom;
		
	/** 
	 * Default Constructor
	 */
	public DAMLCredentialRequest() {
		super();
	}

	/**
	 * @param webServiceURL
	 */
	public DAMLCredentialRequest(String webServiceURL) {
		super(webServiceURL);
	}

 	/**
 	 * Build DAML information request by overriding parent method
 	 * @param sElement 
 	 * @param sNameSpace 
 	 * @param sFactory 
 	 * @return
 	 */
	@Override
	public OMElement buildDAMLInfoRequest(SOAPFactory sFactory, OMNamespace sNameSpace) {
		
		// Convert Qname to OMNameSpace
		OMNamespaceImpl samlNameSpace=new OMNamespaceImpl(Subject.DEFAULT_ELEMENT_NAME.getNamespaceURI(),Subject.DEFAULT_ELEMENT_NAME.getPrefix());
		OMElement infoRequest=sFactory.createOMElement("SubjectConfirmationData",samlNameSpace);
		// Add Recipient attribute
		infoRequest.addAttribute("Recipient", "NRL Service", samlNameSpace);
		
		// Check all properties for possible values
		OMElement parentElement;
		OMElement childElement;	
		if (hasBadge) {
			parentElement=sFactory.createOMElement("Badge", sNameSpace);
			childElement=sFactory.createOMElement("cardID",sNameSpace);
			childElement.setText(badgeCardID);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("docAuthority",sNameSpace);
			childElement.setText(badgeDocAuthority);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("expirationDate",sNameSpace);
			childElement.setText(badgeExpirationDate);
			parentElement.addChild(childElement);
			infoRequest.addChild(parentElement);
		}
		if (hasCreditCard) {
			parentElement=sFactory.createOMElement("CreditCard", sNameSpace);
			childElement=sFactory.createOMElement("cardID",sNameSpace);
			childElement.setText(creditCardID);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("docAuthority",sNameSpace);
			childElement.setText(creditDocAuthority);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("expirationDate",sNameSpace);
			childElement.setText(creditExpirationDate);
			parentElement.addChild(childElement);
			infoRequest.addChild(parentElement);
		}
		if (hasDebitCard) {
			parentElement=sFactory.createOMElement("DebitCard", sNameSpace);
			childElement=sFactory.createOMElement("cardID",sNameSpace);
			childElement.setText(debitCardID);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("docAuthority",sNameSpace);
			childElement.setText(debitDocAuthority);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("expirationDate",sNameSpace);
			childElement.setText(debitExpirationDate);
			parentElement.addChild(childElement);
			infoRequest.addChild(parentElement);
		}
		if (hasDriversLicense) {
			parentElement=sFactory.createOMElement("DriversLicense", sNameSpace);
			childElement=sFactory.createOMElement("cardID",sNameSpace);
			childElement.setText(driversCardID);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("docAuthority",sNameSpace);
			childElement.setText(driversDocAuthority);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("expirationDate",sNameSpace);
			childElement.setText(driversExpirationDate);
			parentElement.addChild(childElement);
			infoRequest.addChild(parentElement);
		}
		if (hasPassport) {
			parentElement=sFactory.createOMElement("Passport", sNameSpace);
			childElement=sFactory.createOMElement("cardID",sNameSpace);
			childElement.setText(passportCardID);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("docAuthority",sNameSpace);
			childElement.setText(passportDocAuthority);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("expirationDate",sNameSpace);
			childElement.setText(passportExpirationDate);
			parentElement.addChild(childElement);
			infoRequest.addChild(parentElement);
		}
		if (hasLoginWithPassphrase) {
			parentElement=sFactory.createOMElement("LoginWithPassphrase", sNameSpace);
			childElement=sFactory.createOMElement("loginName",sNameSpace);
			childElement.setText(loginLoginName);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("passphrase",sNameSpace);
			childElement.setText(loginPassphrase);
			parentElement.addChild(childElement);
			infoRequest.addChild(parentElement);
		}
		if (hasOneTimePassword) {
			parentElement=sFactory.createOMElement("OneTimePassword", sNameSpace);
			childElement=sFactory.createOMElement("seed",sNameSpace);
			childElement.setText(onetimeSeed);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("hashFct",sNameSpace);
			childElement.setText(onetimeHashFct);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("passphrase",sNameSpace);
			childElement.setText(onetimePassphrase);
			parentElement.addChild(childElement);
			infoRequest.addChild(parentElement);
		}
		if (hasXMLSignatureX509v3Certificate) {
			parentElement=sFactory.createOMElement("XMLSignatureX509v3Certificate", sNameSpace);
			childElement=sFactory.createOMElement("certSerial",sNameSpace);
			childElement.setText(xmlsigCertSerial);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("certVersion",sNameSpace);
			childElement.setText(xmlsigCertVersion);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("validFrom",sNameSpace);
			childElement.setText(xmlsigValidFrom);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("validUntil",sNameSpace);
			childElement.setText(xmlsigValidUntil);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("certIssuer",sNameSpace);
			childElement.setText(xmlsigCertIssuer);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("associatedData",sNameSpace);
			childElement.setText(xmlsigAssociatedData);
			parentElement.addChild(childElement);
			infoRequest.addChild(parentElement);
		}
		if (hasSmartCard) {
			parentElement=sFactory.createOMElement("SmartCard", sNameSpace);
			childElement=sFactory.createOMElement("smartCardID",sNameSpace);
			childElement.setText(scSmartCardID);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("smartCardValidUntil",sNameSpace);
			childElement.setText(scSmartCardValidUntil);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("smartCardIssuer",sNameSpace);
			childElement.setText(scSmartCardIssuer);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("Credential",sNameSpace);
			childElement.setText(scCredential);
			parentElement.addChild(childElement);
			infoRequest.addChild(parentElement);
		}
		if (hasFingerPrint) {
			parentElement=sFactory.createOMElement("Fingerprint", sNameSpace);
			childElement=sFactory.createOMElement("biometricSerial",sNameSpace);
			childElement.setText(fingerBiometricSerial);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("imageData",sNameSpace);
			childElement.setText(fingerImageData);
			parentElement.addChild(childElement);
			infoRequest.addChild(parentElement);
		}
		if (hasVoice) {
			parentElement=sFactory.createOMElement("Voice", sNameSpace);
			childElement=sFactory.createOMElement("biometricSerial",sNameSpace);
			childElement.setText(voiceBiometricSerial);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("soundData",sNameSpace);
			childElement.setText(voiceSoundData);
			parentElement.addChild(childElement);
			infoRequest.addChild(parentElement);
		}
		if (hasCookie) {
			parentElement=sFactory.createOMElement("Cookie", sNameSpace);
			childElement=sFactory.createOMElement("name",sNameSpace);
			childElement.setText(cookieName);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("value",sNameSpace);
			childElement.setText(cookieValue);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("expDate",sNameSpace);
			childElement.setText(cookieExpDate);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("path",sNameSpace);
			childElement.setText(cookiePath);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("secConn",sNameSpace);
			childElement.setText(cookieSecConn);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("dom",sNameSpace);
			childElement.setText(cookieDom);
			parentElement.addChild(childElement);
			infoRequest.addChild(parentElement);
		}
		
		return infoRequest;
	}

	/**
	 * @return the badgeCardID
	 */
	public String getBadgeCardID() {
		return badgeCardID;
	}

	/**
	 * @param badgeCardID the badgeCardID to set
	 */
	public void setBadgeCardID(String badgeCardID) {
		this.badgeCardID = badgeCardID;
	}

	/**
	 * @return the badgeDocAuthority
	 */
	public String getBadgeDocAuthority() {
		return badgeDocAuthority;
	}

	/**
	 * @param badgeDocAuthority the badgeDocAuthority to set
	 */
	public void setBadgeDocAuthority(String badgeDocAuthority) {
		this.badgeDocAuthority = badgeDocAuthority;
	}

	/**
	 * @return the badgeExpirationDate
	 */
	public String getBadgeExpirationDate() {
		return badgeExpirationDate;
	}

	/**
	 * @param badgeExpirationDate the badgeExpirationDate to set
	 */
	public void setBadgeExpirationDate(String badgeExpirationDate) {
		this.badgeExpirationDate = badgeExpirationDate;
	}

	/**
	 * @return the creditCardID
	 */
	public String getCreditCardID() {
		return creditCardID;
	}

	/**
	 * @param creditCardID the creditCardID to set
	 */
	public void setCreditCardID(String creditCardID) {
		this.creditCardID = creditCardID;
	}

	/**
	 * @return the creditDocAuthority
	 */
	public String getCreditDocAuthority() {
		return creditDocAuthority;
	}

	/**
	 * @param creditDocAuthority the creditDocAuthority to set
	 */
	public void setCreditDocAuthority(String creditDocAuthority) {
		this.creditDocAuthority = creditDocAuthority;
	}

	/**
	 * @return the creditExpirationDate
	 */
	public String getCreditExpirationDate() {
		return creditExpirationDate;
	}

	/**
	 * @param creditExpirationDate the creditExpirationDate to set
	 */
	public void setCreditExpirationDate(String creditExpirationDate) {
		this.creditExpirationDate = creditExpirationDate;
	}

	/**
	 * @return the debitCardID
	 */
	public String getDebitCardID() {
		return debitCardID;
	}

	/**
	 * @param debitCardID the debitCardID to set
	 */
	public void setDebitCardID(String debitCardID) {
		this.debitCardID = debitCardID;
	}

	/**
	 * @return the debitDocAuthority
	 */
	public String getDebitDocAuthority() {
		return debitDocAuthority;
	}

	/**
	 * @param debitDocAuthority the debitDocAuthority to set
	 */
	public void setDebitDocAuthority(String debitDocAuthority) {
		this.debitDocAuthority = debitDocAuthority;
	}

	/**
	 * @return the debitExpirationDate
	 */
	public String getDebitExpirationDate() {
		return debitExpirationDate;
	}

	/**
	 * @param debitExpirationDate the debitExpirationDate to set
	 */
	public void setDebitExpirationDate(String debitExpirationDate) {
		this.debitExpirationDate = debitExpirationDate;
	}

	/**
	 * @return the driversCardID
	 */
	public String getDriversCardID() {
		return driversCardID;
	}

	/**
	 * @param driversCardID the driversCardID to set
	 */
	public void setDriversCardID(String driversCardID) {
		this.driversCardID = driversCardID;
	}

	/**
	 * @return the driversDocAuthority
	 */
	public String getDriversDocAuthority() {
		return driversDocAuthority;
	}

	/**
	 * @param driversDocAuthority the driversDocAuthority to set
	 */
	public void setDriversDocAuthority(String driversDocAuthority) {
		this.driversDocAuthority = driversDocAuthority;
	}

	/**
	 * @return the driversExpirationDate
	 */
	public String getDriversExpirationDate() {
		return driversExpirationDate;
	}

	/**
	 * @param driversExpirationDate the driversExpirationDate to set
	 */
	public void setDriversExpirationDate(String driversExpirationDate) {
		this.driversExpirationDate = driversExpirationDate;
	}

	/**
	 * @return the passportCardID
	 */
	public String getPassportCardID() {
		return passportCardID;
	}

	/**
	 * @param passportCardID the passportCardID to set
	 */
	public void setPassportCardID(String passportCardID) {
		this.passportCardID = passportCardID;
	}

	/**
	 * @return the passportDocAuthority
	 */
	public String getPassportDocAuthority() {
		return passportDocAuthority;
	}

	/**
	 * @param passportDocAuthority the passportDocAuthority to set
	 */
	public void setPassportDocAuthority(String passportDocAuthority) {
		this.passportDocAuthority = passportDocAuthority;
	}

	/**
	 * @return the passportExpirationDate
	 */
	public String getPassportExpirationDate() {
		return passportExpirationDate;
	}

	/**
	 * @param passportExpirationDate the passportExpirationDate to set
	 */
	public void setPassportExpirationDate(String passportExpirationDate) {
		this.passportExpirationDate = passportExpirationDate;
	}

	/**
	 * @return the loginLoginName
	 */
	public String getLoginLoginName() {
		return loginLoginName;
	}

	/**
	 * @param loginLoginName the loginLoginName to set
	 */
	public void setLoginLoginName(String loginLoginName) {
		this.loginLoginName = loginLoginName;
	}

	/**
	 * @return the loginPassphrase
	 */
	public String getLoginPassphrase() {
		return loginPassphrase;
	}

	/**
	 * @param loginPassphrase the loginPassphrase to set
	 */
	public void setLoginPassphrase(String loginPassphrase) {
		this.loginPassphrase = loginPassphrase;
	}

	/**
	 * @return the onetimeSeed
	 */
	public String getOnetimeSeed() {
		return onetimeSeed;
	}

	/**
	 * @param onetimeSeed the onetimeSeed to set
	 */
	public void setOnetimeSeed(String onetimeSeed) {
		this.onetimeSeed = onetimeSeed;
	}

	/**
	 * @return the onetimeHashFct
	 */
	public String getOnetimeHashFct() {
		return onetimeHashFct;
	}

	/**
	 * @param onetimeHashFct the onetimeHashFct to set
	 */
	public void setOnetimeHashFct(String onetimeHashFct) {
		this.onetimeHashFct = onetimeHashFct;
	}

	/**
	 * @return the onetimePassphrase
	 */
	public String getOnetimePassphrase() {
		return onetimePassphrase;
	}

	/**
	 * @param onetimePassphrase the onetimePassphrase to set
	 */
	public void setOnetimePassphrase(String onetimePassphrase) {
		this.onetimePassphrase = onetimePassphrase;
	}

	/**
	 * @return the xmlsigCertSerial
	 */
	public String getXmlsigCertSerial() {
		return xmlsigCertSerial;
	}

	/**
	 * @param xmlsigCertSerial the xmlsigCertSerial to set
	 */
	public void setXmlsigCertSerial(String xmlsigCertSerial) {
		this.xmlsigCertSerial = xmlsigCertSerial;
	}

	/**
	 * @return the xmlsigCertVersion
	 */
	public String getXmlsigCertVersion() {
		return xmlsigCertVersion;
	}

	/**
	 * @param xmlsigCertVersion the xmlsigCertVersion to set
	 */
	public void setXmlsigCertVersion(String xmlsigCertVersion) {
		this.xmlsigCertVersion = xmlsigCertVersion;
	}

	/**
	 * @return the xmlsigValidFrom
	 */
	public String getXmlsigValidFrom() {
		return xmlsigValidFrom;
	}

	/**
	 * @param xmlsigValidFrom the xmlsigValidFrom to set
	 */
	public void setXmlsigValidFrom(String xmlsigValidFrom) {
		this.xmlsigValidFrom = xmlsigValidFrom;
	}

	/**
	 * @return the xmlsigValidUntil
	 */
	public String getXmlsigValidUntil() {
		return xmlsigValidUntil;
	}

	/**
	 * @param xmlsigValidUntil the xmlsigValidUntil to set
	 */
	public void setXmlsigValidUntil(String xmlsigValidUntil) {
		this.xmlsigValidUntil = xmlsigValidUntil;
	}

	/**
	 * @return the xmlsigCertIssuer
	 */
	public String getXmlsigCertIssuer() {
		return xmlsigCertIssuer;
	}

	/**
	 * @param xmlsigCertIssuer the xmlsigCertIssuer to set
	 */
	public void setXmlsigCertIssuer(String xmlsigCertIssuer) {
		this.xmlsigCertIssuer = xmlsigCertIssuer;
	}

	/**
	 * @return the xmlsigAssociatedData
	 */
	public String getXmlsigAssociatedData() {
		return xmlsigAssociatedData;
	}

	/**
	 * @param xmlsigAssociatedData the xmlsigAssociatedData to set
	 */
	public void setXmlsigAssociatedData(String xmlsigAssociatedData) {
		this.xmlsigAssociatedData = xmlsigAssociatedData;
	}

	/**
	 * @return the scSmartCardID
	 */
	public String getScSmartCardID() {
		return scSmartCardID;
	}

	/**
	 * @param scSmartCardID the scSmartCardID to set
	 */
	public void setScSmartCardID(String scSmartCardID) {
		this.scSmartCardID = scSmartCardID;
	}

	/**
	 * @return the scSmartCardValidUntil
	 */
	public String getScSmartCardValidUntil() {
		return scSmartCardValidUntil;
	}

	/**
	 * @param scSmartCardValidUntil the scSmartCardValidUntil to set
	 */
	public void setScSmartCardValidUntil(String scSmartCardValidUntil) {
		this.scSmartCardValidUntil = scSmartCardValidUntil;
	}

	/**
	 * @return the scSmartCardIssuer
	 */
	public String getScSmartCardIssuer() {
		return scSmartCardIssuer;
	}

	/**
	 * @param scSmartCardIssuer the scSmartCardIssuer to set
	 */
	public void setScSmartCardIssuer(String scSmartCardIssuer) {
		this.scSmartCardIssuer = scSmartCardIssuer;
	}

	/**
	 * @return the scCredential
	 */
	public String getScCredential() {
		return scCredential;
	}

	/**
	 * @param scCredential the scCredential to set
	 */
	public void setScCredential(String scCredential) {
		this.scCredential = scCredential;
	}

	/**
	 * @return the fingerBiometricSerial
	 */
	public String getFingerBiometricSerial() {
		return fingerBiometricSerial;
	}

	/**
	 * @param fingerBiometricSerial the fingerBiometricSerial to set
	 */
	public void setFingerBiometricSerial(String fingerBiometricSerial) {
		this.fingerBiometricSerial = fingerBiometricSerial;
	}

	/**
	 * @return the fingerImageData
	 */
	public String getFingerImageData() {
		return fingerImageData;
	}

	/**
	 * @param fingerImageData the fingerImageData to set
	 */
	public void setFingerImageData(String fingerImageData) {
		this.fingerImageData = fingerImageData;
	}

	/**
	 * @return the voiceBiometricSerial
	 */
	public String getVoiceBiometricSerial() {
		return voiceBiometricSerial;
	}

	/**
	 * @param voiceBiometricSerial the voiceBiometricSerial to set
	 */
	public void setVoiceBiometricSerial(String voiceBiometricSerial) {
		this.voiceBiometricSerial = voiceBiometricSerial;
	}

	/**
	 * @return the voiceSoundData
	 */
	public String getVoiceSoundData() {
		return voiceSoundData;
	}

	/**
	 * @param voiceSoundData the voiceSoundData to set
	 */
	public void setVoiceSoundData(String voiceSoundData) {
		this.voiceSoundData = voiceSoundData;
	}

	/**
	 * @return the cookieName
	 */
	public String getCookieName() {
		return cookieName;
	}

	/**
	 * @param cookieName the cookieName to set
	 */
	public void setCookieName(String cookieName) {
		this.cookieName = cookieName;
	}

	/**
	 * @return the cookieValue
	 */
	public String getCookieValue() {
		return cookieValue;
	}

	/**
	 * @param cookieValue the cookieValue to set
	 */
	public void setCookieValue(String cookieValue) {
		this.cookieValue = cookieValue;
	}

	/**
	 * @return the cookieExpirationDate
	 */
	public String getCookieExpDate() {
		return cookieExpDate;
	}

	/**
	 * @param cookieExpDate the cookieExpirationDate to set
	 */
	public void setCookieExpDate(String cookieExpDate) {
		this.cookieExpDate = cookieExpDate;
	}

	/**
	 * @return the cookiePath
	 */
	public String getCookiePath() {
		return cookiePath;
	}

	/**
	 * @param cookiePath the cookiePath to set
	 */
	public void setCookiePath(String cookiePath) {
		this.cookiePath = cookiePath;
	}

	/**
	 * @return the cookieSecConn
	 */
	public String getCookieSecConn() {
		return cookieSecConn;
	}

	/**
	 * @param cookieSecConn the cookieSecConn to set
	 */
	public void setCookieSecConn(String cookieSecConn) {
		this.cookieSecConn = cookieSecConn;
	}

	/**
	 * @return the cookieDom
	 */
	public String getCookieDom() {
		return cookieDom;
	}

	/**
	 * @param cookieDom the cookieDom to set
	 */
	public void setCookieDom(String cookieDom) {
		this.cookieDom = cookieDom;
	}

}
