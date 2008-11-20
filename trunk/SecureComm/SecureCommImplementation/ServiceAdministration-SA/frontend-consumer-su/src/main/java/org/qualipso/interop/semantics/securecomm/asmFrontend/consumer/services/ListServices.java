package org.qualipso.interop.semantics.securecomm.asmFrontend.consumer.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import org.qualipso.interop.semantics.securecomm.asmFrontend.consumer.util.Util;

/** Displays all available services (security mappings) that are deployed
 *  in the ASM. This is done by reading the property file that is updated 
 *  everytime a service is inserted/deleted.
 *  
 *  Displays this information as html content.
 *  
 * @author wp32
 */
public class ListServices implements Constants, CommonActionInterface {
	
	public  final String head = "<title>" + OPERATION_LIST_LABEL + "</title>";
	private String body = null;
	
	/** constructor */
	public ListServices() throws Exception {
		body = generateServiceListAsHTML();
	}
	
	/** Displays the information read from <code>loadAvailableServices</code> as
	 *  formatted html.
	 * 
	 * @return: html content as String
	 * @throws Exception
	 */
	public static String generateServiceListAsHTML() throws Exception {
		ArrayList availMappings = loadAvailableSecurityMappings();
		String url = SERVICE_URL + OPERATION_NAME_ON_URL + "=" + OPERATION_SERVICE_INFO;
		
		StringBuffer tmp = new StringBuffer(512);
		tmp.append("<h2>" + OPERATION_LIST_LABEL + "</h2>");
		Iterator i=availMappings.iterator();
		while (i.hasNext()) {
			String serviceName=(String) i.next();
			tmp.append("<p><a href=\"" + url + "&" + SERVICE_NAME_ON_URL + "=" + serviceName + "\">" + serviceName + "</a></p>");
		}
		tmp.append("</div>");
		return tmp.toString();
	}
	
	
	/** Retrieves the available security mappings by checking the existance of service description file
	 * for each available service, otherwise throws an error. 
	 * 
	 * @return: <code>ArrayList</code>
	 * @throws Exception
	 */
	public static ArrayList loadAvailableSecurityMappings() throws Exception {
			
	    String asmHome = (String) System.getenv("ASM_HOME");
	    Set availableServices=null;
	    ArrayList availableSecurityMappings=new ArrayList();
	    
	    try {
	    	availableServices=Util.getServicesDefinedByAdmin(asmHome);
	    	
	    	if (availableServices!=null && availableServices.size()>0) {
	    		Iterator iter=availableServices.iterator();
	    		while (iter.hasNext()) {
	    			String serviceName=(String) iter.next();
	    			String serviceDesc=asmHome+File.separator+PATH_TO_SERVICES_DIR+serviceName+File.separator+SERVICE_DESCRIPTION_FILE;
	    			File serviceDescFile=new File(serviceDesc);
	    			if (serviceDescFile.exists()) {
	    				availableSecurityMappings.add(serviceName);
	    			}
	    		}
	    	}    	
	    } 
	    catch (FileNotFoundException fnfex) {
	    	throw new Exception("Currently there are no available services");
	    }
	        
	    return availableSecurityMappings;
	}

	/** body getter */
	public String getBody() {
		return body;
	}

	/** head getter */
	public String getHead() {
		return head;
	}
	
}