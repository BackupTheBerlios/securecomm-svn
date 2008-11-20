package org.qualipso.interop.semantics.securecomm.components;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;
import javax.jbi.messaging.MessageExchange;


/** This class is a wrapper designed to call the custom java class
 * that has been given from the user. It provides the 3 core methods
 *  [lifting, bridging, grounding] not dependant on each other.  
 *  Reflection. Sets up the necessary methods parameters and types and 
 *  calls the custom implementation. 
 * 
 * @author wp32
 *
 */
public class JavaComponent implements SemanticLifting, SemanticGrounding, SemanticBridging {

	private Class classObj = null;
	private Object classInstance = null;
	
	
	/** Initialize the class that will be used.
	 * 
	 * @param className: The name of the class
	 * @throws Exception: 
	 */
	JavaComponent(String className) throws Exception {
		classObj = Class.forName(className);
        Constructor constr = classObj.getConstructor();
        classInstance = constr.newInstance();
	}

	
	public void semanticLifting( MessageExchange exchange, String source, String instanceSource) throws Exception {
		
		Class[] declareMethArgType = new Class[3];
		declareMethArgType[0] = MessageExchange.class;
		declareMethArgType[1] = String.class;
		declareMethArgType[2] = String.class;
		
		Method method = classObj.getMethod("semanticLifting", declareMethArgType);
		Object[] methodArgs = new Object[3];
		methodArgs[0] = exchange;
		methodArgs[1] = source;
		methodArgs[2] = instanceSource;
		
		method.invoke(classInstance, methodArgs);
		//println("CreditTranslator ++++ [" + returnOfMapping + "]");
	}
	
	
	public void semanticBridging(String sourceOntology, String targetOntology,
			String bridgeOntology, String bridgeRepository, List otherOntologies, String resultBridgeFile) 
			throws Exception {
		
		Class[] declareMethArgType = new Class[6];
		declareMethArgType[0] = String.class;
		declareMethArgType[1] = String.class;
		declareMethArgType[2] = String.class;
		declareMethArgType[3] = String.class;
		declareMethArgType[4] = List.class;
		declareMethArgType[5] = String.class;
		
		Method method = classObj.getMethod("semanticBridging", declareMethArgType);
		Object[] methodArgs = new Object[6];
		methodArgs[0] = sourceOntology;
		methodArgs[1] = targetOntology;
		methodArgs[2] = bridgeOntology;
		methodArgs[3] = bridgeRepository;
		methodArgs[4] = otherOntologies;
		methodArgs[5] = resultBridgeFile;
		
		method.invoke(classInstance, methodArgs);
	}
	
	
	public MessageExchange semanticGrounding(MessageExchange msgExchange, String outstr) 
		throws Exception {
		
		Class[] declareMethArgType = new Class[2];
		declareMethArgType[0] = MessageExchange.class;
		declareMethArgType[1] = String.class;
		
		Method method = classObj.getMethod("semanticGrounding", declareMethArgType);
		Object[] methodArgs = new Object[2];
		methodArgs[0] = msgExchange;
		methodArgs[1] = outstr;
		
		Object returnOfThirdMeth = method.invoke(classInstance, methodArgs);
		return (MessageExchange) returnOfThirdMeth;
	}
	
}
