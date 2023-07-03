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

        } catch (RuntimeException ex) {
            // Capturar la excepción personalizada lanzada desde el servicio
            return handleCustomException(ex);
        }
    }




    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleCustomException(RuntimeException ex) {
        // Obtener el mensaje de la excepción
        String mensaje = ex.getMessage();

        // Devolver una respuesta con el mensaje de la excepción
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensaje);
    }
}
