import { AppPage } from './app.po';
import { browser } from 'protractor';

function sleep() {
  browser.driver.sleep(1500);
}

describe('Cuando se estacione un carro', () => {
  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });

  it('Deberia cargarse en la tabla de carros estacionados', () => {
    page.inicio();
    page.ingresarTabCarros();
    page.abrirModalIngresarCarro();
    sleep();
    page.campoPlaca('TGR 586');
    page.campoMarca('MAZDA');
    page.campoModelo('2015');
    sleep();
    page.enviarIngresarVehiculo();
    sleep();
  });
});
