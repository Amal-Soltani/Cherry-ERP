import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Page } from '../models/page';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
const apiUrl = '/cherry-hse/incident';

@Injectable()
export class IncidentService {

  constructor(private http: HttpClient) { }

  findByPage(p: number, size: number, status?: string): Observable<any> {
    let params = new HttpParams()
      .set('page', String(p)).set('pageSize', String(size));
      if (status) {
        params = params.set('status', status);
      }
    return this.http.get<Page>(apiUrl, { params });
  }

  findAll(): Observable<any> {
    const url = `${apiUrl}/all-basic`;
    return this.http.get<any>(url);
  }

  getById(id: number) {
    const url = `${apiUrl}/${id}`;
    return this.http.get<any>(url);
  }

  create(employee: any) {
    return this.http.post<any>(apiUrl, employee, httpOptions);
  }

  update(id: number, employee: any) {
    const url = `${apiUrl}/${id}`;
    return this.http.put(url, employee, httpOptions);
  }

  delete(id: number) {
    const url = `${apiUrl}/${id}`;
    return this.http.delete<any>(url, httpOptions);
  }

}
