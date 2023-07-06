package ar.com.codoAcodo.proyectoSkyFly.dto.request;

import ar.com.codoAcodo.proyectoSkyFly.entity.Reservas;
import ar.com.codoAcodo.proyectoSkyFly.enums.FormaDePago;
import ar.com.codoAcodo.proyectoSkyFly.enums.PagoEstado;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.Date;

@Data
public class PagosDto {

    private Long reservaId;

    private FormaDePago formaDePago;
    private Date fecha;
    private PagoEstado estadoDePago;
}
