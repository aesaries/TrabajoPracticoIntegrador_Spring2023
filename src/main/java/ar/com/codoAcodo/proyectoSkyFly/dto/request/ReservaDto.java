package ar.com.codoAcodo.proyectoSkyFly.dto.request;

import ar.com.codoAcodo.proyectoSkyFly.entity.Usuarios;
import ar.com.codoAcodo.proyectoSkyFly.entity.Vuelos;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class ReservaDto {
    private Long usuarioId;
    private Long vueloId;
    private List<Integer> listaAsientos;


}
