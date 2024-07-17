import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PIPES_CADENAS } from './pipes/cadenas.pipe';
import { SizerComponent } from './components/sizer.component';
import { FormsModule } from '@angular/forms'; // Importa FormsModule




@NgModule({
  declarations: [],
  exports: [ PIPES_CADENAS, SizerComponent, ],
  imports: [
    CommonModule, PIPES_CADENAS, SizerComponent,FormsModule,
  ]
})
export class MyCoreModule { }