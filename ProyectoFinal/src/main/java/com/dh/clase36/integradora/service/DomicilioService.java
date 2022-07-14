package com.dh.clase36.integradora.service;

import com.dh.clase36.integradora.entities.Domicilio;
import com.dh.clase36.integradora.entities.Odontologo;
import com.dh.clase36.integradora.entities.Paciente;
import com.dh.clase36.integradora.exceptions.BadRequestException;
import com.dh.clase36.integradora.exceptions.ResourceNotFoundException;
import com.dh.clase36.integradora.repository.DomicilioRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Lazy
@Service
public class DomicilioService {
    @Autowired
    DomicilioRepository repository;
    @Autowired
    private PacienteService pacienteService;

    //LOGGER PARA LOS REGISTROS E INFO
    private static final Logger logger= Logger.getLogger(DomicilioService.class);

    //Buscar domicilio
    public Optional<Domicilio> buscar(Long id) throws ResourceNotFoundException {
        Optional<Domicilio> domicilioEncontrado = repository.findById(id);
        if (domicilioEncontrado.isPresent()) {
            logger.info("Domicilio encontrado exitosamente");
            return domicilioEncontrado;
        }
        else {
            logger.error("Error en busqueda de domicilio");
            throw new ResourceNotFoundException("No existe el domicilio con id: " + id + " Domicilio no encontrado");
        }
    }

    //listar todos los domicilios
    public List<Domicilio> buscarTodos(){
        logger.info("Consulta de total de domicilios");
        return repository.findAll();
    }

    //registro y guardado de domicilio
    public Domicilio registrarDomicilio(Domicilio domicilio) throws ResourceNotFoundException, BadRequestException {
        if (domicilio.getCalle()!=null) {
            logger.info("Domicilio registrado exitosamente");
            return repository.save(domicilio);
        }
        else {
            logger.error("Error en registro de domicilio");
            throw new BadRequestException("No existe paciente en el sistema ni calle ingresada");
        }
    }

    //eliminación de domicilio con base a su id
    public void eliminarDomicilio(Long id) throws ResourceNotFoundException {
        Optional<Domicilio> domicilioBuscado = buscar(id);
        if (domicilioBuscado.isPresent()){
            logger.info("Domicilio eliminado exitosamente");
            repository.deleteById(id);
        }
        else{
            logger.error("Error en eliminacion de domicilio");
            throw new ResourceNotFoundException("No existe el domicilio con id: "+id+", no se pudo borrar");
        }
    }

    //actualizacion y modificacion de domicilio
    public Domicilio actualizar(Domicilio domicilio) throws BadRequestException, ResourceNotFoundException {
        boolean registrarDomicilio = false;
        Optional<Paciente> pacienteBuscado = pacienteService.buscar(domicilio.getPaciente().getId());


        if (!pacienteBuscado.isPresent() && domicilio.getCalle()==null) {
            logger.error("Error en actualizacion de domicilio -> No existe paciente en el sistema ni calle ingresada");
            throw new BadRequestException("No existe paciente en el sistema ni calle ingresada");
        }
        if (pacienteBuscado.isPresent() && domicilio.getCalle()!=null)
            registrarDomicilio = true;

        if (buscar(domicilio.getId()).isPresent() && registrarDomicilio){
            logger.info("Domicilio actualizado exitosamente");
            return repository.save(domicilio);
        }
        else{
            logger.error("Error en actualizacion de domicilio -> No existe ningun domicilio para actualizar con los valores previos");
            throw new BadRequestException("No existe ningun domicilio para actualizar con los valores previos");
        }
    }
}
