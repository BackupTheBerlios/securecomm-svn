package org.qualipso.interop.semantics.securecomm.components.bridger.parser;

import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.qualipso.interop.semantics.securecomm.components.bridger.BridgerConstants;

/** This class inherits the reasons/logic of the <b>OntologyIdentifier</b> class
 *  yet is implements a different logic since it has to extract the preficces of
 *  the imported ontologies.
 *  These are stored on the <b>importedPreficesAndNamespaces</b> HashMap as 
 *  key=namespace_URI, value=prefix
 * 
 * @author wp32
 */
public class BridgeOntologyIdentifer extends OntologyIdentifier {

	private HashMap<String, String> importedPreficesAndNamespaces = null;
	
	/** constructor */
	public BridgeOntologyIdentifer(String filepath) {
		super.setFilepath(filepath);
		
		try {
			getImportedOntologiesFromBridgeOntology(filepath);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

	/** parse all imported ontologies in the bridge and store their
	 *  prefix and namespace. 
	 * 
	 * @param filepath: disk path to file
	 * @throws Exception: several might be thrown
	 */
	private void getImportedOntologiesFromBridgeOntology(String filepath) throws Exception {
		
		/** get all the ontologies that are imported as "owl:imports" */
		HashMap<String, String> importedOWLS = new HashMap<String, String>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true); // never forget this!
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(filepath);

		NodeList nodelist = doc.getElementsByTagNameNS("*", BridgerConstants.OWL_IMPORT_KEYWORD);
		//System.err.println("SIZE_OF_IMPORTS=[" + nodelist.getLength() + "]");
		for (int i = 0; i < nodelist.getLength(); i++) {
			Element tmp = (Element) nodelist.item(i);

			NamedNodeMap nmm = tmp.getAttributes();
			for (int j = 0; j < nmm.getLength(); j++) {
				Attr toto = (Attr) nmm.item(j);
				if (toto.getName().equalsIgnoreCase(BridgerConstants.OWL_IMPORT_NAMESPACE_ATTR)) {
					//System.out.println("[" + toto.getLocalName() + "][" + toto.getValue() + "]");
					importedOWLS.put(toto.getValue(), toto.getValue());
					break;
				}
			}
		}
		
		/** at least 2 ontologies are imported: source & target. */
		if (importedOWLS.size() > 2) {
			this.importedPreficesAndNamespaces = new HashMap<String, String>();
			
			XPathFactory xfac = XPathFactory.newInstance();
			XPath xpath = xfac.newXPath();
			XPathExpression expr = xpath.compile("//namespace::*");
			Object result = expr.evaluate(doc, XPathConstants.NODESET);
			
			NodeList namespaceNodes = (NodeList) result;
			for (int i = 0; i < namespaceNodes.getLength(); i++) {
		    	Node tmp = namespaceNodes.item(i);
		    	String nodeValue = tmp.getNodeValue();
		    	nodeValue = nodeValue.substring(0, (nodeValue.length() - 1));
		        //System.out.println(tmp.getNodeName() + "===" + nodeValue); 
		    	
		    	if (importedOWLS.containsKey(nodeValue)) {
		    		String val = tmp.getNodeName().split(":")[1];
		    		//System.out.println("postfix=["+val+"] for [" + importedOWLS.get(nodeValue) +"]");
		    		importedPreficesAndNamespaces.put(importedOWLS.get(nodeValue), val);
		    	} else  if (tmp.getNodeName().equalsIgnoreCase(BridgerConstants.XML_NS_BASE)) {
		    		//String value = nodeValue;
		    		super.setNamespace(nodeValue);
		    	}
		    	
		    			
			}
		}
		
		doc = null;
	}


	/** importedPreficesAndNamespaces getter */
	public HashMap<String, String> getImportedPreficesAndNamespaces() {
		return importedPreficesAndNamespaces;
	}
	
	
}
