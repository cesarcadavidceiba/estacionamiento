package co.com.ceiba.estacionamiento.test.controlador;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.time.LocalDateTime;
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

import co.com.ceiba.estacionamiento.controladores.VehiculoControlador;
import co.com.ceiba.estacionamiento.enumerados.EtipoVehiculo;
import co.com.ceiba.estacionamiento.modelos.FacturaVehiculoModel;
import co.com.ceiba.estacionamiento.servicios.FacturaVehiculoService;
import co.com.ceiba.estacionamiento.test.testdatabuilder.FacturaVehiculoTestDataBuilder;

@RunWith(SpringRunner.class)
@WebMvcTest(VehiculoControlador.class)
public class VehiculoContraladorTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	FacturaVehiculoService facturaVehiculoService;

	@Test
	public void dadoVehiculosEstacionados_cuandoObtengaVehiculosEstacionados_entoncesDevuelvaArregloJson()
			throws IOException, Exception {

		// Arrange
		FacturaVehiculoTestDataBuilder builder = new FacturaVehiculoTestDataBuilder();
		FacturaVehiculoModel estacionarRenult = builder.buildModel();

		builder = new FacturaVehiculoTestDataBuilder().conPlaca("GTE 459")
				.conFechaEntrada(LocalDateTime.of(2019, 01, 15, 9, 10)).conMarca("Mazda").conModelo("2017")
				.conTipo(EtipoVehiculo.CARRO);
		FacturaVehiculoModel estacionarMazda = builder.buildModel();

		builder = new FacturaVehiculoTestDataBuilder().conPlaca("ZSD 953")
				.conFechaEntrada(LocalDateTime.of(2019, 01, 15, 8, 30)).conMarca("Chevrolet").conModelo("2015")
				.conTipo(EtipoVehiculo.CARRO);
		FacturaVehiculoModel estacionarChevrolet = builder.buildModel();

		builder = new FacturaVehiculoTestDataBuilder().conPlaca("NIV 17E")
				.conFechaEntrada(LocalDateTime.of(2019, 01, 15, 7, 10)).conMarca("YAMAHA").conModelo("2018")
				.conCilindraje(150)
				.conTipo(EtipoVehiculo.MOTO);
		FacturaVehiculoModel estacionarMotoYahama = builder.buildModel();

		List<FacturaVehiculoModel> todosVehiculosEstacinados = Arrays.asList(estacionarRenult, estacionarMazda,
				estacionarChevrolet, estacionarMotoYahama);

		// Act
		given(facturaVehiculoService.cargarVehiculosEstacionados()).willReturn(todosVehiculosEstacinados);

		// Assert
		mvc.perform(get("/api/vehiculos").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(4))).andExpect(jsonPath("$[0].placa", is(estacionarRenult.getPlaca())))
				.andExpect(jsonPath("$[1].placa", is(estacionarMazda.getPlaca())))
				.andExpect(jsonPath("$[2].placa", is(estacionarChevrolet.getPlaca())))
				.andExpect(jsonPath("$[3].placa", is(estacionarMotoYahama.getPlaca())));

		verify(facturaVehiculoService, VerificationModeFactory.times(1)).cargarVehiculosEstacionados();
		reset(facturaVehiculoService);
	}
	
	
	

}