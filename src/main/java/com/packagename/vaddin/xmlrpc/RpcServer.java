package com.packagename.vaddin.xmlrpc;

import com.packagename.vaddin.Message;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.webserver.WebServer;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RpcServer {

    private ServerProperties serverProperties;
    private Message message;


    public RpcServer(ServerProperties serverProperties, Message message) {
        this.serverProperties = serverProperties;
        this.message = message;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startServer() throws XmlRpcException, IOException {
            WebServer server = new WebServer(serverProperties.getPort() + 2);
            PropertyHandlerMapping mapping = new PropertyHandlerMapping();
            RequestProcessorFactory requestProcessorFactory = new RequestProcessorFactory(message);
            mapping.setRequestProcessorFactoryFactory(requestProcessorFactory);
            mapping.addHandler("SERVER", MessageHandler.class);
            server.getXmlRpcServer().setHandlerMapping(mapping);
            server.start();
        }
    }

