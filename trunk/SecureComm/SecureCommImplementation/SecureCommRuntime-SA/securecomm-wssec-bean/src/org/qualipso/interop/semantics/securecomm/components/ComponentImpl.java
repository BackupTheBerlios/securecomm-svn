package org.qualipso.interop.semantics.securecomm.components;

import javax.jbi.messaging.MessageExchange;

/**
 * Class defining the operations that must be implement by another
 * semantic factory implementator if another one is to be plugged
 * in instead of the default. 
 * 
 * @author wp32
 *
 */
public abstract class ComponentImpl {
	
	/** Initialization related actions. 
	 * 
	 * @throws Exception
	 */
	public abstract void init() throws Exception;
	
	
	/** Definition of the SemanticLifting method.
	 * 
	 * @param msgExchange: <code>MessageExchange</code> input from ASM.
	 * @throws Exception
	 */
	public abstract void semanticLifting(MessageExchange msgExchange) throws Exception;
	
	/** Definition of the SemanticBridging method.
	 * 
	 * @throws Exception
	 */
	public abstract void semanticBridging() throws Exception;
	
	/** Definition of the SemanticGrounding method.
	 * 
	 * @param msgExchange: <code>MessageExchange</code> input from ASM.
	 * @return: processed and changed <code>MessageExchange</code>.
	 * @throws Exception
	 */
	public abstract MessageExchange semanticGrounding(MessageExchange msgExchange) throws Exception;
	
}
