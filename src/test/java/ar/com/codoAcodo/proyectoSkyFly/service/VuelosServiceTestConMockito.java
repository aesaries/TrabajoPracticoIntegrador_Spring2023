package ar.com.codoAcodo.proyectoSkyFly.service;

import ar.com.codoAcodo.proyectoSkyFly.dto.request.PagosDto;
import ar.com.codoAcodo.proyectoSkyFly.dto.response.RespPagosDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class VuelosServiceTestConMockito {


    @Mock
    private VuelosServiceImpl vuelosService;


    @Test
    @DisplayName("Camino feliz pagar reserva")
    public void pagarReservaIntTestOk() throws Exception {
        // Arrange
        PagosDto pagosDto = new PagosDto(1L,"TRANSFERENCIA");
        RespPagosDto expected = new RespPagosDto();
        expected.setAereolinea("Avianca");
        expected.setNumeroVuelo("Av180");
        expected.setCiudadOrigen("Buenos Aires");
        expected.setCiudadDestino("Rio de Janeiro");
        expected.setTotalAPagar(400.00);
        expected.setPagos(pagosDto);
        expected.setMensaje("Su pago con " + pagosDto.getFormaDePago() + " ha sido aceptada");

        when(vuelosService.pagarReserva(any(PagosDto.class))).thenReturn(expected);

        // Act
        RespPagosDto act = vuelosService.pagarReserva(pagosDto);

        // Assert
        assertEquals(expected,act);
    }


}
