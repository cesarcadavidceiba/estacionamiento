package co.com.ceiba.estacionamiento.repositorios;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.com.ceiba.estacionamiento.entidades.FacturaVehiculo;

@Repository
public interface FacturaVehiculoRepository extends JpaRepository<FacturaVehiculo, Serializable> {

	public FacturaVehiculo findByIdFactura(long id);
	
}
