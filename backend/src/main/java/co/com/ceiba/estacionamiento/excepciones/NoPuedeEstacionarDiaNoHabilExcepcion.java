package co.com.ceiba.estacionamiento.excepciones;

public class NoPuedeEstacionarDiaNoHabilExcepcion extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7629467690381613086L;

	private static final String NO_PUEDE_ESTACIONAR_DIA_NO_HABIL = "No puede estacionar por que no esta en un día hábil";
	
	public NoPuedeEstacionarDiaNoHabilExcepcion() {
		super(NO_PUEDE_ESTACIONAR_DIA_NO_HABIL);
	}

}
