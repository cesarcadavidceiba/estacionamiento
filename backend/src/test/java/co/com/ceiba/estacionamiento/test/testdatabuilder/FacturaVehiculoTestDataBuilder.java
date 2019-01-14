package co.com.ceiba.estacionamiento.test.testdatabuilder;

import java.time.LocalDateTime;

import co.com.ceiba.estacionamiento.enumerados.EtipoVehiculo;
import co.com.ceiba.estacionamiento.modelos.FacturaVehiculoModel;

public class FacturaVehiculoTestDataBuilder {
	// Constantes
	private static final LocalDateTime FECHA_ENTRADA = LocalDateTime.now();
	private static final EtipoVehiculo TIPO = EtipoVehiculo.CARRO;
	private static final String PLACA = "DUX 258";
	private static final String MARCA = "RENAULD";
	private static final String MODELO = "2018";
	private static final short POSICION = Short.parseShort("1");

	// Campos

	private LocalDateTime fechaEntrada;

	private LocalDateTime fechaSalida;

	private String placa;

	private String marca;

	private String modelo;

	private Integer cilindraje;

	private EtipoVehiculo tipo;

	private short posicion;

	public FacturaVehiculoTestDataBuilder conFechaEntrada(LocalDateTime fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
		return this;
	}

	public FacturaVehiculoTestDataBuilder conFechaSalida(LocalDateTime fechaSalida) {
		this.fechaSalida = fechaSalida;
		return this;
	}

	public FacturaVehiculoTestDataBuilder conPlaca(String placa) {
		this.placa = placa;
		return this;
	}

	public FacturaVehiculoTestDataBuilder conMarca(String marca) {
		this.marca = marca;
		return this;
	}

	public FacturaVehiculoTestDataBuilder conModelo(String modelo) {
		this.modelo = modelo;
		return this;
	}

	public FacturaVehiculoTestDataBuilder conCilindraje(int cilindraje) {
		this.cilindraje = cilindraje;
		return this;
	}

	public FacturaVehiculoTestDataBuilder conTipo(EtipoVehiculo tipo) {
		this.tipo = tipo;
		return this;
	}

	public FacturaVehiculoModel build() {
		FacturaVehiculoModel facturaVehiculoModel = new FacturaVehiculoModel(this.fechaEntrada, this.placa, this.marca,
				this.modelo, this.cilindraje, this.tipo, this.posicion);
		facturaVehiculoModel.setFechaSalida(this.fechaSalida);
		return facturaVehiculoModel;
	}

}