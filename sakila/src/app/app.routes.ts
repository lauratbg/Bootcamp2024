import { Routes } from '@angular/router';
import { HomeComponent } from './main';
import { PageNotFoundComponent } from './main/page-not-found/page-not-found.component';

export const routes: Routes = [
    { path: '', pathMatch: 'full', component: HomeComponent },
    { path: 'inicio', component: HomeComponent },
    { path: 'actores', loadChildren: () => import("./actores/modulo.module").then(mod => mod.ActoresModule)},
    { path: 'categorias', loadChildren: () => import("./categorias/modulo.module").then(mod => mod.CategoriasModule)},
    { path: 'peliculas', loadChildren: () => import("./peliculas/modulo.module").then(mod => mod.PeliculasModule)},
   
    { path: '404.html', component: PageNotFoundComponent },
    { path: '**', component: PageNotFoundComponent },
];
