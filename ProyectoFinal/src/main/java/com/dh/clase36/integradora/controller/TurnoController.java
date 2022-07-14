package com.dh.clase36.integradora.controller;

import com.dh.clase36.integradora.entities.Turno;
import com.dh.clase36.integradora.exceptions.BadRequestException;
import com.dh.clase36.integradora.exceptions.ResourceNotFoundException;
import com.dh.clase36.integradora.service.OdontologoService;
import com.dh.clase36.integradora.service.PacienteService;
import com.dh.clase36.integradora.service.TurnoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private TurnoService turnoService;

    //LOGGER PARA LOS REGISTROS E INFO
    private static final Logger logger= Logger.getLogger(TurnoController.class);

    @GetMapping
    public ResponseEntity<List<Turno>> listarTurnos(){
        logger.info("Consulta endpoint listar turnos");
        return ResponseEntity.ok(turnoService.listarTurno());
    }
    @PostMapping
    public ResponseEntity<Turno> registrarTurno(@RequestBody Turno turno) throws BadRequestException, ResourceNotFoundException {
        logger.info("Consulta endpoint registar turnos");
        return ResponseEntity.ok(turnoService.registrarTurno(turno));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) throws ResourceNotFoundException{
        logger.info("Consulta endpoint eliminar turnos");
        turnoService.eliminar(id);
        return ResponseEntity.status(HttpStatus.OK).body("Turno con id="+id+" eliminado");

    }

    @PutMapping
    public ResponseEntity<Turno> actualizarTurno(@RequestBody Turno turno) throws BadRequestException, ResourceNotFoundException {
        logger.info("Consulta endpoint actualizar turnos");
        return ResponseEntity.ok(turnoService.actualizar(turno));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turno> buscar(@PathVariable Long id) throws ResourceNotFoundException{
        logger.info("Consulta endpoint buscar turno");
        Turno elTurno = turnoService.buscar(id).get();
        return ResponseEntity.ok(elTurno);
    }
}
