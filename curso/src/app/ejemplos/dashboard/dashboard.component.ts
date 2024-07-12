import { Component } from '@angular/core';
import { DemosComponent } from 'src/app/demos/demos.component';
import { HomeComponent } from 'src/app/main';
import GraficoSvgComponent from 'src/lib/my-core/components/grafico-svg/grafico-svg.component';
import { NotificationComponent } from "../../main/notification/notification.component";
import { CommonModule } from '@angular/common';
import { CalculatorComponent } from 'src/app/common-components';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [NotificationComponent, CommonModule,],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {
    menu = [
      {texto: 'calculator', icono: '', componente: CalculatorComponent },
      {texto: 'inicio', icono: '', componente: HomeComponent },
      {texto: 'demos', icono: '', componente: DemosComponent },
      {texto: 'grafico', icono: '', componente: GraficoSvgComponent},
    ]
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    actual: any = this.menu[0].componente // obligatorio meterle el any

    seleccionar(indice: number){
      this.actual = this.menu[indice].componente
    }
}

