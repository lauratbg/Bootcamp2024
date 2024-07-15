import { Component } from '@angular/core';
import { DemosComponent } from 'src/app/demos/demos.component';
import { AjaxWaitComponent, HomeComponent } from 'src/app/main';
import GraficoSvgComponent from 'src/lib/my-core/components/grafico-svg/grafico-svg.component';
import { NotificationComponent } from "../../main/notification/notification.component";
import { CommonModule } from '@angular/common';
import { CalculatorComponent } from 'src/lib/my-core/components/calculator/calculator.component'
import { FormularioComponent } from '../formulario/formulario.component';
import { ContactosComponent } from 'src/app/contactos';
import { BibliotecaComponent } from 'src/app/biblioteca';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [NotificationComponent, CommonModule, AjaxWaitComponent, ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {
    menu = [
      {texto: 'Biblioteca', icono: '', componente: BibliotecaComponent },
      {texto: 'Contactos', icono: '', componente: ContactosComponent },
      {texto: 'Formulario', icono: '', componente: FormularioComponent },
      {texto: 'Calculadora', icono: '', componente: CalculatorComponent },
      {texto: 'Inicio', icono: '', componente: HomeComponent },
      {texto: 'Demos', icono: '', componente: DemosComponent },
      {texto: 'Gráfico', icono: '', componente: GraficoSvgComponent},
    ]
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    actual: any = this.menu[0].componente // obligatorio meterle el any

    seleccionar(indice: number){
      this.actual = this.menu[indice].componente
    }
}

