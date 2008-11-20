package org.qualipso.interop.semantics.securecomm.testService;

/** PoC authorization class.
 * Currently this class implements prototype auth methods just to prove the PoC
 * idea. However, in a real prod env, this should be extended further to include
 * the necessary authorization methods
 * 
 * @author wp32
 *
 */
public class ActionAuthCheck {

	private String action = null;
	private String auth = null;
	private String user = null;
	
	/** Constructor
	 * 
	  @param aut: authentication type.
	 * @param uname: username doing the action.
	 */
	ActionAuthCheck(String act, String aut) {
		action = act;
		auth = aut;
	}
	
	
	/** Constructor
	 * 
	 * @param act: action to do.
	 * @param aut: authentication type.
	 * @param uname: username doing the action.
	 */
	ActionAuthCheck(String act, String aut, String uname) {
		action = act;
		auth = aut;
		user = uname;
	}
	
	
		
	/** Check to see if an action is publicly exposed i.e.
	 * can be executed remotely. 
	 * This function can be extended/scaled further, the present if just
	 * an implementation to highlight the PoC concept.
	 * 
	 * @return: boolean
	 */
	private boolean isActionAvailable() {
	
		return (action!=null) ? true : false;
	}
	
	
	/** Check to see if an authorization value has been provided. 
	 * This function can be extended/scaled further, the present if just
	 * an implementation to highlight the PoC concept.
	 * 
	 * @return: boolean
	 */
	private boolean isAuthEnabled() {
		return (auth != null) ? true : false;
	}
	
	
	/** Check if the execution of this action is allowed. 
	 * This function can be extended/scaled further, the present if just
	 * an implementation to highlight the PoC concept.
	 * 
	 * @return: boolean
	 */
	public boolean isActionAllowed() {
		
		return (isActionAvailable() && isAuthEnabled() && auth.equalsIgnoreCase("strong")) ? true : false;
	}
	
	/** Check to see if the provided username belongs to our users.
	 * This function can be extended/scaled further, the present if just
	 * an implementation to highlight the PoC concept.
	 * 
	 * @return: boolean
	 */
	private boolean isRegisteredUser() {
		
		return (user!=null) ? true : false;
	}
	
	
	/** Check if the execution of this action is allowed by this user. 
	 * This function can be extended/scaled further, the present if just
	 * an implementation to highlight the PoC concept.
	 * 
	 * @return: boolean
	 */
	public boolean isActionAllowedForUser() {
		
		return (isRegisteredUser() && isActionAllowed()) ? true : false;
	}
	
	
	
}
