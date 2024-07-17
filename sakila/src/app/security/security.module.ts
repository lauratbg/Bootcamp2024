import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegisterUserComponent } from './register-user/register-user.component';
import { LoginComponent, LoginFormComponent } from './login/login.component';



@NgModule({
  declarations: [],
  exports: [ LoginComponent, LoginFormComponent, RegisterUserComponent, ],
  imports: [
    CommonModule, LoginComponent, LoginFormComponent, RegisterUserComponent,
  ]
})
export class SecurityModule { }
