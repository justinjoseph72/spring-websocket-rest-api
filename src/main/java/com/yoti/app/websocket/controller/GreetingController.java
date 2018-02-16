package com.yoti.app.websocket.controller;

import com.yoti.app.websocket.domain.KeyTable;
import com.yoti.app.websocket.message.HelloMessage;
import com.yoti.app.websocket.repos.KeyRepository;
import com.yoti.app.websocket.repos.SavingRepo;
import com.yoti.app.websocket.service.Greeting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import static com.yoti.app.websocket.controller.EnpointsConstants.*;

@Controller
@Slf4j
public class GreetingController {

    @Autowired
    private KeyRepository keyRepository;

    @Autowired
    private SavingRepo savingRepo;

    @MessageMapping(MESSAGE_MAPPING)
    @SendTo(WEB_SOCKET_LINK)
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000);
        KeyTable data = new KeyTable();
        data.setSerialId(message.getName());
        data.setPublicKey(message.getName().getBytes());
        // keyRepository.save(data);
        log.info("got something here {}", message.getName());
        return new Greeting(String.format("Hello %s !!", message.getName()));
    }
}
