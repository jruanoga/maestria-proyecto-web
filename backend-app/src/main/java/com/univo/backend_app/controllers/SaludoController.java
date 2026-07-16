package com.univo.backend_app.controllers;
import com.univo.backend_app.models.MensajeDTO;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/saludos") // Esta es la URL base para este controlador
public class SaludoController {
    // 1. Endpoint GET: Para obtener información
    @GetMapping("/basico")
    public String obtenerSaludo() {
        return "¡Hola desde el Backend con Spring Boot!";
    }
    // 2. Endpoint GET que devuelve JSON
    @GetMapping("/json")
    public MensajeDTO obtenerSaludoJson() {
// Spring Boot automáticamente convertirá este objeto Java a
        return new MensajeDTO("El sistema está funcionando", "Servidor JSON UNIVO");
    }
    // 3. Endpoint POST: Para recibir información desde el Frontend
    @PostMapping("/enviar")
    public MensajeDTO recibirMensaje(@RequestBody MensajeDTO
                                             mensajeRecibido) {
// Simulamos que procesamos la información y devolvemos una respuesta
        String respuestaTexto = "Mensaje recibido correctamente de: "
                + mensajeRecibido.getRemitente();
        return new MensajeDTO(respuestaTexto, "Sistema");
    }
}