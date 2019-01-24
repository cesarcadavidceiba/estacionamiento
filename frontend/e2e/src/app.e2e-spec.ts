import { AppPage } from './app.po';

describe('Estacionamiento', () => {

  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });

  describe('Cuando un carro este estacionado y salga del estacionamiento ', () => {

    it('Deberia mostrar el valor a pagar ', () => {
      page.inicio();
      page.ingresarTabCarros();
      page.darSalidaCarro();
      page.sleep();

      const valorPagar = page.obtenerValorPagar();
      valorPagar.then(value => {
        expect(parseInt(value.replace(',', ''), 0)).toBeGreaterThan(0);
      });
    });

  });

  describe('Cuando se estacione un carro', () => {

    it('Deberia mostrar en la tabla el carro estacionado', () => {
      page.inicio();
      page.ingresarTabCarros();
      page.abrirModalIngresarCarro();
      page.sleep();

      page.campoPlaca('TGR 586');
      page.campoMarca('Mazda');
      page.campoModelo('2013');

      const posicion = page.obtenerPosicion();
      page.ingresarVehiculo();

      posicion.then(value => {
        expect(page.obtenerPlacaCarroDeTabla(value)).toEqual('TGR 586');
        expect(page.obtenerMarcaCarroDeTabla(value)).toEqual('Mazda');
        expect(page.obtenerModeloCarroDeTabla(value)).toEqual('2013');
      });
    });
  });

  describe('Cuando se digite letras en el campo modelo del vehiculo', () => {

    it('Deberia mostrar deshabilitado el boton de enviar', () => {
      page.inicio();
      page.ingresarTabCarros();
      page.abrirModalIngresarCarro();
      page.sleep();

      page.campoPlaca('TGR 586');
      page.campoMarca('Mazda');
      page.campoModelo('201Q');
      page.sleep();

      const botonEnviarActivo = page.botonEnviarActivo();

      botonEnviarActivo.then(value => {
        expect(value).toBeFalsy();
      });

    });
  });

  describe('Cuando se digite una placa de un vehiculo que ya esta estacionado', () => {

    it('Deberia lanzar una advertencia', () => {
      page.inicio();
      page.ingresarTabCarros();
      page.abrirModalIngresarCarro();
      page.sleep();

      page.campoPlaca('TGR 586');
      page.campoMarca('Mazda');
      page.campoModelo('2010');
      page.sleep();

      page.ingresarVehiculo();

      page.esperarAdvertenciaSalga();
      const advertenciaSeMuestra = page.advertenciaSeMuestra();

      advertenciaSeMuestra.then(value => {
        expect(value).toBeTruthy();
      });
    });
  });

  describe('Cuando una moto salga del estacionamiento ', () => {

    it('Deberia mostrar el valor a pagar ', () => {
      page.inicio();
      page.ingresarTabMotos();
      page.sleep();
      page.darSalidaMoto();
      page.sleep();

      const valorPagar = page.obtenerValorPagar();
      valorPagar.then(value => {
        expect(parseInt(value.replace(',', ''), 0)).toBeGreaterThan(0);
      });
    });

  });

  describe('Cuando se estacione una moto', () => {

    it('Deberia mostrar en la tabla la moto estacionada', () => {
      page.inicio();
      page.ingresarTabMotos();
      page.sleep();
      page.abrirModalIngresarMoto();
      page.sleep();

      page.campoPlaca('NIV 17E');
      page.campoMarca('YAMAHA');
      page.campoModelo('2018');
      page.campoCilindraje('150');

      const posicion = page.obtenerPosicion();
      page.ingresarVehiculo();

      posicion.then(value => {
        expect(page.obtenerPlacaMotoDeTabla(value)).toEqual('NIV 17E');
        expect(page.obtenerMarcaMotoDeTabla(value)).toEqual('YAMAHA');
        expect(page.obtenerModeloMotoDeTabla(value)).toEqual('2018');
        expect(page.obtenerCilindrajeMotoDeTabla(value)).toEqual('150');
      });
    });
  });

  describe('Cuando el cilidraje de las motos tenga letras', () => {

    it('Deberia mantenerse el boton de enviar deshabilitado', () => {
      page.inicio();
      page.ingresarTabMotos();
      page.sleep();
      page.abrirModalIngresarMoto();
      page.sleep();

      page.campoPlaca('NIV 17E');
      page.campoMarca('YAMAHA');
      page.campoModelo('2018');
      page.campoCilindraje('150W');
      page.sleep();

      const botonEnviarActivo = page.botonEnviarActivo();

      botonEnviarActivo.then(value => {
        expect(value).toBeFalsy();
      });
    });
  });

});
