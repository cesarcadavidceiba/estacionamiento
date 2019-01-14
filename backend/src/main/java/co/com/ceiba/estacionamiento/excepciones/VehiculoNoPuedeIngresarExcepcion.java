package co.com.ceiba.estacionamiento.excepciones;

public class VehiculoNoPuedeIngresarExcepcion extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7629467690381613086L;

	private static final String MENSAJE_EXCEPCION_VEHICULO_NOPUEDE_ESTACIONAR = "Vehículo con placa inicial en A no puede ingresar los dias Domingos ni Lunes";
	
	public VehiculoNoPuedeIngresarExcepcion() {
		super(MENSAJE_EXCEPCION_VEHICULO_NOPUEDE_ESTACIONAR);
	}

}
