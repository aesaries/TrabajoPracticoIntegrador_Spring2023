package ar.com.codoAcodo.proyectoSkyFly.controller;

import ar.com.codoAcodo.proyectoSkyFly.service.VuelosServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
