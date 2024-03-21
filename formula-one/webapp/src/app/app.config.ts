import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {provideHttpClient} from "@angular/common/http";
import { provideAnimations } from '@angular/platform-browser/animations';
import { MessageService } from 'primeng/api';
import { StoreModule } from '@ngrx/store';
import { formulaOneItemReducer } from './components/store/formula-one-reducers';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(),
    provideAnimations(),
    MessageService,
    importProvidersFrom(StoreModule.forRoot({formulaOneItem: formulaOneItemReducer})),
  ],
};
