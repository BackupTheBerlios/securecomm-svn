package org.qualipso.interop.semantics.securecomm.utils;

public interface Constants {
	
	/** directory within the ASM_HOME containing the common property files. */
	public static final String PATH_TO_COMMON_PROPERTIES = "Files/Common/Properties/";
	
	/** directory (within ASM_HOME) under whom the service directories reside. */
	public static final String PATH_TO_SERVICES_DIR = "Files/";
	
	/** property file holding service names and endpoints as defined by the ASM administrator. */
	public static final String SERVICE_ENDPOINTS = "service_endpoints.properties";
	
	/** property file keys */
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
	
	/**example values */
	public static final String XLST = "xslt";
	public static final String JAVA = "java";
	public static final String GENERIC = "generic";

	/** the message must contain the name of the WS we are connectiong to */
	public static final String WS_NAME = "service";
	
}
