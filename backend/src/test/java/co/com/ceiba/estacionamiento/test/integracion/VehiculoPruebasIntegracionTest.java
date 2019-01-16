package co.com.ceiba.estacionamiento.test.integracion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

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

import com.fasterxml.jackson.core.JsonProcessingException;
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
public class VehiculoPruebasIntegracionTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private FacturaVehiculoRepository facturaVehiculoRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void cargarVehiculosEstacionadosTest() throws JsonProcessingException, Exception {
		// Arrange
		FacturaVehiculoTestDataBuilder builder = new FacturaVehiculoTestDataBuilder();
		FacturaVehiculo estacionarRenult = builder.conFechaEntrada(LocalDateTime.now()).buildEntity();

		facturaVehiculoRepository.save(estacionarRenult);

		// Act
		mvc.perform(get("/api/vehiculos").contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void ingresoVehiculoTest() throws IOException, Exception {
		// Arrange
		FacturaVehiculoTestDataBuilder facturaVehiculoTestDataBuilder = new FacturaVehiculoTestDataBuilder();
		FacturaVehiculoModel estacionarCarro = facturaVehiculoTestDataBuilder.buildModel();

		mvc.perform(post("/api/vehiculos").accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(estacionarCarro)).contentType(MediaType.APPLICATION_JSON));

		// Act
		List<FacturaVehiculo> vehiculosEstacionados = facturaVehiculoRepository.findAllByFechaSalidaIsNull();

		// Assert
		assertThat(vehiculosEstacionados).extracting(FacturaVehiculo::getPlaca)
				.containsOnly(estacionarCarro.getPlaca());
	}

	@Test
	public void darSalidaVehiculoTest() throws Exception {
		// Arrange
		FacturaVehiculoTestDataBuilder builder = new FacturaVehiculoTestDataBuilder();
		FacturaVehiculo estacionarRenult = builder.conPlaca("RFT 451")
				.conFechaEntrada(LocalDateTime.of(2019, Month.JANUARY, 15, 7, 0)).buildEntity();

		facturaVehiculoRepository.save(estacionarRenult);

		// Act
		mvc.perform(put("/api/vehiculos/" + estacionarRenult.getPlaca()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print());

		// Assert
		Optional<FacturaVehiculo> vehiculoEstacionado = facturaVehiculoRepository
				.findByPlacaAndFechaSalidaIsNull(estacionarRenult.getPlaca());

		assertThat(vehiculoEstacionado).isEmpty();
	}

}