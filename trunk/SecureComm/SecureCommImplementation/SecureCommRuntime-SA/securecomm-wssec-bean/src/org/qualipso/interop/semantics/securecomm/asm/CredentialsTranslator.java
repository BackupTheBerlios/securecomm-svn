/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qualipso.interop.semantics.securecomm.asm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.InOnly;
import javax.jbi.messaging.InOut;
import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import org.apache.servicemix.MessageExchangeListener;
import org.apache.servicemix.jbi.util.MessageUtil;
import org.apache.xindice.xml.TextWriter;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.qualipso.interop.semantics.securecomm.utils.Constants;
import org.qualipso.interop.semantics.securecomm.components.ComponentImpl;
import org.qualipso.interop.semantics.securecomm.components.DefaultComponentImplFactory;


/** Overrides the default ASM <code>MessageExchangeListener</code>.
 *  Reflection calls to classnames responsible for the requested service.
 *  pushes the updated message to the service-provider and returns the response.
 *  
 * @author wp32
 *
 */
public class CredentialsTranslator implements MessageExchangeListener, Constants {

	/** folder under ASM dir where service-data are stored. */
	public static final String RUNTIME_DIR = "Files/";
	
	/** folder where common files for all services reside. */
	public static final String COMMON_PROPERTY_FILES = "Common/Properties/";
	
	/** property file describing the service .*/
	public static final String SERVICE_DESCRIPTION = "service_description.properties";
	
	/** where service-ontologies are stored .*/
	public static final String ONTOLOGY_FOLDER = "ontologies/";
	
	/** where service xslts (if any) are stored .*/
	public static final String XSLT_FOLDER = "XSLTs";
	
	@Resource
	private DeliveryChannel channel;

    public void onMessageExchange_Default(MessageExchange exchange) throws MessagingException {
        //System.out.println("\n\n onMessageExchange_Default: -- Received exchange: " + exchange + "\n\n");
        exchange.setStatus(ExchangeStatus.DONE);
        channel.send(exchange);
    }
    
    /** Overrides the servicemix method. 
     *  Just let the message pass through without any processing.
     */
     public void onMessageExchange(MessageExchange exchange) throws MessagingException {
        if (exchange == null) {
            return;
        }
    
        if (exchange.getRole() == MessageExchange.Role.CONSUMER) {
        	
            // The component acts as a consumer, this means this exchange is received because
            // we sent it to another component. As it is active, this is either an out or a fault
            // If this component does not create / send exchanges, you may just throw an
            // UnsupportedOperationException
            onConsumerExchange(exchange);
        } else if (exchange.getRole() == MessageExchange.Role.PROVIDER) {
            // The component acts as a provider, this means that another component has requested our service
            // As this exchange is active, this is either an in or a fault (out are send by this component)
            onProviderExchange(exchange);
        } else {
            // Unknown role
            throw new MessagingException(
                    "HandlerBean.onMessageExchange(): Unknown role: " + exchange.getRole());
        }
    }
    
   /** Overrides the respective method. 
    *  Just let the message pass through without any processing.
    */
    private void onConsumerExchange(MessageExchange exchange) throws MessagingException {
        if (exchange.getMessage("out") != null) {
            // Out message
           println("MyWssecBean @@@ onConsumerExchange @@@ out");
           exchange.setStatus(ExchangeStatus.DONE);
           channel.send(exchange);
        } else if (exchange.getFault() != null) {
        	// Fault message
            exchange.setStatus(ExchangeStatus.DONE);
            channel.send(exchange);
        } else {
            // This is not compliant with the default MEPs
            throw new MessagingException(
                    "HandlerBean.onConsumerExchange(): Consumer exchange is ACTIVE, but no out or fault is provided");
        }
    }
    
    
    /** Overrides the default AMS handler. 
     * Leaves control of the incoming request to the className that has
     * been declared as the service handler (when deploying a new service via frontend-ui). The 
     * handler per se elaborates and returns the updated message that will be pushed to the provider.
     * 
     * @param exchange: ASM <code>MessageExchange</code> object
     * @throws MessagingException
     */
    private void onProviderExchange (MessageExchange exchange) throws MessagingException {
        if (exchange.getStatus() == ExchangeStatus.DONE) {
            // Exchange is finished
            println("MyWssecBean *** onProviderExchange *** DONE***");
            return;
        } else if (exchange.getStatus() == ExchangeStatus.ERROR) {
            // Exchange has been aborted with an exception
            return;
        } else if (exchange.getFault() != null) {
            // Fault message
            exchange.setStatus(ExchangeStatus.DONE);
            channel.send(exchange);
        } else {
        	if (exchange instanceof InOut) {
                if (exchange.getStatus() == ExchangeStatus.ACTIVE) {
                   try {
                	   NormalizedMessage inMessage = exchange.getMessage("in");
                	   
                	   TransformerFactory tfactory = TransformerFactory.newInstance();
                       Transformer tom = tfactory.newTransformer();
                        
                       if (tfactory.getFeature(DOMResult.FEATURE)) {
                    	   try {
	                            DOMResult result = new DOMResult();
	                            tom.transform(inMessage.getContent(), result);   
	                            Document document = (Document) result.getNode();
	                            Element elem = document.getDocumentElement();
	                            
	                            String operationName = null;
	                            NamedNodeMap attrs = elem.getAttributes();
	    						for (int i=0; i<attrs.getLength(); i++) {
	    							Node temp = attrs.item(i);
	    							//System.out.println("atr_name=[" + temp.getLocalName() +"] val=[" + temp.getNodeValue() +"]\n");
	    							if (temp.getLocalName().equalsIgnoreCase(Constants.WS_NAME)) {
	    								operationName = temp.getNodeValue().trim();
	    								break;
	    							}
	    						}
	                            
	    						if (operationName == null) 
	    							throw new Exception("Can't understand SERVICE to route to");
	    						
	                            System.out.println("-> name=[" + operationName + "] URI=[" + elem.getNamespaceURI() +"]");
	                            
	                            String serviceWorkingDir = RUNTIME_DIR + File.separator + operationName + File.separator;
	                            String pathToServiceProperties = serviceWorkingDir + SERVICE_DESCRIPTION;
	                            
	                            // load the default component 
	                            ComponentImpl runner = new DefaultComponentImplFactory(pathToServiceProperties);
	                            runner.init();
	                            runner.semanticLifting(exchange);
	                            runner.semanticBridging();
	                    		MessageExchange renewed = runner.semanticGrounding(exchange);
	                    		
	                    		//printHeaderProperty(renewed.getMessage("in"), JbiConstants.SOAP_HEADERS, "CREDIT - IN -");
	                    		//printHeaderProperty(renewed.getMessage("out"), JbiConstants.SOAP_HEADERS, "CREDIT - OUT -");
	                    		//println("CreditTranslator ++++ MESSAGE=["+renewed+"]");
	                    		
	                    		channel.send(renewed);
                        	} catch (Exception ex) {
                        		println("CreditTranslator ++++ Exception =[" + ex.getMessage() + "]");
                        		ex.printStackTrace(System.err);
                        		MessageUtil.transferInToOut(exchange, exchange);
                        		channel.send(exchange);
                        	}
                        } else {
                        	println("CreditTranslator ++++ Transformer Exception");
                        	MessageUtil.transferInToOut(exchange, exchange);
                        	channel.send(exchange);
                        }
                    } catch (MessagingException mex) {
                    	println("CreditTranslator ++++ MessagingException Exception");
                        mex.printStackTrace();
                    } catch (Exception ex) {
                        println("EXCEPTION ------------->"+ex.getMessage());
                        ex.printStackTrace(System.out);
                    }
                }
   
            } else if (exchange instanceof InOnly) {
                println("MyWssecBean *** onProviderExchange *** InOnly *** DONE***");
                exchange.setStatus(ExchangeStatus.DONE);
                channel.send(exchange);
            } else {
                println("MyWssecBean --- ELSE ---" + exchange + "---\n\n");
            }
        }
    }

    
    /** Just prints a request property from the message header. it traverses the DOM structure
     *  and prints the result or throws an exception. 
     * 
     * @param nmg: <code>NormalizedMessage</code> to get the header property from.
     * @param requestedProperty: the name of the requested property. 
     * @param label: a print label specific for the caller.
     */
    public static void printHeaderProperty(NormalizedMessage nmg, String requestedProperty, String label) {
    	
    	Map map = (Map) nmg.getProperty(requestedProperty);
	    Set keys = map.keySet();
	    for (Iterator it = keys.iterator(); it.hasNext();) {
	    	Object key = it.next();
	        try {
	        	DocumentFragment docFrag = (DocumentFragment) map.get(key);
		        TextWriter partial = new TextWriter(docFrag);
				System.out.println(label + " printHeaderProperty key=[" + key + "] ====== [" + partial.toString() + "]===");
	        } catch (Exception ox) {
	        	System.out.println(label + " printHeaderProperty key=[" + key + "] EXCEPTION");
	        	ox.printStackTrace();
	        }
	    }
    }
    
        
    /** helper printer. */
    public static void println(Object str) {
        System.out.println("" + str.toString());
    }
    
    
    /** Copies a file.
	 * 
	 * @param source: file to copy (path+name)
	 * @param destination: file to copy to (path + name)
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void copyFile(String source, String destination)  
		throws FileNotFoundException, IOException {
		
		FileChannel ic = new FileInputStream(source).getChannel();
		FileChannel oc = new FileOutputStream(destination).getChannel();
		ic.transferTo(0, ic.size(), oc);
		ic.close();
		oc.close();
	}
    
    
}
