package org.qualipso.interop.semantics.securecomm.components;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.NormalizedMessage;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.servicemix.JbiConstants;
import org.jdom.input.DOMBuilder;
import org.jdom.output.DOMOutputter;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *  This class implements any operation defined by the user to be done 
 *  with XSLT. Only semantic-lifting and semantic-grounding while the 
 *  semantic-bridging is handled by the generic bridger class. 
 * 
 * @author wp32
 *
 */
public class XSLTComponent implements SemanticLifting, SemanticGrounding {
	
	/** xsl file used for lifting*/
	private String pathToLiftingXsl = null;

	/** xsl file used for grounding*/
	private String pathToGroundingXsl = null;
	
	/** Constructor
	 * 
	 * @param liftingStylesheet: <code>String</code> path to lifting XSL 
	 * @param groundingStylesheet: <code>String</code> path to grounding XSL
	 */
	XSLTComponent(String liftingStylesheet, String groundingStylesheet) {
		pathToLiftingXsl = liftingStylesheet;
		pathToGroundingXsl = groundingStylesheet;
	}
	
	
	/** Apply the XSL transformation on the payload of the <code>MessageExchange</code>. 
	 * Then attached  the created XML i.e. instance to the source-ontology DOM. 
	 * Save the resulting xml to the file path set in <code>instanceSource</code>.
	 * 
	 * @param msgExchange: <code>MessageExchange</code> XML to apply transformation on.
	 * @param sourceOntPath: Path to skeleton source ontology.
	 * @param instanceSource: Where to save the source ontology instance.
	 * 
	 * @throws Exception: <code>TransformerConfigurationException</code>, <code>TransformerException</code>
	 * */
	public void semanticLifting(MessageExchange exchange, String sourceOntPath, String instanceSource) 
		throws Exception {
		
		//get the payload of the message
		NormalizedMessage nmr = exchange.getMessage("in");
		Source payload = nmr.getContent();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new File(sourceOntPath));
		DOMBuilder jdomBuilder = new DOMBuilder();
		DOMOutputter domOut = new DOMOutputter();
		jdomBuilder.setFactory(new org.jdom.UncheckedJDOMFactory());
		org.jdom.Document yoyo = jdomBuilder.build(doc);
		org.jdom.Element rootElem = yoyo.getRootElement();
		
		//System.out.println("XSLTComponent =[" + pathToLiftingXsl + "]");
		Element fromXsl = applyStylesheet(payload, pathToLiftingXsl);
		
		org.jdom.Element newEl = jdomBuilder.build(fromXsl);
		org.jdom.Content cnt = newEl.detach();
		rootElem.addContent(cnt);
		Document docDomCompliant = domOut.output(yoyo);
		
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		FileOutputStream fOS = new FileOutputStream(instanceSource);
		StreamResult result = new StreamResult(fOS);
		transformer.transform(new DOMSource(docDomCompliant), result);
		fOS.flush();
		fOS.close();
	}

	
	/** Applies the xsl stylesheet on the input document and returns the result.
	 * 
	 * @param domSource: <code>Source</code> xml to apply transformation on
	 * @param xslFile: the XSL stylesheet
	 * @return: <code>Element</code> produced from the transformation
	 * @throws Exception: <code>TransformerConfigurationException</code>, <code>TransformerException</code>
	 */
	private Element applyStylesheet (Source domSource, String xslFile) throws Exception {
		
		//System.setProperty("javax.xml.transform.TransformerFactory","net.sf.saxon.TransformerFactoryImpl");
		
		DOMSource tempor = (DOMSource)domSource;
		Document clauss = (Document)tempor.getNode().getOwnerDocument();
		
		Document ameli = (Document) clauss.cloneNode(true);
		DOMSource ddr = new DOMSource(ameli);
		
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer(new StreamSource(xslFile));
			
		DOMResult  dum = new DOMResult();
		transformer.transform(ddr, dum);
			
		Document resDoc = (Document) dum.getNode();
		return resDoc.getDocumentElement();
	}
	

	/** Does semantic grounding. 
	 * The payload from the <code>MessageExchange</code> will be used as the source of
	 * the transformation. The <code>bridgingResultFile</code> will be passed as a PARAM
	 * to the XSLT processor since it will be open using the "document function" within
	 * the XSL file. Once the transformation is complete the <code>DocumentFragment</code>
	 * is set to the incoming/outgoing SOAP-header and returns the updated MessageExchange.
	 * 
	 * @param msgExchange: <code>MessageExchange</code> XML to apply transformation on
	 * @param bridgingResultFile: the result of bridging saved as an 'owl' file that will
	 * 							  be included with "document()" in the XSL. 
	 * @return: Updated <code>MessageExchange</code>.
	 * @throws Exception: <code>TransformerConfigurationException</code>, <code>TransformerException</code>
	 */
	public MessageExchange semanticGrounding(MessageExchange msgExchange, String bridgingResultFile) 
		throws Exception {
		
		System.out.println("\n\nXSLTComponent ----- semanticGrounding");
		
		DOMResult res = new DOMResult();
		
		NormalizedMessage inMessage = msgExchange.getMessage("in");
		NormalizedMessage outMessage = (NormalizedMessage) msgExchange.createMessage();
		
		//CredentialsTranslator.printHeaderProperty(inMessage, JbiConstants.SOAP_HEADERS, "XSLTComponent * semanticGrounding IN * ");
	    
	    Source sss = inMessage.getContent();
	    Document clauss = (Document)((DOMSource)sss).getNode().getOwnerDocument();
		
		
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer xslTransformer = tf.newTransformer(new StreamSource(pathToGroundingXsl));
		//System.out.println("XSLTComponent ----- pathToGroundingXsl=["+pathToGroundingXsl+"]");
		xslTransformer.setParameter("BRIDGE_RESULT", bridgingResultFile);
		xslTransformer.transform(new DOMSource(clauss), res);
		
		Document resDoc = (Document) res.getNode();
		Element root = resDoc.getDocumentElement();
		//TextWriter eleos = new TextWriter(root);
		//System.out.println("\n\nXSLT ROOOOOT -> [" + eleos.toString() +"]\n\n");
		
		Element body = null;
		NodeList nodeHeader = root.getChildNodes();
		
		Map headerFrags = null;
		if (nodeHeader != null && nodeHeader.getLength() > 0) {
			for (int i=0; i<nodeHeader.getLength(); i++) {
				
				Node temp = nodeHeader.item(i);
				if (temp.getNodeType() == Node.ELEMENT_NODE) {
					if (temp.getLocalName().equalsIgnoreCase("Header")) {
						Node tempos = temp.cloneNode(true);
						headerFrags = getHeaderFragmentsAsMap(resDoc, tempos);
						
					} else if (temp.getLocalName().equalsIgnoreCase("Body")) {
						body = (Element)temp.cloneNode(true);
						body = (Element) body.getChildNodes().item(0);
					} else 
						continue;
				}
			}
			
			// do the setting of the "in" and "out" header-properties in separate loops.
			Map mappos = (Map) inMessage.getProperty(JbiConstants.SOAP_HEADERS);
			Map mpp = new HashMap();
			Set keystt = mappos.keySet();
		    for (Iterator it = keystt.iterator(); it.hasNext();) {
		    	Object key = it.next();
		    	
		    	//replace with the header from the transformation
		    	mpp.put(key, (headerFrags.containsKey(key)) ? headerFrags.get(key) : mappos.get(key) );
		    }
			
			outMessage.setProperty(JbiConstants.SOAP_HEADERS, mpp);
			//CredentialsTranslator.printHeaderProperty(outMessage, JbiConstants.SOAP_HEADERS, "XSLTComponent * semanticGrounding OUT * ");
		    
		    Set keysINN2 = mappos.keySet();
		    for (Iterator it = keysINN2.iterator(); it.hasNext();) {
		    	Object key = it.next();
		    	
		    	if (headerFrags.containsKey(key)) {
		    		mappos.remove(key);
		    		mappos.put(key, headerFrags.get(key));
		    	}
		    }
		    
		    outMessage.setContent(new DOMSource(body));
		} 
		msgExchange.setMessage(outMessage, "out");
	    return msgExchange;
	}
	
	
	/** Parses the Node that contains the &lt;xxx:Header&gt; element and find its
	 * children. Extract the <code>Qname</code> these children and the DOM Fragments
	 * and return them as Map. 
	 * 
	 * @param doc: <code>Document</code> for fragments.
	 * @param allHeader: <code>Node</code> holding the DOM content of the header.
	 * @return: <code>Map</code> where keys are the <code>QNames</code> contained 
	 * in the Header, and values the respective <code>DocumentFragments</code>.
	 */
	private Map getHeaderFragmentsAsMap (Document doc, Node allHeader) {
		
		Map fragments = new HashMap();
		
		NodeList headerFrags = allHeader.getChildNodes();
		for (int j=0; j<headerFrags.getLength(); j++) {
			Node temp = headerFrags.item(j);
			
			if (temp.getNodeType() == Node.ELEMENT_NODE) {
				
				Element fragmentos = (Element) temp;
				//System.out.println("INNER NODE_NAME=[" + yoyo.getLocalName() + "] NS=[" + yoyo.getNamespaceURI() + "]");
				QName elemQname = new QName(fragmentos.getNamespaceURI(), fragmentos.getLocalName());
				
				DocumentFragment tmpFrag = doc.createDocumentFragment();
				tmpFrag.appendChild(temp);
				fragments.put(elemQname, tmpFrag);
			}
		}
		
		return fragments;
	}
	
	
}
