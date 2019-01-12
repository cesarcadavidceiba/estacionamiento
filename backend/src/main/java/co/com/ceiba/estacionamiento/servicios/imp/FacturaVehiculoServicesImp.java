package co.com.ceiba.estacionamiento.servicios.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import co.com.ceiba.estacionamiento.entidades.FacturaVehiculo;
import co.com.ceiba.estacionamiento.repositorios.FacturaVehiculoRepository;
import co.com.ceiba.estacionamiento.servicios.FacturaVehiculoService;

@Service("facturaVehiculoServicesImp")
public class FacturaVehiculoServicesImp implements FacturaVehiculoService {

	@Autowired
	@Qualifier("facturaVehiculoRepository")
	private FacturaVehiculoRepository facturaVehiculoRepository;

	@Override
	public FacturaVehiculo findFacturaVehiculoById(long idFactura) {
		return facturaVehiculoRepository.findByIdFactura(idFactura);
	}

}