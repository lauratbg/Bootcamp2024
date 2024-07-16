import { Routes, UrlSegment } from '@angular/router';
import { HomeComponent } from './main';

import { PageNotFoundComponent } from './main/page-not-found/page-not-found.component';

export function graficoFiles(url: UrlSegment[]) {
  return url.length === 1 && url[0].path.endsWith('.svg') ? ({consumed: url}) : null;
}
export const routes: Routes = [
  { path: '', pathMatch: 'full', component: HomeComponent },
  { path: 'inicio', component: HomeComponent },
//   { path: 'peliculas', component:  },
//   { path: 'actores', component: ContactosListComponent },

  { path: '404.html', component: PageNotFoundComponent },
  { path: '**', component: PageNotFoundComponent },
];