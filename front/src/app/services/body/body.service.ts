import { HttpClient } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Body } from 'src/app/models/body/body.model';
import { AuthService } from '../login/auth.service';
import { BASE_URL } from 'src/app/core/constants/constants';
import { HttpService } from '../http-service/http.service';

@Injectable({
  providedIn: 'root'
})
export class BodyService {

  constructor(private http: HttpClient, private authService: AuthService, private httpService: HttpService,  @Inject(BASE_URL) private baseUrl: string) { }

  getBodys(): Observable<Body[]> {
    return this.http.get<Body[]>(`${this.baseUrl}/body/all`);
  }

  createBody(body: Body): Observable<string> {
    const httpOptions = this.httpService.generateHttpOptions("text");
    return this.http.post<string>(`${this.baseUrl}/body/create`, body, httpOptions)
  }

  updateBody(id: number, body: Body): Observable<string> {
    const httpOptions = this.httpService.generateHttpOptions("text");
    return this.http.put<string>(`${this.baseUrl}/body/update/${id}`, body, httpOptions);
  }

  deleteBody(id: number): Observable<string> {
    const httpOptions = this.httpService.generateHttpOptions("text");
    return this.http.delete<string>(`${this.baseUrl}/body/delete/${id}`, httpOptions)
  }
}
