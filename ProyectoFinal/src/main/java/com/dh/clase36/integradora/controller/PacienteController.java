package com.dh.clase36.integradora.controller;

import com.dh.clase36.integradora.entities.Paciente;
import com.dh.clase36.integradora.exceptions.BadRequestException;
import com.dh.clase36.integradora.exceptions.ResourceNotFoundException;
import com.dh.clase36.integradora.service.PacienteService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    //LOGGER PARA LOS REGISTROS E INFO
    private static final Logger logger= Logger.getLogger(PacienteController.class);


    @GetMapping
    public List<Paciente> buscarPacientes(){
        logger.info("Consulta endpoint listar pacientes");
        return pacienteService.buscarTodos();
    }

    @PostMapping
    public ResponseEntity<Paciente> registrarPaciente(@RequestBody Paciente paciente) throws BadRequestException {
        logger.info("Consulta endpoint registrar pacientes");
        return ResponseEntity.ok(pacienteService.guardar(paciente));
    }
    @PutMapping
    public ResponseEntity<Paciente> actualizarPaciente(@RequestBody Paciente paciente) throws ResourceNotFoundException, BadRequestException {
        logger.info("Consulta endpoint actualizar pacientes");
        return ResponseEntity.ok(pacienteService.actualizar(paciente));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        logger.info("Consulta endpoint buscar paciente");
        Paciente elPaciente = pacienteService.buscar(id).get();
        return ResponseEntity.ok(elPaciente);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        logger.info("Consulta endpoint eliminar pacientes");
        pacienteService.eliminar(id);
        return ResponseEntity.status(HttpStatus.OK).body("Paciente eliminado con id: "+id);
    }

}
