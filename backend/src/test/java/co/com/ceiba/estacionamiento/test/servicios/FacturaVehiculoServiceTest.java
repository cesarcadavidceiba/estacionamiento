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
import co.com.ceiba.estacionamiento.entidades.Vehiculo;
import co.com.ceiba.estacionamiento.enumerados.EtipoVehiculo;
import co.com.ceiba.estacionamiento.excepciones.EstacionamientoCarrosLlenoExcepcion;
import co.com.ceiba.estacionamiento.excepciones.EstacionamientoMotosLlenoExcepcion;
import co.com.ceiba.estacionamiento.excepciones.PosicionEstacionamientoOcupadaExcepcion;
import co.com.ceiba.estacionamiento.excepciones.VehiculoMotoSinCilindrajeExcepcion;
import co.com.ceiba.estacionamiento.excepciones.VehiculoNoPuedeIngresarExcepcion;
import co.com.ceiba.estacionamiento.excepciones.VehiculoNoSeEncuentraEstacionadoExcepcion;
import co.com.ceiba.estacionamiento.excepciones.VehiculoSeEncuentraEstacionadoExcepcion;
import co.com.ceiba.estacionamiento.modelos.FacturaVehiculoModel;
import co.com.ceiba.estacionamiento.modelos.VehiculoModel;
import co.com.ceiba.estacionamiento.repositorios.FacturaVehiculoRepository;
import co.com.ceiba.estacionamiento.servicios.FacturaVehiculoService;
import co.com.ceiba.estacionamiento.servicios.impl.FacturaVehiculoServicesImpl;
import co.com.ceiba.estacionamiento.test.testdatabuilder.FacturaVehiculoModelTestDataBuilder;
import co.com.ceiba.estacionamiento.test.testdatabuilder.FacturaVehiculoTestDataBuilder;
import co.com.ceiba.estacionamiento.test.testdatabuilder.VehiculoModelTestDataBuilder;
import co.com.ceiba.estacionamiento.test.testdatabuilder.VehiculoTestDataBuilder;
import co.com.ceiba.estacionamiento.util.LocalDateTimeWrapper;

@RunWith(SpringRunner.class)
public class FacturaVehiculoServiceTest {

	// Constantes
	private static final int CANTIDAD_MAXIMA_CARROS_ESTACIONADOS = 20;
	private static final int CANTIDAD_MAXIMA_MOTOS_ESTACIONADAS = 10;
	private static final short POSICION_ESTACIONAMIENTO_VEHICULO = 1;

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
		FacturaVehiculo facturaVehiculo = new FacturaVehiculoTestDataBuilder().conId(1L).build();
		facturaVehiculoService = new FacturaVehiculoServicesImpl(facturaVehiculoRepository, localDateTimeWrapper);
		when(facturaVehiculoRepository.save(any(FacturaVehiculo.class))).thenReturn(facturaVehiculo);
	}

	@Test
	public void registrarVehiculoTest() {
		// Arrange
		FacturaVehiculoModel facturaVehiculoModel = new FacturaVehiculoModelTestDataBuilder().build();
		when(facturaVehiculoRepository
				.countByVehiculoTipoAndFechaSalidaIsNull(facturaVehiculoModel.getVehiculo().getTipo())).thenReturn(1);

		// Act
		FacturaVehiculoModel model = facturaVehiculoService.estacionarVehiculo(facturaVehiculoModel);

		// Assert
		assertThat(model.getId()).isEqualTo(1L);
	}

	@Test
	public void cargarVehiculosEstacionadosTest() {
		// Arrange
		FacturaVehiculo estacionarRenult = new FacturaVehiculoTestDataBuilder().build();

		Vehiculo mazda = new VehiculoTestDataBuilder().conPlaca("GTE 459").conMarca("Mazda").conModelo("2017")
				.conTipo(EtipoVehiculo.CARRO).build();
		FacturaVehiculo estacionarMazda = new FacturaVehiculoTestDataBuilder().conVehiculo(mazda).build();

		Vehiculo chrevrolet = new VehiculoTestDataBuilder().conPlaca("ZSD 953").conMarca("Chevrolet").conModelo("2015")
				.conTipo(EtipoVehiculo.CARRO).build();
		FacturaVehiculo estacionarChevrolet = new FacturaVehiculoTestDataBuilder().conVehiculo(chrevrolet).build();

		Vehiculo yamaha = new VehiculoTestDataBuilder().conPlaca("NIV 17E").conMarca("YAMAHA").conModelo("2018")
				.conCilindraje(150).conTipo(EtipoVehiculo.MOTO).build();
		FacturaVehiculo estacionarMotoYahama = new FacturaVehiculoTestDataBuilder().conVehiculo(yamaha).build();

		List<FacturaVehiculo> todosVehiculosEstacinados = Arrays.asList(estacionarRenult, estacionarMazda,
				estacionarChevrolet, estacionarMotoYahama);

		when(facturaVehiculoRepository.findAllByFechaSalidaIsNull()).thenReturn(todosVehiculosEstacinados);

		// Act
		List<FacturaVehiculoModel> vehiculosEstacionados = facturaVehiculoService.cargarVehiculosEstacionados();

		// Assert
		assertThat(vehiculosEstacionados.get(0).getVehiculo().getPlaca())
				.isEqualTo(estacionarRenult.getVehiculo().getPlaca());
		assertThat(vehiculosEstacionados.get(1).getVehiculo().getPlaca()).isEqualTo(mazda.getPlaca());
		assertThat(vehiculosEstacionados.get(2).getVehiculo().getPlaca()).isEqualTo(chrevrolet.getPlaca());
		assertThat(vehiculosEstacionados.get(3).getVehiculo().getPlaca()).isEqualTo(yamaha.getPlaca());
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
		FacturaVehiculoModel facturaVehiculoModel = new FacturaVehiculoModelTestDataBuilder().build();
		when(facturaVehiculoRepository.countByVehiculoTipoAndFechaSalidaIsNull(EtipoVehiculo.CARRO))
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
		VehiculoModel vehiculoModel = new VehiculoModelTestDataBuilder().conPlaca("NIV 17E").conCilindraje(150)
				.conMarca("YAMAHA").conModelo("2018").conTipo(EtipoVehiculo.MOTO).build();
		FacturaVehiculoModel facturaVehiculoModel = new FacturaVehiculoModelTestDataBuilder().conVehiculo(vehiculoModel)
				.build();

		when(facturaVehiculoRepository.countByVehiculoTipoAndFechaSalidaIsNull(vehiculoModel.getTipo()))
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
		VehiculoModel vehiculoModel = new VehiculoModelTestDataBuilder().conPlaca("NIV 17E").conMarca("YAMAHA")
				.conModelo("2018").conCilindraje(150).conTipo(EtipoVehiculo.MOTO).build();

		FacturaVehiculoModel facturaVehiculoModel = new FacturaVehiculoModelTestDataBuilder().conVehiculo(vehiculoModel)
				.conPosicion(POSICION_ESTACIONAMIENTO_VEHICULO).build();

		when(facturaVehiculoRepository.existsByVehiculoTipoAndPosicionAndFechaSalidaIsNull(vehiculoModel.getTipo(),
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
		VehiculoModel vehiculoModel = new VehiculoModelTestDataBuilder().conPlaca("NIV 17E").conMarca("YAMAHA")
				.conModelo("2018").conCilindraje(150).conTipo(EtipoVehiculo.MOTO).build();

		FacturaVehiculoModel facturaVehiculoModel = new FacturaVehiculoModelTestDataBuilder().conVehiculo(vehiculoModel)
				.conPosicion(POSICION_ESTACIONAMIENTO_VEHICULO).build();

		when(facturaVehiculoRepository.existsByVehiculoPlacaAndFechaSalidaIsNull(vehiculoModel.getPlaca()))
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
	public void vehiculoNoPuedeEstacionarPlacaIniciEnADiaLunesTest() {
		// Arrange
		VehiculoModel vehiculoModel = new VehiculoModelTestDataBuilder().conPlaca("ADF 465").build();
		FacturaVehiculoModel facturaVehiculoModel = new FacturaVehiculoModelTestDataBuilder().conVehiculo(vehiculoModel)
				.build();
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
		VehiculoModel vehiculoModel = new VehiculoModelTestDataBuilder().conPlaca("ADF 465").build();
		FacturaVehiculoModel facturaVehiculoModel = new FacturaVehiculoModelTestDataBuilder().conVehiculo(vehiculoModel)
				.build();
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
		VehiculoModel vehiculoModel = new VehiculoModelTestDataBuilder().conPlaca("ADF 465").build();
		FacturaVehiculoModel facturaVehiculoModel = new FacturaVehiculoModelTestDataBuilder().conVehiculo(vehiculoModel)
				.build();
		when(localDateTimeWrapper.now()).thenReturn(FECHA_ENTRADA_MARTES);

		// Act
		FacturaVehiculoModel model = facturaVehiculoService.estacionarVehiculo(facturaVehiculoModel);

		// Assert
		assertTrue(model.getId() == 1L);
	}

	@Test
	public void vehiculoNoEstaEnElEstacionamientoTest() {
		// Arrange
		FacturaVehiculoModel facturaVehiculoModel = new FacturaVehiculoModelTestDataBuilder().build();
		when(facturaVehiculoRepository
				.findByVehiculoPlacaAndFechaSalidaIsNull(facturaVehiculoModel.getVehiculo().getPlaca()))
						.thenReturn(empty());

		// Act
		try {
			facturaVehiculoService.darSalidaVehiculoEstacionado(facturaVehiculoModel.getVehiculo().getPlaca());
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
		final LocalDateTime FECHA_ENTRADA = LocalDateTime.of(2019, Month.JANUARY, 1, 10, 0);
		final LocalDateTime FECHA_FINAL = LocalDateTime.of(2019, Month.JANUARY, 4, 18, 0);

		FacturaVehiculo entity = new FacturaVehiculoTestDataBuilder().conFechaEntrada(FECHA_ENTRADA).build();

		FacturaVehiculoModel facturaVehiculoModel = new FacturaVehiculoModelTestDataBuilder().build();

		when(facturaVehiculoRepository
				.findByVehiculoPlacaAndFechaSalidaIsNull(facturaVehiculoModel.getVehiculo().getPlaca()))
						.thenReturn(Optional.of(entity));

		when(localDateTimeWrapper.now()).thenReturn(FECHA_FINAL);

		// Act
		Long total = facturaVehiculoService.darSalidaVehiculoEstacionado(facturaVehiculoModel.getVehiculo().getPlaca());

		// Assert
		assertThat(total).isEqualTo(32000L);
	}

	@Test
	public void darSalidaEstacionamientoMoto8HorasCilindraje650ValorPagar6000() {
		// Arrange
		final int CILINDRAJE_MOTO_MAYOR_500 = 650;
		final LocalDateTime FECHA_ENTRADA = LocalDateTime.of(2019, Month.JANUARY, 15, 7, 0);

		Vehiculo vehiculo = new VehiculoTestDataBuilder().conPlaca("NIV 17E").conMarca("YAMAHA")
				.conTipo(EtipoVehiculo.MOTO).conCilindraje(CILINDRAJE_MOTO_MAYOR_500).build();

		FacturaVehiculo entity = new FacturaVehiculoTestDataBuilder().conVehiculo(vehiculo)
				.conFechaEntrada(FECHA_ENTRADA).build();

		when(facturaVehiculoRepository.findByVehiculoPlacaAndFechaSalidaIsNull(vehiculo.getPlaca()))
				.thenReturn(Optional.of(entity));

		when(localDateTimeWrapper.now()).thenReturn(LocalDateTime.of(2019, Month.JANUARY, 15, 15, 0));

		// Act
		Long total = facturaVehiculoService.darSalidaVehiculoEstacionado(vehiculo.getPlaca());

		// Assert
		assertThat(total).isEqualTo(6000L);
	}

	@Test
	public void darSalidaEstacionamientoMoto10HorasCilindraje150ValorPagar4000() {
		// Arrange
		final LocalDateTime FECHA_ENTRADA = LocalDateTime.of(2019, Month.JANUARY, 15, 7, 0);
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conPlaca("NIV 17E").conMarca("YAMAHA")
				.conTipo(EtipoVehiculo.MOTO).conCilindraje(CILINDRAJE_MOTO_MENOR_500).build();

		FacturaVehiculo entity = new FacturaVehiculoTestDataBuilder().conVehiculo(vehiculo)
				.conFechaEntrada(FECHA_ENTRADA).build();

		when(facturaVehiculoRepository.findByVehiculoPlacaAndFechaSalidaIsNull(vehiculo.getPlaca()))
				.thenReturn(Optional.of(entity));

		when(localDateTimeWrapper.now()).thenReturn(LocalDateTime.of(2019, Month.JANUARY, 15, 17, 0));

		// Act
		Long total = facturaVehiculoService.darSalidaVehiculoEstacionado(vehiculo.getPlaca());

		// Assert
		assertThat(total).isEqualTo(4000L);
	}

	@Test
	public void ingresoMotoConCilindrajeNull() {
		// Arrange
		VehiculoModelTestDataBuilder builder = new VehiculoModelTestDataBuilder().conPlaca("NIV 17E").conMarca("YAMAHA")
				.conTipo(EtipoVehiculo.MOTO);

		// Act
		try {
			builder.build();
			fail();
		} catch (VehiculoMotoSinCilindrajeExcepcion ex) {
			// Assert
			assertThat(ex.getMessage()).isEqualTo(VehiculoMotoSinCilindrajeExcepcion.VEHICULO_MOTO_SIN_CILINDRAJE);
		}

	}

	@Test
	public void ingresoMotoConCilindrajeCero() {
		// Arrange
		VehiculoModelTestDataBuilder vehiculoModel = new VehiculoModelTestDataBuilder().conPlaca("NIV 17E")
				.conCilindraje(0).conMarca("YAMAHA").conTipo(EtipoVehiculo.MOTO);

		// Act
		try {
			vehiculoModel.build();
			fail();
		} catch (VehiculoMotoSinCilindrajeExcepcion ex) {
			// Assert
			assertThat(ex.getMessage()).isEqualTo(VehiculoMotoSinCilindrajeExcepcion.VEHICULO_MOTO_SIN_CILINDRAJE);
		}

	}

}
