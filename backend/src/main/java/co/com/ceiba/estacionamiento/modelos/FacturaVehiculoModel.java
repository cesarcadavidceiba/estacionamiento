package co.com.ceiba.estacionamiento.modelos;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.com.ceiba.estacionamiento.enumerados.EActiva;
import co.com.ceiba.estacionamiento.excepciones.VehiculoNoPuedeIngresarExcepcion;
import co.com.ceiba.estacionamiento.util.LocalDateTimeWrapper;

public class FacturaVehiculoModel {

	private long idFactura;

	private LocalDateTime fechaEntrada;

	private LocalDateTime fechaSalida;

	private String placa;

	private String marca;

	private String modelo;

	private Integer cilindraje;

	private short tipo;

	private short posicion;

	private short swActiva;

	// Constantes
	private static final String PLACA_INICIA_CON_A = "A";
	
	@JsonCreator
	public FacturaVehiculoModel(@JsonProperty("fechaEntrada") LocalDateTime fechaEntrada, 
			@JsonProperty("placa") String placa, @JsonProperty("marca") String marca, 
			@JsonProperty("modelo") String modelo,
			@JsonProperty("cilindraje") Integer cilindraje, 
			@JsonProperty("tipo") short tipo, 
			@JsonProperty("posicion") short posicion, 
			@JsonProperty("swActiva") short swActiva) {
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

	// Acciones
	public void vehiculoPuedeEstacionar() {
		if (placa.startsWith(PLACA_INICIA_CON_A)) {
			DayOfWeek dayOfWeek = new LocalDateTimeWrapper().now().getDayOfWeek();
			if (dayOfWeek != DayOfWeek.SUNDAY && dayOfWeek != DayOfWeek.MONDAY) {
				throw new VehiculoNoPuedeIngresarExcepcion();
			}
		}

		fechaEntrada = new LocalDateTimeWrapper().now();
		swActiva = EActiva.SI.getId();
	}

	public int obtenerCantidadHoras() {
		return 0;
	}

	public String obtenerValorFactura() {
		return null;
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

	public short getPosicion() {
		return posicion;
	}

	public short getSwActiva() {
		return swActiva;
	}

	// Setter

	public void setFechaSalida(LocalDateTime fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

}