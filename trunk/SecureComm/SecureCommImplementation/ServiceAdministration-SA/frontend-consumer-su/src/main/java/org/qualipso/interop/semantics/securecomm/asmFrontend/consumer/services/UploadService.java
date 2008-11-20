package org.qualipso.interop.semantics.securecomm.asmFrontend.consumer.services;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.qualipso.interop.semantics.securecomm.asmFrontend.consumer.util.*;
import org.qualipso.interop.semantics.securecomm.asmFrontend.consumer.output.HtmlElements;
import org.qualipso.interop.semantics.securecomm.asmFrontend.consumer.services.*;

/** Implements the form/actions needed to upload a new security mapping into 
 *  the ASM. 
 * 
 * @author wp32
 * 
 */
public class UploadService implements Constants, CommonActionInterface {
	/** html head to display. */
	private String head = "<title>" + OPERATION_UPLOAD_LABEL + "</title>";
	/** html body to display. */
	private String body = null;
	
	/** Constructor
	 * When "doUpload"=="true" means that the data filled form is submitted.
	 *      otherwise the success-upload method is called.
	 * 
	 * @param doUpload: String
	 * @throws Exception
	 */
	public UploadService(String doUpload) throws Exception {
		if (doUpload == null) {
			head += generateJS();
			body = generateFormBody();
		} else {
			System.out.println("NORMALLY Uploading A Service");
			body = generateBodyOnSuccess();
		}
	}
	
	/** When a new service has been successfully uploaded then this method
	 *  generates a 'success header' and also a list of the (now) available 
	 *  services by making a static call to ListServices.
	 * 
	 * @return HTML string
	 * @throws Exception
	 */
	private String generateBodyOnSuccess() throws Exception {
		StringBuffer temp = new StringBuffer(1024);
		temp.append("<h2>Service Uploaded Successfully</h2>");
		
		temp.append(ListServices.generateServiceListAsHTML());
		
		return temp.toString();
	}
	
	/** When a new service is being uploaded this method generates the html form
	 * fields in order to upload the necessary files.
	 * 
	 * @return: html string
	 */
	private String generateFormBody() {
		
		String asmHome = (String) System.getenv("ASM_HOME");
		String submitUrl = SERVICE_URL + OPERATION_NAME_ON_URL + "=" + OPERATION_UPLOAD_SERVICE;
		StringBuffer temp = new StringBuffer(1024);
		
		Set definedByAdmin = null;
		try {
			definedByAdmin = Util.getServicesDefinedByAdmin(asmHome);
		} catch (Exception ex) {
			
		}
		
		if (definedByAdmin == null || definedByAdmin.size() == 0) {
			temp.append("ASM Administrator has not defined any services");
		} else {
			temp.append("<h2>" + OPERATION_UPLOAD_LABEL + "</h2>");
			temp.append("<div>");
			temp.append("<form name=\"uploadForm\" action=\"" + submitUrl + "\" method=\"post\" enctype=\"multipart/form-data\">");
			temp.append("\n<div>");
			
			Iterator iter = definedByAdmin.iterator();
			temp.append("\n<p>\n<label for=\"" + SERVICE_NAME + "\">Choose Applicable Service</label>");
			temp.append("\n<select name=\"" + SERVICE_NAME + "\" id=\"" + SERVICE_NAME + "\" >");
			while (iter.hasNext()) {
				String servicename = (String)iter.next();
				temp.append("\n<option>" + servicename + "</option>");
			}
			temp.append("</select></p>");
		
			
			/** input processing options */
			temp.append("\n<p><h3>Input processing for semantic lifting</h3>");
			temp.append(HtmlElements.getRadioInputType(LIFTING_METHOD, XLST, "phaseIn(\""+LIFTING_CUSTOM_FILE+"\")"))
				.append("Based on XSL Transformation<br/>");
			temp.append(HtmlElements.getRadioInputType(LIFTING_METHOD, JAVA, "phaseOut(\""+LIFTING_CUSTOM_FILE+"\")"))
				.append("Based on Java code provided below<br/>");
			temp.append(HtmlElements.getFileInputTypeHidden(LIFTING_CUSTOM_FILE));
			temp.append("</p>");
			
			
			/** semantic bridging options */
			temp.append("\n<p><h3>Semantic Bridging</h3>");
			temp.append(HtmlElements.getParagraph(SOURCE_ONTOLOGY, SOURCE_ONTOLOGY_LABEL));
			temp.append(HtmlElements.getParagraph(BRIDGE_ONTOLOGY, BRIDGE_ONTOLOGY_LABEL));
			temp.append(HtmlElements.getParagraph(OTHER_ONTOLOGIES, OTHER_ONTOLOGIES_LABEL));
			temp.append(HtmlElements.getParagraph(TARGET_ONTOLOGY, TARGET_ONTOLOGY_LABEL));
			temp.append(HtmlElements.getRadioInputType(BRIDGING_METHOD, GENERIC))
				.append("Generic Bridging<br/>");
			temp.append(HtmlElements.getRadioInputType(BRIDGING_METHOD, JAVA))
				.append("Based on Java code provided below<br/>");
			temp.append("</p>");
			
			
			/** semantic grounding options */
			temp.append("\n<p><h3>Output processing for semantic grounding</h3>");
			temp.append(HtmlElements.getRadioInputType(GROUNDING_METHOD, XLST, "phaseIn(\""+GROUNDING_CUSTOM_FILE+"\")"))
				.append("Based on XSL Transformation<br/>");
			temp.append(HtmlElements.getRadioInputType(GROUNDING_METHOD, JAVA, "phaseOut(\""+GROUNDING_CUSTOM_FILE+"\")"))
				.append("Based on Java code provided below<br/>");
			temp.append(HtmlElements.getFileInputTypeHidden(GROUNDING_CUSTOM_FILE));
			temp.append("</p>");
			
			
			/** semantic grounding options */
			temp.append("<p><h3>Custom Java Code for bridging and processing</h3>");
			temp.append("\n<label for=\"" + CLASS_NAME +"\">" + CLASS_NAME_LABEL + "</label>");
			temp.append("\n<input type=\"text\"  name=\"" + CLASS_NAME + "\" id=\"" 
						+ CLASS_NAME + "\" value=\"\" onkeypress=\"return callEnterValue(event)\" >");
			temp.append("\n</p>");
			temp.append(HtmlElements.getParagraph(TRANSFORMATION_CODE, TRANSFORMATION_CODE_LABEL));
			temp.append(HtmlElements.getParagraph(OTHER_JARS, OTHER_JARS_LABEL));
			
			temp.append("\n<div>");
			temp.append("\n<input type=\"button\" id=\"btn\" value=\"submit\" onclick='validate()' />");
			temp.append("\n</div>");
			temp.append("</div>");
			temp.append("\n</form>");
		}
		return temp.toString();
	}
	
	/** Generate the validation JavaScript.
	 * 
	 * @return
	 */
	private String generateJS() {
		StringBuffer js = new StringBuffer(1024);
		js.append("<script type=\"text/javascript\">\n");
				
		js.append("\tfunction phaseIn(id) {");
		js.append("\t\tdocument.getElementById(id).style.visibility='visible';");
		js.append("\t}\n\n");
		js.append("\tfunction phaseOut(id) {");
		js.append("\t\tdocument.getElementById(id).style.visibility='hidden';");	
		js.append("\t}\n\n");
		
		js.append("function getCheckedValue(radioObj) {\n");
		js.append("if(!radioObj)\n");
		js.append("return \"\";\n");
		js.append("var radioLength = radioObj.length;\n");
		js.append("if(radioLength == undefined)\n");
		js.append("if(radioObj.checked)\n");
		js.append("return radioObj.value;\n");
		js.append("else\n");
		js.append("return \"\";\n");
		js.append("for(var i = 0; i < radioLength; i++) {\n");
		js.append("if(radioObj[i].checked) {\n");
		js.append("return radioObj[i].value;\n");
		js.append("}\n");
		js.append("}\n");
		js.append("return \"\";\n");
		js.append("}\n");
				
		js.append("function validate(){\n");
		js.append("var SourceOntology = document.getElementById('" + SOURCE_ONTOLOGY + "');\n");
		js.append("var TargetOntology = document.getElementById('" + TARGET_ONTOLOGY + "');\n");
		js.append("var BridgeOntology = document.getElementById('" + BRIDGE_ONTOLOGY + "');\n");
		js.append("var TransformationCode = document.getElementById('" + TRANSFORMATION_CODE + "');\n");
		js.append("var Collateralfiles = document.getElementById('" + COLLATERAL_FILES + "');\n");
		js.append("var ServiceName = document.getElementById('" + SERVICE_NAME + "');\n");
		js.append("var ClassName = document.getElementById('" + CLASS_NAME + "');\n");
		js.append("var LiftingMethod=document.uploadForm."+LIFTING_METHOD+";\n");
		js.append("var BridgingMethod=document.uploadForm."+BRIDGING_METHOD+";\n");
		js.append("var GroundingMethod=document.uploadForm."+GROUNDING_METHOD+";\n");
		js.append("var MappingRulesCode = document.getElementById('" + OTHER_JARS + "');\n");
		js.append("var MappingRulesOntology = document.getElementById('" + OTHER_ONTOLOGIES + "');\n");
		js.append("var submit=false;\n");
		
		js.append("var needCustomJava=false;\n");
		js.append("if (getCheckedValue(LiftingMethod)=='"+JAVA+"' || getCheckedValue(BridgingMethod)=='"+JAVA+"' || getCheckedValue(GroundingMethod)=='"+JAVA+"')\n");
		js.append("\tneedCustomJava=true;\n");
		
		js.append("if(ServiceName != null && ServiceName.value != ''){\n");
		js.append("\tsubmit = true;\n");
		js.append("}\n");
		js.append("else{\n");
		js.append("\talert('Enter a valid Service Name');\n");
		js.append("\tsubmit = false;\n}\n");

		js.append("if (submit==true) {\nif (getCheckedValue(LiftingMethod)!='') {\n");
		js.append("\tsubmit=true;\n");
		js.append("}\n");
		js.append("else {\n");
		js.append("\talert('No selection for semantic lifting was given');\n");
		js.append("\tsubmit=false;\n");
		js.append("}\n}\n");
		
		js.append("\nif(submit==true && checkValidFileName(SourceOntology,'.owl','Upload a valid Source Ontology file')){\n");
		js.append("submit = true;\n}\nelse{\nsubmit = false;\n}");
		
		js.append("\nif(submit==true && checkValidFileName(TargetOntology,'.owl','Upload a valid Target Ontology file')){");
		js.append("submit = true;\n}\nelse{\nsubmit = false;\n}");
		
		js.append("\nif(submit==true && checkValidFileName(BridgeOntology,'.owl','Upload a valid Ontology Mapping file')){\n");
		js.append("submit = true;\n}\nelse{\nsubmit = false;\n}");
		
		js.append("\nif(submit==true && needCustomJava && checkValidFileName(TransformationCode,'.jar','Upload a valid Transformation Code file')){\n");			
		js.append("submit = true;\n}\nelse{\nif (needCustomJava) submit = false;\n}\n");
		
		js.append("if (submit==true) {\nif (getCheckedValue(BridgingMethod)!='') {\n");
		js.append("\tsubmit=true;\n");
		js.append("}\n");
		js.append("else {\n");
		js.append("\talert('No selection for bridging method was given');\n");
		js.append("\tsubmit=false;\n");
		js.append("}\n}\n");

		js.append("if (submit==true) {\nif (getCheckedValue(GroundingMethod)!='') {\n");
		js.append("\tsubmit=true;\n");
		js.append("}\n");
		js.append("else {\n");
		js.append("\talert('No selection for grounding method was given');\n");
		js.append("\tsubmit=false;\n");
		js.append("}\n}\n");

		js.append("\nif(submit==true){");
		js.append("if(ClassName != null && ClassName.value != '' && needCustomJava){\n");
		js.append("		submit = true;\n");
		js.append("	}\n");
		js.append("	else{\n");
		js.append("if (needCustomJava) {\n");
		js.append("alert('Enter a valid Class Name');\n");
		js.append("	submit = false;\n}\n}\n}\n");
		
		js.append("var hasUploadedRules = false;\n");
		// the mapping-rules are not mandatory
		js.append("\n\nif(submit == true) {");
		js.append("\nif (MappingRulesCode.value == '') {");
		js.append("\nhasUploadedRules = false;");
		js.append("\n} else if (checkValidFileName(MappingRulesOntology, '.owl', 'Upload a valid rule ontology file')) {");
		js.append("\nhasUploadedRules = true;");
		js.append("\n} else {");
		js.append("\nsubmit = false;");
		js.append("\n}");
		js.append("\n}");
		js.append("\nif (submit == true)  {");
		js.append("\nif (hasUploadedRules == true) {");
		js.append("\nif (checkValidFileName(MappingRulesCode,'.jar','Upload a valid rule Transformation Code file')) {");
		js.append("\nsubmit = true;");
		js.append("\n} else {");
		js.append("\n alert('No valid rule ontology has been defined');");
		js.append("\nsubmit = false;");
		js.append("\n}");
		js.append("\n}");
		js.append("\n}\n");
		
		js.append("if(submit==true) {\n");
		js.append("document.uploadForm.action = document.uploadForm.action + '&submit=true';\n");
		js.append("	document.uploadForm.submit();\n");
		js.append("}\n");
		js.append("}\n");
	
		js.append("function checkValidFileName(field, ext, Message){\n");
		js.append("if(field != null && field.value != ''){\n");
		js.append("if(checkFileName(field.value,ext)){\n");
		js.append("return true;\n");
		js.append("}\n");
		js.append("else{\n");
		js.append("alert(Message+' (*'+ext+')');\n");
		js.append("return false;\n");
		js.append("}\n");		
		js.append("}\n");
		js.append("else{\n");
		js.append("alert(Message+' (*'+ext+')');\n");
		js.append("return false;\n");
		js.append("}\n");
		js.append("}\n");
	
		js.append("function checkFileName(str, fileTypeStr){\n");
		js.append("var len = str.length\n");
		js.append("var position = eval(len - 4)\n");
		js.append("var fileType = str.substring(position, len)\n");
		
		js.append("if(fileType == fileTypeStr) {\n");
		js.append("return true;\n");
		js.append("}\n");
		js.append("else {\n");
		js.append("return false;\n");
		js.append("}\n");
		js.append("}\n");
		
		js.append("function callEnterValue(e){\n");
		js.append("	var uniCode=e.charCode? e.charCode : e.keyCode\n");
		js.append("	if(uniCode==32){\n");
		js.append("		return false;\n");
		js.append("	}\n");
		js.append("return true;\n");
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
