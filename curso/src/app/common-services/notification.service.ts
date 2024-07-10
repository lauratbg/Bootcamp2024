import { Injectable, OnDestroy } from '@angular/core';
import { LoggerService } from '@my/core';
import { Subject } from 'rxjs';
export enum NotificationType {
  error = 'error',
  warn = 'warn',
  info = 'info',
  log = 'log',
}

// modelo de notificación
export class Notification {
  constructor(
    private id: number,
    private message: string,
    private type: NotificationType
  ) {}
  public get Id() {
    return this.id;
  }
  public get Message() {
    return this.message;
  }
  public get Type() {
    return this.type;
  }
}

@Injectable({
  providedIn: 'root',
})
@Injectable({ providedIn: 'root' })
export class NotificationService implements OnDestroy {
  public readonly NotificationType = NotificationType;
  private listado: Notification[] = [];
  private notificacion$ = new Subject<Notification>();

  constructor(private out: LoggerService) {}

  public get Listado(): Notification[] {
    return Object.assign([], this.listado);
  }
  public get HayNotificaciones() {
    return this.listado.length > 0;
  }

  public get Notificacion() {
    return this.notificacion$;
  }

  // añadir nuevas notificaciones y emitirlas
  public add(msg: string, type: NotificationType = NotificationType.error) {
    if (!msg || msg === '') {
      this.out.error('Falta el mensaje de notificación.');
      return;
    }
    const id = this.HayNotificaciones
      ? this.listado[this.listado.length - 1].Id + 1
      : 1;
    const n = new Notification(id, msg, type);
    this.listado.push(n);
    this.notificacion$.next(n);
    // Redundancia: Los errores también se muestran en consola
    if (type === NotificationType.error) {
      this.out.error(`NOTIFICATION: ${msg}`);
    }
  }

  // eliminar notificación según su posición
  public remove(index: number) {
    if (index < 0 || index >= this.listado.length) {
      this.out.error('Index out of range.');
      return;
    }
    this.listado.splice(index, 1);
  }

  // borrar todas las notificaciones
  public clear() {
    if (this.HayNotificaciones) this.listado.splice(0);
  }

  // hasta que no se pone esto, protesta
  // notifica en el destructor que el observable se ha completado
  ngOnDestroy(): void {
    this.notificacion$.complete();
  }
}
