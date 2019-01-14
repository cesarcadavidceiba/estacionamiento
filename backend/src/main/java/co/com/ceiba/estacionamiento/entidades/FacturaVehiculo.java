package co.com.ceiba.estacionamiento.entidades;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import co.com.ceiba.estacionamiento.enumerados.EtipoVehiculo;
import co.com.ceiba.estacionamiento.modelos.FacturaVehiculoModel;

@Entity
@Table(name = "factura_vehiculo")
public class FacturaVehiculo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "fechaEntrada", nullable = false)
	private LocalDateTime fechaEntrada;

	@Column(name = "fechaSalida")
	private LocalDateTime fechaSalida;

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

	@Column(name = "posicion", nullable = false)
	private short posicion;

	public FacturaVehiculo() {

	}

	public FacturaVehiculo(LocalDateTime fechaEntrada, String placa, String marca, String modelo, Integer cilindraje,
			EtipoVehiculo tipo, short posicion) {
		super();
		this.fechaEntrada = fechaEntrada;
		this.placa = placa;
		this.marca = marca;
		this.modelo = modelo;
		this.cilindraje = cilindraje;
		this.tipo = tipo;
		this.posicion = posicion;
	}

	// Getter

	public LocalDateTime getFechaEntrada() {
		return fechaEntrada;
	}

	public long getId() {
		return id;
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

	public EtipoVehiculo getTipo() {
		return tipo;
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
				facturaVehiculoModel.getTipo(), facturaVehiculoModel.getPosicion());
	}

	public static FacturaVehiculoModel convertirAModelo(FacturaVehiculo facturaVehiculo) {

		FacturaVehiculoModel facturaVehiculoModel = new FacturaVehiculoModel(facturaVehiculo.getFechaEntrada(),
				facturaVehiculo.getPlaca(), facturaVehiculo.getMarca(), facturaVehiculo.getModelo(),
				facturaVehiculo.getCilindraje(), facturaVehiculo.getTipo(), facturaVehiculo.getPosicion());

		facturaVehiculoModel.setFechaSalida(facturaVehiculo.getFechaSalida());

		return facturaVehiculoModel;
	}

}