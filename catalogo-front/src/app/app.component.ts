import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from "./main/header/header.component";
import { AjaxWaitComponent } from "./main/ajax-wait";
import { NotificationComponent } from "./main/notification/notification.component";
import { NotificationModalComponent } from "./main/notification-modal/notification-modal.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HeaderComponent, AjaxWaitComponent, NotificationComponent, NotificationModalComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'catalogo-front';
}
