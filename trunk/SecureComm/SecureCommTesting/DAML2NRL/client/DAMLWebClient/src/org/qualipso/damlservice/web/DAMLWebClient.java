package org.qualipso.damlservice.web;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.axiom.soap.SOAPEnvelope;
import org.qualipso.damlservice.client.DAMLCredentialRequest;
import org.qualipso.damlservice.client.DAMLSecMechanismRequest;
import org.qualipso.damlservice.client.DAMLSecNotationRequest;
import org.qualipso.damlservice.client.SAMLSOAPClient;
import org.qualipso.damlservice.common.Constants;

/**
 * Servlet implementation class DAMLWebClient
 */
/**
 * @author Panos Kavalagios
 *
 */
public class DAMLWebClient extends HttpServlet implements Constants {
	private static final long serialVersionUID = 1L;
	private String soapServerAddress;
	private String soapServiceURI;
	private String soapServiceURL;
	private String servletRealPath;

    /**
     * Default constructor. 
     */
    public DAMLWebClient() {
    	super();
    }


	/** Method to initialise servlet initialisation parameters
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() throws ServletException {
		super.init();
		
		// Get initialisation parameters
		soapServerAddress=getServletConfig().getInitParameter(SOAP_SERVER_ADDRESS);
		soapServiceURI=getServletConfig().getInitParameter(SOAP_SERVICE_URI);
		if (soapServerAddress==null) {
			throw new ServletException("SOAP Server Address is not defined!");
		}
		else if (soapServiceURI==null) {
			throw new ServletException("SOAP Service URI is not defined!");
		}
		else {
			soapServiceURL=soapServerAddress+soapServiceURI;
		}
		servletRealPath=getServletContext().getRealPath("/");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {				
		say("Received Request...");
		try {
			HashMap<String, String[]> parMap=new HashMap<String, String[]>();
			parMap.putAll(request.getParameterMap());
			SAMLSOAPClient serviceNRL;
			if (request.getParameter(CRED_SUBMIT)!=null) {
				say("Starting Credential request...");
				serviceNRL=buildCredentialRequest(soapServiceURL, parMap);
			}
			else if (request.getParameter(SECMECH_SUBMIT)!=null) {
				say("Starting Security Mechanism request...");
				serviceNRL=buildSecMechanismRequest(soapServiceURL, parMap);				
			}
			else if (request.getParameter(SECNOT_SUBMIT)!=null) {
				say("Starting Security Notation request...");
				serviceNRL=buildSecNotationRequest(soapServiceURL, parMap);								
			}
			else {
				throw new ServletException("Unsupported request");
			}
			SOAPEnvelope answer=serviceNRL.send("getCookieInfo");
			// Create SOAP answer XML files
			produceXMLFile(serviceNRL.getSoapRequest(), servletRealPath+SOAP_REQUEST_FILE);
			produceXMLFile(serviceNRL.getSoapResponse(),servletRealPath+SOAP_RESPONSE_FILE);
			// Set result message
			request.getSession().setAttribute(RESULTS, "Credentials request sent: <a href=\""+SOAP_REQUEST_FILE+"\">SOAP Request</a> ==&gt; <a href=\""+SOAP_RESPONSE_FILE+"\">SOAP Response</a>");
			response.sendRedirect(SUCCESS_PAGE);
		}
 		catch (Exception e){
 			e.printStackTrace();
 			request.getSession().setAttribute(RESULTS, "An error occurred: Reason: "+e.getMessage());
 			response.sendRedirect(ERROR_PAGE);
 		}
	}

	/**
	 * Create XML file
	 * @param xmlContent
	 * @param xmlFilePath
	 * @throws IOException 
	 */
	private void produceXMLFile(String xmlContent, String xmlFilePath) throws IOException {
		File xmlFile=new File(xmlFilePath);
		FileWriter fw;
		fw = new FileWriter(xmlFile);
		fw.write(xmlContent);
		fw.close();		
	}

	/**
	 * Build Credential request
	 * @param servURL
	 * @param parData
	 * @return
	 */
	private SAMLSOAPClient buildCredentialRequest(String servURL, HashMap<String,String[]> parData) {
		
		DAMLCredentialRequest dsClient=new DAMLCredentialRequest(servURL);
		
		dsClient.setSubjectName(parData.get("subjectname")[0]);
		if (parData.containsKey("badge")) {
			dsClient.hasBadge=true;
			dsClient.setBadgeCardID(parData.get("badge_cardid")[0]);
			dsClient.setBadgeDocAuthority(parData.get("badge_docauthority")[0]);
			dsClient.setBadgeExpirationDate(parData.get("badge_expirationdate")[0]);
		}
		if (parData.containsKey("creditcard")) {
			dsClient.hasCreditCard=true;
			dsClient.setCreditCardID(parData.get("credit_cardid")[0]);
			dsClient.setCreditDocAuthority(parData.get("credit_docauthority")[0]);
			dsClient.setCreditExpirationDate(parData.get("credit_expirationdate")[0]);
		}
		if (parData.containsKey("debitcard")) {
			dsClient.hasDebitCard=true;
			dsClient.setDebitCardID(parData.get("debit_cardid")[0]);
			dsClient.setDebitDocAuthority(parData.get("debit_docauthority")[0]);
			dsClient.setDebitExpirationDate(parData.get("debit_expirationdate")[0]);
		}
		if (parData.containsKey("driverslicense")) {
			dsClient.hasDriversLicense=true;
			dsClient.setDriversCardID(parData.get("drivers_cardid")[0]);
			dsClient.setDriversDocAuthority(parData.get("drivers_docauthority")[0]);
			dsClient.setDriversExpirationDate(parData.get("drivers_expirationdate")[0]);
		}
		if (parData.containsKey("passport")) {
			dsClient.hasPassport=true;
			dsClient.setPassportCardID(parData.get("passport_cardid")[0]);
			dsClient.setPassportDocAuthority(parData.get("passport_docauthority")[0]);
			dsClient.setPassportExpirationDate(parData.get("passport_expirationdate")[0]);
		}
		if (parData.containsKey("loginwithpassphrase")) {
			dsClient.hasLoginWithPassphrase=true;
			dsClient.setLoginLoginName(parData.get("login_loginname")[0]);
			dsClient.setLoginPassphrase(parData.get("login_passphrase")[0]);
		}
		if (parData.containsKey("onetimepassword")) {
			dsClient.hasOneTimePassword=true;
			dsClient.setOnetimeSeed(parData.get("onetime_seed")[0]);
			dsClient.setOnetimeHashFct(parData.get("onetime_hashfct")[0]);
			dsClient.setOnetimePassphrase(parData.get("onetime_passphrase")[0]);
		}
		if (parData.containsKey("xmlsignaturex509v3certificate")) {
			dsClient.hasXMLSignatureX509v3Certificate=true;
			dsClient.setXmlsigCertSerial(parData.get("xmlsig_certserial")[0]);
			dsClient.setXmlsigCertVersion(parData.get("xmlsig_certversion")[0]);
			dsClient.setXmlsigValidFrom(parData.get("xmlsig_validfrom")[0]);
			dsClient.setXmlsigValidUntil(parData.get("xmlsig_validuntil")[0]);
			dsClient.setXmlsigCertIssuer(parData.get("xmlsig_certissuer")[0]);
			dsClient.setXmlsigAssociatedData(parData.get("xmlsig_associateddata")[0]);
		}
		if (parData.containsKey("smartcard")) {
			dsClient.hasSmartCard=true;
			dsClient.setScSmartCardID(parData.get("sc_smartcardid")[0]);
			dsClient.setScSmartCardValidUntil(parData.get("sc_smartcardvaliduntil")[0]);
			dsClient.setScSmartCardIssuer(parData.get("sc_smartcardissuer")[0]);
			dsClient.setScCredential(parData.get("sc_credential")[0]);
		}
		if (parData.containsKey("fingerprint")) {
			dsClient.hasFingerPrint=true;
			dsClient.setFingerBiometricSerial(parData.get("finger_biometricserial")[0]);
			dsClient.setFingerImageData(parData.get("finger_imagedata")[0]);
		}
		if (parData.containsKey("voice")) {
			dsClient.hasVoice=true;
			dsClient.setVoiceBiometricSerial(parData.get("voice_biometricserial")[0]);
			dsClient.setVoiceSoundData(parData.get("voice_sounddata")[0]);
		}
		if (parData.containsKey("cookie")) {
			dsClient.hasCookie=true;
			dsClient.setCookieName(parData.get("cookie_name")[0]);
			dsClient.setCookieValue(parData.get("cookie_value")[0]);
			dsClient.setCookieExpDate(parData.get("cookie_expdate")[0]);
			dsClient.setCookiePath(parData.get("cookie_path")[0]);
			dsClient.setCookieSecConn(parData.get("cookie_secconn")[0]);
			dsClient.setCookieDom(parData.get("cookie_dom")[0]);
		}

		return dsClient;
	}

	/**
	 * Build Security Mechanism Request
	 * @param servURL
	 * @param parData
	 * @return
	 */
	private SAMLSOAPClient buildSecMechanismRequest(String servURL, HashMap<String,String[]> parData) {
		
		DAMLSecMechanismRequest dsClient=new DAMLSecMechanismRequest(servURL);
		
		dsClient.setSubjectName(parData.get("subjectname")[0]);
		if (parData.containsKey("encryption")) {
			dsClient.hasEncryption=true;
			dsClient.setEncryptionSyntax(parData.get("encryption_syntax")[0]);
			dsClient.setEncryptionEnc(parData.get("encryption_enc")[0]);
			dsClient.setEncryptionDocumentation(parData.get("encryption_documentation")[0]);
			dsClient.setEncryptionRelSecNotation(parData.get("encryption_relsecnotation")[0]);
			dsClient.setEncryptionSig(parData.get("encryption_signature")[0]);
			dsClient.setEncryptionReqCredential(parData.get("encryption_reqcredential")[0]);
		}
		if (parData.containsKey("datatransferprotocol")) {
			dsClient.hasDataTransferProtocol=true;
			dsClient.setDataSyntax(parData.get("data_syntax")[0]);
			dsClient.setDataEnc(parData.get("data_enc")[0]);
			dsClient.setDataDocumentation(parData.get("data_documentation")[0]);
			dsClient.setDataRelSecNotation(parData.get("data_relsecnotation")[0]);
			dsClient.setDataSig(parData.get("data_signature")[0]);
			dsClient.setDataReqCredential(parData.get("data_reqcredential")[0]);
		}
		if (parData.containsKey("keyinformationprot")) {
			dsClient.hasKeyInformationProt=true;
			dsClient.setKeyinfoSyntax(parData.get("keyinfo_syntax")[0]);
			dsClient.setKeyinfoEnc(parData.get("keyinfo_enc")[0]);
			dsClient.setKeyinfoDocumentation(parData.get("keyinfo_documentation")[0]);
			dsClient.setKeyinfoRelSecNotation(parData.get("keyinfo_relsecnotation")[0]);
			dsClient.setKeyinfoSig(parData.get("keyinfo_signature")[0]);
			dsClient.setKeyinfoReqCredential(parData.get("keyinfo_reqcredential")[0]);
		}
		if (parData.containsKey("keyregistrationprot")) {
			dsClient.hasKeyRegistrationProt=true;
			dsClient.setKeyregSyntax(parData.get("keyreg_syntax")[0]);
			dsClient.setKeyregEnc(parData.get("keyreg_enc")[0]);
			dsClient.setKeyregDocumentation(parData.get("keyreg_documentation")[0]);
			dsClient.setKeyregRelSecNotation(parData.get("keyreg_relsecnotation")[0]);
			dsClient.setKeyregSig(parData.get("keyreg_signature")[0]);
			dsClient.setKeyregReqCredential(parData.get("keyreg_reqcredential")[0]);
		}
		if (parData.containsKey("signature")) {
			dsClient.hasSignature=true;
			dsClient.setSignatureSyntax(parData.get("signature_syntax")[0]);
			dsClient.setSignatureEnc(parData.get("signature_enc")[0]);
			dsClient.setSignatureDocumentation(parData.get("signature_documentation")[0]);
			dsClient.setSignatureRelSecNotation(parData.get("signature_relsecnotation")[0]);
			dsClient.setSignatureSig(parData.get("signature_signature")[0]);
			dsClient.setSignatureReqCredential(parData.get("signature_reqcredential")[0]);
		}

		return dsClient;
	}

	private SAMLSOAPClient buildSecNotationRequest(String servURL, HashMap<String,String[]> parData) {
		
		DAMLSecNotationRequest dsClient=new DAMLSecNotationRequest(servURL);
		
		dsClient.setSubjectName(parData.get("subjectname")[0]);
		if (parData.containsKey("authentication")) {
			dsClient.hasAuthentication=true;
		}
		if (parData.containsKey("authorization")) {
			dsClient.hasAuthorization=true;
		}
		if (parData.containsKey("accesscontrol")) {
			dsClient.hasAccessControl=true;
		}
		if (parData.containsKey("dataintegrity")) {
			dsClient.hasDataIntegrity=true;
		}
		if (parData.containsKey("confidentiality")) {
			dsClient.hasConfidentiality=true;
		}
		if (parData.containsKey("privacy")) {
			dsClient.hasPrivacy=true;
		}
		if (parData.containsKey("exposurecontrol")) {
			dsClient.hasExposureControl=true;
		}
		if (parData.containsKey("anonymity")) {
			dsClient.hasAnonymity=true;
		}
		if (parData.containsKey("negotiation")) {
			dsClient.hasNegotiation=true;
		}
		if (parData.containsKey("policy")) {
			dsClient.hasPolicy=true;
		}
		if (parData.containsKey("policylanguage")) {
			dsClient.hasPolicyLanguage=true;
		}
		if (parData.containsKey("keydistribution")) {
			dsClient.hasKeyDistribution=true;
		}

		return dsClient;
	}

	protected void say(String msg) {
		System.out.println("DAMLWebClient: "+msg);
	}

}
