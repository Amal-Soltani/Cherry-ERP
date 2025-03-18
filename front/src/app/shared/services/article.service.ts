import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Page } from '../models/page';
import { Observable } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};


@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  readonly apiUrl = '/cherry-hse/article';

  constructor(private http: HttpClient) { }

  findByPage(p: number, size: number): Observable<any> {
    const params = new HttpParams()
      .set('page', String(p)).set('pageSize', String(size));
    return this.http.get<Page>(`${this.apiUrl}/all`, { params });
  }

  getAll(){
    return this.http.get(`${this.apiUrl}/findAll`,httpOptions)
  }

  generateReference(){
    return this.http.get(`${this.apiUrl}/generateReference`,httpOptions)
  }

  getByProduct(id:any){
    return this.http.get(`${this.apiUrl}/byProduct/${id}`,httpOptions)
  }

  FindAllExecptUsed(idProduct:any){
    return this.http.get(`${this.apiUrl}/findAllExecptUsed/${idProduct}`,httpOptions)
  }

  delete(id:any){
    return this.http.delete(`${this.apiUrl}/deleteById/${id}`,httpOptions)
  }

  add(article: any){
    return this.http.post(`${this.apiUrl}/add`,article,httpOptions)
  }

  getByLibelle(libelle:any){
    return this.http.get(`${this.apiUrl}/findByLibelle/${encodeURIComponent(libelle)}`,httpOptions)
  }
}
