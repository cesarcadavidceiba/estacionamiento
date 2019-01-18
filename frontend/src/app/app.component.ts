import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';

import { VehiculoService } from './services/vehiculo.service';
import { FacturaVehiculo } from './model/factura-vehiculo';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  public facturaVehiculos: Map<number, FacturaVehiculo>;
  public facturaVehiucloSeleccionado: FacturaVehiculo;

  public valorPagar: number;

  @ViewChild('closeBtn') private closeBtn: ElementRef;

  constructor(private vehiculoService: VehiculoService) {
    this.facturaVehiculos = new Map<number, FacturaVehiculo>();
    this.facturaVehiucloSeleccionado = new FacturaVehiculo();
    this.crearParqueadero();
  }

  private crearParqueadero(): void {
    for (let i = 1; i <= 20; i++) {
      this.facturaVehiculos.set(i, new FacturaVehiculo());
    }
  }

  ngOnInit() {
    this.vehiculoService.consultarVehiculos()
      .subscribe(vehiculos => {
        for (const facturaVehiculo of vehiculos) {
          this.facturaVehiculos.set(facturaVehiculo.posicion, facturaVehiculo);
        }
      });
  }

  public salir(posicion: number): void {
    const facturaVehiculo = this.facturaVehiculos.get(posicion);
    this.vehiculoService.darSalidaVehiculo(facturaVehiculo.vehiculo.placa).subscribe(valorPagar => {
      console.log(valorPagar);
      this.valorPagar = valorPagar;
      this.facturaVehiculos.set(posicion, new FacturaVehiculo());
    });
  }

  public ingresar(posicion: number, tipo: number): void {
    this.facturaVehiucloSeleccionado.posicion = posicion;
    this.facturaVehiucloSeleccionado.vehiculo.tipo = tipo;
  }

  public registrarVehiculo(): void {
    this.closeBtn.nativeElement.click();
    this.vehiculoService.estacionarVehiculo(this.facturaVehiucloSeleccionado).subscribe(id => {
      this.facturaVehiucloSeleccionado.id = id;
      this.facturaVehiculos.set(this.facturaVehiucloSeleccionado.posicion, this.facturaVehiucloSeleccionado);
    });
  }
}
