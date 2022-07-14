package com.dh.clase36.integradora.service;

import com.dh.clase36.integradora.entities.Odontologo;
import com.dh.clase36.integradora.entities.Paciente;
import com.dh.clase36.integradora.entities.Turno;
import com.dh.clase36.integradora.exceptions.BadRequestException;
import com.dh.clase36.integradora.exceptions.ResourceNotFoundException;
import com.dh.clase36.integradora.repository.OdontologoRepository;
import com.dh.clase36.integradora.repository.PacienteRepository;
import com.dh.clase36.integradora.repository.TurnoRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {
    @Autowired
    TurnoRepository repository;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private PacienteService pacienteService;

    //LOGGER PARA LOS REGISTROS E INFO
    private static final Logger logger= Logger.getLogger(TurnoService.class);

    //registro de turno teniendo en cuenta si tanto paciente como odontologo se encuentran en el sistema
    //tambien se tiene en cuenta si existen o estan nulos tanto el paciente como el odontologo
    public Turno registrarTurno(Turno turno) throws BadRequestException, ResourceNotFoundException {
        boolean registrarTurno = false;
        Optional<Odontologo> odontologoBuscado = odontologoService.buscar(turno.getOdontologo().getId());
        Optional<Paciente> pacienteBuscado = pacienteService.buscar(turno.getPaciente().getId());

        if ((odontologoBuscado.isPresent() && !pacienteBuscado.isPresent()) || (odontologoBuscado.isPresent() && pacienteBuscado.isEmpty())){
            logger.error("Error en registro de turno -> Odontologo con id "+turno.getOdontologo().getId()+" no existe en el sistema");
            throw new BadRequestException("Paciente con id "+turno.getPaciente().getId()+" no existe en el sistema");
        }
        if ((!odontologoBuscado.isPresent() && pacienteBuscado.isPresent()) || (odontologoBuscado.isEmpty() && pacienteBuscado.isPresent())){
            logger.error("Error en registro de turno ->Odontologo con id "+turno.getOdontologo().getId()+" no existe en el sistema");
            throw new BadRequestException("Odontologo con id "+turno.getOdontologo().getId()+" no existe en el sistema");
        }

        if (odontologoBuscado.isPresent() && pacienteBuscado.isPresent())
            registrarTurno = true;

        if (registrarTurno){
            logger.info("Turno registrado exitosamente");
            return repository.save(turno);
        }
        else{
            logger.error("Error en registro de turno -> No existe paciente ni odontlogo con dichos id en el sistema");
            throw new BadRequestException("No existe paciente ni odontlogo con dichos id en el sistema");
        }
    }

    //listado de todos lo turnos
    public List<Turno> listarTurno(){
        logger.info("Consulta de todos los turnos");
        return repository.findAll();
    }

    //eliminacion de turno con base a su id
    public void eliminar (Long id) throws ResourceNotFoundException{
        Optional<Turno> turnoBuscado= buscar(id);
        if (turnoBuscado.isPresent()){
            logger.info("Eliminacion de turno exitosa ");
            repository.deleteById(id);
        }
        else{
            logger.error("Error en la eliminacion de turno");
            throw new ResourceNotFoundException("No existe el turno con id: "+id+", no se pudo borrar");
        }
    }

    //actualizacion o modificacion de turno teniendo en cuenta si tanto paciente como odontologo se encuentran en el sistema
    //tambien se tiene en cuenta si existen o estan nulos tanto el paciente como el odontologo
    public Turno actualizar(Turno turno) throws BadRequestException, ResourceNotFoundException {
        boolean actualizarTurno = false;
        Optional<Odontologo> odontologoBuscado = odontologoService.buscar(turno.getOdontologo().getId());
        Optional<Paciente> pacienteBuscado = pacienteService.buscar(turno.getPaciente().getId());
        Optional<Turno> turnoBuscado=buscar(turno.getId());

        if ((odontologoBuscado.isPresent() && !pacienteBuscado.isPresent()) || (odontologoBuscado.isPresent() && pacienteBuscado.isEmpty())){
            logger.error("Error al actualizar turno -> Paciente con id "+turno.getPaciente().getId()+" no existe en el sistema");
            throw new BadRequestException("Paciente con id "+turno.getPaciente().getId()+" no existe en el sistema");
        }
        if ((!odontologoBuscado.isPresent() && pacienteBuscado.isPresent()) || (odontologoBuscado.isEmpty() && pacienteBuscado.isPresent())){
            logger.error("Error al actualizar turno -> Odontologo con id "+turno.getOdontologo().getId()+" no existe en el sistema");
            throw new BadRequestException("Odontologo con id "+turno.getOdontologo().getId()+" no existe en el sistema");
        }

        if ((!odontologoBuscado.isPresent() && !pacienteBuscado.isPresent()) || (odontologoBuscado.isEmpty() && pacienteBuscado.isEmpty())){
            logger.error("Error al actualizar turno -> No existe paciente ni odontlogo con dichos id en el sistema");
            throw new BadRequestException("No existe paciente ni odontlogo con dichos id en el sistema");
        }

        if (odontologoBuscado.isPresent() && pacienteBuscado.isPresent())
            actualizarTurno = true;

       if (turnoBuscado.isPresent() && actualizarTurno){
           logger.info("Turno actualizado exitosamente");
           return repository.save(turno);
       }
       else{
           logger.error("Error al actualizar turno -> No existe ningun turno para actualizar con los valores previos");
           throw new BadRequestException("No existe ningun turno para actualizar con los valores previos");
        }
    }

    //busqueda de turno con base a su id
    public Optional<Turno> buscar(Long id) throws ResourceNotFoundException{

        Optional<Turno> turnoEncontrado =  repository.findById(id);
        if (turnoEncontrado.isPresent()){
            logger.info("Turno encontrado exitosamente");
            return turnoEncontrado;
        }
        else{
            logger.error("Error al buscar turno");
            throw new ResourceNotFoundException("No existe el turno con id: "+id+" Turno no encontrado");
        }
    }
}
