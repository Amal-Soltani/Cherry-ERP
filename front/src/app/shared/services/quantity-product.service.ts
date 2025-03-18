import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class QuantityProductService {

  readonly apiUrl = '/cherry-hse/quantityProduct';

  constructor(private http: HttpClient) { }

  add(parent: any,child: any,qte: any){
    return this.http.post(`${this.apiUrl}/add/${parent}/${child}/${qte}`,httpOptions)
  }
  getAll(numProduct : any ){
    return this.http.get(`${this.apiUrl}/getAll/${numProduct}`,httpOptions)
  }

  update(id : any,child: any, qte:any){
    return this.http.put(`${this.apiUrl}/update/${id}/${child}/${qte}`,httpOptions)
  }

  delete(parent :any, child:any){
    return this.http.delete(`${this.apiUrl}/delete/${parent}/${child}`,httpOptions)
  }
  deleteByParent(parent :any){
    return this.http.delete(`${this.apiUrl}/deleteByParent/${parent}`,httpOptions)
  }
}
