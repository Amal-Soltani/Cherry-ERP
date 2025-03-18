import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class FNCAnomaliesService {

  readonly apiUrl = '/cherry-hse/fncAnomalie';

  constructor(private http: HttpClient) { }

  findAll(id: any): Observable<any> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.get(url);
  }

  add(ficheAn: any, fnc:any) {
    const url = `${this.apiUrl}/${fnc}`;
    return this.http.post(url, ficheAn, httpOptions);
  }

  delete(fiche :number, id:number) {
    const url = `${this.apiUrl}/${fiche}/${id}`;
    return this.http.delete<any>(url, httpOptions);
  }

  GetFNCCountByYearAndMonth(year: number): Observable<any> {
    const params = new HttpParams().set('year', year.toString());
   return this.http.get(`${this.apiUrl}/month/${year}`, { params });
  }

  GetFNCCountByYearAndWeek(year: number): Observable<any> {
  const params = new HttpParams().set('year', year.toString());
   return this.http.get(`${this.apiUrl}/week/${year}`, { params });
  }
}
