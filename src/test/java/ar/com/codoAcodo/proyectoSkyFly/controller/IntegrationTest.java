package ar.com.codoAcodo.proyectoSkyFly.controller;

import ar.com.codoAcodo.proyectoSkyFly.dto.request.ReservaDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(properties = {"SCOPE = integration_test"})
public class IntegrationTest {
    @Autowired
    MockMvc mockMvc;


    @Test
    @DisplayName("Camino feliz buscar vuelos")
    void buscarVuelosIntTestOk()throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8080/vuelos/buscarVuelos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].aerolinea").value("Avianca"))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("camino feliz realizar reserva")
    public void realizarReservaIntTestOk() throws Exception {

        ReservaDto reservaDto = new ReservaDto(1L,1L, Arrays.asList(20,21));

        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE,false)
                .writer();

        String jsonPayload = writer.writeValueAsString(reservaDto);

        mockMvc.perform(post("http://localhost:8080/vuelos/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reservaId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensaje").value("La reserva se realizo con exito"));
                }

    @Test
    @DisplayName("Camino feliz ver asientos")
    public void verAsientosIntTestOk()throws Exception{
        mockMvc.perform(get("http://localhost:8080/vuelos/verAsientos")
                        .param("vuelosId", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].numeroDeAsiento").value(20));

    }
}
//    @Test
//    public void pagarReservaIntTestOk() throws Exception {
//
//        PagosDto pagosDto = new PagosDto(1L,"TRANSFERENCIA");
//
//        ObjectWriter writer = new ObjectMapper()
//                .configure(SerializationFeature.WRAP_ROOT_VALUE,false)
//                .writer();
//
//        String jsonPayload = writer.writeValueAsString(pagosDto);
//
//        mockMvc.perform(post("http://localhost:8080/vuelos/pagarReserva")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonPayload))
//                .andDo(print())
//                .andExpect(content().contentType("text/plain;charset=UTF-8"))
//
//                  .andExpect(MockMvcResultMatchers.jsonPath("$.mensaje").value("Su pago con " + pagosDto.getFormaDePago() + " ha sido aceptada"))
//                  .andExpect(MockMvcResultMatchers.jsonPath("$.formaDePago").value("TRANSFERENCIA"))
//                  .andExpect(MockMvcResultMatchers.jsonPath("$.reservaId").value(1L));
//                  .andExpect(MockMvcResultMatchers.content().string("Su pago con " + pagosDto.getFormaDePago() + " ha sido aceptada"));
//
//
//    }




