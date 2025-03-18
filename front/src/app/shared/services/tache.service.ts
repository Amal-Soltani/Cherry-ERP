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
export class TacheService {

  readonly apiUrl = '/cherry-hse/task';

  constructor(private http: HttpClient) { }

  getAll(){
    return this.http.get(`${this.apiUrl}/findAll`,httpOptions);
  }

  findByPage(p: number, size: number): Observable<any> {
    const params = new HttpParams()
      .set('page', String(p)).set('pageSize', String(size));
    return this.http.get<Page>(`${this.apiUrl}/all`, { params });
  }

  findByPageAndProject(p: number, size: number, idProjet: number): Observable<any> {
    let params = new HttpParams()
    .set('page', String(p))
    .set('pageSize', String(size))
    .set('idProjet', String(idProjet));
  return this.http.get<Page>(`${this.apiUrl}/allByProject`, { params });
  }

  findByPageAndStatus(p: number, size: number, status: string): Observable<any> {
    let params = new HttpParams()
    .set('page', String(p))
    .set('pageSize', String(size))
    .set('status', String(status));
  return this.http.get<Page>(`${this.apiUrl}/allByStatus`, { params });
}

findByPageAndProjectAndStatus(p: number, size: number, idProjet: number,status: string): Observable<any> {
  let params = new HttpParams()
  .set('page', String(p))
  .set('pageSize', String(size))
  .set('idProjet', String(idProjet))
  .set('status', String(status));
return this.http.get<Page>(`${this.apiUrl}/allByProjectAndStatus`, { params });
}

  add(tache:any){
    return this.http.post(`${this.apiUrl}/add`,tache,httpOptions);
  }
  generateReference(){
    return this.http.get(`${this.apiUrl}/generateReference`,httpOptions)
  }
  
  AddSousTache(tache:any , idProjet :any, parent : any ,libelleProduit:any){
    const encodedNom = encodeURIComponent(libelleProduit);
    return this.http.post(`${this.apiUrl}/addSousTache/${idProjet}/${parent}/${encodedNom}`,tache,httpOptions);
  }

  getByProject(idProjet :any){
    return this.http.get(`${this.apiUrl}/byProject/${idProjet}`,httpOptions);
  }

  getSousTache(tacheId :any,projetId :any){
    return this.http.get(`${this.apiUrl}/sousTache/${tacheId}/${projetId}`,httpOptions);
  }

  getById(id : any){
    return this.http.get(`${this.apiUrl}/${id}`,httpOptions);
  }

  getByClient(tacheId : any){
    return this.http.get(`${this.apiUrl}/byClient/${tacheId}`,httpOptions);
  }


  deleteTache( id : any){
    return this.http.delete(`${this.apiUrl}/${id}`,httpOptions);
  }

}
