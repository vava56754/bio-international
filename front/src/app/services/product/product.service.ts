import { HttpClient } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from 'src/app/models/product/product.model';
import { AuthService } from '../login/auth.service';
import { BASE_URL } from 'src/app/core/constants/constants';
import { HttpService } from '../http-service/http.service';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient, private httpService: HttpService, @Inject(BASE_URL) private baseUrl: string) {
   
  }

  getAllProducts():Observable<Product[]> {
    const httpOptions = this.httpService.generateHttpOptions("json");
    return this.http.get<Product[]>(`${this.baseUrl}/product/all`, httpOptions);
  }

  getProductByIdVisible(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.baseUrl}/product/visible/${id}`)
  }

  getProductById(id: number): Observable<Product> {
    const httpOptions = this.httpService.generateHttpOptions("json");
    return this.http.get<Product>(`${this.baseUrl}/product/${id}`, httpOptions)
  }

  getProductByTypeAndBody(typeId: number, bodyId: number):  Observable<Product[]> {
    return this.http.get<Product[]>(`${this.baseUrl}/product/type/${typeId}/body/${bodyId}`)
  }

  searchProduct(name: string): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.baseUrl}/product/search?name=${name}`);
  }

  createProduct(formData: FormData): Observable<string> {
    const httpOptions = this.httpService.generateHttpOptions("text");
    return this.http.post<string>(`${this.baseUrl}/product/create`, formData, httpOptions)
  }

  updateProduct(id: number, formData: FormData): Observable<string> {
    const httpOptions = this.httpService.generateHttpOptions("text");
    return this.http.put<string>(`${this.baseUrl}/product/update/${id}`,formData, httpOptions)

  }

  productVisible(id: number): Observable<string> {
    const httpOptions = this.httpService.generateHttpOptions("text");
    return this.http.put<string>(`${this.baseUrl}/product/update/visible/${id}`, {}, httpOptions)
  }
}
