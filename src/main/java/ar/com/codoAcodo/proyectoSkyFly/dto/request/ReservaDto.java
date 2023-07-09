package ar.com.codoAcodo.proyectoSkyFly.dto.request;

import lombok.*;

@Getter
@Setter
public class ReservaDto {

    private Long usuarioId;
    private Long vueloId;
    private int numeroDeAsiento;
}
