import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { LineProcurement } from 'src/app/models/procurement/line-procurement.model';
import { AuthService } from '../login/auth.service';
import { Observable } from 'rxjs';
import { Procurement } from 'src/app/models/procurement/procurement.model';
import { ProcurementState } from 'src/app/enums/procrument-state.enum';
import { BASE_URL } from 'src/app/core/constants/constants';
import { HttpService } from '../http-service/http.service';

@Injectable({
  providedIn: 'root'
})
export class ProcurementService {

  constructor(private http: HttpClient, private httpService: HttpService,  @Inject(BASE_URL) private baseUrl: string) { }

  createLineProcurement(lineProcurement: LineProcurement): Observable<string> {
    const httpOptions = this.httpService.generateHttpOptions("text");
    return this.http.post<string>(`${this.baseUrl}/line/create`,lineProcurement, httpOptions);
  }

  updateLineProcurement(line: LineProcurement): Observable<string> {
    const httpOptions = this.httpService.generateHttpOptions("text");
    return this.http.put<string>(`${this.baseUrl}/line/update/${line.lineId}`,line, httpOptions)
  }

  deleteLineProcurement(id: number): Observable<string> {
    const httpOptions = this.httpService.generateHttpOptions("text");
    return this.http.delete<string>(`${this.baseUrl}/line/delete/${id}`,httpOptions)
  }


  getCurrentProcurement(): Observable<Procurement> {
    const httpOptions = this.httpService.generateHttpOptions("json");
    return this.http.get<Procurement>(`${this.baseUrl}/procurement/current`, httpOptions);
  }

  getPastProcurement(): Observable<Procurement[]> {
    const httpOptions = this.httpService.generateHttpOptions("json");
    return this.http.get<Procurement[]>(`${this.baseUrl}/procurement/past`, httpOptions);
  }

  getValidateProcurement(): Observable<Procurement[]> {
    const httpOptions = this.httpService.generateHttpOptions("json");
    return this.http.get<Procurement[]>(`${this.baseUrl}/procurement/validate`, httpOptions);
  }

  getProcurement(id: number): Observable<Procurement> {
    const httpOptions = this.httpService.generateHttpOptions("json");
    return this.http.get<Procurement>(`${this.baseUrl}/procurement/${id}`, httpOptions);
  }

  getAllValidateProcurement(): Observable<Procurement[]> {
    const httpOptions = this.httpService.generateHttpOptions("json");
    return this.http.get<Procurement[]>(`${this.baseUrl}/procurement/validate/all`, httpOptions);
  }

  updateProcurement(procrument: Procurement): Observable<string> {
    const httpOptions = this.httpService.generateHttpOptions("text");
    return this.http.put<string>(`${this.baseUrl}/procurement/update/${procrument.procurementId}`, procrument,httpOptions);
  }

  validateProcurement(procrument: Procurement): Observable<string> {
    const httpOptions = this.httpService.generateHttpOptions("text");
    return this.http.put<string>(`${this.baseUrl}/procurement/validate/${procrument.procurementId}`,null,httpOptions);
  }

  completeProcurement(procrument: Procurement): Observable<string> {
    const httpOptions = this.httpService.generateHttpOptions("text");
    return this.http.put<string>(`${this.baseUrl}/procurement/complete/${procrument.procurementId}`,null,httpOptions);
  }

  deleteProcurement(procurementId: number): Observable<string> {
    const httpOptions = this.httpService.generateHttpOptions("text");
    return this.http.delete<string>(`${this.baseUrl}/procurement/delete/${procurementId}`, httpOptions)
  }



}
