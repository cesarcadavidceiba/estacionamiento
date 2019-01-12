package co.com.ceiba.estacionamiento.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import co.com.ceiba.estacionamiento.modelos.VehiculoModel;
import co.com.ceiba.estacionamiento.servicios.FacturaVehiculoService;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/rest")
public class RestControlador {

	@Autowired
	@Qualifier("facturaVehiculoServicesImp")
	private FacturaVehiculoService facturaVehiculoService;

	@PostMapping("/registrarFactura")
	public void registrarFactura(@ModelAttribute(name = "vehiculoModel") VehiculoModel vehiculoModel) {

	}
	
	@GetMapping("/verificar")
	public ResponseEntity<String> verificarConexion() {
		return new ResponseEntity<String>("Hola Mundo", HttpStatus.OK);
	}

}
