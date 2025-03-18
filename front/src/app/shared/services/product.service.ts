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
export class ProductService {

  readonly apiUrl = '/cherry-hse/product';

  constructor(private http: HttpClient) { }

  getAll(): Observable<any> {
    return this.http.get(`${this.apiUrl}/findAll`,httpOptions)
  }

  generateReference(){
    return this.http.get(`${this.apiUrl}/generateReference`,httpOptions)
  }

  findByPage(p: number, size: number): Observable<any> {
    const params = new HttpParams()
      .set('page', String(p)).set('pageSize', String(size));
    return this.http.get<Page>(`${this.apiUrl}/all`, { params });
  }

  findByPageAndType(p: number, size: number, type: string): Observable<any> {
    let params = new HttpParams()
    .set('page', String(p))
    .set('pageSize', String(size))
    .set('type', String(type));
  return this.http.get<Page>(`${this.apiUrl}/allByType`, { params });
}

  findByPageAndProject(p: number, size: number, idProjet: number): Observable<any> {
    let params = new HttpParams()
    .set('page', String(p))
    .set('pageSize', String(size))
    .set('idProjet', String(idProjet));
  return this.http.get<Page>(`${this.apiUrl}/allByProject`, { params });
  }

  AddProduct(product: any){
    return this.http.post(`${this.apiUrl}/add`,product,httpOptions)
  }

  getById(id:any): Observable<any> {
    return this.http.get(`${this.apiUrl}/findById/${id}`,httpOptions)
  }

  getAvailableByProject(idProjet : any): Observable<any> {
    return this.http.get(`${this.apiUrl}/findAvailableByProject/${idProjet}`,httpOptions)
  }

  getAvailable(): Observable<any> {
    return this.http.get(`${this.apiUrl}/findAvailable`,httpOptions)
  }

  getByReference(reference : any ){
    return this.http.get(`${this.apiUrl}/findByReference/${reference}`,httpOptions)
  }
  getByLibelle(libelle : any ){
    return this.http.get(`${this.apiUrl}/findByLibelle/${encodeURIComponent(libelle)}`,httpOptions)
  }

  FindAllExceptParent(id : any ){
    return this.http.get(`${this.apiUrl}/findAllExceptParent/${id}`,httpOptions)
  }

  affecterProductNomenclature(id:any,numNomenclature:any){
    return this.http.put(`${this.apiUrl}/affecterNomenclature/${id}/${numNomenclature}`,httpOptions)
  }

  getNomenclature(id:any){
    return this.http.get(`${this.apiUrl}/findNomenclature/${id}`,httpOptions)
  }

  getByParent(id:any): Observable<any> {
    return this.http.get(`${this.apiUrl}/findByParent/${id}`,httpOptions)
  }

  update(id:any, product : any){
    return this.http.put(`${this.apiUrl}/update/${id}`,product,httpOptions)
  }

  delete(id:any){
    return this.http.delete(`${this.apiUrl}/deleteById/${id}`,httpOptions)
  }

  getAllByProject(id:any){
    return this.http.get(`${this.apiUrl}/findAllByProject/${id}`,httpOptions)
  }
}
