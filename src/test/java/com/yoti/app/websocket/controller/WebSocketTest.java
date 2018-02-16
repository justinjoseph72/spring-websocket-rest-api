package com.yoti.app.websocket.controller;

import com.yoti.app.websocket.message.HelloMessage;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static com.yoti.app.websocket.controller.EnpointsConstants.WEB_SOCKET_TOPIC;
import static com.yoti.app.websocket.controller.EnpointsConstants.WEB_SOCKET_URL_PATH;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class WebSocketTest {

    private static final String WEB_SOCKET_URI = "ws://localhost:8081" + WEB_SOCKET_URL_PATH;

    BlockingQueue<String> blockingQueue;
    WebSocketStompClient stompClient;

    @Before
    public void setup() {
        blockingQueue = new LinkedBlockingDeque<>();
        stompClient = new WebSocketStompClient(new SockJsClient(
                Arrays.asList(new WebSocketTransport(new StandardWebSocketClient()))
        ));
    }

    @Test
    public void shouldReceiveAMessageFromTheServer() throws Exception {
        StompSession session = stompClient
                .connect(WEB_SOCKET_URI, new StompSessionHandlerAdapter() {
                })
                .get(1, TimeUnit.SECONDS);
        session.subscribe(WEB_SOCKET_TOPIC, new DefaultStompFrameHandler() {
        });

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "test");
        String message = jsonObject.toString();
        session.send(WEB_SOCKET_TOPIC, message.getBytes());
        Assert.assertEquals(message, blockingQueue.poll(1, TimeUnit.SECONDS));
    }

    class DefaultStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return byte[].class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            log.info("the object is {}", o.toString());
            blockingQueue.offer(new String((byte[]) o));
        }
    }
}
