package org.qualipso.damlservice.client;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.impl.OMNamespaceImpl;
import org.apache.axiom.soap.SOAP11Constants;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.OperationClient;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.util.XMLUtils;
import org.apache.axis2.wsdl.WSDLConstants;
import org.opensaml.Configuration;
import org.opensaml.DefaultBootstrap;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.SubjectConfirmation;
import org.opensaml.saml2.core.Subject;
import org.opensaml.saml2.core.SubjectConfirmationData;
import org.opensaml.saml2.core.impl.AssertionBuilder;
import org.opensaml.saml2.core.impl.NameIDBuilder;
import org.opensaml.saml2.core.impl.SubjectBuilder;
import org.opensaml.saml2.core.impl.SubjectConfirmationBuilder;
import org.opensaml.saml2.core.impl.SubjectConfirmationDataBuilder;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallerFactory;
import org.w3c.dom.Element;

public class SAMLSOAPClient {
	private String webServiceURL;
	private String subjectName;
	private String soapRequest;
	private String soapResponse;
	
	/** 
	 * Default Constructor
	 */
	public SAMLSOAPClient() {
		
	}

	/**
	 * @param webServiceURL
	 */
	public SAMLSOAPClient(String webServiceURL) {
		this.webServiceURL = webServiceURL;
	}

	/** 
	 * Method to send a request to SOAP server
	 */
 	public SOAPEnvelope send(String methodWS) throws Exception 
 	{ 		
 		// Make Client allowing service access
 		ServiceClient sender = new ServiceClient();

 		// Creation and definition of the object Options : 		
 		Options options = new Options();
 		
 		EndpointReference anEPR = new EndpointReference(webServiceURL);
 		options.setTo(anEPR);
 		
 		// make configuration of the client for connection
 		options.setAction("urn:" + methodWS);
 		options.setSoapVersionURI(SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI);
 		sender.setOptions(options);
 		
 		OperationClient anOperationService = sender.createClient(ServiceClient.ANON_OUT_IN_OP);
 		
 		// build the envelope of the soap message :
 		SOAPEnvelope anEnvelopeSoap = generateSOAP(methodWS);  		
 		System.out.println("SOAP envelope request: " + anEnvelopeSoap);
 		soapRequest=anEnvelopeSoap.toString();
 		
 		// call web service with SOAP message :
 		MessageContext outMsgContext = new MessageContext();
 		outMsgContext.setEnvelope(anEnvelopeSoap);
 		
 		// Send Soap Envelope to Web Service :
 		anOperationService.addMessageContext(outMsgContext);
 		anOperationService.execute(true); 		
 		MessageContext aResponseMsgContext =  anOperationService.getMessageContext(WSDLConstants.MESSAGE_LABEL_IN_VALUE);
 				
 		SOAPEnvelope result = aResponseMsgContext.getEnvelope();
		System.out.println("SOAP envelope result: " + result);
 		soapResponse=result.toString();
 		
 		// information from Web Service
 		return result;
 	}
 	
 	/**
 	 * This method built the soap message with OMElement Objects !
 	 * @param request is the HttpServletRequest from the client
 	 * @param aMethod is the function name used in web service
 	 * @return is a SoapEnvelope object (axiom library)
 	 * @throws Exception 
 	 */
 	private SOAPEnvelope generateSOAP(String aMethod) throws Exception 	{
 		
 		// Get SOAP factory and create envelope and name space
 		SOAPFactory omFactory = OMAbstractFactory.getSOAP11Factory();
 		SOAPEnvelope envelopeSOAP = OMAbstractFactory.getSOAP11Factory().getDefaultEnvelope();
 		OMNamespace defaultNS=omFactory.createOMNamespace(SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI, "env");
 		envelopeSOAP.setNamespace(defaultNS);
 		OMNamespace damlNS = omFactory.createOMNamespace("http://www.qualipso.org/daml2nrl", "daml");
 				
		// Initialise SAML library
		DefaultBootstrap.bootstrap();
		
		// Get the builder factory
		XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();

		// Get the Assertion builder and create the object
		AssertionBuilder assertBuilder=(AssertionBuilder) builderFactory.getBuilder(Assertion.DEFAULT_ELEMENT_NAME);
		Assertion damlAssertion=assertBuilder.buildObject();

		// Get the Subject builder and create the object
		SubjectBuilder subjBuilder = (SubjectBuilder) builderFactory.getBuilder(Subject.DEFAULT_ELEMENT_NAME);
		Subject subject = subjBuilder.buildObject();

		// Get the NameID builder and create the object
		NameIDBuilder nameBuilder=(NameIDBuilder) builderFactory.getBuilder(NameID.DEFAULT_ELEMENT_NAME);
		NameID nameID=nameBuilder.buildObject();
		nameID.setValue(subjectName);

		// Get the SubjectConfirmation builder and create the object
		SubjectConfirmationBuilder scBuilder=(SubjectConfirmationBuilder) builderFactory.getBuilder(SubjectConfirmation.DEFAULT_ELEMENT_NAME);
		SubjectConfirmation subjConf=scBuilder.buildObject();

		// Get the SubjectConfirmationData builder and create the object		
		SubjectConfirmationDataBuilder scdBuilder=(SubjectConfirmationDataBuilder) builderFactory.getBuilder(SubjectConfirmationData.DEFAULT_ELEMENT_NAME);
		SubjectConfirmationData subjConfData=scdBuilder.buildObject();
		// Generate SubjectConfirmationData
 		OMElement damlInfoRequest=buildDAMLInfoRequest(omFactory,damlNS);
		subjConfData.setDOM(XMLUtils.toDOM(damlInfoRequest));
		
		// Initialise Subject
		subject.setNameID(nameID);
		subjConf.setSubjectConfirmationData(subjConfData);
		subject.getSubjectConfirmations().add(subjConf);

		// Initialise Assertion
		damlAssertion.setSubject(subject);
		damlAssertion.setID("DAML2NRL");
		
		// Get the marshaller factory
		MarshallerFactory marshallerFactory = Configuration.getMarshallerFactory();

		// Get the Assertion marshaller
		Marshaller marshaller = marshallerFactory.getMarshaller(damlAssertion);

		// Marshall the Assertion
		Element assertElement = marshaller.marshall(damlAssertion);
		
		// Convert to OMElement
		OMElement samlElement=XMLUtils.toOM(assertElement);
		
		// Initialise body
		envelopeSOAP.getBody().setNamespace(defaultNS);
 		envelopeSOAP.getBody().addChild(samlElement);
 		
 		return envelopeSOAP;
 	}

 	/**
 	 * Build DAML information request. Override this method to include in SubjectConfirmationData your 
 	 * custom information. The default implementation creates an empty request.
 	 * @param sElement 
 	 * @param sNameSpace 
 	 * @param sFactory 
 	 * @return
 	 */
	public OMElement buildDAMLInfoRequest(SOAPFactory sFactory, OMNamespace sNameSpace) {
		
		// Convert Qname to OMNameSpace
		OMNamespaceImpl samlNameSpace=new OMNamespaceImpl(Subject.DEFAULT_ELEMENT_NAME.getNamespaceURI(),Subject.DEFAULT_ELEMENT_NAME.getPrefix());
		OMElement infoRequest=sFactory.createOMElement("SubjectConfirmationData",samlNameSpace);
		// Add Recipient attribute
		infoRequest.addAttribute("Recipient", "NRL Service", samlNameSpace);
				
		return infoRequest;
	}

	/**
	 * @return the webServiceURL
	 */
	public String getWebServiceURL() {
		return webServiceURL;
	}

	/**
	 * @param webServiceURL the webServiceURL to set
	 */
	public void setWebServiceURL(String webServiceURL) {
		this.webServiceURL = webServiceURL;
	}

	/**
	 * @return the subject
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * @param subjectName the subject to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/**
	 * @param soapRequest the soapRequest to set
	 */
	public void setSoapRequest(String soapRequest) {
		this.soapRequest = soapRequest;
	}

	/**
	 * @param soapResponse the soapResponse to set
	 */
	public void setSoapResponse(String soapResponse) {
		this.soapResponse = soapResponse;
	}

	/**
	 * @return the soapResponse
	 */
	public String getSoapResponse() {
		return soapResponse;
	}

	/**
	 * @return the soapRequest
	 */
	public String getSoapRequest() {
		return soapRequest;
	}

}
