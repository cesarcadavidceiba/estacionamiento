package co.com.ceiba.estacionamiento.test.testdatabuilder;

import co.com.ceiba.estacionamiento.entidades.Vehiculo;
import co.com.ceiba.estacionamiento.enumerados.EtipoVehiculo;

public class VehiculoTestDataBuilder {
	// Constantes
	private static final EtipoVehiculo TIPO = EtipoVehiculo.CARRO;
	private static final String PLACA = "DUX 258";
	private static final String MARCA = "RENAULD";
	private static final String MODELO = "2018";

	// Campos
	private String placa;

	private String marca;

	private String modelo;

	private Integer cilindraje;

	private EtipoVehiculo tipo;

	public VehiculoTestDataBuilder() {
		this.placa = PLACA;
		this.tipo = TIPO;
		this.marca = MARCA;
		this.modelo = MODELO;
	}

	public VehiculoTestDataBuilder conPlaca(String placa) {
		this.placa = placa;
		return this;
	}

	public VehiculoTestDataBuilder conMarca(String marca) {
		this.marca = marca;
		return this;
	}

	public VehiculoTestDataBuilder conModelo(String modelo) {
		this.modelo = modelo;
		return this;
	}

	public VehiculoTestDataBuilder conCilindraje(int cilindraje) {
		this.cilindraje = cilindraje;
		return this;
	}

	public VehiculoTestDataBuilder conTipo(EtipoVehiculo tipo) {
		this.tipo = tipo;
		return this;
	}

	public Vehiculo build() {
		Vehiculo vehiculo = new Vehiculo(this.placa, this.marca, this.modelo, this.tipo);
		vehiculo.setCilindraje(this.cilindraje);
		return vehiculo;

	}

}