package org.qualipso.interop.semantics.securecomm.asmFrontend.consumer;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.jbi.component.ComponentContext;
import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.servicemix.http.endpoints.DefaultHttpConsumerMarshaler;
import org.apache.servicemix.jbi.jaxp.StringSource;
import org.apache.servicemix.jbi.messaging.MessageExchangeSupport;
import org.apache.servicemix.jbi.util.ByteArrayDataSource;
import org.apache.servicemix.jbi.util.StreamDataSource;
import org.qualipso.interop.semantics.securecomm.asmFrontend.consumer.util.Util;
import org.qualipso.interop.semantics.securecomm.asmFrontend.consumer.output.ResponsePrinter;
import org.qualipso.interop.semantics.securecomm.asmFrontend.consumer.services.*;

/** This class implements an uploader interface for the ASM instance.
 * 
 * @author wp32
 *
 */
public class HTTPMarshaler extends DefaultHttpConsumerMarshaler implements Constants
{
    private static final Logger logger = Logger.getLogger(HTTPMarshaler.class.getName());
    private static final int MAX_MEM_SIZE = 10 * 1024 * 1024;
    private static final File TEMP_FOLDER = new File(System.getProperty("java.io.tmpdir"));
    private static final long MAX_UPLOAD_SIZE = 1024 * 1024 * 100;

    private DiskFileItemFactory factory;
    private ServletFileUpload upload;

    /** 
     * constructor 
     */
    public HTTPMarshaler() {
    	
    	super(MessageExchangeSupport.IN_OUT);

    	// Create a factory for disk-based file items
		factory = new DiskFileItemFactory();

		// Set factory constraints
		factory.setSizeThreshold(MAX_MEM_SIZE);
		factory.setRepository(TEMP_FOLDER);

		// Create a new file upload handler
		upload = new ServletFileUpload(factory);

		// Set overall request size constraint
		upload.setSizeMax(MAX_UPLOAD_SIZE);
	}

    
    public MessageExchange createExchange(HttpServletRequest request, ComponentContext context) 
    	throws Exception {
    	
		MessageExchange me = context.getDeliveryChannel()
				.createExchangeFactory().createExchange(getDefaultMep());

		//System.out.println("queryString=[" + request.getQueryString() +"]");
		
		String requestedOp = (String) request.getParameter(OPERATION_NAME_ON_URL);
		//System.out.println("Requested Operation = [" + requestedOp + "]");
		
		NormalizedMessage in = me.createMessage();
		in.setContent(new StringSource("<payload/>"));
		
		try {
			if (requestedOp != null) {
				if (requestedOp.equalsIgnoreCase(OPERATION_DELETE_SERVICE)) {
					String serviceName = (String) request.getParameter("servicetodelete");
					String submit = (String) request.getParameter(SUBMIT_ON_URL);
					
					if (submit!=null && submit.equalsIgnoreCase("true")) {
						if (serviceName!=null && !serviceName.equals("")) {
							deleteService(serviceName);
						} else {
							in.setProperty(ERROR_ON_PROCESS, "Must provide a Service Name to delete");
						}
					}
				} else if (requestedOp.equalsIgnoreCase(OPERATION_SERVICE_INFO)) {
					String serviceName = (String) request.getParameter(SERVICE_NAME_ON_URL);
					in.setProperty(SERVICE_NAME_ON_URL, serviceName);
				} else if (requestedOp.equalsIgnoreCase(OPERATION_UPLOAD_SERVICE)) {
					String submit = (String) request.getParameter(SUBMIT_ON_URL);
					if (submit!=null && submit.equalsIgnoreCase("true")) {
												
						boolean isMultipart = ServletFileUpload.isMultipartContent(request);
						if (isMultipart) {
							uploadNewService(request);
							in.setProperty(SUBMIT_ON_URL, submit);
						} else {
							in.setProperty(ERROR_ON_PROCESS, "Request is not a valid multipart message");
						}
					}
				} 
				in.setProperty(OPERATION_NAME_ON_URL, requestedOp);
	    	}
		} catch (Exception ex) {
			in.setProperty(ERROR_ON_PROCESS, ex.getMessage());
			ex.printStackTrace(System.out);
			System.out.println("Exception --->" + ex.getMessage());
		} 
		
		me.setMessage(in, "in");
		
		return me;
	}

    
    /** Deletes a service (i.e. security mapping) from the ASM container
     * 
     * @param servicename: name of service to delete
     * @throws Exception
     */
    private void deleteService(String servicename) throws Exception {
    	
    	String baseFilePath = System.getProperty("user.dir");
    	Util.deleteServiceJarFromASMLib(baseFilePath, servicename);
		Util.deleteServiceDirectory(baseFilePath, servicename);
    }
  
    
    /** Uploads a service (security mapping) into the ASM container
     *  It saves the uploaded files (ontologies, jars etc) in the proper
     *  locations, creates the directory structure and respective property
     *  files. Also it inserts the service into the available service list.
     *  
     * @param request: <code>HttpServletRequest</code>
     * @throws Exception
     */
    private void uploadNewService(HttpServletRequest request) throws Exception {
    	
    	String serviceName = null;
		String baseFilePath = System.getProperty("user.dir");
		// Parse the request
		List items = upload.parseRequest(request);

		// Process the uploaded items
		Iterator runOnce = items.iterator();
		while (runOnce.hasNext()) {
			FileItem item = (FileItem) runOnce.next();
			if (item.getFieldName().equals(SERVICE_NAME)) {
				serviceName = item.getString();
				break;
			}
		}
		
		Iterator iter = items.iterator();
		Properties servProps = new Properties();
		while (iter.hasNext()) {
			FileItem item = (FileItem) iter.next();
			String fieldName = item.getFieldName();
			if (item.isFormField()) {
				String fvalue=item.getString();
				if (fvalue!=null && !fvalue.equals("")) {
					System.out.println("Processing field[" + fieldName + "]");
					servProps.setProperty(fieldName, fvalue);
				}
			} else {
				String filename = item.getName();
				if (filename != null && !filename.equals("")) {
					System.out.println("Processing file=[" + filename + "]");
					servProps.setProperty(fieldName, filename);
					DataHandler dh = processUploadedFile(item);
					
					//filename ends with '.owl'
					if (SOURCE_ONTOLOGY.equals(fieldName) || TARGET_ONTOLOGY.equals(fieldName)
							|| BRIDGE_ONTOLOGY.equals(fieldName) || OTHER_ONTOLOGIES.equals(fieldName)) {
						
						Util.uploadSourceOntologyFiles(baseFilePath, filename, serviceName, dh);
					} else if (TRANSFORMATION_CODE.equals(fieldName) || OTHER_JARS.equals(fieldName)) {
						//fileName.endsWith ".jar"
						
						Util.uploadJarFilesToRestectedDirectory(baseFilePath, filename, serviceName, dh);
					} else if (COLLATERAL_FILES.equals(fieldName)) {
						Util.uploadOtherFiles(baseFilePath, filename, serviceName, dh);
					}  else if (LIFTING_CUSTOM_FILE.equals(fieldName) || GROUNDING_CUSTOM_FILE.equals(fieldName)) {
						Util.writeXSLTToServiceDirectory(baseFilePath, filename, serviceName, dh);
					}
				}
			}
		}

		Util.writeServiceDescription(baseFilePath, serviceName, servProps);
    }
  
    
    /** processes file items
	 * 
	 * @param item
	 *            the item
	 * @param msg
	 *            the in message
	 */
    private DataHandler processUploadedFile(FileItem item) {
		boolean isInMemory = item.isInMemory();
	
		DataHandler dh = null;

		if (isInMemory) {
			dh = new DataHandler(new ByteArrayDataSource(item.get(), item.getContentType()));
		} else {
			try {
				dh = new DataHandler(new StreamDataSource(item.getInputStream(), item.getContentType()));
			} catch (IOException ioex) {
				dh = new DataHandler(new ByteArrayDataSource(item.get(), item.getContentType()));
			}
		}

		return dh;
	}

    /** this is executed when ASM responds to the user request 
     * 
     */
    public void sendOut(MessageExchange exchange, NormalizedMessage outMsg,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
    	
		try {
			String operName = (String) outMsg.getProperty(OPERATION_NAME_ON_URL);
			String errata = (String) outMsg.getProperty(ERROR_ON_PROCESS);
			
			PrintWriter outGoing = response.getWriter();
			StringBuffer html = new StringBuffer(512);
			html.append("<html>");
			if (errata != null) {
				html.append(ResponsePrinter.generateHTMLError(operName, errata));
			} else {
				CommonActionInterface result = null;
				
				try {
					//System.out.println("sendOut ==== op=[" + operName + "]");
					if (operName.equalsIgnoreCase(OPERATION_LIST_SERVICES)) {
						result = new ListServices();
					} else if (operName.equalsIgnoreCase(OPERATION_SERVICE_INFO)) {
						String serviceName = (String) outMsg.getProperty(SERVICE_NAME_ON_URL);
						result = new ServiceInfo(serviceName);
					} else if (operName.equalsIgnoreCase(OPERATION_UPLOAD_SERVICE)) {
						String submit = (String) request.getParameter(SUBMIT_ON_URL);
						result = new UploadService(submit);
					} else if (operName.equalsIgnoreCase(OPERATION_DELETE_SERVICE)) {
						String submit = (String) request.getParameter(SUBMIT_ON_URL);
						result = new DeleteService(submit);
					}
					
					html.append(ResponsePrinter.generateHTMLHead(result.getHead()));
					html.append(ResponsePrinter.generateHTMLBody(result.getBody()));
				} catch (Exception ex) {
					html.append(ResponsePrinter.generateHTMLError(operName, ex.getMessage()));
				}
			}
			html.append("</html>");
			outGoing.print(html);
		} catch (Exception e) {
			System.out.println("Exception --->" + e.getMessage());
			logger.log(Level.WARNING, "Exception occurred" + e.getMessage(), e);
		}
	}

}