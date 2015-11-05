package com.prototype.chat.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * Created by david.hong on 2/11/2015.
 */
@Configuration
@EnableScheduling
@ComponentScan("com.prototype")
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //registry.enableSimpleBroker("/queue/", "/topic/");

        registry.enableStompBrokerRelay("/queue/", "/topic/")
                .setRelayHost("10.32.8.60") // Change this accordingly
                .setVirtualHost("mybroker") //This is the default broker name according to Apollo installation guide
                .setClientLogin("admin") //Refer to Step.2 in Prerequisite
                .setClientPasscode("password") //Refer to Step.2 in Prerequisite
                .setSystemLogin("admin") //Refer to Step.2 in Prerequisite
                .setSystemPasscode("password") //Refer to Step.2 in Prerequisite
                .setRelayPort(61613); //This is the default port according to Apollo installation guide

        registry.setApplicationDestinationPrefixes("/app");
    }
}
