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
export class FNCService {

  readonly apiUrl = '/cherry-hse/fnc';

  constructor(private http: HttpClient) { }


  add(fiche: any) {
    const url = `${this.apiUrl}/add`;
    return this.http.post(url, fiche, httpOptions);
  }

  generateReference(){
    return this.http.get(`${this.apiUrl}/generateReference`,httpOptions)
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

  findByYear(year : any): Observable<any> {
    const url = `${this.apiUrl}/year/${year}`;
    return this.http.get(url);
  }

  findTheFirstDate() {
    const url = `${this.apiUrl}/first`;
    return this.http.get(url);
  }


  delete(id: number) {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete(url, httpOptions);
  }

  findFNCPercentageByYear(year: number): Observable<any> {
    const params = new HttpParams().set('year', year.toString());
    return this.http.get(`${this.apiUrl}/fnc-percentage/${year}`, { params });
   }
}
