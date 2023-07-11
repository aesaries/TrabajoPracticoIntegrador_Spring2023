package ar.com.codoAcodo.proyectoSkyFly.service;


import ar.com.codoAcodo.proyectoSkyFly.dto.response.RespReservaDto;
import ar.com.codoAcodo.proyectoSkyFly.entity.Reservas;
import ar.com.codoAcodo.proyectoSkyFly.entity.Usuarios;
import ar.com.codoAcodo.proyectoSkyFly.enums.UsuarioRol;
import ar.com.codoAcodo.proyectoSkyFly.exception.UsuarioNotFoundException;
import ar.com.codoAcodo.proyectoSkyFly.repository.IReservasRepository;
import ar.com.codoAcodo.proyectoSkyFly.repository.IUsuariosRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UsuarioServiceImpl implements IUsuarioService{
    Usuarios usuario;
    ModelMapper mapper = new ModelMapper();
    @Autowired
    IUsuariosRepository usuariosRepository;

    @Autowired
    IReservasRepository reservasRepository;

    @Override
    public List<RespReservaDto> verReservas(Long agenteId, Long clienteId) {

        chequeoDeTipoDeUsuarioYRol(agenteId,UsuarioRol.AGENTE);

        chequeoDeTipoDeUsuarioYRol(clienteId, UsuarioRol.CLIENTE);

        List<Reservas>listaReserva= reservasRepository.findAll();



        List<RespReservaDto> listaReservasDto = new ArrayList<>();

        for (Reservas r: listaReserva) {
            if (Objects.equals(r.getUsuarios().getUsuariosId(), clienteId)) {
                RespReservaDto respReservaDto = new RespReservaDto();

                respReservaDto.setReservaId(r.getReservasId());
                respReservaDto.setFechaReserva(r.getFechaReserva().toString());
                respReservaDto.setPrecioUnitario(r.getVuelos().getPrecio());
                respReservaDto.setMontoAPagar(r.getCostoTotal());

                listaReservasDto.add(mapper.map(respReservaDto, RespReservaDto.class));
            }
        }
        return listaReservasDto;
    }

    private void chequeoDeTipoDeUsuarioYRol(Long id, UsuarioRol usuarioRol) {
        Usuarios usu = usuariosRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException( usuarioRol.toString() + "  No Existe!"   ));

        boolean esUsuarioValido;

        if (usuarioRol.equals(UsuarioRol.AGENTE)){
            esUsuarioValido = (usu.getRol().equals(usuarioRol)) ||
                    (usu.getRol().equals(UsuarioRol.ADMIN));
        }else{
            esUsuarioValido = usu.getRol().equals(usuarioRol);
            usuario = usu;
        }

        if (!esUsuarioValido){throw new RuntimeException("No es un rol Valido " + usuarioRol);}

    }

}
