import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class RawMaterialService {

  readonly apiUrl = '/cherry-hse/rawMaterial';

  constructor(private http: HttpClient) { }

  add(numProduct : any, codeArticle : any, dimensionBrut : any, qteBrut : any ){
    return this.http.post(`${this.apiUrl}/add/${numProduct}/${codeArticle}/${dimensionBrut}/${qteBrut}`,httpOptions)
  }

  update(idProduct:any,id:any, dimensionBrut : any, qteBrut : any){
    return this.http.put(`${this.apiUrl}/update/${id}/${idProduct}/${dimensionBrut}/${qteBrut}`,httpOptions)
  }

  getAll(numProduct : any ){
    return this.http.get(`${this.apiUrl}/getAll/${numProduct}`,httpOptions)
  }

  delete(numProduct:any, codeArticle:any){
    return this.http.delete(`${this.apiUrl}/delete/${numProduct}/${codeArticle}`,httpOptions)
  }

  deleteByProduct(numProduct:any){
    return this.http.delete(`${this.apiUrl}/deleteByProduct/${numProduct}`,httpOptions)
  }
}
