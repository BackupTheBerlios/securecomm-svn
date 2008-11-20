package org.qualipso.interop.semantics.securecomm.components.bridger;


/** Holds some constants related to the Generic bridging process. 
 * 
 * @author wp32
 *
 */
public interface BridgerConstants {

	/** The keyword to look for */
	public static final String OWL_IMPORT_KEYWORD = "imports";
	
	/** The namespace attibute name */
	public static final String OWL_IMPORT_NAMESPACE_ATTR = "rdf:resource";

	/** prefix of xml base uri */
	public static final String XML_NS_BASE = "xmlns";
	
	/** uri of the bridging result ontology */
	public static final String RESULT_ONTOLOGY_URI = 
		"http://www.qualipso.org/interop/semant/securecomm/TelephoneService/resultOntology.owl";
	
}
