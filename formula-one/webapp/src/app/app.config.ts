import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {HTTP_INTERCEPTORS, provideHttpClient} from "@angular/common/http";
import { provideAnimations } from '@angular/platform-browser/animations';
import { MessageService } from 'primeng/api';
import { StoreModule } from '@ngrx/store';
import { formulaOneItemReducer } from './store/formula-one-reducers';
import { HttpInterceptorService } from './services/http-interceptor.service';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(),
    provideAnimations(),
    MessageService,
    importProvidersFrom(StoreModule.forRoot({ formulaOneItem: formulaOneItemReducer })),
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptorService,
      multi: true
    }
  ],
};
