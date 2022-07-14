package com.dh.clase36.integradora.controller;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    //LOGGER PARA LOS REGISTROS E INFO
    private static final Logger logger= Logger.getLogger(TurnoController.class);

    @GetMapping("/")
    public ResponseEntity<String> home(){
        logger.info("Se ingreso al sistema a traves del login");
        return ResponseEntity.ok("<h1>INGRESASTE AL SISTEMA</h1>");
    }
}
