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
import co.com.ceiba.estacionamiento.entidades.Vehiculo;
import co.com.ceiba.estacionamiento.modelos.FacturaVehiculoModel;
import co.com.ceiba.estacionamiento.repositorios.FacturaVehiculoRepository;
import co.com.ceiba.estacionamiento.test.testdatabuilder.FacturaVehiculoModelTestDataBuilder;
import co.com.ceiba.estacionamiento.test.testdatabuilder.FacturaVehiculoTestDataBuilder;
import co.com.ceiba.estacionamiento.test.testdatabuilder.VehiculoTestDataBuilder;

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
		FacturaVehiculo estacionarRenult = new FacturaVehiculoTestDataBuilder().conFechaEntrada(LocalDateTime.now())
				.build();

		facturaVehiculoRepository.save(estacionarRenult);

		// Act
		mvc.perform(get("/api/vehiculos").contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void ingresoVehiculoTest() throws IOException, Exception {
		// Arrange
		FacturaVehiculoModel estacionarCarro = new FacturaVehiculoModelTestDataBuilder().build();

		mvc.perform(post("/api/vehiculos").accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(estacionarCarro)).contentType(MediaType.APPLICATION_JSON));

		// Act
		List<FacturaVehiculo> vehiculosEstacionados = facturaVehiculoRepository.findAllByFechaSalidaIsNull();

		// Assert
		assertThat(vehiculosEstacionados).extracting(factura -> factura.getVehiculo().getPlaca())
				.containsOnly(estacionarCarro.getVehiculo().getPlaca());
	}

	@Test
	public void darSalidaVehiculoTest() throws Exception {
		// Arrange
		final LocalDateTime FECHA_ENTRADA = LocalDateTime.of(2019, Month.JANUARY, 15, 7, 0);

		Vehiculo vehiculo = new VehiculoTestDataBuilder().conPlaca("RFT 451").build();
		FacturaVehiculo facturaVehiculo = new FacturaVehiculoTestDataBuilder().conVehiculo(vehiculo)
				.conFechaEntrada(FECHA_ENTRADA).build();

		facturaVehiculoRepository.save(facturaVehiculo);

		// Act
		mvc.perform(put("/api/vehiculos/" + vehiculo.getPlaca()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());

		// Assert
		Optional<FacturaVehiculo> vehiculoEstacionado = facturaVehiculoRepository
				.findByVehiculoPlacaAndFechaSalidaIsNull(vehiculo.getPlaca());

		assertThat(vehiculoEstacionado).isEmpty();
	}
	
	@Test
	public void cargarTrm() throws JsonProcessingException, Exception {
		// Act
		mvc.perform(get("/api/trm").contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

}