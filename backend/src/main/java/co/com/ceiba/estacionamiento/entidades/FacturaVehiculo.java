package co.com.ceiba.estacionamiento.entidades;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import co.com.ceiba.estacionamiento.modelos.FacturaVehiculoModel;
import co.com.ceiba.estacionamiento.modelos.VehiculoModel;

@Entity
@Table(name = "factura_vehiculo")
public class FacturaVehiculo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Embedded
	private Vehiculo vehiculo;

	@Column(name = "fechaEntrada", nullable = false)
	private LocalDateTime fechaEntrada;

	@Column(name = "fechaSalida")
	private LocalDateTime fechaSalida;

	@Column(name = "posicion", nullable = false)
	private short posicion;

	public FacturaVehiculo() {

	}

	public FacturaVehiculo(LocalDateTime fechaEntrada, Vehiculo vehiculo, short posicion) {
		super();
		this.vehiculo = vehiculo;
		this.fechaEntrada = fechaEntrada;
		this.posicion = posicion;
	}

	// Getter
	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public LocalDateTime getFechaEntrada() {
		return fechaEntrada;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getFechaSalida() {
		return fechaSalida;
	}

	public short getPosicion() {
		return posicion;
	}

	// Sets

	public void setFechaSalida(LocalDateTime fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public static FacturaVehiculo convertirAEntity(FacturaVehiculoModel facturaVehiculoModel) {

		Vehiculo vehiculo = new Vehiculo(facturaVehiculoModel.getVehiculo().getPlaca(),
				facturaVehiculoModel.getVehiculo().getMarca(), facturaVehiculoModel.getVehiculo().getModelo(),
				facturaVehiculoModel.getVehiculo().getTipo());
		
		vehiculo.setCilindraje(facturaVehiculoModel.getVehiculo().getCilindraje());

		return new FacturaVehiculo(facturaVehiculoModel.getFechaEntrada(), vehiculo,
				facturaVehiculoModel.getPosicion());
	}

	public static FacturaVehiculoModel convertirAModelo(FacturaVehiculo facturaVehiculo) {

		VehiculoModel vehiculo = new VehiculoModel(facturaVehiculo.getVehiculo().getPlaca(),
				facturaVehiculo.getVehiculo().getMarca(), facturaVehiculo.getVehiculo().getModelo(),
				facturaVehiculo.getVehiculo().getCilindraje(), facturaVehiculo.getVehiculo().getTipo());

		FacturaVehiculoModel facturaVehiculoModel = new FacturaVehiculoModel(vehiculo, facturaVehiculo.getPosicion());

		facturaVehiculoModel.setFechaEntrada(facturaVehiculo.getFechaEntrada());
		facturaVehiculoModel.setFechaSalida(facturaVehiculo.getFechaSalida());

		return facturaVehiculoModel;
	}

}