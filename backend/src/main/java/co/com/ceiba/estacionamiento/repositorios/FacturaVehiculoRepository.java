package co.com.ceiba.estacionamiento.repositorios;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.com.ceiba.estacionamiento.entidades.FacturaVehiculo;

@Repository("facturaVehiculoRepository")
public interface FacturaVehiculoRepository extends JpaRepository<FacturaVehiculo, Serializable> {

	public abstract FacturaVehiculo findByIdFactura(long id);

}
