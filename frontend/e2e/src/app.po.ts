import { browser, by, element } from 'protractor';

export class AppPage {
  inicio() {
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

  enviarIngresarVehiculo() {
    const btnEnviar = element(by.id('btnEnviar'));
    btnEnviar.click();
    return;
  }

  tdConPlaca() {
    const tablaCarros = element(by.id('tbCarros'));
    const rows = tablaCarros.all(by.tagName('tr'));

    const cells = rows.all(by.tagName('td'));
    return cells;
  }

}
