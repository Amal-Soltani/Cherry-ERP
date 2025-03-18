import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Page} from '../models/page';


const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};
const apiUrl = '/cherry-hse/generic-grids';

@Injectable()
export class GenericGridService {

  constructor(private http: HttpClient) {}

  findAll(): Observable<any> {
    const url = `${apiUrl}/all`;
    return this.http.get<any>(url);
  }

  getById(id: number) {
    const url = `${apiUrl}/${id}`;
    return this.http.get<any>(url);
  }

  create(category: any) {
    return this.http.post<any>(apiUrl, category, httpOptions);
  }

  update(id: number, category: any) {
    const url = `${apiUrl}/${id}`;
    return this.http.put(url, category, httpOptions);
  }

  delete(id: number) {
    const url = `${apiUrl}/${id}`;
    return this.http.delete<any>(url, httpOptions);
  }
}
