package co.com.ceiba.estacionamiento.excepciones;

public class VehiculoNoSeEncuentraEstacionadoExcepcion extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7629467690381613086L;

	private static final String VEHICULO_NO_ESTA_ESTACIONADO = "El vehiculo no se encuentra estacionado";
	
	public VehiculoNoSeEncuentraEstacionadoExcepcion() {
		super(VEHICULO_NO_ESTA_ESTACIONADO);
	}

}
