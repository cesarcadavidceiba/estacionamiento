package co.com.ceiba.estacionamiento.test.dominio.integracion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.ceiba.estacionamiento.Aplicacion;
import co.com.ceiba.estacionamiento.entidades.FacturaVehiculo;
import co.com.ceiba.estacionamiento.modelos.FacturaVehiculoModel;
import co.com.ceiba.estacionamiento.repositorios.FacturaVehiculoRepository;
import co.com.ceiba.estacionamiento.test.testdatabuilder.FacturaVehiculoTestDataBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = Aplicacion.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class RestControlladorTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private FacturaVehiculoRepository facturaVehiculoRepository;

	@Autowired
	private ObjectMapper objectMapper;

	// Constantes
	private static final String PLACA_CARRO = "DUX 258";

	@Test
	public void cuandoValidaEntrada_EntoncesEstacionarUnVehiculo() throws IOException, Exception {
		// Arrange
		FacturaVehiculoTestDataBuilder facturaVehiculoTestDataBuilder = new FacturaVehiculoTestDataBuilder().conPlaca("DUX 258");
		FacturaVehiculoModel estacionarDuster = facturaVehiculoTestDataBuilder.build();

		mvc.perform(post("/api/vehiculos").accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(estacionarDuster)).contentType(MediaType.APPLICATION_JSON));

		// Act
		List<FacturaVehiculo> encontrados = facturaVehiculoRepository.findAll();

		// Assert
		assertThat(encontrados).extracting(FacturaVehiculo::getPlaca).containsOnly("DUX 258");
	}

}
