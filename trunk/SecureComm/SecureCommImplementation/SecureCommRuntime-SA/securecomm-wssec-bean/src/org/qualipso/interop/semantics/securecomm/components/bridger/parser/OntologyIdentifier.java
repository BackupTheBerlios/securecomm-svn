package org.qualipso.interop.semantics.securecomm.components.bridger.parser;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.qualipso.interop.semantics.securecomm.components.bridger.BridgerConstants;


/** When an ontology (besides bridgeOntology) is to be used by the Bridger
 * three things are necessary: real_file, uri, prefix. 
 *  This class represents that information. 
 * 
 * @author wp32
 */
public class OntologyIdentifier implements BridgerConstants {

	private String prefix = null;
	private String namespace = null;
	private String filepath = null;
	
	/** To be used by sub-classes */
	protected OntologyIdentifier() {
		
	}

	/** Constructor. It also calls the method responsible for finding
	 *  and setting the namespace of the ontology.
	 * 
	 * @param filepath: disk path to ontology file
	 */
	public OntologyIdentifier(String filepath) {
		this.filepath = filepath;
		
		try {
			getNamespaceFromOntFile();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	/** Reads the ontology files and using XPATH gathers all the imported namespaces.
	 * Assuming the base namespace is preficed "xmlns" set the value with that prefix
	 * as the ontology's namespace. 
	 * 
	 * @throws Exception: several.
	 */
	private void getNamespaceFromOntFile() throws Exception {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true); // never forget this!
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(this.filepath);
		
		XPathFactory xfac = XPathFactory.newInstance();
		XPath xpath = xfac.newXPath();
		XPathExpression expr = xpath.compile("//namespace::*");
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		
		NodeList nodes = (NodeList) result;
		//System.out.println("SIZE_OF_RESULTS=["+nodes.getLength()+"]");
	    for (int i = 0; i < nodes.getLength(); i++) {
	    	Node tmp = nodes.item(i);
	        //System.out.println("file=["+filepath+"] NODENAME=["+tmp.getNodeName() + "]===[" + tmp.getNodeValue()+"]"); 
	    	if (tmp.getNodeName().equalsIgnoreCase(BridgerConstants.XML_NS_BASE)) {
	    		String value = tmp.getNodeValue();
	    		value = value.substring(0, (value.length() - 1));
	    		namespace = value;
	    	}
	    }
	}
	
	/** namespace getter*/
	public String getNamespace() {
		return namespace;
	}

	/** filepath getter*/
	public String getFilepath() {
		//return filepath;
		return new File(filepath).toURI().toString();
	}

	/** prefix getter*/
	public String getPrefix() {
		return prefix;
	}


	/** prefix setter*/
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/** namespace setter*/
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	/** filepath setter*/
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
	
}
