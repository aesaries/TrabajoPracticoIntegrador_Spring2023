package ar.com.codoAcodo.proyectoSkyFly.service;
import ar.com.codoAcodo.proyectoSkyFly.dto.request.VuelosDto;
import ar.com.codoAcodo.proyectoSkyFly.repository.IReservasRepository;
import ar.com.codoAcodo.proyectoSkyFly.repository.IVuelosRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@SpringBootTest(classes = ProyectoSkyFlyApplication.class)
//@SpringJUnitConfig
@SpringBootTest
@TestPropertySource(properties = {"SCOPE = integration_test"})
public class VuelosServiceTest {

    @Autowired
    IVuelosService vuelosService;

    @Autowired
    IVuelosRepository vuelosRepository;

    @Autowired
    IReservasRepository reservasRepository;

    @Test
    @DisplayName("Camino Feliz buscar vuelos...")
    void buscarVuelosOkTest(){

        //ARRANGE
        List<VuelosDto> expected = new ArrayList<>();
        expected.add(new VuelosDto("Avianca","Av180","Buenos Aires","Rio de Janeiro", LocalDateTime.parse("2023-06-29T18:00:00"),LocalDateTime.parse("2023-06-30T09:00:00"),400.00,false));
        expected.add(new VuelosDto("Aerolineas Argentinas","Ar200","Buenos Aires","Cancun",LocalDateTime.parse("2023-06-28T18:00:00"),LocalDateTime.parse("2023-06-29T21:30:00"),500.00,false));
        expected.add(new VuelosDto("Panam","Pan150","Buenos Aires","Madrid",LocalDateTime.parse("2023-06-27T18:00:00"),LocalDateTime.parse("2023-06-29T21:30:00"),900.00,true));

        //ACT
        List<VuelosDto> result = vuelosService.buscarVuelos();
        //ASSERT
        assertEquals(expected,result);
    }

//    @Test
//    @DisplayName("Camino Feliz ver asientos libres...")
//    void verAsientosLibresOkTest(){
//
//        //ARRANGE
//        List<AsientosDto> expected = new ArrayList<>();
//        expected.add(new ));;
//
//        //ACT
//        List<AsientosDto> result = vuelosService.verAsientosLibres(3L);
//        //ASSERT
//        assertEquals(expected,result);
//    }

}