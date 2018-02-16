package com.yoti.app.websocket.websocket_handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
public class MyHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(final WebSocketSession session, final TextMessage message) throws Exception {
        log.info("inside the web socket");
        log.info("the message is {}", message.getPayload());
    }

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
        log.info("The connection is establised");
    }
}
