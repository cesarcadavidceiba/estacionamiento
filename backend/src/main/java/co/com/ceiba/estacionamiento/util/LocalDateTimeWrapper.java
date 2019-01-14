package co.com.ceiba.estacionamiento.util;

import java.time.LocalDateTime;

/**
 * 
 * Esta clase se crea con el objetivo de poder mokear la fecha en la pruebas
 * 
 * @author cesar.cadavid
 *
 */
public class LocalDateTimeWrapper {

	public LocalDateTime now() {
		return LocalDateTime.now();
	}

}
