package ar.com.codoAcodo.proyectoSkyFly.entity;

import ar.com.codoAcodo.proyectoSkyFly.enums.FormaDePago;
import ar.com.codoAcodo.proyectoSkyFly.enums.PagoEstado;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

import java.util.Date;
@Data
@Entity
@Table(name = "pagos")
public class Pagos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pagosId;

    @Enumerated(value = EnumType.STRING)
    private FormaDePago formaDePago;

    private Date fecha;

    @Enumerated(value = EnumType.STRING)
    private PagoEstado estadoDePago;

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "reservas_id", referencedColumnName = "reservasId")//JoinColumn me genera la FK en la entidad pagos
    private Reservas reservas;



}
