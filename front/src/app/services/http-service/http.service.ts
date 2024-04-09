import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  constructor() { }


  generateHttpOptions(type: string): {} {
    const bearerToken = localStorage.getItem('bearer');

    const httpOptions = {
        headers: new HttpHeaders().set('Authorization', `Bearer ${bearerToken}`),
        responseType: `${type}` as const
    };
    return httpOptions;
  }
}
