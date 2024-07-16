import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegisterUserComponent } from './register-user/register-user.component';
import { LoginComponent, LoginFormComponent } from './login/login.component';



@NgModule({
  declarations: [],
  exports: [ LoginComponent, LoginFormComponent, ],
  imports: [
    CommonModule, LoginComponent, LoginFormComponent,
  ]
})
export class SecurityModule { }
