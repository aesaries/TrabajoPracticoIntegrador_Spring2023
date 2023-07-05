package ar.com.codoAcodo.proyectoSkyFly.dto.response;

import ar.com.codoAcodo.proyectoSkyFly.dto.request.ReservaDto;
import ar.com.codoAcodo.proyectoSkyFly.dto.request.VuelosDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RespReservaDto {

    private String mensaje;
    private ReservaDto datos_de_reserva;
    private String fechaReserva;

    private Long reservaId;

    public RespReservaDto(String mensaje) {
        this.mensaje = mensaje;
    }
}
