import { HttpClient } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TypeProduct } from 'src/app/models/type/type.model';
import { AuthService } from '../login/auth.service';
import { HttpService } from '../http-service/http.service';
import { BASE_URL } from 'src/app/core/constants/constants';

@Injectable({
  providedIn: 'root'
})
export class TypeService {

  constructor(private http: HttpClient, private authService: AuthService, private httpService: HttpService,  @Inject(BASE_URL) private baseUrl: string) { }

  getTypes(): Observable<TypeProduct[]> {
    return this.http.get<TypeProduct[]>(`${this.baseUrl}/type/all`);
  }

  createType(type: TypeProduct): Observable<string> {
    const httpOptions = this.httpService.generateHttpOptions("text");
    return this.http.post<string>(`${this.baseUrl}/type/create`, type, httpOptions)
  }

  updateType(id: number, type: TypeProduct): Observable<string> {
    const httpOptions = this.httpService.generateHttpOptions("text");
    return this.http.put<string>(`${this.baseUrl}/type/update/${id}`, type, httpOptions);
  }

  deleteType(id: number): Observable<string> {
    const httpOptions = this.httpService.generateHttpOptions("text");
    return this.http.delete<string>(`${this.baseUrl}/type/delete/${id}`, httpOptions);
  }
}
