package com.dh.clase36.integradora;

import com.dh.clase36.integradora.entities.Domicilio;
import com.dh.clase36.integradora.entities.Odontologo;
import com.dh.clase36.integradora.entities.Paciente;
import com.dh.clase36.integradora.entities.Turno;
import com.dh.clase36.integradora.service.DomicilioService;
import com.dh.clase36.integradora.service.OdontologoService;
import com.dh.clase36.integradora.service.PacienteService;
import com.dh.clase36.integradora.service.TurnoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class IntegracionTurnosTest {
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

    public void cargarDatosEnBD() throws Exception{
        //cargar un turno
        //odontologo y un paciente junto con la fecha
        domicilioService.registrarDomicilio(new Domicilio("Av",445,"salta","salta"));
        pacienteService.guardar(new Paciente("Baspineiro","Rodolfo","rodolfo@gmail.com",45741,LocalDate.of(2022,04,15),domicilioService.buscar(0L).get()));
        odontologoService.registrarOdontologo(new Odontologo("Baspineiro","Rodolfo",52410));
        turnoService.registrarTurno(new Turno(pacienteService.buscar(1L).get(),odontologoService.buscar(1L).get(), LocalDate.of(2022,04,01)));

    }
    @Test
    public void listarTurnos() throws Exception {
        //cargarDatosEnB();
        MvcResult resultado= mockMvc.perform(MockMvcRequestBuilders.get("/turnos").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertFalse(resultado.getResponse().getContentAsString().isEmpty());
    }
    @Test
    public void listarOdontologos() throws Exception{
        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.get("/odontologos").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertFalse(resultado.getResponse().getContentAsString().isEmpty());
        //resultado.getResponse().getContentAsString().length()
    }
    @Test
    public void listarPacientes() throws Exception{
        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.get("/pacientes").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertFalse(resultado.getResponse().getContentAsString().isEmpty());
    }
    @Test
    public void listarDomicilios() throws Exception{
        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.get("/domicilios").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertFalse(resultado.getResponse().getContentAsString().isEmpty());
    }
    @Test
    public void buscarOdontologo() throws Exception{
        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.get("/odontologos/6").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();
                //.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String respuestaOdontologos = "No existe el odontologo con id: "+"6"+" Odontologo no encontrado";
        Assert.assertEquals(resultado.getResponse().getContentAsString(),respuestaOdontologos);
    }
    @Test
    public void buscarPaciente() throws Exception{
        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.get("/pacientes/9").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();
        //.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String respuestaPacientes = "No existe el paciente con id: "+"9"+" Paciente no encontrado";
        Assert.assertEquals(resultado.getResponse().getContentAsString(),respuestaPacientes);
    }
    @Test
    public void buscarTurno() throws Exception{
        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.get("/turnos/11").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();
        //.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String respuestaTurnos = "No existe el turno con id: "+"11"+" Turno no encontrado";
        Assert.assertEquals(resultado.getResponse().getContentAsString(),respuestaTurnos);
    }
    @Test
    public void buscarDomicilio() throws Exception{
        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.get("/domicilios/7").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();
        //.andExpect(MockMvcResltMatchers.status().isOk()).andReturn();
        String respuestaDomicilios = "No existe el domicilio con id: "+"7"+" Domicilio no encontrado";
        Assert.assertEquals(resultado.getResponse().getContentAsString(),respuestaDomicilios);
    }
    @Test
    public void eliminarOdontologo() throws Exception{
        //odontologoService.registrarOdontologo(new Odontologo("Baspineiro","Rodolfo",52410));

        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.delete("/odontologos/{id}","1").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();
                //.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String respuestaOdontologos = "Odontologo eliminado";
        Assert.assertEquals(resultado.getResponse().getContentAsString(),respuestaOdontologos);

    }
    @Test
    public void registrarOdontologo() throws Exception{
        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.post("/odontologos").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertEquals(resultado.getResponse().getContentAsString(),400);

    }
}
