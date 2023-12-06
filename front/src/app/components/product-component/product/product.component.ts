import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { LOG_MESSAGES } from 'src/app/core/constants/log-messages';
import { Body } from 'src/app/models/body/body.model';
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
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit, OnDestroy{
  products: Product[] = [];
  typeProduct: TypeProduct[]= [];
  bodys: Body[] = [];
  selectedType: number = -1;
  selectedBody: number = -1;
  subscription: Subscription[] = []

  constructor( public authService: AuthService, private productService: ProductService, private fileService: FileService, private typeService: TypeService, private router: Router, private bodyService: BodyService, private procurementService: ProcurementService) {}

  ngOnInit(): void {
    this.subscription.push(this.getProductsByBodyAndType(-1,-1),  this.getTypes(), this.getBodys())
  }

  ngOnDestroy(): void {
    this.subscription.forEach((subscription) => {
      subscription.unsubscribe();
    })
  }

  onTypeSelected(): void {
    this.filterProducts();
  }

  onBodySelected(): void {
    this.filterProducts();
  }

  private filterProducts(): void {
    this.getProductsByBodyAndType(this.selectedType, this.selectedBody);
  }

  isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }

  /*getProducts(): Subscription {
    return this.productService.getProductsVisibles().subscribe({
      next: (products) => {
        this.products = products
        this.products.forEach((product)=>{
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
      },
      error: (e) => console.error(LOG_MESSAGES.productRetrieval.error, e)
    })
  }*/

  getProductsByBodyAndType(typeId: number, bodyId: number): Subscription {
    return this.productService.getProductByTypeAndBody(typeId, bodyId).subscribe({
      next: (products) => {
        this.products = products
        this.products.forEach((product)=>{
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
      },
      error: (e) => console.error(LOG_MESSAGES.productRetrieval.error, e)
    })
  }

  getTypes(): Subscription {
    return this.typeService.getTypes().subscribe({
      next: (types)=> {
        this.typeProduct = types;
      },
      error: (e) => console.error(LOG_MESSAGES.typeRetrieval.error, e)
    })
  }

  getBodys(): Subscription {
    return this.bodyService.getBodys().subscribe({
      next: (bodys)=>{
        this.bodys = bodys;
      },
      error: (e) => console.error(LOG_MESSAGES.bodyRetrieval.error, e)
    })
  }

  createProcurement(product: Product) : void {
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

}
