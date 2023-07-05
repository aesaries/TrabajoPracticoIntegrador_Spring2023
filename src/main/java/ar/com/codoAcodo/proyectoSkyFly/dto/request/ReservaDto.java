package ar.com.codoAcodo.proyectoSkyFly.dto.request;

import ar.com.codoAcodo.proyectoSkyFly.entity.Usuarios;
import ar.com.codoAcodo.proyectoSkyFly.entity.Vuelos;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ReservaDto {
    private Long usuarioId;
    private Long vueloId;
    private int numeroDeAsiento;
    private String formaDePago;

}
