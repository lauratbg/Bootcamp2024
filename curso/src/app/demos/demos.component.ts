import { Component, OnDestroy, OnInit } from '@angular/core';
import { NotificationService, NotificationType } from '../common-services';
import { Unsubscribable } from 'rxjs';

@Component({
  selector: 'app-demos',
  standalone: true,
  imports: [],
  templateUrl: './demos.component.html',
  styleUrl: './demos.component.css',
})
export class DemosComponent implements OnInit, OnDestroy {
  // almacena el suscriptor para poder cancelar la suscripción al destruir el componente
  private suscriptor: Unsubscribable | undefined;
  // para tener acceso al sistema de notificaciones
  constructor(public vm: NotificationService) {}

  // al iniicializar el componente se crea la suscripción y se indica el tratamiento de las nuevas notificaciones
  ngOnInit(): void {
    this.suscriptor = this.vm.Notificacion.subscribe((n) => {
      if (n.Type !== NotificationType.error) {
        return;
      }
      window.alert(`Suscripcion: ${n.Message}`);
      this.vm.remove(this.vm.Listado.length - 1);
    });
  }

  // necesario para que no proteste
  // al destruir el componente se debe cancelar la suscripción para evitar fugas de memoria y de proceso
  ngOnDestroy(): void {
    if (this.suscriptor) {
      this.suscriptor.unsubscribe();
    }
  }
}
