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

  // está en español
  es = true;

  digitos(digito: string) {
    if(this.display=='0')
      this.display=''
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
    this.display = this.display.slice(0, -1);
  }
  ce() {
    this.display = '0';
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
    this.display = '0';
  }
  decimal() {
    if (this.es) this.display += ',';
    else this.display += '.';
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
    // Reemplaza comas con puntos para las operaciones
    let displayForEval = this.display;

    if (this.es) displayForEval = this.display.replace(/,/g, '.');

    const hasOperator = displayForEval
      .split('')
      .some(
        (char) => char === '*' || char === '+' || char === '/' || char === '-'
      );

    let resultado: number;
    if (hasOperator) {
      resultado = eval(displayForEval);
    } else {
      resultado = eval(displayForEval + this.operator + this.ultimo);
    }

    // Formatea el resultado según el idioma
    this.display = this.formatResultado(resultado);
  }

  // Método para formatear el resultado según el idioma
  private formatResultado(resultado: number): string {
    if (this.es) {
      return resultado.toFixed(2).toString().replace('.', ',');
    } else {
      return resultado.toFixed(2).toString();
    }
  }

  cambiaIdioma() {
    this.es = !this.es;
  }
}
