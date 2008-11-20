/*
 * IST-FP6-034763 QualiPSo: QualiPSo is a unique alliance 
 * of European, Brazilian and Chinese ICT industry players, 
 * SMEs, governments and academics to help industries and 
 * governments fuel innovation and competitiveness with Open 
 * Source software. To meet that goal, the QualiPSo consortium 
 * intends to define and implement the technologies, processes 
 * and policies to facilitate the development and use of Open 
 * Source software components, with the same level of trust 
 * traditionally offered by proprietary software. QualiPSo is 
 * partially funded by the European Commission under EU�s sixth 
 * framework program (FP6), as part of the Information Society 
 * Technologies (IST) initiative. 
 * 
 * This program has been created as part of the QualiPSo work 
 * package on "Semantic Interoperability". The basic idea is to show 
 * how semantic technologies can be used to cope with the diversity 
 * and heterogeneity of software and services in the OSS domain.
 *
 * You can redistribute this program and/or modify it under 
 * the terms of the European Union Public License v1.0 (EUPL v1.0)
 * as published by the European Commission.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 *
 * You should have received a copy of the EU Public License
 * along with this program; if not, please have a look at 
 * http://ec.europa.eu/idabc/en/document/6523 
 * to obtain the full license text.
 *
 * Author of this program: 
 * European Dynamics, http://www.eurodyn.com
 */
package org.qualipso.interop.semantics.securecomm.telcoService.onto;

import java.util.LinkedHashMap;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/** Holds a representation of the incomming request: element namespaces, element
 * names along with the appropriate class variables. The request is parsed
 * (header and body in separate ways) and the parsed data are used to fill the 
 * class-member <code>LinkedHashMaps</code>. These hashes are then fetched and used 
 * by the calling class in the next semantic steps. 
 * 
 * @author wp32
 *
 */
public class PrepareSourceOntology {

	/** every request has these fields */
	public static final String entryPoint = "entryPoint";
	public static final String operation = "operation";
	public static final String phoneNumber = "phoneNumber";
	
	/** existence of these fields depend on the request */
	private static final String uname = "uname";
	private static final String password = "password";
	private static final String telephoneNumber = "telephoneNumber";
	private static final String fullname = "fullname";
	private static final String id = "id";
	private static final String socsecnum = "socsecnum";

	/** these fields are grabbed from the requests HEADER */
	private static final String X509IssuerName = "X509IssuerName";
	
	/** these fields are deduces from java code */
	private static final String hasCertificate = "hasCertificate";
	private static final String hasSocSecNum = "hasSocSecNum";
	private static final String hasId = "hasId";
	
	/** true value*/
	private static final String TRUE = "true";
	/** false value */
	private static final String FALSE = "false";
	
	private LinkedHashMap<String, String> requestFields = null;
	private LinkedHashMap<String, String> userDependantFields = null;
	private LinkedHashMap<String, String> requestHeaderFields = null;
	
	public static final String REQUEST_ELEM = "TelcoServiceRequest";
	public static final String REQUEST_USER_ELEM = "user";
	public static final String REQUEST_SPECIFIC_ELEM = "TelcoClient";
	
	public static final String NAMESPACE = "http://www.qualipso.org/interop/semant/securecomm/TelephoneService/sourceOntology.owl#";
	
	/** protected constructor */
	public PrepareSourceOntology () {
		requestFields = commonRequestElemements();
	}
	
	/** This methods has as input the SOAP request body. It runs
	 * a series of checks on that DOM. See below.
	 * 
	 * @param docum: <code>Document</code> selection to check.
	 */
	public void doPrepare(Document docum) {
		getUserElements();
		recurse(docum);
		
		if (userDependantFields.get(id) != null) {
			userDependantFields.put(hasId, TRUE);
		} else {
			userDependantFields.put(hasId, FALSE);
		}
		userDependantFields.remove(id);
		
		if (userDependantFields.get(socsecnum) != null) {
			userDependantFields.put(hasSocSecNum, TRUE);
		} else {
			userDependantFields.put(hasSocSecNum, FALSE);
		}
		userDependantFields.remove(socsecnum);
	}
	
	
	/** This methods has as input a specific SOAP-Header part. It runs
	 * a series of checks on that DOM. See below.
	 * 
	 * @param docfr: <code>DocumentFragment</code> selection to check.
	 */
	public void doPrepareHeader(DocumentFragment docfr) {
		getRequestHeaderFields();
		recurseHeader(docfr);
		
		if (requestHeaderFields.get(X509IssuerName) != null) {
			System.out.println("PrepareSourceOntology --> FOUND_CERTIFICATE");
			userDependantFields.put(hasCertificate, TRUE);
		} else {
			System.out.println("PrepareSourceOntology --> NULL Certificate");
			userDependantFields.put(hasCertificate, FALSE);
		}
		/** add other fields here as it goes...*/
		
	}
	
	/** A request contains a &lt;user&gt; element. This method defines
	 *  the fields/children that this element may have. 
	 * 
	 */
	private void getUserElements() {
		userDependantFields = new LinkedHashMap<String, String>();
		
		/** some form of user credentials/identification */
		userDependantFields.put(uname, null);
		userDependantFields.put(password, null);
		
		
		/** some other form of user credentials/identification */
		userDependantFields.put(telephoneNumber, null);
		userDependantFields.put(fullname, null);
		userDependantFields.put(id, null);
		userDependantFields.put(socsecnum, null);
	}
	
	
	/** Specific element-names to check for in the Header.
	 *  Their values are used at a latter stage, this method just
	 *  loads them (key=element_name, value=value from DOM) to the 
	 *  "requestHeaderFields" <code>LinkedHashMap</code>
	 * 
	 */
	private void getRequestHeaderFields() {
		requestHeaderFields = new LinkedHashMap<String, String>();
		requestHeaderFields.put(X509IssuerName, null);
	}
	
	
	/** Every request submitted must contain these elements.
	 * 
	 * @return: Nothings, loads to class member.
	 */
	private static LinkedHashMap<String, String> commonRequestElemements() {
		LinkedHashMap<String, String> tmp = new LinkedHashMap<String, String>(3);
		
		tmp.put(entryPoint, null);
		tmp.put(operation, null);
		tmp.put(phoneNumber, null);
		return tmp;
	}
	
	
	/** Traverse the input DOM and try to find if it contains any of the elements
	 * that are hash-keys to the "requestFields" and "userDependantFields" members.
	 * If they are found copy their value to the hashes. 
	 * 
	 * @param node: The <code>Node</code> DOM to recurse.
	 */
	private void recurse(Node node)  {
        
        switch (node.getNodeType()) {

            case Document.DOCUMENT_NODE:
                NodeList nodes = node.getChildNodes();
                if (nodes != null) {
                    for (int i=0; i<nodes.getLength(); i++) {
                        recurse(nodes.item(i));
                    }
                }
                break;
                
            case DocumentFragment.DOCUMENT_FRAGMENT_NODE:
                NodeList fnodes = node.getChildNodes();
                if (fnodes != null) {
                    for (int i=0; i<fnodes.getLength(); i++) {
                        recurse(fnodes.item(i));
                    }
                }
                break;
                
            case DocumentFragment.ELEMENT_NODE:
                String name = node.getNodeName();
                String localName = node.getLocalName();
                
                if (requestFields.containsKey(localName)) {
                    Node mynode = node.getFirstChild();
                    requestFields.put(localName, mynode.getNodeValue());
                    //print("FOUND->11 key=[" + localName.toLowerCase() + "] value=[" + mynode.getNodeValue() + "]");
                } else if (userDependantFields.containsKey(localName)) {
                    Node mynode = node.getFirstChild();
                    userDependantFields.put(localName, mynode.getNodeValue());
                    //print("FOUND->22 key=[" + localName.toLowerCase() + "] value=[" + mynode.getNodeValue() + "]");
                }
                
                // recurse on each child
                NodeList children = node.getChildNodes();
                if (children != null) {
                    for (int i=0; i<children.getLength(); i++) {
                        recurse(children.item(i));
                    }
                }
                
                break;

            case Node.TEXT_NODE:
                break;
        }
    }
	
	
	/** Traverse the input DOM that corresponds to on SOAP Header fragment and try
	 * to find if it contains any of the elements that are hash-keys to the 
	 * "requestHeaderFields" class field. If they are found copy their value to the hashes. 
	 * 
	 * @param node: The <code>Node</code> DOM to recurse.
	 */
	private void recurseHeader (Node node)  {
		switch (node.getNodeType()) {

            case Document.DOCUMENT_NODE:
                NodeList nodes = node.getChildNodes();
                if (nodes != null) {
                    for (int i=0; i<nodes.getLength(); i++) {
                    	recurseHeader(nodes.item(i));
                    }
                }
                break;
                
            case DocumentFragment.DOCUMENT_FRAGMENT_NODE:
            	NodeList fnodes = node.getChildNodes();
                if (fnodes != null) {
                    for (int i=0; i<fnodes.getLength(); i++) {
                    	recurseHeader(fnodes.item(i));
                    }
                }
                break;
                
            case DocumentFragment.ELEMENT_NODE:
                String localName = node.getLocalName();
                if (requestHeaderFields.containsKey(localName)) {
                    Node mynode = node.getFirstChild();
                    requestHeaderFields.put(localName, mynode.getNodeValue());
                    //print("FOUND->11 key=[" + localName.toLowerCase() + "] value=[" + mynode.getNodeValue() + "]");
                } 
                
                // recurse on each child
                NodeList children = node.getChildNodes();
                if (children != null) {
                    for (int i=0; i<children.getLength(); i++) {
                    	recurseHeader(children.item(i));
                    }
                }
                
                break;

            case Node.TEXT_NODE:
                break;
        }
    }
	
	/** A helper printer method */
	public static void print(Object o) {
		System.err.println(""+o);
	}
	
	/** requestFields getter */
	public LinkedHashMap<String, String> getRequestFields() {
		return this.requestFields;
	}

	/** userDependantFields getter */
	public LinkedHashMap<String, String> getUserDependantFields() {
		return userDependantFields;
	}

}
