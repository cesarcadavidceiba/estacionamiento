package co.com.ceiba.estacionamiento.test.testdatabuilder;

import java.time.LocalDateTime;

import co.com.ceiba.estacionamiento.modelos.FacturaVehiculoModel;
import co.com.ceiba.estacionamiento.modelos.VehiculoModel;

public class FacturaVehiculoModelTestDataBuilder {
	// Constantes
	private static final VehiculoModel VEHICULO = new VehiculoModelTestDataBuilder().build();
	private static final short POSICION = Short.parseShort("1");

	// Campos
	private VehiculoModel vehiculo;

	private short posicion;

	private LocalDateTime fechaSalida;

	public FacturaVehiculoModelTestDataBuilder() {
		this.vehiculo = VEHICULO;
		this.posicion = POSICION;
	}

	public FacturaVehiculoModelTestDataBuilder conVehiculo(VehiculoModel vehiculo) {
		this.vehiculo = vehiculo;
		return this;
	}

	public FacturaVehiculoModelTestDataBuilder conPosicion(short posicion) {
		this.posicion = posicion;
		return this;
	}

	public FacturaVehiculoModelTestDataBuilder conFechaSalida(LocalDateTime fechaSalida) {
		this.fechaSalida = fechaSalida;
		return this;
	}

	public FacturaVehiculoModel build() {
		FacturaVehiculoModel facturaVehiculoModel = new FacturaVehiculoModel(this.vehiculo, this.posicion);
		facturaVehiculoModel.setFechaSalida(this.fechaSalida);
		return facturaVehiculoModel;
	}
}