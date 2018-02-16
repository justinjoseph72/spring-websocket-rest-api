package com.yoti.app.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import static com.yoti.app.websocket.controller.EnpointsConstants.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketMessageBrokerConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(final StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint(WEB_SOCKET_URL_PATH).withSockJS();
        stompEndpointRegistry.addEndpoint("/myHandler").withSockJS();
    }

    @Override
    public void configureMessageBroker(final MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(WEB_SOCKET_TOPIC);
        registry.setApplicationDestinationPrefixes(WEB_SOCKET_MESSAGE_PREFIX);
    }
}
