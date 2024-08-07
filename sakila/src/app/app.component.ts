import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AjaxWaitComponent, NotificationComponent, NotificationModalComponent } from './main';
import { HeaderComponent } from './main/header/header.component';
import { NavigationService } from './common-services';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NotificationComponent, NotificationModalComponent, AjaxWaitComponent, HeaderComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  constructor(nav:NavigationService){}
  
}