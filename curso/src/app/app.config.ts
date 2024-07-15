import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { ERROR_LEVEL, LoggerService } from '@my/core';
import { environment } from 'src/environments/environment';
import { provideHttpClient, withInterceptors, withInterceptorsFromDi } from '@angular/common/http';
import { ajaxWaitInterceptor } from './main';

export const appConfig: ApplicationConfig = {
  providers: [
    LoggerService,
    {provide: ERROR_LEVEL, useValue: environment.ERROR_LEVEL},
    provideZoneChangeDetection({eventCoalescing:true}),
    provideRouter(routes),
    provideHttpClient(withInterceptorsFromDi(), withInterceptors([ajaxWaitInterceptor])),
  ],
};
