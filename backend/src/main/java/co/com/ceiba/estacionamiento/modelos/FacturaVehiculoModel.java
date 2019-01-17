package co.com.ceiba.estacionamiento.modelos;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.com.ceiba.estacionamiento.enumerados.EtipoVehiculo;
import co.com.ceiba.estacionamiento.excepciones.VehiculoNoPuedeIngresarExcepcion;

public class FacturaVehiculoModel {

	private long idFactura;

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
	private static final int SESENTA_MINUTOS = 60;

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
		Long valorCalculado = 0L;

		long cantidadHoras = obtenerCantidadHoras();
		boolean continuar = true;

		long horasRestantes = cantidadHoras;

		while (continuar) {
			if (horasRestantes > VEINTICUATRO_HORAS) {
				valorCalculado += vehiculo.getTipo().getValorDia();
				horasRestantes = horasRestantes - VEINTICUATRO_HORAS;
			} else {
				if (horasRestantes > NUEVE_HORAS) {
					// Se cobra el dia
					valorCalculado += vehiculo.getTipo().getValorDia();
				} else {
					valorCalculado += vehiculo.getTipo().getValorHora() * horasRestantes;
				}
				continuar = false;
			}
		}

		if (vehiculo.getTipo() == EtipoVehiculo.MOTO && vehiculo.getCilindraje() > CILINDRAJE_MAXIMO_MOTO) {
			valorCalculado += VALOR_EXTRA_MOTO;
		}

		return valorCalculado;
	}

	// Getter

	public long getIdFactura() {
		return idFactura;
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

	public void setFechaSalida(LocalDateTime fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
}