package co.com.ceiba.estacionamiento.excepciones;

public class EstacionamientoCarrosLlenoExcepcion extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4410429075401727354L;
	public static final String ESTACIONAMIENTO_CARROS_LLENO = "El estacionamiento para carros se encuentra lleno";

	public EstacionamientoCarrosLlenoExcepcion() {
		super(ESTACIONAMIENTO_CARROS_LLENO);
	}

}
