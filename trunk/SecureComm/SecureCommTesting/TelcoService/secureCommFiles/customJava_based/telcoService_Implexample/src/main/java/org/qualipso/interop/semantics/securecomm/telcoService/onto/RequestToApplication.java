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

import java.util.HashMap;
import java.util.LinkedHashMap;


/** This class represents an request that is made from ASM to the WS/application.
 * It holds the data that comprise the request. 
 * 
 * @author wp32
 */
public class RequestToApplication {
	
	/** these are the request fields */
	public static final String authType = "authType";
	public static final String action = "action";
	public static final String telephoneNumber = "telephoneNumber";
	public static final String username = "username";
	
	public static String startTimeFrame = "startTimeFrame";
	public static String endTimeFrame = "endTimeFrame";
	public static String newPin = "newPin";
	
	
	/**
	 *  Get the request fields as a <code>LinkedHashMap</code> where the 
	 *  name is the key and value = null;
	 * 
	 * @return:<code>LinkedHashMap</code>
	 */
	public static LinkedHashMap<String, String> getTargetElements() {
		LinkedHashMap<String, String> temp = new LinkedHashMap<String, String>();
		
		temp.put(authType, null);
		temp.put(action, null);
		temp.put(PrepareSourceOntology.entryPoint, null);
		temp.put(telephoneNumber, null);
		temp.put(username, null);
		
		return temp;
	}

	
	/** Get the payload fields as a <code>LinkedHashMap</code> where the 
	 *  name is the key and value = null; These will be transfered "as is"
	 *  to the WS/application. 
	 * 
	 * @return: <code>LinkedHashMap</code> of payload elements
	 */
	public static HashMap<String, String> getPayloadElements() {
		HashMap<String, String> temp = new HashMap<String, String>();
	
		temp.put(startTimeFrame, null);
		temp.put(endTimeFrame, null);
		temp.put(newPin, null);
		return temp;
	}
	
}
