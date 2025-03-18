import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Page } from '../models/page';
import {ConfigService} from '../../core/services/config.service';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class GenericParametersService {

  apiUrl = ConfigService.config.APIEndpoint + '/generic-parameters';

  constructor(private http: HttpClient) { }

  findByCompany(): Observable<any> {
    const params = new HttpParams();
    const url = this.apiUrl;
    return this.http.get<any>(url, {params});
  }

  create(parameters: any) {
    return this.http.post<any>(this.apiUrl, parameters, httpOptions);
  }

  update(id: number, parameters: any) {
    return this.http.put<any>(this.apiUrl + '/' + id, parameters, httpOptions);
  }

}
