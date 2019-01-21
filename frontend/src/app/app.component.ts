import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { VehiculoService } from './services/vehiculo.service';
import { FacturaVehiculo } from './model/factura-vehiculo';
import { Tcrm } from './model/tcrm';

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

  public tcrm: Tcrm;

  @ViewChild('closeBtn') private closeBtn: ElementRef;

  constructor(private vehiculoService: VehiculoService, private toastr: ToastrService) {
    this.carrosEstacionados = new Map<number, FacturaVehiculo>();
    this.motosEstacionadas = new Map<number, FacturaVehiculo>();
    this.vehiculoSeleccionado = new FacturaVehiculo();
    this.tcrm = new Tcrm();
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

    this.vehiculoService.consultarTrm().subscribe(tcrm => {
      this.tcrm = tcrm;
      console.log(this.tcrm);
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
    this.vehiculoSeleccionado = new FacturaVehiculo();
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

  public consultarTrm(): void {

  }

}
