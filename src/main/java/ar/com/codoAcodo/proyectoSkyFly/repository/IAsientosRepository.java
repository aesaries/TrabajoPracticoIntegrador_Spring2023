package ar.com.codoAcodo.proyectoSkyFly.repository;

import ar.com.codoAcodo.proyectoSkyFly.entity.Asientos;
import ar.com.codoAcodo.proyectoSkyFly.entity.Vuelos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IAsientosRepository extends JpaRepository<Asientos,Long> {
// List<Asientos> findByNumeroDeAsiento(int num);

// Optional<Asientos> findByVuelosAndNumeroDeAsiento(Vuelos v, int na);


}

