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
package org.qualipso.interop.semantics.securecomm.testService.authenticators;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

/** Sample Authenticator Class that can be extended very easily.
 * 
 * @author wp31
 *
 */
public class TestAuthenticator {
    
    private String user = null;
    private String password = null;
    private String domain = null;
    
    private final static String PROP_FILE = "authenticator.properties";
    private final static String USER_DOMAIN_JOINER = "@";
    
    public TestAuthenticator(String u, String p, String d) {
        user = u;
        password = p;
        domain =d;
    }
    
    /** Authenticate Method Sample
     *  In a property file there are pairs of the style: user@domain=password. 
     *  Try to find the object's values there.
     * 
     * @return: true if success false otherwise
     */
    public boolean authenticate () {
        try {
            Properties props = new Properties();
            FileInputStream fis = new FileInputStream(PROP_FILE);
            props.load(fis);
            String userDomain = user + USER_DOMAIN_JOINER + domain;
            //System.err.println("TestAuthenticator --- authenticate userDomain=["+userDomain+"]");
            String passFromAuth = props.getProperty(userDomain);
            if (passFromAuth == null) {
                System.err.println("User not found");
            } else {
                if (passFromAuth.equals(password)) {
                    return true;
                } else {
                    System.err.println("Password no match");
                    return false;
                }
            }
        } catch (Exception fex) {
            fex.printStackTrace();
            System.err.println("Auth currently unavailable\n");
        } 
        
        return false;
    }
    
    
    
    
    
}