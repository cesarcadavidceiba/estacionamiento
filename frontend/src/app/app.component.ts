import { Component, OnInit } from '@angular/core';

import { VehiculoService } from './services/vehiculo.service';
import { FacturaVehiculo } from './model/factura-vehiculo';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  public facturaVehiculos: FacturaVehiculo[];

  constructor(private vehiculoService: VehiculoService) {
    this.facturaVehiculos = [];
  }

  ngOnInit() {
    this.vehiculoService.consultarVehiculos()
    .subscribe(vehiculos => this.facturaVehiculos = vehiculos);
  }

}
