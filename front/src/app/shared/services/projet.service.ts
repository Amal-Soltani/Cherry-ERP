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
export class ProjetService {

  readonly apiUrl = '/cherry-hse/project';

  constructor(private http: HttpClient) { }

  findByPage(p: number, size: number): Observable<any> {
    const params = new HttpParams()
      .set('page', String(p)).set('pageSize', String(size));
    return this.http.get<Page>(`${this.apiUrl}/all`, { params });
  }

  findByPageAndStatus(p: number, size: number, status: string): Observable<any> {
    let params = new HttpParams()
    .set('page', String(p))
    .set('pageSize', String(size))
    .set('status', String(status));
  return this.http.get<Page>(`${this.apiUrl}/allByStatus`, { params });
}

  addProjet(projet:any){
    return this.http.post(`${this.apiUrl}`,projet,httpOptions);
  }

  generateReference(){
    return this.http.get(`${this.apiUrl}/generateReference`,httpOptions)
  }

  FindAllProjets(): Observable<any>{
     return this.http.get(`${this.apiUrl}`,httpOptions);
  }

  getById(id:any){
    return this.http.get(`${this.apiUrl}/${id}`,httpOptions);
 }

  DeleteProjetBYId(id:any){
    return this.http.delete(`${this.apiUrl}/${id}`,httpOptions);
  }
}
