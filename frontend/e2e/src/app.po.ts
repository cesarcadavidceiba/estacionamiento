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

  darSalidaCarro() {
    return element.all(by.id('btnDarSalidaCarro')).first().click();
  }

  darSalidaMoto() {
    return element.all(by.id('btnDarSalidaMoto')).first().click();
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

  campoCilindraje(cilindraje: string) {
    const txtCilindraje = element(by.id('txtCilindraje'));
    txtCilindraje.click();
    txtCilindraje.clear();
    txtCilindraje.sendKeys(cilindraje);
    return txtCilindraje;
  }

  ingresarVehiculo() {
    return element(by.id('btnEnviar')).click();
  }

  botonEnviarActivo() {
    return element(by.id('btnEnviar')).isEnabled();
  }

  public sleep(): void {
    browser.driver.sleep(1500);
  }

  public obtenerPosicion() {
    return element(by.id('txtPosicion')).getAttribute('value');
  }

  public obtenerPlacaCarroDeTabla(posicion: string) {
    return element(by.css('#tbCarros .text-center:nth-child(' + posicion + ') > td:nth-child(2)')).getText();
  }

  public obtenerMarcaCarroDeTabla(posicion: string) {
    return element(by.css('#tbCarros .text-center:nth-child(' + posicion + ') > td:nth-child(3)')).getText();
  }

  public obtenerModeloCarroDeTabla(posicion: string) {
    return element(by.css('#tbCarros .text-center:nth-child(' + posicion + ') > td:nth-child(4)')).getText();
  }

  public obtenerPlacaMotoDeTabla(posicion: string) {
    return element(by.css('#tbMotos .text-center:nth-child(' + posicion + ') > td:nth-child(2)')).getText();
  }

  public obtenerMarcaMotoDeTabla(posicion: string) {
    return element(by.css('#tbMotos .text-center:nth-child(' + posicion + ') > td:nth-child(3)')).getText();
  }

  public obtenerModeloMotoDeTabla(posicion: string) {
    return element(by.css('#tbMotos .text-center:nth-child(' + posicion + ') > td:nth-child(4)')).getText();
  }

  public obtenerCilindrajeMotoDeTabla(posicion: string) {
    return element(by.css('#tbMotos .text-center:nth-child(' + posicion + ') > td:nth-child(5)')).getText();
  }

  obtenerValorPagar() {
    return element(by.id('lblValorPagar')).getText();
  }

  esperarAdvertenciaSalga() {
    browser.wait(() => {
      return element(by.css('.toast-message')).isDisplayed;
    }, 2000);
  }

  advertenciaSeMuestra() {
    return element(by.css('.toast-message')).isPresent();
  }

}
