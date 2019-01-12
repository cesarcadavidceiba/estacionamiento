package co.com.ceiba.estacionamiento.entidades;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "factura_vehiculo")
public class FacturaVehiculo {

	@Id
	@GeneratedValue
	@Column(name = "id_factura")
	private long idFactura;

	@Column(name = "cantidadHoras")
	private int cantidadHoras;

	@Column(name = "valorFactura")
	private double valorFactura;

	@Column(name = "fechaEntrada")
	private Date fechaEntrada;

	@Column(name = "fechaSalida")
	private Date fechaSalida;

	@Column(name = "placa")
	private String placa;

	@Column(name = "marca")
	private String marca;

	@Column(name = "modelo")
	private String modelo;

	@Column(name = "cilindraje")
	private Integer cilindraje;

	@Column(name = "tipo")
	private short tipo;

	@Column(name = "posicion")
	private short posicion;

	@Column(name = "sw_activa")
	private short swActiva;

	public FacturaVehiculo(int cantidadHoras, double valorFactura, Date fechaEntrada, Date fechaSalida, String placa,
			String marca, String modelo, Integer cilindraje, short tipo, short posicion, short swActiva) {
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
		this.posicion = posicion;
		this.swActiva = swActiva;
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

	public short getSwActiva() {
		return swActiva;
	}

	public short getPosicion() {
		return posicion;
	}

	public void setPosicion(short posicion) {
		this.posicion = posicion;
	}

	public void setIdFactura(long idFactura) {
		this.idFactura = idFactura;
	}

	public void setCantidadHoras(int cantidadHoras) {
		this.cantidadHoras = cantidadHoras;
	}

	public void setValorFactura(double valorFactura) {
		this.valorFactura = valorFactura;
	}

	public void setFechaEntrada(Date fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public void setCilindraje(Integer cilindraje) {
		this.cilindraje = cilindraje;
	}

	public void setTipo(short tipo) {
		this.tipo = tipo;
	}

	public void setSwActiva(short swActiva) {
		this.swActiva = swActiva;
	}

}
