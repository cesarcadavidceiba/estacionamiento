package co.com.ceiba.estacionamiento.excepciones;

public class NoHayEstacionamientoDisponibleExcepcion extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7629467690381613086L;

	private static final String NO_HAY_ESTACIONAMIENTO_DISPONIBLE = "No hay estacionamiento disponible para el vehiculo";
	
	public NoHayEstacionamientoDisponibleExcepcion() {
		super(NO_HAY_ESTACIONAMIENTO_DISPONIBLE);
	}

}
