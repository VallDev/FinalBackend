package com.dh.clase36.integradora.controller;

import com.dh.clase36.integradora.entities.Domicilio;
import com.dh.clase36.integradora.entities.Odontologo;
import com.dh.clase36.integradora.exceptions.BadRequestException;
import com.dh.clase36.integradora.exceptions.ResourceNotFoundException;
import com.dh.clase36.integradora.service.AppUserService;
import com.dh.clase36.integradora.service.DomicilioService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/domicilios")
public class DomicilioController {
    @Lazy
    @Autowired
    private DomicilioService domicilioService;

    //LOGGER PARA LOS REGISTROS E INFO
    private static final Logger logger= Logger.getLogger(DomicilioController.class);

    @GetMapping
    public ResponseEntity<List<Domicilio>> buscarDomicilios(){
        logger.info("Consulta endpoint listar domicilios");
        return ResponseEntity.ok(domicilioService.buscarTodos());
    }

    @PostMapping
    public ResponseEntity<Domicilio> registrarDomicilio(@RequestBody Domicilio domicilio) throws BadRequestException, ResourceNotFoundException {
        logger.info("Consulta endpoint registrar domicilios");
        return ResponseEntity.ok(domicilioService.registrarDomicilio(domicilio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarDomicilio(@PathVariable Long id) throws ResourceNotFoundException{
        logger.info("Consulta endpoint eliminar domicilios");
        domicilioService.eliminarDomicilio(id);
        return ResponseEntity.status(HttpStatus.OK).body("Domicilio con id "+id+ " eliminado");
    }

    @PutMapping
    public ResponseEntity<Domicilio> actualizarDomicilio(@RequestBody Domicilio domicilio) throws BadRequestException, ResourceNotFoundException {
        logger.info("Consulta endpoint actualizar domicilios");
        return ResponseEntity.ok(domicilioService.actualizar(domicilio));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Domicilio> buscarDomi(@PathVariable Long id) throws ResourceNotFoundException{
        logger.info("Consulta endpoint buscar domicilio");
        Domicilio elDomicilio = domicilioService.buscar(id).get();
        return ResponseEntity.ok(elDomicilio);
    }
}

