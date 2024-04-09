import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/models/user/user.model';
import { BASE_URL } from 'src/app/core/constants/constants';
import { HttpService } from '../http-service/http.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient,  private httpService: HttpService, @Inject(BASE_URL) private baseUrl: string) { }


  sign(user: User) {
    const httpOptions = {
      responseType: 'text' as const
    };
    return this.http.post(`${this.baseUrl}/user/sign`, user, httpOptions);
  }

  activate(code: string) {
    const httpOptions = {
      responseType: 'text' as const
    };
    let obj = {"code": code};
    return this.http.post(`${this.baseUrl}/user/activation`, obj, httpOptions)
  }

  getUserById(): Observable<User> {
    const httpOptions = this.httpService.generateHttpOptions('json');
    return this.http.get<User>(`${this.baseUrl}/user/get`, httpOptions)
  }

  updateUserInfo(user: User): Observable<string> {
    const httpOptions = this.httpService.generateHttpOptions('text')
    return this.http.put<string>(`${this.baseUrl}/user/update`,user,httpOptions)
  }

  updatePassword(formData: FormData): Observable<string> {
    const httpOptions = this.httpService.generateHttpOptions('text');
    return this.http.put<string>(`${this.baseUrl}/user/update/password`, formData, httpOptions)
  }
  
}
