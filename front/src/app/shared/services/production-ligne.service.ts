import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Page } from '../models/page';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};
const apiUrl = '/cherry-hse/productionLigne';

@Injectable({
  providedIn: 'root'
})
export class ProductionLigneService {

  constructor(private http: HttpClient) { }

  findByPage(p: number, size: number): Observable<any> {
    const params = new HttpParams()
      .set('page', String(p)).set('pageSize', String(size));
    return this.http.get<Page>(`${apiUrl}/all`, { params });
  }

  findAll(): Observable<any> {
    const url = `${apiUrl}/findAll`;
    return this.http.get<any>(url);
  }

  generateReference(){
    return this.http.get(`${apiUrl}/generateReference`,httpOptions)
  }

  getById(id: number) {
    const url = `${apiUrl}/${id}`;
    return this.http.get<any>(url);
  }

  add(ligneProduction: any) {
    const url = `${apiUrl}/add`;
    return this.http.post<any>(url, ligneProduction, httpOptions);
  }
  delete(id: number) {
    const url = `${apiUrl}/${id}`;
    return this.http.delete<any>(url, httpOptions);
  }
}
