package com.packagename.vaddin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SendMessageService {

    private ServerProperties serverProperties;


    public SendMessageService(ServerProperties serverProperties) {

        this.serverProperties = serverProperties;
    }

    public void sendMessage(String message) {
        String address = "";
        if ("8080".equalsIgnoreCase(serverProperties.getPort().toString())) {
            address = "http://localhost:8090/putMessage?message=" + message;
        } else {
            address = "http://localhost:8080/putMessage?message=" + message;
        }
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(
                address,
                HttpMethod.PUT,
                HttpEntity.EMPTY,
                String.class);
    }
}