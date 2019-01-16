package co.com.ceiba.estacionamiento.util;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

/**
 * 
 * Esta clase se crea con el objetivo de poder mokear la fecha en la pruebas
 * 
 * @author cesar.cadavid
 *
 */
@Component
public class LocalDateTimeWrapper {

	public LocalDateTime now() {
		return LocalDateTime.now();
	}

}
