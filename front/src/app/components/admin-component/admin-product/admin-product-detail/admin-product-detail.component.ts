import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Body } from 'src/app/models/body/body.model';
import { House } from 'src/app/models/house/house.model';
import { Product } from 'src/app/models/product/product.model';
import { TypeProduct } from 'src/app/models/type/type.model';
import { BodyService } from 'src/app/services/body/body.service';
import { FileService } from 'src/app/services/file/file.service';
import { HouseService } from 'src/app/services/house/house.service';
import { ProductService } from 'src/app/services/product/product.service';
import { TypeService } from 'src/app/services/type/type.service';
import { Location } from '@angular/common';
import { LOG_MESSAGES } from 'src/app/core/constants/log-messages';
import { FIELD, FILE, PRODUCT } from 'src/app/core/constants/constants';
import { MESSAGE } from 'src/app/core/constants/messages';
import { NotificationService } from 'src/app/services/notification/notification.service';

@Component({
  selector: 'app-admin-product-detail',
  templateUrl: './admin-product-detail.component.html',
  styleUrls: ['./admin-product-detail.component.scss']
})
export class AdminProductDetailComponent implements OnInit, OnDestroy {
  id: number = 0;
  product: Product = {} as Product;
  houses: House[] = [];
  bodys: Body[] = [];
  types: TypeProduct[] = [];
  selectedHouse: number = 0;
  selectedBody: number = 0;
  selectedType: number = 0;
  displayProductName: boolean = false;
  displayProductDescription: boolean = false;
  displayProductPrice: boolean = false;
  displayProductStock: boolean = false;
  updateProductName: string = "";
  updateProductDescription: string = "";
  updateProductPrice: number = 0;
  updateProductStock: number = 0;
  selectedFile!: File;
  defaultfile! :Blob;
  private subscriptions: Subscription[] = [];

  constructor(private fileService: FileService, private productService: ProductService, private houseService: HouseService, private bodyService: BodyService, private typeService: TypeService, private notificationService: NotificationService, private route: ActivatedRoute, private router:Router, private location: Location) {}

  ngOnInit(): void {
      this.route.params.subscribe(param => {
        this.id = Number.parseInt(this.route.snapshot.params['productId']);
        this.subscriptions.push(this.getProductById(this.id));
      })
      this.subscriptions.push(this.getAllHouse(),this.getAllBody(),this.getAllType())
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }


  toggleDisplayProperty(propertyName: string) {
    (this as any)[propertyName] = !(this as any)[propertyName];
  }

  setProductProperty(id: string, property: string, value: any): void {
    if (Object.prototype.hasOwnProperty.call(this.product, property)) {
      (this.product as any)[property as keyof Product] = value;
      this.disableEditing(id);
    } else {
      console.error(`La propriété ${property} n'existe pas dans l'objet Product.`);
    }
  }

  resetProductProperty(property: string) {
    (this as any)[`update${property.charAt(0).toUpperCase() + property.slice(1)}`] = (this.product as any)[property as keyof Product];
  }

  enableEditing(id: string) {
    const inputElement = document.getElementById(id);
    if(inputElement != null) {
      inputElement.removeAttribute("readonly");
    }

  }

  disableEditing(id: string) {
    const inputElement = document.getElementById(id);
    if(inputElement != null) {
      inputElement.setAttribute("readonly", "true")
    }

  }

  setHouseProduct() {
    const house = this.houses.find((house: House)=>this.selectedHouse == house.houseId);
    if(house){
      this.product.house = house
    } else {
      console.error(LOG_MESSAGES.houseRetrieval.notFound)
    }
  }

  setBodyProduct() {
    const body = this.bodys.find((body: Body)=> this.selectedBody == body.bodyId)
    if(body){
      this.product.bodyParts = [body];
    } else {
      console.error(LOG_MESSAGES.bodyRetrieval.notFound)
    }
    
  }

  setTypeProduct() {
    const type = this.types.find((type: TypeProduct)=> this.selectedType == type.typeId)
    if(type){
      this.product.productTypes = [type];
    } else {
      console.error(LOG_MESSAGES.typeRetrieval.notFound)
    }
  }

  onFileSelected(event: any): void {
    if (event.target.files.length > 0) {
      this.selectedFile = event.target.files[0];
    }
  }

  getAllHouse(): Subscription {
    return this.houseService.getHouses().subscribe({
      next : (houses) => {
        this.houses = houses;
      },
      error : (e) => {
        console.error(LOG_MESSAGES.houseRetrieval.error, e);
      }
    })
  }

  getAllBody(): Subscription {
    return this.bodyService.getBodys().subscribe({
      next : (bodys)=> {
        this.bodys = bodys
      },
      error: (e) => {
        console.error(LOG_MESSAGES.bodyRetrieval.error, e);
      }
    })
  }

  getAllType(): Subscription {
    return this.typeService.getTypes().subscribe({
      next : (types)=> {
        this.types = types;
      },
      error : (e) => {
        console.error(LOG_MESSAGES.typeRetrieval.error, e);
      }
    })
  }

  getProductById(id:number): Subscription {
    return this.productService.getProductById(id).subscribe({
      next: (product) => {
        this.product = product;
        this.fileService.getFile(product.productLink).subscribe({
          next: (file) => {
            const reader = new FileReader();
            reader.onloadend = () => {
              this.product.productImageUrl = reader.result as string;
            };
            reader.readAsDataURL(file);
            this.defaultfile = file;
          },
          error: (e) => console.error(LOG_MESSAGES.fileRetrieval.error, e)
        });
      },
      error:(e) => console.error(LOG_MESSAGES.productRetrieval.error, e)
    });
  }


  updateProduct() {
    let data = new FormData();
    if(this.selectedFile) {
      data.append(FILE, this.selectedFile, this.selectedFile.name);
    } else {
      data.append(FILE, this.defaultfile);
    }
    data.append(PRODUCT, JSON.stringify(this.checkProduct(this.product)))
    this.productService.updateProduct(this.product.productId, data).subscribe({
      next : () => {
        this.notificationService.showNotification(MESSAGE.UPDATE.SUCCESS, FIELD.RESULT.SUCCESS);
      },
      error : (e) => {
        console.error(LOG_MESSAGES.update.error, e);
        this.notificationService.showNotification(MESSAGE.UPDATE.FAIL, FIELD.RESULT.SUCCESS);
      },complete: () => {
        setTimeout(() => {
          this.router.navigate(["/admin-product"]);
        }, 2500);
      },
    });
  }

  checkProduct(product: Product): Product {

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
    
    if (product.productStock < 0) {
      this.notificationService.showNotification(MESSAGE.PRODUCT.INVALID_QUANTITY,FIELD.RESULT.ERROR);
      throw null;
    }
    return product;
  }

  cancel() {
    this.location.back();
  }
}
