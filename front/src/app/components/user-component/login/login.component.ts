import { HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { BEARER } from 'src/app/core/constants/constants';
import { LOG_MESSAGES } from 'src/app/core/constants/log-messages';
import { MESSAGE } from 'src/app/core/constants/messages';
import { AuthService } from 'src/app/services/login/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  username: string="";
  password: string="";
  errorMessage: string = ""; 

  constructor(private authService: AuthService, private router: Router) {

  }

  login(): void {
    this.authService.login(this.username, this.password).subscribe({
      next: (response) => {
        localStorage.setItem(BEARER, response.bearer);
        this.router.navigate(['/products'])
      },
      error: (e: HttpErrorResponse) => {
        console.error(LOG_MESSAGES.login.error, e)
        this.errorMessage = MESSAGE.LOGIN.FAIL;
      }
    });
  }
}
