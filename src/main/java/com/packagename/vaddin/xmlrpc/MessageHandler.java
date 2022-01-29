package com.packagename.vaddin.xmlrpc;

import com.packagename.vaddin.Message;

public class MessageHandler {

    private Message message;

    public MessageHandler(Message message) {

        this.message = message;
    }

    public String sendRPCMessage(String message) {
        this.message.addMessage(message);
        return message;
    }
}
