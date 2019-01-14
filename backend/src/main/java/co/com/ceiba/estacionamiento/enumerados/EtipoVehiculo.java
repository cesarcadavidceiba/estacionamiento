package co.com.ceiba.estacionamiento.enumerados;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EtipoVehiculo {

	CARRO {

		@Override
		public int getId() {
			return 0;
		}

		@Override
		public int getValorHora() {
			return 1000;
		}

		@Override
		public int getValorDia() {
			return 8000;
		}

	},
	MOTO {

		@Override
		public int getId() {
			return 1;
		}

		@Override
		public int getValorHora() {
			return 500;
		}

		@Override
		public int getValorDia() {
			return 4000;
		}

	};

	@JsonValue
	public abstract int getId();

	public abstract int getValorHora();

	public abstract int getValorDia();
}
