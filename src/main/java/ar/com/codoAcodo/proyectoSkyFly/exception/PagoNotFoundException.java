package ar.com.codoAcodo.proyectoSkyFly.exception;

public class PagoNoFoundException extends RuntimeException{
    public PagoNoFoundException (String msg){
        super(msg);
    }
}
