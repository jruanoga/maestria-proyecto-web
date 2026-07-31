package com.univo.backend_app.services;

import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.client.ChatClient;
import java.util.List;
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
    public String generarPreguntas(String contenidoDocumento) {
        String systemPromptTexto = "Eres un asistente experto en educación. Tu tarea es leer el "
                + "contenido de un documento académico y generar exactamente 5 preguntas de "
                + "opción múltiple para evaluar la comprensión del tema. "
                + "Responde ÚNICAMENTE en formato JSON válido, siguiendo esta estructura exacta, "
                + "sin texto adicional antes ni después: "
                + "[{\"pregunta\":\"texto de la pregunta\",\"opciones\":[\"opcion A\",\"opcion B\",\"opcion C\",\"opcion D\"],\"respuestaCorrecta\":\"opcion A\"}]";

        SystemMessage systemMessage = new SystemMessage(systemPromptTexto);
        UserMessage userMessage = new UserMessage(contenidoDocumento);

        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));

        return chatClient.prompt(prompt)
                .call()
                .content();
    }
}