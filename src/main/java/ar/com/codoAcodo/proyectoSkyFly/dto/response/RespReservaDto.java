package ar.com.codoAcodo.proyectoSkyFly.dto.response;

import ar.com.codoAcodo.proyectoSkyFly.dto.request.ReservaDto;
import ar.com.codoAcodo.proyectoSkyFly.entity.Vuelos;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RespReservaDto {

    private String mensaje;
    private ReservaDto datos_de_reserva;
    private String fechaReserva;
    private Long reservaId;
    private Double montoAPagar;
}
