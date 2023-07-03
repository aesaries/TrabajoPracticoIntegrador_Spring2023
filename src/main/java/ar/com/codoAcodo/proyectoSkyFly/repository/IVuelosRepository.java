package ar.com.codoAcodo.proyectoSkyFly.repository;

import ar.com.codoAcodo.proyectoSkyFly.entity.Vuelos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IVuelosRepository extends JpaRepository<Vuelos,Long> {


}
