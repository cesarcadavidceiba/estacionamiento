package co.com.ceiba.estacionamiento.excepciones;

public class VehiculoSeEncuentraEstacionadoExcepcion extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7629467690381613086L;

	private static final String VEHICULO_ESTA_ESTACIONADO = "El vehiculo ya se encuentra estacionado";
	
	public VehiculoSeEncuentraEstacionadoExcepcion() {
		super(VEHICULO_ESTA_ESTACIONADO);
	}

}
