package org.qualipso.interop.semantics.securecomm.components;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.jbi.messaging.MessageExchange;
import org.qualipso.interop.semantics.securecomm.components.bridger.parser.GenericBridger;
import org.qualipso.interop.semantics.securecomm.utils.PropertyReader;
import org.qualipso.interop.semantics.securecomm.utils.Constants;
import org.qualipso.interop.semantics.securecomm.asm.CredentialsTranslator;

/**
 * Implements all the semantic bridging phases. 
 * 
 * @author wp32
 *
 */
public class DefaultComponentImplFactory extends ComponentImpl implements Constants {

	private PropertyReader sProps = null;
	
	private String serviceWorkingDir = null;
	private String instanceDir = null;
	private String stylesheetsDir = null;
	
	private String sourceOntology = null;
	private String targetOntology = null;
	private String bridgeOntology = null;
	private String bridgeRepository = null;
	private List<String> otherOntologies = null;
	
	private String sourceInstance = null;
	
	private String bridgingResultFilepath = null;
	
	public DefaultComponentImplFactory (String propertyfile) throws Exception {
		sProps  = new PropertyReader(propertyfile);
	}
	
	
	/** Initializes the class members. It creates the instance runtime-directory of 
	 * the mapping, set the ontologies and XSLT stylesheets directory and the file paths
	 * to the ontology (source, bridge etc.) files. 
	 * Finally, it copies the necessary ontology files to the instance-directory.
	 * 
	 */
	public void init() throws Exception {
		
		String serviceName = sProps.getProperty(SERVICE_NAME);
		serviceWorkingDir = CredentialsTranslator.RUNTIME_DIR + File.separator + serviceName + File.separator;
        
        instanceDir = CredentialsTranslator.ONTOLOGY_FOLDER + "Results_" 
        					+ String.valueOf(System.currentTimeMillis()) + File.separator;
        
        stylesheetsDir = serviceWorkingDir + CredentialsTranslator.XSLT_FOLDER + File.separator;
		(new File(serviceWorkingDir + instanceDir)).mkdirs();
		
		// set file paths accordingly 
		String sourceOntFromProp = sProps.getProperty(SOURCE_ONTOLOGY);
		String targetOntFromProp = sProps.getProperty(TARGET_ONTOLOGY);
		String bridgeOntFromProp = sProps.getProperty(BRIDGE_ONTOLOGY);
		String bridgeOntRepository = bridgeOntFromProp.substring(0, bridgeOntFromProp.lastIndexOf(".")) 
									+ "." + "repository";
		
		/** set class variables that will be used from here on */
		sourceInstance = serviceWorkingDir + instanceDir + sourceOntFromProp;
		sourceOntology = serviceWorkingDir + CredentialsTranslator.ONTOLOGY_FOLDER + sourceOntFromProp;
		targetOntology = serviceWorkingDir + CredentialsTranslator.ONTOLOGY_FOLDER + targetOntFromProp;
		bridgeOntology = serviceWorkingDir + instanceDir + bridgeOntFromProp;
		bridgeRepository = serviceWorkingDir + instanceDir + bridgeOntRepository;
		
		// this will be used within the xslt document() and must be fullpath. 
		File dir = new File(".");
		bridgingResultFilepath = dir.getCanonicalPath() + File.separator 
			+ serviceWorkingDir + instanceDir + "resultOfBridging.owl";
		
		String otherOntFromProp=null;
		try {
			otherOntFromProp = sProps.getProperty(OTHER_ONTOLOGIES);
		}
		catch (Exception e) {
			// Property not found
		}
		
		if (otherOntFromProp != null) {
			String[] onts = otherOntFromProp.split(",");
			otherOntologies = new ArrayList<String>(onts.length);
			for (int i=0; i<onts.length; i++) {
				otherOntologies.add(serviceWorkingDir + CredentialsTranslator.ONTOLOGY_FOLDER + onts[i].trim());
			}
		}
		
		// copy files to the instance directory 
		CredentialsTranslator.copyFile(serviceWorkingDir + CredentialsTranslator.ONTOLOGY_FOLDER + bridgeOntFromProp, 
				serviceWorkingDir + instanceDir + bridgeOntFromProp);
	}
	
		
	/** The Semantic-Lifting method.
	 * Check the service-description file and see which method has the user
	 * chosen for the semantic lifting. Then fetch method-related supplementary
	 * info from the property file and hand over control to the appropriate
	 * component. 
	 * 
	 */
	public void semanticLifting(MessageExchange msgExchange) throws Exception {
		
		SemanticLifting cimp = null;
		
		/** decide here which class_method to call */
		String liftingMethod  = sProps.getProperty(LIFTING_METHOD);
		if (liftingMethod.equalsIgnoreCase(XLST)) {
			String customLiftingFile = stylesheetsDir + sProps.getProperty(LIFTING_CUSTOM_FILE);
			// GROUNDING_CUSTOM_FILE may be null; don't care. but add the path only if not null
			print("DefaultComponentImplFactory === semanticLifting -> XSLT");
			String customGroundingFile = null;
			try {
				customGroundingFile = sProps.getProperty(LIFTING_CUSTOM_FILE);
				customGroundingFile = stylesheetsDir + customGroundingFile;
			} catch (Exception ex) {
				
			}
			
			cimp = new XSLTComponent(customLiftingFile, customLiftingFile);
		} else if (liftingMethod.equalsIgnoreCase(JAVA)) {
			String customJavaClass = sProps.getProperty(CLASS_NAME);
			
			cimp = new JavaComponent(customJavaClass);
			print("DefaultComponentImplFactory === semanticLifting -> JAVA");
		} else {
			throw new Exception("Not supported");
		}
		
		cimp.semanticLifting(msgExchange, sourceOntology, sourceInstance);
		
		/** set the source_Ontology path to the created source+instance */
		sourceOntology = sourceInstance;
	}
	
	
	/** The Semantic-Bridging method.
	 * Check the service-description file and see which method has the user
	 * chosen for the semantic bridging. Then fetch method-related supplementary
	 * info from the property file and hand over control to the appropriate
	 * component. 
	 * 
	 */
	public void semanticBridging() throws Exception {
		SemanticBridging cimp = null;
		
		String bridgingMethod = sProps.getProperty(BRIDGING_METHOD);
		if (bridgingMethod.equalsIgnoreCase(GENERIC)) {
			cimp = new GenericBridger();
		} else if (bridgingMethod.equalsIgnoreCase(JAVA)) {
			String customJavaClass = sProps.getProperty(CLASS_NAME);
			cimp = new JavaComponent(customJavaClass);
		} else {
			throw new Exception("Not supported");
		}
		
		/*print("DefaultComponentImplFactory === semanticBridging -> sourceOntology=[" + sourceOntology + "]");
		print("DefaultComponentImplFactory === semanticBridging -> targetOntology=[" + targetOntology + "]");
		print("DefaultComponentImplFactory === semanticBridging -> bridgeOntology=[" + bridgeOntology + "]");
		print("DefaultComponentImplFactory === semanticBridging -> bridgeRepository=[" + bridgeRepository + "]");*/
		
		cimp.semanticBridging(sourceOntology, targetOntology, bridgeOntology, bridgeRepository, otherOntologies, bridgingResultFilepath);
	}
	
	/** The Semantic-Grounding method.
	 * Check the service-description file and see which method has the user
	 * chosen for the semantic grounding. Then fetch method-related supplementary
	 * info from the property file and hand over control to the appropriate
	 * component. 
	 * 
	 */
	public MessageExchange semanticGrounding(MessageExchange msgExchange) throws Exception {
		SemanticGrounding cimp = null;
		
		String groundingMethod = sProps.getProperty(GROUNDING_METHOD);
		if (groundingMethod.equalsIgnoreCase(XLST)) {
			//LIFTING_CUSTOM_FILE may be null, but we need only the grounding
			String customLiftingFile = null;
			String customGroundingFile = stylesheetsDir + sProps.getProperty(GROUNDING_CUSTOM_FILE);
			try {
				customLiftingFile = sProps.getProperty(GROUNDING_CUSTOM_FILE);
				customLiftingFile = stylesheetsDir + customGroundingFile;
			} catch (Exception ex) {
				
			}
			cimp = new XSLTComponent(customLiftingFile, customGroundingFile);
		} else if (groundingMethod.equalsIgnoreCase(JAVA)) {
			String customJavaClass = sProps.getProperty(CLASS_NAME);
			
			cimp = new JavaComponent(customJavaClass);
    		
		} else {
			throw new Exception("Not supported");
		}
		
		return cimp.semanticGrounding(msgExchange, bridgingResultFilepath);
	}
	
	/** A helper printer method */
	public static void print(Object o) {
		System.out.println(o);
	}
	
}
