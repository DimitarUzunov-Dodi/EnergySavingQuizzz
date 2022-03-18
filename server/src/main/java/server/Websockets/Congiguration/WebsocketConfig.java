package server.Websockets.Congiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import server.Websockets.Handler.GameWebsocketHandler;

import java.util.logging.Logger;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {

    private final static String GAME_ENDPOINT = "/game";

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(GAME_ENDPOINT);
        //System.out.println("foo");
    }


    @Override
    public void configureMessageBroker(final MessageBrokerRegistry registry){
        registry.enableSimpleBroker("/topic", "/emoji");
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
    @Bean
    public WebSocketHandler getGameWebSocketHandler(){
        return new GameWebsocketHandler();
    }
}
