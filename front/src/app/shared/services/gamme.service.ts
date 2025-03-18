import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};
const apiUrl = '/cherry-hse/gamme';

@Injectable({
  providedIn: 'root'
})
export class GammeService {

  constructor(private http: HttpClient) { }

  getByProduct(id: number) {
    const url = `${apiUrl}/${id}`;
    return this.http.get(url);
  }

  add(productId : any) {
    const url = `${apiUrl}/add/${productId}`;
    return this.http.post(url, httpOptions);
  }

}
