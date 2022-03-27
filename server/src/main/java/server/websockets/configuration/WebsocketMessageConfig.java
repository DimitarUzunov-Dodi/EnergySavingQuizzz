package server.websockets.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;




@Configuration
@EnableWebSocketMessageBroker
public class WebsocketMessageConfig implements WebSocketMessageBrokerConfigurer {
    /**
     * String representing the endpoint for basic websocket configurations.
     */
    private static final String GAME_ENDPOINT = "/game";

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(GAME_ENDPOINT);
        //System.out.println("foo");
    }


    @Override
    public void configureMessageBroker(final MessageBrokerRegistry registry) {

        registry.enableSimpleBroker("/topic", "/emoji", "/time");
        registry.setApplicationDestinationPrefixes("/app");
    }



    /*
        @Override
        public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry){
            webSocketHandlerRegistry.addHandler(getGameWebSocketHandler(), GAME_ENDPOINT)
                    .setAllowedOriginPatterns("*")

            ;
        }
    */

}
