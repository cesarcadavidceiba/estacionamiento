package co.com.ceiba.estacionamiento.modelos;

public class VehiculoModel {

	private String placa;

	private String marca;

	private String modelo;

	private Integer cilindraje;

	private short tipo;

	public VehiculoModel(String placa, String marca, String modelo, Integer cilindraje, short tipo) {
		super();
		this.placa = placa;
		this.marca = marca;
		this.modelo = modelo;
		this.cilindraje = cilindraje;
		this.tipo = tipo;
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

	public Integer getCilindraje() {
		return cilindraje;
	}

	public short getTipo() {
		return tipo;
	}

}