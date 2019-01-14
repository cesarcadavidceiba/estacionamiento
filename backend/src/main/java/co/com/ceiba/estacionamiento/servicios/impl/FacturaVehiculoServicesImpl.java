package co.com.ceiba.estacionamiento.servicios.impl;

import org.springframework.stereotype.Service;

import co.com.ceiba.estacionamiento.entidades.FacturaVehiculo;
import co.com.ceiba.estacionamiento.modelos.FacturaVehiculoModel;
import co.com.ceiba.estacionamiento.repositorios.FacturaVehiculoRepository;
import co.com.ceiba.estacionamiento.servicios.FacturaVehiculoService;

@Service
public class FacturaVehiculoServicesImpl implements FacturaVehiculoService {

	//Constantes
	private final static int CANTIDAD_MAXIMA_CARROS = 20;
	private final static int CANTIDAD_MAXIMA_MOTOS = 10;
	private final static double VALOR_HORA_CARRO = 1000;
	private final static int VALOR_HORA_MOTO = 500;
	private final static int VALOR_DIA_CARARO = 8000;
	private final static int VALOR_DIA_MOTO = 4000;

	private final static int CILINDRAJE_MOTO = 500;
	private final static int VALOR_EXTRA_MOTO = 2000;
	
	private FacturaVehiculoRepository facturaVehiculoRepository;

	public FacturaVehiculoServicesImpl(FacturaVehiculoRepository facturaVehiculoRepository) {
		this.facturaVehiculoRepository = facturaVehiculoRepository;
	}

	@Override
	public FacturaVehiculo findFacturaVehiculoById(long idFactura) {
		return facturaVehiculoRepository.findByIdFactura(idFactura);
	}

	@Override
	public void registrarVehiculo(FacturaVehiculoModel facturaVehiculoModel) {
		facturaVehiculoModel.vehiculoPuedeEstacionar();
		
		FacturaVehiculo facturaVehiculo = FacturaVehiculo.convertirAEntity(facturaVehiculoModel);
		facturaVehiculoRepository.save(facturaVehiculo);
	}

}