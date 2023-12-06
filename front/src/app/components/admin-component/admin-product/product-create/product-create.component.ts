import { Component, OnDestroy, OnInit, Type } from '@angular/core';
import { Router } from '@angular/router';
import { Body } from 'src/app/models/body/body.model';
import { House } from 'src/app/models/house/house.model';
import { Product } from 'src/app/models/product/product.model';
import { TypeProduct } from 'src/app/models/type/type.model';
import { BodyService } from 'src/app/services/body/body.service';
import { HouseService } from 'src/app/services/house/house.service';
import { NotificationService } from 'src/app/services/notification/notification.service';
import { ProductService } from 'src/app/services/product/product.service';
import { TypeService } from 'src/app/services/type/type.service';
import { Location } from '@angular/common';
import { LOG_MESSAGES } from 'src/app/core/constants/log-messages';
import { Subscription } from 'rxjs';
import { FIELD, FILE, PRODUCT } from 'src/app/core/constants/constants';
import { MESSAGE } from 'src/app/core/constants/messages';

@Component({
  selector: 'app-product-create',
  templateUrl: './product-create.component.html',
  styleUrls: ['./product-create.component.scss']
})
export class ProductCreateComponent implements OnInit, OnDestroy{
  houses: House[] = [];
  bodys: Body[] = [];
  types: TypeProduct[] = [];
  selectedHouse: number = 0;
  selectedBody: number = 0;
  selectedType: number = 0;
  house!: House;
  typeCreate!: TypeProduct[];
  bodyCreate!: Body[];
  productCreateName: string = "";
  productCreateDescription: string = "";
  productCreatePrice:number = 0;
  productCreateStock:number = 0;
  selectedFile!: File;
  private subscriptions: Subscription[] = [];

  constructor(private productService: ProductService, private notificationService: NotificationService, private houseService: HouseService, private bodyService: BodyService, private typeService: TypeService, private router:Router, private location: Location){

  }

  ngOnInit(): void {
    this.subscriptions.push(this.getAllType(),this.getAllBody(),this.getAllHouse());
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  getAllHouse(): Subscription {
    return this.houseService.getHouses().subscribe({
      next: (houses) => {
        this.houses = houses;
      },
      error: (e) => {
        console.error(LOG_MESSAGES.houseRetrieval.error,e)
      }
    })
  }

  getAllBody(): Subscription {
    return this.bodyService.getBodys().subscribe({
      next: (bodys)=> {
        this.bodys = bodys
      },
      error: (e) => {
        console.error(LOG_MESSAGES.bodyRetrieval.error,e);
      }
    })
  }

  getAllType(): Subscription {
    return this.typeService.getTypes().subscribe({
      next: (types) => {
        this.types = types;
      },error: (e) => {
        console.error(LOG_MESSAGES.typeRetrieval.error,e);
      }
    })
  }

  setHouseProduct(): void {
    const house = this.houses.find((house: House)=>this.selectedHouse == house.houseId);
    if(house){
      this.house = house
    } else {
      console.error(LOG_MESSAGES.houseRetrieval.notFound)
    }
    

  }

  setBodyProduct(): void {
    const body = this.bodys.find((body: Body)=> this.selectedBody == body.bodyId)
    if(body){
      this.bodyCreate = [body];
    } else {
      console.error(LOG_MESSAGES.bodyRetrieval.notFound);
    }
    
  }

  setTypeProduct(): void {
    const type = this.types.find((type: TypeProduct)=> this.selectedType == type.typeId)
    if(type){
      this.typeCreate = [type];
    } else {
      console.error(LOG_MESSAGES.typeRetrieval.notFound);
    }
  }

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0];
  }

  createProduct(): void {
    let product = this.checkProduct();
    let data = new FormData();
    data.append(FILE, this.selectedFile, this.selectedFile.name);
    data.append(PRODUCT, JSON.stringify(product));


    this.productService.createProduct(data).subscribe({
        next: ()=> {
          this.notificationService.showNotification(MESSAGE.CREATE.SUCCESS,FIELD.RESULT.SUCCESS);
        },
        error: (e) => {
          console.error(LOG_MESSAGES.create.error, e);
          this.notificationService.showNotification(MESSAGE.CREATE.FAIL,FIELD.RESULT.ERROR);
        },
        complete: () => {
          setTimeout(() => {
            this.router.navigate(["/admin-product"]);
          }, 2500);
        } 
    })

  }

  checkProduct(): Product {
    let product: Product = {} as Product;
    product.productName = this.productCreateName;
    product.productDescription = this.productCreateDescription;
    product.productUnitPrice = this.productCreatePrice;
    product.productStock = this.productCreateStock;

    if (!product.productName || product.productName.trim() === '') {
      this.notificationService.showNotification(MESSAGE.PRODUCT.EMPTY_NAME,FIELD.RESULT.ERROR);
      throw null;
    }

    if (!product.productDescription || product.productDescription.trim() === '') {
      this.notificationService.showNotification(MESSAGE.PRODUCT.EMPTY_DESC,FIELD.RESULT.ERROR);
      throw null;
    }
    
    if (product.productUnitPrice <= 0) {
      this.notificationService.showNotification(MESSAGE.PRODUCT.INVALID_PRICE,FIELD.RESULT.ERROR);
      throw null; 
    }
    
    if (product.productStock <= 0) {
      this.notificationService.showNotification(MESSAGE.PRODUCT.INVALID_QUANTITY,FIELD.RESULT.ERROR);
      throw null;
    }

    if(this.house != null) {
      product.house = this.house;
    }
    if(this.bodyCreate != null) {
      product.bodyParts = this.bodyCreate;
    }
    if(this.typeCreate != null) {
      product.productTypes = this.typeCreate;
    }

    return product;
  }

  cancel(): void {
    this.location.back();
  }
}
