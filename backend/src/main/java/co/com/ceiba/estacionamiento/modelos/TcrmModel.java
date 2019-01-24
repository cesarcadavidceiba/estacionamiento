package co.com.ceiba.estacionamiento.modelos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TcrmModel {
	private final long id;
	private final LocalDateTime fechaVigenciaInicial;
	private final LocalDateTime fechaVigenciaFinal;
	private final String unidad;
	private final float valor;

	@JsonCreator
	public TcrmModel(@JsonProperty("id") long id,
			@JsonProperty("fechaVigenciaInicial") LocalDateTime fechaVigenciaInicial,
			@JsonProperty("fechaVigenciaFinal") LocalDateTime fechaVigenciaFinal, @JsonProperty("unidad") String unidad,
			@JsonProperty("valor") float valor) {
		this.id = id;
		this.fechaVigenciaInicial = fechaVigenciaInicial;
		this.fechaVigenciaFinal = fechaVigenciaFinal;
		this.unidad = unidad;
		this.valor = valor;
	}

	public float getValor() {
		return valor;
	}

	public long getId() {
		return id;
	}

	public LocalDateTime getFechaVigenciaInicial() {
		return fechaVigenciaInicial;
	}

	public LocalDateTime getFechaVigenciaFinal() {
		return fechaVigenciaFinal;
	}

	public String getUnidad() {
		return unidad;
	}

}
