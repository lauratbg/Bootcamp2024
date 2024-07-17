import { Routes } from '@angular/router';
import { HomeComponent } from './main';
import { PageNotFoundComponent } from './main/page-not-found/page-not-found.component';

export const routes: Routes = [
    { path: '', pathMatch: 'full', component: HomeComponent },
    { path: 'inicio', component: HomeComponent },
    { path: '404.html', component: PageNotFoundComponent },
    { path: '**', component: PageNotFoundComponent },
];
