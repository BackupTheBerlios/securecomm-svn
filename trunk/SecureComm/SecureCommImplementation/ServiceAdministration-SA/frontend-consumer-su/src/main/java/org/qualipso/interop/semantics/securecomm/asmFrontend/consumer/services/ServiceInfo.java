package org.qualipso.interop.semantics.securecomm.asmFrontend.consumer.services;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.qualipso.interop.semantics.securecomm.asmFrontend.consumer.output.HtmlElements;
import org.qualipso.interop.semantics.securecomm.asmFrontend.consumer.util.Util;

/** Displays information about a security mapping as they are saved into
 *  the service_description.properties file from the "uploadService" action.
 *  
 * @author wp32
 *
 */
public class ServiceInfo implements Constants, CommonActionInterface {
	
	private static final String head = "<title>" + OPERATION_SERVICE_INFO_LABEL + "</title>";
	private String body;
	
	/** constructor. */
	public ServiceInfo(String servicename) throws Exception {
		loadServiceInfo(servicename);
	}
	
	/** simple wrapper. */
	void loadServiceInfo(String servicename) throws Exception {
		Properties serviceProps = loadServicePropertiesFromFile(servicename);
		generateBody(serviceProps);
	}
	
	/** Displays in html the service properties.
	 *  the methods from Utils are called simply to present first the 
	 *  "name" etc.
	 * 
	 * @param properties: service <code>Properties</code>
	 */
	private void generateBody(Properties properties) {
		
		StringBuffer temp = new StringBuffer(512);
		temp.append("\n<h2>" + OPERATION_SERVICE_INFO_LABEL + "</h2>");
		
		temp.append("\n<p><strong>" + SERVICE_NAME_LABEL + "</strong>: " + properties.getProperty(SERVICE_NAME) + "</p>");
		
		/** semantic lifting option listing */
		temp.append("\n<p><h3>Semantic lifting</h3>");
		temp.append("\n<p><strong>" + LIFTING_METHOD_LABEL + "</strong>: " + properties.getProperty(LIFTING_METHOD) + "</p>");
		if (properties.getProperty(LIFTING_CUSTOM_FILE) != null)
			temp.append("\n<p><strong>" + LIFTING_CUSTOM_FILE_LABEL + "</strong>: " + properties.getProperty(LIFTING_CUSTOM_FILE) + "</p>");
		
		/** semantic bridging option listing */
		temp.append("\n<p><h3>Semantic Bridging</h3>");
		temp.append("\n<p><strong>" + SOURCE_ONTOLOGY_LABEL + "</strong>: " + properties.getProperty(SOURCE_ONTOLOGY) + "</p>");
		temp.append("\n<p><strong>" + TARGET_ONTOLOGY_LABEL + "</strong>: " + properties.getProperty(TARGET_ONTOLOGY) + "</p>");
		temp.append("\n<p><strong>" + BRIDGE_ONTOLOGY_LABEL + "</strong>: " + properties.getProperty(BRIDGE_ONTOLOGY) + "</p>");
		if (properties.getProperty(OTHER_ONTOLOGIES) != null)
			temp.append("\n<p><strong>" + OTHER_ONTOLOGIES_LABEL + "</strong>: " + properties.getProperty(OTHER_ONTOLOGIES) + "</p>");
		temp.append("\n<p><strong>" + BRIDGING_METHOD + "</strong>: " + properties.getProperty(BRIDGING_METHOD) + "</p>");
		
		/** semantic grounding option listing */
		temp.append("\n<p><h3>Semantic grounding</h3>");
		temp.append("\n<p><strong>" + GROUNDING_METHOD_LABEL + "</strong>: " + properties.getProperty(GROUNDING_METHOD) + "</p>");
		if (properties.getProperty(GROUNDING_CUSTOM_FILE) != null)
			temp.append("\n<p><strong>" + GROUNDING_CUSTOM_FILE_LABEL + "</strong>: " + properties.getProperty(GROUNDING_CUSTOM_FILE) + "</p>");
		
		/** Custom Code option listing */
		temp.append("<p><h3>Custom Option</h3>");
		if (properties.getProperty(CLASS_NAME) != null)
			temp.append("\n<p><strong>" + CLASS_NAME_LABEL + "</strong>: " + properties.getProperty(CLASS_NAME) + "</p>");
		if (properties.getProperty(TRANSFORMATION_CODE) != null)
			temp.append("\n<p><strong>" + TRANSFORMATION_CODE_LABEL + "</strong>: " + properties.getProperty(TRANSFORMATION_CODE) + "</p>");
		if (properties.getProperty(OTHER_JARS) != null)
			temp.append("\n<p><strong>" + OTHER_JARS_LABEL + "</strong>: " + properties.getProperty(OTHER_JARS) + "</p>");
	
		
		body = temp.toString();
	}
	 
	/** Loads the service_description.properties file into a <code>Properties</code> object.
	 * 
	 * @param servicename: servicename to fetch properties for
	 * @return: <code>Properties</code> or throws Exception
	 * @throws Exception
	 */
	private static Properties loadServicePropertiesFromFile(String servicename) throws Exception {

		String asmHome = (String) System.getenv("ASM_HOME");
		String fileName = Util.getServiceDescriptionFile(asmHome, servicename);
		Properties properties = new Properties();
		File file = new File(fileName);
		FileInputStream fis = new FileInputStream(file);
		properties.load(fis);
		fis.close();
		return properties;
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
