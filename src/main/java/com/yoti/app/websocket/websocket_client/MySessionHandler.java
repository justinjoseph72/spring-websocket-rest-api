package com.yoti.app.websocket.websocket_client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.yoti.app.websocket.controller.EnpointsConstants;
import com.yoti.app.websocket.message.HelloMessage;
import com.yoti.app.websocket.service.Greeting;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

@Slf4j
public class MySessionHandler extends StompSessionHandlerAdapter {

    // private String messageStr;
    @Autowired
    ObjectMapper mapper;

    @Override
    public void afterConnected(final StompSession session, final StompHeaders connectedHeaders) {
        session.subscribe(EnpointsConstants.WEB_SOCKET_LINK, this);
//        if (!Strings.isNullOrEmpty(messageStr)) {
//            session.send("app/hello", messageStr.getBytes());
//        }

        log.info("connection done and and new session id is {}", session.getSessionId());
    }

    @Override
    public Type getPayloadType(final StompHeaders headers) {
        return Greeting.class;
    }

    @Override
    public void handleFrame(final StompHeaders headers, final Object payload) {
        Greeting message = (Greeting) payload;
        log.info("receved {}", message.getContent());
        //log.info("received in handler {}", payload.toString());
    }

    @Override
    public void handleException(final StompSession session, final StompCommand command, final StompHeaders headers, final byte[] payload, final Throwable exception) {
        exception.printStackTrace();
    }
}
