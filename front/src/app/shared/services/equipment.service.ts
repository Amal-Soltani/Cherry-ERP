import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Page } from '../models/page';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
const apiUrl = '/cherry-hse/equipments';

@Injectable({
  providedIn: 'root'
})
export class EquipmentService {

  constructor(private http: HttpClient) { }


  findByPage(p: number, size: number): Observable<any> {
    const params = new HttpParams()
      .set('page', String(p)).set('pageSize', String(size));
    return this.http.get<Page>(`${apiUrl}/findAll`, { params });
  }


  findAll(): Observable<any> {
    const url = `${apiUrl}/all`;
    return this.http.get<any>(url);
  }

  generateReference() {
    return this.http.get(`${apiUrl}/generateReference`, httpOptions)
  }

  create(equipment: any) {
    const url = `${apiUrl}/add`;
    return this.http.post<any>(url, equipment, httpOptions);
  }

  delete(id: number) {
    const url = `${apiUrl}/${id}`;
    return this.http.delete<any>(url, httpOptions);
  }
}
