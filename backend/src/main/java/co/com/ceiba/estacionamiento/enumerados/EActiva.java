package co.com.ceiba.estacionamiento.enumerados;

public enum EActiva {

	SI {

		@Override
		public short getId() {
			return 1;
		}

		@Override
		public String getDescripcion() {
			return "Si";
		}

	},
	NO {

		@Override
		public short getId() {
			return 0;
		}

		@Override
		public String getDescripcion() {
			return "No";
		}

	};

	public abstract short getId();

	public abstract String getDescripcion();

	public String getDescripcion(short id) {
		for (EActiva tipo : EActiva.values()) {
			if (tipo.getId() == id) {
				return tipo.getDescripcion();
			}
		}
		return null;
	}

}