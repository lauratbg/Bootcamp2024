import { Component, OnDestroy, OnInit } from '@angular/core';
import { NotificationService, NotificationType } from '../common-services';
import { Unsubscribable } from 'rxjs';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CapitalizePipe, ElipsisPipe, MyCoreModule } from '@my/core';

@Component({
  selector: 'app-demos',
  standalone: true,
  imports: [CommonModule, FormsModule, ElipsisPipe, CapitalizePipe],
  templateUrl: './demos.component.html',
  styleUrl: './demos.component.css',
})
export class DemosComponent implements OnInit, OnDestroy {
  private nombre: string = 'mundo';
  fecha = '2024-07-11';
  fontSize = 24;
  listado = [
    { id: 1, nombre: 'Madrid' },
    { id: 2, nombre: 'asturias' },
    { id: 3, nombre: 'ciudad Real' },
    { id: 4, nombre: 'BARCELONA' },
  ];
  idProvincia = 2;

  resultado?: string; // donde no hay resultado será undefined por eso le pongo ?
  visible = true;
  estetica = { importante: true, error: false, urgente: true };

  public calcula(a: number, b: number): number {
    return a + b;
  }

  public add(provincia: string) {
    const id = this.listado[this.listado.length - 1].id + 1;
    this.listado.push({ id, nombre: provincia });
    this.idProvincia = id;
  }

  // almacena el suscriptor para poder cancelar la suscripción al destruir el componente
  private suscriptor: Unsubscribable | undefined;
  // para tener acceso al sistema de notificaciones
  constructor(public vm: NotificationService) {}

  // propiedades
  public get Nombre(): string {
    return this.nombre;
  }

  public set Nombre(value: string) {
    if (this.nombre === value) return;
    this.nombre = value;
  }

  // comandos
  public saluda(): void {
    this.resultado = `Hola ${this.Nombre}`;
  }

  public despide(): void {
    this.resultado = `Adiós ${this.Nombre}`;
  }

  public di(algo: string): void {
    this.resultado = `Dice ${algo}`;
  }

  cambia() {
    this.visible = !this.visible;
    this.estetica.error = !this.estetica.error;
    this.estetica.importante = !this.estetica.importante;
  }

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
