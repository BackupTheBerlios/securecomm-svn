package org.qualipso.interop.semantics.securecomm.asmFrontend.consumer.services;

/**
 * Holds common constants.
 * 
 * @author wp32
 *
 */
public interface Constants {
	
	/** the ASM service-assembly that acts as a container for the seccurity mappings. */
	public static final String ASM_SERVICE_ASSEMBLY_NAME = "securecomm-sa";
	
	/** the ASM service-unit type within the ASM_SERVICE_ASSEMBLY_NAME */
	public static final String ASM_SERVICE_UNIT_TYPE = "servicemix-bean";
	
	/** the ASM service-unit that acts as a container for the seccurity mappings. */
	public static final String ASM_SERVICE_UNIT_NAME = "securecomm-wssec-bean";
	
	/** listServices operation name. */
	public static final String OPERATION_LIST_SERVICES = "listServices";
	/** uploadService operation name. */
	public static final String OPERATION_UPLOAD_SERVICE = "uploadService";
	/** deleteService operation name. */
	public static final String OPERATION_DELETE_SERVICE = "deleteService";
	/** infoService operation name. */
	public static final String OPERATION_SERVICE_INFO = "infoService";
	
	/** directory within the ASM_HOME containing the common property files. */
	public static final String PATH_TO_COMMON_PROPERTIES = "Files/Common/Properties/";
	
	/** directory (within ASM_HOME) under whom the service directories reside. */
	public static final String PATH_TO_SERVICES_DIR = "Files/";
	
	/** property file holding service names and endpoints as defined by the ASM administrator. */
	public static final String SERVICE_ENDPOINTS = "service_endpoints.properties";
	
	/** prop file holding service names and classes that implement them. */
	public static final String SERVICE_DESCRIPTION_FILE = "service_description.properties";
	
	/** frontend url. */
	public static final String SERVICE_URL = "/frontend/?";
	
	/** which operation is requested from the URL. */
    public static final String OPERATION_NAME_ON_URL = "operation";
    
    /** which service is requested from the URL. */
    public static final String SERVICE_NAME_ON_URL = "service";
    
    /** what error occurred during processing. */
    public static final String ERROR_ON_PROCESS = "errorOnProcess";
	
    /**submit attr on url. */
    public static final String SUBMIT_ON_URL = "submit";
    
    /** keys */
    public static final String SOURCE_ONTOLOGY = "sourceOntology";
	public static final String TARGET_ONTOLOGY = "targetOntology";
	public static final String BRIDGE_ONTOLOGY = "bridgeOntology";
	public static final String TRANSFORMATION_CODE = "transformationCode";
	public static final String COLLATERAL_FILES = "collateralfiles";
	public static final String OTHER_JARS = "mappingRulesCode";
	public static final String SERVICE_NAME = "serviceName";
	public static final String CLASS_NAME = "className";
	public static final String OTHER_ONTOLOGIES = "mappingRulesOntology";
	public static final String LIFTING_METHOD = "liftingMethod";
	public static final String BRIDGING_METHOD = "bridgeMethod";
	public static final String GROUNDING_METHOD = "groundingMethod";
	public static final String LIFTING_CUSTOM_FILE = "liftingFile";
	public static final String GROUNDING_CUSTOM_FILE = "groundingFile";
	
	/** labels */
	public static final String SOURCE_ONTOLOGY_LABEL = "Source Ontology";
	public static final String TARGET_ONTOLOGY_LABEL = "Target Ontology";
	public static final String BRIDGE_ONTOLOGY_LABEL = "Bridge Ontology";
	public static final String TRANSFORMATION_CODE_LABEL = "JAR file implementing the class";
	public static final String COLLATERAL_FILES_LABEL = "collateralfiles";
	public static final String OTHER_JARS_LABEL = "Other Jars";
	public static final String SERVICE_NAME_LABEL = "Service Name";
	public static final String CLASS_NAME_LABEL = "Java Class implementing Processing and Bridging";
	public static final String OTHER_ONTOLOGIES_LABEL = "Other Ontology(ies)";
	public static final String LIFTING_METHOD_LABEL = "Lifting Method";
	public static final String BRIDGING_METHOD_LABEL = "Bridging Method";
	public static final String GROUNDING_METHOD_LABEL = "Grounding Method";
	public static final String LIFTING_CUSTOM_FILE_LABEL = "Lifting File";
	public static final String GROUNDING_CUSTOM_FILE_LABEL = "Grounding File";
	
	/**radio button values */
	public static final String XLST = "xslt";
	public static final String JAVA = "java";
	public static final String GENERIC = "generic";
	
	
	/** action labels, these will be displayed in the output html. */
	public static final String OPERATION_LIST_LABEL = "List Security Mappings";
	public static final String OPERATION_UPLOAD_LABEL = "Deploy Security Mapping";
	public static final String OPERATION_DELETE_LABEL = "Delete Security Mapping";
	public static final String OPERATION_SERVICE_INFO_LABEL = "Mapping Information";
    
}