import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FacturaVehiculo } from '../model/factura-vehiculo';

@Injectable()
export class VehiculoService {

  constructor(private httpClient: HttpClient) {
  }

  public consultarVehiculos(): Observable<FacturaVehiculo[]> {
    return this.httpClient.get<FacturaVehiculo[]>('/api/vehiculos');
  }

  public estacionarVehiculo(facturaVehiculo: FacturaVehiculo): Observable<number> {
    return this.httpClient.post<number>('/api/vehiculos', facturaVehiculo);
  }

  public darSalidaVehiculo(placa: String): Observable<number> {
    return this.httpClient.put<number>('/api/vehiculos/' + placa, {});
  }
}
