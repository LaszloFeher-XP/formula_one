import { Routes } from '@angular/router';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { MainComponent } from './components/main/main.component';

export const routes: Routes = [
    { path: '', component: MainComponent },
    { path: '**', component: PageNotFoundComponent }
];
