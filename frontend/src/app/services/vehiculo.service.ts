import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FacturaVehiculo } from '../model/factura-vehiculo';
import { Tcrm } from '../model/tcrm';

@Injectable()
export class VehiculoService {

  constructor(private httpClient: HttpClient) {
  }

  public vehiculosEstacionados(): Observable<FacturaVehiculo[]> {
    return this.httpClient.get<FacturaVehiculo[]>('/api/vehiculos');
  }

  public estacionarVehiculo(facturaVehiculo: FacturaVehiculo): Observable<FacturaVehiculo> {
    return this.httpClient.post<FacturaVehiculo>('/api/vehiculos', facturaVehiculo);
  }

  public darSalidaVehiculo(placa: String): Observable<number> {
    return this.httpClient.put<number>('/api/vehiculos/' + placa, {});
  }

  public consultarTrm(): Observable<Tcrm> {
    return this.httpClient.get<Tcrm>('/api/trm');
  }

}
