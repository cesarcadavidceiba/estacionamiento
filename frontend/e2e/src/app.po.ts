import { browser, by, element } from 'protractor';
import { Component } from '@angular/core';

export class AppPage {
  inicio() {
    browser.manage().window().maximize();
    return browser.get('/');
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

  darSalidaVehiculo() {
    return element.all(by.id('btnDarSalidaCarro')).first().click();
  }

  campoPlaca(placa: string) {
    const txtPlaca = element(by.id('txtPlaca'));
    txtPlaca.click();
    txtPlaca.clear();
    txtPlaca.sendKeys(placa);
    return txtPlaca;
  }

  campoMarca(marca: string) {
    const txtMarca = element(by.id('txtMarca'));
    txtMarca.click();
    txtMarca.clear();
    txtMarca.sendKeys(marca);
    return txtMarca;
  }

  campoModelo(modelo: string) {
    const txtModelo = element(by.id('txtModelo'));
    txtModelo.click();
    txtModelo.clear();
    txtModelo.sendKeys(modelo);
    return txtModelo;
  }

  campoCilindraje(cilindraje: number) {
    const txtCilindraje = element(by.id('txtCilindraje'));
    txtCilindraje.click();
    txtCilindraje.clear();
    txtCilindraje.sendKeys(cilindraje);
    return txtCilindraje;
  }

  ingresarVehiculo() {
    return element(by.id('btnEnviar')).click();
  }

  public sleep(): void {
    browser.driver.sleep(1500);
  }

  public obtenerPosicion() {
    return element(by.id('txtPosicion')).getAttribute('value');
  }

  public obtenerPlacaDeTabla(posicion: string) {
    return element(by.css('#tbCarros .text-center:nth-child(' + posicion + ') > td:nth-child(2)')).getText();
  }

  obtenerValorPagar() {
    return element(by.id('lblValorPagar')).getText();
  }

}