package ar.com.codoAcodo.proyectoSkyFly.service;

import ar.com.codoAcodo.proyectoSkyFly.dto.request.ReservaDto;
import ar.com.codoAcodo.proyectoSkyFly.dto.request.VuelosDto;
import ar.com.codoAcodo.proyectoSkyFly.entity.Asientos;
import ar.com.codoAcodo.proyectoSkyFly.entity.Reservas;
import ar.com.codoAcodo.proyectoSkyFly.entity.Usuarios;
import ar.com.codoAcodo.proyectoSkyFly.entity.Vuelos;
import ar.com.codoAcodo.proyectoSkyFly.enums.AsientoEstado;
import ar.com.codoAcodo.proyectoSkyFly.enums.UsuarioRol;
import ar.com.codoAcodo.proyectoSkyFly.exception.AsientoNotFoundException;
import ar.com.codoAcodo.proyectoSkyFly.exception.UsuarioNotFoundException;
import ar.com.codoAcodo.proyectoSkyFly.exception.VueloNotFoundException;
import ar.com.codoAcodo.proyectoSkyFly.repository.IAsientosRepository;
import ar.com.codoAcodo.proyectoSkyFly.repository.IReservasRepository;
import ar.com.codoAcodo.proyectoSkyFly.repository.IUsuariosRepository;
import ar.com.codoAcodo.proyectoSkyFly.repository.IVuelosRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    Asientos asiento;
    Usuarios usuario;
    Vuelos vuelo;



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


        usuario = checkExisteYRol(reservaDto.getUsuarioId());

        vuelo = checkExisteVuelo(reservaDto.getVueloId());


        boolean asientoLibre = existeAsientoYEstaLIbre(reservaDto.getNumeroDeAsiento());

        if(asientoLibre){
            Reservas reserva = new Reservas();
            reserva.setFormaDePago(reservaDto.getFormaDePago());
            reserva.setCategoria(asiento.getTipoDeAsiento().name());
            reserva.setCostoTotal(vuelo.getPrecio());
            reserva.setVuelos(vuelo);
            reserva.setUsuarios(usuario);

            reservasRepository.save(reserva);

            //cambio el estado del asiento que de libre a vendido
            asiento.setEstadoAsiento(AsientoEstado.VENDIDO);
            asientosRepository.save(asiento);



        }else{
            throw new RuntimeException("El Asiento ya se encuentra vendido");
        }


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

        if (oAsiento.isPresent()){
            asiento = oAsiento.get();

        }else{
            asiento = oAsiento.orElseThrow(()-> new AsientoNotFoundException("Asiento no encontrado"));
        }
        //log.info(asiento.toString());
        return asiento.getEstadoAsiento().equals(AsientoEstado.LIBRE);
    }
}
