package co.com.ceiba.estacionamiento.servicios;

import co.com.ceiba.estacionamiento.entidades.FacturaVehiculo;
import co.com.ceiba.estacionamiento.modelos.FacturaVehiculoModel;

public interface FacturaVehiculoService {

	public FacturaVehiculo findFacturaVehiculoById(long idFactura);

	public void registrarVehiculo(FacturaVehiculoModel facturaVehiculoModel);

}
