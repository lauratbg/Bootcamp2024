import {
  NgClass,
  NgFor,
  NgIf,
  NgSwitch,
  NgSwitchCase,
  NgSwitchDefault,
} from '@angular/common';
import { Component } from '@angular/core';
import { NotificationService } from 'src/app/common-services';

@Component({
  selector: 'app-notification-modal',
  standalone: true,
  imports: [NgIf, NgClass, NgFor, NgSwitch, NgSwitchCase, NgSwitchDefault],
  templateUrl: './notification-modal.component.html',
  styleUrl: './notification-modal.component.css',
})
export class NotificationModalComponent {
  // actuará como ViewModel de la vista
  constructor(private vm: NotificationService) {}

  // se expone como propiedad de solo lectura para permitir la inculación desde la plantilla
  public get VM() {
    return this.vm;
  }
}
