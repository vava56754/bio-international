import { Component, ElementRef, HostListener, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subject, debounceTime } from 'rxjs';
import { AuthService } from 'src/app/services/login/auth.service';
import { ProductService } from 'src/app/services/product/product.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent {
  isSidenavOpen = false;
  isUserNavOpen = false;
  isConnect = false;
  nameSearch = "";
  isNavResOpen = false;
  isNavSearchOpen = false;
  private searchSubject: Subject<string> = new Subject<string>();

  constructor(private el: ElementRef, public authService: AuthService, private router: Router, private productService: ProductService) {
    this.searchSubject.pipe(debounceTime(300)).subscribe(() => {

    });
  }

  logout() {
    return this.authService.logout().subscribe({
      next: (response) => {
        console.log('Logout Successful:', response);
        localStorage.clear()
      },
      error: (e) => {
        console.error('Logout Error:', e)
        localStorage.clear()
        this.router.navigate(['/'])
      },
      complete: () => {
        this.router.navigate(['/'])
      }
    });
  }

  onSearchChange(event: any): void {
    const newValue = event.target.value; 
    this.nameSearch = newValue; 
    if (newValue.trim() !== '') { 
      this.searchSubject.next(newValue);
    } 
 }


  getRole(): string | null {
    return this.authService.getRole();
  }

  isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }

  openSearchNav() {
    this.isNavSearchOpen = !this.isNavSearchOpen
    document.addEventListener('click', this.handleDocumentClick.bind(this));
  }

  closeSearchNav() {
    this.isNavSearchOpen = false;
    document.removeEventListener('click', this.handleDocumentClick.bind(this));
  }

  private handleDocumentClick(event: Event) {
    if (!this.el.nativeElement.contains(event.target)) {
      this.closeSearchNav();
    }
  }

  @HostListener('document:click', ['$event'])
  clickout(event: Event) {
    if (!this.el.nativeElement.contains(event.target)) {
      this.isNavSearchOpen = false;
    }
  }

  onValeurChange(event: any) {
    this.nameSearch;
  }

}
