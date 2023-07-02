package ar.com.codoAcodo.proyectoSkyFly.controller;

import ar.com.codoAcodo.proyectoSkyFly.dto.request.ReservaDto;
import ar.com.codoAcodo.proyectoSkyFly.service.VuelosServiceImpl;
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

    @PostMapping("/reservas")
    public ResponseEntity<String> realizarReserva(@RequestBody ReservaDto reservaDto) {

        try {

            vuelosService.realizarReserva(reservaDto);
            return ResponseEntity.ok("Reserva realizada con éxito");

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al realizar la reserva");
        }
    }
}
