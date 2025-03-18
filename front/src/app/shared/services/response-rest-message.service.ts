import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import { Page } from '../models/page';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};
const apiUrl = '/cherry-hse/response-rest-msgs';

@Injectable()
export class ResponseRestMessageService {

  constructor(private http: HttpClient) {}

  findByPage(p: number, size: number): Observable<any> {
    const params = new HttpParams()
      .set('page', String(p)).set('pageSize', String(size));
    return this.http.get<Page>(apiUrl, {params});
  }

  findAll(): Observable<any> {
    const url = `${apiUrl}/all`;
    return this.http.get<any>(url);
  }

  getById(id: number) {
    const url = `${apiUrl}/${id}`;
    return this.http.get<any>(url);
  }

  create(msg: any) {
    return this.http.post<any>(apiUrl, msg, httpOptions);
  }

  update(id: number, msg: any) {
    const url = `${apiUrl}/${id}`;
    return this.http.put(url, msg, httpOptions);
  }

  delete(id: number) {
    const url = `${apiUrl}/${id}`;
    return this.http.delete<any>(url, httpOptions);
  }
}
