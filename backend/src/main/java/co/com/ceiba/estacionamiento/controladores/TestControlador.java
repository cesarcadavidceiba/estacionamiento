package co.com.ceiba.estacionamiento.controladores;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestControlador {
	
	@GetMapping("/hello")
	public String test() {
		return "Hello World !!!";
	}
	
}
