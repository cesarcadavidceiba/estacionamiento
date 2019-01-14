package co.com.ceiba.estacionamiento.enumerados;

public enum EtipoVehiculo {

	CARRO {

		@Override
		public short getId() {
			return 1;
		}

		@Override
		public String getDescripcion() {
			return "Carro";
		}

	},
	MOTO {

		@Override
		public short getId() {
			return 2;
		}

		@Override
		public String getDescripcion() {
			return "Moto";
		}

	};

	public abstract short getId();

	public abstract String getDescripcion();

	public String getDescripcion(short id) {
		for (EtipoVehiculo tipo : EtipoVehiculo.values()) {
			if (tipo.getId() == id) {
				return tipo.getDescripcion();
			}
		}
		return null;
	}

}
