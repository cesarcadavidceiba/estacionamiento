package co.com.ceiba.estacionamiento.excepciones;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { EstacionamientoCarrosLlenoExcepcion.class, EstacionamientoMotosLlenoExcepcion.class,
			PosicionEstacionamientoOcupadaExcepcion.class, VehiculoMotoSinCilindrajeExcepcion.class,
			VehiculoNoPuedeIngresarExcepcion.class, VehiculoNoSeEncuentraEstacionadoExcepcion.class,
			VehiculoSeEncuentraEstacionadoExcepcion.class })
	protected ResponseEntity<Object> hadleConflict(RuntimeException ex, WebRequest request) {
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

}
