package org.qualipso.interop.semantics.securecomm.testService;

import java.util.ArrayList;
import java.util.List;

/**
 *  PoC sample class that represents the Request fields and
 *  supports some method-independant actions with them. 
 *  
 * @author wp32
 *
 */
public class ServiceRequestFields {

	public static final String authType = "authType";
	public static final String action = "action";
	public static final String entryPoint = "entryPoint";
	public static final String telephoneNumber = "telephoneNumber";
	public static final String username = "username";
	public static final String startTimeFrame = "startTimeFrame";
	public static final String endTimeFrame = "endTimeFrame";
	public static final String newPin = "newPin";
	
	/** The fields of the <b>getAccountInfo</b> request.
	 * 
	 * @return: <code>List</code> of request fields.
	 */
	public static List getAccountInfoFields() {
		List fields = new ArrayList();
		
		fields.add(authType);
		fields.add(action);
		fields.add(telephoneNumber);
		fields.add(username);
		fields.add(entryPoint);
		fields.add(startTimeFrame);
		fields.add(endTimeFrame);
		
		return fields;
	}
	
	
	/** The fields of the <b>resetPin</b> request.
	 * 
	 * @return: <code>List</code> of request fields.
	 */
	public static List resetPinFields() {
		List fields = new ArrayList();
		
		fields.add(authType);
		fields.add(action);
		fields.add(telephoneNumber);
		fields.add(entryPoint);
		fields.add(newPin);
		
		return fields;
	}
	
	
}
