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
	 * @return vehiculo estacionado
	 */
	public FacturaVehiculoModel estacionarVehiculo(FacturaVehiculoModel facturaVehiculoModel);

	/**
	 * 
	 * Permite dar salida a un vehiculo estacionado
	 * 
	 * @param placa
	 * @return Valor a pagar por el cliente
	 */
	public long darSalidaVehiculoEstacionado(String placa);
}
