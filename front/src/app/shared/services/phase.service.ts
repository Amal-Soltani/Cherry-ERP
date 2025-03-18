import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Page } from '../models/page';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})


export class PhaseService {
  readonly apiUrl = '/cherry-hse/phase';

  constructor(private http: HttpClient) { }

  findByPage(p: number, size: number): Observable<any> {
    const params = new HttpParams()
      .set('page', String(p)).set('pageSize', String(size));
    return this.http.get<Page>(`${this.apiUrl}/all`, { params });
  }

  findAll(): Observable<any> {
    const url = `${this.apiUrl}/findAll`;
    return this.http.get(url);
  }

  getById(id: number) {
    const url = `${this.apiUrl}/${id}`;
    return this.http.get(url);
  }

  getByName(name: string) {
    const url = `${this.apiUrl}/find/${name}`;
    return this.http.get(url);
  }

  getPhase(tacheId : any){
    return this.http.get(`${this.apiUrl}/phases/${tacheId}`,httpOptions);
  }

  add(phase: any) {
    const url = `${this.apiUrl}/add`;
    return this.http.post(url, phase, httpOptions);
  }

  delete(id: number) {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete(url, httpOptions);
  }
}
