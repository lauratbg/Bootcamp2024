import { Component } from '@angular/core';

@Component({
  selector: 'app-calculator',
  templateUrl: './calculator.component.html',
  styleUrls: ['./calculator.component.css'],
})
export class CalculatorComponent {
  display: string = ''; // Variable para almacenar y mostrar el resultado en la pantalla
  operator: string = ''; // Variable para almacenar el último operador utilizado
  ultimo: string = '';

  digitos(digito: string) {
    this.display += digito;
  }

  suma() {
    this.operator = '+';
    this.ultimo = this.display;
    this.display += '+';
  }

  resta() {
    this.operator = '-';
    this.ultimo = this.display;
    this.display += '-';
  }
  borrar() {
    this.display = '';
  }
  ce() {
    this.display = '';
  }
  cambioSigno() {
    this.display = (parseFloat(this.display) * -1).toString();
  }
  raiz() {
    this.display = Math.sqrt(parseFloat(this.display)).toString();
  }
  porcentaje() {
    // Implementa la lógica para el porcentaje
  }
  entreX() {
    this.display = (1 / parseFloat(this.display)).toString();
  }
  c() {
    this.display = '';
  }
  decimal() {
    this.display += '.';
  }
  cuadrado() {
    this.display = (
      parseFloat(this.display) * parseFloat(this.display)
    ).toString();
    console.log(parseFloat(this.display).toString());
  }
  multiplicacion() {
    this.operator = '*';
    this.ultimo = this.display;
    this.display += '*';
  }

  division() {
    this.operator = '/';
    this.ultimo = this.display;
    this.display += '/';
  }

  igual() {
    const hasOperator = this.display
      .split('')
      .some(
        (char) => char === '*' || char === '+' || char === '/' || char === '-'
      );

    if (hasOperator) {
      this.display = eval(this.display).toString();
    } else {
      this.display = eval(
        this.display + this.operator + this.ultimo
      ).toString();
    }
  }
}
