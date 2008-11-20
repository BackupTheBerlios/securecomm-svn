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
package org.qualipso.interop.semantics.securecomm.testService.handler;

import java.io.File;
import java.io.FileInputStream;

/** Sample Handler Class for some repository action
 * 
 * @author wp31
 *
 */
public class RepositoryHandler {
    
    public static String getFileAsString(String filename) {
        try {
            File currentDirectory = new File("." );
            String currDir = currentDirectory.getCanonicalPath();
            //System.out.println("OUT RepositoryHandler @@@ getFileAsString [" + currDir +"]");
            FileInputStream fis = new FileInputStream(currDir + File.separator + filename);
            byte[] content = new byte[fis.available()];
            fis.read(content);
            
            return new String(content);
        } catch (Exception ex) {
            System.err.println("RepositoryHandler *** getFileAsString *** EXCEPTION *** " + ex.getMessage());
            return "FILE_NOT_FOUND";
        }
    }
    
}