import { AppPage } from './app.po';

describe('Estacionamiento', () => {

  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });

  describe('Cuando un carro este estacionado y salga del estacionamiento ', () => {

    it('Deberia mostar el valor a pagar', () => {
      page.inicio();
      page.ingresarTabCarros();
      page.darSalidaVehiculo();
      page.sleep();

      const valorPagar = page.obtenerValorPagar();
      valorPagar.then(value => {
        console.log('Valor Pagar: ' + value.replace(',', ''));
        page.sleep();
      });
    })

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
        expect(page.obtenerPlacaDeTabla(value)).toEqual('TGR 586');
      });
    });
  });

});