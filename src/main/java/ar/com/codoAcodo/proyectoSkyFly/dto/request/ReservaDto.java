package ar.com.codoAcodo.proyectoSkyFly.dto.request;


import ar.com.codoAcodo.proyectoSkyFly.entity.Asientos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ReservaDto {
    private Long usuarioId;
    private Long vueloId;
    private List<Integer> listaAsientos;


}
