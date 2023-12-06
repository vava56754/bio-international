import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Product } from 'src/app/models/product/product.model';
import { FileService } from 'src/app/services/file/file.service';
import { NotificationService } from 'src/app/services/notification/notification.service';
import { ProductService } from 'src/app/services/product/product.service';
import { FIELD } from 'src/app/core/constants/constants';
import { MESSAGE } from 'src/app/core/constants/messages';
import { LOG_MESSAGES } from 'src/app/core/constants/log-messages';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-admin-product',
  templateUrl: './admin-product.component.html',
  styleUrls: ['./admin-product.component.scss']
})
export class AdminProductComponent implements OnInit, OnDestroy{
  products: Product[] = [];
  productsVisible: Product[] = [];
  productsHidden: Product[] = [];
  private subscription: Subscription = new Subscription();

  constructor(private productService: ProductService, private notificationService: NotificationService, private fileService: FileService, private router: Router) {}

  ngOnInit(): void {
    this.subscription.add(this.getProducts());
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  getProducts(): Subscription {
    return this.productService.getAllProducts().subscribe({
      next: (res) => {
        this.products = res;
        this.products.forEach(product => {
          this.fileService.getFile(product.productLink).subscribe({
            next: (file) => {
              const reader = new FileReader();
              reader.onloadend = () => {
                product.productImageUrl = reader.result as string;
              };
              reader.readAsDataURL(file);
            },
            error: (e) => {
              console.error(LOG_MESSAGES.fileRetrieval.error, e);
            }

          })
        })
      },
      error: (e) => {
        console.error(LOG_MESSAGES.productRetrieval.error, e);
      },
      complete: () => {
        this.separateProducts();
      }
    })
  }

  separateProducts(): void {
    this.productsVisible  = this.products.filter((product)=> product.visible == true);
    this.productsHidden = this.products.filter((product)=> !product.visible);
  }

  hiddenOrShowProduct(id: number): void {
    this.productService.productVisible(id).subscribe({
      next: (res) => {
        this.notificationService.showNotification(MESSAGE.PRODUCT.MOVE_SUCCESS, FIELD.RESULT.SUCCESS)
      },
      error: (e) => {
        console.error(e);
        this.notificationService.showNotification(MESSAGE.PRODUCT.MOVE_FAIL, FIELD.RESULT.ERROR)
      }, 
      complete: () => {
        this.getProducts();
      }
    })
  }

  goToCreate() {
    this.router.navigate(["/admin-product-create"])
  }

  goToProductDetail(productId: number): void {
    this.router.navigate(['/admin-product', productId]);
  }
}
