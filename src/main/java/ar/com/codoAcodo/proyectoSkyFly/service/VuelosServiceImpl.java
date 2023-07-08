package ar.com.codoAcodo.proyectoSkyFly.service;

import ar.com.codoAcodo.proyectoSkyFly.dto.request.AsientosDto;
import ar.com.codoAcodo.proyectoSkyFly.dto.request.PagosDto;
import ar.com.codoAcodo.proyectoSkyFly.dto.request.ReservaDto;
import ar.com.codoAcodo.proyectoSkyFly.dto.request.VuelosDto;
import ar.com.codoAcodo.proyectoSkyFly.dto.response.RespPagosDto;
import ar.com.codoAcodo.proyectoSkyFly.dto.response.RespReservaDto;
import ar.com.codoAcodo.proyectoSkyFly.entity.*;
import ar.com.codoAcodo.proyectoSkyFly.enums.AsientoEstado;
import ar.com.codoAcodo.proyectoSkyFly.enums.PagoEstado;
import ar.com.codoAcodo.proyectoSkyFly.enums.UsuarioRol;
import ar.com.codoAcodo.proyectoSkyFly.exception.AsientoNotFoundException;
import ar.com.codoAcodo.proyectoSkyFly.exception.PagoNotFoundException;
import ar.com.codoAcodo.proyectoSkyFly.exception.UsuarioNotFoundException;
import ar.com.codoAcodo.proyectoSkyFly.exception.VueloNotFoundException;
import ar.com.codoAcodo.proyectoSkyFly.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @Autowired
    IPagosRepository pagosRepository;


    Asientos asiento;
    Usuarios usuario;
    Vuelos vuelo;

    Reservas reserva;


    ModelMapper mapper = new ModelMapper();//creamos un ModelMapper(debemos tener la dependencia en el pom).La clase ModelMapper nos permite transformar un objeto relacional en un objeto java

    @Override
    public List<VuelosDto> buscarVuelos() {

        List<Vuelos> vuelosEnt = vuelosRepository.findAll();//creamos una lista de vuelos,que es una clase del tipo entidad, por ende es una lista de entidades. la vamos a crear por medio del findAll. buscamos en el repositorio mediante el findAll todos los vuelos entidades

        List<VuelosDto> vuelosDto = new ArrayList<>();

        vuelosEnt.forEach(c-> vuelosDto.add(mapper.map(c,VuelosDto.class)));

        return vuelosDto;
    }


    @Override
    public RespReservaDto realizarReserva(ReservaDto reservaDto) {

        usuario = checkExisteYRol(reservaDto.getUsuarioId());

        vuelo = checkExisteVuelo(reservaDto.getVueloId());

        boolean asientoLibre = existeAsientoYEstaLIbre(reservaDto.getNumeroDeAsiento());

        if(asientoLibre){

            Reservas reserva = guardaReserva();

            //cambio el estado del asiento que de libre a vendido
            cambiaEstadoDelAsientoaVendido();


            //Se genera un registro en PAGOS con el estado "Pendiente" d
            generaPagoPendiente(reserva);

            return new RespReservaDto("La reserva se realizo con exito",
                    reservaDto,LocalDateTime.now().toString(),
                    reserva.getReservasId(),
                    reserva.getCostoTotal());
        }else{
            throw new AsientoNotFoundException("el asiento ya se encuentra vendido");
        }
    }


    private void generaPagoPendiente(Reservas reserva) {
        Pagos pago = new Pagos();
        pago.setEstadoDePago(PagoEstado.PENDIENTE);
        pago.setReservas(reserva);
        pagosRepository.save(pago);
    }

    private Reservas guardaReserva() {
        Reservas reserva = new Reservas();

        reserva.setCategoria(asiento.getTipoDeAsiento().name());
        reserva.setCostoTotal(vuelo.getPrecio());
        reserva.setVuelos(vuelo);
        reserva.setUsuarios(usuario);
        reserva.setFechaReserva(LocalDateTime.now());

        reservasRepository.save(reserva);

        return reserva;
    }

    private void cambiaEstadoDelAsientoaVendido() {
        asiento.setEstadoAsiento(AsientoEstado.VENDIDO);
        asientosRepository.save(asiento);
    }

    private Usuarios checkExisteYRol(Long id){
    Usuarios usu = usuariosRepository.findById(id)
            .orElseThrow(() -> new UsuarioNotFoundException("Usuario no Existe !!"));

    boolean esCliente = usu.getRol().equals(UsuarioRol.CLIENTE);

    if (!esCliente){throw new RuntimeException("No es un rol Valido");}

    return usu;
        //falta verificar el rol
    }

    private Vuelos checkExisteVuelo(Long id){
        return vuelosRepository.findById(id)
                .orElseThrow(() -> new VueloNotFoundException("Vuelo No Encontrado"));
    }

    private boolean existeAsientoYEstaLIbre(int numeroAsiento){

        List<Asientos> listaAsientos = asientosRepository.findAll();

        Optional<Asientos> oAsiento = listaAsientos.stream()
                .filter(a-> a.getNumeroDeAsiento()==numeroAsiento)
                .filter(a-> a.getVuelos().equals(vuelo))
                .findFirst();

        asiento = oAsiento.orElseGet(() ->
                oAsiento.orElseThrow(() ->
                        new AsientoNotFoundException("Asiento no encontrado")));
        //log.info(asiento.toString());
        return asiento.getEstadoAsiento().equals(AsientoEstado.LIBRE);
    }

    private Reservas checkExisteReserva(Long id){
        return reservasRepository.findById(id)
                .orElseThrow(() -> new VueloNotFoundException("Reserva No Encontrado"));
    }

    @Override
    public RespPagosDto pagarReserva(PagosDto pagosDto) {
        RespPagosDto respPagosDto = new RespPagosDto();
        reserva = checkExisteReserva(pagosDto.getReservaId());

        Pagos pagoAConfirmar = pagosRepository.findById
               (reserva.getPagos().getPagosId())
               .orElseThrow(()->new PagoNotFoundException("Pago No encontrado"));

        if (pagoAConfirmar.getEstadoDePago().equals(PagoEstado.PENDIENTE)){
            respPagosDto.setMensaje("Su pago con " + pagosDto.getFormaDePago() + "ha sido aceptada");
            respPagosDto.setAereolinea(pagoAConfirmar.getReservas().getVuelos().getAerolinea());
            respPagosDto.setNumeroVuelo(pagoAConfirmar.getReservas().getVuelos().getNumeroVuelo());
            respPagosDto.setCiudadOrigen(pagoAConfirmar.getReservas().getVuelos().getCiudadOrigen());
            respPagosDto.setCiudadDestino(pagoAConfirmar.getReservas().getVuelos().getCiudadDestino());

            respPagosDto.setPagos(pagosDto);
            respPagosDto.setTotalAPagar(pagoAConfirmar.getReservas().getVuelos().getPrecio());

            pagoAConfirmar.setEstadoDePago(PagoEstado.CONFIRMADO);
            pagosRepository.save(pagoAConfirmar);

        }
        else{

            throw new PagoNotFoundException("Ya esta Pagado !!! no insista");
        }
        return mapper.map(respPagosDto, RespPagosDto.class);

    }

    @Override
    public List<AsientosDto> verAsientos() {
        List<Asientos> asientosEnt = asientosRepository.findAll(); // Obtener asientos del vuelo específico

        List<AsientosDto> asientosDto = new ArrayList<>();

        asientosEnt.forEach(c -> asientosDto.add(mapper.map(c, AsientosDto.class)));

        return asientosDto;
    }

//    @Override
//    public List<AsientosDto> verAsientosDisp(@RequestParam Long vuelosId) {
//        Optional<Asientos> asientosEnt = asientosRepository.findById(vuelosId); // Obtener asientos del vuelo específico
//
//        List<AsientosDto> asientosDto = new ArrayList<>();
//
//        asientosEnt.forEach(c -> asientosDto.add(mapper.map(c, AsientosDto.class)));
//
//        return asientosDto;
//    }

//    @Override
//    public List<AsientosDto> verAsientosDisp(@RequestParam Long vuelosId) {
//        Optional<Asientos> asientosEntOptional = asientosRepository.findById(vuelosId); // Obtener asientos del vuelo específico
//
//        List<AsientosDto> asientosDto = new ArrayList<>();
//
//        asientosEntOptional.ifPresent(asientosEnt -> {
//            AsientosDto asientosDto = mapper.map(asientosEnt, AsientosDto.class);
//            asientosDto.add(asientosDto);
//        });
//
//        return asientosDto;
//    }

}
