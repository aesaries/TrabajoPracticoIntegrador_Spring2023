package ar.com.codoAcodo.proyectoSkyFly.service;

import ar.com.codoAcodo.proyectoSkyFly.dto.request.ReservaDto;
import ar.com.codoAcodo.proyectoSkyFly.dto.request.VuelosDto;
import ar.com.codoAcodo.proyectoSkyFly.entity.Asientos;
import ar.com.codoAcodo.proyectoSkyFly.entity.Reservas;
import ar.com.codoAcodo.proyectoSkyFly.entity.Usuarios;
import ar.com.codoAcodo.proyectoSkyFly.entity.Vuelos;
import ar.com.codoAcodo.proyectoSkyFly.enums.EstadoAsiento;
import ar.com.codoAcodo.proyectoSkyFly.exception.AsientoNotFoundException;
import ar.com.codoAcodo.proyectoSkyFly.exception.UsuarioNotFoundException;
import ar.com.codoAcodo.proyectoSkyFly.exception.VueloNotFoundException;
import ar.com.codoAcodo.proyectoSkyFly.repository.IAsientosRepository;
import ar.com.codoAcodo.proyectoSkyFly.repository.IReservasRepository;
import ar.com.codoAcodo.proyectoSkyFly.repository.IUsuariosRepository;
import ar.com.codoAcodo.proyectoSkyFly.repository.IVuelosRepository;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.rmi.server.LogStream.log;

@Slf4j
@Service
public class VuelosServiceImpl implements IVuelosService {


    IVuelosRepository vuelosRepository;

    public VuelosServiceImpl(IVuelosRepository vuelosRepository) {
        this.vuelosRepository = vuelosRepository;
    }

    @Autowired
    IReservasRepository reservasRepository;
    @Autowired
    IUsuariosRepository usuariosRepository;
    @Autowired
    IAsientosRepository asientosRepository;


    @Override
    public List<VuelosDto> buscarVuelos() {

        ModelMapper mapper = new ModelMapper();//creamos un ModelMapper(debemos tener la dependencia en el pom). La clase ModelMapper nos permite transformar un objeto relacional en un objeto java

        List<Vuelos> vuelosEnt = vuelosRepository.findAll();//creamos una lista de cart,que es una clase del tipo entidad, por ende es una lista de entidades. la vamos a crear por medio del findAll. buscamos en el repositorio mediante el findAll todos los carritos entidades

        List<VuelosDto> vuelosDto = new ArrayList<>();

        vuelosEnt.stream()
                .forEach(c-> vuelosDto.add(mapper.map(c,VuelosDto.class)));

        return vuelosDto;
    }


    @Override
    public void realizarReserva(ReservaDto reservaDto) {
        Asientos asiento = null;

        Usuarios usuario = usuariosRepository.findById(reservaDto.getUsuarioId())
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no Existe !!"));

        Vuelos vuelo = vuelosRepository.findById(reservaDto.getVueloId())
                .orElseThrow(() -> new VueloNotFoundException("Vuelo No Encontrado"));

        List<Asientos> listaAsientos = asientosRepository.findAll();

        Optional<Asientos> oAsiento = listaAsientos.stream()
                .filter(a-> a.getNumeroDeAsiento()==reservaDto.getNumeroDeAsiento())
                .filter(a-> a.getVuelos().equals(vuelo))
                .findFirst();

        if (oAsiento.isPresent()){
            asiento = oAsiento.get();
        }else{
            asiento = oAsiento.orElseThrow(()-> new AsientoNotFoundException("Asiento no encontrado"));
        }

        log.info(asiento.toString());
        if(asiento.getEstadoAsiento().equals(EstadoAsiento.LIBRE)){
            Reservas reserva = new Reservas();
            reserva.setFormaDePago(reservaDto.getFormaDePago());
            reserva.setCategoria(asiento.getTipoDeAsiento().name());
            reserva.setCostoTotal(vuelo.getPrecio());
            reserva.setVuelos(vuelo);
            reserva.setUsuarios(usuario);

            reservasRepository.save(reserva);

            //cambio el estado del asiento que de libre a vendido
            asiento.setEstadoAsiento(EstadoAsiento.VENDIDO);
            asientosRepository.save(asiento);



        }else{
            throw new RuntimeException("El Asiento ya se encuentra vendido");
        }








        /*
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaSalida = LocalDate.parse(reservaDto.getFechaSalida(),formatter);

       log.info(reservaDto.getNombre());
       log.info(fechaSalida.toString());
*/



    }
}
