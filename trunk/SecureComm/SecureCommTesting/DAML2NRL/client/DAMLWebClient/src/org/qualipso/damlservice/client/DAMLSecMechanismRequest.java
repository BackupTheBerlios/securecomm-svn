package org.qualipso.damlservice.client;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.impl.OMNamespaceImpl;
import org.apache.axiom.soap.SOAPFactory;
import org.opensaml.saml2.core.Subject;

public class DAMLSecMechanismRequest extends SAMLSOAPClient {
	public boolean hasEncryption;
	private String encryptionSyntax;
	private String encryptionEnc;
	private String encryptionDocumentation;
	private String encryptionRelSecNotation;
	private String encryptionSig;
	private String encryptionReqCredential;
	public boolean hasDataTransferProtocol;
	private String dataSyntax;
	private String dataEnc;
	private String dataDocumentation;
	private String dataRelSecNotation;
	private String dataSig;
	private String dataReqCredential;
	public boolean hasKeyInformationProt;
	private String keyinfoSyntax;
	private String keyinfoEnc;
	private String keyinfoDocumentation;
	private String keyinfoRelSecNotation;
	private String keyinfoSig;
	private String keyinfoReqCredential;
	public boolean hasKeyRegistrationProt;
	private String keyregSyntax;
	private String keyregEnc;
	private String keyregDocumentation;
	private String keyregRelSecNotation;
	private String keyregSig;
	private String keyregReqCredential;
	public boolean hasSignature;
	private String signatureSyntax;
	private String signatureEnc;
	private String signatureDocumentation;
	private String signatureRelSecNotation;
	private String signatureSig;
	private String signatureReqCredential;
	
	/** 
	 * Default Constructor
	 */
	public DAMLSecMechanismRequest() {
		super();
	}

	/**
	 * @param webServiceURL
	 */
	public DAMLSecMechanismRequest(String webServiceURL) {
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
		if (hasEncryption) {
			parentElement=sFactory.createOMElement("Encryption", sNameSpace);
			childElement=sFactory.createOMElement("syntax",sNameSpace);
			childElement.setText(encryptionSyntax);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("enc",sNameSpace);
			childElement.setText(encryptionEnc);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("documentation",sNameSpace);
			childElement.setText(encryptionDocumentation);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("relSecNotation",sNameSpace);
			childElement.setText(encryptionRelSecNotation);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("sig",sNameSpace);
			childElement.setText(encryptionSig);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("reqCredential",sNameSpace);
			childElement.setText(encryptionReqCredential);
			parentElement.addChild(childElement);
			infoRequest.addChild(parentElement);
		}
		if (hasDataTransferProtocol) {
			parentElement=sFactory.createOMElement("DataTransferProtocol", sNameSpace);
			childElement=sFactory.createOMElement("syntax",sNameSpace);
			childElement.setText(dataSyntax);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("enc",sNameSpace);
			childElement.setText(dataEnc);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("documentation",sNameSpace);
			childElement.setText(dataDocumentation);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("relSecNotation",sNameSpace);
			childElement.setText(dataRelSecNotation);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("sig",sNameSpace);
			childElement.setText(dataSig);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("reqCredential",sNameSpace);
			childElement.setText(dataReqCredential);
			parentElement.addChild(childElement);
			infoRequest.addChild(parentElement);
		}
		if (hasKeyInformationProt) {
			parentElement=sFactory.createOMElement("KeyInformationProt", sNameSpace);
			childElement=sFactory.createOMElement("syntax",sNameSpace);
			childElement.setText(keyinfoSyntax);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("enc",sNameSpace);
			childElement.setText(keyinfoEnc);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("documentation",sNameSpace);
			childElement.setText(keyinfoDocumentation);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("relSecNotation",sNameSpace);
			childElement.setText(keyinfoRelSecNotation);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("sig",sNameSpace);
			childElement.setText(keyinfoSig);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("reqCredential",sNameSpace);
			childElement.setText(keyinfoReqCredential);
			parentElement.addChild(childElement);
			infoRequest.addChild(parentElement);
		}
		if (hasKeyRegistrationProt) {
			parentElement=sFactory.createOMElement("KeyRegistrationProt", sNameSpace);
			childElement=sFactory.createOMElement("syntax",sNameSpace);
			childElement.setText(keyregSyntax);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("enc",sNameSpace);
			childElement.setText(keyregEnc);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("documentation",sNameSpace);
			childElement.setText(keyregDocumentation);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("relSecNotation",sNameSpace);
			childElement.setText(keyregRelSecNotation);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("sig",sNameSpace);
			childElement.setText(keyregSig);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("reqCredential",sNameSpace);
			childElement.setText(keyregReqCredential);
			parentElement.addChild(childElement);
			infoRequest.addChild(parentElement);
		}
		if (hasSignature) {
			parentElement=sFactory.createOMElement("Signature", sNameSpace);
			childElement=sFactory.createOMElement("syntax",sNameSpace);
			childElement.setText(signatureSyntax);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("enc",sNameSpace);
			childElement.setText(signatureEnc);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("documentation",sNameSpace);
			childElement.setText(signatureDocumentation);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("relSecNotation",sNameSpace);
			childElement.setText(signatureRelSecNotation);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("sig",sNameSpace);
			childElement.setText(signatureSig);
			parentElement.addChild(childElement);
			childElement=sFactory.createOMElement("reqCredential",sNameSpace);
			childElement.setText(signatureReqCredential);
			parentElement.addChild(childElement);
			infoRequest.addChild(parentElement);
		}
		
		return infoRequest;
	}

	/**
	 * @return the encryptionSyntax
	 */
	public String getEncryptionSyntax() {
		return encryptionSyntax;
	}

	/**
	 * @param encryptionSyntax the encryptionSyntax to set
	 */
	public void setEncryptionSyntax(String encryptionSyntax) {
		this.encryptionSyntax = encryptionSyntax;
	}

	/**
	 * @return the encryptionEnc
	 */
	public String getEncryptionEnc() {
		return encryptionEnc;
	}

	/**
	 * @param encryptionEnc the encryptionEnc to set
	 */
	public void setEncryptionEnc(String encryptionEnc) {
		this.encryptionEnc = encryptionEnc;
	}

	/**
	 * @return the encryptionDocumentation
	 */
	public String getEncryptionDocumentation() {
		return encryptionDocumentation;
	}

	/**
	 * @param encryptionDocumentation the encryptionDocumentation to set
	 */
	public void setEncryptionDocumentation(String encryptionDocumentation) {
		this.encryptionDocumentation = encryptionDocumentation;
	}

	/**
	 * @return the encryptionRelSecNotation
	 */
	public String getEncryptionRelSecNotation() {
		return encryptionRelSecNotation;
	}

	/**
	 * @param encryptionRelSecNotation the encryptionRelSecNotation to set
	 */
	public void setEncryptionRelSecNotation(String encryptionRelSecNotation) {
		this.encryptionRelSecNotation = encryptionRelSecNotation;
	}

	/**
	 * @return the encryptionSig
	 */
	public String getEncryptionSig() {
		return encryptionSig;
	}

	/**
	 * @param encryptionSig the encryptionSig to set
	 */
	public void setEncryptionSig(String encryptionSig) {
		this.encryptionSig = encryptionSig;
	}

	/**
	 * @return the encryptionReqCredential
	 */
	public String getEncryptionReqCredential() {
		return encryptionReqCredential;
	}

	/**
	 * @param encryptionReqCredential the encryptionReqCredential to set
	 */
	public void setEncryptionReqCredential(String encryptionReqCredential) {
		this.encryptionReqCredential = encryptionReqCredential;
	}

	/**
	 * @return the dataSyntax
	 */
	public String getDataSyntax() {
		return dataSyntax;
	}

	/**
	 * @param dataSyntax the dataSyntax to set
	 */
	public void setDataSyntax(String dataSyntax) {
		this.dataSyntax = dataSyntax;
	}

	/**
	 * @return the dataEnc
	 */
	public String getDataEnc() {
		return dataEnc;
	}

	/**
	 * @param dataEnc the dataEnc to set
	 */
	public void setDataEnc(String dataEnc) {
		this.dataEnc = dataEnc;
	}

	/**
	 * @return the dataDocumentation
	 */
	public String getDataDocumentation() {
		return dataDocumentation;
	}

	/**
	 * @param dataDocumentation the dataDocumentation to set
	 */
	public void setDataDocumentation(String dataDocumentation) {
		this.dataDocumentation = dataDocumentation;
	}

	/**
	 * @return the dataRelSecNotation
	 */
	public String getDataRelSecNotation() {
		return dataRelSecNotation;
	}

	/**
	 * @param dataRelSecNotation the dataRelSecNotation to set
	 */
	public void setDataRelSecNotation(String dataRelSecNotation) {
		this.dataRelSecNotation = dataRelSecNotation;
	}

	/**
	 * @return the dataSig
	 */
	public String getDataSig() {
		return dataSig;
	}

	/**
	 * @param dataSig the dataSig to set
	 */
	public void setDataSig(String dataSig) {
		this.dataSig = dataSig;
	}

	/**
	 * @return the dataReqCredential
	 */
	public String getDataReqCredential() {
		return dataReqCredential;
	}

	/**
	 * @param dataReqCredential the dataReqCredential to set
	 */
	public void setDataReqCredential(String dataReqCredential) {
		this.dataReqCredential = dataReqCredential;
	}

	/**
	 * @return the keyinfoSyntax
	 */
	public String getKeyinfoSyntax() {
		return keyinfoSyntax;
	}

	/**
	 * @param keyinfoSyntax the keyinfoSyntax to set
	 */
	public void setKeyinfoSyntax(String keyinfoSyntax) {
		this.keyinfoSyntax = keyinfoSyntax;
	}

	/**
	 * @return the keyinfoEnc
	 */
	public String getKeyinfoEnc() {
		return keyinfoEnc;
	}

	/**
	 * @param keyinfoEnc the keyinfoEnc to set
	 */
	public void setKeyinfoEnc(String keyinfoEnc) {
		this.keyinfoEnc = keyinfoEnc;
	}

	/**
	 * @return the keyinfoDocumentation
	 */
	public String getKeyinfoDocumentation() {
		return keyinfoDocumentation;
	}

	/**
	 * @param keyinfoDocumentation the keyinfoDocumentation to set
	 */
	public void setKeyinfoDocumentation(String keyinfoDocumentation) {
		this.keyinfoDocumentation = keyinfoDocumentation;
	}

	/**
	 * @return the keyinfoRelSecNotation
	 */
	public String getKeyinfoRelSecNotation() {
		return keyinfoRelSecNotation;
	}

	/**
	 * @param keyinfoRelSecNotation the keyinfoRelSecNotation to set
	 */
	public void setKeyinfoRelSecNotation(String keyinfoRelSecNotation) {
		this.keyinfoRelSecNotation = keyinfoRelSecNotation;
	}

	/**
	 * @return the keyinfoSig
	 */
	public String getKeyinfoSig() {
		return keyinfoSig;
	}

	/**
	 * @param keyinfoSig the keyinfoSig to set
	 */
	public void setKeyinfoSig(String keyinfoSig) {
		this.keyinfoSig = keyinfoSig;
	}

	/**
	 * @return the keyinfoReqCredential
	 */
	public String getKeyinfoReqCredential() {
		return keyinfoReqCredential;
	}

	/**
	 * @param keyinfoReqCredential the keyinfoReqCredential to set
	 */
	public void setKeyinfoReqCredential(String keyinfoReqCredential) {
		this.keyinfoReqCredential = keyinfoReqCredential;
	}

	/**
	 * @return the keyregSyntax
	 */
	public String getKeyregSyntax() {
		return keyregSyntax;
	}

	/**
	 * @param keyregSyntax the keyregSyntax to set
	 */
	public void setKeyregSyntax(String keyregSyntax) {
		this.keyregSyntax = keyregSyntax;
	}

	/**
	 * @return the keyregEnc
	 */
	public String getKeyregEnc() {
		return keyregEnc;
	}

	/**
	 * @param keyregEnc the keyregEnc to set
	 */
	public void setKeyregEnc(String keyregEnc) {
		this.keyregEnc = keyregEnc;
	}

	/**
	 * @return the keyregDocumentation
	 */
	public String getKeyregDocumentation() {
		return keyregDocumentation;
	}

	/**
	 * @param keyregDocumentation the keyregDocumentation to set
	 */
	public void setKeyregDocumentation(String keyregDocumentation) {
		this.keyregDocumentation = keyregDocumentation;
	}

	/**
	 * @return the keyregRelSecNotation
	 */
	public String getKeyregRelSecNotation() {
		return keyregRelSecNotation;
	}

	/**
	 * @param keyregRelSecNotation the keyregRelSecNotation to set
	 */
	public void setKeyregRelSecNotation(String keyregRelSecNotation) {
		this.keyregRelSecNotation = keyregRelSecNotation;
	}

	/**
	 * @return the keyregSig
	 */
	public String getKeyregSig() {
		return keyregSig;
	}

	/**
	 * @param keyregSig the keyregSig to set
	 */
	public void setKeyregSig(String keyregSig) {
		this.keyregSig = keyregSig;
	}

	/**
	 * @return the keyregReqCredential
	 */
	public String getKeyregReqCredential() {
		return keyregReqCredential;
	}

	/**
	 * @param keyregReqCredential the keyregReqCredential to set
	 */
	public void setKeyregReqCredential(String keyregReqCredential) {
		this.keyregReqCredential = keyregReqCredential;
	}

	/**
	 * @return the signatureSyntax
	 */
	public String getSignatureSyntax() {
		return signatureSyntax;
	}

	/**
	 * @param signatureSyntax the signatureSyntax to set
	 */
	public void setSignatureSyntax(String signatureSyntax) {
		this.signatureSyntax = signatureSyntax;
	}

	/**
	 * @return the signatureEnc
	 */
	public String getSignatureEnc() {
		return signatureEnc;
	}

	/**
	 * @param signatureEnc the signatureEnc to set
	 */
	public void setSignatureEnc(String signatureEnc) {
		this.signatureEnc = signatureEnc;
	}

	/**
	 * @return the signatureDocumentation
	 */
	public String getSignatureDocumentation() {
		return signatureDocumentation;
	}

	/**
	 * @param signatureDocumentation the signatureDocumentation to set
	 */
	public void setSignatureDocumentation(String signatureDocumentation) {
		this.signatureDocumentation = signatureDocumentation;
	}

	/**
	 * @return the signatureRelSecNotation
	 */
	public String getSignatureRelSecNotation() {
		return signatureRelSecNotation;
	}

	/**
	 * @param signatureRelSecNotation the signatureRelSecNotation to set
	 */
	public void setSignatureRelSecNotation(String signatureRelSecNotation) {
		this.signatureRelSecNotation = signatureRelSecNotation;
	}

	/**
	 * @return the signatureSig
	 */
	public String getSignatureSig() {
		return signatureSig;
	}

	/**
	 * @param signatureSig the signatureSig to set
	 */
	public void setSignatureSig(String signatureSig) {
		this.signatureSig = signatureSig;
	}

	/**
	 * @return the signatureReqCredential
	 */
	public String getSignatureReqCredential() {
		return signatureReqCredential;
	}

	/**
	 * @param signatureReqCredential the signatureReqCredential to set
	 */
	public void setSignatureReqCredential(String signatureReqCredential) {
		this.signatureReqCredential = signatureReqCredential;
	}
}
