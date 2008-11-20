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
package org.qualipso.interop.semantics.securecomm.telcoService.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import org.w3c.dom.Document;
import org.jdom.input.DOMBuilder;
import org.jdom.output.DOMOutputter;

/** Considering that the payload of the SOAP messages will be changed/altered according to the Application doing the request
 * this class executes the 'per application' necessary XML constructions & modifications to the original payload.
 * 
 * Each method inserted here should implement the signature: 
 * public static Source updateMessagePayload (Source source, UP1_Authenticator up1Auth)
 * 
 * The Auth_OBJ is already loaded with data from the Semantic/Ontology mappings. If the original payload doesn't contain
 * the data they are inserted otherwise their values are replaced with the respective values from the Auth_OBJ. 
 * The updated <code>Source</code> is then returned. If an fault/error occurs then the input <code>Source</code>
 * is returned on purpose since then the request should be denied by the WS-end.
 * 
 * Extendable: IMHO, override the method by changing only the input OBJ that holds/represents the data of the App.
 * 
 * @author wp31 / kk
 *
 */
public class XMLPayloadConstructor {
    
    /**Modify the SOAP message payload for the Application UP1 whose data are contained/represented by the 
     * <code> UP1_Authenticator</code> object.
     *  
     * @param source: <code>Source</code> containing the soap message payload
     * @param up1Auth: <code>LinkedHashMap</code> object key=tagName to add, value=Tag value
     * @return: updated <source>Source</source>  or the input if some fault/error happen
     */
    public static Source updateMessagePayload(Source source, LinkedHashMap<String, String> changes, 
    		HashMap<String, String> unchanged) {
    	
        try {
            TransformerFactory tfactory = TransformerFactory.newInstance();
            Transformer tom = tfactory.newTransformer();
            if (tfactory.getFeature(DOMResult.FEATURE)) {
                DOMResult result = new DOMResult();
                tom.transform(source, result);   
                Document document = (Document) result.getNode();
                DOMBuilder jdomBuilder = new DOMBuilder();
        		DOMOutputter domOut = new DOMOutputter();

        		jdomBuilder.setFactory(new org.jdom.UncheckedJDOMFactory());

        		org.jdom.Document yoyo = jdomBuilder.build(document);
        		org.jdom.Element root = yoyo.getRootElement();
                
        		
                /** keep the payload elements */
        		Collection elemsToKeep = new ArrayList();
        		List children = root.getChildren();
        		Iterator iterUnch = children.iterator();
                while (iterUnch.hasNext()) {
                	org.jdom.Element tempEl = (org.jdom.Element)iterUnch.next();
                	//QualipsoSemanticMapper.print("ELEM_NAME=["+tempEl.getName()+"]");
                	if (unchanged.containsKey(tempEl.getName())) {
                		elemsToKeep.add(tempEl);
                	}
                }
                
                
        		root.removeContent();
        		root.addContent(elemsToKeep);
        		
        		/** delete all element in the previous request 
                 * if (in hash) & (element exists) => update value
                 *               & !(element exists) => insert Element
                 *  if !(in hash) and (element exists)  => remove Element
                 */
        		Iterator<String> tagNameIter = changes.keySet().iterator();
                while (tagNameIter.hasNext()) {
                    String elemName = tagNameIter.next();
                    String elemValue = changes.get(elemName);
                    org.jdom.Element newElement = new org.jdom.Element(elemName);
                    org.jdom.Text text = new org.jdom.Text(elemName);
					text.setText(elemValue);
					newElement.addContent(text);
					root.addContent(newElement);
                }
                
                //TextWriter zapp = new TextWriter(domOut.output(yoyo));
                //QualipsoSemanticMapper.print("\nQUALIPSO_MAPPER *** semanticGrounding 222 " + zapp.toString()+"\n\n");
                
                Source src = new DOMSource(domOut.output(yoyo));
                return src;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // in any case, return what we got as input
        return source;
    }
    
    
    public static Document getMessagePayloadAsDoc(Source source) {
        
        Document document = null;
        try {
            TransformerFactory tfactory = TransformerFactory.newInstance();
            Transformer tom = tfactory.newTransformer();
            if (tfactory.getFeature(DOMResult.FEATURE)) {
                DOMResult result = new DOMResult();
                tom.transform(source, result);   
                document = (Document) result.getNode();
            }
        } catch (Exception ex) {
        	ex.printStackTrace();
		}
	        
        // in any case, return what we got as input
        return document;
    }
    
    
}