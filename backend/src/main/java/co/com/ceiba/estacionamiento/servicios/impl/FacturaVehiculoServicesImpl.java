package co.com.ceiba.estacionamiento.servicios.impl;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import co.com.ceiba.estacionamiento.entidades.FacturaVehiculo;
import co.com.ceiba.estacionamiento.enumerados.EtipoVehiculo;
import co.com.ceiba.estacionamiento.excepciones.EstacionamientoCarrosLlenoExcepcion;
import co.com.ceiba.estacionamiento.excepciones.EstacionamientoMotosLlenoExcepcion;
import co.com.ceiba.estacionamiento.excepciones.PosicionEstacionamientoOcupadaExcepcion;
import co.com.ceiba.estacionamiento.excepciones.VehiculoNoSeEncuentraEstacionadoExcepcion;
import co.com.ceiba.estacionamiento.excepciones.VehiculoSeEncuentraEstacionadoExcepcion;
import co.com.ceiba.estacionamiento.modelos.FacturaVehiculoModel;
import co.com.ceiba.estacionamiento.repositorios.FacturaVehiculoRepository;
import co.com.ceiba.estacionamiento.servicios.FacturaVehiculoService;
import co.com.ceiba.estacionamiento.util.LocalDateTimeWrapper;

@Service
public class FacturaVehiculoServicesImpl implements FacturaVehiculoService {

	// Constantes
	private static final int CANTIDAD_MAXIMA_CARROS = 20;
	private static final int CANTIDAD_MAXIMA_MOTOS = 10;

	private FacturaVehiculoRepository facturaVehiculoRepository;
	
	private LocalDateTimeWrapper localDateTimeWrapper; 

	public FacturaVehiculoServicesImpl(FacturaVehiculoRepository facturaVehiculoRepository) {
		this.facturaVehiculoRepository = facturaVehiculoRepository;
	}

	@Override
	public List<FacturaVehiculoModel> cargarVehiculosEstacionados() {
		List<FacturaVehiculo> listaVehiculosEstacionadosRepo = facturaVehiculoRepository.findAllByFechaSalidaIsNull();
		if (listaVehiculosEstacionadosRepo != null && !listaVehiculosEstacionadosRepo.isEmpty()) {
			List<FacturaVehiculoModel> listaVehiculosEstacionados = new ArrayList<>();
			for (FacturaVehiculo facturaVehiculo : listaVehiculosEstacionadosRepo) {
				listaVehiculosEstacionados.add(FacturaVehiculo.convertirAModelo(facturaVehiculo));
			}
			return listaVehiculosEstacionados;
		}

		return emptyList();
	}

	@Override
	public long estacionarVehiculo(FacturaVehiculoModel facturaVehiculoModel) {
		facturaVehiculoModel.vehiculoPuedeEstacionar();
		facturaVehiculoModel.validarCilindrajeMotos();

		// Verificar si hay estacionamiento disponible
		int cantidadVehiculosEstacionados = facturaVehiculoRepository
				.countByTipoAndFechaSalidaIsNull(facturaVehiculoModel.getTipo());

		if (facturaVehiculoModel.getTipo() == EtipoVehiculo.CARRO) {
			if (cantidadVehiculosEstacionados == CANTIDAD_MAXIMA_CARROS) {
				throw new EstacionamientoCarrosLlenoExcepcion();
			}
		} else {
			if (cantidadVehiculosEstacionados == CANTIDAD_MAXIMA_MOTOS) {
				throw new EstacionamientoMotosLlenoExcepcion();
			}
		}

		// Verificar si la posicion del estacionamiento esta disponible
		boolean estacionamientoOcupado = facturaVehiculoRepository.existsByTipoAndPosicionAndFechaSalidaIsNull(
				facturaVehiculoModel.getTipo(), facturaVehiculoModel.getPosicion());
		if (estacionamientoOcupado) {
			throw new PosicionEstacionamientoOcupadaExcepcion();
		}

		// Verificar que el vehiculo no se encuentre ya estacionado
		boolean vehiculoEstacionado = facturaVehiculoRepository
				.existsByPlacaAndFechaSalidaIsNull(facturaVehiculoModel.getPlaca());
		if (vehiculoEstacionado) {
			throw new VehiculoSeEncuentraEstacionadoExcepcion();
		}

		FacturaVehiculo facturaVehiculo = FacturaVehiculo.convertirAEntity(facturaVehiculoModel);

		return facturaVehiculoRepository.save(facturaVehiculo).getId();
	}

	@Override
	public String darSalidaVehiculoEstacionado(String placa) {
		// Verificar que el vehiculo se encuentre estacionado
		FacturaVehiculo facturaVehiculo = facturaVehiculoRepository.findByPlacaAndFechaSalidaIsNull(placa);

		if (facturaVehiculo == null) {
			throw new VehiculoNoSeEncuentraEstacionadoExcepcion();
		}

		facturaVehiculo.setFechaSalida(new LocalDateTimeWrapper().now());
		facturaVehiculoRepository.save(facturaVehiculo);

		FacturaVehiculoModel facturaVehiculoModel = FacturaVehiculo.convertirAModelo(facturaVehiculo);

		return facturaVehiculoModel.obtenerValorPagar();
	}

}