package co.com.ceiba.estacionamiento.entidades;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import co.com.ceiba.estacionamiento.enumerados.EtipoVehiculo;

@Embeddable
public class Vehiculo {

	@Column(name = "placa", nullable = false)
	private String placa;

	@Column(name = "marca", nullable = false)
	private String marca;

	@Column(name = "modelo", nullable = false)
	private String modelo;

	@Column(name = "cilindraje")
	private Integer cilindraje;

	@Column(name = "tipo", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private EtipoVehiculo tipo;

	public Vehiculo() {

	}

	public Vehiculo(String placa, String marca, String modelo, EtipoVehiculo tipo) {
		this.placa = placa;
		this.marca = marca;
		this.modelo = modelo;
		this.tipo = tipo;
	}

	public Integer getCilindraje() {
		return cilindraje;
	}

	public void setCilindraje(Integer cilindraje) {
		this.cilindraje = cilindraje;
	}

	public String getPlaca() {
		return placa;
	}

	public String getMarca() {
		return marca;
	}

	public String getModelo() {
		return modelo;
	}

	public EtipoVehiculo getTipo() {
		return tipo;
	}

}
