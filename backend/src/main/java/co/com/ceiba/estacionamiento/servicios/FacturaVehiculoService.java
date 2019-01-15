package co.com.ceiba.estacionamiento.servicios;

import java.util.List;

import co.com.ceiba.estacionamiento.modelos.FacturaVehiculoModel;

public interface FacturaVehiculoService {

	/**
	 * 
	 * Permite cargar la factura de un vehiculo de acuerdo al id de la factura
	 * 
	 * @return
	 */
	public List<FacturaVehiculoModel> cargarVehiculosEstacionados();

	/**
	 * 
	 * Registra un vehiculo para estacionar
	 * 
	 * @param facturaVehiculoModel
	 * @return devuelve el id generado de la factura
	 */
	public long estacionarVehiculo(FacturaVehiculoModel facturaVehiculoModel);

	/**
	 * 
	 * Permite dar salida a un vehiculo estacionado
	 * 
	 * @param placa
	 * @return Valor a pagar por el cliente
	 */
	public String darSalidaVehiculoEstacionado(String placa);
}
