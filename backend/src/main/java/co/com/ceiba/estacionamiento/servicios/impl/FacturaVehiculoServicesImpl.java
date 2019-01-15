package co.com.ceiba.estacionamiento.servicios.impl;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import co.com.ceiba.estacionamiento.entidades.FacturaVehiculo;
import co.com.ceiba.estacionamiento.enumerados.EtipoVehiculo;
import co.com.ceiba.estacionamiento.excepciones.NoPuedeEstacionarDiaNoHabilExcepcion;
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
	public long registrarVehiculo(FacturaVehiculoModel facturaVehiculoModel) {
		facturaVehiculoModel.vehiculoPuedeEstacionar();
		facturaVehiculoModel.validarCilindrajeMotos();

		// Verificar si hay estacionamiento disponible
		int cantidadVehiculosEstacionados = facturaVehiculoRepository
				.countByTipoAndFechaSalidaIsNull(facturaVehiculoModel.getTipo());

		if (facturaVehiculoModel.getTipo() == EtipoVehiculo.CARRO) {
			if (cantidadVehiculosEstacionados == CANTIDAD_MAXIMA_CARROS) {
				throw new NoPuedeEstacionarDiaNoHabilExcepcion();
			}
		} else {
			if (cantidadVehiculosEstacionados == CANTIDAD_MAXIMA_MOTOS) {
				throw new NoPuedeEstacionarDiaNoHabilExcepcion();
			}
		}

		// Verificar si la posicion del estacionamiento esta disponible
		FacturaVehiculo facturaVehiculo = facturaVehiculoRepository.findByTipoAndPosicionAndFechaSalidaIsNull(
				facturaVehiculoModel.getTipo(), facturaVehiculoModel.getPosicion());
		if (facturaVehiculo != null) {
			throw new PosicionEstacionamientoOcupadaExcepcion();
		}

		// Verificar que el vehiculo no se encuentre ya estacionado
		facturaVehiculo = facturaVehiculoRepository.findByPlacaAndFechaSalidaIsNull(facturaVehiculoModel.getPlaca());
		if (facturaVehiculo != null) {
			throw new VehiculoSeEncuentraEstacionadoExcepcion();
		}

		facturaVehiculo = FacturaVehiculo.convertirAEntity(facturaVehiculoModel);

		FacturaVehiculo save = facturaVehiculoRepository.save(facturaVehiculo);

		return save.getId();
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