package ar.com.codoAcodo.proyectoSkyFly.dto.response;

import ar.com.codoAcodo.proyectoSkyFly.dto.request.VuelosDto;
import lombok.*;

@Setter
@Getter
public class RespVuelosDto {
    private VuelosDto vuelos;
    private String mensaje;
}
