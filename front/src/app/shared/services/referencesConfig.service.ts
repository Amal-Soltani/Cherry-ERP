import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
const apiUrl = '/cherry-hse/reference-configs';

@Injectable()
export class ReferencesConfigService {

  constructor(private http: HttpClient) {}

  findByYear(year: number): Observable<any> {
    const url = `${apiUrl}/year/` + year;
    return this.http.get<any>(url);
  }

  findWithoutYear(): Observable<any> {
    const url = `${apiUrl}/without-year`;
    return this.http.get<any>(url);
  }

  findAll(): Observable<any> {
    const url = `${apiUrl}/all`;
    return this.http.get<any>(url);
  }

  getById(id: number) {
    const url = `${apiUrl}/${id}`;
    return this.http.get<any>(url);
  }

  create(refConfig: any) {
    return this.http.post<any>(apiUrl, refConfig, httpOptions);
  }

  update(id: number, refConfig: any) {
    const url = `${apiUrl}/${id}`;
    return this.http.put(url, refConfig, httpOptions);
  }

  delete(id: number) {
    const url = `${apiUrl}/${id}`;
    return this.http.delete<any>(url, httpOptions);
  }

}
