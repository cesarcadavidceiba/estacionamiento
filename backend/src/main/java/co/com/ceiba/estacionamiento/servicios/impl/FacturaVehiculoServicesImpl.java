package co.com.ceiba.estacionamiento.servicios.impl;

import static java.util.stream.Collectors.toList;

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

	public FacturaVehiculoServicesImpl(FacturaVehiculoRepository facturaVehiculoRepository,
			LocalDateTimeWrapper localDateTimeWrapper) {
		this.facturaVehiculoRepository = facturaVehiculoRepository;
		this.localDateTimeWrapper = localDateTimeWrapper;
	}

	@Override
	public List<FacturaVehiculoModel> cargarVehiculosEstacionados() {
		List<FacturaVehiculo> listaVehiculosEstacionadosRepo = facturaVehiculoRepository.findAllByFechaSalidaIsNull();
		return listaVehiculosEstacionadosRepo.stream().map(FacturaVehiculo::convertirAModelo).collect(toList());
	}

	@Override
	public FacturaVehiculoModel estacionarVehiculo(FacturaVehiculoModel facturaVehiculoModel) {
		facturaVehiculoModel.getVehiculo().validarCilindrajeMotos();
		
		facturaVehiculoModel.setFechaEntrada(localDateTimeWrapper.now());

		facturaVehiculoModel.vehiculoPuedeEstacionar();

		verificarDisponibilidadEstacionamiento(facturaVehiculoModel);
		verificarDisponibilidadPosicion(facturaVehiculoModel);
		verificarVehiculoEstacionado(facturaVehiculoModel);

		FacturaVehiculo facturaVehiculo = FacturaVehiculo.convertirAEntity(facturaVehiculoModel);
		facturaVehiculo = facturaVehiculoRepository.save(facturaVehiculo);

		facturaVehiculoModel.setId(facturaVehiculo.getId());

		return facturaVehiculoModel;
	}

	private void verificarDisponibilidadEstacionamiento(FacturaVehiculoModel facturaVehiculoModel) {
		int cantidadVehiculosEstacionados = facturaVehiculoRepository
				.countByVehiculoTipoAndFechaSalidaIsNull(facturaVehiculoModel.getVehiculo().getTipo());

		if (facturaVehiculoModel.getVehiculo().getTipo() == EtipoVehiculo.CARRO) {
			if (cantidadVehiculosEstacionados == CANTIDAD_MAXIMA_CARROS) {
				throw new EstacionamientoCarrosLlenoExcepcion();
			}
		} else {
			if (cantidadVehiculosEstacionados == CANTIDAD_MAXIMA_MOTOS) {
				throw new EstacionamientoMotosLlenoExcepcion();
			}
		}
	}

	private void verificarDisponibilidadPosicion(FacturaVehiculoModel facturaVehiculoModel) {
		boolean estacionamientoOcupado = facturaVehiculoRepository.existsByVehiculoTipoAndPosicionAndFechaSalidaIsNull(
				facturaVehiculoModel.getVehiculo().getTipo(), facturaVehiculoModel.getPosicion());
		if (estacionamientoOcupado) {
			throw new PosicionEstacionamientoOcupadaExcepcion();
		}
	}

	private void verificarVehiculoEstacionado(FacturaVehiculoModel facturaVehiculoModel) {
		boolean vehiculoEstacionado = facturaVehiculoRepository
				.existsByVehiculoPlacaAndFechaSalidaIsNull(facturaVehiculoModel.getVehiculo().getPlaca());
		if (vehiculoEstacionado) {
			throw new VehiculoSeEncuentraEstacionadoExcepcion();
		}
	}

	@Override
	public long darSalidaVehiculoEstacionado(String placa) {
		// Verificar que el vehiculo se encuentre estacionado
		FacturaVehiculo facturaVehiculo = facturaVehiculoRepository.findByVehiculoPlacaAndFechaSalidaIsNull(placa)
				.orElseThrow(() -> new VehiculoNoSeEncuentraEstacionadoExcepcion());

		facturaVehiculo.setFechaSalida(localDateTimeWrapper.now());
		FacturaVehiculoModel facturaVehiculoModel = FacturaVehiculo.convertirAModelo(facturaVehiculo);
		Long valorPagar = facturaVehiculoModel.obtenerValorPagar();

		facturaVehiculoRepository.save(facturaVehiculo);

		return valorPagar;
	}

}