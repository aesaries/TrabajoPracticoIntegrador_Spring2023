package ar.com.codoAcodo.proyectoSkyFly.service;

import ar.com.codoAcodo.proyectoSkyFly.dto.request.ReservaDto;
import ar.com.codoAcodo.proyectoSkyFly.dto.request.VuelosDto;

import java.util.List;

public interface IVuelosService {

    List<VuelosDto> buscarVuelos();

    void realizarReserva(ReservaDto reservaDto);


}
