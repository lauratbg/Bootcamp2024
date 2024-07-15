import { Component, forwardRef, OnDestroy, OnInit } from '@angular/core';
import { BibliotecaViewModelService } from './servicios.service';
import { ErrorMessagePipe, TypeValidator } from '@my/core';
import { FormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-bibliotecas',
  templateUrl: './tmpl-anfitrion.component.html',
  styleUrls: ['./componente.component.css'],
  standalone: true,
  imports: [
    forwardRef(() => BibliotecaAddComponent),
    forwardRef(() => BibliotecaEditComponent),
    forwardRef(() => BibliotecaViewComponent),
    forwardRef(() => BibliotecaListComponent),
  ],
})
export class BibliotecaComponent implements OnInit, OnDestroy {
  constructor(protected vm: BibliotecaViewModelService) {}
  public get VM(): BibliotecaViewModelService {
    return this.vm;
  }
  ngOnInit(): void {
    this.vm.list();
  }
  ngOnDestroy(): void {
    this.vm.clear();
  }
}

@Component({
  selector: 'app-biblioteca-list',
  templateUrl: './tmpl-list.component.html',
  styleUrls: ['./componente.component.css'],
  standalone: true,
})
export class BibliotecaListComponent implements OnInit, OnDestroy {
  constructor(protected vm: BibliotecaViewModelService) {}
  public get VM(): BibliotecaViewModelService {
    return this.vm;
  }
  ngOnInit(): void {}
  ngOnDestroy(): void {
    this.vm.clear();
  }
}

@Component({
  selector: 'app-biblioteca-add',
  templateUrl: './tmpl-form.component.html',
  styleUrls: ['./componente.component.css'],
  standalone: true,
  imports: [FormsModule, TypeValidator, ErrorMessagePipe],
})
export class BibliotecaAddComponent implements OnInit {
  constructor(protected vm: BibliotecaViewModelService) {}
  public get VM(): BibliotecaViewModelService {
    return this.vm;
  }
  ngOnInit(): void {}
}
@Component({
  selector: 'app-biblioteca-edit',
  templateUrl: './tmpl-form.component.html',
  styleUrls: ['./componente.component.css'],
  standalone: true,
  imports: [FormsModule, TypeValidator, ErrorMessagePipe],
})
export class BibliotecaEditComponent implements OnInit, OnDestroy {
  constructor(protected vm: BibliotecaViewModelService) {}
  public get VM(): BibliotecaViewModelService {
    return this.vm;
  }
  ngOnInit(): void {}
  ngOnDestroy(): void {}
}
@Component({
  selector: 'app-biblioteca-view',
  templateUrl: './tmpl-view.component.html',
  styleUrls: ['./componente.component.css'],
  standalone: true,
  imports: [DatePipe],
})
export class BibliotecaViewComponent implements OnInit, OnDestroy {
  constructor(protected vm:BibliotecaViewModelService) {}
  public get VM(): BibliotecaViewModelService {
    return this.vm;
  }
  ngOnInit(): void {}
  ngOnDestroy(): void {}
}

export const BIBLIOTECA_COMPONENTES = [
  BibliotecaComponent,
  BibliotecaListComponent,
  BibliotecaAddComponent,
  BibliotecaEditComponent,
  BibliotecaViewComponent,
];
