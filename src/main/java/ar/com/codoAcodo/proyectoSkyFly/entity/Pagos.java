package ar.com.codoAcodo.proyectoSkyFly.entity;

import ar.com.codoAcodo.proyectoSkyFly.enums.PagoEstado;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Date;
import java.util.Objects;

@Entity
@Setter
@RequiredArgsConstructor
@Table(name = "pagos")
public class Pagos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pagosId;

    private Date fecha;
    @Enumerated(value = EnumType.STRING)
    private PagoEstado estadoDePago;

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "reservas_id", referencedColumnName = "reservasId")//JoinColumn me genera la FK en la entidad pagos
    private Reservas reservas;



}
