import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { MainComponent } from './components/main/main.component';

import { ButtonModule } from 'primeng/button';
import { AuthenticationService } from './services/authentication.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    MainComponent,
    RouterOutlet,

    ButtonModule
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
    
  
export class AppComponent {  
  title = "webapp";

  constructor(
    private authenticationService: AuthenticationService,
    private router: Router
  ) { }

  toLogin(): void {
    if (this.isUserLoggedIn()) {
      this.logout();
      return;
    }
    this.router.navigateByUrl('/login');
  }

  isUserLoggedIn(): boolean{
    return this.authenticationService.isUserLoggedIn();
  }

  logout() {
    this.authenticationService.logout();
    this.router.navigateByUrl('/');
  }

  get loginLabel(): string{
    return this.isUserLoggedIn() ? 'Logout' : 'Login';
  }
}

