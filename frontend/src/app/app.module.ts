import { BrowserModule } from '@angular/platform-browser';
import { NgModule, ErrorHandler } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { VehiculoService } from './services/vehiculo.service';
import { Handlers } from './services/handlers';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    CommonModule,
    HttpClientModule,
    FormsModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot({
      preventDuplicates: true,
      closeButton: true,
      tapToDismiss: true,
      progressBar: true,
      positionClass: 'toast-bottom-right',
    })
  ],
  providers: [
    VehiculoService,
    {provide: ErrorHandler, useClass: Handlers}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
