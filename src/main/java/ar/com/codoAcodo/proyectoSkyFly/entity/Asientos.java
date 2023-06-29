package ar.com.codoAcodo.proyectoSkyFly.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "asientos")
public class Asientos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long asientosId;

    private Boolean vendido;
    private int tipoDeAsiento;
    private String descripcion;



    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "vuelos_id", nullable = false)
    private Vuelos vuelos;

}
