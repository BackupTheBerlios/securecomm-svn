package org.qualipso.interop.semantics.securecomm.components;

import java.util.List;

/** Any class that wants to provide custom Semantic-Bridging 
 * capabilities must implement this interface.
 * 
 * @author wp32
 *
 */
public interface SemanticBridging {
	
	/** Execute the semantic bridging. Return the resulting ontology
	 * as an <code>OutputStream</code> that will be parsed further on.
	 * 
	 * @param sourceOntology: The path to the sourceOntology.owl file.
	 * @param targetOntology: The path to the targetOntology.owl file.
	 * @param bridgeOntology: The path to the bridgeOntology.owl file.
	 * @param bridgeRepository: The path to the bridgeRepository file.
	 * @param otherOntologies: A list containing all other ontos imported/used.
	 * 
	 * @return: result of bridging as <code>OutputStream.</code>
	 */ 
	public void semanticBridging(String sourceOntology, String targetOntology,
			String bridgeOntology, String bridgeRepository, List otherOntologies,
			String resultOfBridging) throws Exception;
	
	
}
