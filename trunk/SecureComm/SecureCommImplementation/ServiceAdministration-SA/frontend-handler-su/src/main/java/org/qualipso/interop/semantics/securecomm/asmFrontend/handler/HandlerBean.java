package org.qualipso.interop.semantics.securecomm.asmFrontend.handler;


import java.util.Iterator;
import java.util.Set;
import javax.annotation.Resource;
import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.jbi.messaging.MessageExchange.Role;
import org.apache.log4j.Logger;
import org.apache.servicemix.MessageExchangeListener;
import org.apache.servicemix.components.util.CopyTransformer;

/** This class implement the provider endpart of the service.
 *  It simply copies (any available) properties from the 
 *  incoming to the outgoing message.
 * 
 */
public class HandlerBean implements MessageExchangeListener
{
    private static final Logger logger = Logger.getLogger(HandlerBean.class);
    
    /** which operation is requested from the URL */
    private static final String OPERATION_NAME_ON_URL = "operation";
    /** what error occurred during processing*/
    private static final String ERROR_ON_PROCESS = "errorOnProcess";
    
    @Resource
    private DeliveryChannel channel;

    /*
     * (non-Javadoc)
     * @see org.apache.servicemix.MessageExchangeListener#onMessageExchange(javax.jbi.messaging.MessageExchange)
     */
    public void onMessageExchange(MessageExchange exchange)
		throws MessagingException {
    	
		if (exchange == null) {
			return;
		}

		// The component acts as a consumer, this means this exchange is
		// received because
		// we sent it to another component. As it is active, this is either an
		// out or a fault
		// If this component does not create / send exchanges, you may just
		// throw an
		// UnsupportedOperationException
		if (exchange.getRole() == Role.CONSUMER) {
			onConsumerExchange(exchange);
		}

		// The component acts as a provider, this means that another component
		// has requested our
		// service
		// As this exchange is active, this is either an in or a fault (out are
		// send by this
		// component)
		else if (exchange.getRole() == MessageExchange.Role.PROVIDER) {
			onProviderExchange(exchange);
		}

		// Unknown role
		else {
			throw new MessagingException(
					"HandlerBean.onMessageExchange(): Unknown role: " + exchange.getRole());
		}
	}

    /**
	 * handles the incoming consumer messages
	 * 
	 * @param exchange
	 * @throws MessagingException
	 */
    private void onConsumerExchange(MessageExchange exchange)
			throws MessagingException {
		// Out message
		if (exchange.getMessage("out") != null) {
			exchange.setStatus(ExchangeStatus.DONE);
			channel.send(exchange);
		}

		// Fault message
		else if (exchange.getFault() != null) {
			exchange.setStatus(ExchangeStatus.DONE);
			channel.send(exchange);
		}

		// This is not compliant with the default MEPs
		else {
			throw new MessagingException(
					"HandlerBean.onConsumerExchange(): Consumer exchange is ACTIVE, but no out or fault is provided");
		}
	}
    
    /** Handles the incoming provider messages. It simply copies any existing
     *  properties within the message and returns the message.
	 * 
	 * @param exchange
	 * @throws MessagingException
	 */
    private void onProviderExchange(MessageExchange exchange) throws MessagingException {
		// Exchange is finished
		if (exchange.getStatus() == ExchangeStatus.DONE) {
			return;
		}
		// Exchange has been aborted with an exception
		else if (exchange.getStatus() == ExchangeStatus.ERROR) {
			return;
		}
		// Fault message
		else if (exchange.getFault() != null) {
			exchange.setStatus(ExchangeStatus.DONE);
			channel.send(exchange);
		} else {
			NormalizedMessage in = exchange.getMessage("in");
			
			
			if (in == null) {
				// no in message - strange
				throw new MessagingException(
						"HandlerBean.onProviderExchange(): Exchange has no IN message");
			} else {
				
				NormalizedMessage copyMessage = (NormalizedMessage) exchange.createMessage();
                CopyTransformer.getInstance().transform(exchange, in, copyMessage);
                String requstedOp = (String) in.getProperty(OPERATION_NAME_ON_URL);
                
                // just copy any properties verbatim
                Set props = in.getPropertyNames();
                Iterator iter = props.iterator();
                while (iter.hasNext()) {
                	String propName = (String)iter.next();
                	//System.out.println("HandlerBear name=["+propName+"] value=["+in.getProperty(propName)+"]");
                	copyMessage.setProperty(propName, in.getProperty(propName));
                }
                
                exchange.setMessage(copyMessage, "out");
                channel.send(exchange);
			}
		}
	}
}


