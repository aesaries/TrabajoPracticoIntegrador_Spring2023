package ar.com.codoAcodo.proyectoSkyFly.dto.request;

import ar.com.codoAcodo.proyectoSkyFly.enums.AsientoDescripcion;
import ar.com.codoAcodo.proyectoSkyFly.enums.AsientoEstado;
import ar.com.codoAcodo.proyectoSkyFly.enums.AsientoTipo;
import lombok.*;

import java.util.List;
import java.util.stream.Collector;


@NoArgsConstructor
@Getter
@Setter
public class AsientosDto {

    private Long vuelosId;
    private Long numeroDeAsiento;
    private AsientoDescripcion descripcion;
    private AsientoTipo tipoDeAsiento;
    private AsientoEstado estadoAsiento;
}
