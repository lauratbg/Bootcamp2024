import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ErrorMessagePipe } from '@my/core';

@Component({
  selector: 'app-formulario',
  standalone: true,
  imports: [CommonModule, FormsModule, ErrorMessagePipe ],
  templateUrl: './formulario.component.html',
  styleUrl: './formulario.component.css',
})
export class FormularioComponent {
  modo: 'add' | 'edit' = 'add';
  elemento: any = { }

  add() {
    this.elemento = {}; // si no le ponemos any, da error, le estoy metiendo un objeto vacío
    this.modo = 'add';
  }

  // pasar la clave del elemento que se quiere editar
  edit(key: number) {
    this.elemento = {
      id: key,
      nombre: 'Pepito',
      apellidos: 'Grillo',
      correo: 'pepito@grillo.com',
      edad: 99,
      fecha: '2024-07-15',
      conflictivo: true,
    }; // si no le ponemos any, da error
    this.modo = 'edit';
  }

  cancel() {}

  send() {
    switch (this.modo) {
      case 'add':
        window.alert(`POST: ${JSON.stringify(this.elemento)}`);
        this.cancel();
        break;
      case 'edit':
        window.alert(`PUT: ${JSON.stringify(this.elemento)}`);
        this.cancel();
        break;
    }
  }
}
