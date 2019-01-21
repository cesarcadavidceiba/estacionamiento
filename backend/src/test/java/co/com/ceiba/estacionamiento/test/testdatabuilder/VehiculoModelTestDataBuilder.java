package co.com.ceiba.estacionamiento.test.testdatabuilder;

import co.com.ceiba.estacionamiento.enumerados.EtipoVehiculo;
import co.com.ceiba.estacionamiento.modelos.VehiculoModel;

public class VehiculoModelTestDataBuilder {
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

	public VehiculoModelTestDataBuilder() {
		this.placa = PLACA;
		this.tipo = TIPO;
		this.marca = MARCA;
		this.modelo = MODELO;
	}

	public VehiculoModelTestDataBuilder conPlaca(String placa) {
		this.placa = placa;
		return this;
	}

	public VehiculoModelTestDataBuilder conMarca(String marca) {
		this.marca = marca;
		return this;
	}

	public VehiculoModelTestDataBuilder conMarcaYamaha() {
		this.marca = "YAMAHA";
		return this;
	}

	
	public VehiculoModelTestDataBuilder conModelo(String modelo) {
		this.modelo = modelo;
		return this;
	}

	public VehiculoModelTestDataBuilder conCilindraje(int cilindraje) {
		this.cilindraje = cilindraje;
		return this;
	}

	public VehiculoModelTestDataBuilder conTipo(EtipoVehiculo tipo) {
		this.tipo = tipo;
		return this;
	}

	public VehiculoModel build() {
		return new VehiculoModel(this.placa, this.marca, this.modelo, this.cilindraje, this.tipo);
	}

}