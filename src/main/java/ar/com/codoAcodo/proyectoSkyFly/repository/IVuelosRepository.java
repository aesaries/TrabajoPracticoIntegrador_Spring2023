package ar.com.codoAcodo.proyectoSkyFly.repository;

import ar.com.codoAcodo.proyectoSkyFly.entity.Vuelos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVuelosRepository extends JpaRepository<Vuelos,Long> {
}
