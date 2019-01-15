package co.com.ceiba.estacionamiento.test.servicios;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.estacionamiento.entidades.FacturaVehiculo;
import co.com.ceiba.estacionamiento.enumerados.EtipoVehiculo;
import co.com.ceiba.estacionamiento.excepciones.NoPuedeEstacionarDiaNoHabilExcepcion;
import co.com.ceiba.estacionamiento.modelos.FacturaVehiculoModel;
import co.com.ceiba.estacionamiento.repositorios.FacturaVehiculoRepository;
import co.com.ceiba.estacionamiento.servicios.FacturaVehiculoService;
import co.com.ceiba.estacionamiento.servicios.impl.FacturaVehiculoServicesImpl;
import co.com.ceiba.estacionamiento.test.testdatabuilder.FacturaVehiculoTestDataBuilder;

@RunWith(SpringRunner.class)
public class FacturaVehiculoServiceTest {

	// Constantes
	private static final int CANTIDAD_MAXIMA_CARROS_ESTACIONADOS = 20;
	private static final String NO_PUEDE_ESTACIONAR_DIA_NO_HABIL = "No puede estacionar por que no esta en un día hábil";

	@MockBean
	private FacturaVehiculoRepository facturaVehiculoRepository;

	@Before
	public void setup() {
		// Crear un build para esta entidad
		FacturaVehiculoTestDataBuilder builder = new FacturaVehiculoTestDataBuilder().conId(1L);
		FacturaVehiculo facturaVehiculo = builder.buildEntity();
		when(facturaVehiculoRepository.save(any(FacturaVehiculo.class))).thenReturn(facturaVehiculo);
	}

	@Test
	public void registrarVehiculoTest() {
		// Arrange
		FacturaVehiculoModel facturaVehiculoModel = new FacturaVehiculoTestDataBuilder().buildModel();
		when(facturaVehiculoRepository.countByTipoAndFechaSalidaIsNull(facturaVehiculoModel.getTipo())).thenReturn(1);

		FacturaVehiculoService facturaVehiculoService = new FacturaVehiculoServicesImpl(facturaVehiculoRepository);

		// Act
		long id = facturaVehiculoService.registrarVehiculo(facturaVehiculoModel);

		// Assert
		assertThat(id).isEqualTo(1L);
	}

	@Test
	public void cargarVehiculosEstacionadosTest() {
		// Arrange
		FacturaVehiculoTestDataBuilder builder = new FacturaVehiculoTestDataBuilder();
		FacturaVehiculo estacionarRenult = builder.buildEntity();

		builder = new FacturaVehiculoTestDataBuilder().conPlaca("GTE 459")
				.conFechaEntrada(LocalDateTime.of(2019, 01, 15, 9, 10)).conMarca("Mazda").conModelo("2017")
				.conTipo(EtipoVehiculo.CARRO);
		FacturaVehiculo estacionarMazda = builder.buildEntity();

		builder = new FacturaVehiculoTestDataBuilder().conPlaca("ZSD 953")
				.conFechaEntrada(LocalDateTime.of(2019, 01, 15, 8, 30)).conMarca("Chevrolet").conModelo("2015")
				.conTipo(EtipoVehiculo.CARRO);
		FacturaVehiculo estacionarChevrolet = builder.buildEntity();

		builder = new FacturaVehiculoTestDataBuilder().conPlaca("NIV 17E")
				.conFechaEntrada(LocalDateTime.of(2019, 01, 15, 7, 10)).conMarca("YAMAHA").conModelo("2018")
				.conCilindraje(150).conTipo(EtipoVehiculo.MOTO);
		FacturaVehiculo estacionarMotoYahama = builder.buildEntity();

		List<FacturaVehiculo> todosVehiculosEstacinados = Arrays.asList(estacionarRenult, estacionarMazda,
				estacionarChevrolet, estacionarMotoYahama);

		when(facturaVehiculoRepository.findAllByFechaSalidaIsNull()).thenReturn(todosVehiculosEstacinados);

		FacturaVehiculoService facturaVehiculoService = new FacturaVehiculoServicesImpl(facturaVehiculoRepository);

		// Act
		List<FacturaVehiculoModel> vehiculosEstacionados = facturaVehiculoService.cargarVehiculosEstacionados();

		// Assert
		assertThat(vehiculosEstacionados.get(0).getPlaca()).isEqualTo("DUX 258");
		assertThat(vehiculosEstacionados.get(1).getPlaca()).isEqualTo("GTE 459");
		assertThat(vehiculosEstacionados.get(2).getPlaca()).isEqualTo("ZSD 953");
		assertThat(vehiculosEstacionados.get(3).getPlaca()).isEqualTo("NIV 17E");
	}

	@Test
	public void NohayVehiculosEstacionadosListaVacia() {
		// Arrange
		when(facturaVehiculoRepository.findAllByFechaSalidaIsNull()).thenReturn(new ArrayList<FacturaVehiculo>());

		FacturaVehiculoService facturaVehiculoService = new FacturaVehiculoServicesImpl(facturaVehiculoRepository);

		// Act
		List<FacturaVehiculoModel> listaVaciaModel = facturaVehiculoService.cargarVehiculosEstacionados();

		// Assert
		assertTrue(listaVaciaModel.isEmpty());
	}

	@Test
	public void NohayVehiculosEstacionadosListaNull() {
		// Arrange
		when(facturaVehiculoRepository.findAllByFechaSalidaIsNull()).thenReturn(null);

		FacturaVehiculoService facturaVehiculoService = new FacturaVehiculoServicesImpl(facturaVehiculoRepository);

		// Act
		List<FacturaVehiculoModel> listaVaciaModel = facturaVehiculoService.cargarVehiculosEstacionados();

		// Assert
		assertTrue(listaVaciaModel.isEmpty());
	}

	public void estaciomaientoCarrosLleno() {
		// Arrange
		FacturaVehiculoModel facturaVehiculoModel = new FacturaVehiculoTestDataBuilder().buildModel();
		when(facturaVehiculoRepository.countByTipoAndFechaSalidaIsNull(EtipoVehiculo.CARRO))
				.thenReturn(CANTIDAD_MAXIMA_CARROS_ESTACIONADOS);

		FacturaVehiculoService facturaVehiculoService = new FacturaVehiculoServicesImpl(facturaVehiculoRepository);

		// Act
		try {
			facturaVehiculoService.registrarVehiculo(facturaVehiculoModel);
			fail();
		} catch (NoPuedeEstacionarDiaNoHabilExcepcion ex) {
			// Assert
			assertThat(ex.getMessage()).isEqualTo(NO_PUEDE_ESTACIONAR_DIA_NO_HABIL);
		}
	}
}
