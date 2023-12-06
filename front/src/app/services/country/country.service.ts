import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CountryService {
  private cityCache: { [key: string]: string[] } = {};

  constructor(private http: HttpClient) { }

  getCountries(): Observable<string[]> {
    return this.http.get<any[]>('https://restcountries.com/v3.1/subregion/Western Europe').pipe(
      map(response => {
        if (response && response.length > 0) {
          return response.map(country => country.name ? country.name.common : null);
        } else {
          return [];
        }
      })
    );
  }

  getCity(country: string) : Observable<string[]> {
    if (this.cityCache[country]) {
      return of(this.cityCache[country]); // Retournez les villes depuis le cache
    } else {
      return this.http.post<any>('https://countriesnow.space/api/v0.1/countries/cities', {"country": country}).pipe( map(response => {
        const cities = response.data;
        this.cityCache[country] = cities;
        return cities;
      }))
    }
    
  }
}
