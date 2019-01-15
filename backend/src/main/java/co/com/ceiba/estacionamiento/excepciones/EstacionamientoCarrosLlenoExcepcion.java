package co.com.ceiba.estacionamiento.excepciones;

public class EstacionamientoCarrosLlenoExcepcion extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7629467690381613086L;

	private static final String ESTACIONAMIENTO_CARROS_LLENO = "El estacionamiento para carros se encuentra lleno";
	
	public EstacionamientoCarrosLlenoExcepcion() {
		super(ESTACIONAMIENTO_CARROS_LLENO);
	}

}
