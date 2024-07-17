import { NgModule } from '@angular/core';
import { CATEGORIAS_COMPONENTES, CategoriasAddComponent, CategoriasEditComponent, CategoriasListComponent, CategoriasViewComponent } from './componente.component';
import { RouterModule, Routes } from '@angular/router';
// import { InRoleCanActivate } from '../security';

export const routes: Routes = [
  { path: '', component: CategoriasListComponent },
  { path: 'add', component: CategoriasAddComponent, /*canActivate: [ InRoleCanActivate('Empleados')]*/ },
  { path: ':id/edit', component: CategoriasEditComponent, /*canActivate: [ InRoleCanActivate('Empleados')]*/ },
  { path: ':id', component: CategoriasViewComponent, /*canActivate: [ InRoleCanActivate('Empleados')]*/},
  { path: ':id/:kk', component: CategoriasViewComponent, /*canActivate: [ InRoleCanActivate('Empleados')]*/ },
]
@NgModule({
  declarations: [],
  imports: [ CATEGORIAS_COMPONENTES, RouterModule.forChild(routes) ],
  exports: [ RouterModule ]
})
export class CategoriasModule { }
