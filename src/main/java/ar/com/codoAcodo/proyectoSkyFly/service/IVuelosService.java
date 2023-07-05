package ar.com.codoAcodo.proyectoSkyFly.service;

import ar.com.codoAcodo.proyectoSkyFly.dto.request.ReservaDto;
import ar.com.codoAcodo.proyectoSkyFly.dto.request.VuelosDto;
import ar.com.codoAcodo.proyectoSkyFly.dto.response.RespReservaDto;

import java.util.List;

public interface IVuelosService {

    List<VuelosDto> buscarVuelos();

    RespReservaDto realizarReserva(ReservaDto reservaDto);


}
