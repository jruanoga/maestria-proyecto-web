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

    public String generarResumen(String contenidoDocumento) {
        String systemPrompt = "Eres un asistente experto en estudio. Tu tarea es leer el "
                + "contenido de un documento académico y generar un resumen claro, conciso "
                + "y fácil de entender, de máximo 5 líneas. No agregues opiniones ni texto "
                + "adicional fuera del resumen mismo.";

        return chatClient.prompt()
                .system(systemPrompt)
                .user(contenidoDocumento)
                .call()
                .content();
    }
}