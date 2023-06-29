package ar.com.codoAcodo.proyectoSkyFly.service;

import ar.com.codoAcodo.proyectoSkyFly.dto.request.VuelosDto;
import ar.com.codoAcodo.proyectoSkyFly.entity.Vuelos;
import ar.com.codoAcodo.proyectoSkyFly.repository.IVuelosRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VuelosServiceImpl implements IVuelosService {

    IVuelosRepository vuelosRepository;

    public VuelosServiceImpl(IVuelosRepository vuelosRepository) {
        this.vuelosRepository = vuelosRepository;
    }

    @Override
    public List<VuelosDto> buscarVuelos() {

        ModelMapper mapper = new ModelMapper();//creamos un ModelMapper(debemos tener la dependencia en el pom). La clase ModelMapper nos permite transformar un objeto relacional en un objeto java

        List<Vuelos> vuelosEnt = vuelosRepository.findAll();//creamos una lista de cart,que es una clase del tipo entidad, por ende es una lista de entidades. la vamos a crear por medio del findAll. buscamos en el repositorio mediante el findAll todos los carritos entidades

        List<VuelosDto> vuelosDto = new ArrayList<>();

        vuelosEnt.stream()
                .forEach(c-> vuelosDto.add(mapper.map(c,VuelosDto.class)));

        return vuelosDto;
    }
}
