package co.com.ceiba.estacionamiento.test.servicios;

import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.estacionamiento.entidades.FacturaVehiculo;
import co.com.ceiba.estacionamiento.enumerados.EtipoVehiculo;
import co.com.ceiba.estacionamiento.excepciones.EstacionamientoCarrosLlenoExcepcion;
import co.com.ceiba.estacionamiento.excepciones.EstacionamientoMotosLlenoExcepcion;
import co.com.ceiba.estacionamiento.excepciones.PosicionEstacionamientoOcupadaExcepcion;
import co.com.ceiba.estacionamiento.excepciones.VehiculoMotoSinCilindrajeExcepcion;
import co.com.ceiba.estacionamiento.excepciones.VehiculoNoPuedeIngresarExcepcion;
import co.com.ceiba.estacionamiento.excepciones.VehiculoNoSeEncuentraEstacionadoExcepcion;
import co.com.ceiba.estacionamiento.excepciones.VehiculoSeEncuentraEstacionadoExcepcion;
import co.com.ceiba.estacionamiento.modelos.FacturaVehiculoModel;
import co.com.ceiba.estacionamiento.repositorios.FacturaVehiculoRepository;
import co.com.ceiba.estacionamiento.servicios.FacturaVehiculoService;
import co.com.ceiba.estacionamiento.servicios.impl.FacturaVehiculoServicesImpl;
import co.com.ceiba.estacionamiento.test.testdatabuilder.FacturaVehiculoTestDataBuilder;
import co.com.ceiba.estacionamiento.util.LocalDateTimeWrapper;

@RunWith(SpringRunner.class)
public class FacturaVehiculoServiceTest {

	// Constantes
	private static final int CANTIDAD_MAXIMA_CARROS_ESTACIONADOS = 20;
	private static final int CANTIDAD_MAXIMA_MOTOS_ESTACIONADAS = 10;
	private static final short POSICION_ESTACIONAMIENTO_VEHICULO = 1;
	private static final int CILINDRAJE_MOTO_MAYOR_500 = 650;
	private static final int CILINDRAJE_MOTO_MENOR_500 = 150;
	private static final LocalDateTime FECHA_ENTRADA_DOMINGO = LocalDateTime.of(2019, Month.JANUARY, 13, 8, 20);
	private static final LocalDateTime FECHA_ENTRADA_LUNES = LocalDateTime.of(2019, Month.JANUARY, 14, 8, 20);
	private static final LocalDateTime FECHA_ENTRADA_MARTES = LocalDateTime.of(2019, Month.JANUARY, 15, 8, 20);

	@MockBean
	private FacturaVehiculoRepository facturaVehiculoRepository;

	@MockBean
	private LocalDateTimeWrapper localDateTimeWrapper;

	private FacturaVehiculoService facturaVehiculoService;

	@Before
	public void setup() {
		// Crear un build para esta entidad
		FacturaVehiculoTestDataBuilder builder = new FacturaVehiculoTestDataBuilder().conId(1L);
		FacturaVehiculo facturaVehiculo = builder.buildEntity();
		facturaVehiculoService = new FacturaVehiculoServicesImpl(facturaVehiculoRepository, localDateTimeWrapper);
		when(facturaVehiculoRepository.save(any(FacturaVehiculo.class))).thenReturn(facturaVehiculo);

	}

	@Test
	public void registrarVehiculoTest() {
		// Arrange
		FacturaVehiculoModel facturaVehiculoModel = new FacturaVehiculoTestDataBuilder().buildModel();
		when(facturaVehiculoRepository.countByTipoAndFechaSalidaIsNull(facturaVehiculoModel.getTipo())).thenReturn(1);

		// Act
		long id = facturaVehiculoService.estacionarVehiculo(facturaVehiculoModel);

		// Assert
		assertThat(id).isEqualTo(1L);
	}

	@Test
	public void cargarVehiculosEstacionadosTest() {
		// Arrange
		FacturaVehiculoTestDataBuilder builder = new FacturaVehiculoTestDataBuilder();
		FacturaVehiculo estacionarRenult = builder.buildEntity();

		builder = new FacturaVehiculoTestDataBuilder().conPlaca("GTE 459").conMarca("Mazda").conModelo("2017")
				.conTipo(EtipoVehiculo.CARRO);
		FacturaVehiculo estacionarMazda = builder.buildEntity();

		builder = new FacturaVehiculoTestDataBuilder().conPlaca("ZSD 953").conMarca("Chevrolet").conModelo("2015")
				.conTipo(EtipoVehiculo.CARRO);
		FacturaVehiculo estacionarChevrolet = builder.buildEntity();

		builder = new FacturaVehiculoTestDataBuilder().conPlaca("NIV 17E").conMarca("YAMAHA").conModelo("2018")
				.conCilindraje(150).conTipo(EtipoVehiculo.MOTO);
		FacturaVehiculo estacionarMotoYahama = builder.buildEntity();

		List<FacturaVehiculo> todosVehiculosEstacinados = Arrays.asList(estacionarRenult, estacionarMazda,
				estacionarChevrolet, estacionarMotoYahama);

		when(facturaVehiculoRepository.findAllByFechaSalidaIsNull()).thenReturn(todosVehiculosEstacinados);

		// Act
		List<FacturaVehiculoModel> vehiculosEstacionados = facturaVehiculoService.cargarVehiculosEstacionados();

		// Assert
		assertThat(vehiculosEstacionados.get(0).getPlaca()).isEqualTo("DUX 258");
		assertThat(vehiculosEstacionados.get(1).getPlaca()).isEqualTo("GTE 459");
		assertThat(vehiculosEstacionados.get(2).getPlaca()).isEqualTo("ZSD 953");
		assertThat(vehiculosEstacionados.get(3).getPlaca()).isEqualTo("NIV 17E");
	}

	@Test
	public void nohayVehiculosEstacionadosListaVaciaTest() {
		// Arrange
		when(facturaVehiculoRepository.findAllByFechaSalidaIsNull()).thenReturn(new ArrayList<FacturaVehiculo>());

		// Act
		List<FacturaVehiculoModel> listaVaciaModel = facturaVehiculoService.cargarVehiculosEstacionados();

		// Assert
		assertTrue(listaVaciaModel.isEmpty());
	}

	@Test
	public void estaciomaientoCarrosLlenoTest() {
		// Arrange
		FacturaVehiculoModel facturaVehiculoModel = new FacturaVehiculoTestDataBuilder().buildModel();
		when(facturaVehiculoRepository.countByTipoAndFechaSalidaIsNull(EtipoVehiculo.CARRO))
				.thenReturn(CANTIDAD_MAXIMA_CARROS_ESTACIONADOS);

		// Act
		try {
			facturaVehiculoService.estacionarVehiculo(facturaVehiculoModel);
			fail();
		} catch (EstacionamientoCarrosLlenoExcepcion ex) {
			// Assert
			assertThat(ex.getMessage()).isEqualTo(EstacionamientoCarrosLlenoExcepcion.ESTACIONAMIENTO_CARROS_LLENO);
		}
	}

	@Test
	public void estaciomaientoMotosLlenoTest() {
		// Arrange
		FacturaVehiculoModel facturaVehiculoModel = new FacturaVehiculoTestDataBuilder().conPlaca("NIV 17E")
				.conCilindraje(150).conMarca("YAMAHA").conModelo("2018").conTipo(EtipoVehiculo.MOTO).buildModel();

		when(facturaVehiculoRepository.countByTipoAndFechaSalidaIsNull(facturaVehiculoModel.getTipo()))
				.thenReturn(CANTIDAD_MAXIMA_MOTOS_ESTACIONADAS);

		// Act
		try {
			facturaVehiculoService.estacionarVehiculo(facturaVehiculoModel);
			fail();
		} catch (EstacionamientoMotosLlenoExcepcion ex) {
			// Assert
			assertThat(ex.getMessage()).isEqualTo(EstacionamientoMotosLlenoExcepcion.ESTACIONAMIENTO_MOTOS_LLENO);
		}
	}

	@Test
	public void posicionEstacionamientoOcupadaTest() {
		// Arrange
		FacturaVehiculoModel facturaVehiculoModel = new FacturaVehiculoTestDataBuilder().conPlaca("NIV 17E")
				.conMarca("YAMAHA").conModelo("2018").conCilindraje(150).conTipo(EtipoVehiculo.MOTO)
				.conPosicion(POSICION_ESTACIONAMIENTO_VEHICULO).buildModel();

		when(facturaVehiculoRepository.existsByTipoAndPosicionAndFechaSalidaIsNull(facturaVehiculoModel.getTipo(),
				facturaVehiculoModel.getPosicion())).thenReturn(true);

		// Act
		try {
			facturaVehiculoService.estacionarVehiculo(facturaVehiculoModel);
			fail();
		} catch (PosicionEstacionamientoOcupadaExcepcion ex) {
			// Assert
			assertThat(ex.getMessage())
					.isEqualTo(PosicionEstacionamientoOcupadaExcepcion.POSICION_ESTACIONAMIENTO_OCUPADA);
		}
	}

	@Test
	public void vehiculoSeEncuentraEstacionadoTest() {
		// Arrange
		FacturaVehiculoModel facturaVehiculoModel = new FacturaVehiculoTestDataBuilder().conPlaca("NIV 17E")
				.conMarca("YAMAHA").conModelo("2018").conCilindraje(150).conTipo(EtipoVehiculo.MOTO)
				.conPosicion(POSICION_ESTACIONAMIENTO_VEHICULO).buildModel();

		when(facturaVehiculoRepository.existsByPlacaAndFechaSalidaIsNull(facturaVehiculoModel.getPlaca()))
				.thenReturn(true);

		// Act
		try {
			facturaVehiculoService.estacionarVehiculo(facturaVehiculoModel);
			fail();
		} catch (VehiculoSeEncuentraEstacionadoExcepcion ex) {
			// Assert
			assertThat(ex.getMessage()).isEqualTo(VehiculoSeEncuentraEstacionadoExcepcion.VEHICULO_ESTA_ESTACIONADO);
		}
	}

	@Test
	public void vehiculoNoPuedeEstacionarPlacaIniciaEnADiaLunesTest() {
		// Arrange
		FacturaVehiculoModel facturaVehiculoModel = new FacturaVehiculoTestDataBuilder().conPlaca("ADF 465")
				.buildModel();
		when(localDateTimeWrapper.now()).thenReturn(FECHA_ENTRADA_LUNES);

		// Act
		try {
			facturaVehiculoService.estacionarVehiculo(facturaVehiculoModel);
			fail();
		} catch (VehiculoNoPuedeIngresarExcepcion ex) {
			// assert
			assertThat(ex.getMessage()).isEqualTo(VehiculoNoPuedeIngresarExcepcion.VEHICULO_NOPUEDE_ESTACIONAR);
		}
	}

	@Test
	public void vehiculoNoPuedeEstacionarPlacaIniciaEnADiaDomingoTest() {
		// Arrange
		FacturaVehiculoModel facturaVehiculoModel = new FacturaVehiculoTestDataBuilder().conPlaca("ADF 465")
				.buildModel();
		when(localDateTimeWrapper.now()).thenReturn(FECHA_ENTRADA_DOMINGO);

		// Act
		try {
			facturaVehiculoService.estacionarVehiculo(facturaVehiculoModel);
			fail();
		} catch (VehiculoNoPuedeIngresarExcepcion ex) {
			// assert
			assertThat(ex.getMessage()).isEqualTo(VehiculoNoPuedeIngresarExcepcion.VEHICULO_NOPUEDE_ESTACIONAR);
		}
	}

	@Test
	public void vehiculoNoPuedeEstacionarPlacaIniciaEnADiaMartesTest() {
		// Arrange
		FacturaVehiculoModel facturaVehiculoModel = new FacturaVehiculoTestDataBuilder().conPlaca("ADF 465")
				.buildModel();
		when(localDateTimeWrapper.now()).thenReturn(FECHA_ENTRADA_MARTES);

		// Act
		long id = facturaVehiculoService.estacionarVehiculo(facturaVehiculoModel);

		// Assert
		assertTrue(id == 1L);
	}

	@Test
	public void vehiculoNoEstaEnElEstacionamientoTest() {
		// Arrange
		FacturaVehiculoModel facturaVehiculoModel = new FacturaVehiculoTestDataBuilder().buildModel();
		when(facturaVehiculoRepository.findByPlacaAndFechaSalidaIsNull(facturaVehiculoModel.getPlaca()))
				.thenReturn(empty());

		// Act
		try {
			facturaVehiculoService.darSalidaVehiculoEstacionado(facturaVehiculoModel.getPlaca());
			fail();
		} catch (VehiculoNoSeEncuentraEstacionadoExcepcion ex) {
			// assert
			assertThat(ex.getMessage())
					.isEqualTo(VehiculoNoSeEncuentraEstacionadoExcepcion.VEHICULO_NO_ESTA_ESTACIONADO);
		}
	}

	@Test
	public void darSalidaEstacionamiento80HorasValorPagar32000() {
		// Arrange
		FacturaVehiculo entity = new FacturaVehiculoTestDataBuilder()
				.conFechaEntrada(LocalDateTime.of(2019, Month.JANUARY, 1, 10, 0)).buildEntity();

		FacturaVehiculoModel facturaVehiculoModel = new FacturaVehiculoTestDataBuilder().buildModel();

		when(facturaVehiculoRepository.findByPlacaAndFechaSalidaIsNull(facturaVehiculoModel.getPlaca()))
				.thenReturn(Optional.of(entity));

		when(localDateTimeWrapper.now()).thenReturn(LocalDateTime.of(2019, Month.JANUARY, 4, 18, 0));

		// Act
		Long total = facturaVehiculoService.darSalidaVehiculoEstacionado(facturaVehiculoModel.getPlaca());

		// Assert
		assertThat(total).isEqualTo(32000L);
	}

	@Test
	public void darSalidaEstacionamientoMoto8HorasCilindraje650ValorPagar6000() {
		// Arrange
		FacturaVehiculo entity = new FacturaVehiculoTestDataBuilder().conPlaca("NIV 17E").conMarca("YAMAHA")
				.conTipo(EtipoVehiculo.MOTO).conCilindraje(CILINDRAJE_MOTO_MAYOR_500)
				.conFechaEntrada(LocalDateTime.of(2019, Month.JANUARY, 15, 7, 0)).buildEntity();

		FacturaVehiculoModel facturaVehiculoModel = new FacturaVehiculoTestDataBuilder().buildModel();

		when(facturaVehiculoRepository.findByPlacaAndFechaSalidaIsNull(facturaVehiculoModel.getPlaca()))
				.thenReturn(Optional.of(entity));

		when(localDateTimeWrapper.now()).thenReturn(LocalDateTime.of(2019, Month.JANUARY, 15, 15, 0));

		// Act
		Long total = facturaVehiculoService.darSalidaVehiculoEstacionado(facturaVehiculoModel.getPlaca());

		// Assert
		assertThat(total).isEqualTo(6000L);
	}

	@Test
	public void darSalidaEstacionamientoMoto10HorasCilindraje150ValorPagar4000() {
		// Arrange
		FacturaVehiculo entity = new FacturaVehiculoTestDataBuilder().conPlaca("NIV 17E").conMarca("YAMAHA")
				.conTipo(EtipoVehiculo.MOTO).conCilindraje(CILINDRAJE_MOTO_MENOR_500)
				.conFechaEntrada(LocalDateTime.of(2019, Month.JANUARY, 15, 7, 0)).buildEntity();

		FacturaVehiculoModel facturaVehiculoModel = new FacturaVehiculoTestDataBuilder().buildModel();

		when(facturaVehiculoRepository.findByPlacaAndFechaSalidaIsNull(facturaVehiculoModel.getPlaca()))
				.thenReturn(Optional.of(entity));

		when(localDateTimeWrapper.now()).thenReturn(LocalDateTime.of(2019, Month.JANUARY, 15, 17, 0));

		// Act
		Long total = facturaVehiculoService.darSalidaVehiculoEstacionado(facturaVehiculoModel.getPlaca());

		// Assert
		assertThat(total).isEqualTo(4000L);
	}

	@Test
	public void ingresoMotoConCilindrajeNull() {
		// Arrange
		FacturaVehiculoModel facturaVehiculoModel = new FacturaVehiculoTestDataBuilder().conPlaca("NIV 17E")
				.conMarca("YAMAHA").conTipo(EtipoVehiculo.MOTO).buildModel();

		// Act
		try {
			facturaVehiculoService.estacionarVehiculo(facturaVehiculoModel);
			fail();
		} catch (VehiculoMotoSinCilindrajeExcepcion ex) {
			// Assert
			assertThat(ex.getMessage()).isEqualTo(VehiculoMotoSinCilindrajeExcepcion.VEHICULO_MOTO_SIN_CILINDRAJE);
		}

	}

	@Test
	public void ingresoMotoConCilindrajeCero() {
		// Arrange
		FacturaVehiculoModel facturaVehiculoModel = new FacturaVehiculoTestDataBuilder().conPlaca("NIV 17E")
				.conCilindraje(0).conMarca("YAMAHA").conTipo(EtipoVehiculo.MOTO).buildModel();

		// Act
		try {
			facturaVehiculoService.estacionarVehiculo(facturaVehiculoModel);
			fail();
		} catch (VehiculoMotoSinCilindrajeExcepcion ex) {
			// Assert
			assertThat(ex.getMessage()).isEqualTo(VehiculoMotoSinCilindrajeExcepcion.VEHICULO_MOTO_SIN_CILINDRAJE);
		}

	}

}
