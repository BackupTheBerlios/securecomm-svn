package org.qualipso.interop.semantics.securecomm.asmFrontend.consumer.services;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

/** Responsible for deleting a security mapping from the ASM.
 *  If the sec-map is deleted successfully a body-content is
 *  generated; otherwise an error-response. 
 *  
 *  Deleting a sec-mapp means deleting the its folder within the ASM
 *  and its JAR from the location where the securecomm-sa 
 *  container is deployed.
 * 
 * @author wp32
 *
 */
public class DeleteService implements Constants, CommonActionInterface {
	/** html head to display. */
	private String head = "<title>" + OPERATION_DELETE_LABEL +"</title>";
	/** html body to displayed. */
	private String body = null;
	
	/** 
	 * Execute the delete service action.
	 */
	public DeleteService(String submit) throws Exception {
		if (submit == null) {
			head += generateJS();
			body = generateBody();
		} else {
			System.out.println("DELETING A SERVICE");
			body = generateBodyOnSuccess();
		}
		
	}
	
	/** Display a success message and also print the 
	 *  remaining available security mapppings.
	 * 
	 * @return: html content as string.
	 * @throws Exception
	 */
	private String generateBodyOnSuccess() throws Exception {
		StringBuffer temp = new StringBuffer(1024);
		temp.append("<h3>Deleted Successfully</h3>");
		
		temp.append(ListServices.generateServiceListAsHTML());
		
		return temp.toString();
	}
	
	/** Generates the delete security mapping (service) form.
	 * 
	 * @return: html content as string.
	 * @throws Exception
	 */
	public String generateBody() throws Exception {  
		
		String submitUrl = SERVICE_URL + OPERATION_NAME_ON_URL + "=" + OPERATION_DELETE_SERVICE;
		ArrayList allSecMaps = ListServices.loadAvailableSecurityMappings();
		StringBuffer temp = new StringBuffer();
		
		if (allSecMaps.size() > 0) {
			temp.append("<h2>" + OPERATION_DELETE_LABEL + "</h2>");
			temp.append("<div id=\"delete_form\">");
			
			temp.append("\n<form name=\"deleteServiceForm\" action=\"" + submitUrl + "\" method=\"post\" >");
			temp.append("<div class=\"delete_service\">");
			temp.append("\n<p>");
			temp.append("\n<label for=\"servicetodelete=\" id=\"label_text\">Choose Applicable Service</label>");
			temp.append("\n<select id=\"servicetodelete\" name=\"servicetodelete\">");
			Iterator iter = allSecMaps.iterator();
			while (iter.hasNext()) {
				String servicename=(String) iter.next();
				temp.append("\n<option>" + servicename + "</option>");
			}
			temp.append("</select>");
			temp.append("\n</div>");
			temp.append("\n<div>");
			temp.append("\n<input type=\"button\" value=\"submit\" id=\"btn\" onclick='onSubmit()'></input>");
			temp.append("\n</div>");
			temp.append("\n</div>");
			temp.append("</form>");
		} else {
			temp.append("<p>There are no services to delete</p>");
		}
		return temp.toString();
	}
	
	/** generates a helper javascript function.
	 * 
	 * @return: js content as html string.
	 */
	private String generateJS() {
		StringBuffer js = new StringBuffer(1024);
		js.append("<script type=\"text/javascript\">\n");
		js.append("function onSubmit(){\n");
		
		js.append("document.deleteServiceForm.action = document.deleteServiceForm.action + '&submit=true';\n");
		js.append("	document.deleteServiceForm.submit();\n");
		js.append("}\n");
		
		js.append("</script>");
		return js.toString();
	}

	/** head getter. */
	public String getHead() {
		return head;
	}

	/** body getter. */
	public String getBody() {
		return body;
	}
	
}
