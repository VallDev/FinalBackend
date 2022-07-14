package com.dh.clase36.integradora.service;

import com.dh.clase36.integradora.entities.Domicilio;
import com.dh.clase36.integradora.entities.Odontologo;
import com.dh.clase36.integradora.exceptions.BadRequestException;
import com.dh.clase36.integradora.exceptions.ResourceNotFoundException;
import com.dh.clase36.integradora.repository.OdontologoRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {
    @Autowired
    OdontologoRepository repository;

    //LOGGER PARA LOS REGISTROS E INFO
    private static final Logger logger= Logger.getLogger(OdontologoService.class);

    //busqueda de odontologo con base a su id
    public Optional<Odontologo> buscar(Long id) throws ResourceNotFoundException{
        Optional<Odontologo> odontologoEncontrado = repository.findById(id);
        if (odontologoEncontrado.isPresent()){
            logger.info("Odontologo encontrado exitosamente");
            return odontologoEncontrado;
        }
        else{
            logger.error("Error en busqueda de odontologo");
            throw new ResourceNotFoundException("No existe el odontologo con id: "+id+" Odontologo no encontrado");
        }
    }

    //listar todos los odontologos
    public List<Odontologo> buscarTodos(){
        logger.info("Consulta total de odontologos");
        return repository.findAll();
    }

    //registro y guardado de un odontologo
    public Odontologo registrarOdontologo(Odontologo odontologo) throws BadRequestException{
        if (odontologo.getMatricula()!=null){
            logger.info("Odontologo registrado exitosamente");
            return repository.save(odontologo);
        }
        else{
            logger.error("Error en el registro de odontologo");
            throw new BadRequestException("Matricula de odontologo no ingresada");
        }
    }

    //eliminacion de odontologo con base a su id
    public void eliminarOdontologo(Long id) throws ResourceNotFoundException{
        Optional<Odontologo> odontologoBuscado= buscar(id);
        if (odontologoBuscado.isPresent()){
            logger.info("Odontologo eliminado exitosamente");
            repository.deleteById(id);
        }
        else{
            logger.error("Error en eliminacion de odontologo");
            throw new ResourceNotFoundException("No se puede borrar el odontologo con id: "+id+", error al borrar");
        }

    }

    //actualizacion y modificacion de odontologo
    public Odontologo actualizar(Odontologo odontologo) throws ResourceNotFoundException {

        if (buscar(odontologo.getId()).isPresent()){
            logger.info("Actualizacion de odontologo exitosa");
            return repository.save(odontologo);
        }
        else{
            logger.error("Error en actualizacion de odontologo");
            throw new ResourceNotFoundException("No existe ningun odontologo para actualizar con los valores previos");
        }
    }
}
