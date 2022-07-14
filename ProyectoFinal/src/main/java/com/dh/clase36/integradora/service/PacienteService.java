package com.dh.clase36.integradora.service;

import com.dh.clase36.integradora.entities.Domicilio;
import com.dh.clase36.integradora.entities.Paciente;
import com.dh.clase36.integradora.exceptions.BadRequestException;
import com.dh.clase36.integradora.exceptions.ResourceNotFoundException;
import com.dh.clase36.integradora.repository.PacienteRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PacienteService{
    @Autowired
    PacienteRepository repository;
    @Lazy
    @Autowired
    private DomicilioService domicilioService;

    //LOGGER PARA LOS REGISTROS E INFO
    private static final Logger logger= Logger.getLogger(PacienteService.class);

    //listar todos los pacientes
    public List<Paciente> buscarTodos(){
        logger.info("Consulta total de pacientes");
        return repository.findAll();
    }

    //guardado y registro de paciente
    public Paciente guardar(Paciente p) throws BadRequestException{
        if (p.getDni()!=null){
            logger.info("Registro exitoso de paciente");
            return repository.save(p);
        }
        else{
            logger.error("Error en registro de paciente");
            throw new BadRequestException("Dni de paciente no ingresado en el sistema");
        }
    }

    //eliminacion de paciente con base en su id
    public void eliminar(Long id) throws ResourceNotFoundException{
        Optional<Paciente> pacienteBuscado=buscar(id);
        if (pacienteBuscado.isPresent()){
            logger.info("Eliminacion exitosa de paciente");
            repository.deleteById(id);
        }
        else{
            logger.error("Error en eliminacion de paciente");
            throw new ResourceNotFoundException("No existe el paciente con id: "+id+", no se puede borrar.");
        }
    }

    //busqueda de paciente con base en su id
    public Optional<Paciente> buscar(Long id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteEncontrado = repository.findById(id);
        if (pacienteEncontrado.isPresent()){
            logger.info("Busqueda exitosa de paciente");
            return pacienteEncontrado;
        }
        else{
            logger.error("Error en busqueda de paciente");
            throw new ResourceNotFoundException("No existe el paciente con id: "+id+" Paciente no encontrado");
        }
    }

    //actualizacion y modificacion de paciente
    public Paciente actualizar(Paciente p) throws ResourceNotFoundException, BadRequestException {

        if (buscar(p.getId()).isPresent()){
            logger.info("Actualizacion exitosa de paciente");
            return repository.save(p);
        }
        else{
            logger.error("Error en actualizacion de paciente");
            throw new BadRequestException("No existe ningun paciente para actualizar con los valores previos");
        }
    }


}
