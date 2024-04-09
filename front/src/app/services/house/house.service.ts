import { Inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { House } from 'src/app/models/house/house.model';
import { Observable } from 'rxjs';
import { AuthService } from '../login/auth.service';
import { BASE_URL } from 'src/app/core/constants/constants';
import { HttpService } from '../http-service/http.service';

@Injectable({
  providedIn: 'root'
})
export class HouseService {

  constructor(private http: HttpClient, private authService: AuthService, private httpService: HttpService, @Inject(BASE_URL) private baseUrl: string) { 

  }

  getHouseById(id: number): Observable<House> {
    return this.http.get<House>(`${this.baseUrl}/house/${id}`)
  }

  getHouses(): Observable<House[]> {
    return this.http.get<House[]>(`${this.baseUrl}/house/all`);
  }

  createhouse(formData: FormData): Observable<string> {
    const httpOptions = this.httpService.generateHttpOptions("text");
    return this.http.post<string>(`${this.baseUrl}/house/create`, formData, httpOptions)
  }

  updateHouse(id: number, formData: FormData): Observable<any> {
    const httpOptions = this.httpService.generateHttpOptions("text");
    return this.http.put(`${this.baseUrl}/house/update/${id}`, formData, httpOptions)

  }

  deleteHouse(id: number): Observable<string> {
    const httpOptions = this.httpService.generateHttpOptions("text");
    return this.http.delete<string>(`${this.baseUrl}/house/delete/${id}`, httpOptions);
  }
}
