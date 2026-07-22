package com.univo.backend_app.services;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class InteligenciaArtificialService {

        private final ChatClient chatClient;

        public InteligenciaArtificialService(ChatClient.Builder chatClientBuilder) {
            this.chatClient = chatClientBuilder.build();
        }

        public String generarRespuestaSimple(String preguntaUsuario) {
            return chatClient.prompt()
                    .user(preguntaUsuario)
                    .call()
                    .content();
        }
}
