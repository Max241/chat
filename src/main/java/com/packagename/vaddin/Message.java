package com.packagename.vaddin;


import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class Message {

    private String Message = "";

    public String getMessage() {
        return Message;
    }

    public ZonedDateTime zonedDateTime;

    public void addMessage(String message) { this.Message += "" + ZonedDateTime.now().getHour()+":"+ ZonedDateTime.now().getMinute()+ System.lineSeparator()+ message + "\n";
    }

}
