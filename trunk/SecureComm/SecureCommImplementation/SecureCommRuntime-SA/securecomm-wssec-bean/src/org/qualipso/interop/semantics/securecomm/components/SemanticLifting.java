package org.qualipso.interop.semantics.securecomm.components;

import javax.jbi.messaging.MessageExchange;

/** Any class that wants to provide custom Semantic-Lifting
 * capabilities must implement this interface.
 * 
 * @author wp32
 *
 */
public interface SemanticLifting {

	/** In the source ontology generate an instance with the data
	 * that are parsed from the incomming request.
	 * @return: full path to ont+instance
	 * 
	 * @param exchange: the <code>MessageExchange</code> wrapping the current request.
	 * @param sourceOntology: The path to the sourceOntology.owl file
	 * @param sourceInstance: The filename to save the source instance
	 */
	public void semanticLifting(MessageExchange exchange,  String sourceOntology, String sourceInstance) 
		throws Exception;
	
	
}
