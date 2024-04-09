import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BASE_URL, BEARER } from 'src/app/core/constants/constants';
import { HttpService } from '../http-service/http.service';
import { LOG_MESSAGES } from 'src/app/core/constants/log-messages';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  isLoggedInVar: boolean = false;
  private readonly TOKEN_CHECK_INTERVAL = 60000;

  constructor(private http: HttpClient, @Inject(BASE_URL) private baseUrl: string, private router: Router) {
    setInterval(() => {
      this.checkTokenExpiration();
    }, this.TOKEN_CHECK_INTERVAL);
   }

  login(username: string, password: string): Observable<any> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };
    return this.http.post<any>(`${this.baseUrl}/user/login`,{username, password}, httpOptions);
  }

  logout(): Observable<string> {
    const bearerToken = localStorage.getItem(BEARER);
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }).set('Authorization', `Bearer ${bearerToken}`),
      responseType: 'text' as const
    };
    return this.http.post(`${this.baseUrl}/user/logout`, null, httpOptions)
  }

  getRole(): string | null {
    const token = localStorage.getItem(BEARER);
    if (token) {
      const decodedToken = this.decodeToken(token);
      return decodedToken ? decodedToken.role : null;
    }
    return null;
  }

  getExpirationDate(): Date | null {
    const token = localStorage.getItem(BEARER);
    if (token) {
      const decodedToken = this.decodeToken(token);
      return new Date(decodedToken.exp*1000);
    }
    return null;
  }

  isTokenExpired(): boolean {
    const token = localStorage.getItem(BEARER);
    if (!token) {
      return true; 
    }
    const expirationDate = this.getExpirationDate();
    if (!expirationDate) {
      return true; 
    }
    return expirationDate.getTime() < Date.now(); 
  }

  private checkTokenExpiration(): void {

    if(localStorage.getItem(BEARER)) {
      if (this.isTokenExpired()) {
        this.logout();
        localStorage.clear();
        this.router.navigate(['/'])
      }
    }
  }

  private decodeToken(token: string): any {
    try {
      return JSON.parse(atob(token.split('.')[1]));
    } catch (e) {
      console.error(LOG_MESSAGES.token.error, e);
      return null;
    }
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem(BEARER);
  }
}
