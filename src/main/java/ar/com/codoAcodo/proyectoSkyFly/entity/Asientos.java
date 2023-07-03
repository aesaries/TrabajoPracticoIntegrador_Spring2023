package ar.com.codoAcodo.proyectoSkyFly.entity;

import ar.com.codoAcodo.proyectoSkyFly.enums.DescripcionAsiento;
import ar.com.codoAcodo.proyectoSkyFly.enums.EstadoAsiento;
import ar.com.codoAcodo.proyectoSkyFly.enums.TipoAsiento;
import jakarta.persistence.*;
import lombok.Data;


@Entity @Data
@Table(name = "asientos")
public class Asientos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long asientosId;

    @Column(name = "numero_asiento")
    private int numeroDeAsiento;

    @Enumerated(value = EnumType.ORDINAL)
    private EstadoAsiento estadoAsiento;
    @Enumerated(value = EnumType.ORDINAL)
    private TipoAsiento tipoDeAsiento;
    @Enumerated(value = EnumType.ORDINAL)
    private DescripcionAsiento descripcion;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "vuelos_id", nullable = false)
    private Vuelos vuelos;

}
