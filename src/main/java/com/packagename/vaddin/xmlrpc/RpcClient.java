package com.packagename.vaddin.xmlrpc;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

@Controller
public class RpcClient {

    private ServerProperties serverProperties;

    public RpcClient(ServerProperties serverProperties) {

        this.serverProperties = serverProperties;
    }

    public void sendRPCMessage(String message) throws XmlRpcException, MalformedURLException {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL("http://localhost:" + ("8080".equalsIgnoreCase(serverProperties.getPort().toString()) ? "8092" : "8082")));
        org.apache.xmlrpc.client.XmlRpcClient client = new org.apache.xmlrpc.client.XmlRpcClient();
        client.setConfig(config);
        client.execute("SERVER.sendRPCMessage", Collections.singletonList(message));
    }

}