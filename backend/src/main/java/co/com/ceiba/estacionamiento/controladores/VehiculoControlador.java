package co.com.ceiba.estacionamiento.controladores;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.ceiba.estacionamiento.modelos.FacturaVehiculoModel;
import co.com.ceiba.estacionamiento.servicios.FacturaVehiculoService;

@RestController
@RequestMapping("/api")
public class VehiculoControlador {

	private FacturaVehiculoService facturaVehiculoService;

	public VehiculoControlador(FacturaVehiculoService facturaVehiculoService) {
		this.facturaVehiculoService = facturaVehiculoService;
	}

	@PostMapping("/vehiculos")
	public void ingresarVehiculoEstacionamiento(@RequestBody FacturaVehiculoModel facturaVehiculoModel) {
		facturaVehiculoService.registrarVehiculo(facturaVehiculoModel);
	}

	@PutMapping("/vehiculos/:placa")
	public void darSalidaVehiculoEstacionamiento(@RequestBody long placa) {

	}

	@GetMapping("/vehiculos/:placa")
	public FacturaVehiculoModel cargarVehiculoEstacionado(@RequestBody long placa) {
		return null;
	}

	@GetMapping("/verificar")
	public String verificarConexion() {
		return "Hola Mundo";
	}

}