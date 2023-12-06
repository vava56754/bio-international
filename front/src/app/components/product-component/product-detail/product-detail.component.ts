import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationStart, Router } from '@angular/router';
import { Subscription, switchMap } from 'rxjs';
import { FIELD } from 'src/app/core/constants/constants';
import { LOG_MESSAGES } from 'src/app/core/constants/log-messages';
import { MESSAGE } from 'src/app/core/constants/messages';
import { LineProcurement } from 'src/app/models/procurement/line-procurement.model';
import { Product } from 'src/app/models/product/product.model';
import { FileService } from 'src/app/services/file/file.service';
import { AuthService } from 'src/app/services/login/auth.service';
import { NotificationService } from 'src/app/services/notification/notification.service';
import { ProcurementService } from 'src/app/services/procurement/procurement.service';
import { ProductService } from 'src/app/services/product/product.service';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.scss']
})
export class ProductDetailComponent implements OnInit, OnDestroy {
  id: number = 0;
  product!: Product;
  productQuantityBasket: number = 1;
  subscription: Subscription = new Subscription();
  constructor(public authService: AuthService, private fileService: FileService, private productService: ProductService, private route: ActivatedRoute, private router:Router, private procurementService: ProcurementService, private notificationService: NotificationService) {}

  ngOnInit(): void {
      this.route.params.subscribe(param => {
        this.id = Number.parseInt(this.route.snapshot.params['productId']);
        this.subscription.add(this.getProductById(this.id));
      })
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }

  getProductById(id:number): Subscription {
    return this.productService.getProductByIdVisible(id).subscribe({
      next: (product) => {
        this.product = product
        this.fileService.getFile(this.product.productLink).subscribe({
          next: (file) => {
            const reader = new FileReader();
            reader.onloadend = () => {
              this.product.productImageUrl = reader.result as string;
            };
            reader.readAsDataURL(file);
          },
          error: (e) => console.log(LOG_MESSAGES.fileRetrieval.error, e)
        })
      },
      error: (e) => {
        console.log(LOG_MESSAGES.productRetrieval.error, e)
      }
    })
  }

  createProcurement(product: Product): void {
    if(this.isLoggedIn() == true) {
      let lineProcurement: LineProcurement = {} as LineProcurement;
      lineProcurement.lineQuantity = this.productQuantityBasket;
      lineProcurement.lineUnitPrice = product.productUnitPrice;
      lineProcurement.product = product;
      this.procurementService.createLineProcurement(lineProcurement).subscribe({
        next: () => {
            this.router.navigate(['/basket']);
        },
        error: (e) => {
          console.error(LOG_MESSAGES.create.error, e);
          this.notificationService.showNotification(MESSAGE.CREATE.FAIL, FIELD.RESULT.ERROR)
        }
      });
  
    } else {
      this.router.navigate(['/login']);
    }
  }

  

}
