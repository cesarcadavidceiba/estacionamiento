package co.com.ceiba.estacionamiento.test.testdatabuilder;

import java.time.LocalDateTime;

import co.com.ceiba.estacionamiento.entidades.FacturaVehiculo;
import co.com.ceiba.estacionamiento.entidades.Vehiculo;

public class FacturaVehiculoTestDataBuilder {
	// Constantes
	private static final long ID = 1L;
	private static final LocalDateTime FECHA_ENTRADA = LocalDateTime.now();
	private static final Vehiculo VEHICULO = new VehiculoTestDataBuilder().build();
	private static final short POSICION = Short.parseShort("1");

	// Campos
	private long id;

	private LocalDateTime fechaEntrada;

	private Vehiculo vehiculo;

	private short posicion;

	public FacturaVehiculoTestDataBuilder() {
		this.id = ID;
		this.fechaEntrada = FECHA_ENTRADA;
		this.vehiculo = VEHICULO;
		this.posicion = POSICION;
	}

	public FacturaVehiculoTestDataBuilder conId(long id) {
		this.id = id;
		return this;
	}

	public FacturaVehiculoTestDataBuilder conVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
		return this;
	}

	public FacturaVehiculoTestDataBuilder conFechaEntrada(LocalDateTime fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
		return this;
	}

	public FacturaVehiculoTestDataBuilder conPosicion(short posicion) {
		this.posicion = posicion;
		return this;
	}

	public FacturaVehiculo build() {
		FacturaVehiculo facturaVehiculo = new FacturaVehiculo(this.fechaEntrada, this.vehiculo, this.posicion);
		facturaVehiculo.setId(this.id);
		return facturaVehiculo;
	}
}