import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SecurityModule } from './security';
import {  MyCoreModule } from '@my/core';
// import { NotificationComponent } from './main/notification/notification.component';
import { CommonModule } from '@angular/common';
import { DemosComponent } from './demos/demos.component';
import { NotificationModalComponent } from './main';
import { HomeComponent } from "./main/home/home.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, NotificationModalComponent, RouterOutlet, SecurityModule, MyCoreModule, DemosComponent, HomeComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  // title: string = 'world';
  // constructor(log: LoggerService) {
  //   log.error('Es un error');
  //   log.warn('Es un warn');
  //   log.info('Es un info');
  //   log.log('Es un log');
  // }
}
