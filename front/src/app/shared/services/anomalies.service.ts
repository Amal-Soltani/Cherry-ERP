import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Page } from '../models/page';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AnomaliesService {

  readonly apiUrl = '/cherry-hse/anomalie';

  constructor(private http: HttpClient) { }

  add(anomalie: any) {
    const url = `${this.apiUrl}/add`;
    return this.http.post(url, anomalie, httpOptions);
  }

  findByPage(p: number, size: number): Observable<any> {
    const params = new HttpParams()
      .set('page', String(p)).set('pageSize', String(size));
    return this.http.get<Page>(`${this.apiUrl}/all`, { params });
  }

  findAll(): Observable<any> {
    const url = `${this.apiUrl}/findAll`;
    return this.http.get(url);
  }

  findByType(type : any){
    return this.http.get(`${this.apiUrl}/findByType/${type}`, httpOptions)
  }

  delete(id: number) {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete(url, httpOptions);
  }

  findCategoryPercentageByYear(year: number): Observable<any> {
    const params = new HttpParams().set('year', year.toString());
    return this.http.get(`${this.apiUrl}/category-percentage/${year}`, { params });
   }
}
