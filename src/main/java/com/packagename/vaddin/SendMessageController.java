package com.packagename.vaddin;

import org.springframework.web.bind.annotation.*;

@RestController
public class SendMessageController {

    private Message message;


    public SendMessageController(Message message) {

        this.message = message;
    }

    @RequestMapping(value = "/putMessage", method = RequestMethod.PUT)
    @ResponseBody
    public void sendMessage(@RequestParam("message") String message) {
        this.message.addMessage(message);
    }
}
