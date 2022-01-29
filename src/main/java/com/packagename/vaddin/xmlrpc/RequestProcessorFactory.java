package com.packagename.vaddin.xmlrpc;

import com.packagename.vaddin.Message;
import org.apache.xmlrpc.XmlRpcRequest;
import org.apache.xmlrpc.server.RequestProcessorFactoryFactory;

public class RequestProcessorFactory extends RequestProcessorFactoryFactory.RequestSpecificProcessorFactoryFactory {

    private Message message;

    public RequestProcessorFactory(Message message) {
        this.message = message;
    }

    @Override
    protected Object getRequestProcessor(Class pClass, XmlRpcRequest pRequest) {
        return new MessageHandler(message);
    }

}
