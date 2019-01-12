package co.com.ceiba.estacionamiento.modelos;

import java.util.Date;

public class FacturaVehiculoModel {

	private long idFactura;

	private int cantidadHoras;

	private double valorFactura;

	private Date fechaEntrada;

	private Date fechaSalida;

	private String placa;

	private String marca;

	private String modelo;

	private Integer cilindraje;

	private short tipo;

	public FacturaVehiculoModel(int cantidadHoras, double valorFactura, Date fechaEntrada, Date fechaSalida,
			String placa, String marca, String modelo, Integer cilindraje, short tipo) {
		super();
		this.cantidadHoras = cantidadHoras;
		this.valorFactura = valorFactura;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.placa = placa;
		this.marca = marca;
		this.modelo = modelo;
		this.cilindraje = cilindraje;
		this.tipo = tipo;
	}

	public long getIdFactura() {
		return idFactura;
	}

	public int getCantidadHoras() {
		return cantidadHoras;
	}

	public double getValorFactura() {
		return valorFactura;
	}

	public Date getFechaEntrada() {
		return fechaEntrada;
	}

	public Date getFechaSalida() {
		return fechaSalida;
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
