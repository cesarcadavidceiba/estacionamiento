package co.com.ceiba.estacionamiento.modelos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.com.ceiba.estacionamiento.enumerados.EtipoVehiculo;
import co.com.ceiba.estacionamiento.excepciones.VehiculoMotoSinCilindrajeExcepcion;

public class VehiculoModel {

	private String placa;

	private String marca;

	private String modelo;

	private Integer cilindraje;

	private EtipoVehiculo tipo;

	// Constantes
	private static final int CILINDRAJE_MINIMO = 0;

	@JsonCreator
	public VehiculoModel(@JsonProperty("placa") String placa, @JsonProperty("marca") String marca,
			@JsonProperty("modelo") String modelo, @JsonProperty("cilindraje") Integer cilindraje,
			@JsonProperty("tipo") EtipoVehiculo tipo) {
		if (tipo == EtipoVehiculo.MOTO && (cilindraje == null || cilindraje > CILINDRAJE_MINIMO)) {
			throw new VehiculoMotoSinCilindrajeExcepcion();
		}

		this.placa = placa;
		this.marca = marca;
		this.modelo = modelo;
		this.cilindraje = cilindraje;
		this.tipo = tipo;
	}

	// Getter
	public String getPlaca() {
		return placa.toUpperCase();
	}

	public String getMarca() {
		return marca;
	}

	public String getModelo() {
		return modelo;
	}

	public Integer getCilindraje() {
		return cilindraje;
	}

	public EtipoVehiculo getTipo() {
		return tipo;
	}
}