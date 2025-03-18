import { HttpClient, HttpErrorResponse, HttpEvent, HttpEventType, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, map, throwError } from 'rxjs';
import { Page } from '../models/page';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class DocumentService {

  readonly apiUrl = '/cherry-hse/document';

  constructor(private http: HttpClient) { }

  save(id : any, indice){
    return this.http.post(`${this.apiUrl}/add/${id}/${indice}`,httpOptions)
  }

  findByPageAndProduct(p: number, size: number, idProduct: number): Observable<any> {
    const params = new HttpParams()
      .set('page', String(p))
      .set('pageSize', String(size))
      .set('idProduct', String(idProduct));
    return this.http.get<Page>(`${this.apiUrl}/allByProduct`, { params });
  }

  findAll():Observable<any> {
    const url = `${this.apiUrl}/all`;
    return this.http.get<any>(url);
  }

  findByProduct(idProduct: any) {
    return this.http.get(`${this.apiUrl}/findByProduct/${idProduct}`, httpOptions)
  }

  getOne(ref: any) {
    return this.http.get(`${this.apiUrl}/getOne/${ref}`, httpOptions)
  }

  delete(id: any) {
    return this.http.delete(`${this.apiUrl}/delete/${id}`, httpOptions)
  }

  download(id):Observable<HttpEvent<Blob>> {
    return this.http.get(`${this.apiUrl}/file/${id}`, { 
      reportProgress: true,
      observe: 'events',
      responseType: 'blob' })
  }

  upload(idProduct:any, indice: any | undefined, formData):Observable<HttpEvent<string[]>>  {
    return this.http.post<string[]>(`${this.apiUrl}/upload/${idProduct}/${indice}`, formData, {
      reportProgress: true,
      observe: 'events',
    })
  }

  getFile(id: number): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/file/${id}`, { responseType: 'blob' });
  }


}
