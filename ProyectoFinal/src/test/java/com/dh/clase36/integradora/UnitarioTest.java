package com.dh.clase36.integradora;

import com.dh.clase36.integradora.entities.Domicilio;
import com.dh.clase36.integradora.entities.Odontologo;
import com.dh.clase36.integradora.entities.Paciente;
import com.dh.clase36.integradora.entities.Turno;
import com.dh.clase36.integradora.service.DomicilioService;
import com.dh.clase36.integradora.service.OdontologoService;
import com.dh.clase36.integradora.service.PacienteService;
import com.dh.clase36.integradora.service.TurnoService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UnitarioTest {
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private DomicilioService domicilioService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void listarTurnos() throws Exception {
        List<Turno> listaTurnos = turnoService.listarTurno();
        Assertions.assertTrue(listaTurnos.isEmpty());
    }
    @Test
    public void listarPacientes() throws Exception {
        List<Paciente> listaPacientes = pacienteService.buscarTodos();
        Assertions.assertTrue(listaPacientes.isEmpty());
    }
    @Test
    public void listarOdontologos() throws Exception {
        List<Odontologo> listaOdontologos = odontologoService.buscarTodos();
        Assertions.assertTrue(listaOdontologos.isEmpty());
    }
    @Test
    public void listarDomicilios() throws Exception {
        List<Domicilio> listaDomicilios = domicilioService.buscarTodos();
        Assertions.assertTrue(listaDomicilios.isEmpty());
    }
    @Test
    public void buscarOdontologo() throws Exception {
        odontologoService.registrarOdontologo(new Odontologo("Baspineiro", "Rodolfo", 52410));
        Optional<Odontologo> odontologoEncontrado = odontologoService.buscar(1L);
        Assertions.assertTrue(odontologoEncontrado.isPresent());
    }
}
