/**
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
package org.qualipso.interop.semantics.securecomm.asm.inout;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.Set;
import java.util.concurrent.Exchanger;
import javax.swing.text.Document;
import javax.xml.transform.dom.DOMSource;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.LoggingErrorHandlerBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultExchange;
import org.apache.commons.logging.*;
import org.apache.xindice.xml.TextWriter;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * A Camel Router on Content Message.
 * 
 * The message content will be parsed and the appropriate route-table will be 
 * selected based on that information. The <b>evaluate</b> method will run for
 * each incoming message. 
 * 
 * @version $Revision: 1.1 $
 */
public class MyInOutRouteBuilder extends RouteBuilder {

	private static final String propertyFileName = "Files/Common/Properties/service_endpoints.properties"; 
	private static final String WS_NAME_ATTRIBUTE = "service";
	private static final String WS_NAME_ATTRIBUTE2 = "ID";
	private Properties availableServicesAsProperties = null;
	
	
    public void configure() {

    	try {
		    File file = new File(propertyFileName);
			FileInputStream fis = new FileInputStream(file);
			availableServicesAsProperties = new Properties();
			availableServicesAsProperties.load(fis);
			fis.close();
			
			from("jbi:service:urn:qualipso:wp32:secCommNode2:camelinout")
			.to("jbi:service:urn:qualipso:wp32:secCommNode2:secCommNode2beanwssecService?mep=in-out")
    		.setHeader("endpointName",
    			constant("jbi:service:urn:qualipso:wp32:secCommNode2:")
    				.append(new Expression<Exchange>() {
    					public Object evaluate(Exchange exchange) {
    						Exchange copyEx = exchange.copy();
    						Message msg = copyEx.getIn();
    						
    						org.w3c.dom.Document doc = (org.w3c.dom.Document) msg.getBody(org.w3c.dom.Document.class);
    						Element root = doc.getDocumentElement();
    						System.out.println("MyInOutRouteBuilder elemNAME=["+root.getLocalName()+"]");
    						
    						NamedNodeMap attrs = root.getAttributes();
    						for (int i=0; i<attrs.getLength(); i++) {
    							Node temp = attrs.item(i);
    							
    							if (temp.getNodeName().equalsIgnoreCase(WS_NAME_ATTRIBUTE) || temp.getNodeName().equalsIgnoreCase(WS_NAME_ATTRIBUTE2)) {
    								System.out.println("MyInOutRouteBuilder ROUTING =>["+temp.getNodeValue()+"]");
    								return availableServicesAsProperties.get(temp.getNodeValue().trim());
    							}
    						}
    						
    						// assuming we'll have an error page, redirect there 
    						return "errata";
    					}
    				})
    			).recipientList(header("endpointName")); 
				//.to("jbi:service:urn:qualipso:wp32:secCommNode2:secCommNode2ServerServiceProvInOut?mep=in-out");

    	} catch (Exception ex) {
    		//assuming we shall have an error page, redirect to that one.
    		System.out.println("CAMEL_EXCEPTION");
    		ex.printStackTrace(System.out);
    	}
    }

}
