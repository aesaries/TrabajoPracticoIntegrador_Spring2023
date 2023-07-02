package ar.com.codoAcodo.proyectoSkyFly.dto.request;

import ar.com.codoAcodo.proyectoSkyFly.entity.Usuarios;
import ar.com.codoAcodo.proyectoSkyFly.entity.Vuelos;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ReservaDto {
    private String nombre;
    private String apellido;
    private String email;
    private String rol;
    private String numeroVuelo;
    private String ciudadOrigen;
    private String ciudadDestino;
    private String fechaSalida;
    private Long numAsiento;


}
