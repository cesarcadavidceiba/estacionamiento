package co.com.ceiba.estacionamiento.entidades;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import co.com.ceiba.estacionamiento.enumerados.EActiva;
import co.com.ceiba.estacionamiento.modelos.FacturaVehiculoModel;

@Entity
@Table(name = "factura_vehiculo")
public class FacturaVehiculo {

	@Id
	@GeneratedValue
	@Column(name = "id_factura")
	private long idFactura;

	@Column(name = "fechaEntrada")
	private LocalDateTime fechaEntrada;

	@Column(name = "fechaSalida")
	private LocalDateTime fechaSalida;

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
	
	public FacturaVehiculo() {
		
	}

	public FacturaVehiculo(LocalDateTime fechaEntrada, String placa, String marca, String modelo, Integer cilindraje,
			short tipo, short posicion, short swActiva) {
		super();
		this.fechaEntrada = fechaEntrada;
		this.placa = placa;
		this.marca = marca;
		this.modelo = modelo;
		this.cilindraje = cilindraje;
		this.tipo = tipo;
		this.posicion = posicion;
		this.swActiva = swActiva;
	}

	// Getter

	public long getIdFactura() {
		return idFactura;
	}

	public LocalDateTime getFechaEntrada() {
		return fechaEntrada;
	}

	public LocalDateTime getFechaSalida() {
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

	// Sets

	public void setFechaSalida(LocalDateTime fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public static FacturaVehiculo convertirAEntity(FacturaVehiculoModel facturaVehiculoModel) {
		return new FacturaVehiculo(facturaVehiculoModel.getFechaEntrada(), facturaVehiculoModel.getPlaca(),
				facturaVehiculoModel.getMarca(), facturaVehiculoModel.getModelo(), facturaVehiculoModel.getCilindraje(),
				facturaVehiculoModel.getTipo(), facturaVehiculoModel.getPosicion(), facturaVehiculoModel.getSwActiva());
	}

	public static FacturaVehiculo convertirAModelo(FacturaVehiculo facturaVehiculo) {
		return new FacturaVehiculo(facturaVehiculo.getFechaEntrada(), facturaVehiculo.getPlaca(),
				facturaVehiculo.getMarca(), facturaVehiculo.getModelo(), facturaVehiculo.getCilindraje(),
				facturaVehiculo.getTipo(), facturaVehiculo.getPosicion(), facturaVehiculo.getSwActiva());
	}

}