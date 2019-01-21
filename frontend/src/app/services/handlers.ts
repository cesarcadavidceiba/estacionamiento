import { ErrorHandler, Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { HttpErrorResponse } from '@angular/common/http';

@Injectable()
export class Handlers implements ErrorHandler {
  constructor(private toastr: ToastrService) {

  }

  handleError(error: Error): void {
    console.log(error);
    if (error instanceof HttpErrorResponse) {
      if (error.status === 409) {
        this.toastr.info(error.error);
      } else {
        this.toastr.info('El servidor no se encuentra disponible en estos momentos, por favor intente mas tarde');
      }

    }
  }
}
