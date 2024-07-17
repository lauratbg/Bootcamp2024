/* eslint-disable @typescript-eslint/no-unused-vars */
/* eslint-disable @typescript-eslint/no-empty-function */
/* eslint-disable @angular-eslint/no-empty-lifecycle-method */
import { Component, OnInit, OnDestroy, Input, OnChanges, SimpleChanges, forwardRef } from '@angular/core';
import { ActivatedRoute, Router, ParamMap, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule, DatePipe, NgIf, } from '@angular/common';
import { PaginatorModule } from 'primeng/paginator';
import { ErrorMessagePipe, TypeValidator } from '@my/core';
import { PeliculasViewModelService } from './servicios.service';
import { Subscription } from 'rxjs';

@Component({
    selector: 'app-peliculas-list',
    templateUrl: './tmpl-list.component.html',
    styleUrls: ['./componente.component.css'],
    standalone: true,
    imports: [RouterLink, PaginatorModule, CommonModule]
})
export class PeliculasListComponent implements OnChanges, OnDestroy {
  @Input() page = 0

  constructor(protected vm: PeliculasViewModelService) { }
  public get VM(): PeliculasViewModelService { return this.vm; }
  ngOnChanges(_changes: SimpleChanges): void {
    this.vm.load(this.page)
  }
  ngOnDestroy(): void { this.vm.clear(); }
}
@Component({
    selector: 'app-peliculas-add',
    templateUrl: './tmpl-form.component.html',
    styleUrls: ['./componente.component.css'],
    standalone: true,
    imports: [FormsModule, TypeValidator, ErrorMessagePipe]
})
export class PeliculasAddComponent implements OnInit {
  constructor(protected vm: PeliculasViewModelService) { }
  public get VM(): PeliculasViewModelService { return this.vm; }
  ngOnInit(): void {
    this.vm.add();
  }
}
@Component({
    selector: 'app-peliculas-edit',
    templateUrl: './tmpl-form.component.html',
    styleUrls: ['./componente.component.css'],
    standalone: true,
    imports: [FormsModule, TypeValidator, ErrorMessagePipe]
})
export class PeliculasEditComponent implements OnInit, OnDestroy {
  private obs$?: Subscription;
  constructor(protected vm: PeliculasViewModelService,
    protected route: ActivatedRoute, protected router: Router) { }
  public get VM(): PeliculasViewModelService { return this.vm; }
  ngOnInit(): void {
    this.obs$ = this.route.paramMap.subscribe(
      (params: ParamMap) => {
        const id = parseInt(params?.get('id') ?? '');
        if (id) {
          this.vm.edit(id);
        } else {
          this.router.navigate(['/404.html']);
        }
      });
  }
  ngOnDestroy(): void {
    this.obs$!.unsubscribe();
  }
}
@Component({
    selector: 'app-peliculas-view',
    templateUrl: './tmpl-view.component.html',
    styleUrls: ['./componente.component.css'],
    standalone: true,
    imports: [DatePipe, CommonModule, RouterLink,]
})
export class PeliculasViewComponent implements OnChanges {
  @Input() id?: string;

  constructor(protected vm: PeliculasViewModelService, protected router: Router) { 

  }
  public get VM(): PeliculasViewModelService { return this.vm; }
  ngOnChanges(_changes: SimpleChanges): void {

    if (this.id) {
      this.vm.view(+this.id);
    } else {
      this.router.navigate(['/404.html']);
    }
  }
}

export const PELICULAS_COMPONENTES = [
  PeliculasListComponent, PeliculasAddComponent, PeliculasEditComponent, PeliculasViewComponent,
];
