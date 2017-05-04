package com.storyworld.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig extends AbstractWebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/publicChat").setAllowedOrigins("*").withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/queue", "/topic");
		registry.setApplicationDestinationPrefixes("/app");
	}

	/*@Override
	  public void configureClientInboundChannel(ChannelRegistration registration) {
	    registration.setInterceptors(new ChannelInterceptorAdapter() {

	        @Override
	        public Message<?> preSend(Message<?> message, MessageChannel channel) {

	            StompHeaderAccessor accessor =
	                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

	            if (StompCommand.CONNECT.equals(accessor.getCommand())) {
	                Principal user =  null // access authentication header(s)
	                accessor.setUser(user);
	            }

	            return message;
	        }
	    });
	  }*/
}
