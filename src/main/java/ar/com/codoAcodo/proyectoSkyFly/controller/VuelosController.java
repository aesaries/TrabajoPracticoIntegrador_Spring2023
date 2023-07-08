package ar.com.codoAcodo.proyectoSkyFly.controller;

import ar.com.codoAcodo.proyectoSkyFly.dto.request.PagosDto;
import ar.com.codoAcodo.proyectoSkyFly.dto.request.ReservaDto;
import ar.com.codoAcodo.proyectoSkyFly.dto.response.RespPagosDto;
import ar.com.codoAcodo.proyectoSkyFly.dto.response.RespReservaDto;
import ar.com.codoAcodo.proyectoSkyFly.service.VuelosServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path ="/vuelos")
public class VuelosController {

    VuelosServiceImpl vuelosService;


    public VuelosController(VuelosServiceImpl vuelosService) {

        this.vuelosService = vuelosService;
    }

    @GetMapping("/buscarVuelos")
    public ResponseEntity<?> buscarVuelos(){
        return new ResponseEntity<>(vuelosService.buscarVuelos(), HttpStatus.OK);
    }



    @PostMapping("/reservas")
    public ResponseEntity<RespReservaDto> realizarReserva(@RequestBody ReservaDto reservaDto){
        return new ResponseEntity<>(vuelosService.realizarReserva(reservaDto), HttpStatus.OK);
    }

    @PostMapping(value = "/pagarReserva")
    public ResponseEntity<RespPagosDto> pagarReserva(@RequestBody PagosDto pagosDto){

        return new ResponseEntity<>(vuelosService.pagarReserva(pagosDto), HttpStatus.OK);
    }

    @GetMapping("/verAsientos")
    public ResponseEntity<?> verAsientos(){
        return new ResponseEntity<>(vuelosService.verAsientos(), HttpStatus.OK);
    }



}
