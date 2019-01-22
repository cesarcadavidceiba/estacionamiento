import { browser, by, element } from 'protractor';

export class AppPage {
  navigateTo() {
    return browser.get('/');
  }

  getTitleText() {
    return element(by.css('app-root h1')).getText();
  }

  ingresarTabCarros() {
    return element(by.id('carros-tab')).click();
  }

  ingresarTabMotos() {
    return element(by.id('motos-tab')).click();
  }

  ingresarTabTrm() {
    return element(by.id('trm-tab')).click();
  }

  abrirModalIngresarMoto() {
    return element.all(by.id('btnIngresarMoto')).first().click();
  }

  abrirModalIngresarCarro() {
    return element.all(by.id('btnIngresarCarro')).first().click();
  }

  campoPlaca() {
    const placa = element(by.id('txtPlaca'));
    placa.click();
    placa.clear();
    return placa;
  }

  campoMarca() {
    const marca = element(by.id('txtMarca'));
    marca.click();
    marca.clear();
    return marca;
  }

  campoModelo() {
    const modelo = element(by.id('txtModelo'));
    modelo.click();
    modelo.clear();
    return modelo;
  }

  campoCilindraje() {
    const cilindraje = element(by.id('txtCilindraje'));
    cilindraje.click();
    cilindraje.clear();
    return cilindraje;
  }

  enviarIngresarVehiculo() {
    return element(by.id('btnEnviar')).click();
  }

}
