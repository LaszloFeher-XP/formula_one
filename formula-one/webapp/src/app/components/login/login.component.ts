import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/authentication.service';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';

import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule,    
    ReactiveFormsModule,    

    ButtonModule,
    CardModule,
    InputTextModule,
    PasswordModule,
    ToastModule,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  formGroup: FormGroup;

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService,
    private messageService : MessageService,
  ) {  
      this.formGroup = new FormGroup({
        username: new FormControl('', Validators.required),
        password: new FormControl('', Validators.required),
      })
     }

  back(): void{
    this.router.navigateByUrl('/');
  }

  login() {
    this.authenticationService.authenticationService(
      this.formGroup.controls['username'].value, 
      this.formGroup.controls['password'].value)
      .subscribe(() => {        
        this.showSuccess();
        setTimeout(() => {      
          this.formGroup.reset();
          this.back();
        }, 1000);   
    }, () => {
      this.showInvalid();
    });      
  }

  private showSuccess(): void{
    this.messageService.add({ severity: 'success', summary: 'Login Successful.', detail: '' });
  }

  private showInvalid(): void{
    this.messageService.add({ severity: 'error', summary: 'Invalid Credentials.', detail: 'Please check the login data.' });
  }

}



