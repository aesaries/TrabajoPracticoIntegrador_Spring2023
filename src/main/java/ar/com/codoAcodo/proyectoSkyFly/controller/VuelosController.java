package ar.com.codoAcodo.proyectoSkyFly.controller;

import ar.com.codoAcodo.proyectoSkyFly.dto.request.PagosDto;
import ar.com.codoAcodo.proyectoSkyFly.dto.request.ReservaDto;
import ar.com.codoAcodo.proyectoSkyFly.dto.response.RespReservaDto;
import ar.com.codoAcodo.proyectoSkyFly.dto.response.RespVuelosDto;
import ar.com.codoAcodo.proyectoSkyFly.exception.AsientoNotFoundException;
import ar.com.codoAcodo.proyectoSkyFly.exception.UsuarioNotFoundException;
import ar.com.codoAcodo.proyectoSkyFly.service.VuelosServiceImpl;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vuelos")
public class VuelosController {

    VuelosServiceImpl vuelosService;

    public VuelosController(VuelosServiceImpl vuelosService) {

        this.vuelosService = vuelosService;
    }

    @GetMapping("/buscarVuelos")
    public ResponseEntity<?> buscarVuelos(){
        return new ResponseEntity<>(vuelosService.buscarVuelos(), HttpStatus.OK);
    }

//    @PostMapping("/reservas")
//    public ResponseEntity<String> realizarReserva(@RequestBody ReservaDto reservaDto) {
//
//        try {
//            vuelosService.realizarReserva(reservaDto);
//            return ResponseEntity.ok("Reserva realizada con éxito");
//
//        } catch (RuntimeException ex) {
//            // Capturar la excepción personalizada lanzada desde el servicio
//            return handleCustomException(ex);
//        }
//    }

    @PostMapping("/reservas")
    public ResponseEntity<RespReservaDto> realizarReserva(@RequestBody ReservaDto reservaDto){
        return new ResponseEntity<>(vuelosService.realizarReserva(reservaDto), HttpStatus.OK);
    }

    @PostMapping("/pagarReserva")
    public ResponseEntity<?> pagarReserva(@RequestBody PagosDto pagos){
        return new ResponseEntity<>(vuelosService.pagarReserva(pagos), HttpStatus.OK);
    }


}
