package co.com.ceiba.estacionamiento.modelos;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.com.ceiba.estacionamiento.enumerados.EtipoVehiculo;
import co.com.ceiba.estacionamiento.excepciones.VehiculoMotoSinCilindrajeExcepcion;
import co.com.ceiba.estacionamiento.excepciones.VehiculoNoPuedeIngresarExcepcion;

public class FacturaVehiculoModel {

	private long idFactura;

	private LocalDateTime fechaEntrada;

	private LocalDateTime fechaSalida;

	private String placa;

	private String marca;

	private String modelo;

	private Integer cilindraje;

	private EtipoVehiculo tipo;

	private short posicion;

	// Constantes
	private static final String PLACA_INICIA_CON_A = "A";
	private static final int NUEVE_HORAS = 9;
	private static final int VEINTICUATRO_HORAS = 24;
	private static final int CILINDRAJE_MAXIMO_MOTO = 500;
	private static final int VALOR_EXTRA_MOTO = 2000;
	private static final int SESENTA_MINUTOS = 60;
	private static final int CILINDRAJE_MINIMO = 0;

	@JsonCreator
	public FacturaVehiculoModel(@JsonProperty("placa") String placa, @JsonProperty("marca") String marca,
			@JsonProperty("modelo") String modelo, @JsonProperty("cilindraje") Integer cilindraje,
			@JsonProperty("tipo") EtipoVehiculo tipo, @JsonProperty("posicion") short posicion) {
		super();
		this.placa = placa;
		this.marca = marca;
		this.modelo = modelo;
		this.cilindraje = cilindraje;
		this.tipo = tipo;
		this.posicion = posicion;
	}

	// Acciones
	public void vehiculoPuedeEstacionar() {
		if (placa.startsWith(PLACA_INICIA_CON_A)) {
			DayOfWeek dayOfWeek = fechaEntrada.getDayOfWeek();
			if (dayOfWeek == DayOfWeek.SUNDAY || dayOfWeek == DayOfWeek.MONDAY) {
				throw new VehiculoNoPuedeIngresarExcepcion();
			}
		}
	}

	public void validarCilindrajeMotos() {
		if (tipo == EtipoVehiculo.MOTO && (cilindraje == null || cilindraje == CILINDRAJE_MINIMO)) {
			throw new VehiculoMotoSinCilindrajeExcepcion();
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
				valorCalculado += tipo.getValorDia();
				horasRestantes = horasRestantes - VEINTICUATRO_HORAS;
			} else {
				if (horasRestantes > NUEVE_HORAS) {
					// Se cobra el dia
					valorCalculado += tipo.getValorDia();
				} else {
					valorCalculado += tipo.getValorHora() * horasRestantes;
				}
				continuar = false;
			}
		}

		if (tipo == EtipoVehiculo.MOTO && cilindraje > CILINDRAJE_MAXIMO_MOTO) {
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

	// Setter

	public void setFechaSalida(LocalDateTime fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

}