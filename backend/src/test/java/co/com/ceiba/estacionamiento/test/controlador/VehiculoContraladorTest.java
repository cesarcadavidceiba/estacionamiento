package co.com.ceiba.estacionamiento.test.controlador;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.ceiba.estacionamiento.controladores.VehiculoControlador;
import co.com.ceiba.estacionamiento.entidades.FacturaVehiculo;
import co.com.ceiba.estacionamiento.entidades.Vehiculo;
import co.com.ceiba.estacionamiento.enumerados.EtipoVehiculo;
import co.com.ceiba.estacionamiento.modelos.FacturaVehiculoModel;
import co.com.ceiba.estacionamiento.modelos.VehiculoModel;
import co.com.ceiba.estacionamiento.servicios.FacturaVehiculoService;
import co.com.ceiba.estacionamiento.test.testdatabuilder.FacturaVehiculoModelTestDataBuilder;
import co.com.ceiba.estacionamiento.test.testdatabuilder.FacturaVehiculoTestDataBuilder;
import co.com.ceiba.estacionamiento.test.testdatabuilder.VehiculoModelTestDataBuilder;
import co.com.ceiba.estacionamiento.test.testdatabuilder.VehiculoTestDataBuilder;
import co.com.ceiba.estacionamiento.util.LocalDateTimeWrapper;

@RunWith(SpringRunner.class)
@WebMvcTest(VehiculoControlador.class)
public class VehiculoContraladorTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	FacturaVehiculoService facturaVehiculoService;

	@MockBean
	private LocalDateTimeWrapper localDateTimeWrapper;

	@Autowired
	private ObjectMapper objectMapper;

	public void cargarVehiculosEstacionadosTest() throws IOException, Exception {

		// Arrange
		FacturaVehiculoModel estacionarRenult = new FacturaVehiculoModelTestDataBuilder().build();

		VehiculoModel mazda = new VehiculoModelTestDataBuilder().conPlaca("GTE 459").conMarca("Mazda").conModelo("2017")
				.conTipo(EtipoVehiculo.CARRO).build();
		FacturaVehiculoModel estacionarMazda = new FacturaVehiculoModelTestDataBuilder().conVehiculo(mazda).build();

		VehiculoModel chrevrolet = new VehiculoModelTestDataBuilder().conPlaca("ZSD 953").conMarca("Chevrolet")
				.conModelo("2015").conTipo(EtipoVehiculo.CARRO).build();
		FacturaVehiculoModel estacionarChevrolet = new FacturaVehiculoModelTestDataBuilder().conVehiculo(chrevrolet)
				.build();

		VehiculoModel yamaha = new VehiculoModelTestDataBuilder().conPlaca("NIV 17E").conMarca("YAMAHA")
				.conModelo("2018").conCilindraje(150).conTipo(EtipoVehiculo.MOTO).build();
		FacturaVehiculoModel estacionarMotoYahama = new FacturaVehiculoModelTestDataBuilder().conVehiculo(yamaha)
				.build();

		List<FacturaVehiculoModel> todosVehiculosEstacinados = Arrays.asList(estacionarRenult, estacionarMazda,
				estacionarChevrolet, estacionarMotoYahama);

		given(facturaVehiculoService.cargarVehiculosEstacionados()).willReturn(todosVehiculosEstacinados);

		// Act
		mvc.perform(get("/api/vehiculos").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(4)))
				.andExpect(jsonPath("$[0].placa", is(estacionarRenult.getVehiculo().getPlaca())))
				.andExpect(jsonPath("$[1].placa", is(mazda.getPlaca())))
				.andExpect(jsonPath("$[2].placa", is(chrevrolet.getPlaca())))
				.andExpect(jsonPath("$[3].placa", is(yamaha.getPlaca()))).andDo(print());

		verify(facturaVehiculoService, VerificationModeFactory.times(1)).cargarVehiculosEstacionados();
		reset(facturaVehiculoService);
	}

	@Test
	public void estacionarVehiculoTest() throws JsonProcessingException, Exception {
		// Arrange
		final LocalDateTime FECHA_ENTRADA = LocalDateTime.of(2019, Month.JANUARY, 1, 7, 0);
		FacturaVehiculoModel estacionarCarro = new FacturaVehiculoModelTestDataBuilder().build();

		when(localDateTimeWrapper.now()).thenReturn(FECHA_ENTRADA);

		given(facturaVehiculoService.estacionarVehiculo(estacionarCarro)).willReturn(estacionarCarro);

		// Act
		mvc.perform(post("/api/vehiculos").accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(estacionarCarro)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void darSalidaVehiculoTest() throws JsonProcessingException, Exception {
		// Arrange
		final int HORAS_PARQUEADO = 8;
		final LocalDateTime FECHA_ENTRADA = LocalDateTime.of(2019, Month.JANUARY, 1, 7, 0);
		final LocalDateTime FECHA_SALIDA = FECHA_ENTRADA.plusHours(HORAS_PARQUEADO);

		FacturaVehiculo estacionarCarro = new FacturaVehiculoTestDataBuilder().conFechaEntrada(FECHA_ENTRADA).build();

		long valorCobrar = HORAS_PARQUEADO * estacionarCarro.getVehiculo().getTipo().getValorHora();

		when(localDateTimeWrapper.now()).thenReturn(FECHA_SALIDA);

		given(facturaVehiculoService.darSalidaVehiculoEstacionado(estacionarCarro.getVehiculo().getPlaca()))
				.willReturn(valorCobrar);

		// Act
		mvc.perform(put("/api/vehiculos/" + estacionarCarro.getVehiculo().getPlaca())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", is((int) valorCobrar))).andDo(print());
	}

}