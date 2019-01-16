package co.com.ceiba.estacionamiento.excepciones;

public class EstacionamientoMotosLlenoExcepcion extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7629467690381613086L;

	public static final String ESTACIONAMIENTO_MOTOS_LLENO = "El estacionamiento para motos se encuentra lleno";
	
	public EstacionamientoMotosLlenoExcepcion() {
		super(ESTACIONAMIENTO_MOTOS_LLENO);
	}

}
