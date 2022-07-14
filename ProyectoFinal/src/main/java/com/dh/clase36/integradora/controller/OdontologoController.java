package com.dh.clase36.integradora.controller;

import com.dh.clase36.integradora.entities.Odontologo;
import com.dh.clase36.integradora.entities.Paciente;
import com.dh.clase36.integradora.exceptions.BadRequestException;
import com.dh.clase36.integradora.exceptions.ResourceNotFoundException;
import com.dh.clase36.integradora.service.OdontologoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {
    @Autowired
    OdontologoService service;

    //LOGGER PARA LOS REGISTROS E INFO
    private static final Logger logger= Logger.getLogger(OdontologoController.class);

    @GetMapping
    public List<Odontologo> buscarOdontologos(){
        logger.info("Consulta enpoint listar odontologos");
        return service.buscarTodos();
    }

    @PostMapping
    public ResponseEntity<Odontologo> registrarOdontologo(@RequestBody Odontologo odontologo) throws BadRequestException {
        logger.info("Consulta enpoint registrar odontologos");
        return ResponseEntity.ok(service.registrarOdontologo(odontologo));
    }
    @PutMapping
    public ResponseEntity<Odontologo> actualizarOdontologo(@RequestBody Odontologo odontologo) throws ResourceNotFoundException {
        logger.info("Consulta enpoint actualizar odontologos");
        return ResponseEntity.ok(service.actualizar(odontologo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarOdontologo(@PathVariable Long id) throws ResourceNotFoundException {
        logger.info("Consulta enpoint buscar odontologo");
        Odontologo elOdontolgo = service.buscar(id).get();
        return ResponseEntity.ok(elOdontolgo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Long id) throws ResourceNotFoundException {
        logger.info("Consulta enpoint eliminar odontologos");
        service.eliminarOdontologo(id);
        return ResponseEntity.ok("Odontologo eliminado");

    }
}
