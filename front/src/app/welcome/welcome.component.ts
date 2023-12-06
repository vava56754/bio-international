import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Subscription, interval } from 'rxjs';
import { AuthService } from '../services/login/auth.service';
import { Product } from '../models/product/product.model';
import { FileService } from '../services/file/file.service';
import { ProductService } from '../services/product/product.service';
import { Router } from '@angular/router';
import { LOG_MESSAGES } from '../core/constants/log-messages';



@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.scss']
})
export class WelcomeComponent implements OnInit{
  currentIndex: number = 0;
  private intervalSubscription!: Subscription;
  private subscription: Subscription = new Subscription();
  itemWidth!: number; 
  products: Product[] = [];
  @ViewChild('imageElement') imageElement!: ElementRef;
  constructor(public authService: AuthService,private productService: ProductService, private fileService: FileService, private router: Router) {
    
  }

  ngOnInit(): void {
    this.intervalSubscription = interval(5000).subscribe(() => {
      this.shiftRight();
    });
    this.subscription.add(this.getProducts());
  }

  ngOnDestroy() {
    if (this.intervalSubscription) {
      this.intervalSubscription.unsubscribe();
    }
    this.subscription.unsubscribe();
  }

  
  pictures = [
    { src: '../../assets/wallpaper/bd1.jpg', alt: 'img1' },
    { src: '../../assets/wallpaper/bd2.jpg', alt: 'img2' },
    { src: '../../assets/wallpaper/bd3.jpg', alt: 'img3' }
  ];

  shiftLeft() {
    if (this.currentIndex > 0) {
      this.currentIndex--;
    }else {
      this.currentIndex = this.pictures.length - 1;
    }
  }

  shiftRight() {
    const maxIndex = this.pictures.length - 1;
    if (this.currentIndex < maxIndex) {
      this.currentIndex++;
    } else {
      this.currentIndex = 0;
    }
  }

  goToProductList(): void {
    this.router.navigate(['/products']);
  }

  getProducts(): Subscription {
    return this.productService.getProductByTypeAndBody(-1, -1).subscribe({
      next: (products) => {
        this.products = products.slice(0,3)
        this.products.forEach((product) => {
          this.fileService.getFile(product.productLink).subscribe({
            next: (file) => {
              const reader = new FileReader();
              reader.onloadend = () => {
                product.productImageUrl = reader.result as string;
              };
              reader.readAsDataURL(file)
            },
            error: (e) => console.error(LOG_MESSAGES.fileRetrieval.error, e)
          })
        })
      },
      error: (e) => console.error(LOG_MESSAGES.productRetrieval.error, e)
    })
  }

  getRole(): string | null {
    return this.authService.getRole();
  }

  isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }
}
