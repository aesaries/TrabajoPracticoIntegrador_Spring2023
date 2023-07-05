package ar.com.codoAcodo.proyectoSkyFly.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity @Data
@Table(name = "reservas")
public class Reservas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservasId;
    private String formaDePago;
    private String categoria;
    @Column(name = "fecha_reserva", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaReserva;
    private Double costoTotal;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "usuarios_id", nullable = false)//JoinColumn me genera la FK en la entidad reservas
    private Usuarios usuarios;
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "vuelos_id", referencedColumnName = "vuelosId")//JoinColumn me genera la FK en la entidad reservas
    private Vuelos vuelos;

    @OneToOne(mappedBy = "reservas")
    private Pagos pagos;

}
