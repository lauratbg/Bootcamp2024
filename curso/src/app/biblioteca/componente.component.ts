import { Component, forwardRef, Input, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import { BibliotecaViewModelService } from './servicios.service';
import { ErrorMessagePipe, TypeValidator } from '@my/core';
import { FormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { ActivatedRoute, ParamMap, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-biblioteca',
  templateUrl: './tmpl-anfitrion.component.html',
  styleUrls: ['./componente.component.css'],
  standalone: true,
  imports: [
    forwardRef(() => BibliotecaAddComponent),
    forwardRef(() =>BibliotecaEditComponent),
    forwardRef(() => BibliotecaViewComponent),
    forwardRef(() => BibliotecaListComponent),
  ],
})
export class BibliotecaComponent implements OnInit, OnDestroy {
  constructor(protected vm: BibliotecaViewModelService) { }
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
  imports: [RouterLink]
})
export class BibliotecaListComponent implements OnInit, OnDestroy {
  constructor(protected vm: BibliotecaViewModelService) { }
  public get VM(): BibliotecaViewModelService { return this.vm; }
  ngOnInit(): void { this.vm.list(); }
  ngOnDestroy(): void { this.vm.clear(); }
}

@Component({
  selector: 'app-biblioteca-add',
  templateUrl: './tmpl-form.component.html',
  styleUrls: ['./componente.component.css'],
  standalone: true,
  imports: [FormsModule, TypeValidator, ErrorMessagePipe]
})
export class BibliotecaAddComponent implements OnInit {
  constructor(protected vm: BibliotecaViewModelService) { }
  public get VM(): BibliotecaViewModelService { return this.vm; }
  ngOnInit(): void {
    this.vm.add();
  }
}
@Component({
  selector: 'app-biblioteca-edit',
  templateUrl: './tmpl-form.component.html',
  styleUrls: ['./componente.component.css'],
  standalone: true,
  imports: [FormsModule, TypeValidator, ErrorMessagePipe]
})
export class BibliotecaEditComponent implements OnInit, OnDestroy {
  private obs$?: Subscription;
  constructor(protected vm: BibliotecaViewModelService,
    protected route: ActivatedRoute, protected router: Router) { }
  public get VM(): BibliotecaViewModelService { return this.vm; }
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
  selector: 'app-biblioteca-view',
  templateUrl: './tmpl-view.component.html',
  styleUrls: ['./componente.component.css'],
  standalone: true,
  imports: [DatePipe]
})
export class BibliotecaViewComponent implements OnChanges {
  @Input() id?: string;
  constructor(protected vm: BibliotecaViewModelService, protected router: Router) { }
  public get VM(): BibliotecaViewModelService { return this.vm; }
  ngOnChanges(changes: SimpleChanges): void {
    if (this.id) {
      this.vm.view(+this.id);
    } else {
      this.router.navigate(['/404.html']);
    }
  }
}

export const BIBLIOTECA_COMPONENTES = [
  BibliotecaComponent,
  BibliotecaListComponent,
  BibliotecaAddComponent,
  BibliotecaEditComponent,
  BibliotecaViewComponent,
];
