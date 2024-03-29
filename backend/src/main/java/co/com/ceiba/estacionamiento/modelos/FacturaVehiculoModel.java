package co.com.ceiba.estacionamiento.modelos;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.com.ceiba.estacionamiento.enumerados.EtipoVehiculo;
import co.com.ceiba.estacionamiento.excepciones.VehiculoNoPuedeIngresarExcepcion;

public class FacturaVehiculoModel {

	private long id;

	private VehiculoModel vehiculo;

	private LocalDateTime fechaEntrada;

	private LocalDateTime fechaSalida;

	private short posicion;

	// Constantes
	private static final String PLACA_INICIA_CON_A = "A";
	private static final int NUEVE_HORAS = 9;
	private static final int VEINTICUATRO_HORAS = 24;
	private static final int CILINDRAJE_MAXIMO_MOTO = 500;
	private static final int VALOR_EXTRA_MOTO = 2000;
	private static final double SESENTA_MINUTOS = 60;

	@JsonCreator
	public FacturaVehiculoModel(@JsonProperty("vehiculo") VehiculoModel vehiculo,
			@JsonProperty("posicion") short posicion) {
		super();
		this.vehiculo = vehiculo;
		this.posicion = posicion;
	}

	// Acciones
	public void vehiculoPuedeEstacionar() {
		if (vehiculo.getPlaca().startsWith(PLACA_INICIA_CON_A)) {
			DayOfWeek dayOfWeek = fechaEntrada.getDayOfWeek();
			if (dayOfWeek == DayOfWeek.SUNDAY || dayOfWeek == DayOfWeek.MONDAY) {
				throw new VehiculoNoPuedeIngresarExcepcion();
			}
		}
	}

	private long obtenerCantidadHoras() {
		long minutos = ChronoUnit.MINUTES.between(fechaEntrada, fechaSalida);
		return (int) Math.ceil(minutos / SESENTA_MINUTOS);
	}

	public Long obtenerValorPagar() {
		long valorCalculado = 0;

		long cantidadHoras = obtenerCantidadHoras();

		if (cantidadHoras > VEINTICUATRO_HORAS) {
			long diasEstacionado = cantidadHoras / VEINTICUATRO_HORAS;
			long horas = cantidadHoras % VEINTICUATRO_HORAS;
			valorCalculado = diasEstacionado * vehiculo.getTipo().getValorDia()
					+ horas * vehiculo.getTipo().getValorHora();
		} else {
			if (cantidadHoras < NUEVE_HORAS) {
				valorCalculado = cantidadHoras * vehiculo.getTipo().getValorHora();
			} else {
				valorCalculado = vehiculo.getTipo().getValorDia();
			}
		}

		if (vehiculo.getTipo() == EtipoVehiculo.MOTO && vehiculo.getCilindraje() > CILINDRAJE_MAXIMO_MOTO) {
			valorCalculado += VALOR_EXTRA_MOTO;
		}

		return valorCalculado;
	}

	// Getter

	public long getId() {
		return id;
	}

	public LocalDateTime getFechaEntrada() {
		return fechaEntrada;
	}

	public void setFechaEntrada(LocalDateTime fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public LocalDateTime getFechaSalida() {
		return fechaSalida;
	}

	public VehiculoModel getVehiculo() {
		return vehiculo;
	}

	public short getPosicion() {
		return posicion;
	}

	// Setter

	public void setId(long id) {
		this.id = id;
	}

	public void setFechaSalida(LocalDateTime fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
}