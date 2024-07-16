import { Routes } from '@angular/router';
import { HomeComponent, PageNotFoundComponent } from './main';
import { PeliculasComponent } from './peliculas/peliculas.component';

export const routes: Routes = [
  { path: '', pathMatch: 'full', component: HomeComponent },
  { path: 'inicio', component: HomeComponent },
  { path: 'peliculas', component: PeliculasComponent },
  { path: '**', component: PageNotFoundComponent },
];
