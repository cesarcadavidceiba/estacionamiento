package co.com.ceiba.estacionamiento.repositorios;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.com.ceiba.estacionamiento.entidades.FacturaVehiculo;
import co.com.ceiba.estacionamiento.enumerados.EtipoVehiculo;

@Repository
public interface FacturaVehiculoRepository extends JpaRepository<FacturaVehiculo, Serializable> {

	/**
	 * 
	 * Permite cargar un vehiculo estacionado de acuerdo a la placa
	 * 
	 * @return lista de vehiculos estacionados
	 */
	List<FacturaVehiculo> findAllByFechaSalidaIsNull();

	/**
	 * 
	 * Permite obtener el numero de vehiculos parqueados por tipo
	 * 
	 * @param tipoVehiculo
	 * @return cantidad de vehiculos parqueados por tipo
	 */
	int countByTipoAndFechaSalidaIsNull(EtipoVehiculo tipoVehiculo);

	/**
	 * 
	 * Permite conocer si existe ya un vehiculo estacionado en una posicion dada de
	 * acuerdo al tipo de vehiculo
	 * 
	 * @param tipoVehiculo
	 * @param posicion
	 * @return vehiculo parqueado por tipo y posicion
	 */
	boolean existsByTipoAndPosicionAndFechaSalidaIsNull(EtipoVehiculo tipoVehiculo, short posicion);

	/**
	 * 
	 * Permite conocer si existe un vehiculo estacionado de acuerdo a la placa
	 * 
	 * @param placa
	 * @return vehiculo parqueado por tipo y placa
	 */
	boolean existsByPlacaAndFechaSalidaIsNull(String placa);
	
	/**
	 * 
	 * Permite obtener un vehiculo estacionado de acuerdo a la placa
	 * 
	 * @param placa
	 * @return vehiculo parqueado por tipo y placa
	 */
	Optional<FacturaVehiculo> findByPlacaAndFechaSalidaIsNull(String placa);
}