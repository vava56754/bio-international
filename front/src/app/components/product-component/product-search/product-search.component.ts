import { Component, Input, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { LOG_MESSAGES } from 'src/app/core/constants/log-messages';
import { LineProcurement } from 'src/app/models/procurement/line-procurement.model';
import { Product } from 'src/app/models/product/product.model';
import { TypeProduct } from 'src/app/models/type/type.model';
import { BodyService } from 'src/app/services/body/body.service';
import { FileService } from 'src/app/services/file/file.service';
import { AuthService } from 'src/app/services/login/auth.service';
import { ProcurementService } from 'src/app/services/procurement/procurement.service';
import { ProductService } from 'src/app/services/product/product.service';
import { TypeService } from 'src/app/services/type/type.service';

@Component({
  selector: 'app-product-search',
  templateUrl: './product-search.component.html',
  styleUrls: ['./product-search.component.scss']
})
export class ProductSearchComponent implements OnInit, OnDestroy {
  products: Product[] = [];
  @Input() searchName: string = "";
  name: string = "";
  subscription: Subscription = new Subscription();

  constructor( public authService: AuthService, private productService: ProductService, private fileService: FileService, private typeService: TypeService, private router: Router, private bodyService: BodyService, private procurementService: ProcurementService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.subscription.add(this.search(this.searchName));
  }

  ngOnChanges(changes: SimpleChanges): void {
    if ('searchName' in changes) {
      this.search(this.searchName);
    }
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  search(name: string): Subscription {
    return this.productService.searchProduct(name).subscribe({
      next: (products) => {
        this.products = products;
        this.products.forEach((product) => {
          this.fileService.getFile(product.productLink).subscribe({
            next: (file) => {
              const reader = new FileReader();
              reader.onloadend = () => {
                product.productImageUrl = reader.result as string;
              };
              reader.readAsDataURL(file);
            },
            error: (e) => console.error(LOG_MESSAGES.fileRetrieval.error, e)
          })
        })
      }
      ,error: (e) => console.error(LOG_MESSAGES.productRetrieval.error, e)
    })
    
  }

  goToProductDetail(productId: number): void {
    this.router.navigate(['/product', productId]);
  }

  createProcurement(product: Product): void {
    if(this.isLoggedIn() == true) {
      let lineProcurement: LineProcurement = {} as LineProcurement;
      lineProcurement.lineQuantity = 1;
      lineProcurement.lineUnitPrice = product.productUnitPrice;
      lineProcurement.product = product;
      this.procurementService.createLineProcurement(lineProcurement).subscribe({
        next: () => {
            this.router.navigate(['/basket']);
        },
        error: (e) => console.error(LOG_MESSAGES.create.error, e)
      });
  
    } else {
      this.router.navigate(['/login']);
    }
  }

  isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }


}
