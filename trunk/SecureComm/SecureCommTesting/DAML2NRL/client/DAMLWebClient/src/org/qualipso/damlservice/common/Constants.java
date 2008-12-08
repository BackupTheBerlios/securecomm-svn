/**
 * Constants public interface
 */
package org.qualipso.damlservice.common;

/**
 * @author Panos Kavalagios
 *
 */
public interface Constants {
	// web.xml servlet initialisation parameters
	public static final String SOAP_SERVER_ADDRESS="soapserveraddress";
	public static final String SOAP_SERVICE_URI="soapserviceuri";

	// Form submit buttons names
	public static final String CRED_SUBMIT="cred_submit";
	public static final String SECMECH_SUBMIT="secmech_submit";
	public static final String SECNOT_SUBMIT="secnot_submit";
	
	// JSP files constants
	public static final String RESULTS="results";
	public static final String SUCCESS_PAGE="success.jsp";
	public static final String ERROR_PAGE="error.jsp";
	public static final String SOAP_REQUEST_FILE="request.xml";
	public static final String SOAP_RESPONSE_FILE="response.xml";
}
