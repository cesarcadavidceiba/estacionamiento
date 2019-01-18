import { Vehiculo } from './vehiculo';

export class FacturaVehiculo {

  public id: number;
  public vehiculo: Vehiculo;
  public posicion: number;
  public fechaEntrada: string;

  constructor() {
    this.vehiculo = new Vehiculo();
  }

}
