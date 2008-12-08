package org.qualipso.damlservice.client;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.impl.OMNamespaceImpl;
import org.apache.axiom.soap.SOAPFactory;
import org.opensaml.saml2.core.Subject;

/**
 * Subclass for implementing Security Notation case
 * @author Panos Kavalagios
 *
 */
public class DAMLSecNotationRequest extends SAMLSOAPClient {
	public boolean hasAuthentication;
	public boolean hasAuthorization;
	public boolean hasAccessControl;
	public boolean hasDataIntegrity;
	public boolean hasConfidentiality;
	public boolean hasPrivacy;
	public boolean hasExposureControl;
	public boolean hasAnonymity;
	public boolean hasNegotiation;
	public boolean hasPolicy;
	public boolean hasPolicyLanguage;
	public boolean hasKeyDistribution;
	
	/**
	 * Default constructor 
	 */
	public DAMLSecNotationRequest() {
		super();
	}

	/**
	 * @param webServiceURL
	 */
	public DAMLSecNotationRequest(String webServiceURL) {
		super(webServiceURL);
	}

	/**
	 * Overriding of parent method for Security Notation case
	 * @see org.qualipso.damlservice.client.SAMLSOAPClient#buildDAMLInfoRequest(org.apache.axiom.soap.SOAPFactory, org.apache.axiom.om.OMNamespace)
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
		if (hasAuthentication) {
			parentElement=sFactory.createOMElement("Authentication", sNameSpace);
			infoRequest.addChild(parentElement);
		}
		if (hasAuthorization) {
			parentElement=sFactory.createOMElement("Authorization", sNameSpace);
			infoRequest.addChild(parentElement);
		}
		if (hasAccessControl) {
			parentElement=sFactory.createOMElement("AccessControl", sNameSpace);
			infoRequest.addChild(parentElement);
		}
		if (hasDataIntegrity) {
			parentElement=sFactory.createOMElement("DataIntegrity", sNameSpace);
			infoRequest.addChild(parentElement);
		}
		if (hasConfidentiality) {
			parentElement=sFactory.createOMElement("Confidentiality", sNameSpace);
			infoRequest.addChild(parentElement);
		}
		if (hasPrivacy) {
			parentElement=sFactory.createOMElement("Privacy", sNameSpace);
			infoRequest.addChild(parentElement);
		}
		if (hasExposureControl) {
			parentElement=sFactory.createOMElement("ExposureControl", sNameSpace);
			infoRequest.addChild(parentElement);
		}
		if (hasAnonymity) {
			parentElement=sFactory.createOMElement("Anonymity", sNameSpace);
			infoRequest.addChild(parentElement);
		}
		if (hasNegotiation) {
			parentElement=sFactory.createOMElement("Negotiation", sNameSpace);
			infoRequest.addChild(parentElement);
		}
		if (hasPolicy) {
			parentElement=sFactory.createOMElement("Policy", sNameSpace);
			infoRequest.addChild(parentElement);
		}
		if (hasPolicyLanguage) {
			parentElement=sFactory.createOMElement("PolicyLanguage", sNameSpace);
			infoRequest.addChild(parentElement);
		}
		if (hasKeyDistribution) {
			parentElement=sFactory.createOMElement("KeyDistribution", sNameSpace);
			infoRequest.addChild(parentElement);
		}
		
		return infoRequest;
	}

}
