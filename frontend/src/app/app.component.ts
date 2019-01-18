import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';

import { VehiculoService } from './services/vehiculo.service';
import { FacturaVehiculo } from './model/factura-vehiculo';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  public carrosEstacionados: Map<number, FacturaVehiculo>;
  public motosEstacionadas: Map<number, FacturaVehiculo>;

  public vehiculoSeleccionado: FacturaVehiculo;

  public valorPagar: number;

  public pintarTablaCarros: boolean;
  public cambiarEstiloTabCarro: String;
  public cambiarEstiloTabMoto: String;

  @ViewChild('closeBtn') private closeBtn: ElementRef;

  constructor(private vehiculoService: VehiculoService) {
    this.carrosEstacionados = new Map<number, FacturaVehiculo>();
    this.motosEstacionadas = new Map<number, FacturaVehiculo>();
    this.vehiculoSeleccionado = new FacturaVehiculo();
    this.pintarTablaCarros = true;
    this.cambiarEstiloTabCarro = 'active';
    this.cambiarEstiloTabMoto = '';
    this.crearParqueadero();
  }

  private crearParqueadero(): void {
    for (let i = 1; i <= 20; i++) {
      this.carrosEstacionados.set(i, new FacturaVehiculo());
    }

    for (let i = 1; i <= 10; i++) {
      this.motosEstacionadas.set(i, new FacturaVehiculo());
    }
  }

  ngOnInit() {
    this.vehiculoService.vehiculosEstacionados()
      .subscribe(vehiculos => {
        for (const vehiculoEstacionado of vehiculos) {
          if (vehiculoEstacionado.vehiculo.tipo === 0) {
            this.carrosEstacionados.set(vehiculoEstacionado.posicion, vehiculoEstacionado);
          } else {
            this.motosEstacionadas.set(vehiculoEstacionado.posicion, vehiculoEstacionado);
          }
        }
      });
  }

  public darSalidaVehiculos(posicion: number, tipoVehiculo: number): void {
    const vehiculoEstacionado = tipoVehiculo === 0 ? this.carrosEstacionados.get(posicion) : this.motosEstacionadas.get(posicion);
    this.vehiculoService.darSalidaVehiculo(vehiculoEstacionado.vehiculo.placa).subscribe(valorPagar => {
      this.valorPagar = valorPagar;

      if (tipoVehiculo === 0) {
        this.carrosEstacionados.set(posicion, new FacturaVehiculo());
      } else {
        this.motosEstacionadas.set(posicion, new FacturaVehiculo());
      }


    });
  }

  public ingresarVehiculo(posicion: number, tipo: number): void {
    this.vehiculoSeleccionado.posicion = posicion;
    this.vehiculoSeleccionado.vehiculo.tipo = tipo;
  }

  public estacionarVehiculo(): void {
    this.closeBtn.nativeElement.click();
    this.vehiculoService.estacionarVehiculo(this.vehiculoSeleccionado).subscribe(facturaVehiculo => {
      this.vehiculoSeleccionado = facturaVehiculo;
      if (this.vehiculoSeleccionado.vehiculo.tipo === 0) {
        this.carrosEstacionados.set(this.vehiculoSeleccionado.posicion, this.vehiculoSeleccionado);
      } else {
        this.motosEstacionadas.set(this.vehiculoSeleccionado.posicion, this.vehiculoSeleccionado);
      }

      this.vehiculoSeleccionado = new FacturaVehiculo();
    });
  }

  public navegarTabVehiculos(tipoVehiculo: number): void {
    console.log(tipoVehiculo);
    this.pintarTablaCarros = tipoVehiculo === 0 ? true : false;
    this.cambiarEstiloTabCarro = this.pintarTablaCarros ? 'active' : '';
    this.cambiarEstiloTabMoto = !this.pintarTablaCarros ? 'active' : '';
  }

}
