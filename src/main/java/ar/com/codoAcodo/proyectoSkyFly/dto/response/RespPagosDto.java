package ar.com.codoAcodo.proyectoSkyFly.dto.response;

import ar.com.codoAcodo.proyectoSkyFly.dto.request.PagosDto;
import ar.com.codoAcodo.proyectoSkyFly.dto.request.VuelosDto;

public class RespPagosDto{
        private PagosDto pagos;
        private String mensaje;

    public RespPagosDto(String mensaje) {
        this.mensaje = mensaje;
    }
}
