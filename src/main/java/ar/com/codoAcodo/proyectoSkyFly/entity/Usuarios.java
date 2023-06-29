package ar.com.codoAcodo.proyectoSkyFly.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "usuarios")
public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuariosId;

    private String nombre;
    private String apellido;
    private String dni;
    private Date fechaNac;
    private String email;
    private String rol;

    @OneToMany(mappedBy = "usuarios", cascade = CascadeType.ALL, fetch = FetchType.EAGER)//el cascade se encarga de definir que operaciones realizadas sobre un objeto voy a propagar sobre un objeto relacionado, en este caso items.
    private Set<Reservas> reservas;

}
