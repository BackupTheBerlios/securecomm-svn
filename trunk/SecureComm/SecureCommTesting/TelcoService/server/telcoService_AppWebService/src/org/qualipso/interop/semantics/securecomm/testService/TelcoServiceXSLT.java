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
package org.qualipso.interop.semantics.securecomm.testService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.qualipso.interop.semantics.securecomm.testService.ActionAuthCheck;
import javax.xml.stream.XMLStreamException;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.qualipso.interop.semantics.securecomm.testService.ServiceRequestFields;

/** Sample Service With Authentication
 * 
 * @author wp32
 *
 */
public class TelcoServiceXSLT {
	
	private String namespace = "http://qualipso.authenticators/xsd";
	
	OMFactory fac = OMAbstractFactory.getOMFactory();
	OMNamespace omNs = fac.createOMNamespace(namespace, "xsd");
	
	
	/** This is a sample operation. 
	 * It provides information about the actions done within the given timeframe. 
	 * Auth checking is passed to the <code>ActionAuthCheck</code> class. 
	 * A randomly generated list of actions per date is returned otherwise an error message.
	 * 
	 * @param element: <code>Axiom</code> Request element.
	 * @return: response.
	 * @throws XMLStreamException
	 * @throws Exception
	 */
	public OMElement getAccountInfo(OMElement element) throws XMLStreamException, Exception {

		System.err.println("----getAccountInfo----");
		
		element.build();
        element.detach();

        /** make ready the factory */
        OMElement method = fac.createOMElement("getAccountInfoResponse", omNs);
        
        try {
	        List fields = ServiceRequestFields.getAccountInfoFields();
	        //System.err.println("getAccountInfo SIZE=["+fields.size()+"]");
	        Iterator<OMElement> iterEl = element.getChildElements();
	        
	        String startDate = null, endDate = null;
	        String authVal = null, action = null, uname = null;
	        while (iterEl.hasNext()) {
	        	OMElement tmp = (OMElement)iterEl.next();
	        	if (fields.contains(tmp.getLocalName())) {
	        		System.err.println("getAccountInfo --- localName=["+tmp.getLocalName()+"] INSERTING=["+tmp.getText()+"]");
	        		if  (tmp.getLocalName().equalsIgnoreCase(ServiceRequestFields.startTimeFrame)) {
	        			startDate = tmp.getText();
	        		} else if (tmp.getLocalName().equalsIgnoreCase(ServiceRequestFields.endTimeFrame)) {
	        			endDate = tmp.getText();
	        		} else if (tmp.getLocalName().equalsIgnoreCase(ServiceRequestFields.authType)) {
	        			authVal = tmp.getText();
	        		} else if (tmp.getLocalName().equalsIgnoreCase(ServiceRequestFields.action)) {
	        			action = tmp.getText();
	        		} else if (tmp.getLocalName().equalsIgnoreCase(ServiceRequestFields.username)) {
	        			uname = tmp.getText();
	        		}
	        	} 
	        }
	        
	        /** check authentication */
	        ActionAuthCheck checker = new ActionAuthCheck(action, authVal, uname);
	        if (! checker.isActionAllowedForUser())
	        	throw new Exception("Permission denied");
	        
	        /** insert the fake elements */
	        List<OMElement> elemList = getAccountInfoBetweenTimeframe(startDate, endDate);
	        for (int i=0; i< elemList.size(); i++) {
	        	method.addChild(elemList.get(i));
	        }
	        
        } catch (Exception ex) {
        	System.err.println("getAccountInfo - Exception =[" + ex.getMessage() + "]");
        	ex.printStackTrace();
        	OMElement error = fac.createOMElement("error", omNs);
        	error.addChild(fac.createOMText(error, ex.getMessage()));
    	    method.addChild(error);
        }
        
        return method;
	}
	
  
	/** Generate a fake list of actions in between the given dates. 
	 * 
	 * @param start: start date, unparsed, of the form xx/yy/zzzz
	 * @param end: end date, unparsed, of the form xx/yy/zzzz
	 * @return: List of sample elements
	 * @throws Exception
	 */
	private List<OMElement> getAccountInfoBetweenTimeframe (String start, String end) throws Exception {
		String[] startArr = start.split("/");
		String[] endArr = end.split("/");
		
		List<OMElement> info = new ArrayList<OMElement>();
		
		Random rtm = new Random(10999);
		
		Calendar startCal = Calendar.getInstance();
		startCal.set(Integer.valueOf(startArr[2]).intValue(), Integer.valueOf(startArr[1]).intValue(), 
				Integer.valueOf(startArr[0]).intValue());
		
		Calendar endCal = Calendar.getInstance();
		endCal.set(Integer.valueOf(endArr[2]).intValue(), Integer.valueOf(endArr[1]).intValue(), 
				Integer.valueOf(endArr[0]).intValue());
		
		int count = 0;
		while (startCal.before(endCal)) {
			startCal.add(Calendar.DAY_OF_MONTH, +1);
			OMElement move = fac.createOMElement("call", omNs);
			String attrVal = startCal.get(Calendar.DAY_OF_MONTH) + "/" + startCal.get(Calendar.MONTH) 
								+ "/" + startCal.get(Calendar.YEAR);
			OMAttribute attr  = fac.createOMAttribute("date", omNs, attrVal);
			move.addAttribute(attr);
			move.setText(String.valueOf(Math.abs(rtm.nextInt())));
			info.add(move);
			count++;
		}
		
		//System.err.println(" Days DISTANCE =[" + count + "]");
		return info;
	}
	
	
	/** This is a sample operation. 
	 * It replaces the account pin with the new value parsed from the request. 
	 * Auth checking is passed to the <code>ActionAuthCheck</code> class. 
	 * The new value is returned as success otherwise an error message. 
	 * 
	 * @param element
	 * @return
	 * @throws XMLStreamException
	 */
	public OMElement resetPin (OMElement element) throws XMLStreamException {

		System.out.println("----resetPin----");
		
		element.build();
        element.detach();

        OMElement method = fac.createOMElement("resetPinResponse", omNs);
        
        try {
	        List fields = ServiceRequestFields.resetPinFields();
	        Iterator<OMElement> iterEl = element.getChildElements();
	        
	        String authVal = null, action = null;
	        String returnString = new String();
	        while (iterEl.hasNext()) {
	        	OMElement tmp = (OMElement)iterEl.next();
	        	if (fields.contains(tmp.getLocalName())) {
	        		//System.err.println("resetPin --- localName=["+tmp.getLocalName()+"] INSERTING=["+tmp.getText()+"]");
	        		if  (tmp.getLocalName().equalsIgnoreCase(ServiceRequestFields.newPin)) {
	        			returnString = tmp.getText();
	        		} else if (tmp.getLocalName().equalsIgnoreCase(ServiceRequestFields.authType)) {
	        			authVal = tmp.getText();
	        		} else if (tmp.getLocalName().equalsIgnoreCase(ServiceRequestFields.action)) {
	        			action = tmp.getText();
	        		} 
	        	} 
	        }
	        
	        /** check authentication */
	        ActionAuthCheck checker = new ActionAuthCheck(action, authVal);
	        if (! checker.isActionAllowed())
	        	throw new Exception("Permission denied");
	        
	        
	        OMFactory fac = OMAbstractFactory.getOMFactory();
	        OMElement value = fac.createOMElement("success", omNs);
	        value.addChild(fac.createOMText(value, returnString));
	        method.addChild(value);
        } catch (Exception ex) {
        	System.err.println("getAccountInfo - Exception =[" + ex.getMessage() + "]");
        	ex.printStackTrace();
        	OMElement error = fac.createOMElement("error", omNs);
        	error.addChild(fac.createOMText(error, ex.getMessage()));
    	    method.addChild(error);
        }
        
        return method;
	}
	
    
}