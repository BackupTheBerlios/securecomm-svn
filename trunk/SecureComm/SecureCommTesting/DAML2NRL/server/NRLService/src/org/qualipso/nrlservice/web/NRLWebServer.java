package org.qualipso.nrlservice.web;

import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axis2.util.XMLUtils;
import org.opensaml.Configuration;
import org.opensaml.DefaultBootstrap;
import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.saml2.common.Extensions;
import org.opensaml.saml2.common.impl.ExtensionsBuilder;
import org.opensaml.saml2.common.impl.ExtensionsImpl;
import org.opensaml.saml2.core.Advice;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.Status;
import org.opensaml.saml2.core.StatusCode;
import org.opensaml.saml2.core.StatusDetail;
import org.opensaml.saml2.core.StatusMessage;
import org.opensaml.saml2.core.Subject;
import org.opensaml.saml2.core.SubjectConfirmation;
import org.opensaml.saml2.core.SubjectConfirmationData;
import org.opensaml.saml2.core.impl.AssertionBuilder;
import org.opensaml.saml2.core.impl.IssuerBuilder;
import org.opensaml.saml2.core.impl.IssuerImpl;
import org.opensaml.saml2.core.impl.ResponseBuilder;
import org.opensaml.saml2.core.impl.ResponseImpl;
import org.opensaml.saml2.core.impl.StatusBuilder;
import org.opensaml.saml2.core.impl.StatusCodeBuilder;
import org.opensaml.saml2.core.impl.StatusCodeImpl;
import org.opensaml.saml2.core.impl.StatusDetailBuilder;
import org.opensaml.saml2.core.impl.StatusDetailImpl;
import org.opensaml.saml2.core.impl.StatusMessageBuilder;
import org.opensaml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.xml.Namespace;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallerFactory;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.impl.SignatureBuilder;
import org.qualipso.nrlservice.common.Constants;
import org.w3c.dom.Element;

/**
 * Main class implementing a sample NRL server
 * 
 * @author Panos Kavalagios
 *
 */
public class NRLWebServer implements Constants {

	public OMElement Assertion(OMElement element) throws XMLStreamException,Exception {
		
		OMElement method;
		OMElement subjConfData=null;

		try {
			element.build();
			element.detach();

			String rootName = element.getLocalName();
			System.out.println("NRLWebServer: "+rootName+" request...");

			OMElement subject=element.getFirstChildWithName(Subject.DEFAULT_ELEMENT_NAME);
			OMElement operationName=subject.getFirstChildWithName(NameID.DEFAULT_ELEMENT_NAME);
			OMElement subjConf=subject.getFirstChildWithName(SubjectConfirmation.DEFAULT_ELEMENT_NAME);
			subjConfData=subjConf.getFirstChildWithName(SubjectConfirmationData.DEFAULT_ELEMENT_NAME);
			if (operationName!=null) {
				System.out.println("NRLWebServer: "+operationName.getText()+" subject received...");
				if (operationName.getText().equalsIgnoreCase(SECURITY_CONCEPT)) {
					if (subjConfData.getChildren().hasNext()) {
						method=buildNRLResponse("SUCCESS","Security Concept assertion has been accepted",subjConfData);
					}
					else {
						method=buildNRLResponse("FAILED", "Security Concept assertion has been rejected",subjConfData);
					}
				}
				else if (operationName.getText().equalsIgnoreCase(SECURITY_OBJECTIVE)) {
					if (subjConfData.getChildren().hasNext()) {
						method=buildNRLResponse("SUCCESS","Security Objective assertion has been accepted",subjConfData);
					}
					else {
						method=buildNRLResponse("FAILED", "Security Objective assertion has been rejected",subjConfData);
					}
				}
				else {
					if (subjConfData.getChildren().hasNext()) {
						method=buildNRLResponse("SUCCESS","Credential assertion has been accepted",subjConfData);
					}
					else {
						method=buildNRLResponse("FAILED", "Credential assertion has been rejected",subjConfData);
					}
				}
			}
			else {
				throw new XMLStreamException("Undefined subject in assertion");
			}
		} 
		catch (XMLStreamException e) {
        	System.err.println("NRLWebServer: ERROR: "+e.getMessage());
        	e.printStackTrace();
    	    method=buildNRLResponse("ERROR", "An error occurred: "+e.getMessage(),subjConfData);
		}

		return method;
	}

	private OMElement buildNRLResponse(String operStatus, String operMessage, OMElement nrlData) throws Exception {
		
		// Initialise SAML library
		DefaultBootstrap.bootstrap();
		
		// Get the builder factory
		XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();

		// Get the Response builder and create the object
		ResponseBuilder respBuilder=(ResponseBuilder) builderFactory.getBuilder(Response.DEFAULT_ELEMENT_NAME);
		Response nrlResponse=respBuilder.buildObject();
		
		// Get the status builder and create the object
		StatusBuilder statBuilder=(StatusBuilder) builderFactory.getBuilder(Status.DEFAULT_ELEMENT_NAME);
		Status nrlStatus=statBuilder.buildObject();
		
		// Get the status code builder and create the object
		StatusCodeBuilder statCodeBuilder=(StatusCodeBuilder) builderFactory.getBuilder(StatusCode.DEFAULT_ELEMENT_NAME);
		StatusCode nrlStatusCode=statCodeBuilder.buildObject();
		nrlStatusCode.setValue(operStatus);
		
		// Get the status message builder and create the object
		StatusMessageBuilder statMsgBuilder=(StatusMessageBuilder) builderFactory.getBuilder(StatusMessage.DEFAULT_ELEMENT_NAME);
		StatusMessage nrlStatusMessage=statMsgBuilder.buildObject();
		nrlStatusMessage.setMessage(operMessage);
		
		// Initialise reponse
		nrlStatus.setStatusCode(nrlStatusCode);
		nrlStatus.setStatusMessage(nrlStatusMessage);
		nrlResponse.setStatus(nrlStatus);
		
		// Transform to OMElement
		MarshallerFactory marshallerFactory=Configuration.getMarshallerFactory();
		Marshaller marshaller = marshallerFactory.getMarshaller(nrlResponse);
		Element respElement=marshaller.marshall(nrlResponse);
		OMElement samlResponse=XMLUtils.toOM(respElement);
		if (nrlData!=null) {
			nrlData.setLocalName("OriginalRequest");
			samlResponse.addChild(nrlData);
		}
		
		return samlResponse;	
	}
}
