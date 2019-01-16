package co.com.ceiba.estacionamiento.controladores;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public long ingresarVehiculoEstacionamiento(@RequestBody FacturaVehiculoModel facturaVehiculoModel) {
		return facturaVehiculoService.estacionarVehiculo(facturaVehiculoModel);
	}

	@PutMapping("/vehiculos/{placa}")
	public Long darSalidaVehiculoEstacionado(@PathVariable String placa) {
		return facturaVehiculoService.darSalidaVehiculoEstacionado(placa);
	}

	@GetMapping("/vehiculos")
	public List<FacturaVehiculoModel> cargarVehiculosEstacionados() {
		return facturaVehiculoService.cargarVehiculosEstacionados();
	}

}