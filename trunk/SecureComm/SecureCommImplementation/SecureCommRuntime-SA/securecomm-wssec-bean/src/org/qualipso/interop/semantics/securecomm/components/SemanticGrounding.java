package org.qualipso.interop.semantics.securecomm.components;

import javax.jbi.messaging.MessageExchange;

/** Any class that wants to provide custom Semantic-Grounding 
 * capabilities must implement this interface.
 * 
 * @author wp32
 *
 */
public interface SemanticGrounding {

	/** Use the data from the instance of the bridging result
	 *  in order to transform the request that will be sent to the 
	 *  service. 
	 * 
	 * @param exchange: the <code>MessageExchange</code> wrapping the current request.
	 * @param outstr: <code>String</code> to file holding the result of bridging.
	 * 
	 * @return: The updated <code>MessageExchange</code>
	 */
	public MessageExchange semanticGrounding(MessageExchange msgExchange, String outstr)
		throws Exception;
	
	
}
