package co.com.ceiba.estacionamiento.excepciones;

public class VehiculoNoPuedeIngresarExcepcion extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7629467690381613086L;

	public static final String VEHICULO_NOPUEDE_ESTACIONAR = "Vehículo con placa inicial en A no puede ingresar los dias Domingos o Lunes";

	public VehiculoNoPuedeIngresarExcepcion() {
		super(VEHICULO_NOPUEDE_ESTACIONAR);
	}

}
