import { Routes } from '@angular/router';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { MainComponent } from './components/main/main.component';
import { AddTeamComponent } from './components/add-team/add-team.component';
import { LoginComponent } from './components/login/login.component';

export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: '', component: MainComponent },
    { path: 'add-new-team', component: AddTeamComponent },
    { path: 'update-team', component: AddTeamComponent },
    { path: '**', component: PageNotFoundComponent }
];
