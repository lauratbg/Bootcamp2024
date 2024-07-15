import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Injectable } from '@angular/core';
import {
  ErrorMessagePipe,
  NIFNIEValidator,
  TypeValidator,
  UppercaseValidator,
} from '@my/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { NotificationService, NotificationType } from 'src/app/common-services';
export abstract class RESTDAOService<T, K> {
  protected baseUrl = environment.apiURL;
  protected http = inject(HttpClient);
  constructor(entidad: string, protected option = {}) {
    this.baseUrl += entidad;
  }
  query(extras = {}): Observable<Array<T>> {
    return this.http.get<Array<T>>(
      this.baseUrl,
      Object.assign({}, this.option, extras)
    );
  }
  get(id: K, extras = {}): Observable<T> {
    return this.http.get<T>(
      `${this.baseUrl}/${id}`,
      Object.assign({}, this.option, extras)
    );
  }
  add(item: T, extras = {}): Observable<T> {
    return this.http.post<T>(
      this.baseUrl,
      item,
      Object.assign({}, this.option, extras)
    );
  }
  change(id: K, item: T, extras = {}): Observable<T> {
    return this.http.put<T>(
      `${this.baseUrl}/${id}`,
      item,
      Object.assign({}, this.option, extras)
    );
  }
  remove(id: K, extras = {}): Observable<T> {
    return this.http.delete<T>(
      `${this.baseUrl}/${id}`,
      Object.assign({}, this.option, extras)
    );
  }
}

@Injectable({ providedIn: 'root' })
export class PersonasDAOService extends RESTDAOService<any, any> {
  constructor() {
    super('personas');
  }
}

@Component({
  selector: 'app-formulario',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ErrorMessagePipe,
    NIFNIEValidator,
    UppercaseValidator,
    TypeValidator,
  ],
  templateUrl: './formulario.component.html',
  styleUrl: './formulario.component.css',
})
export class FormularioComponent {
  modo: 'add' | 'edit' = 'add';
  elemento: any = {};

  constructor(
    private dao: PersonasDAOService,
    private notify: NotificationService
  ) {}

  add() {
    this.elemento = {}; // si no le ponemos any, da error, le estoy metiendo un objeto vacío
    this.modo = 'add';
  }

  // pasar la clave del elemento que se quiere editar
  edit(key: number) {
    this.dao.get(key).subscribe({
      next: (data) => {
        this.elemento = data;
        this.modo = 'edit';
      },
      error: (err) => this.notify.add(err.message),
    });
    // this.elemento = {
    //   id: key,
    //   nombre: 'Pepito',
    //   apellidos: 'Grillo',
    //   correo: 'pepito@grillo.com',
    //   edad: 99,
    //   fecha: '2024-07-15',
    //   conflictivo: true,
    // }; // si no le ponemos any, da error
    // this.modo = 'edit';
  }

  cancel() {}

  send() {
    switch (this.modo) {
      case 'add':
        this.dao.add(this.elemento).subscribe({
          next: () => {
            this.notify.add('Persona creada', NotificationType.info);
            this.cancel();
          },
        });
        // window.alert(`POST: ${JSON.stringify(this.elemento)}`);
        break;
      case 'edit':
        this.dao.change(this.elemento.id, this.elemento).subscribe({
          next: () => {
            this.notify.add('Persona modificada', NotificationType.info);
            this.cancel();
          },
        });
        // window.alert(`PUT: ${JSON.stringify(this.elemento)}`);
        // this.cancel();
        break;
    }
  }
}
