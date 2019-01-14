package co.com.ceiba.estacionamiento.excepciones;

public class PosicionEstacionamientoOcupadaExcepcion extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7629467690381613086L;

	private static final String POSICION_ESTACIONAMIENTO_OCUPADA = "La posición del estacionamiento que solicita se encuentra ocupada";
	
	public PosicionEstacionamientoOcupadaExcepcion() {
		super(POSICION_ESTACIONAMIENTO_OCUPADA);
	}

}
