package ar.com.codoAcodo.proyectoSkyFly.service;

import ar.com.codoAcodo.proyectoSkyFly.dto.request.ReservaDto;
import ar.com.codoAcodo.proyectoSkyFly.dto.response.RespReservaDto;

import java.util.List;

public interface IUsuarioService {

    List<RespReservaDto> verReservas(Long agenteId, Long clienteId);

}
