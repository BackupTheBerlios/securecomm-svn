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
package org.qualipso.interop.semantics.securecomm.telcoService;


import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.NormalizedMessage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.qualipso.interop.semantics.securecomm.telcoService.utils.*;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.apache.servicemix.JbiConstants;
import org.apache.servicemix.components.util.CopyTransformer;
import org.jdom.input.DOMBuilder;
import org.jdom.output.DOMOutputter;
import org.qualipso.interop.semantics.securecomm.telcoService.onto.PrepareSourceOntology;
import org.qualipso.interop.semantics.securecomm.telcoService.onto.RequestToApplication;


/** This class is responsible for the semantic mappings based on the data that are extracted from the 
 *  WSsec signature. Ontology URI/Names are set as variables of this class. 
 *  The method <b>doSemanticMapping</b> does most of the work.
 * 
 * @author wp31
 *
 */
public class QualipsoSemanticMapper {
	public static HashMap<String, String> availableApps = null;
	
	public static final String LOCATION_IMPL_PROPERTY_FILE = "/Other/";
	public final static String ONTOLOGY_FOLDER = "/ontologies/";
	
	/** NameSpaces **/
	final static String SOURCE_ONT_URI = "http://www.qualipso.org/interop/semant/securecomm/TelephoneService/sourceOntology.owl";	
	final static String TARGET_ONT_URI = "http://www.qualipso.org/interop/semant/securecomm/TelephoneService/targetOntology.owl";	
	final static String RESULTING_ONT_URI = "http://www.qualipso.org/interop/semant/securecomm/TelephoneService/resultOntology.owl";
	final static String AUTHPOLICY_ONT_URI = "http://www.qualipso.org/interop/semant/securecomm/TelephoneService/authTypeOntology.owl";
	
	final static String SOURCE_NAMESPACE_PREFIX = "p1";
	public final static String TARGET_NAMESPACE_PREFIX = "p2";
	public final static String AUTHPOLICY_NAMESPACE_PREFIX = "p3";
	
	/** prepare the request<=>source relation objs */
	PrepareSourceOntology preparer = null;

	
	/** Does Semantic Grounding. 
	 * It parses the results from the input filename and uses them in order
	 * to update the payload of the outgoing <code>msgExchange</code>. 
	 * Also, it changes the "mustUnderstand" attribute to 0 in order to
	 * skip WS-Security verification from the WS service. 
	 * 
	 * @param msgExchange: <code>msgExchange</code> input from ASM.
	 * @param outstr: filename holding the result of bridging.
	 * @return: updated <code>msgExchange</code>.
	 * 
	 * @throws Exception
	 */
	public MessageExchange semanticGrounding(MessageExchange msgExchange, String outstr) throws Exception {
		
		LinkedHashMap<String, String> results = loadDataFromResultOntology(outstr, RequestToApplication.getTargetElements());
		HashMap<String, String> payloadElems = RequestToApplication.getPayloadElements();
		
		NormalizedMessage inMessage = msgExchange.getMessage("in");
		NormalizedMessage outMessage = (NormalizedMessage) msgExchange.createMessage();
        CopyTransformer.getInstance().transform(msgExchange, inMessage, outMessage);
        	
       
        Source content = XMLPayloadConstructor
                            .updateMessagePayload(inMessage.getContent(), results, payloadElems);
       
        outMessage.setContent(content);
        
        // change the "mustUnderstand" field of the SOAP-headers.
        Map map = (Map) outMessage.getProperty(JbiConstants.SOAP_HEADERS);
        Set keys = map.keySet();
        for (Iterator it = keys.iterator(); it.hasNext();) {
            DocumentFragment docFrag = (DocumentFragment) map.get(it.next());
            
            recurse(docFrag);
        }
        msgExchange.setMessage(outMessage, "out");
        
        return msgExchange;
	}
	
	/** Execute the semantic bridging. Set the members and call the 
	 *  <code>doBridging</code> method that does the real work. 
	 *  Finally, the outcome is saved as a file. 
	 * 
	 * @param sourceOntology: The path to the sourceOntology.owl file.
	 * @param targetOntology: The path to the targetOntology.owl file.
	 * @param bridgeOntology: The path to the bridgeOntology.owl file.
	 * @param bridgeRepository: The path to the bridgeRepository file.
	 * @param otherOntologies: A list containing all other ontos imported/used.
	 * 
	 */ 
	public void semanticBridging(String sourceOnto, String targetOnto,
			String bridgeOnto, String bridgeRepository, List otherOnto, String resultOfBridging) 
			throws Exception {
		
			/** load bridgeOntology data */
			BridgeOntologyIdentifer bridgeOntology = new BridgeOntologyIdentifer(bridgeOnto);
			HashMap importedPrefixNamespace = bridgeOntology.getImportedPreficesAndNamespaces();
			
			/** load sourceOntology data */
			OntologyIdentifier sourceOntology = new OntologyIdentifier(sourceOnto);
			if (importedPrefixNamespace.containsKey(sourceOntology.getNamespace()))
				sourceOntology.setPrefix((String) importedPrefixNamespace.get(sourceOntology.getNamespace()));
			
			List otherOntologies = new ArrayList<OntologyIdentifier>();
			/** load target ontology -- 1st on the list*/
			OntologyIdentifier targetOnt = new OntologyIdentifier(targetOnto);
			//System.out.println("TARGET->["+targetOnt.getNamespace()+"]");
			if (importedPrefixNamespace.containsKey(targetOnt.getNamespace())) {
				targetOnt.setPrefix((String) importedPrefixNamespace.get(targetOnt.getNamespace()));
				otherOntologies.add(targetOnt);
			}
			
			/** load other ontologies */
			for (int i=0; i<otherOnto.size(); i++) {
				OntologyIdentifier tmp = new OntologyIdentifier((String)otherOnto.get(i));
				if (importedPrefixNamespace.containsKey(tmp.getNamespace())) {
					tmp.setPrefix((String) importedPrefixNamespace.get(tmp.getNamespace()));
					otherOntologies.add(tmp);
				}
				tmp = null;
			}
			
			SemanticBridge semanticBridge = new SemanticBridge();
			
			semanticBridge.setSourceOntology(sourceOntology);
			semanticBridge.setBridgeOntology(bridgeOntology);
			semanticBridge.setOtherOntologies(otherOntologies);
			
			semanticBridge.setResultingOntURI(BridgerConstants.RESULT_ONTOLOGY_URI);
			semanticBridge.createRepositoryFile(bridgeRepository);
			semanticBridge.setResultingBridgeOntFile(resultOfBridging);
			
			semanticBridge.execute();
	}
	
	
	/** Parse the payload of the <code>MessageExchange</code> input message and 
	 * generate an instance ontology based in the skeleton ontology <code>sourceOntology</code>.
	 * Save this in the <code>sourceInstance</code> filename.
	 * 
	 * @param exchange: the <code>MessageExchange</code> wrapping the current request.
	 * @param sourceOntology: The path to the sourceOntology.owl file
	 * @param sourceInstance: The filename to save the source instance
	 */
	public void semanticLifting(MessageExchange msgExchange, String sourceOntology, String sourceInstance) 
		throws Exception {
		
		/** this also copied the "in" to "out" message. so use with extreme care
		 see above how it was before */
		NormalizedMessage message = msgExchange.getMessage("in");
		
		Document requestDoc = XMLPayloadConstructor.getMessagePayloadAsDoc(message.getContent());
		
		/** process the body of the request*/
		preparer = new PrepareSourceOntology();
		preparer.doPrepare(requestDoc);
		
		/** process the headers -- we want info from the certificate */
		Map map = (Map) message.getProperty("org.apache.servicemix.soap.headers");
        Set keys = map.keySet();
        for (Iterator it = keys.iterator(); it.hasNext();) {
            DocumentFragment docFrag = (DocumentFragment) map.get(it.next());
            
            preparer.doPrepareHeader(docFrag);
        }
		
		fillSourceOntologyIndividual(sourceOntology, sourceInstance);
		//print("semanticLifting *** DONE_FILLING");
	}
	
	
	/** Get the DOM structure from the skeleton source ontology. Create instance
	 * elements by using the data from <code>PrepareSourceOntology</code> and 
	 * attach this data to the DOM. Finally, the updated DOM is saved in the path
	 * specified by <code>filenameToSave</code>
	 * 
	 * @param currentSourceFile: Filename to the source ontology skeleton.
	 * @param filenameToSave: Filename to save the instance source ontology.
	 * @throws Exception
	 */
	private void fillSourceOntologyIndividual(String currentSourceFile, String filenameToSave) throws Exception {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new File(currentSourceFile));
		DOMBuilder jdomBuilder = new DOMBuilder();
		DOMOutputter domOut = new DOMOutputter();
	
		jdomBuilder.setFactory(new org.jdom.UncheckedJDOMFactory());
	
		org.jdom.Document yoyo = jdomBuilder.build(doc);
		org.jdom.Element rootElem = yoyo.getRootElement();
	
		/** add the common fields */
		LinkedHashMap<String, String> dataToInsert = preparer.getRequestFields();
		/** create the Request_element Instance in the ontology DOM */
		org.jdom.Element requestElem = 
			new org.jdom.Element(PrepareSourceOntology.REQUEST_ELEM, PrepareSourceOntology.NAMESPACE);
			
		Iterator<String> iterS = dataToInsert.keySet().iterator();
		while (iterS.hasNext()) {
			String name = iterS.next();
			String value = dataToInsert.get(name);
			if (value != null) {
				org.jdom.Element found = findTagsToChange(rootElem, name);
				if (found != null) {
					found.setText(value);
					//print("common -------------ADDING------------- [" + name +"]");
				} else {
					// try to insert the new element 
					//print("common -------------INSERTING------------- [" + name +"]");
					org.jdom.Text text = new org.jdom.Text(name);
					org.jdom.Element newEl = new org.jdom.Element(name, PrepareSourceOntology.NAMESPACE);
					text.setText(value);
					newEl.addContent(text);
					requestElem.addContent(newEl);
				}
			}
		}
		
		
		/** create the "user" element */
		org.jdom.Element userEl = 
			new org.jdom.Element(PrepareSourceOntology.REQUEST_USER_ELEM, PrepareSourceOntology.NAMESPACE);
		org.jdom.Element telcoCl = 
			new org.jdom.Element(PrepareSourceOntology.REQUEST_SPECIFIC_ELEM, PrepareSourceOntology.NAMESPACE);
		
		LinkedHashMap<String, String> requestDependantData = preparer.getUserDependantFields();
		Iterator<String> iter = requestDependantData.keySet().iterator();
		while (iter.hasNext()) {
			String name = iter.next();
			String value = requestDependantData.get(name);
			if (value != null) {
				//print("specific -------------INSERTING------------- [" + name +"]");
				org.jdom.Text text = new org.jdom.Text(name);
				org.jdom.Element newEl = new org.jdom.Element(name, PrepareSourceOntology.NAMESPACE);
				text.setText(value);
				newEl.addContent(text);
				telcoCl.addContent(newEl);
			}
		}
		
		/** finally attach element-instance */
		userEl.addContent(telcoCl);
		requestElem.addContent(userEl);
		rootElem.addContent(requestElem);
		
		
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		DOMSource source = new DOMSource(domOut.output(yoyo));
		FileOutputStream fOS = new FileOutputStream(filenameToSave);
		StreamResult result = new StreamResult(fOS);
		transformer.transform(source, result);
		fOS.flush();
		fOS.close();
	}
	
	
	/** Within a given father-element search recursively all the tree to find 
	 * an element that matches the <code>tagName</code> and 
	 * <code>namespace</code> criteria. Returns the <b>first</b> element that is found.
	 * 
	 * @param fatherElem: <code>org.jdom.Element</code> DOM to search for.
	 * @param tagName: The name of the tag to search.
	 * @param namespace: The namespace of the tag to search.
	 * @return: <code>org.jdom.Element</code> if found, null if not found.
	 */
	private org.jdom.Element findTagsToChange(org.jdom.Element fatherElem, String tagName, String namespace) {
		org.jdom.filter.ElementFilter filtros = new org.jdom.filter.ElementFilter(tagName);
		List sameNameList = fatherElem.getContent(filtros);
		if (sameNameList.size() > 0) {
			for (int i=0; i<sameNameList.size(); i++) {
				org.jdom.Element tmp = (org.jdom.Element)sameNameList.get(i);
				if (namespace.equalsIgnoreCase(tmp.getNamespacePrefix())) {
					return tmp;
				}
			}
		} else {
			List lista = fatherElem.getChildren();
			for (int i=0; lista!=null && i<lista.size(); i++) {
				org.jdom.Element found = findTagsToChange((org.jdom.Element)lista.get(i), tagName, namespace);
				if (found != null) {
					return found;
				}
			}
		}
		return null;
	}
	
	
	/** Recursively travel thru the XML until we find the <b>mustUnderstand</b> element. 
     * Once found return change it's value to "0". Otherwise the WS-server will not 
     * understand our security and will reject our request.
     * 
     * @param node: <code>DocumentFragment</code> representing the signature.
     */
    private void recurse(Node node)  {
        
        switch (node.getNodeType()) {

            case DocumentFragment.DOCUMENT_NODE:
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
                
                NamedNodeMap attributes = node.getAttributes();
                for (int i=0; i<attributes.getLength(); i++) {
                    Node current = attributes.item(i);
                    if (current.getNodeName().contains("mustUnderstand")) {
                        current.setNodeValue("0");
                        break;
                    }
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
    
   
	/** Searches recursively all the children of the given element for elements whose name is the given tag.
	 * Returns the <b>first</b> element that is found.
	 * 
	 * @param zozo: <code>org.jdom.Element</code> Element to check if contains the requested tag.
	 * @param tagName: tagname to search for.
	 * @return: the found <code>org.jdom.Element</code>
	 */
	private org.jdom.Element findTagsToChange(org.jdom.Element zozo, String tagName) {
		org.jdom.filter.ElementFilter filtros = new org.jdom.filter.ElementFilter(tagName);
		if (zozo.getContent(filtros).size() > 0) {
		    return (org.jdom.Element)zozo.getContent(filtros).get(0);
		} else {
			List lista = zozo.getChildren();
			for (int i=0; lista!=null && i<lista.size(); i++) {
			    //print("SEARCHING for =" +tagName+"=");
				org.jdom.Element found = findTagsToChange((org.jdom.Element)lista.get(i), tagName);
				if (found != null) {
					return found;
				}
			}
		}
		return null;
	}
	
	
	/** Helper printer method.
	 * 
	 * @param o: object to print
	 */
	public static void print(Object o) {
		System.out.println(""+o);
	}
	
	
	/** Traverse the bridge result and extract the values for the element name that 
	 * are in the hash.
	 * 
	 * @param stream: filename containing the XML to parse.
	 * @param data: data to extract as <code>LinkedHashMap</code>
	 * @return: updated <code>LinkedHashMap</code> with values from the result
	 */
	private LinkedHashMap<String, String> loadDataFromResultOntology(String stream,
			LinkedHashMap<String, String> data) {
		try {
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new File(stream));
			
			DOMBuilder jdomBuilder = new DOMBuilder();
			jdomBuilder.setFactory(new org.jdom.UncheckedJDOMFactory());
			org.jdom.Element root = jdomBuilder.build(doc).getRootElement();
			
			
			Iterator iter = data.keySet().iterator();
			while (iter.hasNext()) {
				String tagName = (String) iter.next();
				org.jdom.Element found = findTagsToChange(root, tagName, QualipsoSemanticMapper.TARGET_NAMESPACE_PREFIX);
				if (found!=null) 
					data.put(tagName, found.getText());
			}
			return data;
		} catch (Exception ex) {
			QualipsoSemanticMapper.print("$$$$$$$ getDataFromResultOntology $$$$ ["+ex.getMessage()+"]");
		    ex.printStackTrace(System.err);
		}
		return null;
	}
	
	
}
	
	
