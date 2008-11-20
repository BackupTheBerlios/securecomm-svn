package org.qualipso.interop.semantics.securecomm.asmFrontend.consumer.services;

/** All security_mapping related action supported must implement this
 *  interface, that defined the simple methods of getting the content
 *   for the "head" and "body" of the request from the responsible action.
 * 
 * @author wp32
 *
 */
public interface CommonActionInterface {

	/** head getter. */
	public String getHead();
	
	/** body getter. */
	public String getBody();
	
}
