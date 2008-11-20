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

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/** A simple Property Reader class. 
 * 
 * @author wp32
 *
 */
public class PropertyReader {
	
	Properties properties = null;
	
	/** Default constructor.
	 *  Also, will throw an exception if the property file exists but is empty.
	 * 
	 * @param propertyFileName: property File to read values from
	 * @throws Exception: <code>FileNotFoundException</code>, <code>IOException</code>, <code>Exception</code>
	 */
	public PropertyReader(String propertyFileName) throws Exception {
		
		File file = new File(propertyFileName);
		FileInputStream fis = new FileInputStream(file);
		properties = new Properties();
		properties.load(fis);
		fis.close();
		if (properties.isEmpty()) {
			System.err.println("NO PROPERTY FILE");
			throw new Exception("NO PROPERTY FILE");
		}
	}
	
	/** Simple Property Getter.
	 * 
	 * @param key: Key to get value for
	 * @return: the value or throw exception
	 * @throws Exception: <code>Exception</code> with the message as below
	 * @return: value of property
	 */
	public String getProperty(String key) throws Exception {
		String value = this.properties.getProperty(key);
		if (value == null) { 
			throw new Exception("CANNOT FIND PROPERTY KEY");
		}
		
		return value;
	}
	
	
}