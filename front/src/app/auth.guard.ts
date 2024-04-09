
import { Injectable } from '@angular/core';
import { Router, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';
import { AuthService } from './services/login/auth.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard {
  constructor(private authService: AuthService, private router: Router) {}



  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
   
    const roles = next.data && next.data['role'] as Array<string>;
    
   
    if (!roles) {
      if (this.authService.isLoggedIn()) {
        return true;
      } else {
        this.router.navigate(['/unauthorized']);
        return false;
      }
    }

    if (this.authService.isLoggedIn() && roles && roles.includes(this.authService.getRole()!)) {
      return true;
    } else {
      this.router.navigate(['/unauthorized']);
      return false;
    }
  }
}