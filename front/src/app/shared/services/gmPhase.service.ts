import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})

export class GmPhaseService {

  readonly apiUrl = '/cherry-hse/gmPhase';

  constructor(private http: HttpClient) { }

  add(gmPhase:any,gammeId:number){
    return this.http.post(`${this.apiUrl}/${gammeId}`,gmPhase,httpOptions)
  }

  getAll(gammeId : any){
    return this.http.get(`${this.apiUrl}/${gammeId}`,httpOptions);
  }

  getOne(gammeId : any, phaseId:any){
    return this.http.get(`${this.apiUrl}/${gammeId}/${phaseId}`,httpOptions);
  }


  delete(id: any, gammeId: any){
    return this.http.delete(`${this.apiUrl}/${id}/${gammeId}`,httpOptions);
  }

}