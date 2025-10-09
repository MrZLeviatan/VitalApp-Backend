package co.edu.uniquindio.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Verificar que el proyecto compila y responda antes de automatizar.
@RestController
public class TestController {

    @GetMapping("/api/test")
    public String test(){
        return "Ultima Pruebas";
    }

}
