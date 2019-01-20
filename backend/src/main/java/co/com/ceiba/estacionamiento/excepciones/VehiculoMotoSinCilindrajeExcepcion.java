package co.com.ceiba.estacionamiento.excepciones;

public class VehiculoMotoSinCilindrajeExcepcion extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2273988674045221941L;
	public static final String VEHICULO_MOTO_SIN_CILINDRAJE = "El cilindraje para las motos es obligatorio";

	public VehiculoMotoSinCilindrajeExcepcion() {
		super(VEHICULO_MOTO_SIN_CILINDRAJE);
	}

}
