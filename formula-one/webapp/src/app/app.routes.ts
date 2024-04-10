import { Routes } from '@angular/router';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { MainComponent } from './components/main/main.component';
import { TeamGuard } from './components/guards/team-guard';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () =>
      import('./components/login/login.component').then(m => m.LoginComponent),
  },
  { path: '', component: MainComponent },
  {
    path: 'add-new-team',
    loadComponent: () =>
      import('./components/add-team/add-team.component').then(
        m => m.AddTeamComponent
      ),
    canActivate: [TeamGuard],
  },
  { path: 'update-team', pathMatch: 'full', redirectTo: 'add-new-team' },
  { path: '**', component: PageNotFoundComponent },
];
