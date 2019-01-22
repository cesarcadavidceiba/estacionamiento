import { AppPage } from './app.po';
import { browser } from 'protractor';

function sleep() {
  browser.driver.sleep(1500);
}

describe('Navegar por los tabs', () => {

  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });

  it('Ingresar al tab de Motos', () => {
    page.navigateTo();
    page.ingresarTabMotos();
    sleep();
  });

  it('Ingresar al tab de TRM', () => {
    page.ingresarTabTrm();
    sleep();
  });

  it('Ingresar Tab Carros', () => {
    page.ingresarTabCarros();
    sleep();
  });

});

describe('Estacionar un carro', () => {
  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });

  it('Ingresar al tab de carros', () => {
    page.navigateTo();
    page.ingresarTabCarros();
    sleep();
  });

  it('Abrir Modal registrar carro', () => {
    page.abrirModalIngresarCarro();
    sleep();
  });

  it('Llenar campos formulario carro', () => {
    page.campoPlaca().sendKeys('TGR 586');
    page.campoMarca().sendKeys('MAZDA');
    page.campoModelo().sendKeys('2015');
    sleep();
  });

  it('Enviar estacionar carro', () => {
    page.enviarIngresarVehiculo();
    sleep();
  });

});

describe('Estacionar una moto', () => {
  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });

  it('Ingresar al tab de motos', () => {
    page.navigateTo();
    page.ingresarTabMotos();
  });

  it('Abrir Modal registro Moto', () => {
    page.ingresarTabMotos();
    sleep();
    page.abrirModalIngresarMoto();
    sleep();
  });

  it('Llenar campos formulario moto', () => {
    page.campoPlaca().sendKeys('NIV 17E');
    page.campoMarca().sendKeys('YAMAHA');
    page.campoModelo().sendKeys('2018');
    page.campoCilindraje().sendKeys(150);
    sleep();

  });

  it('Enviar estacionar moto', () => {
    page.enviarIngresarVehiculo();
    sleep();
  });
});
