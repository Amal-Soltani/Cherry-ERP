import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class BOMService {

  readonly apiUrl = '/cherry-hse/bom';

  constructor(private http: HttpClient) { }


  add(idProduct:any,nomenclature:any){
    return this.http.post(`${this.apiUrl}/add/${idProduct}`,nomenclature,httpOptions);
  }

  generateReference(){
    return this.http.get(`${this.apiUrl}/generateReference`,httpOptions)
  }
  
  deleteNomenclature( id : any){
    return this.http.delete(`${this.apiUrl}/deleteBYId/${id}`,httpOptions);
  }
}
