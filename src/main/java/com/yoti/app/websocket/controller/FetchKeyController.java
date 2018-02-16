package com.yoti.app.websocket.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.common.base.Strings;
import com.yoti.app.websocket.domain.KeyTable;
import com.yoti.app.websocket.message.HelloMessage;
import com.yoti.app.websocket.repos.KeyRepository;
import com.yoti.app.websocket.websocket_client.MySessionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static com.yoti.app.websocket.controller.EnpointsConstants.CONTEXT;
import static com.yoti.app.websocket.controller.EnpointsConstants.FETCH_KEY;


@RestController
@RequestMapping(CONTEXT)
@Slf4j
public class FetchKeyController {

    @Autowired
    KeyRepository repository;

    @Autowired
    private SimpMessagingTemplate simpTemplate;

    @Autowired
    ObjectMapper mapper;


    @GetMapping(value = FETCH_KEY + "/{serialId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<byte[]> getPublicKey(@PathVariable(name = "serialId") String serialId) {
        if (Strings.isNullOrEmpty(serialId)) {
            return (ResponseEntity<byte[]>) ResponseEntity.badRequest();
        }
        Optional<KeyTable> data = repository.findBySerialId(serialId);
        if (data.isPresent()) {
            return ResponseEntity.ok(Base64.getEncoder().encode(data.get().getPublicKey()));
        }
        return (ResponseEntity<byte[]>) ResponseEntity.notFound();
    }

    @GetMapping(value = "sss" + "/{serailId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> getPublicKeyString(@PathVariable(name = "serailId") String serialId) {
        if (Strings.isNullOrEmpty(serialId)) {
            return (ResponseEntity<String>) ResponseEntity.badRequest();
        }
        Optional<KeyTable> data = repository.findBySerialId(serialId);
        if (data.isPresent()) {
            log.info("The public key is {}", new String(data.get().getPublicKey()));
            return ResponseEntity.ok().body(new String(data.get().getPublicKey()));
        }
        return (ResponseEntity<String>) ResponseEntity.notFound();
    }

    @PostMapping(value = "rrr", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> callWebSocket() throws ExecutionException, InterruptedException, JsonProcessingException {
        HelloMessage message = new HelloMessage();
        message.setName("Justin");
        // simpTemplate.convertAndSend("/app/hello", message);

        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        transports.add(new RestTemplateXhrTransport());
        SockJsClient sockJsClient = new SockJsClient(transports);

        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("name", "Robin");
        StompSessionHandler sessionHandler = new MySessionHandler();
        StompSession session = stompClient.connect("ws://localhost:8081/gs-guide-websocket", sessionHandler).get();
        session.send("/app/hello", mapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(objectNode));
        session.disconnect();
        stompClient.stop();

        return ResponseEntity.ok().build();
    }

}
